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
package ai.group_template;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.GameTimeController;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.enums.ChatType;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.util.Util;

/**
 * Sel Mahum Training Ground AI for squads and chefs.
 * @author GKR
 */
public final class SelMahumSquad extends AbstractNpcAI
{
	// NPC's
	private static final int CHEF = 18908;
	private static final int FIRE = 18927;
	private static final int STOVE = 18933;
	
	private static final int OHS_Weapon = 15280;
	private static final int THS_Weapon = 15281;
	
	// Sel Mahum Squad Leaders
	private static final int[] SQUAD_LEADERS =
	{
		22786,
		22787,
		22788
	};
	
	private static final NpcStringId[] CHEF_FSTRINGS =
	{
		NpcStringId.I_BROUGHT_THE_FOOD,
		NpcStringId.COME_AND_EAT
	};
	
	private static final int FIRE_EFFECT_BURN = 1;
	private static final int FIRE_EFFECT_NONE = 2;
	
	private static final int MAHUM_EFFECT_EAT = 1;
	private static final int MAHUM_EFFECT_SLEEP = 2;
	private static final int MAHUM_EFFECT_NONE = 3;
	
	private SelMahumSquad()
	{
		super(SelMahumSquad.class.getSimpleName(), "ai/group_template");
		
		addAttackId(CHEF);
		addAttackId(SQUAD_LEADERS);
		addEventReceivedId(CHEF, FIRE, STOVE);
		addEventReceivedId(SQUAD_LEADERS);
		addFactionCallId(SQUAD_LEADERS);
		addKillId(CHEF);
		addMoveFinishedId(SQUAD_LEADERS);
		addNodeArrivedId(CHEF);
		addSkillSeeId(STOVE);
		addSpawnId(CHEF, FIRE);
		addSpawnId(SQUAD_LEADERS);
		addSpellFinishedId(CHEF);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "chef_disable_reward":
			{
				npc.getVariables().set("REWARD_TIME_GONE", 1);
				break;
			}
			case "chef_heal_player":
			{
				healPlayer(npc, player);
				break;
			}
			case "chef_remove_invul":
			{
				if (npc.isMonster())
				{
					npc.setIsInvul(false);
					npc.getVariables().remove("INVUL_REMOVE_TIMER_STARTED");
					if ((player != null) && !player.isDead() && npc.getKnownList().knowsThePlayer(player))
					{
						addAttackPlayerDesire(npc, player);
					}
				}
				break;
			}
			case "chef_set_invul":
			{
				if (!npc.isDead())
				{
					npc.setIsInvul(true);
				}
				break;
			}
			case "fire":
			{
				startQuestTimer("fire", 30000 + getRandom(5000), npc, null);
				npc.setState(FIRE_EFFECT_NONE);
				
				if (getRandom(GameTimeController.getInstance().isNight() ? 2 : 4) < 1)
				{
					npc.setState(FIRE_EFFECT_BURN); // fire burns
					npc.broadcastEvent("SCE_CAMPFIRE_START", 600, null);
				}
				else
				{
					npc.setState(FIRE_EFFECT_NONE); // fire goes out
					npc.broadcastEvent("SCE_CAMPFIRE_END", 600, null);
				}
				break;
			}
			case "fire_arrived":
			{
				// myself.i_quest0 = 1;
				npc.setIsRunning(false);
				npc.setTarget(npc);
				
				if (npc.isNoRndWalk())
				{
					npc.doCast(SkillData.getInstance().getSkill(6331, 1));
					npc.setState(MAHUM_EFFECT_SLEEP);
				}
				if (npc.getVariables().getInt("BUSY_STATE") == 1) // Eating
				{
					npc.doCast(SkillData.getInstance().getSkill(6332, 1));
					npc.setState(MAHUM_EFFECT_EAT);
				}
				
				startQuestTimer("remove_effects", 300000, npc, null);
				break;
			}
			case "notify_dinner":
			{
				npc.broadcastEvent("SCE_DINNER_EAT", 600, null);
				break;
			}
			case "remove_effects":
			{
				// myself.i_quest0 = 0;
				npc.setIsRunning(true);
				npc.setState(MAHUM_EFFECT_NONE);
				break;
			}
			case "reset_full_bottle_prize":
			{
				npc.getVariables().remove("FULL_BARREL_REWARDING_PLAYER");
				break;
			}
			case "return_from_fire":
			{
				if (npc.isMonster() && !npc.isDead())
				{
					((L2MonsterInstance) npc).returnHome();
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill)
	{
		if ((npc.getId() == CHEF) && (npc.getVariables().getInt("BUSY_STATE") == 0))
		{
			if (npc.getVariables().getInt("INVUL_REMOVE_TIMER_STARTED") == 0)
			{
				startQuestTimer("chef_remove_invul", 180000, npc, attacker);
				startQuestTimer("chef_disable_reward", 60000, npc, null);
				npc.getVariables().set("INVUL_REMOVE_TIMER_STARTED", 1);
			}
			startQuestTimer("chef_heal_player", 1000, npc, attacker);
			startQuestTimer("chef_set_invul", 60000, npc, null);
			npc.getVariables().set("BUSY_STATE", 1);
		}
		else if (Util.contains(SQUAD_LEADERS, npc.getId()))
		{
			handlePreAttackMotion(npc);
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	@Override
	public String onFactionCall(L2Npc npc, L2Npc caller, L2PcInstance attacker, boolean isSummon)
	{
		handlePreAttackMotion(npc);
		return super.onFactionCall(npc, caller, attacker, isSummon);
	}
	
	@Override
	public String onEventReceived(String eventName, L2Npc sender, L2Npc receiver, L2Object reference)
	{
		switch (eventName)
		{
			case "SCE_DINNER_CHECK":
			{
				if (receiver.getId() == FIRE)
				{
					receiver.setState(FIRE_EFFECT_BURN);
					final L2Npc stove = addSpawn(STOVE, receiver.getX(), receiver.getY(), receiver.getZ() + 100, 0, false, 0);
					stove.setSummoner(receiver);
					startQuestTimer("notify_dinner", 2000, receiver, null); // @SCE_DINNER_EAT
					broadcastNpcSay(sender, ChatType.NPC_GENERAL, CHEF_FSTRINGS[getRandom(2)], 1250);
				}
				break;
			}
			case "SCE_CAMPFIRE_START":
			{
				if (!receiver.isNoRndWalk() && !receiver.isDead() && (receiver.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK) && Util.contains(SQUAD_LEADERS, receiver.getId()))
				{
					receiver.setIsNoRndWalk(true); // Moving to fire - i_ai0 = 1
					receiver.setIsRunning(true);
					final Location loc = sender.getPointInRange(100, 200);
					loc.setHeading(receiver.getHeading());
					receiver.stopMove(null);
					receiver.getVariables().set("DESTINATION_X", loc.getX());
					receiver.getVariables().set("DESTINATION_Y", loc.getY());
					receiver.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
				}
				break;
			}
			case "SCE_CAMPFIRE_END":
			{
				if ((receiver.getId() == STOVE) && (receiver.getSummoner() == sender))
				{
					receiver.deleteMe();
				}
				else if ((receiver.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK) && Util.contains(SQUAD_LEADERS, receiver.getId()))
				{
					receiver.setIsNoRndWalk(false);
					receiver.getVariables().remove("BUSY_STATE");
					receiver.setRHandId(THS_Weapon);
					startQuestTimer("return_from_fire", 3000, receiver, null);
				}
				break;
			}
			case "SCE_DINNER_EAT":
			{
				if (!receiver.isDead() && (receiver.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK) && (receiver.getVariables().getInt("BUSY_STATE", 0) == 0) && Util.contains(SQUAD_LEADERS, receiver.getId()))
				{
					if (receiver.isNoRndWalk()) // i_ai0 == 1
					{
						receiver.setRHandId(THS_Weapon);
					}
					receiver.setIsNoRndWalk(true); // Moving to fire - i_ai0 = 1
					receiver.getVariables().set("BUSY_STATE", 1); // Eating - i_ai3 = 1
					receiver.setIsRunning(true);
					broadcastNpcSay(receiver, ChatType.NPC_GENERAL, (getRandom(3) < 1) ? NpcStringId.LOOKS_DELICIOUS : NpcStringId.LET_S_GO_EAT);
					final Location loc = sender.getPointInRange(100, 200);
					loc.setHeading(receiver.getHeading());
					receiver.stopMove(null);
					receiver.getVariables().set("DESTINATION_X", loc.getX());
					receiver.getVariables().set("DESTINATION_Y", loc.getY());
					receiver.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
				}
				break;
			}
			case "SCE_SOUP_FAILURE":
			{
				if (Util.contains(SQUAD_LEADERS, receiver.getId()))
				{
					receiver.getVariables().set("FULL_BARREL_REWARDING_PLAYER", reference.getObjectId()); // TODO: Use it in 289 quest
					startQuestTimer("reset_full_bottle_prize", 180000, receiver, null);
				}
				break;
			}
		}
		return super.onEventReceived(eventName, sender, receiver, reference);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc.isMonster() && (npc.getVariables().getInt("REWARD_TIME_GONE") == 0))
		{
			((L2MonsterInstance) npc).dropItem(killer, 15492, 1);
		}
		cancelQuestTimer("chef_remove_invul", npc, null);
		cancelQuestTimer("chef_disable_reward", npc, null);
		cancelQuestTimer("chef_heal_player", npc, null);
		cancelQuestTimer("chef_set_invul", npc, null);
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public void onMoveFinished(L2Npc npc)
	{
		// Npc moves to fire
		if (npc.isNoRndWalk() && (npc.getX() == npc.getVariables().getInt("DESTINATION_X")) && (npc.getY() == npc.getVariables().getInt("DESTINATION_Y")))
		{
			npc.setRHandId(OHS_Weapon);
			startQuestTimer("fire_arrived", 3000, npc, null);
		}
	}
	
	@Override
	public void onNodeArrived(L2Npc npc)
	{
		npc.broadcastEvent("SCE_DINNER_CHECK", 300, null);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, Skill skill, L2Object[] targets, boolean isSummon)
	{
		if ((npc.getId() == STOVE) && (skill.getId() == 9075) && Util.contains(targets, npc))
		{
			npc.doCast(SkillData.getInstance().getSkill(6688, 1));
			npc.broadcastEvent("SCE_SOUP_FAILURE", 600, caster);
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.getId() == CHEF)
		{
			npc.setIsInvul(false);
		}
		else if (npc.getId() == FIRE)
		{
			startQuestTimer("fire", 1000, npc, null);
		}
		else if (Util.contains(SQUAD_LEADERS, npc.getId()))
		{
			npc.setState(3);
			npc.setIsNoRndWalk(false);
		}
		return super.onSpawn(npc);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, Skill skill)
	{
		if ((skill != null) && (skill.getId() == 6330))
		{
			healPlayer(npc, player);
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	private void healPlayer(L2Npc npc, L2PcInstance player)
	{
		if ((player != null) && !player.isDead() && (npc.getVariables().getInt("INVUL_REMOVE_TIMER_STARTED") != 1) && ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK) || (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_CAST)))
		{
			npc.setTarget(player);
			npc.doCast(SkillData.getInstance().getSkill(6330, 1));
		}
		else
		{
			cancelQuestTimer("chef_set_invul", npc, null);
			npc.getVariables().remove("BUSY_STATE");
			npc.getVariables().remove("INVUL_REMOVE_TIMER_STARTED");
			npc.setIsRunning(false);
		}
	}
	
	private void handlePreAttackMotion(L2Npc attacked)
	{
		cancelQuestTimer("remove_effects", attacked, null);
		attacked.getVariables().remove("BUSY_STATE");
		attacked.setIsNoRndWalk(false);
		attacked.setState(MAHUM_EFFECT_NONE);
		if (attacked.getRightHandItem() == OHS_Weapon)
		{
			attacked.setRHandId(THS_Weapon);
		}
		// TODO: Check about i_quest0
	}
	
	public static void main(String[] args)
	{
		new SelMahumSquad();
	}
}
