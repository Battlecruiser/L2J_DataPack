-- -------------------------------------------
-- Table structure for table `custom_droplist`
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS `custom_droplist` (
  `mobId` INT NOT NULL DEFAULT '0',
  `itemId` INT NOT NULL DEFAULT '0',
  `min` INT NOT NULL DEFAULT '0',
  `max` INT NOT NULL DEFAULT '0',
  `category` INT NOT NULL DEFAULT '0',
  `chance` INT NOT NULL DEFAULT '0',
  PRIMARY KEY  (`mobId`,`itemId`,`category`),
  KEY `key_mobId` (`mobId`)
);