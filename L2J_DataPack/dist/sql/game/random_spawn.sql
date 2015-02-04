DROP TABLE IF EXISTS `random_spawn`;
CREATE TABLE `random_spawn` (
  `groupId` tinyint(3) unsigned NOT NULL,
  `npcId` smallint(5) unsigned NOT NULL,
  `count` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `initialDelay` int(8) NOT NULL DEFAULT '-1',
  `respawnDelay` int(8) NOT NULL DEFAULT '-1',
  `despawnDelay` int(8) NOT NULL DEFAULT '-1',
  `broadcastSpawn` enum('true','false') NOT NULL DEFAULT 'false',
  `randomSpawn` enum('true','false') NOT NULL DEFAULT 'true',
  PRIMARY KEY (`groupId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `random_spawn` VALUES
(136,32012,1,-1,3600000,0,'false','true'), -- Tantan (Aged ExAdventurer quest)
(137,32335,1,-1,120000,120000,'false','true'), -- Marksman (Guards on kamael island)
(138,32335,1,-1,120000,120000,'false','true'), -- Marksman (Guards on kamael island)
(139,32335,1,-1,120000,120000,'false','true'), -- Marksman (Guards on kamael island)
(140,32335,1,-1,120000,120000,'false','true'), -- Marksman (Guards on kamael island)
(141,32335,1,-1,120000,120000,'false','true'); -- Marksman (Guards on kamael island)