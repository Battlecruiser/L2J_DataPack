-- ----------------------------
-- Table structure for clan_notices
-- ----------------------------
CREATE TABLE IF NOT EXISTS `clan_notices` (
  `clan_id` INT NOT NULL DEFAULT 0,
  `enabled` enum('true','false') DEFAULT 'false' NOT NULL,
  `notice` TEXT NOT NULL,
  PRIMARY KEY  (`clan_id`)
);
