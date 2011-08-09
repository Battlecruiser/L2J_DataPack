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

public class TheTerritoryDion extends TerritoryWarSuperClass
{
	public static String qn1 = "718_FortheSakeoftheTerritoryDion";
	public static int qnu = 718;
	public static String qna = "For the Sake of the Territory - Dion";
	
	public TheTerritoryDion()
	{
		super(qnu,qn1,qna);
		CATAPULT_ID = 36500;
		TERRITORY_ID = 82;
		LEADER_IDS = new int[]{36514, 36516, 36519, 36592};
		GUARD_IDS = new int[]{36515, 36517, 36518};
		qn = qn1;
		npcString = new int[]{72962};
		registerKillIds();
	}
}
