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
package handlers.itemhandlers;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Template for item skills handler.
 * @author Zoey76
 */
public class ItemSkillsTemplate implements IItemHandler
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!playable.isPlayer() && !playable.isPet())
		{
			return false;
		}
		
		if (!TvTEvent.onScrollUse(playable.getObjectId()))
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return false;
		}
		
		// Pets can use items only when they are tradable.
		if (playable.isPet() && !item.isTradeable())
		{
			playable.sendPacket(SystemMessageId.ITEM_NOT_FOR_PETS);
			return false;
		}
		
		// Verify that item is not under reuse.
		if (!checkReuse(playable, null, item))
		{
			return false;
		}
		
		final SkillHolder[] skills = item.getEtcItem().getSkills();
		if (skills == null)
		{
			_log.info("Item " + item + " does not have registered any skill for handler.");
			return false;
		}
		
		final L2PcInstance activeChar = playable.getActingPlayer();
		for (SkillHolder skillInfo : skills)
		{
			if (skillInfo == null)
			{
				continue;
			}
			
			L2Skill itemSkill = skillInfo.getSkill();
			if (itemSkill != null)
			{
				if (!itemSkill.checkCondition(playable, playable.getTarget(), false))
				{
					return false;
				}
				
				if (playable.isSkillDisabled(itemSkill))
				{
					return false;
				}
				
				// Verify that skill is not under reuse.
				if (!checkReuse(playable, itemSkill, item))
				{
					return false;
				}
				
				if (!item.isPotion() && !item.isElixir() && playable.isCastingNow())
				{
					return false;
				}
				
				if ((itemSkill.getItemConsumeId() == 0) && (itemSkill.getItemConsume() > 0) && (item.isPotion() || item.isElixir() || itemSkill.isSimultaneousCast()))
				{
					if (!playable.destroyItem("Consume", item.getObjectId(), itemSkill.getItemConsume(), playable, false))
					{
						playable.sendPacket(SystemMessageId.NOT_ENOUGH_ITEMS);
						return false;
					}
				}
				
				// Send message to the master.
				if (playable.isPet())
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.PET_USES_S1);
					sm.addSkillName(itemSkill);
					playable.sendPacket(sm);
				}
				
				if (itemSkill.isSimultaneousCast() || ((item.getItem().hasImmediateEffect() || item.getItem().hasExImmediateEffect()) && itemSkill.isStatic()))
				{
					playable.doSimultaneousCast(itemSkill);
					// Summons should be affected by herbs too, self time effect is handled at L2Effect constructor
					if (!playable.isSummon() && activeChar.hasSummon())
					{
						activeChar.getSummon().doSimultaneousCast(itemSkill);
					}
				}
				else
				{
					playable.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
					if (!playable.useMagic(itemSkill, forceUse, false))
					{
						return false;
					}
					
					// Consume.
					if ((itemSkill.getItemConsumeId() == 0) && (itemSkill.getItemConsume() > 0))
					{
						if (!playable.destroyItem("Consume", item.getObjectId(), itemSkill.getItemConsume(), null, false))
						{
							playable.sendPacket(SystemMessageId.NOT_ENOUGH_ITEMS);
							return false;
						}
					}
				}
				
				if (itemSkill.getReuseDelay() > 0)
				{
					playable.addTimeStamp(itemSkill, itemSkill.getReuseDelay());
				}
			}
		}
		return true;
	}
	
	/**
	 * @param playable the character using the item or skill
	 * @param skill the skill being used, can be null
	 * @param item the item being used
	 * @return {@code true} if the the item or skill to check is available, {@code false} otherwise
	 */
	private boolean checkReuse(L2Playable playable, L2Skill skill, L2ItemInstance item)
	{
		final long remainingTime = (skill != null) ? playable.getSkillRemainingReuseTime(skill.getReuseHashCode()) : playable.getItemRemainingReuseTime(item.getObjectId());
		final boolean isAvailable = remainingTime <= 0;
		if (playable.isPlayer())
		{
			if (!isAvailable)
			{
				final int hours = (int) (remainingTime / 3600000L);
				final int minutes = (int) (remainingTime % 3600000L) / 60000;
				final int seconds = (int) ((remainingTime / 1000) % 60);
				SystemMessage sm = null;
				if (hours > 0)
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S2_HOURS_S3_MINUTES_S4_SECONDS_REMAINING_FOR_REUSE_S1);
					if ((skill == null) || skill.isStatic())
					{
						sm.addItemName(item);
					}
					else
					{
						sm.addSkillName(skill);
					}
					sm.addNumber(hours);
					sm.addNumber(minutes);
				}
				else if (minutes > 0)
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MINUTES_S3_SECONDS_REMAINING_FOR_REUSE_S1);
					if ((skill == null) || skill.isStatic())
					{
						sm.addItemName(item);
					}
					else
					{
						sm.addSkillName(skill);
					}
					sm.addNumber(minutes);
				}
				else
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S2_SECONDS_REMAINING_FOR_REUSE_S1);
					if ((skill == null) || skill.isStatic())
					{
						sm.addItemName(item);
					}
					else
					{
						sm.addSkillName(skill);
					}
				}
				sm.addNumber(seconds);
				playable.sendPacket(sm);
			}
		}
		return isAvailable;
	}
}
