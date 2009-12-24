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
package handlers.itemhandlers;

import javolution.util.FastMap;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance.TimeStamp;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2EffectType;

/**
 * 
 * temp handler
 * here u can found items that yet cannot be unhardcoded due to missing better core support
 *
 */
public class Potions implements IItemHandler
{
	/**
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public synchronized void useItem(L2Playable playable, L2ItemInstance item)
	{
		L2PcInstance activeChar; // use activeChar only for L2PcInstance checks where cannot be used PetInstance

		boolean res = false;

		if (playable instanceof L2PcInstance)
			activeChar = (L2PcInstance) playable;
		else if (playable instanceof L2PetInstance)
			activeChar = ((L2PetInstance) playable).getOwner();
		else
			return;

		if (!TvTEvent.onPotionUse(playable.getObjectId()) || playable.isAllSkillsDisabled())
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}

		int itemId = item.getItemId();

		switch (itemId)
		{
			
			case 726: // Custom Mana Drug, xml: 10000
				if (Config.L2JMOD_ENABLE_MANA_POTIONS_SUPPORT)
					usePotion(activeChar, 10000, 1);
				else
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
				break;
			case 728: // Custom Mana Potion, xml: 10001
				if (Config.L2JMOD_ENABLE_MANA_POTIONS_SUPPORT)
					usePotion(activeChar, 10001, 1);
				else
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
				break;
			case 727: // Healing_potion, xml: 2032
			case 1061:
				if (!isEffectReplaceable(playable, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				res = usePotion(playable, 2032, 1);
				break;
			case 1060: // Lesser Healing Potion
			case 1073: // Beginner's Potion, xml: 2031
				if (!isEffectReplaceable(activeChar, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				res = usePotion(playable, 2031, 1);
				break;
			// Control of this needs to be moved back into potions.java so proper message support can be handled for specific events.
			case 10409: // Empty Bottle of Souls
				// system message should be handled in the xml
				if (activeChar.getSouls() >= SkillTable.getInstance().getInfo(2498, 1).getSoulConsumeCount())
					res = usePotion(activeChar, 2498, 1);
				else
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
					sm.addSkillName(2498);
					playable.sendPacket(sm);
				}
				break;
			case 10410: // 5 Souls Bottle
			case 10411:
				res = usePotion(activeChar, 2499, 1);
				break;
			case 20393: // Sweet Fruit Cocktail  
				res = usePotion(playable, 22056, 1);  
				usePotion(playable, 22057, 1);  
				usePotion(playable, 22058, 1);  
				usePotion(playable, 22059, 1);  
				usePotion(playable, 22060, 1);  
				usePotion(playable, 22061, 1);  
				usePotion(playable, 22064, 1);  
				usePotion(playable, 22065, 1);  
				break;  
			case 20394: // Fresh Fruit Cocktail  
				res = usePotion(playable, 22062, 1);  
				usePotion(playable, 22063, 1);  
				usePotion(playable, 22065, 1);  
				usePotion(playable, 22066, 1);  
				usePotion(playable, 22067, 1);  
				usePotion(playable, 22068, 1);  
				usePotion(playable, 22069, 1);  
				usePotion(playable, 22070, 1);  
				break;  
			case 4416:
			case 7061:
				res = usePotion(playable, 2073, 1);
				break;
			case 8515:
			case 8516:
			case 8517:
			case 8518:
			case 8519:
			case 8520:
				res = usePotion(playable, 5041, 1);
				break;
			case 10143:  
				res = usePotion(playable, 2379, 1);  
				usePotion(playable, 2380, 1);  
				usePotion(playable, 2381, 1);  
				usePotion(playable, 2382, 1);  
				usePotion(playable, 2383, 1);  
				break;  
			case 10144:  
				res = usePotion(playable, 2379, 1);  
				usePotion(playable, 2380, 1);  
				usePotion(playable, 2381, 1);  
				usePotion(playable, 2384, 1);  
				usePotion(playable, 2385, 1);  
				break;  
			case 10145:  
				res = usePotion(playable, 2379, 1);  
				usePotion(playable, 2380, 1);  
				usePotion(playable, 2381, 1);  
				usePotion(playable, 2384, 1);  
				usePotion(playable, 2386, 1);  
				break;  
			case 10146:  
				res = usePotion(playable, 2379, 1);  
				usePotion(playable, 2387, 1);  
				usePotion(playable, 2381, 1);  
				usePotion(playable, 2388, 1);  
				usePotion(playable, 2383, 1);  
				break;  
			case 10147:  
				res = usePotion(playable, 2379, 1);  
				usePotion(playable, 2387, 1);  
				usePotion(playable, 2381, 1);  
				usePotion(playable, 2383, 1);  
				usePotion(playable, 2389, 1);  
				break;  
			case 10148:  
				res = usePotion(playable, 2390, 1);  
				usePotion(playable, 2391, 1);  
				break;  	
			default:
		}

		if (res)
			playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
	}

	/**
	 * @param activeChar
	 * @param effectType
	 * @param itemId
	 * @return
	 */
	private boolean isEffectReplaceable(L2Playable playable, L2EffectType effectType, int itemId)
	{
		L2Effect e = playable.getFirstEffect(effectType);
		if (e == null)
			return true;

		// One can reuse pots after 2/3 of their duration is over.
		// It would be faster to check if its > 10 but that would screw custom pot durations...
		if (e.getTaskTime() > (e.getSkill().getBuffDuration() * 67) / 100000)
			return true;

		SystemMessage sm = new SystemMessage(SystemMessageId.S1_PREPARED_FOR_REUSE);
		sm.addItemName(itemId);
		playable.sendPacket(sm);
		return false;
	}
	
