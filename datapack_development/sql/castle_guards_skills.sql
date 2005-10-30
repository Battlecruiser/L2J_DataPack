--
-- Table structure for `castle_guards_skills`
--

CREATE TABLE `castle_guards_skills` (
  `npcid` int(11) NOT NULL default '0',
  `skillid` int(11) NOT NULL default '0',
  `level` int(11) NOT NULL default '0',
  PRIMARY KEY  (`npcid`,`skillid`,`level`)
) TYPE=MyISAM;

