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
package handlers.actionhandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2DropCategory;
import com.l2jserver.gameserver.model.L2DropData;
import com.l2jserver.gameserver.model.MobGroupTable;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2ControllableMobInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.skills.Stats;
import com.l2jserver.gameserver.templates.item.L2Item;
import com.l2jserver.gameserver.util.StringUtil;

public class L2NpcActionShift implements IActionHandler
{
	/**
	 * Manage and Display the GM console to modify the L2NpcInstance (GM only).<BR><BR>
	 * 
	 * <B><U> Actions (If the L2PcInstance is a GM only)</U> :</B><BR><BR>
	 * <li>Set the L2NpcInstance as target of the L2PcInstance player (if necessary)</li>
	 * <li>Send a Server->Client packet MyTargetSelected to the L2PcInstance player (display the select window)</li>
	 * <li>If L2NpcInstance is autoAttackable, send a Server->Client packet StatusUpdate to the L2PcInstance in order to update L2NpcInstance HP bar </li>
	 * <li>Send a Server->Client NpcHtmlMessage() containing the GM console about this L2NpcInstance </li><BR><BR>
	 * 
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : Each group of Server->Client packet must be terminated by a ActionFailed packet in order to avoid
	 * that client wait an other packet</B></FONT><BR><BR>
	 * 
	 * <B><U> Example of use </U> :</B><BR><BR>
	 * <li> Client packet : Action</li><BR><BR>
	 */
	public boolean action(L2PcInstance activeChar, L2Character target, boolean interact)
	{
		// Check if the L2PcInstance is a GM
		if (activeChar.getAccessLevel().isGm())
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);

