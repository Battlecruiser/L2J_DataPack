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
package hellbound.AI.NPC.Budenka;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Budenka AI.
 * @author St3eT
 */
public final class Budenka extends AbstractNpcAI
{
	// NPCs
	private static final int BUDENKA = 32294;
	// Items
	private static final int STANDART_CERT = 9851;
	private static final int PREMIUM_CERT = 9852;
	
	public Budenka()
	{
		super(Budenka.class.getSimpleName(), "hellbound/AI/NPC");
		addStartNpc(BUDENKA);
		addFirstTalkId(BUDENKA);
		addTalkId(BUDENKA);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "Budenka-02.html":
			case "Budenka-03.html":
			case "Budenka-04.html":
			case "Budenka-05.html":
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (hasQuestItems(player, STANDART_CERT, PREMIUM_CERT))
		{
			htmltext = "Budenka-07.html";
		}
		else if (hasQuestItems(player, STANDART_CERT))
		{
			htmltext = "Budenka-06.html";
		}
		else
		{
			htmltext = "Budenka-01.html";
		}
		return htmltext;
	}
}