CREATE TABLE IF NOT EXISTS `clan_introductions` (
  `serverId` int(8) NOT NULL,
  `clanId` int(8) NOT NULL,
  `introduction` text NOT NULL,
  PRIMARY KEY (`serverId`,`clanId`)
);
