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
package handlers.effecthandlers;

import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.idfactory.IdFactory;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DecoyInstance;
import com.l2jserver.gameserver.model.actor.instance.L2EffectPointInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.util.Rnd;

/**
 * Summon Npc effect implementation.
 * @author Zoey76
 */
public final class SummonNpc extends AbstractEffect
{
	private final int _despawnDelay;
	private final int _npcId;
	private final int _npcCount;
	private final boolean _randomOffset;
	private final boolean _isSummonSpawn;
	
	public SummonNpc(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		_despawnDelay = getParameters().getInt("despawnDelay", 20000);
		_npcId = getParameters().getInt("npcId", 0);
		_npcCount = getParameters().getInt("npcCount", 1);
		_randomOffset = getParameters().getBoolean("randomOffset", false);
		_isSummonSpawn = getParameters().getBoolean("isSummonSpawn", false);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffected() == null) || !info.getEffected().isPlayer() || info.getEffected().isAlikeDead() || info.getEffected().getActingPlayer().inObserverMode())
		{
			return;
		}
		
		if ((_npcId <= 0) || (_npcCount <= 0))
		{
			_log.warning(SummonNpc.class.getSimpleName() + ": Invalid NPC ID or count skill ID: " + info.getSkill().getId());
			return;
		}
		
		final L2PcInstance player = info.getEffected().getActingPlayer();
		if (player.isMounted())
		{
			return;
		}
		
		final L2NpcTemplate npcTemplate = NpcTable.getInstance().getTemplate(_npcId);
		if (npcTemplate == null)
		{
			_log.warning(SummonNpc.class.getSimpleName() + ": Spawn of the nonexisting NPC ID: " + _npcId + ", skill ID:" + info.getSkill().getId());
			return;
		}
		
		switch (npcTemplate.getType())
		{
			case "L2Decoy":
			{
				final L2DecoyInstance decoy = new L2DecoyInstance(IdFactory.getInstance().getNextId(), npcTemplate, player, _despawnDelay);
				decoy.setCurrentHp(decoy.getMaxHp());
				decoy.setCurrentMp(decoy.getMaxMp());
				decoy.setHeading(player.getHeading());
				decoy.setInstanceId(player.getInstanceId());
				decoy.setSummoner(player);
				decoy.spawnMe(player.getX(), player.getY(), player.getZ());
				player.setDecoy(decoy);
				break;
			}
			case "L2EffectPoint": // TODO: Implement proper signet skills.
			{
				final L2EffectPointInstance effectPoint = new L2EffectPointInstance(IdFactory.getInstance().getNextId(), npcTemplate, player);
				effectPoint.setCurrentHp(effectPoint.getMaxHp());
				effectPoint.setCurrentMp(effectPoint.getMaxMp());
				int x = player.getX();
				int y = player.getY();
				int z = player.getZ();
				
				if (info.getSkill().getTargetType() == L2TargetType.GROUND)
				{
					final Location wordPosition = player.getActingPlayer().getCurrentSkillWorldPosition();
					if (wordPosition != null)
					{
						x = wordPosition.getX();
						y = wordPosition.getY();
						z = wordPosition.getZ();
					}
				}
				
				effectPoint.setIsInvul(true);
				effectPoint.setSummoner(player);
				effectPoint.spawnMe(x, y, z);
				if (_despawnDelay > 0)
				{
					effectPoint.scheduleDespawn(_despawnDelay);
				}
				break;
			}
			default:
			{
				L2Spawn spawn;
				try
				{
					spawn = new L2Spawn(npcTemplate);
				}
				catch (Exception e)
				{
					_log.warning(SummonNpc.class.getSimpleName() + ": " + e.getMessage());
					return;
				}
				
				int x = player.getX();
				int y = player.getY();
				if (_randomOffset)
				{
					x += (Rnd.nextBoolean() ? Rnd.get(20, 50) : Rnd.get(-50, -20));
					y += (Rnd.nextBoolean() ? Rnd.get(20, 50) : Rnd.get(-50, -20));
				}
				
				spawn.setX(x);
				spawn.setY(y);
				spawn.setZ(player.getZ());
				spawn.setHeading(player.getHeading());
				spawn.stopRespawn();
				
				final L2Npc npc = spawn.doSpawn(_isSummonSpawn);
				npc.setSummoner(player);
				npc.setName(npcTemplate.getName());
				npc.setTitle(npcTemplate.getName());
				if (_despawnDelay > 0)
				{
					npc.scheduleDespawn(_despawnDelay);
				}
				npc.setIsRunning(false); // TODO: Fix broadcast info.
			}
		}
	}
}
