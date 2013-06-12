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
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DecoyInstance;
import com.l2jserver.gameserver.model.actor.instance.L2EffectPointInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.util.Point3D;
import com.l2jserver.util.Rnd;

/**
 * Summon Npc effect implementation.
 * @author Zoey76
 */
public class SummonNpc extends L2Effect
{
	private final int _despawnDelay;
	private final int _npcId;
	private final int _npcCount;
	private final boolean _randomOffset;
	private final boolean _isSummonSpawn;
	
	public SummonNpc(Env env, EffectTemplate template)
	{
		super(env, template);
		_despawnDelay = template.getParameters().getInteger("despawnDelay", 20000);
		_npcId = template.getParameters().getInteger("npcId", 0);
		_npcCount = template.getParameters().getInteger("npcCount", 1);
		_randomOffset = template.getParameters().getBool("randomOffset", false);
		_isSummonSpawn = template.getParameters().getBool("isSummonSpawn", false);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		if ((getEffected() == null) || !getEffected().isPlayer() || getEffected().isAlikeDead() || getEffected().getActingPlayer().inObserverMode())
		{
			return false;
		}
		
		if ((_npcId <= 0) || (_npcCount <= 0))
		{
			_log.warning(SummonNpc.class.getSimpleName() + ": Invalid NPC Id or count skill Id: " + getSkill().getId());
			return false;
		}
		
		final L2PcInstance player = getEffected().getActingPlayer();
		if (player.hasSummon() || player.isMounted())
		{
			return false;
		}
		
		final L2NpcTemplate npcTemplate = NpcTable.getInstance().getTemplate(_npcId);
		if (npcTemplate == null)
		{
			_log.warning(SummonNpc.class.getSimpleName() + ": Spawn of the nonexisting NPC Id: " + _npcId + ", skill Id:" + getSkill().getId());
			return false;
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
				
				if (getSkill().getTargetType() == L2TargetType.GROUND)
				{
					final Point3D wordPosition = player.getActingPlayer().getCurrentSkillWorldPosition();
					if (wordPosition != null)
					{
						x = wordPosition.getX();
						y = wordPosition.getY();
						z = wordPosition.getZ();
					}
				}
				getSkill().getEffects(player, effectPoint);
				effectPoint.setIsInvul(true);
				effectPoint.spawnMe(x, y, z);
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
					return false;
				}
				
				int x = player.getX();
				int y = player.getY();
				if (_randomOffset)
				{
					x += (Rnd.nextBoolean() ? Rnd.get(20, 50) : Rnd.get(-50, -20));
					y += (Rnd.nextBoolean() ? Rnd.get(20, 50) : Rnd.get(-50, -20));
				}
				
				spawn.setLocx(x);
				spawn.setLocy(y);
				spawn.setLocz(player.getZ());
				spawn.setHeading(player.getHeading());
				spawn.stopRespawn();
				
				final L2Npc npc = spawn.doSpawn(_isSummonSpawn);
				npc.setName(npcTemplate.getName());
				npc.setTitle(npcTemplate.getName());
				npc.setSummoner(player);
				if (_despawnDelay > 0)
				{
					npc.scheduleDespawn(_despawnDelay);
				}
				npc.setIsRunning(false); // TODO: Fix broadcast info.
			}
		}
		return true;
	}
}
