DROP TABLE IF EXISTS `pledge_skill_trees`;
CREATE TABLE IF NOT EXISTS `pledge_skill_trees` (
  `skill_id` smallint(5) unsigned NOT NULL,
  `level` tinyint(1) unsigned NOT NULL,
  `name` varchar(24) NOT NULL,
  `clan_lvl` tinyint(2) unsigned NOT NULL,
  `repCost` smallint(5) unsigned NOT NULL,
  `itemId` smallint(5) unsigned NOT NULL,
  `itemCount` tinyint(2) unsigned NOT NULL
);

INSERT INTO `pledge_skill_trees` VALUES
-- Clan Level 5
(370,1,'Clan Body',5,1500,9816,10),
(373,1,'Clan Regeneration',5,1500,9816,10),
(379,1,'Clan Magic Resistance',5,1500,9818,10),
(391,1,'Clan Imperium',5,0,8176,1),
-- Clan Level 6
(371,1,'Clan Spirituality',6,2100,9817,10),
(374,1,'Clan Morale',6,2600,9817,10),
(376,1,'Clan Might',6,3000,9815,10),
(377,1,'Clan Shield',6,3000,9816,10),
(383,1,'Clan Shield Block',6,2100,9815,10),
-- Clan Level 7
(370,2,'Clan Body',7,6900,9816,10),
(371,2,'Clan Spirituality',7,5600,9817,10),
(373,2,'Clan Regeneration',7,6900,9816,10),
(376,2,'Clan Might',7,6500,9815,10),
(377,2,'Clan Shield',7,6500,9816,10),
(379,2,'Clan Magic Resistance',7,6900,9818,10),
(380,1,'Clan Guidance',7,5600,9814,10),
(382,1,'Clan Shield Defense',7,5100,9814,10),
(384,1,'Clan Cyclonic Resistance',7,5100,8176,1),
(385,1,'Clan Magmatic Resistance',7,5100,8176,1),
(386,1,'Clan Resist Shock',7,5100,8176,1),
(387,1,'Clan Resist Hold',7,5100,8176,1),
(388,1,'Clan Resist Sleep',7,5100,8176,1),
(390,1,'Clan Luck',7,6900,8175,1),
-- Clan Level 8
(371,3,'Clan Spirituality',8,12000,9817,10),
(372,1,'Clan Soul',8,12000,9818,10),
(374,2,'Clan Morale',8,13000,9817,10),
(375,1,'Clan Clarity',8,12000,9818,10),
(376,3,'Clan Might',8,13000,9815,10),
(377,3,'Clan Shield',8,13000,9816,10),
(378,1,'Clan Empower',8,12000,9818,10),
(380,2,'Clan Guidance',8,12000,9814,10),
(381,1,'Clan Agility',8,12000,9814,10),
(382,2,'Clan Shield Defense',8,12000,9814,10),
(383,2,'Clan Shield Block',8,12000,9815,10),
(384,2,'Clan Cyclonic Resistance',8,12000,8176,1),
(385,2,'Clan Magmatic Resistance',8,12000,8176,1),
(386,2,'Clan Resist Shock',8,12000,8176,1),
(387,2,'Clan Resist Hold',8,12000,8176,1),
(388,2,'Clan Resist Sleep',8,12000,8176,1),
(389,1,'Clan Wind Walk',8,11000,8175,1),
(390,2,'Clan Luck',8,14000,8175,1),
-- Clan Level 11
(370,3,'Clan Body',11,13200,9816,10),
(373,3,'Clan Regeneration',11,13200,9816,10),
(379,3,'Clan Magic Resistance',11,13200,9818,10);