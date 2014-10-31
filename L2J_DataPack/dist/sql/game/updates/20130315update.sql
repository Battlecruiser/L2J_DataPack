UPDATE character_quests SET name='Q00324_SweetestVenom' WHERE name='324_SweetestVenom';
UPDATE character_quests SET name='Q00328_SenseForBusiness' WHERE name='328_SenseForBusiness';
UPDATE character_quests SET name='Q00331_ArrowOfVengeance' WHERE name='331_ArrowForVengeance';
UPDATE character_quests SET name='Q00341_HuntingForWildBeasts' WHERE name='341_HuntingForWildBeasts';
UPDATE character_quests SET name='Q00605_AllianceWithKetraOrcs' WHERE name='605_AllianceWithKetraOrcs';
UPDATE character_quests SET name='Q00606_BattleAgainstVarkaSilenos' WHERE name='606_WarWithVarkaSilenos';
UPDATE character_quests SET name='Q00607_ProveYourCourageKetra' WHERE name='607_ProveYourCourage_Ketra';
UPDATE character_quests SET name='Q00608_SlayTheEnemyCommanderKetra' WHERE name='608_SlayTheEnemyCommander_Ketra';
UPDATE character_quests SET name='Q00609_MagicalPowerOfWaterPart1' WHERE name='609_MagicalPowerOfWaterPart1';
UPDATE character_quests SET name='Q00610_MagicalPowerOfWaterPart2' WHERE name='610_MagicalPowerOfWaterPart2';
UPDATE character_quests SET name='Q00611_AllianceWithVarkaSilenos' WHERE name='611_AllianceWithVarkaSilenos';
UPDATE character_quests SET name='Q00612_BattleAgainstKetraOrcs' WHERE name='612_WarWithKetraOrcs';
UPDATE character_quests SET name='Q00613_ProveYourCourageVarka' WHERE name='613_ProveYourCourage_Varka';
UPDATE character_quests SET name='Q00614_SlayTheEnemyCommanderVarka' WHERE name='614_SlayTheEnemyCommander_Varka';
UPDATE character_quests SET name='Q00615_MagicalPowerOfFirePart1' WHERE name='615_MagicalPowerOfFirePart1';
UPDATE character_quests SET name='Q00616_MagicalPowerOfFirePart2' WHERE name='616_MagicalPowerOfFirePart2';

UPDATE quest_global_data SET var='Q00610_respawn' WHERE var='610_respawn';
UPDATE quest_global_data SET var='Q00616_respawn' WHERE var='616_respawn';

ALTER TABLE `characters` DROP `varka_ketra_ally`;