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
package custom.SkillTransfer;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.AcquireSkillInfo;
import com.l2jserver.gameserver.network.serverpackets.AcquireSkillList;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

public class SkillTransfer extends Quest
{
	private static String qn = "SkillTransfer";

	private static final int RESET_ADENA_COST = 10000000;

	private static final int NPCs[] =
	{
		30022,30030,30032,30036,30067,30068,30116,30117,30118,30119,
		30144,30145,30188,30194,30293,30330,30375,30377,30464,30473,
		30476,30680,30701,30720,30721,30858,30859,30860,30861,30864,
		30906,30908,30912,31280,31281,31287,31329,31330,31335,31969,
		31970,31976,32155,32162
	};

	/*
	 * Item ID, count
	 */
	private static final int[][] PORMANDERS =
	{
		{ 15307, 1 }, // Cardinal (97)
		{ 15308, 1 }, // Eva's Saint (105)
		{ 15309, 4 } // Shillen Saint (112)
	};

	/*
	 * Skill ID, Skill Level
	 */
	private static final int[][][] SKILL_TRANSFER_TREE =
	{
		// Cardinal (97)
		{
			{1013,32},
			{1033,3},
			{1050,2},
			{1059,3},
			{1087,3},
			{1189,3},
			{1240,3},
			{1242,3},
			{1243,6},
			{1255,2},
			{1257,3},
			{1259,4},
			{1268,4},
			{1273,13},
			{1303,2},
			{1304,3},
			{1392,3},
			{1393,3},
			{1397,3},
			{1531,7},
			{1539,4}
		},
		// Eva's Saint (105)
		{
			{1018,3},
			{1034,13},
			{1042,12},
			{1049,14},
			{1059,3},
			{1075,15},
			{1077,3},
			{1189,3},
			{1218,33},
			{1240,3},
			{1242,3},
			{1254,6},
			{1258,4},
			{1268,4},
			{1271,1},
			{1307,3},
			{1311,6},
			{1392,3},
			{1396,10},
			{1399,5},
			{1402,5},
			{1418,1},
			{1531,7},
			{1539,4}
		},
		// Shillen Saint (112)
		{
			{1020,27},
			{1028,19},
			{1033,3},
			{1034,13},
			{1042,12},
			{1043,1},
			{1044,3},
			{1049,14},
			{1050,2},
			{1075,15},
			{1087,3},
			{1218,33},
			{1243,6},
			{1254,6},
			{1255,2},
			{1257,3},
			{1258,4},
			{1259,4},
			{1271,1},
			{1273,13},
			{1304,3},
			{1307,3},
			{1311,6},
			{1393,3},
			{1394,10},
			{1396,10},
			{1397,3},
			{1399,5},
			{1400,10},
			{1401,11},
			{1402,5},
			{1418,1}
		}
	};

	@Override
	public final String onAcquireSkillList(L2Npc npc, L2PcInstance player)
	{
		if (player == null)
			return null;

		final int index = getTransferClassIndex(player);
		if (index >= 0)
		{
			boolean found = false;
			AcquireSkillList asl = new AcquireSkillList(AcquireSkillList.SkillType.unk4);
			int[][] skillsList = SKILL_TRANSFER_TREE[index];

			for (int i = 0; i < skillsList.length; i++)
			{
				int skillId = skillsList[i][0];
				int skillLevel = skillsList[i][1];
				if (player.getSkillLevel(skillId) >= skillLevel)
					continue;

				asl.addSkill(skillId, skillLevel, skillLevel, 0, 0);
				found = true;
			}
			if (found)
			{
				player.sendPacket(asl);
				return null;
			}
		}

		player.sendPacket(new SystemMessage(SystemMessageId.NO_MORE_SKILLS_TO_LEARN));
		return null;
	}

	@Override
	public final String onAcquireSkill(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (player == null || skill == null)
			return null;

		final int index = getTransferClassIndex(player);
		if (index >= 0)
		{
			int[][] skillsList = SKILL_TRANSFER_TREE[index];
			
			for (int i = 0; i < skillsList.length; i++)
			{
				if (skill.getId() == skillsList[i][0] && skill.getLevel() <= skillsList[i][1])
				{
					final int itemId = PORMANDERS[index][0];
					if (player.getInventory().getItemByItemId(itemId) != null)
					{
						if (player.destroyItemByItemId(qn, itemId, 1, npc, true))
							return "true";
					}
					player.sendPacket(new SystemMessage(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL));
					return "false";
				}
			}
		}
		Util.handleIllegalPlayerAction(player, "Player " + player.getName()
				+ " tried to learn skill that he can't!!!", Config.DEFAULT_PUNISH);

		return "false";
	}

