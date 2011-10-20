ALTER TABLE `character_offline_trade_items` MODIFY `charId` int(10) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `character_offline_trade_items` MODIFY `item` int(10) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `character_offline_trade_items` MODIFY `count` bigint(20) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `character_offline_trade_items` MODIFY `price` bigint(20) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `character_offline_trade_items` DROP PRIMARY KEY;