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
package conquerablehalls.RainbowSpringsChateau;

import gnu.trove.map.hash.TIntLongHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.instancemanager.CHSiegeManager;
import com.l2jserver.gameserver.instancemanager.MapRegionManager.TeleportWhereType;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.SiegableHall;
import com.l2jserver.gameserver.model.entity.clanhall.SiegeStatus;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.L2TIntObjectHashMap;
import com.l2jserver.util.Rnd;

/**
 * @author BiggBoss
 * Rainbow Springs Chateau clan hall siege script
 */
public class RainbowSpringsChateau extends Quest
{
	private static final Logger _log = Logger.getLogger(RainbowSpringsChateau.class.getName());
	
	private static class SetFinalAttackers implements Runnable
	{ 
		@Override
		public void run()
		{
			if(_rainbow == null)
				_rainbow = CHSiegeManager.getInstance().getSiegableHall(RAINBOW_SPRINGS);
			
			int spotLeft = 4;
			if(_rainbow.getOwnerId() > 0)
			{
				L2Clan owner = ClanTable.getInstance().getClan(_rainbow.getOwnerId());
				if(owner != null)
				{
					_rainbow.free();
					owner.setHasHideout(0);
					_acceptedClans.add(owner);
					--spotLeft;
				}
					
				for(int i = 0; i < spotLeft; i++)
				{
					long counter = 0;
					L2Clan clan = null;
					for(int clanId : _warDecreesCount.keys())
					{
						L2Clan actingClan = ClanTable.getInstance().getClan(clanId);
						if(actingClan == null || actingClan.getDissolvingExpiryTime() > 0)
						{
							_warDecreesCount.remove(clanId);
							continue;
						}
						
						final long count = _warDecreesCount.get(clanId);
						if(count > counter)
						{								counter = count;
							clan = actingClan;
						}
					}
					if(_acceptedClans.size() < 4)
					{
						_acceptedClans.add(clan);
						L2PcInstance leader = clan.getLeader().getPlayerInstance();
						if(leader != null)
							leader.sendMessage("Your clan has been accepted to join the RainBow Srpings Chateau siege!");
					}
				}
				if(_acceptedClans.size() >= 2)
				{
					_nextSiege = ThreadPoolManager.getInstance().scheduleGeneral(new SiegeStart(), 3600000);
					_rainbow.updateSiegeStatus(SiegeStatus.WAITING_BATTLE);
				}
				else
					Announcements.getInstance().announceToAll("Rainbow Springs Chateau siege aborted due lack of population");
			}
		}		
	}
	
	private static class SiegeStart implements Runnable
	{		
		@Override
		public void run()
		{
			if(_rainbow == null)
				_rainbow = CHSiegeManager.getInstance().getSiegableHall(RAINBOW_SPRINGS);

			//XXX _rainbow.siegeStarts();
			
			spawnGourds();
			_siegeEnd = ThreadPoolManager.getInstance().scheduleGeneral(new SiegeEnd(null), _rainbow.getSiegeLenght() - 120000);
		}
	}
		
	private static class SiegeEnd implements Runnable
	{
		private L2Clan _winner;
		
		private SiegeEnd(L2Clan winner)
		{
			_winner = winner;
		}
		
		@Override
		public void run()
		{
			if(_rainbow == null)
				_rainbow = CHSiegeManager.getInstance().getSiegableHall(RAINBOW_SPRINGS);

			unSpawnGourds();
			
			if(_winner != null)
				_rainbow.setOwner(_winner);
			
			//XXX _rainbow.siegeEnds();
			
			ThreadPoolManager.getInstance().scheduleGeneral(new SetFinalAttackers(), _rainbow.getNextSiegeTime());
			setRegistrationEndString(_rainbow.getNextSiegeTime() + System.currentTimeMillis() - 3600000);
			// Teleport out of the arenas is made 2 mins after game ends
			ThreadPoolManager.getInstance().scheduleGeneral(new TeleportBack(), 120000);
		}
	}
	
	private static class TeleportBack implements Runnable
	{		
		@Override
		public void run()
		{
			for(int arenaId : ARENA_ZONES)
			{
				final L2TIntObjectHashMap<L2Character> chars = ZoneManager.getInstance().getZoneById(arenaId).getCharactersInside();
				for(L2Character chr : chars.values(new L2Character[0]))
				{
					chr.teleToLocation(TeleportWhereType.Town);
				}
			}
		}
	}
	
