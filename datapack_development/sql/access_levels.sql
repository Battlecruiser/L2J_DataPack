-- ---------------------------------
-- Table structure for access_levels
-- ---------------------------------
CREATE TABLE IF NOT EXISTS `access_levels` (
  `access_level` mediumint(9) NOT NULL,
  `name` varchar(255) NOT NULL default '',
  `name_color` varchar(6) NOT NULL default 'FFFFFF',
  `title_color` varchar(6) NOT NULL default 'FFFFFF',
  `child_access` varchar(255) NOT NULL default '',
  `is_gm` tinyint(1) NOT NULL default 0,
  `allow_peace_attack` tinyint(1) NOT NULL default 0,
  `allow_fixed_res` tinyint(1) NOT NULL default 0,
  `allow_transaction` tinyint(1) NOT NULL default 0,
  `allow_altg` tinyint(1) NOT NULL default 0,
  `give_damage` tinyint(1) NOT NULL default 0,
  `take_aggro` tinyint(1) NOT NULL default 0,
  `gain_exp` tinyint(1) NOT NULL default 0,
  PRIMARY KEY  (`access_level`)
) ;

INSERT IGNORE INTO `access_levels` VALUES 
(1,'Admin','0FF000','FFFFFF','2;3;4;5;6',1,1,1,1,1,1,1,1),
(2,'Head GM','0C0000','FFFFFF','5;6',0,0,1,1,1,1,1,1),
(3,'Event GM','00C000','FFFFFF','5;6',0,0,1,0,1,0,0,0),
(4,'Support GM','000C00','FFFFFF','5;6',0,0,1,0,1,0,0,0),
(5,'General GM','0000C0','FFFFFF','6',0,0,1,0,1,0,0,0),
(6,'Test GM','FFFFFF','FFFFFF','','0',0,1,0,1,0,0,0);

