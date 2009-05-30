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
package handlers.itemhandlers;

import javolution.util.FastMap;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SummonInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance.TimeStamp;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.item.L2EtcItemType;


public class ItemSkills implements IItemHandler
{
	// TODO: unhardcode item ids
	private static final int[] ITEM_IDS =
	{
		65,725,726,733,734,735,1374,1375,1539,1540,3926,3927,3928,3929,
		3930,3931,3932,3933,3934,3935,4218,4411,4412,
		4413,4414,4415,4417,5010,5234,5562,5563,5564,5565,5566,5583,
		5584,5585,5586,5587,5589,5591,5592,5593,5594,5595,5703,5803,
		5804,5805,5806,5807,6035,6036,6037,6403,6406,6407,6652,6654,6655,6903,
		7061,7062,8154,8155,8156,8157,8202,8555,8594,8595,8596,8597,
		8598,8599,8600,8601,8602,8603,8604,8605,8606,8607,8608,8609,
		8610,8611,8612,8613,8614,8952,8953,8954,8955,8956,9146,9147,
		9148,9149,9150,9151,9152,9153,9154,9155,9688,9689,9897,9997,
		9998,9999,10000,10001,10002,10131,10132,10133,10134,10135,
		10136,10137,10138,10151,10155,10157,10260,
		10261,10262,10263,10264,10265,10266,10267,10268,10269,10270,
		10274,10409,10432,10433,10549,10550,10551,10552,10553,
		10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,
		10564,10565,10566,10567,10568,10569,10570,10571,10572,10573,
		10574,10575,10576,10577,10578,10579,10580,10581,10582,10583,
		10584,10585,10586,10587,10588,10589,10591,10592,10593,10594,
		10595,10608,10609,10610,10655,10656,10657,
		12768,12769,12770,12771,13032,13261,13262,13263,13264,13265,
		13266,13267,13268,13269,13386,13387,13388,13552,13553,13554,
		13728,13844,14170,14171,14172,14173,14174,14175,14176,14177,
		14178,14179,14180,14181,14182,14183,14184,14185,14186,14187,
		14188,14189,14190,14191,14192,14193,14194,14195,14196,14197,
		14198,14199,14200,14201,14202,14203,14204,14205,14206,14207,
		14208,14209,14210,14211,14212,14213,14214,14215,14216,14217,
		14218,14219,14220,14221,14222,14223,14224,14225,14226,14227,
		20353,20364,20365,20366,20367,20368,20369,20370,20371,22022,
		22023,22024,22025,22026,22037,22039,22040,22041,22042,22043,
		22044,22045,22046,22047,22048,22049,22050,22051,22052,22053,
		22089,22090,22091,22092,22093,22094,22095,22096,22097,22098,
		22099,22100,22101,22102,22103,22104,22105,22106,22107,22108,
		22109,22110,22111,22112,22113,22114,22115,22116,22117,22118,
		22119,22120,22121,22122,22123,22124,22125,22126,22127,22128,
		22129,22130,22131,22132,22133,22134,22135,22136,22137,22138,
		22139,22140,22141,22142,22143,22149,22150,22151,22152,22153
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		L2PcInstance activeChar; // use activeChar only for L2PcInstance checks where cannot be used PetInstance
		boolean isPet = playable instanceof L2PetInstance;
		if (playable instanceof L2PcInstance)
			activeChar = (L2PcInstance) playable;
		else if (isPet)
			activeChar = ((L2PetInstance) playable).getOwner();
		else
			return;
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		if (!TvTEvent.onScrollUse(playable.getObjectId()))
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		int skillId;
		int skillLvl;

		final String[] skills = item.getEtcItem().getSkills();
		if (skills != null)
		{
			for (String skillInfo : skills)
			{
				String[] skill = skillInfo.split("-");
				if (skill != null && skill.length == 2)
				{
					skillId = Integer.parseInt(skill[0]);
					skillLvl = Integer.parseInt(skill[1]);
					if (skillId > 0 && skillLvl > 0)
					{
						L2Skill itemSkill = SkillTable.getInstance().getInfo(skillId, skillLvl);
						if (itemSkill != null)
						{
							if (!itemSkill.checkCondition(playable, playable, false))
					        	return;
							if ( playable.isSkillDisabled(skillId))
							{
								reuse(activeChar,itemSkill);
								return ;
							}
							// pets can use items only when they are tradeable
							if (isPet && !item.isTradeable())
								activeChar.sendPacket(new SystemMessage(SystemMessageId.ITEM_NOT_FOR_PETS));
							else
							{
								// send message to owner
								if (isPet)
								{
									SystemMessage sm = new SystemMessage(SystemMessageId.PET_USES_S1);
									sm.addString(itemSkill.getName());
									activeChar.sendPacket(sm);
								}
								else
								{
									switch (skillId)
									{
										// short buff icon for healing potions
										case 2031:
										case 2032:
										case 2037:
										case 26025:
										case 26026:
											int buffId = activeChar._shortBuffTaskSkillId;
											// greater healing potions
											if (skillId == 2037 || skillId == 26025)
												activeChar.shortBuffStatusUpdate(skillId, skillLvl, itemSkill.getBuffDuration()/1000);
											// healing potions
											else if ((skillId == 2032 || skillId == 26026) && buffId !=2037 && buffId != 26025)
												activeChar.shortBuffStatusUpdate(skillId, skillLvl, itemSkill.getBuffDuration()/1000);
											// lesser healing potions
											else
											{
												if (buffId != 2037 && buffId != 26025 && buffId != 2032 && buffId != 26026)
													activeChar.shortBuffStatusUpdate(skillId, skillLvl, itemSkill.getBuffDuration()/1000);
											}
											break;
									}
								}
								if (itemSkill.isPotion())
								{
									playable.doSimultaneousCast(itemSkill);
									// Summons should be affected by herbs too, self time effect is handled at L2Effect constructor
									if (!isPet && item.getItemType() == L2EtcItemType.HERB && activeChar.getPet() != null && activeChar.getPet() instanceof L2SummonInstance)
										activeChar.getPet().doSimultaneousCast(itemSkill);
								}
								else
								{
									playable.stopMove(null);
									playable.doCast(itemSkill);
								}
								if (itemSkill.getReuseDelay() > 0)
									activeChar.addTimeStamp(skillId, itemSkill.getReuseDelay());
							}
						}
					}
				}
			}
		}
	}
	
	private void reuse(L2PcInstance player,L2Skill skill)
	{
		SystemMessage sm = null;
    	FastMap<Integer, TimeStamp> timeStamp = player.getReuseTimeStamp();
			
    	if (timeStamp != null && timeStamp.containsKey(skill.getId()))
    	{
    		int remainingTime = (int)(player.getReuseTimeStamp().get(skill.getId()).getRemaining()/1000);
    		int minutes = (remainingTime%3600)/60;
    		int seconds = (remainingTime%60);
    		if (minutes > 0)
    		{
    			sm = new SystemMessage(SystemMessageId.S2_MINUTES_S3_SECONDS_REMAINING_FOR_REUSE_S1);
    			sm.addSkillName(skill);
    			sm.addNumber(minutes);
    		}
    		else
    		{
    			sm = new SystemMessage(SystemMessageId.S2_SECONDS_REMAINING_FOR_REUSE_S1);
    			sm.addSkillName(skill);
    		}
    		sm.addNumber(seconds);
    	}
    	else
    	{
    		sm = new SystemMessage(SystemMessageId.S1_PREPARED_FOR_REUSE);
    		sm.addSkillName(skill);
    	}
    	player.sendPacket(sm);
	}
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}
