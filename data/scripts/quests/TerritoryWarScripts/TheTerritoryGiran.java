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

public class TheTerritoryGiran extends TerritoryWarSuperClass
{
	public static String qn1 = "719_FortheSakeoftheTerritoryGiran";
	public static int qnu = 719;
	public static String qna = "For the Sake of the Territory - Giran";

	public TheTerritoryGiran()
	{
		super(qnu,qn1,qna);
		CATAPULT_ID = 36501;
		TERRITORY_ID = 83;
		LEADER_IDS = new int[]{36520, 36522, 36525, 36593};
		GUARD_IDS = new int[]{36521, 36523, 36524};
		qn = qn1;
		Text = new String[]{"The catapult of Giran has been destroyed!"};
		registerKillIds();
	}
}
