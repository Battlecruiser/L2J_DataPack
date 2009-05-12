DROP TABLE IF EXISTS `special_skill_trees`;
CREATE TABLE IF NOT EXISTS `special_skill_trees` (
  `skill_id` int(10) NOT NULL default '0',
  `level` int(10) NOT NULL default '0',
  `name` varchar(25) NOT NULL default '',
  `costid` int(10) NOT NULL default '0',
  `cost` int(10) NOT NULL default '0',
  PRIMARY KEY (`skill_id`,`level`)
);

INSERT INTO `special_skill_trees` VALUES
(932,1,'Star Stone',13728,1),
(932,2,'Star Stone',57,400000),
(932,3,'Star Stone',57,1200000);