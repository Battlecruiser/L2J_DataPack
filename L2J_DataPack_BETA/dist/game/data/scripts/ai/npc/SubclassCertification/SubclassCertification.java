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
package ai.npc.SubclassCertification;

import java.util.HashMap;
import java.util.Map;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.ClassListData;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2VillageMasterInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Subclass certification
 * @author xban1x, jurchiks
 */
public class SubclassCertification extends AbstractNpcAI
{
	// @formatter:off
	private static final int[] NPCS =
	{
		30026, 30031, 30037, 30066, 30070, 30109, 30115, 30120, 30154, 30174,
		30175, 30176, 30187, 30191, 30195, 30288, 30289, 30290, 30297, 30358,
		30373, 30462, 30474, 30498, 30499, 30500, 30503, 30504, 30505, 30508,
		30511, 30512, 30513, 30520, 30525, 30565, 30594, 30595, 30676, 30677,
		30681, 30685, 30687, 30689, 30694, 30699, 30704, 30845, 30847, 30849,
		30854, 30857, 30862, 30865, 30894, 30897, 30900, 30905, 30910, 30913,
		31269, 31272, 31276, 31279, 31285, 31288, 31314, 31317, 31321, 31324,
		31326, 31328, 31331, 31334, 31336, 31755, 31958, 31961, 31965, 31968,
		31974, 31977, 31996, 32092, 32093, 32094, 32095, 32096, 32097, 32098,
		32145, 32146, 32147, 32150, 32153, 32154, 32157, 32158, 32160, 32171,
		32193, 32199, 32202, 32213, 32214, 32221, 32222, 32229, 32230, 32233,
		32234
	};
	// @formatter:on
	private static final int CERTIFICATE_EMERGENT_ABILITY = 10280;
	private static final int CERTIFICATE_MASTER_ABILITY = 10612;
	private static final Map<ClassId, Integer> CLASSES = new HashMap<>();
	private static final Map<Integer, Integer> ABILITY_CERTIFICATES = new HashMap<>();
	private static final Map<Integer, Integer> TRANSFORMATION_SEALBOOKS = new HashMap<>();
	static
	{
		// Warrior classes
		CLASSES.put(ClassId.gladiator, 0);
		CLASSES.put(ClassId.warlord, 0);
		CLASSES.put(ClassId.destroyer, 0);
		CLASSES.put(ClassId.tyrant, 0);
		CLASSES.put(ClassId.bountyHunter, 0);
		CLASSES.put(ClassId.artisan, 0);
		CLASSES.put(ClassId.duelist, 0);
		CLASSES.put(ClassId.dreadnought, 0);
		CLASSES.put(ClassId.titan, 0);
		CLASSES.put(ClassId.grandKhavatari, 0);
		CLASSES.put(ClassId.fortuneSeeker, 0);
		CLASSES.put(ClassId.maestro, 0);
		CLASSES.put(ClassId.berserker, 0);
		CLASSES.put(ClassId.maleSoulbreaker, 0);
		CLASSES.put(ClassId.femaleSoulbreaker, 0);
		CLASSES.put(ClassId.doombringer, 0);
		CLASSES.put(ClassId.maleSoulhound, 0);
		CLASSES.put(ClassId.femaleSoulhound, 0);
		// Rogue classes
		CLASSES.put(ClassId.treasureHunter, 1);
		CLASSES.put(ClassId.hawkeye, 1);
		CLASSES.put(ClassId.plainsWalker, 1);
		CLASSES.put(ClassId.silverRanger, 1);
		CLASSES.put(ClassId.abyssWalker, 1);
		CLASSES.put(ClassId.phantomRanger, 1);
		CLASSES.put(ClassId.sagittarius, 1);
		CLASSES.put(ClassId.adventurer, 1);
		CLASSES.put(ClassId.windRider, 1);
		CLASSES.put(ClassId.moonlightSentinel, 1);
		CLASSES.put(ClassId.ghostHunter, 1);
		CLASSES.put(ClassId.ghostSentinel, 1);
		CLASSES.put(ClassId.arbalester, 1);
		CLASSES.put(ClassId.trickster, 1);
		// Knight classes
		CLASSES.put(ClassId.paladin, 2);
		CLASSES.put(ClassId.darkAvenger, 2);
		CLASSES.put(ClassId.templeKnight, 2);
		CLASSES.put(ClassId.shillienKnight, 2);
		CLASSES.put(ClassId.phoenixKnight, 2);
		CLASSES.put(ClassId.hellKnight, 2);
		CLASSES.put(ClassId.evaTemplar, 2);
		CLASSES.put(ClassId.shillienTemplar, 2);
		// Summoner classes
		CLASSES.put(ClassId.warlock, 3);
		CLASSES.put(ClassId.elementalSummoner, 3);
		CLASSES.put(ClassId.phantomSummoner, 3);
		CLASSES.put(ClassId.arcanaLord, 3);
		CLASSES.put(ClassId.elementalMaster, 3);
		CLASSES.put(ClassId.spectralMaster, 3);
		// Wizard classes
		CLASSES.put(ClassId.sorceror, 4);
		CLASSES.put(ClassId.necromancer, 4);
		CLASSES.put(ClassId.spellsinger, 4);
		CLASSES.put(ClassId.spellhowler, 4);
		CLASSES.put(ClassId.archmage, 4);
		CLASSES.put(ClassId.soultaker, 4);
		CLASSES.put(ClassId.mysticMuse, 4);
		CLASSES.put(ClassId.stormScreamer, 4);
		// Healer classes
		CLASSES.put(ClassId.bishop, 5);
		CLASSES.put(ClassId.elder, 5);
		CLASSES.put(ClassId.shillenElder, 5);
		CLASSES.put(ClassId.cardinal, 5);
		CLASSES.put(ClassId.evaSaint, 5);
		CLASSES.put(ClassId.shillienSaint, 5);
		// Enchanter classes
		CLASSES.put(ClassId.prophet, 6);
		CLASSES.put(ClassId.swordSinger, 6);
		CLASSES.put(ClassId.bladedancer, 6);
		CLASSES.put(ClassId.overlord, 6);
		CLASSES.put(ClassId.warcryer, 6);
		CLASSES.put(ClassId.hierophant, 6);
		CLASSES.put(ClassId.swordMuse, 6);
		CLASSES.put(ClassId.spectralDancer, 6);
		CLASSES.put(ClassId.dominator, 6);
		CLASSES.put(ClassId.doomcryer, 6);
		CLASSES.put(ClassId.inspector, 6);
		CLASSES.put(ClassId.judicator, 6);
		
		ABILITY_CERTIFICATES.put(0, 10281); // Certificate - Warrior Ability
		ABILITY_CERTIFICATES.put(1, 10283); // Certificate - Rogue Ability
		ABILITY_CERTIFICATES.put(2, 10282); // Certificate - Knight Ability
		ABILITY_CERTIFICATES.put(3, 10286); // Certificate - Summoner Ability
		ABILITY_CERTIFICATES.put(4, 10284); // Certificate - Wizard Ability
		ABILITY_CERTIFICATES.put(5, 10285); // Certificate - Healer Ability
		ABILITY_CERTIFICATES.put(6, 10287); // Certificate - Enchanter Ability
		
		TRANSFORMATION_SEALBOOKS.put(0, 10289); // Transformation Sealbook: Divine Warrior
		TRANSFORMATION_SEALBOOKS.put(1, 10290); // Transformation Sealbook: Divine Rogue
		TRANSFORMATION_SEALBOOKS.put(2, 10288); // Transformation Sealbook: Divine Knight
		TRANSFORMATION_SEALBOOKS.put(3, 10294); // Transformation Sealbook: Divine Summoner
		TRANSFORMATION_SEALBOOKS.put(4, 10292); // Transformation Sealbook: Divine Wizard
		TRANSFORMATION_SEALBOOKS.put(5, 10291); // Transformation Sealbook: Divine Healer
		TRANSFORMATION_SEALBOOKS.put(6, 10293); // Transformation Sealbook: Divine Enchanter
	}
	
