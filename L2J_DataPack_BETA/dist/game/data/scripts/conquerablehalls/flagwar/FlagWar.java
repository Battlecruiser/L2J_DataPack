/**
 * 
 */
package conquerablehalls.flagwar;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.ai.L2SpecialSiegeGuardAI;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.instancemanager.MapRegionManager.TeleportWhereType;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2SiegeClan;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.L2SiegeClan.SiegeClanType;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.ClanHallSiegeEngine;
import com.l2jserver.gameserver.model.zone.type.L2ResidenceHallTeleportZone;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.chars.L2NpcTemplate;

/**
 * @author BiggBoss
 */
public abstract class FlagWar extends ClanHallSiegeEngine
{
	protected static String qn;
	
	private static final String SQL_LOAD_ATTACKERS			= "SELECT * FROM siegable_hall_flagwar_attackers WHERE hall_id = ?";
	private static final String SQL_SAVE_ATTACKER 			= "INSERT INTO siegable_hall_flagwar_attackers_members VALUES (?,?,?)";
	private static final String SQL_LOAD_MEMEBERS			= "SELECT object_id FROM siegable_hall_flagwar_attackers_members WHERE clan_id = ?";
	private static final String SQL_SAVE_CLAN 				= "INSERT INTO siegable_hall_flagwar_attackers VALUES(?,?,?,?)";
	private static final String SQL_SAVE_NPC				= "UPDATE siegable_hall_flagwar_attackers SET npc = ? WHERE clan_id = ?";
	private static final String SQL_CLEAR_CLAN 				= "DELETE FROM siegable_hall_flagwar_attackers WHERE hall_id = ?";
	private static final String SQL_CLEAR_CLAN_ATTACKERS 	= "DELETE FROM siegable_hall_flagwar_attackers_members WHERE hall_id = ?";

	protected static int FLAG_RED;
	protected static int FLAG_YELLOW;
	protected static int FLAG_GREEN;
	protected static int FLAG_BLUE;
	protected static int FLAG_PURPLE;
	
	protected static int ALLY_1;
	protected static int ALLY_2;
	protected static int ALLY_3;
	protected static int ALLY_4;
	protected static int ALLY_5;
	
	protected static int TELEPORT_1;
	
	protected static int MESSENGER;
	
	protected static int[][] FLAG_COORDS = new int[5][3];
	protected static int[][] ALLY_COORDS = new int[5][3];
	
	protected static L2ResidenceHallTeleportZone[] TELE_ZONES = new L2ResidenceHallTeleportZone[5];
	
	protected static int QUEST_REWARD;
	
	protected static L2CharPosition CENTER;
	
	protected TIntObjectHashMap<ClanData> _data =  new TIntObjectHashMap<ClanData>();
	protected L2Clan _winner;
	
