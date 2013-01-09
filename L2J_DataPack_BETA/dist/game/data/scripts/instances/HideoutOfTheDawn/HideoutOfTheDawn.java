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
package instances.HideoutOfTheDawn;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Hideout of the Dawn instance zone.
 * @author Adry_85
 */
public class HideoutOfTheDawn extends Quest
{
	protected class HotDWorld extends InstanceWorld
	{
		long storeTime = 0;
	}
	
	private static final int INSTANCEID = 113;
	// NPCs
	private static final int WOOD = 32593;
	private static final int JAINA = 32617;
	// Location
	private static final Location WOOD_LOC = new Location(-23758, -8959, -5384, 0, 0);
	private static final Location JAINA_LOC = new Location(147072, 23743, -1984, 0);
	
	public HideoutOfTheDawn(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(WOOD);
		addTalkId(WOOD, JAINA);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (npc.getNpcId())
		{
			case WOOD:
			{
				enterInstance(talker, "HideoutOfTheDawn.xml", WOOD_LOC);
				return "32593-01.htm";
			}
			case JAINA:
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(talker);
				world.removeAllowed(talker.getObjectId());
				talker.setInstanceId(0);
				talker.teleToLocation(JAINA_LOC, 0);
				return "32617-01.htm";
			}
		}
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof HotDWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			removeBuffs(player);
			return 0;
		}
		// New instance
		world = new HotDWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((HotDWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("SevenSign started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		removeBuffs(player);
		world.addAllowed(player.getObjectId());
		
		return world.getInstanceId();
	}
	
	private static final void removeBuffs(L2Character ch)
	{
		for (L2Effect e : ch.getAllEffects())
		{
			if (e == null)
			{
				continue;
			}
			L2Skill skill = e.getSkill();
			if (skill.isDebuff() || skill.isStayAfterDeath())
			{
				continue;
			}
			e.exit();
		}
		if (ch.getSummon() != null)
		{
			for (L2Effect e : ch.getSummon().getAllEffects())
			{
				if (e == null)
				{
					continue;
				}
				L2Skill skill = e.getSkill();
				if (skill.isDebuff() || skill.isStayAfterDeath())
				{
					continue;
				}
				e.exit();
			}
		}
	}
	
	public static void main(String[] args)
	{
		new HideoutOfTheDawn(-1, HideoutOfTheDawn.class.getSimpleName(), "instances");
	}
}