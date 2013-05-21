package handlers.effecthandlers;

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Cubic Mastery effect implementation.
 * @author Zoey76
 */
public class CubicMastery extends L2Effect
{
	public CubicMastery(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CUBIC_MASTERY;
	}
	
	@Override
	public boolean onActionTime()
	{
		return getSkill().isPassive();
	}
	
	@Override
	public boolean onStart()
	{
		return (getEffector() != null) && (getEffected() != null) && getEffected().isPlayer();
	}
}
