ALTER TABLE `accounts` 
CHANGE `access_level` `accessLevel` TINYINT NOT NULL DEFAULT 0 ,
CHANGE `lastIP` `lastIP` CHAR( 15 ) NULL DEFAULT NULL ,
CHANGE `lastServer` `lastServer` TINYINT UNSIGNED NULL DEFAULT '1';