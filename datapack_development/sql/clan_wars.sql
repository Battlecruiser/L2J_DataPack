/*
MySQL Backup
Source Host:           localhost
Source Server Version: 4.1.9-max
Source Database:       l2jdb
Date:                  2005/07/24 18:40:12
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for clan_wars
#----------------------------
CREATE TABLE `clan_wars` (
  `clan1` varchar(35) NOT NULL default '',
  `clan2` varchar(35) NOT NULL default '',
  `wantspeace1` decimal(1,0) NOT NULL default '0',
  `wantspeace2` decimal(1,0) NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
#----------------------------
# No records for table clan_wars
#----------------------------


