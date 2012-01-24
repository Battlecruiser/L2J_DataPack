/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10274_CollectingInTheAir;

import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;

/**
 * Collecting in the Air (10274).<br>
 * Original Jython script by Kerberos v1.0 on 2009/04/26
 * @author nonom
 */
public class Q10274_CollectingInTheAir extends Quest
{
	private static final String qn = "10274_CollectingInTheAir";
	
	// NPCs
	private static final int LEKON = 32557;
	
	// Items
	private static final int SCROLL = 13844;
	private static final int RED = 13858;
	private static final int BLUE = 13859;
	private static final int GREEN = 13860;
	
	private static final int MOBS[] =
	{
		18684, 18685, 18686, 18687, 18688, 18689, 18690, 18691, 18692
	};
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = "32557-0a.htm";
				break;
			case State.CREATED:
				QuestState qs = player.getQuestState("10273_GoodDayToFly");
				if (qs != null)
				{
					htmltext = (qs.isCompleted() && (player.getLevel() >= 75)) ? "32557-01.htm" : "32557-00.htm";
				}
				else
				{
					htmltext = "32557-00.htm";
				}
				break;
			case State.STARTED:
				if ((st.getQuestItemsCount(RED) + st.getQuestItemsCount(BLUE) + st.getQuestItemsCount(GREEN)) >= 8)
				{
					htmltext = "32557-05.htm";
					st.giveItems(13728, 1);
					st.addExpAndSp(25160, 2525);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(false);
				}
				else
				{
					htmltext = "32557-04.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("32557-03.htm"))
		{
			st.set("cond", "1");
			st.giveItems(SCROLL, 8);
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		final QuestState st = caster.getQuestState(qn);
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		if (Util.contains(targets, npc) && (st.getInt("cond") == 1) && (skill.getId() == 2630))
		{
			st.playSound("ItemSound.quest_itemget");
			final int npcId = npc.getNpcId();
			// Red Star Stones
			if ((npcId >= 18684) && (npcId <= 18686))
			{
				st.giveItems(RED, 1);
			}
			// Blue Star Stones
			else if ((npcId >= 18687) && (npcId <= 18689))
			{
				st.giveItems(BLUE, 1);
			}
			// Green Star Stones
			else if ((npcId >= 18690) && (npcId <= 18692))
			{
				st.giveItems(GREEN, 1);
			}
			npc.doDie(caster);
		}
		return super.onSkillSee(npc, caster, skill, targets, isPet);
	}
	
	public Q10274_CollectingInTheAir(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LEKON);
		addTalkId(LEKON);
		
		addSkillSeeId(MOBS);
		
		questItemIds = new int[]
		{
			SCROLL, RED, BLUE, GREEN
		};
	}
	
	public static void main(String[] args)
	{
		new Q10274_CollectingInTheAir(10274, qn, "Collecting in the Air");
	}
}
