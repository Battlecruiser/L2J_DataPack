CREATE TABLE IF NOT EXISTS `cursed_weapons` (
  `itemId` INT,
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `playerKarma` INT DEFAULT 0,
  `playerPkKills` INT DEFAULT 0,
  `nbKills` INT DEFAULT 0,
  `endTime` DECIMAL(20,0) DEFAULT 0,
  PRIMARY KEY (`itemId`)
);