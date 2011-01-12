CREATE TABLE IF NOT EXISTS `character_offline_trade_items` (
  `charId` int(10) unsigned NOT NULL DEFAULT '0',
  `item` smallint(5) unsigned NOT NULL DEFAULT '0',
  `count` bigint(20) unsigned NOT NULL DEFAULT '0',
  `price` bigint(20) unsigned NOT NULL DEFAULT '0'
);