	private static final String qn = "RainbowSpringsChateau";
	
	private static final int RAINBOW_SPRINGS = 62;
	
	private static final int WAR_DECREES = 8034;
	private static final int RAINBOW_NECTAR= 8030;
	private static final int RAINBOW_MWATER = 8031;
	private static final int RAINBOW_WATER = 8032;
	private static final int RAINBOW_SULFUR = 8033;
	
	private static final int MESSENGER = 35604;
	private static final int CARETAKER = 35603;
	private static final int CHEST = 35593;
	
	private static final int[] GOURDS = { 35588, 35589, 35590, 35591 };
	private static L2Spawn[] _gourds = new L2Spawn[4];
	
	private static final int[] YETIS = { 35596, 35597, 35598, 35599 };
	
	private static final int[][] ARENAS =
	{
		{ 151562, -127080, -2214 }, // Arena 1
		{ 153141, -125335, -2214 }, // Arena 2
		{ 153892, -127530, -2214 }, // Arena 3
		{ 155657, -125752, -2214 }, // Arena 4
	};
	
	private static final int[] ARENA_ZONES = { 112081, 112082, 112083, 112084 };
	
	private static final String[] _textPassages =
	{
		"Text Passage 1",
		"Passage Text 2",
		"Im getting out of ideas",
		"But i can write few more",
		"Are five sentences",
		"enough for this f*** siege?",
		"i think ill add few more",
		"like this one",
		"Please, if you know the true passages",
		"Contact me at L2JForum =)"
	};
	
	private static final L2Skill[] DEBUFFS =
	{
		SkillTable.getInstance().getInfo(0, 1)
	};
		
	private static TIntLongHashMap _warDecreesCount = new TIntLongHashMap();
	private static List<L2Clan> _acceptedClans = new ArrayList<L2Clan>(4);
	private static Map<String, ArrayList<L2Clan>> _usedTextPassages = new HashMap<String, ArrayList<L2Clan>>();
	private static Map<L2Clan, Integer> _pendingItemToGet = new HashMap<L2Clan, Integer>();
	
