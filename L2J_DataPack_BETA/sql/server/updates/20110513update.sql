ALTER TABLE `pets` ADD COLUMN `ownerId` decimal(11) NOT NULL DEFAULT '0' AFTER `fed`;
ALTER TABLE `pets` ADD COLUMN `restore` enum('true','false') NOT NULL DEFAULT 'false' AFTER `ownerId`;