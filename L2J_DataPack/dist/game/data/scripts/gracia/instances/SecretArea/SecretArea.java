/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package gracia.instances.SecretArea;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Secret Area in the Keucereus Fortress instance zone.
 * @author Gladicek
 */
public final class SecretArea extends Quest
{
	protected class SAWorld extends InstanceWorld
	{
		
	}
	
	private static final int TEMPLATE_ID = 117;
	private static final int GINBY = 32566;
	private static final int LELRIKIA = 32567;
	private static final int ENTER = 0;
	private static final int EXIT = 1;
	private static final Location[] TELEPORTS =
	{
		new Location(-23758, -8959, -5384),
		new Location(-185057, 242821, 1576)
	};
	
	public SecretArea()
	{
		super(-1, SecretArea.class.getSimpleName(), "gracia/instances");
		addStartNpc(GINBY);
		addTalkId(GINBY);
		addTalkId(LELRIKIA);
	}
	
	protected void enterInstance(L2PcInstance player)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof SAWorld)
			{
				teleportPlayer(player, TELEPORTS[ENTER], world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			return;
		}
		
		world = new SAWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance("SecretArea.xml"));
		world.setTemplateId(TEMPLATE_ID);
		world.addAllowed(player.getObjectId());
		world.setStatus(0);
		InstanceManager.getInstance().addWorld(world);
		teleportPlayer(player, TELEPORTS[ENTER], world.getInstanceId());
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		if ((npc.getId() == GINBY) && event.equalsIgnoreCase("enter"))
		{
			enterInstance(player);
			return "32566-01.html";
		}
		else if ((npc.getId() == LELRIKIA) && event.equalsIgnoreCase("exit"))
		{
			teleportPlayer(player, TELEPORTS[EXIT], 0);
			return "32567-01.html";
		}
		return htmltext;
	}
}