	public FlagWar(int questId, String name, String descr, int hallId)
	{
		super(questId, name, descr, hallId);
		
		addStartNpc(MESSENGER);
		addFirstTalkId(MESSENGER);
		addTalkId(MESSENGER);
		
		for(int i = 0; i < 5; i++)
			addFirstTalkId(TELEPORT_1 + i);
		
		addKillId(FLAG_RED);
		addKillId(FLAG_YELLOW);
		addKillId(FLAG_GREEN);
		addKillId(FLAG_BLUE);
		addKillId(FLAG_PURPLE);
		
		addSpawnId(ALLY_1);
		addSpawnId(ALLY_2);
		addSpawnId(ALLY_3);
		addSpawnId(ALLY_4);
		addSpawnId(ALLY_5);

		// If siege ends w/ more than 1 flag alive, winner is old owner
		_winner = ClanTable.getInstance().getClan(_hall.getOwnerId());
	}

	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String html = null;
		/*
		if(player.getQuestState(qn) == null)
			newQuestState(player);
		*/
		if(npc.getNpcId() == MESSENGER)
		{
			if(!checkIsAttacker(player.getClan()))
			{
				L2Clan clan = ClanTable.getInstance().getClan(_hall.getOwnerId());
				String content = HtmCache.getInstance().getHtm(null, "data/scripts/conquerablehalls/flagwar/"+qn+"/messenger_initial.htm");
				content.replaceAll("%clanName%", clan == null? "no owner" : clan.getName());
				content.replaceAll("%objectId%", String.valueOf(npc.getObjectId()));
				html = content;
			}
			else
				html = "messenger_initial.htm";
		}
		else
		{
			int index = npc.getNpcId() - TELEPORT_1;
			TELE_ZONES[index].checkTeleporTask();
			html = "teleporter.htm";
		}
		return html;
	}
	
	@Override
	public synchronized String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String html = event;
		L2Clan clan = player.getClan();
				
		if(event.startsWith("register_clan")) // Register the clan for the siege
		{
			if(!_hall.isRegistering())
			{
				if(_hall.isInSiege())
					html = "messenger_registrationpassed.htm";
				else
				{
					sendRegistrationPageDate(player);
					return null;
				}
			}
			else if(clan == null || !player.isClanLeader())
				html = "messenger_notclannotleader.htm";
			else if((_hall.getOwnerId() > 0 && getAttackers().size() >= 4)
					|| getAttackers().size() >= 5)
				html = "messenger_attackersqueuefull.htm";
			else if(checkIsAttacker(clan))
				html = "messenger_clanalreadyregistered.htm";
			else if(_hall.getOwnerId() == clan.getClanId())
				html = "messenger_curownermessage.htm";
			else
			{
				String[] arg = event.split(" ");
				if(arg.length >= 2)
				{
					// Register passing the quest
					if(arg[1].equals("wQuest"))
					{
						if(player.destroyItemByItemId(_hall.getName()+" Siege", QUEST_REWARD, 1, npc, false)) // Quest passed
						{
							registerClan(clan);
							html = getFlagHtml(_data.get(clan.getClanId()).flag);
						}
						else // Quest not accoplished, try by paying
							html = "messenger_noquest.htm";
					}
					// Register paying the fee
					else if(arg[1].equals("wFee") && canPayRegistration())
					{
						if(player.reduceAdena(qn+" Siege", 200000, npc, false)) // Fee payed
						{
							registerClan(clan);
							html = getFlagHtml(_data.get(clan.getClanId()).flag);
						}
						else // Fee couldnt be payed, try with quest
							html = "messenger_nomoney.htm";
					}
				}
			}
		}
		// Select the flag to defend
		else if(event.startsWith("select_clan_npc"))
		{
			if(!player.isClanLeader())
				html = "messenger_onlyleaderselectally.htm";
			else if(!_data.containsKey(clan.getClanId()))
				html = "messenger_clannotregistered.htm";
			else
			{
				String[] var = event.split(" ");
				if(var.length >= 2)
				{
					int id = 0;
					try { id = Integer.parseInt(var[1]); }
					catch(Exception e)
					{
						_log.warning(qn+"->select_clan_npc->Wrong mahum warrior id: "+var[1]);
					}
					if(id > 0 && (html = getAllyHtml(id)) != null)
					{
						_data.get(clan.getClanId()).npc = id;
						saveNpc(id, clan.getClanId());
					}
				}
				else
					_log.warning(qn+" Siege: Not enough parameters to save clan npc for clan: "+clan.getName());
			}
		}
		// View (and change ? ) the current selected mahum warrior
		else if(event.startsWith("view_clan_npc"))
		{
			ClanData cd = null;
			if(clan == null)
				html = "messenger_clannotregistered.htm";
			else if((cd = _data.get(clan.getClanId())) == null)
				html = "messenger_notclannotleader.htm";
			else if(cd.npc == 0)
				html = "messenger_leaderdidnotchooseyet.htm";
			else
				html = getAllyHtml(cd.npc);
		}
		// Register a clan member for the fight
		else if(event.equals("register_member"))
		{
			if(clan == null)
				html = "messenger_clannotregistered.htm";
			else if(!_hall.isRegistering())
				html = "messenger_registrationpassed.htm";
			else if(!_data.containsKey(clan.getClanId()))
				html = "messenger_notclannotleader.htm";
			else if(_data.get(clan.getClanId()).players.size() >= 18)
				html = "messenger_clanqueuefull.htm";
			else
			{
				ClanData data = _data.get(clan.getClanId());
				data.players.add(player.getObjectId());
				saveMember(clan.getClanId(), player.getObjectId());
				if(data.npc == 0)
					html = "messenger_leaderdidnotchooseyet.htm";
				else
					html = "messenger_clanregistered.htm";
			}
		}
		// Show cur attacker list
		else if(event.equals("view_attacker_list"))
		{
			if(_hall.isRegistering())
				sendRegistrationPageDate(player);
			else
			{
				html = HtmCache.getInstance().getHtm(null, "data/scripts/conquerablehalls/flagwar/"+qn+"/messenger_registeredclans.htm");
				for(int i = 0; i < _data.size(); i++)
				{
					L2Clan attacker = ClanTable.getInstance().getClan(_data.keys()[i]);
					if(attacker == null)
						continue;
					html.replaceAll("%clan"+i+"%", clan.getName());
					html.replaceAll("%clanMem"+i+"%", String.valueOf(((ClanData)_data.values()[i]).players.size()));
				}
				html.replaceAll("%clan", "Empty pos. ");
				html.replaceAll("%clanMem", "Empty pos. ");
			}
		}
		
		return html;
	}
	
	@Override
	public synchronized String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if(_hall.isInSiege())
		{
			final int npcId = npc.getNpcId();
			for(int keys : _data.keys())
				if(_data.get(keys).flag == npcId)
					removeParticipant(keys, true);
			
			synchronized(this)
			{
				// Siege ends if just 1 flag is alive
				if(_data.size() == 1)
				{
					_missionAccomplished = true;
					_winner = ClanTable.getInstance().getClan(_data.keys()[0]);
					removeParticipant(_data.keys()[0], false);
					cancelSiegeTask();
					endSiege();
				}
			}
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, CENTER);
		return null;
	}
	
	@Override
	public L2Clan getWinner()
	{
		return _winner;
	}
	
	@Override
	public void startSiege()
	{
		if(getAttackers().size() < 2)
		{
			onSiegeEnds();
			getAttackers().clear();
			_hall.updateNextSiege();
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.SIEGE_OF_S1_HAS_BEEN_CANCELED_DUE_TO_LACK_OF_INTEREST);
			sm.addString(_hall.getName());
			Announcements.getInstance().announceToAll(sm);
			return;
		}
		
		super.startSiege();
	}
	
	@Override
	public void onSiegeStarts()
	{
		for(ClanData data : _data.values())
		{
			try
			{
				L2NpcTemplate flagTemplate = NpcTable.getInstance().getTemplate(data.flag);
				L2NpcTemplate mahumTemplate = NpcTable.getInstance().getTemplate(data.npc);
				
				if(flagTemplate == null)
				{
					_log.warning(qn+": Flag L2NpcTemplate["+data.flag+"] does not exist!");
					continue;
				}
				if(mahumTemplate == null)
				{
					_log.warning(qn+": Ally L2NpcTemplate["+data.npc+"] does not exist!");
					continue;
				}
						
				data.flagInstance = new L2Spawn(flagTemplate);
				int index = data.flag - FLAG_RED;
				int[] flagCoords = FLAG_COORDS[index];		
				data.flagInstance.setLocx(flagCoords[0]);
				data.flagInstance.setLocy(flagCoords[1]);
				data.flagInstance.setLocz(flagCoords[2]);
				data.flagInstance.setRespawnDelay(10000);
				data.flagInstance.setAmount(1);
				data.flagInstance.init();
				
				for(int objId : data.players)
				{
					L2PcInstance plr = L2World.getInstance().getPlayer(objId);
					if(plr != null)
					{
						data.playersInstance.add(plr);
						plr.teleToLocation(flagCoords[0] + 50, flagCoords[1] + 50, flagCoords[2]);
					}
				}
				
				data.warrior = new L2Spawn(mahumTemplate);
				int indexx = data.npc - ALLY_1;
				data.warrior.setLocx(ALLY_COORDS[indexx][0]);
				data.warrior.setLocy(ALLY_COORDS[indexx][1]);
				data.warrior.setLocz(ALLY_COORDS[indexx][2]);
				data.warrior.setRespawnDelay(10000);
				data.warrior.setAmount(1);
				data.warrior.init();
				
				((L2SpecialSiegeGuardAI)data.warrior.getLastSpawn().getAI()).getAlly().addAll(data.players);	
			}
			catch(Exception e)
			{
				endSiege();
				_log.warning(qn+": Problems in siege initialization!");
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void onSiegeEnds()
	{
		if(_data.size() > 0)
		{
			for(int clanId : _data.keys())
			{
				if(_hall.getOwnerId() == clanId)
					removeParticipant(clanId, false);
				else
					removeParticipant(clanId, true);
			}
		}
		clearTables();
	}
	
	@Override
	public final Location getInnerSpawnLoc(final L2PcInstance player)
	{
		Location loc = null;
		ClanData cd = _data.get(player.getClanId());
		if(cd != null)
		{
			// 0 is owner inner spawn point during no siege
			int index = cd.npc - ALLY_1 + 1;
			if(index >= 1 && index <= 5)
				loc = _hall.getZone().getSpawns().get(index);
			else 
				throw new ArrayIndexOutOfBoundsException();
		}
		return loc;
	}
	
	@Override
	public final boolean canPlantFlag()
	{
		return false;
	}
	
	private void registerClan(L2Clan clan)
	{
		final int clanId = clan.getClanId();
		
		L2SiegeClan sc = new L2SiegeClan(clanId, SiegeClanType.ATTACKER);
		getAttackers().put(clanId, sc);
		
		ClanData data = new ClanData();
		data.flag = FLAG_RED + _data.size();
		data.players.add(clan.getLeaderId());
		_data.put(clanId, data);
		
		saveClan(clanId, data.flag);
		saveMember(clanId, clan.getLeaderId());
	}
	
	private final void removeParticipant(int clanId, boolean teleport)
	{
		ClanData dat = _data.remove(clanId);
		
		if(dat != null)
		{	
			// Destroy clan flag
			if(dat.flagInstance != null)
			{
				dat.flagInstance.stopRespawn();
				if(dat.flagInstance.getLastSpawn() != null)
					dat.flagInstance.getLastSpawn().deleteMe();
			}
		
			if(dat.warrior != null)
			{
				// Destroy clan warrior
				dat.warrior.stopRespawn();
				if(dat.warrior.getLastSpawn() != null)
					dat.warrior.getLastSpawn().deleteMe();
			}
		
			dat.players.clear();
			
			if(teleport)
			{
				// Teleport players outside
				for(L2PcInstance pc : dat.playersInstance)
					if(pc != null)
						pc.teleToLocation(TeleportWhereType.Town);
			}
			
			dat.playersInstance.clear();
		}
	}
	
	public boolean canPayRegistration()
	{
		return true;
	}
	
	private void sendRegistrationPageDate(L2PcInstance player)
	{
		NpcHtmlMessage msg = new NpcHtmlMessage(5);
		msg.setFile(null, "data/scripts/conquerablehalls/flagwar/"+qn+"/siege_date.htm");
		msg.replace("%nextSiege%", _hall.getSiegeDate().getTime().toString());
		player.sendPacket(msg);
	}
	
	public abstract String getFlagHtml(int flag);	
	public abstract String getAllyHtml(int ally);
	
	// =============================================
	// Database access methods
	// =============================================
	@Override
	public final void loadAttackers()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_LOAD_ATTACKERS);
			statement.setInt(1, _hall.getId());
			ResultSet rset = statement.executeQuery();
			while(rset.next())
			{
				final int clanId = rset.getInt("clan_id");
				
				if(ClanTable.getInstance().getClan(clanId) == null)
				{
					_log.warning(qn+": Loaded an unexistent clan as attacker! Clan Id: "+clanId);
					continue;
				}
				
				ClanData data = new ClanData();
				data.flag = rset.getInt("flag");;
				data.npc = rset.getInt("npc");

				_data.put(clanId, data);
				loadAttackerMembers(clanId);
			}
			rset.close();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".loadAttackers()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private final void loadAttackerMembers(int clanId)
	{
		Connection con = null;
		try
		{
			ArrayList<Integer> listInstance = _data.get(clanId).players;
			
			if(listInstance == null)
			{
				_log.warning(qn+": Tried to load unregistered clan: "+clanId+"[clan Id]");
				return;
			}
			
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_LOAD_MEMEBERS);
			statement.setInt(1, clanId);
			ResultSet rset = statement.executeQuery();
			while(rset.next())
			{
				listInstance.add(rset.getInt("object_id"));
				
			}
			rset.close();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".loadAttackerMembers()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private final void saveClan(int clanId, int flag)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_SAVE_CLAN);
			statement.setInt(1, _hall.getId());
			statement.setInt(2, flag);
			statement.setInt(3, 0);
			statement.setInt(4, clanId);
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".saveClan()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private final void saveNpc(int npc, int clanId)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_SAVE_NPC);
			statement.setInt(1, npc);
			statement.setInt(2, clanId);
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".saveNpc()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private final void saveMember(int clanId, int objectId)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_SAVE_ATTACKER);
			statement.setInt(1, _hall.getId());
			statement.setInt(2, clanId);
			statement.setInt(3, objectId);
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".saveMember()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private void clearTables()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			PreparedStatement stat1 = con.prepareStatement(SQL_CLEAR_CLAN);
			stat1.setInt(1, _hall.getId());
			stat1.execute();
			stat1.close();
			
			PreparedStatement stat2 = con.prepareStatement(SQL_CLEAR_CLAN_ATTACKERS);
			stat2.setInt(1, _hall.getId());
			stat2.execute();
			stat2.close();
		}
		catch(Exception e)
		{
			_log.warning(qn+".clearTables()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	class ClanData
	{
		int flag = 0;
		int npc = 0;
		ArrayList<Integer> players = new ArrayList<Integer>(18);
		ArrayList<L2PcInstance> playersInstance = new ArrayList<L2PcInstance>(18);
		L2Spawn warrior = null;
		L2Spawn flagInstance = null;
	}
}
