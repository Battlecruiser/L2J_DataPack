CREATE TABLE IF NOT EXISTS `heroes` (
  `charId` INT UNSIGNED NOT NULL default 0,
  `class_id` decimal(3,0) NOT NULL default 0,
  `count` decimal(3,0) NOT NULL default 0,
  `played` decimal(1,0) NOT NULL default 0,
  PRIMARY KEY (`charId`)
);