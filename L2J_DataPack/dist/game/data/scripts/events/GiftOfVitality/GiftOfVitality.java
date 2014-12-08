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
package events.GiftOfVitality;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Gift of Vitality event AI.
 * @author Gnacik, Adry_85
 */
public final class GiftOfVitality extends LongTimeEvent
{
	// NPC
	private static final int STEVE_SHYAGEL = 4306;
	// Skills
	private static final SkillHolder GIFT_OF_VITALITY = new SkillHolder(23179, 1);
	private static final SkillHolder JOY_OF_VITALITY = new SkillHolder(23180, 1);
	
	private static SkillHolder[] FIGHTER_SKILLS =
	{
		new SkillHolder(5627, 1), // Wind Walk
		new SkillHolder(5628, 1), // Shield
		new SkillHolder(5637, 1), // Magic Barrier
		new SkillHolder(5629, 1), // Bless the Body
		new SkillHolder(5630, 1), // Vampiric Rage
		new SkillHolder(5631, 1), // Regeneration
		new SkillHolder(5632, 1), // Haste
	};
	
	private static SkillHolder[] MAGE_SKILLS =
	{
		new SkillHolder(5627, 1), // Wind Walk
		new SkillHolder(5628, 1), // Shield
		new SkillHolder(5637, 1), // Magic Barrier
		new SkillHolder(5633, 1), // Bless the Soul
		new SkillHolder(5634, 1), // Acumen
		new SkillHolder(5635, 1), // Concentration
		new SkillHolder(5636, 1), // Empower
	};
	
	private static SkillHolder[] SERVITOR_SKILLS =
	{
		new SkillHolder(5627, 1), // Wind Walk
		new SkillHolder(5628, 1), // Shield
		new SkillHolder(5637, 1), // Magic Barrier
		new SkillHolder(5629, 1), // Bless the Body
		new SkillHolder(5633, 1), // Bless the Soul
		new SkillHolder(5630, 1), // Vampiric Rage
		new SkillHolder(5634, 1), // Acumen
		new SkillHolder(5631, 1), // Regeneration
		new SkillHolder(5635, 1), // Concentration
		new SkillHolder(5632, 1), // Haste
		new SkillHolder(5636, 1), // Empower
	};
	
	private GiftOfVitality()
	{
		super(GiftOfVitality.class.getSimpleName(), "events");
		addStartNpc(STEVE_SHYAGEL);
		addFirstTalkId(STEVE_SHYAGEL);
		addTalkId(STEVE_SHYAGEL);
	}
	
	// Misc
	private static final int HOURS = 5; // Reuse between buffs
	private static final int MIN_LEVEL = 75;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = getQuestState(player, false);
		
		switch (event)
		{
			case "vitality":
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
					sm.addInt(hours);
					sm.addInt(minutes);
					player.sendPacket(sm);
					htmltext = "4306-notime.htm";
				}
				else
				{
					player.doCast(GIFT_OF_VITALITY.getSkill());
					player.doSimultaneousCast(JOY_OF_VITALITY.getSkill());
					st.setState(State.STARTED);
					st.set("reuse", String.valueOf(System.currentTimeMillis() + (HOURS * 3600000)));
					htmltext = "4306-okvitality.htm";
				}
				break;
			}
			case "memories_player":
			{
				if (player.getLevel() <= MIN_LEVEL)
				{
					htmltext = "4306-nolevel.htm";
				}
				else
				{
					final SkillHolder[] skills = (player.isMageClass()) ? MAGE_SKILLS : FIGHTER_SKILLS;
					npc.setTarget(player);
					for (SkillHolder sk : skills)
					{
						npc.doCast(sk.getSkill());
					}
					htmltext = "4306-okbuff.htm";
				}
				break;
			}
			case "memories_summon":
			{
				if (player.getLevel() <= MIN_LEVEL)
				{
					htmltext = "4306-nolevel.htm";
				}
				else if (!player.hasServitor())
				{
					htmltext = "4306-nosummon.htm";
				}
				else
				{
					npc.setTarget(player.getSummon());
					for (SkillHolder sk : SERVITOR_SKILLS)
					{
						npc.doCast(sk.getSkill());
					}
					htmltext = "4306-okbuff.htm";
				}
				break;
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
		new GiftOfVitality();
	}
}
