CREATE TABLE IF NOT EXISTS `comments` (
  `serverId` int(8) NOT NULL DEFAULT '0',
  `comment_id` int(8) NOT NULL DEFAULT '0',
  `comment_ownerid` int(8) NOT NULL DEFAULT '0',
  `comment_date` decimal(20,0) NOT NULL DEFAULT '0',
  `comment_post_id` int(8) NOT NULL DEFAULT '0',
  `comment_topic_id` int(8) NOT NULL DEFAULT '0',
  `comment_forum_id` int(8) NOT NULL DEFAULT '0',
  `comment_txt` text NOT NULL
);
