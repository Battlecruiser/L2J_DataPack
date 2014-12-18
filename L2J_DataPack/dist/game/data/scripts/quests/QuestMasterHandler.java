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
import quests.Q00050_LanoscosSpecialBait.Q00050_LanoscosSpecialBait;
import quests.Q00051_OFullesSpecialBait.Q00051_OFullesSpecialBait;
import quests.Q00052_WilliesSpecialBait.Q00052_WilliesSpecialBait;
import quests.Q00061_LawEnforcement.Q00061_LawEnforcement;
import quests.Q00109_InSearchOfTheNest.Q00109_InSearchOfTheNest;
import quests.Q00110_ToThePrimevalIsle.Q00110_ToThePrimevalIsle;
import quests.Q00111_ElrokianHuntersProof.Q00111_ElrokianHuntersProof;
import quests.Q00113_StatusOfTheBeaconTower.Q00113_StatusOfTheBeaconTower;
import quests.Q00114_ResurrectionOfAnOldManager.Q00114_ResurrectionOfAnOldManager;
import quests.Q00115_TheOtherSideOfTruth.Q00115_TheOtherSideOfTruth;
import quests.Q00117_TheOceanOfDistantStars.Q00117_TheOceanOfDistantStars;
import quests.Q00119_LastImperialPrince.Q00119_LastImperialPrince;
import quests.Q00120_PavelsLastResearch.Q00120_PavelsLastResearch;
import quests.Q00121_PavelTheGiant.Q00121_PavelTheGiant;
import quests.Q00122_OminousNews.Q00122_OminousNews;
import quests.Q00124_MeetingTheElroki.Q00124_MeetingTheElroki;
import quests.Q00125_TheNameOfEvil1.Q00125_TheNameOfEvil1;
import quests.Q00126_TheNameOfEvil2.Q00126_TheNameOfEvil2;
import quests.Q00128_PailakaSongOfIceAndFire.Q00128_PailakaSongOfIceAndFire;
import quests.Q00129_PailakaDevilsLegacy.Q00129_PailakaDevilsLegacy;
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
import quests.Q00176_StepsForHonor.Q00176_StepsForHonor;
import quests.Q00183_RelicExploration.Q00183_RelicExploration;
import quests.Q00184_ArtOfPersuasion.Q00184_ArtOfPersuasion;
import quests.Q00185_NikolasCooperation.Q00185_NikolasCooperation;
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
import quests.Q00237_WindsOfChange.Q00237_WindsOfChange;
import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;
import quests.Q00239_WontYouJoinUs.Q00239_WontYouJoinUs;
import quests.Q00240_ImTheOnlyOneYouCanTrust.Q00240_ImTheOnlyOneYouCanTrust;
import quests.Q00249_PoisonedPlainsOfTheLizardmen.Q00249_PoisonedPlainsOfTheLizardmen;
import quests.Q00250_WatchWhatYouEat.Q00250_WatchWhatYouEat;
import quests.Q00251_NoSecrets.Q00251_NoSecrets;
import quests.Q00252_ItSmellsDelicious.Q00252_ItSmellsDelicious;
import quests.Q00254_LegendaryTales.Q00254_LegendaryTales;
import quests.Q00270_TheOneWhoEndsSilence.Q00270_TheOneWhoEndsSilence;
import quests.Q00278_HomeSecurity.Q00278_HomeSecurity;
import quests.Q00279_TargetOfOpportunity.Q00279_TargetOfOpportunity;
import quests.Q00287_FiguringItOut.Q00287_FiguringItOut;
import quests.Q00288_HandleWithCare.Q00288_HandleWithCare;
import quests.Q00289_NoMoreSoupForYou.Q00289_NoMoreSoupForYou;
import quests.Q00290_ThreatRemoval.Q00290_ThreatRemoval;
import quests.Q00299_GatherIngredientsForPie.Q00299_GatherIngredientsForPie;
import quests.Q00300_HuntingLetoLizardman.Q00300_HuntingLetoLizardman;
import quests.Q00307_ControlDeviceOfTheGiants.Q00307_ControlDeviceOfTheGiants;
import quests.Q00308_ReedFieldMaintenance.Q00308_ReedFieldMaintenance;
import quests.Q00309_ForAGoodCause.Q00309_ForAGoodCause;
import quests.Q00310_OnlyWhatRemains.Q00310_OnlyWhatRemains;
import quests.Q00311_ExpulsionOfEvilSpirits.Q00311_ExpulsionOfEvilSpirits;
import quests.Q00312_TakeAdvantageOfTheCrisis.Q00312_TakeAdvantageOfTheCrisis;
import quests.Q00325_GrimCollector.Q00325_GrimCollector;
import quests.Q00326_VanquishRemnants.Q00326_VanquishRemnants;
import quests.Q00327_RecoverTheFarmland.Q00327_RecoverTheFarmland;
import quests.Q00328_SenseForBusiness.Q00328_SenseForBusiness;
import quests.Q00329_CuriosityOfADwarf.Q00329_CuriosityOfADwarf;
import quests.Q00331_ArrowOfVengeance.Q00331_ArrowOfVengeance;
import quests.Q00338_AlligatorHunter.Q00338_AlligatorHunter;
import quests.Q00341_HuntingForWildBeasts.Q00341_HuntingForWildBeasts;
import quests.Q00344_1000YearsTheEndOfLamentation.Q00344_1000YearsTheEndOfLamentation;
import quests.Q00345_MethodToRaiseTheDead.Q00345_MethodToRaiseTheDead;
import quests.Q00350_EnhanceYourWeapon.Q00350_EnhanceYourWeapon;
import quests.Q00351_BlackSwan.Q00351_BlackSwan;
import quests.Q00352_HelpRoodRaiseANewPet.Q00352_HelpRoodRaiseANewPet;
import quests.Q00354_ConquestOfAlligatorIsland.Q00354_ConquestOfAlligatorIsland;
import quests.Q00355_FamilyHonor.Q00355_FamilyHonor;
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
import quests.Q00371_ShrieksOfGhosts.Q00371_ShrieksOfGhosts;
import quests.Q00376_ExplorationOfTheGiantsCavePart1.Q00376_ExplorationOfTheGiantsCavePart1;
import quests.Q00377_ExplorationOfTheGiantsCavePart2.Q00377_ExplorationOfTheGiantsCavePart2;
import quests.Q00378_GrandFeast.Q00378_GrandFeast;
import quests.Q00379_FantasyWine.Q00379_FantasyWine;
import quests.Q00380_BringOutTheFlavorOfIngredients.Q00380_BringOutTheFlavorOfIngredients;
import quests.Q00381_LetsBecomeARoyalMember.Q00381_LetsBecomeARoyalMember;
import quests.Q00382_KailsMagicCoin.Q00382_KailsMagicCoin;
import quests.Q00383_TreasureHunt.Q00383_TreasureHunt;
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
import quests.Q00501_ProofOfClanAlliance.Q00501_ProofOfClanAlliance;
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
import quests.Q10732_AForeignLand.Q10732_AForeignLand;
import quests.Q10733_TheTestForSurvival.Q10733_TheTestForSurvival;

