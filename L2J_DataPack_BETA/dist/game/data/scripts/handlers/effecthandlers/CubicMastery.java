package handlers.effecthandlers;

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Cubic Mastery effect implementation.
 * @author Zoey76
 */
public final class CubicMastery extends AbstractEffect
{
	public CubicMastery(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean canStart(BuffInfo info)
	{
		return (info.getEffector() != null) && (info.getEffected() != null) && info.getEffected().isPlayer();
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CUBIC_MASTERY;
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		return info.getSkill().isPassive();
	}
}
