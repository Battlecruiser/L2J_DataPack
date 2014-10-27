/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package quests;

import java.util.logging.Level;
import java.util.logging.Logger;

import quests.Q00001_LettersOfLove.Q00001_LettersOfLove;
import quests.Q00002_WhatWomenWant.Q00002_WhatWomenWant;
import quests.Q00003_WillTheSealBeBroken.Q00003_WillTheSealBeBroken;
import quests.Q00004_LongLiveThePaagrioLord.Q00004_LongLiveThePaagrioLord;
import quests.Q00005_MinersFavor.Q00005_MinersFavor;
import quests.Q00006_StepIntoTheFuture.Q00006_StepIntoTheFuture;
import quests.Q00007_ATripBegins.Q00007_ATripBegins;
import quests.Q00008_AnAdventureBegins.Q00008_AnAdventureBegins;
import quests.Q00009_IntoTheCityOfHumans.Q00009_IntoTheCityOfHumans;
import quests.Q00010_IntoTheWorld.Q00010_IntoTheWorld;
import quests.Q00011_SecretMeetingWithKetraOrcs.Q00011_SecretMeetingWithKetraOrcs;
import quests.Q00012_SecretMeetingWithVarkaSilenos.Q00012_SecretMeetingWithVarkaSilenos;
import quests.Q00013_ParcelDelivery.Q00013_ParcelDelivery;
import quests.Q00014_WhereaboutsOfTheArchaeologist.Q00014_WhereaboutsOfTheArchaeologist;
import quests.Q00015_SweetWhispers.Q00015_SweetWhispers;
import quests.Q00016_TheComingDarkness.Q00016_TheComingDarkness;
import quests.Q00017_LightAndDarkness.Q00017_LightAndDarkness;
import quests.Q00018_MeetingWithTheGoldenRam.Q00018_MeetingWithTheGoldenRam;
import quests.Q00019_GoToThePastureland.Q00019_GoToThePastureland;
import quests.Q00020_BringUpWithLove.Q00020_BringUpWithLove;
import quests.Q00021_HiddenTruth.Q00021_HiddenTruth;
import quests.Q00024_InhabitantsOfTheForestOfTheDead.Q00024_InhabitantsOfTheForestOfTheDead;
import quests.Q00026_TiredOfWaiting.Q00026_TiredOfWaiting;
import quests.Q00027_ChestCaughtWithABaitOfWind.Q00027_ChestCaughtWithABaitOfWind;
import quests.Q00028_ChestCaughtWithABaitOfIcyAir.Q00028_ChestCaughtWithABaitOfIcyAir;
import quests.Q00029_ChestCaughtWithABaitOfEarth.Q00029_ChestCaughtWithABaitOfEarth;
import quests.Q00030_ChestCaughtWithABaitOfFire.Q00030_ChestCaughtWithABaitOfFire;
import quests.Q00031_SecretBuriedInTheSwamp.Q00031_SecretBuriedInTheSwamp;
import quests.Q00032_AnObviousLie.Q00032_AnObviousLie;
import quests.Q00033_MakeAPairOfDressShoes.Q00033_MakeAPairOfDressShoes;
import quests.Q00034_InSearchOfCloth.Q00034_InSearchOfCloth;
import quests.Q00035_FindGlitteringJewelry.Q00035_FindGlitteringJewelry;
import quests.Q00036_MakeASewingKit.Q00036_MakeASewingKit;
import quests.Q00037_MakeFormalWear.Q00037_MakeFormalWear;
import quests.Q00038_DragonFangs.Q00038_DragonFangs;
import quests.Q00039_RedEyedInvaders.Q00039_RedEyedInvaders;
import quests.Q00040_ASpecialOrder.Q00040_ASpecialOrder;
import quests.Q00042_HelpTheUncle.Q00042_HelpTheUncle;
import quests.Q00043_HelpTheSister.Q00043_HelpTheSister;
import quests.Q00044_HelpTheSon.Q00044_HelpTheSon;
import quests.Q00045_ToTalkingIsland.Q00045_ToTalkingIsland;
import quests.Q00046_OnceMoreInTheArmsOfTheMotherTree.Q00046_OnceMoreInTheArmsOfTheMotherTree;
import quests.Q00047_IntoTheDarkElvenForest.Q00047_IntoTheDarkElvenForest;
import quests.Q00048_ToTheImmortalPlateau.Q00048_ToTheImmortalPlateau;
import quests.Q00049_TheRoadHome.Q00049_TheRoadHome;
import quests.Q00050_LanoscosSpecialBait.Q00050_LanoscosSpecialBait;
import quests.Q00051_OFullesSpecialBait.Q00051_OFullesSpecialBait;
import quests.Q00052_WilliesSpecialBait.Q00052_WilliesSpecialBait;
import quests.Q00053_LinnaeusSpecialBait.Q00053_LinnaeusSpecialBait;
import quests.Q00062_PathOfTheTrooper.Q00062_PathOfTheTrooper;
import quests.Q00063_PathOfTheWarder.Q00063_PathOfTheWarder;
import quests.Q00064_CertifiedBerserker.Q00064_CertifiedBerserker;
import quests.Q00101_SwordOfSolidarity.Q00101_SwordOfSolidarity;
import quests.Q00102_SeaOfSporesFever.Q00102_SeaOfSporesFever;
import quests.Q00103_SpiritOfCraftsman.Q00103_SpiritOfCraftsman;
import quests.Q00104_SpiritOfMirrors.Q00104_SpiritOfMirrors;
import quests.Q00105_SkirmishWithOrcs.Q00105_SkirmishWithOrcs;
import quests.Q00106_ForgottenTruth.Q00106_ForgottenTruth;
import quests.Q00107_MercilessPunishment.Q00107_MercilessPunishment;
import quests.Q00108_JumbleTumbleDiamondFuss.Q00108_JumbleTumbleDiamondFuss;
import quests.Q00109_InSearchOfTheNest.Q00109_InSearchOfTheNest;
import quests.Q00110_ToThePrimevalIsle.Q00110_ToThePrimevalIsle;
import quests.Q00111_ElrokianHuntersProof.Q00111_ElrokianHuntersProof;
import quests.Q00112_WalkOfFate.Q00112_WalkOfFate;
import quests.Q00113_StatusOfTheBeaconTower.Q00113_StatusOfTheBeaconTower;
import quests.Q00114_ResurrectionOfAnOldManager.Q00114_ResurrectionOfAnOldManager;
import quests.Q00115_TheOtherSideOfTruth.Q00115_TheOtherSideOfTruth;
import quests.Q00116_BeyondTheHillsOfWinter.Q00116_BeyondTheHillsOfWinter;
import quests.Q00119_LastImperialPrince.Q00119_LastImperialPrince;
import quests.Q00121_PavelTheGiant.Q00121_PavelTheGiant;
import quests.Q00122_OminousNews.Q00122_OminousNews;
import quests.Q00124_MeetingTheElroki.Q00124_MeetingTheElroki;
import quests.Q00125_TheNameOfEvil1.Q00125_TheNameOfEvil1;
import quests.Q00126_TheNameOfEvil2.Q00126_TheNameOfEvil2;
import quests.Q00128_PailakaSongOfIceAndFire.Q00128_PailakaSongOfIceAndFire;
import quests.Q00129_PailakaDevilsLegacy.Q00129_PailakaDevilsLegacy;
import quests.Q00131_BirdInACage.Q00131_BirdInACage;
import quests.Q00132_MatrasCuriosity.Q00132_MatrasCuriosity;
import quests.Q00134_TempleMissionary.Q00134_TempleMissionary;
import quests.Q00135_TempleExecutor.Q00135_TempleExecutor;
import quests.Q00136_MoreThanMeetsTheEye.Q00136_MoreThanMeetsTheEye;
import quests.Q00137_TempleChampionPart1.Q00137_TempleChampionPart1;
import quests.Q00138_TempleChampionPart2.Q00138_TempleChampionPart2;
import quests.Q00139_ShadowFoxPart1.Q00139_ShadowFoxPart1;
import quests.Q00140_ShadowFoxPart2.Q00140_ShadowFoxPart2;
import quests.Q00141_ShadowFoxPart3.Q00141_ShadowFoxPart3;
import quests.Q00142_FallenAngelRequestOfDawn.Q00142_FallenAngelRequestOfDawn;
import quests.Q00143_FallenAngelRequestOfDusk.Q00143_FallenAngelRequestOfDusk;
import quests.Q00146_TheZeroHour.Q00146_TheZeroHour;
import quests.Q00147_PathtoBecominganEliteMercenary.Q00147_PathtoBecominganEliteMercenary;
import quests.Q00148_PathtoBecominganExaltedMercenary.Q00148_PathtoBecominganExaltedMercenary;
import quests.Q00151_CureForFever.Q00151_CureForFever;
import quests.Q00152_ShardsOfGolem.Q00152_ShardsOfGolem;
import quests.Q00153_DeliverGoods.Q00153_DeliverGoods;
import quests.Q00154_SacrificeToTheSea.Q00154_SacrificeToTheSea;
import quests.Q00155_FindSirWindawood.Q00155_FindSirWindawood;
import quests.Q00156_MillenniumLove.Q00156_MillenniumLove;
import quests.Q00157_RecoverSmuggledGoods.Q00157_RecoverSmuggledGoods;
import quests.Q00158_SeedOfEvil.Q00158_SeedOfEvil;
import quests.Q00159_ProtectTheWaterSource.Q00159_ProtectTheWaterSource;
import quests.Q00160_NerupasRequest.Q00160_NerupasRequest;
import quests.Q00161_FruitOfTheMotherTree.Q00161_FruitOfTheMotherTree;
import quests.Q00162_CurseOfTheUndergroundFortress.Q00162_CurseOfTheUndergroundFortress;
import quests.Q00163_LegacyOfThePoet.Q00163_LegacyOfThePoet;
import quests.Q00164_BloodFiend.Q00164_BloodFiend;
import quests.Q00165_ShilensHunt.Q00165_ShilensHunt;
import quests.Q00166_MassOfDarkness.Q00166_MassOfDarkness;
import quests.Q00167_DwarvenKinship.Q00167_DwarvenKinship;
import quests.Q00168_DeliverSupplies.Q00168_DeliverSupplies;
import quests.Q00169_OffspringOfNightmares.Q00169_OffspringOfNightmares;
import quests.Q00170_DangerousSeduction.Q00170_DangerousSeduction;
import quests.Q00172_NewHorizons.Q00172_NewHorizons;
import quests.Q00173_ToTheIsleOfSouls.Q00173_ToTheIsleOfSouls;
import quests.Q00174_SupplyCheck.Q00174_SupplyCheck;
import quests.Q00175_TheWayOfTheWarrior.Q00175_TheWayOfTheWarrior;
import quests.Q00176_StepsForHonor.Q00176_StepsForHonor;
import quests.Q00179_IntoTheLargeCavern.Q00179_IntoTheLargeCavern;
import quests.Q00182_NewRecruits.Q00182_NewRecruits;
import quests.Q00183_RelicExploration.Q00183_RelicExploration;
import quests.Q00186_ContractExecution.Q00186_ContractExecution;
import quests.Q00187_NikolasHeart.Q00187_NikolasHeart;
import quests.Q00188_SealRemoval.Q00188_SealRemoval;
import quests.Q00189_ContractCompletion.Q00189_ContractCompletion;
import quests.Q00190_LostDream.Q00190_LostDream;
import quests.Q00191_VainConclusion.Q00191_VainConclusion;
import quests.Q00192_SevenSignsSeriesOfDoubt.Q00192_SevenSignsSeriesOfDoubt;
import quests.Q00193_SevenSignsDyingMessage.Q00193_SevenSignsDyingMessage;
import quests.Q00194_SevenSignsMammonsContract.Q00194_SevenSignsMammonsContract;
import quests.Q00195_SevenSignsSecretRitualOfThePriests.Q00195_SevenSignsSecretRitualOfThePriests;
import quests.Q00196_SevenSignsSealOfTheEmperor.Q00196_SevenSignsSealOfTheEmperor;
import quests.Q00197_SevenSignsTheSacredBookOfSeal.Q00197_SevenSignsTheSacredBookOfSeal;
import quests.Q00198_SevenSignsEmbryo.Q00198_SevenSignsEmbryo;
import quests.Q00211_TrialOfTheChallenger.Q00211_TrialOfTheChallenger;
import quests.Q00212_TrialOfDuty.Q00212_TrialOfDuty;
import quests.Q00213_TrialOfTheSeeker.Q00213_TrialOfTheSeeker;
import quests.Q00214_TrialOfTheScholar.Q00214_TrialOfTheScholar;
import quests.Q00215_TrialOfThePilgrim.Q00215_TrialOfThePilgrim;
import quests.Q00217_TestimonyOfTrust.Q00217_TestimonyOfTrust;
import quests.Q00218_TestimonyOfLife.Q00218_TestimonyOfLife;
import quests.Q00219_TestimonyOfFate.Q00219_TestimonyOfFate;
import quests.Q00221_TestimonyOfProsperity.Q00221_TestimonyOfProsperity;
import quests.Q00235_MimirsElixir.Q00235_MimirsElixir;
import quests.Q00237_WindsOfChange.Q00237_WindsOfChange;
import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;
import quests.Q00239_WontYouJoinUs.Q00239_WontYouJoinUs;
import quests.Q00240_ImTheOnlyOneYouCanTrust.Q00240_ImTheOnlyOneYouCanTrust;
import quests.Q00241_PossessorOfAPreciousSoul1.Q00241_PossessorOfAPreciousSoul1;
import quests.Q00242_PossessorOfAPreciousSoul2.Q00242_PossessorOfAPreciousSoul2;
import quests.Q00246_PossessorOfAPreciousSoul3.Q00246_PossessorOfAPreciousSoul3;
import quests.Q00247_PossessorOfAPreciousSoul4.Q00247_PossessorOfAPreciousSoul4;
import quests.Q00249_PoisonedPlainsOfTheLizardmen.Q00249_PoisonedPlainsOfTheLizardmen;
import quests.Q00250_WatchWhatYouEat.Q00250_WatchWhatYouEat;
import quests.Q00251_NoSecrets.Q00251_NoSecrets;
import quests.Q00252_ItSmellsDelicious.Q00252_ItSmellsDelicious;
import quests.Q00254_LegendaryTales.Q00254_LegendaryTales;
import quests.Q00257_TheGuardIsBusy.Q00257_TheGuardIsBusy;
import quests.Q00258_BringWolfPelts.Q00258_BringWolfPelts;
import quests.Q00259_RequestFromTheFarmOwner.Q00259_RequestFromTheFarmOwner;
import quests.Q00260_OrcHunting.Q00260_OrcHunting;
import quests.Q00261_CollectorsDream.Q00261_CollectorsDream;
import quests.Q00262_TradeWithTheIvoryTower.Q00262_TradeWithTheIvoryTower;
import quests.Q00263_OrcSubjugation.Q00263_OrcSubjugation;
import quests.Q00264_KeenClaws.Q00264_KeenClaws;
import quests.Q00265_BondsOfSlavery.Q00265_BondsOfSlavery;
import quests.Q00266_PleasOfPixies.Q00266_PleasOfPixies;
import quests.Q00267_WrathOfVerdure.Q00267_WrathOfVerdure;
import quests.Q00268_TracesOfEvil.Q00268_TracesOfEvil;
import quests.Q00269_InventionAmbition.Q00269_InventionAmbition;
import quests.Q00270_TheOneWhoEndsSilence.Q00270_TheOneWhoEndsSilence;
import quests.Q00271_ProofOfValor.Q00271_ProofOfValor;
import quests.Q00272_WrathOfAncestors.Q00272_WrathOfAncestors;
import quests.Q00273_InvadersOfTheHolyLand.Q00273_InvadersOfTheHolyLand;
import quests.Q00274_SkirmishWithTheWerewolves.Q00274_SkirmishWithTheWerewolves;
import quests.Q00275_DarkWingedSpies.Q00275_DarkWingedSpies;
import quests.Q00276_TotemOfTheHestui.Q00276_TotemOfTheHestui;
import quests.Q00277_GatekeepersOffering.Q00277_GatekeepersOffering;
import quests.Q00278_HomeSecurity.Q00278_HomeSecurity;
import quests.Q00279_TargetOfOpportunity.Q00279_TargetOfOpportunity;
import quests.Q00280_TheFoodChain.Q00280_TheFoodChain;
import quests.Q00281_HeadForTheHills.Q00281_HeadForTheHills;
import quests.Q00283_TheFewTheProudTheBrave.Q00283_TheFewTheProudTheBrave;
import quests.Q00284_MuertosFeather.Q00284_MuertosFeather;
import quests.Q00286_FabulousFeathers.Q00286_FabulousFeathers;
import quests.Q00287_FiguringItOut.Q00287_FiguringItOut;
import quests.Q00288_HandleWithCare.Q00288_HandleWithCare;
import quests.Q00289_NoMoreSoupForYou.Q00289_NoMoreSoupForYou;
import quests.Q00290_ThreatRemoval.Q00290_ThreatRemoval;
import quests.Q00291_RevengeOfTheRedbonnet.Q00291_RevengeOfTheRedbonnet;
import quests.Q00292_BrigandsSweep.Q00292_BrigandsSweep;
import quests.Q00293_TheHiddenVeins.Q00293_TheHiddenVeins;
import quests.Q00294_CovertBusiness.Q00294_CovertBusiness;
import quests.Q00295_DreamingOfTheSkies.Q00295_DreamingOfTheSkies;
import quests.Q00296_TarantulasSpiderSilk.Q00296_TarantulasSpiderSilk;
import quests.Q00297_GatekeepersFavor.Q00297_GatekeepersFavor;
import quests.Q00298_LizardmensConspiracy.Q00298_LizardmensConspiracy;
import quests.Q00299_GatherIngredientsForPie.Q00299_GatherIngredientsForPie;
import quests.Q00300_HuntingLetoLizardman.Q00300_HuntingLetoLizardman;
import quests.Q00303_CollectArrowheads.Q00303_CollectArrowheads;
import quests.Q00306_CrystalOfFireAndIce.Q00306_CrystalOfFireAndIce;
import quests.Q00307_ControlDeviceOfTheGiants.Q00307_ControlDeviceOfTheGiants;
import quests.Q00308_ReedFieldMaintenance.Q00308_ReedFieldMaintenance;
import quests.Q00309_ForAGoodCause.Q00309_ForAGoodCause;
import quests.Q00310_OnlyWhatRemains.Q00310_OnlyWhatRemains;
import quests.Q00311_ExpulsionOfEvilSpirits.Q00311_ExpulsionOfEvilSpirits;
import quests.Q00312_TakeAdvantageOfTheCrisis.Q00312_TakeAdvantageOfTheCrisis;
import quests.Q00313_CollectSpores.Q00313_CollectSpores;
import quests.Q00316_DestroyPlagueCarriers.Q00316_DestroyPlagueCarriers;
import quests.Q00317_CatchTheWind.Q00317_CatchTheWind;
import quests.Q00319_ScentOfDeath.Q00319_ScentOfDeath;
import quests.Q00320_BonesTellTheFuture.Q00320_BonesTellTheFuture;
import quests.Q00324_SweetestVenom.Q00324_SweetestVenom;
import quests.Q00325_GrimCollector.Q00325_GrimCollector;
import quests.Q00326_VanquishRemnants.Q00326_VanquishRemnants;
import quests.Q00327_RecoverTheFarmland.Q00327_RecoverTheFarmland;
import quests.Q00328_SenseForBusiness.Q00328_SenseForBusiness;
import quests.Q00329_CuriosityOfADwarf.Q00329_CuriosityOfADwarf;
import quests.Q00331_ArrowOfVengeance.Q00331_ArrowOfVengeance;
import quests.Q00338_AlligatorHunter.Q00338_AlligatorHunter;
import quests.Q00341_HuntingForWildBeasts.Q00341_HuntingForWildBeasts;
import quests.Q00344_1000YearsTheEndOfLamentation.Q00344_1000YearsTheEndOfLamentation;
import quests.Q00347_GoGetTheCalculator.Q00347_GoGetTheCalculator;
import quests.Q00350_EnhanceYourWeapon.Q00350_EnhanceYourWeapon;
import quests.Q00354_ConquestOfAlligatorIsland.Q00354_ConquestOfAlligatorIsland;
import quests.Q00356_DigUpTheSeaOfSpores.Q00356_DigUpTheSeaOfSpores;
import quests.Q00357_WarehouseKeepersAmbition.Q00357_WarehouseKeepersAmbition;
import quests.Q00358_IllegitimateChildOfTheGoddess.Q00358_IllegitimateChildOfTheGoddess;
import quests.Q00359_ForASleeplessDeadman.Q00359_ForASleeplessDeadman;
import quests.Q00360_PlunderTheirSupplies.Q00360_PlunderTheirSupplies;
import quests.Q00362_BardsMandolin.Q00362_BardsMandolin;
import quests.Q00363_SorrowfulSoundOfFlute.Q00363_SorrowfulSoundOfFlute;
import quests.Q00364_JovialAccordion.Q00364_JovialAccordion;
import quests.Q00365_DevilsLegacy.Q00365_DevilsLegacy;
import quests.Q00366_SilverHairedShaman.Q00366_SilverHairedShaman;
import quests.Q00367_ElectrifyingRecharge.Q00367_ElectrifyingRecharge;
import quests.Q00368_TrespassingIntoTheHolyGround.Q00368_TrespassingIntoTheHolyGround;
import quests.Q00369_CollectorOfJewels.Q00369_CollectorOfJewels;
import quests.Q00370_AnElderSowsSeeds.Q00370_AnElderSowsSeeds;
import quests.Q00376_ExplorationOfTheGiantsCavePart1.Q00376_ExplorationOfTheGiantsCavePart1;
import quests.Q00377_ExplorationOfTheGiantsCavePart2.Q00377_ExplorationOfTheGiantsCavePart2;
import quests.Q00380_BringOutTheFlavorOfIngredients.Q00380_BringOutTheFlavorOfIngredients;
import quests.Q00381_LetsBecomeARoyalMember.Q00381_LetsBecomeARoyalMember;
import quests.Q00382_KailsMagicCoin.Q00382_KailsMagicCoin;
import quests.Q00385_YokeOfThePast.Q00385_YokeOfThePast;
import quests.Q00401_PathOfTheWarrior.Q00401_PathOfTheWarrior;
import quests.Q00402_PathOfTheHumanKnight.Q00402_PathOfTheHumanKnight;
import quests.Q00403_PathOfTheRogue.Q00403_PathOfTheRogue;
import quests.Q00404_PathOfTheHumanWizard.Q00404_PathOfTheHumanWizard;
import quests.Q00405_PathOfTheCleric.Q00405_PathOfTheCleric;
import quests.Q00406_PathOfTheElvenKnight.Q00406_PathOfTheElvenKnight;
import quests.Q00407_PathOfTheElvenScout.Q00407_PathOfTheElvenScout;
import quests.Q00408_PathOfTheElvenWizard.Q00408_PathOfTheElvenWizard;
import quests.Q00409_PathOfTheElvenOracle.Q00409_PathOfTheElvenOracle;
import quests.Q00410_PathOfThePalusKnight.Q00410_PathOfThePalusKnight;
import quests.Q00411_PathOfTheAssassin.Q00411_PathOfTheAssassin;
import quests.Q00412_PathOfTheDarkWizard.Q00412_PathOfTheDarkWizard;
import quests.Q00413_PathOfTheShillienOracle.Q00413_PathOfTheShillienOracle;
import quests.Q00414_PathOfTheOrcRaider.Q00414_PathOfTheOrcRaider;
import quests.Q00415_PathOfTheOrcMonk.Q00415_PathOfTheOrcMonk;
import quests.Q00416_PathOfTheOrcShaman.Q00416_PathOfTheOrcShaman;
import quests.Q00417_PathOfTheScavenger.Q00417_PathOfTheScavenger;
import quests.Q00418_PathOfTheArtisan.Q00418_PathOfTheArtisan;
import quests.Q00420_LittleWing.Q00420_LittleWing;
import quests.Q00421_LittleWingsBigAdventure.Q00421_LittleWingsBigAdventure;
import quests.Q00423_TakeYourBestShot.Q00423_TakeYourBestShot;
import quests.Q00431_WeddingMarch.Q00431_WeddingMarch;
import quests.Q00432_BirthdayPartySong.Q00432_BirthdayPartySong;
import quests.Q00450_GraveRobberRescue.Q00450_GraveRobberRescue;
import quests.Q00451_LuciensAltar.Q00451_LuciensAltar;
import quests.Q00452_FindingtheLostSoldiers.Q00452_FindingtheLostSoldiers;
import quests.Q00453_NotStrongEnoughAlone.Q00453_NotStrongEnoughAlone;
import quests.Q00455_WingsOfSand.Q00455_WingsOfSand;
import quests.Q00456_DontKnowDontCare.Q00456_DontKnowDontCare;
import quests.Q00457_LostAndFound.Q00457_LostAndFound;
import quests.Q00458_PerfectForm.Q00458_PerfectForm;
import quests.Q00461_RumbleInTheBase.Q00461_RumbleInTheBase;
import quests.Q00463_IMustBeaGenius.Q00463_IMustBeaGenius;
import quests.Q00464_Oath.Q00464_Oath;
import quests.Q00504_CompetitionForTheBanditStronghold.Q00504_CompetitionForTheBanditStronghold;
import quests.Q00508_AClansReputation.Q00508_AClansReputation;
import quests.Q00509_AClansFame.Q00509_AClansFame;
import quests.Q00510_AClansPrestige.Q00510_AClansPrestige;
import quests.Q00511_AwlUnderFoot.Q00511_AwlUnderFoot;
import quests.Q00551_OlympiadStarter.Q00551_OlympiadStarter;
import quests.Q00552_OlympiadVeteran.Q00552_OlympiadVeteran;
import quests.Q00553_OlympiadUndefeated.Q00553_OlympiadUndefeated;
import quests.Q00601_WatchingEyes.Q00601_WatchingEyes;
import quests.Q00602_ShadowOfLight.Q00602_ShadowOfLight;
import quests.Q00603_DaimonTheWhiteEyedPart1.Q00603_DaimonTheWhiteEyedPart1;
import quests.Q00605_AllianceWithKetraOrcs.Q00605_AllianceWithKetraOrcs;
import quests.Q00606_BattleAgainstVarkaSilenos.Q00606_BattleAgainstVarkaSilenos;
import quests.Q00607_ProveYourCourageKetra.Q00607_ProveYourCourageKetra;
import quests.Q00608_SlayTheEnemyCommanderKetra.Q00608_SlayTheEnemyCommanderKetra;
import quests.Q00609_MagicalPowerOfWaterPart1.Q00609_MagicalPowerOfWaterPart1;
import quests.Q00610_MagicalPowerOfWaterPart2.Q00610_MagicalPowerOfWaterPart2;
import quests.Q00611_AllianceWithVarkaSilenos.Q00611_AllianceWithVarkaSilenos;
import quests.Q00612_BattleAgainstKetraOrcs.Q00612_BattleAgainstKetraOrcs;
import quests.Q00613_ProveYourCourageVarka.Q00613_ProveYourCourageVarka;
import quests.Q00614_SlayTheEnemyCommanderVarka.Q00614_SlayTheEnemyCommanderVarka;
import quests.Q00615_MagicalPowerOfFirePart1.Q00615_MagicalPowerOfFirePart1;
import quests.Q00616_MagicalPowerOfFirePart2.Q00616_MagicalPowerOfFirePart2;
import quests.Q00617_GatherTheFlames.Q00617_GatherTheFlames;
import quests.Q00618_IntoTheFlame.Q00618_IntoTheFlame;
import quests.Q00619_RelicsOfTheOldEmpire.Q00619_RelicsOfTheOldEmpire;
import quests.Q00621_EggDelivery.Q00621_EggDelivery;
import quests.Q00622_SpecialtyLiquorDelivery.Q00622_SpecialtyLiquorDelivery;
import quests.Q00623_TheFinestFood.Q00623_TheFinestFood;
import quests.Q00624_TheFinestIngredientsPart1.Q00624_TheFinestIngredientsPart1;
import quests.Q00625_TheFinestIngredientsPart2.Q00625_TheFinestIngredientsPart2;
import quests.Q00626_ADarkTwilight.Q00626_ADarkTwilight;
import quests.Q00627_HeartInSearchOfPower.Q00627_HeartInSearchOfPower;
import quests.Q00628_HuntGoldenRam.Q00628_HuntGoldenRam;
import quests.Q00629_CleanUpTheSwampOfScreams.Q00629_CleanUpTheSwampOfScreams;
import quests.Q00631_DeliciousTopChoiceMeat.Q00631_DeliciousTopChoiceMeat;
import quests.Q00632_NecromancersRequest.Q00632_NecromancersRequest;
import quests.Q00633_InTheForgottenVillage.Q00633_InTheForgottenVillage;
import quests.Q00634_InSearchOfFragmentsOfDimension.Q00634_InSearchOfFragmentsOfDimension;
import quests.Q00635_IntoTheDimensionalRift.Q00635_IntoTheDimensionalRift;
import quests.Q00636_TruthBeyond.Q00636_TruthBeyond;
import quests.Q00637_ThroughOnceMore.Q00637_ThroughOnceMore;
import quests.Q00638_SeekersOfTheHolyGrail.Q00638_SeekersOfTheHolyGrail;
import quests.Q00639_GuardiansOfTheHolyGrail.Q00639_GuardiansOfTheHolyGrail;
import quests.Q00641_AttackSailren.Q00641_AttackSailren;
import quests.Q00642_APowerfulPrimevalCreature.Q00642_APowerfulPrimevalCreature;
import quests.Q00643_RiseAndFallOfTheElrokiTribe.Q00643_RiseAndFallOfTheElrokiTribe;
import quests.Q00644_GraveRobberAnnihilation.Q00644_GraveRobberAnnihilation;
import quests.Q00645_GhostsOfBatur.Q00645_GhostsOfBatur;
import quests.Q00646_SignsOfRevolt.Q00646_SignsOfRevolt;
import quests.Q00647_InfluxOfMachines.Q00647_InfluxOfMachines;
import quests.Q00648_AnIceMerchantsDream.Q00648_AnIceMerchantsDream;
import quests.Q00649_ALooterAndARailroadMan.Q00649_ALooterAndARailroadMan;
import quests.Q00650_ABrokenDream.Q00650_ABrokenDream;
import quests.Q00651_RunawayYouth.Q00651_RunawayYouth;
import quests.Q00652_AnAgedExAdventurer.Q00652_AnAgedExAdventurer;
import quests.Q00653_WildMaiden.Q00653_WildMaiden;
import quests.Q00654_JourneyToASettlement.Q00654_JourneyToASettlement;
import quests.Q00659_IdRatherBeCollectingFairyBreath.Q00659_IdRatherBeCollectingFairyBreath;
import quests.Q00660_AidingTheFloranVillage.Q00660_AidingTheFloranVillage;
import quests.Q00661_MakingTheHarvestGroundsSafe.Q00661_MakingTheHarvestGroundsSafe;
import quests.Q00662_AGameOfCards.Q00662_AGameOfCards;
import quests.Q00688_DefeatTheElrokianRaiders.Q00688_DefeatTheElrokianRaiders;
import quests.Q00690_JudesRequest.Q00690_JudesRequest;
import quests.Q00691_MatrasSuspiciousRequest.Q00691_MatrasSuspiciousRequest;
import quests.Q00692_HowtoOpposeEvil.Q00692_HowtoOpposeEvil;
import quests.Q00699_GuardianOfTheSkies.Q00699_GuardianOfTheSkies;
import quests.Q00700_CursedLife.Q00700_CursedLife;
import quests.Q00701_ProofOfExistence.Q00701_ProofOfExistence;
import quests.Q00702_ATrapForRevenge.Q00702_ATrapForRevenge;
import quests.Q00901_HowLavasaurusesAreMade.Q00901_HowLavasaurusesAreMade;
import quests.Q00902_ReclaimOurEra.Q00902_ReclaimOurEra;
import quests.Q00903_TheCallOfAntharas.Q00903_TheCallOfAntharas;
import quests.Q00904_DragonTrophyAntharas.Q00904_DragonTrophyAntharas;
import quests.Q00905_RefinedDragonBlood.Q00905_RefinedDragonBlood;
import quests.Q00906_TheCallOfValakas.Q00906_TheCallOfValakas;
import quests.Q00907_DragonTrophyValakas.Q00907_DragonTrophyValakas;
import quests.Q00998_FallenAngelSelect.Q00998_FallenAngelSelect;
import quests.Q10267_JourneyToGracia.Q10267_JourneyToGracia;
import quests.Q10268_ToTheSeedOfInfinity.Q10268_ToTheSeedOfInfinity;
import quests.Q10269_ToTheSeedOfDestruction.Q10269_ToTheSeedOfDestruction;
import quests.Q10271_TheEnvelopingDarkness.Q10271_TheEnvelopingDarkness;
import quests.Q10272_LightFragment.Q10272_LightFragment;
import quests.Q10273_GoodDayToFly.Q10273_GoodDayToFly;
import quests.Q10274_CollectingInTheAir.Q10274_CollectingInTheAir;
import quests.Q10275_ContainingTheAttributePower.Q10275_ContainingTheAttributePower;
import quests.Q10276_MutatedKaneusGludio.Q10276_MutatedKaneusGludio;
import quests.Q10277_MutatedKaneusDion.Q10277_MutatedKaneusDion;
import quests.Q10278_MutatedKaneusHeine.Q10278_MutatedKaneusHeine;
import quests.Q10279_MutatedKaneusOren.Q10279_MutatedKaneusOren;
import quests.Q10280_MutatedKaneusSchuttgart.Q10280_MutatedKaneusSchuttgart;
import quests.Q10281_MutatedKaneusRune.Q10281_MutatedKaneusRune;
import quests.Q10282_ToTheSeedOfAnnihilation.Q10282_ToTheSeedOfAnnihilation;
import quests.Q10283_RequestOfIceMerchant.Q10283_RequestOfIceMerchant;
import quests.Q10284_AcquisitionOfDivineSword.Q10284_AcquisitionOfDivineSword;
import quests.Q10285_MeetingSirra.Q10285_MeetingSirra;
import quests.Q10286_ReunionWithSirra.Q10286_ReunionWithSirra;
import quests.Q10287_StoryOfThoseLeft.Q10287_StoryOfThoseLeft;
import quests.Q10288_SecretMission.Q10288_SecretMission;
import quests.Q10289_FadeToBlack.Q10289_FadeToBlack;
import quests.Q10290_LandDragonConqueror.Q10290_LandDragonConqueror;
import quests.Q10291_FireDragonDestroyer.Q10291_FireDragonDestroyer;
import quests.Q10292_SevenSignsGirlOfDoubt.Q10292_SevenSignsGirlOfDoubt;
import quests.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom;
import quests.Q10294_SevenSignsToTheMonasteryOfSilence.Q10294_SevenSignsToTheMonasteryOfSilence;
import quests.Q10501_ZakenEmbroideredSoulCloak.Q10501_ZakenEmbroideredSoulCloak;
import quests.Q10502_FreyaEmbroideredSoulCloak.Q10502_FreyaEmbroideredSoulCloak;
import quests.Q10503_FrintezzaEmbroideredSoulCloak.Q10503_FrintezzaEmbroideredSoulCloak;
import quests.Q10504_JewelOfAntharas.Q10504_JewelOfAntharas;
import quests.Q10505_JewelOfValakas.Q10505_JewelOfValakas;