	private static SiegableHall _rainbow;
	private static ScheduledFuture<?> _nextSiege, _siegeEnd;
	private static String _registrationEnds;
	
	
	/**
	 * @param questId
	 * @param name
	 * @param descr
	 */
	public RainbowSpringsChateau(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(MESSENGER);
		addTalkId(MESSENGER);
		addFirstTalkId(CARETAKER);
		addTalkId(CARETAKER);
		for(int npc : YETIS)
		{
			addFirstTalkId(npc);
			addTalkId(npc);
		}
		
		loadAttackers();
		
		_rainbow = CHSiegeManager.getInstance().getSiegableHall(RAINBOW_SPRINGS);
		if(_rainbow != null)
		{
			long delay = _rainbow.getNextSiegeTime();
			if(delay > -1)
			{
				setRegistrationEndString(delay - 3600000);
				_nextSiege = ThreadPoolManager.getInstance().scheduleGeneral(new SetFinalAttackers(), delay);
			}
			else
				_log.warning("CHSiegeManager: No Date setted for RainBow Springs Chateau Clan hall siege!. SIEGE CANCELED!");
		}
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if(player.getQuestState(qn) == null)
		{
			QuestState state = newQuestState(player);
			state.setState(State.STARTED);
		}

		int npcId = npc.getNpcId();
		String html = "";
		
		if(npcId == MESSENGER)
		{
			sendMessengerMain(player);
		}
		else if(npcId == CARETAKER)
		{
			html = "caretaker_main.htm";
		}
		else if(_rainbow.isInSiege())
		{
			if(!player.isClanLeader())
				html = "no_clan_leader.htm";	
			else
			{
				L2Clan clan = player.getClan();
				if(clan != null && _acceptedClans.contains(clan))
				{
					int index = _acceptedClans.indexOf(clan);
					if(npcId == YETIS[index])
						html = "yeti_main.htm";
				}
			}
		}
		player.setLastQuestNpcObject(npc.getObjectId());
		return html;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if(!player.isClanLeader())
			return "no_clan_leader.htm";

		String html = event;
		final L2Clan clan = player.getClan();		
		final int clanId = clan.getClanId();
		
		if(event.equals("register"))
		{
			if(!_rainbow.isRegistering())
				html = "messenger_not_registering.htm";
			else if(_warDecreesCount.containsKey(clanId))
				html = "messenger_alredy_registered.htm";
			else if(clan.getLevel() < 3 || clan.getMembersCount() < 5)
				html = "messenger_no_level.htm";
			else
			{
				L2ItemInstance warDecrees = player.getInventory().getItemByItemId(WAR_DECREES);
				if(warDecrees == null)
					html = "messenger_nowardecrees.htm";
				else
				{
					long count = warDecrees.getCount();
					_warDecreesCount.put(clanId, count);
					player.destroyItem("Rainbow Springs Registration", warDecrees, npc, true);
					updateAttacker(clanId, count, false);
					html = "messenger_registered.htm";
				}
			}
		}
		else if(event.equals("unregister"))
		{
			if(!_warDecreesCount.containsKey(clanId))
				html = "messenger_notinlist.htm";
			else if(_rainbow.isRegistering())
			{
				String[] split = event.split("_");
				int step = Integer.parseInt(split[1]);
				
				switch(step)
				{
					case 0:
						html = "messenger_unregister_confirmation.htm";
						break;
					case 1:
						html = "messenger_retrive_wardecrees.htm";
						updateAttacker(clanId, 0, true);
						break;
					case 2:
						html = "messenger_unregistered.htm";
						long toRetrive = _warDecreesCount.get(clanId) / 2;
						player.addItem("Rainbow Spring unregister", WAR_DECREES, toRetrive, npc, true);
						_warDecreesCount.remove(clanId);
						break;
						default:
							html = "messenger_main.htm";
				}
			}
			else if(_rainbow.isWaitingBattle())
			{
				if(!_acceptedClans.contains(clan))
					return "messenger_notinlist.htm";
				
				String[] split = event.split("_");
				int step = Integer.parseInt(split[1]);
				
				switch(step)
				{
					case 0:
						html = "messenger_unregister_confirmation_no_retrive.htm";
						break;
					case 1:
						html = "messenger_unregistered.htm";
						_acceptedClans.remove(clan);
						break;
						default:
							html = "messenger_main.htm";
				}
			}
		}
		else if(event.equals("portToArena"))
		{
			if(!_acceptedClans.contains(clan))
				html = "caretaker_not_allowed.htm";
			else if(player.getParty() == null)
				html = "caretaker_no_party.htm";
			else
			{
				int index = _acceptedClans.indexOf(clan);
				portToArena(player, index);
			}
		}
		else if(event.startsWith("enterText"))
		{
			// Shouldnt happen
			if(!_acceptedClans.contains(clan))
				return null;
			
			String[] split = event.split("_");
			if(split.length < 2)
				return null;
			
			final String passage = split[1];
			
			if(!isValidPassage(passage))
				return null;
			
			if(_usedTextPassages.containsKey(passage))
			{
				ArrayList<L2Clan> list = _usedTextPassages.get(passage);
				
				if(list.contains(clan))
					html = "yeti_passage_used.htm";
				else
				{
					list.add(clan);
					synchronized(_pendingItemToGet)
					{
						if(_pendingItemToGet.containsKey(clan))
						{
							int left = _pendingItemToGet.get(clan);
							++left;
							_pendingItemToGet.put(clan, left);
						}
						else
							_pendingItemToGet.put(clan, 1);
					}
					html = "yeti_item_exchange.htm";
				}
			}
		}
		else if(event.startsWith("getItem"))
		{
			if(!_pendingItemToGet.containsKey(clanId))
				html = "yeti_cannot_exchange.htm";
				
			int left = _pendingItemToGet.get(clan);
			if(left > 0)
			{
				int itemId = Integer.parseInt(event.split("_")[1]);
				player.addItem("Rainbow Spring Chateau Siege", itemId, 1, npc, true);
				--left;
				_pendingItemToGet.put(clan, left);
				html = "yeti_main.htm";
			}
			else
				html = "yeti_cannot_exchange.htm";
		}
		
		return html;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if(!_rainbow.isInSiege())
			return null;
		
		final L2Clan clan = killer.getClan();
		if(clan == null || !_acceptedClans.contains(clan))
			return null;
		
		final int npcId = npc.getNpcId();
		final int index = _acceptedClans.indexOf(clan);
		
		if(npcId == CHEST)
		{
			shoutRandomText(npc);
		}
		else if(npcId == GOURDS[index])
		{
			synchronized(this)
			{
				if(_siegeEnd != null)
					_siegeEnd.cancel(false);
				ThreadPoolManager.getInstance().executeTask(new SiegeEnd(clan));
			}
		}
		
		return null;
	}
	
