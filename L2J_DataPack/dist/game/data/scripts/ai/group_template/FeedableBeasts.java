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
package ai.group_template;

import java.util.Map;

import javolution.util.FastMap;
import quests.Q00020_BringUpWithLove.Q00020_BringUpWithLove;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.NpcData;
import com.l2jserver.gameserver.idfactory.IdFactory;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2TamedBeastInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Growth-capable mobs: Polymorphing upon successful feeding.
 * @author Fulminus
 */
public final class FeedableBeasts extends AbstractNpcAI
{
	private static final int GOLDEN_SPICE = 6643;
	private static final int CRYSTAL_SPICE = 6644;
	private static final int SKILL_GOLDEN_SPICE = 2188;
	private static final int SKILL_CRYSTAL_SPICE = 2189;
	private static final int FOODSKILLDIFF = GOLDEN_SPICE - SKILL_GOLDEN_SPICE;
	// @formatter:off
	private static final int[] TAMED_BEASTS =
	{
		16013, 16014, 16015, 16016, 16017, 16018
	};
	// all mobs that can eat...
	private static final int[] FEEDABLE_BEASTS =
	{
		21451, 21452, 21453, 21454, 21455, 21456, 21457, 21458, 21459, 21460,
		21461, 21462, 21463, 21464, 21465, 21466, 21467, 21468, 21469, 21470,
		21471, 21472, 21473, 21474, 21475, 21476, 21477, 21478, 21479, 21480,
		21481, 21482, 21483, 21484, 21485, 21486, 21487, 21488, 21489, 21490,
		21491, 21492, 21493, 21494, 21495, 21496, 21497, 21498, 21499, 21500,
		21501, 21502, 21503, 21504, 21505, 21506, 21507, 21824, 21825, 21826,
		21827, 21828, 21829, 16013, 16014, 16015, 16016, 16017, 16018
	};
	// @formatter:on
	
	private static final Map<Integer, Integer> MAD_COW_POLYMORPH = new FastMap<>();
	static
	{
		MAD_COW_POLYMORPH.put(21824, 21468);
		MAD_COW_POLYMORPH.put(21825, 21469);
		MAD_COW_POLYMORPH.put(21826, 21487);
		MAD_COW_POLYMORPH.put(21827, 21488);
		MAD_COW_POLYMORPH.put(21828, 21506);
		MAD_COW_POLYMORPH.put(21829, 21507);
	}
	
	private static final NpcStringId[][] TEXT =
	{
		{
			NpcStringId.WHAT_DID_YOU_JUST_DO_TO_ME,
			NpcStringId.ARE_YOU_TRYING_TO_TAME_ME_DON_T_DO_THAT,
			NpcStringId.DON_T_GIVE_SUCH_A_THING_YOU_CAN_ENDANGER_YOURSELF,
			NpcStringId.YUCK_WHAT_IS_THIS_IT_TASTES_TERRIBLE,
			NpcStringId.I_M_HUNGRY_GIVE_ME_A_LITTLE_MORE_PLEASE,
			NpcStringId.WHAT_IS_THIS_IS_THIS_EDIBLE,
			NpcStringId.DON_T_WORRY_ABOUT_ME,
			NpcStringId.THANK_YOU_THAT_WAS_DELICIOUS,
			NpcStringId.I_THINK_I_AM_STARTING_TO_LIKE_YOU,
			NpcStringId.EEEEEK_EEEEEK
		},
		{
			NpcStringId.DON_T_KEEP_TRYING_TO_TAME_ME_I_DON_T_WANT_TO_BE_TAMED,
			NpcStringId.IT_IS_JUST_FOOD_TO_ME_ALTHOUGH_IT_MAY_ALSO_BE_YOUR_HAND,
			NpcStringId.IF_I_KEEP_EATING_LIKE_THIS_WON_T_I_BECOME_FAT_CHOMP_CHOMP,
			NpcStringId.WHY_DO_YOU_KEEP_FEEDING_ME,
			NpcStringId.DON_T_TRUST_ME_I_M_AFRAID_I_MAY_BETRAY_YOU_LATER
		},
		{
			NpcStringId.GRRRRR,
			NpcStringId.YOU_BROUGHT_THIS_UPON_YOURSELF,
			NpcStringId.I_FEEL_STRANGE_I_KEEP_HAVING_THESE_EVIL_THOUGHTS,
			NpcStringId.ALAS_SO_THIS_IS_HOW_IT_ALL_ENDS,
			NpcStringId.I_DON_T_FEEL_SO_GOOD_OH_MY_MIND_IS_VERY_TROUBLED
		}
	};
	
