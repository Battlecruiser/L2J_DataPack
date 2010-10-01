CREATE TABLE IF NOT EXISTS `custom_armor` (
  `item_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `name` varchar(120) NOT NULL DEFAULT '',
  `additionalname` varchar(120) NOT NULL DEFAULT '',
  `bodypart` varchar(15) NOT NULL DEFAULT 'none',
  `crystallizable` enum('true','false') NOT NULL DEFAULT 'false',
  `armor_type` varchar(5) NOT NULL DEFAULT 'none',
  `weight` smallint(4) NOT NULL DEFAULT '0',
  `material` varchar(15) NOT NULL DEFAULT 'wood',
  `crystal_type` varchar(4) NOT NULL DEFAULT 'none',
  `avoid_modify` tinyint(2) NOT NULL DEFAULT '0',
  `duration` mediumint(5) NOT NULL DEFAULT '-1', -- duration in minutes for shadow items
  `time` mediumint(5) NOT NULL DEFAULT '-1',     -- duration in minutes for time limited items
  `p_def` smallint(3) NOT NULL DEFAULT '0',
  `m_def` smallint(3) NOT NULL DEFAULT '0',
  `mp_bonus` smallint(3) NOT NULL DEFAULT '0',
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `crystal_count` smallint(4) unsigned NOT NULL DEFAULT '0',
  `sellable` enum('true','false') NOT NULL DEFAULT 'false',
  `dropable` enum('true','false') NOT NULL DEFAULT 'false',
  `destroyable` enum('true','false') NOT NULL DEFAULT 'false',
  `tradeable` enum('true','false') NOT NULL DEFAULT 'false',
  `depositable` enum('true','false') NOT NULL DEFAULT 'false',
  `enchant4_skill` varchar(8) NOT NULL DEFAULT '0-0',
  `skill` varchar(70) DEFAULT '0-0;',
  PRIMARY KEY (`item_id`)
);