	@Override
	public String onItemUse(L2Item item, L2PcInstance player)
	{
		if(!_rainbow.isInSiege())
			return null;
		
		L2Object target = player.getTarget();
		
		if(target == null || !(target instanceof L2Npc))
			return null;
		
		int yeti = ((L2Npc)target).getNpcId();
		
		if(!isYetiTarget(yeti))
			return null;
		
		final L2Clan clan = player.getClan();
		
		if(clan == null || !_acceptedClans.contains(clan))
			return null;
		
		final int itemId = item.getItemId();
		
		// Nectar must spawn the enraged yeti. Dunno if it makes any other thing
		// Also, the items must execute:
		// - Reduce gourd hpb ( reduceGourdHp(int, L2PcInstance) )
		// - Cast debuffs on enemy clans ( castDebuffsOnEnemies(int) )
		// - Change arena gourds ( moveGourds() )
		// - Increase gourd hp ( increaseGourdHp(int) )
		
		if(itemId == RAINBOW_NECTAR)
		{
			// Spawn enraged (where?)
			reduceGourdHp(_acceptedClans.indexOf(clan), player);
		}
		else if(itemId == RAINBOW_MWATER)
		{
			increaseGourdHp(_acceptedClans.indexOf(clan));
		}
		else if(itemId == RAINBOW_WATER)
		{
			moveGourds();
		}
		else if(itemId == RAINBOW_SULFUR)
		{
			castDebuffsOnEnemies(_acceptedClans.indexOf(clan));
		}
		return null;
	}
	
	private static void portToArena(L2PcInstance leader, int arena)
	{
		if(arena < 0 || arena > 3)
		{
			_log.warning("RainbowSptringChateau siege: Wrong arena id passed: "+arena);
			return;
		}
		for(L2PcInstance pc : leader.getParty().getPartyMembers())
			if(pc != null)
			{
				pc.stopAllEffects();
				if(pc.getPet() != null)
					pc.getPet().unSummon(pc);
				pc.teleToLocation(ARENAS[arena][0], ARENAS[arena][1], ARENAS[arena][2]);
			}	
	}
	
