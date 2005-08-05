--
-- Table structure for table `teleport`
--

CREATE TABLE `teleport` (
  `Description` varchar(75) default NULL,
  `id` decimal(11,0) NOT NULL default '0',
  `loc_x` decimal(9,0) default NULL,
  `loc_y` decimal(9,0) default NULL,
  `loc_z` decimal(9,0) default NULL,
  `price` decimal(5,0) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

--
-- Dumping data for table `teleport`
--

INSERT INTO teleport VALUES ('Elven Town & Dark Elven Town -> Village of Gludin',1,-80826,149775,-3043,6400);
INSERT INTO teleport VALUES ('Elven Town & Dark Elven Town -> Town of Gludio',2,-12672,122776,-3116,3700);
INSERT INTO teleport VALUES ('Town of gludio -> Elven village',3,46934,51467,-2977,3700);
INSERT INTO teleport VALUES ('Town of gludio -> Dark Elven village',4,9745,15606,-4574,3700);
INSERT INTO teleport VALUES ('Town of gludio -> Village of Gludin',5,-80826,149775,-3043,2900);
INSERT INTO teleport VALUES ('Town of gludio -> Dion',6,15670,142983,-2705,4100);
INSERT INTO teleport VALUES ('Town of gludio & Village of Gludin -> Orc village',7,-44836,-112524,-235,6000);
INSERT INTO teleport VALUES ('Town of gludio & Village of Gludin -> Dwarven village',8,115113,-178212,-901,6000);
INSERT INTO teleport VALUES ('Village of Gludin -> TI',9,-84318,244579,-3730,18000);
INSERT INTO teleport VALUES ('Village of Gludin -> Elven Village',10,46934,51467,-2977,6400);
INSERT INTO teleport VALUES ('Village of Gludin -> Dark Elven Village',11,9745,15606,-4574,6400);
INSERT INTO teleport VALUES ('Village of Gludin -> Town of Gludio',12,-12672,122776,-3116,2900);
INSERT INTO teleport VALUES ('Village of Gludin -> Southern entrance of wastelands',13,-16730,209417,-3664,2400);
INSERT INTO teleport VALUES ('Dark Elven Town -> Southern part of dark elven forest',14,-61095,75104,-3356,1100);
INSERT INTO teleport VALUES ('TI -> Village of Gludin',15,-80826,149775,-3043,18000);
INSERT INTO teleport VALUES ('Dwarf Town & Orc Town -> Village of Gludin',16,-80826,149775,-3043,3000);
INSERT INTO teleport VALUES ('Dwarf Town -> The Northeast Coast',17,169008,-208272,-3504,700);
INSERT INTO teleport VALUES ('Dwarven Town -> Abandoned Coal Mines',418,155535,-173560,2495,290); 
INSERT INTO teleport VALUES ('Dwarven Town -> Mithril Mines',419,179039,-184080,-319,680);
INSERT INTO teleport VALUES ('Dion Town -> Town of Gludio',18,-12672,122776,-3116,4200);
INSERT INTO teleport VALUES ('Dion Town -> Town of Giran',19,83400,147943,-3404,8100);
INSERT INTO teleport VALUES ('Dion Town & Giran Town -> Entrance Giran',20,47942,186764,-3485,6500);
INSERT INTO teleport VALUES ('Cruma Tower Entrance -> Cruma Tower 1st floor',21,17724,114004,-11672,0);
INSERT INTO teleport VALUES ('Cruma Tower 1st floor -> Cruma Tower Entrance',22,17192,114178,-3439,0);
INSERT INTO teleport VALUES ('Cruma Tower 1st floor -> Cruma Tower 2nd floor',23,17730,108301,-9057,0);
INSERT INTO teleport VALUES ('Cruma Tower 2nd floor -> Cruma Tower 1st floor',24,17714,107923,-11850,0);
INSERT INTO teleport VALUES ('Town of Giran -> Dion Town',25,15670,142983,-2705,8100);
INSERT INTO teleport VALUES ('Town of Giran -> Oren Town',26,82956,53162,-1495,11000);
INSERT INTO teleport VALUES ('Town of Giran -> Hunter Village',27,116819,76994,-2714,9400);
INSERT INTO teleport VALUES ('Town of Giran -> Hardin''s Private Academy',28,105918,109759,-3207,5300);
INSERT INTO teleport VALUES ('TI Dungeon inside -> outside',29,-113329,235327,-3653,0);
INSERT INTO teleport VALUES ('TI Dungeon outside -> inside',30,48736,248463,-6162,0);
INSERT INTO teleport VALUES ('IvoryTower Basement',31,84915,15969,-4294,0);
INSERT INTO teleport VALUES ('IvoryTower Ground Floor',32,85399,16197,-3679,0);
INSERT INTO teleport VALUES ('IvoryTower 1st Floor',33,85399,16197,-2809,0);
INSERT INTO teleport VALUES ('IvoryTower 2nd Floor',34,85399,16197,-2293,0);
INSERT INTO teleport VALUES ('IvoryTower 3th Floor',35,85399,16197,-1776,0);
INSERT INTO teleport VALUES ('IvoryTower Ground Floor -> Oren Castle Town',36,82956,53162,-1495,4400);
INSERT INTO teleport VALUES ('IvoryTower Ground Floor -> Hunter''s Village',37,116819,76994,-2714,8200);
INSERT INTO teleport VALUES ('IvoryTower Ground Floor -> Aden Castle Town',38,146331,25762,-2018,12000);
INSERT INTO teleport VALUES ('Aden Town -> Ivory Tower',39,85339,16197,-3679,12000);
INSERT INTO teleport VALUES ('Aden Town -> Oren Town',40,82956,53162,-1495,13000);
INSERT INTO teleport VALUES ('Aden Town -> Hunter''s Village',41,116819,76994,-2714,11000);
INSERT INTO teleport VALUES ('Hunter''s Village -> Giran Town',42,83400,147943,-3404,9400);
INSERT INTO teleport VALUES ('Hunter''s Village -> Oren Town',43,82956,53162,-1495,4900);
INSERT INTO teleport VALUES ('Hunter''s Village -> Ivory Tower',44,85339,16197,-3679,8200);
INSERT INTO teleport VALUES ('Hunter''s Village -> Hardins Private Academy',45,105918,109759,-3207,4100);
INSERT INTO teleport VALUES ('Hunter''s Village -> Aden Town',46,146331,25762,-2018,11000);
INSERT INTO teleport VALUES ('Oren Town -> Giran Town',47,83400,147943,-3404,11000);
INSERT INTO teleport VALUES ('Oren Town -> Ivory Tower',48,85339,16197,-3679,4400);
INSERT INTO teleport VALUES ('Oren Town -> Hunter''s Village',49,116819,76994,-2714,4900);
INSERT INTO teleport VALUES ('Oren Town -> Hardins Private Academy',50,105918,109759,-3207,7300);
INSERT INTO teleport VALUES ('Oren Town -> Aden Town',51,146331,25762,-2018,13000);
INSERT INTO teleport VALUES ('Hardin''s Private Academy -> Giran Town',52,83400,147943,-3404,5300);
INSERT INTO teleport VALUES ('Hardin''s Private Academy -> Oren Town',53,82956,53162,-1495,7300);
INSERT INTO teleport VALUES ('Hardin''s Private Academy -> Hunter''s Village',54,116819,76994,-2714,4100);
INSERT INTO teleport VALUES ('Cruma level 2 -> Cruma level 3',55,17719,115590,-6584,0);
INSERT INTO teleport VALUES ('Cruma level 3 -> Cruma Core',56,17692,112284,-6250,0);
INSERT INTO teleport VALUES ('Cruma core -> Cruma level 3',57,17719,115590,-6584,0);
INSERT INTO teleport VALUES ('Cruma Tower 3rd floor -> Cruma Tower 2nd Floor',58,17731,119465,-9067,0);
INSERT INTO teleport VALUES ('Heine -> The Town of Giran',59,83400,147943,-3404,9200);
INSERT INTO teleport VALUES ('Heine -> Entrance to Giran',60,47942,186764,-3485,8500);
INSERT INTO teleport VALUES ('Lair end -> Antharas Nest',61,173826,115333,-7708,0);
INSERT INTO teleport VALUES ('Antharas Nest - > Giran castle town',62,83400,147943,-3404,0);
INSERT INTO teleport VALUES ('Giran Harbor -> Giran Town',63,83400,147943,-3404,6300);
INSERT INTO teleport VALUES ('Giran Harbor -> Dion Town',64,15670,142983,-2705,6500);
INSERT INTO teleport VALUES ('Heine -> The Town of Dion',65,15670,142983,-2705,9800);
INSERT INTO teleport VALUES ('Heine -> Field of Silence',66,82684,183551,-3597,2400);
INSERT INTO teleport VALUES ('Heine -> Field of Whispers',67,91186,217104,-3649,2400);
INSERT INTO teleport VALUES ('Heine -> Entrance to Alligator Islands',68,126450,174774,-3079,3500);
INSERT INTO teleport VALUES ('Giran -> Dragon Valley',69,122824,110836,-3720,6400);
INSERT INTO teleport VALUES ('Giran -> Heine',70,111409,219364,-3545,9200);
INSERT INTO teleport VALUES ('Giran -> Patriots Necropolis',71,-25472,77728,-3440,15500);
INSERT INTO teleport VALUES ('Giran -> Ascetics Necropolis',72,-55385,78667,-3012,18600);
INSERT INTO teleport VALUES ('Giran -> Saints Necropolis',73,79296,209584,-3704,9800);
INSERT INTO teleport VALUES ('Giran -> Catacomb of Dark Omens',74,-23165,13827,-3172,20400);
INSERT INTO teleport VALUES ('Monster Derby Track',75,12661,181687,-3560,0);
INSERT INTO teleport VALUES ('Aden -> Coliseum',76,146440,46723,-3432,4000);
INSERT INTO teleport VALUES ('Aden -> Patriots Necropolis',77,-25472,77728,-3440,35900);
INSERT INTO teleport VALUES ('Aden -> Ascetics Necropolis',78,-55385,78667,-3012,41900);
INSERT INTO teleport VALUES ('Aden -> Saints Necropolis',79,79296,209584,-3704,39100);
INSERT INTO teleport VALUES ('Aden -> Catacomb of Dark Omens',80,-23165,13827,-3172,33900);
INSERT INTO teleport VALUES ('Aden -> Blazing Swamp',81,159455,-12931,-2872,5700);
INSERT INTO teleport VALUES ('Aden -> The Forbidden Gateway',82,185319,20218,-3264,5400);
INSERT INTO teleport VALUES ('Aden -> The Front of Anghell Waterfall',83,163341,91374,-3320,9400);
INSERT INTO teleport VALUES ('Aden -> Forsaken Plains',84,167285,37109,-4008,3400);
INSERT INTO teleport VALUES ('Dion -> Heine', 85, 111409, 219364, -3545, 9800);
INSERT INTO teleport VALUES ('Dion -> Partisan Hideaway', 86, 46467, 126885, -3720, 1900);
INSERT INTO teleport VALUES ('Dion -> Bee Hive', 87, 20505, 189036, -3344, 2500);
INSERT INTO teleport VALUES ('Gludio -> Windawood Manor', 88, -23789, 169683, -3424, 1000);
INSERT INTO teleport VALUES ('Gludio -> Southern Pathway to the Wasteland', 89, -16730, 209417, -3664, 2400);
INSERT INTO teleport VALUES ('Gludin -> Abandoned Camp', 90, -46932, 140883, -2936, 900);
INSERT INTO teleport VALUES ('Gludin -> Fellmere Harvest Grounds', 91, -70387, 115501, -3472, 1000);
INSERT INTO teleport VALUES ('Gludin -> Langk Lizardman Dwelling', 92, -45210, 202654, -3592, 1700);
INSERT INTO teleport VALUES ('Orc Village -> Immortal Plateau, Central Region', 93, -8804, -114748, -3088, 510);
INSERT INTO teleport VALUES ('Orc Village -> Immortal Plateau, Southern Region', 94, -17870, -90980, -2528, 490);
INSERT INTO teleport VALUES ('Orc Village -> Immortal Plateau, Southeast Region', 95, 8209, -93524, -2312, 750);
INSERT INTO teleport VALUES ('Orc Village -> Frozen Waterfall', 96, 7603, -138871, -920, 760);
