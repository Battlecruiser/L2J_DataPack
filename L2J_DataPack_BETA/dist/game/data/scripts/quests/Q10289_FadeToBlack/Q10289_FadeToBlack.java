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
package quests.Q10289_FadeToBlack;

import java.util.ArrayList;
import java.util.List;

import quests.Q10288_SecretMission.Q10288_SecretMission;

import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Fade to Black (10289)
 * @author Plim
 */
public class Q10289_FadeToBlack extends Quest
{
	// NPC
	private static final int GREYMORE = 32757;
	// Items
	private static final int MARK_OF_SPLENDOR = 15527;
	private static final int MARK_OF_DARKNESS = 15528;
	// Monster
	private static final int ANAYS = 25701;
	
	public Q10289_FadeToBlack(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(GREYMORE);
		addTalkId(GREYMORE);
		addKillId(ANAYS);
		registerQuestItems(MARK_OF_SPLENDOR, MARK_OF_DARKNESS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		switch (event)
		{
			case "32757-03.htm":
				st.startQuest();
				break;
			case "32757-06.html":
				if (st.isCond(2) && st.hasQuestItems(MARK_OF_DARKNESS))
				{
					htmltext = "32757-07.html";
				}
				else if (st.isCond(3) && st.hasQuestItems(MARK_OF_SPLENDOR))
				{
					htmltext = "32757-08.html";
				}
				break;
			default:
				if (st.isCond(3) && Util.isDigit(event) && st.hasQuestItems(MARK_OF_SPLENDOR))
				{
					htmltext = "32757-09.html";
					// see 32757-08.html for recipe list (all moirai armor 60%)
					switch (Integer.parseInt(event))
					{
						case 11:
							st.rewardItems(15775, 1);
							st.giveAdena(420920, true);
							break;
						case 12:
							st.rewardItems(15776, 1);
							st.giveAdena(420920, true);
							break;
						case 13:
							st.rewardItems(15777, 1);
							st.giveAdena(420920, true);
							break;
						case 14:
							st.rewardItems(15778, 1);
							break;
						case 15:
							st.rewardItems(15779, 1);
							st.giveAdena(168360, true);
							break;
						case 16:
							st.rewardItems(15780, 1);
							st.giveAdena(168360, true);
							break;
						case 17:
							st.rewardItems(15781, 1);
							st.giveAdena(252540, true);
							break;
						case 18:
							st.rewardItems(15782, 1);
							st.giveAdena(357780, true);
							break;
						case 19:
							st.rewardItems(15783, 1);
							st.giveAdena(357780, true);
							break;
						case 20:
							st.rewardItems(15784, 1);
							st.giveAdena(505100, true);
							break;
						case 21:
							st.rewardItems(15785, 1);
							st.giveAdena(505100, true);
							break;
						case 22:
							st.rewardItems(15786, 1);
							st.giveAdena(505100, true);
							break;
						case 23:
							st.rewardItems(15787, 1);
							st.giveAdena(505100, true);
							break;
						case 24:
							st.rewardItems(15787, 1);
							st.giveAdena(505100, true);
							break;
						case 25:
							st.rewardItems(15789, 1);
							st.giveAdena(505100, true);
							break;
						case 26:
							st.rewardItems(15790, 1);
							st.giveAdena(496680, true);
							break;
						case 27:
							st.rewardItems(15791, 1);
							st.giveAdena(496680, true);
							break;
						case 28:
							st.rewardItems(15792, 1);
							st.giveAdena(563860, true);
							break;
						case 29:
							st.rewardItems(15793, 1);
							st.giveAdena(509040, true);
							break;
						case 30:
							st.rewardItems(15794, 1);
							st.giveAdena(454240, true);
							break;
						default:
							return null;
					}
					
					int marksOfDarkness = (int) st.getQuestItemsCount(MARK_OF_DARKNESS);
					if (marksOfDarkness > 0)
					{
						st.addExpAndSp(55983 * marksOfDarkness, 136500 * marksOfDarkness);
					}
					st.exitQuest(false, true);
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc anays, L2PcInstance killer, boolean isSummon)
	{
		List<L2PcInstance> killers = new ArrayList<>();
		// first, populate the list of players liable for a reward
		QuestState st = killer.getQuestState(getName());
		
		if ((st != null) && st.isStarted() && (st.getCond() < 3))
		{
			killers.add(killer);
		}
		
		L2Party party = killer.getParty();
		
		if (party != null)
		{
			for (L2PcInstance member : party.getMembers())
			{
				st = member.getQuestState(getName());
				
				if ((st != null) && st.isStarted() && (st.getCond() < 3))
				{
					killers.add(member);
				}
			}
		}
		
		// if at least one killer is found...
		if (!killers.isEmpty())
		{
			// .. then first, we roll for a random one
			L2PcInstance randomKiller = killers.get(getRandom(killers.size()));
			
			if (Util.checkIfInRange(1500, anays, randomKiller, false))
			{
				st = randomKiller.getQuestState(getName());
				
				// technically, this should never be false (a player can't have this item at cond < 3), but l2off checks it
				if (!st.hasQuestItems(MARK_OF_SPLENDOR))
				{
					if (party == null) // if no party, the winner gets it all
					{
						st.giveItems(MARK_OF_SPLENDOR, 1);
						st.setCond(3, true);
					}
					else
					// otherwise, reward all party members
					{
						int idx = 0;
						int rnd = getRandom(party.getMemberCount());
						
						for (L2PcInstance member : party.getMembers())
						{
							st = member.getQuestState(getName());
							
							if (idx == rnd) // only one lucky player will get the good item
							{
								st.giveItems(MARK_OF_SPLENDOR, 1);
								st.setCond(3, true);
							}
							else
							// the rest will get the bad one and can get multiple ones (the reward increases so not entirely bad)
							{
								st.giveItems(MARK_OF_DARKNESS, 1);
								st.setCond(2, true);
							}
							idx++;
						}
					}
				}
			}
		}
		return super.onKill(anays, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				st = player.getQuestState(Q10288_SecretMission.class.getSimpleName());
				htmltext = ((player.getLevel() < 82) || (st == null) || !st.isCompleted()) ? "32757-00.htm" : "32757-01.htm";
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = "32757-04.html";
						break;
					case 2:
					case 3:
						htmltext = "32757-05.html";
						break;
				}
				break;
			case State.COMPLETED:
				htmltext = "32757-10.html";
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q10289_FadeToBlack(10289, Q10289_FadeToBlack.class.getSimpleName(), "Fade to Black");
	}
}
