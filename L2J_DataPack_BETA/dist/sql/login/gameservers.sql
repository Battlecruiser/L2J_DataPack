CREATE TABLE IF NOT EXISTS `gameservers` (
  `server_id` int(11) NOT NULL DEFAULT '0',
  `hexid` varchar(50) NOT NULL DEFAULT '',
  `host` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;