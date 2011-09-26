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
package teleports.OracleTeleport;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

public class OracleTeleport extends Quest
{
	private final static int[] TOWN_DAWN =
	{
		31078,31079,31080,31081,31083,31084,31082,31692,31694,31997,31168
	};
	
	private final static int[] TOWN_DUSK =
	{
		31085,31086,31087,31088,31090,31091,31089,31693,31695,31998,31169
	};
	
	private final static int[] TEMPLE_PRIEST =
	{
		31127,31128,31129,31130,31131,31137,31138,31139,31140,31141
	};
	
	private final static int[] RIFT_POSTERS =
	{
		31488,31489,31490,31491,31492,31493
	};
	
	private final static int[] TELEPORTERS =
	{
		31078,31079,31080,31081,31082,31083,31084,31692,31694,31997,31168,
		31085,31086,31087,31088,31089,31090,31091,31693,31695,31998,31169,
		31494,31495,31496,31497,31498,31499,31500,31501,31502,31503,31504,
		31505,31506,31507,31095,31096,31097,31098,31099,31100,31101,31102,
		31103,31104,31105,31106,31107,31108,31109,31110,31114,31115,31116,
		31117,31118,31119,31120,31121,31122,31123,31124,31125
	};
	
	private final static int[][] RETURN_LOCS =
	{
		{-80555,150337,-3040},{-13953,121404,-2984},{16354,142820,-2696},{83369,149253,-3400},
		{111386,220858,-3544},{83106,53965,-1488},{146983,26595,-2200},{148256,-55454,-2779},
		{45664,-50318,-800},{86795,-143078,-1341},{115136,74717,-2608},{-82368,151568,-3120},
		{-14748,123995,-3112},{18482,144576,-3056},{81623,148556,-3464},{112486,220123,-3592},
		{82819,54607,-1520},{147570,28877,-2264},{149888,-56574,-2979},{44528,-48370,-800},
		{85129,-142103,-1542},{116642,77510,-2688},{-41572,209731,-5087},{-52872,-250283,-7908},
		{45256,123906,-5411},{46192,170290,-4981},{111273,174015,-5437},{-20604,-250789,-8165},
		{-21726, 77385,-5171},{140405, 79679,-5427},{-52366, 79097,-4741},{118311,132797,-4829},
		{172185,-17602,-4901},{ 83000,209213,-5439},{-19500, 13508,-4901},{12525, -248496,-9580},
		{-41561,209225,-5087},{45242,124466,-5413},{110711,174010,-5439},{-22341,77375,-5173},
		{-52889,79098,-4741},{117760,132794,-4831},{171792,-17609,-4901},{82564,209207,-5439},
		{-41565,210048,-5085},{45278,123608,-5411},{111510,174013,-5437},{-21489,77372,-5171},
		{-52016,79103,-4739},{118557,132804,-4829},{172570,-17605,-4899},{83347,209215,-5437},
		{42495,143944,-5381},{45666,170300,-4981},{77138,78389,-5125},{139903,79674,-5429},
		{-20021,13499,-4901},{113418,84535,-6541},{-52940,-250272,-7907},{46499,170301,-4979},
		{-20280,-250785,-8163},{140673,79680,-5437},{-19182,13503,-4899},{12837,-248483,-9579}
	};
	
