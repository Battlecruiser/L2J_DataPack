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
package custom.SubClassSkills;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.AcquireSkillInfo;
import com.l2jserver.gameserver.network.serverpackets.AcquireSkillList;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @authors Gigiikun (python), Nyaran (java)
 */
public class SubClassSkills extends Quest
{
	private static final String qn = "SubClassSkills";

	private static final int NPC = 32323;
	private static final int[] SKILLITEMS =
	{
		10280, 10281, 10282, 10283, 10284, 10285, 10286, 10287, 10288, 10289, 10290, 10291, 10292, 10293, 10294, 10612
	};

	private static final Map<Integer, int[]> SUBSKILLS = new FastMap<Integer, int[]>();
	private static final Map<String, int[]> QUESTVARSITEMS = new FastMap<String, int[]>();

	public SubClassSkills(int id, String name, String descr)
	{
		super(id, name, descr);

		addStartNpc(NPC);
		addTalkId(NPC);
		addAcquireSkillId(NPC);
		
		SUBSKILLS.put(10280, new int[] {631,632,633,634});	// Common
		SUBSKILLS.put(10612, new int[] {637,638,639,640,799,800});	// Enhanced
		SUBSKILLS.put(10281, new int[] {801,650,651});	// Warriors
		SUBSKILLS.put(10282, new int[] {804,641,652});	// Knights
		SUBSKILLS.put(10283, new int[] {644,645,653});	// Rogues
		SUBSKILLS.put(10284, new int[] {802,646,654});	// Wizards
		SUBSKILLS.put(10285, new int[] {803,648,1490});	// Healers
		SUBSKILLS.put(10286, new int[] {643,1489,1491});	// Summoners
		SUBSKILLS.put(10287, new int[] {642,647,655});	// Enchanters
		SUBSKILLS.put(10289, new int[] {656});	// Warriors
		SUBSKILLS.put(10288, new int[] {657});	// Knights
		SUBSKILLS.put(10290, new int[] {658});	// Rogues
		SUBSKILLS.put(10292, new int[] {659});	// Wizards
		SUBSKILLS.put(10291, new int[] {661});	// Healers
		SUBSKILLS.put(10294, new int[] {660});	// Summoners
		SUBSKILLS.put(10293, new int[] {662});	// Enchanters
		
		QUESTVARSITEMS.put("EmergentAbility65-", new int[] {10280});
		QUESTVARSITEMS.put("EmergentAbility70-", new int[] {10280});
		QUESTVARSITEMS.put("ClassAbility75-", new int[] {10612, 10281, 10282, 10283, 10284, 10285, 10286, 10287});
		QUESTVARSITEMS.put("ClassAbility80-", new int[] {10288, 10289, 10290, 10291, 10292, 10293, 10294});
	}

