--
-- Table structure for table `class_list`
--
DROP TABLE IF EXISTS class_list;
CREATE TABLE `class_list` (
  `class_name` varchar(19) NOT NULL default '',
  `id` int(10) unsigned NOT NULL default '0',
  `parent_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

--
-- Dumping data for table `class_list`
--

INSERT INTO `class_list` VALUES ('H_Fighter',0,-1);
INSERT INTO `class_list` VALUES ('H_Warrior',1,0);
INSERT INTO `class_list` VALUES ('H_Gladiator',2,1);
INSERT INTO `class_list` VALUES ('H_Warlord',3,1);
INSERT INTO `class_list` VALUES ('H_Knight',4,0);
INSERT INTO `class_list` VALUES ('H_Paladin',5,4);
INSERT INTO `class_list` VALUES ('H_DarkAvenger',6,4);
INSERT INTO `class_list` VALUES ('H_Rogue',7,0);
INSERT INTO `class_list` VALUES ('H_TreasureHunter',8,7);
INSERT INTO `class_list` VALUES ('H_Hawkeye',9,7);
INSERT INTO `class_list` VALUES ('H_Mage',10,-1);
INSERT INTO `class_list` VALUES ('H_Wizard',11,10);
INSERT INTO `class_list` VALUES ('H_Sorceror',12,11);
INSERT INTO `class_list` VALUES ('H_Necromancer',13,11);
INSERT INTO `class_list` VALUES ('H_Warlock',14,11);
INSERT INTO `class_list` VALUES ('H_Cleric',15,10);
INSERT INTO `class_list` VALUES ('H_Bishop',16,15);
INSERT INTO `class_list` VALUES ('H_Prophet',17,15);
INSERT INTO `class_list` VALUES ('E_Fighter',18,-1);
INSERT INTO `class_list` VALUES ('E_Knight',19,18);
INSERT INTO `class_list` VALUES ('E_TempleKnight',20,19);
INSERT INTO `class_list` VALUES ('E_SwordSinger',21,19);
INSERT INTO `class_list` VALUES ('E_Scout',22,18);
INSERT INTO `class_list` VALUES ('E_PlainsWalker',23,22);
INSERT INTO `class_list` VALUES ('E_SilverRanger',24,22);
INSERT INTO `class_list` VALUES ('E_Mage',25,-1);
INSERT INTO `class_list` VALUES ('E_Wizard',26,25);
INSERT INTO `class_list` VALUES ('E_SpellSinger',27,26);
INSERT INTO `class_list` VALUES ('E_ElementalSummoner',28,26);
INSERT INTO `class_list` VALUES ('E_Oracle',29,25);
INSERT INTO `class_list` VALUES ('E_Elder',30,29);
INSERT INTO `class_list` VALUES ('DE_Fighter',31,-1);
INSERT INTO `class_list` VALUES ('DE_PaulusKnight',32,31);
INSERT INTO `class_list` VALUES ('DE_ShillienKnight',33,32);
INSERT INTO `class_list` VALUES ('DE_BladeDancer',34,32);
INSERT INTO `class_list` VALUES ('DE_Assassin',35,31);
INSERT INTO `class_list` VALUES ('DE_AbyssWalker',36,35);
INSERT INTO `class_list` VALUES ('DE_PhantomRanger',37,35);
INSERT INTO `class_list` VALUES ('DE_Mage',38,-1);
INSERT INTO `class_list` VALUES ('DE_DarkWizard',39,38);
INSERT INTO `class_list` VALUES ('DE_Spellhowler',40,39);
INSERT INTO `class_list` VALUES ('DE_PhantomSummoner',41,39);
INSERT INTO `class_list` VALUES ('DE_ShillienOracle',42,38);
INSERT INTO `class_list` VALUES ('DE_ShillienElder',43,42);
INSERT INTO `class_list` VALUES ('O_Fighter',44,-1);
INSERT INTO `class_list` VALUES ('O_Raider',45,44);
INSERT INTO `class_list` VALUES ('O_Destroyer',46,45);
INSERT INTO `class_list` VALUES ('O_Monk',47,44);
INSERT INTO `class_list` VALUES ('O_Tyrant',48,47);
INSERT INTO `class_list` VALUES ('O_Mage',49,-1);
INSERT INTO `class_list` VALUES ('O_Shaman',50,49);
INSERT INTO `class_list` VALUES ('O_Overlord',51,50);
INSERT INTO `class_list` VALUES ('O_Warcryer',52,50);
INSERT INTO `class_list` VALUES ('D_Fighter',53,-1);
INSERT INTO `class_list` VALUES ('D_Scavenger',54,53);
INSERT INTO `class_list` VALUES ('D_BountyHunter',55,54);
INSERT INTO `class_list` VALUES ('D_Artisan',56,53);
INSERT INTO `class_list` VALUES ('D_Warsmith',57,56);

