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
package handlers.admincommandhandlers;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.CastleManager;
import com.l2jserver.gameserver.instancemanager.ClanHallManager;
import com.l2jserver.gameserver.instancemanager.FortManager;
import com.l2jserver.gameserver.instancemanager.SiegeManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2ClanMember;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.communityserver.CommunityServerThread;
import com.l2jserver.gameserver.network.communityserver.writepackets.WorldInfo;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author ThE_PuNiSHeR a.k.a UnAfraid
 */
public class AdminClan implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_clan_info",
		"admin_clan_changeleader"
	};
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String cmd = st.nextToken();
		if (cmd.startsWith("admin_clan_info"))
		{
			try
			{
               int objectId = 0;
               try
               {
                   objectId = Integer.parseInt(st.nextToken());
               }
               catch (NoSuchElementException NSEE)
               {
                   objectId = activeChar.getTargetId();
               }
				L2PcInstance player = L2World.getInstance().getPlayer(objectId);
				if (player != null)
				{
					L2Clan clan = player.getClan();
					if (clan != null)
					{
						try
						{
							NpcHtmlMessage msg = new NpcHtmlMessage(0);
							String htm = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/claninfo.htm");
							msg.setHtml(htm.toString());
							msg.replace("%clan_name%", clan.getName());
							msg.replace("%clan_leader%", clan.getLeaderName());
							msg.replace("%clan_level%", String.valueOf(clan.getLevel()));
							msg.replace("%clan_has_castle%", clan.getHasCastle() > 0 ? CastleManager.getInstance().getCastleById(clan.getHasCastle()).getName() : "No");
							msg.replace("%clan_has_clanhall%", clan.getHasHideout() > 0 ? ClanHallManager.getInstance().getClanHallById(clan.getHasHideout()).getName() : "No");
							msg.replace("%clan_has_fortress%", clan.getHasFort() > 0 ? FortManager.getInstance().getFortById(clan.getHasFort()).getName() : "No");
							msg.replace("%clan_points%", String.valueOf(clan.getReputationScore()));
							msg.replace("%clan_players_count%", String.valueOf(clan.getMembersCount()));
							msg.replace("%clan_ally%", clan.getAllyId() > 0 ? clan.getAllyName() : "Not in ally");
							msg.replace("%current_player_objectId%", String.valueOf(objectId));
							msg.replace("%current_player_name%", player.getName());
							activeChar.sendPacket(msg);
							
						}
						catch (NullPointerException npe)
						{
							npe.printStackTrace();
						}
						
					}
					else
					{
						activeChar.sendMessage("Clan not found.");
						return false;
					}
				}
				else
				{
					activeChar.sendMessage("Player is offline!");
					return false;
				}
			}
			catch (NumberFormatException nfe)
			{
				activeChar.sendMessage("This shouldn't happening");
				return false;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (cmd.startsWith("admin_clan_changeleader"))
		{
			try
			{
				int objectId = Integer.parseInt(st.nextToken());
				
				L2PcInstance player = L2World.getInstance().getPlayer(objectId);
				if (player != null)
				{
					L2Clan clan = player.getClan();
					if (clan == null)
					{
						activeChar.sendMessage("Player don't have clan");
						return false;
					}
					for (L2ClanMember member : clan.getMembers())
					{
						if (member.getObjectId() == player.getObjectId())
						{
							L2PcInstance exLeader = clan.getLeader().getPlayerInstance();
							if (exLeader != null)
							{
								SiegeManager.getInstance().removeSiegeSkills(exLeader);
								exLeader.setClan(clan);
								exLeader.setClanPrivileges(L2Clan.CP_NOTHING);
								exLeader.broadcastUserInfo();
								exLeader.setPledgeClass(exLeader.getClan().getClanMember(exLeader.getObjectId()).calculatePledgeClass(exLeader));
								exLeader.broadcastUserInfo();
								exLeader.checkItemRestriction();
							}
							else
							{
								// TODO: with query?
							}
							
							clan.setLeader(member);
							clan.updateClanInDB();
							
							L2PcInstance newLeader = member.getPlayerInstance();
							newLeader.setClan(clan);
							newLeader.setPledgeClass(member.calculatePledgeClass(newLeader));
							newLeader.setClanPrivileges(L2Clan.CP_ALL);
							
							if (clan.getLevel() >= SiegeManager.getInstance().getSiegeClanMinLevel())
								SiegeManager.getInstance().addSiegeSkills(newLeader);
							
							newLeader.broadcastUserInfo();
							
							clan.broadcastClanStatus();
							
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.CLAN_LEADER_PRIVILEGES_HAVE_BEEN_TRANSFERRED_TO_C1);
							sm.addString(newLeader.getName());
							clan.broadcastToOnlineMembers(sm);
							activeChar.sendMessage("Clan leader has been changed!");
							CommunityServerThread.getInstance().sendPacket(new WorldInfo(null, clan, WorldInfo.TYPE_UPDATE_CLAN_DATA));
						}
					}
				}
				else
				{
					activeChar.sendMessage("Player is offline");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
