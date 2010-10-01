CREATE TABLE IF NOT EXISTS `custom_etcitem` (
  `item_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `additionalname` varchar(100) NOT NULL DEFAULT '',
  `crystallizable` enum('true','false') NOT NULL DEFAULT 'false',
  `item_type` varchar(14) NOT NULL DEFAULT 'none',
  `weight` smallint(4) NOT NULL DEFAULT '0',
  `consume_type` varchar(9) NOT NULL DEFAULT 'normal',
  `material` varchar(11) NOT NULL DEFAULT 'wood',
  `crystal_type` varchar(4) NOT NULL DEFAULT 'none',
  `duration` mediumint(5) NOT NULL DEFAULT '-1', -- duration in minutes for shadow items
  `time` mediumint(5) NOT NULL DEFAULT '-1',     -- duration in minutes for time limited items
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `crystal_count` smallint(4) unsigned NOT NULL DEFAULT '0',
  `sellable` enum('true','false') NOT NULL DEFAULT 'false',
  `dropable` enum('true','false') NOT NULL DEFAULT 'false',
  `destroyable` enum('true','false') NOT NULL DEFAULT 'false',
  `tradeable` enum('true','false') NOT NULL DEFAULT 'false',
  `depositable` enum('true','false') NOT NULL DEFAULT 'false',
  `handler` varchar(70) NOT NULL DEFAULT 'none',
  `skill` varchar(70) NOT NULL DEFAULT '0-0;',
  PRIMARY KEY (`item_id`)
);