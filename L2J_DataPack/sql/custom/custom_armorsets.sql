CREATE TABLE IF NOT EXISTS `custom_armorsets` (
 `id` SMALLINT UNSIGNED NOT NULL auto_increment,
 `chest` SMALLINT UNSIGNED NOT NULL default 0,
 `legs` SMALLINT UNSIGNED NOT NULL default 0,
 `head` SMALLINT UNSIGNED NOT NULL default 0,
 `gloves` SMALLINT UNSIGNED NOT NULL default 0,
 `feet` SMALLINT UNSIGNED NOT NULL default 0,
 `skill` varchar(70) NOT NULL DEFAULT '0-0;',
 `shield` SMALLINT UNSIGNED NOT NULL default 0,
 `shield_skill_id` SMALLINT UNSIGNED NOT NULL default 0,
 `enchant6skill` SMALLINT UNSIGNED NOT NULL default 0,
 `mw_legs` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_head` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_gloves` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_feet` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_shield` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 PRIMARY KEY (`id`,`chest`)
);