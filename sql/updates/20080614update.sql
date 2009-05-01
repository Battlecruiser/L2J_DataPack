ALTER TABLE `custom_armorsets` ADD `skill_lvl` TINYINT UNSIGNED NOT NULL default 0 AFTER `skill_id` ;
ALTER TABLE `armorsets` ADD `skill_lvl` TINYINT UNSIGNED NOT NULL default 0 AFTER `skill_id` ;