/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00458_PerfectForm;

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExQuestNpcLogList;

/**
 * Perfect Form (458)
 * @author jurchiks
 */
public class Q00458_PerfectForm extends Quest
{
	// NPCs
	private static final int KELLEYIA = 32768;
	// Monsters
	// Level 4 (full grown) feedable beasts
	private static final int[] KOOKABURRAS =
	{
		18878,
		18879
	};
	private static final int[] COUGARS =
	{
		18885,
		18886
	};
	private static final int[] BUFFALOS =
	{
		18892,
		18893
	};
	private static final int[] GRENDELS =
	{
		18899,
		18900
	};
	
	// Rewards
	// 60% Icarus weapon recipes (except kamael weapons)
	// @formatter:off
	private static final int[] ICARUS_WEAPON_RECIPES =
	{
		10373, 10374, 10375, 10376, 10377, 10378, 10379, 10380, 10381
	};
	
	private static final int[] ICARUS_WEAPON_PIECES =
	{
		10397, 10398, 10399, 10400, 10401, 10402, 10403, 10404, 10405
	};
	// @formatter:on
	
	private Q00458_PerfectForm()
	{
		super(458, Q00458_PerfectForm.class.getSimpleName(), "Perfect Form");
		addStartNpc(KELLEYIA);
		addTalkId(KELLEYIA);
		addKillId(KOOKABURRAS);
		addKillId(COUGARS);
		addKillId(BUFFALOS);
		addKillId(GRENDELS);
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
			case State.COMPLETED:
				if (!st.isNowAvailable())
				{
					htmltext = "32768-18.htm";
					break;
				}
				st.setState(State.CREATED);
				//$FALL-THROUGH$
			case State.CREATED:
				htmltext = (player.getLevel() > 81) ? "32768-01.htm" : "32768-00.htm";
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						if ((st.getInt("18879") == 0) && (st.getInt("18886") == 0) && (st.getInt("18893") == 0) && (st.getInt("18900") == 0))
						{
							htmltext = "32768-11.html";
						}
						else
						{
							htmltext = "32768-12.html";
						}
						break;
					case 2:
						htmltext = "32768-13.html";
						break;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String noQuest = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return noQuest;
		}
		
		String htmltext = event;
		int overHits = 0;
		boolean overHitHtml = false;
		
		switch (event)
		{
			case "32768-10.htm":
				st.startQuest();
				break;
			case "results1":
				if (st.isCond(2))
				{
					final int overhitsTotal = st.getInt("overhitsTotal");
					if (overhitsTotal >= 35)
					{
						htmltext = "32768-14a.html";
					}
					else if (overhitsTotal >= 10)
					{
						htmltext = "32768-14b.html";
					}
					else
					{
						htmltext = "32768-14c.html";
					}
					overHits = overhitsTotal;
					overHitHtml = true;
				}
				else
				{
					htmltext = noQuest;
				}
				break;
			case "results2":
				if (st.isCond(2))
				{
					final int overhitsCritical = st.getInt("overhitsCritical");
					if (overhitsCritical >= 30)
					{
						htmltext = "32768-15a.html";
					}
					else if (overhitsCritical >= 5)
					{
						htmltext = "32768-15b.html";
					}
					else
					{
						htmltext = "32768-15c.html";
					}
					overHits = overhitsCritical;
					overHitHtml = true;
				}
				else
				{
					htmltext = noQuest;
				}
				break;
			case "results3":
				if (st.isCond(2))
				{
					final int overhitsConsecutive = st.getInt("overhitsConsecutive");
					if (overhitsConsecutive >= 20)
					{
						htmltext = "32768-16a.html";
					}
					else if (overhitsConsecutive >= 7)
					{
						htmltext = "32768-16b.html";
					}
					else
					{
						htmltext = "32768-16c.html";
					}
					overHits = overhitsConsecutive;
					overHitHtml = true;
				}
				else
				{
					htmltext = noQuest;
				}
				break;
			case "32768-17.html":
				if (st.isCond(2))
				{
					int overhitsConsecutive = st.getInt("overhitsConsecutive");
					if (overhitsConsecutive >= 20)
					{
						int rnd = getRandom(ICARUS_WEAPON_RECIPES.length);
						st.rewardItems(ICARUS_WEAPON_RECIPES[rnd], 1);
					}
					else if (overhitsConsecutive >= 7)
					{
						int rnd = getRandom(ICARUS_WEAPON_PIECES.length);
						st.rewardItems(ICARUS_WEAPON_PIECES[rnd], 5);
					}
					else
					{
						int rnd = getRandom(ICARUS_WEAPON_PIECES.length);
						st.rewardItems(ICARUS_WEAPON_PIECES[rnd], 2);
						// not sure if this should use rewardItems
						st.giveItems(15482, 10); // Golden Spice Crate
						st.giveItems(15483, 10); // Crystal Spice Crate
					}
					st.exitQuest(QuestType.DAILY, true);
				}
				else
				{
					htmltext = noQuest;
				}
				break;
		}
		
