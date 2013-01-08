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
package hellbound.Shadai;

import com.l2jserver.gameserver.GameTimeController;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @author GKR
 */
public class Shadai extends Quest
{
	private static final int SHADAI = 32347;
	
	private static final int[] DAY_COORDS =
	{
		16882,
		238952,
		9776
	};
	private static final int[] NIGHT_COORDS =
	{
		9064,
		253037,
		-1928
	};
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (!npc.isTeleporting())
		{
			ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new ValidatePosition(npc), 60000, 60000);
		}
		
		return super.onSpawn(npc);
	}
	
	protected static void validatePosition(L2Npc npc)
	{
		int[] coords = DAY_COORDS;
		boolean mustRevalidate = false;
		if ((npc.getX() != NIGHT_COORDS[0]) && GameTimeController.getInstance().isNowNight())
		{
			coords = NIGHT_COORDS;
			mustRevalidate = true;
		}
		else if ((npc.getX() != DAY_COORDS[0]) && !GameTimeController.getInstance().isNowNight())
		{
			mustRevalidate = true;
		}
		
		if (mustRevalidate)
		{
			npc.getSpawn().setLocx(coords[0]);
			npc.getSpawn().setLocy(coords[1]);
			npc.getSpawn().setLocz(coords[2]);
			npc.teleToLocation(coords[0], coords[1], coords[2]);
		}
	}
	
	private static class ValidatePosition implements Runnable
	{
		private final L2Npc _npc;
		
		public ValidatePosition(L2Npc npc)
		{
			_npc = npc;
		}
		
		@Override
		public void run()
		{
			validatePosition(_npc);
		}
	}
	
	public Shadai(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addSpawnId(SHADAI);
	}
	
	public static void main(String[] args)
	{
		new Shadai(-1, "Shadai", "hellbound");
	}
}
