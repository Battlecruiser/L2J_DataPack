ALTER TABLE `items` DROP `time_left`;
alter table `items` add `time` decimal NOT NULL DEFAULT 0 after `mana_left`;