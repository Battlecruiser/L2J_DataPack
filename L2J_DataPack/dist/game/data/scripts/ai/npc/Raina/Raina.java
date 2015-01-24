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
package ai.npc.Raina;

import static com.l2jserver.gameserver.model.base.ClassLevel.THIRD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.data.xml.impl.CategoryData;
import com.l2jserver.gameserver.data.xml.impl.ClassListData;
import com.l2jserver.gameserver.data.xml.impl.SkillTreesData;
import com.l2jserver.gameserver.enums.CategoryType;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.base.ClassLevel;
import com.l2jserver.gameserver.model.base.PlayerClass;
import com.l2jserver.gameserver.model.base.SubClass;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.Id;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.npc.OnNpcMenuSelect;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.AcquireSkillList;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * Raina AI.
 * @author St3eT
 */
public final class Raina extends AbstractNpcAI
{
	// NPC
	private static final int RAINA = 33491;
	// Items
	private static final int SUBCLASS_CERTIFICATE = 30433;
	private final static int ABELIUS_POWER = 32264;
	private final static int SAPYROS_POWER = 32265;
	private final static int ASHAGEN_POWER = 32266;
	private final static int CRANIGG_POWER = 32267;
	private final static int SOLTKREIG_POWER = 32268;
	private final static int NAVIAROPE_POWER = 32269;
	private final static int LEISTER_POWER = 32270;
	private final static int LAKCIS_POWER = 32271;
	// Misc
	private static final Set<PlayerClass> mainSubclassSet;
	private static final Set<PlayerClass> neverSubclassed = EnumSet.of(PlayerClass.Overlord, PlayerClass.Warsmith);
	private static final Set<PlayerClass> subclasseSet1 = EnumSet.of(PlayerClass.DarkAvenger, PlayerClass.Paladin, PlayerClass.TempleKnight, PlayerClass.ShillienKnight);
	private static final Set<PlayerClass> subclasseSet2 = EnumSet.of(PlayerClass.TreasureHunter, PlayerClass.AbyssWalker, PlayerClass.Plainswalker);
	private static final Set<PlayerClass> subclasseSet3 = EnumSet.of(PlayerClass.Hawkeye, PlayerClass.SilverRanger, PlayerClass.PhantomRanger);
	private static final Set<PlayerClass> subclasseSet4 = EnumSet.of(PlayerClass.Warlock, PlayerClass.ElementalSummoner, PlayerClass.PhantomSummoner);
	private static final Set<PlayerClass> subclasseSet5 = EnumSet.of(PlayerClass.Sorceror, PlayerClass.Spellsinger, PlayerClass.Spellhowler);
	private static final EnumMap<PlayerClass, Set<PlayerClass>> subclassSetMap = new EnumMap<>(PlayerClass.class);
	static
	{
		final Set<PlayerClass> subclasses = PlayerClass.getSet(null, THIRD);
		subclasses.removeAll(neverSubclassed);
		mainSubclassSet = subclasses;
		subclassSetMap.put(PlayerClass.DarkAvenger, subclasseSet1);
		subclassSetMap.put(PlayerClass.Paladin, subclasseSet1);
		subclassSetMap.put(PlayerClass.TempleKnight, subclasseSet1);
		subclassSetMap.put(PlayerClass.ShillienKnight, subclasseSet1);
		subclassSetMap.put(PlayerClass.TreasureHunter, subclasseSet2);
		subclassSetMap.put(PlayerClass.AbyssWalker, subclasseSet2);
		subclassSetMap.put(PlayerClass.Plainswalker, subclasseSet2);
		subclassSetMap.put(PlayerClass.Hawkeye, subclasseSet3);
		subclassSetMap.put(PlayerClass.SilverRanger, subclasseSet3);
		subclassSetMap.put(PlayerClass.PhantomRanger, subclasseSet3);
		subclassSetMap.put(PlayerClass.Warlock, subclasseSet4);
		subclassSetMap.put(PlayerClass.ElementalSummoner, subclasseSet4);
		subclassSetMap.put(PlayerClass.PhantomSummoner, subclasseSet4);
		subclassSetMap.put(PlayerClass.Sorceror, subclasseSet5);
		subclassSetMap.put(PlayerClass.Spellsinger, subclasseSet5);
		subclassSetMap.put(PlayerClass.Spellhowler, subclasseSet5);
	}
	private static final Map<CategoryType, Integer> classCloak = new HashMap<>();
	{
		classCloak.put(CategoryType.SIGEL_GROUP, 30310); // Abelius Cloak
		classCloak.put(CategoryType.TYRR_GROUP, 30311); // Sapyros Cloak Grade
		classCloak.put(CategoryType.OTHELL_GROUP, 30312); // Ashagen Cloak Grade
		classCloak.put(CategoryType.YUL_GROUP, 30313); // Cranigg Cloak Grade
		classCloak.put(CategoryType.FEOH_GROUP, 30314); // Soltkreig Cloak Grade
		classCloak.put(CategoryType.ISS_GROUP, 30315); // Naviarope Cloak Grade
		classCloak.put(CategoryType.WYNN_GROUP, 30316); // Leister Cloak Grade
		classCloak.put(CategoryType.AEORE_GROUP, 30317); // Laksis Cloak Grade
	}
	private static final List<PlayerClass> dualClassList = new ArrayList<>();
	{
		dualClassList.addAll(Arrays.asList(PlayerClass.sigelPhoenixKnight, PlayerClass.sigelHellKnight, PlayerClass.sigelEvasTemplar, PlayerClass.sigelShilenTemplar));
		dualClassList.addAll(Arrays.asList(PlayerClass.tyrrDuelist, PlayerClass.tyrrDreadnought, PlayerClass.tyrrTitan, PlayerClass.tyrrGrandKhavatari, PlayerClass.tyrrDoombringer));
		dualClassList.addAll(Arrays.asList(PlayerClass.othellAdventurer, PlayerClass.othellWindRider, PlayerClass.othellGhostHunter, PlayerClass.othellFortuneSeeker));
		dualClassList.addAll(Arrays.asList(PlayerClass.yulSagittarius, PlayerClass.yulMoonlightSentinel, PlayerClass.yulGhostSentinel, PlayerClass.yulTrickster));
		dualClassList.addAll(Arrays.asList(PlayerClass.feohArchmage, PlayerClass.feohSoultaker, PlayerClass.feohMysticMuse, PlayerClass.feoStormScreamer, PlayerClass.feohSoulHound));
		dualClassList.addAll(Arrays.asList(PlayerClass.issHierophant, PlayerClass.issSwordMuse, PlayerClass.issSpectralDancer, PlayerClass.issDoomcryer));
		dualClassList.addAll(Arrays.asList(PlayerClass.wynnArcanaLord, PlayerClass.wynnElementalMaster, PlayerClass.wynnSpectralMaster));
		dualClassList.addAll(Arrays.asList(PlayerClass.aeoreCardinal, PlayerClass.aeoreEvaSaint, PlayerClass.aeoreShillienSaint));
	}
	// @formatter:off
	private static final int[] REAWAKEN_PRICE =
	{
		100_000_000, 90_000_000, 80_000_000, 70_000_000, 60_000_000, 50_000_000, 40_000_000, 30_000_000, 20_000_000, 10_000_000
	};
	// @formatter:on
	
