-- ----------------------------
-- Table structure for fortsiege_clans
-- ----------------------------
CREATE TABLE IF NOT EXISTS `fortsiege_clans` (
  `fort_id` int(1) NOT NULL default '0',
  `clan_id` int(11) NOT NULL default '0',
  PRIMARY KEY (`clan_id`,`fort_id`)
);