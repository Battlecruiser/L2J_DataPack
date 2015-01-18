/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package ai.npc.MentorGuide;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.engines.DocumentParser;
import com.l2jserver.gameserver.enums.CategoryType;
import com.l2jserver.gameserver.enums.MailType;
import com.l2jserver.gameserver.instancemanager.MailManager;
import com.l2jserver.gameserver.instancemanager.MentorManager;
import com.l2jserver.gameserver.model.L2Mentee;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassLevel;
import com.l2jserver.gameserver.model.entity.Message;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerLevelChanged;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerProfessionChange;
import com.l2jserver.gameserver.model.events.impl.character.player.mentoring.OnPlayerMenteeAdd;
import com.l2jserver.gameserver.model.events.impl.character.player.mentoring.OnPlayerMenteeLeft;
import com.l2jserver.gameserver.model.events.impl.character.player.mentoring.OnPlayerMenteeRemove;
import com.l2jserver.gameserver.model.events.impl.character.player.mentoring.OnPlayerMenteeStatus;
import com.l2jserver.gameserver.model.events.impl.character.player.mentoring.OnPlayerMentorStatus;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.network.serverpackets.mentoring.ExMentorList;
import com.l2jserver.gameserver.util.Util;

/**
 * Mentor Guide.
 * @author Gnacik, UnAfraid
 */
public class MentorGuide extends AbstractNpcAI implements DocumentParser
{
	// NPCs
	private static final int MENTOR_GUIDE = 33587;
	// Items
	private static final int MENTEE_CERT = 33800;
	private static final int MENTEE_MARK = 33804;
	private static final int MENTEE_HEADPHONE = 34759;
	private static final int DIPLOMA = 33805;
	// Skills
	private final static SkillHolder[] MENTEE_BUFFS =
	{
		new SkillHolder(9227, 1), // Mentor's Poem of Horn
		new SkillHolder(9228, 1), // Mentor's Poem of Drum
		new SkillHolder(9229, 1), // Mentor's Poem of Lute
		new SkillHolder(9230, 1), // Mentor's Poem of Organ
		new SkillHolder(9231, 1), // Mentor's Poem of Guitar
		new SkillHolder(9232, 1), // Mentor's Poem of Harp
		new SkillHolder(9233, 1), // Mentor's Guidance
	};
	protected static final SkillHolder[] MENTOR_BUFFS =
	{
		new SkillHolder(9256, 1), // Mentee's Appreciation;
	};
	private static final SkillHolder MENTEE_MENTOR_SUMMON = new SkillHolder(9379, 1); // Mentee's Mentor Summon
	// Misc
	private static final int MAX_LEVEL = 85;
	private static final String LEVEL_UP_TITLE = "Mentee coin from Mentee leveling";
	private static final String LEVEL_UP_BODY = "Your mentee %s has reached level %d, so you are receiving some Mentee Coin. After Mentee Coin has successfully been removed and placed into your inventory please be sure to delete this letter. If your mailbox is full when any future letters are sent to you cannot be delivered and you will not receive these items.";
	private static final String MENTEE_ADDED_TITLE = "Congratulations on becoming a mentee.";
	private static final String MENTEE_ADDED_BODY = "Greetings. This is the Mentor Guide.\n\nYou will experience a world of unlimited adventures with your mentor, Exciting, isn't it?\n\nWhen you graduate from mentee status (upon awakening at level 85), you will receive a Mentee Certificate. If you bring it to me, I will give you a Diploma that you can exchange for R-grade equipment.";
	static final Map<Integer, Integer> MENTEE_COINS = new HashMap<>();
	
	@Override
	public void load()
	{
		parseDatapackFile("config/MentorCoins.xml");
		LOGGER.log(Level.INFO, MentorGuide.class.getSimpleName() + ": Loaded: " + MENTEE_COINS.size() + " mentee coins");
	}
	
