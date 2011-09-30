CREATE TABLE IF NOT EXISTS `character_norestart_zone_time` (
  `charId` int(10) unsigned NOT NULL default '0',
  `time_limit` bigint(13) unsigned NOT NULL default '0',
  PRIMARY KEY  (`charId`)
);