	@Override
	public String onAcquireSkillList(L2Npc npc, L2PcInstance player)
	{
		AcquireSkillList asl = new AcquireSkillList(AcquireSkillList.SkillType.unk4);

		L2Skill[] oldSkills = player.getAllSkills();
		int count = 0;
		for (int i : SKILLITEMS)
		{
			for (int j : SUBSKILLS.get(i))
			{
				int minLevel = 0;
				int maxLevel = SkillTable.getInstance().getMaxLevel(j);
				for (L2Skill oldsk : oldSkills)
				{
					if (oldsk.getId() == j)
						minLevel = oldsk.getLevel();
				}
				if (minLevel < maxLevel)
				{
					count += 1;
					asl.addSkill(j, minLevel + 1, maxLevel, 0, 0);
				}
			}
		}
		player.sendPacket(asl);
		if (count == 0)
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NO_MORE_SKILLS_TO_LEARN));
		return "";
	}

	@Override
	public String onAcquireSkill(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (player.isSubClassActive())
		{
			player.sendMessage("You are trying to learn skill that u can't..");
			Util.handleIllegalPlayerAction(player, "Player " + player.getName() + " tried to learn skill that he can't!!!", Config.DEFAULT_PUNISH);
			return "false";
		}
		QuestState st = player.getQuestState(qn);
		for (int i : SKILLITEMS)
		{
			if (Util.contains(SUBSKILLS.get(i), skill.getId()))
			{
				for (String var : QUESTVARSITEMS.keySet())
				{
					if (Util.contains(QUESTVARSITEMS.get(var), i))
					{
						for (int j = 0; j < Config.MAX_SUBCLASS; j++)
						{
							String qvar = st.getGlobalQuestVar(var + String.valueOf(j + 1));
							if (qvar != "" && qvar != "0" && !qvar.endsWith(";"))
							{
								L2ItemInstance Item = player.getInventory().getItemByObjectId(Integer.parseInt(qvar));
								if (Item != null && Item.getItemId() == i)
								{
									player.destroyItem(qn, Integer.parseInt(qvar), 1, player, false);
									st.saveGlobalQuestVar(var + String.valueOf(j + 1), String.valueOf(skill.getId()) + ";");
									return "true";
								}
							}
						}
					}
				}
			}
		}
		player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL));
		return "false";
	}

	@Override
	public String onAcquireSkillInfo(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		AcquireSkillInfo asi = new AcquireSkillInfo(skill.getId(), skill.getLevel(), 0, 5);
		for (int i : SKILLITEMS)
		{
			if (Util.contains(SUBSKILLS.get(i), skill.getId()))
				asi.addRequirement(99, i, 1, 50);
		}
		player.sendPacket(asi);
		return "";
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (event.equals("learn"))
		{
			htmltext = "";
			QuestState st2 = player.getQuestState("136_MoreThanMeetsTheEye");
			if (player.isSubClassActive())
				htmltext = "8005-04.htm";
			else if (st2 == null || st2.getState() != State.COMPLETED)
				htmltext = "8005-03.htm";
			else
			{
				int j = 0;
				for (int i : SKILLITEMS)
					j += st.getQuestItemsCount(i);
				if (j > 0)
					this.onAcquireSkillList(npc, player);
				else
					htmltext = "8005-04.htm";
			}
		}
		else if (event.equals("cancel"))
			if (st.getQuestItemsCount(57) < 10000000)
				htmltext = "8005-07.htm";
			else if (player.getSubClasses().size() == 0)
				htmltext = "8005-03.htm";
			else if (player.isSubClassActive())
				htmltext = "8005-04.htm";
			else
			{
				int activeCertifications = 0;
				for (String var : QUESTVARSITEMS.keySet())
				{
					for (int i = 0; i < Config.MAX_SUBCLASS; i++)
					{
						String qvar = st.getGlobalQuestVar(var + String.valueOf(i + 1));
						if (qvar.endsWith(";"))
							activeCertifications += 1;
						else if (qvar != "" && qvar != "0")
							activeCertifications += 1;
					}
				}
				if (activeCertifications == 0)
					htmltext = "8005-08.htm";
				else
				{
					for (String var : QUESTVARSITEMS.keySet())
					{
						for (int i = 0; i < Config.MAX_SUBCLASS; i++)
						{
							String qvar = st.getGlobalQuestVar(var + String.valueOf(i + 1));
							if (qvar.endsWith(";"))
							{
								L2Skill skill = SkillTable.getInstance().getInfo(Integer.parseInt(qvar.replace(";", "")), 1);
								if (skill != null)
								{
									qvar = st.getGlobalQuestVar(var + String.valueOf(i + 1));
									player.removeSkill(skill, true);
									st.saveGlobalQuestVar(var + String.valueOf(i + 1), "0");
								}
							}
							else if (qvar != "" && qvar != "0")
							{
								L2ItemInstance Item = player.getInventory().getItemByObjectId(Integer.parseInt(qvar));
								if (Item != null)
									player.destroyItem(qn, Integer.parseInt(qvar), 1, player, false);
								else
								{
									Item = player.getWarehouse().getItemByObjectId(Integer.parseInt(qvar));
									if (Item != null)
									{
										_log.warning("Somehow " + player.getName() + " put certification book into warehouse!");
										player.getWarehouse().destroyItem(qn, Item, 1, player, false);
									}
									else
										_log.warning("Somehow " + player.getName() + " his/her delete certification book!");
								}
								st.saveGlobalQuestVar(var + String.valueOf(i + 1), "0");
							}
						}
					}
					st.takeItems(57, 10000000);
					htmltext = "8005-09.htm";
					player.sendSkillList();
				}
			}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);

		QuestState st = player.getQuestState(qn);
		int npcId = npc.getNpcId();
		if (npcId == NPC)
		{
			st.set("cond", "0");
			st.setState(State.STARTED);
			htmltext = "8005-01.htm";
		}

		return htmltext;
	}

	public static void main(String args[])
	{
		new SubClassSkills(-1, qn, "custom");
	}
}
