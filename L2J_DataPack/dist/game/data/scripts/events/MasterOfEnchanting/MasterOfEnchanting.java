/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package events.MasterOfEnchanting;

import java.util.Date;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Master of Enchanting event AI.
 * @author Gnacik
 */
public final class MasterOfEnchanting extends LongTimeEvent
{
	// NPC
	private static final int MASTER_YOGI = 32599;
	// Items
	private static final int MASTER_YOGI_STAFF = 13539;
	private static final int MASTER_YOGI_SCROLL = 13540;
	// Misc
	private static final int STAFF_PRICE = 1000000;
	private static final int SCROLL_24_PRICE = 5000000;
	private static final int SCROLL_24_TIME = 6;
	private static final int SCROLL_1_PRICE = 500000;
	private static final int SCROLL_10_PRICE = 5000000;
	
	private static final int[] HAT_SHADOW_REWARD =
	{
		13074,
		13075,
		13076
	};
	private static final int[] HAT_EVENT_REWARD =
	{
		13518,
		13519,
		13522
	};
	private static final int[] CRYSTAL_REWARD =
	{
		9570,
		9571,
		9572
	};
	
	@SuppressWarnings("deprecation")
	private static final Date _eventStart = new Date(2011, 7, 1);
	
	private MasterOfEnchanting()
	{
		super(MasterOfEnchanting.class.getSimpleName(), "events");
		addStartNpc(MASTER_YOGI);
		addFirstTalkId(MASTER_YOGI);
		addTalkId(MASTER_YOGI);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (event.equalsIgnoreCase("buy_staff"))
		{
			if (!st.hasQuestItems(MASTER_YOGI_STAFF) && (st.getQuestItemsCount(Inventory.ADENA_ID) > STAFF_PRICE))
			{
				st.takeItems(Inventory.ADENA_ID, STAFF_PRICE);
				st.giveItems(MASTER_YOGI_STAFF, 1);
				htmltext = "32599-staffbuyed.htm";
			}
			else
			{
				htmltext = "32599-staffcant.htm";
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_24"))
		{
			long _curr_time = System.currentTimeMillis();
			String value = loadGlobalQuestVar(player.getAccountName());
			long _reuse_time = value == "" ? 0 : Long.parseLong(value);
			if (player.getCreateDate().after(_eventStart))
			{
				return "32599-bidth.htm";
			}
			
			if (_curr_time > _reuse_time)
			{
				if (st.getQuestItemsCount(Inventory.ADENA_ID) > SCROLL_24_PRICE)
				{
					st.takeItems(Inventory.ADENA_ID, SCROLL_24_PRICE);
					st.giveItems(MASTER_YOGI_SCROLL, 24);
					saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (SCROLL_24_TIME * 3600000)));
					htmltext = "32599-scroll24.htm";
				}
				else
				{
					htmltext = "32599-s24-no.htm";
				}
			}
			else
			{
				long _remaining_time = (_reuse_time - _curr_time) / 1000;
				int hours = (int) _remaining_time / 3600;
				int minutes = ((int) _remaining_time % 3600) / 60;
				if (hours > 0)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ITEM_PURCHASABLE_IN_S1_HOURS_S2_MINUTES);
					sm.addInt(hours);
					sm.addInt(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else if (minutes > 0)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ITEM_PURCHASABLE_IN_S1_MINUTES);
					sm.addInt(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else
				{
					// Little glitch. There is no SystemMessage with seconds only.
					// If time is less than 1 minute player can buy scrolls
					if (st.getQuestItemsCount(Inventory.ADENA_ID) > SCROLL_24_PRICE)
					{
						st.takeItems(Inventory.ADENA_ID, SCROLL_24_PRICE);
						st.giveItems(MASTER_YOGI_SCROLL, 24);
						saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (SCROLL_24_TIME * 3600000)));
						htmltext = "32599-scroll24.htm";
					}
					else
					{
						htmltext = "32599-s24-no.htm";
					}
				}
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_1"))
		{
			if (st.getQuestItemsCount(Inventory.ADENA_ID) > SCROLL_1_PRICE)
			{
				st.takeItems(Inventory.ADENA_ID, SCROLL_1_PRICE);
				st.giveItems(MASTER_YOGI_SCROLL, 1);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s1-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_10"))
		{
			if (st.getQuestItemsCount(Inventory.ADENA_ID) > SCROLL_10_PRICE)
			{
				st.takeItems(Inventory.ADENA_ID, SCROLL_10_PRICE);
				st.giveItems(MASTER_YOGI_SCROLL, 10);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s10-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("receive_reward"))
		{
			if ((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == MASTER_YOGI_STAFF) && (st.getEnchantLevel(MASTER_YOGI_STAFF) > 3))
			{
				switch (st.getEnchantLevel(MASTER_YOGI_STAFF))
				{
					case 4:
						st.giveItems(6406, 1); // Firework
						break;
					case 5:
						st.giveItems(6406, 2); // Firework
						st.giveItems(6407, 1); // Large Firework
						break;
					case 6:
						st.giveItems(6406, 3); // Firework
						st.giveItems(6407, 2); // Large Firework
						break;
					case 7:
						st.giveItems(HAT_SHADOW_REWARD[getRandom(3)], 1);
						break;
					case 8:
						st.giveItems(955, 1); // Scroll: Enchant Weapon (D)
						break;
					case 9:
						st.giveItems(955, 1); // Scroll: Enchant Weapon (D)
						st.giveItems(956, 1); // Scroll: Enchant Armor (D)
						break;
					case 10:
						st.giveItems(951, 1); // Scroll: Enchant Weapon (C)
						break;
					case 11:
						st.giveItems(951, 1); // Scroll: Enchant Weapon (C)
						st.giveItems(952, 1); // Scroll: Enchant Armor (C)
						break;
					case 12:
						st.giveItems(948, 1); // Scroll: Enchant Armor (B)
						break;
					case 13:
						st.giveItems(729, 1); // Scroll: Enchant Weapon (A)
						break;
					case 14:
						st.giveItems(HAT_EVENT_REWARD[getRandom(3)], 1);
						break;
					case 15:
						st.giveItems(13992, 1); // Grade S Accessory Chest (Event)
						break;
					case 16:
						st.giveItems(8762, 1); // Top-Grade Life Stone: level 76
						break;
					case 17:
						st.giveItems(959, 1); // Scroll: Enchant Weapon (S)
						break;
					case 18:
						st.giveItems(13991, 1); // Grade S Armor Chest (Event)
						break;
					case 19:
						st.giveItems(13990, 1); // Grade S Weapon Chest (Event)
						break;
					case 20:
						st.giveItems(CRYSTAL_REWARD[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 14
						break;
					case 21:
						st.giveItems(8762, 1); // Top-Grade Life Stone: level 76
						st.giveItems(8752, 1); // High-Grade Life Stone: level 76
						st.giveItems(CRYSTAL_REWARD[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 14
						break;
					case 22:
						st.giveItems(13989, 1); // S80 Grade Armor Chest (Event)
						break;
					case 23:
						st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
					default:
						if (st.getEnchantLevel(MASTER_YOGI_STAFF) > 23)
						{
							st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
						}
						break;
				}
				st.takeItems(MASTER_YOGI_STAFF, 1);
				htmltext = "32599-rewardok.htm";
			}
			else
			{
				htmltext = "32599-rewardnostaff.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		return npc.getId() + ".htm";
	}
	
	public static void main(String[] args)
	{
		new MasterOfEnchanting();
	}
}
