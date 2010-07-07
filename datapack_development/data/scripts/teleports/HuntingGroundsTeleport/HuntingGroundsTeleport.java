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
package teleports.HuntingGroundsTeleport;

import com.l2jserver.gameserver.SevenSigns;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

public class HuntingGroundsTeleport extends Quest
{
	private final static int[] PRIESTs =
	{
		31078,31079,31080,31081,31082,31083,31084,31085,31086,31087,31088,
		31089,31090,31091,31168,31169,31692,31693,31694,31695,31997,31998
	};

	private static final int[] DAWN_NPCs =
	{
		31078,31079,31080,31081,31082,31083,31084,31168,31692,31694,31997
	};

	public HuntingGroundsTeleport(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int id : PRIESTs)
		{
			addStartNpc(id);
			addTalkId(id);
		}
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getNpcId();
		int playerCabal = SevenSigns.getInstance().getPlayerCabal(player.getObjectId());
		int playerSeal = SevenSigns.getInstance().getPlayerSeal(player.getObjectId());
		int sealOwnerGnosis = SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_GNOSIS);
		boolean periodValidate = SevenSigns.getInstance().isSealValidationPeriod();

		if (playerCabal == SevenSigns.CABAL_NULL)
		{
			if (contains(DAWN_NPCs, npcId))
				htmltext = "dawn_tele-no.htm";
			else
				htmltext = "dusk_tele-no.htm";
		}
		else if (npcId == 31078 || npcId == 31085)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_gludin.htm";
			else
				htmltext = "hg_gludin.htm";
		}
		else if (npcId == 31079 || npcId == 31086)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_gludio.htm";
			else
				htmltext = "hg_gludio.htm";
		}
		else if (npcId == 31080 || npcId == 31087)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_dion.htm";
			else
				htmltext = "hg_dion.htm";
		}
		else if (npcId == 31081 || npcId == 31088)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_giran.htm";
			else
				htmltext = "hg_giran.htm";
		}
		else if (npcId == 31082 || npcId == 31089)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_heine.htm";
			else
				htmltext = "hg_heine.htm";
		}
		else if (npcId == 31083 || npcId == 31090)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_oren.htm";
			else
				htmltext = "hg_oren.htm";
		}
		else if (npcId == 31084 || npcId == 31091)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_aden.htm";
			else
				htmltext = "hg_aden.htm";
		}
		else if (npcId == 31168 || npcId == 31169)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_hw.htm";
			else
				htmltext = "hg_hw.htm";
		}
		else if (npcId == 31692 || npcId == 31693)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_goddard.htm";
			else
				htmltext = "hg_goddard.htm";
		}
		else if (npcId == 31694 || npcId == 31695)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_rune.htm";
			else
				htmltext = "hg_rune.htm";
		}
		else if (npcId == 31997 || npcId == 31998)
		{
			if (periodValidate && playerCabal == sealOwnerGnosis && playerSeal == SevenSigns.SEAL_GNOSIS)
				htmltext = "low_schuttgart.htm";
			else
				htmltext = "hg_schuttgart.htm";
		}

		st.exitQuest(true);
		return htmltext;
	}

	private static boolean contains(int[] array, int obj)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == obj)
				return true;
		}

		return false;
	}

	public static void main(String[] args)
	{
		new HuntingGroundsTeleport(-1, "HuntingGroundsTeleport", "teleports");
	}
}