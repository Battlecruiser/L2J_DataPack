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
package handlers.skillhandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2ExtractableProductItem;
import com.l2jserver.gameserver.model.L2ExtractableSkill;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * @author Zoey76, based on previous version.
 */
public class Extractable implements ISkillHandler
{
	//FIXME: Remove this once skill reuse will be global for main/subclass.
	private static final int[] protectedSkillIds = { 323, 324, 419, 519, 520, 620, 1324, 1387 };
	
	private static final L2SkillType[] SKILL_TYPES =
	{
		L2SkillType.EXTRACTABLE,
		L2SkillType.EXTRACTABLE_FISH
	};
	
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
		{
			return;
		}
		
		L2ExtractableSkill exItem = skill.getExtractableSkill();
		
		if (exItem == null)
		{
			return;
		}
		
		if (exItem.getProductItemsArray().isEmpty())
		{
			_log.warning("Extractable Item Skill with no data, probably wrong/empty table with Skill Id: " + skill.getId());
			return;
		}
		
		final double rndNum = 100 * Rnd.nextDouble();
		double chance = 0;
		double chanceFrom = 0;
		int[] createItemID = new int[20];
		int[] createAmount = new int[20];
		
		//Explanation for future changes:
		//You get one chance for the current skill, then you can fall into
		//one of the "areas" like in a roulette.
		//Example: for an item like Id1,A1,30;Id2,A2,50;Id3,A3,20;
		//#---#-----#--#
		//0  30     80 100
		//If you get chance equal 45% you fall into the second zone 30-80.
		//Meaning you get the second production list.
		//Calculate extraction
		for (L2ExtractableProductItem expi : exItem.getProductItemsArray())
		{
			chance = expi.getChance();
			if ((rndNum >= chanceFrom) && (rndNum <= (chance + chanceFrom)))
			{
				for (int i = 0; i < expi.getId().length; i++)
				{
					createItemID[i] = expi.getId()[i];
					
					if (skill.getSkillType() == L2SkillType.EXTRACTABLE_FISH)
					{
						createAmount[i] = (int) (expi.getAmmount()[i] * Config.RATE_EXTR_FISH);
					}
					else
					{
						createAmount[i] = expi.getAmmount()[i];
					}
				}
				break;
			}
			chanceFrom += chance;
		}
		
		L2PcInstance player = (L2PcInstance) activeChar;
		
		//FIXME: Remove this once skill reuse will be global for main/subclass.
		if (player.isSubClassActive() && (skill.getReuseDelay() > 0) && !Util.contains(protectedSkillIds, skill.getId()))
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.MAIN_CLASS_SKILL_ONLY));
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_CANNOT_BE_USED).addSkillName(skill));
			return;
		}
		
		if (createItemID[0] <= 0)
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NOTHING_INSIDE_THAT));
			return;
		}
		else
		{
			for (int i = 0; i < createItemID.length; i++)
			{
				if (createItemID[i] <= 0)
				{
					continue;
				}
				
				if (ItemTable.getInstance().createDummyItem(createItemID[i]) == null)
				{
					_log.warning("Extractable Item Skill Id:" + skill.getId() + " createItemID " + createItemID[i] + " doesn't have a template!");
					player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NOTHING_INSIDE_THAT));
					return;
				}
				
				if (ItemTable.getInstance().createDummyItem(createItemID[i]).isStackable())
				{
					player.addItem("Extract", createItemID[i], createAmount[i], targets[0], false);
				}
				else
				{
					for (int j = 0; j < createAmount[i]; j++)
					{
						player.addItem("Extract", createItemID[i], 1, targets[0], false);
					}
				}
				
				if (createItemID[i] == 57)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S1_ADENA);;
					sm.addNumber(createAmount[i]);
					player.sendPacket(sm);
				}
				else
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S);;
					sm.addItemName(createItemID[i]);
					if (createAmount[i] > 1)
					{
						sm.addNumber(createAmount[i]);
					}
					player.sendPacket(sm);
				}
			}
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_TYPES;
	}
}