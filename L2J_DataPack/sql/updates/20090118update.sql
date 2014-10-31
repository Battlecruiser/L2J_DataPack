ALTER TABLE `characters` CHANGE `in_jail` `punish_level` TINYINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `characters` CHANGE `jail_timer` `punish_timer` INT UNSIGNED NOT NULL DEFAULT 0;
UPDATE `admin_command_access_rights` SET `adminCommand` = 'admin_ban_chat' WHERE `adminCommand` = 'admin_banchat';
UPDATE `admin_command_access_rights` SET `adminCommand` = 'admin_unban_chat' WHERE `adminCommand` = 'admin_unbanchat';