		if (overHitHtml)
		{
			htmltext = getHtm(player.getHtmlPrefix(), htmltext);
			htmltext = htmltext.replace("<?number?>", String.valueOf(overHits));
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1))
		{
			int npcId = npc.getNpcId();
			if ((npcId == KOOKABURRAS[0]) || (npcId == COUGARS[0]) || (npcId == BUFFALOS[0]) || (npcId == GRENDELS[0]))
			{
				npcId++;
			}
			
			String variable = String.valueOf(npcId); // i3
			int currentValue = st.getInt(variable);
			if (currentValue < 10)
			{
				st.set(variable, String.valueOf(currentValue + 1)); // IncreaseNPCLogByID
				
				L2Attackable mob = (L2Attackable) npc;
				if (mob.isOverhit())
				{
					st.set("overhitsTotal", String.valueOf(st.getInt("overhitsTotal") + 1)); // memoStateEx 1
					int maxHp = mob.getMaxHp();
					// L2Attackable#calculateOverhitExp() way of calculating overhit % seems illogical
					double overhitPercentage = (maxHp + mob.getOverhitDamage()) / maxHp;
					if (overhitPercentage >= 1.2)
					{
						st.set("overhitsCritical", String.valueOf(st.getInt("overhitsCritical") + 1)); // memoStateEx 2
					}
					int overhitsConsecutive = st.getInt("overhitsConsecutive") + 1;
					st.set("overhitsConsecutive", String.valueOf(overhitsConsecutive)); // memoStateEx 3
					/*
					 * Retail logic (makes for a long/messy string in database): int i0 = overhitsConsecutive % 100; int i1 = overhitsConsecutive - (i0 * 100); if (i0 < i1) { st.set("overhitsConsecutive", String.valueOf((i1 * 100) + i1)); }
					 */
				}
				else
				{
					// st.set("overhitsConsecutive", String.valueOf((st.getInt("overhitsConsecutive") % 100) * 100));
					if (st.getInt("overhitsConsecutive") > 0)
					{
						// avoid writing to database if variable is already zero
						st.set("overhitsConsecutive", "0");
					}
				}
				
				if ((st.getInt("18879") == 10) && (st.getInt("18886") == 10) && (st.getInt("18893") == 10) && (st.getInt("18900") == 10))
				{
					st.setCond(2, true);
					// st.set("overhitsConsecutive", String.valueOf(st.getInt("overhitsConsecutive") % 100));
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				
				final ExQuestNpcLogList log = new ExQuestNpcLogList(getQuestIntId());
				log.addNpc(18879, st.getInt("18879"));
				log.addNpc(18886, st.getInt("18886"));
				log.addNpc(18893, st.getInt("18893"));
				log.addNpc(18900, st.getInt("18900"));
				
				player.sendPacket(log);
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00458_PerfectForm();
	}
}
