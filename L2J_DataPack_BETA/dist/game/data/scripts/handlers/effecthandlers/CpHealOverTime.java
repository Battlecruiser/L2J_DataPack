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
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;

public class CpHealOverTime extends L2Effect
{
	public CpHealOverTime(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	// Special constructor to steal this effect
	public CpHealOverTime(Env env, L2Effect effect)
	{
		super(env, effect);
	}

	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#effectCanBeStolen()
	 */
	@Override
	protected boolean effectCanBeStolen()
	{
		return true;
	}

	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#getEffectType()
	 */
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPHEAL_OVER_TIME;
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#onActionTime()
	 */
	@Override
	public boolean onActionTime()
	{
		if (getEffected().isDead())
			return false;
		
		double cp = getEffected().getCurrentCp();
		double maxcp = getEffected().getMaxRecoverableCp();
		
		// Not needed to set the CP and send update packet if player is already at max CP
		if (cp >= maxcp)
			return true;
		
		cp += calc();
		if (cp > maxcp)
			cp = maxcp;
		
		getEffected().setCurrentCp(cp);
		StatusUpdate sump = new StatusUpdate(getEffected());
		sump.addAttribute(StatusUpdate.CUR_CP, (int) cp);
		getEffected().sendPacket(sump);
		return true;
	}
}
