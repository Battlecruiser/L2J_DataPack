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
package quests.Q00034_InSearchOfCloth;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * In Search of Cloth (34)
 * @author malyelfik
 */
public class Q00034_InSearchOfCloth extends Quest
{
	// NPCs
	private static final int RADIA = 30088;
	private static final int RALFORD = 30165;
	private static final int VARAN = 30294;
	// Monsters
	private static final int[] MOBS =
	{
		20560, // Trisalim Spider
		20561, // Trisalim Tarantula
	};
	// Items
	private static final int SUEDE = 1866;
	private static final int THREAD = 1868;
	private static final int MYSTERIOUS_CLOTH = 7076;
	private static final int SKEIN_OF_YARN = 7161;
	private static final int SPINNERET = 7528;
	// Misc
	private static final int MIN_LEVEL = 60;
	private static final int SPINNERET_COUNT = 10;
	private static final int SUEDE_COUNT = 3000;
	private static final int THREAD_COUNT = 5000;
	
	public Q00034_InSearchOfCloth()
	{
		super(34, Q00034_InSearchOfCloth.class.getSimpleName(), "In Search of Cloth");
		addStartNpc(RADIA);
		addTalkId(RADIA, RALFORD, VARAN);
		addKillId(MOBS);
		registerQuestItems(SKEIN_OF_YARN, SPINNERET);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30088-03.htm":
				st.startQuest();
				break;
			case "30294-02.html":
				st.setCond(2, true);
				break;
			case "30088-06.html":
				st.setCond(3, true);
				break;
			case "30165-02.html":
				st.setCond(4, true);
				break;
			case "30165-05.html":
				if (st.getQuestItemsCount(SPINNERET) < SPINNERET_COUNT)
				{
					return getNoQuestMsg(player);
				}
				st.takeItems(SPINNERET, SPINNERET_COUNT);
				st.giveItems(SKEIN_OF_YARN, 1);
				st.setCond(6, true);
				break;
			case "30088-10.html":
				if ((st.getQuestItemsCount(SUEDE) >= SUEDE_COUNT) && (st.getQuestItemsCount(THREAD) >= THREAD_COUNT) && st.hasQuestItems(SKEIN_OF_YARN))
				{
					st.takeItems(SKEIN_OF_YARN, 1);
					st.takeItems(SUEDE, SUEDE_COUNT);
					st.takeItems(THREAD, THREAD_COUNT);
					st.giveItems(MYSTERIOUS_CLOTH, 1);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "30088-11.html";
				}
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
		final L2PcInstance member = getRandomPartyMember(player, 4);
		if ((member != null) && getRandomBoolean())
		{
			final QuestState st = member.getQuestState(getName());
			st.giveItems(SPINNERET, 1);
			if (st.getQuestItemsCount(SPINNERET) >= SPINNERET_COUNT)
			{
				st.setCond(5, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case RADIA:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30088-01.htm" : "30088-02.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30088-04.html";
								break;
							case 2:
								htmltext = "30088-05.html";
								break;
							case 3:
								htmltext = "30088-07.html";
								break;
							case 6:
								htmltext = ((st.getQuestItemsCount(SUEDE) >= SUEDE_COUNT) && (st.getQuestItemsCount(THREAD) >= THREAD_COUNT)) ? "30088-08.html" : "30088-09.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case VARAN:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "30294-01.html";
							break;
						case 2:
							htmltext = "30294-03.html";
							break;
					}
				}
				break;
			case RALFORD:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 3:
							htmltext = "30165-01.html";
							break;
						case 4:
							htmltext = "30165-03.html";
							break;
						case 5:
							htmltext = "30165-04.html";
							break;
						case 6:
							htmltext = "30165-06.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
}