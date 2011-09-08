DROP TABLE IF EXISTS `custom_notspawned`;
ALTER TABLE `custom_spawnlist` DROP COLUMN `id`;
ALTER TABLE `custom_spawnlist` DROP INDEX `key_npc_templateid`;