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


public class ItemSkills implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		6403,6406,6407,13268,13269,
		22039,22040,22041,22042,22043,22044,22045,22046,22047,
		22048,22049,22050,22051,22052,22053,
		22089,22090,22091,22092,22093,
		22094,22095,22096,22097,22098,22099,22100,22101,22102,
		22103,22104,22105,22106,22107,22108,22109,22110,22111,
		22112,22113,22114,22115,22116,22117,22118,22119,22120,
		22121,22122,22123,
		22124,22125,22126,22127,22128,22129,22130,22131,
		22132,22133,22134,22135,22136,22137,22138,22139,22140,
		22141,22142,22143,22149,22150,22151,22152,22153
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return; // prevent Class cast exception
		L2PcInstance activeChar = (L2PcInstance) playable;
		int skillId = 0;
		int skillLvl = 1;
		int itemId = item.getItemId();
		switch (itemId)
		{
			case 6403:
				skillId = 2023;
				break;
			case 6406:
				skillId = 2024;
				break;
			case 6407:
				skillId = 2025;
				break;
			case 13268:
				skillId = 2604;
				break;
			case 13269:
				skillId = 2605;
				break;
			case 22039:
				skillId = 26031;
				break;
			case 22040:
				skillId = 26032;
				break;
			case 22041:
				skillId = 26033;
				break;
			case 22042:
				skillId = 26034;
				break;
			case 22043:
				skillId = 26035;
				break;
			case 22044:
				skillId = 26036;
				break;
			case 22045:
				skillId = 26037;
				break;
			case 22046:
				skillId = 26038;
				break;
			case 22047:
				skillId = 26039;
				break;
			case 22048:
				skillId = 26040;
				break;
			case 22049:
				skillId = 26041;
				break;
			case 22050:
				skillId = 26042;
				break;
			case 22051:
				skillId = 26043;
				break;
			case 22052:
				skillId = 26044;
				break;
			case 22053:
				skillId = 26045;
				break;
			case 22089:
			case 22090:
			case 22091:
			case 22092:
			case 22093:
				skillId = 26067;
				skillLvl = itemId-22088;
				break;
			case 22094:
			case 22095:
			case 22096:
			case 22097:
			case 22098:
				skillId = 26068;
				skillLvl = itemId-22093;
				break;
			case 22099:
			case 22100:
			case 22101:
			case 22102:
			case 22103:
				skillId = 26069;
				skillLvl = itemId-22098;
				break;
			case 22104:
			case 22105:
			case 22106:
			case 22107:
			case 22108:
				skillId = 26070;
				skillLvl = itemId-22103;
				break;
			case 22109:
			case 22110:
			case 22111:
			case 22112:
			case 22113:
				skillId = 26068;
				skillLvl = itemId-22103;
				break;
			case 22114:
			case 22115:
			case 22116:
			case 22117:
			case 22118:
				skillId = 26069;
				skillLvl = itemId-22108;
				break;
			case 22119:
			case 22120:
			case 22121:
			case 22122:
			case 22123:
				skillId = 26070;
				skillLvl = itemId-22113;
				break;
			case 22124:
			case 22125:
			case 22126:
			case 22127:
			case 22128:
			case 22129:
			case 22130:
			case 22131:
			case 22132:
			case 22133:
			case 22134:
			case 22135:
			case 22136:
			case 22137:
			case 22138:
			case 22139:
			case 22140:
				skillId = 26071;
				skillLvl = itemId-22123;
				break;
			case 22141:
			case 22142:
			case 22143:
				skillId = 26072;
				skillLvl = itemId-22140;
				break;
			case 22149:
			case 22150:
			case 22151:
			case 22152:
			case 22153:
				skillId = 26073;
				skillLvl = itemId-22148;
				break;
		}
		
		
		L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLvl);
		if (skill != null)
			activeChar.useMagic(skill, false, false);

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
