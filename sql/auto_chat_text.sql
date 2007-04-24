DROP TABLE IF EXISTS `auto_chat_text`;
CREATE TABLE `auto_chat_text` (
  `groupId` INT NOT NULL default '0',
  `chatText` VARCHAR(255) NOT NULL default '',
  PRIMARY KEY  (`groupId`,`chatText`)
) ENGINE=InnoDB;

INSERT INTO `auto_chat_text` VALUES 
(1,'%player_cabal_loser%! All is lost! Prepare to meet the goddess of death!'),
(1,'%player_cabal_loser%! You bring an ill wind!'),
(1,'%player_cabal_loser%! You might as well give up!'),
(1,'A curse upon you!'),
(1,'All is lost! Prepare to meet the goddess of death!'),
(1,'All is lost! The prophecy of destruction has been fulfilled!'),
(1,'The prophecy of doom has awoken!'),
(1,'This world will soon be annihilated!'),
(2,'%player_cabal_winner%! I bestow on you the authority of the abyss!'),
(2,'%player_cabal_winner%, Darkness shall be banished forever!'),
(2,'%player_cabal_winner%, the time for glory is at hand!'),
(2,'All hail the eternal twilight!'),
(2,'As foretold in the prophecy of darkness, the era of chaos has begun!'),
(2,'The day of judgment is near!'),
(2,'The prophecy of darkness has been fulfilled!'),
(2,'The prophecy of darkness has come to pass!');
