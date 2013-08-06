/*
 * Copyright (C) 2004-2013 L2J DataPack
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
package hellbound.Natives;

import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * This class manages Natives' behavior up to 9 level of Hellbound. 10 level are handled in Hellbound Town.
 * @author DS, GKR
 */
public class Natives extends Quest
{
	private static final int NATIVE = 32362;
	private static final int INSURGENT = 32363;
	private static final int TRAITOR = 32364;
	private static final int INCASTLE = 32357;
	private static final int MARK_OF_BETRAYAL = 9676;
	private static final int BADGES = 9674;
	
	private static final int[] doors =
	{
		19250003,
		19250004
	};
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		final int hellboundLevel = HellboundManager.getInstance().getLevel();
		final int npcId = npc.getId();
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		switch (npcId)
		{
			case NATIVE:
				htmltext = hellboundLevel > 5 ? "32362-01.htm" : "32362.htm";
				break;
			case INSURGENT:
				htmltext = hellboundLevel > 5 ? "32363-01.htm" : "32363.htm";
				break;
			case INCASTLE:
				if (hellboundLevel < 9)
				{
					htmltext = "32357-01a.htm";
				}
				else if (hellboundLevel == 9)
				{
					htmltext = npc.isBusy() ? "32357-02.htm" : "32357-01.htm";
				}
				else
				{
					htmltext = "32357-01b.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (npc.getId() == TRAITOR)
		{
			if (event.equalsIgnoreCase("open_door"))
			{
				final QuestState qs = player.getQuestState(getName());
				if (qs.getQuestItemsCount(MARK_OF_BETRAYAL) >= 10)
				{
					qs.takeItems(MARK_OF_BETRAYAL, 10);
					npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.ALRIGHT_NOW_LEODAS_IS_YOURS));
					HellboundManager.getInstance().updateTrust(-50, true);
					
					for (int doorId : doors)
					{
						L2DoorInstance door = DoorTable.getInstance().getDoor(doorId);
						if (door != null)
						{
							door.openMe();
						}
					}
					
					cancelQuestTimers("close_doors");
					startQuestTimer("close_doors", 1800000, npc, player); // 30 min
				}
				else if (qs.hasQuestItems(MARK_OF_BETRAYAL))
				{
					htmltext = "32364-01.htm";
				}
				else
				{
					htmltext = "32364-02.htm";
				}
			}
			else if (event.equalsIgnoreCase("close_doors"))
			{
				for (int doorId : doors)
				{
					L2DoorInstance door = DoorTable.getInstance().getDoor(doorId);
					if (door != null)
					{
						door.closeMe();
					}
				}
			}
		}
		else if ((npc.getId() == NATIVE) && event.equalsIgnoreCase("hungry_death"))
		{
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.HUN_HUNGRY));
			npc.doDie(null);
		}
		else if (npc.getId() == INCASTLE)
		{
			if (event.equalsIgnoreCase("FreeSlaves"))
			{
				final QuestState qs = player.getQuestState(getName());
				if (qs.getQuestItemsCount(BADGES) >= 5)
				{
					qs.takeItems(BADGES, 5);
					npc.setBusy(true); // Prevent Native from take items more, than once
					HellboundManager.getInstance().updateTrust(100, true);
					htmltext = "32357-02.htm";
					startQuestTimer("delete_me", 3000, npc, null);
				}
				else
				{
					htmltext = "32357-02a.htm";
				}
			}
			else if (event.equalsIgnoreCase("delete_me"))
			{
				npc.setBusy(false); // TODO: Does it really need?
				npc.deleteMe();
				npc.getSpawn().decreaseCount(npc);
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if ((npc.getId() == NATIVE) && (HellboundManager.getInstance().getLevel() < 6))
		{
			startQuestTimer("hungry_death", 600000, npc, null);
		}
		
		return super.onSpawn(npc);
	}
	
	public Natives(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(NATIVE);
		addFirstTalkId(INSURGENT);
		addFirstTalkId(INCASTLE);
		addStartNpc(TRAITOR);
		addStartNpc(INCASTLE);
		addTalkId(TRAITOR);
		addTalkId(INCASTLE);
		addSpawnId(NATIVE);
	}
	
	public static void main(String[] args)
	{
		new Natives(-1, "Natives", "hellbound");
	}
}
