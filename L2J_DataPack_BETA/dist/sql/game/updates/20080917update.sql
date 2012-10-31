ALTER TABLE `castle` DROP COLUMN `siegeDayOfWeek`;
ALTER TABLE `castle` DROP COLUMN `siegeHourOfDay`;
ALTER TABLE `castle` ADD `regTimeOver` enum('true','false') DEFAULT 'true' NOT NULL AFTER `siegeDate`;
ALTER TABLE `castle` ADD `regTimeEnd` DECIMAL(20,0) NOT NULL DEFAULT 0 AFTER `regTimeOver`;