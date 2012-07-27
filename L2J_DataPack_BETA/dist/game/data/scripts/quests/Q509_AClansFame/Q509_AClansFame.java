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
package quests.Q509_AClansFame;

import gnu.trove.map.hash.TIntObjectHashMap;

import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.PledgeShowInfoUpdate;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * A Clan's Fame (509)
 * @author Adry_85
 */
public class Q509_AClansFame extends Quest
{
	private static final String qn = "509_AClansFame";
	
	// NPC
	private static final int VALDIS = 31331;
	
	private static final TIntObjectHashMap<int[]> REWARD_POINTS = new TIntObjectHashMap<>();
	
	//@formatter:off
	static
	{
		REWARD_POINTS.put(1, new int[] {25290, 8489, 1378 }); // Daimon The White-Eyed
		REWARD_POINTS.put(2, new int[] {25293, 8490, 1378 }); // Hestia, Guardian Deity Of The Hot Springs
		REWARD_POINTS.put(3, new int[] {25523, 8491, 1070 }); // Plague Golem
		REWARD_POINTS.put(4, new int[] {25322, 8492, 782 }); // Demon's Agent Falston
	}
	//@formatter:on
	
	private static final int[] RAID_BOSS =
	{
		25290,
		25293,
		25523,
		25322
	};
	
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
			case "31331-0.html":
				st.startQuest();
				break;
			case "31331-1.html":
				st.set("raid", "1");
				player.sendPacket(new RadarControl(0, 2, 186304, -43744, -3193));
				break;
			case "31331-2.html":
				st.set("raid", "2");
				player.sendPacket(new RadarControl(0, 2, 134672, -115600, -1216));
				break;
			case "31331-3.html":
				st.set("raid", "3");
				player.sendPacket(new RadarControl(0, 2, 170000, -60000, -3500));
				break;
			case "31331-4.html":
				st.set("raid", "4");
				player.sendPacket(new RadarControl(0, 2, 93296, -75104, -1824));
				break;
			case "31331-5.html":
				st.exitQuest(true, true);
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
		
		L2Clan clan = player.getClan();
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (clan == null || !player.isClanLeader() || clan.getLevel() < 6) ? "31331-0a.htm" : "31331-0b.htm";
				break;
			case State.STARTED:
				if (clan == null || !player.isClanLeader())
				{
					st.exitQuest(true);
					return "31331-6.html";
				}
				
				int raid = st.getInt("raid");
				
				if (REWARD_POINTS.containsKey(raid))
				{
					if (st.hasQuestItems(REWARD_POINTS.get(raid)[1]))
					{
						htmltext = "31331-" + raid + "b.html";
						st.playSound("ItemSound.quest_fanfare_1");
						st.takeItems(REWARD_POINTS.get(raid)[1], -1);
						clan.addReputationScore(REWARD_POINTS.get(raid)[2], true);
						player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.CLAN_QUEST_COMPLETED_AND_S1_POINTS_GAINED).addNumber(REWARD_POINTS.get(raid)[2]));
						clan.broadcastToOnlineMembers(new PledgeShowInfoUpdate(clan));
					}
					else
					{
						htmltext = "31331-" + raid + "a.html";
					}
				}
				else
				{
					htmltext = "31331-0.html";
				}
				break;
			default:
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (player.getClan() == null)
		{
			return null;
		}
		
		QuestState st = null;
		if (player.isClanLeader())
		{
			st = player.getQuestState(qn);
		}
		else
		{
			L2PcInstance pleader = player.getClan().getLeader().getPlayerInstance();
			if (pleader != null && player.isInsideRadius(pleader, 1500, true, false))
			{
				st = pleader.getQuestState(qn);
			}
		}
		
		if (st != null && st.isStarted())
		{
			int raid = st.getInt("raid");
			if (REWARD_POINTS.containsKey(raid))
			{
				if (npc.getNpcId() == REWARD_POINTS.get(raid)[0] && !st.hasQuestItems(REWARD_POINTS.get(raid)[1]))
				{
					st.rewardItems(REWARD_POINTS.get(raid)[1], 1);
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return null;
	}
	
	public Q509_AClansFame(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(VALDIS);
		addTalkId(VALDIS);
		addKillId(RAID_BOSS);
	}
	
	public static void main(String[] args)
	{
		new Q509_AClansFame(509, qn, "A Clan's Fame");
	}
}