	public OracleTeleport(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int posters : RIFT_POSTERS)
		{
			addStartNpc(posters);
			addTalkId(posters);
		}
		for (int teleporters : TELEPORTERS)
		{
			addStartNpc(teleporters);
			addTalkId(teleporters);
		}
		for (int priests : TEMPLE_PRIEST)
		{
			addStartNpc(priests);
			addTalkId(priests);
		}
		for (int dawn : TOWN_DAWN)
		{
			addStartNpc(dawn);
			addTalkId(dawn);
		}
		for (int dusk : TOWN_DUSK)
		{
			addStartNpc(dusk);
			addTalkId(dusk);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		int npcId = npc.getNpcId();
		if (event.equalsIgnoreCase("Return"))
		{
			if (Util.contains(TEMPLE_PRIEST, npcId) && st.getState() == State.STARTED)
			{
				int x = RETURN_LOCS[st.getInt("id")][0];
				int y = RETURN_LOCS[st.getInt("id")][1];
				int z = RETURN_LOCS[st.getInt("id")][2];
				player.teleToLocation(x, y, z);
				player.setIsIn7sDungeon(false);
				st.exitQuest(true);
			}
			else if (Util.contains(RIFT_POSTERS, npcId) && st.getState() == State.STARTED)
			{
				int x = RETURN_LOCS[st.getInt("id")][0];
				int y = RETURN_LOCS[st.getInt("id")][1];
				int z = RETURN_LOCS[st.getInt("id")][2];
				player.teleToLocation(x, y, z);
				htmltext = "rift_back.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("Festival"))
		{
			int id = st.getInt("id");
			if (Util.contains(TOWN_DAWN, id))
			{
				player.teleToLocation(-80157, 111344, -4901);
				player.setIsIn7sDungeon(true);
			}
			else if (Util.contains(TOWN_DUSK, id))
			{
				player.teleToLocation(-81261, 86531, -5157);
				player.setIsIn7sDungeon(true);
			}
			else
				htmltext = "oracle1.htm";
		}
		else if (event.equalsIgnoreCase("Dimensional"))
		{
			htmltext = "oracle.htm";
			player.teleToLocation(-114755, -179466, -6752);
		}
		else if (event.equalsIgnoreCase("5.htm"))
		{
			int id = st.getInt("id");
			if (id > -1)
				htmltext = "5a.htm";
			int i = 0;
			for (int id1 : TELEPORTERS)
			{
				if (id1 == npcId)
					break;
				i++;
			}
			st.set("id", Integer.toString(i));
			st.setState(State.STARTED);
			player.teleToLocation(-114755, -179466, -6752);
		}
		else if (event.equalsIgnoreCase("6.htm"))
		{
			htmltext = "6.htm";
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("zigurratDimensional"))
		{
			int playerLevel = player.getLevel();
			if (playerLevel >= 20 && playerLevel < 30)
				st.takeItems(57, 2000);
			else if (playerLevel >= 30 && playerLevel < 40)
				st.takeItems(57, 4500);
			else if (playerLevel >= 40 && playerLevel < 50)
				st.takeItems(57, 8000);
			else if (playerLevel >= 50 && playerLevel < 60)
				st.takeItems(57, 12500);
			else if (playerLevel >= 60 && playerLevel < 70)
				st.takeItems(57, 18000);
			else if (playerLevel >= 70)
				st.takeItems(57, 24500);
			int i = 0;
			for (int zigurrat : TELEPORTERS)
			{
				if (zigurrat == npcId)
					break;
				i++;
			}
			st.set("id", Integer.toString(i));
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			htmltext = "ziggurat_rift.htm";
			player.teleToLocation(-114755, -179466, -6752);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		int npcId = npc.getNpcId();
		if (Util.contains(TOWN_DAWN, npcId))
		{
			st.setState(State.STARTED);
			int i = 0;
			for (int dawn : TELEPORTERS)
			{
				if (dawn == npcId)
					break;
				i++;
			}
			st.set("id", Integer.toString(i));
			st.playSound("ItemSound.quest_accept");
			player.teleToLocation(-80157, 111344, -4901);
			player.setIsIn7sDungeon(true);
		}
		if (Util.contains(TOWN_DUSK, npcId))
		{
			st.setState(State.STARTED);
			int i = 0;
			for (int dusk : TELEPORTERS)
			{
				if (dusk == npcId)
					break;
				i++;
			}
			st.set("id", Integer.toString(i));
			st.playSound("ItemSound.quest_accept");
			player.teleToLocation(-81261, 86531, -5157);
			player.setIsIn7sDungeon(true);
		}
		else if (npcId >= 31494 && npcId <= 31507)
		{
			if (player.getLevel() < 20)
			{
				htmltext = "1.htm";
				st.exitQuest(true);
			}
			else if (player.getAllActiveQuests().length > 23)
			{
				htmltext = "1a.htm";
				st.exitQuest(true);
			}
			else if (!st.hasQuestItems(7079))
				htmltext = "3.htm";
			else
			{
				st.setState(State.CREATED);
				htmltext = "4.htm";
			}
		}
		else if ((npcId >= 31095 && npcId <= 31111) || (npcId >= 31114 && npcId <= 31126))
		{
			int playerLevel = player.getLevel();
			if (playerLevel < 20)
			{
				htmltext = "ziggurat_lowlevel.htm";
				st.exitQuest(true);
			}
			else if (player.getAllActiveQuests().length > 40)
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.TOO_MANY_QUESTS));
				st.exitQuest(true);
			}
			else if (!st.hasQuestItems(7079))
			{
				htmltext = "ziggurat_nofrag.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 20 && playerLevel < 30 && st.getQuestItemsCount(57) < 2000)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 30 && playerLevel < 40 && st.getQuestItemsCount(57) < 4500)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 40 && playerLevel < 50 && st.getQuestItemsCount(57) < 8000)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 50 && playerLevel < 60 && st.getQuestItemsCount(57) < 12500)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 60 && playerLevel < 70 && st.getQuestItemsCount(57) < 18000)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else if (playerLevel >= 70 && st.getQuestItemsCount(57) < 24500)
			{
				htmltext = "ziggurat_noadena.htm";
				st.exitQuest(true);
			}
			else
				htmltext = "ziggurat.htm";
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new OracleTeleport(-1, "OracleTeleport", "teleports");
	}
}