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
package hellbound.Hude;

import com.l2jserver.gameserver.datatables.MultisellData;
import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author DS
 */
public class Hude extends Quest
{
	private static final int HUDE = 32298;
	private static final int BASIC_CERT = 9850;
	private static final int STANDART_CERT = 9851;
	private static final int PREMIUM_CERT = 9852;
	private static final int MARK_OF_BETRAYAL = 9676;
	private static final int LIFE_FORCE = 9681;
	private static final int CONTAINED_LIFE_FORCE = 9682;
	private static final int MAP = 9994;
	private static final int STINGER = 10012;
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		
		if ("scertif".equalsIgnoreCase(event))
		{
			if (HellboundManager.getInstance().getLevel() > 3)
			{
				if (qs.hasQuestItems(BASIC_CERT) && (qs.getQuestItemsCount(MARK_OF_BETRAYAL) >= 30) && (qs.getQuestItemsCount(STINGER) >= 60))
				{
					qs.takeItems(MARK_OF_BETRAYAL, 30);
					qs.takeItems(STINGER, 60);
					qs.takeItems(BASIC_CERT, 1);
					qs.giveItems(STANDART_CERT, 1);
					return "32298-04a.htm";
				}
			}
			return "32298-04b.htm";
		}
		else if ("pcertif".equalsIgnoreCase(event))
		{
			if (HellboundManager.getInstance().getLevel() > 6)
			{
				if (qs.hasQuestItems(STANDART_CERT) && (qs.getQuestItemsCount(LIFE_FORCE) >= 56) && (qs.getQuestItemsCount(CONTAINED_LIFE_FORCE) >= 14))
				{
					qs.takeItems(LIFE_FORCE, 56);
					qs.takeItems(CONTAINED_LIFE_FORCE, 14);
					qs.takeItems(STANDART_CERT, 1);
					qs.giveItems(PREMIUM_CERT, 1);
					qs.giveItems(MAP, 1);
					return "32298-06a.htm";
				}
			}
			return "32298-06b.htm";
		}
		else if ("multisell1".equalsIgnoreCase(event))
		{
			if (qs.hasQuestItems(STANDART_CERT) || qs.hasQuestItems(PREMIUM_CERT))
			{
				MultisellData.getInstance().separateAndSend(322980001, player, npc, false);
			}
		}
		else if ("multisell2".equalsIgnoreCase(event))
		{
			if (qs.hasQuestItems(PREMIUM_CERT))
			{
				MultisellData.getInstance().separateAndSend(322980002, player, npc, false);
			}
		}
		return null;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		
		if (!qs.hasQuestItems(BASIC_CERT) && !qs.hasQuestItems(STANDART_CERT) && !qs.hasQuestItems(PREMIUM_CERT))
		{
			htmltext = "32298-01.htm";
		}
		else if (qs.hasQuestItems(BASIC_CERT) && !qs.hasQuestItems(STANDART_CERT) && !qs.hasQuestItems(PREMIUM_CERT))
		{
			htmltext = "32298-03.htm";
		}
		else if (qs.hasQuestItems(STANDART_CERT) && !qs.hasQuestItems(PREMIUM_CERT))
		{
			htmltext = "32298-05.htm";
		}
		else if (qs.hasQuestItems(PREMIUM_CERT))
		{
			htmltext = "32298-07.htm";
		}
		return htmltext;
	}
	
	public Hude(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(HUDE);
		addStartNpc(HUDE);
		addTalkId(HUDE);
	}
	
	public static void main(String[] args)
	{
		new Hude(-1, "Hude", "hellbound");
	}
}
