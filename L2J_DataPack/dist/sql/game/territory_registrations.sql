CREATE TABLE IF NOT EXISTS `territory_registrations` (
  `castleId` int(1) NOT NULL default '0',
  `registeredId` int(11) NOT NULL default '0',
  PRIMARY KEY (`castleId`,`registeredId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;