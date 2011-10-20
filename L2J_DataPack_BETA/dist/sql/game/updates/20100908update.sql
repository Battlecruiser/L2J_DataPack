ALTER TABLE `clan_skills` ADD COLUMN `sub_pledge_id` INT NOT NULL default '-2';
ALTER TABLE `clan_skills` DROP PRIMARY KEY;
ALTER TABLE `clan_skills` ADD PRIMARY KEY (`clan_id`,`skill_id`,`sub_pledge_id`);