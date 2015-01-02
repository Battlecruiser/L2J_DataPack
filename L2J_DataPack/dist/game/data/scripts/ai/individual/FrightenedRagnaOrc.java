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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Frightened Ragna Orc AI.
 * @author Gladicek, malyelfik
 */
public final class FrightenedRagnaOrc extends AbstractNpcAI
{
	// NPC ID
	private static final int MOB_ID = 18807;
	// Chances
	private static final int ADENA = 10000;
	private static final int CHANCE = 1000;
	private static final int ADENA2 = 1000000;
	private static final int CHANCE2 = 10;
	// Skill
	private static final SkillHolder SKILL = new SkillHolder(6234, 1);
	
	private FrightenedRagnaOrc()
	{
		super(FrightenedRagnaOrc.class.getSimpleName(), "ai/individual");
		addAttackId(MOB_ID);
		addKillId(MOB_ID);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (npc.isScriptValue(0))
		{
			npc.setScriptValue(1);
			startQuestTimer("say", (getRandom(5) + 3) * 1000, npc, null, true);
		}
		else if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.2)) && npc.isScriptValue(1))
		{
			startQuestTimer("reward", 10000, npc, attacker);
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.WAIT_WAIT_STOP_SAVE_ME_AND_I_LL_GIVE_YOU_10_000_000_ADENA);
			npc.setScriptValue(2);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final NpcStringId msg = getRandomBoolean() ? NpcStringId.UGH_A_CURSE_UPON_YOU : NpcStringId.I_REALLY_DIDN_T_WANT_TO_FIGHT;
		broadcastNpcSay(npc, Say2.NPC_ALL, msg);
		cancelQuestTimer("say", npc, null);
		cancelQuestTimer("reward", npc, player);
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "say":
			{
				if (npc.isDead() || !npc.isScriptValue(1))
				{
					cancelQuestTimer("say", npc, null);
					return null;
				}
				final NpcStringId msg = getRandomBoolean() ? NpcStringId.I_DON_T_WANT_TO_FIGHT : NpcStringId.IS_THIS_REALLY_NECESSARY;
				broadcastNpcSay(npc, Say2.NPC_ALL, msg);
				break;
			}
			case "reward":
			{
				if (!npc.isDead() && npc.isScriptValue(2))
				{
					if (getRandom(100000) < CHANCE2)
					{
						final NpcStringId msg = getRandomBoolean() ? NpcStringId.TH_THANKS_I_COULD_HAVE_BECOME_GOOD_FRIENDS_WITH_YOU : NpcStringId.I_LL_GIVE_YOU_10_000_000_ADENA_LIKE_I_PROMISED_I_MIGHT_BE_AN_ORC_WHO_KEEPS_MY_PROMISES;
						broadcastNpcSay(npc, Say2.NPC_ALL, msg);
						npc.setScriptValue(3);
						npc.doCast(SKILL.getSkill());
						for (int i = 0; i < 10; i++)
						{
							npc.dropItem(player, Inventory.ADENA_ID, ADENA2);
						}
					}
					else if (getRandom(100000) < CHANCE)
					{
						final NpcStringId msg = getRandomBoolean() ? NpcStringId.TH_THANKS_I_COULD_HAVE_BECOME_GOOD_FRIENDS_WITH_YOU : NpcStringId.SORRY_BUT_THIS_IS_ALL_I_HAVE_GIVE_ME_A_BREAK;
						broadcastNpcSay(npc, Say2.NPC_ALL, msg);
						npc.setScriptValue(3);
						npc.doCast(SKILL.getSkill());
						for (int i = 0; i < 10; i++)
						{
							((L2Attackable) npc).dropItem(player, Inventory.ADENA_ID, ADENA);
						}
					}
					else
					{
						final NpcStringId msg = getRandomBoolean() ? NpcStringId.THANKS_BUT_THAT_THING_ABOUT_10_000_000_ADENA_WAS_A_LIE_SEE_YA : NpcStringId.YOU_RE_PRETTY_DUMB_TO_BELIEVE_ME;
						broadcastNpcSay(npc, Say2.NPC_ALL, msg);
					}
					startQuestTimer("despawn", 1000, npc, null);
				}
				break;
			}
			case "despawn":
			{
				npc.setRunning();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location((npc.getX() + getRandom(-800, 800)), (npc.getY() + getRandom(-800, 800)), npc.getZ(), npc.getHeading()));
				npc.deleteMe();
				break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new FrightenedRagnaOrc();
	}
}
