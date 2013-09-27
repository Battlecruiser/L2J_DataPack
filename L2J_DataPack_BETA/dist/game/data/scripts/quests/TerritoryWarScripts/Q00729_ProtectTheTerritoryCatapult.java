/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.TerritoryWarScripts;

/**
 * Protect the Territory Catapult! (729)
 * @author Gigiikun
 */
public final class Q00729_ProtectTheTerritoryCatapult extends TerritoryWarSuperClass
{
	public Q00729_ProtectTheTerritoryCatapult()
	{
		super(729, Q00729_ProtectTheTerritoryCatapult.class.getSimpleName(), "Protect the Territory Catapult");
		NPC_IDS = new int[]
		{
			36499,
			36500,
			36501,
			36502,
			36503,
			36504,
			36505,
			36506,
			36507
		};
		addAttackId(NPC_IDS);
	}
	
	@Override
	public int getTerritoryIdForThisNPCId(int npcId)
	{
		return npcId - 36418;
	}
}
