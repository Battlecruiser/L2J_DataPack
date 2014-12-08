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
package village_master.DarkElvenChange2;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.util.Util;

/**
 * Dark Elven Change Part 2<br>
 * Original Jython script by DraX and DrLecter
 * @author nonom
 */
public final class DarkElvenChange2 extends Quest
{
	// NPCs
	private static int[] NPCS =
	{
		31328, // Innocentin
		30195, // Brecson
		30699, // Medown
		30474, // Angus
		31324, // Andromeda
		30862, // Oltran
		30910, // Xairakin
		31285, // Samael
		31331, // Valdis
		31334, // Tifaren
		31974, // Drizzit
		32096, // Helminter
	};
	// Items
	private static int MARK_OF_CHALLENGER = 2627;
	private static int MARK_OF_DUTY = 2633;
	private static int MARK_OF_SEEKER = 2673;
	private static int MARK_OF_SCHOLAR = 2674;
	private static int MARK_OF_PILGRIM = 2721;
	private static int MARK_OF_DUELIST = 2762;
	private static int MARK_OF_SEARCHER = 2809;
	private static int MARK_OF_REFORMER = 2821;
	private static int MARK_OF_MAGUS = 2840;
	private static int MARK_OF_FATE = 3172;
	private static int MARK_OF_SAGITTARIUS = 3293;
	private static int MARK_OF_WITCHCRAFT = 3307;
	private static int MARK_OF_SUMMONER = 3336;
	// @formatter:off
	private static int[][] CLASSES = 
	{
		{ 33, 32, 26, 27, 28, 29, MARK_OF_DUTY, MARK_OF_FATE, MARK_OF_WITCHCRAFT }, // SK
		{ 34, 32, 30, 31, 32, 33, MARK_OF_CHALLENGER, MARK_OF_FATE, MARK_OF_DUELIST }, // BD
		{ 43, 42, 34, 35, 36, 37, MARK_OF_PILGRIM, MARK_OF_FATE, MARK_OF_REFORMER }, // SE
		{ 36, 35, 38, 39, 40, 41, MARK_OF_SEEKER, MARK_OF_FATE, MARK_OF_SEARCHER }, // AW
		{ 37, 35, 42, 43, 44, 45, MARK_OF_SEEKER, MARK_OF_FATE, MARK_OF_SAGITTARIUS }, // PR
		{ 40, 39, 46, 47, 48, 49, MARK_OF_SCHOLAR, MARK_OF_FATE, MARK_OF_MAGUS }, // SH
		{ 41, 39, 50, 51, 52, 53, MARK_OF_SCHOLAR, MARK_OF_FATE, MARK_OF_SUMMONER }, // PS
	};
	// @formatter:on
	private DarkElvenChange2()
	{
		super(-1, DarkElvenChange2.class.getSimpleName(), "village_master");
		addStartNpc(NPCS);
		addTalkId(NPCS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (Util.isDigit(event))
		{
			int i = Integer.valueOf(event);
			final ClassId cid = player.getClassId();
			if ((cid.getRace() == Race.DARK_ELF) && (cid.getId() == CLASSES[i][1]))
			{
				int suffix;
				final boolean item1 = st.hasQuestItems(CLASSES[i][6]);
				final boolean item2 = st.hasQuestItems(CLASSES[i][7]);
				final boolean item3 = st.hasQuestItems(CLASSES[i][8]);
				if (player.getLevel() < 40)
				{
					suffix = (!item1 || !item2 || !item3) ? CLASSES[i][2] : CLASSES[i][3];
				}
				else
				{
					if (!item1 || !item2 || !item3)
					{
						suffix = CLASSES[i][4];
					}
					else
					{
						suffix = CLASSES[i][5];
						st.takeItems(CLASSES[i][6], -1);
						st.takeItems(CLASSES[i][7], -1);
						st.takeItems(CLASSES[i][8], -1);
						st.playSound(QuestSound.ITEMSOUND_QUEST_FANFARE_2);
						player.setClassId(CLASSES[i][0]);
						player.setBaseClass(CLASSES[i][0]);
						player.broadcastUserInfo();
						st.exitQuest(false);
					}
				}
				event = "30474-" + suffix + ".html";
			}
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		getQuestState(player, true);
		
		if (player.isSubClassActive())
		{
			return htmltext;
		}
		
		final ClassId cid = player.getClassId();
		if (cid.getRace() == Race.DARK_ELF)
		{
			switch (cid)
			{
				case palusKnight:
				{
					htmltext = "30474-01.html";
					break;
				}
				case shillienOracle:
				{
					htmltext = "30474-08.html";
					break;
				}
				case assassin:
				{
					htmltext = "30474-12.html";
					break;
				}
				case darkWizard:
				{
					htmltext = "30474-19.html";
					break;
				}
				default:
				{
					if (cid.level() == 0)
					{
						// first occupation not made yet
						htmltext = "30474-55.html";
					}
					else if (cid.level() >= 2)
					{
						// second/third occupation change already made
						htmltext = "30474-54.html";
					}
					else
					{
						htmltext = "30474-56.html";
					}
					
				}
			}
		}
		else
		{
			htmltext = "30474-56.html"; // other races
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new DarkElvenChange2();
	}
}
