CREATE TABLE IF NOT EXISTS `custom_merchant_shopids` (
  `shop_id` mediumint(7) unsigned NOT NULL DEFAULT '0',
  `npc_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
);