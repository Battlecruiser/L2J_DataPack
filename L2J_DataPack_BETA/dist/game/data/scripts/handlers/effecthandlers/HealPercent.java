/**
 * 
 */
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.Env;
import com.l2jserver.gameserver.templates.effects.EffectTemplate;
import com.l2jserver.gameserver.templates.skills.L2EffectType;

/**
 * @author UnAfraid
 *
 */
public class HealPercent extends L2Effect
{
	public HealPercent(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HEAL_PERCENT;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		if (target == null || target.isDead() || target instanceof L2DoorInstance)
			return false;
		
		StatusUpdate su = new StatusUpdate(target);
		double amount = 0;
		double power = calc();
		boolean full = (power == 100.0);
		
		if (full)
			amount = target.getMaxHp();
		else
			amount = target.getMaxHp() * power / 100.0;
		
		amount = Math.min(amount, target.getMaxRecoverableHp() - target.getCurrentHp());
		
		// Prevent negative amounts
		if (amount < 0)
			amount = 0;
		
		// To prevent -value heals, set the value only if current hp is less than max recoverable.
		if (target.getCurrentHp() < target.getMaxRecoverableHp())
			target.setCurrentHp(amount + target.getCurrentHp());
		
		SystemMessage sm;
		if (getEffector().getObjectId() != target.getObjectId())
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.S2_HP_RESTORED_BY_C1);
			sm.addCharName(getEffector());
		}
		else
			sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
		
		sm.addNumber((int)amount);
		target.sendPacket(sm);
		su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
		target.sendPacket(su);
		
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
