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
package quests.Q10273_GoodDayToFly;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Good Day to Fly (10273)<br>
 * Original Jython script by Kerberos v1.0 on 2009/04/25
 * @author nonom
 */
public class Q10273_GoodDayToFly extends Quest
{
	// NPC
	private static final int LEKON = 32557;
	// Monsters
	private static final int[] MOBS =
	{
		22614, // Vulture Rider
		22615, // Vulture Rider
	};
	
	// Item
	private static final int MARK = 13856;
	// Skills
	private static final SkillHolder AURA_BIRD_FALCON = new SkillHolder(5982, 1);
	private static final SkillHolder AURA_BIRD_OWL = new SkillHolder(5983, 1);
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		final int transform = st.getInt("transform");
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = "32557-0a.html";
				break;
			case State.CREATED:
				htmltext = (player.getLevel() < 75) ? "32557-00.html" : "32557-01.htm";
				break;
			default:
				if (st.getQuestItemsCount(MARK) >= 5)
				{
					htmltext = "32557-14.html";
					if (transform == 1)
					{
						st.giveItems(13553, 1);
					}
					else if (transform == 2)
					{
						st.giveItems(13554, 1);
					}
					st.giveItems(13857, 1);
					st.addExpAndSp(25160, 2525);
					st.exitQuest(false, true);
				}
				else if (transform == 0)
				{
					htmltext = "32557-07.html";
				}
				else
				{
					htmltext = "32557-11.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "32557-06.htm":
				st.startQuest();
				break;
			case "32557-09.html":
				st.set("transform", "1");
				AURA_BIRD_FALCON.getSkill().getEffects(player, player);
				break;
			case "32557-10.html":
				st.set("transform", "2");
				AURA_BIRD_OWL.getSkill().getEffects(player, player);
				break;
			case "32557-13.html":
				switch (st.getInt("transform"))
				{
					case 1:
						AURA_BIRD_FALCON.getSkill().getEffects(player, player);
						break;
					case 2:
						AURA_BIRD_OWL.getSkill().getEffects(player, player);
						break;
				}
				break;
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		final QuestState st = killer.getQuestState(getName());
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		final long count = st.getQuestItemsCount(MARK);
		if (st.isCond(1) && (count < 5))
		{
			st.giveItems(MARK, 1);
			if (count == 4)
			{
				st.setCond(2, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return null;
	}
	
	public Q10273_GoodDayToFly(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LEKON);
		addTalkId(LEKON);
		addKillId(MOBS);
		registerQuestItems(MARK);
	}
	
	public static void main(String[] args)
	{
		new Q10273_GoodDayToFly(10273, Q10273_GoodDayToFly.class.getSimpleName(), "Good Day to Fly");
	}
}