			// Send a Server->Client packet MyTargetSelected to the L2PcInstance activeChar
			// The activeChar.getLevel() - getLevel() permit to display the correct color in the select window
			MyTargetSelected my = new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - target.getLevel());
			activeChar.sendPacket(my);

			// Check if the activeChar is attackable (without a forced attack)
			if (target.isAutoAttackable(activeChar))
			{
				// Send a Server->Client packet StatusUpdate of the L2NpcInstance to the L2PcInstance to update its HP bar
				StatusUpdate su = new StatusUpdate(target.getObjectId());
				su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
				su.addAttribute(StatusUpdate.MAX_HP, target.getMaxHp());
				activeChar.sendPacket(su);
			}

			// Send a Server->Client NpcHtmlMessage() containing the GM console about this L2NpcInstance
			NpcHtmlMessage html = new NpcHtmlMessage(0);
                        final StringBuilder html1 = StringUtil.startAppend(500,
                                "<html><body><center><font color=\"LEVEL\">NPC Info</font></center><br>" +
                                "Instance Type: ",
                                getClass().getSimpleName(),
                                "<br1>Faction: ",
                                ((L2Npc)target).getFactionId() != null ? ((L2Npc)target).getFactionId() : "null"
                                );
                        StringUtil.append(html1,
                        		"<br1>Coords: ",
                        		String.valueOf(target.getX()),
                        		", ",
                        		String.valueOf(target.getY()),
                        		", ",
                        		String.valueOf(target.getZ())
                        		);
                        if (((L2Npc)target).getSpawn() != null)
                        	StringUtil.append(html1,
                        			"<br1>Spawn: ",
                        			String.valueOf(((L2Npc)target).getSpawn().getLocx()),
                        			", ",
                        			String.valueOf(((L2Npc)target).getSpawn().getLocy()),
                        			", ",
                        			String.valueOf(((L2Npc)target).getSpawn().getLocz()),
                                    " ; Loc ID: ",
                                    String.valueOf(((L2Npc)target).getSpawn().getLocation()),
                                    "<br1>Distance from spawn 2D: ",
                                    String.valueOf((int)Math.sqrt(target.getPlanDistanceSq(((L2Npc)target).getSpawn().getLocx(), ((L2Npc)target).getSpawn().getLocy()))),
                                    " ; 3D: ",
                                    String.valueOf((int)Math.sqrt(target.getDistanceSq(((L2Npc)target).getSpawn().getLocx(), ((L2Npc)target).getSpawn().getLocy(), ((L2Npc)target).getSpawn().getLocz())))
                            );

			if (target.isInstanceType(InstanceType.L2ControllableMobInstance))
			{
				StringUtil.append(html1,
						"<br1>Mob Group: ",
						String.valueOf(MobGroupTable.getInstance().getGroupForMob((L2ControllableMobInstance) target).getGroupId()),
						"<br>"
				);
			}
			else
			{
				StringUtil.append(html1,
						"<br1>Respawn Time: ",
						(((L2Npc)target).getSpawn() != null ? String.valueOf(((L2Npc)target).getSpawn().getRespawnDelay() / 1000) : "?"),
						"  Seconds<br>"
				);
			}

			StringUtil.append(html1,
					"<table border=\"0\" width=\"100%\">" +
					"<tr><td>Level</td><td>",
					String.valueOf(target.getLevel()),
					"</td><td>    </td><td>NPC ID</td><td>",
					String.valueOf(((L2Npc)target).getTemplate().npcId),
					"</td></tr>" +
					"<tr><td>Aggro</td><td>" +
					String.valueOf((target.isInstanceType(InstanceType.L2Attackable)) ? ((L2Attackable) target).getAggroRange() : 0),
					"</td><td>    </td><td>Object ID</td><td>",
					String.valueOf(target.getObjectId()),
					"</td></tr>" +
					"<tr><td>Castle</td><td>",
					String.valueOf(((L2Npc)target).getCastle().getCastleId()),
					"</td><td>    </td><td>AI </td><td>",
					(target.hasAI() ? String.valueOf(target.getAI().getIntention().name()) : "NULL"),
					"</td></tr>" +
					"</table><br>" +
					"<font color=\"LEVEL\">Combat</font>" +
					"<table border=\"0\" width=\"100%\">" +
					"<tr><td>Current HP</td><td>",
					String.valueOf(target.getCurrentHp()),
					"</td><td>Current MP</td><td>",
					String.valueOf(target.getCurrentMp()),
					"</td></tr>" +
					"<tr><td>Max.HP</td><td>",
					String.valueOf((int) (target.getMaxHp() / target.getStat().calcStat(Stats.MAX_HP, 1, target, null))),
					"*",
					String.valueOf((int) (target.getStat().calcStat(Stats.MAX_HP, 1, target, null))),
					"</td><td>Max.MP</td><td>",
					String.valueOf(target.getMaxMp()),
					"</td></tr>" +
					"<tr><td>P.Atk.</td><td>",
					String.valueOf(target.getPAtk(null)),
					"</td><td>M.Atk.</td><td>",
					String.valueOf(target.getMAtk(null, null)),
					"</td></tr>" +
					"<tr><td>P.Def.</td><td>",
					String.valueOf(target.getPDef(null)),
					"</td><td>M.Def.</td><td>",
					String.valueOf(target.getMDef(null, null)),
					"</td></tr>" +
					"<tr><td>Accuracy</td><td>" +
					String.valueOf(target.getAccuracy()),
					"</td><td>Evasion</td><td>",
					String.valueOf(target.getEvasionRate(null)),
					"</td></tr>" +
					"<tr><td>Critical</td><td>",
					String.valueOf(target.getCriticalHit(null, null)),
					"</td><td>Speed</td><td>",
					String.valueOf(target.getRunSpeed()),
					"</td></tr>" +
					"<tr><td>Atk.Speed</td><td>",
					String.valueOf(target.getPAtkSpd()),
					"</td><td>Cast.Speed</td><td>",
					String.valueOf(target.getMAtkSpd()),
					"</td></tr>" +
					"</table><br>" +
					"<font color=\"LEVEL\">Basic Stats</font>" +
					"<table border=\"0\" width=\"100%\">" +
					"<tr><td>STR</td><td>",
					String.valueOf(target.getSTR()),
					"</td><td>DEX</td><td>",
					String.valueOf(target.getDEX()),
					"</td><td>CON</td><td>",
					String.valueOf(target.getCON()),
					"</td></tr>" +
					"<tr><td>INT</td><td>",
					String.valueOf(target.getINT()),
					"</td><td>WIT</td><td>",
					String.valueOf(target.getWIT()),
					"</td><td>MEN</td><td>",
					String.valueOf(target.getMEN()),
					"</td></tr>" +
					"</table>" +
					"<br><center><table><tr><td><button value=\"Edit NPC\" action=\"bypass -h admin_edit_npc ",
					String.valueOf(((L2Npc)target).getTemplate().npcId),
					"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br1></td>" +
					"<td><button value=\"Kill\" action=\"bypass -h admin_kill\" width=40 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><br1></tr>" +
					"<tr><td><button value=\"Show DropList\" action=\"bypass -h admin_show_droplist ",
					String.valueOf(((L2Npc)target).getTemplate().npcId),
					"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr>" +
					"<td><button value=\"Delete\" action=\"bypass -h admin_delete\" width=40 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr>" +
					"<tr><td><button value=\"Show SkillList\" action=\"bypass -h admin_show_skilllist_npc ",
					String.valueOf(((L2Npc)target).getTemplate().npcId),
				 	"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td></td></tr></table></center><br></body></html>"
					);

			html.setHtml(html1.toString());
			activeChar.sendPacket(html);
		}
		else if (Config.ALT_GAME_VIEWNPC)
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);

			// Send a Server->Client packet MyTargetSelected to the L2PcInstance activeChar
			// The activeChar.getLevel() - getLevel() permit to display the correct color in the select window
			MyTargetSelected my = new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - target.getLevel());
			activeChar.sendPacket(my);

			// Check if the activeChar is attackable (without a forced attack)
			if (target.isAutoAttackable(activeChar))
			{
				// Send a Server->Client packet StatusUpdate of the L2NpcInstance to the L2PcInstance to update its HP bar
				StatusUpdate su = new StatusUpdate(target.getObjectId());
				su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
				su.addAttribute(StatusUpdate.MAX_HP, target.getMaxHp());
				activeChar.sendPacket(su);
			}

			NpcHtmlMessage html = new NpcHtmlMessage(0);
			final StringBuilder html1 = StringUtil.startAppend(
					1000,
					"<html><body>" +
					"<br><center><font color=\"LEVEL\">[Combat Stats]</font></center>" +
					"<table border=0 width=\"100%\">" +
					"<tr><td>Max.HP</td><td>",
					String.valueOf((int) (target.getMaxHp() / target.getStat().calcStat(Stats.MAX_HP, 1, target, null))),
					"*",
					String.valueOf((int) target.getStat().calcStat(Stats.MAX_HP, 1, target, null)),
					"</td><td>Max.MP</td><td>",
					String.valueOf(target.getMaxMp()),
					"</td></tr>" +
					"<tr><td>P.Atk.</td><td>",
					String.valueOf(target.getPAtk(null)),
					"</td><td>M.Atk.</td><td>",
					String.valueOf(target.getMAtk(null, null)),
					"</td></tr>" +
					"<tr><td>P.Def.</td><td>",
					String.valueOf(target.getPDef(null)),
					"</td><td>M.Def.</td><td>",
					String.valueOf(target.getMDef(null, null)),
					"</td></tr>" +
					"<tr><td>Accuracy</td><td>",
					String.valueOf(target.getAccuracy()),
					"</td><td>Evasion</td><td>",
					String.valueOf(target.getEvasionRate(null)),
					"</td></tr>" +
					"<tr><td>Critical</td><td>",
					String.valueOf(target.getCriticalHit(null, null)),
					"</td><td>Speed</td><td>",
					String.valueOf(target.getRunSpeed()),
					"</td></tr>" +
					"<tr><td>Atk.Speed</td><td>",
					String.valueOf(target.getPAtkSpd()),
					"</td><td>Cast.Speed</td><td>",
					String.valueOf(target.getMAtkSpd()),
					"</td></tr>" +
					"<tr><td>Race</td><td>",
					((L2Npc)target).getTemplate().getRace().toString(),
					"</td><td></td><td></td></tr>" +
					"</table>" +
					"<br><center><font color=\"LEVEL\">[Basic Stats]</font></center>" +
					"<table border=0 width=\"100%\">" +
					"<tr><td>STR</td><td>",
					String.valueOf(target.getSTR()),
					"</td><td>DEX</td><td>",
					String.valueOf(target.getDEX()),
					"</td><td>CON</td><td>",
					String.valueOf(target.getCON()),
					"</td></tr>" +
					"<tr><td>INT</td><td>",
					String.valueOf(target.getINT()),
					"</td><td>WIT</td><td>",
					String.valueOf(target.getWIT()),
					"</td><td>MEN</td><td>",
					String.valueOf(target.getMEN()),
					"</td></tr>" +
					"</table>"
					);

			if (((L2Npc)target).getTemplate().getDropData() != null)
			{
				StringUtil.append(html1,
						"<br><center><font color=\"LEVEL\">[Drop Info]</font></center>" +
						"<br>Rates legend: <font color=\"ff0000\">50%+</font> <font color=\"00ff00\">30%+</font> <font color=\"0000ff\">less than 30%</font>" +
						"<table border=0 width=\"100%\">"
						);
				for (L2DropCategory cat : ((L2Npc)target).getTemplate().getDropData())
				{
					for (L2DropData drop : cat.getAllDrops())
					{
						final L2Item item = ItemTable.getInstance().getTemplate(drop.getItemId());
						if (item == null)
							continue;

						final String color;

						if (drop.getChance() >= 500000)
							color = "ff0000";
						else if (drop.getChance() >= 300000)
							color = "00ff00";
						else
							color = "0000ff";

						StringUtil.append(html1,
								"<tr><td><font color=\"",
								color,
								"\">",
								item.getName(),
								"</font></td><td>",
								(drop.isQuestDrop() ? "Quest" : (cat.isSweep() ? "Sweep" : "Drop")),
								"</td></tr>"
								);
					}
				}
				html1.append("</table>");
			}
			html1.append("</body></html>");

			html.setHtml(html1.toString());
			activeChar.sendPacket(html);
		}
		return true;
	}

	public InstanceType getInstanceType()
	{
		return InstanceType.L2Npc;
	}
}
