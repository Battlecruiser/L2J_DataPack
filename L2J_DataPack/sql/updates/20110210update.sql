ALTER TABLE `character_offline_trade_items`
MODIFY `charId` int(10) unsigned NOT NULL;

ALTER TABLE `character_offline_trade`
MODIFY `charId` int(10) unsigned NOT NULL;

ALTER TABLE `character_reco_bonus`
MODIFY `rec_have` int(3) unsigned NOT NULL DEFAULT '0';