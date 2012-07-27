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
package quests.Q126_TheNameOfEvil2;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;

/**
 * The Name of Evil - 2 (126)
 * @author Adry_85
 */
public class Q126_TheNameOfEvil2 extends Quest
{
	private static final String qn = "126_TheNameOfEvil2";
	// NPC
	private static final int SHILENS_STONE_STATUE = 32109;
	private static final int MUSHIKA = 32114;
	private static final int ASAMAH = 32115;
	private static final int ULU_KAIMU = 32119;
	private static final int BALU_KAIMU = 32120;
	private static final int CHUTA_KAIMU = 32121;
	private static final int WARRIORS_GRAVE = 32122;
	
	// Quest Item
	private static final int GAZKH_FRAGMENT = 8782;
	private static final int BONE_POWDER = 8783;
	
	// Reward
	private static final int ENCHANT_WEAPON_A = 729;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "32115-1.html":
				st.startQuest();
				break;
			case "32115-1b.html":
				if (st.isCond(1))
				{
					st.setCond(2, true);
				}
				break;
			case "32119-3.html":
				if (st.isCond(2))
				{
					st.setCond(3, true);
				}
				break;
			case "32119-4.html":
				if (st.isCond(3))
				{
					st.setCond(4, true);
				}
				break;
			case "32119-4a.html":
			case "32119-5b.html":
				st.playSound("EtcSound.elcroki_song_1st");
				break;
			case "32119-5.html":
				if (st.isCond(4))
				{
					st.setCond(5, true);
				}
				break;
			case "32120-3.html":
				if (st.isCond(5))
				{
					st.setCond(6, true);
				}
				break;
			case "32120-4.html":
				if (st.isCond(6))
				{
					st.setCond(7, true);
				}
				break;
			case "32120-4a.html":
			case "32120-5b.html":
				st.playSound("EtcSound.elcroki_song_2nd");
				break;
			case "32120-5.html":
				if (st.isCond(7))
				{
					st.setCond(8, true);
				}
				break;
			case "32121-3.html":
				if (st.isCond(8))
				{
					st.setCond(9, true);
				}
				break;
			case "32121-4.html":
				if (st.isCond(9))
				{
					st.setCond(10, true);
				}
				break;
			case "32121-4a.html":
			case "32121-5b.html":
				st.playSound("EtcSound.elcroki_song_3rd");
				break;
			case "32121-5.html":
				if (st.isCond(10))
				{
					st.giveItems(GAZKH_FRAGMENT, 1);
					st.setCond(11, true);
				}
				break;
			case "32122-2a.html":
				npc.broadcastPacket(new MagicSkillUse(npc, player, 5089, 1, 1000, 0));
				break;
			case "32122-2d.html":
				st.takeItems(GAZKH_FRAGMENT, -1);
				break;
			case "32122-3.html":
				if (st.isCond(12))
				{
					st.setCond(13, true);
				}
				break;
			case "32122-4.html":
				if (st.isCond(13))
				{
					st.setCond(14, true);
				}
				break;
			case "DO_One":
				st.set("DO", "1");
				event = "32122-4d.html";
				break;
			case "MI_One":
				st.set("MI", "1");
				event = "32122-4f.html";
				break;
			case "FA_One":
				st.set("FA", "1");
				event = "32122-4h.html";
				break;
			case "SOL_One":
				st.set("SOL", "1");
				event = "32122-4j.html";
				break;
			case "FA2_One":
				st.set("FA2", "1");
				if (st.isCond(14) && (st.getInt("DO") > 0) && (st.getInt("MI") > 0) && (st.getInt("FA") > 0) && (st.getInt("SOL") > 0) && (st.getInt("FA2") > 0))
				{
					event = "32122-4n.html";
					st.setCond(15, true);
				}
				else
				{
					event = "32122-4m.html";
				}
				st.unset("DO");
				st.unset("MI");
				st.unset("FA");
				st.unset("SOL");
				st.unset("FA2");
				break;
			case "32122-4m.html":
				st.unset("DO");
				st.unset("MI");
				st.unset("FA");
				st.unset("SOL");
				st.unset("FA2");
				break;
			case "FA_Two":
				st.set("FA", "1");
				event = "32122-5a.html";
				break;
			case "SOL_Two":
				st.set("SOL", "1");
				event = "32122-5c.html";
				break;
			case "TI_Two":
				st.set("TI", "1");
				event = "32122-5e.html";
				break;
			case "SOL2_Two":
				st.set("SOL2", "1");
				event = "32122-5g.html";
				break;
			case "FA2_Two":
				st.set("FA2", "1");
				if (st.isCond(15) && (st.getInt("FA") > 0) && (st.getInt("SOL") > 0) && (st.getInt("TI") > 0) && (st.getInt("SOL2") > 0) && (st.getInt("FA2") > 0))
				{
					event = "32122-5j.html";
					st.setCond(16, true);
				}
				else
				{
					event = "32122-5i.html";
				}
				st.unset("FA");
				st.unset("SOL");
				st.unset("TI");
				st.unset("SOL2");
				st.unset("FA2");
				break;
			case "32122-5i.html":
				st.unset("FA");
				st.unset("SOL");
				st.unset("TI");
				st.unset("SOL2");
				st.unset("FA2");
				break;
			case "SOL_Three":
				st.set("SOL", "1");
				event = "32122-6a.html";
				break;
			case "FA_Three":
				st.set("FA", "1");
				event = "32122-6c.html";
				break;
			case "MI_Three":
				st.set("MI", "1");
				event = "32122-6e.html";
				break;
			case "FA2_Three":
				st.set("FA2", "1");
				event = "32122-6g.html";
				break;
			case "MI2_Three":
				st.set("MI2", "1");
				if (st.isCond(16) && (st.getInt("SOL") > 0) && (st.getInt("FA") > 0) && (st.getInt("MI") > 0) && (st.getInt("FA2") > 0) && (st.getInt("MI2") > 0))
				{
					event = "32122-6j.html";
					st.setCond(17, true);
				}
				else
				{
					event = "32122-6i.html";
				}
				st.unset("SOL");
				st.unset("FA");
				st.unset("MI");
				st.unset("FA2");
				st.unset("MI2");
				break;
			case "32122-6i.html":
				st.unset("SOL");
				st.unset("FA");
				st.unset("MI");
				st.unset("FA2");
				st.unset("MI2");
				break;
			case "32122-7.html":
				st.giveItems(BONE_POWDER, 1);
				st.playSound("EtcSound.elcroki_song_full");
				npc.broadcastPacket(new MagicSkillUse(npc, player, 5089, 1, 1000, 0));
				break;
			case "32122-8.html":
				if (st.isCond(17))
				{
					st.setCond(18, true);
				}
				break;
			case "32109-2.html":
				if (st.isCond(18))
				{
					st.setCond(19, true);
				}
				break;
			case "32109-3.html":
				if (st.isCond(19))
				{
					st.takeItems(BONE_POWDER, -1);
					st.setCond(20, true);
				}
				break;
			case "32115-4.html":
				if (st.isCond(20))
				{
					st.setCond(21, true);
				}
				break;
			case "32115-5.html":
				if (st.isCond(21))
				{
					st.setCond(22, true);
				}
				break;
			case "32114-2.html":
				if (st.isCond(22))
				{
					st.setCond(23, true);
				}
				break;
			case "32114-3.html":
				st.rewardItems(ENCHANT_WEAPON_A, 1);
				st.giveAdena(460483, true);
				st.addExpAndSp(1015973, 102802);
				st.exitQuest(false, true);
				break;
		}
		
		return event;
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
		
		switch (npc.getNpcId())
		{
			case ASAMAH:
				switch (st.getState())
				{
					case State.CREATED:
						if (player.getLevel() < 77)
						{
							htmltext = "32115-0.htm";
						}
						else
						{
							st = player.getQuestState("125_TheNameOfEvil1");
							htmltext = ((st != null) && st.isCompleted()) ? "32115-0a.htm" : "32115-0b.htm";
						}
						break;
					case State.STARTED:
						switch (st.getInt("cond"))
						{
							case 1:
								htmltext = "32115-1d.html";
								break;
							case 2:
								htmltext = "32115-1c.html";
								break;
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
							case 10:
							case 11:
							case 12:
							case 13:
							case 14:
							case 15:
							case 16:
							case 17:
							case 18:
							case 19:
								htmltext = "32115-2.html";
								break;
							case 20:
								htmltext = "32115-3.html";
								break;
							case 21:
								htmltext = "32115-4j.html";
								break;
							case 22:
								htmltext = "32115-5a.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case ULU_KAIMU:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
							htmltext = "32119-1.html";
							break;
						case 2:
							htmltext = "32119-2.html";
							npc.broadcastPacket(new MagicSkillUse(npc, player, 5089, 1, 1000, 0));
							break;
						case 3:
							htmltext = "32119-3c.html";
							break;
						case 4:
							htmltext = "32119-4c.html";
							break;
						case 5:
							htmltext = "32119-5a.html";
							break;
					}
				}
				break;
			case BALU_KAIMU:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
						case 2:
						case 3:
						case 4:
							htmltext = "32120-1.html";
							break;
						case 5:
							htmltext = "32120-2.html";
							npc.broadcastPacket(new MagicSkillUse(npc, player, 5089, 1, 1000, 0));
							break;
						case 6:
							htmltext = "32120-3c.html";
							break;
						case 7:
							htmltext = "32120-4c.html";
							break;
						default:
							htmltext = "32120-5a.html";
							break;
					}
				}
				break;
			case CHUTA_KAIMU:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
							htmltext = "32121-1.html";
							break;
						case 8:
							htmltext = "32121-2.html";
							npc.broadcastPacket(new MagicSkillUse(npc, player, 5089, 1, 1000, 0));
							break;
						case 9:
							htmltext = "32121-3e.html";
							break;
						case 10:
							htmltext = "32121-4e.html";
							break;
						default:
							htmltext = "32121-5a.html";
							break;
					}
				}
				break;
			case WARRIORS_GRAVE:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
						case 9:
						case 10:
							htmltext = "32122-1.html";
							break;
						case 11:
							htmltext = "32122-2.html";
							st.setCond(12, true);
							break;
						case 12:
							htmltext = "32122-2l.html";
							break;
						case 13:
							htmltext = "32122-3b.html";
							break;
						case 14:
							htmltext = "32122-4.html";
							st.unset("DO");
							st.unset("MI");
							st.unset("FA");
							st.unset("SOL");
							st.unset("FA2");
							break;
						case 15:
							htmltext = "32122-5.html";
							st.unset("FA");
							st.unset("SOL");
							st.unset("TI");
							st.unset("SOL2");
							st.unset("FA2");
							break;
						case 16:
							htmltext = "32122-6.html";
							st.unset("SOL");
							st.unset("FA");
							st.unset("MI");
							st.unset("FA2");
							st.unset("MI2");
							break;
						case 17:
							htmltext = st.hasQuestItems(BONE_POWDER) ? "32122-7.html" : "32122-7b.html";
							break;
						case 18:
							htmltext = "32122-8.html";
							break;
						default:
							htmltext = "32122-9.html";
							break;
					}
				}
				break;
			case SHILENS_STONE_STATUE:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
						case 9:
						case 10:
						case 11:
						case 12:
						case 13:
						case 14:
						case 15:
						case 16:
						case 17:
							htmltext = "32109-1a.html";
							break;
						case 18:
							if (st.hasQuestItems(BONE_POWDER))
							{
								htmltext = "32109-1.html";
							}
							break;
						case 19:
							htmltext = "32109-2l.html";
							break;
						case 20:
							htmltext = "32109-5.html";
							break;
						default:
							htmltext = "32109-4.html";
							break;
					}
				}
				break;
			case MUSHIKA:
				if (st.isStarted())
				{
					int cond = st.getInt("cond");
					if (cond < 22)
					{
						htmltext = "32114-4.html";
					}
					else if (cond == 22)
					{
						htmltext = "32114-1.html";
					}
					else
					{
						htmltext = "32114-2.html";
					}
				}
				break;
		}
		return htmltext;
	}
	
	public Q126_TheNameOfEvil2(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(ASAMAH);
		addTalkId(ASAMAH, ULU_KAIMU, BALU_KAIMU, CHUTA_KAIMU, WARRIORS_GRAVE, SHILENS_STONE_STATUE, MUSHIKA);
		questItemIds = new int[]
		{
			GAZKH_FRAGMENT,
			BONE_POWDER
		};
	}
	
	public static void main(String[] args)
	{
		new Q126_TheNameOfEvil2(126, qn, "The Name of Evil - 2");
	}
}