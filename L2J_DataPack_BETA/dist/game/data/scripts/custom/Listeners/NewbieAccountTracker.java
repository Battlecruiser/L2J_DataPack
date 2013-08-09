package custom.Listeners;

import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.events.AbstractCharEvents;
import com.l2jserver.gameserver.model.actor.events.listeners.ILevelChangeEventListener;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.variables.AccountVariables;

/**
 * This tracker is attached to every player, until 1 character on account reaches level 6.
 * @author xban1x
 */
public final class NewbieAccountTracker implements ILevelChangeEventListener
{
	private NewbieAccountTracker()
	{
		AbstractCharEvents.registerStaticListener(this);
	}
	
	@Override
	public boolean onLevelChange(L2Playable playable, byte levels)
	{
		if (playable.isPlayer())
		{
			final L2PcInstance player = playable.getActingPlayer();
			if ((player != null) && (player.getLevel() < 6) && ((player.getLevel() + levels) >= 6))
			{
				final AccountVariables vars = player.getAccountVariables();
				if (!vars.hasVariable(L2PcInstance.NEWBIE_KEY))
				{
					vars.set(L2PcInstance.NEWBIE_KEY, player.getObjectId());
					player.getEvents().unregisterListener(this);
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		new NewbieAccountTracker();
	}
}