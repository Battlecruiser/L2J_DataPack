/*
 * Copyright (C) 2004-2013 L2J DataPack
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
package handlers;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.gameserver.handler.EffectHandler;

import handlers.effecthandlers.*;

/**
 * Effect Master handler.
 * @author BiggBoss
 */
public final class EffectMasterHandler
{
	private static final Logger _log = Logger.getLogger(EffectMasterHandler.class.getName());
	
	private static final Class<?> LOAD_INSTANCES = EffectHandler.class;
	
	private static final Class<?>[] EFFECTS =
	{
		AddHate.class,
		Backstab.class,
		Betray.class,
		BigHead.class,
		Blink.class,
		BlockBuffSlot.class,
		BlockResurrection.class,
		Bluff.class,
		Buff.class,
		CallParty.class,
		CallPc.class,
		ChameleonRest.class,
		ChanceSkillTrigger.class,
		ChangeFace.class,
		ChangeFishingMastery.class,
		ChangeHairColor.class,
		ChangeHairStyle.class,
		CharmOfCourage.class,
		CharmOfLuck.class,
		ClanGate.class,
		Confusion.class,
		ConsumeBody.class,
		ConvertItem.class,
		CpDamPercent.class,
		CpHeal.class,
		CpHealOverTime.class,
		CpHealPercent.class,
		CrystalGradeModify.class,
		CubicMastery.class,
		DamOverTime.class,
		DamOverTimePercent.class,
		DeathLink.class,
		Debuff.class,
		DeleteHate.class,
		DeleteHateOfMe.class,
		Disarm.class,
		DispelAll.class,
		DispelByCategory.class,
		DispelBySlot.class,
		DispelBySlotProbability.class,
		EnemyCharge.class,
		EnergyAttack.class,
		EnlargeAbnormalSlot.class,
		Escape.class,
		FakeDeath.class,
		FatalBlow.class,
		Fear.class,
		FocusEnergy.class,
		FocusMaxEnergy.class,
		FocusSouls.class,
		Fusion.class,
		GetAgro.class,
		GiveSp.class,
		Grow.class,
		Harvesting.class,
		Heal.class,
		HealOverTime.class,
		HealPercent.class,
		Hide.class,
		HpByLevel.class,
		HpDrain.class,
		ImmobileBuff.class,
		ImmobilePetBuff.class,
		Invincible.class,
		Lethal.class,
		Lucky.class,
		MagicalAttack.class,
		MagicalAttackMp.class,
		MagicalSoulAttack.class,
		ManaDamOverTime.class,
		ManaHeal.class,
		ManaHealByLevel.class,
		ManaHealOverTime.class,
		ManaHealPercent.class,
		MpByLevel.class,
		MpConsumePerLevel.class,
		Mute.class,
		NoblesseBless.class,
		NpcKill.class,
		OpenCommonRecipeBook.class,
		OpenDwarfRecipeBook.class,
		Paralyze.class,
		Passive.class,
		Petrification.class,
		PhoenixBless.class,
		PhysicalAttack.class,
		PhysicalAttackHpLink.class,
		PhysicalAttackMute.class,
		PhysicalMute.class,
		PhysicalSoulAttack.class,
		ProtectionBlessing.class,
		RandomizeHate.class,
		RebalanceHP.class,
		Recovery.class,
		RefuelAirship.class,
		Relax.class,
		Restoration.class,
		RestorationRandom.class,
		Root.class,
		ServitorShare.class,
		SetSkill.class,
		Signet.class,
		SignetAntiSummon.class,
		SignetMDam.class,
		SignetNoise.class,
		SilentMove.class,
		SkillTurning.class,
		Sleep.class,
		SoulBlow.class,
		SoulEating.class,
		Spoil.class,
		StaticDamage.class,
		StealAbnormal.class,
		Stun.class,
		SummonAgathion.class,
		SummonCubic.class,
		SummonNpc.class,
		SummonPet.class,
		SummonTrap.class,
		Sweeper.class,
		TargetCancel.class,
		TargetMe.class,
		TargetMeProbability.class,
		Teleport.class,
		TeleportToTarget.class,
		ThrowUp.class,
		TransferDamage.class,
		TransferHate.class,
		Transformation.class,
		TriggerSkillByDamage.class,
		UnsummonAgathion.class,
		VitalityPointUp.class,
	};
	
	public static void main(String[] args)
	{
		Object loadInstance = null;
		Method method = null;
		
		try
		{
			method = LOAD_INSTANCES.getMethod("getInstance");
			loadInstance = method.invoke(LOAD_INSTANCES);
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Failed invoking getInstance method for handler: " + LOAD_INSTANCES.getSimpleName(), e);
			return;
		}
		
		method = null; // Releasing variable for next method
		
		for (Class<?> c : EFFECTS)
		{
			if (c == null)
			{
				continue; // Disabled handler
			}
			
			try
			{
				if (method == null)
				{
					method = loadInstance.getClass().getMethod("registerHandler", Class.class);
				}
				
				method.invoke(loadInstance, c);
				
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, "Failed loading effect handler: " + c.getSimpleName(), e);
				continue;
			}
		}
		
		// And lets try get size
		try
		{
			method = loadInstance.getClass().getMethod("size");
			Object returnVal = method.invoke(loadInstance);
			_log.log(Level.INFO, loadInstance.getClass().getSimpleName() + ": Loaded " + returnVal + " Handlers");
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Failed invoking size method for handler: " + loadInstance.getClass().getSimpleName(), e);
		}
	}
}