/**
 * @author NosBit
 */
public class QuestMasterHandler
{
	private static final Logger _log = Logger.getLogger(QuestMasterHandler.class.getName());
	
	private static final Class<?>[] QUESTS =
	{
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
		Q00050_LanoscosSpecialBait.class,
		Q00051_OFullesSpecialBait.class,
		Q00052_WilliesSpecialBait.class,
		Q00061_LawEnforcement.class,
		Q00109_InSearchOfTheNest.class,
		Q00110_ToThePrimevalIsle.class,
		Q00111_ElrokianHuntersProof.class,
		Q00113_StatusOfTheBeaconTower.class,
		Q00114_ResurrectionOfAnOldManager.class,
		Q00115_TheOtherSideOfTruth.class,
		Q00117_TheOceanOfDistantStars.class,
		Q00119_LastImperialPrince.class,
		Q00120_PavelsLastResearch.class,
		Q00121_PavelTheGiant.class,
		Q00122_OminousNews.class,
		Q00124_MeetingTheElroki.class,
		Q00125_TheNameOfEvil1.class,
		Q00126_TheNameOfEvil2.class,
		Q00128_PailakaSongOfIceAndFire.class,
		Q00129_PailakaDevilsLegacy.class,
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
		Q00176_StepsForHonor.class,
		Q00183_RelicExploration.class,
		Q00184_ArtOfPersuasion.class,
		Q00185_NikolasCooperation.class,
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
		Q00237_WindsOfChange.class,
		Q00238_SuccessFailureOfBusiness.class,
		Q00239_WontYouJoinUs.class,
		Q00240_ImTheOnlyOneYouCanTrust.class,
		Q00249_PoisonedPlainsOfTheLizardmen.class,
		Q00250_WatchWhatYouEat.class,
		Q00251_NoSecrets.class,
		Q00252_ItSmellsDelicious.class,
		Q00254_LegendaryTales.class,
		Q00270_TheOneWhoEndsSilence.class,
		Q00278_HomeSecurity.class,
		Q00279_TargetOfOpportunity.class,
		Q00287_FiguringItOut.class,
		Q00288_HandleWithCare.class,
		Q00289_NoMoreSoupForYou.class,
		Q00290_ThreatRemoval.class,
		Q00299_GatherIngredientsForPie.class,
		Q00300_HuntingLetoLizardman.class,
		Q00307_ControlDeviceOfTheGiants.class,
		Q00308_ReedFieldMaintenance.class,
		Q00309_ForAGoodCause.class,
		Q00310_OnlyWhatRemains.class,
		Q00311_ExpulsionOfEvilSpirits.class,
		Q00312_TakeAdvantageOfTheCrisis.class,
		Q00325_GrimCollector.class,
		Q00326_VanquishRemnants.class,
		Q00327_RecoverTheFarmland.class,
		Q00328_SenseForBusiness.class,
		Q00329_CuriosityOfADwarf.class,
		Q00331_ArrowOfVengeance.class,
		Q00338_AlligatorHunter.class,
		Q00341_HuntingForWildBeasts.class,
		Q00344_1000YearsTheEndOfLamentation.class,
		Q00345_MethodToRaiseTheDead.class,
		Q00350_EnhanceYourWeapon.class,
		Q00351_BlackSwan.class,
		Q00352_HelpRoodRaiseANewPet.class,
		Q00354_ConquestOfAlligatorIsland.class,
		Q00355_FamilyHonor.class,
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
		Q00371_ShrieksOfGhosts.class,
		Q00376_ExplorationOfTheGiantsCavePart1.class,
		Q00377_ExplorationOfTheGiantsCavePart2.class,
		Q00378_GrandFeast.class,
		Q00379_FantasyWine.class,
		Q00380_BringOutTheFlavorOfIngredients.class,
		Q00381_LetsBecomeARoyalMember.class,
		Q00382_KailsMagicCoin.class,
		Q00383_TreasureHunt.class,
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
		Q00501_ProofOfClanAlliance.class,
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
		Q10505_JewelOfValakas.class,
		Q10732_AForeignLand.class,
		Q10733_TheTestForSurvival.class
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
