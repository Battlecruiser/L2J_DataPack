ALTER TABLE `characters` ADD `createDate` date NOT NULL DEFAULT '0000-00-00' AFTER `createTime`;
UPDATE `characters` SET `createDate`=FROM_UNIXTIME(`createTime`/1000, '%Y-%m%-%d');
UPDATE `characters` SET `createDate`=CURDATE() WHERE `createDate`='0000-00-00';
ALTER TABLE `characters` DROP `createTime`;

ALTER TABLE `messages` CHANGE `isFourStars` `sendBySystem` tinyint(1) unsigned NOT NULL DEFAULT '0' AFTER `isLocked`;
ALTER TABLE `messages` CHANGE `isNews` `isReturned` enum('true','false') NOT NULL DEFAULT 'false' AFTER `sendBySystem`;

-- After alter table, false is setted to 2 but should be 0
UPDATE `messages` SET `sendBySystem` = 0 WHERE `sendBySystem` = 2;