package ai.npc.AvantGarde;

import java.util.List;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.MultisellData;
import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.datatables.SkillTreesData;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.L2SkillLearn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.AcquireSkillType;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.RequestAcquireSkill;
import com.l2jserver.gameserver.network.serverpackets.ExAcquirableSkillListByClass;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

import custom.Validators.SubClassSkills;

/**
 * Avant-Garde AI.<br>
 * Sub-Class Certification system, skill learning and certification canceling.<br>
 * Transformation skill learning and transformation scroll sell.
 * @author Zoey76
 */
public class AvantGarde extends AbstractNpcAI
{
	// NPC
	private static final int AVANT_GARDE = 32323;
	// Items
	// @formatter:off
	private static final int[] ITEMS =
	{
		10280, 10281, 10282, 10283, 10284, 10285, 10286, 10287, 10288, 10289, 10290, 10291, 10292, 10293, 10294, 10612
	};
	// @formatter:on
	// Misc
	private static final String[] QUEST_VAR_NAMES =
	{
		"EmergentAbility65-",
		"EmergentAbility70-",
		"ClassAbility75-",
		"ClassAbility80-"
	};
	
	public AvantGarde()
	{
		super(AvantGarde.class.getSimpleName(), "ai/npc");
		addStartNpc(AVANT_GARDE);
		addTalkId(AVANT_GARDE);
		addFirstTalkId(AVANT_GARDE);
		addAcquireSkillId(AVANT_GARDE);
	}
	
