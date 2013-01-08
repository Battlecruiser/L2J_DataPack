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
package ai.group_template;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Summon Minions AI. Based on work of Slyce.
 * @author Sandro
 */
public class SummonMinions extends AbstractNpcAI
{
	private static int HasSpawned;
	private static Set<Integer> myTrackingSet = new FastSet<Integer>().shared(); // Used to track instances of npcs
	private final FastMap<Integer, FastList<L2PcInstance>> _attackersList = new FastMap<Integer, FastList<L2PcInstance>>().shared();
	private static final Map<Integer, List<Integer>> MINIONS = new HashMap<>();
	
	static
	{
		// Timak Orc Troop
		MINIONS.put(20767, Arrays.asList(20768, 20769, 20770));
		// Ragna Orc Shaman
		// MINIONS.put(22030, Arrays.asList(22045, 22047, 22048));
		// Ragna Orc Warrior - summons shaman but not 22030 ><
		// MINIONS.put(22032, Arrays.asList(22036));
		// Ragna Orc Hero
		// MINIONS.put(22038, Arrays.asList(22037));
		// Blade of Splendor
		MINIONS.put(21524, Arrays.asList(21525));
		// Punishment of Splendor
		MINIONS.put(21531, Arrays.asList(21658));
		// Wailing of Splendor
		MINIONS.put(21539, Arrays.asList(21540));
		// Island Guardian
		MINIONS.put(22257, Arrays.asList(18364, 18364));
		// White Sand Mirage
		MINIONS.put(22258, Arrays.asList(18364, 18364));
		// Muddy Coral
		MINIONS.put(22259, Arrays.asList(18364, 18364));
		// Kleopora
		MINIONS.put(22260, Arrays.asList(18364, 18364));
		// Seychelles
		MINIONS.put(22261, Arrays.asList(18365, 18365));
		// Naiad
		MINIONS.put(22262, Arrays.asList(18365, 18365));
		// Sonneratia
		MINIONS.put(22263, Arrays.asList(18365, 18365));
		// Castalia
		MINIONS.put(22264, Arrays.asList(18366, 18366));
		// Chrysocolla
		MINIONS.put(22265, Arrays.asList(18366, 18366));
		// Pythia
		MINIONS.put(22266, Arrays.asList(18366, 18366));
		// Tanta Lizardman Summoner
		MINIONS.put(22774, Arrays.asList(22768, 22768));
	}
	
	// Timak Orc Troop Messages
	private static final NpcStringId[] ATTACK_LEADER_MSG =
	{
		NpcStringId.FORCES_OF_DARKNESS_FOLLOW_ME,
		NpcStringId.DESTROY_THE_ENEMY_MY_BROTHERS,
		NpcStringId.SHOW_YOURSELVES,
		NpcStringId.COME_OUT_YOU_CHILDREN_OF_DARKNESS
	};
	
	private SummonMinions(String name, String descr)
	{
		super(name, descr);
		registerMobs(MINIONS.keySet(), QuestEventType.ON_ATTACK, QuestEventType.ON_KILL);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int npcId = npc.getNpcId();
		int npcObjId = npc.getObjectId();
		
		if (!myTrackingSet.contains(npcObjId)) // this allows to handle multiple instances of npc
		{
			myTrackingSet.add(npcObjId);
			HasSpawned = npcObjId;
		}
		if (HasSpawned == npcObjId)
		{
			switch (npcId)
			{
				case 22030: // mobs that summon minions only on certain hp
				case 22032:
				case 22038:
				{
					if (npc.getCurrentHp() < (npc.getMaxHp() / 2.0))
					{
						HasSpawned = 0;
						if (getRandom(100) < 33) // mobs that summon minions only on certain chance
						{
							for (int val : MINIONS.get(npcId))
							{
								L2Attackable newNpc = (L2Attackable) this.addSpawn(val, (npc.getX() + getRandom(-150, 150)), (npc.getY() + getRandom(-150, 150)), npc.getZ(), 0, false, 0);
								newNpc.setRunning();
								newNpc.addDamageHate(attacker, 0, 999);
								newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
							}
						}
					}
					break;
				}
				case 22257:
				case 22258:
				case 22259:
				case 22260:
				case 22261:
				case 22262:
				case 22263:
				case 22264:
				case 22265:
				case 22266:
				{
					if (isPet)
					{
						attacker = attacker.getSummon().getOwner();
					}
					if (attacker.getParty() != null)
					{
						for (L2PcInstance member : attacker.getParty().getMembers())
						{
							if (_attackersList.get(npcObjId) == null)
							{
								FastList<L2PcInstance> player = new FastList<>();
								player.add(member);
								_attackersList.put(npcObjId, player);
							}
							else if (!_attackersList.get(npcObjId).contains(member))
							{
								_attackersList.get(npcObjId).add(member);
							}
						}
					}
					else
					{
						if (_attackersList.get(npcObjId) == null)
						{
							FastList<L2PcInstance> player = new FastList<>();
							player.add(attacker);
							_attackersList.put(npcObjId, player);
						}
						else if (!_attackersList.get(npcObjId).contains(attacker))
						{
							_attackersList.get(npcObjId).add(attacker);
						}
					}
					if (((attacker.getParty() != null) && (attacker.getParty().getMemberCount() > 2)) || (_attackersList.get(npcObjId).size() > 2)) // Just to make sure..
					{
						HasSpawned = 0;
						for (int val : MINIONS.get(npcId))
						{
							L2Attackable newNpc = (L2Attackable) this.addSpawn(val, npc.getX() + getRandom(-150, 150), npc.getY() + getRandom(-150, 150), npc.getZ(), 0, false, 0);
							newNpc.setRunning();
							newNpc.addDamageHate(attacker, 0, 999);
							newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
						}
					}
					break;
				}
				default: // mobs without special conditions
				{
					HasSpawned = 0;
					if (npcId != 20767)
					{
						for (int val : MINIONS.get(npcId))
						{
							L2Attackable newNpc = (L2Attackable) this.addSpawn(val, npc.getX() + getRandom(-150, 150), npc.getY() + getRandom(-150, 150), npc.getZ(), 0, false, 0);
							newNpc.setRunning();
							newNpc.addDamageHate(attacker, 0, 999);
							newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
						}
					}
					else
					{
						broadcastNpcSay(npc, Say2.NPC_ALL, ATTACK_LEADER_MSG[getRandom(ATTACK_LEADER_MSG.length)]);
						for (int val : MINIONS.get(npcId))
						{
							this.addSpawn(val, (npc.getX() + getRandom(-100, 100)), (npc.getY() + getRandom(-100, 100)), npc.getZ(), 0, false, 0);
						}
					}
					break;
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcObjId = npc.getObjectId();
		
		myTrackingSet.remove(npcObjId);
		
		if (_attackersList.containsKey(npcObjId))
		{
			_attackersList.get(npcObjId).clear();
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new SummonMinions(SummonMinions.class.getSimpleName(), "ai");
	}
}
