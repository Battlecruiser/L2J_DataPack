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
package quests.Q10501_ZakenEmbroideredSoulCloak;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Zaken Embroidered Soul Cloak (10501)
 * @author Zoey76
 */
public class Q10501_ZakenEmbroideredSoulCloak extends Quest
{
	// NPC
	private static final int OLF_ADAMS = 32612;
	// Monster
	private static final int ZAKEN = 29181;
	// Items
	private static final int ZAKENS_SOUL_FRAGMENT = 21722;
	private static final int SOUL_CLOAK_OF_ZAKEN = 21719;
	// Misc
	private static final int MIN_LEVEL = 78;
	private static final int FRAGMENT_COUNT = 20;
	
	private Q10501_ZakenEmbroideredSoulCloak(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(OLF_ADAMS);
		addTalkId(OLF_ADAMS);
		addKillId(ZAKEN);
		registerQuestItems(ZAKENS_SOUL_FRAGMENT);
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, player, false))
		{
			st.giveItems(ZAKENS_SOUL_FRAGMENT, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			
			if (st.getQuestItemsCount(ZAKENS_SOUL_FRAGMENT) >= FRAGMENT_COUNT)
			{
				st.setCond(2, true);
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && (player.getLevel() >= MIN_LEVEL) && event.equals("32612-04.html"))
		{
			st.startQuest();
			return event;
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		executeForEachPlayer(killer, npc, isPet, true, true);
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = getNoQuestMsg(player);
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() < MIN_LEVEL) ? "32612-02.html" : "32612-01.htm";
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "32612-05.html";
						break;
					}
					case 2:
					{
						if (st.getQuestItemsCount(ZAKENS_SOUL_FRAGMENT) >= FRAGMENT_COUNT)
						{
							st.giveItems(SOUL_CLOAK_OF_ZAKEN, 1);
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
							st.exitQuest(false, true);
							htmltext = "32612-06.html";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = "32612-03.html";
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q10501_ZakenEmbroideredSoulCloak(10501, Q10501_ZakenEmbroideredSoulCloak.class.getSimpleName(), "Zaken Embroidered Soul Cloak");
	}
}
