/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package instances.ElcadiasTent;

import quests.Q10292_SevenSignsGirlOfDoubt.Q10292_SevenSignsGirlOfDoubt;
import quests.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom;
import quests.Q10294_SevenSignsToTheMonasteryOfSilence.Q10294_SevenSignsToTheMonasteryOfSilence;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Elcadia's Tent instance zone.
 * @author Adry_85
 */
public final class ElcadiasTent extends AbstractNpcAI
{
	protected class ETWorld extends InstanceWorld
	{
		
	}
	
	private static final int TEMPLATE_ID = 158;
	// NPCs
	private static final int ELCADIA = 32784;
	private static final int GRUFF_LOOKING_MAN = 32862;
	// Locations
	private static final Location START_LOC = new Location(89706, -238074, -9632, 0, 0);
	private static final Location EXIT_LOC = new Location(43316, -87986, -2832, 0, 0);
	
	private ElcadiasTent()
	{
		super(ElcadiasTent.class.getSimpleName(), "instances");
		addFirstTalkId(GRUFF_LOOKING_MAN, ELCADIA);
		addStartNpc(GRUFF_LOOKING_MAN, ELCADIA);
		addTalkId(GRUFF_LOOKING_MAN, ELCADIA);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		if (npc.getId() == GRUFF_LOOKING_MAN)
		{
			final QuestState GirlOfDoubt = talker.getQuestState(Q10292_SevenSignsGirlOfDoubt.class.getSimpleName());
			final QuestState ForbiddenBook = talker.getQuestState(Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.class.getSimpleName());
			final QuestState Monastery = talker.getQuestState(Q10294_SevenSignsToTheMonasteryOfSilence.class.getSimpleName());
			if (((GirlOfDoubt != null) && GirlOfDoubt.isStarted()) //
				|| ((GirlOfDoubt != null) && GirlOfDoubt.isCompleted() && (ForbiddenBook == null)) //
				|| ((ForbiddenBook != null) && ForbiddenBook.isStarted()) //
				|| ((ForbiddenBook != null) && ForbiddenBook.isCompleted() && (Monastery == null)))
			{
				enterInstance(talker, "ElcadiasTent.xml", START_LOC);
			}
			else
			{
				return "32862-01.html";
			}
		}
		else
		{
			final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(talker);
			world.removeAllowed(talker.getObjectId());
			talker.setInstanceId(0);
			talker.teleToLocation(EXIT_LOC);
		}
		return super.onTalk(npc, talker);
	}
	
	private void enterInstance(L2PcInstance player, String template, Location loc)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof ETWorld))
			{
				player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			}
			else
			{
				teleportPlayer(player, loc, world.getInstanceId(), false);
			}
		}
		else
		{
			// New instance.
			world = new ETWorld();
			world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			world.setTemplateId(TEMPLATE_ID);
			world.setStatus(0);
			InstanceManager.getInstance().addWorld(world);
			_log.info("Elcadia's Tent started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
			// Teleport players.
			teleportPlayer(player, loc, world.getInstanceId(), false);
			world.addAllowed(player.getObjectId());
		}
	}
	
	public static void main(String[] args)
	{
		new ElcadiasTent();
	}
}
