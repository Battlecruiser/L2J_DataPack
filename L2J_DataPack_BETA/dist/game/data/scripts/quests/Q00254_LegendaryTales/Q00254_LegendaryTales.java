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
package quests.Q00254_LegendaryTales;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Legendary Tales (254)
 * @author nonom
 */
public class Q00254_LegendaryTales extends Quest
{
	// NPC
	private static final int GILMORE = 30754;
	
	// Monsters
	private static final int EMERALD_HORN = 25718;
	private static final int DUST_RIDER = 25719;
	private static final int BLEEDING_FLY = 25720;
	private static final int BLACK_DAGGER = 25721;
	private static final int SHADOW_SUMMONER = 25722;
	private static final int SPIKE_SLASHER = 25723;
	private static final int MUSCLE_BOMBER = 25724;
	// @formatter:off
	private static final int[] MONSTERS =
	{
		EMERALD_HORN, DUST_RIDER, BLEEDING_FLY, BLACK_DAGGER, SHADOW_SUMMONER,
		SPIKE_SLASHER, MUSCLE_BOMBER
	};
	// @formatter:on
	
	// Items
	private static final int LARGE_DRAGON_SKULL = 17249;
	
	// Misc
	private static final int MIN_LEVEL = 80;
	
	public Q00254_LegendaryTales()
	{
		super(254, Q00254_LegendaryTales.class.getSimpleName(), "Legendary Tales");
		addStartNpc(GILMORE);
		addTalkId(GILMORE);
		addKillId(MONSTERS);
		registerQuestItems(LARGE_DRAGON_SKULL);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() < MIN_LEVEL) ? "30754-00.htm" : "30754-01.htm";
				break;
			case State.STARTED:
				int cond = st.getCond();
				long count = getQuestItemsCount(player, LARGE_DRAGON_SKULL);
				if (cond == 1)
				{
					htmltext = ((count > 0) ? "30754-14.htm" : "30754-06.html");
				}
				else if (cond == 2)
				{
					htmltext = ((count < 7) ? "30754-12.htm" : "30754-07.html");
				}
				break;
			case State.COMPLETED:
				htmltext = "30754-29.html";
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "30754-05.html":
				st.startQuest();
			case "30754-02.html":
			case "30754-03.html":
			case "30754-04.htm":
			case "30754-08.html":
			case "30754-15.html":
			case "30754-20.html":
			case "30754-21.html":
				htmltext = event;
				break;
			case "25718": // Emerald Horn
				htmltext = (checkPosition(st, EMERALD_HORN) ? "30754-22.html" : "30754-16.html");
				break;
			case "25719": // Dust Rider
				htmltext = (checkPosition(st, DUST_RIDER) ? "30754-23.html" : "30754-17.html");
				break;
			case "25720": // Bleeding Fly
				htmltext = (checkPosition(st, BLEEDING_FLY) ? "30754-24.html" : "30754-18.html");
				break;
			case "25721": // Black Dagger Wing
				htmltext = (checkPosition(st, BLACK_DAGGER) ? "30754-25.html" : "30754-19.html");
				break;
			case "25722": // Shadow Summoner
				htmltext = (checkPosition(st, SHADOW_SUMMONER) ? "30754-26.html" : "30754-16.html");
				break;
			case "25723": // Spike Slasher
				htmltext = (checkPosition(st, SPIKE_SLASHER) ? "30754-27.html" : "30754-17.html");
				break;
			case "25724": // Muscle Bomber
				htmltext = (checkPosition(st, MUSCLE_BOMBER) ? "30754-28.html" : "30754-18.html");
				break;
			case "13467": // Vesper Thrower
			case "13466": // Vesper Singer
			case "13465": // Vesper Caster
			case "13464": // Vesper Retributer
			case "13463": // Vesper Avenger
			case "13457": // Vesper Cutter
			case "13458": // Vesper Slasher
			case "13459": // Vesper Buster
			case "13460": // Vesper Sharper
			case "13461": // Vesper Fighter
			case "13462": // Vesper Stormer
				if (st.isCond(2) && (getQuestItemsCount(player, LARGE_DRAGON_SKULL) >= 7))
				{
					htmltext = "30754-09.html";
					rewardItems(player, Integer.parseInt(event), 1);
					st.exitQuest(false, true);
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (player.isInParty())
		{
			for (L2PcInstance partyMember : player.getParty().getMembers())
			{
				actionForEachPlayer(partyMember, npc, false);
			}
		}
		else
		{
			actionForEachPlayer(player, npc, false);
		}
		return super.onKill(npc, player, isPet);
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isSummon)
	{
		final QuestState st = player.getQuestState(Q00254_LegendaryTales.class.getSimpleName());
		
		if ((st != null) && st.isCond(1))
		{
			boolean giveItem = false;
			int raids = st.getInt("raids");
			int pos = getPosition(npc.getNpcId());
			
			if ((raids & pos) == 0)
			{
				st.set("raids", raids | pos);
				giveItem = true;
			}
			
			if (giveItem)
			{
				st.giveItems(LARGE_DRAGON_SKULL, 1);
				
				if (st.getQuestItemsCount(LARGE_DRAGON_SKULL) < 7)
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				else
				{
					st.setCond(2, true);
				}
			}
		}
	}
	
	private static boolean checkPosition(QuestState qs, int npcId)
	{
		int pos = getPosition(npcId);
		return ((qs.getInt("raids") & pos) == pos);
	}
	
	private static int getPosition(int npcId)
	{
		int pos = 1;
		for (int mobId : MONSTERS)
		{
			if (mobId == npcId)
			{
				break;
			}
			pos = pos << 1;
		}
		return pos;
	}
	
	public static void main(String[] args)
	{
		new Q00254_LegendaryTales();
	}
}