	/**
	 * 
	 * @param activeChar
	 * @param magicId
	 * @param level
	 * @return
	 */
	public boolean usePotion(L2Playable activeChar, int magicId, int level)
	{

		L2Skill skill = SkillTable.getInstance().getInfo(magicId, level);

		if (skill != null)
		{
			if (!skill.checkCondition(activeChar, activeChar, false))
				return false;
			// Return false if potion is in reuse so it is not destroyed from inventory
			if (activeChar.isSkillDisabled(skill.getId()))
			{
				displayReuse(activeChar, skill);
				return false;
			}
			if (skill.isPotion())
				activeChar.doSimultaneousCast(skill);
			else
				activeChar.doCast(skill);

			if (activeChar instanceof L2PcInstance)
			{
				L2PcInstance player = (L2PcInstance)activeChar;
				// Only for Heal potions
				if (magicId == 2031 || magicId == 2032 || magicId == 2037)
					player.shortBuffStatusUpdate(magicId, level, 15);

				if (!(player.isSitting() && !skill.isPotion()))
					return true;
			}
			else if (activeChar instanceof L2PetInstance)
			{
				SystemMessage sm = new SystemMessage(SystemMessageId.PET_USES_S1);
				sm.addString(skill.getName());
				((L2PetInstance)(activeChar)).getOwner().sendPacket(sm);
				return true;
			}
		}
		return false;
	}

	private final void displayReuse(L2Playable activeChar, L2Skill skill)
	{
		final L2PcInstance player = activeChar.getActingPlayer();
		if (player == null)
			return;

		final FastMap<Integer, TimeStamp> timeStamp = player.getReuseTimeStamp();
		SystemMessage sm = null;

		if (timeStamp != null && timeStamp.containsKey(skill.getId()))
		{
			final int remainingTime = (int)(player.getReuseTimeStamp().get(skill.getId()).getRemaining()/1000);
			final int hours = remainingTime/3600;
			final int minutes = (remainingTime%3600)/60;
			final int seconds = (remainingTime%60);
			if (hours > 0)
			{
				sm = new SystemMessage(SystemMessageId.S2_HOURS_S3_MINUTES_S4_SECONDS_REMAINING_FOR_REUSE_S1);
				sm.addSkillName(skill);
				sm.addNumber(hours);
				sm.addNumber(minutes);
			}
			else if (minutes > 0)
			{
				sm = new SystemMessage(SystemMessageId.S2_MINUTES_S3_SECONDS_REMAINING_FOR_REUSE_S1);
				sm.addSkillName(skill);
				sm.addNumber(minutes);
			}
			else
			{
				sm = new SystemMessage(SystemMessageId.S2_SECONDS_REMAINING_FOR_REUSE_S1);
				sm.addSkillName(skill);
			}
			sm.addNumber(seconds);
		}
		else
		{
			sm = new SystemMessage(SystemMessageId.S1_PREPARED_FOR_REUSE);
			sm.addSkillName(skill);
		}
		player.sendPacket(sm);
	}
}
