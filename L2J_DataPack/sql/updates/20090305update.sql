ALTER TABLE `grandboss_list` DROP PRIMARY KEY;
ALTER TABLE `grandboss_list` ADD PRIMARY KEY (`player_id`,`zone`);