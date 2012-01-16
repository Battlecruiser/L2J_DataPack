package handlers.admincommandhandlers;

import java.util.Map;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.GMAudit;
import com.l2jserver.util.StringUtil;


public class AdminInstanceZone implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_instancezone",
		"admin_instancezone_clear"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		String target = (activeChar.getTarget() != null) ? activeChar.getTarget().getName() : "no-target";
		GMAudit.auditGMAction(activeChar.getName(), command, target, "");
		
		if (command.startsWith("admin_instancezone_clear"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				
				st.nextToken();
				final L2PcInstance player = L2World.getInstance().getPlayer(st.nextToken());
				final int instanceId = Integer.parseInt(st.nextToken());
				final String name = InstanceManager.getInstance().getInstanceIdName(instanceId);
				InstanceManager.getInstance().deleteInstanceTime(player.getObjectId(), instanceId);
				activeChar.sendMessage("Instance zone " + name + " cleared for player "+player.getName());
				player.sendMessage("Admin cleared instance zone " + name + " for you");
				
				return true;
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Failed clearing instance time: " + e.getMessage());
				activeChar.sendMessage("Usage: //instancezone_clear <playername> [instanceId]");
				return false;
			}
		}
		else if (command.startsWith("admin_instancezone"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			command = st.nextToken();
			
			if (st.hasMoreTokens())
			{
				L2PcInstance player = null;
				String playername = st.nextToken();
				
				try
				{
					player = L2World.getInstance().getPlayer(playername);
				}
				catch (Exception e)
				{
				}
				
				if (player != null)
					display(player, activeChar);
				else
				{
					activeChar.sendMessage("The player " + playername + " is not online");
					activeChar.sendMessage("Usage: //instancezone [playername]");
					return false;
				}
			}
			else if (activeChar.getTarget() != null)
			{
				if (activeChar.getTarget() instanceof L2PcInstance)
					display((L2PcInstance) activeChar.getTarget(), activeChar);
			}
			else
				display(activeChar, activeChar);
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void display(L2PcInstance player, L2PcInstance activeChar)
	{
		Map<Integer, Long> instanceTimes = InstanceManager.getInstance().getAllInstanceTimes(player.getObjectId());
		
		final StringBuilder html = StringUtil.startAppend(500 + instanceTimes.size() * 200,
				"<html><center><table width=260><tr>"+
				"<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"+
				"<td width=180><center>Character Instances</center></td>"+
				"<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"+
				"</tr></table><br><font color=\"LEVEL\">Instances for ",
				player.getName(),
				"</font><center><br>" +
				"<table>" +
				"<tr><td width=150>Name</td><td width=50>Time</td><td width=70>Action</td></tr>"
		);
		
		
		for (int id : instanceTimes.keySet())
		{
			int hours = 0;
			int minutes = 0;
			long remainingTime = (instanceTimes.get(id) - System.currentTimeMillis()) / 1000;
			if (remainingTime > 0)
			{
				hours = (int) (remainingTime / 3600);
				minutes = (int) ((remainingTime % 3600) / 60);
			}
			
			StringUtil.append(html,
					"<tr><td>",
					InstanceManager.getInstance().getInstanceIdName(id),
					"</td><td>",
					String.valueOf(hours),
					":",
					String.valueOf(minutes),
					"</td><td><button value=\"Clear\" action=\"bypass -h admin_instancezone_clear ",
					player.getName(),
					" ",
					String.valueOf(id),
					"\" width=60 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr>"
			);
		}
		
		StringUtil.append(html, "</table></html>");
		
		NpcHtmlMessage ms = new NpcHtmlMessage(1);
		ms.setHtml(html.toString());
		
		activeChar.sendPacket(ms);
	}
}