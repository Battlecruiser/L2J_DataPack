ALTER TABLE `armorsets` 
CHANGE id id SMALLINT UNSIGNED NOT NULL auto_increment,
CHANGE chest chest SMALLINT UNSIGNED NOT NULL default 0,
CHANGE legs legs SMALLINT UNSIGNED NOT NULL default 0,
CHANGE head head SMALLINT UNSIGNED NOT NULL default 0,
CHANGE gloves gloves SMALLINT UNSIGNED NOT NULL default 0,
CHANGE feet feet SMALLINT UNSIGNED NOT NULL default 0,
CHANGE skill_id skill_id SMALLINT UNSIGNED NOT NULL default 0,
CHANGE skill_lvl skill_lvl TINYINT UNSIGNED NOT NULL default 0,
CHANGE shield shield SMALLINT UNSIGNED NOT NULL default 0,
CHANGE shield_skill_id shield_skill_id SMALLINT UNSIGNED NOT NULL default 0,
CHANGE enchant6skill enchant6skill SMALLINT UNSIGNED NOT NULL default 0;

ALTER TABLE `custom_armorsets` 
CHANGE id id SMALLINT UNSIGNED NOT NULL auto_increment,
CHANGE chest chest SMALLINT UNSIGNED NOT NULL default 0,
CHANGE legs legs SMALLINT UNSIGNED NOT NULL default 0,
CHANGE head head SMALLINT UNSIGNED NOT NULL default 0,
CHANGE gloves gloves SMALLINT UNSIGNED NOT NULL default 0,
CHANGE feet feet SMALLINT UNSIGNED NOT NULL default 0,
CHANGE skill_id skill_id SMALLINT UNSIGNED NOT NULL default 0,
CHANGE skill_lvl skill_lvl TINYINT UNSIGNED NOT NULL default 0,
CHANGE shield shield SMALLINT UNSIGNED NOT NULL default 0,
CHANGE shield_skill_id shield_skill_id SMALLINT UNSIGNED NOT NULL default 0,
CHANGE enchant6skill enchant6skill SMALLINT UNSIGNED NOT NULL default 0;