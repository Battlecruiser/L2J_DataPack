ALTER TABLE `pets` DROP `karma`;
ALTER TABLE `pets` DROP `pkkills`;
ALTER TABLE `pets` ADD `weapon` int(5) NOT NULL default 0 AFTER `fed`;
ALTER TABLE `pets` ADD `armor` int(5) NOT NULL default 0 AFTER `weapon`;
ALTER TABLE `pets` ADD `jewel` int(5) NOT NULL default 0 AFTER `armor`;