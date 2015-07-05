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
package handlers.bypasshandlers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.drops.DropListScope;
import com.l2jserver.gameserver.model.drops.GeneralDropItem;
import com.l2jserver.gameserver.model.drops.GroupedGeneralDropItem;
import com.l2jserver.gameserver.model.drops.IDropItem;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.HtmlUtil;
import com.l2jserver.gameserver.util.Util;

/**
 * @author NosBit
 */
public class NpcViewMod implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"NpcViewMod"
	};
	
	private static final int DROP_LIST_ITEMS_PER_PAGE = 10;
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character bypassOrigin)
	{
		final StringTokenizer st = new StringTokenizer(command);
		st.nextToken();
		
		if (!st.hasMoreTokens())
		{
			_log.warning("Bypass[NpcViewMod] used without enough parameters.");
			return false;
		}
		
		final String actualCommand = st.nextToken();
		switch (actualCommand.toLowerCase())
		{
			case "view":
			{
				final L2Object target;
				if (st.hasMoreElements())
				{
					try
					{
						target = L2World.getInstance().findObject(Integer.parseInt(st.nextToken()));
					}
					catch (NumberFormatException e)
					{
						return false;
					}
				}
				else
				{
					target = activeChar.getTarget();
				}
				
				final L2Npc npc = target instanceof L2Npc ? (L2Npc) target : null;
				if (npc == null)
				{
					return false;
				}
				
				NpcViewMod.sendNpcView(activeChar, npc);
				break;
			}
			case "droplist":
			{
				if (st.countTokens() < 2)
				{
					_log.warning("Bypass[NpcViewMod] used without enough parameters.");
					return false;
				}
				
				final String dropListScopeString = st.nextToken();
				try
				{
					final DropListScope dropListScope = Enum.valueOf(DropListScope.class, dropListScopeString);
					final L2Object target = L2World.getInstance().findObject(Integer.parseInt(st.nextToken()));
					final L2Npc npc = target instanceof L2Npc ? (L2Npc) target : null;
					if (npc == null)
					{
						return false;
					}
					final int page = st.hasMoreElements() ? Integer.parseInt(st.nextToken()) : 0;
					sendNpcDropList(activeChar, npc, dropListScope, page);
				}
				catch (NumberFormatException e)
				{
					return false;
				}
				catch (IllegalArgumentException e)
				{
					_log.warning("Bypass[NpcViewMod] unknown drop list scope: " + dropListScopeString);
					return false;
				}
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
	
	public static void sendNpcView(L2PcInstance activeChar, L2Npc npc)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage();
		html.setFile(activeChar.getHtmlPrefix(), "data/html/mods/NpcView/Info.htm");
		html.replace("%name%", npc.getName());
		html.replace("%hpGauge%", HtmlUtil.getHpGauge(250, (long) npc.getCurrentHp(), npc.getMaxHp(), false));
		html.replace("%mpGauge%", HtmlUtil.getMpGauge(250, (long) npc.getCurrentMp(), npc.getMaxMp(), false));
		
		final L2Spawn npcSpawn = npc.getSpawn();
		if ((npcSpawn == null) || (npcSpawn.getRespawnMinDelay() == 0))
		{
			html.replace("%respawn%", "None");
		}
		else
		{
			TimeUnit timeUnit = TimeUnit.MILLISECONDS;
			long min = Long.MAX_VALUE;
			for (TimeUnit tu : TimeUnit.values())
			{
				final long minTimeFromMillis = tu.convert(npcSpawn.getRespawnMinDelay(), TimeUnit.MILLISECONDS);
				final long maxTimeFromMillis = tu.convert(npcSpawn.getRespawnMaxDelay(), TimeUnit.MILLISECONDS);
				if ((TimeUnit.MILLISECONDS.convert(minTimeFromMillis, tu) == npcSpawn.getRespawnMinDelay()) && (TimeUnit.MILLISECONDS.convert(maxTimeFromMillis, tu) == npcSpawn.getRespawnMaxDelay()))
				{
					if (min > minTimeFromMillis)
					{
						min = minTimeFromMillis;
						timeUnit = tu;
					}
				}
			}
			final long minRespawnDelay = timeUnit.convert(npcSpawn.getRespawnMinDelay(), TimeUnit.MILLISECONDS);
			final long maxRespawnDelay = timeUnit.convert(npcSpawn.getRespawnMaxDelay(), TimeUnit.MILLISECONDS);
			final String timeUnitName = timeUnit.name().charAt(0) + timeUnit.name().toLowerCase().substring(1);
			if (npcSpawn.hasRespawnRandom())
			{
				html.replace("%respawn%", minRespawnDelay + "-" + maxRespawnDelay + " " + timeUnitName);
			}
			else
			{
				html.replace("%respawn%", minRespawnDelay + " " + timeUnitName);
			}
		}
		
		html.replace("%atktype%", Util.capitalizeFirst(npc.getAttackType().name().toLowerCase()));
		html.replace("%atkrange%", npc.getStat().getPhysicalAttackRange());
		
		html.replace("%patk%", npc.getPAtk(activeChar));
		html.replace("%pdef%", npc.getPDef(activeChar));
		
		html.replace("%matk%", npc.getMAtk(activeChar, null));
		html.replace("%mdef%", npc.getMDef(activeChar, null));
		
		html.replace("%atkspd%", npc.getPAtkSpd());
		html.replace("%castspd%", npc.getMAtkSpd());
		
		html.replace("%critrate%", npc.getStat().getCriticalHit(activeChar, null));
		html.replace("%evasion%", npc.getEvasionRate(activeChar));
		
		html.replace("%accuracy%", npc.getStat().getAccuracy());
		html.replace("%speed%", (int) npc.getStat().getMoveSpeed());
		
		html.replace("%attributeatktype%", Elementals.getElementName(npc.getStat().getAttackElement()));
		html.replace("%attributeatkvalue%", npc.getStat().getAttackElementValue(npc.getStat().getAttackElement()));
		html.replace("%attributefire%", npc.getStat().getDefenseElementValue(Elementals.FIRE));
		html.replace("%attributewater%", npc.getStat().getDefenseElementValue(Elementals.WATER));
		html.replace("%attributewind%", npc.getStat().getDefenseElementValue(Elementals.WIND));
		html.replace("%attributeearth%", npc.getStat().getDefenseElementValue(Elementals.EARTH));
		html.replace("%attributedark%", npc.getStat().getDefenseElementValue(Elementals.DARK));
		html.replace("%attributeholy%", npc.getStat().getDefenseElementValue(Elementals.HOLY));
		
		html.replace("%dropListButtons%", getDropListButtons(npc));
		
		activeChar.sendPacket(html);
	}
	
	public static String getDropListButtons(L2Npc npc)
	{
		final StringBuilder sb = new StringBuilder();
		final Map<DropListScope, List<IDropItem>> dropLists = npc.getTemplate().getDropLists();
		if ((dropLists != null) && !dropLists.isEmpty() && (dropLists.containsKey(DropListScope.DEATH) || dropLists.containsKey(DropListScope.CORPSE)))
		{
			sb.append("<table width=275 cellpadding=0 cellspacing=0><tr>");
			if (dropLists.containsKey(DropListScope.DEATH))
			{
				sb.append("<td align=center><button value=\"Show Drop\" width=100 height=25 action=\"bypass NpcViewMod dropList DEATH " + npc.getObjectId() + "\" back=\"L2UI_CT1.Button_DF_Calculator_Down\" fore=\"L2UI_CT1.Button_DF_Calculator\"></td>");
			}
			
			if (dropLists.containsKey(DropListScope.CORPSE))
			{
				sb.append("<td align=center><button value=\"Show Spoil\" width=100 height=25 action=\"bypass NpcViewMod dropList CORPSE " + npc.getObjectId() + "\" back=\"L2UI_CT1.Button_DF_Calculator_Down\" fore=\"L2UI_CT1.Button_DF_Calculator\"></td>");
			}
			sb.append("</tr></table>");
		}
		return sb.toString();
	}
	
	public static void sendNpcDropList(L2PcInstance activeChar, L2Npc npc, DropListScope dropListScope, int page)
	{
		final List<IDropItem> dropList = npc.getTemplate().getDropList(dropListScope);
		if ((dropList == null) || dropList.isEmpty())
		{
			return;
		}
		
		int pages = dropList.size() / DROP_LIST_ITEMS_PER_PAGE;
		if ((DROP_LIST_ITEMS_PER_PAGE * pages) < dropList.size())
		{
			pages++;
		}
		
		final StringBuilder pagesSb = new StringBuilder();
		if (pages > 1)
		{
			pagesSb.append("<table><tr>");
			for (int i = 0; i < pages; i++)
			{
				pagesSb.append("<td align=center><button value=\"" + (i + 1) + "\" width=20 height=20 action=\"bypass NpcViewMod dropList " + dropListScope + " " + npc.getObjectId() + " " + i + "\" back=\"L2UI_CT1.Button_DF_Calculator_Down\" fore=\"L2UI_CT1.Button_DF_Calculator\"></td>");
			}
			pagesSb.append("</tr></table>");
		}
		
		if (page >= pages)
		{
			page = pages - 1;
		}
		
		final int start = page > 0 ? page * DROP_LIST_ITEMS_PER_PAGE : 0;
		
		int end = (page * DROP_LIST_ITEMS_PER_PAGE) + DROP_LIST_ITEMS_PER_PAGE;
		if (end > dropList.size())
		{
			end = dropList.size();
		}
		
		final DecimalFormat amountFormat = new DecimalFormat("#,###");
		final DecimalFormat chanceFormat = new DecimalFormat("0.00##");
		
		int leftHeight = 0;
		int rightHeight = 0;
		final StringBuilder leftSb = new StringBuilder();
		final StringBuilder rightSb = new StringBuilder();
		for (int i = start; i < end; i++)
		{
			final StringBuilder sb = new StringBuilder();
			
			int height = 64;
			final IDropItem dropItem = dropList.get(i);
			if (dropItem instanceof GeneralDropItem)
			{
				addGeneralDropItem(activeChar, npc, amountFormat, chanceFormat, sb, (GeneralDropItem) dropItem);
			}
			else if (dropItem instanceof GroupedGeneralDropItem)
			{
				final GroupedGeneralDropItem generalGroupedDropItem = (GroupedGeneralDropItem) dropItem;
				if (generalGroupedDropItem.getItems().size() == 1)
				{
					final GeneralDropItem generalDropItem = generalGroupedDropItem.getItems().get(0);
					addGeneralDropItem(activeChar, npc, amountFormat, chanceFormat, sb, new GeneralDropItem(generalDropItem.getItemId(), generalDropItem.getMin(), generalDropItem.getMax(), (generalDropItem.getChance() * generalGroupedDropItem.getChance()) / 100, generalDropItem.getAmountStrategy(), generalDropItem.getChanceStrategy(), generalGroupedDropItem.getPreciseStrategy(), generalGroupedDropItem.getKillerChanceModifierStrategy(), generalDropItem.getDropCalculationStrategy()));
				}
				else
				{
					GroupedGeneralDropItem normalized = generalGroupedDropItem.normalizeMe(npc, activeChar);
					sb.append("<table width=332 cellpadding=2 cellspacing=0 background=\"L2UI_CT1.Windows.Windows_DF_TooltipBG\">");
					sb.append("<tr><td width=32 valign=top><img src=\"L2UI_CT1.ICON_DF_premiumItem\" width=32 height=32></td>");
					sb.append("<td fixwidth=300 align=center><font name=\"ScreenMessageSmall\" color=\"CD9000\">One from group</font>");
					sb.append("</td></tr><tr><td width=32></td><td width=300><table width=295 cellpadding=0 cellspacing=0><tr>");
					sb.append("<td width=48 align=right valign=top><font color=\"LEVEL\">Chance:</font></td>");
					sb.append("<td width=247 align=center>");
					sb.append(chanceFormat.format(Math.min(normalized.getChance(), 100)));
					sb.append("%</td></tr></table><br>");
					
					for (GeneralDropItem generalDropItem : normalized.getItems())
					{
						final L2Item item = ItemTable.getInstance().getTemplate(generalDropItem.getItemId());
						sb.append("<table width=291 cellpadding=2 cellspacing=0 background=\"L2UI_CT1.Windows.Windows_DF_TooltipBG\">");
						sb.append("<tr><td width=32 valign=top>");
						sb.append("<img src=\"" + item.getIcon() + "\" width=32 height=32>");
						sb.append("</td><td fixwidth=259 align=center><font name=\"hs9\" color=\"CD9000\">");
						sb.append(item.getName());
						sb.append("</font></td></tr><tr><td width=32></td><td width=259><table width=253 cellpadding=0 cellspacing=0>");
						sb.append("<tr><td width=48 align=right valign=top><font color=\"LEVEL\">Amount:</font></td><td width=205 align=center>");
						MinMax minMax = getPreciseMinMax(normalized.getChance(), generalDropItem.getMin(npc), generalDropItem.getMax(npc), generalDropItem.isPreciseCalculated());
						final long min = minMax.min;
						final long max = minMax.max;
						if (min == max)
						{
							sb.append(amountFormat.format(min));
						}
						else
						{
							sb.append(amountFormat.format(min));
							sb.append(" - ");
							sb.append(amountFormat.format(max));
						}
						
						sb.append("</td></tr><tr><td width=48 align=right valign=top><font color=\"LEVEL\">Chance:</font></td>");
						sb.append("<td width=205 align=center>");
						sb.append(chanceFormat.format(Math.min(generalDropItem.getChance(), 100)));
						sb.append("%</td></tr></table></td></tr><tr><td width=32></td><td width=259>&nbsp;</td></tr></table>");
						
						height += 64;
					}
					
					sb.append("</td></tr><tr><td width=32></td><td width=300>&nbsp;</td></tr></table>");
				}
			}
			
			if (leftHeight >= (rightHeight + height))
			{
				rightSb.append(sb);
				rightHeight += height;
			}
			else
			{
				leftSb.append(sb);
				leftHeight += height;
			}
		}
		
		final StringBuilder bodySb = new StringBuilder();
		bodySb.append("<table><tr>");
		bodySb.append("<td>");
		bodySb.append(leftSb.toString());
		bodySb.append("</td><td>");
		bodySb.append(rightSb.toString());
		bodySb.append("</td>");
		bodySb.append("</tr></table>");
		
		String html = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/mods/NpcView/DropList.htm");
		if (html == null)
		{
			_log.warning(NpcViewMod.class.getSimpleName() + ": The html file data/html/mods/NpcView/DropList.htm could not be found.");
			return;
		}
		html = html.replaceAll("%name%", npc.getName());
		html = html.replaceAll("%dropListButtons%", getDropListButtons(npc));
		html = html.replaceAll("%pages%", pagesSb.toString());
		html = html.replaceAll("%items%", bodySb.toString());
		Util.sendCBHtml(activeChar, html);
	}
	
	/**
	 * @param activeChar
	 * @param npc
	 * @param amountFormat
	 * @param chanceFormat
	 * @param sb
	 * @param dropItem
	 */
	private static void addGeneralDropItem(L2PcInstance activeChar, L2Npc npc, final DecimalFormat amountFormat, final DecimalFormat chanceFormat, final StringBuilder sb, final GeneralDropItem dropItem)
	{
		final L2Item item = ItemTable.getInstance().getTemplate(dropItem.getItemId());
		sb.append("<table width=332 cellpadding=2 cellspacing=0 background=\"L2UI_CT1.Windows.Windows_DF_TooltipBG\">");
		sb.append("<tr><td width=32 valign=top>");
		sb.append("<img src=\"" + item.getIcon() + "\" width=32 height=32>");
		sb.append("</td><td fixwidth=300 align=center><font name=\"hs9\" color=\"CD9000\">");
		sb.append(item.getName());
		sb.append("</font></td></tr><tr><td width=32></td><td width=300><table width=295 cellpadding=0 cellspacing=0>");
		sb.append("<tr><td width=48 align=right valign=top><font color=\"LEVEL\">Amount:</font></td>");
		sb.append("<td width=247 align=center>");
		MinMax minMax = getPreciseMinMax(dropItem.getChance(npc, activeChar), dropItem.getMin(npc), dropItem.getMax(npc), dropItem.isPreciseCalculated());
		
		final long min = minMax.min;
		final long max = minMax.max;
		if (min == max)
		{
			sb.append(amountFormat.format(min));
		}
		else
		{
			sb.append(amountFormat.format(min));
			sb.append(" - ");
			sb.append(amountFormat.format(max));
		}
		
		sb.append("</td></tr><tr><td width=48 align=right valign=top><font color=\"LEVEL\">Chance:</font></td>");
		sb.append("<td width=247 align=center>");
		sb.append(chanceFormat.format(Math.min(dropItem.getChance(npc, activeChar), 100)));
		sb.append("%</td></tr></table></td></tr><tr><td width=32></td><td width=300>&nbsp;</td></tr></table>");
	}
	
	private static class MinMax
	{
		public final long min, max;
		
		public MinMax(long min, long max)
		{
			this.min = min;
			this.max = max;
			
		}
		
	}
	
	private static MinMax getPreciseMinMax(double chance, long min, long max, boolean isPrecise)
	{
		if (!isPrecise || (chance <= 100))
		{
			return new MinMax(min, max);
		}
		
		int mult = (int) (chance) / 100;
		return new MinMax(mult * min, (chance % 100) > 0 ? (mult + 1) * max : mult * max);
	}
}
