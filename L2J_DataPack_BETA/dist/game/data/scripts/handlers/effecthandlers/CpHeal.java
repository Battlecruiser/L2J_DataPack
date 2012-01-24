/**
 * 
 */
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 *
 */
public class CpHeal extends L2Effect
{
	public CpHeal(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPHEAL;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		if (target == null || target.isDead() || target instanceof L2DoorInstance)
			return false;
		StatusUpdate su = new StatusUpdate(target);
		
		double amount = calc();
		
		amount = Math.min(amount, target.getMaxRecoverableCp() - target.getCurrentCp());
		
		// Prevent negative amounts
		if (amount < 0)
			amount = 0;
		
		// To prevent -value heals, set the value only if current Cp is less than max recoverable.
		if (target.getCurrentCp() < target.getMaxRecoverableCp())
			target.setCurrentCp(amount + target.getCurrentCp());
		
		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_CP_WILL_BE_RESTORED);
		sm.addNumber((int) amount);
		target.sendPacket(sm);
		su.addAttribute(StatusUpdate.CUR_CP, (int) target.getCurrentCp());
		target.sendPacket(su);
		
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
