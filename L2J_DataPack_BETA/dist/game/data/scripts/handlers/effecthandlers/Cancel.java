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

import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.util.Rnd;
import com.l2jserver.util.StringUtil;

/**
 * @author DS
 */
public class Cancel extends L2Effect
{
	protected static final Logger _log = Logger.getLogger(Cancel.class.getName());
	
	public Cancel(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CANCEL;
	}
	
	@Override
	public boolean onStart()
	{
		return cancel(getEffector(), getEffected(), this);
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	private static boolean cancel(L2Character caster, L2Character target, L2Effect effect)
	{
		if (target.isDead())
		{
			return false;
		}
		
		final int cancelLvl = effect.getSkill().getMagicLevel();
		int count = effect.getSkill().getMaxNegatedEffects();
		
		double rate = effect.getEffectPower();
		final double vulnModifier = target.calcStat(Stats.CANCEL_VULN, 0, target, null);
		final double profModifier = caster.calcStat(Stats.CANCEL_PROF, 0, target, null);
		double res = vulnModifier + profModifier;
		double resMod = 1;
		if (res != 0)
		{
			if (res < 0)
			{
				resMod = 1 - (0.075 * res);
				resMod = 1 / resMod;
			}
			else
			{
				resMod = 1 + (0.02 * res);
			}
			
			rate *= resMod;
		}
		
		if (caster.isDebug())
		{
			final StringBuilder stat = new StringBuilder(100);
			StringUtil.append(stat, effect.getSkill().getName(), " power:", String.valueOf((int) effect.getEffectPower()), " lvl:", String.valueOf(cancelLvl), " res:", String.format("%1.2f", resMod), "(", String.format("%1.2f", profModifier), "/", String.format("%1.2f", vulnModifier), ") total:", String.valueOf(rate));
			final String result = stat.toString();
			if (caster.isDebug())
			{
				caster.sendDebugMessage(result);
			}
			if (Config.DEVELOPER)
			{
				_log.info(result);
			}
		}
		
		final L2Effect[] effects = target.getAllEffects();
		
		if (effect.getSkill().getNegateAbnormals() != null) // Cancel for abnormals
		{
			for (L2Effect eff : effects)
			{
				if (eff == null)
				{
					continue;
				}
				
				for (String negateAbnormalType : effect.getSkill().getNegateAbnormals().keySet())
				{
					if (negateAbnormalType.equalsIgnoreCase(eff.getAbnormalType()) && (effect.getSkill().getNegateAbnormals().get(negateAbnormalType) >= eff.getAbnormalLvl()))
					{
						if (calcCancelSuccess(eff, cancelLvl, (int) rate))
						{
							eff.exit();
						}
					}
				}
			}
		}
		else
		{
			L2Effect eff;
			int lastCanceledSkillId = 0;
			
			for (int i = effects.length; --i >= 0;)
			{
				eff = effects[i];
				if (eff == null)
				{
					continue;
				}
				
				if (!eff.canBeStolen())
				{
					effects[i] = null;
					continue;
				}
				
				// first pass - dances/songs only
				if (!eff.getSkill().isDance())
				{
					continue;
				}
				
				if (eff.getSkill().getId() == lastCanceledSkillId)
				{
					eff.exit(); // this skill already canceled
					continue;
				}
				
				if (!calcCancelSuccess(eff, cancelLvl, (int) rate))
				{
					continue;
				}
				
				lastCanceledSkillId = eff.getSkill().getId();
				
				eff.exit();
				count--;
				
				if (count == 0)
				{
					break;
				}
			}
			
			if (count != 0)
			{
				lastCanceledSkillId = 0;
				for (int i = effects.length; --i >= 0;)
				{
					eff = effects[i];
					if (eff == null)
					{
						continue;
					}
					
					// second pass - all except dances/songs
					if (eff.getSkill().isDance())
					{
						continue;
					}
					
					if (eff.getSkill().getId() == lastCanceledSkillId)
					{
						eff.exit(); // this skill already canceled
						continue;
					}
					
					if (!calcCancelSuccess(eff, cancelLvl, (int) rate))
					{
						continue;
					}
					
					lastCanceledSkillId = eff.getSkill().getId();
					eff.exit();
					count--;
					
					if (count == 0)
					{
						break;
					}
				}
			}
		}
		
		return true;
	}
	
	private static boolean calcCancelSuccess(L2Effect effect, int cancelLvl, int baseRate)
	{
		int rate = 2 * (cancelLvl - effect.getSkill().getMagicLevel());
		rate += effect.getAbnormalTime() / 120;
		rate += baseRate;
		
		if (rate < 25)
		{
			rate = 25;
		}
		else if (rate > 75)
		{
			rate = 75;
		}
		
		return Rnd.get(100) < rate;
	}
}