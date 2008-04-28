-- --------------------------------
-- Table structure for custom_armor
-- --------------------------------
DROP TABLE IF EXISTS `custom_armor`;
CREATE TABLE IF NOT EXISTS `custom_armor` (
  `item_id` int(11) NOT NULL default '0',
  `name` varchar(70) default NULL,
  `bodypart` varchar(15) NOT NULL default '',
  `crystallizable` varchar(5) NOT NULL default '',
  `armor_type` varchar(5) NOT NULL default '',
  `weight` int(5) NOT NULL default '0',
  `material` varchar(15) NOT NULL default '',
  `crystal_type` varchar(4) NOT NULL default '',
  `avoid_modify` int(1) NOT NULL default '0',
  `duration` int(3) NOT NULL default '0',
  `p_def` int(3) NOT NULL default '0',
  `m_def` int(2) NOT NULL default '0',
  `mp_bonus` int(3) NOT NULL default '0',
  `price` int(11) NOT NULL default '0',
  `crystal_count` int(4) default NULL,
  `sellable` varchar(5) default NULL,
  `dropable` varchar(5) NOT NULL default 'true',
  `destroyable` varchar(5) NOT NULL default 'true',
  `tradeable` varchar(5) NOT NULL default 'true',
  `skill` varchar(70) default '0-0;',
  PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;
