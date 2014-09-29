package handlers.effecthandlers;

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;

/**
 * Change Fishing Mastery dummy effect implementation.
 * @author Zoey76
 */
public final class ChangeFishingMastery extends AbstractEffect
{
	public ChangeFishingMastery(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
}
