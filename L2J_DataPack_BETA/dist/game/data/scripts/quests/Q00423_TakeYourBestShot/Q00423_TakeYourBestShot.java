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
package quests.Q00423_TakeYourBestShot;

import quests.Q00249_PoisonedPlainsOfTheLizardmen.Q00249_PoisonedPlainsOfTheLizardmen;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Based on official server Franz.
 * @version 2010-06-26
 * @author Gnacik
 */
public class Q00423_TakeYourBestShot extends Quest
{
	// NPC
	private static final int _batracos = 32740;
	private static final int _johnny = 32744;
	// Item
	private static final int _seer_ugoros_pass = 15496;
	// Spawn chance x/1000
	private static final int _spawn_chance = 2;
	// Guard
	private static final int _tanta_guard = 18862;
	// Mobs
	private static final int[] _mobs =
	{
		22768,
		22769,
		22770,
		22771,
		22772,
		22773,
		22774
	};
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _johnny)
		{
			if (event.equalsIgnoreCase("32744-04.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("32744-quit.htm"))
			{
				st.exitQuest(true);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>";
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _johnny)
		{
			switch (st.getState())
			{
				case State.CREATED:
					QuestState _prev = player.getQuestState(Q00249_PoisonedPlainsOfTheLizardmen.class.getSimpleName());
					if ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 82))
					{
						if (st.hasQuestItems(_seer_ugoros_pass))
						{
							htmltext = "32744-07.htm";
						}
						else
						{
							htmltext = "32744-01.htm";
						}
					}
					else
					{
						htmltext = "32744-00.htm";
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "32744-05.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						htmltext = "32744-06.htm";
					}
					break;
			}
		}
		else if (npc.getNpcId() == _batracos)
		{
			if (st.getState() == State.CREATED)
			{
				if (st.hasQuestItems(_seer_ugoros_pass))
				{
					htmltext = "32740-05.htm";
				}
				else
				{
					htmltext = "32740-00.htm";
				}
			}
			else if ((st.getState() == State.STARTED) && (st.getInt("cond") == 1))
			{
				htmltext = "32740-02.htm";
			}
			else if ((st.getState() == State.STARTED) && (st.getInt("cond") == 2))
			{
				st.giveItems(_seer_ugoros_pass, 1);
				st.playSound("ItemSound.quest_finish");
				st.unset("cond");
				st.exitQuest(true);
				htmltext = "32740-04.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (npc.isInsideRadius(96782, 85918, 100, true))
		{
			return "32740-ugoros.htm";
		}
		return "32740.htm";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (Util.contains(_mobs, npc.getNpcId()) && (getRandom(1000) <= _spawn_chance))
		{
			L2Npc guard = addSpawn(_tanta_guard, npc, false);
			attackPlayer((L2Attackable) guard, player);
		}
		else if ((npc.getNpcId() == _tanta_guard) && (st.getInt("cond") == 1))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		return null;
	}
	
	private void attackPlayer(L2Attackable npc, L2PcInstance player)
	{
		npc.setIsRunning(true);
		npc.addDamageHate(player, 0, 999);
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
	}
	
	public Q00423_TakeYourBestShot(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_johnny);
		addTalkId(_johnny);
		addStartNpc(_batracos);
		addTalkId(_batracos);
		addFirstTalkId(_batracos);
		
		addKillId(_tanta_guard);
		for (int _mob : _mobs)
		{
			addKillId(_mob);
		}
	}
	
	public static void main(String[] args)
	{
		new Q00423_TakeYourBestShot(423, Q00423_TakeYourBestShot.class.getSimpleName(), "Take Your Best Shot!");
	}
}