	private static final NpcStringId[] TAMED_TEXT =
	{
		NpcStringId.S1_SO_WHAT_DO_YOU_THINK_IT_IS_LIKE_TO_BE_TAMED,
		NpcStringId.S1_WHENEVER_I_SEE_SPICE_I_THINK_I_WILL_MISS_YOUR_HAND_THAT_USED_TO_FEED_IT_TO_ME,
		NpcStringId.S1_DON_T_GO_TO_THE_VILLAGE_I_DON_T_HAVE_THE_STRENGTH_TO_FOLLOW_YOU,
		NpcStringId.THANK_YOU_FOR_TRUSTING_ME_S1_I_HOPE_I_WILL_BE_HELPFUL_TO_YOU,
		NpcStringId.S1_WILL_I_BE_ABLE_TO_HELP_YOU,
		NpcStringId.I_GUESS_IT_S_JUST_MY_ANIMAL_MAGNETISM,
		NpcStringId.TOO_MUCH_SPICY_FOOD_MAKES_ME_SWEAT_LIKE_A_BEAST,
		NpcStringId.ANIMALS_NEED_LOVE_TOO
	};
	
	private static Map<Integer, Integer> _FeedInfo = new FastMap<>();
	private static Map<Integer, GrowthCapableMob> _GrowthCapableMobs = new FastMap<>();
	
	// all mobs that grow by eating
	private static class GrowthCapableMob
	{
		private final int _growthLevel;
		private final int _chance;
		
		private final Map<Integer, int[][]> _spiceToMob = new FastMap<>();
		
		public GrowthCapableMob(int growthLevel, int chance)
		{
			_growthLevel = growthLevel;
			_chance = chance;
		}
		
		public void addMobs(int spice, int[][] Mobs)
		{
			_spiceToMob.put(spice, Mobs);
		}
		
		public Integer getMob(int spice, int mobType, int classType)
		{
			if (_spiceToMob.containsKey(spice))
			{
				return _spiceToMob.get(spice)[mobType][classType];
			}
			return null;
		}
		
		public Integer getRandomMob(int spice)
		{
			int[][] temp;
			temp = _spiceToMob.get(spice);
			int rand = getRandom(temp[0].length);
			return temp[0][rand];
		}
		
		public Integer getChance()
		{
			return _chance;
		}
		
		public Integer getGrowthLevel()
		{
			return _growthLevel;
		}
	}
	
