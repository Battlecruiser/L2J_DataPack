package handlers.effecthandlers;

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Change Fishing Mastery dummy effect implementation.
 * @author Zoey76
 */
public class ChangeFishingMastery extends L2Effect
{
	public ChangeFishingMastery(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	public ChangeFishingMastery(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	@Override
	public boolean canBeStolen()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
}
