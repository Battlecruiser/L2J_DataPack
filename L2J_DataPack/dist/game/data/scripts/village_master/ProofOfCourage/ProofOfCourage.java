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
package village_master.ProofOfCourage;

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
 * Proof Of Courage implementation.
 * @author St3eT
 */
public final class ProofOfCourage extends AbstractNpcAI
{
	// Misc
	private static final Map<Integer, List<ClassId>> CLASSLIST = new HashMap<>();
	static
	{
		CLASSLIST.put(32146, Arrays.asList(ClassId.TROOPER, ClassId.WARDER));
		CLASSLIST.put(32147, Arrays.asList(ClassId.ELVEN_KNIGHT, ClassId.ELVEN_SCOUT, ClassId.ELVEN_WIZARD, ClassId.ORACLE));
		CLASSLIST.put(32150, Arrays.asList(ClassId.ORC_RAIDER, ClassId.ORC_MONK));
		CLASSLIST.put(32153, Arrays.asList(ClassId.WARRIOR, ClassId.KNIGHT, ClassId.ROGUE, ClassId.WIZARD, ClassId.CLERIC));
		CLASSLIST.put(32157, Arrays.asList(ClassId.SCAVENGER, ClassId.ARTISAN));
		CLASSLIST.put(32160, Arrays.asList(ClassId.PALUS_KNIGHT, ClassId.ASSASSIN, ClassId.DARK_WIZARD, ClassId.SHILLIEN_ORACLE));
	}
	
	private ProofOfCourage()
	{
		super(ProofOfCourage.class.getSimpleName(), "village_master");
		addStartNpc(CLASSLIST.keySet());
		addTalkId(CLASSLIST.keySet());
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		if (talker.getClassId().level() == 0)
		{
			return npc.getId() + "-noclass.html";
		}
		else if (!CLASSLIST.get(npc.getId()).contains(talker.getClassId()))
		{
			return npc.getId() + "-no.html";
		}
		MultisellData.getInstance().separateAndSend(717, talker, npc, false);
		return super.onTalk(npc, talker);
	}
	
	public static void main(String[] args)
	{
		new ProofOfCourage();
	}
}