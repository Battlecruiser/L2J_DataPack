CREATE TABLE IF NOT EXISTS `custom_armor` (
  `item_id` int(11) NOT NULL default '0',
  `name` varchar(120) NOT NULL default '',
  `additionalname` varchar(120) NOT NULL default '',
  `bodypart` varchar(15) NOT NULL default 'none',
  `crystallizable` varchar(5) NOT NULL default 'false',
  `armor_type` varchar(5) NOT NULL default 'none',
  `weight` int(5) NOT NULL default '0',
  `material` varchar(15) NOT NULL default 'wood',
  `crystal_type` varchar(4) NOT NULL default 'none',
  `avoid_modify` int(1) NOT NULL default '0',
  `duration` int(3) NOT NULL default '-1', -- duration in minutes for shadown items
  `time` int(4) NOT NULL default '-1',     -- duration in minutes for time limited items
  `p_def` int(3) NOT NULL default '0',
  `m_def` int(2) NOT NULL default '0',
  `mp_bonus` int(3) NOT NULL default '0',
  `price` int(11) NOT NULL default '0',
  `crystal_count` int(4) NOT NULL default '0',
  `sellable` varchar(5) NOT NULL default 'false',
  `dropable` varchar(5) NOT NULL default 'false',
  `destroyable` varchar(5) NOT NULL default 'false',
  `tradeable` varchar(5) NOT NULL default 'false',
  `depositable` varchar(5) NOT NULL default 'false',
  `enchant4_skill` varchar(8) NOT NULL default '0-0',
  `skill` varchar(70) default '0-0;',
  PRIMARY KEY (`item_id`)
);