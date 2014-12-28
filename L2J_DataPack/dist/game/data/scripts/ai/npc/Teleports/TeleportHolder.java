/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package ai.npc.Teleports;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author UnAfraid
 */
public class TeleportHolder
{
	private final int _npcId;
	private final Map<Integer, TeleportLocation> _teleportLocations = new TreeMap<>();
	
	public TeleportHolder(int id)
	{
		_npcId = id;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public void addLocation(TeleportLocation loc)
	{
		_teleportLocations.put(loc.getId(), loc);
	}
	
	public TeleportLocation getLocation(int index)
	{
		return _teleportLocations.get(index);
	}
	
	public Collection<TeleportLocation> getLocations()
	{
		return _teleportLocations.values();
	}
}