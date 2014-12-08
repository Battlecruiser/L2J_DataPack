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
package quests.Q00114_ResurrectionOfAnOldManager;

import quests.Q00121_PavelTheGiant.Q00121_PavelTheGiant;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Resurrection of an Old Manager (114)<br>
 * Original Jython script by Kerberos
 * @author malyelfik
 */
public class Q00114_ResurrectionOfAnOldManager extends Quest
{
	// NPCs
	private static final int NEWYEAR = 31961;
	private static final int YUMI = 32041;
	private static final int STONES = 32046;
	private static final int WENDY = 32047;
	private static final int BOX = 32050;
	// Items
	private static final int STARSTONE = 8287;
	private static final int LETTER = 8288;
	private static final int STARSTONE2 = 8289;
	private static final int DETCTOR = 8090;
	private static final int DETCTOR2 = 8091;
	// Monster
	private static final int GUARDIAN = 27318;
	
	private static L2Attackable golem = null;
	
	public Q00114_ResurrectionOfAnOldManager()
	{
		super(114, Q00114_ResurrectionOfAnOldManager.class.getSimpleName(), "Resurrection of an Old Manager");
		addStartNpc(YUMI);
		addTalkId(YUMI, WENDY, BOX, STONES, NEWYEAR);
		addKillId(GUARDIAN);
		addSeeCreatureId(STONES);
		registerQuestItems(STARSTONE, STARSTONE2, DETCTOR, DETCTOR2, LETTER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
		// Yumi
			case "32041-04.htm":
				st.startQuest();
				break;
			case "32041-08.html":
				st.set("talk", "1");
				break;
			case "32041-09.html":
				st.setCond(2, true);
				st.unset("talk");
				break;
			case "32041-12.html":
				switch (st.getCond())
				{
					case 3:
						htmltext = "32041-12.html";
						break;
					case 4:
						htmltext = "32041-13.html";
						break;
					case 5:
						htmltext = "32041-14.html";
						break;
				}
				break;
			case "32041-15.html":
				st.set("talk", "1");
				break;
			case "32041-23.html":
				st.set("talk", "2");
				break;
			case "32041-26.html":
				st.setCond(6, true);
				st.unset("talk");
				break;
			case "32041-31.html":
				st.giveItems(DETCTOR, 1);
				st.setCond(17, true);
				break;
			case "32041-34.html":
				st.set("talk", "1");
				st.takeItems(DETCTOR2, 1);
				break;
			case "32041-38.html":
				if (st.getInt("choice") == 2)
				{
					htmltext = "32041-37.html";
				}
				break;
			case "32041-39.html":
				st.unset("talk");
				st.setCond(20, true);
				break;
			case "32041-40.html":
				st.setCond(21, true);
				st.unset("talk");
				st.giveItems(LETTER, 1);
				break;
			// Suspicious-Looking Pile of Stones
			case "32046-03.html":
				st.setCond(19, true);
				break;
			case "32046-07.html":
				st.addExpAndSp(1846611, 144270);
				st.exitQuest(false, true);
				break;
			// Wendy
			case "32047-02.html":
				if (st.getInt("talk") == 0)
				{
					st.set("talk", "1");
				}
				break;
			case "32047-03.html":
				if (st.getInt("talk1") == 0)
				{
					st.set("talk1", "1");
				}
				break;
			case "32047-05.html":
				if ((st.getInt("talk") == 0) || (st.getInt("talk1") == 0))
				{
					htmltext = "32047-04.html";
				}
				break;
			case "32047-06.html":
				st.set("choice", "1");
				st.setCond(3, true);
				st.unset("talk1");
				st.unset("talk");
				break;
			case "32047-07.html":
				st.set("choice", "2");
				st.setCond(4, true);
				st.unset("talk1");
				st.unset("talk");
				break;
			case "32047-09.html":
				st.set("choice", "3");
				st.setCond(5, true);
				st.unset("talk1");
				st.unset("talk");
				break;
			case "32047-14ab.html":
				st.set("choice", "3");
				st.setCond(7, true);
				break;
			case "32047-14b.html":
				st.setCond(10, true);
				break;
			case "32047-15b.html":
				if ((golem == null) || ((golem != null) && golem.isDead()))
				{
					golem = (L2Attackable) addSpawn(GUARDIAN, 96977, -110625, -3280, 0, false, 0);
					golem.broadcastPacket(new NpcSay(golem.getObjectId(), Say2.NPC_ALL, golem.getId(), NpcStringId.YOU_S1_YOU_ATTACKED_WENDY_PREPARE_TO_DIE).addStringParameter(player.getName()));
					golem.setRunning();
					golem.addDamageHate(player, 0, 999);
					golem.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
					st.set("spawned", "1");
					startQuestTimer("golem_despawn", 300000, null, player);
				}
				else if (st.getInt("spawned") == 1)
				{
					htmltext = "32047-17b.html";
				}
				else
				{
					htmltext = "32047-16b.html";
				}
				break;
			case "32047-20a.html":
				st.setCond(8, true);
				break;
			case "32047-20b.html":
				st.setCond(12, true);
				break;
			case "32047-20c.html":
				st.setCond(13, true);
				break;
			case "32047-21a.html":
				st.setCond(9, true);
				break;
			case "32047-23a.html":
				st.setCond(23, true);
				break;
			case "32047-23c.html":
				st.takeItems(STARSTONE, 1);
				st.setCond(15, true);
				break;
			case "32047-29c.html":
				if (player.getAdena() >= 3000)
				{
					st.giveItems(STARSTONE2, 1);
					st.takeItems(Inventory.ADENA_ID, 3000);
					st.unset("talk");
					st.setCond(26, true);
				}
				else
				{
					htmltext = "32047-29ca.html";
				}
				break;
			case "32047-30c.html":
				st.set("talk", "1");
				break;
			// Box
			case "32050-01r.html":
				st.set("talk", "1");
				break;
			case "32050-03.html":
				st.giveItems(STARSTONE, 1);
				st.setCond(14, true);
				st.unset("talk");
				break;
			case "32050-05.html":
				st.setCond(24, true);
				st.giveItems(STARSTONE2, 1);
				break;
			// Newyear
			case "31961-02.html":
				st.takeItems(LETTER, 1);
				st.giveItems(STARSTONE2, 1);
				st.setCond(22, true);
				break;
			// Quest timer
			case "golem_despawn":
				st.unset("spawned");
				golem.broadcastPacket(new NpcSay(golem.getObjectId(), Say2.NPC_ALL, golem.getId(), NpcStringId.S1_YOUR_ENEMY_WAS_DRIVEN_OUT_I_WILL_NOW_WITHDRAW_AND_AWAIT_YOUR_NEXT_COMMAND).addStringParameter(player.getName()));
				golem.deleteMe();
				golem = null;
				htmltext = null;
				break;
			// HTMLs
			case "32041-05.html":
			case "32041-06.html":
			case "32041-07.html":
			case "32041-17.html":
			case "32041-18.html":
			case "32041-19.html":
			case "32041-20.html":
			case "32041-21.html":
			case "32041-22.html":
			case "32041-25.html":
			case "32041-29.html":
			case "32041-30.html":
			case "32041-35.html":
			case "32041-36.html":
			case "32046-05.html":
			case "32046-06.html":
			case "32047-06a.html":
			case "32047-12a.html":
			case "32047-12b.html":
			case "32047-12c.html":
			case "32047-13a.html":
			case "32047-14a.html":
			case "32047-13b.html":
			case "32047-13c.html":
			case "32047-14c.html":
			case "32047-15c.html":
			case "32047-17c.html":
			case "32047-13ab.html":
			case "32047-15a.html":
			case "32047-16a.html":
			case "32047-16c.html":
			case "32047-18a.html":
			case "32047-19a.html":
			case "32047-18ab.html":
			case "32047-19ab.html":
			case "32047-18c.html":
			case "32047-17a.html":
			case "32047-19c.html":
			case "32047-21b.html":
			case "32047-27c.html":
			case "32047-28c.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getQuestState(player, false);
		
		if ((st != null) && st.isCond(10) && (st.getInt("spawned") == 1))
		{
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.THIS_ENEMY_IS_FAR_TOO_POWERFUL_FOR_ME_TO_FIGHT_I_MUST_WITHDRAW));
			st.setCond(11, true);
			st.unset("spawned");
			cancelQuestTimers("golem_despawn");
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (creature.isPlayer())
		{
			final QuestState st = creature.getActingPlayer().getQuestState(getName());
			if ((st != null) && st.isCond(17))
			{
				st.takeItems(DETCTOR, 1);
				st.giveItems(DETCTOR2, 1);
				st.setCond(18, true);
				showOnScreenMsg(creature.getActingPlayer(), NpcStringId.THE_RADIO_SIGNAL_DETECTOR_IS_RESPONDING_A_SUSPICIOUS_PILE_OF_STONES_CATCHES_YOUR_EYE, 2, 4500);
			}
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		int talk = st.getInt("talk");
		
		switch (npc.getId())
		{
			case YUMI:
				switch (st.getState())
				{
					case State.CREATED:
						final QuestState prev = player.getQuestState(Q00121_PavelTheGiant.class.getSimpleName());
						if ((prev != null) && prev.isCompleted())
						{
							htmltext = (player.getLevel() >= 70) ? "32041-02.htm" : "32041-03.htm";
						}
						else
						{
							htmltext = "32041-01.htm";
						}
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = (talk == 1) ? "32041-08.html" : "32041-04.htm";
								break;
							case 2:
								htmltext = "32041-10.html";
								break;
							case 3:
							case 4:
							case 5:
								switch (talk)
								{
									case 0:
										htmltext = "32041-11.html";
										break;
									case 1:
										htmltext = "32041-16.html";
										break;
									case 2:
										htmltext = "32041-24.html";
										break;
								}
								break;
							case 6:
							case 7:
							case 8:
							case 10:
							case 11:
							case 13:
							case 14:
							case 15:
								htmltext = "32041-27.html";
								break;
							case 9:
							case 12:
							case 16:
								htmltext = "32041-28.html";
								break;
							case 17:
							case 18:
								htmltext = "32041-32.html";
								break;
							case 19:
								htmltext = (talk == 1) ? "32041-34z.html" : "32041-33.html";
								break;
							case 20:
								htmltext = "32041-39z.html";
								break;
							case 21:
								htmltext = "32041-40z.html";
								break;
							case 22:
							case 25:
							case 26:
								st.setCond(27, true);
								htmltext = "32041-41.html";
								break;
							case 27:
								htmltext = "32041-42.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case WENDY:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 2:
							htmltext = ((talk == 1) && (st.getInt("talk1") == 1)) ? "32047-05.html" : "32047-01.html";
							break;
						case 3:
							htmltext = "32047-06b.html";
							break;
						case 4:
							htmltext = "32047-08.html";
							break;
						case 5:
							htmltext = "32047-10.html";
							break;
						case 6:
							switch (st.getInt("choice"))
							{
								case 1:
									htmltext = "32047-11a.html";
									break;
								case 2:
									htmltext = "32047-11b.html";
									break;
								case 3:
									htmltext = "32047-11c.html";
									break;
							}
							break;
						case 7:
							htmltext = "32047-11c.html";
							break;
						case 8:
							htmltext = "32047-17a.html";
							break;
						case 9:
						case 12:
						case 16:
							htmltext = "32047-25c.html";
							break;
						case 10:
							htmltext = "32047-18b.html";
							break;
						case 11:
							htmltext = "32047-19b.html";
							break;
						case 13:
							htmltext = "32047-21c.html";
							break;
						case 14:
							htmltext = "32047-22c.html";
							break;
						case 15:
							st.setCond(16, true);
							htmltext = "32047-24c.html";
							break;
						case 20:
							if (st.getInt("choice") == 1)
							{
								htmltext = "32047-22a.html";
							}
							else
							{
								htmltext = (talk == 1) ? "32047-31c.html" : "32047-26c.html";
							}
							break;
						case 23:
							htmltext = "32047-23z.html";
							break;
						case 24:
							st.setCond(25, true);
							htmltext = "32047-24a.html";
							break;
						case 25:
							htmltext = "32047-24a.html";
							break;
						case 26:
							htmltext = "32047-32c.html";
							break;
					}
				}
				break;
			case NEWYEAR:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 21:
							htmltext = "31961-01.html";
							break;
						case 22:
							htmltext = "31961-03.html";
							break;
					}
				}
				break;
			case BOX:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 13:
							htmltext = (talk == 1) ? "32050-02.html" : "32050-01.html";
							break;
						case 14:
							htmltext = "32050-04.html";
							break;
						case 23:
							htmltext = "32050-04b.html";
							break;
						case 24:
							htmltext = "32050-05z.html";
							break;
					}
				}
				break;
			case STONES:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 18:
							htmltext = "32046-02.html";
							break;
						case 19:
							htmltext = "32046-03.html";
							break;
						case 27:
							htmltext = "32046-04.html";
							break;
					}
				}
				break;
		}
		
		return htmltext;
	}
}