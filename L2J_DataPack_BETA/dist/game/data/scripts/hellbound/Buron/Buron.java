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
package hellbound.Buron;

import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author DS
 */
public class Buron extends Quest
{
	private static final int BURON = 32345;
	private static final int HELMET = 9669;
	private static final int TUNIC = 9670;
	private static final int PANTS = 9671;
	private static final int DARION_BADGE = 9674;
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		if ("Rumor".equalsIgnoreCase(event))
		{
			htmltext = "32345-" + HellboundManager.getInstance().getLevel() + "r.htm";
		}
		else
		{
			if (HellboundManager.getInstance().getLevel() < 2)
			{
				htmltext = "32345-lowlvl.htm";
			}
			else
			{
				QuestState qs = player.getQuestState(getName());
				if (qs == null)
				{
					qs = newQuestState(player);
				}
				
				if (qs.getQuestItemsCount(DARION_BADGE) >= 10)
				{
					qs.takeItems(DARION_BADGE, 10);
					if (event.equalsIgnoreCase("Tunic"))
					{
						player.addItem("Quest", TUNIC, 1, npc, true);
					}
					else if (event.equalsIgnoreCase("Helmet"))
					{
						player.addItem("Quest", HELMET, 1, npc, true);
					}
					else if (event.equalsIgnoreCase("Pants"))
					{
						player.addItem("Quest", PANTS, 1, npc, true);
					}
					htmltext = null;
				}
				else
				{
					htmltext = "32345-noitems.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		
		switch (HellboundManager.getInstance().getLevel())
		{
			case 1:
				return "32345-01.htm";
			case 2:
			case 3:
			case 4:
				return "32345-02.htm";
			default:
				return "32345-01a.htm";
		}
	}
	
	public Buron(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(BURON);
		addStartNpc(BURON);
		addTalkId(BURON);
	}
	
	public static void main(String[] args)
	{
		new Buron(-1, "Buron", "hellbound");
	}
}