/**
 * @author Nos
 */
public class QuestMasterHandler
{
	private static final Logger _log = Logger.getLogger(QuestMasterHandler.class.getName());
	
	private static final Class<?>[] QUESTS =
	{
		Q00001_LettersOfLove.class,
		Q00002_WhatWomenWant.class,
		Q00003_WillTheSealBeBroken.class,
		Q00004_LongLiveThePaagrioLord.class,
		Q00005_MinersFavor.class,
		Q00006_StepIntoTheFuture.class,
		Q00007_ATripBegins.class,
		Q00008_AnAdventureBegins.class,
		Q00009_IntoTheCityOfHumans.class,
		Q00010_IntoTheWorld.class,
		Q00011_SecretMeetingWithKetraOrcs.class,
		Q00012_SecretMeetingWithVarkaSilenos.class,
		Q00013_ParcelDelivery.class,
		Q00014_WhereaboutsOfTheArchaeologist.class,
		Q00015_SweetWhispers.class,
		Q00016_TheComingDarkness.class,
		Q00017_LightAndDarkness.class,
		Q00018_MeetingWithTheGoldenRam.class,
		Q00019_GoToThePastureland.class,
		Q00020_BringUpWithLove.class,
		Q00021_HiddenTruth.class,
		Q00024_InhabitantsOfTheForestOfTheDead.class,
		Q00026_TiredOfWaiting.class,
		Q00027_ChestCaughtWithABaitOfWind.class,
		Q00028_ChestCaughtWithABaitOfIcyAir.class,
		Q00029_ChestCaughtWithABaitOfEarth.class,
		Q00030_ChestCaughtWithABaitOfFire.class,
		Q00031_SecretBuriedInTheSwamp.class,
		Q00032_AnObviousLie.class,
		Q00033_MakeAPairOfDressShoes.class,
		Q00034_InSearchOfCloth.class,
		Q00035_FindGlitteringJewelry.class,
		Q00036_MakeASewingKit.class,
		Q00037_MakeFormalWear.class,
		Q00038_DragonFangs.class,
		Q00039_RedEyedInvaders.class,
		Q00040_ASpecialOrder.class,
		Q00042_HelpTheUncle.class,
		Q00043_HelpTheSister.class,
		Q00044_HelpTheSon.class,
		Q00045_ToTalkingIsland.class,
		Q00046_OnceMoreInTheArmsOfTheMotherTree.class,
		Q00047_IntoTheDarkElvenForest.class,
		Q00048_ToTheImmortalPlateau.class,
		Q00049_TheRoadHome.class,
		Q00050_LanoscosSpecialBait.class,
		Q00051_OFullesSpecialBait.class,
		Q00052_WilliesSpecialBait.class,
		Q00053_LinnaeusSpecialBait.class,
		Q00062_PathOfTheTrooper.class,
		Q00063_PathOfTheWarder.class,
		Q00064_CertifiedBerserker.class,
		Q00101_SwordOfSolidarity.class,
		Q00102_SeaOfSporesFever.class,
		Q00103_SpiritOfCraftsman.class,
		Q00104_SpiritOfMirrors.class,
		Q00105_SkirmishWithOrcs.class,
		Q00106_ForgottenTruth.class,
		Q00107_MercilessPunishment.class,
		Q00108_JumbleTumbleDiamondFuss.class,
		Q00109_InSearchOfTheNest.class,
		Q00110_ToThePrimevalIsle.class,
		Q00111_ElrokianHuntersProof.class,
		Q00112_WalkOfFate.class,
		Q00113_StatusOfTheBeaconTower.class,
		Q00114_ResurrectionOfAnOldManager.class,
		Q00115_TheOtherSideOfTruth.class,
		Q00116_BeyondTheHillsOfWinter.class,
		Q00119_LastImperialPrince.class,
		Q00121_PavelTheGiant.class,
		Q00122_OminousNews.class,
		Q00124_MeetingTheElroki.class,
		Q00125_TheNameOfEvil1.class,
		Q00126_TheNameOfEvil2.class,
		Q00128_PailakaSongOfIceAndFire.class,
		Q00129_PailakaDevilsLegacy.class,
		Q00131_BirdInACage.class,
		Q00132_MatrasCuriosity.class,
		Q00134_TempleMissionary.class,
		Q00135_TempleExecutor.class,
		Q00136_MoreThanMeetsTheEye.class,
		Q00137_TempleChampionPart1.class,
		Q00138_TempleChampionPart2.class,
		Q00139_ShadowFoxPart1.class,
		Q00140_ShadowFoxPart2.class,
		Q00141_ShadowFoxPart3.class,
		Q00142_FallenAngelRequestOfDawn.class,
		Q00143_FallenAngelRequestOfDusk.class,
		Q00146_TheZeroHour.class,
		Q00147_PathtoBecominganEliteMercenary.class,
		Q00148_PathtoBecominganExaltedMercenary.class,
		Q00151_CureForFever.class,
		Q00152_ShardsOfGolem.class,
		Q00153_DeliverGoods.class,
		Q00154_SacrificeToTheSea.class,
		Q00155_FindSirWindawood.class,
		Q00156_MillenniumLove.class,
		Q00157_RecoverSmuggledGoods.class,
		Q00158_SeedOfEvil.class,
		Q00159_ProtectTheWaterSource.class,
		Q00160_NerupasRequest.class,
		Q00161_FruitOfTheMotherTree.class,
		Q00162_CurseOfTheUndergroundFortress.class,
		Q00163_LegacyOfThePoet.class,
		Q00164_BloodFiend.class,
		Q00165_ShilensHunt.class,
		Q00166_MassOfDarkness.class,
		Q00167_DwarvenKinship.class,
		Q00168_DeliverSupplies.class,
		Q00169_OffspringOfNightmares.class,
		Q00170_DangerousSeduction.class,
		Q00172_NewHorizons.class,
		Q00173_ToTheIsleOfSouls.class,
		Q00174_SupplyCheck.class,
		Q00175_TheWayOfTheWarrior.class,
		Q00176_StepsForHonor.class,
		Q00179_IntoTheLargeCavern.class,
		Q00182_NewRecruits.class,
		Q00183_RelicExploration.class,
		Q00186_ContractExecution.class,
		Q00187_NikolasHeart.class,
		Q00188_SealRemoval.class,
		Q00189_ContractCompletion.class,
		Q00190_LostDream.class,
		Q00191_VainConclusion.class,
		Q00192_SevenSignsSeriesOfDoubt.class,
		Q00193_SevenSignsDyingMessage.class,
		Q00194_SevenSignsMammonsContract.class,
		Q00195_SevenSignsSecretRitualOfThePriests.class,
		Q00196_SevenSignsSealOfTheEmperor.class,
		Q00197_SevenSignsTheSacredBookOfSeal.class,
		Q00198_SevenSignsEmbryo.class,
		Q00211_TrialOfTheChallenger.class,
		Q00212_TrialOfDuty.class,
		Q00213_TrialOfTheSeeker.class,
		Q00214_TrialOfTheScholar.class,
		Q00215_TrialOfThePilgrim.class,
		Q00217_TestimonyOfTrust.class,
		Q00218_TestimonyOfLife.class,
		Q00219_TestimonyOfFate.class,
		Q00221_TestimonyOfProsperity.class,
		Q00235_MimirsElixir.class,
		Q00237_WindsOfChange.class,
		Q00238_SuccessFailureOfBusiness.class,
		Q00239_WontYouJoinUs.class,
		Q00240_ImTheOnlyOneYouCanTrust.class,
		Q00241_PossessorOfAPreciousSoul1.class,
		Q00242_PossessorOfAPreciousSoul2.class,
		Q00246_PossessorOfAPreciousSoul3.class,
		Q00247_PossessorOfAPreciousSoul4.class,
		Q00249_PoisonedPlainsOfTheLizardmen.class,
		Q00250_WatchWhatYouEat.class,
		Q00251_NoSecrets.class,
		Q00252_ItSmellsDelicious.class,
		Q00254_LegendaryTales.class,
		Q00257_TheGuardIsBusy.class,
		Q00258_BringWolfPelts.class,
		Q00259_RequestFromTheFarmOwner.class,
		Q00260_OrcHunting.class,
		Q00261_CollectorsDream.class,
		Q00262_TradeWithTheIvoryTower.class,
		Q00263_OrcSubjugation.class,
		Q00264_KeenClaws.class,
		Q00265_BondsOfSlavery.class,
		Q00266_PleasOfPixies.class,
		Q00267_WrathOfVerdure.class,
		Q00268_TracesOfEvil.class,
		Q00269_InventionAmbition.class,
		Q00270_TheOneWhoEndsSilence.class,
		Q00271_ProofOfValor.class,
		Q00272_WrathOfAncestors.class,
		Q00273_InvadersOfTheHolyLand.class,
		Q00274_SkirmishWithTheWerewolves.class,
		Q00275_DarkWingedSpies.class,
		Q00276_TotemOfTheHestui.class,
		Q00277_GatekeepersOffering.class,
		Q00278_HomeSecurity.class,
		Q00279_TargetOfOpportunity.class,
		Q00280_TheFoodChain.class,
		Q00281_HeadForTheHills.class,
		Q00283_TheFewTheProudTheBrave.class,
		Q00284_MuertosFeather.class,
		Q00286_FabulousFeathers.class,
		Q00287_FiguringItOut.class,
		Q00288_HandleWithCare.class,
		Q00289_NoMoreSoupForYou.class,
		Q00290_ThreatRemoval.class,
		Q00291_RevengeOfTheRedbonnet.class,
		Q00292_BrigandsSweep.class,
		Q00293_TheHiddenVeins.class,
		Q00294_CovertBusiness.class,
		Q00295_DreamingOfTheSkies.class,
		Q00296_TarantulasSpiderSilk.class,
		Q00297_GatekeepersFavor.class,
		Q00298_LizardmensConspiracy.class,
		Q00299_GatherIngredientsForPie.class,
		Q00300_HuntingLetoLizardman.class,
		Q00303_CollectArrowheads.class,
		Q00306_CrystalOfFireAndIce.class,
		Q00307_ControlDeviceOfTheGiants.class,
		Q00308_ReedFieldMaintenance.class,
		Q00309_ForAGoodCause.class,
		Q00310_OnlyWhatRemains.class,
		Q00311_ExpulsionOfEvilSpirits.class,
		Q00312_TakeAdvantageOfTheCrisis.class,
		Q00313_CollectSpores.class,
		Q00316_DestroyPlagueCarriers.class,
		Q00317_CatchTheWind.class,
		Q00319_ScentOfDeath.class,
		Q00320_BonesTellTheFuture.class,
		Q00324_SweetestVenom.class,
		Q00325_GrimCollector.class,
		Q00326_VanquishRemnants.class,
		Q00327_RecoverTheFarmland.class,
		Q00328_SenseForBusiness.class,
		Q00329_CuriosityOfADwarf.class,
		Q00331_ArrowOfVengeance.class,
		Q00338_AlligatorHunter.class,
		Q00341_HuntingForWildBeasts.class,
		Q00344_1000YearsTheEndOfLamentation.class,
		Q00347_GoGetTheCalculator.class,
		Q00350_EnhanceYourWeapon.class,
		Q00354_ConquestOfAlligatorIsland.class,
		Q00356_DigUpTheSeaOfSpores.class,
		Q00357_WarehouseKeepersAmbition.class,
		Q00358_IllegitimateChildOfTheGoddess.class,
		Q00359_ForASleeplessDeadman.class,
		Q00360_PlunderTheirSupplies.class,
		Q00362_BardsMandolin.class,
		Q00363_SorrowfulSoundOfFlute.class,
		Q00364_JovialAccordion.class,
		Q00365_DevilsLegacy.class,
		Q00366_SilverHairedShaman.class,
		Q00367_ElectrifyingRecharge.class,
		Q00368_TrespassingIntoTheHolyGround.class,
		Q00369_CollectorOfJewels.class,
		Q00370_AnElderSowsSeeds.class,
		Q00376_ExplorationOfTheGiantsCavePart1.class,
		Q00377_ExplorationOfTheGiantsCavePart2.class,
		Q00380_BringOutTheFlavorOfIngredients.class,
		Q00381_LetsBecomeARoyalMember.class,
		Q00382_KailsMagicCoin.class,
		Q00385_YokeOfThePast.class,
		Q00401_PathOfTheWarrior.class,
		Q00402_PathOfTheHumanKnight.class,
		Q00403_PathOfTheRogue.class,
		Q00404_PathOfTheHumanWizard.class,
		Q00405_PathOfTheCleric.class,
		Q00406_PathOfTheElvenKnight.class,
		Q00407_PathOfTheElvenScout.class,
		Q00408_PathOfTheElvenWizard.class,
		Q00409_PathOfTheElvenOracle.class,
		Q00410_PathOfThePalusKnight.class,
		Q00411_PathOfTheAssassin.class,
		Q00412_PathOfTheDarkWizard.class,
		Q00413_PathOfTheShillienOracle.class,
		Q00414_PathOfTheOrcRaider.class,
		Q00415_PathOfTheOrcMonk.class,
		Q00416_PathOfTheOrcShaman.class,
		Q00417_PathOfTheScavenger.class,
		Q00418_PathOfTheArtisan.class,
		Q00420_LittleWing.class,
		Q00421_LittleWingsBigAdventure.class,
		Q00423_TakeYourBestShot.class,
		Q00431_WeddingMarch.class,
		Q00432_BirthdayPartySong.class,
		Q00450_GraveRobberRescue.class,
		Q00451_LuciensAltar.class,
		Q00452_FindingtheLostSoldiers.class,
		Q00453_NotStrongEnoughAlone.class,
		Q00455_WingsOfSand.class,
		Q00456_DontKnowDontCare.class,
		Q00457_LostAndFound.class,
		Q00458_PerfectForm.class,
		Q00461_RumbleInTheBase.class,
		Q00463_IMustBeaGenius.class,
		Q00464_Oath.class,
		Q00504_CompetitionForTheBanditStronghold.class,
		Q00508_AClansReputation.class,
		Q00509_AClansFame.class,
		Q00510_AClansPrestige.class,
		Q00511_AwlUnderFoot.class,
		Q00551_OlympiadStarter.class,
		Q00552_OlympiadVeteran.class,
		Q00553_OlympiadUndefeated.class,
		Q00601_WatchingEyes.class,
		Q00602_ShadowOfLight.class,
		Q00603_DaimonTheWhiteEyedPart1.class,
		Q00605_AllianceWithKetraOrcs.class,
		Q00606_BattleAgainstVarkaSilenos.class,
		Q00607_ProveYourCourageKetra.class,
		Q00608_SlayTheEnemyCommanderKetra.class,
		Q00609_MagicalPowerOfWaterPart1.class,
		Q00610_MagicalPowerOfWaterPart2.class,
		Q00611_AllianceWithVarkaSilenos.class,
		Q00612_BattleAgainstKetraOrcs.class,
		Q00613_ProveYourCourageVarka.class,
		Q00614_SlayTheEnemyCommanderVarka.class,
		Q00615_MagicalPowerOfFirePart1.class,
		Q00616_MagicalPowerOfFirePart2.class,
		Q00617_GatherTheFlames.class,
		Q00618_IntoTheFlame.class,
		Q00619_RelicsOfTheOldEmpire.class,
		Q00621_EggDelivery.class,
		Q00622_SpecialtyLiquorDelivery.class,
		Q00623_TheFinestFood.class,
		Q00624_TheFinestIngredientsPart1.class,
		Q00625_TheFinestIngredientsPart2.class,
		Q00626_ADarkTwilight.class,
		Q00627_HeartInSearchOfPower.class,
		Q00628_HuntGoldenRam.class,
		Q00629_CleanUpTheSwampOfScreams.class,
		Q00631_DeliciousTopChoiceMeat.class,
		Q00632_NecromancersRequest.class,
		Q00633_InTheForgottenVillage.class,
		Q00634_InSearchOfFragmentsOfDimension.class,
		Q00635_IntoTheDimensionalRift.class,
		Q00636_TruthBeyond.class,
		Q00637_ThroughOnceMore.class,
		Q00638_SeekersOfTheHolyGrail.class,
		Q00639_GuardiansOfTheHolyGrail.class,
		Q00641_AttackSailren.class,
		Q00642_APowerfulPrimevalCreature.class,
		Q00643_RiseAndFallOfTheElrokiTribe.class,
		Q00644_GraveRobberAnnihilation.class,
		Q00645_GhostsOfBatur.class,
		Q00646_SignsOfRevolt.class,
		Q00647_InfluxOfMachines.class,
		Q00648_AnIceMerchantsDream.class,
		Q00649_ALooterAndARailroadMan.class,
		Q00650_ABrokenDream.class,
		Q00651_RunawayYouth.class,
		Q00652_AnAgedExAdventurer.class,
		Q00653_WildMaiden.class,
		Q00654_JourneyToASettlement.class,
		Q00659_IdRatherBeCollectingFairyBreath.class,
		Q00660_AidingTheFloranVillage.class,
		Q00661_MakingTheHarvestGroundsSafe.class,
		Q00662_AGameOfCards.class,
		Q00688_DefeatTheElrokianRaiders.class,
		Q00690_JudesRequest.class,
		Q00691_MatrasSuspiciousRequest.class,
		Q00692_HowtoOpposeEvil.class,
		Q00699_GuardianOfTheSkies.class,
		Q00700_CursedLife.class,
		Q00701_ProofOfExistence.class,
		Q00702_ATrapForRevenge.class,
		Q00901_HowLavasaurusesAreMade.class,
		Q00902_ReclaimOurEra.class,
		Q00903_TheCallOfAntharas.class,
		Q00904_DragonTrophyAntharas.class,
		Q00905_RefinedDragonBlood.class,
		Q00906_TheCallOfValakas.class,
		Q00907_DragonTrophyValakas.class,
		Q00998_FallenAngelSelect.class,
		Q10267_JourneyToGracia.class,
		Q10268_ToTheSeedOfInfinity.class,
		Q10269_ToTheSeedOfDestruction.class,
		Q10271_TheEnvelopingDarkness.class,
		Q10272_LightFragment.class,
		Q10273_GoodDayToFly.class,
		Q10274_CollectingInTheAir.class,
		Q10275_ContainingTheAttributePower.class,
		Q10276_MutatedKaneusGludio.class,
		Q10277_MutatedKaneusDion.class,
		Q10278_MutatedKaneusHeine.class,
		Q10279_MutatedKaneusOren.class,
		Q10280_MutatedKaneusSchuttgart.class,
		Q10281_MutatedKaneusRune.class,
		Q10282_ToTheSeedOfAnnihilation.class,
		Q10283_RequestOfIceMerchant.class,
		Q10284_AcquisitionOfDivineSword.class,
		Q10285_MeetingSirra.class,
		Q10286_ReunionWithSirra.class,
		Q10287_StoryOfThoseLeft.class,
		Q10288_SecretMission.class,
		Q10289_FadeToBlack.class,
		Q10290_LandDragonConqueror.class,
		Q10291_FireDragonDestroyer.class,
		Q10292_SevenSignsGirlOfDoubt.class,
		Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.class,
		Q10294_SevenSignsToTheMonasteryOfSilence.class,
		Q10501_ZakenEmbroideredSoulCloak.class,
		Q10502_FreyaEmbroideredSoulCloak.class,
		Q10503_FrintezzaEmbroideredSoulCloak.class,
		Q10504_JewelOfAntharas.class,
		Q10505_JewelOfValakas.class
	};
	
	public static void main(String[] args)
	{
		for (Class<?> quest : QUESTS)
		{
			try
			{
				quest.newInstance();
			}
			catch (Exception e)
			{
				_log.log(Level.SEVERE, QuestMasterHandler.class.getSimpleName() + ": Failed loading " + quest.getSimpleName() + ":", e);
			}
		}
	}
}
