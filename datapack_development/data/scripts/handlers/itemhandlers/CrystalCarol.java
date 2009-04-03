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

import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

/**
 * This class ...
 *
 * @version $Revision: 1.2.4.4 $ $Date: 2005/03/27 15:30:07 $
 */

public class CrystalCarol implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		5562, 5563, 5564, 5565, 5566, 5583, 5584,
		5585, 5586, 5587, 4411, 4412, 4413, 4414,
		4415, 4416, 4417, 5010, 6903, 7061, 7062,
		8555
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
		int itemId = item.getItemId();
		
		switch (itemId)
		{
			case 5562:
			{ //crystal_carol_01
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2140, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5563:
			{ //crystal_carol_02
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2141, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5564:
			{ //crystal_carol_03
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2142, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5565:
			{ //crystal_carol_04
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2143, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5566:
			{ //crystal_carol_05
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2144, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5583:
			{ //crystal_carol_06
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2145, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5584:
			{ //crystal_carol_07
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2146, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5585:
			{ //crystal_carol_08
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2147, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5586:
			{ //crystal_carol_09
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2148, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5587:
			{ //crystal_carol_10
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2149, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4411:
			{ //crystal_journey
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2069, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4412:
			{ //crystal_battle
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2068, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4413:
			{ //crystal_love
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2070, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4414:
			{ //crystal_solitude
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2072, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4415:
			{ //crystal_festival
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2071, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4416:
			{ //crystal_celebration
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2073, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 4417:
			{ //crystal_comedy
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2067, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 5010:
			{ //crystal_victory
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2066, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 6903:
			{ //music_box_m
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2187, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 7061:
			{ //crystal_birthday
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2073, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 7062:
			{ //crystal_wedding
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2230, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
			case 8555:
			{ //VVKorea
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, 2272, 1, 1, 0);
				activeChar.broadcastPacket(MSU);
				break;
			}
		}
		activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
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
