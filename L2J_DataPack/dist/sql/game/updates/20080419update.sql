ALTER TABLE `fort` DROP `siegeDayOfWeek`;
ALTER TABLE `fort` DROP `siegeHourOfDay`;
ALTER TABLE `fort` ADD `lastOwnedTime` DECIMAL(20,0) DEFAULT '0' NOT NULL AFTER `siegeDate`;