CREATE TABLE IF NOT EXISTS `grandboss_data` (
  `boss_id` smallint(5) unsigned NOT NULL,
  `loc_x` mediumint(6) NOT NULL,
  `loc_y` mediumint(6) NOT NULL,
  `loc_z` mediumint(6) NOT NULL,
  `heading` mediumint(6) NOT NULL DEFAULT '0',
  `respawn_time` bigint(13) unsigned NOT NULL DEFAULT '0',
  `currentHP` decimal(30,15) NOT NULL,
  `currentMP` decimal(30,15) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

REPLACE INTO `grandboss_data` (`boss_id`,`loc_x`,`loc_y`,`loc_z`,`heading`,`currentHP`,`currentMP`) VALUES
(29001, -21610, 181594, -5734, 0, 229898.48, 667.776), -- Queen Ant (40)
(29006, 17726, 108915, -6480, 0, 622493.58388, 3793.536), -- Core (50)
(29014, 55024, 17368, -5412, 10126, 622493.58388, 3793.536), -- Orfen (50)
(29019, 185708, 114298, -8221, 32768, 17850000, 39960), -- Antharas (79)
(29020, 116033, 17447, 10107, -25348, 4068372, 39960), -- Baium (75)
(29022, 55312, 219168, -3223, 0, 858518.36, 399600), -- Zaken (60)
(29028, -105200, -253104, -15264, 0, 223107426.1796109, 4497143.692870192), -- Valakas (85)
(29066, 185708, 114298, -8221,32768, 14518000, 3996000), -- Antharas Weak (79)
(29067, 185708, 114298, -8221,32768, 16184000, 3996000), -- Antharas Normal (79)
(29068, 185708, 114298, -8221,32768, 204677324.07859, 3996000), -- Antharas Strong (85)
(29118, 0, 0, 0, 0, 4109288, 1220547); -- Beleth (83)
-- (29045, -87780, -155086, -9080, 16384, 1018821.42723286, 52001.06567747795), -- Frintezza (85)
-- (29046, -87789, -153295, -9176, 16384, 1824900, 23310), -- Scarlet Van Halisha (85)
-- (29047, -87789, -153295, -9176, 16384, 898044, 4519), -- Scarlet Van Halisha (85)
-- (29099, 0, 0, 0, 0, 1703893, 111000), -- Baylor (83)
-- (29150, 0, 0, 0, 0, 8727677, 204995), -- Ekimus (?)
-- (29151, 0, 0, 0, 0, 6690, 204995), -- Feral (?)
-- (29152, 0, 0, 0, 0, 6690, 204995), -- Feral (?)
-- (29163, 0, 0, 0, 0, 8727677, 204995), -- Tiat (87)