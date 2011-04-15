CREATE TABLE IF NOT EXISTS `grandboss_data` (
  `boss_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `loc_x` mediumint(6) NOT NULL DEFAULT '0',
  `loc_y` mediumint(6) NOT NULL DEFAULT '0',
  `loc_z` mediumint(6) NOT NULL DEFAULT '0',
  `heading` mediumint(6) NOT NULL DEFAULT '0',
  `respawn_time` bigint(13) unsigned NOT NULL DEFAULT '0',
  `currentHP` decimal(30,15) NOT NULL,
  `currentMP` decimal(30,15) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`boss_id`)
);

INSERT IGNORE INTO `grandboss_data` VALUES 
(29001, -21610, 181594, -5734, 0, 0, 229898.48, 667.776, 0), -- Queen Ant (40)
(29006, 17726, 108915, -6480, 0, 0, 622493.58388, 3793.536, 0), -- Core (50)
(29014, 55024, 17368, -5412, 10126, 0, 622493.58388, 3793.536, 0), -- Orfen (50)
(29019, 185708, 114298, -8221, 32768, 0, 17850000, 39960, 0), -- Antharas (79)
(29020, 116033, 17447, 10104, 40188, 0, 4068372, 39960, 0), -- Baium (75)
(29022, 55312, 219168, -3223, 0, 0, 858518.36, 399600, 0), -- Zaken (60)
(29028, -105200, -253104, -15264, 0, 0, 223107426.1796109, 4497143.692870192, 0), -- Valakas (85)
(29066, 185708, 114298, -8221,32768, 0, 14518000, 3996000, 0), -- Antharas Weak (79)
(29067, 185708, 114298, -8221,32768, 0, 16184000, 3996000, 0), -- Antharas Normal (79)
(29068, 185708, 114298, -8221,32768, 0, 204677324.07859, 3996000, 0), -- Antharas Strong (85)
(29045, 0, 0, 0, 0, 0, 1018821.42723286, 52001.06567747795, 0); -- Frintezza (85)
-- (29046, 0, 0, 0, 0, 0, 1824900, 23310, 0), -- Scarlet Van Halisha (85)
-- (29047, 174238, -89792, -5002, 0, 0, 898044, 4519, 0), -- Scarlet Van Halisha (85)
-- (29099, 0, 0, 0, 0, 0, 1703893, 111000, 0), -- Baylor (83)
-- (29118, 0, 0, 0, 0, 0, 94800, 1110000, 0), -- Beleth (83)
-- (29150, 0, 0, 0, 0, 0, 8727677, 204995, 0), -- Ekimus (?)
-- (29151, 0, 0, 0, 0, 0, 6690, 204995, 0), -- Feral (?)
-- (29152, 0, 0, 0, 0, 0, 6690, 204995, 0), -- Feral (?)
-- (29163, 0, 0, 0, 0, 0, 8727677, 204995, 0), -- Tiat (87)