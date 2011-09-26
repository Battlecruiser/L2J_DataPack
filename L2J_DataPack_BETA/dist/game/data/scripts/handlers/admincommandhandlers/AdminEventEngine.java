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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.GmListTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.L2Event;
import com.l2jserver.gameserver.model.entity.L2Event.EventState;
import com.l2jserver.gameserver.network.serverpackets.CharInfo;
import com.l2jserver.gameserver.network.serverpackets.ExBrExtraUserInfo;
import com.l2jserver.gameserver.network.serverpackets.ItemList;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.network.serverpackets.UserInfo;
import com.l2jserver.util.StringUtil;


/**
 * This class handles following admin commands:
 * - admin = shows menu
 *
 * @version $Revision: 1.3.2.1.2.4 $ $Date: 2005/04/11 10:06:06 $
 */
public class AdminEventEngine implements IAdminCommandHandler
{
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_event",
		"admin_event_new",
		"admin_event_choose",
		"admin_event_store",
		"admin_event_set",
		"admin_event_change_teams_number",
		"admin_event_announce",
		"admin_event_panel",
		"admin_event_control_begin",
		"admin_event_control_teleport",
		"admin_add", "admin_event_see",
		"admin_event_del",
		"admin_delete_buffer",
		"admin_event_control_sit",
		"admin_event_name",
		"admin_event_control_kill",
		"admin_event_control_res",
		"admin_event_control_poly",
		"admin_event_control_unpoly",
		"admin_event_control_transform", 
		"admin_event_control_untransform",
		"admin_event_control_prize",
		"admin_event_control_chatban",
		"admin_event_control_kick",
		"admin_event_control_finish"
	};
	
	private static String tempBuffer = "";
	private static String tempName = "";
	private static boolean npcsDeleted = false;
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		try
		{
			if (command.equals("admin_event"))
			{
				if (L2Event.eventState != EventState.OFF)
					showEventControl(activeChar);
				else
					showMainPage(activeChar);
			}
			
			else if (command.equals("admin_event_new"))
			{
				showNewEventPage(activeChar);
			}
			else if (command.startsWith("admin_add"))
			{
				tempBuffer += command.substring(10);
				showNewEventPage(activeChar);
				
			}
			else if (command.startsWith("admin_event_see"))
			{
				String eventName = command.substring(16);
				try
				{
					NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
					
					DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("data/events/" + eventName)));
					BufferedReader inbr = new BufferedReader(new InputStreamReader(in));
					
					final String replyMSG = StringUtil.concat(
							"<html><body>" +
							"<center><font color=\"LEVEL\">",
							eventName,
							"</font><font color=\"FF0000\"> bY ",
							inbr.readLine(),
							"</font></center><br>" +
							"<br>",
							inbr.readLine(),
							"</body></html>"
					);
					adminReply.setHtml(replyMSG);
					activeChar.sendPacket(adminReply);
					inbr.close();
				}
				catch (Exception e)
				{
					
					e.printStackTrace();
					
				}
				
			}
			else if (command.startsWith("admin_event_del"))
			{
				String eventName = command.substring(16);
				File file = new File("data/events/" + eventName);
				file.delete();
				showMainPage(activeChar);
				
			}
			
			else if (command.startsWith("admin_event_name"))
			{
				tempName += command.substring(17);
				showNewEventPage(activeChar);
				
			}
			
			else if (command.equalsIgnoreCase("admin_delete_buffer"))
			{
				try
				{
					tempBuffer += tempBuffer.substring(0, tempBuffer.length() - 10);
					showNewEventPage(activeChar);
				}
				catch (Exception e)
				{
					tempBuffer = "";
				}
			}
			
			else if (command.startsWith("admin_event_store"))
			{
				
				try
				{
					FileOutputStream file = new FileOutputStream("data/events/" + tempName);
					PrintStream p = new PrintStream(file);
					p.println(activeChar.getName());
					p.println(tempBuffer);
					file.close();
					p.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				tempBuffer = "";
				tempName = "";
				showMainPage(activeChar);
			}
			else if (command.startsWith("admin_event_set"))
			{
				L2Event._eventName = command.substring(16);
				showEventParameters(activeChar, 2);
				
			}
			else if (command.startsWith("admin_event_change_teams_number"))
			{
				showEventParameters(activeChar, Integer.parseInt(command.substring(32)));
			}
			else if (command.startsWith("admin_event_panel"))
			{
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_announce"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(21));
				L2Event._npcId = Integer.parseInt(st.nextToken());
				L2Event._teamsNumber = Integer.parseInt(st.nextToken());
				String temp = " ";
				String temp2 = "";
				while (st.hasMoreElements())
				{
					temp += st.nextToken() + " ";
				}
				
				st = new StringTokenizer(temp, "-");
				
				Integer i = 1;
				
				while (st.hasMoreElements())
				{
					temp2 = st.nextToken();
					if (!temp2.equals(" "))
					{
						L2Event._teamNames.put(i++, temp2.substring(1, temp2.length() - 1));
					}
				}
				
				activeChar.sendMessage(L2Event.startEventParticipation());
				Announcements.getInstance().announceToAll(activeChar.getName() + " has started an event. You will find a participation NPC somewhere around you.");
				
				PlaySound _snd = new PlaySound(1, "B03_F", 0, 0, 0, 0, 0);
				activeChar.sendPacket(_snd);
				activeChar.broadcastPacket(_snd);
				
				NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
				
				final String replyMSG = StringUtil.concat(
						"<html><body>" +
						"<center><font color=\"LEVEL\">[ L2J EVENT ENGINE</font></center><br>" +
						"<center>The event <font color=\"LEVEL\">",
						L2Event._eventName,
						"</font> has been announced, now you can type //event_panel to see the event panel control</center><br>" +
						"</body></html>"
				);
				adminReply.setHtml(replyMSG);
				activeChar.sendPacket(adminReply);
			}
			else if (command.startsWith("admin_event_control_begin"))
			{
				// Starts the event and sends a message of the result
				activeChar.sendMessage(L2Event.startEvent());
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_finish"))
			{
				// Finishes the event and sends a message of the result
				activeChar.sendMessage(L2Event.finishEvent());
			}
			else if (command.startsWith("admin_event_control_teleport"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(29), "-");
				
				while (st.hasMoreElements())
				{
					int teamId = Integer.parseInt(st.nextToken());
					
					for (L2PcInstance player : L2Event._teams.get(teamId))
					{
						player.setTitle(L2Event._teamNames.get(teamId));
						player.teleToLocation(activeChar.getX(), activeChar.getY(), activeChar.getZ(), true);
						player.setInstanceId(activeChar.getInstanceId());
					}
				}
				showEventControl(activeChar);
			}
			
			else if (command.startsWith("admin_event_control_sit"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(24), "-");
				
				while (st.hasMoreElements())
				{
					// Integer.parseInt(st.nextToken()) == teamId
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
					{
						if (player.getEventStatus() == null)
							continue;
						
						player.getEventStatus().eventSitForced = !player.getEventStatus().eventSitForced;
						if (player.getEventStatus().eventSitForced)
							player.sitDown();
						else
							player.standUp();
					}
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_kill"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(25), "-");
				
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
						player.reduceCurrentHp(player.getMaxHp() + player.getMaxCp() + 1, activeChar, null);
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_res"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(24), "-");
				
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
					{
						if (player == null || !player.isDead())
							continue;
						player.restoreExp(100.0);
						player.doRevive();
						player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
						player.setCurrentCp(player.getMaxCp());
					}
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_poly"))
			{
				StringTokenizer st0 = new StringTokenizer(command.substring(25));
				StringTokenizer st = new StringTokenizer(st0.nextToken(), "-");
				String id = st0.nextToken();
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
					{
						player.getPoly().setPolyInfo("npc", id);
						player.teleToLocation(player.getX(), player.getY(), player.getZ(), true);
						CharInfo info1 = new CharInfo(player);
						player.broadcastPacket(info1);
						UserInfo info2 = new UserInfo(player);
						player.sendPacket(info2);
						player.broadcastPacket(new ExBrExtraUserInfo(player));
					}
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_unpoly"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(27), "-");
				
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
					{
						player.getPoly().setPolyInfo(null, "1");
						player.decayMe();
						player.spawnMe(player.getX(), player.getY(), player.getZ());
						CharInfo info1 = new CharInfo(player);
						player.broadcastPacket(info1);
						UserInfo info2 = new UserInfo(player);
						player.sendPacket(info2);
						player.broadcastPacket(new ExBrExtraUserInfo(player));
					}
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_transform"))
			{
				StringTokenizer st0 = new StringTokenizer(command.substring(30));
				StringTokenizer st = new StringTokenizer(st0.nextToken(), "-");
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
					{
						int id = Integer.parseInt(st0.nextToken());
						if (!TransformationManager.getInstance().transformPlayer(id, player))
							GmListTable.broadcastMessageToGMs("EventEngine: Unknow transformation id: " + id);
					}
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_untransform"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(32), "-");
				
				while (st.hasMoreElements())
				{
					for (L2PcInstance player : L2Event._teams.get(Integer.parseInt(st.nextToken())))
						player.stopTransformation(true);
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_kick"))
			{
				StringTokenizer st = new StringTokenizer(command.substring(25), "-");
				
				if (st.hasMoreElements())
				{
					L2PcInstance player = L2World.getInstance().getPlayer(st.nextToken());
					if (player != null)
						L2Event.removeAndResetPlayer(player);
				}
				else
				{
					if (activeChar.getTarget() != null && activeChar.getTarget() instanceof L2PcInstance)
						L2Event.removeAndResetPlayer((L2PcInstance) activeChar.getTarget());
				}
				showEventControl(activeChar);
			}
			else if (command.startsWith("admin_event_control_prize"))
			{
				StringTokenizer st0 = new StringTokenizer(command.substring(26));
				StringTokenizer st = new StringTokenizer(st0.nextToken(), "-");
				String n = st0.nextToken();
				StringTokenizer st1 = new StringTokenizer(n, "*");
				n = st1.nextToken();
				String type = "";
				if (st1.hasMoreElements())
					type = st1.nextToken();
				
				String id = st0.nextToken();
				while (st.hasMoreElements())
				{
					rewardTeam(activeChar, Integer.parseInt(st.nextToken()), Integer.parseInt(n), Integer.parseInt(id), type);
				}
				showEventControl(activeChar);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
			GmListTable.broadcastMessageToGMs("EventEngine: Error! Possible blank boxes while executing a command which requires a value in the box?"); 
		}
		
		return true;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	String showStoredEvents()
	{
		File dir = new File("data/events");
		String[] files = dir.list();
		
		if (files == null) {
			return "<font color=\"FF0000\"> No 'data/events' directory! <font> <BR>" + 
					"<font color=\"FF0000\"> UNABLE TO CREATE AN EVENT!!! Please create this folder. <font>";
		}
		
		final StringBuilder result = new StringBuilder(files.length * 500);
		
		for (int i = 0; i < files.length; i++) {
			final File file = new File("data/events/" + files[i]);
			final String fileName = file.getName();
			StringUtil.append(result,
					"<font color=\"LEVEL\">",
					fileName,
					" </font><br><button value=\"select\" action=\"bypass -h admin_event_set ",
					fileName,
					"\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"ver\" action=\"bypass -h admin_event_see ",
					fileName,
					"\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"delete\" action=\"bypass -h admin_event_del ",
					fileName,
					"\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br><br>"
			);
		}
		
		return result.toString();
	}
	
	public void showMainPage(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		
		final String replyMSG = StringUtil.concat(
				"<html><body>" +
				"<center><font color=\"LEVEL\">[ L2J EVENT ENGINE ]</font></center><br>" +
				"<br><center><button value=\"Create NEW event \" action=\"bypass -h admin_event_new\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">" +
				"<center><br>Stored Events<br></center>",
				showStoredEvents(),
				"</body></html>"
		);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	public void showNewEventPage(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		
		final StringBuilder replyMSG = StringUtil.startAppend(500,
				"<html><body>" +
				"<center><font color=\"LEVEL\">[ L2J EVENT ENGINE ]</font></center><br>" +
				"<br><center>Event's Title <br><font color=\"LEVEL\">"
		);
		
		if (tempName.isEmpty())
			replyMSG.append("Use //event_name text to insert a new title");
		else
			replyMSG.append(tempName);
		replyMSG.append("</font></center><br><br>Event's description<br>");
		if (tempBuffer.isEmpty())
			replyMSG.append("Use //add text o //delete_buffer to modify this text field");
		else
			replyMSG.append(tempBuffer);
		
		if (!(tempName.isEmpty() && tempBuffer.isEmpty()))
			replyMSG.append("<br><button value=\"Crear\" action=\"bypass -h admin_event_store\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
		
		replyMSG.append("</body></html>");
		
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	public void showEventParameters(L2PcInstance activeChar, int teamnumbers)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		
		final StringBuilder replyMSG = StringUtil.startAppend(
				1000 + teamnumbers * 150,
				"<html><body>" +
				"<center><font color=\"LEVEL\">[ L2J EVENT ENGINE ]</font></center><br>" +
				"<center><font color=\"LEVEL\">",
				L2Event._eventName,
				"</font></center><br>" +
				"<br><center><button value=\"Change number of teams to\" action=\"bypass -h admin_event_change_teams_number $event_teams_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"> <edit var=\"event_teams_number\" width=100 height=20><br><br>" +
				"<font color=\"LEVEL\">Team's Names</font><br>"
		);
		
		for (int i = 0; i < teamnumbers; i++) {
			StringUtil.append(replyMSG,
					String.valueOf(i + 1),
					".- <edit var=\"event_teams_name",
					String.valueOf(i + 1),
			"\" width=100 height=20><br>");
		}
		
		StringUtil.append(replyMSG,
				"<br><br>Announcer NPC id<edit var=\"event_npcid\" width=100 height=20><br><br><button value=\"Announce Event!!\" action=\"bypass -h admin_event_announce $event_npcid ",
				String.valueOf(teamnumbers),
		" ");
		
		for (int i = 0; i < teamnumbers; i++) {
			StringUtil.append(replyMSG,
					"$event_teams_name",
					String.valueOf(i + 1),
			" - ");
		}
		replyMSG.append("\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">" +
		"</body></html>");
		
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showEventControl(L2PcInstance activeChar)
	{
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		
		final StringBuilder replyMSG = StringUtil.startAppend(1000,
				"<html><body>" +
				"<center><font color=\"LEVEL\">[ L2J EVENT ENGINE ]</font></center><br><font color=\"LEVEL\">",
				L2Event._eventName,
				"</font><br><br><table width=200>" +
				"<tr><td>Apply this command to teams number </td><td><edit var=\"team_number\" width=100 height=15></td></tr>" +
				"<tr><td>&nbsp;</td></tr>"
		);
		if (!npcsDeleted) {
			replyMSG.append("<tr><td><button value=\"Start\" action=\"bypass -h admin_event_control_begin\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Destroys all event npcs so no more people can't participate now on</font></td></tr>");
		}
		
		replyMSG.append(
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Teleport\" action=\"bypass -h admin_event_control_teleport $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Teleports the specified team to your position</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Sit\" action=\"bypass -h admin_event_control_sit $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Sits/Stands up the team</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Kill\" action=\"bypass -h admin_event_control_kill $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Finish with the life of all the players in the selected team</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Resurrect\" action=\"bypass -h admin_event_control_res $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Resurrect Team's members</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Polymorph\" action=\"bypass -h admin_event_control_poly $team_number $poly_id\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><edit var=\"poly_id\" width=100 height=15><font color=\"LEVEL\">Polymorphs the team into the NPC with the id specified</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"UnPolymorph\" action=\"bypass -h admin_event_control_unpoly $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Unpolymorph the team</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Transform\" action=\"bypass -h admin_event_control_transform $team_number $transf_id\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><edit var=\"transf_id\" width=100 height=15><font color=\"LEVEL\">Transforms the team into the transformation with the id specified</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"UnTransform\" action=\"bypass -h admin_event_control_untransform $team_number\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Untransforms the team</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Give Item\" action=\"bypass -h admin_event_control_prize $team_number $n $id\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"> number <edit var=\"n\" width=100 height=15> item id <edit var=\"id\" width=100 height=15></td><td><font color=\"LEVEL\">Give the specified item id to every single member of the team, you can put 5*level, 5*kills or 5 in the number field for example</font></td></tr>" +
				"<tr><td>&nbsp;</td></tr>" +
				"<tr><td><button value=\"Kick Player\" action=\"bypass -h admin_event_control_kick $player_name\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><edit var=\"player_name\" width=100 height=15><font color=\"LEVEL\">Kicks the specified player from the event. Blank field kicks target.</font></td></tr>" +  
				"<tr><td>&nbsp;</td></tr>" +  
				"<tr><td><button value=\"End\" action=\"bypass -h admin_event_control_finish\" width=90 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td><font color=\"LEVEL\">Will finish the event teleporting back all the players</font></td></tr>" +
		"</table></body></html>");
		
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void rewardTeam(L2PcInstance activeChar, int team, int n, int id, String type)
	{
		int num = n;
		for (L2PcInstance player : L2Event._teams.get(team))
		{
			if (type.equalsIgnoreCase("level"))
				num = n * player.getLevel();
			else if (type.equalsIgnoreCase("kills") && player.getEventStatus() != null)
			{
				num = n * player.getEventStatus().kills.size();
			}
			else
				num = n;
			
			player.getInventory().addItem("Event", id, num, player, activeChar);
			player.sendPacket(new ItemList(player, true));
			
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
			adminReply.setHtml(
					"<html><body>" +
					"CONGRATULATIONS, you should have a present in your inventory" +
			"</body></html>");
			player.sendPacket(adminReply);
		}
	}
}
