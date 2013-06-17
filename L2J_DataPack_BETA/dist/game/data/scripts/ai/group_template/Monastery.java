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

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Monastery AI.
 * @author Kerberos, nonom
 */
public class Monastery extends AbstractNpcAI
{
	private static final int CAPTAIN = 18910;
	private static final int KNIGHT = 18909;
	private static final int SCARECROW = 18912;
	
	private static final int[] SOLINA_CLAN =
	{
		22789, // Guide Solina
		22790, // Seeker Solina
		22791, // Savior Solina
		22793, // Ascetic Solina
	};
	
	private static final int[] DIVINITY_CLAN =
	{
		22794, // Divinity Judge
		22795, // Divinity Manager
	};
	
	private static final NpcStringId[] SOLINA_KNIGHTS_MSG =
	{
		NpcStringId.PUNISH_ALL_THOSE_WHO_TREAD_FOOTSTEPS_IN_THIS_PLACE,
		NpcStringId.WE_ARE_THE_SWORD_OF_TRUTH_THE_SWORD_OF_SOLINA,
		NpcStringId.WE_RAISE_OUR_BLADES_FOR_THE_GLORY_OF_SOLINA
	};
	
	private static final NpcStringId[] DIVINITY_MSG =
	{
		NpcStringId.S1_WHY_WOULD_YOU_CHOOSE_THE_PATH_OF_DARKNESS,
		NpcStringId.S1_HOW_DARE_YOU_DEFY_THE_WILL_OF_EINHASAD
	};
	
	private static final SkillHolder DECREASE_SPEED = new SkillHolder(4589, 8);
	
	private Monastery()
	{
		super(Monastery.class.getSimpleName(), "ai/group_template");
		addAggroRangeEnterId(SOLINA_CLAN);
		addAggroRangeEnterId(CAPTAIN, KNIGHT);
		addSpellFinishedId(SOLINA_CLAN);
		addSkillSeeId(DIVINITY_CLAN);
		addAttackId(KNIGHT, CAPTAIN);
		addSpawnId(KNIGHT);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(KNIGHT))
		{
			startQuestTimer("training", 5000, spawn.getLastSpawn(), null, true);
		}
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(SCARECROW))
		{
			spawn.getLastSpawn().setIsInvul(true);
			spawn.getLastSpawn().disableCoreAI(true);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("training") && !npc.isInCombat() && (getRandom(100) < 25))
		{
			for (L2Character character : npc.getKnownList().getKnownCharactersInRadius(300))
			{
				if (character.isNpc() && (((L2Npc) character).getNpcId() == SCARECROW))
				{
					for (L2Skill skill : npc.getAllSkills())
					{
						if (skill.isActive())
						{
							npc.disableSkill(skill, 0);
						}
					}
					npc.setRunning();
					((L2Attackable) npc).addDamageHate(character, 0, 100);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, character, null);
					break;
				}
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (player.getActiveWeaponInstance() == null)
		{
			npc.setTarget(null);
			((L2Attackable) npc).disableAllSkills();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			return super.onAggroRangeEnter(npc, player, isSummon);
		}
		
		if (player.isVisible() && !player.isGM())
		{
			npc.setRunning();
			npc.setTarget(player);
			((L2Attackable) npc).enableAllSkills();
			if (Util.contains(SOLINA_CLAN, npc.getNpcId()))
			{
				if (getRandom(10) < 3)
				{
					broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOU_CANNOT_CARRY_A_WEAPON_WITHOUT_AUTHORIZATION);
				}
				npc.doCast(DECREASE_SPEED.getSkill());
			}
			((L2Attackable) npc).addDamageHate(player, 0, 100);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player, null);
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		if (skill.hasEffectType(L2EffectType.AGGRESSION) && (targets.length != 0))
		{
			for (L2Object obj : targets)
			{
				if (obj.equals(npc))
				{
					NpcSay packet = new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getNpcId(), DIVINITY_MSG[getRandom(1)]);
					packet.addStringParameter(caster.getName());
					npc.broadcastPacket(packet);
					((L2Attackable) npc).addDamageHate(caster, 0, 999);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, caster);
					break;
				}
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		if (getRandom(10) < 1)
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, SOLINA_KNIGHTS_MSG[getRandom(2)]);
		}
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.FOR_THE_GLORY_OF_SOLINA);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (skill.getId() == DECREASE_SPEED.getSkillId())
		{
			npc.setIsRunning(true);
			((L2Attackable) npc).addDamageHate(player, 0, 999);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	public static void main(String[] args)
	{
		new Monastery();
	}
}
