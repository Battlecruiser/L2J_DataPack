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
package instances.PailakaSongOfIceAndFire;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Pailaka Song of Ice and Fire Instance zone.
 * @author Gnacik, St3eT
 */
public final class PailakaSongOfIceAndFire extends AbstractNpcAI
{
	protected class PSoIWorld extends InstanceWorld
	{
		
	}
	
	// NPCs
	private static final int ADLER1 = 32497;
	private static final int GARGOS = 18607;
	private static final int BLOOM = 18616;
	private static final int BOTTLE = 32492;
	private static final int BRAZIER = 32493;
	// Items
	private static final int FIRE_ENHANCER = 13040;
	private static final int WATER_ENHANCER = 13041;
	private static final int SHIELD_POTION = 13032;
	private static final int HEAL_POTION = 13033;
	// Location
	private static final Location TELEPORT = new Location(-52875, 188232, -4696);
	// Misc
	private static final int INSTANCE_ID = 43;
	private static final int ZONE = 20108;
	
	private PailakaSongOfIceAndFire()
	{
		super(PailakaSongOfIceAndFire.class.getSimpleName(), "instances");
		addStartNpc(ADLER1);
		addTalkId(ADLER1);
		addAttackId(BOTTLE, BRAZIER);
		addExitZoneId(ZONE);
		addSeeCreatureId(GARGOS);
		addSpawnId(BLOOM);
		addKillId(BLOOM);
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof PSoIWorld)
			{
				teleportPlayer(player, TELEPORT, world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
			return;
		}
		world = new PSoIWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCE_ID);
		InstanceManager.getInstance().addWorld(world);
		world.addAllowed(player.getObjectId());
		teleportPlayer(player, TELEPORT, world.getInstanceId());
		_log.info("Pailaka Song of Ice and Fire" + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "enter":
			{
				enterInstance(player, "PailakaSongOfIceAndFire.xml");
				break;
			}
			case "GARGOS_LAUGH":
			{
				broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.OHHOHOH);
				break;
			}
			case "TELEPORT":
			{
				teleportPlayer(player, TELEPORT, player.getInstanceId());
				break;
			}
			case "DELETE":
			{
				if (npc != null)
				{
					npc.deleteMe();
				}
				break;
			}
			case "BLOOM_TIMER":
			{
				startQuestTimer("BLOOM_TIMER2", getRandom(2, 4) * 60 * 1000, npc, null);
				break;
			}
			case "BLOOM_TIMER2":
			{
				npc.setInvisible(!npc.isInvisible());
				startQuestTimer("BLOOM_TIMER", 5000, npc, null);
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public final String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		if ((damage > 0) && npc.isScriptValue(0))
		{
			switch (getRandom(6))
			{
				case 0:
				{
					if (npc.getId() == BOTTLE)
					{
						npc.dropItem(player, WATER_ENHANCER, getRandom(1, 6));
					}
					break;
				}
				case 1:
				{
					if (npc.getId() == BRAZIER)
					{
						npc.dropItem(player, FIRE_ENHANCER, getRandom(1, 6));
					}
					break;
				}
				case 2:
				case 3:
				{
					npc.dropItem(player, SHIELD_POTION, getRandom(1, 10));
					break;
				}
				case 4:
				case 5:
				{
					npc.dropItem(player, HEAL_POTION, getRandom(1, 10));
					break;
				}
			}
			npc.setScriptValue(1);
			startQuestTimer("DELETE", 3000, npc, null);
		}
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		npc.dropItem(player, getRandomBoolean() ? SHIELD_POTION : HEAL_POTION, getRandom(1, 7));
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onExitZone(L2Character character, L2ZoneType zone)
	{
		if ((character.isPlayer()) && !character.isDead() && !character.isTeleporting() && ((L2PcInstance) character).isOnline())
		{
			final InstanceWorld world = InstanceManager.getInstance().getWorld(character.getInstanceId());
			if ((world != null) && (world.getTemplateId() == INSTANCE_ID))
			{
				startQuestTimer("TELEPORT", 1000, null, (L2PcInstance) character);
			}
		}
		return super.onExitZone(character, zone);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (npc.isScriptValue(0) && creature.isPlayer())
		{
			npc.setScriptValue(1);
			startQuestTimer("GARGOS_LAUGH", 1000, npc, creature.getActingPlayer());
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setInvisible(true);
		startQuestTimer("BLOOM_TIMER", 1000, npc, null);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new PailakaSongOfIceAndFire();
	}
}