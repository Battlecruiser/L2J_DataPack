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
package hellbound.Solomon;

import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @author DS
 */
public class Solomon extends Quest
{
	private static final int SOLOMON = 32355;
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (HellboundManager.getInstance().getLevel() == 5)
		{
			return "32355-01.htm";
		}
		else if (HellboundManager.getInstance().getLevel() > 5)
		{
			return "32355-01a.htm";
		}
		return null;
	}
	
	public Solomon(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(SOLOMON);
	}
	
	public static void main(String[] args)
	{
		new Solomon(-1, "Solomon", "hellbound");
	}
}