	private static void spawnGourds()
	{
		for(int i = 0; i < _acceptedClans.size(); i++)
		{
			if(_gourds[i] == null)
			{
				try
				{
					_gourds[i] = new L2Spawn(NpcTable.getInstance().getTemplate(GOURDS[i]));
					_gourds[i].setLocx(ARENAS[i][0] + 150);
					_gourds[i].setLocy(ARENAS[i][1] + 150);
					_gourds[i].setLocz(ARENAS[i][2]);
					_gourds[i].setHeading(1);
					_gourds[i].setAmount(1);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			SpawnTable.getInstance().addNewSpawn(_gourds[i], false);
			_gourds[i].init();
		}
	}
	
	private static void unSpawnGourds()
	{
		for(int i = 0; i < _acceptedClans.size(); i++)
		{
			_gourds[i].getLastSpawn().deleteMe();
			SpawnTable.getInstance().deleteSpawn(_gourds[i], false);
		}
	}
	
	private static void moveGourds()
	{
		L2Spawn[] tempArray = _gourds;
		int iterator = _acceptedClans.size();
		for(int i = 0; i < iterator; i++)
		{
			L2Spawn oldSpawn = _gourds[(iterator-1)-i];
			L2Spawn curSpawn = tempArray[i];
			
			_gourds[(iterator -1) - i] = curSpawn;
			
			int newX = oldSpawn.getLocx();
			int newY = oldSpawn.getLocy();
			int newZ = oldSpawn.getLocz();
			
			curSpawn.getLastSpawn().teleToLocation(newX, newY, newZ);
		}
	}
	
	private static void reduceGourdHp(int index, L2PcInstance player)
	{
		L2Spawn gourd = _gourds[index];
		gourd.getLastSpawn().reduceCurrentHp(1000, player, null);
	}
	
	private static void increaseGourdHp(int index)
	{
		L2Spawn gourd = _gourds[index];
		L2Npc gourdNpc = gourd.getLastSpawn();
		gourdNpc.setCurrentHp(gourdNpc.getCurrentHp() + 1000);
	}
	
	private static void castDebuffsOnEnemies(int myArena)
	{
		for(int id : ARENA_ZONES)
		{
			if(id == myArena)
				continue;
			
			final L2TIntObjectHashMap<L2Character> chars = ZoneManager.getInstance().getZoneById(id).getCharactersInside();
			for(L2Character chr : chars.values(new L2Character[0]))
			{
				for(L2Skill sk : DEBUFFS)
					sk.getEffects(chr, chr);
			}
		}
	}
	
	private static void shoutRandomText(L2Npc npc)
	{
		int length = _textPassages.length;
		
		if(_usedTextPassages.size() >= length)
			return;

		int randomPos = Rnd.get(length);
		String message = _textPassages[randomPos];
		
		if(_usedTextPassages.containsKey(message))
			shoutRandomText(npc);
		else
		{
			_usedTextPassages.put(message, new ArrayList<L2Clan>());
			int shout = Say2.SHOUT;
			int objId = npc.getObjectId();
			NpcSay say = new NpcSay(objId, shout, npc.getNpcId(), message);
			npc.broadcastPacket(say);
		}
	}
	
	private static boolean isValidPassage(String text)
	{
		for(String st : _textPassages)
			if(st.equalsIgnoreCase(text))
				return true;
		return false;
	}
	
	private static boolean isYetiTarget(int npcId)
	{
		for(int yeti : YETIS)
			if(yeti == npcId)
				return true;
		return false;
	}
	
	private static void updateAttacker(int clanId, long count, boolean remove)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement;
			if(remove)
			{
				statement = con.prepareStatement("DELETE FROM rainbowsprings_attacker_list WHERE clanId = ?");
				statement.setInt(1, clanId);
			}
			else
			{
				statement = con.prepareStatement("INSERT INTO rainbowsprings_attacker_list VALUES (?,?)");
				statement.setInt(1, clanId);
				statement.setLong(2, count);
			}
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private static void loadAttackers()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM rainbowsprings_attacker_list");
			ResultSet rset = statement.executeQuery();
			while(rset.next())
			{
				int clanId = rset.getInt("clan_id");
				long count = rset.getLong("decrees_count");
				_warDecreesCount.put(clanId, count);
			}
			rset.close();
			statement.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private static void setRegistrationEndString(long time)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR);
		int mins = c.get(Calendar.MINUTE);
		
		_registrationEnds = year+"-"+month+"-"+day+" "+hour+":"+mins;
	}
	
	private static void sendMessengerMain(L2PcInstance player)
	{
		NpcHtmlMessage message = new NpcHtmlMessage(5);
		message.setFile(null, "data/scripts/conquerablehalls/RainbowSpringsChateau/messenger_main.htm");
		message.replace("%time%", _registrationEnds);
		player.sendPacket(message);
	}
	
	public static void launchSiege()
	{
		_nextSiege.cancel(false);
		ThreadPoolManager.getInstance().executeTask(new SiegeStart());
	}
	
	public static void endSiege()
	{
		if(_siegeEnd != null)
			_siegeEnd.cancel(false);
		ThreadPoolManager.getInstance().executeTask(new SiegeEnd(null));
	}
	
	public static void updateAdminDate(long date)
	{
		if(_rainbow == null)
			_rainbow = CHSiegeManager.getInstance().getSiegableHall(RAINBOW_SPRINGS);
		
		_rainbow.setNextSiegeDate(date);
		if(_nextSiege != null)
			_nextSiege.cancel(true);
		date -= 3600000;
		setRegistrationEndString(date);
		_nextSiege = ThreadPoolManager.getInstance().scheduleGeneral(new SetFinalAttackers(), _rainbow.getNextSiegeTime());
	}
	
	public static void main(String[] args)
	{
		new RainbowSpringsChateau(-1, qn, "conquerablehalls");
	}
}
