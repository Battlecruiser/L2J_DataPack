DROP TABLE IF EXISTS `skill_residential`;
CREATE TABLE `skill_residential` (
  `entityId` tinyint(3) unsigned NOT NULL,
  `skillId` smallint(5) unsigned NOT NULL DEFAULT '0',
  `skillLevel` tinyint(2) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`entityId`,`skillId`)
);

INSERT INTO `skill_residential` VALUES
-- Gludio castle
(1,593,1), -- Residence Health
(1,600,1), -- Residence Guidance
(1,606,1), -- Residence Fortitude
-- Dion castle
(2,591,1), -- Residence Spirit
(2,597,1), -- Residence Shield
(2,609,1), -- Residence Movement
-- Giran castle
(3,592,1), -- Residence Soul
(3,601,1), -- Residence Agility
(3,610,1), -- Residence Death Fortune
-- Oren castle
(4,590,1), -- Residence Body
(4,598,1), -- Residence Empower
(4,605,1), -- Residence Resist Lava
-- Aden castle
(5,596,1), -- Residence Might
(5,602,1), -- Residence Shield Block
(5,608,1), -- Residence Vigilance
-- Innadril castle
(6,595,1), -- Residence Clarity
(6,599,1), -- Residence Magic Barrier
(6,607,1), -- Residence Freedom
-- Goddard castle
(7,594,1), -- Residence Moral
(7,603,1), -- Residence Shield Defense
(7,590,1), -- Residence Body
-- Rune castle
(8,593,1), -- Residence Health
(8,599,1), -- Residence Magic Barrier
(8,604,1), -- Residence Resist Typhoon
-- Schuttgart castle
(9,592,1), -- Residence Soul
(9,600,1), -- Residence Guidance
(9,610,1), -- Residence Death Fortune

-- Territory Ward skills
(81,848,1), -- Gludio Territory Benefaction
(82,849,1), -- Dion Territory Benefaction
(83,850,1), -- Giran Territory Benefaction
(84,851,1), -- Oren Territory Benefaction
(85,852,1), -- Aden Territory Benefaction
(86,853,1), -- Innadril Territory Benefaction
(87,854,1), -- Goddard Territory Benefaction
(88,855,1), -- Rune Territory Benefaction
(89,856,1), -- Schuttgart Territory Benefaction

-- Shanty Fortress
(101,590,1), -- Residence Body
(101,603,1), -- Residence Shield Defense
-- Southern Fortress
(102,602,1), -- Residence Shield Block
(102,604,1), -- Residence Resist Typhoon
-- Hive Fortress
(103,601,1), -- Residence Agility
(103,605,1), -- Residence Resist Lava
-- Valley Fortress
(104,595,1), -- Residence Clarity
(104,606,1), -- Residence Fortitude
-- Ivory Fortress
(105,594,1), -- Residence Moral
(105,607,1), -- Residence Freedom
-- Narsell Fortress
(106,593,1), -- Residence Health
(106,608,1), -- Residence Vigilance
-- Bayou Fortress
(107,596,1), -- Residence Might
(107,598,1), -- Residence Empower
-- White Sands Fortress
(108,592,1), -- Residence Soul
(108,599,1), -- Residence Magic Barrier
-- Borderland Fortress
(109,591,1), -- Residence Spirit
(109,610,1), -- Residence Death Fortune
-- Marshland/Swamp Fortress
(110,597,1), -- Residence Shield
(110,600,1), -- Residence Guidance
-- Archaic Fortress
(111,590,1), -- Residence Body
(111,608,1), -- Residence Vigilance
-- Floran Fortress
(112,600,1), -- Residence Guidance
(112,607,1), -- Residence Freedom
-- Cloud Mountain Fortress
(113,610,1), -- Residence Death Fortune
(113,606,1), -- Residence Fortitude
-- Tanor Fortress
(114,609,1), -- Residence Movement
(114,605,1), -- Residence Resist Lava
-- Dragonspine Fortress
(115,599,1), -- Residence Magic Barrier
(115,604,1), -- Residence Resist Typhoon
-- Antharas Fortress
(116,598,1), -- Residence Empower
(116,603,1), -- Residence Shield Defense
-- Western Fortress
(117,597,1), -- Residence Shield
(117,602,1), -- Residence Shield Block
(117,610,1), -- Residence Death Fortune
-- Hunters Fortress
(118,596,1), -- Residence Might
(118,601,1), -- Residence Agility
-- Aaru Fortress
(119,592,1), -- Residence Soul
(119,595,1), -- Residence Clarity
-- Demon Fortress
(120,591,1), -- Residence Spirit
(120,594,1), -- Residence Moral
-- Monastic Fortress
(121,590,1), -- Residence Body
(121,593,1); -- Residence Health