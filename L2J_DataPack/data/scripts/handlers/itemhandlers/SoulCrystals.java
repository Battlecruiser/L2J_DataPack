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

import java.util.logging.Level;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;


/**
 * This class ...
 *
 * @version $Revision: 1.2.4 $ $Date: 2005/08/14 21:31:07 $
 */

public class SoulCrystals implements IItemHandler
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		L2Object target = activeChar.getTarget();
		if (!(target instanceof L2MonsterInstance))
		{
			// Send a System Message to the caster
			SystemMessage sm = new SystemMessage(SystemMessageId.INCORRECT_TARGET);
			activeChar.sendPacket(sm);
			
			// Send a Server->Client packet ActionFailed to the L2PcInstance
			ActionFailed af = ActionFailed.STATIC_PACKET;
			activeChar.sendPacket(af);
			
			return;
		}
		
		// u can use soul crystal only when target hp goes below 50%
		if (((L2MonsterInstance) target).getCurrentHp() > ((L2MonsterInstance) target).getMaxHp() / 2.0)
		{
			ActionFailed af = ActionFailed.STATIC_PACKET;
			activeChar.sendPacket(af);
			return;
		}
		
		int crystalId = item.getItemId();
		
		// Soul Crystal Casting section
		L2Skill skill = SkillTable.getInstance().getInfo(2096, 1);
		activeChar.useMagic(skill, false, true);
		// End Soul Crystal Casting section
		
		// Continue execution later
		CrystalFinalizer cf = new CrystalFinalizer(activeChar, target, crystalId);
		ThreadPoolManager.getInstance().scheduleEffect(cf, skill.getHitTime());
		
	}
	
	// TODO: this should be inside skill handler
	static class CrystalFinalizer implements Runnable
	{
		private L2PcInstance _activeChar;
		private L2Attackable _target;
		private int _crystalId;
		
		CrystalFinalizer(L2PcInstance activeChar, L2Object target, int crystalId)
		{
			_activeChar = activeChar;
			_target = (L2Attackable) target;
			_crystalId = crystalId;
		}
		
		public void run()
		{
			if (_activeChar.isDead() || _target.isDead())
				return;
			
			_activeChar.enableAllSkills();
			
			try
			{
				_target.addAbsorber(_activeChar, _crystalId);
				_activeChar.setTarget(_target);
			}
			catch (Exception e)
			{
				_log.log(Level.SEVERE, "", e);
			}
		}
	}
}