	@Override
	public String onAcquireSkill(L2Npc npc, L2PcInstance player, Skill skill, AcquireSkillType type)
	{
		switch (type)
		{
			case TRANSFORM:
			{
				showTransformSkillList(player);
				break;
			}
			case SUBCLASS:
			{
				showSubClassSkillList(player);
				break;
			}
		}
		return null;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "32323-02.html":
			case "32323-02a.html":
			case "32323-02b.html":
			case "32323-02c.html":
			case "32323-05.html":
			case "32323-05a.html":
			case "32323-05no.html":
			case "32323-06.html":
			case "32323-06no.html":
			{
				htmltext = event;
				break;
			}
			case "LearnTransformationSkill":
			{
				if (RequestAcquireSkill.canTransform(player))
				{
					showTransformSkillList(player);
				}
				else
				{
					htmltext = "32323-03.html";
				}
				break;
			}
			case "BuyTransformationItems":
			{
				if (RequestAcquireSkill.canTransform(player))
				{
					MultisellData.getInstance().separateAndSend(32323001, player, npc, false);
				}
				else
				{
					htmltext = "32323-04.html";
				}
				break;
			}
			case "LearnSubClassSkill":
			{
				if (!RequestAcquireSkill.canTransform(player))
				{
					htmltext = "32323-04.html";
				}
				if (player.isSubClassActive())
				{
					htmltext = "32323-08.html";
				}
				else
				{
					boolean hasItems = false;
					for (int id : ITEMS)
					{
						if (player.getInventory().getItemByItemId(id) != null)
						{
							hasItems = true;
							break;
						}
					}
					if (hasItems)
					{
						showSubClassSkillList(player);
					}
					else
					{
						htmltext = "32323-08.html";
					}
				}
				break;
			}
			case "CancelCertification":
			{
				if (player.getSubClasses().size() == 0)
				{
					htmltext = "32323-07.html";
				}
				else if (player.isSubClassActive())
				{
					htmltext = "32323-08.html";
				}
				else if (player.getAdena() < Config.FEE_DELETE_SUBCLASS_SKILLS)
				{
					htmltext = "32323-08no.html";
				}
				else
				{
					QuestState st = player.getQuestState(SubClassSkills.class.getSimpleName());
					if (st == null)
					{
						st = QuestManager.getInstance().getQuest(SubClassSkills.class.getSimpleName()).newQuestState(player);
					}
					
					int activeCertifications = 0;
					for (String varName : QUEST_VAR_NAMES)
					{
						for (int i = 1; i <= Config.MAX_SUBCLASS; i++)
						{
							String qvar = st.getGlobalQuestVar(varName + i);
							if (!qvar.isEmpty() && (qvar.endsWith(";") || !qvar.equals("0")))
							{
								activeCertifications++;
							}
						}
					}
					if (activeCertifications == 0)
					{
						htmltext = "32323-10no.html";
					}
					else
					{
						for (String varName : QUEST_VAR_NAMES)
						{
							for (int i = 1; i <= Config.MAX_SUBCLASS; i++)
							{
								final String qvarName = varName + i;
								final String qvar = st.getGlobalQuestVar(qvarName);
								if (qvar.endsWith(";"))
								{
									final String skillIdVar = qvar.replace(";", "");
									if (Util.isDigit(skillIdVar))
									{
										int skillId = Integer.parseInt(skillIdVar);
										final Skill sk = SkillData.getInstance().getSkill(skillId, 1);
										if (sk != null)
										{
											player.removeSkill(sk);
											st.saveGlobalQuestVar(qvarName, "0");
										}
									}
									else
									{
										_log.warning("Invalid Sub-Class Skill Id: " + skillIdVar + " for player " + player.getName() + "!");
									}
								}
								else if (!qvar.isEmpty() && !qvar.equals("0"))
								{
									if (Util.isDigit(qvar))
									{
										final int itemObjId = Integer.parseInt(qvar);
										L2ItemInstance itemInstance = player.getInventory().getItemByObjectId(itemObjId);
										if (itemInstance != null)
										{
											player.destroyItem("CancelCertification", itemObjId, 1, player, false);
										}
										else
										{
											itemInstance = player.getWarehouse().getItemByObjectId(itemObjId);
											if (itemInstance != null)
											{
												_log.warning("Somehow " + player.getName() + " put a certification book into warehouse!");
												player.getWarehouse().destroyItem("CancelCertification", itemInstance, 1, player, false);
											}
											else
											{
												_log.warning("Somehow " + player.getName() + " deleted a certification book!");
											}
										}
										st.saveGlobalQuestVar(qvarName, "0");
									}
									else
									{
										_log.warning("Invalid item object Id: " + qvar + " for player " + player.getName() + "!");
									}
								}
							}
						}
						
						player.reduceAdena("Cleanse", Config.FEE_DELETE_SUBCLASS_SKILLS, npc, true);
						htmltext = "32323-09no.html";
						player.sendSkillList();
					}
					
					// Let's consume all certification books, even those not present in database.
					for (int itemId : ITEMS)
					{
						L2ItemInstance item = player.getInventory().getItemByItemId(itemId);
						if (item != null)
						{
							_log.warning(getClass().getName() + ": player " + player + " had 'extra' certification skill books while cancelling sub-class certifications!");
							player.destroyItem("CancelCertificationExtraBooks", item, npc, false);
						}
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return "32323-01.html";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		return "32323-01.html";
	}
	
	/**
	 * Display the Sub-Class Skill list to the player.
	 * @param player the player
	 */
	public static void showSubClassSkillList(L2PcInstance player)
	{
		final List<L2SkillLearn> subClassSkills = SkillTreesData.getInstance().getAvailableSubClassSkills(player);
		
		if (subClassSkills.isEmpty())
		{
			player.sendPacket(SystemMessageId.THERE_ARE_NO_OTHER_SKILLS_TO_LEARN);
		}
		else
		{
			player.sendPacket(new ExAcquirableSkillListByClass(subClassSkills, AcquireSkillType.TRANSFORM));
		}
	}
	
	/**
	 * This displays Transformation Skill List to the player.
	 * @param player the active character.
	 */
	public static void showTransformSkillList(L2PcInstance player)
	{
		final List<L2SkillLearn> skills = SkillTreesData.getInstance().getAvailableTransformSkills(player);
		
		if (skills.isEmpty())
		{
			final int minlevel = SkillTreesData.getInstance().getMinLevelForNewSkill(player, SkillTreesData.getInstance().getTransformSkillTree());
			if (minlevel > 0)
			{
				// No more skills to learn, come back when you level.
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN_COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1);
				sm.addInt(minlevel);
				player.sendPacket(sm);
			}
			else
			{
				player.sendPacket(SystemMessageId.THERE_ARE_NO_OTHER_SKILLS_TO_LEARN);
			}
		}
		else
		{
			player.sendPacket(new ExAcquirableSkillListByClass(skills, AcquireSkillType.TRANSFORM));
		}
	}
	
	public static void main(String[] args)
	{
		new AvantGarde();
	}
}
