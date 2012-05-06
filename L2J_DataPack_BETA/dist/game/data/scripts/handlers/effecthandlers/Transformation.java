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

import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * @author nBd
 */
public class Transformation extends L2Effect
{
	public Transformation(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	// Special constructor to steal this effect
	public Transformation(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.TRANSFORMATION;
	}
	
	@Override
	public boolean onStart()
	{
		if (!(getEffected() instanceof L2PcInstance))
		{
			return false;
		}
		
		L2PcInstance trg = (L2PcInstance) getEffected();
		if (trg == null)
		{
			return false;
		}
		
		if (trg.isAlikeDead() || trg.isCursedWeaponEquipped())
		{
			return false;
		}
		
		if (trg.isTransformed() || trg.isInStance())
		{
			trg.sendPacket(SystemMessageId.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN);
			return false;
		}
		
		if (trg.isInWater())
		{
			trg.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_INTO_THE_DESIRED_FORM_IN_WATER);
			return false;
		}
		
		if (trg.isMounted() || trg.isFlyingMounted())
		{
			trg.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_WHILE_RIDING_A_PET);
			return false;
		}
		
		TransformationManager.getInstance().transformPlayer(getSkill().getTransformId(), trg);
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	@Override
	public void onExit()
	{
		getEffected().stopTransformation(false);
	}
}
