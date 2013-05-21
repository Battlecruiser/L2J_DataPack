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
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

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
	private static final Map<Integer, Integer> CLASSES = new HashMap<>();
	private static final Map<Integer, Integer> ABILITY_CERTIFICATES = new HashMap<>();
	private static final Map<Integer, Integer> TRANSFORMATION_SEALBOOKS = new HashMap<>();
	static
	{
		// Warrior classes
		CLASSES.put(0x02, 0); // Gladiator (Human)
		CLASSES.put(0x03, 0); // Warlord (Human)
		CLASSES.put(0x2E, 0); // Destroyer (Orc)
		CLASSES.put(0x30, 0); // Tyrant (Orc)
		CLASSES.put(0x37, 0); // Bounty Hunter (Dwarf)
		CLASSES.put(0x38, 0); // Artisan (Dwarf)
		CLASSES.put(0x58, 0); // Duelist (Human)
		CLASSES.put(0x59, 0); // Dreadnought (Human)
		CLASSES.put(0x71, 0); // Titan (Orc)
		CLASSES.put(0x72, 0); // Grand Khavatari (Orc)
		CLASSES.put(0x75, 0); // Fortune Seeker (Dwarf)
		CLASSES.put(0x76, 0); // Maestro (Dwarf)
		CLASSES.put(0x7F, 0); // Berserker (Kamael)
		CLASSES.put(0x80, 0); // Male Soulbreaker (Kamael)
		CLASSES.put(0x81, 0); // Female Soulbreaker (Kamael)
		CLASSES.put(0x83, 0); // Doombringer (Kamael)
		CLASSES.put(0x84, 0); // Male Soulhound (Kamael)
		CLASSES.put(0x85, 0); // Female Soulhound (Kamael)
		// Rogue classes
		CLASSES.put(0x08, 1); // Treasure Hunter (Human)
		CLASSES.put(0x09, 1); // Hawkeye (Human)
		CLASSES.put(0x17, 1); // Plainswalker (Elf)
		CLASSES.put(0x18, 1); // Silver Ranger (Elf)
		CLASSES.put(0x24, 1); // Abyss Walker (Dark Elf)
		CLASSES.put(0x25, 1); // Phantom Ranger (Dark Elf)
		CLASSES.put(0x5C, 1); // Sagittarius (Human)
		CLASSES.put(0x5D, 1); // Adventurer (Human)
		CLASSES.put(0x65, 1); // Wind Rider (Elf)
		CLASSES.put(0x66, 1); // Moonlight Sentinel (Elf)
		CLASSES.put(0x6C, 1); // Ghost Hunter (Dark Elf)
		CLASSES.put(0x6D, 1); // Ghost Sentinel (Dark Elf)
		CLASSES.put(0x82, 1); // Arbalester (Kamael)
		CLASSES.put(0x86, 1); // Trickster (Kamael)
		// Knight classes
		CLASSES.put(0x05, 2); // Paladin (Human)
		CLASSES.put(0x06, 2); // Dark Avenger (Human)
		CLASSES.put(0x14, 2); // Temple Knight (Elf)
		CLASSES.put(0x21, 2); // Shillien Knight (Dark Elf)
		CLASSES.put(0x5A, 2); // Phoenix Knight (Human)
		CLASSES.put(0x5B, 2); // Hell Knight (Human)
		CLASSES.put(0x63, 2); // Eva Templar (Elf)
		CLASSES.put(0x6A, 2); // Shillien Templar (Dark Elf)
		// Summoner classes
		CLASSES.put(0x0E, 3); // Warlock (Human)
		CLASSES.put(0x1C, 3); // Elemental Summoner (Elf)
		CLASSES.put(0x29, 3); // Phantom Summoner (Dark Elf)
		CLASSES.put(0x60, 3); // Arcana Lord (Human)
		CLASSES.put(0x68, 3); // Elemental Master (Elf)
		CLASSES.put(0x6F, 3); // Spectral Master (Dark Elf)
		// Wizard classes
		CLASSES.put(0x0C, 4); // Sorceror (Human)
		CLASSES.put(0x0D, 4); // Necromancer (Human)
		CLASSES.put(0x1B, 4); // Spellsinger (Elf)
		CLASSES.put(0x28, 4); // Spellhowler (Dark Elf)
		CLASSES.put(0x5E, 4); // Archmage (Human)
		CLASSES.put(0x5F, 4); // Soultaker (Human)
		CLASSES.put(0x67, 4); // Mystic Muse (Elf)
		CLASSES.put(0x6E, 4); // Storm Screamer (Dark Elf)
		// Healer classes
		CLASSES.put(0x10, 5); // Bishop (Human)
		CLASSES.put(0x1E, 5); // Elder (Elf)
		CLASSES.put(0x2B, 5); // Shillen Elder (Dark Elf)
		CLASSES.put(0x61, 5); // Cardinal (Human)
		CLASSES.put(0x69, 5); // Eva Saint (Elf)
		CLASSES.put(0x70, 5); // Shillien Saint (Dark Elf)
		// Enchanter classes
		CLASSES.put(0x11, 6); // Prophet (Human)
		CLASSES.put(0x15, 6); // Sword Singer (Elf)
		CLASSES.put(0x22, 6); // Bladedancer (Dark Elf)
		CLASSES.put(0x33, 6); // Overlord (Orc)
		CLASSES.put(0x34, 6); // Warcryer (Orc)
		CLASSES.put(0x62, 6); // Hierophant (Human)
		CLASSES.put(0x64, 6); // Sword Muse (Elf)
		CLASSES.put(0x6B, 6); // Spectral Dancer (Dark Elf)
		CLASSES.put(0x73, 6); // Dominator (orc)
		CLASSES.put(0x74, 6); // Doomcryer (Orc)
		CLASSES.put(0x87, 6); // Inspector (Kamael)
		CLASSES.put(0x88, 6); // Judicator (Kamael)
		
		ABILITY_CERTIFICATES.put(0, 10281); // Certificate - Warrior Ability
		ABILITY_CERTIFICATES.put(1, 10282); // Certificate - Knight Ability
		ABILITY_CERTIFICATES.put(2, 10283); // Certificate - Rogue Ability
		ABILITY_CERTIFICATES.put(3, 10287); // Certificate - Wizard Ability
		ABILITY_CERTIFICATES.put(4, 10284); // Certificate - Healer Ability
		ABILITY_CERTIFICATES.put(5, 10286); // Certificate - Summoner Ability
		ABILITY_CERTIFICATES.put(6, 10285); // Certificate - Enchanter Ability
		
		TRANSFORMATION_SEALBOOKS.put(0, 10289); // Transformation Sealbook: Divine Warrior
		TRANSFORMATION_SEALBOOKS.put(1, 10288); // Transformation Sealbook: Divine Knight
		TRANSFORMATION_SEALBOOKS.put(2, 10290); // Transformation Sealbook: Divine Rogue
		TRANSFORMATION_SEALBOOKS.put(3, 10293); // Transformation Sealbook: Divine Enchanter
		TRANSFORMATION_SEALBOOKS.put(4, 10292); // Transformation Sealbook: Divine Wizard
		TRANSFORMATION_SEALBOOKS.put(5, 10294); // Transformation Sealbook: Divine Summoner
		TRANSFORMATION_SEALBOOKS.put(6, 10291); // Transformation Sealbook: Divine Healer
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
			htmltext = htmltext.replace("%class%", String.valueOf(ClassListData.getInstance().getClass(player.getActiveClass()).getEscapedClientCode()));
		}
		if (levelToReplace != null)
		{
			htmltext = htmltext.replace("%level%", levelToReplace);
		}
		return htmltext;
	}
	
	private static int getClassIndex(L2PcInstance player)
	{
		Integer tmp = CLASSES.get(player.getClassId().getId());
		if (tmp == null)
		{
			return -1;
		}
		return tmp;
	}
	
	private String doCertification(L2PcInstance player, QuestState qs, String variable, Integer itemId, int level)
	{
		if (itemId == null)
		{
			return null;
		}
		
		String htmltext;
		String tmp = variable + level + "-" + player.getClassIndex();
		String globalVariable = qs.getGlobalQuestVar(variable);
		
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
			giveItems(player, itemId, 1);
			qs.saveGlobalQuestVar(tmp, String.valueOf(itemId));
			htmltext = "GetAbility.html";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new SubclassCertification();
	}
}
