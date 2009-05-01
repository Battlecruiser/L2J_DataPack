-- ---------------------------
-- Table structure for grandboss_data
-- ---------------------------
CREATE TABLE IF NOT EXISTS `grandboss_data` (
  `boss_id` INTEGER NOT NULL DEFAULT 0,
  `loc_x` INTEGER NOT NULL DEFAULT 0,
  `loc_y` INTEGER NOT NULL DEFAULT 0,
  `loc_z` INTEGER NOT NULL DEFAULT 0,
  `heading` INTEGER NOT NULL DEFAULT 0,
  `respawn_time` BIGINT NOT NULL DEFAULT 0,
  `currentHP` DECIMAL(8,0) DEFAULT NULL,
  `currentMP` DECIMAL(8,0) DEFAULT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`boss_id`)
);

INSERT IGNORE INTO `grandboss_data` VALUES 
(29001, -21610, 181594, -5734, 0, 0, 229898, 667, 0), -- Queen Ant (40)
(29006, 17726, 108915, -6480, 0, 0, 622493, 575, 0), -- Core (50)
(29014, 43728, 17220, -4342, 10126, 0, 622493, 1660, 0), -- Orfen (50)
(29019, 185708,114298,-8221,32768, 0, 14518000, 22197, 0), -- Antharas (79)
(29020, 115213,16623,10080,41740, 0, 4068372, 3347, 0), -- Baium (75)
(29022, 55312, 219168, -3223, 0, 0, 858518, 1975, 0), -- Zaken (60)
(29028, -105200,-253104,-15264,0, 0, 17850000, 22197, 0); -- Valakas (85)
-- (29045, 0, 0, 0, 0, 0, 791683, 1859, 0), -- Frintezza (85)
-- (29046, 0, 0, 0, 0, 0, 1832600, 44, 0), -- Scarlet Van Halisha (85)
-- (29047, 0, 0, 0, 0, 0, 2748900, 85, 0), -- Scarlet Van Halisha (85)
-- (29099, 0, 0, 0, 0, 0, 2566624, 9999, 0), -- Baylor (83) -- stats to be done
-- (29118, 0, 0, 0, 0, 0, 9999, 9999, 0), -- Beleth (?) -- stats to be done
-- (22215, 24767, -12441, -2532, 15314, 0, 340753, 2339, 0), -- Tyrannosaurus (80)
-- (22215, 28263, -17486, -2539, 50052, 0, 340753, 2339, 0), -- Tyrannosaurus (80)
-- (22215, 18229, -17975, -3219, 65140, 0, 340753, 2339, 0), -- Tyrannosaurus (80)
-- (22216, 19897, -9087, -2781, 2686, 0, 340753, 2339, 0), -- Tyrannosaurus (80)
-- (22217, 22827, -14698, -3080, 53946, 0, 340753, 2339, 0), -- Tyrannosaurus (80)