-- ----------------------------
-- Table structure for character_friends
-- ---------------------------- 
CREATE TABLE IF NOT EXISTS `character_friends` ( 
  `charId` INT UNSIGNED NOT NULL default 0,
  `friendId` INT UNSIGNED NOT NULL DEFAULT 0,
  `friend_name` VARCHAR(35) NOT NULL DEFAULT '',
  PRIMARY KEY (`charId`,`friend_name`)
);