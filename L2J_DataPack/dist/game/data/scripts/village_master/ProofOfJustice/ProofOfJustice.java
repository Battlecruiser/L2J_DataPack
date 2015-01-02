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
package village_master.ProofOfJustice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.MultisellData;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;

/**
 * Proof Of Justice implementation.
 * @author St3eT
 */
public final class ProofOfJustice extends AbstractNpcAI
{
	// Misc
	private static final Map<Integer, List<ClassId>> CLASSLIST = new HashMap<>();
	static
	{
		CLASSLIST.put(30505, Arrays.asList(ClassId.DESTROYER, ClassId.TYRANT, ClassId.OVERLORD, ClassId.WARCRYER));
		CLASSLIST.put(30504, Arrays.asList(ClassId.BOUNTY_HUNTER, ClassId.WARSMITH));
		CLASSLIST.put(30288, Arrays.asList(ClassId.GLADIATOR, ClassId.WARLORD, ClassId.PALADIN, ClassId.DARK_AVENGER, ClassId.TREASURE_HUNTER, ClassId.HAWKEYE));
		CLASSLIST.put(30297, Arrays.asList(ClassId.SHILLIEN_KNIGHT, ClassId.BLADEDANCER, ClassId.ABYSS_WALKER, ClassId.PHANTOM_RANGER, ClassId.SPELLHOWLER, ClassId.PHANTOM_SUMMONER, ClassId.SHILLIEN_ELDER));
		CLASSLIST.put(30158, Arrays.asList(ClassId.SPELLSINGER, ClassId.ELEMENTAL_SUMMONER, ClassId.ELDER));
		CLASSLIST.put(30155, Arrays.asList(ClassId.TEMPLE_KNIGHT, ClassId.SWORDSINGER, ClassId.PLAINS_WALKER, ClassId.SILVER_RANGER));
		CLASSLIST.put(30289, Arrays.asList(ClassId.SORCERER, ClassId.NECROMANCER, ClassId.WARLOCK, ClassId.BISHOP, ClassId.PROPHET));
		CLASSLIST.put(32196, Arrays.asList(ClassId.BERSERKER, ClassId.MALE_SOULBREAKER, ClassId.FEMALE_SOULBREAKER, ClassId.ARBALESTER));
	}
	
	private ProofOfJustice()
	{
		super(ProofOfJustice.class.getSimpleName(), "village_master");
		addStartNpc(CLASSLIST.keySet());
		addTalkId(CLASSLIST.keySet());
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		if (talker.getClassId().level() < 2)
		{
			return npc.getId() + "-noclass.html";
		}
		else if (!CLASSLIST.get(npc.getId()).contains(talker.getClassId()))
		{
			return npc.getId() + "-no.html";
		}
		MultisellData.getInstance().separateAndSend(718, talker, npc, false);
		return super.onTalk(npc, talker);
	}
	
	public static void main(String[] args)
	{
		new ProofOfJustice();
	}
}