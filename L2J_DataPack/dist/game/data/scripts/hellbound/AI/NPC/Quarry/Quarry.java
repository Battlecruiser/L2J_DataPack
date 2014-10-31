/*
 * Copyright (C) 2004-2014 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package hellbound.AI.NPC.Quarry;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2QuestGuardInstance;
import com.l2jserver.gameserver.model.holders.ItemChanceHolder;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

import hellbound.HellboundEngine;

/**
 * Quarry AI.
 * @author DS, GKR
 */
public final class Quarry extends AbstractNpcAI
{
	// NPCs
	private static final int SLAVE = 32299;
	// Items
	protected static final ItemChanceHolder[] DROP_LIST =
	{
		new ItemChanceHolder(9628, 261), // Leonard
		new ItemChanceHolder(9630, 175), // Orichalcum
		new ItemChanceHolder(9629, 145), // Adamantine
		new ItemChanceHolder(1876, 6667), // Mithril ore
		new ItemChanceHolder(1877, 1333), // Adamantine nugget
		new ItemChanceHolder(1874, 2222), // Oriharukon ore
	};
	// Zone
	private static final int ZONE = 40107;
	// Misc
	private static final int TRUST = 50;
	
	public Quarry()
	{
		super(Quarry.class.getSimpleName(), "hellbound/AI/NPC");
		addSpawnId(SLAVE);
		addFirstTalkId(SLAVE);
		addStartNpc(SLAVE);
		addTalkId(SLAVE);
		addKillId(SLAVE);
		addEnterZoneId(ZONE);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "FollowMe":
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
				npc.setTarget(player);
				npc.setAutoAttackable(true);
				npc.setRHandId(9136);
				npc.setWalking();
				
				if (getQuestTimer("TIME_LIMIT", npc, null) == null)
				{
					startQuestTimer("TIME_LIMIT", 900000, npc, null); // 15 min limit for save
				}
				htmltext = "32299-02.htm";
				break;
			}
			case "TIME_LIMIT":
			{
				for (L2ZoneType zone : ZoneManager.getInstance().getZones(npc))
				{
					if (zone.getId() == 40108)
					{
						npc.setTarget(null);
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
						npc.setAutoAttackable(false);
						npc.setRHandId(0);
						npc.teleToLocation(npc.getSpawn().getLocation());
						return null;
					}
				}
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.HUN_HUNGRY);
				npc.doDie(npc);
				break;
			}
			case "DECAY":
			{
				if ((npc != null) && !npc.isDead())
				{
					if (npc.getTarget().isPlayer())
					{
						for (ItemChanceHolder item : DROP_LIST)
						{
							if (getRandom(10000) < item.getChance())
							{
								npc.dropItem((L2PcInstance) npc.getTarget(), item.getId(), (int) (item.getCount() * Config.RATE_QUEST_DROP));
								break;
							}
						}
					}
					npc.setAutoAttackable(false);
					npc.deleteMe();
					npc.getSpawn().decreaseCount(npc);
					HellboundEngine.getInstance().updateTrust(TRUST, true);
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		npc.setAutoAttackable(false);
		if (npc instanceof L2QuestGuardInstance)
		{
			((L2QuestGuardInstance) npc).setPassive(true);
		}
		return super.onSpawn(npc);
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (HellboundEngine.getInstance().getLevel() != 5)
		{
			return "32299.htm";
		}
		return "32299-01.htm";
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		npc.setAutoAttackable(false);
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public final String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if (character.isAttackable())
		{
			final L2Attackable npc = (L2Attackable) character;
			if (npc.getId() == SLAVE)
			{
				if (!npc.isDead() && !npc.isDecayed() && (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_FOLLOW))
				{
					if (HellboundEngine.getInstance().getLevel() == 5)
					{
						startQuestTimer("DECAY", 1000, npc, null);
						try
						{
							broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.THANK_YOU_FOR_THE_RESCUE_ITS_A_SMALL_GIFT);
						}
						catch (Exception e)
						{
							//
						}
					}
				}
			}
		}
		return super.onEnterZone(character, zone);
	}
}