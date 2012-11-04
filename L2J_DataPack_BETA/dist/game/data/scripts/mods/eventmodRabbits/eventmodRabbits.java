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
package mods.eventmodRabbits;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2EventChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2EventMonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Event;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;

public class eventmodRabbits extends Event
{
	// Event NPC's list
	private List<L2Npc> _npclist;
	// Event Task
	ScheduledFuture<?> _eventTask = null;
	// Event time
	public static final int _event_time = 10;
	// Event state
	private static boolean _isactive = false;
	// Current Chest count
	private static int _chest_count = 0;
	// How much Chests
	private static final int _option_howmuch = 100;
	// NPc's
	public static final int _npc_snow = 900101;
	public static final int _npc_chest = 900102;
	// Skills
	public static final int _skill_tornado = 630;
	public static final int _skill_magic_eye = 629;
	
	/**
	 * Drop data:<br>
	 * Higher the chance harder the item.<br>
	 * ItemId, chance in percent, min amount, max amount
	 */
	// @formatter:off
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
	// @formatter:on
	
	public static void main(String[] args)
	{
		new eventmodRabbits(-1, "eventmodRabbits", "mods");
	}
	
	public eventmodRabbits(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_npc_snow);
		addFirstTalkId(_npc_snow);
		addTalkId(_npc_snow);
		
		addFirstTalkId(_npc_chest);
		addSkillSeeId(_npc_chest);
		addSpawnId(_npc_chest);
		addAttackId(_npc_chest);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		((L2EventMonsterInstance) npc).eventSetDropOnGround(true);
		((L2EventMonsterInstance) npc).eventSetBlockOffensiveSkills(true);
		
		npc.setIsImmobilized(true);
		npc.disableCoreAI(true);
		
		return super.onSpawn(npc);
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
			_log.info(getName() + ": Event can't be started, because custom npc table is disabled!");
			return false;
		}
		
		// Initialize list
		_npclist = new FastList<>();
		
		// Set Event active
		_isactive = true;
		
		// Spawn Manager
		recordSpawn(_npc_snow, -59227, -56939, -2039, 64106, false, 0);
		
		// Spawn Chests
		for (int i = 0; i < _option_howmuch; i++)
		{
			int x = getRandom(-60653, -58772);
			int y = getRandom(-55830, -57718);
			recordSpawn(_npc_chest, x, y, -2030, 0, true, _event_time * 60 * 1000);
			_chest_count++;
		}
		
		// Announce event start
		Announcements.getInstance().announceToAll("Rabbit Event : Chests spawned!");
		Announcements.getInstance().announceToAll("Go to Fantasy Isle and grab some rewards!");
		Announcements.getInstance().announceToAll("You have " + _event_time + " min - after that time all chests will disappear...");
		
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
		Announcements.getInstance().announceToAll("Rabbit Event finished");
		
		return true;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		if (event.equalsIgnoreCase("transform"))
		{
			if (player.isTransformed() || player.isInStance())
			{
				player.untransform();
			}
			
			SkillTable.getInstance().getInfo(2428, 1).getEffects(npc, player);
			
			return null;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		return npc.getNpcId() + ".htm";
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		if (Util.contains(targets, npc))
		{
			if (skill.getId() == _skill_tornado)
			{
				dropItem(npc, caster, DROPLIST);
				npc.deleteMe();
				_chest_count--;
				
				if (_chest_count <= 0)
				{
					Announcements.getInstance().announceToAll("No more chests...");
					eventStop();
				}
			}
			else if (skill.getId() == _skill_magic_eye)
			{
				if (npc instanceof L2EventChestInstance)
				{
					((L2EventChestInstance) npc).trigger();
				}
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isPet);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		// Some retards go to event and disturb it by breaking chests
		// So... Apply raid curse if player don't use skill on chest but attack it
		if (_isactive && (npc.getNpcId() == _npc_chest))
		{
			SkillTable.getInstance().getInfo(4515, 1).getEffects(npc, attacker);
		}
		
		return super.onAttack(npc, attacker, damage, isPet);
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