	private Raina()
	{
		super(Raina.class.getSimpleName(), "ai/npc");
		addStartNpc(RAINA);
		addFirstTalkId(RAINA);
		addTalkId(RAINA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "33491-01.html":
			case "33491-02.html":
			case "33491-03.html":
			case "33491-04.html":
			case "reawakenCancel.html":
			{
				htmltext = event;
				break;
			}
			case "addSubclass":
			{
				if (player.isTransformed())
				{
					htmltext = "noTransform.html";
					break;
				}
				else if (player.hasSummon())
				{
					htmltext = "noSummon.html";
					break;
				}
				else if (player.getRace() == Race.ERTHEIA)
				{
					htmltext = "noErtheia.html";
					break;
				}
				else if (!haveDoneQuest(player) && Config.ALT_GAME_SUBCLASS_WITHOUT_QUESTS && !player.isGM())
				{
					htmltext = "noQuest.html";
					break;
				}
				else if (!hasAllSubclassLeveled(player) || (player.getTotalSubClasses() >= Config.MAX_SUBCLASS))
				{
					htmltext = "addFailed.html";
					break;
				}
				else if (!player.isInventoryUnder90(true) || (player.getWeightPenalty() >= 2))
				{
					htmltext = "inventoryLimit.html";
					break;
				}
				
				final Set<PlayerClass> availSubs = getAvailableSubClasses(player);
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "subclassList.html");
				
				if ((availSubs == null) || availSubs.isEmpty())
				{
					break;
				}
				
				for (PlayerClass subClass : availSubs)
				{
					if (subClass != null)
					{
						final int classId = subClass.ordinal();
						final int npcStringId = 11170000 + classId;
						sb.append("<fstring p1=\"0\" p2=\"" + classId + "\">" + npcStringId + "</fstring>");
					}
				}
				html.replace("%subclassList%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case "removeSubclass":
			{
				if (player.isTransformed())
				{
					htmltext = "noTransform.html";
					break;
				}
				else if (player.hasSummon())
				{
					htmltext = "noSummon.html";
					break;
				}
				else if (player.getRace() == Race.ERTHEIA)
				{
					htmltext = "noErtheia.html";
					break;
				}
				else if (!player.isInventoryUnder90(true) || (player.getWeightPenalty() >= 2))
				{
					htmltext = "inventoryLimit.html";
					break;
				}
				else if (player.getSubClasses().isEmpty())
				{
					htmltext = "noSubChange.html";
					break;
				}
				
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "subclassRemoveList.html");
				
				for (SubClass subClass : player.getSubClasses().values())
				{
					if (subClass != null)
					{
						final int classId = subClass.getClassId();
						final int npcStringId = 11170000 + classId;
						sb.append("<fstring p1=\"2\" p2=\"" + subClass.getClassIndex() + "\">" + npcStringId + "</fstring>");
					}
				}
				html.replace("%removeList%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case "changeSubclass":
			{
				if (player.isTransformed())
				{
					htmltext = "noTransform.html";
					break;
				}
				else if (player.hasSummon())
				{
					htmltext = "noSummon.html";
					break;
				}
				else if (player.getRace() == Race.ERTHEIA)
				{
					htmltext = "noErtheia.html";
					break;
				}
				else if (player.getSubClasses().isEmpty())
				{
					htmltext = "noSubChange.html";
					break;
				}
				else if (!hasQuestItems(player, SUBCLASS_CERTIFICATE))
				{
					htmltext = "noCertificate.html";
					break;
				}
				
				player.sendMessage("Not done yet.");
				break;
			}
			case "ertheiaDualClass":
			{
				if ((player.getRace() != Race.ERTHEIA) || (player.getLevel() < 85) || player.hasDualClass())
				{
					htmltext = "addDualClassErtheiaFailed.html";
					break;
				}
				htmltext = "addDualClassErtheia.html";
				break;
			}
			case "addDualClass_SIGEL_GROUP":
			case "addDualClass_TYRR_GROUP":
			case "addDualClass_OTHELL_GROUP":
			case "addDualClass_YUL_GROUP":
			case "addDualClass_FEOH_GROUP":
			case "addDualClass_ISS_GROUP":
			case "addDualClass_WYNN_GROUP":
			case "addDualClass_AEORE_GROUP":
			{
				final CategoryType cType = CategoryType.valueOf(event.replace("addDualClass_", ""));
				
				if (cType == null)
				{
					_log.log(Level.WARNING, getClass().getSimpleName() + ": Cannot parse CategoryType, event: " + event);
				}
				
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addDualClassErtheiaList.html");
				
				for (PlayerClass dualClasses : getDualClasses(player, cType))
				{
					if (dualClasses != null)
					{
						sb.append("<button value=\"" + ClassListData.getInstance().getClass(dualClasses.ordinal()).getClassName() + "\" action=\"bypass -h menu_select?ask=6&reply=" + dualClasses.ordinal() + "\" width=\"200\" height=\"31\" back=\"L2UI_CT1.HtmlWnd_DF_Awake_Down\" fore=\"L2UI_CT1.HtmlWnd_DF_Awake\"><br>");
					}
				}
				html.replace("%dualclassList%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case "reawekenDualclass":
			{
				if (player.isTransformed())
				{
					htmltext = "noTransform.html";
					break;
				}
				else if (player.hasSummon())
				{
					htmltext = "noSummon.html";
					break;
				}
				else if (!player.hasDualClass() || !player.isDualClassActive() || (player.getClassId().level() != ClassLevel.AWAKEN.ordinal()))
				{
					htmltext = "reawakenNoDual.html";
					break;
				}
				
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawaken.html");
				final int index = player.getLevel() > 94 ? REAWAKEN_PRICE.length - 1 : player.getLevel() - 85;
				html.replace("%price%", REAWAKEN_PRICE[index]);
				player.sendPacket(html);
				break;
			}
			case "reawakenDualclassConfirm":
			{
				final int index = player.getLevel() > 94 ? REAWAKEN_PRICE.length - 1 : player.getLevel() - 85;
				if (player.isTransformed())
				{
					htmltext = "noTransform.html";
					break;
				}
				else if (player.hasSummon())
				{
					htmltext = "noSummon.html";
					break;
				}
				else if (!player.hasDualClass() || !player.isDualClassActive() || (player.getClassId().level() != ClassLevel.AWAKEN.ordinal()))
				{
					htmltext = "reawakenNoDual.html";
					break;
				}
				else if ((player.getAdena() < REAWAKEN_PRICE[index]) || !hasQuestItems(player, getCloakId(player)))
				{
					final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawakenNoFee.html");
					html.replace("%price%", REAWAKEN_PRICE[index]);
					player.sendPacket(html);
					break;
				}
				
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawakenList.html");
				player.sendPacket(html);
				break;
			}
			case "reawaken_SIGEL_GROUP":
			case "reawaken_TYRR_GROUP":
			case "reawaken_OTHELL_GROUP":
			case "reawaken_YUL_GROUP":
			case "reawaken_FEOH_GROUP":
			case "reawaken_ISS_GROUP":
			case "reawaken_WYNN_GROUP":
			case "reawaken_AEORE_GROUP":
			{
				final CategoryType cType = CategoryType.valueOf(event.replace("reawaken_", ""));
				
				if (cType == null)
				{
					_log.log(Level.WARNING, getClass().getSimpleName() + ": Cannot parse CategoryType, event: " + event);
				}
				
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawakenClassList.html");
				
				for (PlayerClass dualClasses : getDualClasses(player, cType))
				{
					if (dualClasses != null)
					{
						sb.append("<button value=\"" + ClassListData.getInstance().getClass(dualClasses.ordinal()).getClassName() + "\" action=\"bypass -h menu_select?ask=5&reply=" + dualClasses.ordinal() + "\" width=\"200\" height=\"31\" back=\"L2UI_CT1.HtmlWnd_DF_Awake_Down\" fore=\"L2UI_CT1.HtmlWnd_DF_Awake\"><br>");
					}
				}
				html.replace("%dualclassList%", sb.toString());
				player.sendPacket(html);
				break;
			}
		}
		return htmltext;
	}
	
	@RegisterEvent(EventType.ON_NPC_MENU_SELECT)
	@RegisterType(ListenerRegisterType.NPC)
	@Id(RAINA)
	public final void OnNpcMenuSelect(OnNpcMenuSelect event)
	{
		final L2PcInstance player = event.getTalker();
		final L2Npc npc = event.getNpc();
		final int ask = event.getAsk();
		
		switch (ask)
		{
			case 0: // Add subclass confirm menu
			{
				final int classId = event.getReply();
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addConfirm.html");
				
				if (!isValidNewSubClass(player, classId))
				{
					return;
				}
				
				final int npcStringId = 11170000 + classId;
				sb.append("<fstring p1=\"1\" p2=\"" + classId + "\">" + npcStringId + "</fstring>");
				html.replace("%confirmButton%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case 1: // Add subclass
			{
				final int classId = event.getReply();
				if (!isValidNewSubClass(player, classId))
				{
					return;
				}
				
				if (!player.addSubClass(classId, player.getTotalSubClasses() + 1, false))
				{
					return;
				}
				
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addSuccess.html");
				player.setActiveClass(player.getTotalSubClasses());
				player.sendPacket(SystemMessageId.THE_NEW_SUBCLASS_HAS_BEEN_ADDED);
				player.sendPacket(html);
				break;
			}
			case 2: // Remove (change) subclass list
			{
				final int subclassIndex = event.getReply();
				final Set<PlayerClass> availSubs = getAvailableSubClasses(player);
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "removeSubclassList.html");
				
				if ((availSubs == null) || availSubs.isEmpty())
				{
					return;
				}
				
				for (PlayerClass subClass : availSubs)
				{
					if (subClass != null)
					{
						final int classId = subClass.ordinal();
						final int npcStringId = 11170000 + classId;
						sb.append("<fstring p1=\"3\" p2=\"" + classId + "\">" + npcStringId + "</fstring>");
					}
				}
				npc.getVariables().set("SUBCLASS_INDEX_" + player.getObjectId(), subclassIndex);
				html.replace("%subclassList%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case 3: // Remove (change) subclass confirm menu
			{
				final int classId = event.getReply();
				final int classIndex = npc.getVariables().getInt("SUBCLASS_INDEX_" + player.getObjectId(), -1);
				if (classIndex < 0)
				{
					return;
				}
				
				final StringBuilder sb = new StringBuilder();
				final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addConfirm2.html");
				final int npcStringId = 11170000 + classId;
				sb.append("<fstring p1=\"4\" p2=\"" + classId + "\">" + npcStringId + "</fstring>");
				html.replace("%confirmButton%", sb.toString());
				player.sendPacket(html);
				break;
			}
			case 4: // Remove (change) subclass
			{
				final int classId = event.getReply();
				final int classIndex = npc.getVariables().getInt("SUBCLASS_INDEX_" + player.getObjectId(), -1);
				if (classIndex < 0)
				{
					return;
				}
				
				if (player.modifySubClass(classIndex, classId, false))
				{
					player.abortCast();
					player.stopAllEffectsExceptThoseThatLastThroughDeath();
					player.stopAllEffectsNotStayOnSubclassChange();
					player.stopCubics();
					player.setActiveClass(classIndex);
					
					final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addSuccess.html");
					
					player.sendPacket(html);
					player.sendPacket(SystemMessageId.THE_NEW_SUBCLASS_HAS_BEEN_ADDED);
				}
				break;
			}
			case 5: // Reawaken (change dual class)
			{
				final int classId = event.getReply();
				if (player.isTransformed() || player.hasSummon() || (!player.hasDualClass() || !player.isDualClassActive() || (player.getClassId().level() != ClassLevel.AWAKEN.ordinal())))
				{
					break;
				}
				
				// Validating classId
				if (!getDualClasses(player, null).contains(PlayerClass.values()[classId]))
				{
					break;
				}
				
				final int index = player.getLevel() > 94 ? REAWAKEN_PRICE.length - 1 : player.getLevel() - 85;
				if ((player.getAdena() < REAWAKEN_PRICE[index]) || !hasQuestItems(player, getCloakId(player)))
				{
					final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawakenNoFee.html");
					html.replace("%price%", REAWAKEN_PRICE[index]);
					player.sendPacket(html);
					break;
				}
				
				player.reduceAdena((getClass().getSimpleName() + "_Reawaken"), REAWAKEN_PRICE[index], npc, true);
				takeItems(player, getCloakId(player), 1);
				
				final int classIndex = player.getClassIndex();
				if (player.modifySubClass(classIndex, classId, true))
				{
					player.abortCast();
					player.stopAllEffectsExceptThoseThatLastThroughDeath();
					player.stopAllEffectsNotStayOnSubclassChange();
					player.stopCubics();
					player.setActiveClass(classIndex);
					
					final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "reawakenSuccess.html");
					player.sendPacket(html);
					SkillTreesData.getInstance().cleanSkillUponAwakening(player);
					player.sendPacket(new AcquireSkillList(player));
					player.sendSkillList();
					addPowerItem(player);
				}
				break;
			}
			case 6: // Add dual class for ertheia
			{
				final int classId = event.getReply();
				if (player.isTransformed() || player.hasSummon())
				{
					break;
				}
				
				// Validating classId
				if (!getDualClasses(player, null).contains(PlayerClass.values()[classId]))
				{
					break;
				}
				
				if (player.addSubClass(classId, player.getTotalSubClasses() + 1, true))
				{
					final NpcHtmlMessage html = getNpcHtmlMessage(player, npc, "addSuccess.html");
					player.setActiveClass(player.getTotalSubClasses());
					player.sendPacket(SystemMessageId.THE_NEW_SUBCLASS_HAS_BEEN_ADDED);
					player.sendPacket(html);
					SkillTreesData.getInstance().cleanSkillUponAwakening(player);
					player.sendPacket(new AcquireSkillList(player));
					player.sendSkillList();
					addPowerItem(player);
				}
				break;
			}
		}
	}
	
	private void addPowerItem(L2PcInstance player)
	{
		int itemId = ABELIUS_POWER; // Sigel
		if (player.isInCategory(CategoryType.TYRR_GROUP))
		{
			itemId = SAPYROS_POWER;
		}
		else if (player.isInCategory(CategoryType.OTHELL_GROUP))
		{
			itemId = ASHAGEN_POWER;
		}
		else if (player.isInCategory(CategoryType.YUL_GROUP))
		{
			itemId = CRANIGG_POWER;
		}
		else if (player.isInCategory(CategoryType.FEOH_GROUP))
		{
			itemId = SOLTKREIG_POWER;
		}
		else if (player.isInCategory(CategoryType.ISS_GROUP))
		{
			itemId = NAVIAROPE_POWER;
		}
		else if (player.isInCategory(CategoryType.WYNN_GROUP))
		{
			itemId = LEISTER_POWER;
		}
		else if (player.isInCategory(CategoryType.AEORE_GROUP))
		{
			itemId = LAKCIS_POWER;
		}
		giveItems(player, itemId, 1);
	}
	
	/**
	 * Returns list of available subclasses Base class and already used subclasses removed
	 * @param player
	 * @return
	 */
	private Set<PlayerClass> getAvailableSubClasses(L2PcInstance player)
	{
		final int currentBaseId = player.getBaseClass();
		final ClassId baseCID = ClassId.getClassId(currentBaseId);
		int baseClassId = (baseCID.level() > 2) ? baseCID.getParent().ordinal() : currentBaseId;
		
		final Set<PlayerClass> availSubs = getSubclasses(player, baseClassId);
		
		if ((availSubs != null) && !availSubs.isEmpty())
		{
			for (PlayerClass pclass : availSubs)
			{
				// scan for already used subclasses
				final int availClassId = pclass.ordinal();
				final ClassId cid = ClassId.getClassId(availClassId);
				
				for (SubClass subList : player.getSubClasses().values())
				{
					final ClassId subId = ClassId.getClassId(subList.getClassId());
					
					if (subId.equalsOrChildOf(cid))
					{
						availSubs.remove(cid);
						break;
					}
				}
			}
		}
		return availSubs;
	}
	
	private boolean haveDoneQuest(L2PcInstance player)
	{
		final QuestState qs = player.getQuestState("Q10385_RedThreadOfFate"); // TODO: Replace with class name
		return qs == null ? false : qs.isCompleted();
	}
	
	/**
	 * Check new subclass classId for validity. Base class not added into allowed subclasses.
	 * @param player
	 * @param classId
	 * @return
	 */
	private boolean isValidNewSubClass(L2PcInstance player, int classId)
	{
		final ClassId cid = ClassId.values()[classId];
		ClassId subClassId;
		for (SubClass subList : player.getSubClasses().values())
		{
			subClassId = ClassId.values()[subList.getClassId()];
			
			if (subClassId.equalsOrChildOf(cid))
			{
				return false;
			}
		}
		
		// get player base class
		final int currentBaseId = player.getBaseClass();
		final ClassId baseCID = ClassId.getClassId(currentBaseId);
		
		// we need 2nd occupation ID
		final int baseClassId = baseCID.level() > 2 ? baseCID.getParent().ordinal() : currentBaseId;
		final Set<PlayerClass> availSubs = getSubclasses(player, baseClassId);
		
		if ((availSubs == null) || availSubs.isEmpty())
		{
			return false;
		}
		
		boolean found = false;
		for (PlayerClass pclass : availSubs)
		{
			if (pclass.ordinal() == classId)
			{
				found = true;
				break;
			}
		}
		return found;
	}
	
	private boolean hasAllSubclassLeveled(L2PcInstance player)
	{
		boolean leveled = true;
		
		for (SubClass sub : player.getSubClasses().values())
		{
			if ((sub != null) && (sub.getLevel() < 75))
			{
				leveled = false;
			}
		}
		return leveled;
	}
	
	public final List<PlayerClass> getAvailableDualclasses(L2PcInstance player)
	{
		final List<PlayerClass> dualClasses = new ArrayList<>();
		
		for (PlayerClass playerClass : PlayerClass.values())
		{
			if (!playerClass.isOfRace(Race.ERTHEIA) && playerClass.isOfLevel(ClassLevel.AWAKEN) && (playerClass.ordinal() != player.getClassId().getId()))
			{
				dualClasses.add(playerClass);
			}
		}
		return dualClasses;
	}
	
	private List<PlayerClass> getDualClasses(L2PcInstance player, CategoryType cType)
	{
		final List<PlayerClass> tempList = new ArrayList<>();
		final int baseClassId = player.getBaseClass();
		final int dualClassId = player.getClassId().getId();
		
		for (PlayerClass temp : dualClassList)
		{
			if ((temp.ordinal() != baseClassId) && (temp.ordinal() != dualClassId) && ((cType == null) || CategoryData.getInstance().isInCategory(cType, temp.ordinal())))
			{
				tempList.add(temp);
			}
		}
		return tempList;
	}
	
	public final Set<PlayerClass> getSubclasses(L2PcInstance player, int classId)
	{
		Set<PlayerClass> subclasses = null;
		final PlayerClass pClass = PlayerClass.values()[classId];
		
		if ((pClass.getLevel() == ClassLevel.THIRD) || (pClass.getLevel() == ClassLevel.FOURTH))
		{
			subclasses = EnumSet.copyOf(mainSubclassSet);
			
			subclasses.remove(this);
			
			subclasses.removeAll(PlayerClass.getSet(Race.ERTHEIA, THIRD));
			
			if (player.getRace() == Race.KAMAEL)
			{
				if (player.getAppearance().getSex())
				{
					subclasses.remove(PlayerClass.femaleSoulbreaker);
				}
				else
				{
					subclasses.remove(PlayerClass.maleSoulbreaker);
				}
				
				if (!player.getSubClasses().containsKey(2) || (player.getSubClasses().get(2).getLevel() < 75))
				{
					subclasses.remove(PlayerClass.inspector);
				}
			}
			else
			{
				// Only Kamael can take Kamael classes as subclasses.
				subclasses.removeAll(PlayerClass.getSet(Race.KAMAEL, THIRD));
			}
			
			Set<PlayerClass> unavailableClasses = subclassSetMap.get(this);
			
			if (unavailableClasses != null)
			{
				subclasses.removeAll(unavailableClasses);
			}
		}
		return subclasses;
	}
	
	private NpcHtmlMessage getNpcHtmlMessage(L2PcInstance player, L2Npc npc, String fileName)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());
		html.setHtml(HtmCache.getInstance().getHtm(player.getHtmlPrefix(), "data/scripts/ai/npc/Raina/" + fileName));
		return html;
	}
	
	private int getCloakId(L2PcInstance player)
	{
		CategoryType catType = null;
		
		if (player.isInCategory(CategoryType.SIGEL_GROUP))
		{
			catType = CategoryType.SIGEL_GROUP;
		}
		else if (player.isInCategory(CategoryType.TYRR_GROUP))
		{
			catType = CategoryType.TYRR_GROUP;
		}
		else if (player.isInCategory(CategoryType.OTHELL_GROUP))
		{
			catType = CategoryType.OTHELL_GROUP;
		}
		else if (player.isInCategory(CategoryType.YUL_GROUP))
		{
			catType = CategoryType.YUL_GROUP;
		}
		else if (player.isInCategory(CategoryType.FEOH_GROUP))
		{
			catType = CategoryType.FEOH_GROUP;
		}
		else if (player.isInCategory(CategoryType.ISS_GROUP))
		{
			catType = CategoryType.ISS_GROUP;
		}
		else if (player.isInCategory(CategoryType.WYNN_GROUP))
		{
			catType = CategoryType.WYNN_GROUP;
		}
		else if (player.isInCategory(CategoryType.AEORE_GROUP))
		{
			catType = CategoryType.AEORE_GROUP;
		}
		return classCloak.get(catType);
	}
	
	public static void main(String[] args)
	{
		new Raina();
	}
}