ALTER TABLE `custom_npc` MODIFY `sex` ENUM('etc','female','male') NOT NULL DEFAULT  'etc';
ALTER TABLE `custom_npc` MODIFY `hpreg` DECIMAL(10,4) NULL DEFAULT NULL;
ALTER TABLE `custom_npc` MODIFY `mpreg` DECIMAL(10,4) NULL DEFAULT NULL;
ALTER TABLE `custom_npc` ADD `targetable` TINYINT(1) NOT NULL DEFAULT '1' AFTER `runspd`;
ALTER TABLE `custom_npc` ADD `show_name`  TINYINT(1) NOT NULL DEFAULT '1' AFTER `targetable`;
ALTER TABLE `custom_npcaidata` CHANGE `canMove` `can_move` TINYINT(1) NOT NULL DEFAULT '1';