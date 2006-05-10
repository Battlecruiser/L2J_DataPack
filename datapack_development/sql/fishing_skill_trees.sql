/*
MySQL Backup
Source Host:           reddragon.servegame.org
Source Server Version: 4.0.25-standard-log
Source Database:       l2jdb
Date:                  2006-04-26 21:15:38
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for fishing_skill_trees
#----------------------------
CREATE TABLE `fishing_skill_trees` (
  `skill_id` int(10) NOT NULL default '0',
  `level` int(10) NOT NULL default '0',
  `name` varchar(25) NOT NULL default '',
  `sp` int(10) NOT NULL default '0',
  `min_level` int(10) NOT NULL default '0',
  `costid` int(10) NOT NULL default '0',
  `cost` int(10) NOT NULL default '0',
  `isfordwarf` int(1) NOT NULL default '0',
  PRIMARY KEY  (`skill_id`,`level`)
) TYPE=MyISAM;
#----------------------------
# Records for table fishing_skill_trees
#----------------------------


