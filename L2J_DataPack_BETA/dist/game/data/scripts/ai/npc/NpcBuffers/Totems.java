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
package ai.npc.NpcBuffers;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;

/**
 * @author UnAfraid
 */
public class Totems extends AbstractNpcAI
{
	private static final int TOTEM_OF_BODY = 143;
	private static final int TOTEM_OF_SPIRIT = 144;
	private static final int TOTEM_OF_BRAVERY = 145;
	private static final int TOTEM_OF_FORTITUDE = 146;
	
	protected Totems(String name, String descr)
	{
		super(name, descr);
		addFirstTalkId(TOTEM_OF_BODY, TOTEM_OF_SPIRIT, TOTEM_OF_BRAVERY, TOTEM_OF_FORTITUDE);
		addSpawnId(TOTEM_OF_BODY, TOTEM_OF_SPIRIT, TOTEM_OF_BRAVERY, TOTEM_OF_FORTITUDE);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		addTask(npc);
		return super.onSpawn(npc);
	}
	
	private void addTask(L2Npc npc)
	{
		final SkillHolder holder;
		switch (npc.getNpcId())
		{
			case TOTEM_OF_BODY:
			{
				holder = new SkillHolder(23308, 1);
				break;
			}
			case TOTEM_OF_SPIRIT:
			{
				holder = new SkillHolder(23309, 1);
				break;
			}
			case TOTEM_OF_BRAVERY:
			{
				holder = new SkillHolder(23310, 1);
				break;
			}
			case TOTEM_OF_FORTITUDE:
			{
				holder = new SkillHolder(23311, 1);
				break;
			}
			default:
			{
				return;
			}
		}
		
		ThreadPoolManager.getInstance().scheduleGeneral(new TotemAI(npc, holder), 1000);
	}
	
	protected class TotemAI implements Runnable
	{
		private final L2Npc _npc;
		private final SkillHolder _holder;
		
		protected TotemAI(L2Npc cake, SkillHolder holder)
		{
			_npc = cake;
			_holder = holder;
		}
		
		@Override
		public void run()
		{
			if ((_npc == null) || !_npc.isVisible() || (_holder == null) || (_holder.getSkill() == null))
			{
				return;
			}
			
			L2Skill skill = _holder.getSkill();
			for (L2PcInstance player : _npc.getKnownList().getKnownPlayersInRadius(skill.getSkillRadius()))
			{
				if (player.getFirstEffect(skill.getId()) == null)
				{
					skill.getEffects(player, player);
				}
			}
			ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
		}
	}
	
	public static void main(String[] args)
	{
		new Totems(Totems.class.getSimpleName(), "ai/npc");
	}
}
