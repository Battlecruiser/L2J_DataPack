CREATE TABLE IF NOT EXISTS `item_elementals` (
  `itemId` int(11) NOT NULL default 0,
  `elemType` tinyint(1) NOT NULL default -1,
  `elemValue` int(11) NOT NULL default -1,
  PRIMARY KEY (`itemId`, `elemType`)
);