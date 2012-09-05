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
import com.l2jserver.gameserver.datatables.ManorData;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
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
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!activeChar.isPlayer())
			return;
		
		final L2Object[] targetList = skill.getTargetList(activeChar);
		if (targetList == null || targetList.length == 0)
			return;
		
		if (Config.DEBUG)
			_log.info("Casting sow");
		
		L2MonsterInstance target;
		
		for (L2Object tgt: targetList)
		{
			if (!tgt.isMonster())
				continue;
			
			target = (L2MonsterInstance) tgt;
			if (target.isDead()
					|| target.isSeeded()
					|| target.getSeederId() != activeChar.getObjectId())
			{
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			final int seedId = target.getSeedType();
			if (seedId == 0)
			{
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				continue;
			}
			
			//Consuming used seed
			if (!activeChar.destroyItemByItemId("Consume", seedId, 1, target, false))
			{
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			
			SystemMessage sm;
			if (calcSuccess(activeChar, target, seedId))
			{
				activeChar.sendPacket(new PlaySound("Itemsound.quest_itemget"));
				target.setSeeded(activeChar.getActingPlayer());
				sm = SystemMessage.getSystemMessage(SystemMessageId.THE_SEED_WAS_SUCCESSFULLY_SOWN);
			}
			else
				sm = SystemMessage.getSystemMessage(SystemMessageId.THE_SEED_WAS_NOT_SOWN);
			
			if (activeChar.getParty() == null)
				activeChar.sendPacket(sm);
			else
				activeChar.getParty().broadcastPacket(sm);
			
			//TODO: Mob should not aggro on player, this way doesn't work really nice
			target.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
	}
	
	private boolean calcSuccess(L2Character activeChar, L2Character target, int seedId)
	{
		// TODO: check all the chances
		int basicSuccess = (ManorData.getInstance().isAlternative(seedId) ? 20 : 90);
		final int minlevelSeed = ManorData.getInstance().getSeedMinLevel(seedId);
		final int maxlevelSeed = ManorData.getInstance().getSeedMaxLevel(seedId);
		final int levelPlayer = activeChar.getLevel(); // Attacker Level
		final int levelTarget = target.getLevel(); // target Level
		
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
		
		return Rnd.nextInt(99) < basicSuccess;
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
