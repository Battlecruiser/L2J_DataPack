package events.HeavyMedal;

import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.util.Rnd;

/**
 ** @author Gnacik
 **
 ** Retail Event : 'Heavy Medals'
 */
public class HeavyMedal extends Quest
{
	private final static int	CAT_ROY = 31228;
	private final static int	CAT_WINNIE = 31229;
	private final static int	GLITTERING_MEDAL = 6393;
	
	private final static int	WIN_CHANCE = 50;
	
	private final static int[] MEDALS = { 5,10,20,40 };
	private final static int[] BADGES = { 6399,6400,6401,6402 };

	public HeavyMedal(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(CAT_ROY);
		addStartNpc(CAT_WINNIE);
		addTalkId(CAT_ROY);
		addTalkId(CAT_WINNIE);
		addFirstTalkId(CAT_ROY);
		addFirstTalkId(CAT_WINNIE);
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		htmltext = event;
		
		int level = checkLevel(st);
		
		if (event.equalsIgnoreCase("game"))
		{
			if (st.getQuestItemsCount(GLITTERING_MEDAL) < MEDALS[level])
				return "31229-no.htm";
			else
				return "31229-game.htm";
		}
		else if (event.equalsIgnoreCase("heads") || event.equalsIgnoreCase("tails"))
		{
			if (st.getQuestItemsCount(GLITTERING_MEDAL) < MEDALS[level])
				return "31229-"+event.toLowerCase()+"-10.htm";
			
			st.takeItems(GLITTERING_MEDAL, MEDALS[level]);
			
			if(Rnd.get(100) > WIN_CHANCE)
			{
				level = 0;
			}
			else
			{
				if (level>0)
					st.takeItems(BADGES[level-1], -1);
				st.giveItems(BADGES[level], 1);
				st.playSound("Itemsound.quest_itemget");
				level++;
			}
			return "31229-"+event.toLowerCase()+"-"+String.valueOf(level)+".htm";
		}
		else if (event.equalsIgnoreCase("talk"))
		{
			return String.valueOf(npc.getNpcId())+ "-lvl-"+String.valueOf(level)+".htm";			
		}
		return htmltext;
	}

	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			Quest q = QuestManager.getInstance().getQuest(getName());
			st = q.newQuestState(player);
		}
		return npc.getNpcId()+".htm";
	}

	public int checkLevel(QuestState st)
	{
		int _lev = 0;
		if(st == null)
			return 0;
		else if (st.getQuestItemsCount(6402) > 0)
			_lev = 4;
		else if (st.getQuestItemsCount(6401) > 0)
			_lev = 3;
		else if (st.getQuestItemsCount(6400) > 0)
			_lev = 2;
		else if (st.getQuestItemsCount(6399) > 0)
			_lev = 1;
		
		return _lev;
	}
	
	public static void main(String[] args)
	{
		new HeavyMedal(-1, "HeavyMedal", "events");
	}
}