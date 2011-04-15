CREATE TABLE IF NOT EXISTS `posts` (
  `serverId` int(8) NOT NULL DEFAULT '0',
  `post_id` int(8) NOT NULL DEFAULT '0',
  `post_ownerid` int(8) NOT NULL DEFAULT '0',
  `post_recipient_list` varchar(255) NOT NULL DEFAULT '0',
  `post_parent_id` int(8) NOT NULL DEFAULT '0',
  `post_date` decimal(20,0) NOT NULL DEFAULT '0',
  `post_topic_id` int(8) NOT NULL DEFAULT '0',
  `post_forum_id` int(8) NOT NULL DEFAULT '0',
  `post_txt` text NOT NULL,
  `post_title` text,
  `post_type` int(8) NOT NULL DEFAULT '0',
  `post_read_count` int(8) NOT NULL DEFAULT '0'
);
