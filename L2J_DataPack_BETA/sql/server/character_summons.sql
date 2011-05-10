CREATE TABLE IF NOT EXISTS `character_summons` (
  `ownerId` INT UNSIGNED NOT NULL DEFAULT 0,
  `summonSkillId` INT UNSIGNED NOT NULL DEFAULT 0,
  `curHp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `curMp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `time` decimal(11),
  PRIMARY KEY (`ownerId`,`summonSkillId`)
);