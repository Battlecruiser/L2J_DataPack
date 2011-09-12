package quests.Q504_CompetitionfortheBanditStronghold;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.instancemanager.CHSiegeManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.SiegableHall;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author BiggBoss
 */
public final class Q504_CompetitionfortheBanditStronghold extends Quest
{
	private static final String qn = "504_CompetitionfortheBanditStronghold";
	// Quest reward item
	private static final int TARLK_AMULET = 4332;
	private static final int TROPHY_OF_ALLIANCE = 5009;
	// Quest npc
	private static final int MESSENGER = 35437;
	
	private static final SiegableHall BANDIT_STRONGHOLD = CHSiegeManager.getInstance().getSiegableHall(35);
	
	/**
	 * @param questId
	 * @param name
	 * @param descr
	 */
	public Q504_CompetitionfortheBanditStronghold(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MESSENGER);
		addTalkId(MESSENGER);
		
		addKillId(20570); // TARLK BUGBEAR
		addKillId(20571); // TARLK BUGBEAR WARRIOR
		addKillId(20572); // TARLK BUGBEAR HIGH WARRIOR
		addKillId(20573); // TARLK BASILISK
		addKillId(20574); // ELDER TARLK BASILISK
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String result = null;
		QuestState st = player.getQuestState(qn);
		final L2Clan clan = player.getClan();
		
		if(st == null)
		{
			newQuestState(player);
			result = "azit_messenger_q0504_01.htm";
		}
		else if(!canRunQuest())
			sendDatePage("azit_messenger_q0504_03.htm", player);
		else if(clan != null && (clan.getHasHideout() > 0 || clan.getHasFort() > 0
				|| clan.getHasCastle() > 0))
			result = "azit_messenger_q0504_10.htm";
		else if(st.getState() == State.CREATED)
		{
			 if(!canRunQuest())
				 sendDatePage("azit_messenger_q0504_09.htm", player);
			 else if(player.getClan() == null || player.getClan().getLevel() < 4)
				 result = "azit_messenger_q0504_04.htm";
			 else if(!player.isClanLeader())
				 result = "azit_messenger_q0504_05.htm";
			 else if(BANDIT_STRONGHOLD.getSiege().getAttackers().size() >= 5)
				 result = "35437-3.htm";
			 else 
				 result = "azit_messenger_q0504_02.htm";
	
			 st.setState(State.STARTED);
		}
		else if(st.getState() == State.STARTED)
		{
			 if(!canRunQuest())
				 sendDatePage("azit_messenger_q0504_09.htm", player);
			 else if(st.getQuestItemsCount(TARLK_AMULET) < 30)
				result = "azit_messenger_q0504_07.htm";
			 else
			 {
				 st.takeItems(TARLK_AMULET, 30);
				 st.rewardItems(TROPHY_OF_ALLIANCE, 1);
				 st.exitQuest(true);
				 result = "azit_messenger_q0504_08.htm";
			 }
		}
		else if(st.getState() == State.COMPLETED)
		{
			if(!canRunQuest())
				sendDatePage("azit_messenger_q0504_09.htm", player);
			result = "azit_messenger_q0504_07a.htm";
		}
		return result;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if(!canRunQuest())
			return null;
		
		QuestState st = killer.getQuestState(qn);
		
		if(st == null)
			return null;
		
		if(st.isStarted())
		{
			st.giveItems(TARLK_AMULET, 1);
			if(st.getQuestItemsCount(TARLK_AMULET) < 30)
				st.playSound("Itemsound.quest_itemget");
			else
				st.playSound("Itemsound.quest_middle");
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	public final boolean canRunQuest()
	{
		// Siegable halls register status is just true 1 hour before siege
		return BANDIT_STRONGHOLD.isRegistering();
	}
	
	private final void sendDatePage(final String page, final L2PcInstance player)
	{
		String result = HtmCache.getInstance().getHtm(null, "data/scripts/quests/Q504_CompetitionfortheBanditStronghold/"+page+".htm");
		if(result != null)
		{
			NpcHtmlMessage msg = new NpcHtmlMessage(5);
			msg.setHtml(result);
			msg.replace("%nextSiege%", BANDIT_STRONGHOLD.getSiegeDate().getTime().toString());
		
			player.sendPacket(msg);
		}
	}
	
	public static void main(String[] args)
	{
		new Q504_CompetitionfortheBanditStronghold(504, qn, "Right to Participate");
	}
}
