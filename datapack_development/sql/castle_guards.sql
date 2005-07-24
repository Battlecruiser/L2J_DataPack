/*
MySQL Backup
Source Host:           localhost
Source Server Version: 4.1.9-max
Source Database:       l2jdb
Date:                  2005/07/24 18:38:14
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for castle_guards
#----------------------------
CREATE TABLE `castle_guards` (
  `object_id` decimal(11,0) NOT NULL default '0',
  `x` decimal(11,0) NOT NULL default '0',
  `y` decimal(11,0) NOT NULL default '0',
  `z` decimal(11,0) NOT NULL default '0',
  `clan_id` decimal(11,0) NOT NULL default '0',
  `hp` decimal(3,0) NOT NULL default '0',
  `mp` decimal(3,0) NOT NULL default '0',
  `patk` decimal(3,0) NOT NULL default '0',
  `pspd` decimal(3,0) NOT NULL default '0',
  `pdef` decimal(3,0) NOT NULL default '0',
  `matk` decimal(3,0) NOT NULL default '0',
  `mspd` decimal(3,0) NOT NULL default '0',
  `mdef` decimal(3,0) NOT NULL default '0',
  `magery` decimal(3,0) NOT NULL default '0',
  `name` varchar(35) NOT NULL default '',
  `hpcontrol` decimal(1,0) NOT NULL default '0',
  `mpcontrol` decimal(1,0) NOT NULL default '0',
  `patkcontrol` decimal(1,0) NOT NULL default '0',
  `pspdcontrol` decimal(1,0) NOT NULL default '0',
  `pdefcontrol` decimal(1,0) NOT NULL default '0',
  `matkcontrol` decimal(1,0) NOT NULL default '0',
  `mspdcontrol` decimal(1,0) NOT NULL default '0',
  `mdefcontrol` decimal(1,0) NOT NULL default '0',
  `magerycontrol` decimal(1,0) NOT NULL default '0',
  `template_id` decimal(11,0) NOT NULL default '0',
  `group` varchar(35) NOT NULL default '',
  PRIMARY KEY  (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
#----------------------------
# No records for table castle_guards
#----------------------------

#----------------------------
# Table structure for castle_guards_skills
#----------------------------
CREATE TABLE `castle_guards_skills` (
  `npcid` int(11) NOT NULL default '0',
  `skillid` int(11) NOT NULL default '0',
  `level` int(11) NOT NULL default '0',
  PRIMARY KEY  (`npcid`,`skillid`,`level`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
#----------------------------
# No records for table castle_guards_skills
#----------------------------


