CREATE TABLE IF NOT EXISTS `forums` (
  `serverId` int(8) NOT NULL DEFAULT '0',
  `forum_id` int(8) NOT NULL DEFAULT '0',
  `forum_name` varchar(255) NOT NULL DEFAULT '',
  `forum_post` int(8) NOT NULL DEFAULT '0',
  `forum_type` int(8) NOT NULL DEFAULT '0',
  `forum_owner_id` int(8) NOT NULL DEFAULT '0',
  PRIMARY KEY (`serverId`,`forum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;