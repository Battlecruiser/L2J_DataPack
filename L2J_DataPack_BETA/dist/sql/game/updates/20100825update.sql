ALTER TABLE `custom_npc` ADD COLUMN `dropHerbGroup` int(2) NOT NULL DEFAULT '0';
ALTER TABLE `custom_npc` DROP COLUMN `drop_herbs`;