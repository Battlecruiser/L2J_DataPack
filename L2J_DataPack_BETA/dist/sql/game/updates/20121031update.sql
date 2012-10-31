ALTER TABLE `clan_data` ADD `blood_alliance_count` SMALLINT(5) UNSIGNED NOT NULL DEFAULT 0 AFTER `hasCastle`;
ALTER TABLE `clan_data` ADD `blood_oath_count` SMALLINT(5) UNSIGNED NOT NULL DEFAULT 0 AFTER `blood_alliance_count`;
ALTER TABLE `castle` DROP `bloodAlliance`;
ALTER TABLE `fort` DROP `blood`;