CREATE TABLE IF NOT EXISTS `character_norestart_zone_time` (
  `charId` int(10) unsigned NOT NULL DEFAULT '0',
  `time_limit` bigint(13) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY  (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;