	@Override
	public final String onAcquireSkillInfo(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (player == null || skill == null)
			return null;

		final int index = getTransferClassIndex(player);
		if (index >= 0)
		{
			AcquireSkillInfo asi = new AcquireSkillInfo(skill.getId(), skill.getLevel(), 0, 4);
			int[][] skillsList = SKILL_TRANSFER_TREE[index];
			
			for (int i = 0; i < skillsList.length; i++)
			{
				if (skill.getId() == skillsList[i][0])
				{
					asi.addRequirement(99, PORMANDERS[index][0], 1, 50);
					player.sendPacket(asi);
					break;
				}
			}
		}

		return null;
	}

	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";

		if (player == null)
			return htmltext;

		if (event.equalsIgnoreCase("learn"))
		{
			if (!npc.getTemplate().canTeach(player.getClassId()))
				npc.showNoTeachHtml(player);
			else if (player.getLevel() < 76 || player.getClassId().level() < 3)
				htmltext = "learn-lowlevel.htm";
			else
				onAcquireSkillList(npc, player);
		}
		else if (event.equalsIgnoreCase("cleanse"))
		{
			if (!npc.getTemplate().canTeach(player.getClassId()))
			{
				npc.showNoTeachHtml(player);
				return htmltext;
			}
			else if (player.getLevel() < 76 || player.getClassId().level() < 3)
			{
				htmltext = "cleanse-no.htm";
				return htmltext;
			}
			else if (player.getAdena() < RESET_ADENA_COST)
			{
				player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_RESET_SKILL_LINK_BECAUSE_NOT_ENOUGH_ADENA));
				return htmltext;
			}

			final int index = getTransferClassIndex(player);
			if (index >= 0)
			{
				final int itemId = PORMANDERS[index][0];
				final int count = PORMANDERS[index][1];
				if (player.getInventory().getItemByItemId(itemId) == null)
				{
					int[][] skillsList = SKILL_TRANSFER_TREE[index];
					boolean found = false;

					for (L2Skill skill : player.getAllSkills())
					{
						for (int i = 0; i < skillsList.length; i++)
						{
							if (skill.getId() == skillsList[i][0])
							{
								if (!found)
									found = true;
								player.removeSkill(skill);
							}
						}
					}
					if (found)
					{
						player.sendSkillList();
						if (player.reduceAdena(qn, RESET_ADENA_COST, npc, true))
							player.addItem(qn, itemId, count, npc, true);
						return htmltext;
					}
				}
				htmltext = "cleanse-no_skills.htm";
			}
			else
				npc.showNoTeachHtml(player);
		}
		else if (event.equalsIgnoreCase("givePormanders"))
			givePormanders(npc, player);

		return htmltext;
	}

	@Override
	public final String onEnterWorld(L2PcInstance player)
	{
		givePormanders(null, player);
		return null;
	}

	private final synchronized void givePormanders(L2Npc npc, L2PcInstance player)
	{
		final int index = getTransferClassIndex(player);
		if (index >= 0)
		{
			QuestState st = player.getQuestState(qn);
			if (st == null)
				st = newQuestState(player);

			final String name = qn + String.valueOf(player.getClassId().getId());
			if (st.getInt(name) == 0)
			{
				st.setInternal(name, "1");
				if (st.getGlobalQuestVar(name).isEmpty())
				{
					st.saveGlobalQuestVar(name, "1");
					player.addItem(qn, PORMANDERS[index][0], PORMANDERS[index][1], npc, true);
				}
			}
		}
	}

	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		return "main.htm";
	}

	private final int getTransferClassIndex(L2PcInstance player)
	{
		switch (player.getClassId().getId())
		{
			case 97: // Cardinal
				return 0;
			case 105: // Eva's Saint
				return 1;
			case 112: // Shillien Saint
				return 2;
			default:
				return -1;
		}
	}

	public SkillTransfer(int id, String name, String descr)
	{
		super(id, name, descr);
		setOnEnterWorld(true);
		for (int i : NPCs)
		{
			addStartNpc(i);
			addTalkId(i);
			addAcquireSkillId(i);
		}
	}

	public static void main(String[] args)
	{
		new SkillTransfer(-1, qn, "custom");
	}
}