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
package ai.npc.MercenaryCaptain;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager.Territory;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager.TerritoryNPCSpawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.util.Util;

/**
 * Mercenary Captain AI
 * @author malyelfik
 */
public class MercenaryCaptain extends AbstractNpcAI
{
	// NPCs
	private static final int[] NPCS =
	{
		36481, // Mercenary Captain (Gludio)
		36482, // Mercenary Captain (Dion)
		36483, // Mercenary Captain (Giran)
		36484, // Mercenary Captain (Oren)
		36485, // Mercenary Captain (Aden)
		36486, // Mercenary Captain (Innadril)
		36487, // Mercenary Captain (Goddard)
		36488, // Mercenary Captain (Rune)
	};
	// Misc
	private static final int DELAY = 3600000; // 1 hour
	
	private MercenaryCaptain(String name, String descr)
	{
		super(name, descr);
		for (Territory terr : TerritoryWarManager.getInstance().getAllTerritories())
		{
			for (TerritoryNPCSpawn spawn : terr.getSpawnList())
			{
				if (Util.contains(NPCS, spawn.getNpcId()))
				{
					startQuestTimer("say", DELAY, spawn.getNpc(), null, true);
				}
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("say") && !npc.isDecayed())
		{
			if (TerritoryWarManager.getInstance().isTWInProgress())
			{
				broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.CHARGE_CHARGE_CHARGE);
			}
			else if (getRandom(2) == 0)
			{
				broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.COURAGE_AMBITION_PASSION_MERCENARIES_WHO_WANT_TO_REALIZE_THEIR_DREAM_OF_FIGHTING_IN_THE_TERRITORY_WAR_COME_TO_ME_FORTUNE_AND_GLORY_ARE_WAITING_FOR_YOU);
			}
			else
			{
				broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.DO_YOU_WISH_TO_FIGHT_ARE_YOU_AFRAID_NO_MATTER_HOW_HARD_YOU_TRY_YOU_HAVE_NOWHERE_TO_RUN_BUT_IF_YOU_FACE_IT_HEAD_ON_OUR_MERCENARY_TROOP_WILL_HELP_YOU_OUT);
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new MercenaryCaptain(MercenaryCaptain.class.getSimpleName(), "ai/npc");
	}
}