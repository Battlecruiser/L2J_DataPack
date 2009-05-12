DROP TABLE IF EXISTS `transform_skill_trees`;
CREATE TABLE `transform_skill_trees` (
  `race_id` int(10) NOT NULL default '0',
  `skill_id` int(10) NOT NULL default '0',
  `item_id` int(10) NOT NULL default '0',
  `level` int(10) NOT NULL default '0',
  `name` varchar(40) NOT NULL default '',
  `sp` int(10) NOT NULL default '0',
  `min_level` int(10) NOT NULL default '0',
  PRIMARY KEY (`race_id`,`skill_id`,`level`)
);

INSERT INTO `transform_skill_trees` VALUES

-- Human
(0, 541, 9650, 1, 'Transform Grail Apostle', 0, 60),
(0, 545, 9651, 1, 'Transform Unicorn', 0, 60),
(0, 548, 9652, 1, 'Transform Lilim Knight', 0, 60),
(0, 551, 9653, 1, 'Transform Golem Guardian', 0, 60),
(0, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(0, 558, 9655, 1, 'Transform Dragon Bomber', 0, 60), 

-- Elf
(1, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(1, 544, 9651, 1, 'Transform Unicorn', 0, 60), 
(1, 549, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(1, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(1, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(1, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),

-- Delf
(2, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(2, 546, 9651, 1, 'Transform Unicorn', 0, 60), 
(2, 547, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(2, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(2, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(2, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),

-- Dwarf
(4, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(4, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(4, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(4, 550, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(4, 555, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(4, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),

-- Orc
(3, 542, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(3, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(3, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(3, 552, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(3, 553, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(3, 557, 9655, 1, 'Transform Dragon Bomber', 0, 60),

-- Kamael
(5, 543, 9650, 1, 'Transform Grail Apostle', 0, 60), 
(5, 545, 9651, 1, 'Transform Unicorn', 0, 60), 
(5, 548, 9652, 1, 'Transform Lilim Knight', 0, 60), 
(5, 551, 9653, 1, 'Transform Golem Guardian', 0, 60), 
(5, 554, 9654, 1, 'Transform Inferno Drake', 0, 60), 
(5, 556, 9655, 1, 'Transform Dragon Bomber', 0, 60),

-- Race independent
(-1, 617, 9648, 1, 'Transform Onyx Beast', 0, 50),
(-1, 618, 9649, 1, 'Transform Death Blader', 0, 55),
(-1, 663, 10295, 1, 'Transfomr Zaken', 0, 60),
(-1, 664, 10296, 1, 'Transform Anakim', 0, 70),
(-1, 665, 10297, 1, 'Transform Benom', 0, 70),
(-1, 666, 10298, 1, 'Transform Gordon', 0, 76),
(-1, 667, 10299, 1, 'Transform Ranku', 0, 76),
(-1, 668, 10300, 1, 'Transform Kiyachi', 0, 76),
(-1, 669, 10301, 1, 'Transform Demon Prince', 0, 76),
(-1, 670, 10302, 1, 'Transform Heretic', 0, 70),
(-1, 671, 10303, 1, 'Transform Vale Master', 0, 70),
(-1, 672, 10304, 1, 'Transform Saber Tooth Tiger', 0, 70),
(-1, 673, 10305, 1, 'Transform Ol Mahum', 0, 70),
(-1, 674, 10306, 1, 'Transform Doll Blader', 0, 70);