	private static final int MIN_LVL = 65;
	
	private SubclassCertification()
	{
		super(SubclassCertification.class.getSimpleName(), "ai/npc");
		addStartNpc(NPCS);
		addTalkId(NPCS);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			st.setState(State.STARTED);
			htmltext = "Main.html";
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "GetCertified":
			{
				if (!player.isSubClassActive())
				{
					htmltext = "NotSubclass.html";
				}
				else if (player.getLevel() < MIN_LVL)
				{
					htmltext = "NotMinLevel.html";
				}
				else if (((L2VillageMasterInstance) npc).checkVillageMaster(player.getActiveClass()))
				{
					htmltext = "CertificationList.html";
				}
				else
				{
					htmltext = "WrongVillageMaster.html";
				}
				break;
			}
			case "Obtain65":
			{
				htmltext = replaceHtml(player, "EmergentAbility.html", true, null).replace("%level%", "65").replace("%skilltype%", "common skill").replace("%event%", "lvl65Emergent");
				break;
			}
			case "Obtain70":
			{
				htmltext = replaceHtml(player, "EmergentAbility.html", true, null).replace("%level%", "70").replace("%skilltype%", "common skill").replace("%event%", "lvl70Emergent");
				break;
			}
			case "Obtain75":
			{
				htmltext = replaceHtml(player, "ClassAbility.html", true, null);
				break;
			}
			case "Obtain80":
			{
				htmltext = replaceHtml(player, "EmergentAbility.html", true, null).replace("%level%", "80").replace("%skilltype%", "transformation skill").replace("%event%", "lvl80Class");
				break;
			}
			case "lvl65Emergent":
			{
				htmltext = doCertification(player, st, "EmergentAbility", CERTIFICATE_EMERGENT_ABILITY, 65);
				break;
			}
			case "lvl70Emergent":
			{
				htmltext = doCertification(player, st, "EmergentAbility", CERTIFICATE_EMERGENT_ABILITY, 70);
				break;
			}
			case "lvl75Master":
			{
				htmltext = doCertification(player, st, "ClassAbility", CERTIFICATE_MASTER_ABILITY, 75);
				break;
			}
			case "lvl75Class":
			{
				htmltext = doCertification(player, st, "ClassAbility", ABILITY_CERTIFICATES.get(getClassIndex(player)), 75);
				break;
			}
			case "lvl80Class":
			{
				htmltext = doCertification(player, st, "ClassAbility", TRANSFORMATION_SEALBOOKS.get(getClassIndex(player)), 80);
				break;
			}
			case "Main.html":
			case "Explanation.html":
			case "NotObtain.html":
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	private String replaceHtml(L2PcInstance player, String htmlFile, boolean replaceClass, String levelToReplace)
	{
		String htmltext = getHtm(player.getHtmlPrefix(), htmlFile);
		if (replaceClass)
		{
			htmltext = htmltext.replace("%class%", String.valueOf(ClassListData.getInstance().getClass(player.getActiveClass()).getClientCode()));
		}
		if (levelToReplace != null)
		{
			htmltext = htmltext.replace("%level%", levelToReplace);
		}
		return htmltext;
	}
	
	private static int getClassIndex(L2PcInstance player)
	{
		Integer tmp = CLASSES.get(player.getClassId());
		if (tmp == null)
		{
			return -1;
		}
		return tmp.intValue();
	}
	
	private String doCertification(L2PcInstance player, QuestState qs, String variable, Integer itemId, int level)
	{
		if (itemId == null)
		{
			return null;
		}
		
		String htmltext;
		String tmp = variable + level + "-" + player.getClassIndex();
		String globalVariable = qs.getGlobalQuestVar(tmp);
		
		if (!globalVariable.equals("") && !globalVariable.equals("0"))
		{
			htmltext = "AlreadyReceived.html";
		}
		else if (player.getLevel() < level)
		{
			htmltext = replaceHtml(player, "LowLevel.html", false, Integer.toString(level));
		}
		else
		{
			// Add items to player's inventory
			final L2ItemInstance item = player.getInventory().addItem("Quest", itemId, 1, player, player.getTarget());
			if (item == null)
			{
				return null;
			}
			
			final SystemMessage smsg = SystemMessage.getSystemMessage(SystemMessageId.EARNED_ITEM_S1);
			smsg.addItemName(item);
			player.sendPacket(smsg);
			
			qs.saveGlobalQuestVar(tmp, String.valueOf(item.getObjectId()));
			htmltext = "GetAbility.html";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new SubclassCertification();
	}
}
