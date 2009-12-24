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

import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Manor;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.util.Rnd;


/**
 * @author  l3x
 */
public class Sow implements ISkillHandler
{
	private static Logger _log = Logger.getLogger(Sow.class.getName());
	
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.SOW
	};
	
	private L2PcInstance _activeChar;
	private L2MonsterInstance _target;
	private int _seedId;
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#useSkill(com.l2jserver.gameserver.model.actor.L2Character, com.l2jserver.gameserver.model.L2Skill, com.l2jserver.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;
		
		_activeChar = (L2PcInstance) activeChar;
		
		L2Object[] targetList = skill.getTargetList(activeChar);
		
		if (targetList == null || targetList.length == 0)
		{
			return;
		}
		
		if (Config.DEBUG)
		{
			_log.info("Casting sow");
		}
		
		for (int index = 0; index < targetList.length; index++)
		{
			if (!(targetList[0] instanceof L2MonsterInstance))
				continue;
			
			_target = (L2MonsterInstance) targetList[0];
			
			if (_target.isSeeded())
			{
				_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			if (_target.isDead())
			{
				_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			if (_target.getSeeder() != _activeChar)
			{
				_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			_seedId = _target.getSeedType();
			if (_seedId == 0)
			{
				_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			L2ItemInstance item = _activeChar.getInventory().getItemByItemId(_seedId);
			if (item == null)
			{
				_activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}

			//Consuming used seed
			_activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
			
			SystemMessage sm = null;
			if (calcSuccess())
			{
				_activeChar.sendPacket(new PlaySound("Itemsound.quest_itemget"));
				_target.setSeeded();
				sm = new SystemMessage(SystemMessageId.THE_SEED_WAS_SUCCESSFULLY_SOWN);
			}
			else
			{
				sm = new SystemMessage(SystemMessageId.THE_SEED_WAS_NOT_SOWN);
			}
			if (_activeChar.getParty() == null)
			{
				_activeChar.sendPacket(sm);
			}
			else
			{
				_activeChar.getParty().broadcastToPartyMembers(sm);
			}
			//TODO: Mob should not agro on player, this way doesn't work really nice
			_target.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
		
	}
	
	private boolean calcSuccess()
	{
		// TODO: check all the chances
		int basicSuccess = (L2Manor.getInstance().isAlternative(_seedId) ? 20 : 90);
		int minlevelSeed = 0;
		int maxlevelSeed = 0;
		minlevelSeed = L2Manor.getInstance().getSeedMinLevel(_seedId);
		maxlevelSeed = L2Manor.getInstance().getSeedMaxLevel(_seedId);
		
		int levelPlayer = _activeChar.getLevel(); // Attacker Level
		int levelTarget = _target.getLevel(); // target Level
		
		// seed level
		if (levelTarget < minlevelSeed)
			basicSuccess -= 5 * (minlevelSeed - levelTarget);
		if (levelTarget > maxlevelSeed)
			basicSuccess -= 5 * (levelTarget - maxlevelSeed);
		
		// 5% decrease in chance if player level
		// is more than +/- 5 levels to _target's_ level
		int diff = (levelPlayer - levelTarget);
		if (diff < 0)
			diff = -diff;
		if (diff > 5)
			basicSuccess -= 5 * (diff - 5);
		
		//chance can't be less than 1%
		if (basicSuccess < 1)
			basicSuccess = 1;
		
		int rate = Rnd.nextInt(99);
		
		return (rate < basicSuccess);
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
