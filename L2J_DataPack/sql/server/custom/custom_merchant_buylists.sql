CREATE TABLE IF NOT EXISTS `custom_merchant_buylists` (
  `item_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price` int(10) NOT NULL DEFAULT '0',
  `shop_id` mediumint(7) unsigned NOT NULL DEFAULT '0',
  `order` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `count` tinyint(2) NOT NULL DEFAULT '-1',
  `currentCount` tinyint(2) NOT NULL DEFAULT '-1',
  `time` int(11) NOT NULL DEFAULT '0',
  `savetimer` bigint(13) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`shop_id`,`order`)
);