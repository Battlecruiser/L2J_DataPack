CREATE TABLE IF NOT EXISTS `registered_gameservers` (
  `serverId` int(11) NOT NULL default '0',
  `hex_id` varchar(50) NOT NULL default '',
  `host` varchar(50) NOT NULL default '',
  PRIMARY KEY (`serverId`)
);