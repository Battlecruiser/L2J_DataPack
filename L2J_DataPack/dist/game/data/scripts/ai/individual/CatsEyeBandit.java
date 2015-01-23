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

import quests.Q00403_PathOfTheRogue.Q00403_PathOfTheRogue;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Cat's Eye Bandit (Quest Monster) AI.
 * @author Gladicek
 */
public final class CatsEyeBandit extends AbstractNpcAI
{
	// NPC ID
	private static final int MOB_ID = 27038;
	// Weapons
	private static final int BOW = 1181;
	private static final int DAGGER = 1182;
	
	private CatsEyeBandit()
	{
		super(CatsEyeBandit.class.getSimpleName(), "ai/individual");
		addAttackId(MOB_ID);
		addKillId(MOB_ID);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		final QuestState qs = attacker.getQuestState(Q00403_PathOfTheRogue.class.getSimpleName());
		if (npc.isScriptValue(0) && (qs != null) && ((getItemEquipped(attacker, Inventory.PAPERDOLL_RHAND) == BOW) || (getItemEquipped(attacker, Inventory.PAPERDOLL_RHAND) == DAGGER)))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOU_CHILDISH_FOOL_DO_YOU_THINK_YOU_CAN_CATCH_ME);
			npc.setScriptValue(1);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = killer.getQuestState(Q00403_PathOfTheRogue.class.getSimpleName());
		if (qs != null)
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.I_MUST_DO_SOMETHING_ABOUT_THIS_SHAMEFUL_INCIDENT);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new CatsEyeBandit();
	}
}