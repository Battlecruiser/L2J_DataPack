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

import com.l2jserver.gameserver.datatables.MapRegionTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.instancemanager.CastleManorManager;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Manor;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2ChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.SkillHolder;

/**
 * @author  l3x
 */
public class Seed implements IItemHandler
{	
	private int _seedId;
	private L2MonsterInstance _target;
	private L2PcInstance _activeChar;
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		if (CastleManorManager.getInstance().isDisabled())
			return;
		
		_activeChar = (L2PcInstance) playable;
		L2Object target_ = _activeChar.getTarget();
		
		if (!(target_ instanceof L2Npc))
		{
			_activeChar.sendPacket(new SystemMessage(SystemMessageId.INCORRECT_TARGET));
			_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		L2Character target = ((L2Character)target_);
		if (!(target instanceof L2MonsterInstance) || target instanceof L2ChestInstance || target.isRaid())
		{
			_activeChar.sendPacket(new SystemMessage(SystemMessageId.THE_TARGET_IS_UNAVAILABLE_FOR_SEEDING));
			_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		_target = (L2MonsterInstance) target;
		
		if (_target == null || _target.isDead())
		{
			_activeChar.sendPacket(new SystemMessage(SystemMessageId.INCORRECT_TARGET));
			_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (_target.isSeeded())
		{
			_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		_seedId = item.getItemId();
		
		if (areaValid(MapRegionTable.getInstance().getAreaCastle(_activeChar)))
		{
			_target.setSeeded(_seedId, _activeChar);
			final SkillHolder[] skills = item.getEtcItem().getSkills();
			if (skills != null)
			{
				if(skills[0] == null)
					return;
				
				L2Skill itemskill = skills[0].getSkill();
				_activeChar.useMagic(itemskill, false, false);
			}
			
		}
		else
		{
			_activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_SEED_MAY_NOT_BE_SOWN_HERE));
		}
	}
	
	/**
	 * 
	 * @param castleId
	 * @return
	 */
	private boolean areaValid(int castleId)
	{
		return (L2Manor.getInstance().getCastleIdForSeed(_seedId) == castleId);
	}
}