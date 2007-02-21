CREATE TABLE `repair_character_quests` (
  `char_id` INT NOT NULL DEFAULT 0,
  `cond` VARCHAR(40) NOT NULL DEFAULT '',
  PRIMARY KEY  (`char_id`,`cond`)
  );

INSERT INTO `repair_character_quests` SELECT `char_id`,`value` FROM `character_quests` WHERE `name` = '336_CoinOfMagic' and `var`= 'cond';

UPDATE `character_quests` SET `value` = 'Solo' WHERE `name` = '336_CoinOfMagic' and `var` = '<state>' and `value` = 'Started' and `char_id` IN ( SELECT `char_id` FROM `repair_character_quests` WHERE `cond` < 4 );
UPDATE `character_quests` SET `value` = 'Party' WHERE `name` = '336_CoinOfMagic' and `var` = '<state>' and `value` = 'Started' and `char_id` IN ( SELECT `char_id` FROM `repair_character_quests` WHERE `cond` >= 4 );

DROP TABLE `repair_character_quests`;
