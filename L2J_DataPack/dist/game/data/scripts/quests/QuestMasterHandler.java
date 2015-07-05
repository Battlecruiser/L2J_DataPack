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
package quests;

import java.util.logging.Level;
import java.util.logging.Logger;

import quests.Q00013_ParcelDelivery.Q00013_ParcelDelivery;
import quests.Q00015_SweetWhispers.Q00015_SweetWhispers;
import quests.Q00016_TheComingDarkness.Q00016_TheComingDarkness;
import quests.Q00017_LightAndDarkness.Q00017_LightAndDarkness;
import quests.Q00019_GoToThePastureland.Q00019_GoToThePastureland;
import quests.Q00020_BringUpWithLove.Q00020_BringUpWithLove;
import quests.Q00026_TiredOfWaiting.Q00026_TiredOfWaiting;
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
import quests.Q00061_LawEnforcement.Q00061_LawEnforcement;
import quests.Q00109_InSearchOfTheNest.Q00109_InSearchOfTheNest;
import quests.Q00110_ToThePrimevalIsle.Q00110_ToThePrimevalIsle;
import quests.Q00111_ElrokianHuntersProof.Q00111_ElrokianHuntersProof;
import quests.Q00113_StatusOfTheBeaconTower.Q00113_StatusOfTheBeaconTower;
import quests.Q00114_ResurrectionOfAnOldManager.Q00114_ResurrectionOfAnOldManager;
import quests.Q00115_TheOtherSideOfTruth.Q00115_TheOtherSideOfTruth;
import quests.Q00117_TheOceanOfDistantStars.Q00117_TheOceanOfDistantStars;
import quests.Q00118_ToLeadAndBeLed.Q00118_ToLeadAndBeLed;
import quests.Q00119_LastImperialPrince.Q00119_LastImperialPrince;
import quests.Q00120_PavelsLastResearch.Q00120_PavelsLastResearch;
import quests.Q00121_PavelTheGiant.Q00121_PavelTheGiant;
import quests.Q00122_OminousNews.Q00122_OminousNews;
import quests.Q00123_TheLeaderAndTheFollower.Q00123_TheLeaderAndTheFollower;
import quests.Q00124_MeetingTheElroki.Q00124_MeetingTheElroki;
import quests.Q00125_TheNameOfEvil1.Q00125_TheNameOfEvil1;
import quests.Q00126_TheNameOfEvil2.Q00126_TheNameOfEvil2;
import quests.Q00128_PailakaSongOfIceAndFire.Q00128_PailakaSongOfIceAndFire;
import quests.Q00129_PailakaDevilsLegacy.Q00129_PailakaDevilsLegacy;
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
import quests.Q00177_SplitDestiny.Q00177_SplitDestiny;
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
import quests.Q00236_SeedsOfChaos.Q00236_SeedsOfChaos;
import quests.Q00237_WindsOfChange.Q00237_WindsOfChange;
import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;
import quests.Q00239_WontYouJoinUs.Q00239_WontYouJoinUs;
import quests.Q00240_ImTheOnlyOneYouCanTrust.Q00240_ImTheOnlyOneYouCanTrust;
import quests.Q00254_LegendaryTales.Q00254_LegendaryTales;
import quests.Q00270_TheOneWhoEndsSilence.Q00270_TheOneWhoEndsSilence;
import quests.Q00278_HomeSecurity.Q00278_HomeSecurity;
import quests.Q00279_TargetOfOpportunity.Q00279_TargetOfOpportunity;
import quests.Q00300_HuntingLetoLizardman.Q00300_HuntingLetoLizardman;
import quests.Q00307_ControlDeviceOfTheGiants.Q00307_ControlDeviceOfTheGiants;
import quests.Q00310_OnlyWhatRemains.Q00310_OnlyWhatRemains;
import quests.Q00326_VanquishRemnants.Q00326_VanquishRemnants;
import quests.Q00333_HuntOfTheBlackLion.Q00333_HuntOfTheBlackLion;
import quests.Q00337_AudienceWithTheLandDragon.Q00337_AudienceWithTheLandDragon;
import quests.Q00350_EnhanceYourWeapon.Q00350_EnhanceYourWeapon;
import quests.Q00357_WarehouseKeepersAmbition.Q00357_WarehouseKeepersAmbition;
import quests.Q00359_ForASleeplessDeadman.Q00359_ForASleeplessDeadman;
import quests.Q00371_ShrieksOfGhosts.Q00371_ShrieksOfGhosts;
import quests.Q00373_SupplierOfReagents.Q00373_SupplierOfReagents;
import quests.Q00376_ExplorationOfTheGiantsCavePart1.Q00376_ExplorationOfTheGiantsCavePart1;
import quests.Q00377_ExplorationOfTheGiantsCavePart2.Q00377_ExplorationOfTheGiantsCavePart2;
import quests.Q00381_LetsBecomeARoyalMember.Q00381_LetsBecomeARoyalMember;
import quests.Q00382_KailsMagicCoin.Q00382_KailsMagicCoin;
import quests.Q00420_LittleWing.Q00420_LittleWing;
import quests.Q00421_LittleWingsBigAdventure.Q00421_LittleWingsBigAdventure;
import quests.Q00422_RepentYourSins.Q00422_RepentYourSins;
import quests.Q00426_QuestForFishingShot.Q00426_QuestForFishingShot;
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
import quests.Q00617_GatherTheFlames.Q00617_GatherTheFlames;
import quests.Q00618_IntoTheFlame.Q00618_IntoTheFlame;
import quests.Q00621_EggDelivery.Q00621_EggDelivery;
import quests.Q00622_SpecialtyLiquorDelivery.Q00622_SpecialtyLiquorDelivery;
import quests.Q00623_TheFinestFood.Q00623_TheFinestFood;
import quests.Q00624_TheFinestIngredientsPart1.Q00624_TheFinestIngredientsPart1;
import quests.Q00625_TheFinestIngredientsPart2.Q00625_TheFinestIngredientsPart2;
import quests.Q00626_ADarkTwilight.Q00626_ADarkTwilight;
import quests.Q00627_HeartInSearchOfPower.Q00627_HeartInSearchOfPower;
import quests.Q00631_DeliciousTopChoiceMeat.Q00631_DeliciousTopChoiceMeat;
import quests.Q00641_AttackSailren.Q00641_AttackSailren;
import quests.Q00642_APowerfulPrimevalCreature.Q00642_APowerfulPrimevalCreature;
import quests.Q00643_RiseAndFallOfTheElrokiTribe.Q00643_RiseAndFallOfTheElrokiTribe;
import quests.Q00645_GhostsOfBatur.Q00645_GhostsOfBatur;
import quests.Q00647_InfluxOfMachines.Q00647_InfluxOfMachines;
import quests.Q00648_AnIceMerchantsDream.Q00648_AnIceMerchantsDream;
import quests.Q00650_ABrokenDream.Q00650_ABrokenDream;
import quests.Q00652_AnAgedExAdventurer.Q00652_AnAgedExAdventurer;
import quests.Q00655_AGrandPlanForTamingWildBeasts.Q00655_AGrandPlanForTamingWildBeasts;
import quests.Q00662_AGameOfCards.Q00662_AGameOfCards;
import quests.Q00688_DefeatTheElrokianRaiders.Q00688_DefeatTheElrokianRaiders;
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
import quests.Q10273_GoodDayToFly.Q10273_GoodDayToFly;
import quests.Q10274_CollectingInTheAir.Q10274_CollectingInTheAir;
import quests.Q10275_ContainingTheAttributePower.Q10275_ContainingTheAttributePower;
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
import quests.Q10320_LetsGoToTheCentralSquare.Q10320_LetsGoToTheCentralSquare;
import quests.Q10321_QualificationsOfTheSeeker.Q10321_QualificationsOfTheSeeker;
import quests.Q10322_SearchingForTheMysteriousPower.Q10322_SearchingForTheMysteriousPower;
import quests.Q10323_TrainLikeItsReal.Q10323_TrainLikeItsReal;
import quests.Q10324_FindingMagisterGallint.Q10324_FindingMagisterGallint;
import quests.Q10325_SearchingForNewPower.Q10325_SearchingForNewPower;
import quests.Q10326_RespectYourElders.Q10326_RespectYourElders;
import quests.Q10338_SeizeYourDestiny.Q10338_SeizeYourDestiny;
import quests.Q10501_ZakenEmbroideredSoulCloak.Q10501_ZakenEmbroideredSoulCloak;
import quests.Q10502_FreyaEmbroideredSoulCloak.Q10502_FreyaEmbroideredSoulCloak;
import quests.Q10503_FrintezzaEmbroideredSoulCloak.Q10503_FrintezzaEmbroideredSoulCloak;
import quests.Q10504_JewelOfAntharas.Q10504_JewelOfAntharas;
import quests.Q10505_JewelOfValakas.Q10505_JewelOfValakas;
import quests.Q10732_AForeignLand.Q10732_AForeignLand;
import quests.Q10733_TheTestForSurvival.Q10733_TheTestForSurvival;
import quests.Q10734_DoOrDie.Q10734_DoOrDie;
import quests.Q10735_ASpecialPower.Q10735_ASpecialPower;
import quests.Q10736_ASpecialPower.Q10736_ASpecialPower;
import quests.Q10737_GrakonsWarehouse.Q10737_GrakonsWarehouse;
import quests.Q10738_AnInnerBeauty.Q10738_AnInnerBeauty;
import quests.Q10739_SupplyAndDemand.Q10739_SupplyAndDemand;
import quests.Q10740_NeverForget.Q10740_NeverForget;
import quests.Q10741_ADraughtForTheCold.Q10741_ADraughtForTheCold;
import quests.Q10742_AFurryFriend.Q10742_AFurryFriend;
import quests.Q10743_StrangeFungus.Q10743_StrangeFungus;
import quests.Q10744_StrongerThanSteel.Q10744_StrongerThanSteel;

