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
package quests.TerritoryWarScripts;


/**
 *
 * @author Gigiikun
 */

public class TheTerritoryAden extends TerritoryWarSuperClass
{
	public static String qn1 = "721_FortheSakeoftheTerritoryAden";
	public static int qnu = 721;
	public static String qna = "For the Sake of the Territory - Aden";
	
	public TheTerritoryAden()
	{
		super(qnu,qn1,qna);
		CATAPULT_ID = 36503;
		TERRITORY_ID = 85;
		LEADER_IDS = new int[]{36532, 36534, 36537, 36595};
		GUARD_IDS = new int[]{36533, 36535, 36536};
		qn = qn1;
		npcString = new int[]{72965};
		registerKillIds();
	}
}
