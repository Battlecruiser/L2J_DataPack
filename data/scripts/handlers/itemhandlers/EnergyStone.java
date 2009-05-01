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
package handlers.itemhandlers;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.EtcStatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.skills.effects.EffectCharge;
import net.sf.l2j.gameserver.skills.l2skills.L2SkillCharge;

public class EnergyStone implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		5589
	};
	private EffectCharge _effect;
	private L2SkillCharge _skill;
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		
		L2PcInstance activeChar;
		if (playable instanceof L2PcInstance)
		{
			activeChar = (L2PcInstance) playable;
		}
		else if (playable instanceof L2PetInstance)
		{
			activeChar = ((L2PetInstance) playable).getOwner();
		}
		else
			return;
		
		if (item.getItemId() != 5589)
			return;
		int classid = activeChar.getClassId().getId();
		
		if (classid == 2 || classid == 48 || classid == 88 || classid == 114)
		{
			
			if (activeChar.isAllSkillsDisabled())
			{
				ActionFailed af = ActionFailed.STATIC_PACKET;
				activeChar.sendPacket(af);
				return;
			}
			
			if (activeChar.isSitting())
			{
				activeChar.sendPacket(new SystemMessage(SystemMessageId.CANT_MOVE_SITTING));
				return;
			}
			
			_skill = getChargeSkill(activeChar);
			if (_skill == null)
			{
				SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
				sm.addItemName(5589);
				activeChar.sendPacket(sm);
				return;
			}
			
			_effect = activeChar.getChargeEffect();
			
			if (_effect == null)
			{
				L2Skill dummy = SkillTable.getInstance().getInfo(_skill.getId(), _skill.getLevel());
				if (dummy != null)
				{
					dummy.getEffects(activeChar, activeChar);
					activeChar.destroyItemWithoutTrace("Consume", item.getObjectId(), 1, null, false);
					return;
				}
				return;
			}
			
			if (_effect.getLevel() < 2)
			{
				MagicSkillUse MSU = new MagicSkillUse(playable, activeChar, _skill.getId(), 1, 1, 0);
				activeChar.sendPacket(MSU);
				activeChar.broadcastPacket(MSU);
				_effect.addNumCharges(1);
				activeChar.sendPacket(new EtcStatusUpdate(activeChar));
				activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
			}
			else if (_effect.getLevel() == 2)
			{
				activeChar.sendPacket(new SystemMessage(SystemMessageId.FORCE_MAXLEVEL_REACHED));
			}
			SystemMessage sm = new SystemMessage(SystemMessageId.FORCE_INCREASED_TO_S1);
			sm.addNumber(_effect.getLevel());
			activeChar.sendPacket(sm);
			return;
		}
		else
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addItemName(5589);
			activeChar.sendPacket(sm);
			return;
		}
	}
	
	/**
	 * 
	 * @param activeChar
	 * @return
	 */
	private L2SkillCharge getChargeSkill(L2PcInstance activeChar)
	{
		L2Skill[] skills = activeChar.getAllSkills();
		for (L2Skill s : skills)
		{
			if (s.getId() == 50 || s.getId() == 8)
			{
				return (L2SkillCharge) s;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}