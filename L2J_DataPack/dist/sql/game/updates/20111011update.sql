RENAME TABLE `bandit_stronghold_attackers` TO `siegable_hall_flagwar_attackers`;
RENAME TABLE `bandit_stronghold_attackers_members` TO `siegable_hall_flagwar_attackers_members`;
ALTER TABLE `siegable_hall_flagwar_attackers` ADD COLUMN `hall_id` tinyint(2) unsigned NOT NULL DEFAULT '0' FIRST;
ALTER TABLE `siegable_hall_flagwar_attackers_members` ADD COLUMN `hall_id` tinyint(2) unsigned NOT NULL DEFAULT '0' FIRST;