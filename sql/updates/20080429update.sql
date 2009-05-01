ALTER TABLE `clan_subpledges` ADD COLUMN `leader_id` INTEGER NOT NULL DEFAULT 0;
UPDATE `clan_subpledges` , `characters` SET clan_subpledges.leader_id = characters.obj_id WHERE clan_subpledges.leader_name = characters.char_name;
ALTER TABLE `clan_subpledges` DROP COLUMN `leader_name`;