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
package village_master.FirstClassTransferTalk;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * This script manages the dialogs of the headmasters of all newbie villages. None of them provide actual class transfers, they only talk about it. Everything is 100% retail-like including htmls.
 * @author jurchiks
 */
public class FirstClassTransferTalk extends Quest
{
	private static final int BITZ = 30026; // TI Fighter Guild Head Master
	private static final int BIOTIN = 30031; // TI Einhasad Temple High Priest
	private static final int ASTERIOS = 30154; // Elf Tetrarch
	private static final int THIFIELL = 30358; // Dark Elf Tetrarch
	private static final int KAKAI = 30565; // Orc Flame Lord
	private static final int REED = 30520; // Dwarf Warehouse Chief
	private static final int BRONK = 30525; // Dwarf Head Blacksmith
	
	private static final int HOFFA = 32171; // Kamael Warehouse Chief
	private static final int FISLER = 32158; // Kamael Village Dwarf Guild Warehouse Chief
	private static final int MOKA = 32157; // Kamael Village Dwarf Guild Head Blacksmith
	private static final int DEVON = 32160; // Kamael Village Dark Elf Guild Grand Magister
	private static final int RIVIAN = 32147; // Kamael Village Elf Guild Grand Master
	private static final int TOOK = 32150; // Kamael Village Orc Guild High Prefect
	private static final int PRANA = 32153; // Kamael Village Human Guild High Priest
	private static final int ALDENIA = 32154; // Kamael Village Human Guild Grand Master
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = npc.getNpcId() + "_";
		switch (npc.getNpcId())
		{
			case BITZ:
			case ALDENIA:
			{
				if ((player.getRace() != Race.Human) || player.getClassId().isMage())
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					htmltext += "fighter.html";
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case BIOTIN:
			case PRANA:
			{
				if ((player.getRace() != Race.Human) || !player.getClassId().isMage())
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					htmltext += "mystic.html";
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case ASTERIOS:
			case RIVIAN:
			{
				if (player.getRace() != Race.Elf)
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					if (player.getClassId().isMage())
					{
						htmltext += "mystic.html";
					}
					else
					{
						htmltext += "fighter.html";
					}
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case THIFIELL:
			case DEVON:
			{
				if (player.getRace() != Race.DarkElf)
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					if (player.getClassId().isMage())
					{
						htmltext += "mystic.html";
					}
					else
					{
						htmltext += "fighter.html";
					}
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case KAKAI:
			case TOOK:
			{
				if (player.getRace() != Race.Orc)
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					if (player.getClassId().isMage())
					{
						htmltext += "mystic.html";
					}
					else
					{
						htmltext += "fighter.html";
					}
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case REED:
			case HOFFA:
			case FISLER: // there are 2 warehouse chiefs in kamael village
			{
				if (player.getRace() != Race.Dwarf)
				{
					htmltext += "no.html";
				}
				else if (player.getClassId().level() == 0)
				{
					htmltext += "fighter.html";
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
			case BRONK:
			case MOKA:
			{
				// no race restriction for blacksmiths (as weird as it may sound...)
				if (player.getClassId().level() == 0)
				{
					htmltext += "fighter.html";
				}
				else if (player.getClassId().level() == 1)
				{
					htmltext += "transfer_1.html";
				}
				else
				{
					htmltext += "transfer_2.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public FirstClassTransferTalk(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int npc : new int[]
		{
			BITZ,
			BIOTIN,
			ASTERIOS,
			THIFIELL,
			KAKAI,
			REED,
			BRONK,
			HOFFA,
			FISLER,
			MOKA,
			DEVON,
			RIVIAN,
			TOOK,
			PRANA,
			ALDENIA
		})
		{
			addStartNpc(npc);
			addTalkId(npc);
		}
	}
	
	public static void main(String[] args)
	{
		new FirstClassTransferTalk(-2, "FirstClassTransferTalk", "village_master");
	}
}
