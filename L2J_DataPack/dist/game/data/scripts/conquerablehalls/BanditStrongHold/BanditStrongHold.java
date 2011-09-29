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
package conquerablehalls.BanditStrongHold;

import gnu.trove.TIntObjectHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.ai.L2SpecialSiegeGuardAI;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.instancemanager.MapRegionManager.TeleportWhereType;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2SiegeClan;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.L2SiegeClan.SiegeClanType;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.ClanHallSiegeEngine;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.chars.L2NpcTemplate;

/**
 * @author BiggBoss
 * Bandit Stronghold hall siege script
 */
public final class BanditStrongHold extends ClanHallSiegeEngine
{
	private class ClanData
	{
		int flag = 0;
		int npc = 0;
		ArrayList<Integer> players = new ArrayList<Integer>(18);
		ArrayList<L2PcInstance> playersInstance = new ArrayList<L2PcInstance>(18);
		L2Spawn warrior = null;
		L2Spawn flagInstance = null;
	}
	
	private static final String qn = "BanditStrongHold";
	
	private static final String SQL_LOAD_ATTACKERS			= "SELECT * FROM bandit_stronghold_attackers";
	private static final String SQL_SAVE_ATTACKER 			= "INSERT INTO bandit_stronghold_attackers_members VALUES (?,?)";
	private static final String SQL_LOAD_MEMEBERS			= "SELECT object_id FROM bandit_stronghold_attackers_members WHERE clan_id = ?";
	private static final String SQL_SAVE_CLAN 				= "INSERT INTO bandit_stronghold_attackers VALUES(?,?,?)";
	private static final String SQL_SAVE_NPC				= "UPDATE bandit_stronghold_attackers SET npc = ? WHERE clan_id = ?";
	private static final String SQL_CLEAR_CLAN 				= "DELETE FROM bandit_stronghold_attackers";
	private static final String SQL_CLEAR_CLAN_ATTACKERS 	= "DELETE FROM bandit_stronghold_attackers_members";
	
	private static final int RED_FLAG = 35423;
	private static final int YELLOW_FLAG = 35424;
	private static final int GREEN_FLAG = 35425;
	private static final int BLUE_FLAG = 35426;
	private static final int PURPLE_FLAG = 35427;
	
	private static final int OEL_MAHUM_BERSERKER = 35428;
	private static final int OEL_MAHUM_SCOUT = 35429;
	private static final int OEL_MAHUM_LEADER = 35430;
	private static final int OEL_MAHUM_CLERIC = 35431;
	private static final int OEL_MAHUM_THIEF = 35432;
	
	private static final int MESSENGER = 35437;
	
	// Custom values
	private static final int[][] FLAGS_COORDS =
	{
		{83607,-17541,-1829},
		{84095,-15478,-1829},
		{81768,-17036,-1826},
		{81287,-16025,-1843},
		{83243,-15077,-1829}
	};
	
	// Custom values
	private static final int[][] MAHUM_COORDS =
	{
		{83658,-17337,-1829},
		{84076,-15846,-1825},
		{81990,-16900,-1839},
		{81527,-15965,-1860},
		{83168,-15319,-1848}
	};
	
	// Custom values
	private static final L2CharPosition CENTER = new L2CharPosition(82882,-16280,-1894,0);
	
	private TIntObjectHashMap<ClanData> _data = new  TIntObjectHashMap<ClanData>();
	private L2Clan _winner;
	
	public BanditStrongHold(int questId, String name, String descr, final int hallId)
	{
		super(questId, name, descr, hallId);
		addStartNpc(MESSENGER);
		addFirstTalkId(MESSENGER);
		addTalkId(MESSENGER);
		
		addKillId(RED_FLAG);
		addKillId(YELLOW_FLAG);
		addKillId(GREEN_FLAG);
		addKillId(BLUE_FLAG);
		addKillId(PURPLE_FLAG);
		
		addSpawnId(OEL_MAHUM_BERSERKER);
		addSpawnId(OEL_MAHUM_SCOUT);
		addSpawnId(OEL_MAHUM_LEADER);
		addSpawnId(OEL_MAHUM_CLERIC);
		addSpawnId(OEL_MAHUM_THIEF);
		
		// Load alredy registered attackers
		loadAttackers();
		
		// If siege ends w/ more than 1 flag alive, winner is old owner
		_winner = ClanTable.getInstance().getClan(_hall.getOwnerId());
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if(player.getQuestState(qn) == null)
			newQuestState(player);
		return "agit_oel_mahum_messenger_1.htm";
	}
	
	@Override
	public synchronized String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String html = event;
		L2Clan clan = player.getClan();
				
