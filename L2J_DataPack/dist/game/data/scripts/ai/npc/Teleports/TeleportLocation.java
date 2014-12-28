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

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * @author UnAfraid
 */
public class TeleportLocation extends Location
{
	private final int _id;
	private final String _name;
	private final NpcStringId _npcStringId;
	private final int _feeId;
	private final long _feeCount;
	
	public TeleportLocation(int id, StatsSet set)
	{
		super(set);
		_id = id;
		_name = set.getString("name", null);
		_npcStringId = NpcStringId.getNpcStringIdOrDefault(set.getInt("npcStringId", -1), null);
		_feeId = set.getInt("feeId", Inventory.ADENA_ID);
		_feeCount = set.getLong("feeCount", 0);
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public NpcStringId getNpcStringId()
	{
		return _npcStringId;
	}
	
	public int getFeeId()
	{
		return _feeId;
	}
	
	public long getFeeCount()
	{
		return _feeCount;
	}
}