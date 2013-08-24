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
package handlers.admincommandhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jserver.gameserver.datatables.CharNameTable;
import com.l2jserver.gameserver.datatables.ClassListData;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.network.L2GameClient;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.communityserver.CommunityServerThread;
import com.l2jserver.gameserver.network.communityserver.writepackets.WorldInfo;
import com.l2jserver.gameserver.network.serverpackets.CharInfo;
import com.l2jserver.gameserver.network.serverpackets.ExBrExtraUserInfo;
import com.l2jserver.gameserver.network.serverpackets.ExVoteSystemInfo;
import com.l2jserver.gameserver.network.serverpackets.GMViewItemList;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.PartySmallWindowAll;
import com.l2jserver.gameserver.network.serverpackets.PartySmallWindowDeleteAll;
import com.l2jserver.gameserver.network.serverpackets.SetSummonRemainTime;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.network.serverpackets.UserInfo;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.StringUtil;

/**
 * This class handles following admin commands: - edit_character - current_player - character_list - show_characters - find_character - find_ip - find_account - rec - nokarma - setkarma - settitle - changename - setsex - setclass - fullfood - save_modifications - setcolor - settcolor - setpk -
 * setpvp - remove_clan_penalty - summon_info - unsummon - summon_setlvl - show_pet_inv - partyinfo
 * @version $Revision: 1.3.2.1.2.10 $ $Date: 2005/04/11 10:06:06 $ Typo fix, rework for admin_tracert, gatherCharacterInfo and editCharacter by Zoey76 28/02/2011
 */
