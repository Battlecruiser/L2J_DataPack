/**
 * 
 */
package handlers.itemhandlers;

import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.item.L2Item;
import com.l2jserver.gameserver.model.item.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author BiggBoss
 *
 */
public class QuestItems implements IItemHandler
{

	/* (non-Javadoc)
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceuse)
	{
		if(!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance player = (L2PcInstance) playable;
		
		if(!player.destroyItem("Item Handler - QuestItems", item, player, true))
			return;
		
		L2Item itm = item.getItem();
		for(Quest quest : itm.getQuestEvents())
		{
			QuestState state = player.getQuestState(quest.getName());
			if(state == null || !state.isStarted())
				continue;
			
			quest.notifyItemUse(itm, player);
		}
	}
}
