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
package handlers;

import handlers.effecthandlers.EffectAbortCast;
import handlers.effecthandlers.EffectBetray;
import handlers.effecthandlers.EffectBigHead;
import handlers.effecthandlers.EffectBlockResurrection;
import handlers.effecthandlers.EffectBluff;
import handlers.effecthandlers.EffectBuff;
import handlers.effecthandlers.EffectCancel;
import handlers.effecthandlers.EffectCancelAll;
import handlers.effecthandlers.EffectCancelDebuff;
import handlers.effecthandlers.EffectChameleonRest;
import handlers.effecthandlers.EffectChanceSkillTrigger;
import handlers.effecthandlers.EffectCharmOfCourage;
import handlers.effecthandlers.EffectCharmOfLuck;
import handlers.effecthandlers.EffectClanGate;
import handlers.effecthandlers.EffectCombatPointHealOverTime;
import handlers.effecthandlers.EffectConfuseMob;
import handlers.effecthandlers.EffectConfusion;
import handlers.effecthandlers.EffectCpDamPercent;
import handlers.effecthandlers.EffectCpHeal;
import handlers.effecthandlers.EffectCpHealPercent;
import handlers.effecthandlers.EffectDamOverTime;
import handlers.effecthandlers.EffectDebuff;
import handlers.effecthandlers.EffectDisarm;
import handlers.effecthandlers.EffectDispelBySlot;
import handlers.effecthandlers.EffectEnemyCharge;
import handlers.effecthandlers.EffectFakeDeath;
import handlers.effecthandlers.EffectFear;
import handlers.effecthandlers.EffectFusion;
import handlers.effecthandlers.EffectGrow;
import handlers.effecthandlers.EffectHeal;
import handlers.effecthandlers.EffectHealOverTime;
import handlers.effecthandlers.EffectHealPercent;
import handlers.effecthandlers.EffectHide;
import handlers.effecthandlers.EffectImmobileBuff;
import handlers.effecthandlers.EffectImmobilePetBuff;
import handlers.effecthandlers.EffectIncreaseCharges;
import handlers.effecthandlers.EffectInvincible;
import handlers.effecthandlers.EffectManaDamOverTime;
import handlers.effecthandlers.EffectManaHeal;
import handlers.effecthandlers.EffectManaHealOverTime;
import handlers.effecthandlers.EffectManaHealPercent;
import handlers.effecthandlers.EffectMpConsumePerLevel;
import handlers.effecthandlers.EffectMute;
import handlers.effecthandlers.EffectNegate;
import handlers.effecthandlers.EffectNoblesseBless;
import handlers.effecthandlers.EffectParalyze;
import handlers.effecthandlers.EffectPetrification;
import handlers.effecthandlers.EffectPhoenixBless;
import handlers.effecthandlers.EffectPhysicalAttackMute;
import handlers.effecthandlers.EffectPhysicalMute;
import handlers.effecthandlers.EffectProtectionBlessing;
import handlers.effecthandlers.EffectRandomizeHate;
import handlers.effecthandlers.EffectRecovery;
import handlers.effecthandlers.EffectRelax;
import handlers.effecthandlers.EffectRemoveTarget;
import handlers.effecthandlers.EffectRoot;
import handlers.effecthandlers.EffectSignet;
import handlers.effecthandlers.EffectSignetAntiSummon;
import handlers.effecthandlers.EffectSignetMDam;
import handlers.effecthandlers.EffectSignetNoise;
import handlers.effecthandlers.EffectSilentMove;
import handlers.effecthandlers.EffectSleep;
import handlers.effecthandlers.EffectSpoil;
import handlers.effecthandlers.EffectStun;
import handlers.effecthandlers.EffectTargetMe;
import handlers.effecthandlers.EffectThrowUp;
import handlers.effecthandlers.EffectTransferDamage;
import handlers.effecthandlers.EffectTransformation;
import handlers.effecthandlers.EffectWarp;

import com.l2jserver.gameserver.handler.EffectHandler;

/**
 * @author BiggBoss
 */
