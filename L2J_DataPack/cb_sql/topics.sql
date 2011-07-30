CREATE TABLE IF NOT EXISTS `topics` (
  `serverId` int(8) NOT NULL DEFAULT '0',
  `topic_id` int(8) NOT NULL DEFAULT '0',
  `topic_forum_id` int(8) NOT NULL DEFAULT '0',
  `topic_name` varchar(255) NOT NULL DEFAULT '',
  `topic_ownerid` int(8) NOT NULL DEFAULT '0',
  `topic_permissions` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;