/**
 * @author NosBit
 */
public class QuestMasterHandler
{
	private static final Logger _log = Logger.getLogger(QuestMasterHandler.class.getName());
	
	private static final Class<?>[] QUESTS =
	{
		Q00013_ParcelDelivery.class,
		Q00015_SweetWhispers.class,
		Q00016_TheComingDarkness.class,
		Q00017_LightAndDarkness.class,
		Q00019_GoToThePastureland.class,
		Q00020_BringUpWithLove.class,
		Q00026_TiredOfWaiting.class,
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
		Q00061_LawEnforcement.class,
		Q00109_InSearchOfTheNest.class,
		Q00110_ToThePrimevalIsle.class,
		Q00111_ElrokianHuntersProof.class,
		Q00113_StatusOfTheBeaconTower.class,
		Q00114_ResurrectionOfAnOldManager.class,
		Q00115_TheOtherSideOfTruth.class,
		Q00117_TheOceanOfDistantStars.class,
		Q00118_ToLeadAndBeLed.class,
		Q00119_LastImperialPrince.class,
		Q00120_PavelsLastResearch.class,
		Q00121_PavelTheGiant.class,
		Q00122_OminousNews.class,
		Q00123_TheLeaderAndTheFollower.class,
		Q00124_MeetingTheElroki.class,
		Q00125_TheNameOfEvil1.class,
		Q00126_TheNameOfEvil2.class,
		Q00128_PailakaSongOfIceAndFire.class,
		Q00129_PailakaDevilsLegacy.class,
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
		Q00177_SplitDestiny.class,
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
		Q00236_SeedsOfChaos.class,
		Q00237_WindsOfChange.class,
		Q00238_SuccessFailureOfBusiness.class,
		Q00239_WontYouJoinUs.class,
		Q00240_ImTheOnlyOneYouCanTrust.class,
		Q00254_LegendaryTales.class,
		Q00270_TheOneWhoEndsSilence.class,
		Q00278_HomeSecurity.class,
		Q00279_TargetOfOpportunity.class,
		Q00300_HuntingLetoLizardman.class,
		Q00307_ControlDeviceOfTheGiants.class,
		Q00310_OnlyWhatRemains.class,
		Q00326_VanquishRemnants.class,
		Q00333_HuntOfTheBlackLion.class,
		Q00337_AudienceWithTheLandDragon.class,
		Q00350_EnhanceYourWeapon.class,
		Q00357_WarehouseKeepersAmbition.class,
		Q00359_ForASleeplessDeadman.class,
		Q00371_ShrieksOfGhosts.class,
		Q00373_SupplierOfReagents.class,
		Q00376_ExplorationOfTheGiantsCavePart1.class,
		Q00377_ExplorationOfTheGiantsCavePart2.class,
		Q00381_LetsBecomeARoyalMember.class,
		Q00382_KailsMagicCoin.class,
		Q00420_LittleWing.class,
		Q00421_LittleWingsBigAdventure.class,
		Q00422_RepentYourSins.class,
		Q00426_QuestForFishingShot.class,
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
		Q00617_GatherTheFlames.class,
		Q00618_IntoTheFlame.class,
		Q00621_EggDelivery.class,
		Q00622_SpecialtyLiquorDelivery.class,
		Q00623_TheFinestFood.class,
		Q00624_TheFinestIngredientsPart1.class,
		Q00625_TheFinestIngredientsPart2.class,
		Q00626_ADarkTwilight.class,
		Q00627_HeartInSearchOfPower.class,
		Q00631_DeliciousTopChoiceMeat.class,
		Q00641_AttackSailren.class,
		Q00642_APowerfulPrimevalCreature.class,
		Q00643_RiseAndFallOfTheElrokiTribe.class,
		Q00645_GhostsOfBatur.class,
		Q00647_InfluxOfMachines.class,
		Q00648_AnIceMerchantsDream.class,
		Q00650_ABrokenDream.class,
		Q00652_AnAgedExAdventurer.class,
		Q00655_AGrandPlanForTamingWildBeasts.class,
		Q00662_AGameOfCards.class,
		Q00688_DefeatTheElrokianRaiders.class,
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
		Q10273_GoodDayToFly.class,
		Q10274_CollectingInTheAir.class,
		Q10275_ContainingTheAttributePower.class,
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
		Q10320_LetsGoToTheCentralSquare.class,
		Q10321_QualificationsOfTheSeeker.class,
		Q10322_SearchingForTheMysteriousPower.class,
		Q10323_TrainLikeItsReal.class,
		Q10324_FindingMagisterGallint.class,
		Q10325_SearchingForNewPower.class,
		Q10326_RespectYourElders.class,
		Q10338_SeizeYourDestiny.class,
		Q10501_ZakenEmbroideredSoulCloak.class,
		Q10502_FreyaEmbroideredSoulCloak.class,
		Q10503_FrintezzaEmbroideredSoulCloak.class,
		Q10504_JewelOfAntharas.class,
		Q10505_JewelOfValakas.class,
		Q10732_AForeignLand.class,
		Q10733_TheTestForSurvival.class,
		Q10734_DoOrDie.class,
		Q10735_ASpecialPower.class,
		Q10736_ASpecialPower.class,
		Q10737_GrakonsWarehouse.class,
		Q10738_AnInnerBeauty.class,
		Q10739_SupplyAndDemand.class,
		Q10740_NeverForget.class,
		Q10741_ADraughtForTheCold.class,
		Q10742_AFurryFriend.class,
		Q10743_StrangeFungus.class,
		Q10744_StrongerThanSteel.class
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
