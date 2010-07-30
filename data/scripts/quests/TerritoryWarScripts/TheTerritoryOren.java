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

public class TheTerritoryOren extends TerritoryWarSuperClass
{
	public static String qn1 = "720_FortheSakeoftheTerritoryOren";
	public static int qnu = 720;
	public static String qna = "For the Sake of the Territory - Oren";

	public TheTerritoryOren()
	{
		super(qnu,qn1,qna);
		CATAPULT_ID = 36502;
		TERRITORY_ID = 84;
		LEADER_IDS = new int[]{36526, 36528, 36531, 36594};
		GUARD_IDS = new int[]{36527, 36529, 36530};
		qn = qn1;
		Text = new String[]{"The catapult of Oren has been destroyed!"};
		registerKillIds();
	}
}