	@Override
	public void parseDocument(Document doc)
	{
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("mentee".equalsIgnoreCase(d.getNodeName()))
					{
						final int level = parseInteger(d.getAttributes(), "level");
						final int coins = parseInteger(d.getAttributes(), "coins");
						MENTEE_COINS.put(level, coins);
					}
				}
			}
		}
	}
	
	private MentorGuide()
	{
		super(MentorGuide.class.getSimpleName(), "ai/npc");
		
		addFirstTalkId(MENTOR_GUIDE);
		addStartNpc(MENTOR_GUIDE);
		addTalkId(MENTOR_GUIDE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		if (event.equalsIgnoreCase("exchange"))
		{
			if (hasQuestItems(player, MENTEE_CERT) && (player.getLevel() >= MAX_LEVEL) && (player.getClassId().level() == ClassLevel.AWAKEN.ordinal()))
			{
				takeItems(player, MENTEE_CERT, 1);
				giveItems(player, DIPLOMA, 1);
				return null;
			}
			htmltext = "33587-04.htm";
		}
		else if (event.startsWith("REMOVE_BUFFS"))
		{
			final String[] params = event.split(" ");
			if (Util.isDigit(params[1]))
			{
				final int objectId = Integer.valueOf(params[1]);
				MentorManager.getInstance().getMentees(objectId).stream().filter(Objects::nonNull).filter(L2Mentee::isOnline).forEach(mentee ->
				{
					MentorManager.getInstance().cancelMentoringBuffs(mentee.getPlayerInstance());
					mentee.sendPacket(new ExMentorList(mentee.getPlayerInstance()));
				});
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return "33587-01.htm";
	}
	
	@RegisterEvent(EventType.ON_PLAYER_MENTEE_ADD)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onMenteeAdded(OnPlayerMenteeAdd event)
	{
		// Starting buffs for Mentor
		for (SkillHolder sk : MENTOR_BUFFS)
		{
			sk.getSkill().applyEffects(event.getMentor(), event.getMentor());
		}
		
		// Starting buffs for Mentee
		for (SkillHolder sk : MENTEE_BUFFS)
		{
			sk.getSkill().applyEffects(event.getMentee(), event.getMentee());
		}
		
		// Update mentor list
		event.getMentor().sendPacket(new ExMentorList(event.getMentor()));
		
		// Add the mentee skill
		event.getMentee().addSkill(MENTEE_MENTOR_SUMMON.getSkill(), true);
		
		// Send mail with the headphone
		sendMail(event.getMentee(), MENTEE_ADDED_TITLE, MENTEE_ADDED_BODY, MENTEE_HEADPHONE, 1);
	}
	
	@RegisterEvent(EventType.ON_PLAYER_MENTEE_STATUS)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void OnPlayerMenteeStatus(OnPlayerMenteeStatus event)
	{
		final L2PcInstance player = event.getMentee();
		
		if (event.isMenteeOnline())
		{
			final L2Mentee mentor = MentorManager.getInstance().getMentor(player.getObjectId());
			if ((mentor != null) && mentor.isOnline())
			{
				//@formatter:off
				final long mentorBuffs = mentor.getPlayerInstance().getEffectList().getEffects()
					.stream()
					.map(BuffInfo::getSkill)
					.filter(Skill::isMentoring)
					.count();
				//@formatter:on
				
				if (mentorBuffs != MENTOR_BUFFS.length)
				{
					// Starting buffs for Mentor
					for (SkillHolder sk : MENTOR_BUFFS)
					{
						sk.getSkill().applyEffects(mentor.getPlayerInstance(), mentor.getPlayerInstance());
					}
				}
				
				// Starting buffs for Mentee
				for (SkillHolder sk : MENTEE_BUFFS)
				{
					sk.getSkill().applyEffects(player, player);
				}
				
				mentor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_MENTEE_S1_HAS_CONNECTED).addCharName(player));
				mentor.sendPacket(new ExMentorList(mentor.getPlayerInstance()));
			}
			player.sendPacket(new ExMentorList(player));
		}
		else
		{
			final L2Mentee mentor = MentorManager.getInstance().getMentor(player.getObjectId());
			if ((mentor != null) && mentor.isOnline())
			{
				if (MentorManager.getInstance().isAllMenteesOffline(mentor.getObjectId(), player.getObjectId()))
				{
					MentorManager.getInstance().cancelMentoringBuffs(mentor.getPlayerInstance());
				}
				
				mentor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_MENTEE_S1_HAS_DISCONNECTED).addCharName(player));
				mentor.sendPacket(new ExMentorList(mentor.getPlayerInstance()));
			}
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_MENTOR_STATUS)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void OnPlayerMentorStatus(OnPlayerMentorStatus event)
	{
		final L2PcInstance player = event.getMentor();
		
		if (event.isMentorOnline())
		{
			// stop buffs removal task
			cancelQuestTimer("REMOVE_BUFFS " + player.getObjectId(), null, null);
			
			MentorManager.getInstance().getMentees(player.getObjectId()).stream().filter(Objects::nonNull).filter(L2Mentee::isOnline).forEach(mentee ->
			{
				//@formatter:off
				final long menteeBuffs = mentee.getPlayerInstance().getEffectList().getEffects()
					.stream()
					.map(BuffInfo::getSkill)
					.filter(Skill::isMentoring)
					.count();
				//@formatter:on
				
				if (menteeBuffs != MENTEE_BUFFS.length)
				{
					// Starting buffs for Mentee
					for (SkillHolder sk : MENTEE_BUFFS)
					{
						sk.getSkill().applyEffects(mentee.getPlayerInstance(), mentee.getPlayerInstance());
					}
				}
				
				mentee.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_MENTOR_S1_HAS_CONNECTED).addCharName(player));
				mentee.sendPacket(new ExMentorList(mentee.getPlayerInstance()));
			});
			
			if (MentorManager.getInstance().hasOnlineMentees(player.getObjectId()))
			{
				// Starting buffs for Mentor
				for (SkillHolder sk : MENTOR_BUFFS)
				{
					sk.getSkill().applyEffects(player, player);
				}
			}
			
			player.sendPacket(new ExMentorList(player));
		}
		else
		{
			startQuestTimer("REMOVE_BUFFS " + player.getObjectId(), 5 * 60 * 1000, null, null);
			MentorManager.getInstance().getMentees(player.getObjectId()).stream().filter(Objects::nonNull).filter(L2Mentee::isOnline).forEach(mentee ->
			{
				mentee.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_MENTOR_S1_HAS_DISCONNECTED).addCharName(player));
				mentee.sendPacket(new ExMentorList(mentee.getPlayerInstance()));
			});
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_PROFESSION_CHANGE)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onProfessionChange(OnPlayerProfessionChange event)
	{
		final L2PcInstance player = event.getActiveChar();
		
		// Not a mentee
		if (!player.isMentee())
		{
			return;
		}
		
		if (player.isInCategory(CategoryType.AWAKEN_GROUP))
		{
			handleGraduateMentee(player);
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_LEVEL_CHANGED)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onLevelIncreased(OnPlayerLevelChanged event)
	{
		final L2PcInstance player = event.getActiveChar();
		
		// Not a mentee
		if (!player.isMentee())
		{
			return;
		}
		
		checkLevelForReward(player); // Checking level to send a mail if is necessary
		
		if (player.getLevel() > MAX_LEVEL)
		{
			handleGraduateMentee(player);
		}
		else
		{
			final L2Mentee mentor = MentorManager.getInstance().getMentor(player.getObjectId());
			if ((mentor != null) && mentor.isOnline())
			{
				mentor.sendPacket(new ExMentorList(mentor.getPlayerInstance()));
			}
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_MENTEE_LEFT)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onMenteeLeft(OnPlayerMenteeLeft event)
	{
		final L2PcInstance player = event.getMentee();
		
		// Remove the mentee skills
		player.removeSkill(MENTEE_MENTOR_SUMMON.getSkillId());
		
		// Clear mentee status
		player.sendPacket(new ExMentorList(player));
	}
	
	@RegisterEvent(EventType.ON_PLAYER_MENTEE_REMOVE)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onMenteeRemove(OnPlayerMenteeRemove event)
	{
		final L2Mentee mentee = event.getMentee();
		final L2PcInstance player = mentee.getPlayerInstance();
		
		if (player != null)
		{
			// Remove the mentee skills
			player.removeSkill(MENTEE_MENTOR_SUMMON.getSkillId());
			
			// Clear mentee status
			player.sendPacket(new ExMentorList(player));
		}
		
		// Remove mentee from the list
		event.getMentor().sendPacket(new ExMentorList(event.getMentor()));
	}
	
	private void handleGraduateMentee(L2PcInstance player)
	{
		MentorManager.getInstance().cancelMentoringBuffs(player);
		final L2Mentee mentor = MentorManager.getInstance().getMentor(player.getObjectId());
		if (mentor != null)
		{
			MentorManager.getInstance().setPenalty(mentor.getObjectId(), Config.MENTOR_PENALTY_FOR_MENTEE_COMPLETE);
			if (mentor.isOnline())
			{
				mentor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_HAS_AWAKENED_AND_THE_MENTOR_MENTEE_RELATIONSHIP_HAS_ENDED_THE_MENTOR_CANNOT_OBTAIN_ANOTHER_MENTEE_FOR_ONE_DAY_AFTER_THE_MENTEE_S_GRADUATION).addPcName(player));
				
				if (MentorManager.getInstance().isAllMenteesOffline(mentor.getObjectId(), player.getObjectId()))
				{
					MentorManager.getInstance().cancelMentoringBuffs(mentor.getPlayerInstance());
				}
				mentor.sendPacket(new ExMentorList(mentor.getPlayerInstance()));
			}
			
			MentorManager.getInstance().deleteMentor(mentor.getObjectId(), player.getObjectId());
			
			// Remove the mentee skills
			player.removeSkill(MENTEE_MENTOR_SUMMON.getSkillId());
			
			// Clear mentee status
			player.sendPacket(new ExMentorList(player));
			
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_MENTOR_MENTEE_RELATIONSHIP_WITH_YOUR_MENTOR_S1_HAS_ENDED_AS_YOU_ARE_AN_AWAKENED_CHARACTER_OF_LV_85_OR_ABOVE_YOU_CAN_NO_LONGER_BE_PAIRED_WITH_A_MENTOR).addPcName(player));
			
			// TODO: Find correct message!
			sendMail(player, MENTEE_ADDED_TITLE, MENTEE_ADDED_BODY, MENTEE_CERT, 1);
		}
	}
	
	/**
	 * Verifies if player is mentee and if his current level should reward his mentor and if so sends a mail with reward.
	 * @param player
	 */
	private void checkLevelForReward(L2PcInstance player)
	{
		if (!MENTEE_COINS.containsKey(player.getLevel()))
		{
			return;
		}
		
		final L2Mentee mentor = MentorManager.getInstance().getMentor(player.getObjectId());
		if (mentor == null)
		{
			return;
		}
		
		int amount = MENTEE_COINS.get(player.getLevel());
		if (amount > 0)
		{
			sendMail(player, LEVEL_UP_TITLE, String.format(LEVEL_UP_BODY, player.getName(), player.getLevel()), MENTEE_MARK, amount);
		}
	}
	
	private void sendMail(L2PcInstance player, String title, String body, int itemId, long amount)
	{
		final Message msg = new Message(MENTOR_GUIDE, player.getObjectId(), title, body, MailType.MENTOR_NPC);
		msg.createAttachments().addItem(getName(), itemId, amount, null, player);
		
		MailManager.getInstance().sendMessage(msg);
	}
	
	public static void main(String[] args)
	{
		new MentorGuide();
	}
}