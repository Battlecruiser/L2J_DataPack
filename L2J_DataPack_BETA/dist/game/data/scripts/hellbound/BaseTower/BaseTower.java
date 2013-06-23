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
package hellbound.BaseTower;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @author GKR
 */
public class BaseTower extends Quest
{
	private static final int GUZEN = 22362;
	private static final int KENDAL = 32301;
	private static final int BODY_DESTROYER = 22363;
	
	private static final Map<Integer, L2PcInstance> BODY_DESTROYER_TARGET_LIST = new FastMap<>();
	
	private static final SkillHolder DEATH_WORD = new SkillHolder(5256, 1);
	
	public BaseTower(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addKillId(GUZEN);
		addKillId(BODY_DESTROYER);
		addFirstTalkId(KENDAL);
		addAggroRangeEnterId(BODY_DESTROYER);
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		ClassId classId = player.getClassId();
		if (classId.equalsOrChildOf(ClassId.hellKnight) || classId.equalsOrChildOf(ClassId.soultaker))
		{
			return "32301-02.htm";
		}
		return "32301-01.htm";
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("close"))
		{
			DoorTable.getInstance().getDoor(20260004).closeMe();
		}
		return null;
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (!BODY_DESTROYER_TARGET_LIST.containsKey(npc.getObjectId()))
		{
			BODY_DESTROYER_TARGET_LIST.put(npc.getObjectId(), player);
			npc.setTarget(player);
			npc.doSimultaneousCast(DEATH_WORD.getSkill());
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		switch (npc.getNpcId())
		{
			case GUZEN:
				// Should Kendal be despawned before Guzen's spawn? Or it will be crowd of Kendal's
				addSpawn(KENDAL, npc.getSpawn().getLocation(), false, npc.getSpawn().getRespawnDelay(), false);
				DoorTable.getInstance().getDoor(20260003).openMe();
				DoorTable.getInstance().getDoor(20260004).openMe();
				startQuestTimer("close", 60000, npc, null, false);
				break;
			case BODY_DESTROYER:
				if (BODY_DESTROYER_TARGET_LIST.containsKey(npc.getObjectId()))
				{
					final L2PcInstance pl = BODY_DESTROYER_TARGET_LIST.get(npc.getObjectId());
					if ((pl != null) && pl.isOnline() && !pl.isDead())
					{
						final L2Effect e = pl.getFirstEffect(DEATH_WORD.getSkill());
						if (e != null)
						{
							e.exit();
						}
					}
					
					BODY_DESTROYER_TARGET_LIST.remove(npc.getObjectId());
				}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new BaseTower(-1, BaseTower.class.getSimpleName(), "hellbound");
	}
}
