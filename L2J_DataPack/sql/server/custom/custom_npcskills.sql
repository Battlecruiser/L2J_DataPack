CREATE TABLE IF NOT EXISTS `custom_npcskills` (
  `npcid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `skillid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `level` tinyint(2) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`npcid`,`skillid`,`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;