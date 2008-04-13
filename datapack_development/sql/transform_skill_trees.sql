#----------------------------
# Table structure for transform_skill_trees
#----------------------------
DROP TABLE IF EXISTS transform_skill_trees;
CREATE TABLE `transform_skill_trees` (
  `race_id` int(10) NOT NULL default '0',
  `skill_id` int(10) NOT NULL default '0',
  `item_id` int(10) NOT NULL default '0',
  `level` int(10) NOT NULL default '0',
  `name` varchar(40) NOT NULL default '',
  `sp` int(10) NOT NULL default '0',
  `min_level` int(10) NOT NULL default '0',
  PRIMARY KEY  (`race_id`,`skill_id`,`level`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

insert  into transform_skill_trees values 
# Human
(0, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(0, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(0, 541, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(0, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(0, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(0, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(0, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(0, 558, 9655, 1, 'Transform Dragon Bomber', 0, 60), 
# Elf
(1, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(1, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(1, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(1, 544, 9651, 1, 'Transform Unicorn', 0, 60), 
(1, 549, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(1, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(1, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(1, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),
# Delf
(2, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(2, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(2, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(2, 546, 9651, 1, 'Transform Unicorn', 0, 60), 
(2, 547, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(2, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(2, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(2, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),
# Dwarf
(4, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(4, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(4, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(4, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(4, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(4, 550, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(4, 555, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(4, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),
# Orc
(3, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(3, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(3, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(3, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(3, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(3, 552, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(3, 553, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(3, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),
# Kamael
(5, 617, 9648, 1, 'Transform Onyx Beast', 0, 50), 
(5, 618, 9649, 1, 'Transform Death Blader', 0, 55), 
(5, 543, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(5, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(5, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(5, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(5, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(5, 556, 9655, 1, 'Transform Dragon Bomber', 0, 60);
