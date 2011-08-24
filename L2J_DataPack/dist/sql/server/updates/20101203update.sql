DROP TABLE IF EXISTS `character_recommends`;

ALTER TABLE `characters` DROP `rec_have`;
ALTER TABLE `characters` DROP `rec_left`;
ALTER TABLE `characters` DROP `last_recom_date`;

DELETE FROM `global_tasks` WHERE `task`='sp_recommendations';
