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
		65,725,726,733,734,735,736,1374,1375,1538,1539,1540,1829,1830,
		3926,3927,3928,3929,3930,3931,3932,3933,3934,3935,3958,4218,4411,4412,
		4413,4414,4415,4417,5010,5134,5135,5136,5137,5138,5139,5140,
		5141,5142,5143,5144,5145,5146,5147,5148,5149,5150,5151,5234,
		5250,5251,5252,5253,5254,5255,5256,5257,5258,5259,5260,5261,
		5262,5263,5264,5265,5266,5267,5562,5563,5564,5565,5566,5583,
		5584,5585,5586,5587,5589,5591,5592,5593,5594,5595,5703,5803,
		5804,5805,5806,5807,5858,5859,5916,5944,5955,5966,5967,5968,5969,6007,
		6008,6009,6010,6035,6036,6037,6403,6406,6407,6411,6412,6413,
		6414,6415,6416,6417,6418,6419,6420,6421,6422,6423,6424,6425,
		6426,6427,6428,6429,6430,6431,6432,6433,6434,6435,6436,6437,
		6438,6439,6440,6441,6442,6443,6444,6445,6446,6447,6448,6449,
		6450,6451,6452,6453,6454,6455,6456,6457,6458,6459,6460,6461,
		6462,6463,6464,6465,6466,6467,6468,6469,6470,6471,6472,6473,
		6474,6475,6476,6477,6478,6479,6480,6481,6482,6483,6484,6485,
		6486,6487,6488,6489,6490,6491,6492,6493,6494,6495,6496,6497,
		6498,6499,6500,6501,6502,6503,6504,6505,6506,6507,6508,6509,
		6510,6511,6512,6513,6514,6515,6516,6517,6518,6652,6654,6655,
		6665,6666,6667,6668,6669,6670,6671,6672,6903,7061,7062,7117,
		7118,7119,7120,7121,7122,7123,7124,7125,7126,7127,7128,7129,
		7130,7131,7132,7133,7134,7135,7554,7555,7556,7557,7558,7559,
		7618,7619,7629,7630,7631,7632,7633,7634,7635,7636,7637,
		7725,7726,7727,7728,7729,7730,7731,7732,7733,7734,7735,7736,
		7737,7738,7739,7740,7741,7742,7743,7744,7745,7746,7747,7748,
		7749,7750,7751,7752,7753,7754,7755,7756,7757,7758,7759,7760,
		7761,7762,7763,7764,7765,7766,7767,7768,7769,7770,7771,7772,
		7773,7774,7775,7776,7777,7778,7779,7780,7781,7782,7783,7784,
		7785,7786,7787,7788,7789,7790,7791,7792,7793,7794,7795,7796,
		7797,7798,7799,7800,7801,7802,7803,7804,7805,7806,8060,8154,8155,
		8156,8157,8202,8403,8404,8405,8406,8407,8408,8409,8410,8411,
		8412,8413,8414,8415,8416,8417,8418,8419,8420,8421,8422,8423,
		8424,8425,8426,8427,8428,8429,8430,8431,8432,8433,8434,8435,
		8436,8437,8438,8439,8440,8441,8442,8443,8444,8445,8446,8447,
		8448,8449,8450,8451,8452,8453,8454,8455,8456,8457,8458,8459,
		8460,8461,8462,8463,8464,8465,8466,8467,8468,8469,8470,8471,
		8472,8473,8474,8475,8476,8477,8478,8479,8480,8481,8482,8483,
		8534,8535,8536,8537,8538,8539,8540,8555,8594,8595,8596,8597,
		8598,8599,8600,8601,8602,8603,8604,8605,8606,8607,8608,8609,
		8610,8611,8612,8613,8614,8952,8953,8954,8955,8956,9146,9147,
		9148,9149,9150,9151,9152,9153,9154,9155,9156,9647,9688,9689,
		9716,9897,9997,9998,9999,10000,10001,10002,10129,10130,10131,
		10132,10133,10134,10135,10136,10137,10138,10149,10151,10155,
		10157,10260,10261,10262,10263,
		10264,10265,10266,10267,10268,10269,10270,10274,10409,10432,
		10433,10515,10516,10517,10549,10550,10551,10552,10553,
		10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,
		10564,10565,10566,10567,10568,10569,10570,10571,10572,10573,
		10574,10575,10576,10577,10578,10579,10580,10581,10582,10583,
		10584,10585,10586,10587,10588,10589,10591,10592,10593,10594,
		10595,10608,10609,10610,10632,10650,10655,10656,10657,12768,
		12769,12770,12771,13032,13129,13258,13261,13262,13263,13264,
		13265,13266,13267,13268,13269,13386,13387,13388,13395,13396,
		13397,13398,13399,13400,13401,13402,13403,13404,13405,13406,
		13407,13408,13409,13410,13411,13412,13413,13414,13552,13553,
		13554,13728,13731,13732,13733,13734,13735,13736,13737,13738,
		13739,13844,14170,14171,14172,14173,14174,14175,14176,14177,
		14178,14179,14180,14181,14182,14183,14184,14185,14186,14187,
		14188,14189,14190,14191,14192,14193,14194,14195,14196,14197,
		14198,14199,14200,14201,14202,14203,14204,14205,14206,14207,
		14208,14209,14210,14211,14212,14213,14214,14215,14216,14217,
		14218,14219,14220,14221,14222,14223,14224,14225,14226,14227,
		20353,20364,20365,20366,20367,20368,20369,20370,20371,20372,
		20373,20374,20375,20376,20377,20378,20379,20380,20381,20382,
		20383,20384,20385,20386,20387,20388,20389,20390,22022,
		22023,22024,22025,22026,22037,22039,22040,22041,22042,22043,
		22044,22045,22046,22047,22048,22049,22050,22051,22052,22053,
		22087,22088,
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
