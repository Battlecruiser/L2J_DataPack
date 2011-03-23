CREATE TABLE IF NOT EXISTS `itemsonground` (
  `object_id` int(11) NOT NULL default '0',
  `item_id` int(11) default NULL,
  `count` BIGINT UNSIGNED NOT NULL default 0,
  `enchant_level` int(11) default NULL,
  `x` int(11) default NULL,
  `y` int(11) default NULL,
  `z` int(11) default NULL,
  `drop_time` bigint(13) unsigned NOT NULL DEFAULT '0',
  `equipable` int(1) default '0',
  PRIMARY KEY (`object_id`)
);