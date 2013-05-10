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
package handlers.actionhandlers;

import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2StaticObjectInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.StaticObject;
import com.l2jserver.util.StringUtil;

public class L2StaticObjectInstanceActionShift implements IActionHandler
{
	@Override
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		if (activeChar.getAccessLevel().isGm())
		{
			activeChar.setTarget(target);
			activeChar.sendPacket(new StaticObject((L2StaticObjectInstance) target));
			
			NpcHtmlMessage html = new NpcHtmlMessage(target.getObjectId());
			html.setHtml(StringUtil.concat("<html><body><center><font color=\"LEVEL\">Static Object Info</font></center><br><table border=0><tr><td>Coords X,Y,Z: </td><td>", String.valueOf(target.getX()), ", ", String.valueOf(target.getY()), ", ", String.valueOf(target.getZ()), "</td></tr><tr><td>Object ID: </td><td>", String.valueOf(target.getObjectId()), "</td></tr><tr><td>Static Object ID: </td><td>", String.valueOf(((L2StaticObjectInstance) target).getStaticObjectId()), "</td></tr><tr><td>Mesh Index: </td><td>", String.valueOf(((L2StaticObjectInstance) target).getMeshIndex()), "</td></tr><tr><td><br></td></tr><tr><td>Class: </td><td>", target.getClass().getSimpleName(), "</td></tr></table></body></html>"));
			activeChar.sendPacket(html);
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2StaticObjectInstance;
	}
}
