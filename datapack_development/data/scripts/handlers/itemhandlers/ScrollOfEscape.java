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
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.instancemanager.ClanHallManager;
import net.sf.l2j.gameserver.instancemanager.FortManager;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
 * This class ...
 *
 * @version $Revision: 1.2.2.3.2.5 $ $Date: 2005/03/27 15:30:07 $
 */

public class ScrollOfEscape implements IItemHandler
{
	// all the items IDs that this handler knows
	private static final int[] ITEM_IDS =
	{
		 736, 1538, 1829, 1830, 3958, 5858,
		5859, 7117, 7118, 7119,
		7120, 7121, 7122, 7123, 7124, 7125,
		7126, 7127,	7128, 7129, 7130, 7131,
		7132, 7133,	7134, 7135, 7554, 7555,
		7556, 7557,	7558, 7559, 7618, 7619,
		9156, 9647, 9716, 10129, 10130, 10650,
		10149, 13129, 13258, 13395, 13396, 13397,
		13398, 13399, 13400, 13401, 13402, 13403, 
		13404, 13405, 13406, 13407, 13408, 13409,
		13410, 13411, 13412, 13413, 13414, 13731,
		13732, 13733, 13734, 13735, 13736, 13737,
		13738, 13739
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		
		// Thanks nbd
		if (!TvTEvent.onEscapeUse(activeChar.getObjectId()))
			
			return;
		
		if (GrandBossManager.getInstance().getZone(activeChar) != null && !activeChar.isGM())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.YOU_MAY_NOT_SUMMON_FROM_YOUR_CURRENT_LOCATION));
			return;
		}
		
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		
		// Check to see if the player is in a festival.
		if (activeChar.isFestivalParticipant())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.YOU_MAY_NOT_SUMMON_FROM_YOUR_CURRENT_LOCATION));
			return;
		}
		
		// Check to see if player is in jail
		if (activeChar.isInJail())
		{
			activeChar.sendMessage("You can not escape from jail.");
			return;
		}
		// Check to see if player is in a duel
		if (activeChar.isInDuel())
		{
			activeChar.sendMessage("You cannot use escape skills during a duel.");
			return;
		}
		
		// TODO: unhardcode me
		// blessed scrolls don't do anything if hideout target it is null
		boolean ret = false;
		switch(item.getItemId())
		{
			case 5859:
				if (activeChar.getClan() != null && CastleManager.getInstance().getCastleByOwner(activeChar.getClan()) == null)
					ret = true;
				break;
			case 10130:
				if (activeChar.getClan() != null && FortManager.getInstance().getFortByOwner(activeChar.getClan()) == null)
					ret = true;
				break;
			case 5858:
				if (activeChar.getClan() != null && ClanHallManager.getInstance().getClanHallByOwner(activeChar.getClan()) == null)
					ret = true;
				break;
		}		
		if (ret)
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addItemName(item);
			activeChar.sendPacket(sm);
			return;
		}
		
		int itemId = item.getItemId();
		switch (itemId)
		{
			case 736: // Scroll of Escape
				useSkill(activeChar,2013,1);
				break;
			case 1538: // Blessed Scroll of Escape
				useSkill(activeChar,2036,1);
				break;
			case 1829: // Scroll of Escape: Clan Hall
				useSkill(activeChar,2040,1);
				break;
			case 1830: // Scroll of Escape: Castle
				useSkill(activeChar,2041,1);
				break;
			case 3958: // L2Day - Blessed Escape Effect
				useSkill(activeChar,2036,2);
				break;
			case 5858: // Blessed Scroll of Escape: Clan Hall
				useSkill(activeChar,2177,1);
				break;
			case 5859: // Blessed Scroll of Escape: Castle
				useSkill(activeChar,2178,1);
				break;
			case 7117: // Scroll of Escape: Talking Island
			case 7118: // Scroll of Escape: Elven Village
			case 7119: // Scroll of Escape: Dark Elf Village
			case 7120: // Scroll of Escape: Orc Village
			case 7121: // Scroll of Escape: Dwarven Village
			case 7122: // Scroll of Escape: Gludin Village
			case 7123: // Scroll of Escape: Town of Gludio
			case 7124: // Scroll of Escape: Town of Dion
			case 7125: // Scroll of Escape: Floran
			case 7126: // Scroll of Escape: Giran Castle Town
			case 7127: // Scroll of Escape: Hardin's Private Academy
			case 7128: // Scroll of Escape: Heine
			case 7129: // Scroll of Escape: Town of Oren
			case 7130: // Scroll of Escape: Ivory Tower
			case 7131: // Scroll of Escape: Hunters Village
			case 7132: // Scroll of Escape: Aden Castle Town
			case 7133: // Scroll of Escape: Town of Goddard
			case 7134: // Scroll of Escape: Rune Township
			case 7135: // Scroll of Escape: Town of Schuttgart
				useSkill(activeChar,2213,itemId-7116);
				break;
			case 7554: // Scroll of Escape: Talking Island
			case 7555: // Scroll of Escape: Elven Village
			case 7556: // Scroll of Escape: Dark Elf Village
			case 7557: // Scroll of Escape: Orc Village
			case 7558: // Scroll of Escape: Dwarven Village
				useSkill(activeChar,2214,itemId-7553);
				break;
			case 7559: // Scroll of Escape: Giran Castle Town
				useSkill(activeChar,2214,10);
				break;
			case 7618: // Scroll of Escape: Ketra Orc Village
				useSkill(activeChar,2213,20);
				break;
			case 7619: // Scroll of Escape: Varka Silenos Village
				useSkill(activeChar,2213,21);
				break;
			case 9156: // Blessed Scroll of Escape (Event)
				useSkill(activeChar,2320,1);
				break;
			case 9647: // Scroll of Escape: Kamael Village
				useSkill(activeChar,2213,22);
				break;
			case 9716: // Scroll of Escape: Kamael Village
				useSkill(activeChar,2214,6);
				break;
			case 10129: // Scroll of Escape: Fortress
				useSkill(activeChar,2365,1);
				break;
			case 10130: // Blessed Scroll of Escape: Fortress
				useSkill(activeChar,2364,1);
				break;
			case 10149: // Battleground Blessed Scroll of Escape
				useSkill(activeChar,2392,1);
				break;
			case 10650: // Adventurer's Scroll of Escape
				useSkill(activeChar,2531,1);
				break;
			case 13129: // Pailaka Scroll of Escape
				useSkill(activeChar,2594,1);
				break;
			case 13258: // Gran Kain's Blessed Scroll of Escape
				useSkill(activeChar,2595,1);
				break;
			case 13395: // Escape - Talking Island Village
			case 13396: // Escape - Elven Village
			case 13397: // Escape - Dark Elven Village
			case 13398: // Escape - Orc Village
			case 13399: // Escape - Dwarven Village
			case 13400: // Escape - Gludin Village
			case 13401: // Escape - Town of Gludio
			case 13402: // Escape - Town of Dion
			case 13403: // Escape - Floran Village
			case 13404: // Escape - Giran Castle Town
			case 13405: // Escape - Hardin's Academy
			case 13406: // Escape - Heine
			case 13407: // Escape - Town of Oren
			case 13408: // Escape - Ivory Tower
			case 13409: // Escape - Hunters Village
			case 13410: // Escape - Town of Aden
			case 13411: // Escape - Town of Goddard
			case 13412: // Escape - Rune Township
			case 13413: // Escape - Town of Schuttgart
			case 13414: // Escape - Kamael Village
				useSkill(activeChar,2609,itemId-13394);
				break;
			case 13731: // Gludio Blessed Scroll of Escape
			case 13732: // Dion Blessed Scroll of Escape
			case 13733: // Giran Blessed Scroll of Escape
			case 13734: // Oren Blessed Scroll of Escape
			case 13735: // Aden Blessed Scroll of Escape
			case 13736: // Innadril Blessed Scroll of Escape
			case 13737: // Goddard Blessed Scroll of Escape
			case 13738: // Rune Blessed Scroll of Escape
			case 13739: // Schuttgart Blessed Scroll of Escape
				useSkill(activeChar,2649,itemId-13730);
				break;
			case 20372: // Escape - Talking Island Village
			case 20373: // Escape - Elven Village
			case 20374: // Escape - Dark Elven Village
			case 20375: // Escape - Orc Village
			case 20376: // Escape - Dwarven Village
			case 20377: // Escape - Gludin Village
			case 20378: // Escape - Town of Gludio
			case 20379: // Escape - Town of Dion
			case 20380: // Escape - Floran Village
			case 20381: // Escape - Giran Castle Town
			case 20382: // Escape - Hardin's Academy
			case 20383: // Escape - Heine
			case 20384: // Escape - Town of Oren
			case 20385: // Escape - Ivory Tower
			case 20386: // Escape - Hunters Village
			case 20387: // Escape - Town of Aden
			case 20388: // Escape - Town of Goddard
			case 20389: // Escape - Rune Township
			case 20390: // Escape - Town of Schuttgart
				//useSkill(activeChar,??,itemId-20371);
				break;
		}
	}
	
	private void useSkill(L2PcInstance player, int skillId, int skillLvl)
	{
		L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLvl);
		
		if (skill != null)
			player.useMagic(skill, false, true);
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
