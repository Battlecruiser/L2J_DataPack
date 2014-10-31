CREATE TABLE IF NOT EXISTS `custom_npc_elementals` (
  `npc_id` decimal(11,0) NOT NULL default '0',
  `elemAtkType` tinyint(1) NOT NULL default -1,
  `elemAtkValue` int(3) NOT NULL default 0,
  `fireDefValue` int(3) NOT NULL default 0,
  `waterDefValue` int(3) NOT NULL default 0,
  `windDefValue` int(3) NOT NULL default 0,
  `earthDefValue` int(3) NOT NULL default 0,
  `holyDefValue` int(3) NOT NULL default 0,
  `darkDefValue` int(3) NOT NULL default 0,
  PRIMARY KEY (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;