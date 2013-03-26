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
package quests.Q00246_PossessorOfAPreciousSoul3;

import quests.Q00242_PossessorOfAPreciousSoul2.Q00242_PossessorOfAPreciousSoul2;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Possessor Of A PreciousSoul part 3 (246)<br>
 * Original Jython script by disKret.
 * @author nonom
 */
public class Q00246_PossessorOfAPreciousSoul3 extends Quest
{
	// NPCs
	private static final int LADD = 30721;
	private static final int CARADINE = 31740;
	private static final int OSSIAN = 31741;
	private static final int PILGRIM_OF_SPLENDOR = 21541;
	private static final int JUDGE_OF_SPLENDOR = 21544;
	private static final int BARAKIEL = 25325;
	private static final int[] MOBS =
	{
		21535, // Signet of Splendor
		21536, // Crown of Splendor
		21537, // Fang of Splendor
		21538, // Fang of Splendor
		21539, // Wailing of Splendor
		21540, // Wailing of Splendor
	};
	// Items
	private static final int CARADINE_LETTER = 7678;
	private static final int CARADINE_LETTER_LAST = 7679;
	private static final int WATERBINDER = 7591;
	private static final int EVERGREEN = 7592;
	private static final int RAIN_SONG = 7593;
	private static final int RELIC_BOX = 7594;
	private static final int FRAGMENTS = 21725;
	// Rewards
	private static final int CHANCE_FOR_DROP = 30;
	private static final int CHANCE_FOR_DROP_FRAGMENTS = 60;
	
