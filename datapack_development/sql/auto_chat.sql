DROP TABLE IF EXISTS `auto_chat`;
CREATE TABLE `auto_chat` (
  `groupId` INT NOT NULL default '0',
  `npcId` INT NOT NULL default '0',
  `chatDelay` BIGINT NOT NULL default '-1',
  PRIMARY KEY  (`groupId`)
) ENGINE=InnoDB;

INSERT INTO `auto_chat` VALUES 
(1,8093,-1),
(2,8094,-1);
