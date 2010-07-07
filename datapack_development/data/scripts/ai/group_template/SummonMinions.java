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
package ai.group_template;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.util.Rnd;

import gnu.trove.TIntHashSet;
import gnu.trove.TIntObjectHashMap;
import javolution.util.FastList;
import javolution.util.FastMap;

public class SummonMinions extends L2AttackableAIScript
{
	private static int HasSpawned;
	private static TIntHashSet myTrackingSet = new TIntHashSet(); //Used to track instances of npcs
	private FastMap<Integer, FastList<L2PcInstance>> _attackersList = new FastMap<Integer, FastList<L2PcInstance>>().shared();
	private static final TIntObjectHashMap<int[]> MINIONS = new TIntObjectHashMap<int[]>();
	
	static
    {
            MINIONS.put(20767,new int[]{20768,20769,20770}); //Timak Orc Troop
            //MINIONS.put(22030,new Integer[]{22045,22047,22048}); //Ragna Orc Shaman
            //MINIONS.put(22032,new Integer[]{22036}); //Ragna Orc Warrior - summons shaman but not 22030 ><
            //MINIONS.put(22038,new Integer[]{22037}); //Ragna Orc Hero
            MINIONS.put(21524,new int[]{21525}); //Blade of Splendor
            MINIONS.put(21531,new int[]{21658}); //Punishment of Splendor
            MINIONS.put(21539,new int[]{21540}); //Wailing of Splendor
            MINIONS.put(22257,new int[]{18364,18364}); //Island Guardian
            MINIONS.put(22258,new int[]{18364,18364}); //White Sand Mirage
            MINIONS.put(22259,new int[]{18364,18364}); //Muddy Coral
            MINIONS.put(22260,new int[]{18364,18364}); //Kleopora
            MINIONS.put(22261,new int[]{18365,18365}); //Seychelles
            MINIONS.put(22262,new int[]{18365,18365}); //Naiad
            MINIONS.put(22263,new int[]{18365,18365}); //Sonneratia
            MINIONS.put(22264,new int[]{18366,18366}); //Castalia
            MINIONS.put(22265,new int[]{18366,18366}); //Chrysocolla
            MINIONS.put(22266,new int[]{18366,18366}); //Pythia
    }
	
	public SummonMinions(int questId, String name, String descr)
	{
		super(questId, name, descr);
		int[] temp =
		{
			20767,  21524, 21531, 21539, 22257, 22258, 22259, 22260, 22261, 22262, 22263, 22264, 22265, 22266
		};
		this.registerMobs(temp);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int npcId = npc.getNpcId();
		int npcObjId = npc.getObjectId();
		if (MINIONS.containsKey(npcId))
		{
			if (!myTrackingSet.contains(npcObjId)) //this allows to handle multiple instances of npc
			{
				synchronized (myTrackingSet)
				{
					myTrackingSet.add(npcObjId);
				}
				
				HasSpawned = npcObjId;
			}
			if (HasSpawned == npcObjId)
			{
				switch (npcId)
				{
					case 22030: //mobs that summon minions only on certain hp
					case 22032:
					case 22038:
					{
						if (npc.getCurrentHp() < (npc.getMaxHp() / 2.0))
						{
							HasSpawned = 0;
							if (Rnd.get(100) < 33) //mobs that summon minions only on certain chance
							{
								int[] minions = MINIONS.get(npcId);
								for (int val : minions)
								{
									L2Attackable newNpc = (L2Attackable) this.addSpawn(val, (npc.getX() + Rnd.get(-150, 150)), (npc.getY() + Rnd.get(-150, 150)), npc.getZ(), 0, false, 0);
									newNpc.setRunning();
									newNpc.addDamageHate(attacker, 0, 999);
									newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
								}
								minions = null;
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
							attacker = (attacker).getPet().getOwner();
						if (attacker.getParty() != null)
						{
							for (L2PcInstance member : attacker.getParty().getPartyMembers())
							{
								if (_attackersList.get(npcObjId) == null)
								{
									FastList<L2PcInstance> player = new FastList<L2PcInstance>();
									player.add(member);
									_attackersList.put(npcObjId,player);
								}
								else if (!_attackersList.get(npcObjId).contains(member))
									_attackersList.get(npcObjId).add(member);
							}
						}
						else
						{
							if (_attackersList.get(npcObjId) == null)
							{
								FastList<L2PcInstance> player = new FastList<L2PcInstance>();
								player.add(attacker);
								_attackersList.put(npcObjId,player);
							}
							else if (!_attackersList.get(npcObjId).contains(attacker))
								_attackersList.get(npcObjId).add(attacker);
						}
						if ((attacker.getParty() != null && attacker.getParty().getMemberCount() > 2)||_attackersList.get(npcObjId).size() > 2) //Just to make sure..
						{
							HasSpawned = 0;
							for (int val : MINIONS.get(npcId))
							{
								L2Attackable newNpc = (L2Attackable) this.addSpawn(val, npc.getX() + Rnd.get(-150, 150), npc.getY() + Rnd.get(-150, 150), npc.getZ(), 0, false, 0);
								newNpc.setRunning();
								newNpc.addDamageHate(attacker, 0, 999);
								newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
							}
						}
						break;
					}
					default: //mobs without special conditions
					{
						HasSpawned = 0;
						if (npcId != 20767)
						{
							for (int val : MINIONS.get(npcId))
							{
								L2Attackable newNpc = (L2Attackable) this.addSpawn(val, npc.getX() + Rnd.get(-150, 150), npc.getY() + Rnd.get(-150, 150), npc.getZ(), 0, false, 0);
								newNpc.setRunning();
								newNpc.addDamageHate(attacker, 0, 999);
								newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
							}
						}
						else
						{
							for (int val : MINIONS.get(npcId))
							{
								this.addSpawn(val, (npc.getX() + Rnd.get(-100, 100)), (npc.getY() + Rnd.get(-100, 100)), npc.getZ(), 0, false, 0);
							}
						}
						if (npcId == 20767)
						{
							npc.broadcastPacket(new NpcSay(npcObjId, 0, npcId, "Come out, you children of darkness!"));
						}
						break;
					}
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcId = npc.getNpcId();
		int npcObjId = npc.getObjectId();
		if (MINIONS.containsKey(npcId))
		{
			synchronized (myTrackingSet)
			{
				myTrackingSet.remove(npcObjId);
			}
		}
		if (_attackersList.get(npcObjId) != null)
		{
			_attackersList.get(npcObjId).clear();
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		// now call the constructor (starts up the ai)
		new SummonMinions(-1, "SummonMinions", "ai");
	}
}