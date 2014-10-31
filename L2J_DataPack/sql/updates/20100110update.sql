ALTER TABLE `seven_signs_status` CHANGE `date` `date` decimal(20,0) NOT NULL default 0;
-- UPDATE `seven_signs_status` SET `date` = UNIX_TIMESTAMP() * 1000;