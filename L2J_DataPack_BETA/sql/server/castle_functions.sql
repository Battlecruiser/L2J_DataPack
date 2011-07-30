CREATE TABLE IF NOT EXISTS `castle_functions` (
  `castle_id` int(2) NOT NULL default '0',
  `type` int(1) NOT NULL default '0',
  `lvl` int(3) NOT NULL default '0',
  `lease` int(10) NOT NULL default '0',
  `rate` decimal(20,0) NOT NULL default '0',
  `endTime` bigint(13) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;