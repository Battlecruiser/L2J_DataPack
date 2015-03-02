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

import com.l2jserver.gameserver.data.xml.impl.ExperienceData;
import com.l2jserver.gameserver.data.xml.impl.NpcData;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.idfactory.IdFactory;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2ServitorInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Summon effect implementation.
 * @author UnAfraid
 */
public final class Summon extends AbstractEffect
{
	private final int _npcId;
	private final float _expMultiplier;
	private final ItemHolder _consumeItem;
	private final int _lifeTime;
	private final int _consumeItemInterval;
	private final int _summonPoints;
	
	public Summon(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params.isEmpty())
		{
			throw new IllegalArgumentException("Summon effect without parameters!");
		}
		
		_npcId = params.getInt("npcId");
		_expMultiplier = params.getFloat("expMultiplier", 1);
		_consumeItem = new ItemHolder(params.getInt("consumeItemId", 0), params.getInt("consumeItemCount", 1));
		_consumeItemInterval = params.getInt("consumeItemInterval", 0);
		_lifeTime = params.getInt("lifeTime", 3600) * 1000;
		_summonPoints = params.getInt("summonPoints", 0);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (!info.getEffected().isPlayer())
		{
			return;
		}
		
		final L2PcInstance player = info.getEffected().getActingPlayer();
		final L2NpcTemplate template = NpcData.getInstance().getTemplate(_npcId);
		final L2ServitorInstance summon = new L2ServitorInstance(IdFactory.getInstance().getNextId(), template, player);
		final int consumeItemInterval = (_consumeItemInterval > 0 ? _consumeItemInterval : (template.getRace() != Race.SIEGE_WEAPON ? 240 : 60)) * 1000;
		
		summon.setName(template.getName());
		summon.setTitle(info.getEffected().getName());
		summon.setReferenceSkill(info.getSkill().getId());
		summon.setExpMultiplier(_expMultiplier);
		summon.setLifeTime(_lifeTime);
		summon.setItemConsume(_consumeItem);
		summon.setItemConsumeInterval(consumeItemInterval);
		
		if (summon.getLevel() >= ExperienceData.getInstance().getMaxPetLevel())
		{
			summon.getStat().setExp(ExperienceData.getInstance().getExpForLevel(ExperienceData.getInstance().getMaxPetLevel() - 1));
			_log.warning(Summon.class.getSimpleName() + ": (" + summon.getName() + ") NpcID: " + summon.getId() + " has a level above " + ExperienceData.getInstance().getMaxPetLevel() + ". Please rectify.");
		}
		else
		{
			summon.getStat().setExp(ExperienceData.getInstance().getExpForLevel(summon.getLevel() % ExperienceData.getInstance().getMaxPetLevel()));
		}
		
		summon.setCurrentHp(summon.getMaxHp());
		summon.setCurrentMp(summon.getMaxMp());
		summon.setHeading(player.getHeading());
		summon.setSummonPoints(_summonPoints);
		
		if (summon.isPet())
		{
			player.setPet(summon);
		}
		else
		{
			player.addServitor(summon);
		}
		summon.setShowSummonAnimation(true);
		summon.setRunning();
		summon.spawnMe();
	}
}
