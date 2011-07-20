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
package handlers.skillhandlers;

import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Attackable.RewardItem;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.l2skills.L2SkillSweeper;
import com.l2jserver.gameserver.templates.skills.L2SkillType;

/**
 * @author _drunk_, Zoey76
 */
public class Sweep implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS = { L2SkillType.SWEEP };
	
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
		{
			return;
		}
		
		final L2PcInstance player = activeChar.getActingPlayer();
		RewardItem[] items = null;
		L2Attackable target;
		L2SkillSweeper sweep;
		SystemMessage sm;
		
		for (L2Object tgt : targets)
		{
			if (!(tgt instanceof L2Attackable))
			{
				continue;
			}
			target = (L2Attackable) tgt;
			boolean isSweeping = false;
			synchronized (target)
			{
				if (target.isSweepActive())
				{
					items = target.takeSweep();
					isSweeping = true;
				}
			}
			if (isSweeping)
			{
				if ((items == null) || (items.length == 0))
				{
					continue;
				}
				for (RewardItem ritem : items)
				{
					if (player.isInParty())
					{
						player.getParty().distributeItem(player, ritem, true, target);
					}
					else
					{
						player.addItem("Sweep", ritem.getItemId(), ritem.getCount(), player, true);
					}
				}
			}
			target.endDecayTask();
			
			sweep = (L2SkillSweeper) skill;
			if (sweep.getAbsorbAbs() != -1)
			{
				int restored = 0;
				double absorb = 0;
				final StatusUpdate su = new StatusUpdate(activeChar);
				final int abs = sweep.getAbsorbAbs();
				if (sweep.isAbsorbHp())
				{
					absorb = ((activeChar.getCurrentHp() + abs) > activeChar.getMaxHp() ? activeChar.getMaxHp() : (activeChar.getCurrentHp() + abs));
					restored = (int) (absorb - activeChar.getCurrentHp());
					activeChar.setCurrentHp(absorb);
					
					su.addAttribute(StatusUpdate.CUR_HP, (int) absorb);
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
				}
				else
				{
					absorb = ((activeChar.getCurrentMp() + abs) > activeChar.getMaxMp() ? activeChar.getMaxMp() : (activeChar.getCurrentMp() + abs));
					restored = (int) (absorb - activeChar.getCurrentMp());
					activeChar.setCurrentMp(absorb);
					
					su.addAttribute(StatusUpdate.CUR_MP, (int) absorb);
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_MP_RESTORED);
				}
				activeChar.sendPacket(su);
				sm.addNumber(restored);
				activeChar.sendPacket(sm);
			}
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
