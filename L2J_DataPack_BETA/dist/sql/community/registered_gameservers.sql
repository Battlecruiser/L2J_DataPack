CREATE TABLE IF NOT EXISTS `registered_gameservers` (
  `serverId` int(11) NOT NULL DEFAULT '0',
  `hex_id` varchar(50) NOT NULL DEFAULT '',
  `host` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`serverId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;