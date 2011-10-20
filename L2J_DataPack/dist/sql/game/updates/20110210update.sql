ALTER TABLE `character_offline_trade_items` MODIFY `charId` int(10) unsigned NOT NULL;

ALTER TABLE `character_offline_trade` MODIFY `charId` int(10) unsigned NOT NULL;

UPDATE `character_reco_bonus` SET `rec_have`=255 WHERE `rec_have`>255;
UPDATE `character_reco_bonus` SET `rec_left`=255 WHERE `rec_left`>255;

ALTER TABLE `character_reco_bonus` MODIFY `rec_have` tinyint(3) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `character_reco_bonus` MODIFY `rec_left` tinyint(3) unsigned NOT NULL DEFAULT '0';
