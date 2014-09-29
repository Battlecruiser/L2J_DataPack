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
package custom.EchoCrystals;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.util.Util;

/**
 * Echo Crystals AI.<br>
 * Original Jython script by DrLecter, formerly based on Elektra's script.
 * @author Plim
 */
public final class EchoCrystals extends Quest
{
	private final static int[] NPCs =
	{
		31042,
		31043
	};
	
	private static final int ADENA = 57;
	private static final int COST = 200;
	
	private static final Map<Integer, ScoreData> SCORES = new FastMap<>();
	
	private class ScoreData
	{
		private final int crystalId;
		private final String okMsg;
		private final String noAdenaMsg;
		private final String noScoreMsg;
		
		public ScoreData(int crystalId, String okMsg, String noAdenaMsg, String noScoreMsg)
		{
			super();
			this.crystalId = crystalId;
			this.okMsg = okMsg;
			this.noAdenaMsg = noAdenaMsg;
			this.noScoreMsg = noScoreMsg;
		}
		
		public int getCrystalId()
		{
			return crystalId;
		}
		
		public String getOkMsg()
		{
			return okMsg;
		}
		
		public String getNoAdenaMsg()
		{
			return noAdenaMsg;
		}
		
		public String getNoScoreMsg()
		{
			return noScoreMsg;
		}
	}
	
	private EchoCrystals()
	{
		super(-1, EchoCrystals.class.getSimpleName(), "custom");
		// Initialize Map
		SCORES.put(4410, new ScoreData(4411, "01", "02", "03"));
		SCORES.put(4409, new ScoreData(4412, "04", "05", "06"));
		SCORES.put(4408, new ScoreData(4413, "07", "08", "09"));
		SCORES.put(4420, new ScoreData(4414, "10", "11", "12"));
		SCORES.put(4421, new ScoreData(4415, "13", "14", "15"));
		SCORES.put(4419, new ScoreData(4417, "16", "05", "06"));
		SCORES.put(4418, new ScoreData(4416, "17", "05", "06"));
		
		for (int npc : NPCs)
		{
			addStartNpc(npc);
			addTalkId(npc);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(EchoCrystals.class.getSimpleName());
		
		if ((st != null) && Util.isDigit(event))
		{
			int score = Integer.parseInt(event);
			if (SCORES.containsKey(score))
			{
				int crystal = SCORES.get(score).getCrystalId();
				String ok = SCORES.get(score).getOkMsg();
				String noadena = SCORES.get(score).getNoAdenaMsg();
				String noscore = SCORES.get(score).getNoScoreMsg();
				
				if (!st.hasQuestItems(score))
				{
					htmltext = npc.getId() + "-" + noscore + ".htm";
				}
				else if (st.getQuestItemsCount(ADENA) < COST)
				{
					htmltext = npc.getId() + "-" + noadena + ".htm";
				}
				else
				{
					st.takeItems(ADENA, COST);
					st.giveItems(crystal, 1);
					htmltext = npc.getId() + "-" + ok + ".htm";
				}
			}
		}
		else
		{
			return htmltext;
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		return "1.htm";
	}
	
	public static void main(String[] args)
	{
		new EchoCrystals();
	}
}
