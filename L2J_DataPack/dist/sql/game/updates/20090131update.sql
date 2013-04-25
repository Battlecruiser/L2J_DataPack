ALTER TABLE `fort` ADD COLUMN `fortType` int(1) NOT NULL DEFAULT 0;
ALTER TABLE `fort` ADD COLUMN `state` int(1) NOT NULL DEFAULT 0;
ALTER TABLE `fort` ADD COLUMN `castleId` int(1) NOT NULL DEFAULT 0;
UPDATE `fort` SET `fortType` = '1' WHERE id IN (102,104,107,109,110,112,113,116,117,118);
ALTER TABLE `fortsiege_clans` DROP `type`;
ALTER TABLE `fortsiege_clans` DROP `fort_owner`;
DROP TABLE IF EXISTS `fort_door`;
UPDATE `fort` SET `name`='Swamp' WHERE id = 110;
UPDATE `fort` SET `name`='Cloud Mountain' WHERE id = 113;