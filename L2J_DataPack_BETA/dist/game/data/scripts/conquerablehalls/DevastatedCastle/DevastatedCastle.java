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
package conquerablehalls.DevastatedCastle;

import gnu.trove.TIntIntHashMap;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.ClanHallSiegeEngine;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * @author BiggBoss
 * Devastated Castle clan hall siege script
 */
public final class DevastatedCastle extends ClanHallSiegeEngine
{		
	private static final String qn = "DevastatedCastle";
	
	private static final int GUSTAV = 35410;
	private static final int MIKHAIL = 35409;
	private static final int DIETRICH = 35408;
	private static final double GUSTAV_TRIGGER_HP = NpcTable.getInstance().getTemplate(GUSTAV).baseHpMax / 12;
	
	private static TIntIntHashMap _damageToGustav = new TIntIntHashMap();

	public DevastatedCastle(int questId, String name, String descr, int hallId)
	{
		super(questId, name, descr, hallId);
		addKillId(GUSTAV);
		
		addSpawnId(MIKHAIL);
		addSpawnId(DIETRICH);
		
		addAttackId(GUSTAV);	
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if(npc.getNpcId() == MIKHAIL)
			broadcastNpcSay(npc, Say2.SHOUT, 1000276);
		else if(npc.getNpcId() == DIETRICH)
			broadcastNpcSay(npc, Say2.SHOUT, 1000277);
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if(!_hall.isInSiege())
			return null;
		
		synchronized(this)
		{
			final L2Clan clan = attacker.getClan();
				
			if(clan != null && checkIsAttacker(clan))
			{
				final int id = clan.getClanId();
				if(_damageToGustav.containsKey(id))
				{
					int newDamage = _damageToGustav.get(id);
					newDamage += damage;
					_damageToGustav.put(id, newDamage);
				}
				else
					_damageToGustav.put(id, damage);
			}
			
			if(npc.getCurrentHp() < GUSTAV_TRIGGER_HP
					&& npc.getAI().getIntention() != CtrlIntention.AI_INTENTION_CAST)
			{
				broadcastNpcSay(npc, Say2.ALL, 1000278);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_CAST, SkillTable.getInstance().getInfo(4235, 1), npc);
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if(!_hall.isInSiege()) 
			return null;
		
		_missionAccomplished = true;

		if(npc.getNpcId() == GUSTAV)
		{
			synchronized(this)
			{
				cancelSiegeTask();
				endSiege();
			}
		}
			
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public L2Clan getWinner()
	{
		double counter = 0;
		int damagest = 0;
		for(int clan : _damageToGustav.keys())
		{
			final double damage = _damageToGustav.get(clan);
			if(damage > counter)
			{
				counter = damage;
				damagest = clan;
			}
		}
		L2Clan winner = ClanTable.getInstance().getClan(damagest);
		return winner;
	}
	
	public static void main(String[] args)
	{
		new DevastatedCastle(-1, qn, "conquerablehalls", DEVASTATED_CASTLE);
	}
}