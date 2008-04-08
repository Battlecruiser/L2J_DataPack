-- ----------------------------
-- Table structure for fort
-- ----------------------------
CREATE TABLE IF NOT EXISTS `fort` (
  `id` int(11) NOT NULL default '0',
  `name` varchar(25) NOT NULL,
  `siegeDate` decimal(20,0) NOT NULL default '0',
  `siegeDayOfWeek` int(11) NOT NULL default '7',
  `siegeHourOfDay` int(11) NOT NULL default '20',
  `owner` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT IGNORE INTO `fort` VALUES 
('101','Shanty','0','0','0','0'),
('102','Southern','0','0','20','0'),
('103','Hive','0','0','20','0'),
('104','Valley','0','0','20','0'),
('105','Ivory','0','0','20','0'),
('106','Narsell','0','0','20','0'),
('107','Basin','0','0','20','0'),
('108','White Sands','0','0','20','0'),
('109','Borderland','0','0','20','0'),
('110','Marshland','0','0','20','0'),
('111','Archaic','0','0','20','0'),
('112','Floran','0','0','20','0'),
('113','Cloud Mountain','0','0','20','0'),
('114','Tanor','0','0','20','0'),
('115','Dragonspine','0','0','20','0'),
('116','Land Dragon','0','0','20','0'),
('117','Western Guard','0','0','20','0'),
('118','Hunters','0','0','20','0'),
('119','Aaru','0','0','20','0'),
('120','Demon','0','0','20','0'),
('121','Monastic','0','0','20','0');