/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package handlers.bypasshandlers;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.MultisellData;
import com.l2jserver.gameserver.datatables.NpcBufferTable;
import com.l2jserver.gameserver.datatables.NpcBufferTable.NpcBufferData;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2OlympiadManagerInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.olympiad.Olympiad;
import com.l2jserver.gameserver.model.olympiad.OlympiadManager;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExHeroList;
import com.l2jserver.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author DS
 */
public class OlympiadManagerLink implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"olympiaddesc",
		"olympiadnoble",
		"olybuff",
		"olympiad"
	};
	
	private static final String FEWER_THAN = "Fewer than " + String.valueOf(Config.ALT_OLY_REG_DISPLAY);
	private static final String MORE_THAN = "More than " + String.valueOf(Config.ALT_OLY_REG_DISPLAY);
	private static final int GATE_PASS = Config.ALT_OLY_COMP_RITEM;
	
	@Override
	public final boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2OlympiadManagerInstance))
		{
			return false;
		}
		
		try
		{
			if (command.toLowerCase().startsWith("olympiaddesc"))
			{
				int val = Integer.parseInt(command.substring(13, 14));
				String suffix = command.substring(14);
				((L2OlympiadManagerInstance) target).showChatWindow(activeChar, val, suffix);
			}
			else if (command.toLowerCase().startsWith("olympiadnoble"))
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(target.getObjectId());
				if (activeChar.isCursedWeaponEquipped())
				{
					html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_cursed_weapon.htm");
					activeChar.sendPacket(html);
					return false;
				}
				if (activeChar.getClassIndex() != 0)
				{
					html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_sub.htm");
					html.replace("%objectId%", String.valueOf(target.getObjectId()));
					activeChar.sendPacket(html);
					return false;
				}
				if (!activeChar.isNoble() || (activeChar.getClassId().level() < 3))
				{
					html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_thirdclass.htm");
					html.replace("%objectId%", String.valueOf(target.getObjectId()));
					activeChar.sendPacket(html);
					return false;
				}
				
				int passes;
				int val = Integer.parseInt(command.substring(14));
				switch (val)
				{
					case 0: // H5 match selection
						if (!OlympiadManager.getInstance().isRegistered(activeChar))
						{
							final int olympiad_round = 0; // TODO : implement me
							final int olympiad_week = 0; // TODO: implement me
							final int olympiad_participant = 0; // TODO: implement me
							
							html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_desc2a.htm");
							html.replace("%objectId%", String.valueOf(target.getObjectId()));
							html.replace("%olympiad_round%", String.valueOf(olympiad_round));
							html.replace("%olympiad_week%", String.valueOf(olympiad_week));
							html.replace("%olympiad_participant%", String.valueOf(olympiad_participant));
							activeChar.sendPacket(html);
						}
						else
						{
							html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_unregister.htm");
							html.replace("%objectId%", String.valueOf(target.getObjectId()));
							activeChar.sendPacket(html);
						}
						break;
					case 1: // unregister
						OlympiadManager.getInstance().unRegisterNoble(activeChar);
						break;
					case 2: // show waiting list | TODO: cleanup (not used anymore)
						final int nonClassed = OlympiadManager.getInstance().getRegisteredNonClassBased().size();
						final int teams = OlympiadManager.getInstance().getRegisteredTeamsBased().size();
						final Collection<List<Integer>> allClassed = OlympiadManager.getInstance().getRegisteredClassBased().values();
						int classed = 0;
						if (!allClassed.isEmpty())
						{
							for (List<Integer> cls : allClassed)
							{
								if (cls != null)
								{
									classed += cls.size();
								}
							}
						}
						html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_registered.htm");
						if (Config.ALT_OLY_REG_DISPLAY > 0)
						{
							html.replace("%listClassed%", classed < Config.ALT_OLY_REG_DISPLAY ? FEWER_THAN : MORE_THAN);
							html.replace("%listNonClassedTeam%", teams < Config.ALT_OLY_REG_DISPLAY ? FEWER_THAN : MORE_THAN);
							html.replace("%listNonClassed%", nonClassed < Config.ALT_OLY_REG_DISPLAY ? FEWER_THAN : MORE_THAN);
						}
						else
						{
							html.replace("%listClassed%", String.valueOf(classed));
							html.replace("%listNonClassedTeam%", String.valueOf(teams));
							html.replace("%listNonClassed%", String.valueOf(nonClassed));
						}
						html.replace("%objectId%", String.valueOf(target.getObjectId()));
						activeChar.sendPacket(html);
						break;
					case 3: // There are %points% Grand Olympiad points granted for this event. | TODO: cleanup (not used anymore)
						int points = Olympiad.getInstance().getNoblePoints(activeChar.getObjectId());
						html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_points1.htm");
						html.replace("%points%", String.valueOf(points));
						html.replace("%objectId%", String.valueOf(target.getObjectId()));
						activeChar.sendPacket(html);
						break;
					case 4: // register non classed
						OlympiadManager.getInstance().registerNoble(activeChar, CompetitionType.NON_CLASSED);
						break;
					case 5: // register classed
						OlympiadManager.getInstance().registerNoble(activeChar, CompetitionType.CLASSED);
						break;
					case 6: // request tokens reward
						passes = Olympiad.getInstance().getNoblessePasses(activeChar, false);
						if (passes > 0)
						{
							html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_settle.htm");
							html.replace("%objectId%", String.valueOf(target.getObjectId()));
							activeChar.sendPacket(html);
						}
						else
						{
							html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_nopoints2.htm");
							html.replace("%objectId%", String.valueOf(target.getObjectId()));
							activeChar.sendPacket(html);
						}
						break;
					case 7: // Equipment Rewards
						MultisellData.getInstance().separateAndSend(102, activeChar, (L2Npc) target, false);
						break;
					case 8: // Misc. Rewards
						MultisellData.getInstance().separateAndSend(103, activeChar, (L2Npc) target, false);
						break;
					case 9: // Your Grand Olympiad Score from the previous period is %points% point(s) | TODO: cleanup (not used anymore)
						int point = Olympiad.getInstance().getLastNobleOlympiadPoints(activeChar.getObjectId());
						html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "noble_points2.htm");
						html.replace("%points%", String.valueOf(point));
						html.replace("%objectId%", String.valueOf(target.getObjectId()));
						activeChar.sendPacket(html);
						break;
					case 10: // give tokens to player
						passes = Olympiad.getInstance().getNoblessePasses(activeChar, true);
						if (passes > 0)
						{
							L2ItemInstance item = activeChar.getInventory().addItem("Olympiad", GATE_PASS, passes, activeChar, target);
							
							InventoryUpdate iu = new InventoryUpdate();
							iu.addModifiedItem(item);
							activeChar.sendPacket(iu);
							
							final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S);
							sm.addLong(passes);
							sm.addItemName(item);
							activeChar.sendPacket(sm);
						}
						break;
					case 11: // register team
						OlympiadManager.getInstance().registerNoble(activeChar, CompetitionType.TEAMS);
						break;
					default:
						_log.warning("Olympiad System: Couldnt send packet for request " + val);
						break;
				}
			}
			else if (command.toLowerCase().startsWith("olybuff"))
			{
				if (activeChar.getOlympiadBuffCount() <= 0)
				{
					return false;
				}
				
				final NpcHtmlMessage html = new NpcHtmlMessage(target.getObjectId());
				String[] params = command.split(" ");
				
				if (params[1] == null)
				{
					_log.warning("Olympiad Buffer Warning: npcId = " + target.getId() + " has no buffGroup set in the bypass for the buff selected.");
					return false;
				}
				int buffGroup = Integer.parseInt(params[1]);
				
				NpcBufferData npcBuffGroupInfo = NpcBufferTable.getInstance().getSkillInfo(target.getId(), buffGroup);
				
				if (npcBuffGroupInfo == null)
				{
					_log.warning("Olympiad Buffer Warning: npcId = " + target.getId() + " Location: " + target.getX() + ", " + target.getY() + ", " + target.getZ() + " Player: " + activeChar.getName() + " has tried to use skill group (" + buffGroup + ") not assigned to the NPC Buffer!");
					return false;
				}
				
				Skill skill = npcBuffGroupInfo.getSkill().getSkill();
				target.setTarget(activeChar);
				
				if (activeChar.getOlympiadBuffCount() > 0)
				{
					if (skill != null)
					{
						activeChar.setOlympiadBuffCount(activeChar.getOlympiadBuffCount() - 1);
						target.broadcastPacket(new MagicSkillUse(target, activeChar, skill.getId(), skill.getLevel(), 0, 0));
						skill.applyEffects(activeChar, activeChar);
						final L2Summon summon = activeChar.getSummon();
						if (summon != null)
						{
							target.broadcastPacket(new MagicSkillUse(target, summon, skill.getId(), skill.getLevel(), 0, 0));
							skill.applyEffects(summon, summon);
						}
					}
				}
				
				if (activeChar.getOlympiadBuffCount() > 0)
				{
					html.setFile(activeChar.getHtmlPrefix(), activeChar.getOlympiadBuffCount() == 5 ? Olympiad.OLYMPIAD_HTML_PATH + "olympiad_buffs.htm" : Olympiad.OLYMPIAD_HTML_PATH + "olympiad_5buffs.htm");
					html.replace("%objectId%", String.valueOf(target.getObjectId()));
					activeChar.sendPacket(html);
				}
				else
				{
					html.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_nobuffs.htm");
					html.replace("%objectId%", String.valueOf(target.getObjectId()));
					activeChar.sendPacket(html);
					target.decayMe();
				}
			}
			else if (command.toLowerCase().startsWith("olympiad"))
			{
				int val = Integer.parseInt(command.substring(9, 10));
				
				final NpcHtmlMessage reply = new NpcHtmlMessage(target.getObjectId());
				
				switch (val)
				{
					case 2: // show rank for a specific class
						// for example >> Olympiad 1_88
						int classId = Integer.parseInt(command.substring(11));
						if (((classId >= 88) && (classId <= 118)) || ((classId >= 131) && (classId <= 134)) || (classId == 136))
						{
							List<String> names = Olympiad.getInstance().getClassLeaderBoard(classId);
							reply.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_ranking.htm");
							
							int index = 1;
							for (String name : names)
							{
								reply.replace("%place" + index + "%", String.valueOf(index));
								reply.replace("%rank" + index + "%", name);
								index++;
								if (index > 10)
								{
									break;
								}
							}
							for (; index <= 10; index++)
							{
								reply.replace("%place" + index + "%", "");
								reply.replace("%rank" + index + "%", "");
							}
							
							reply.replace("%objectId%", String.valueOf(target.getObjectId()));
							activeChar.sendPacket(reply);
						}
						break;
					case 4: // hero list
						activeChar.sendPacket(new ExHeroList());
						break;
					default:
						_log.warning("Olympiad System: Couldnt send packet for request " + val);
						break;
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Exception in " + getClass().getSimpleName(), e);
		}
		
		return true;
	}
	
	@Override
	public final String[] getBypassList()
	{
		return COMMANDS;
	}
}
