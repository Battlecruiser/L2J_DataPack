CREATE TABLE IF NOT EXISTS `custom_armorsets` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `chest` smallint(5) unsigned NOT NULL DEFAULT '0',
  `legs` smallint(5) unsigned NOT NULL DEFAULT '0',
  `head` smallint(5) unsigned NOT NULL DEFAULT '0',
  `gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
  `feet` smallint(5) unsigned NOT NULL DEFAULT '0',
  `skill` varchar(70) NOT NULL DEFAULT '0-0;',
  `shield` smallint(5) unsigned NOT NULL DEFAULT '0',
  `shield_skill_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `enchant6skill` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_legs` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_head` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_feet` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_shield` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`chest`)
);