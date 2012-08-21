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
package custom.FortuneTelling;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * Fortune Telling AI.<br>
 * Original Jython script by Kerberos.
 * @authors Nyaran
 */
public class FortuneTelling extends Quest
{
	private static final String qn = "FortuneTelling";

	private static final int ADENA = 57;
	private static final int COST = 1000;

	private static final int NPC = 32616;

	private static final String[] FORTUNE =
	{
			"What you\'ve endured will return as a benefit.",
			"The dragon now acquires an eagle\'s wings.",
			"Be warned as you may be overwhelmed by surroundings if you lack a clear opinion.",
			"A new trial or start may be successful as luck shadows changes.",
			"You may feel nervous and anxious because of unfavorable situations.",
			"You may meet the person you\'ve longed to see.",
			"You may meet many new people but it will be difficult to find a perfect person who wins your heart.",
			"Good fortune and opportunity may lie ahead as if one's born in a golden spoon.",
			"Be confident and act tenaciously at all times. You may be able to accomplish to perfection during somewhat unstable situations.",
			"There may be an occasion where you are consoled by people.",
			"Be independent at all times.",
			"Do not loosen up with your precautions.",
			"Closely observe people who pass by since you may meet a precious person who can help you.",
			"Listen to the advice that's given to you with a humble attitude.",
			"Focus on networking with like-minded people. They may join you for a big mission in the future.",
			"Staying busy rather than being stationary will help.",
			"You may lose your drive and feel lost.",
			"People around you will encourage your every task in the future.",
			"Be kind to and care for those close to you, they may help in the future.",
			"Your ambition and dream will come true.",
			"Your value will shine as your potential is finally realized.",
			"If you keep smiling without despair, people will come to trust you and offer help.",
			"There may be a little loss, but think of it as an investment for yourself.",
			"The difficult situations will turn to hope with unforeseen help.",
			"Impatience may lie ahead as the situation is unfavorable.",
			"Be responsible with your tasks but do not hesitate to ask for colleagues\' help.",
			"You may fall in danger each time when acting upon improvisation.",
			"A determined act after prepared research will attract people.",
			"A rest will promise a bigger development.",
			"You will be rewarded for your efforts and accomplishments.",
			"There are many things to consider after encountering hindrances.",
			"Consider other\'s situations and treat them sincerely at all times.",
			"A comparison to others may be helpful.",
			"Be cautious to control emotions as temptations are nearby.",
			"Momentarily delay an important decision.",
			"Be confident and act tenaciously at all times. You may be able to accomplish to perfection during somewhat unstable situations.",
			"Visiting a place you\'ve never been before may bring luck.",
			"What used to be well managed may stumble one after another.",
			"Your steady pursuit of new information and staying ahead of others will raise your value.",
			"Being neutral is a good way to go, but clarity may be helpful contrary to your hesitance.",
			"Skillful evasion is needed when dealing with people who pick fights as a disaster may arise from it.",
			"Small things make up big things so even value trivial matters.",
			"Bigger mistakes will be on the road if you fail to correct a small mistake.",
			"Momentarily delay an important decision.",
			"A remedy is on its way for a serious illness."
	};

	public FortuneTelling(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NPC);
		addTalkId(NPC);
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		String PARENT_DIR = "data/scripts/custom/FortuneTelling/";
		
		if (st == null)
			return null;

		if (st.getQuestItemsCount(ADENA) < COST)
			html.setFile(player.getHtmlPrefix(), PARENT_DIR + "lowadena.htm");
		else
		{
			st.takeItems(ADENA, COST);
			html.setFile(player.getHtmlPrefix(), PARENT_DIR + "fortune.htm");
			html.replace("%fortune%", FORTUNE[getRandom(FORTUNE.length)]);
		}
		st.exitQuest(true);
		player.sendPacket(html);
		return null;
	}

	public static void main(String args[])
	{
		new FortuneTelling(-1, qn, "custom");
	}
}
