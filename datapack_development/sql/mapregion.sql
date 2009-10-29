DROP TABLE IF EXISTS `mapregion`;
CREATE TABLE `mapregion` (
  `region` int(11) NOT NULL default '0',
  `sec0` int(2) NOT NULL default '0',
  `sec1` int(2) NOT NULL default '0',
  `sec2` int(2) NOT NULL default '0',
  `sec3` int(2) NOT NULL default '0',
  `sec4` int(2) NOT NULL default '0',
  `sec5` int(2) NOT NULL default '0',
  `sec6` int(2) NOT NULL default '0',
  `sec7` int(2) NOT NULL default '0',
  `sec8` int(2) NOT NULL default '0',
  `sec9` int(2) NOT NULL default '0',
  `sec10` int(2) NOT NULL default '0',
  `sec11` int(2) NOT NULL default '0',
  `sec12` int(2) NOT NULL default '0',
  `sec13` int(2) NOT NULL default '0',
  `sec14` int(2) NOT NULL default '0',
  `sec15` int(2) NOT NULL default '0',
  PRIMARY KEY (`region`)
);

-- 0 = "Talking Island Village"
-- 1 = "Elven Village"
-- 2 = "Dark Elven Village"
-- 3 = "Orc Village"
-- 4 = "Dwarven Village"
-- 5 = "Town of Gludio"
-- 6 = "Gludin Village"
-- 7 = "Town of Dion"
-- 8 = "Town of Giran"
-- 9 = "Town of Oren"
-- 10 = "Town of Aden"
-- 11 = "Hunters Village"
-- 12 = "Giran Harbor"
-- 13 = "Heine"
-- 14 = "Rune Township"
-- 15 = "Town of Goddard"
-- 16 = "Town of Shuttgart"
-- 17 = "Floran Village"
-- 18 = "Primeval Isle"
-- 19 = "Kamael Village"
-- 20 = "South of Wastelands Camp"
-- 21 = "Fantasy Island"
-- 22 = "Neutral Zone"
-- 23 = "Coliseum"
-- 24 = "GM Consultation service"
-- 25 = "Dimensional Gap"
-- 26 = "Cemetery of the Empire"
-- 27 = "inside the Steel Citadel"
-- 28 = "Steel Citadel Resistance"
-- 29 = "Inside Kamaloka"
-- 30 = "Inside Nia Kamaloka"
-- 31 = "Inside Rim Kamaloka"
-- 32 = "near the Keucereus clan association location"
-- 33 = "inside the Seed of Infinity"
-- 34 = "outside the Seed of Infinity"
-- 35 = "inside Aerial Cleft"
-- default = "Town of Aden"

INSERT INTO mapregion VALUES 
--	11	12	13	14	15	16	17	18	19	20	21	22	23	24	25	26
(0,	0,	0,	0,	0,	3,	3,	3,	3,	3,	3,	4,	4,	4,	4,	4,	4),	-- _8
(1,	0,	0,	0,	0,	3,	3,	3,	3,	3,	3,	4,	4,	4,	4,	4,	4),	-- _9
(2,	0,	0,	0,	0,	3,	3,	3,	7,	9,	11,	4,	4,	4,	4,	4,	4),	-- _10
(3,	0,	0,	0,	0,	3,	3,	29,	29,	29,	31,	4,	4,	4,	4,	4,	4),	-- _11
(4,	0,	0,	0,	0,	3,	3,	29,	29,	29,	29,	4,	4,	4,	4,	4,	4),	-- _12
(5,	0,	0,	0,	0,	3,	3,	3,	3,	3,	3,	16,	16,	16,	15,	15,	15),    -- _13
(6,	0,	0,	0,	0,	3,	3,	3,	3,	3,	3,	16,	16,	16,	15,	15,	15),    -- _14
(7,	0,	0,	0,	0,	3,	3,	21,	21,	3,	3,	14,	14,	15,	15,	15,	15),    -- _15
(8,	0,	0,	0,	0,	3,	3,	21,	21,	14,	14,	14,	14,	15,	15,	15,	15),    -- _16
(9,	0,	0,	0,	0,	19,	2,	2,	2,	2,	18,	14,	9,	9,	10,	10,	10),    -- _17
(10,    0,	0,	0,	0,	19,	19,	2,	2,	2,	2,	9,	9,	10,	10,	10,	10),    -- _18
(11,    0,	0,	0,	0,	19,	19,	19,	2,	2,	1,	1,	9,	11,	10,	10,	10),    -- _19
(12,    0,	0,	0,	0,	19,	19,	6,	2,	5,	1,	1,	9,	11,	11,	11,	11),    -- _20
(13,    0,	0,	0,	0,	19,	6,	6,	5,	5,	7,	7,	8,	8,	8,	8,	8),	-- _21
(14,    0,	0,	0,	0,	6,	6,	6,	6,	5,	7,	7,	8,	8,	8,	8,	8),	-- _22
(15,    32,	32,	32,	32,	0,	0,	6,	6,	5,	17,	12,	13,	13,	13,	13,	13),    -- _23
(16,    32,	32,	32,	32,	0,	0,	0,	6,	6,	12,	12,	13,	13,	13,	13,	13),    -- _24
(17,    32,	32,	32,	32,	0,	0,	0,	0,	0,	0,	0,	13,	13,	13,	13,	13);    -- _25