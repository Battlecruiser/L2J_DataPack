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
package mods.eventmodElpies;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2EventMonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Event;

public class eventmodElpies extends Event
{
	// Event NPC's list
	private List<L2Npc> _npclist;
	// Event Task
	ScheduledFuture<?> _eventTask = null;
	// Event time
	public static final int _event_time = 2;
	// Event state
	private static boolean _isactive = false;
	
	// EVENT VARIABLES
	
	// NPc's
	private static final int _elpy = 900100;
	// How much Elpy's
	private static final int _option_howmuch = 100;
	// Elpy's count
	private static int _elpies_count = 0;
	
	private static final String[] _locations =
	{
		"Aden",
		"Gludin",
		"Hunters Village",
		"Dion",
		"Oren"
	};
	
	// @formatter:off
	private static final int[][] _spawns =
	{
		// minx, maxx, miny, maxy, zspawn
		{ 146558, 148341,  26622,  28560, -2200 },
		{ -84040, -81420, 150257, 151175, -3125 },
		{ 116094, 117141,  75776,  77072, -2700 },
		{  18564,  19200, 144377, 145782, -3081 },
		{  82048,  82940,  53240,  54126, -1490 }
	};
	
	/**
	 * Drop data:<br>
	 * Higher the chance harder the item.<br>
	 * ItemId, chance in percent, min amount, max amount
	 */
	private static final int[][] DROPLIST =
	{
		{  1540,  80, 10, 15 },	// Quick Healing Potion
		{  1538,  60,  5, 10 },	// Blessed Scroll of Escape
		{  3936,  40,  5, 10 },	// Blessed Scroll of Ressurection
		{  6387,  25,  5, 10 },	// Blessed Scroll of Ressurection Pets
		{ 22025,  15,  5, 10 },	// Powerful Healing Potion
		{  6622,  10,  1, 1 },	// Giant's Codex
		{ 20034,   5,  1, 1 },	// Revita Pop
		{ 20004,   1,  1, 1 },	// Energy Ginseng
		{ 20004,   0,  1, 1 }	// Energy Ginseng
	};
	
	private static final int[][] DROPLIST_CRYSTALS =
	{
		{ 1458, 80, 50, 100 },	// Crystal D-Grade
		{ 1459, 60, 40,  80 },	// Crystal C-Grade
		{ 1460, 40, 30,  60 },	// Crystal B-Grade
		{ 1461, 20, 20,  30 },	// Crystal A-Grade
		{ 1462,  0, 10,  20 },	// Crystal S-Grade
	};
	// @formatter:on
	
	public static void main(String[] args)
	{
		new eventmodElpies(-1, "eventmodElpies", "mods");
	}
	
	public eventmodElpies(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addSpawnId(_elpy);
		addKillId(_elpy);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		((L2EventMonsterInstance) npc).eventSetDropOnGround(true);
		((L2EventMonsterInstance) npc).eventSetBlockOffensiveSkills(true);
		
		return super.onSpawn(npc);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		// Drop only if event is active
		if (_isactive)
		{
			dropItem(npc, killer, DROPLIST);
			dropItem(npc, killer, DROPLIST_CRYSTALS);
			_elpies_count--;
			
			if (_elpies_count <= 0)
			{
				Announcements.getInstance().announceToAll("No more elpies...");
				eventStop();
			}
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public boolean eventStart()
	{
		// Don't start event if its active
		if (_isactive)
		{
			return false;
		}
		
		// Check Custom Table - we use custom NPC's
		if (!Config.CUSTOM_NPC_TABLE)
		{
			return false;
		}
		
		// Initialize list
		_npclist = new FastList<>();
		
		// Set Event active
		_isactive = true;
		
		// Spawn Elpy's
		int location = getRandom(0, _locations.length - 1);
		
		int[] _spawndata = _spawns[location];
		
		_elpies_count = 0;
		
		for (int i = 0; i < _option_howmuch; i++)
		{
			int x = getRandom(_spawndata[0], _spawndata[1]);
			int y = getRandom(_spawndata[2], _spawndata[3]);
			recordSpawn(_elpy, x, y, _spawndata[4], 0, true, _event_time * 60 * 1000);
			_elpies_count++;
		}
		
		// Announce event start
		Announcements.getInstance().announceToAll("*Squeak Squeak*");
		Announcements.getInstance().announceToAll("Elpy invasion in " + _locations[location]);
		Announcements.getInstance().announceToAll("Help us exterminate them!");
		Announcements.getInstance().announceToAll("You have " + _event_time + " min...");
		
		// Schedule Event end
		_eventTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				timeUp();
			}
		}, _event_time * 60 * 1000);
		
		return true;
	}
	
	protected void timeUp()
	{
		Announcements.getInstance().announceToAll("Time up !");
		eventStop();
	}
	
	@Override
	public boolean eventStop()
	{
		// Don't stop inactive event
		if (!_isactive)
		{
			return false;
		}
		
		// Set inactive
		_isactive = false;
		
		// Cancel task if any
		if (_eventTask != null)
		{
			_eventTask.cancel(true);
			_eventTask = null;
		}
		// Despawn Npc's
		if (!_npclist.isEmpty())
		{
			for (L2Npc _npc : _npclist)
			{
				if (_npc != null)
				{
					_npc.deleteMe();
				}
			}
		}
		_npclist.clear();
		
		// Announce event end
		Announcements.getInstance().announceToAll("*Squeak Squeak*");
		Announcements.getInstance().announceToAll("Elpy's Event finished");
		
		return true;
	}
	
	private static final void dropItem(L2Npc mob, L2PcInstance player, int[][] droplist)
	{
		final int chance = getRandom(100);
		
		for (int[] drop : droplist)
		{
			if (chance > drop[1])
			{
				((L2MonsterInstance) mob).dropItem(player, drop[0], getRandom(drop[2], drop[3]));
				return;
			}
		}
	}
	
	private L2Npc recordSpawn(int npcId, int x, int y, int z, int heading, boolean randomOffSet, long despawnDelay)
	{
		L2Npc _tmp = addSpawn(npcId, x, y, z, heading, randomOffSet, despawnDelay);
		if (_tmp != null)
		{
			_npclist.add(_tmp);
		}
		return _tmp;
	}
	
	@Override
	public boolean eventBypass(L2PcInstance activeChar, String bypass)
	{
		return false;
	}
}
