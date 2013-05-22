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
package ai.npc.BlackJudge;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.EtcStatusUpdate;

/**
 * Black Judge AI.
 * @author St3eT
 */
public class BlackJudge extends AbstractNpcAI
{
	// NPC
	private static final int BLACK_JUDGE = 30981;
	
	// Misc
	private static final int COST_NOGR = 3600;
	private static final int COST_D = 8640;
	private static final int COST_C = 25200;
	private static final int COST_B = 50400;
	private static final int COST_A = 86400;
	private static final int COST_S = 144000;
	
	private BlackJudge()
	{
		super(BlackJudge.class.getSimpleName(), "ai/npc");
		addStartNpc(BLACK_JUDGE);
		addTalkId(BLACK_JUDGE);
		addFirstTalkId(BLACK_JUDGE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "remove_info":
			{
				switch (player.getExpertiseLevel())
				{
					case 0:
					{
						htmltext = "30981-01.html";
						break;
					}
					case 1:
					{
						htmltext = "30981-02.html";
						break;
					}
					case 2:
					{
						htmltext = "30981-03.html";
						break;
					}
					case 3:
					{
						htmltext = "30981-04.html";
						break;
					}
					case 4:
					{
						htmltext = "30981-05.html";
						break;
					}
					case 5:
					case 6:
					case 7:
					{
						htmltext = "30981-06.html";
						break;
					}
				}
				break;
			}
			case "remove_dp":
			{
				if (player.getDeathPenaltyBuffLevel() > 0)
				{
					int cost = 0;
					switch (player.getExpertiseLevel())
					{
						case 0:
						{
							cost = COST_NOGR;
							break;
						}
						case 1:
						{
							cost = COST_D;
							break;
						}
						case 2:
						{
							cost = COST_C;
							break;
						}
						case 3:
						{
							cost = COST_B;
							break;
						}
						case 4:
						{
							cost = COST_A;
							break;
						}
						case 5:
						case 6:
						case 7:
						{
							cost = COST_S;
							break;
						}
					}
					
					if (player.getAdena() >= cost)
					{
						takeItems(player, PcInventory.ADENA_ID, cost);
						player.setDeathPenaltyBuffLevel(player.getDeathPenaltyBuffLevel() - 1);
						player.sendPacket(SystemMessageId.DEATH_PENALTY_LIFTED);
						player.sendPacket(new EtcStatusUpdate(player));
					}
					else
					{
						htmltext = "30981-07.html";
					}
				}
				else
				{
					htmltext = "30981-08.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new BlackJudge();
	}
}