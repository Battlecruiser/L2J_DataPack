/**
 * 
 */
package handlers.itemhandlers;

import java.util.logging.Logger;

import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.instancemanager.HandysBlockCheckerManager;
import com.l2jserver.gameserver.instancemanager.HandysBlockCheckerManager.ArenaParticipantsHolder;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2BlockInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class EventItem implements IItemHandler
{
	private static final Logger _log = Logger.getLogger(EventItem.class.getName());
	
	/* (non-Javadoc)
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.item.instance.L2ItemInstance, boolean)
	 */
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if(!(playable instanceof L2PcInstance))
			return;
		
		final L2PcInstance activeChar = (L2PcInstance)playable;
		
		final int itemId = item.getItemId();
		switch(itemId)
		{
			case 13787: // Handy's Block Checker Bond
				useBlockCheckerItem(activeChar, item);
				break;
			case 13788: // Handy's Block Checker Land Mine
				useBlockCheckerItem(activeChar, item);
				break;
			default:
				_log.warning("EventItemHandler: Item with id: "+itemId+" is not handled");
		}
	}
	
	private final void useBlockCheckerItem(final L2PcInstance castor, L2ItemInstance item)
	{
		final int blockCheckerArena = castor.getBlockCheckerArena();
		if(blockCheckerArena == -1)
		{
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			msg.addItemName(item);
			castor.sendPacket(msg);
			return;
		}
		
		
		final L2Skill sk = item.getEtcItem().getSkills()[0].getSkill();
		if(sk == null)
			return;
		
		if(!castor.destroyItem("Consume", item, 1, castor, true))
			return;
		
		final L2BlockInstance block = (L2BlockInstance) castor.getTarget();
		
		final ArenaParticipantsHolder holder = HandysBlockCheckerManager.getInstance().getHolder(blockCheckerArena);
		if(holder != null)
		{
			final int team = holder.getPlayerTeam(castor);
			for(final L2PcInstance pc : block.getKnownList().getKnownPlayersInRadius(sk.getEffectRange()))
			{
				final int enemyTeam = holder.getPlayerTeam(pc);
				if(enemyTeam != -1 && enemyTeam != team)
					sk.getEffects(castor, pc);
			}
		}
		else
			_log.warning("Char: "+castor.getName()+"["+castor.getObjectId()+"] has unknown block checker arena");
	}
}
