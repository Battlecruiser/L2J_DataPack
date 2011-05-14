ALTER TABLE `castle` ADD COLUMN `bloodAlliance` int(3) NOT NULL DEFAULT 0 AFTER `showNpcCrest`;

ALTER TABLE `pets`
ADD COLUMN `ownerId` int(10) NOT NULL DEFAULT '0' AFTER `fed`,
ADD COLUMN `restore` enum('true','false') NOT NULL DEFAULT 'false' AFTER `ownerId`;