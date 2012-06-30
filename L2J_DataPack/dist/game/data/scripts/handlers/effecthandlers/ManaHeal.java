/**
 * 
 */
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 *
 */
public class ManaHeal extends L2Effect
{
	public ManaHeal(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.MANAHEAL;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		if (target == null || target.isDead() || target.isDoor())
			return false;
	
		StatusUpdate su = new StatusUpdate(target);
		
		double amount = calc();
		
		if (!getSkill().isStaticHeal())
			amount = target.calcStat(Stats.RECHARGE_MP_RATE, amount, null, null);
		
		amount = Math.min(amount, target.getMaxRecoverableMp() - target.getCurrentMp());
		
		// Prevent negative amounts
		if (amount < 0)
			amount = 0;
		
		// To prevent -value heals, set the value only if current mp is less than max recoverable.
		if (target.getCurrentMp() < target.getMaxRecoverableMp())
			target.setCurrentMp(amount + target.getCurrentMp());
		
		SystemMessage sm;
		if (getEffector().getObjectId() != target.getObjectId())
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_RESTORED_BY_C1);
			sm.addCharName(getEffector());
		}
		else
			sm = SystemMessage.getSystemMessage(SystemMessageId.S1_MP_RESTORED);
		sm.addNumber((int) amount);
		target.sendPacket(sm);
		su.addAttribute(StatusUpdate.CUR_MP, (int) target.getCurrentMp());
		target.sendPacket(su);
		
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
