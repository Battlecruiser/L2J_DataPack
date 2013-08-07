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
importPackage(java.util);
importPackage(java.lang);
importPackage(com.l2jserver.gameserver.cache);
importPackage(com.l2jserver.gameserver.datatables);
importPackage(com.l2jserver.gameserver.handler);
importPackage(com.l2jserver.gameserver.instancemanager);
importPackage(com.l2jserver.gameserver.model);
importPackage(com.l2jserver.gameserver.model.actor);
importPackage(com.l2jserver.gameserver.model.actor.instance);
importPackage(com.l2jserver.gameserver.network.serverpackets);
importPackage(com.l2jserver.gameserver.util);

/**
 * @author UnAfraid
 */
AdminCommandHandler.getInstance().registerHandler(new JavaAdapter(IAdminCommandHandler,
{
	// Override useAdminCommand() method.
	useAdminCommand : function(command, player)
	{
		var st = new StringTokenizer(command, " ");
		if (st.hasMoreTokens())
		{
			var cmd = st.nextToken();
			if (cmd == 'admin_scan')
			{
				var radius = 500;
				if (st.hasMoreTokens())
				{
					var obj = st.nextToken();
					if (Util.isDigit(obj))
					{
						radius = obj;
					}
				}
				
				var htm = HtmCache.getInstance().getHtm(player.getHtmlPrefix(), "data/html/admin/scan.htm");
				var sb = new StringBuilder();
				var it = player.getKnownList().getKnownCharactersInRadius(radius).iterator();
				while (it.hasNext())
				{
					var character = it.next();
					if (character instanceof L2Npc)
					{
						sb.append("<tr>");
						sb.append("<td width=\"54\">" + character.getId() + "</td>");
						sb.append("<td width=\"54\">" + character.getName() + "</td>");
						sb.append("<td width=\"54\">" + Math.round(Util.calculateDistance(player, character, false)) + "</td>");
						sb.append("<td width=\"54\"><a action=\"bypass -h admin_deleteNpcByObjectId " + character.getObjectId() + "\"><font color=\"LEVEL\">Delete</font></a></td>");
						sb.append("<td width=\"54\"><a action=\"bypass -h admin_move_to " + character.getX() + " " + character.getY() + " " + character.getZ() + "\"><font color=\"LEVEL\">Go to</font></a></td>");
						sb.append("</tr>");
					}
				}
				htm = htm.replaceAll("%data%", sb.toString());
				player.sendPacket(new NpcHtmlMessage(0, htm));
			}
			else if (cmd = 'admin_deleteNpcByObjectId' && st.hasMoreTokens())
			{
				var objectId = st.nextToken();
				if (Util.isDigit(objectId))
				{
					var it = player.getKnownList().getKnownCharacters().iterator();
					while (it.hasNext())
					{
						var character = it.next();
						if ((character instanceof L2Npc) && (character.getObjectId() == objectId))
						{
							character.deleteMe();
							var spawn = character.getSpawn();
							if (spawn != null)
							{
								spawn.stopRespawn();
								
								if (RaidBossSpawnManager.getInstance().isDefined(spawn.getId()))
								{
									RaidBossSpawnManager.getInstance().deleteSpawn(spawn, true);
								}
								else
								{
									SpawnTable.getInstance().deleteSpawn(spawn, true);
								}
							}
							player.sendMessage(character.getName() + " have been deleted.");
							this.useAdminCommand("admin_scan", player);
						}
					}
				}
			}
		}
		return true;
	},
	
	// Override getAdminCommandList() method.
	getAdminCommandList : function()
	{
		return new Array("admin_scan", "admin_deleteNpcByObjectId");
	}
}));