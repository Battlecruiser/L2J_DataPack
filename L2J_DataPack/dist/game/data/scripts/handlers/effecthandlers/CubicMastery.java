package handlers.effecthandlers;

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Cubic Mastery effect implementation.
 * @author Zoey76
 */
public final class CubicMastery extends AbstractEffect
{
	private final int _cubicCount;
	
	public CubicMastery(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_cubicCount = params.getInt("cubicCount", 1);
	}
	
	@Override
	public boolean canStart(BuffInfo info)
	{
		return (info.getEffector() != null) && (info.getEffected() != null) && info.getEffected().isPlayer();
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		info.getEffected().getActingPlayer().getStat().setMaxCubicCount(_cubicCount);
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		return info.getSkill().isPassive();
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		info.getEffected().getActingPlayer().getStat().setMaxCubicCount(1);
	}
}
