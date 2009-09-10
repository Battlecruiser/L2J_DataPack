ALTER TABLE `custom_armorsets` DROP `skill_id`;
ALTER TABLE `custom_armorsets` DROP `skill_lvl`;
ALTER TABLE `custom_armorsets` ADD `skill` VARCHAR(70) NOT NULL DEFAULT '0-0;' AFTER `feet`;