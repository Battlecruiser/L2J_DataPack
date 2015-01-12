/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q00177_SplitDestiny;

import com.l2jserver.gameserver.datatables.CategoryData;
import com.l2jserver.gameserver.enums.CategoryType;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExSubjobInfo;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @author Sdw
 */
public class Q00177_SplitDestiny extends Quest
{
	// NPCs
	private static final int HADEL = 33344;
	private static final int ISHUMA = 32615;
	// Items
	private static final ItemHolder PETRIFIED_GIANTS_HAND = new ItemHolder(17718, 2);
	private static final ItemHolder PETRIFIED_GIANTS_FOOT = new ItemHolder(17719, 2);
	private static final int PETRIFIED_GIANTS_HAND_PIECE = 17720;
	private static final int PETRIFIED_GIANTS_FOOT_PIECE = 17721;
	// Rewards
	private static final ItemHolder RECIPE_TWILIGHT_NECKLACE = new ItemHolder(36791, 1);
	private static final ItemHolder CRYSTAL_R = new ItemHolder(17371, 1);
	private static final ItemHolder RED_SOUL_CRYSTAL_15 = new ItemHolder(10480, 1);
	private static final ItemHolder BLUE_SOUL_CRYSTAL_15 = new ItemHolder(10481, 1);
	private static final ItemHolder GREEN_SOUL_CRYSTAL_15 = new ItemHolder(10482, 1);
	// Variable
	private static final String VAR_SUB_INDEX = "split_destiny_sub_id";
	
	// Mobs
	private static final int[] GIANTS_HAND_MONSTERS =
	{
		21549, // Corrupted Guard
		21550, // Corrupted Guard
		21547, // Corrupted Knight
		21548, // Resurrected Knight
		21587, // Vampire Warrior
	};
	
	private static final int[] GIANTS_FOOT_MONSTERS =
	{
		22257, // Island Guardian
		22258, // White Sand Mirage
		22259, // Muddy Coral
		22260, // Kleopora
	};
	
