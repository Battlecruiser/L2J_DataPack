-- 
-- Table structure for table `custom_armorsets`
-- 

DROP TABLE IF EXISTS custom_armorsets;
CREATE TABLE custom_armorsets (
 id int(3) NOT NULL auto_increment,
 chest decimal(11,0) NOT NULL default '0',
 legs decimal(11,0) NOT NULL default '0',
 head decimal(11,0) NOT NULL default '0',
 gloves decimal(11,0) NOT NULL default '0',
 feet decimal(11,0) NOT NULL default '0',
 skill_id decimal(11,0) NOT NULL default '0',
 shield decimal(11,0) NOT NULL default '0',
 shield_skill_id decimal(11,0) NOT NULL default '0',
 enchant6skill decimal(11,0) NOT NULL default '0',
 PRIMARY KEY (id,chest)
) ENGINE=MyISAM;
