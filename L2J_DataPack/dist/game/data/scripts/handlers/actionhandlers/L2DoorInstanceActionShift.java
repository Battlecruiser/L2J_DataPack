/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.actionhandlers;

import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.StaticObject;

public class L2DoorInstanceActionShift implements IActionHandler
{
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		if (activeChar.getAccessLevel().isGm())
		{
			activeChar.setTarget(target);
			activeChar.sendPacket(new MyTargetSelected(target.getObjectId(), activeChar.getLevel()));
			
			StaticObject su;
			// send HP amount if doors are inside castle/fortress zone
			// TODO: needed to be added here doors from conquerable clanhalls
			if ((((L2DoorInstance)target).getCastle() != null
					&& ((L2DoorInstance)target).getCastle().getCastleId() > 0)
					|| (((L2DoorInstance)target).getFort() != null
							&& ((L2DoorInstance)target).getFort().getFortId() > 0
							&& !((L2DoorInstance)target).getIsCommanderDoor()))
				su = new StaticObject((L2DoorInstance)target, true);
			else
				su  = new StaticObject((L2DoorInstance)target, false);
			
			activeChar.sendPacket(su);
			
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/doorinfo.htm");
			html.replace("%class%", target.getClass().getSimpleName());
			html.replace("%hp%",    String.valueOf((int)((L2Character)target).getCurrentHp()));
			html.replace("%hpmax%", String.valueOf(((L2Character)target).getMaxHp()));
			html.replace("%objid%", String.valueOf(target.getObjectId()));
			html.replace("%doorid%",  String.valueOf(((L2DoorInstance)target).getDoorId()));
			
			html.replace("%minx%", String.valueOf(((L2DoorInstance)target).getXMin()));
			html.replace("%miny%", String.valueOf(((L2DoorInstance)target).getYMin()));
			html.replace("%minz%", String.valueOf(((L2DoorInstance)target).getZMin()));
			
			html.replace("%maxx%", String.valueOf(((L2DoorInstance)target).getXMax()));
			html.replace("%maxy%", String.valueOf(((L2DoorInstance)target).getYMax()));
			html.replace("%maxz%", String.valueOf(((L2DoorInstance)target).getZMax()));
			html.replace("%unlock%", ((L2DoorInstance)target).isUnlockable() ? "<font color=00FF00>YES<font>" : "<font color=FF0000>NO</font>");
			
			activeChar.sendPacket(html);
		}
		return true;
	}
	
	public InstanceType getInstanceType()
	{
		return InstanceType.L2DoorInstance;
	}
}