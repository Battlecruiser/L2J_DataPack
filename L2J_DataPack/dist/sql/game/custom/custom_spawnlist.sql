CREATE TABLE IF NOT EXISTS `custom_spawnlist` (
  `location` varchar(40) NOT NULL DEFAULT '',
  `count` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `npc_templateid` mediumint(7) unsigned NOT NULL DEFAULT '0',
  `locx` mediumint(6) NOT NULL DEFAULT '0',
  `locy` mediumint(6) NOT NULL DEFAULT '0',
  `locz` mediumint(6) NOT NULL DEFAULT '0',
  `randomx` mediumint(6) NOT NULL DEFAULT '0',
  `randomy` mediumint(6) NOT NULL DEFAULT '0',
  `heading` mediumint(6) NOT NULL DEFAULT '0',
  `respawn_delay` mediumint(5) NOT NULL DEFAULT '0',
  `loc_id` int(9) NOT NULL DEFAULT '0',
  `periodOfDay` tinyint(1) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;