	public Q00246_PossessorOfAPreciousSoul3(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(CARADINE);
		addTalkId(LADD, CARADINE, OSSIAN);
		addKillId(PILGRIM_OF_SPLENDOR, JUDGE_OF_SPLENDOR, BARAKIEL);
		addKillId(MOBS);
		registerQuestItems(WATERBINDER, EVERGREEN, FRAGMENTS, RAIN_SONG, RELIC_BOX);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		if (!player.isSubClassActive())
		{
			return "no_sub.html";
		}
		
		switch (event)
		{
			case "31740-4.html":
				if (st.isCreated())
				{
					st.takeItems(CARADINE_LETTER, -1);
					st.startQuest();
				}
				break;
			case "31741-2.html":
				if (st.isStarted() && st.isCond(1))
				{
					st.set("awaitsWaterbinder", "1");
					st.set("awaitsEvergreen", "1");
					st.setCond(2, true);
				}
				break;
			case "31741-5.html":
				if (st.isCond(3) && st.hasQuestItems(WATERBINDER) && st.hasQuestItems(EVERGREEN))
				{
					st.takeItems(WATERBINDER, 1);
					st.takeItems(EVERGREEN, 1);
					st.setCond(4, true);
				}
				break;
			case "31741-9.html":
				if (st.isCond(5) && (st.hasQuestItems(RAIN_SONG) || (st.getQuestItemsCount(FRAGMENTS) >= 100)))
				{
					st.takeItems(RAIN_SONG, -1);
					st.takeItems(FRAGMENTS, -1);
					st.giveItems(RELIC_BOX, 1);
					st.setCond(6, true);
				}
				else
				{
					return "31741-8.html";
				}
				break;
			case "30721-2.html":
				if (st.isCond(6) && st.hasQuestItems(RELIC_BOX))
				{
					st.takeItems(RELIC_BOX, -1);
					st.giveItems(CARADINE_LETTER_LAST, 1);
					st.addExpAndSp(719843, 0);
					st.exitQuest(false, true);
				}
				break;
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance partyMember;
		final QuestState st;
		switch (npc.getNpcId())
		{
			case PILGRIM_OF_SPLENDOR:
				partyMember = getRandomPartyMember(player, "awaitsWaterbinder", "1");
				if (partyMember != null)
				{
					st = partyMember.getQuestState(getName());
					final int chance = getRandom(100);
					if (st.isCond(2) && !st.hasQuestItems(WATERBINDER))
					{
						if (chance < CHANCE_FOR_DROP)
						{
							st.giveItems(WATERBINDER, 1);
							st.unset("awaitsWaterbinder");
							if (st.hasQuestItems(EVERGREEN))
							{
								st.setCond(3, true);
								
							}
							else
							{
								st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
							}
						}
					}
				}
				break;
			case JUDGE_OF_SPLENDOR:
				partyMember = getRandomPartyMember(player, "awaitsEvergreen", "1");
				if (partyMember != null)
				{
					st = partyMember.getQuestState(getName());
					final long chance = getRandom(100);
					if (st.isCond(2) && !st.hasQuestItems(EVERGREEN))
					{
						if (chance < CHANCE_FOR_DROP)
						{
							st.giveItems(EVERGREEN, 1);
							st.unset("awaitsEvergreen");
							if (st.hasQuestItems(WATERBINDER))
							{
								st.setCond(3, true);
							}
							else
							{
								st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
							}
						}
					}
				}
				break;
			case BARAKIEL:
				QuestState pst;
				if ((player.getParty() != null) && !player.getParty().getMembers().isEmpty())
				{
					for (L2PcInstance member : player.getParty().getMembers())
					{
						pst = member.getQuestState(getName());
						if (pst != null)
						{
							if (pst.isCond(4) && !pst.hasQuestItems(RAIN_SONG))
							{
								pst.giveItems(RAIN_SONG, 1);
								pst.setCond(5, true);
							}
						}
					}
				}
				else
				{
					pst = player.getQuestState(getName());
					if (pst != null)
					{
						if (pst.isCond(4) && !pst.hasQuestItems(RAIN_SONG))
						{
							pst.giveItems(RAIN_SONG, 1);
							pst.setCond(5, true);
						}
					}
				}
				break;
			default:
				st = player.getQuestState(getName());
				if ((st == null))
				{
					return super.onKill(npc, player, isSummon);
				}
				
				if (Util.contains(MOBS, npc.getNpcId()) && (st.getQuestItemsCount(FRAGMENTS) < 100) && (st.isCond(4)))
				{
					if (getRandom(100) < CHANCE_FOR_DROP_FRAGMENTS)
					{
						st.giveItems(FRAGMENTS, 1);
						if (st.getQuestItemsCount(FRAGMENTS) < 100)
						{
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						}
						else
						{
							st.setCond(5, true);
						}
					}
				}
				break;
		}
		return super.onKill(npc, player, isSummon);
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
		if (st.isStarted() && !player.isSubClassActive())
		{
			return "no_sub.html";
		}
		
		switch (npc.getNpcId())
		{
			case CARADINE:
				switch (st.getState())
				{
					case State.CREATED:
						final QuestState qs = player.getQuestState(Q00242_PossessorOfAPreciousSoul2.class.getSimpleName());
						htmltext = ((player.getLevel() >= 65) && (qs != null) && qs.isCompleted()) ? "31740-1.htm" : "31740-2.html";
						break;
					case State.STARTED:
						htmltext = "31740-5.html";
						break;
				}
				break;
			case OSSIAN:
				switch (st.getState())
				{
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "31741-1.html";
								break;
							case 2:
								htmltext = "31741-4.html";
								break;
							case 3:
								if (st.hasQuestItems(WATERBINDER) && st.hasQuestItems(EVERGREEN))
								{
									htmltext = "31741-3.html";
								}
								break;
							case 4:
								htmltext = "31741-8.html";
								break;
							case 5:
								if (st.hasQuestItems(RAIN_SONG) || (st.getQuestItemsCount(FRAGMENTS) >= 100))
								{
									htmltext = "31741-7.html";
								}
								else
								{
									htmltext = "31741-8.html";
								}
								break;
							case 6:
								if (st.getQuestItemsCount(RELIC_BOX) == 1)
								{
									htmltext = "31741-11.html";
								}
								break;
						}
				}
				break;
			case LADD:
				switch (st.getState())
				{
					case State.STARTED:
						if (st.isCond(6))
						{
							htmltext = "30721-1.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00246_PossessorOfAPreciousSoul3(246, Q00246_PossessorOfAPreciousSoul3.class.getSimpleName(), "Possessor Of A Precious Soul 3");
	}
}
