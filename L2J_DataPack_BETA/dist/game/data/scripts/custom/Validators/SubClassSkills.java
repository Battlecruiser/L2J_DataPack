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
package custom.Validators;

import java.util.Arrays;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;

/**
 * @author DS
 */
public final class SubClassSkills extends Quest
{
	// arrays must be sorted
	private static final int[] _allCertSkillIds = {631,632,633,634,637,638,639,640,641,642,643,644,645,646,647,648,650,651,652,653,654,655,656,657,658,659,660,661,662,799,800,801,802,803,804,1489,1490,1491};
	private static final int[][] _certSkillsByLevel =
	{
		{ 631,632,633,634 },
		{ 631,632,633,634 },
		{ 637,638,639,640,641,642,643,644,645,646,647,648,650,651,652,653,654,655,799,800,801,802,803,804,1489,1490,1491 },
		{ 656,657,658,659,660,661,662 }
	};
	
	private static final int[] _allCertItemIds = {10280,10281,10282,10283,10284,10285,10286,10287,10288,10289,10290,10291,10292,10293,10294,10612};
	private static final int[][] _certItemsByLevel =
	{
		{ 10280 },
		{ 10280 },
		{ 10612,10281,10282,10283,10284,10285,10286,10287 },
		{ 10288,10289,10290,10291,10292,10293,10294 }
	};
	
	private static final String[] VARS =
	{
		"EmergentAbility65-",
		"EmergentAbility70-",
		"ClassAbility75-",
		"ClassAbility80-"
	};
	
	public SubClassSkills(int id, String name, String descr)
	{
		super(id, name, descr);
		setOnEnterWorld(true);
	}
	
