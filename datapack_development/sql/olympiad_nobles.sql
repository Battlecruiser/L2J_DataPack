CREATE TABLE IF NOT EXISTS `olympiad_nobles` (
  `charId` INT UNSIGNED NOT NULL default 0,
  `class_id` decimal(3,0) NOT NULL default 0,
  `char_name` varchar(45) NOT NULL default '',
  `olympiad_points` decimal(10,0) NOT NULL default 0,
  `competitions_done` decimal(3,0) NOT NULL default 0,
  PRIMARY KEY (`charId`)
);