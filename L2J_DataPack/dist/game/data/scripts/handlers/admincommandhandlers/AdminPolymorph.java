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
package handlers.admincommandhandlers;

import com.l2jserver.gameserver.datatables.TransformData;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.SetupGauge;
import com.l2jserver.gameserver.util.Util;

/**
 * Polymorph admin command implementation.
 * @author Zoey76
 */
public class AdminPolymorph implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_polymorph",
		"admin_unpolymorph",
		"admin_transform",
		"admin_untransform",
		"admin_transform_menu",
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_transform_menu"))
		{
			AdminHtml.showAdminHtml(activeChar, "transform.htm");
			return true;
		}
		else if (command.startsWith("admin_untransform"))
		{
			L2Object obj = activeChar.getTarget();
			if (obj instanceof L2Character)
			{
				((L2Character) obj).stopTransformation(true);
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else if (command.startsWith("admin_transform"))
		{
			final L2Object obj = activeChar.getTarget();
			if ((obj == null) || !obj.isPlayer())
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return false;
			}
			
			final L2PcInstance player = obj.getActingPlayer();
			if (activeChar.isSitting())
			{
				activeChar.sendPacket(SystemMessageId.CANNOT_TRANSFORM_WHILE_SITTING);
				return false;
			}
			
			if (player.isTransformed() || player.isInStance())
			{
				if (!command.contains(" "))
				{
					player.untransform();
					return true;
				}
				activeChar.sendPacket(SystemMessageId.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN);
				return false;
			}
			
			if (player.isInWater())
			{
				activeChar.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_INTO_THE_DESIRED_FORM_IN_WATER);
				return false;
			}
			
			if (player.isFlyingMounted() || player.isMounted())
			{
				activeChar.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_WHILE_RIDING_A_PET);
				return false;
			}
			
			final String[] parts = command.split(" ");
			if ((parts.length != 2) || !Util.isDigit(parts[1]))
			{
				activeChar.sendMessage("Usage: //transform <id>");
				return false;
			}
			
			final int id = Integer.parseInt(parts[1]);
			if (!TransformData.getInstance().transformPlayer(id, player))
			{
				player.sendMessage("Unknown transformation ID: " + id);
				return false;
			}
		}
		if (command.startsWith("admin_polymorph"))
		{
			final String[] parts = command.split(" ");
			if ((parts.length < 2) || !Util.isDigit(parts[1]))
			{
				activeChar.sendMessage("Usage: //polymorph [type] <id>");
				return false;
			}
			
			if (parts.length > 2)
			{
				doPolymorph(activeChar, activeChar.getTarget(), parts[2], parts[1]);
			}
			else
			{
				doPolymorph(activeChar, activeChar.getTarget(), parts[1], "npc");
			}
		}
		else if (command.equals("admin_unpolymorph"))
		{
			doUnPolymorph(activeChar, activeChar.getTarget());
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	/**
	 * Polymorph a creature.
	 * @param activeChar the active Game Master
	 * @param obj the target
	 * @param id the polymorph ID
	 * @param type the polymorph type
	 */
	private static void doPolymorph(L2PcInstance activeChar, L2Object obj, String id, String type)
	{
		if (obj != null)
		{
			obj.getPoly().setPolyInfo(type, id);
			// animation
			if (obj instanceof L2Character)
			{
				L2Character Char = (L2Character) obj;
				MagicSkillUse msk = new MagicSkillUse(Char, 1008, 1, 4000, 0);
				Char.broadcastPacket(msk);
				SetupGauge sg = new SetupGauge(0, 4000);
				Char.sendPacket(sg);
			}
			// end of animation
			obj.decayMe();
			obj.spawnMe(obj.getX(), obj.getY(), obj.getZ());
			activeChar.sendMessage("Polymorph succeed");
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
		}
	}
	
	/**
	 * Unpolymorh a creature.
	 * @param activeChar the active Game Master
	 * @param target the target
	 */
	private static void doUnPolymorph(L2PcInstance activeChar, L2Object target)
	{
		if (target != null)
		{
			target.getPoly().setPolyInfo(null, "1");
			target.decayMe();
			target.spawnMe(target.getX(), target.getY(), target.getZ());
			activeChar.sendMessage("Unpolymorph succeed");
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
		}
	}
}
