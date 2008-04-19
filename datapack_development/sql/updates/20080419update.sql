ALTER TABLE `fort`
  DROP `siegeDayOfWeek`,
  DROP `siegeHourOfDay`,
  ADD `lastOwnedTime` DECIMAL( 20, 0 ) DEFAULT '0' NOT NULL AFTER `siegeDate` ;
  