ALTER TABLE `teleport` ADD `itemId` decimal(11,0) NOT NULL default '57' AFTER `fornoble`;
ALTER TABLE `custom_teleport` ADD `itemId` decimal(11,0) NOT NULL default '57' AFTER `fornoble`;

UPDATE `teleport` SET itemId = 13722 WHERE fornoble = 1;
UPDATE `custom_teleport` SET itemId = 13722 WHERE fornoble = 1;