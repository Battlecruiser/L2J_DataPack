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
package events.MasterOfEnchanting;

import java.util.Date;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Master of Enchanting event AI.
 * @author Gnacik
 */
public class MasterOfEnchanting extends LongTimeEvent
{
	private static final int _master_yogi = 32599;
	private static final int _master_yogi_staff = 13539;
	private static final int _master_yogi_scroll = 13540;
	
	private static final int _staff_price = 1000000;
	private static final int _scroll_24_price = 5000000;
	private static final int _scroll_24_time = 6;
	
	private static final int _scroll_1_price = 500000;
	private static final int _scroll_10_price = 5000000;
	
	private static final int[] _hat_shadow_reward =
	{
		13074, 13075, 13076
	};
	private static final int[] _hat_event_reward =
	{
		13518, 13519, 13522
	};
	private static final int[] _crystal_reward =
	{
		9570, 9571, 9572
	};
	
	@SuppressWarnings("deprecation")
	private static final Date _eventStart = new Date(2011, 7, 1);

	public MasterOfEnchanting(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(_master_yogi);
		addFirstTalkId(_master_yogi);
		addTalkId(_master_yogi);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (event.equalsIgnoreCase("buy_staff"))
		{
			if (!st.hasQuestItems(_master_yogi_staff) && (st.getQuestItemsCount(PcInventory.ADENA_ID) > _staff_price))
			{
				st.takeItems(PcInventory.ADENA_ID, _staff_price);
				st.giveItems(_master_yogi_staff, 1);
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
				if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_24_price)
				{
					st.takeItems(PcInventory.ADENA_ID, _scroll_24_price);
					st.giveItems(_master_yogi_scroll, 24);
					saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (_scroll_24_time * 3600000)));
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
					sm.addNumber(hours);
					sm.addNumber(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else if (minutes > 0)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ITEM_PURCHASABLE_IN_S1_MINUTES);
					sm.addNumber(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else
				{
					// Little glitch. There is no SystemMessage with seconds only.
					// If time is less than 1 minute player can buy scrolls
					if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_24_price)
					{
						st.takeItems(PcInventory.ADENA_ID, _scroll_24_price);
						st.giveItems(_master_yogi_scroll, 24);
						saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (_scroll_24_time * 3600000)));
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
			if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_1_price)
			{
				st.takeItems(PcInventory.ADENA_ID, _scroll_1_price);
				st.giveItems(_master_yogi_scroll, 1);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s1-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_10"))
		{
			if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_10_price)
			{
				st.takeItems(PcInventory.ADENA_ID, _scroll_10_price);
				st.giveItems(_master_yogi_scroll, 10);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s10-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("receive_reward"))
		{
			if ((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == _master_yogi_staff) && (st.getEnchantLevel(_master_yogi_staff) > 3))
			{
				switch (st.getEnchantLevel(_master_yogi_staff))
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
						st.giveItems(_hat_shadow_reward[getRandom(3)], 1);
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
						st.giveItems(_hat_event_reward[getRandom(3)], 1);
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
						st.giveItems(_crystal_reward[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 14
						break;
					case 21:
						st.giveItems(8762, 1); // Top-Grade Life Stone: level 76
						st.giveItems(8752, 1); // High-Grade Life Stone: level 76
						st.giveItems(_crystal_reward[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 14
						break;
					case 22:
						st.giveItems(13989, 1); // S80 Grade Armor Chest (Event)
						break;
					case 23:
						st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
					default:
						if (st.getEnchantLevel(_master_yogi_staff) > 23)
						{
							st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
						}
						break;
				}
				st.takeItems(_master_yogi_staff, 1);
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
		return npc.getNpcId() + ".htm";
	}
	
	public static void main(String[] args)
	{
		new MasterOfEnchanting(-1, "MasterOfEnchanting", "events");
	}
}
