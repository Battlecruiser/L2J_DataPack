-- ----------------------------------
-- Table structure for custom_etcitem
-- ----------------------------------

CREATE TABLE IF NOT EXISTS `custom_etcitem` (
  `item_id` decimal(11,0) NOT NULL default '0',
  `name` varchar(100) default NULL,
  `crystallizable` varchar(5) default NULL,
  `item_type` varchar(14) default NULL,
  `weight` decimal(4,0) default NULL,
  `consume_type` varchar(9) default NULL,
  `material` varchar(11) default NULL,
  `crystal_type` varchar(4) NOT NULL default 'none',
  `duration` decimal(3,0) default NULL,
  `time` int(4) NOT NULL default '-1',
  `price` decimal(11,0) default NULL,
  `crystal_count` int(4) default NULL,
  `sellable` varchar(5) default NULL,
  `dropable` varchar(5) default NULL,
  `destroyable` varchar(5) default NULL,
  `tradeable` varchar(5) default NULL,
  `skill` varchar(70) default '0-0;',
  `html` varchar(5) default 'false',
  PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;