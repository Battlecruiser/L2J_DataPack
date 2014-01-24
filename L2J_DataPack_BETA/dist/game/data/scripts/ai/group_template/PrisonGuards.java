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
package ai.group_template;

import java.util.Map;

import javolution.util.FastMap;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

import custom.IOPRace.IOPRace;

/**
 * Prison Guards AI.
 * @author Gigiikun
 */
public class PrisonGuards extends AbstractNpcAI
{
	final private static int GUARD1 = 18367;
	final private static int GUARD2 = 18368;
	final private static int STAMP = 10013;
	
	final private static String[] GUARDVARS =
	{
		"1st",
		"2nd",
		"3rd",
		"4th"
	};
	
	private final static int SKILL_SILENCE = 4098;
	private final static int SKILL_PERTIFICATION = 4578;
	private final static int SKILL_EVENT_TIMER = 5239;
	
	private boolean _firstAttacked = false;
	
	private final Map<L2Npc, Integer> _guards = new FastMap<>();
	
	private PrisonGuards()
	{
		super(PrisonGuards.class.getSimpleName(), "ai/group_template");
		registerMobs(GUARD1, GUARD2);
		
		// place 1
		_guards.put(addSpawn(GUARD2, 160704, 184704, -3704, 49152, false, 0), 0);
		_guards.put(addSpawn(GUARD2, 160384, 184704, -3704, 49152, false, 0), 0);
		_guards.put(addSpawn(GUARD1, 160528, 185216, -3704, 49152, false, 0), 0);
		// place 2
		_guards.put(addSpawn(GUARD2, 135120, 171856, -3704, 49152, false, 0), 1);
		_guards.put(addSpawn(GUARD2, 134768, 171856, -3704, 49152, false, 0), 1);
		_guards.put(addSpawn(GUARD1, 134928, 172432, -3704, 49152, false, 0), 1);
		// place 3
		_guards.put(addSpawn(GUARD2, 146880, 151504, -2872, 49152, false, 0), 2);
		_guards.put(addSpawn(GUARD2, 146366, 151506, -2872, 49152, false, 0), 2);
		_guards.put(addSpawn(GUARD1, 146592, 151888, -2872, 49152, false, 0), 2);
		// place 4
		_guards.put(addSpawn(GUARD2, 155840, 160448, -3352, 0, false, 0), 3);
		_guards.put(addSpawn(GUARD2, 155840, 159936, -3352, 0, false, 0), 3);
		_guards.put(addSpawn(GUARD1, 155578, 160177, -3352, 0, false, 0), 3);
		
		for (L2Npc npc : _guards.keySet())
		{
			npc.setIsNoRndWalk(true);
			npc.setIsImmobilized(true);
			if (npc.getId() == GUARD1)
			{
				npc.setIsInvul(true);
				npc.disableCoreAI(true);
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("Respawn"))
		{
			L2Npc newGuard = addSpawn(npc.getId(), npc.getSpawn().getX(), npc.getSpawn().getY(), npc.getSpawn().getZ(), npc.getSpawn().getHeading(), false, 0);
			newGuard.setIsNoRndWalk(true);
			newGuard.setIsImmobilized(true);
			if (npc.getId() == GUARD1)
			{
				newGuard.setIsInvul(true);
				newGuard.disableCoreAI(true);
			}
			
			int place = _guards.get(npc);
			_guards.remove(npc);
			_guards.put(newGuard, place);
		}
		else if (event.equals("attackEnd") && (npc.getId() == GUARD2))
		{
			if ((npc.getX() != npc.getSpawn().getX()) || (npc.getY() != npc.getSpawn().getY()))
			{
				npc.teleToLocation(npc.getSpawn().getLocation());
				npc.setIsImmobilized(true);
			}
			((L2Attackable) npc).getAggroList().clear();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
		
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance player, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		L2Character caster = isSummon ? player.getSummon() : player;
		
		if (npc.getId() == GUARD2)
		{
			if (_firstAttacked && !caster.isAffectedBySkill(SKILL_EVENT_TIMER))
			{
				if (!caster.isAffectedBySkill(SKILL_SILENCE))
				{
					castDebuff(npc, caster, SKILL_SILENCE, isSummon, false, true);
				}
			}
		}
		
		return super.onSkillSee(npc, player, skill, targets, isSummon);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2Character target = isSummon ? player.getSummon() : player;
		
		if (npc.getId() == GUARD2)
		{
			if (target.isAffectedBySkill(SKILL_EVENT_TIMER))
			{
				cancelQuestTimer("attackEnd", null, null);
				startQuestTimer("attackEnd", 180000, npc, null);
				
				npc.setIsImmobilized(false);
				npc.setTarget(target);
				npc.setRunning();
				((L2Attackable) npc).addDamageHate(target, 0, 999);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			}
			else
			{
				if ((npc.getX() != npc.getSpawn().getX()) || (npc.getY() != npc.getSpawn().getY()))
				{
					npc.teleToLocation(npc.getSpawn().getLocation());
					npc.setIsImmobilized(true);
				}
				((L2Attackable) npc).getAggroList().remove(target);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				return null;
			}
		}
		
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		L2Character attacker = isSummon ? player.getSummon() : player;
		
		_firstAttacked = true;
		
		if (!attacker.isAffectedBySkill(SKILL_EVENT_TIMER))
		{
			if (!attacker.isAffectedBySkill(SKILL_PERTIFICATION))
			{
				castDebuff(npc, attacker, SKILL_PERTIFICATION, isSummon, true, false);
			}
			
			npc.setTarget(null);
			((L2Attackable) npc).getAggroList().remove(attacker);
			((L2Attackable) npc).stopHating(attacker);
			((L2Attackable) npc).abortAttack();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			return null;
		}
		
		if (npc.getId() == GUARD2)
		{
			cancelQuestTimer("attackEnd", null, null);
			startQuestTimer("attackEnd", 180000, npc, null);
			
			npc.setIsImmobilized(false);
			npc.setTarget(attacker);
			npc.setRunning();
			((L2Attackable) npc).addDamageHate(attacker, 0, 999);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
		else if ((npc.getId() == GUARD1) && (getRandom(100) < 5))
		{
			final QuestState qs = player.getQuestState(IOPRace.class.getSimpleName());
			if ((qs != null) && (qs.getInt(GUARDVARS[_guards.get(npc)]) != 1))
			{
				qs.set(GUARDVARS[_guards.get(npc)], "1");
				qs.giveItems(STAMP, 1);
			}
		}
		
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (_guards.containsKey(npc))
		{
			startQuestTimer("Respawn", 20000, npc, null);
		}
		
		return super.onKill(npc, player, isSummon);
	}
	
	private void castDebuff(L2Npc npc, L2Character player, int effectId, boolean isSummon, boolean fromAttack, boolean isSpell)
	{
		if (fromAttack)
		{
			NpcStringId npcString = (npc.getId() == GUARD1 ? NpcStringId.ITS_NOT_EASY_TO_OBTAIN : NpcStringId.YOURE_OUT_OF_YOUR_MIND_COMING_HERE);
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), npcString));
		}
		
		L2Skill skill = SkillTable.getInstance().getInfo(effectId, isSpell ? 9 : 1);
		if (skill != null)
		{
			npc.setTarget(isSummon ? player.getSummon() : player);
			npc.doCast(skill);
		}
	}
	
	public static void main(String[] args)
	{
		new PrisonGuards();
	}
}
