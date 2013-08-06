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
package quests.Q00193_SevenSignsDyingMessage;

import quests.Q00192_SevenSignsSeriesOfDoubt.Q00192_SevenSignsSeriesOfDoubt;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Seven Signs, Dying Message (193)
 * @author Adry_85
 */
public class Q00193_SevenSignsDyingMessage extends Quest
{
	// NPCs
	private static final int SHILENS_EVIL_THOUGHTS = 27343;
	private static final int HOLLINT = 30191;
	private static final int SIR_GUSTAV_ATHEBALDT = 30760;
	private static final int CAIN = 32569;
	private static final int ERIC = 32570;
	// Items
	private static final int JACOBS_NECKLACE = 13814;
	private static final int DEADMANS_HERB = 13816;
	private static final int SCULPTURE_OF_DOUBT = 14353;
	// Misc
	private static final int MIN_LEVEL = 79;
	private boolean isBusy = false;
	// Skill
	private static SkillHolder NPC_HEAL = new SkillHolder(4065, 8);
	
	public Q00193_SevenSignsDyingMessage(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HOLLINT);
		addTalkId(HOLLINT, CAIN, ERIC, SIR_GUSTAV_ATHEBALDT);
		addKillId(SHILENS_EVIL_THOUGHTS);
		registerQuestItems(JACOBS_NECKLACE, DEADMANS_HERB, SCULPTURE_OF_DOUBT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((npc.getId() == SHILENS_EVIL_THOUGHTS) && "despawn".equals(event))
		{
			if (!npc.isDead())
			{
				isBusy = false;
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.NEXT_TIME_YOU_WILL_NOT_ESCAPE));
				npc.deleteMe();
			}
			return super.onAdvEvent(event, npc, player);
		}
		
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "30191-02.html":
			{
				st.giveItems(JACOBS_NECKLACE, 1);
				st.startQuest();
				htmltext = event;
				break;
			}
			case "32569-02.html":
			case "32569-03.html":
			case "32569-04.html":
			{
				if (st.isCond(1) && st.hasQuestItems(JACOBS_NECKLACE))
				{
					htmltext = event;
				}
				break;
			}
			case "32569-05.html":
			{
				if (st.isCond(1) && st.hasQuestItems(JACOBS_NECKLACE))
				{
					st.takeItems(JACOBS_NECKLACE, -1);
					st.setCond(2, true);
					htmltext = event;
				}
				break;
			}
			case "showmovie":
			{
				if (st.isCond(3) && st.hasQuestItems(DEADMANS_HERB))
				{
					st.takeItems(DEADMANS_HERB, -1);
					st.setCond(4, true);
					player.showQuestMovie(9);
					return "";
				}
				break;
			}
			case "32569-10.html":
			case "32569-11.html":
			{
				if (st.isCond(5) && st.hasQuestItems(SCULPTURE_OF_DOUBT))
				{
					htmltext = event;
				}
				break;
			}
			case "32569-12.html":
			{
				if (st.isCond(5) && st.hasQuestItems(SCULPTURE_OF_DOUBT))
				{
					st.takeItems(SCULPTURE_OF_DOUBT, -1);
					st.setCond(6, true);
					htmltext = event;
				}
				break;
			}
			case "32570-02.html":
			{
				if (st.isCond(2))
				{
					st.giveItems(DEADMANS_HERB, 1);
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "fight":
			{
				htmltext = "32569-14.html";
				if (st.isCond(4))
				{
					isBusy = true;
					NpcSay ns = new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.S1_THAT_STRANGER_MUST_BE_DEFEATED_HERE_IS_THE_ULTIMATE_HELP);
					ns.addStringParameter(player.getName());
					npc.broadcastPacket(ns);
					startQuestTimer("heal", 30000 - getRandom(20000), npc, player);
					L2MonsterInstance monster = (L2MonsterInstance) addSpawn(SHILENS_EVIL_THOUGHTS, 82425, 47232, -3216, 0, false, 0, false);
					monster.broadcastPacket(new NpcSay(monster.getObjectId(), Say2.NPC_ALL, monster.getId(), NpcStringId.YOU_ARE_NOT_THE_OWNER_OF_THAT_ITEM));
					monster.setRunning();
					monster.addDamageHate(player, 0, 999);
					monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
					startQuestTimer("despawn", 300000, monster, null);
				}
				break;
			}
			case "heal":
			{
				if (!npc.isInsideRadius(player, 600, true, false))
				{
					NpcSay ns = new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.LOOK_HERE_S1_DONT_FALL_TOO_FAR_BEHIND);
					ns.addStringParameter(player.getName());
					npc.broadcastPacket(ns);
				}
				else if (!player.isDead())
				{
					npc.setTarget(player);
					npc.doCast(NPC_HEAL.getSkill());
				}
				startQuestTimer("heal", 30000 - getRandom(20000), npc, player);
				break;
			}
			case "30760-02.html":
			{
				if (st.isCond(6))
				{
					st.addExpAndSp(52518015, 5817677);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, 4);
		if (partyMember == null)
		{
			return null;
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		
		if (npc.isInsideRadius(player, 1500, true, false))
		{
			st.giveItems(SCULPTURE_OF_DOUBT, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_FINISH);
			st.setCond(5);
		}
		
		isBusy = false;
		cancelQuestTimers("despawn");
		cancelQuestTimers("heal");
		NpcSay ns = new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.S1_YOU_MAY_HAVE_WON_THIS_TIME_BUT_NEXT_TIME_I_WILL_SURELY_CAPTURE_YOU);
		ns.addStringParameter(player.getName());
		npc.broadcastPacket(ns);
		
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
			case State.CREATED:
			{
				if (npc.getId() == HOLLINT)
				{
					st = player.getQuestState(Q00192_SevenSignsSeriesOfDoubt.class.getSimpleName());
					htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && (st.isCompleted())) ? "30191-01.htm" : "30191-03.html";
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case HOLLINT:
					{
						if (st.isCond(1) && st.hasQuestItems(JACOBS_NECKLACE))
						{
							htmltext = "30191-04.html";
						}
						break;
					}
					case CAIN:
					{
						switch (st.getCond())
						{
							case 1:
							{
								if (st.hasQuestItems(JACOBS_NECKLACE))
								{
									htmltext = "32569-01.html";
								}
								break;
							}
							case 2:
							{
								htmltext = "32569-06.html";
								break;
							}
							case 3:
							{
								if (st.hasQuestItems(DEADMANS_HERB))
								{
									htmltext = "32569-07.html";
								}
								break;
							}
							case 4:
							{
								if (isBusy)
								{
									htmltext = "32569-13.html";
								}
								else
								{
									htmltext = "32569-08.html";
								}
								break;
							}
							case 5:
							{
								if (st.hasQuestItems(SCULPTURE_OF_DOUBT))
								{
									htmltext = "32569-09.html";
								}
								break;
							}
						}
						break;
					}
					case ERIC:
					{
						switch (st.getCond())
						{
							case 2:
							{
								htmltext = "32570-01.html";
								break;
							}
							case 3:
							{
								htmltext = "32570-03.html";
								break;
							}
						}
						break;
					}
					case SIR_GUSTAV_ATHEBALDT:
					{
						if (st.isCond(6))
						{
							htmltext = "30760-01.html";
						}
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new Q00193_SevenSignsDyingMessage(193, Q00193_SevenSignsDyingMessage.class.getSimpleName(), "Seven Signs, Dying Message");
	}
}
