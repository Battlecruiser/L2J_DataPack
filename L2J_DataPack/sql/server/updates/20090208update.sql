ALTER TABLE `olympiad_nobles` DROP COLUMN `char_name`;
ALTER TABLE `olympiad_nobles` ADD COLUMN `competitions_won` decimal(3,0) NOT NULL default 0;
ALTER TABLE `olympiad_nobles` ADD COLUMN `competitions_lost` decimal(3,0) NOT NULL default 0;
ALTER TABLE `olympiad_nobles` ADD COLUMN `competitions_drawn` decimal(3,0) NOT NULL default 0;