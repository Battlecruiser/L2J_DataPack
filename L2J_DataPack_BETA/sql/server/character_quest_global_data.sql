CREATE TABLE IF NOT EXISTS `character_quest_global_data` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `var`  VARCHAR(20) NOT NULL DEFAULT '',
  `value` VARCHAR(255) ,
  PRIMARY KEY (`charId`,`var`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;