public class AdminEditChar implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminEditChar.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_edit_character",
		"admin_current_player",
		"admin_nokarma", // this is to remove karma from selected char...
		"admin_setkarma", // sets karma of target char to any amount. //setkarma <karma>
		"admin_setfame", // sets fame of target char to any amount. //setfame <fame>
		"admin_character_list", // same as character_info, kept for compatibility purposes
		"admin_character_info", // given a player name, displays an information window
		"admin_show_characters",// list of characters
		"admin_find_character", // find a player by his name or a part of it (case-insensitive)
		"admin_find_ip", // find all the player connections from a given IPv4 number
		"admin_find_account", // list all the characters from an account (useful for GMs w/o DB access)
		"admin_find_dualbox", // list all the IPs with more than 1 char logged in (dualbox)
		"admin_strict_find_dualbox",
		"admin_tracert",
		"admin_save_modifications", // consider it deprecated...
		"admin_rec", // gives recommendation points
		"admin_settitle", // changes char title
		"admin_changename", // changes char name
		"admin_setsex", // changes characters' sex
		"admin_setcolor", // change charnames' color display
		"admin_settcolor", // change char title color
		"admin_setclass", // changes chars' classId
		"admin_setpk", // changes PK count
		"admin_setpvp", // changes PVP count
		"admin_fullfood", // fulfills a pet's food bar
		"admin_remove_clan_penalty", // removes clan penalties
		"admin_summon_info", // displays an information window about target summon
		"admin_unsummon",
		"admin_summon_setlvl",
		"admin_show_pet_inv",
		"admin_partyinfo",
		"admin_setnoble"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_current_player"))
		{
			showCharacterInfo(activeChar, activeChar);
		}
		else if (command.startsWith("admin_character_info"))
		{
			String[] data = command.split(" ");
			if ((data.length > 1))
			{
				showCharacterInfo(activeChar, L2World.getInstance().getPlayer(data[1]));
			}
			else if (activeChar.getTarget() instanceof L2PcInstance)
			{
				showCharacterInfo(activeChar, activeChar.getTarget().getActingPlayer());
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else if (command.startsWith("admin_character_list"))
		{
			listCharacters(activeChar, 0);
		}
		else if (command.startsWith("admin_show_characters"))
		{
			try
			{
				String val = command.substring(22);
				int page = Integer.parseInt(val);
				listCharacters(activeChar, page);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				// Case of empty page number
				activeChar.sendMessage("Usage: //show_characters <page_number>");
			}
		}
		else if (command.startsWith("admin_find_character"))
		{
			try
			{
				String val = command.substring(21);
				findCharacter(activeChar, val);
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of empty character name
				activeChar.sendMessage("Usage: //find_character <character_name>");
				listCharacters(activeChar, 0);
			}
		}
		else if (command.startsWith("admin_find_ip"))
		{
			try
			{
				String val = command.substring(14);
				findCharactersPerIp(activeChar, val);
			}
			catch (Exception e)
			{ // Case of empty or malformed IP number
				activeChar.sendMessage("Usage: //find_ip <www.xxx.yyy.zzz>");
				listCharacters(activeChar, 0);
			}
		}
		else if (command.startsWith("admin_find_account"))
		{
			try
			{
				String val = command.substring(19);
				findCharactersPerAccount(activeChar, val);
			}
			catch (Exception e)
			{ // Case of empty or malformed player name
				activeChar.sendMessage("Usage: //find_account <player_name>");
				listCharacters(activeChar, 0);
			}
		}
		else if (command.startsWith("admin_edit_character"))
		{
			String[] data = command.split(" ");
			if ((data.length > 1))
			{
				editCharacter(activeChar, data[1]);
			}
			else if (activeChar.getTarget() instanceof L2PcInstance)
			{
				editCharacter(activeChar, null);
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		// Karma control commands
		else if (command.equals("admin_nokarma"))
		{
			setTargetKarma(activeChar, 0);
		}
		else if (command.startsWith("admin_setkarma"))
		{
			try
			{
				String val = command.substring(15);
				int karma = Integer.parseInt(val);
				setTargetKarma(activeChar, karma);
			}
			catch (Exception e)
			{
				if (Config.DEVELOPER)
				{
					_log.warning("Set karma error: " + e);
				}
				activeChar.sendMessage("Usage: //setkarma <new_karma_value>");
			}
		}
		else if (command.startsWith("admin_setpk"))
		{
			try
			{
				String val = command.substring(12);
				int pk = Integer.parseInt(val);
				L2Object target = activeChar.getTarget();
				if (target instanceof L2PcInstance)
				{
					L2PcInstance player = (L2PcInstance) target;
					player.setPkKills(pk);
					player.broadcastUserInfo();
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					player.sendMessage("A GM changed your PK count to " + pk);
					activeChar.sendMessage(player.getName() + "'s PK count changed to " + pk);
				}
				else
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				}
			}
			catch (Exception e)
			{
				if (Config.DEVELOPER)
				{
					_log.warning("Set pk error: " + e);
				}
				activeChar.sendMessage("Usage: //setpk <pk_count>");
			}
		}
		else if (command.startsWith("admin_setpvp"))
		{
			try
			{
				String val = command.substring(13);
				int pvp = Integer.parseInt(val);
				L2Object target = activeChar.getTarget();
				if (target instanceof L2PcInstance)
				{
					L2PcInstance player = (L2PcInstance) target;
					player.setPvpKills(pvp);
					player.broadcastUserInfo();
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					player.sendMessage("A GM changed your PVP count to " + pvp);
					activeChar.sendMessage(player.getName() + "'s PVP count changed to " + pvp);
				}
				else
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				}
			}
			catch (Exception e)
			{
				if (Config.DEVELOPER)
				{
					_log.warning("Set pvp error: " + e);
				}
				activeChar.sendMessage("Usage: //setpvp <pvp_count>");
			}
		}
		else if (command.startsWith("admin_setfame"))
		{
			try
			{
				String val = command.substring(14);
				int fame = Integer.parseInt(val);
				L2Object target = activeChar.getTarget();
				if (target instanceof L2PcInstance)
				{
					L2PcInstance player = (L2PcInstance) target;
					player.setFame(fame);
					player.broadcastUserInfo();
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					player.sendMessage("A GM changed your Reputation points to " + fame);
					activeChar.sendMessage(player.getName() + "'s Fame changed to " + fame);
				}
				else
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				}
			}
			catch (Exception e)
			{
				if (Config.DEVELOPER)
				{
					_log.warning("Set Fame error: " + e);
				}
				activeChar.sendMessage("Usage: //setfame <new_fame_value>");
			}
		}
		else if (command.startsWith("admin_save_modifications"))
		{
			try
			{
				String val = command.substring(24);
				adminModifyCharacter(activeChar, val);
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of empty character name
				activeChar.sendMessage("Error while modifying character.");
				listCharacters(activeChar, 0);
			}
		}
		else if (command.startsWith("admin_rec"))
		{
			try
			{
				String val = command.substring(10);
				int recVal = Integer.parseInt(val);
				L2Object target = activeChar.getTarget();
				if (target instanceof L2PcInstance)
				{
					L2PcInstance player = (L2PcInstance) target;
					player.setRecomHave(recVal);
					player.broadcastUserInfo();
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					player.sendPacket(new ExVoteSystemInfo(player));
					player.sendMessage("A GM changed your Recommend points to " + recVal);
					activeChar.sendMessage(player.getName() + "'s Recommend changed to " + recVal);
				}
				else
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //rec number");
			}
		}
		else if (command.startsWith("admin_setclass"))
		{
			try
			{
				String val = command.substring(15).trim();
				int classidval = Integer.parseInt(val);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
				}
				else
				{
					return false;
				}
				boolean valid = false;
				for (ClassId classid : ClassId.values())
				{
					if (classidval == classid.getId())
					{
						valid = true;
					}
				}
				if (valid && (player.getClassId().getId() != classidval))
				{
					player.setClassId(classidval);
					if (!player.isSubClassActive())
					{
						player.setBaseClass(classidval);
					}
					String newclass = ClassListData.getInstance().getClass(player.getClassId()).getClassName();
					player.storeMe();
					player.sendMessage("A GM changed your class to " + newclass + ".");
					player.broadcastUserInfo();
					activeChar.sendMessage(player.getName() + " is a " + newclass + ".");
				}
				else
				{
					activeChar.sendMessage("Usage: //setclass <valid_new_classid>");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				AdminHtml.showAdminHtml(activeChar, "charclasses.htm");
			}
			catch (NumberFormatException e)
			{
				activeChar.sendMessage("Usage: //setclass <valid_new_classid>");
			}
		}
		else if (command.startsWith("admin_settitle"))
		{
			try
			{
				String val = command.substring(15);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
				}
				else
				{
					return false;
				}
				player.setTitle(val);
				player.sendMessage("Your title has been changed by a GM");
				player.broadcastTitleInfo();
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of empty character title
				activeChar.sendMessage("You need to specify the new title.");
			}
		}
		else if (command.startsWith("admin_changename"))
		{
			try
			{
				String val = command.substring(17);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
				}
				else
				{
					return false;
				}
				if (CharNameTable.getInstance().getIdByName(val) > 0)
				{
					activeChar.sendMessage("Warning, player " + val + " already exists");
					return false;
				}
				player.setName(val);
				player.storeMe();
				
				activeChar.sendMessage("Changed name to " + val);
				player.sendMessage("Your name has been changed by a GM.");
				player.broadcastUserInfo();
				CommunityServerThread.getInstance().sendPacket(new WorldInfo(player, null, WorldInfo.TYPE_UPDATE_PLAYER_DATA));
				
				if (player.isInParty())
				{
					// Delete party window for other party members
					player.getParty().broadcastToPartyMembers(player, PartySmallWindowDeleteAll.STATIC_PACKET);
					for (L2PcInstance member : player.getParty().getMembers())
					{
						// And re-add
						if (member != player)
						{
							member.sendPacket(new PartySmallWindowAll(member, player.getParty()));
						}
					}
				}
				if (player.getClan() != null)
				{
					player.getClan().broadcastClanStatus();
				}
				
				RegionBBSManager.getInstance().changeCommunityBoard();
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of empty character name
				activeChar.sendMessage("Usage: //setname new_name_for_target");
			}
		}
		else if (command.startsWith("admin_setsex"))
		{
			L2Object target = activeChar.getTarget();
			L2PcInstance player = null;
			if (target instanceof L2PcInstance)
			{
				player = (L2PcInstance) target;
			}
			else
			{
				return false;
			}
			player.getAppearance().setSex(player.getAppearance().getSex() ? false : true);
			player.sendMessage("Your gender has been changed by a GM");
			player.broadcastUserInfo();
		}
		else if (command.startsWith("admin_setcolor"))
		{
			try
			{
				String val = command.substring(15);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
				}
				else
				{
					return false;
				}
				player.getAppearance().setNameColor(Integer.decode("0x" + val));
				player.sendMessage("Your name color has been changed by a GM");
				player.broadcastUserInfo();
			}
			catch (Exception e)
			{ // Case of empty color or invalid hex string
				activeChar.sendMessage("You need to specify a valid new color.");
			}
		}
		else if (command.startsWith("admin_settcolor"))
		{
			try
			{
				String val = command.substring(16);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
				}
				else
				{
					return false;
				}
				player.getAppearance().setTitleColor(Integer.decode("0x" + val));
				player.sendMessage("Your title color has been changed by a GM");
				player.broadcastUserInfo();
			}
			catch (Exception e)
			{ // Case of empty color or invalid hex string
				activeChar.sendMessage("You need to specify a valid new color.");
			}
		}
		else if (command.startsWith("admin_fullfood"))
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2PetInstance)
			{
				L2PetInstance targetPet = (L2PetInstance) target;
				targetPet.setCurrentFed(targetPet.getMaxFed());
				targetPet.sendPacket(new SetSummonRemainTime(targetPet.getMaxFed(), targetPet.getCurrentFed()));
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else if (command.startsWith("admin_remove_clan_penalty"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				if (st.countTokens() != 3)
				{
					activeChar.sendMessage("Usage: //remove_clan_penalty join|create charname");
					return false;
				}
				
				st.nextToken();
				
				boolean changeCreateExpiryTime = st.nextToken().equalsIgnoreCase("create");
				
				String playerName = st.nextToken();
				L2PcInstance player = null;
				player = L2World.getInstance().getPlayer(playerName);
				
				if (player == null)
				{
					Connection con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement ps = con.prepareStatement("UPDATE characters SET " + (changeCreateExpiryTime ? "clan_create_expiry_time" : "clan_join_expiry_time") + " WHERE char_name=? LIMIT 1");
					
					ps.setString(1, playerName);
					ps.execute();
				}
				else
				{
					// removing penalty
					if (changeCreateExpiryTime)
					{
						player.setClanCreateExpiryTime(0);
					}
					else
					{
						player.setClanJoinExpiryTime(0);
					}
				}
				
				activeChar.sendMessage("Clan penalty successfully removed to character: " + playerName);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (command.startsWith("admin_find_dualbox"))
		{
			int multibox = 2;
			try
			{
				String val = command.substring(19);
				multibox = Integer.parseInt(val);
				if (multibox < 1)
				{
					activeChar.sendMessage("Usage: //find_dualbox [number > 0]");
					return false;
				}
			}
			catch (Exception e)
			{
			}
			findDualbox(activeChar, multibox);
		}
		else if (command.startsWith("admin_strict_find_dualbox"))
		{
			int multibox = 2;
			try
			{
				String val = command.substring(26);
				multibox = Integer.parseInt(val);
				if (multibox < 1)
				{
					activeChar.sendMessage("Usage: //strict_find_dualbox [number > 0]");
					return false;
				}
			}
			catch (Exception e)
			{
			}
			findDualboxStrict(activeChar, multibox);
		}
		else if (command.startsWith("admin_tracert"))
		{
			String[] data = command.split(" ");
			L2PcInstance pl = null;
			if ((data.length > 1))
			{
				pl = L2World.getInstance().getPlayer(data[1]);
			}
			else
			{
				L2Object target = activeChar.getTarget();
				if (target instanceof L2PcInstance)
				{
					pl = (L2PcInstance) target;
				}
			}
			
			if (pl == null)
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return false;
			}
			
			final L2GameClient client = pl.getClient();
			if (client == null)
			{
				activeChar.sendMessage("Client is null.");
				return false;
			}
			
			if (client.isDetached())
			{
				activeChar.sendMessage("Client is detached.");
				return false;
			}
			
			String ip;
			int[][] trace = client.getTrace();
			for (int i = 0; i < trace.length; i++)
			{
				ip = "";
				for (int o = 0; o < trace[0].length; o++)
				{
					ip = ip + trace[i][o];
					if (o != (trace[0].length - 1))
					{
						ip = ip + ".";
					}
				}
				activeChar.sendMessage("Hop" + i + ": " + ip);
			}
		}
		else if (command.startsWith("admin_summon_info"))
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2Summon)
			{
				gatherSummonInfo((L2Summon) target, activeChar);
			}
			else
			{
				activeChar.sendMessage("Invalid target.");
			}
		}
		else if (command.startsWith("admin_unsummon"))
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2Summon)
			{
				((L2Summon) target).unSummon(((L2Summon) target).getOwner());
			}
			else
			{
				activeChar.sendMessage("Usable only with Pets/Summons");
			}
		}
		else if (command.startsWith("admin_summon_setlvl"))
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2PetInstance)
			{
				L2PetInstance pet = (L2PetInstance) target;
				try
				{
					String val = command.substring(20);
					int level = Integer.parseInt(val);
					long newexp, oldexp = 0;
					oldexp = pet.getStat().getExp();
					newexp = pet.getStat().getExpForLevel(level);
					if (oldexp > newexp)
					{
						pet.getStat().removeExp(oldexp - newexp);
					}
					else if (oldexp < newexp)
					{
						pet.getStat().addExp(newexp - oldexp);
					}
				}
				catch (Exception e)
				{
				}
			}
			else
			{
				activeChar.sendMessage("Usable only with Pets");
			}
		}
		else if (command.startsWith("admin_show_pet_inv"))
		{
			String val;
			int objId;
			L2Object target;
			try
			{
				val = command.substring(19);
				objId = Integer.parseInt(val);
				target = L2World.getInstance().getPet(objId);
			}
			catch (Exception e)
			{
				target = activeChar.getTarget();
			}
			
			if (target instanceof L2PetInstance)
			{
				activeChar.sendPacket(new GMViewItemList((L2PetInstance) target));
			}
			else
			{
				activeChar.sendMessage("Usable only with Pets");
			}
			
		}
		else if (command.startsWith("admin_partyinfo"))
		{
			String val;
			L2Object target;
			try
			{
				val = command.substring(16);
				target = L2World.getInstance().getPlayer(val);
				if (target == null)
				{
					target = activeChar.getTarget();
				}
			}
			catch (Exception e)
			{
				target = activeChar.getTarget();
			}
			
			if (target instanceof L2PcInstance)
			{
				if (((L2PcInstance) target).isInParty())
				{
					gatherPartyInfo((L2PcInstance) target, activeChar);
				}
				else
				{
					activeChar.sendMessage("Not in party.");
				}
			}
			else
			{
				activeChar.sendMessage("Invalid target.");
			}
			
		}
		else if (command.equals("admin_setnoble"))
		{
			L2PcInstance player = null;
			if (activeChar.getTarget() == null)
			{
				player = activeChar;
			}
			else if ((activeChar.getTarget() != null) && (activeChar.getTarget() instanceof L2PcInstance))
			{
				player = (L2PcInstance) activeChar.getTarget();
			}
			
			if (player != null)
			{
				player.setNoble(!player.isNoble());
				if (player.getObjectId() != activeChar.getObjectId())
				{
					activeChar.sendMessage("You've changed nobless status of: " + player.getName());
				}
				player.sendMessage("GM changed your nobless status!");
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void listCharacters(L2PcInstance activeChar, int page)
	{
		L2PcInstance[] players = L2World.getInstance().getAllPlayersArray();
		
		int maxCharactersPerPage = 20;
		int maxPages = players.length / maxCharactersPerPage;
		
		if (players.length > (maxCharactersPerPage * maxPages))
		{
			maxPages++;
		}
		
		// Check if number of users changed
		if (page > maxPages)
		{
			page = maxPages;
		}
		
		int charactersStart = maxCharactersPerPage * page;
		int charactersEnd = players.length;
		if ((charactersEnd - charactersStart) > maxCharactersPerPage)
		{
			charactersEnd = charactersStart + maxCharactersPerPage;
		}
		
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/charlist.htm");
		
		final StringBuilder replyMSG = new StringBuilder(1000);
		
		for (int x = 0; x < maxPages; x++)
		{
			int pagenr = x + 1;
			StringUtil.append(replyMSG, "<center><a action=\"bypass -h admin_show_characters ", String.valueOf(x), "\">Page ", String.valueOf(pagenr), "</a></center>");
		}
		
		adminReply.replace("%pages%", replyMSG.toString());
		replyMSG.setLength(0);
		
		for (int i = charactersStart; i < charactersEnd; i++)
		{
			// Add player info into new Table row
			StringUtil.append(replyMSG, "<tr><td width=80><a action=\"bypass -h admin_character_info ", players[i].getName(), "\">", players[i].getName(), "</a></td><td width=110>", ClassListData.getInstance().getClass(players[i].getClassId()).getClientCode(), "</td><td width=40>", String.valueOf(players[i].getLevel()), "</td></tr>");
		}
		
		adminReply.replace("%players%", replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showCharacterInfo(L2PcInstance activeChar, L2PcInstance player)
	{
		if (player == null)
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2PcInstance)
			{
				player = (L2PcInstance) target;
			}
			else
			{
				return;
			}
		}
		else
		{
			activeChar.setTarget(player);
		}
		gatherCharacterInfo(activeChar, player, "charinfo.htm");
	}
	
	/**
	 * Retrieve and replace player's info in filename htm file, sends it to activeChar as NpcHtmlMessage.
	 * @param activeChar
	 * @param player
	 * @param filename
	 */
	private void gatherCharacterInfo(L2PcInstance activeChar, L2PcInstance player, String filename)
	{
		String ip = "N/A";
		
		if (player == null)
		{
			activeChar.sendMessage("Player is null.");
			return;
		}
		
		final L2GameClient client = player.getClient();
		if (client == null)
		{
			activeChar.sendMessage("Client is null.");
		}
		else if (client.isDetached())
		{
			activeChar.sendMessage("Client is detached.");
		}
		else
		{
			ip = client.getConnection().getInetAddress().getHostAddress();
		}
		
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/" + filename);
		adminReply.replace("%name%", player.getName());
		adminReply.replace("%level%", String.valueOf(player.getLevel()));
		adminReply.replace("%clan%", String.valueOf(player.getClan() != null ? "<a action=\"bypass -h admin_clan_info " + player.getObjectId() + "\">" + player.getClan().getName() + "</a>" : null));
		adminReply.replace("%xp%", String.valueOf(player.getExp()));
		adminReply.replace("%sp%", String.valueOf(player.getSp()));
		adminReply.replace("%class%", ClassListData.getInstance().getClass(player.getClassId()).getClientCode());
		adminReply.replace("%ordinal%", String.valueOf(player.getClassId().ordinal()));
		adminReply.replace("%classid%", String.valueOf(player.getClassId()));
		adminReply.replace("%baseclass%", ClassListData.getInstance().getClass(player.getBaseClass()).getClientCode());
		adminReply.replace("%x%", String.valueOf(player.getX()));
		adminReply.replace("%y%", String.valueOf(player.getY()));
		adminReply.replace("%z%", String.valueOf(player.getZ()));
		adminReply.replace("%currenthp%", String.valueOf((int) player.getCurrentHp()));
		adminReply.replace("%maxhp%", String.valueOf(player.getMaxHp()));
		adminReply.replace("%karma%", String.valueOf(player.getKarma()));
		adminReply.replace("%currentmp%", String.valueOf((int) player.getCurrentMp()));
		adminReply.replace("%maxmp%", String.valueOf(player.getMaxMp()));
		adminReply.replace("%pvpflag%", String.valueOf(player.getPvpFlag()));
		adminReply.replace("%currentcp%", String.valueOf((int) player.getCurrentCp()));
		adminReply.replace("%maxcp%", String.valueOf(player.getMaxCp()));
		adminReply.replace("%pvpkills%", String.valueOf(player.getPvpKills()));
		adminReply.replace("%pkkills%", String.valueOf(player.getPkKills()));
		adminReply.replace("%currentload%", String.valueOf(player.getCurrentLoad()));
		adminReply.replace("%maxload%", String.valueOf(player.getMaxLoad()));
		adminReply.replace("%percent%", String.valueOf(Util.roundTo(((float) player.getCurrentLoad() / (float) player.getMaxLoad()) * 100, 2)));
		adminReply.replace("%patk%", String.valueOf(player.getPAtk(null)));
		adminReply.replace("%matk%", String.valueOf(player.getMAtk(null, null)));
		adminReply.replace("%pdef%", String.valueOf(player.getPDef(null)));
		adminReply.replace("%mdef%", String.valueOf(player.getMDef(null, null)));
		adminReply.replace("%accuracy%", String.valueOf(player.getAccuracy()));
		adminReply.replace("%evasion%", String.valueOf(player.getEvasionRate(null)));
		adminReply.replace("%critical%", String.valueOf(player.getCriticalHit(null, null)));
		adminReply.replace("%runspeed%", String.valueOf(player.getRunSpeed()));
		adminReply.replace("%patkspd%", String.valueOf(player.getPAtkSpd()));
		adminReply.replace("%matkspd%", String.valueOf(player.getMAtkSpd()));
		adminReply.replace("%access%", player.getAccessLevel().getLevel() + " (" + player.getAccessLevel().getName() + ")");
		adminReply.replace("%account%", player.getAccountName());
		adminReply.replace("%ip%", ip);
		adminReply.replace("%ai%", String.valueOf(player.getAI().getIntention().name()));
		adminReply.replace("%inst%", player.getInstanceId() > 0 ? "<tr><td>InstanceId:</td><td><a action=\"bypass -h admin_instance_spawns " + String.valueOf(player.getInstanceId()) + "\">" + String.valueOf(player.getInstanceId()) + "</a></td></tr>" : "");
		adminReply.replace("%noblesse%", player.isNoble() ? "Yes" : "No");
		activeChar.sendPacket(adminReply);
	}
	
	private void setTargetKarma(L2PcInstance activeChar, int newKarma)
	{
		// function to change karma of selected char
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		if (target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			return;
		}
		
		if (newKarma >= 0)
		{
			// for display
			int oldKarma = player.getKarma();
			// update karma
			player.setKarma(newKarma);
			// Common character information
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOUR_KARMA_HAS_BEEN_CHANGED_TO_S1);
			sm.addNumber(newKarma);
			player.sendPacket(sm);
			// Admin information
			activeChar.sendMessage("Successfully Changed karma for " + player.getName() + " from (" + oldKarma + ") to (" + newKarma + ").");
			if (Config.DEBUG)
			{
				_log.fine("[SET KARMA] [GM]" + activeChar.getName() + " Changed karma for " + player.getName() + " from (" + oldKarma + ") to (" + newKarma + ").");
			}
		}
		else
		{
			// tell admin of mistake
			activeChar.sendMessage("You must enter a value for karma greater than or equal to 0.");
			if (Config.DEBUG)
			{
				_log.fine("[SET KARMA] ERROR: [GM]" + activeChar.getName() + " entered an incorrect value for new karma: " + newKarma + " for " + player.getName() + ".");
			}
		}
	}
	
	private void adminModifyCharacter(L2PcInstance activeChar, String modifications)
	{
		L2Object target = activeChar.getTarget();
		
		if (!(target instanceof L2PcInstance))
		{
			return;
		}
		
		L2PcInstance player = (L2PcInstance) target;
		StringTokenizer st = new StringTokenizer(modifications);
		
		if (st.countTokens() != 6)
		{
			editCharacter(activeChar, null);
			return;
		}
		
		String hp = st.nextToken();
		String mp = st.nextToken();
		String cp = st.nextToken();
		String pvpflag = st.nextToken();
		String pvpkills = st.nextToken();
		String pkkills = st.nextToken();
		
		int hpval = Integer.parseInt(hp);
		int mpval = Integer.parseInt(mp);
		int cpval = Integer.parseInt(cp);
		int pvpflagval = Integer.parseInt(pvpflag);
		int pvpkillsval = Integer.parseInt(pvpkills);
		int pkkillsval = Integer.parseInt(pkkills);
		
		// Common character information
		player.sendMessage("Admin has changed your stats." + "  HP: " + hpval + "  MP: " + mpval + "  CP: " + cpval + "  PvP Flag: " + pvpflagval + " PvP/PK " + pvpkillsval + "/" + pkkillsval);
		player.setCurrentHp(hpval);
		player.setCurrentMp(mpval);
		player.setCurrentCp(cpval);
		player.setPvpFlag(pvpflagval);
		player.setPvpKills(pvpkillsval);
		player.setPkKills(pkkillsval);
		
		// Save the changed parameters to the database.
		player.storeMe();
		
		StatusUpdate su = new StatusUpdate(player);
		su.addAttribute(StatusUpdate.CUR_HP, hpval);
		su.addAttribute(StatusUpdate.MAX_HP, player.getMaxHp());
		su.addAttribute(StatusUpdate.CUR_MP, mpval);
		su.addAttribute(StatusUpdate.MAX_MP, player.getMaxMp());
		su.addAttribute(StatusUpdate.CUR_CP, cpval);
		su.addAttribute(StatusUpdate.MAX_CP, player.getMaxCp());
		player.sendPacket(su);
		
		// Admin information
		activeChar.sendMessage("Changed stats of " + player.getName() + "." + "  HP: " + hpval + "  MP: " + mpval + "  CP: " + cpval + "  PvP: " + pvpflagval + " / " + pvpkillsval);
		
		if (Config.DEBUG)
		{
			_log.fine("[GM]" + activeChar.getName() + " changed stats of " + player.getName() + ". " + " HP: " + hpval + " MP: " + mpval + " CP: " + cpval + " PvP: " + pvpflagval + " / " + pvpkillsval);
		}
		
		showCharacterInfo(activeChar, null); // Back to start
		
		player.broadcastPacket(new CharInfo(player));
		player.sendPacket(new UserInfo(player));
		player.broadcastPacket(new ExBrExtraUserInfo(player));
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.decayMe();
		player.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());
	}
	
	private void editCharacter(L2PcInstance activeChar, String targetName)
	{
		L2Object target = null;
		if (targetName != null)
		{
			target = L2World.getInstance().getPlayer(targetName);
		}
		else
		{
			target = activeChar.getTarget();
		}
		
		if (target instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) target;
			gatherCharacterInfo(activeChar, player, "charedit.htm");
		}
	}
	
	/**
	 * @param activeChar
	 * @param CharacterToFind
	 */
	private void findCharacter(L2PcInstance activeChar, String CharacterToFind)
	{
		int CharactersFound = 0;
		String name;
		L2PcInstance[] players = L2World.getInstance().getAllPlayersArray();
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/charfind.htm");
		
		final StringBuilder replyMSG = new StringBuilder(1000);
		
		for (L2PcInstance player : players)
		{ // Add player info into new Table row
			name = player.getName();
			if (name.toLowerCase().contains(CharacterToFind.toLowerCase()))
			{
				CharactersFound = CharactersFound + 1;
				StringUtil.append(replyMSG, "<tr><td width=80><a action=\"bypass -h admin_character_info ", name, "\">", name, "</a></td><td width=110>", ClassListData.getInstance().getClass(player.getClassId()).getClientCode(), "</td><td width=40>", String.valueOf(player.getLevel()), "</td></tr>");
			}
			if (CharactersFound > 20)
			{
				break;
			}
		}
		adminReply.replace("%results%", replyMSG.toString());
		
		final String replyMSG2;
		
		if (CharactersFound == 0)
		{
			replyMSG2 = "s. Please try again.";
		}
		else if (CharactersFound > 20)
		{
			adminReply.replace("%number%", " more than 20");
			replyMSG2 = "s.<br>Please refine your search to see all of the results.";
		}
		else if (CharactersFound == 1)
		{
			replyMSG2 = ".";
		}
		else
		{
			replyMSG2 = "s.";
		}
		
		adminReply.replace("%number%", String.valueOf(CharactersFound));
		adminReply.replace("%end%", replyMSG2);
		activeChar.sendPacket(adminReply);
	}
	
	/**
	 * @param activeChar
	 * @param IpAdress
	 * @throws IllegalArgumentException
	 */
	private void findCharactersPerIp(L2PcInstance activeChar, String IpAdress) throws IllegalArgumentException
	{
		boolean findDisconnected = false;
		
		if (IpAdress.equals("disconnected"))
		{
			findDisconnected = true;
		}
		else
		{
			if (!IpAdress.matches("^(?:(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2(?:[0-4][0-9]|5[0-5]))\\.){3}(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2(?:[0-4][0-9]|5[0-5]))$"))
			{
				throw new IllegalArgumentException("Malformed IPv4 number");
			}
		}
		L2PcInstance[] players = L2World.getInstance().getAllPlayersArray();
		int CharactersFound = 0;
		L2GameClient client;
		String name, ip = "0.0.0.0";
		final StringBuilder replyMSG = new StringBuilder(1000);
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/ipfind.htm");
		for (L2PcInstance player : players)
		{
			client = player.getClient();
			if (client == null)
			{
				continue;
			}
			
			if (client.isDetached())
			{
				if (!findDisconnected)
				{
					continue;
				}
			}
			else
			{
				if (findDisconnected)
				{
					continue;
				}
				
				ip = client.getConnection().getInetAddress().getHostAddress();
				if (!ip.equals(IpAdress))
				{
					continue;
				}
			}
			
			name = player.getName();
			CharactersFound = CharactersFound + 1;
			StringUtil.append(replyMSG, "<tr><td width=80><a action=\"bypass -h admin_character_info ", name, "\">", name, "</a></td><td width=110>", ClassListData.getInstance().getClass(player.getClassId()).getClientCode(), "</td><td width=40>", String.valueOf(player.getLevel()), "</td></tr>");
			
			if (CharactersFound > 20)
			{
				break;
			}
		}
		adminReply.replace("%results%", replyMSG.toString());
		
		final String replyMSG2;
		
		if (CharactersFound == 0)
		{
			replyMSG2 = "s. Maybe they got d/c? :)";
		}
		else if (CharactersFound > 20)
		{
			adminReply.replace("%number%", " more than " + String.valueOf(CharactersFound));
			replyMSG2 = "s.<br>In order to avoid you a client crash I won't <br1>display results beyond the 20th character.";
		}
		else if (CharactersFound == 1)
		{
			replyMSG2 = ".";
		}
		else
		{
			replyMSG2 = "s.";
		}
		adminReply.replace("%ip%", IpAdress);
		adminReply.replace("%number%", String.valueOf(CharactersFound));
		adminReply.replace("%end%", replyMSG2);
		activeChar.sendPacket(adminReply);
	}
	
	/**
	 * @param activeChar
	 * @param characterName
	 * @throws IllegalArgumentException
	 */
	private void findCharactersPerAccount(L2PcInstance activeChar, String characterName) throws IllegalArgumentException
	{
		if (characterName.matches(Config.CNAME_TEMPLATE))
		{
			String account = null;
			Map<Integer, String> chars;
			L2PcInstance player = L2World.getInstance().getPlayer(characterName);
			if (player == null)
			{
				throw new IllegalArgumentException("Player doesn't exist");
			}
			chars = player.getAccountChars();
			account = player.getAccountName();
			final StringBuilder replyMSG = new StringBuilder(chars.size() * 20);
			final NpcHtmlMessage adminReply = new NpcHtmlMessage();
			adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/accountinfo.htm");
			for (String charname : chars.values())
			{
				StringUtil.append(replyMSG, charname, "<br1>");
			}
			
			adminReply.replace("%characters%", replyMSG.toString());
			adminReply.replace("%account%", account);
			adminReply.replace("%player%", characterName);
			activeChar.sendPacket(adminReply);
		}
		else
		{
			throw new IllegalArgumentException("Malformed character name");
		}
	}
	
	/**
	 * @param activeChar
	 * @param multibox
	 */
	private void findDualbox(L2PcInstance activeChar, int multibox)
	{
		L2PcInstance[] players = L2World.getInstance().getAllPlayersArray();
		
		Map<String, List<L2PcInstance>> ipMap = new HashMap<>();
		
		String ip = "0.0.0.0";
		L2GameClient client;
		
		final Map<String, Integer> dualboxIPs = new HashMap<>();
		
		for (L2PcInstance player : players)
		{
			client = player.getClient();
			if ((client == null) || client.isDetached())
			{
				continue;
			}
			
			ip = client.getConnection().getInetAddress().getHostAddress();
			if (ipMap.get(ip) == null)
			{
				ipMap.put(ip, new ArrayList<L2PcInstance>());
			}
			ipMap.get(ip).add(player);
			
			if (ipMap.get(ip).size() >= multibox)
			{
				Integer count = dualboxIPs.get(ip);
				if (count == null)
				{
					dualboxIPs.put(ip, multibox);
				}
				else
				{
					dualboxIPs.put(ip, count + 1);
				}
			}
		}
		
		List<String> keys = new ArrayList<>(dualboxIPs.keySet());
		Collections.sort(keys, new Comparator<String>()
		{
			@Override
			public int compare(String left, String right)
			{
				return dualboxIPs.get(left).compareTo(dualboxIPs.get(right));
			}
		});
		Collections.reverse(keys);
		
		final StringBuilder results = new StringBuilder();
		for (String dualboxIP : keys)
		{
			StringUtil.append(results, "<a action=\"bypass -h admin_find_ip " + dualboxIP + "\">" + dualboxIP + " (" + dualboxIPs.get(dualboxIP) + ")</a><br1>");
		}
		
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/dualbox.htm");
		adminReply.replace("%multibox%", String.valueOf(multibox));
		adminReply.replace("%results%", results.toString());
		adminReply.replace("%strict%", "");
		activeChar.sendPacket(adminReply);
	}
	
	private void findDualboxStrict(L2PcInstance activeChar, int multibox)
	{
		L2PcInstance[] players = L2World.getInstance().getAllPlayersArray();
		
		Map<IpPack, List<L2PcInstance>> ipMap = new HashMap<>();
		
		L2GameClient client;
		
		final Map<IpPack, Integer> dualboxIPs = new HashMap<>();
		
		for (L2PcInstance player : players)
		{
			client = player.getClient();
			if ((client == null) || client.isDetached())
			{
				continue;
			}
			
			IpPack pack = new IpPack(client.getConnection().getInetAddress().getHostAddress(), client.getTrace());
			if (ipMap.get(pack) == null)
			{
				ipMap.put(pack, new ArrayList<L2PcInstance>());
			}
			ipMap.get(pack).add(player);
			
			if (ipMap.get(pack).size() >= multibox)
			{
				Integer count = dualboxIPs.get(pack);
				if (count == null)
				{
					dualboxIPs.put(pack, multibox);
				}
				else
				{
					dualboxIPs.put(pack, count + 1);
				}
			}
		}
		
		List<IpPack> keys = new ArrayList<>(dualboxIPs.keySet());
		Collections.sort(keys, new Comparator<IpPack>()
		{
			@Override
			public int compare(IpPack left, IpPack right)
			{
				return dualboxIPs.get(left).compareTo(dualboxIPs.get(right));
			}
		});
		Collections.reverse(keys);
		
		final StringBuilder results = new StringBuilder();
		for (IpPack dualboxIP : keys)
		{
			StringUtil.append(results, "<a action=\"bypass -h admin_find_ip " + dualboxIP.ip + "\">" + dualboxIP.ip + " (" + dualboxIPs.get(dualboxIP) + ")</a><br1>");
		}
		
		final NpcHtmlMessage adminReply = new NpcHtmlMessage();
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/dualbox.htm");
		adminReply.replace("%multibox%", String.valueOf(multibox));
		adminReply.replace("%results%", results.toString());
		adminReply.replace("%strict%", "strict_");
		activeChar.sendPacket(adminReply);
	}
	
	private final class IpPack
	{
		String ip;
		int[][] tracert;
		
		public IpPack(String ip, int[][] tracert)
		{
			this.ip = ip;
			this.tracert = tracert;
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = (prime * result) + ((ip == null) ? 0 : ip.hashCode());
			for (int[] array : tracert)
			{
				result = (prime * result) + Arrays.hashCode(array);
			}
			return result;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			IpPack other = (IpPack) obj;
			if (!getOuterType().equals(other.getOuterType()))
			{
				return false;
			}
			if (ip == null)
			{
				if (other.ip != null)
				{
					return false;
				}
			}
			else if (!ip.equals(other.ip))
			{
				return false;
			}
			for (int i = 0; i < tracert.length; i++)
			{
				for (int o = 0; o < tracert[0].length; o++)
				{
					if (tracert[i][o] != other.tracert[i][o])
					{
						return false;
					}
				}
			}
			return true;
		}
		
		private AdminEditChar getOuterType()
		{
			return AdminEditChar.this;
		}
	}
	
	private void gatherSummonInfo(L2Summon target, L2PcInstance activeChar)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage();
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/petinfo.htm");
		String name = target.getName();
		html.replace("%name%", name == null ? "N/A" : name);
		html.replace("%level%", Integer.toString(target.getLevel()));
		html.replace("%exp%", Long.toString(target.getStat().getExp()));
		String owner = target.getActingPlayer().getName();
		html.replace("%owner%", " <a action=\"bypass -h admin_character_info " + owner + "\">" + owner + "</a>");
		html.replace("%class%", target.getClass().getSimpleName());
		html.replace("%ai%", target.hasAI() ? String.valueOf(target.getAI().getIntention().name()) : "NULL");
		html.replace("%hp%", (int) target.getStatus().getCurrentHp() + "/" + target.getStat().getMaxHp());
		html.replace("%mp%", (int) target.getStatus().getCurrentMp() + "/" + target.getStat().getMaxMp());
		html.replace("%karma%", Integer.toString(target.getKarma()));
		html.replace("%undead%", target.isUndead() ? "yes" : "no");
		if (target instanceof L2PetInstance)
		{
			int objId = target.getActingPlayer().getObjectId();
			html.replace("%inv%", " <a action=\"bypass admin_show_pet_inv " + objId + "\">view</a>");
		}
		else
		{
			html.replace("%inv%", "none");
		}
		if (target instanceof L2PetInstance)
		{
			html.replace("%food%", ((L2PetInstance) target).getCurrentFed() + "/" + ((L2PetInstance) target).getPetLevelData().getPetMaxFeed());
			html.replace("%load%", ((L2PetInstance) target).getInventory().getTotalWeight() + "/" + ((L2PetInstance) target).getMaxLoad());
		}
		else
		{
			html.replace("%food%", "N/A");
			html.replace("%load%", "N/A");
		}
		activeChar.sendPacket(html);
	}
	
	private void gatherPartyInfo(L2PcInstance target, L2PcInstance activeChar)
	{
		boolean color = true;
		final NpcHtmlMessage html = new NpcHtmlMessage();
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/partyinfo.htm");
		StringBuilder text = new StringBuilder(400);
		for (L2PcInstance member : target.getParty().getMembers())
		{
			if (color)
			{
				text.append("<tr><td><table width=270 border=0 bgcolor=131210 cellpadding=2><tr><td width=30 align=right>");
			}
			else
			{
				text.append("<tr><td><table width=270 border=0 cellpadding=2><tr><td width=30 align=right>");
			}
			text.append(member.getLevel() + "</td><td width=130><a action=\"bypass -h admin_character_info " + member.getName() + "\">" + member.getName() + "</a>");
			text.append("</td><td width=110 align=right>" + member.getClassId().toString() + "</td></tr></table></td></tr>");
			color = !color;
		}
		html.replace("%player%", target.getName());
		html.replace("%party%", text.toString());
		activeChar.sendPacket(html);
	}
}