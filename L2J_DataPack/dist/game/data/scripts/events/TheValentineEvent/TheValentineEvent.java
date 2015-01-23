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
package events.TheValentineEvent;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;

/**
 * The Valentine Event event AI.
 * @author Gnacik
 */
public final class TheValentineEvent extends LongTimeEvent
{
	// NPC
	private static final int NPC = 4301;
	// Item
	private static final int RECIPE = 20191;
	// Misc
	private static final String COMPLETED = TheValentineEvent.class.getSimpleName() + "_completed";
	
	private TheValentineEvent()
	{
		super(TheValentineEvent.class.getSimpleName(), "events");
		addStartNpc(NPC);
		addFirstTalkId(NPC);
		addTalkId(NPC);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("4301-3.htm"))
		{
			if (player.getVariables().getBoolean(COMPLETED, false))
			{
				htmltext = "4301-4.htm";
			}
			else
			{
				giveItems(player, RECIPE, 1);
				playSound(player, QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return npc.getId() + ".htm";
	}
	
	public static void main(String[] args)
	{
		new TheValentineEvent();
	}
}
