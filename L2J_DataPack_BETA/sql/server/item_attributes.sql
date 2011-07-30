CREATE TABLE IF NOT EXISTS `item_attributes` (
  `itemId` int(11) NOT NULL default 0,
  `augAttributes` int(11) NOT NULL default -1,
  `augSkillId` int(11) NOT NULL default -1,
  `augSkillLevel` int(11) NOT NULL default -1,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;