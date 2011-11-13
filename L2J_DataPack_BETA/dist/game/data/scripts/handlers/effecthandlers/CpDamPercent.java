package handlers.effecthandlers;

import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.skills.Env;
import com.l2jserver.gameserver.templates.effects.EffectTemplate;
import com.l2jserver.gameserver.templates.skills.L2EffectType;

/**
 * @author Zoey76
 */
public class CpDamPercent extends L2Effect
{
	public CpDamPercent(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPDAMPERCENT;
	}
	
	@Override
	public boolean onActionTime()
	{
		if (getEffected().isDead())
			return false;
		
		double cp = getEffected().getCurrentCp() * (100 - getEffectPower()) / 100;
		getEffected().setCurrentCp(cp);
		
		StatusUpdate sucp = new StatusUpdate(getEffected());
		sucp.addAttribute(StatusUpdate.CUR_CP, (int) cp);
		getEffected().sendPacket(sucp);
		return false;
	}
}