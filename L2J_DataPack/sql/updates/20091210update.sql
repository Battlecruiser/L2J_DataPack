ALTER TABLE `custom_armor` ADD `additionalname` varchar(120) NOT NULL default '' AFTER `name`;
ALTER TABLE `custom_etcitem` ADD `additionalname` varchar(100) NOT NULL default '' AFTER `name`;
ALTER TABLE `custom_weapon` ADD `additionalname` varchar(120) NOT NULL default '' AFTER `name`;
ALTER TABLE `custom_npc` ADD `enchant` INT NOT NULL default 0 after `armor`;