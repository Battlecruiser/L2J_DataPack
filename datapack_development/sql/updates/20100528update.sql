ALTER TABLE `teleport` ADD `itemId` decimal(11,0) NOT NULL default '57' AFTER `fornoble`;
ALTER TABLE `custom_teleport` ADD `itemId` decimal(11,0) NOT NULL default '57' AFTER `fornoble`;

UPDATE `teleport` SET itemId = 13722 WHERE id BETWEEN 9900 AND 10027;
UPDATE `custom_teleport` SET itemId = 13722 WHERE fornoble = 1;