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

import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SummonInstance;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.skills.L2EffectType;

/**
 * This class ...
 *
 * @version $Revision: 1.2.4.4 $ $Date: 2005/03/27 15:30:07 $
 */

public class Potions implements IItemHandler
{
	protected static final Logger _log = Logger.getLogger(Potions.class.getName());
	
	private static final int[] ITEM_IDS =
	{
		65, 725, 726, 727, 728, 734, 735, 1060, 1061, 1073,
		1374, 1375, 1539, 1540, 5591, 5592, 6035, 6036,
		6652, 6653, 6654, 6655, 8193, 8194, 8195, 8196,
		8197, 8198, 8199, 8200, 8201, 8202,
		10155, 13032, 10157,
		//Attribute Potion
		9997, 9998, 9999, 10000, 10001, 10002,
		// Endeavor Potion
		733,
		//bottls of souls
		10409,10410,10411,10412,
		20393,20394
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
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
		
		if (!TvTEvent.onPotionUse(playable.getObjectId()))
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		
		if (playable.isAllSkillsDisabled())
		{
			ActionFailed af = ActionFailed.STATIC_PACKET;
			activeChar.sendPacket(af);
			return;
		}
		
		int itemId = item.getItemId();
		
		switch (itemId)
		{
			// HEALING AND SPEED POTIONS
			case 65: // red_potion, xml: 2001
				usePotion(playable, 2001, 1);
				break;
			case 725: // healing_drug, xml: 2002
				if (!isEffectReplaceable(playable, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				usePotion(playable, 2002, 1);
				break;
			case 726: // custom mana drug, xml: 9007
				if (Config.L2JMOD_ENABLE_MANA_POTIONS_SUPPORT)
					usePotion(activeChar, 9007, 1);
				break;
			case 727: // _healing_potion, xml: 2032
			case 1061:
				if (!isEffectReplaceable(playable, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				res = usePotion(playable, 2032, 1);
				break;
			case 728: // custom mana potion, xml: 9008
				if (Config.L2JMOD_ENABLE_MANA_POTIONS_SUPPORT)
					usePotion(activeChar, 9008, 1);
				break;
			case 733: // Endeavor Potion, xml: 2010
				usePotion(playable, 2010, 1);
				break;
			case 734: // quick_step_potion, xml: 2011
				usePotion(playable, 2011, 1);
				break;
			case 735: // swift_attack_potion, xml: 2012
				usePotion(playable, 2012, 1);
				break;
			case 1060: // lesser_healing_potion,
			case 1073: // beginner's potion, xml: 2031
				if (!isEffectReplaceable(activeChar, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				res = usePotion(playable, 2031, 1);
				break;
			case 10157: // instant haste_potion, xml: 2398
				usePotion(playable, 2398, 1);
				break;
			case 1374: // adv_quick_step_potion, xml: 2034
				usePotion(playable, 2034, 1);
				break;
			case 1375: // adv_swift_attack_potion, xml: 2035
				usePotion(playable, 2035, 1);
				break;
			case 1539: // greater_healing_potion, xml: 2037
				if (!isEffectReplaceable(playable, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				usePotion(playable, 2037, 1);
				break;
			case 1540: // quick_healing_potion, xml: 2038
				if (!isEffectReplaceable(playable, L2EffectType.HEAL_OVER_TIME, itemId))
					return;
				usePotion(playable, 2038, 1);
				break;
			case 5591:
			case 5592: // CP and Greater CP
				if (!isEffectReplaceable(playable, L2EffectType.COMBAT_POINT_HEAL_OVER_TIME, itemId))
					return;
				usePotion(playable, 2166, (itemId == 5591) ? 1 : 2);
				break;
			case 6035: // Magic Haste Potion, xml: 2169
				usePotion(playable, 2169, 1);
				break;
			case 6036: // Greater Magic Haste Potion, xml: 2169
				usePotion(playable, 2169, 2);
				break;
			case 10155: //Mental Potion XML:2396
				usePotion(playable, 2396, 1);
				break;
			case 13032: //Pailaka Instant Shield XML:2577
				usePotion(playable, 2577, 1);
				break;
			
			case 10409: // Empty Bottle of Souls
				usePotion(activeChar, 2498, 1);
				break;
			case 10410: // 5 Souls Bottle
				if (activeChar.getActiveClass() >= 123 && activeChar.getActiveClass() <= 136) //Kamael classes only
					res = usePotion(activeChar, 2499, 1);
				else
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
				break;
			case 10411: // 5 Souls Bottle Combat
				if (activeChar.getActiveClass() >= 123 && activeChar.getActiveClass() <= 136 && activeChar.isInsideZone(L2Character.ZONE_SIEGE)) //Kamael classes only  				{
					res = usePotion(activeChar, 2499, 1);
				else
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
				break;
			case 10412: // 10 Souls Bottle
				if (activeChar.getActiveClass() >= 123 && activeChar.getActiveClass() <= 136) //Kamael classes only  				{
					res = usePotion(activeChar, 2499, 2);
				else
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
				break;
				
			// ATTRIBUTE POTION
			case 9997: // Fire Resist Potion, xml: 2335
				usePotion(playable, 2335, 1);
				break;
			case 9998: // Water Resist Potion, xml: 2336
				usePotion(playable, 2336, 1);
				break;
			case 9999: // Earth Resist Potion, xml: 2338
				usePotion(playable, 2338, 1);
				break;
			case 10000: // Wind Resist Potion, xml: 2337
				usePotion(playable, 2337, 1);
				break;
			case 10001: // Dark Resist Potion, xml: 2340
				usePotion(playable, 2340, 1);
				break;
			case 10002: // Divine Resist Potion, xml: 2339
				usePotion(playable, 2339, 1);
				break;
				// VALAKAS AMULETS
			case 6652: // Amulet Protection of Valakas
				usePotion(playable, 2231, 1);
				break;
			case 6653: // Amulet Flames of Valakas
				usePotion(playable, 2233, 1);
				break;
			case 6654: // Amulet Flames of Valakas
				usePotion(playable, 2233, 1);
				break;
			case 6655: // Amulet Slay Valakas
				usePotion(playable, 2232, 1);
				break;
			
			// FISHERMAN POTIONS
			case 8193: // Fisherman's Potion - Green
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 3)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 1);
				break;
			case 8194: // Fisherman's Potion - Jade
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 6)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 2);
				break;
			case 8195: // Fisherman's Potion - Blue
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 9)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 3);
				break;
			case 8196: // Fisherman's Potion - Yellow
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 12)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 4);
				break;
			case 8197: // Fisherman's Potion - Orange
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 15)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 5);
				break;
			case 8198: // Fisherman's Potion - Purple
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 18)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 6);
				break;
			case 8199: // Fisherman's Potion - Red
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 21)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 7);
				break;
			case 8200: // Fisherman's Potion - White
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				if (activeChar.getSkillLevel(1315) <= 24)
				{
					playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
					playable.sendPacket(new SystemMessage(SystemMessageId.NOTHING_HAPPENED));
					return;
				}
				usePotion(playable, 2274, 8);
				break;
			case 8201: // Fisherman's Potion - Black
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				usePotion(playable, 2274, 9);
				break;
			case 8202: // Fishing Potion
				if (!(playable instanceof L2PcInstance))
				{
					itemNotForPets(activeChar);
					return;
				}
				usePotion(playable, 2275, 1);
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
			default:
		}
		
		if (res)
			playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
	}
	
	/**
	 * 
	 * @param activeChar
	 * @param effectType
	 * @param itemId
	 * @return
	 */
	private boolean isEffectReplaceable(L2Playable playable, Enum<L2EffectType> effectType, int itemId)
	{
		L2Effect[] effects = playable.getAllEffects();
		L2PcInstance activeChar = (L2PcInstance) ((playable instanceof L2PcInstance) ? playable : ((L2Summon) playable).getOwner());
		if (effects == null)
			return true;
		
		for (L2Effect e : effects)
		{
			if (e.getEffectType() == effectType)
			{
				// One can reuse pots after 2/3 of their duration is over.
				// It would be faster to check if its > 10 but that would screw custom pot durations...
				if (e.getTaskTime() > (e.getSkill().getBuffDuration() * 67) / 100000)
					return true;
				SystemMessage sm = new SystemMessage(SystemMessageId.S1_PREPARED_FOR_REUSE);
				sm.addItemName(itemId);
				activeChar.sendPacket(sm);
				return false;
			}
		}
		return true;
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
			// Return false if potion is in reuse
			// so is not destroyed from inventory
			if (activeChar.isSkillDisabled(skill.getId()))
			{
				SystemMessage sm = new SystemMessage(SystemMessageId.S1_PREPARED_FOR_REUSE);
				sm.addSkillName(skill);
				activeChar.sendPacket(sm);
				
				return false;
			}
			
			activeChar.doSimultaneousCast(skill);
				
			if (activeChar instanceof L2PcInstance)
			{
				L2PcInstance player = (L2PcInstance)activeChar;
				//only for Heal potions
				if (magicId == 2031 || magicId == 2032 || magicId == 2037)
				{
					player.shortBuffStatusUpdate(magicId, level, 15);
				}
				// Summons should be affected by herbs too, self time effect is handled at L2Effect constructor 
				else if (((magicId > 2277 && magicId < 2286) || (magicId >= 2512 && magicId <= 2514))
					&& (player.getPet() != null && player.getPet() instanceof L2SummonInstance))
				{
					player.getPet().doSimultaneousCast(skill);
				}
				
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
	
	private void itemNotForPets(L2PcInstance activeChar)
	{
		activeChar.sendPacket(new SystemMessage(SystemMessageId.ITEM_NOT_FOR_PETS));
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}