	public Q00177_SplitDestiny()
	{
		super(177, Q00177_SplitDestiny.class.getSimpleName(), "Split Destiny");
		addStartNpc(HADEL);
		addTalkId(HADEL, ISHUMA);
		addKillId(GIANTS_HAND_MONSTERS);
		addKillId(GIANTS_FOOT_MONSTERS);
		registerQuestItems(PETRIFIED_GIANTS_HAND.getId(), PETRIFIED_GIANTS_FOOT.getId(), PETRIFIED_GIANTS_HAND_PIECE, PETRIFIED_GIANTS_FOOT_PIECE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		
		switch (event)
		{
			case "33344-14.htm":
			{
				qs.startQuest();
				qs.set(VAR_SUB_INDEX, player.getClassIndex());
				htmltext = event;
				break;
			}
			case "33344-19.htm":
			{
				if (qs.isCond(3))
				{
					qs.setCond(4, true);
					htmltext = event;
				}
				break;
			}
			case "33344-22.htm":
			{
				if (qs.isCond(6))
				{
					qs.setCond(7, true);
					htmltext = event;
				}
				break;
			}
			case "32615-03.htm":
			{
				if (qs.isCond(7) && (getQuestItemsCount(player, PETRIFIED_GIANTS_HAND_PIECE) >= 10) && (getQuestItemsCount(player, PETRIFIED_GIANTS_FOOT_PIECE) >= 10))
				{
					takeItems(player, PETRIFIED_GIANTS_HAND_PIECE, 10);
					takeItems(player, PETRIFIED_GIANTS_FOOT_PIECE, 10);
					qs.setCond(8, true);
					htmltext = event;
				}
				break;
			}
			case "33344-25.htm":
			{
				if (qs.isCond(9) && hasItem(player, PETRIFIED_GIANTS_HAND) && hasItem(player, PETRIFIED_GIANTS_FOOT))
				{
					takeItem(player, PETRIFIED_GIANTS_HAND);
					takeItem(player, PETRIFIED_GIANTS_FOOT);
					htmltext = event;
				}
				break;
			}
			case "blue_crystal":
			case "green_crystal":
			case "red_crystal":
			{
				if (qs.isCond(9))
				{
					if (player.getClassIndex() != qs.getInt(VAR_SUB_INDEX))
					{
						return "33344-16.htm";
					}
					
					player.getSubClasses().get(player.getClassIndex()).setIsDualClass(true);
					
					final SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.SUBCLASS_S1_HAS_BEEN_UPGRADED_TO_DUEL_CLASS_S2_CONGRATULATIONS);
					msg.addClassId(player.getClassId().getId());
					msg.addClassId(player.getClassId().getId());
					player.sendPacket(msg);
					
					player.sendPacket(new ExSubjobInfo(player));
					player.broadcastSocialAction(SocialAction.LEVEL_UP);
					
					if (event.equals("red_crystal"))
					{
						giveItems(player, RED_SOUL_CRYSTAL_15);
					}
					else if (event.equals("blue_crystal"))
					{
						giveItems(player, BLUE_SOUL_CRYSTAL_15);
					}
					else if (event.equals("green_crystal"))
					{
						giveItems(player, GREEN_SOUL_CRYSTAL_15);
					}
					
					giveItems(player, RECIPE_TWILIGHT_NECKLACE);
					giveItems(player, CRYSTAL_R);
					addExpAndSp(player, 175739575, 42177);
					qs.exitQuest(false, true);
					htmltext = "33344-28.htm";
				}
				break;
			}
			case "33344-13.htm":
			case "33344-18.htm":
			case "32615-02.htm":
			case "33344-26.htm":
			case "33344-27.htm":
			{
				htmltext = event;
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (npc.getId() == HADEL)
		{
			if (qs.isStarted())
			{
				if (player.getClassIndex() == qs.getInt(VAR_SUB_INDEX))
				{
					switch (qs.getCond())
					{
						case 1:
						case 2:
						{
							htmltext = "33344-15.htm";
							break;
						}
						case 3:
						{
							htmltext = "33344-17.htm";
							break;
						}
						case 4:
						case 5:
						{
							htmltext = "33344-20.htm";
							break;
						}
						case 6:
						{
							htmltext = "33344-21.htm";
							break;
						}
						case 7:
						case 8:
						{
							htmltext = "33344-23.htm";
							break;
						}
						case 9:
						{
							htmltext = "33344-24.htm";
							break;
						}
					}
				}
				else
				{
					htmltext = "33344-16.htm";
				}
			}
			else if (qs.isCompleted())
			{
				htmltext = "33344-12.htm";
			}
			else
			{
				if (!player.isSubClassActive() || !player.isInCategory(CategoryType.FOURTH_CLASS_GROUP) || (player.getLevel() < 80))
				{
					htmltext = "33344-02.htm";
				}
				else if (!CategoryData.getInstance().isInCategory(CategoryType.AWAKEN_GROUP, player.getBaseClass()))
				{
					htmltext = "33344-03.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.SIGEL_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.SIGEL_CANDIDATE))
				{
					htmltext = "33344-sigel.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.TYRR_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.TYRR_CANDIDATE))
				{
					htmltext = "33344-tyrr.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.OTHELL_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.OTHELL_CANDIDATE))
				{
					htmltext = "33344-othell.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.YUL_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.YUL_CANDIDATE))
				{
					htmltext = "33344-yul.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.FEOH_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.FEOH_CANDIDATE))
				{
					htmltext = "33344-feoh.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.ISS_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.ISS_CANDIDATE))
				{
					htmltext = "33344-iss.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.WYNN_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.WYNN_CANDIDATE))
				{
					htmltext = "33344-wynn.htm";
				}
				else if (CategoryData.getInstance().isInCategory(CategoryType.AEORE_GROUP, player.getBaseClass()) && player.isInCategory(CategoryType.AEORE_CANDIDATE))
				{
					htmltext = "33344-aeore.htm";
				}
				else if (player.hasDualClass())
				{
					htmltext = "33344-12.htm";
				}
				else
				{
					htmltext = "33344-01.htm";
				}
			}
		}
		else if ((npc.getId() == ISHUMA) && qs.isStarted())
		{
			switch (qs.getCond())
			{
				case 7:
				{
					htmltext = "32615-01.htm";
					break;
				}
				case 8:
				{
					qs.setCond(9, true);
					giveItems(player, PETRIFIED_GIANTS_HAND);
					giveItems(player, PETRIFIED_GIANTS_FOOT);
					htmltext = "32615-04.htm";
					break;
				}
				default:
				{
					htmltext = "32615-05.htm";
					break;
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		
		if ((qs != null) && (killer.getClassIndex() == qs.getInt(VAR_SUB_INDEX)))
		{
			switch (qs.getCond())
			{
				case 1:
				{
					if (Util.contains(GIANTS_HAND_MONSTERS, npc.getId()))
					{
						giveItems(killer, PETRIFIED_GIANTS_HAND_PIECE, 1);
						qs.setCond(2, true);
					}
					break;
				}
				case 2:
				{
					if (Util.contains(GIANTS_HAND_MONSTERS, npc.getId()))
					{
						if (giveItemRandomly(killer, npc, PETRIFIED_GIANTS_HAND_PIECE, 1, 10, 1.0, true))
						{
							qs.setCond(3, true);
						}
					}
					break;
				}
				case 4:
				{
					if (Util.contains(GIANTS_FOOT_MONSTERS, npc.getId()))
					{
						qs.giveItems(PETRIFIED_GIANTS_FOOT_PIECE, 1);
						qs.setCond(5, true);
					}
					break;
				}
				case 5:
				{
					if (Util.contains(GIANTS_FOOT_MONSTERS, npc.getId()))
					{
						if (giveItemRandomly(killer, npc, PETRIFIED_GIANTS_FOOT_PIECE, 1, 10, 1.0, true))
						{
							qs.setCond(6, true);
						}
					}
					break;
				}
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
}
