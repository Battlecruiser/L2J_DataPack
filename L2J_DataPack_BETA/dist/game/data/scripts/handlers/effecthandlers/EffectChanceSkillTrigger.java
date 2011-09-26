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

import com.l2jserver.gameserver.model.ChanceCondition;
import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.skills.Env;
import com.l2jserver.gameserver.templates.effects.EffectTemplate;
import com.l2jserver.gameserver.templates.skills.L2EffectType;

public class EffectChanceSkillTrigger extends L2Effect
{
	private final int _triggeredId;
	private final int _triggeredLevel;
	private final ChanceCondition _chanceCondition;
	
	public EffectChanceSkillTrigger(Env env, EffectTemplate template)
	{
		super(env, template);
		
		_triggeredId = template.triggeredId;
		_triggeredLevel = template.triggeredLevel;
		_chanceCondition = template.chanceCondition;
	}
	
	// Special constructor to steal this effect
	public EffectChanceSkillTrigger(Env env, L2Effect effect)
	{
		super(env, effect);
		
		_triggeredId = effect.getEffectTemplate().triggeredId;
		_triggeredLevel = effect.getEffectTemplate().triggeredLevel;
		_chanceCondition = effect.getEffectTemplate().chanceCondition;
	}
	
	@Override
	protected boolean effectCanBeStolen()
	{
		return true;
	}

	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CHANCE_SKILL_TRIGGER;
	}
	
	@Override
	public boolean onStart()
	{
		getEffected().addChanceTrigger(this);
		getEffected().onStartChanceEffect(getSkill().getElement());
		return super.onStart();
	}
	
	@Override
	public boolean onActionTime()
	{
		getEffected().onActionTimeChanceEffect(getSkill().getElement());
		return false;
	}
	
	@Override
	public void onExit()
	{
		// trigger only if effect in use and successfully ticked to the end
		if (getInUse() && getCount() == 0)
			getEffected().onExitChanceEffect(getSkill().getElement());
		getEffected().removeChanceEffect(this);
		super.onExit();
	}
	
	@Override
	public int getTriggeredChanceId()
	{
		return _triggeredId;
	}
	
	@Override
	public int getTriggeredChanceLevel()
	{
		return _triggeredLevel;
	}
	
	@Override
	public boolean triggersChanceSkill()
	{
		return _triggeredId > 1;
	}
	
	@Override
	public ChanceCondition getTriggeredChanceCondition()
	{
		return _chanceCondition;
	}
}