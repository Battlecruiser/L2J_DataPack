CREATE TABLE IF NOT EXISTS `auction` (
  `id` int(11) NOT NULL default '0',
  `sellerId` int(11) NOT NULL default '0',
  `sellerName` varchar(50) NOT NULL default 'NPC',
  `sellerClanName` varchar(50) NOT NULL default '',
  `itemType` varchar(25) NOT NULL default '',
  `itemId` int(11) NOT NULL default '0',
  `itemObjectId` int(11) NOT NULL default '0',
  `itemName` varchar(40) NOT NULL default '',
  `itemQuantity` BIGINT UNSIGNED NOT NULL default 0,
  `startingBid` BIGINT UNSIGNED NOT NULL default 0,
  `currentBid` BIGINT UNSIGNED NOT NULL default 0,
  `endDate` bigint(13) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`itemType`,`itemId`,`itemObjectId`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;