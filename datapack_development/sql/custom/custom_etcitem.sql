CREATE TABLE IF NOT EXISTS `custom_etcitem` (
 `item_id` decimal(11,0) NOT NULL default '0',
  `name` varchar(100) NOT NULL default '',
  `additionalname` varchar(100) NOT NULL default '',
  `crystallizable` varchar(5) NOT NULL default 'false',
  `item_type` varchar(14) NOT NULL default 'none',
  `weight` decimal(4,0) NOT NULL default '0',
  `consume_type` varchar(9) NOT NULL default 'normal',
  `material` varchar(11) NOT NULL default 'wood',
  `crystal_type` varchar(4) NOT NULL default 'none',
  `duration` int(3) NOT NULL default '-1', -- duration in minutes for shadow items
  `time` int(4) NOT NULL default '-1',     -- duration in minutes for time limited items
  `price` decimal(11,0) NOT NULL default '0',
  `crystal_count` int(4) NOT NULL default '0',
  `sellable` varchar(5) NOT NULL default 'false',
  `dropable` varchar(5) NOT NULL default 'false',
  `destroyable` varchar(5) NOT NULL default 'false',
  `tradeable` varchar(5) NOT NULL default 'false',
  `depositable` varchar(5) NOT NULL default 'false',
  `handler` varchar(70) NOT NULL DEFAULT 'none',
  `skill` varchar(70) NOT NULL DEFAULT '0-0;',
  PRIMARY KEY (`item_id`)
);