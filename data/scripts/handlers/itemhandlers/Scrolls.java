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

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
* This class ...
*
* @version $Revision: 1.1.6.4 $ $Date: 2005/04/06 18:25:18 $
*/

public class Scrolls implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		3926, 3927, 3928, 3929, 3930, 3931,
		3932, 3933, 3934, 3935, 4218, 5593,
		5594, 5595, 6037, 5703, 5803, 5804,
		5805, 5806, 5807, 8515, 8516, 8517,
		8518, 8519, 8520, 8594, 8595, 8596,
		8597, 8598, 8599, 8954, 8955, 8956,
		9146, 9147, 9148, 9149, 9150, 9151,
		9152, 9153, 9154, 9155, 9897, 10131,
		10132, 10133, 10134, 10135, 10136,
		10137, 10138, 10151, 10274
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		L2PcInstance activeChar;
		if (playable instanceof L2PcInstance)
			activeChar = (L2PcInstance) playable;
		else if (playable instanceof L2PetInstance)
			activeChar = ((L2PetInstance) playable).getOwner();
		else
			return;
		
		if (activeChar.isAllSkillsDisabled() || activeChar.isCastingNow())
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		
		if (!TvTEvent.onScrollUse(playable.getObjectId()))
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		int itemId = item.getItemId();
		
		if (itemId >= 8594 && itemId <= 8599) //Scrolls of recovery XML: 2286
		{
			if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
				return;
			activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2286, 1, 1, 0));
			activeChar.reduceDeathPenaltyBuffLevel();
			useScroll(activeChar, 2286, itemId - 8593);
			return;
		}
		else if (itemId == 5703 || itemId >= 5803 && itemId <= 5807)
		{
			byte expIndex = (byte) activeChar.getExpertiseIndex();
			if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
				return;
			activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2168, (expIndex > 5 ? expIndex : expIndex + 1), 1, 0));
			useScroll(activeChar, 2168, (expIndex > 5 ? expIndex : expIndex + 1));
			activeChar.setCharmOfLuck(true);
			return;
		}
		else if (itemId >= 8515 && itemId <= 8520) // Charm of Courage XML: 5041
		{
			if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
				return;
			activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 5041, 1, 1, 0));
			useScroll(activeChar, 5041, 1);
			return;
		}
		else if (itemId == 9897 || itemId >= 10131 && itemId <= 10138 || itemId == 10151 || itemId == 10274) //transformation scrolls
		{
			if (activeChar.isMounted() || activeChar.getPet() != null || activeChar.isTransformed())
			{
				activeChar.sendPacket(new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED).addItemName(item));
				return;
			}
		}
		else if (itemId >= 8954 && itemId <= 8956)
		{
			if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
				return;
			switch (itemId)
			{
				case 8954: // Blue Primeval Crystal XML: 2306
					activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2306, 1, 1, 0));
					useScroll(activeChar, 2306, 1);
					break;
				case 8955: // Green Primeval Crystal XML: 2306
					activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2306, 2, 1, 0));
					useScroll(activeChar, 2306, 2);
					break;
				case 8956: // Red Primeval Crystal XML: 2306
					activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2306, 3, 1, 0));
					useScroll(activeChar, 2306, 3);
					break;
				
				default:
					break;
			}
			return;
		}
		
		// for the rest, there are no extra conditions
		if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
			return;
		
		switch (itemId)
		{
			case 3926: // Scroll of Guidance XML:2050
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2050, 1, 1, 0));
				useScroll(activeChar, 2050, 1);
				break;
			case 3927: // Scroll of Death Whisper XML:2051
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2051, 1, 1, 0));
				useScroll(activeChar, 2051, 1);
				break;
			case 3928: // Scroll of Focus XML:2052
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2052, 1, 1, 0));
				useScroll(activeChar, 2052, 1);
				break;
			case 3929: // Scroll of Greater Acumen XML:2053
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2053, 1, 1, 0));
				useScroll(activeChar, 2053, 1);
				break;
			case 3930: // Scroll of Haste XML:2054
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2054, 1, 1, 0));
				useScroll(activeChar, 2054, 1);
				break;
			case 3931: // Scroll of Agility XML:2055
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2055, 1, 1, 0));
				useScroll(activeChar, 2055, 1);
				break;
			case 3932: // Scroll of Mystic Empower XML:2056
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2056, 1, 1, 0));
				useScroll(activeChar, 2056, 1);
				break;
			case 3933: // Scroll of Might XML:2057
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2057, 1, 1, 0));
				useScroll(activeChar, 2057, 1);
				break;
			case 3934: // Scroll of Wind Walk XML:2058
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2058, 1, 1, 0));
				useScroll(activeChar, 2058, 1);
				break;
			case 3935: // Scroll of Shield XML:2059
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2059, 1, 1, 0));
				useScroll(activeChar, 2059, 1);
				break;
			case 4218: // Scroll of Mana Regeneration XML:2064
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2064, 1, 1, 0));
				useScroll(activeChar, 2064, 1);
				break;
			case 5593: // SP Scroll Low Grade XML:2167
				activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2167, 1, 1, 0));
				useScroll(activeChar, 2167, 1);
				break;
			case 5594: // SP Scroll Medium Grade XML:2167
				activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2167, 2, 1, 0));
				useScroll(activeChar, 2167, 2);
				break;
			case 5595: // SP Scroll High Grade XML:2167
				activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2167, 3, 1, 0));
				useScroll(activeChar, 2167, 3);
				break;
			case 6037: // Scroll of Waking XML:2170
				activeChar.broadcastPacket(new MagicSkillUse(playable, playable, 2170, 1, 1, 0));
				useScroll(activeChar, 2170, 1);
				break;
			case 9146: // Scroll of Guidance - For Event XML:2050
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2050, 1, 1, 0));
				useScroll(activeChar, 2050, 1);
				break;
			case 9147: // Scroll of Death Whisper - For Event XML:2051
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2051, 1, 1, 0));
				useScroll(activeChar, 2051, 1);
				break;
			case 9148: // Scroll of Focus - For Event XML:2052
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2052, 1, 1, 0));
				useScroll(activeChar, 2052, 1);
				break;
			case 9149: // Scroll of Acumen - For Event XML:2053
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2053, 1, 1, 0));
				useScroll(activeChar, 2053, 1);
				break;
			case 9150: // Scroll of Haste - For Event XML:2054
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2054, 1, 1, 0));
				useScroll(activeChar, 2054, 1);
				break;
			case 9151: // Scroll of Agility - For Event XML:2055
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2055, 1, 1, 0));
				useScroll(activeChar, 2055, 1);
				break;
			case 9152: // Scroll of Empower - For Event XML:2056
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2056, 1, 1, 0));
				useScroll(activeChar, 2056, 1);
				break;
			case 9153: // Scroll of Might - For Event XML:2057
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2057, 1, 1, 0));
				useScroll(activeChar, 2057, 1);
				break;
			case 9154: // Scroll of Wind Walk - For Event XML:2058
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2058, 1, 1, 0));
				useScroll(activeChar, 2058, 1);
				break;
			case 9155: // Scroll of Shield - For Event XML:2059
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2059, 1, 1, 0));
				useScroll(activeChar, 2059, 1);
				break;
			case 9897:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2370, 1, 1, 0));
				useScroll(activeChar, 2370, 1);
				break;
			case 9648:
			case 10131:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2371, 1, 1, 0));
				useScroll(activeChar, 2371, 1);
				break;
			case 9649:
			case 10132:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2372, 1, 1, 0));
				useScroll(activeChar, 2372, 1);
				break;
			case 9650:
			case 10133:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2373, 1, 1, 0));
				useScroll(activeChar, 2373, 1);
				break;
			case 9651:
			case 10134:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2374, 1, 1, 0));
				useScroll(activeChar, 2374, 1);
				break;
			case 9652:
			case 10135:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2375, 1, 1, 0));
				useScroll(activeChar, 2375, 1);
				break;
			case 9653:
			case 10136:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2376, 1, 1, 0));
				useScroll(activeChar, 2376, 1);
				break;
			case 9654:
			case 10137:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2377, 1, 1, 0));
				useScroll(activeChar, 2377, 1);
				break;
			case 9655:
			case 10138:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2378, 1, 1, 0));
				useScroll(activeChar, 2378, 1);
				break;
			case 10151:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2394, 1, 1, 0));
				useScroll(activeChar, 2394, 1);
				break;
			case 10274:
				activeChar.broadcastPacket(new MagicSkillUse(playable, activeChar, 2428, 1, 1, 0));
				useScroll(activeChar, 2428, 1);
				break;
			default:
				break;
		}
	}
	
	/**
	 * 
	 * @param activeChar
	 * @param magicId
	 * @param level
	 */
	public void useScroll(L2PcInstance activeChar, int magicId, int level)
	{
		L2Skill skill = SkillTable.getInstance().getInfo(magicId, level);
		if (skill != null)
			activeChar.useMagic(skill, true, false);
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
