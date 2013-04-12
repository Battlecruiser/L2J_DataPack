ALTER TABLE `castle_doorupgrade` MODIFY COLUMN `doorId` int(8) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `castle_doorupgrade` CHANGE COLUMN `hp` `ratio` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `doorId`;
ALTER TABLE `castle_doorupgrade` CHANGE COLUMN `pDef` `castleId` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `ratio`;
ALTER TABLE `castle_doorupgrade` DROP COLUMN `mDef`;