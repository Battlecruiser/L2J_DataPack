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

import com.l2jserver.gameserver.datatables.HelperBuffTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.L2HelperBuff;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;

public class SupportMagic implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"supportmagicservitor",
		"supportmagic"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
		{
			return false;
		}
		
		boolean result = false;
		if (command.toLowerCase().startsWith(COMMANDS[0]))
		{
			result = makeSupportMagic(activeChar, (L2Npc) target, true);
		}
		else if (command.toLowerCase().startsWith(COMMANDS[1]))
		{
			result = makeSupportMagic(activeChar, (L2Npc) target, false);
		}
		
		return result;
	}
	
	/**
	 * Add Newbie helper buffs to L2Player according to its level.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Get the range level in wich player must be to obtain buff</li> <li>If player level is out of range, display a message and return</li> <li>According to player level cast buff</li><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> Newbie Helper Buff list is define in sql table helper_buff_list</B></FONT><BR>
	 * <BR>
	 * @param player The L2PcInstance that talk with the L2NpcInstance
	 * @param npc
	 * @param isSummon
	 * @return
	 */
	public static boolean makeSupportMagic(L2PcInstance player, L2Npc npc, boolean isSummon)
	{
		if (player == null)
		{
			return false;
		}
		
		// Prevent a cursed weapon weilder of being buffed
		if (player.isCursedWeaponEquipped())
		{
			return false;
		}
		
		int player_level = player.getLevel();
		int lowestLevel = 0;
		int highestLevel = 0;
		
		if (isSummon)
		{
			if (!player.hasSummon() || !player.getSummon().isServitor())
			{
				String content = "<html><body>Only servitors can receive this Support Magic. If you do not have a servitor, you cannot access these spells.</body></html>";
				npc.insertObjectIdAndShowChatWindow(player, content);
				return true;
			}
			npc.setTarget(player.getSummon());
		}
		else
		{
			// Select the player
			npc.setTarget(player);
		}
		
		if (isSummon)
		{
			lowestLevel = HelperBuffTable.getInstance().getServitorLowestLevel();
			highestLevel = HelperBuffTable.getInstance().getServitorHighestLevel();
		}
		else
		{
			// Calculate the min and max level between which the player must be to obtain buff
			if (player.isMageClass())
			{
				lowestLevel = HelperBuffTable.getInstance().getMagicClassLowestLevel();
				highestLevel = HelperBuffTable.getInstance().getMagicClassHighestLevel();
			}
			else
			{
				lowestLevel = HelperBuffTable.getInstance().getPhysicClassLowestLevel();
				highestLevel = HelperBuffTable.getInstance().getPhysicClassHighestLevel();
			}
		}
		// If the player is too high level, display a message and return
		if (player_level > highestLevel)
		{
			String content = "<html><body>Newbie Guide:<br>Only a <font color=\"LEVEL\">novice character of level " + highestLevel + " or less</font> can receive my support magic.<br>Your novice character is the first one that you created and raised in this world.</body></html>";
			npc.insertObjectIdAndShowChatWindow(player, content);
			return true;
		}
		
		// If the player is too low level, display a message and return
		if (player_level < lowestLevel)
		{
			String content = "<html><body>Come back here when you have reached level " + lowestLevel + ". I will give you support magic then.</body></html>";
			npc.insertObjectIdAndShowChatWindow(player, content);
			return true;
		}
		
		L2Skill skill = null;
		if (isSummon)
		{
			for (L2HelperBuff helperBuffItem : HelperBuffTable.getInstance().getHelperBuffTable())
			{
				if (helperBuffItem.isForSummon())
				{
					skill = SkillTable.getInstance().getInfo(helperBuffItem.getSkillID(), helperBuffItem.getSkillLevel());
					if (skill != null)
					{
						npc.doCast(skill);
					}
				}
			}
		}
		else
		{
			// Go through the Helper Buff list define in sql table helper_buff_list and cast skill
			for (L2HelperBuff helperBuffItem : HelperBuffTable.getInstance().getHelperBuffTable())
			{
				if (helperBuffItem.isMagicClassBuff() == player.isMageClass())
				{
					if ((player_level >= helperBuffItem.getLowerLevel()) && (player_level <= helperBuffItem.getUpperLevel()))
					{
						skill = SkillTable.getInstance().getInfo(helperBuffItem.getSkillID(), helperBuffItem.getSkillLevel());
						if (skill.getSkillType() == L2SkillType.SUMMON)
						{
							player.doSimultaneousCast(skill);
						}
						else
						{
							npc.doCast(skill);
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
