ALTER TABLE `character_offline_trade_items`
MODIFY `charId` int(10) unsigned NOT NULL DEFAULT '0',
MODIFY `item` int(10) unsigned NOT NULL DEFAULT '0',
MODIFY `count` bigint(20) unsigned NOT NULL DEFAULT '0',
MODIFY `price` bigint(20) unsigned NOT NULL DEFAULT '0',
DROP PRIMARY KEY;