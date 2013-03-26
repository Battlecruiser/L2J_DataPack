ALTER TABLE `character_macroses` MODIFY `commands` VARCHAR( 500 ) NULL DEFAULT NULL;
ALTER TABLE `heroes` ADD COLUMN `message` varchar(300) NOT NULL DEFAULT '';