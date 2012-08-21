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
 * Weaken the magic! (736)
 * @author Gigiikun
 */
public class KillTheWizards extends TerritoryWarSuperClass
{
	public static String qn1 = "736_Weakenmagic";
	public static int qnu = 736;
	public static String qna = "Weaken magic";
	
	public KillTheWizards()
	{
		super(qnu, qn1, qna);
		CLASS_IDS = new int[]
		{
			40,
			110,
			27,
			103,
			13,
			95,
			12,
			94,
			41,
			111,
			28,
			104,
			14,
			96
		};
		qn = qn1;
		RANDOM_MIN = 10;
		RANDOM_MAX = 15;
		npcString = new NpcStringId[]
		{
			NpcStringId.YOU_HAVE_DEFEATED_S2_OF_S1_ENEMIES,
			NpcStringId.YOU_WEAKENED_THE_ENEMYS_MAGIC
		};
	}
}
