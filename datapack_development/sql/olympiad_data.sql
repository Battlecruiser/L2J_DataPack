CREATE TABLE IF NOT EXISTS `olympiad_data` (
  `id` TINYINT UNSIGNED NOT NULL default 0,
  `current_cycle` MEDIUMINT UNSIGNED NOT NULL default 1,
  `period` MEDIUMINT UNSIGNED NOT NULL default 0,
  `olympiad_end` BIGINT UNSIGNED NOT NULL default 0,
  `validation_end` BIGINT UNSIGNED NOT NULL default 0,
  `next_weekly_change` BIGINT UNSIGNED NOT NULL default 0,
  PRIMARY KEY (`id`)
);
