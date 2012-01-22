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
package quests.Q10275_ContainingTheAttributePower;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Containing the Attribute Power (10275).<br>
 * Original jython script by Kerberos v1.0 on 2009/05/03
 * @author nonom
 */
public class Q10275_ContainingTheAttributePower extends Quest
{
	private static final String qn = "10275_ContainingTheAttributePower";
	
	// NPCs
	private static final int HOLLY = 30839;
	private static final int WEBER = 31307;
	private static final int YIN = 32325;
	private static final int YANG = 32326;
	
	private static final int WATER = 27380;
	private static final int AIR = 27381;
	
	// Items
	private static final int YINSWORD = 13845;
	private static final int YANGSWORD = 13881;
	private static final int SOULPIECEWATER = 13861;
	private static final int SOULPIECEAIR = 13862;
	
	// Skills
	private static final L2Skill BlessingOfFire = SkillTable.getInstance().getInfo(2635, 1);
	private static final L2Skill BlessingOfEarth = SkillTable.getInstance().getInfo(2636, 1);
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		final int npcId = npc.getNpcId();
		final int cond = st.getInt("cond");
		switch (st.getState())
		{
			case State.COMPLETED:
				if (npcId == HOLLY)
				{
					htmltext = "30839-0a.htm";
				}
				else if (npcId == WEBER)
				{
					htmltext = "31307-0a.htm";
				}
				break;
			case State.CREATED:
				if (player.getLevel() >= 76)
				{
					if (npcId == HOLLY)
					{
						htmltext = "30839-01.htm";
					}
					else
					{
						htmltext = "31307-01.htm";
					}
				}
				else if (npcId == HOLLY)
				{
					htmltext = "30839-00.htm";
				}
				else
				{
					htmltext = "31307-00.htm";
				}
				break;
			default:
				switch (npcId)
				{
					case HOLLY:
						switch (cond)
						{
							case 1:
								htmltext = "30839-03.htm";
								break;
							case 2:
								htmltext = "30839-05.htm";
								break;
						}
						break;
					case WEBER:
						switch (cond)
						{
							case 1:
								htmltext = "31307-03.htm";
								break;
							case 7:
								htmltext = "31307-05.htm";
								break;
						}
						break;
					case YIN:
						switch (cond)
						{
							case 2:
								htmltext = "32325-01.htm";
								break;
							case 3:
							case 5:
								htmltext = "32325-04.htm";
								break;
							case 4:
								htmltext = "32325-08.htm";
								st.takeItems(YINSWORD, 1);
								st.takeItems(SOULPIECEWATER, -1);
								break;
							case 6:
								htmltext = "32325-10.htm";
								break;
						}
					case YANG:
						switch (cond)
						{
							case 7:
								htmltext = "32326-01.htm";
								break;
							case 8:
							case 10:
								htmltext = "32326-04.htm";
								break;
							case 9:
								htmltext = "32326-08.htm";
								st.takeItems(YANGSWORD, 1);
								st.takeItems(SOULPIECEAIR, -1);
								break;
							case 11:
								htmltext = "32326-10.htm";
								break;
						}
						break;
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
		
		switch (event)
		{
			case "30839-02.htm":
			case "31307-02.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "30839-05.htm":
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
				break;
			case "31307-05.htm":
				st.set("cond", "7");
				st.playSound("ItemSound.quest_middle");
				break;
			case "32325-03.htm":
				st.set("cond", "3");
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				st.playSound("ItemSound.quest_middle");
				break;
			case "32326-03.htm":
				st.set("cond", "8");
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				st.playSound("ItemSound.quest_middle");
				break;
			case "32325-06.htm":
				if (st.hasQuestItems(YINSWORD))
				{
					st.takeItems(YINSWORD, 1);
					htmltext = "32325-07.htm";
				}
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				break;
			case "32326-06.htm":
				if (st.hasQuestItems(YANGSWORD))
				{
					st.takeItems(YANGSWORD, 1);
					htmltext = "32326-07.htm";
				}
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				break;
			case "32325-09.htm":
				st.set("cond", "5");
				BlessingOfFire.getEffects(player, player);
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				st.playSound("ItemSound.quest_middle");
				break;
			case "32326-09.htm":
				st.set("cond", "10");
				BlessingOfEarth.getEffects(player, player);
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				st.playSound("ItemSound.quest_middle");
				break;
		}
		
		if (Util.isDigit(event))
		{
			st.giveItems(10520 + Integer.valueOf(event), 2);
			st.addExpAndSp(202160, 20375);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
			htmltext = Integer.toString(npc.getNpcId()) + "-1" + event + ".htm";
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return null;
		}
		
		final int cond = st.getInt("cond");
		switch (npc.getNpcId())
		{
			case AIR:
				if ((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == YANGSWORD) && ((cond == 8) || (cond == 10)) && (st.getQuestItemsCount(SOULPIECEAIR) < 6) && (st.getRandom(100) < 30))
				{
					st.giveItems(SOULPIECEAIR, 1);
					if (st.getQuestItemsCount(SOULPIECEAIR) >= 6)
					{
						st.set("cond", Integer.toString(cond + 1));
						st.playSound("ItemSound.quest_middle");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
				}
				break;
			case WATER:
				if ((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == YINSWORD) && ((cond >= 3) || (cond <= 5)) && (st.getQuestItemsCount(SOULPIECEWATER) < 6) && (st.getRandom(100) < 30))
				{
					st.giveItems(SOULPIECEWATER, 1);
					if (st.getQuestItemsCount(SOULPIECEWATER) >= 6)
					{
						st.set("cond", Integer.toString(cond + 1));
						st.playSound("ItemSound.quest_middle");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
				}
				break;
		}
		return null;
		
	}
	
	public Q10275_ContainingTheAttributePower(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(HOLLY, WEBER);
		
		addTalkId(HOLLY, WEBER, YIN, YANG);
		
		addKillId(AIR, WATER);
		
		questItemIds = new int[]
		{
			YINSWORD, YANGSWORD, SOULPIECEWATER, SOULPIECEAIR
		};
	}
	
	public static void main(String[] args)
	{
		new Q10275_ContainingTheAttributePower(10275, qn, "Containing the Attribute Power");
	}
}
