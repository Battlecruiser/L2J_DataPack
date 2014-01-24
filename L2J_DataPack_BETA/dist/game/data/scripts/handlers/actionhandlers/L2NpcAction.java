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
package handlers.actionhandlers;

import java.util.List;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.InstanceType;
import com.l2jserver.gameserver.enums.QuestEventType;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.L2Event;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.util.Rnd;

public class L2NpcAction implements IActionHandler
{
	/**
	 * Manage actions when a player click on the L2Npc.<BR>
	 * <BR>
	 * <B><U> Actions on first click on the L2Npc (Select it)</U> :</B><BR>
	 * <BR>
	 * <li>Set the L2Npc as target of the L2PcInstance player (if necessary)</li> <li>Send a Server->Client packet MyTargetSelected to the L2PcInstance player (display the select window)</li> <li>If L2Npc is autoAttackable, send a Server->Client packet StatusUpdate to the L2PcInstance in order to
	 * update L2Npc HP bar</li> <li>Send a Server->Client packet ValidateLocation to correct the L2Npc position and heading on the client</li><BR>
	 * <BR>
	 * <B><U> Actions on second click on the L2Npc (Attack it/Intercat with it)</U> :</B><BR>
	 * <BR>
	 * <li>Send a Server->Client packet MyTargetSelected to the L2PcInstance player (display the select window)</li> <li>If L2Npc is autoAttackable, notify the L2PcInstance AI with AI_INTENTION_ATTACK (after a height verification)</li> <li>If L2Npc is NOT autoAttackable, notify the L2PcInstance AI
	 * with AI_INTENTION_INTERACT (after a distance verification) and show message</li><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : Each group of Server->Client packet must be terminated by a ActionFailed packet in order to avoid that client wait an other packet</B></FONT><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Client packet : Action, AttackRequest</li><BR>
	 * <BR>
	 * @param activeChar The L2PcInstance that start an action on the L2Npc
	 */
	@Override
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		if (!((L2Npc) target).canTarget(activeChar))
		{
			return false;
		}
		activeChar.setLastFolkNPC((L2Npc) target);
		// Check if the L2PcInstance already target the L2Npc
		if (target != activeChar.getTarget())
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);
			// Check if the activeChar is attackable (without a forced attack)
			if (target.isAutoAttackable(activeChar))
			{
				((L2Npc) target).getAI(); // wake up ai
			}
		}
		else if (interact)
		{
			// Check if the activeChar is attackable (without a forced attack) and isn't dead
			if (target.isAutoAttackable(activeChar) && !((L2Character) target).isAlikeDead())
			{
				// Check the height difference
				if (Math.abs(activeChar.getZ() - target.getZ()) < 400) // this max heigth difference might need some tweaking
				{
					// Set the L2PcInstance Intention to AI_INTENTION_ATTACK
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
					// activeChar.startAttack(this);
				}
				else
				{
					// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				}
			}
			else if (!target.isAutoAttackable(activeChar))
			{
				// Calculate the distance between the L2PcInstance and the L2Npc
				if (!((L2Npc) target).canInteract(activeChar))
				{
					// Notify the L2PcInstance AI with AI_INTENTION_INTERACT
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
				}
				else
				{
					L2Npc npc = (L2Npc) target;
					if (npc.hasRandomAnimation())
					{
						npc.onRandomAnimation(Rnd.get(8));
					}
					// Open a chat window on client with the text of the L2Npc
					if (npc.isEventMob())
					{
						L2Event.showEventHtml(activeChar, String.valueOf(target.getObjectId()));
					}
					else
					{
						List<Quest> qlsa = npc.getTemplate().getEventQuests(QuestEventType.QUEST_START);
						List<Quest> qlst = npc.getTemplate().getEventQuests(QuestEventType.ON_FIRST_TALK);
						if ((qlsa != null) && !qlsa.isEmpty())
						{
							activeChar.setLastQuestNpcObject(target.getObjectId());
						}
						if ((qlst != null) && (qlst.size() == 1))
						{
							qlst.get(0).notifyFirstTalk(npc, activeChar);
						}
						else
						{
							npc.showChatWindow(activeChar);
						}
					}
					if ((Config.PLAYER_MOVEMENT_BLOCK_TIME > 0) && !activeChar.isGM())
					{
						activeChar.updateNotMoveUntil();
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2Npc;
	}
}