	@Override
	public String onEnterWorld(L2PcInstance player)
	{
		if (!Config.SKILL_CHECK_ENABLE)
			return null;
		
		if (player.isGM() && !Config.SKILL_CHECK_GM)
			return null;
		
		final L2Skill[] certSkills = getCertSkills(player);
		final boolean hasCertSkills = certSkills != null;
		if (player.isSubClassActive())
		{
			if (hasCertSkills)
			{
				for (L2Skill s : certSkills)
				{
					Util.handleIllegalPlayerAction(player, "Player "+player.getName() + " has cert skill on subclass :" + s.getName() + "("+s.getId()+"/"+s.getLevel()+"), class:" + player.getTemplate().className, 0);
					
					if (Config.SKILL_CHECK_REMOVE)
						player.removeSkill(s);
				}
			}
			return null;
		}
		
		L2Skill skill;
		int[][] cSkills = null; // skillId/skillLvl
		if (hasCertSkills)
		{
			cSkills = new int[certSkills.length][2];
			for (int i = certSkills.length; --i >= 0;)
			{
				skill = certSkills[i];
				cSkills[i][0] = skill.getId();
				cSkills[i][1] = skill.getLevel();
			}
		}
		
		L2ItemInstance item;
		int[][] cItems = null; // objectId/number
		final L2ItemInstance[] certItems = getCertItems(player);
		final boolean hasCertItems = certItems != null;
		if (hasCertItems)
		{
			cItems = new int[certItems.length][2];
			for (int i = certItems.length; --i >= 0;)
			{
				item = certItems[i];
				cItems[i][0] = item.getObjectId();
				cItems[i][1] = (int)Math.min(item.getCount(), Integer.MAX_VALUE);
			}
		}
		
		QuestState st = player.getQuestState("SubClassSkills");
		if (st == null)
			st = newQuestState(player);
		
		String qName,qValue;
		int id, index;
		for (int i = VARS.length; --i >= 0;)
		{
			for (int j = Config.MAX_SUBCLASS; j > 0; j--)
			{
				qName = VARS[i]+String.valueOf(j);
				qValue = st.getGlobalQuestVar(qName);
				if (qValue == null || qValue.isEmpty())
					continue;
				
				if (qValue.endsWith(";")) // found skill
				{
					try
					{
						id = Integer.parseInt(qValue.replace(";", ""));
						
						skill = null;
						if (hasCertSkills)
						{
							// searching skill in test array
							for (index = certSkills.length; --index >= 0;)
							{
								if (cSkills[index][0] == id)
								{
									skill = certSkills[index];
									cSkills[index][1]--;
									break;
								}
							}
							if (skill != null)
							{
								if (!Util.contains(_certSkillsByLevel[i], id))
								{
									// should remove this skill ?
									Util.handleIllegalPlayerAction(player, "Invalid cert variable WITH skill:" + qName + "=" + qValue + " - skill does not match certificate level", 0);
								}
							}
							else
							{
								Util.handleIllegalPlayerAction(player, "Invalid cert variable:" + qName + "=" + qValue + " - skill not found", 0);
							}
						}
						else
						{
							Util.handleIllegalPlayerAction(player, "Invalid cert variable:" + qName + "=" + qValue + " - no certified skills found", 0);
						}
					}
					catch(NumberFormatException e)
					{
						Util.handleIllegalPlayerAction(player, "Invalid cert variable:" + qName + "=" + qValue + " - not a number", 0);
					}
				}
				else // found item
				{
					try
					{
						id = Integer.parseInt(qValue);
						if (id == 0) // canceled skill, no item
							continue;
						
						item = null;
						if (hasCertItems)
						{
							// searching item in test array
							for (index = certItems.length; --index >= 0;)
							{
								if (cItems[index][0] == id)
								{
									item = certItems[index];
									cItems[index][1]--;
									break;
								}
							}
							if (item != null)
							{
								if (!Util.contains(_certItemsByLevel[i], item.getItemId()))
								{
									Util.handleIllegalPlayerAction(player, "Invalid cert variable:" +
											qName + "=" + qValue + " - item found but does not match certificate level", 0);
								}
							}
							else
							{
								Util.handleIllegalPlayerAction(player, "Invalid cert variable:" +
										qName + "=" + qValue + " - item not found", 0);
							}
						}
						else
						{
							Util.handleIllegalPlayerAction(player, "Invalid cert variable:" +
									qName + "=" + qValue + " - no cert item found in inventory", 0);
						}
						
					}
					catch(NumberFormatException e)
					{
						Util.handleIllegalPlayerAction(player, "Invalid cert variable:" +
								qName + "=" + qValue + " - not a number", 0);
					}
				}
			}
		}
		
		if (hasCertSkills)
		{
			for (int i = cSkills.length; --i >= 0;)
			{
				if (cSkills[i][1] == 0)
					continue;
				
				skill = certSkills[i];
				if (cSkills[i][1] > 0)
				{
					if (cSkills[i][1] == skill.getLevel())
						Util.handleIllegalPlayerAction(player, "Player " + player.getName() +
								" has invalid cert skill :" + skill.getName() +
								"(" + skill.getId() + "/" + skill.getLevel() + ")", 0);
					else
						Util.handleIllegalPlayerAction(player, "Player " + player.getName() +
								" has invalid cert skill :" + skill.getName() +
								"(" + skill.getId() + "/" + skill.getLevel() + "), level too high", 0);
					
					if (Config.SKILL_CHECK_REMOVE)
						player.removeSkill(skill);
				}
				else
				{
					Util.handleIllegalPlayerAction(player, "Invalid cert skill :" + skill.getName() +
							"(" + skill.getId() + "/" + skill.getLevel() + "), level too low", 0);
				}
			}
		}
		
		if (hasCertItems)
		{
			for (int i = cItems.length; --i >= 0;)
			{
				if (cItems[i][1] == 0)
					continue;
				
				item = certItems[i];
				Util.handleIllegalPlayerAction(player, "Invalid cert item without variable or with wrong count:" + item.getObjectId(), 0);
			}
		}
		
		return null;
	}
	
	private L2Skill[] getCertSkills(L2PcInstance player)
	{
		FastList<L2Skill> tmp = null;
		for(L2Skill s : player.getAllSkills())
		{
			if (s != null && Arrays.binarySearch(_allCertSkillIds, s.getId()) >= 0)
			{
				if (tmp == null)
					tmp = FastList.newInstance();
				
				tmp.add(s);
			}
		}
		if (tmp == null)
			return null;
		
		final L2Skill[] result = tmp.toArray(new L2Skill[tmp.size()]);
		FastList.recycle(tmp);
		return result;
	}
	
	private L2ItemInstance[] getCertItems(L2PcInstance player)
	{
		FastList<L2ItemInstance> tmp = null;
		for (L2ItemInstance i : player.getInventory().getItems())
		{
			if (i != null && Arrays.binarySearch(_allCertItemIds, i.getItemId()) >= 0)
			{
				if (tmp == null)
					tmp = FastList.newInstance();
				
				tmp.add(i);
			}
		}
		if (tmp == null)
			return null;
		
		final L2ItemInstance[] result = tmp.toArray(new L2ItemInstance[tmp.size()]);
		FastList.recycle(tmp);
		return result;
	}
	
	public static void main(String[] args)
	{
		new SubClassSkills(-1, "SubClassSkills", "custom");
	}
}