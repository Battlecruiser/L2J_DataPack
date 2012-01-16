ALTER TABLE `custom_npc` DROP COLUMN `aggro`;
ALTER TABLE `custom_npc` DROP COLUMN `targetable`;
ALTER TABLE `custom_npc` DROP COLUMN `show_name`;

ALTER TABLE `custom_npcaidata` ADD COLUMN `targetable` tinyint(1) NOT NULL DEFAULT '1' AFTER `canMove`;
ALTER TABLE `custom_npcaidata` ADD COLUMN `showName` tinyint(1) NOT NULL DEFAULT '1' AFTER `targetable`;
ALTER TABLE `custom_npcaidata` ADD COLUMN `aggro` smallint(4) NOT NULL DEFAULT '0' AFTER `ssChance`;