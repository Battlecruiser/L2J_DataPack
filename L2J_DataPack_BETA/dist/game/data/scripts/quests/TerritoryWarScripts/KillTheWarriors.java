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
		CLASS_IDS = new int[] { 23, 101, 36, 108, 8, 93, 2, 88, 3, 89, 48, 114, 46, 113, 55, 117, 9, 92, 24, 102, 37, 109, 34, 107, 21, 100, 127, 131, 128, 132, 129, 133, 130, 134, 135, 136 };
		qn = qn1;
		RANDOM_MIN = 15;
		RANDOM_MAX = 20;
		npcString = new NpcStringId[]{NpcStringId.YOU_HAVE_DEFEATED_S2_OF_S1_WARRIORS_AND_ROGUES,NpcStringId.YOU_WEAKENED_THE_ENEMYS_ATTACK};
	}
}
