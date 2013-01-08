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
package ai.npc.NpcBuffers;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.ZoneId;

/**
 * @author Drunkard, Zabb0x
 */
public class ChristmasTree extends AbstractNpcAI
{
	private static final int CHRISTMAS_TREE = 13007;
	
	protected ChristmasTree(String name, String descr)
	{
		super(name, descr);
		addFirstTalkId(CHRISTMAS_TREE);
		addSpawnId(CHRISTMAS_TREE);
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
		final SkillHolder holder = new SkillHolder(2139, 1);
		ThreadPoolManager.getInstance().scheduleGeneral(new ChristmasTreeAI(npc, holder), 1000);
	}
	
	protected class ChristmasTreeAI implements Runnable
	{
		private final L2Npc _npc;
		private final SkillHolder _holder;
		
		protected ChristmasTreeAI(L2Npc npc, SkillHolder holder)
		{
			_npc = npc;
			_holder = holder;
		}
		
		@Override
		public void run()
		{
			if ((_npc == null) || !_npc.isVisible() || (_holder == null) || (_holder.getSkill() == null))
			{
				return;
			}
			
			if (!_npc.isInsideZone(ZoneId.PEACE))
			{
				L2Skill skill = _holder.getSkill();
				
				if (_npc.getSummoner() == null || !_npc.getSummoner().isPlayer())
				{
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
					return;
				}
				
				final L2PcInstance player = _npc.getSummoner().getActingPlayer();
				
				if (!player.isInParty())
				{
					if (player.isInsideRadius(_npc, skill.getSkillRadius(), true, true))
					{
						skill.getEffects(_npc, player);
					}
				}
				else
				{
					for (L2PcInstance member : player.getParty().getMembers())
					{
						if ((member != null) && member.isInsideRadius(_npc, skill.getSkillRadius(), true, true))
						{
							skill.getEffects(_npc, member);
						}
					}
				}
			}
			ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
		}
	}
	
	public static void main(String[] args)
	{
		new ChristmasTree(ChristmasTree.class.getSimpleName(), "ai/npc");
	}
}