		// Register the clan for the siege
		if(event.startsWith("register_clan"))
		{
			if(!_hall.isWaitingBattle())
			{
				NpcHtmlMessage msg = new NpcHtmlMessage(5);
				msg.setFile(null, "data/scripts/conquerablehalls/BanditStrongHold/azit_messenger_q0504_09.htm");
				msg.replace("%objectId%", String.valueOf(npc.getObjectId()));
				msg.replace("%nextSiege%", _hall.getSiegeDate().getTime().toString());
				player.sendPacket(msg);
				return null;
			}
			else if(clan == null || !player.isClanLeader())
				html = "agit_oel_mahum_messenger_2.htm";
			else if((_hall.getOwnerId() > 0 && getAttackers().size() >= 4)
					|| getAttackers().size() >= 5)
				html = "agit_oel_mahum_messenger_21.htm";
			else if(checkIsAttacker(clan))
				html = "agit_oel_mahum_messenger_9.htm";
			else if(_hall.getOwnerId() == clan.getClanId())
				html = "agit_oel_mahum_messenger_22.htm";
			else
			{
				String[] arg = event.split(" ");
				if(arg.length >= 2)
				{
					// Register passing the quest
					if(arg[1].equals("wQuest"))
					{
						if(player.destroyItemByItemId("BanditStrongHold Siege", 5009, 1, npc, false)) // Quest passed
						{
							registerClan(clan);
							html = getFlagHtml(_data.get(clan.getClanId()).flag);
						}
						else // Quest not accoplished, try by paying
							html = "agit_oel_mahum_messenger_24.htm";
					}
					// Register paying the fee
					else if(arg[1].equals("wFee"))
					{
						if(player.reduceAdena("Bandit Stronghold registration", 200000, npc, true)) // Fee payed
						{
							registerClan(clan);
							html = getFlagHtml(_data.get(clan.getClanId()).flag);
						}
						else // Fee couldnt be payed, try with quest
							html = "agit_oel_mahum_messenger_26.htm";
					}
				}
			}
		}
		// Select the flag to defend
		else if(event.startsWith("select_clan_npc"))
		{
			if(!player.isClanLeader())
				html = "agit_oel_mahum_messenger_2.htm";
			else if(!_data.containsKey(clan.getClanId()))
				html = "agit_oel_mahum_messenger_7.htm";
			else
			{
				String[] var = event.split(" ");
				if(var.length >= 2)
				{
					int id = 0;
					try { id = Integer.parseInt(var[1]); }
					catch(Exception e)
					{
						_log.warning("BanditStronghold->select_clan_npc->Wrong mahum warrior id: "+var[1]);
					}
					if(id > 0 && (html = getMahumHtml(id)) != null)
					{
						_data.get(clan.getClanId()).npc = id;
						saveNpc(id, clan.getClanId());
					}
				}
				else
					_log.warning("BanditStrongHold Siege: Not enough parameters to save clan npc for clan: "+clan.getName());
			}
		}
		// View (and change ? ) the current selected mahum warrior
		else if(event.startsWith("view_clan_npc"))
		{
			if(clan == null)
				html = "agit_oel_mahum_messenger_2.htm";
			ClanData cd = _data.get(clan.getClanId());
			if(cd == null)
				html = "agit_oel_mahum_messenger_7.htm";
			else if(cd.npc == 0)
				html = "agit_oel_mahum_messenger_10.htm";
			else
				html = getMahumHtml(cd.npc);
		}
		// Register a clan member for the fight
		else if(event.equals("register_member"))
		{
			if(clan == null)
				html = "agit_oel_mahm_messenger_5.htm";
			else if(!_hall.isRegistering())
				html = "agit_oel_mahum_messenger_3.htm";
			else if(!_data.containsKey(clan.getClanId()))
				html = "agit_oel_mahum_messenger_7.htm";
			else if(_data.get(clan.getClanId()).players.size() >= 18)
				html = "agit_oel_mahum_messenger_8.htm";
			else
			{
				ClanData data = _data.get(clan.getClanId());
				data.players.add(player.getObjectId());
				saveMember(clan.getClanId(), player.getObjectId());
				if(data.npc == 0)
					html = "agit_oel_mahum_messenger_16.htm";
				else
					html = "agit_oel_mahum_messenger_9.htm";
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
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
		return super.onSpawn(npc);
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
		for(Object obj : _data.getValues())
		{
			try
			{
				ClanData data = (ClanData)obj;
				
				L2NpcTemplate flagTemplate = NpcTable.getInstance().getTemplate(data.flag);
				L2NpcTemplate mahumTemplate = NpcTable.getInstance().getTemplate(data.npc);
				
				if(flagTemplate == null)
				{
					_log.warning("BanditStrongHoldSiege: Flag L2NpcTemplate["+data.flag+"] does not exist!");
					continue;
				}
				if(mahumTemplate == null)
				{
					_log.warning("BanditStrongHoldSiege: Mahum L2NpcTemplate["+data.npc+"] does not exist!");
					continue;
				}
						
				data.flagInstance = new L2Spawn(flagTemplate);
				int index = data.flag - 35423;
				int[] flagCoords = FLAGS_COORDS[index];		
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
				int indexx = data.npc - 35428;
				data.warrior.setLocx(MAHUM_COORDS[indexx][0]);
				data.warrior.setLocy(MAHUM_COORDS[indexx][1]);
				data.warrior.setLocz(MAHUM_COORDS[indexx][2]);
				data.warrior.setRespawnDelay(10000);
				data.warrior.setAmount(1);
				data.warrior.init();
				
				((L2SpecialSiegeGuardAI)data.warrior.getLastSpawn().getAI()).getAlly().addAll(data.players);	
			}
			catch(Exception e)
			{
				endSiege();
				_log.warning(_hall.getName()+": Problems in siege initialization!");
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
	
	private void registerClan(L2Clan clan)
	{
		final int clanId = clan.getClanId();
		
		L2SiegeClan sc = new L2SiegeClan(clanId, SiegeClanType.ATTACKER);
		getAttackers().put(clanId, sc);
		
		ClanData data = new ClanData();
		data.flag = 35423 + _data.size();
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
	
	private String getFlagHtml(int flag)
	{
		String result = null;
		
		switch(flag)
		{
			case RED_FLAG:
				result = "agit_oel_mahum_messenger_4a.htm";
				break;
			case YELLOW_FLAG:
				result = "agit_oel_mahum_messenger_4b.htm";
				break;
			case GREEN_FLAG:
				result = "agit_oel_mahum_messenger_4c.htm";
				break;
			case BLUE_FLAG:
				result = "agit_oel_mahum_messenger_4d.htm";
				break;
			case PURPLE_FLAG:
				result = "agit_oel_mahum_messenger_4e.htm";
				break;
				default:
					result = "<html><body>Are you kidding me?</body></html>";
		}
		
		return result;
	}
	
	private String getMahumHtml(int mahum)
	{
		String result = null;
		
		switch(mahum)
		{
			case OEL_MAHUM_BERSERKER:
				result = "agit_oel_mahum_messenger_17.htm";
				break;
			case OEL_MAHUM_SCOUT:
				result = "agit_oel_mahum_messenger_18.htm";
				break;
			case OEL_MAHUM_LEADER:
				result = "agit_oel_mahum_messenger_19.htm";
				break;
			case OEL_MAHUM_CLERIC:
				result = "agit_oel_mahum_messenger_20.htm";
				break;
			case OEL_MAHUM_THIEF:
				result = "agit_oel_mahum_messenger_23.htm";
				break;
				default:
					result = "<html><body>Are you kidding me?</body></html>";
		}
		
		return result;
	}
	
	// =============================================
	// Database access methods
	// =============================================
	private final void loadAttackers()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(SQL_LOAD_ATTACKERS);
			ResultSet rset = statement.executeQuery();
			while(rset.next())
			{
				final int clanId = rset.getInt("clan_id");
				
				if(ClanTable.getInstance().getClan(clanId) == null)
				{
					_log.warning("BanditStronghold: Loaded an unexistent clan as attacker! Clan Id: "+clanId);
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
			_log.warning("BanditStrongHold.loadAttackers()->"+e.getMessage());
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
				_log.warning("BanditStronghold: Tried to load unregistered clan: "+clanId+"[clan Id]");
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
			_log.warning("BanditStrongHold.loadAttackerMembers()->"+e.getMessage());
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
			statement.setInt(1, flag);
			statement.setInt(2, 0);
			statement.setInt(3, clanId);
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning("BanditStrongHold.saveClan()->"+e.getMessage());
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
			_log.warning("BanditStrongHold.saveNpc()->"+e.getMessage());
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
			statement.setInt(1, clanId);
			statement.setInt(2, objectId);
			statement.execute();
			statement.close();
		}
		catch(Exception e)
		{
			_log.warning("BanditStronghold.saveMember()->"+e.getMessage());
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
			stat1.execute();
			stat1.close();
			
			PreparedStatement stat2 = con.prepareStatement(SQL_CLEAR_CLAN_ATTACKERS);
			stat2.execute();
			stat2.close();
		}
		catch(Exception e)
		{
			_log.warning("BanditStrongHold.clearTables()->"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	public static void main(String[] args)
	{
		new BanditStrongHold(-1, qn, "conquerablehalls", BANDIT_STRONGHOLD);
	}
}