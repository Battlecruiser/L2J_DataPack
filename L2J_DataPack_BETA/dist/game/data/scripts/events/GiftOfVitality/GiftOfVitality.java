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
package events.GiftOfVitality;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Gift of Vitality event AI.
 * @author Gnacik
 */
public class GiftOfVitality extends LongTimeEvent
{
	// Reuse between buffs
	private static final int HOURS = 5;
	// NPC
	private static final int JACK = 4306;
	
	public GiftOfVitality(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(JACK);
		addFirstTalkId(JACK);
		addTalkId(JACK);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("vitality"))
		{
			long _reuse = 0;
			String _streuse = st.get("reuse");
			if (_streuse != null)
			{
				_reuse = Long.parseLong(_streuse);
			}
			if (_reuse > System.currentTimeMillis())
			{
				long remainingTime = (_reuse - System.currentTimeMillis()) / 1000;
				int hours = (int) (remainingTime / 3600);
				int minutes = (int) ((remainingTime % 3600) / 60);
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.AVAILABLE_AFTER_S1_S2_HOURS_S3_MINUTES);
				sm.addSkillName(23179);
				sm.addNumber(hours);
				sm.addNumber(minutes);
				player.sendPacket(sm);
				htmltext = "4306-notime.htm";
			}
			else
			{
				npc.setTarget(player);
				// Gift of Vitality
				npc.doCast(SkillTable.getInstance().getInfo(23179, 1));
				st.setState(State.STARTED);
				st.set("reuse", String.valueOf(System.currentTimeMillis() + (HOURS * 3600000)));
				htmltext = "4306-okvitality.htm";
			}
		}
		else if (event.equalsIgnoreCase("memories_player"))
		{
			if (player.getLevel() < 76)
			{
				htmltext = "4306-nolevel.htm";
			}
			else
			{
				npc.setTarget(player);
				npc.doCast(SkillTable.getInstance().getInfo(5627, 1)); // Wind Walk
				npc.doCast(SkillTable.getInstance().getInfo(5628, 1)); // Shield
				npc.doCast(SkillTable.getInstance().getInfo(5637, 1)); // Magic Barrier
				if (player.isMageClass())
				{
					npc.doCast(SkillTable.getInstance().getInfo(5633, 1)); // Bless the Soul
					npc.doCast(SkillTable.getInstance().getInfo(5634, 1)); // Acumen
					npc.doCast(SkillTable.getInstance().getInfo(5635, 1)); // Concentration
					npc.doCast(SkillTable.getInstance().getInfo(5636, 1)); // Empower
				}
				else
				{
					npc.doCast(SkillTable.getInstance().getInfo(5629, 1)); // Bless the Body
					npc.doCast(SkillTable.getInstance().getInfo(5630, 1)); // Vampiric Rage
					npc.doCast(SkillTable.getInstance().getInfo(5631, 1)); // Regeneration
					npc.doCast(SkillTable.getInstance().getInfo(5632, 1)); // Haste
				}
				htmltext = "4306-okbuff.htm";
			}
		}
		else if (event.equalsIgnoreCase("memories_summon"))
		{
			if (player.getLevel() < 76)
			{
				htmltext = "4306-nolevel.htm";
			}
			else if (!player.hasSummon() || !player.getSummon().isServitor())
			{
				htmltext = "4306-nosummon.htm";
			}
			else
			{
				npc.setTarget(player.getSummon());
				npc.doCast(SkillTable.getInstance().getInfo(5627, 1)); // Wind Walk
				npc.doCast(SkillTable.getInstance().getInfo(5628, 1)); // Shield
				npc.doCast(SkillTable.getInstance().getInfo(5637, 1)); // Magic Barrier
				npc.doCast(SkillTable.getInstance().getInfo(5629, 1)); // Bless the Body
				npc.doCast(SkillTable.getInstance().getInfo(5633, 1)); // Bless the Soul
				npc.doCast(SkillTable.getInstance().getInfo(5630, 1)); // Vampiric Rage
				npc.doCast(SkillTable.getInstance().getInfo(5634, 1)); // Acumen
				npc.doCast(SkillTable.getInstance().getInfo(5631, 1)); // Regeneration
				npc.doCast(SkillTable.getInstance().getInfo(5635, 1)); // Concentration
				npc.doCast(SkillTable.getInstance().getInfo(5632, 1)); // Haste
				npc.doCast(SkillTable.getInstance().getInfo(5636, 1)); // Empower
				htmltext = "4306-okbuff.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		return "4306.htm";
	}
	
	public static void main(String[] args)
	{
		new GiftOfVitality(-1, GiftOfVitality.class.getSimpleName(), "events");
	}
}
