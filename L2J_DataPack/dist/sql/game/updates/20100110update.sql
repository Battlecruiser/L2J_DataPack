ALTER TABLE `seven_signs_status` MODIFY `date` decimal(20,0) NOT NULL DEFAULT 0;
-- UPDATE `seven_signs_status` SET `date` = UNIX_TIMESTAMP() * 1000;