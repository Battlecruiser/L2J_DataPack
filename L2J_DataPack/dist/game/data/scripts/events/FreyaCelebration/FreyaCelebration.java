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
package events.FreyaCelebration;

import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.event.LongTimeEvent;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Freya Celebration event AI.
 * @author Gnacik
 */
public final class FreyaCelebration extends LongTimeEvent
{
	// NPC
	private static final int FREYA = 13296;
	// Items
	private static final int FREYA_POTION = 15440;
	private static final int FREYA_GIFT = 17138;
	// Misc
	private static final int HOURS = 20;
	
	private static final int[] SKILLS =
	{
		9150,
		9151,
		9152,
		9153,
		9154,
		9155,
		9156
	};
	
	private static final NpcStringId[] FREYA_TEXT =
	{
		NpcStringId.EVEN_THOUGH_YOU_BRING_SOMETHING_CALLED_A_GIFT_AMONG_YOUR_HUMANS_IT_WOULD_JUST_BE_PROBLEMATIC_FOR_ME,
		NpcStringId.I_JUST_DONT_KNOW_WHAT_EXPRESSION_I_SHOULD_HAVE_IT_APPEARED_ON_ME_ARE_HUMANS_EMOTIONS_LIKE_THIS_FEELING,
		NpcStringId.THE_FEELING_OF_THANKS_IS_JUST_TOO_MUCH_DISTANT_MEMORY_FOR_ME,
		NpcStringId.BUT_I_KIND_OF_MISS_IT_LIKE_I_HAD_FELT_THIS_FEELING_BEFORE,
		NpcStringId.I_AM_ICE_QUEEN_FREYA_THIS_FEELING_AND_EMOTION_ARE_NOTHING_BUT_A_PART_OF_MALISSA_MEMORIES
	};
	
	private FreyaCelebration()
	{
		super(FreyaCelebration.class.getSimpleName(), "events");
		addStartNpc(FREYA);
		addFirstTalkId(FREYA);
		addTalkId(FREYA);
		addSkillSeeId(FREYA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("give_potion"))
		{
			if (st.getQuestItemsCount(Inventory.ADENA_ID) > 1)
			{
				long _curr_time = System.currentTimeMillis();
				String value = loadGlobalQuestVar(player.getAccountName());
				long _reuse_time = value == "" ? 0 : Long.parseLong(value);
				
				if (_curr_time > _reuse_time)
				{
					st.setState(State.STARTED);
					st.takeItems(Inventory.ADENA_ID, 1);
					st.giveItems(FREYA_POTION, 1);
					saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (HOURS * 3600000)));
				}
				else
				{
					long remainingTime = (_reuse_time - System.currentTimeMillis()) / 1000;
					int hours = (int) (remainingTime / 3600);
					int minutes = (int) ((remainingTime % 3600) / 60);
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.AVAILABLE_AFTER_S1_S2_HOURS_S3_MINUTES);
					sm.addItemName(FREYA_POTION);
					sm.addInt(hours);
					sm.addInt(minutes);
					player.sendPacket(sm);
				}
			}
			else
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_UNIT_OF_THE_ITEM_S1_REQUIRED);
				sm.addItemName(Inventory.ADENA_ID);
				sm.addInt(1);
				player.sendPacket(sm);
			}
		}
		return null;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, Skill skill, L2Object[] targets, boolean isSummon)
	{
		if ((caster == null) || (npc == null))
		{
			return null;
		}
		
		if ((npc.getId() == FREYA) && Util.contains(targets, npc) && Util.contains(SKILLS, skill.getId()))
		{
			if (getRandom(100) < 5)
			{
				CreatureSay cs = new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.DEAR_S1_THINK_OF_THIS_AS_MY_APPRECIATION_FOR_THE_GIFT_TAKE_THIS_WITH_YOU_THERES_NOTHING_STRANGE_ABOUT_IT_ITS_JUST_A_BIT_OF_MY_CAPRICIOUSNESS);
				cs.addStringParameter(caster.getName());
				
				npc.broadcastPacket(cs);
				
				caster.addItem("FreyaCelebration", FREYA_GIFT, 1, npc, true);
			}
			else
			{
				if (getRandom(10) < 2)
				{
					npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), FREYA_TEXT[getRandom(FREYA_TEXT.length - 1)]));
				}
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		getQuestState(player, true);
		return "13296.htm";
	}
	
	public static void main(String[] args)
	{
		new FreyaCelebration();
	}
}
