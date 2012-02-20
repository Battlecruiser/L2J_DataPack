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

import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jserver.gameserver.network.serverpackets.ItemList;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.util.Rnd;

/**
 * @author  l3x
 */
public class Harvest implements ISkillHandler
{
	private static Logger _log = Logger.getLogger(Harvest.class.getName());
	
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.HARVEST
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;
		
		final L2Object[] targetList = skill.getTargetList(activeChar);
		
		if (targetList == null || targetList.length == 0)
			return;
		
		if (Config.DEBUG)
			_log.info("Casting harvest");
		
		L2MonsterInstance target;
		InventoryUpdate iu = Config.FORCE_INVENTORY_UPDATE ? null : new InventoryUpdate();
		
		for (L2Object tgt: targetList)
		{
			if (!(tgt instanceof L2MonsterInstance))
				continue;
			
			target = (L2MonsterInstance) tgt;
			
			if (activeChar.getObjectId() != target.getSeederId())
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_HARVEST);
				activeChar.sendPacket(sm);
				continue;
			}
			
			boolean send = false;
			int total = 0;
			int cropId = 0;
			
			// TODO: check items and amount of items player harvest
			if (target.isSeeded())
			{
				if (calcSuccess(activeChar, target))
				{
					L2Attackable.RewardItem[] items = target.takeHarvest();
					if (items != null && items.length > 0)
					{
						for (L2Attackable.RewardItem ritem : items)
						{
							cropId = ritem.getItemId(); // always got 1 type of crop as reward
							if (activeChar.isInParty())
								activeChar.getParty().distributeItem((L2PcInstance)activeChar, ritem, true, target);
							else
							{
								L2ItemInstance item = activeChar.getInventory().addItem("Manor", ritem.getItemId(), ritem.getCount(), (L2PcInstance)activeChar, target);
								if (iu != null)
									iu.addItem(item);
								send = true;
								total += ritem.getCount();
							}
						}
						if (send)
						{
							SystemMessage smsg = SystemMessage.getSystemMessage(SystemMessageId.YOU_PICKED_UP_S1_S2);
							smsg.addNumber(total);
							smsg.addItemName(cropId);
							activeChar.sendPacket(smsg);
							if (activeChar.getParty() != null)
							{
								smsg = SystemMessage.getSystemMessage(SystemMessageId.C1_HARVESTED_S3_S2S);
								smsg.addString(activeChar.getName());
								smsg.addNumber(total);
								smsg.addItemName(cropId);
								activeChar.getParty().broadcastToPartyMembers((L2PcInstance)activeChar, smsg);
							}
							
							if (iu != null)
								activeChar.sendPacket(iu);
							else
								activeChar.sendPacket(new ItemList((L2PcInstance)activeChar, false));
						}
					}
				}
				else
					activeChar.sendPacket(SystemMessageId.THE_HARVEST_HAS_FAILED);
			}
			else
				activeChar.sendPacket(SystemMessageId.THE_HARVEST_FAILED_BECAUSE_THE_SEED_WAS_NOT_SOWN);
		}
		
	}
	
	/**
	 * 
	 * @param activeChar 
	 * @param target 
	 * @return
	 */
	private boolean calcSuccess(L2Character activeChar, L2Character target)
	{
		int basicSuccess = 100;
		final int levelPlayer = activeChar.getLevel();
		final int levelTarget = target.getLevel();
		
		int diff = (levelPlayer - levelTarget);
		if (diff < 0)
			diff = -diff;
		
		// apply penalty, target <=> player levels
		// 5% penalty for each level
		if (diff > 5)
			basicSuccess -= (diff - 5) * 5;
		
		// success rate cant be less than 1%
		if (basicSuccess < 1)
			basicSuccess = 1;
		
		return Rnd.nextInt(99) < basicSuccess;
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
