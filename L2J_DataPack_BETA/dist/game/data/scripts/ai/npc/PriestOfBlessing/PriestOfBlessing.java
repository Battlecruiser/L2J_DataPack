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
package ai.npc.PriestOfBlessing;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Priest Of Blessing AI.
 * @author Gnacik
 */
public class PriestOfBlessing extends AbstractNpcAI
{
	// Spawn state
	private static boolean SPAWNED = false;
	// NPC
	private static final int PRIEST = 32783;
	// Prices
	private static final int PRICE_VOICE = 100000;
	private static final int NEVIT_VOICE = 17094;
	private static final int[] PRIEVE_HOURGLASS =
	{
		4000,
		30000,
		110000,
		310000,
		970000,
		2160000,
		5000000
	};
	// @formatter:off
	private static final int[][] HOURGLASSES =
	{
		{ 17095, 17096, 17097, 17098, 17099 },
		{ 17100, 17101, 17102, 17103, 17104 },
		{ 17105, 17106, 17107, 17108, 17109 },
		{ 17110, 17111, 17112, 17113, 17114 },
		{ 17115, 17116, 17117, 17118, 17119 },
		{ 17120, 17121, 17122, 17123, 17124 },
		{ 17125, 17126, 17127, 17128, 17129 }
	};
	// @formatter:on
	// Spawns
	private static final Location[] SPAWNS =
	{
		new Location(-84139, 243145, -3704, 8473),
		new Location(-119702, 44557, 360, 33023),
		new Location(45413, 48351, -3056, 50020),
		new Location(115607, -177945, -896, 38058),
		new Location(12086, 16589, -4584, 3355),
		new Location(-45032, -113561, -192, 32767),
		new Location(-83112, 150922, -3120, 2280),
		new Location(-13931, 121938, -2984, 30212),
		new Location(87127, -141330, -1336, 49153),
		new Location(43520, -47590, -792, 43738),
		new Location(148060, -55314, -2728, 40961),
		new Location(82801, 149381, -3464, 53707),
		new Location(82433, 53285, -1488, 22942),
		new Location(147059, 25930, -2008, 56399),
		new Location(111171, 221053, -3544, 2058),
		new Location(15907, 142901, -2688, 14324),
		new Location(116972, 77255, -2688, 41951)
	};
	
	public PriestOfBlessing(String name, String descr)
	{
		super(name, descr);
		
		addStartNpc(PRIEST);
		addFirstTalkId(PRIEST);
		addTalkId(PRIEST);
		if (!SPAWNED)
		{
			for (Location spawn : SPAWNS)
			{
				addSpawn(PRIEST, spawn, false, 0);
			}
			SPAWNED = true;
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("buy_voice"))
		{
			if (player.getAdena() >= PRICE_VOICE)
			{
				String value = loadGlobalQuestVar(player.getAccountName() + "_voice");
				long _reuse_time = value == "" ? 0 : Long.parseLong(value);
				
				if (System.currentTimeMillis() > _reuse_time)
				{
					takeItems(player, PcInventory.ADENA_ID, PRICE_VOICE);
					giveItems(player, NEVIT_VOICE, 1, -1);
					saveGlobalQuestVar(player.getAccountName() + "_voice", Long.toString(System.currentTimeMillis() + (20 * 3600000)));
				}
				else
				{
					long remainingTime = (_reuse_time - System.currentTimeMillis()) / 1000;
					int hours = (int) (remainingTime / 3600);
					int minutes = (int) ((remainingTime % 3600) / 60);
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.AVAILABLE_AFTER_S1_S2_HOURS_S3_MINUTES);
					sm.addItemName(NEVIT_VOICE);
					sm.addNumber(hours);
					sm.addNumber(minutes);
					player.sendPacket(sm);
				}
				return null;
			}
			htmltext = "32783-adena.htm";
		}
		else if (event.equalsIgnoreCase("buy_hourglass"))
		{
			int _index = getHGIndex(player.getLevel());
			int _price_hourglass = PRIEVE_HOURGLASS[_index];
			
			if (player.getAdena() >= _price_hourglass)
			{
				String value = loadGlobalQuestVar(player.getAccountName() + "_hg_" + _index);
				long _reuse_time = value == "" ? 0 : Long.parseLong(value);
				
				if (System.currentTimeMillis() > _reuse_time)
				{
					int[] _hg = HOURGLASSES[_index];
					int _nevit_hourglass = _hg[getRandom(0, _hg.length - 1)];
					takeItems(player, PcInventory.ADENA_ID, _price_hourglass);
					giveItems(player, _nevit_hourglass, 1, -1);
					saveGlobalQuestVar(player.getAccountName() + "_hg_" + _index, Long.toString(System.currentTimeMillis() + (20 * 3600000)));
				}
				else
				{
					long remainingTime = (_reuse_time - System.currentTimeMillis()) / 1000;
					int hours = (int) (remainingTime / 3600);
					int minutes = (int) ((remainingTime % 3600) / 60);
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.AVAILABLE_AFTER_S1_S2_HOURS_S3_MINUTES);
					sm.addString("Nevit's Hourglass");
					sm.addNumber(hours);
					sm.addNumber(minutes);
					player.sendPacket(sm);
				}
				return null;
			}
			htmltext = "32783-adena.htm";
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage(0);
		final String content = getHtm(player.getHtmlPrefix(), "32783.htm");
		html.setHtml(content);
		html.replace("%donate%", Util.formatAdena(PRIEVE_HOURGLASS[getHGIndex(player.getLevel())]));
		player.sendPacket(html);
		return null;
	}
	
	private int getHGIndex(int lvl)
	{
		int index = 0;
		if (lvl < 20)
		{
			index = 0;
		}
		else if (lvl < 40)
		{
			index = 1;
		}
		else if (lvl < 52)
		{
			index = 2;
		}
		else if (lvl < 61)
		{
			index = 3;
		}
		else if (lvl < 76)
		{
			index = 4;
		}
		else if (lvl < 80)
		{
			index = 5;
		}
		else if (lvl < 86)
		{
			index = 6;
		}
		return index;
	}
	
	public static void main(String[] args)
	{
		new PriestOfBlessing(PriestOfBlessing.class.getSimpleName(), "ai/npc/");
	}
}