	private FeedableBeasts()
	{
		super(FeedableBeasts.class.getSimpleName(), "ai/group_template");
		addKillId(FEEDABLE_BEASTS);
		addSkillSeeId(FEEDABLE_BEASTS);
		
		// TODO: no grendels?
		GrowthCapableMob temp;
		
		//@formatter:off
		final int[][] Kookabura_0_Gold = {{ 21452, 21453, 21454, 21455 }};
		final int[][] Kookabura_0_Crystal = {{ 21456, 21457, 21458, 21459 }};
		final int[][] Kookabura_1_Gold_1= {{ 21460, 21462 }};
		final int[][] Kookabura_1_Gold_2 = {{ 21461, 21463 }};
		final int[][] Kookabura_1_Crystal_1 = {{ 21464, 21466 }};
		final int[][] Kookabura_1_Crystal_2 = {{ 21465, 21467 }};
		final int[][] Kookabura_2_1 = {{ 21468, 21824}, { 16017, 16018 }};
		final int[][] Kookabura_2_2 = {{ 21469, 21825}, { 16017, 16018 }};
		
		final int[][] Buffalo_0_Gold = {{ 21471, 21472, 21473, 21474 }};
		final int[][] Buffalo_0_Crystal = {{ 21475, 21476, 21477, 21478 }};
		final int[][] Buffalo_1_Gold_1 = {{ 21479, 21481 }};
		final int[][] Buffalo_1_Gold_2 = {{ 21481, 21482 }};
		final int[][] Buffalo_1_Crystal_1 = {{ 21483, 21485 }};
		final int[][] Buffalo_1_Crystal_2 = {{ 21484, 21486 }};
		final int[][] Buffalo_2_1 = {{ 21487,21826}, {16013, 16014 }};
		final int[][] Buffalo_2_2 = {{ 21488,21827}, {16013, 16014 }};
		
		final int[][] Cougar_0_Gold = {{ 21490, 21491, 21492, 21493 }};
		final int[][] Cougar_0_Crystal = {{ 21494,21495, 21496, 21497 }};
		final int[][] Cougar_1_Gold_1 = {{ 21498, 21500 }};
		final int[][] Cougar_1_Gold_2 = {{ 21499, 21501 }};
		final int[][] Cougar_1_Crystal_1 = {{ 21502,21504 }};
		final int[][] Cougar_1_Crystal_2 = {{ 21503,21505 }};
		final int[][] Cougar_2_1 = {{ 21506, 21828 }, { 16015,16016 }};
		final int[][] Cougar_2_2 = {{ 21507, 21829 }, { 16015,16016 }};
		//@formatter:on
		
		// Alpen Kookabura
		temp = new GrowthCapableMob(0, 100);
		temp.addMobs(GOLDEN_SPICE, Kookabura_0_Gold);
		temp.addMobs(CRYSTAL_SPICE, Kookabura_0_Crystal);
		_GrowthCapableMobs.put(21451, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Kookabura_1_Gold_1);
		_GrowthCapableMobs.put(21452, temp);
		_GrowthCapableMobs.put(21454, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Kookabura_1_Gold_2);
		_GrowthCapableMobs.put(21453, temp);
		_GrowthCapableMobs.put(21455, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Kookabura_1_Crystal_1);
		_GrowthCapableMobs.put(21456, temp);
		_GrowthCapableMobs.put(21458, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Kookabura_1_Crystal_2);
		_GrowthCapableMobs.put(21457, temp);
		_GrowthCapableMobs.put(21459, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Kookabura_2_1);
		_GrowthCapableMobs.put(21460, temp);
		_GrowthCapableMobs.put(21462, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Kookabura_2_2);
		_GrowthCapableMobs.put(21461, temp);
		_GrowthCapableMobs.put(21463, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Kookabura_2_1);
		_GrowthCapableMobs.put(21464, temp);
		_GrowthCapableMobs.put(21466, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Kookabura_2_2);
		_GrowthCapableMobs.put(21465, temp);
		_GrowthCapableMobs.put(21467, temp);
		
		// Alpen Buffalo
		temp = new GrowthCapableMob(0, 100);
		temp.addMobs(GOLDEN_SPICE, Buffalo_0_Gold);
		temp.addMobs(CRYSTAL_SPICE, Buffalo_0_Crystal);
		_GrowthCapableMobs.put(21470, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Buffalo_1_Gold_1);
		_GrowthCapableMobs.put(21471, temp);
		_GrowthCapableMobs.put(21473, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Buffalo_1_Gold_2);
		_GrowthCapableMobs.put(21472, temp);
		_GrowthCapableMobs.put(21474, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Buffalo_1_Crystal_1);
		_GrowthCapableMobs.put(21475, temp);
		_GrowthCapableMobs.put(21477, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Buffalo_1_Crystal_2);
		_GrowthCapableMobs.put(21476, temp);
		_GrowthCapableMobs.put(21478, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Buffalo_2_1);
		_GrowthCapableMobs.put(21479, temp);
		_GrowthCapableMobs.put(21481, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Buffalo_2_2);
		_GrowthCapableMobs.put(21480, temp);
		_GrowthCapableMobs.put(21482, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Buffalo_2_1);
		_GrowthCapableMobs.put(21483, temp);
		_GrowthCapableMobs.put(21485, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Buffalo_2_2);
		_GrowthCapableMobs.put(21484, temp);
		_GrowthCapableMobs.put(21486, temp);
		
		// Alpen Cougar
		temp = new GrowthCapableMob(0, 100);
		temp.addMobs(GOLDEN_SPICE, Cougar_0_Gold);
		temp.addMobs(CRYSTAL_SPICE, Cougar_0_Crystal);
		_GrowthCapableMobs.put(21489, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Cougar_1_Gold_1);
		_GrowthCapableMobs.put(21490, temp);
		_GrowthCapableMobs.put(21492, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(GOLDEN_SPICE, Cougar_1_Gold_2);
		_GrowthCapableMobs.put(21491, temp);
		_GrowthCapableMobs.put(21493, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Cougar_1_Crystal_1);
		_GrowthCapableMobs.put(21494, temp);
		_GrowthCapableMobs.put(21496, temp);
		
		temp = new GrowthCapableMob(1, 40);
		temp.addMobs(CRYSTAL_SPICE, Cougar_1_Crystal_2);
		_GrowthCapableMobs.put(21495, temp);
		_GrowthCapableMobs.put(21497, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Cougar_2_1);
		_GrowthCapableMobs.put(21498, temp);
		_GrowthCapableMobs.put(21500, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(GOLDEN_SPICE, Cougar_2_2);
		_GrowthCapableMobs.put(21499, temp);
		_GrowthCapableMobs.put(21501, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Cougar_2_1);
		_GrowthCapableMobs.put(21502, temp);
		_GrowthCapableMobs.put(21504, temp);
		
		temp = new GrowthCapableMob(2, 25);
		temp.addMobs(CRYSTAL_SPICE, Cougar_2_2);
		_GrowthCapableMobs.put(21503, temp);
		_GrowthCapableMobs.put(21505, temp);
	}
	
	private void spawnNext(L2Npc npc, int growthLevel, L2PcInstance player, int food)
	{
		int npcId = npc.getId();
		int nextNpcId = 0;
		
		// find the next mob to spawn, based on the current npcId, growthlevel, and food.
		if (growthLevel == 2)
		{
			// if tamed, the mob that will spawn depends on the class type (fighter/mage) of the player!
			if (getRandom(2) == 0)
			{
				if (player.getClassId().isMage())
				{
					nextNpcId = _GrowthCapableMobs.get(npcId).getMob(food, 1, 1);
				}
				else
				{
					nextNpcId = _GrowthCapableMobs.get(npcId).getMob(food, 1, 0);
				}
			}
			else
			{
				// if not tamed, there is a small chance that have "mad cow" disease.
				// that is a stronger-than-normal animal that attacks its feeder
				if (getRandom(5) == 0)
				{
					nextNpcId = _GrowthCapableMobs.get(npcId).getMob(food, 0, 1);
				}
				else
				{
					nextNpcId = _GrowthCapableMobs.get(npcId).getMob(food, 0, 0);
				}
			}
		}
		else
		{
			// all other levels of growth are straight-forward
			nextNpcId = _GrowthCapableMobs.get(npcId).getRandomMob(food);
		}
		
		// remove the feedinfo of the mob that got despawned, if any
		if (_FeedInfo.containsKey(npc.getObjectId()))
		{
			if (_FeedInfo.get(npc.getObjectId()) == player.getObjectId())
			{
				_FeedInfo.remove(npc.getObjectId());
			}
		}
		// despawn the old mob
		// TODO: same code? FIXED?
		// @formatter:off
		/*
		 * if (_GrowthCapableMobs.get(npcId).getGrowthLevel() == 0)
			{
				npc.deleteMe();
			}
			else 
			{
		 */
		npc.deleteMe();
		// }
		// @formatter:on
		
		// if this is finally a trained mob, then despawn any other trained mobs that the
		// player might have and initialize the Tamed Beast.
		if (Util.contains(TAMED_BEASTS, nextNpcId))
		{
			if ((player.getTrainedBeasts() != null) && !player.getTrainedBeasts().isEmpty())
			{
				for (L2TamedBeastInstance oldTrained : player.getTrainedBeasts())
				{
					oldTrained.deleteMe();
				}
			}
			
			L2NpcTemplate template = NpcData.getInstance().getTemplate(nextNpcId);
			L2TamedBeastInstance nextNpc = new L2TamedBeastInstance(IdFactory.getInstance().getNextId(), template, player, food - FOODSKILLDIFF, npc.getX(), npc.getY(), npc.getZ());
			nextNpc.setRunning();
			Q00020_BringUpWithLove.checkJewelOfInnocence(player);
			
			// also, perform a rare random chat
			if (getRandom(20) == 0)
			{
				NpcStringId message = NpcStringId.getNpcStringId(getRandom(2024, 2029));
				NpcSay packet = new NpcSay(nextNpc, 0, message);
				if (message.getParamCount() > 0) // player name, $s1
				{
					packet.addStringParameter(player.getName());
				}
				npc.broadcastPacket(packet);
			}
			// @formatter:off
			/*
			TODO: The tamed beast consumes one golden/crystal spice
			every 60 seconds with an initial delay of 60 seconds
			if (tamed beast exists and is alive)
			{
				if (player has 1+ golden/crystal spice)
				{
					take one golden/crystal spice;
					say random NpcString(getRandom(2029, 2038));
				}
			}
			*/
			// @formatter:on
		}
		else
		{
			// if not trained, the newly spawned mob will automatically be aggro against its feeder
			// (what happened to "never bite the hand that feeds you" anyway?!)
			L2Attackable nextNpc = (L2Attackable) addSpawn(nextNpcId, npc);
			
			if (MAD_COW_POLYMORPH.containsKey(nextNpcId))
			{
				this.startQuestTimer("polymorph Mad Cow", 10000, nextNpc, player);
			}
			
			// register the player in the feedinfo for the mob that just spawned
			_FeedInfo.put(nextNpc.getObjectId(), player.getObjectId());
			nextNpc.setRunning();
			nextNpc.addDamageHate(player, 0, 99999);
			nextNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("polymorph Mad Cow") && (npc != null) && (player != null))
		{
			if (MAD_COW_POLYMORPH.containsKey(npc.getId()))
			{
				// remove the feed info from the previous mob
				if (_FeedInfo.get(npc.getObjectId()) == player.getObjectId())
				{
					_FeedInfo.remove(npc.getObjectId());
				}
				// despawn the mad cow
				npc.deleteMe();
				// spawn the new mob
				L2Attackable nextNpc = (L2Attackable) addSpawn(MAD_COW_POLYMORPH.get(npc.getId()), npc);
				
				// register the player in the feedinfo for the mob that just spawned
				_FeedInfo.put(nextNpc.getObjectId(), player.getObjectId());
				nextNpc.setRunning();
				nextNpc.addDamageHate(player, 0, 99999);
				nextNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, Skill skill, L2Object[] targets, boolean isSummon)
	{
		// this behavior is only run when the target of skill is the passed npc (chest)
		// i.e. when the player is attempting to open the chest using a skill
		if (!Util.contains(targets, npc))
		{
			return super.onSkillSee(npc, caster, skill, targets, isSummon);
		}
		// gather some values on local variables
		int npcId = npc.getId();
		int skillId = skill.getId();
		// check if the npc and skills used are valid for this script. Exit if invalid.
		if ((skillId != SKILL_GOLDEN_SPICE) && (skillId != SKILL_CRYSTAL_SPICE))
		{
			return super.onSkillSee(npc, caster, skill, targets, isSummon);
		}
		
		// first gather some values on local variables
		int objectId = npc.getObjectId();
		int growthLevel = 3; // if a mob is in FEEDABLE_BEASTS but not in _GrowthCapableMobs, then it's at max growth (3)
		if (_GrowthCapableMobs.containsKey(npcId))
		{
			growthLevel = _GrowthCapableMobs.get(npcId).getGrowthLevel();
		}
		
		// prevent exploit which allows 2 players to simultaneously raise the same 0-growth beast
		// If the mob is at 0th level (when it still listens to all feeders) lock it to the first feeder!
		if ((growthLevel == 0) && _FeedInfo.containsKey(objectId))
		{
			return super.onSkillSee(npc, caster, skill, targets, isSummon);
		}
		
		_FeedInfo.put(objectId, caster.getObjectId());
		
		int food = 0;
		if (skillId == SKILL_GOLDEN_SPICE)
		{
			food = GOLDEN_SPICE;
		}
		else if (skillId == SKILL_CRYSTAL_SPICE)
		{
			food = CRYSTAL_SPICE;
		}
		
		// display the social action of the beast eating the food.
		npc.broadcastSocialAction(2);
		
		// if this pet can't grow, it's all done.
		if (_GrowthCapableMobs.containsKey(npcId))
		{
			// do nothing if this mob doesn't eat the specified food (food gets consumed but has no effect).
			if (_GrowthCapableMobs.get(npcId).getMob(food, 0, 0) == null)
			{
				return super.onSkillSee(npc, caster, skill, targets, isSummon);
			}
			
			// rare random talk...
			if (getRandom(20) == 0)
			{
				NpcStringId message = TEXT[growthLevel][getRandom(TEXT[growthLevel].length)];
				NpcSay packet = new NpcSay(npc, 0, message);
				if (message.getParamCount() > 0) // player name, $s1
				{
					packet.addStringParameter(caster.getName());
				}
				npc.broadcastPacket(packet);
			}
			
			if ((growthLevel > 0) && (_FeedInfo.get(objectId) != caster.getObjectId()))
			{
				// check if this is the same player as the one who raised it from growth 0.
				// if no, then do not allow a chance to raise the pet (food gets consumed but has no effect).
				return super.onSkillSee(npc, caster, skill, targets, isSummon);
			}
			
			// Polymorph the mob, with a certain chance, given its current growth level
			if (getRandom(100) < _GrowthCapableMobs.get(npcId).getChance())
			{
				spawnNext(npc, growthLevel, caster, food);
			}
		}
		else if (Util.contains(TAMED_BEASTS, npcId) && (npc instanceof L2TamedBeastInstance))
		{
			L2TamedBeastInstance beast = ((L2TamedBeastInstance) npc);
			if (skillId == beast.getFoodType())
			{
				beast.onReceiveFood();
				NpcStringId message = TAMED_TEXT[getRandom(TAMED_TEXT.length)];
				NpcSay packet = new NpcSay(npc, 0, message);
				if (message.getParamCount() > 0)
				{
					packet.addStringParameter(caster.getName());
				}
				beast.broadcastPacket(packet);
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		// remove the feedinfo of the mob that got killed, if any
		if (_FeedInfo.containsKey(npc.getObjectId()))
		{
			_FeedInfo.remove(npc.getObjectId());
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new FeedableBeasts();
	}
}
