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

import java.util.Map;

import javolution.util.FastMap;

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
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Prison Guards AI.
 * @author Gigiikun
 */
public class PrisonGuards extends L2AttackableAIScript
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
	final private static String qn = "IOPRace";
	
	private final static int silence = 4098;
	private final static int pertification = 4578;
	private final static int eventTimer = 5239;
	
	private boolean _firstAttacked = false;
	
	private final Map<L2Npc, Integer> _guards = new FastMap<>();
	
	public PrisonGuards(int questId, String name, String descr)
	{
		super(questId, name, descr);
		int[] mob =
		{
			GUARD1,
			GUARD2
		};
		registerMobs(mob);
		
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
			if (npc.getNpcId() == GUARD1)
			{
				npc.setIsInvul(true);
				npc.disableCoreAI(true);
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("Respawn"))
		{
			L2Npc newGuard = addSpawn(npc.getNpcId(), npc.getSpawn().getLocx(), npc.getSpawn().getLocy(), npc.getSpawn().getLocz(), npc.getSpawn().getHeading(), false, 0);
			newGuard.setIsNoRndWalk(true);
			newGuard.setIsImmobilized(true);
			if (npc.getNpcId() == GUARD1)
			{
				newGuard.setIsInvul(true);
				newGuard.disableCoreAI(true);
			}
			
			int place = _guards.get(npc);
			_guards.remove(npc);
			_guards.put(newGuard, place);
		}
		else if (event.equalsIgnoreCase("attackEnd") && (npc.getNpcId() == GUARD2))
		{
			if ((npc.getX() != npc.getSpawn().getLocx()) || (npc.getY() != npc.getSpawn().getLocy()))
			{
				npc.teleToLocation(npc.getSpawn().getLocx(), npc.getSpawn().getLocy(), npc.getSpawn().getLocz(), npc.getSpawn().getHeading(), false);
				npc.setIsImmobilized(true);
			}
			((L2Attackable) npc).getAggroList().clear();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
		
		return null;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance player, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		L2Character caster = isPet ? player.getPet() : player;
		
		if (npc.getNpcId() == GUARD2)
		{
			if (_firstAttacked && (caster.getFirstEffect(eventTimer) == null))
			{
				if (caster.getFirstEffect(silence) == null)
				{
					castDebuff(npc, caster, silence, isPet, false, true);
				}
			}
		}
		
		return super.onSkillSee(npc, player, skill, targets, isPet);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2Character target = isPet ? player.getPet() : player;
		
		if (npc.getNpcId() == GUARD2)
		{
			if (target.getFirstEffect(eventTimer) != null)
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
				if ((npc.getX() != npc.getSpawn().getLocx()) || (npc.getY() != npc.getSpawn().getLocy()))
				{
					npc.teleToLocation(npc.getSpawn().getLocx(), npc.getSpawn().getLocy(), npc.getSpawn().getLocz(), npc.getSpawn().getHeading(), false);
					npc.setIsImmobilized(true);
				}
				((L2Attackable) npc).getAggroList().remove(target);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				return null;
			}
		}
		
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		L2Character attacker = isPet ? player.getPet() : player;
		
		_firstAttacked = true;
		
		if (attacker.getFirstEffect(eventTimer) == null)
		{
			if (attacker.getFirstEffect(pertification) == null)
			{
				castDebuff(npc, attacker, pertification, isPet, true, false);
			}
			
			npc.setTarget(null);
			((L2Attackable) npc).getAggroList().remove(attacker);
			((L2Attackable) npc).stopHating(attacker);
			((L2Attackable) npc).abortAttack();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			return null;
		}
		
		if (npc.getNpcId() == GUARD2)
		{
			cancelQuestTimer("attackEnd", null, null);
			startQuestTimer("attackEnd", 180000, npc, null);
			
			npc.setIsImmobilized(false);
			npc.setTarget(attacker);
			npc.setRunning();
			((L2Attackable) npc).addDamageHate(attacker, 0, 999);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
		else if ((npc.getNpcId() == GUARD1) && (getRandom(100) < 5))
		{
			final QuestState qs = player.getQuestState(qn);
			if ((qs != null) && (qs.getInt(GUARDVARS[_guards.get(npc)]) != 1))
			{
				qs.set(GUARDVARS[_guards.get(npc)], "1");
				qs.giveItems(STAMP, 1);
			}
		}
		
		return super.onAttack(npc, player, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (_guards.containsKey(npc))
		{
			startQuestTimer("Respawn", 20000, npc, null);
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	private void castDebuff(L2Npc npc, L2Character player, int effectId, boolean isSummon, boolean fromAttack, boolean isSpell)
	{
		if (fromAttack)
		{
			NpcStringId npcString = (npc.getNpcId() == GUARD1 ? NpcStringId.ITS_NOT_EASY_TO_OBTAIN : NpcStringId.YOURE_OUT_OF_YOUR_MIND_COMING_HERE);
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), npcString));
		}
		
		L2Skill skill = SkillTable.getInstance().getInfo(effectId, isSpell ? 9 : 1);
		if (skill != null)
		{
			npc.setTarget(isSummon ? player.getPet() : player);
			npc.doCast(skill);
		}
	}
	
	public static void main(String[] args)
	{
		new PrisonGuards(-1, PrisonGuards.class.getSimpleName(), "ai");
	}
}
