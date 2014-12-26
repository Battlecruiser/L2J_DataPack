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
package ai.npc.LaVieEnRose;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ExResponseBeautyList;
import com.l2jserver.gameserver.network.serverpackets.ExResponseResetList;
import com.l2jserver.gameserver.network.serverpackets.ExShowBeautyMenu;

/**
 * @author Sdw
 */
public class LaVieEnRose extends AbstractNpcAI
{
	private static final int LA_VIE_EN_ROSE = 33825;
	
	private LaVieEnRose()
	{
		super(LaVieEnRose.class.getSimpleName(), "ai/npc");
		addStartNpc(LA_VIE_EN_ROSE);
		addTalkId(LA_VIE_EN_ROSE);
		addFirstTalkId(LA_VIE_EN_ROSE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "33825.html":
			case "33825-1.html":
			case "33825-2.html":
			case "33825-help.html":
			{
				htmltext = event;
				break;
			}
			case "restore_appearance":
			{
				if (player.getVariables().hasVariable("visualHairId") || player.getVariables().hasVariable("visualFaceId") || player.getVariables().hasVariable("visualHairColorId"))
				{
					htmltext = "33825-2.html";
				}
				else
				{
					htmltext = "33825-norestore.html";
				}
				break;
			}
			case "beauty-change":
			{
				player.sendPacket(new ExShowBeautyMenu(player, ExShowBeautyMenu.MODIFY_APPEARANCE));
				player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
				break;
			}
			case "beauty-restore":
			{
				player.sendPacket(new ExShowBeautyMenu(player, ExShowBeautyMenu.RESTORE_APPEARANCE));
				player.sendPacket(new ExResponseResetList(player));
				break;
			}
			case "cancel":
			default:
			{
				break;
			}
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new LaVieEnRose();
	}
}