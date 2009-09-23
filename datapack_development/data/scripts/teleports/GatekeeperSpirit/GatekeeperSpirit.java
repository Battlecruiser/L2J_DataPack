/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package teleports.GatekeeperSpirit;

import net.sf.l2j.gameserver.SevenSigns;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;

public class GatekeeperSpirit extends Quest
{
	private final static int npcId = 31111;

	public GatekeeperSpirit(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(npcId);
		addFirstTalkId(npcId);
		addTalkId(npcId);
	}

	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		int playerCabal = SevenSigns.getInstance().getPlayerCabal(player);
		int sealAvariceOwner = SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_AVARICE);
		int compWinner = SevenSigns.getInstance().getCabalHighestScore();

		if (playerCabal == sealAvariceOwner && playerCabal == compWinner)
		{
			switch (sealAvariceOwner)
			{
			case SevenSigns.CABAL_DAWN:
				htmltext = "dawn.htm";
				break;
			case SevenSigns.CABAL_DUSK:
				htmltext = "dusk.htm";
				break;
			case SevenSigns.CABAL_NULL:
				npc.showChatWindow(player);
				break;
			}
		}
		else
			npc.showChatWindow(player);

		return htmltext;
	}

	public static void main(String[] args)
	{
		new GatekeeperSpirit(-1, "GatekeeperSpirit", "teleports");
	}
}