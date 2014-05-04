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
package custom.events.TvT.TvTManager;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.instancemanager.AntiFeedManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.model.olympiad.OlympiadManager;

/**
 * TvT Manager AI.
 * @author Zoey76
 */
public final class TvTManager extends AbstractNpcAI
{
	private static final int MANAGER_ID = 70010;
	
	public TvTManager()
	{
		super(TvTManager.class.getSimpleName(), "custom/events/TvT");
		addFirstTalkId(MANAGER_ID);
		addTalkId(MANAGER_ID);
		addStartNpc(MANAGER_ID);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((player == null) || !TvTEvent.isParticipating())
		{
			return super.onAdvEvent(event, npc, player);
		}
		
		String htmltext = null;
		switch (event)
		{
			case "join":
			{
				int playerLevel = player.getLevel();
				final int team1Count = TvTEvent.getTeamsPlayerCounts()[0];
				final int team2Count = TvTEvent.getTeamsPlayerCounts()[1];
				if (player.isCursedWeaponEquipped())
				{
					htmltext = "CursedWeaponEquipped.htm";
				}
				else if (OlympiadManager.getInstance().isRegistered(player))
				{
					htmltext = "Olympiad.htm";
				}
				else if (player.getKarma() > 0)
				{
					htmltext = "Karma.htm";
				}
				else if ((playerLevel < Config.TVT_EVENT_MIN_LVL) || (playerLevel > Config.TVT_EVENT_MAX_LVL))
				{
					htmltext = getHtm(player.getHtmlPrefix(), "Level.htm");
					htmltext = htmltext.replaceAll("%min%", String.valueOf(Config.TVT_EVENT_MIN_LVL));
					htmltext = htmltext.replaceAll("%max%", String.valueOf(Config.TVT_EVENT_MAX_LVL));
				}
				else if ((team1Count == Config.TVT_EVENT_MAX_PLAYERS_IN_TEAMS) && (team2Count == Config.TVT_EVENT_MAX_PLAYERS_IN_TEAMS))
				{
					htmltext = getHtm(player.getHtmlPrefix(), "TeamsFull.htm");
					htmltext = htmltext.replaceAll("%max%", String.valueOf(Config.TVT_EVENT_MAX_PLAYERS_IN_TEAMS));
				}
				else if ((Config.TVT_EVENT_MAX_PARTICIPANTS_PER_IP > 0) && !AntiFeedManager.getInstance().tryAddPlayer(AntiFeedManager.TVT_ID, player, Config.TVT_EVENT_MAX_PARTICIPANTS_PER_IP))
				{
					htmltext = getHtm(player.getHtmlPrefix(), "IPRestriction.htm");
					htmltext = htmltext.replaceAll("%max%", String.valueOf(AntiFeedManager.getInstance().getLimit(player, Config.TVT_EVENT_MAX_PARTICIPANTS_PER_IP)));
				}
				else if (TvTEvent.needParticipationFee() && !TvTEvent.hasParticipationFee(player))
				{
					htmltext = getHtm(player.getHtmlPrefix(), "ParticipationFee.htm");
					htmltext = htmltext.replaceAll("%fee%", TvTEvent.getParticipationFee());
				}
				else if (TvTEvent.addParticipant(player))
				{
					htmltext = "Registered.htm";
				}
				break;
			}
			case "remove":
			{
				TvTEvent.removeParticipant(player.getObjectId());
				if (Config.TVT_EVENT_MAX_PARTICIPANTS_PER_IP > 0)
				{
					AntiFeedManager.getInstance().removePlayer(AntiFeedManager.TVT_ID, player);
				}
				htmltext = "Unregistered.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (TvTEvent.isParticipating())
		{
			final boolean isParticipant = TvTEvent.isPlayerParticipant(player.getObjectId());
			int[] teamsPlayerCounts = TvTEvent.getTeamsPlayerCounts();
			htmltext = getHtm(player.getHtmlPrefix(), (!isParticipant ? "Participation.htm" : "RemoveParticipation.htm"));
			htmltext = htmltext.replaceAll("%objectId%", String.valueOf(npc.getObjectId()));
			htmltext = htmltext.replaceAll("%team1name%", Config.TVT_EVENT_TEAM_1_NAME);
			htmltext = htmltext.replaceAll("%team1playercount%", String.valueOf(teamsPlayerCounts[0]));
			htmltext = htmltext.replaceAll("%team2name%", Config.TVT_EVENT_TEAM_2_NAME);
			htmltext = htmltext.replaceAll("%team2playercount%", String.valueOf(teamsPlayerCounts[1]));
			htmltext = htmltext.replaceAll("%playercount%", String.valueOf(teamsPlayerCounts[0] + teamsPlayerCounts[1]));
			
			if (!isParticipant)
			{
				htmltext = htmltext.replaceAll("%fee%", TvTEvent.getParticipationFee());
			}
		}
		else if (TvTEvent.isStarting() || TvTEvent.isStarted())
		{
			int[] teamsPlayerCounts = TvTEvent.getTeamsPlayerCounts();
			int[] teamsPointsCounts = TvTEvent.getTeamsPoints();
			htmltext = getHtm(player.getHtmlPrefix(), "Status.htm");
			htmltext = htmltext.replaceAll("%team1name%", Config.TVT_EVENT_TEAM_1_NAME);
			htmltext = htmltext.replaceAll("%team1playercount%", String.valueOf(teamsPlayerCounts[0]));
			htmltext = htmltext.replaceAll("%team1points%", String.valueOf(teamsPointsCounts[0]));
			htmltext = htmltext.replaceAll("%team2name%", Config.TVT_EVENT_TEAM_2_NAME);
			htmltext = htmltext.replaceAll("%team2playercount%", String.valueOf(teamsPlayerCounts[1]));
			htmltext = htmltext.replaceAll("%team2points%", String.valueOf(teamsPointsCounts[1]));
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new TvTManager();
	}
}
