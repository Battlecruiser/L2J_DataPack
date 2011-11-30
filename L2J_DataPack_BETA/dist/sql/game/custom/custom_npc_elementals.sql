CREATE TABLE IF NOT EXISTS `custom_npc_elementals` (
  `npc_id` mediumint(7) unsigned NOT NULL,
  `elemAtkType` tinyint(1) NOT NULL default '-1',
  `elemAtkValue` int(3) NOT NULL default '0',
  `fireDefValue` int(3) NOT NULL default '0',
  `waterDefValue` int(3) NOT NULL default '0',
  `windDefValue` int(3) NOT NULL default '0',
  `earthDefValue` int(3) NOT NULL default '0',
  `holyDefValue` int(3) NOT NULL default '0',
  `darkDefValue` int(3) NOT NULL default '0',
  PRIMARY KEY (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `custom_npc_elementals` VALUES
(50007,0,0,20,20,20,20,20,20),
(70010,0,0,20,20,20,20,20,20),
(1000003,0,0,20,20,20,20,20,20),
-- eventmod Elpies
(900100,0,0,20,20,20,20,20,20),
-- eventmod Rabbits
(900101,0,0,20,20,20,20,20,20),
(900102,0,0,20,20,20,20,20,20),
-- eventmod Race
(900103,0,0,20,20,20,20,20,20),
(900104,0,0,20,20,20,20,20,20);