public final class EffectMasterHandler
{
	private static void loadEffectHandlers()
	{
		EffectHandler.getInstance().registerHandler("AbortCast", EffectAbortCast.class);
		EffectHandler.getInstance().registerHandler("Betray", EffectBetray.class);
		EffectHandler.getInstance().registerHandler("BigHead", EffectBigHead.class);
		EffectHandler.getInstance().registerHandler("BlockResurrection", EffectBlockResurrection.class);
		EffectHandler.getInstance().registerHandler("Bluff", EffectBluff.class);
		EffectHandler.getInstance().registerHandler("Buff", EffectBuff.class);
		EffectHandler.getInstance().registerHandler("Cancel", EffectCancel.class);
		EffectHandler.getInstance().registerHandler("CancelAll", EffectCancelAll.class);
		EffectHandler.getInstance().registerHandler("CancelDebuff", EffectCancelDebuff.class);
		EffectHandler.getInstance().registerHandler("ChameleonRest", EffectChameleonRest.class);
		EffectHandler.getInstance().registerHandler("ChanceSkillTrigger", EffectChanceSkillTrigger.class);
		EffectHandler.getInstance().registerHandler("CharmOfCourage", EffectCharmOfCourage.class);
		EffectHandler.getInstance().registerHandler("CharmOfLuck", EffectCharmOfLuck.class);
		EffectHandler.getInstance().registerHandler("ClanGate", EffectClanGate.class);
		EffectHandler.getInstance().registerHandler("CombatPointHealOverTime", EffectCombatPointHealOverTime.class);
		EffectHandler.getInstance().registerHandler("ConfuseMob", EffectConfuseMob.class);
		EffectHandler.getInstance().registerHandler("Confusion", EffectConfusion.class);
		EffectHandler.getInstance().registerHandler("CpDamPercent", EffectCpDamPercent.class);
		EffectHandler.getInstance().registerHandler("CpHealPercent", EffectCpHealPercent.class);
		EffectHandler.getInstance().registerHandler("CpHeal", EffectCpHeal.class);
		EffectHandler.getInstance().registerHandler("DamOverTime", EffectDamOverTime.class);
		EffectHandler.getInstance().registerHandler("Debuff", EffectDebuff.class);
		EffectHandler.getInstance().registerHandler("DispelBySlot", EffectDispelBySlot.class);
		EffectHandler.getInstance().registerHandler("Disarm", EffectDisarm.class);
		EffectHandler.getInstance().registerHandler("EnemyCharge", EffectEnemyCharge.class);
		EffectHandler.getInstance().registerHandler("FakeDeath", EffectFakeDeath.class);
		EffectHandler.getInstance().registerHandler("Fear", EffectFear.class);
		EffectHandler.getInstance().registerHandler("Fusion", EffectFusion.class);
		EffectHandler.getInstance().registerHandler("Grow", EffectGrow.class);
		EffectHandler.getInstance().registerHandler("HealOverTime", EffectHealOverTime.class);
		EffectHandler.getInstance().registerHandler("HealPercent", EffectHealPercent.class);
		EffectHandler.getInstance().registerHandler("Heal", EffectHeal.class);
		EffectHandler.getInstance().registerHandler("Hide", EffectHide.class);
		EffectHandler.getInstance().registerHandler("ImmobileBuff", EffectImmobileBuff.class);
		EffectHandler.getInstance().registerHandler("IncreaseCharges", EffectIncreaseCharges.class);
		EffectHandler.getInstance().registerHandler("ImmobilePetBuff", EffectImmobilePetBuff.class);
		EffectHandler.getInstance().registerHandler("Invincible", EffectInvincible.class);
		EffectHandler.getInstance().registerHandler("ManaDamOverTime", EffectManaDamOverTime.class);
		EffectHandler.getInstance().registerHandler("ManaHealOverTime", EffectManaHealOverTime.class);
		EffectHandler.getInstance().registerHandler("ManaHealPercent", EffectManaHealPercent.class);
		EffectHandler.getInstance().registerHandler("ManaHeal", EffectManaHeal.class);
		EffectHandler.getInstance().registerHandler("MpConsumePerLevel", EffectMpConsumePerLevel.class);
		EffectHandler.getInstance().registerHandler("Mute", EffectMute.class);
		EffectHandler.getInstance().registerHandler("Negate", EffectNegate.class);
		EffectHandler.getInstance().registerHandler("NoblesseBless", EffectNoblesseBless.class);
		EffectHandler.getInstance().registerHandler("Paralyze", EffectParalyze.class);
		EffectHandler.getInstance().registerHandler("Petrification", EffectPetrification.class);
		EffectHandler.getInstance().registerHandler("PhoenixBless", EffectPhoenixBless.class);
		EffectHandler.getInstance().registerHandler("PhysicalAttackMute", EffectPhysicalAttackMute.class);
		EffectHandler.getInstance().registerHandler("PhysicalMute", EffectPhysicalMute.class);
		EffectHandler.getInstance().registerHandler("ProtectionBlessing", EffectProtectionBlessing.class);
		EffectHandler.getInstance().registerHandler("RandomizeHate", EffectRandomizeHate.class);
		EffectHandler.getInstance().registerHandler("Recovery", EffectRecovery.class);
		EffectHandler.getInstance().registerHandler("Relax", EffectRelax.class);
		EffectHandler.getInstance().registerHandler("RemoveTarget", EffectRemoveTarget.class);
		EffectHandler.getInstance().registerHandler("Root", EffectRoot.class);
		EffectHandler.getInstance().registerHandler("Signet", EffectSignet.class);
		EffectHandler.getInstance().registerHandler("SignetAntiSummon", EffectSignetAntiSummon.class);
		EffectHandler.getInstance().registerHandler("SignetMDam", EffectSignetMDam.class);
		EffectHandler.getInstance().registerHandler("SignetNoise", EffectSignetNoise.class);
		EffectHandler.getInstance().registerHandler("SilentMove", EffectSilentMove.class);
		EffectHandler.getInstance().registerHandler("Sleep", EffectSleep.class);
		EffectHandler.getInstance().registerHandler("Spoil", EffectSpoil.class);
		EffectHandler.getInstance().registerHandler("Stun", EffectStun.class);
		EffectHandler.getInstance().registerHandler("TargetMe", EffectTargetMe.class);
		EffectHandler.getInstance().registerHandler("ThrowUp", EffectThrowUp.class);
		EffectHandler.getInstance().registerHandler("TransferDamage", EffectTransferDamage.class);
		EffectHandler.getInstance().registerHandler("Transformation", EffectTransformation.class);
		EffectHandler.getInstance().registerHandler("Warp", EffectWarp.class);
	}
	
	public static void main(String[] args)
	{
		loadEffectHandlers();
	}
}
