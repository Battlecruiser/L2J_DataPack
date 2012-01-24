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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.AbnormalEffect;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.DeleteObject;
import com.l2jserver.gameserver.network.serverpackets.L2GameServerPacket;

/**
 *
 * @author ZaKaX - nBd
 */
public class Hide extends L2Effect
{
	public Hide(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	public Hide(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#getEffectType()
	 */
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HIDE;
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#onStart()
	 */
	@Override
	public boolean onStart()
	{
		if (getEffected() instanceof L2PcInstance)
		{
			L2PcInstance activeChar = ((L2PcInstance) getEffected());
			activeChar.getAppearance().setInvisible();
			activeChar.startAbnormalEffect(AbnormalEffect.STEALTH);
			
			if (activeChar.getAI().getNextIntention() != null
					&& activeChar.getAI().getNextIntention().getCtrlIntention() == CtrlIntention.AI_INTENTION_ATTACK)
				activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			
			L2GameServerPacket del = new DeleteObject(activeChar);
			for (L2Character target : activeChar.getKnownList().getKnownCharacters())
			{
				try
				{
					if (target.getTarget() == activeChar)
					{
						target.setTarget(null);
						target.abortAttack();
						target.abortCast();
						target.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
					}
					
					if (target instanceof L2PcInstance)
						target.sendPacket(del);
				}
				catch (NullPointerException e)
				{
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#onExit()
	 */
	@Override
	public void onExit()
	{
		if (getEffected() instanceof L2PcInstance)
		{
			L2PcInstance activeChar = ((L2PcInstance) getEffected());
			if (!activeChar.inObserverMode())
				activeChar.getAppearance().setVisible();
			activeChar.stopAbnormalEffect(AbnormalEffect.STEALTH);
		}
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.model.effects.L2Effect#onActionTime()
	 */
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}