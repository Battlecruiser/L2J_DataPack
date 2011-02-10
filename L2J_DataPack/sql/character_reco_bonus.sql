CREATE TABLE IF NOT EXISTS `character_reco_bonus` (
  `charId` int(10) unsigned NOT NULL,
  `rec_have` int(3) unsigned NOT NULL DEFAULT '0',
  `rec_left` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `time_left` decimal(20,0) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `charId` (`charId`)
);