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
package ai.individual;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.network.serverpackets.SpecialCamera;

/**
 * DrChaos' AI.
 * @author Kerberos
 */
public class DrChaos extends Quest
{
	private static final int DR_CHAOS = 32033;
	private static final int STRANGE_MACHINE = 32032;
	private static final int CHAOS_GOLEM = 25703;
	private static boolean _IsGolemSpawned;
	
	private static final Location PLAYER_TELEPORT = new Location(94832, -112624, -3304);
	private static final Location NPC_LOCATION = new Location(-113091, -243942, -15536);
	
	private DrChaos(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(DR_CHAOS);
		_IsGolemSpawned = false;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("1"))
		{
			L2Npc machine = null;
			for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(STRANGE_MACHINE))
			{
				if (spawn != null)
				{
					machine = spawn.getLastSpawn();
				}
			}
			if (machine != null)
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, machine);
				machine.broadcastPacket(new SpecialCamera(machine.getObjectId(), 1, -200, 15, 10000, 20000, 0, 0, 1, 0));
			}
			else
			{
				// print "Dr Chaos AI: problem finding Strange Machine (npcid = "+STRANGE_MACHINE+"). Error: not spawned!"
				startQuestTimer("2", 2000, npc, player);
			}
			startQuestTimer("3", 10000, npc, player);
		}
		else if (event.equalsIgnoreCase("2"))
		{
			npc.broadcastSocialAction(3);
		}
		else if (event.equalsIgnoreCase("3"))
		{
			npc.broadcastPacket(new SpecialCamera(npc.getObjectId(), 1, -150, 10, 3000, 20000, 0, 0, 1, 0));
			startQuestTimer("4", 2500, npc, player);
		}
		else if (event.equalsIgnoreCase("4"))
		{
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(96055, -110759, -3312, 0));
			startQuestTimer("5", 2000, npc, player);
		}
		else if (event.equalsIgnoreCase("5"))
		{
			player.teleToLocation(PLAYER_TELEPORT);
			npc.teleToLocation(NPC_LOCATION);
			if (!_IsGolemSpawned)
			{
				L2Npc golem = addSpawn(CHAOS_GOLEM, 94640, -112496, -3336, 0, false, 0);
				_IsGolemSpawned = true;
				startQuestTimer("6", 1000, golem, player);
				player.sendPacket(new PlaySound(1, "Rm03_A", 0, 0, 0, 0, 0));
			}
		}
		else if (event.equalsIgnoreCase("6"))
		{
			npc.broadcastPacket(new SpecialCamera(npc.getObjectId(), 30, -200, 20, 6000, 8000, 0, 0, 1, 0));
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getId() == DR_CHAOS)
		{
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(96323, -110914, -3328, 0));
			this.startQuestTimer("1", 3000, npc, player);
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		new DrChaos(-1, "Doctor Chaos", "ai");
	}
}
