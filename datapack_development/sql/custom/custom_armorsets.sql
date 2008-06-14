-- 
-- Table structure for table `custom_armorsets`
-- 

DROP TABLE IF EXISTS custom_armorsets;
CREATE TABLE custom_armorsets (
 id SMALLINT UNSIGNED NOT NULL auto_increment,
 chest SMALLINT UNSIGNED NOT NULL default 0,
 legs SMALLINT UNSIGNED NOT NULL default 0,
 head SMALLINT UNSIGNED NOT NULL default 0,
 gloves SMALLINT UNSIGNED NOT NULL default 0,
 feet SMALLINT UNSIGNED NOT NULL default 0,
 skill_id SMALLINT UNSIGNED NOT NULL default 0,
 skill_lvl TINYINT UNSIGNED NOT NULL default 0,
 shield SMALLINT UNSIGNED NOT NULL default 0,
 shield_skill_id SMALLINT UNSIGNED NOT NULL default 0,
 enchant6skill SMALLINT UNSIGNED NOT NULL default 0,
 PRIMARY KEY (id,chest)
) ENGINE=MyISAM;
