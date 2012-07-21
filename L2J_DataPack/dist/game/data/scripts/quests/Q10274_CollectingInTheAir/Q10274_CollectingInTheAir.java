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

/**
 * Collecting in the Air (10274)
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
		18684, // Red Star Stone
		18685, // Red Star Stone
		18686, // Red Star Stone
		18687, // Blue Star Stone
		18688, // Blue Star Stone
		18689, // Blue Star Stone
		18690, // Green Star Stone
		18691, // Green Star Stone
		18692, // Green Star Stone
	};
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = "32557-0a.html";
				break;
			case State.CREATED:
				st = player.getQuestState("10273_GoodDayToFly");
				if (st == null)
				{
					htmltext = "32557-00.html";
				}
				else
				{
					htmltext = ((player.getLevel() >= 75) && st.isCompleted()) ? "32557-01.htm" : "32557-00.html";
				}
				break;
			case State.STARTED:
				if ((st.getQuestItemsCount(RED) + st.getQuestItemsCount(BLUE) + st.getQuestItemsCount(GREEN)) >= 8)
				{
					htmltext = "32557-05.html";
					st.giveItems(13728, 1);
					st.addExpAndSp(25160, 2525);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "32557-04.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equals("32557-03.html"))
		{
			st.startQuest();
			st.giveItems(SCROLL, 8);
		}
		return event;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		final QuestState st = caster.getQuestState(qn);
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		if (st.isCond(1) && (skill.getId() == 2630))
		{
			final int npcId = npc.getNpcId();
			if ((npcId >= 18684) && (npcId <= 18686))
			{
				st.giveItems(RED, 1);
			}
			else if ((npcId >= 18687) && (npcId <= 18689))
			{
				st.giveItems(BLUE, 1);
			}
			else if ((npcId >= 18690) && (npcId <= 18692))
			{
				st.giveItems(GREEN, 1);
			}
			st.playSound("ItemSound.quest_itemget");
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
			SCROLL,
			RED,
			BLUE,
			GREEN
		};
	}
	
	public static void main(String[] args)
	{
		new Q10274_CollectingInTheAir(10274, qn, "Collecting in the Air");
	}
}
