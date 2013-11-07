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
package quests.Q00308_ReedFieldMaintenance;

import java.util.HashMap;
import java.util.Map;

import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;
import quests.Q00309_ForAGoodCause.Q00309_ForAGoodCause;

import com.l2jserver.Config;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;
import com.l2jserver.gameserver.util.Util;

/**
 * Reed Field Maintenance (308)<br>
 * Original Jython script by Bloodshed.
 * @author Joxit
 */
public class Q00308_ReedFieldMaintenance extends Quest
{
	// NPC
	private static final int KATENSA = 32646;
	// Mobs
	private static final int AWAKENED_MUCROKIAN = 22655;
	private static final Map<Integer, Integer> MUCROKIAN = new HashMap<>();
	static
	{
		MUCROKIAN.put(22650, 218); // Mucrokian Fanatic
		MUCROKIAN.put(22651, 258); // Mucrokian Ascetic
		MUCROKIAN.put(22652, 248); // Mucrokian Savior
		MUCROKIAN.put(22653, 290); // Mucrokian Preacher
		MUCROKIAN.put(22654, 220); // Contaminated Mucrokian
		MUCROKIAN.put(22655, 124); // Awakened Mucrokian
	}
	
	// Items
	private static final int MUCROKIAN_HIDE = 14871;
	private static final int AWAKENED_MUCROKIAN_HIDE = 14872;
	// Rewards
	private static final int REC_DYNASTY_EARRINGS_70 = 9985;
	private static final int REC_DYNASTY_NECKLACE_70 = 9986;
	private static final int REC_DYNASTY_RING_70 = 9987;
	private static final int REC_DYNASTY_SIGIL_60 = 10115;
	
	private static final int[] MOIRAI_RECIPES =
	{
		15777,
		15780,
		15783,
		15786,
		15789,
		15790,
		15814,
		15813,
		15812
	};
	
	private static final int[] MOIRAI_PIECES =
	{
		15647,
		15650,
		15653,
		15656,
		15659,
		15692,
		15772,
		15773,
		15774
	};
	
	// Misc
	private static final int MIN_LEVEL = 82;
	
	public Q00308_ReedFieldMaintenance(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KATENSA);
		addTalkId(KATENSA);
		addKillId(MUCROKIAN.keySet());
	}
	
	private boolean canGiveItem(QuestState st, int quanty)
	{
		long mucrokian = st.getQuestItemsCount(MUCROKIAN_HIDE);
		long awakened = st.getQuestItemsCount(AWAKENED_MUCROKIAN_HIDE);
		if (awakened > 0)
		{
			if (awakened >= (quanty / 2))
			{
				st.takeItems(AWAKENED_MUCROKIAN_HIDE, (quanty / 2));
				return true;
			}
			else if (mucrokian >= (quanty - (awakened * 2)))
			{
				st.takeItems(AWAKENED_MUCROKIAN_HIDE, awakened);
				st.takeItems(MUCROKIAN_HIDE, (quanty - (awakened * 2)));
				return true;
			}
		}
		else if (mucrokian >= quanty)
		{
			st.takeItems(MUCROKIAN_HIDE, quanty);
			return true;
		}
		return false;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32646-02.htm":
			case "32646-03.htm":
			case "32646-06.html":
			case "32646-07.html":
			case "32646-08.html":
			case "32646-10.html":
				htmltext = event;
				break;
			case "32646-04.html":
				st.startQuest();
				player.sendPacket(new RadarControl(0, 2, 77325, 205773, -3432));
				htmltext = event;
				break;
			case "claimreward":
				final QuestState q238 = player.getQuestState(Q00238_SuccessFailureOfBusiness.class.getName());
				htmltext = ((q238 != null) && q238.isCompleted()) ? "32646-09.html" : "32646-12.html";
				break;
			case "100":
			case "120":
				htmltext = onItemExchangeRequest(st, MOIRAI_PIECES[getRandom(MOIRAI_PIECES.length - 1)], Integer.parseInt(event));
				break;
			case "192":
			case "230":
				htmltext = onItemExchangeRequest(st, REC_DYNASTY_EARRINGS_70, Integer.parseInt(event));
				break;
			case "256":
			case "308":
				htmltext = onItemExchangeRequest(st, REC_DYNASTY_NECKLACE_70, Integer.parseInt(event));
				break;
			case "128":
			case "154":
				htmltext = onItemExchangeRequest(st, REC_DYNASTY_RING_70, Integer.parseInt(event));
				break;
			case "206":
			case "246":
				htmltext = onItemExchangeRequest(st, REC_DYNASTY_SIGIL_60, Integer.parseInt(event));
				break;
			case "180":
			case "216":
				htmltext = onItemExchangeRequest(st, MOIRAI_RECIPES[getRandom(MOIRAI_RECIPES.length - 1)], Integer.parseInt(event));
				break;
			case "32646-11.html":
				st.exitQuest(true, true);
				htmltext = event;
				break;
		}
		return htmltext;
	}
	
	private String onItemExchangeRequest(QuestState st, int item, int quanty)
	{
		String htmltext;
		if (canGiveItem(st, quanty))
		{
			if (Util.contains(MOIRAI_PIECES, item))
			{
				st.giveItems(item, getRandom(1, 4));
			}
			else
			{
				st.giveItems(item, 1);
			}
			st.playSound(QuestSound.ITEMSOUND_QUEST_FINISH);
			htmltext = "32646-14.html";
		}
		else
		{
			htmltext = "32646-13.html";
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final L2PcInstance partyMember = getRandomPartyMember(killer, 1);
		if (partyMember != null)
		{
			final QuestState st = partyMember.getQuestState(getName());
			float chance = (MUCROKIAN.get(npc.getId()) * Config.RATE_QUEST_DROP);
			if (getRandom(1000) < chance)
			{
				if (npc.getId() == AWAKENED_MUCROKIAN)
				{
					st.giveItems(AWAKENED_MUCROKIAN_HIDE, 1);
				}
				else
				{
					st.giveItems(MUCROKIAN_HIDE, 1);
				}
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		String htmltext = getNoQuestMsg(talker);
		final QuestState st = talker.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		final QuestState q309 = talker.getQuestState(Q00309_ForAGoodCause.class.getSimpleName());
		if ((q309 != null) && q309.isStarted())
		{
			htmltext = "32646-15.html";
		}
		else if (st.isStarted())
		{
			htmltext = (st.hasQuestItems(MUCROKIAN_HIDE) || st.hasQuestItems(AWAKENED_MUCROKIAN_HIDE)) ? "32646-06.html" : "32646-05.html";
		}
		else
		{
			htmltext = (talker.getLevel() >= MIN_LEVEL) ? "32646-01.htm" : "32646-00.html";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00308_ReedFieldMaintenance(308, Q00308_ReedFieldMaintenance.class.getSimpleName(), "Reed Field Maintenance");
	}
}