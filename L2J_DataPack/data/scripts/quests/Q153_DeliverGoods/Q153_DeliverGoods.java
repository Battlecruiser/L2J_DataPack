package quests.Q153_DeliverGoods;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Based on Naia (EURO)
 * @author Zoey76
 * @version 1.0 Freya (11/16/2010)
 */
public class Q153_DeliverGoods extends Quest
{
	private static final String qn = "153_DeliverGoods";
	
	// NPCs
	private static final int JacksonId = 30002;
	private static final int SilviaId = 30003;
	private static final int ArnoldId = 30041;
	private static final int RantId = 30054;
	//ITEMs
	private static final int DeliveryListId = 1012;
	private static final int HeavyWoodBoxId = 1013;
	private static final int ClothBundleId = 1014;
	private static final int ClayPotId = 1015;
	private static final int JacksonsReceipt = 1016;
	private static final int SilviasReceipt = 1017;
	private static final int RantsReceipt = 1018;
	
	//REWARDs
	private static final int SoulshotNoGradeId = 1835; //You get 3 Soulshots no grade.
	private static final int RingofKnowledgeId = 875;
	private static final int XpRewardAmount = 600;
	
	public Q153_DeliverGoods(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ArnoldId);
		addTalkId(JacksonId);
		addTalkId(SilviaId);
		addTalkId(ArnoldId);
		addTalkId(RantId);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if ((st != null) && (npc.getNpcId() == ArnoldId))
		{
			if (event.equalsIgnoreCase("30041-02.html"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				st.giveItems(DeliveryListId, 1);
				st.giveItems(HeavyWoodBoxId, 1);
				st.giveItems(ClothBundleId, 1);
				st.giveItems(ClayPotId, 1);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st != null)
		{
			if (npc.getNpcId() == ArnoldId)
			{
				switch (st.getState())
				{
					case State.CREATED:
						if (player.getLevel() >= 2)
						{
							htmltext = "30041-01.htm";
						}
						else
						{
							htmltext = "30041-00.htm";
						}
						break;
					case State.STARTED:
						if (st.getInt("cond") == 1)
						{
							htmltext = "30041-03.html";
						}
						else if (st.getInt("cond") == 2)
						{
							htmltext = "30041-04.html";
							st.takeItems(DeliveryListId, 1);
							st.takeItems(JacksonsReceipt, 1);
							st.takeItems(SilviasReceipt, 1);
							st.takeItems(RantsReceipt, 1);
							//On retail it gives 2 rings but one at the time.
							st.giveItems(RingofKnowledgeId, 1);
							st.giveItems(RingofKnowledgeId, 1);
							st.addExpAndSp(XpRewardAmount, 0);
							st.exitQuest(false);
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
			}
			else
			{
				if (npc.getNpcId() == JacksonId)
				{
					if (st.getQuestItemsCount(HeavyWoodBoxId) == 1)
					{
						htmltext = "30002-01.html";
						st.takeItems(HeavyWoodBoxId, 1);
						st.giveItems(JacksonsReceipt, 1);
					}
					else
					{
						htmltext = "30002-02.html";
					}
				}
				else if (npc.getNpcId() == SilviaId)
				{
					if (st.getQuestItemsCount(ClothBundleId) == 1)
					{
						htmltext = "30003-01.html";
						st.takeItems(ClothBundleId, 1);
						st.giveItems(SilviasReceipt, 1);
						st.giveItems(SoulshotNoGradeId, 3);
					}
					else
					{
						htmltext = "30003-02.html";
					}
				}
				else if (npc.getNpcId() == RantId)
				{
					if (st.getQuestItemsCount(ClayPotId) == 1)
					{
						htmltext = "30054-01.html";
						st.takeItems(ClayPotId, 1);
						st.giveItems(RantsReceipt, 1);
					}
					else
					{
						htmltext = "30054-02.html";
					}
				}
				
				if ((st.getInt("cond") == 1) && (st.getQuestItemsCount(JacksonsReceipt) == 1) && (st.getQuestItemsCount(SilviasReceipt) == 1) && (st.getQuestItemsCount(RantsReceipt) == 1))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q153_DeliverGoods(153, qn, "Deliver Goods");
	}
}
