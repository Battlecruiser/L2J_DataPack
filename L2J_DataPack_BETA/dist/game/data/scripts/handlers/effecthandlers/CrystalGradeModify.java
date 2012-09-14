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

import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Crystal Grade Modify effect implementation.
 * @author Zoey76
 */
public class CrystalGradeModify extends L2Effect
{
	public CrystalGradeModify(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
	
	@Override
	public boolean onStart()
	{
		final L2PcInstance player = getEffected().getActingPlayer();
		if (player != null)
		{
			player.setExpertisePenaltyBonus((int) calc());
			return true;
		}
		return false;
	}
	
	@Override
	public void onExit()
	{
		final L2PcInstance player = getEffected().getActingPlayer();
		if (player != null)
		{
			player.setExpertisePenaltyBonus(0);
		}
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}