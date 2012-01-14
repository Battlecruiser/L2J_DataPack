ALTER TABLE `accounts` ADD COLUMN `email` varchar(255) DEFAULT NULL AFTER `password`;
ALTER TABLE `accounts` ADD COLUMN `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `email`;
UPDATE `accounts` SET `created_time`=NOW() WHERE `created_time`='0000-00-00 00:00:00';