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
package ai.individual;

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
public class CatsEyeBandit extends AbstractNpcAI
{
	// NPC ID
	private static final int MOB_ID = 27038;
	// Weapons
	private static final int BOW = 1181;
	private static final int DAGGER = 1182;
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		final QuestState qs = attacker.getQuestState("403_PathToRogue"); // TODO: Replace with class name.
		if (npc.isScriptValue(0) && (qs != null) && ((qs.getItemEquipped(Inventory.PAPERDOLL_RHAND) == BOW) || (qs.getItemEquipped(Inventory.PAPERDOLL_RHAND) == DAGGER)))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOU_CHILDISH_FOOL_DO_YOU_THINK_YOU_CAN_CATCH_ME);
			npc.setScriptValue(1);
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		final QuestState qs = killer.getQuestState("403_PathToRogue"); // TODO: Replace with class name.
		if (qs != null)
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.I_MUST_DO_SOMETHING_ABOUT_THIS_SHAMEFUL_INCIDENT);
			npc.setScriptValue(0);
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public CatsEyeBandit(String name, String descr)
	{
		super(name, descr);
		addAttackId(MOB_ID);
		addKillId(MOB_ID);
	}
	
	public static void main(String[] args)
	{
		new CatsEyeBandit(CatsEyeBandit.class.getSimpleName(), "ai");
	}
}