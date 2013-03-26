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
package hellbound.Jude;

import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author DS
 */
public class Jude extends Quest
{
	private static final int JUDE = 32356;
	private static final int NativeTreasure = 9684;
	private static final int RingOfWindMastery = 9677;
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		
		if ("TreasureSacks".equalsIgnoreCase(event))
		{
			if (HellboundManager.getInstance().getLevel() == 3)
			{
				if (qs.getQuestItemsCount(NativeTreasure) >= 40)
				{
					qs.takeItems(NativeTreasure, 40);
					qs.giveItems(RingOfWindMastery, 1);
					return "32356-02.htm";
				}
			}
			return "32356-02a.htm";
		}
		return event;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		
		switch (HellboundManager.getInstance().getLevel())
		{
			case 0:
			case 1:
			case 2:
				return "32356-01.htm";
			case 3:
			case 4:
				return "32356-01c.htm";
			case 5:
				return "32356-01a.htm";
			default:
				return "32356-01b.htm";
		}
	}
	
	public Jude(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(JUDE);
		addStartNpc(JUDE);
		addTalkId(JUDE);
	}
	
	public static void main(String[] args)
	{
		new Jude(-1, "Jude", "hellbound");
	}
}
