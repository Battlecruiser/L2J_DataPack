/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package handlers.effecthandlers;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.enums.SubclassInfoType;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExSubjobInfo;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Sdw
 */
public class ClassChange extends AbstractEffect
{
	private final int _index;
	private final static int IDENTITY_CRISIS_SKILL_ID = 1570;
	
	public ClassChange(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_index = params.getInt("index", 0);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (info.getEffector().isPlayer())
		{
			final L2PcInstance player = info.getEffector().getActingPlayer();
			
			// TODO: FIX ME - Executing 1 second later otherwise interupted exception during storeCharBase()
			ThreadPoolManager.getInstance().scheduleGeneral(() ->
			{
				final int activeClass = player.getClassId().getId();
				
				if (!player.setActiveClass(_index))
				{
					player.sendMessage("You cannot switch your class right now!.");
					return;
				}
				
				final Skill identifyCrisis = SkillData.getInstance().getSkill(IDENTITY_CRISIS_SKILL_ID, 1);
				if (identifyCrisis != null)
				{
					identifyCrisis.applyEffects(player, player);
				}
				
				final SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_SUCCESSFULLY_SWITCHED_S1_TO_S2);
				msg.addClassId(activeClass);
				msg.addClassId(player.getClassId().getId());
				player.sendPacket(msg);
				
				player.broadcastUserInfo();
				player.sendPacket(new ExSubjobInfo(player, SubclassInfoType.CLASS_CHANGED));
			}, 1000);
		}
	}
}
