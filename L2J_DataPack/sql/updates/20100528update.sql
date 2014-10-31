ALTER TABLE `custom_teleport` ADD `itemId` decimal(11,0) NOT NULL default '57' AFTER `fornoble`;
UPDATE `custom_teleport` SET itemId = 13722 WHERE fornoble = 1;