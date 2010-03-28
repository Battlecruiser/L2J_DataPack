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

public class ProtectTheEconomic extends TerritoryWarSuperClass
{
	public static String qn1 = "733_ProtecttheEconomicAssociationLeader";
	public static int qnu = 733;
	public static String qna = "Protect the Economic Association Leader";

	public ProtectTheEconomic()
	{
		super(qnu,qn1,qna);
		NPC_IDS = new int[]{36513,36519,36525,36531,36537,36543,36549,36555,36561};
		qn = qn1;
		registerAttackIds();
	}
	
	@Override
	public int getTerritoryIdForThisNPCId(int npcid)
	{
		return 81 + (npcid - 36513) / 6;
	}
}
