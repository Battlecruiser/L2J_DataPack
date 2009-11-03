CREATE TABLE IF NOT EXISTS `character_skills` (
  `charId` INT UNSIGNED NOT NULL default 0,
  `skill_id` INT NOT NULL default 0,
  `skill_level` INT(3) NOT NULL default 1,
  `class_index` INT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`,`skill_id`,`class_index`)
);