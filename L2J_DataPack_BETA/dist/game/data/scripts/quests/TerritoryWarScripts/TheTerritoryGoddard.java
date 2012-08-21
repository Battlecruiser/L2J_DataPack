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

import com.l2jserver.gameserver.network.NpcStringId;

/**
 * For the Sake of the Territory - Goddard (723)
 * @author Gigiikun
 */
public class TheTerritoryGoddard extends TerritoryWarSuperClass
{
	public static String qn1 = "723_FortheSakeoftheTerritoryGoddard";
	public static int qnu = 723;
	public static String qna = "For the Sake of the Territory - Goddard";
	
	public TheTerritoryGoddard()
	{
		super(qnu, qn1, qna);
		CATAPULT_ID = 36505;
		TERRITORY_ID = 87;
		LEADER_IDS = new int[]
		{
			36544,
			36546,
			36549,
			36597
		};
		GUARD_IDS = new int[]
		{
			36545,
			36547,
			36548
		};
		qn = qn1;
		npcString = new NpcStringId[]
		{
			NpcStringId.THE_CATAPULT_OF_GODDARD_HAS_BEEN_DESTROYED
		};
		registerKillIds();
	}
}
