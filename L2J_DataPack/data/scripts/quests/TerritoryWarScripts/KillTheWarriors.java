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

public class KillTheWarriors extends TerritoryWarSuperClass
{
	public static String qn1 = "735_Makespearsdull";
	public static int qnu = 735;
	public static String qna = "Make spears dull";

	public KillTheWarriors()
	{
		super(qnu,qn1,qna);
		CLASS_IDS = new int[]{2, 3, 8, 9, 23, 24, 36, 37, 46, 48, 55, 88, 89, 92, 93, 101, 102, 108, 109, 113, 114, 117, 127, 128, 129, 130, 131, 132, 133, 134};
		qn = qn1;
		RANDOM_MIN = 15;
		RANDOM_MAX = 20;
		Text = new String[]{"Out of MAX Warriors and Rogues you have defeated KILL.","You weakened the enemy's attack!"};
	}
}
