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
package handlers.bypasshandlers;

import java.util.StringTokenizer;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SetupGauge;

public class RentPet implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"RentPet"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2MerchantInstance))
		{
			return false;
		}
		
		if (!Config.ALLOW_RENTPET)
		{
			return false;
		}
		
		if (!Config.LIST_PET_RENT_NPC.contains(((L2Npc) target).getTemplate().getNpcId()))
		{
			return false;
		}
		
		try
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			
			if (st.countTokens() < 1)
			{
				NpcHtmlMessage msg = new NpcHtmlMessage(((L2Npc) target).getObjectId());
				msg.setHtml("<html><body>Pet Manager:<br>" + "You can rent a wyvern or strider for adena.<br>My prices:<br1>" + "<table border=0><tr><td>Ride</td></tr>" + "<tr><td>Wyvern</td><td>Strider</td></tr>" + "<tr><td><a action=\"bypass -h npc_%objectId%_RentPet 1\">30 sec/1800 adena</a></td><td><a action=\"bypass -h npc_%objectId%_RentPet 11\">30 sec/900 adena</a></td></tr>" + "<tr><td><a action=\"bypass -h npc_%objectId%_RentPet 2\">1 min/7200 adena</a></td><td><a action=\"bypass -h npc_%objectId%_RentPet 12\">1 min/3600 adena</a></td></tr>" + "<tr><td><a action=\"bypass -h npc_%objectId%_RentPet 3\">10 min/720000 adena</a></td><td><a action=\"bypass -h npc_%objectId%_RentPet 13\">10 min/360000 adena</a></td></tr>" + "<tr><td><a action=\"bypass -h npc_%objectId%_RentPet 4\">30 min/6480000 adena</a></td><td><a action=\"bypass -h npc_%objectId%_RentPet 14\">30 min/3240000 adena</a></td></tr>" + "</table>" + "</body></html>");
				msg.replace("%objectId%", String.valueOf(((L2Npc) target).getObjectId()));
				activeChar.sendPacket(msg);
			}
			else
			{
				tryRentPet(activeChar, Integer.parseInt(st.nextToken()));
			}
			
			return true;
		}
		catch (Exception e)
		{
			_log.info("Exception in " + getClass().getSimpleName());
		}
		return false;
	}
	
	public static final void tryRentPet(L2PcInstance player, int val)
	{
		if ((player == null) || player.hasSummon() || player.isMounted() || player.isRentedPet() || player.isTransformed() || player.isCursedWeaponEquipped())
		{
			return;
		}
		if (!player.disarmWeapons())
		{
			return;
		}
		
		int petId;
		double price = 1;
		int cost[] =
		{
			1800, 7200, 720000, 6480000
		};
		int ridetime[] =
		{
			30, 60, 600, 1800
		};
		
		if (val > 10)
		{
			petId = 12526;
			val -= 10;
			price /= 2;
		}
		else
		{
			petId = 12621;
		}
		
		if ((val < 1) || (val > 4))
		{
			return;
		}
		
		price *= cost[val - 1];
		int time = ridetime[val - 1];
		
		if (!player.reduceAdena("Rent", (long) price, player.getLastFolkNPC(), true))
		{
			return;
		}
		
		player.mount(petId, 0, false);
		SetupGauge sg = new SetupGauge(3, time * 1000);
		player.sendPacket(sg);
		player.startRentPet(time);
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
