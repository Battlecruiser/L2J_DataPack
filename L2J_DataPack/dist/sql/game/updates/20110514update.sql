ALTER TABLE `character_summons` MODIFY `ownerId` int(10) unsigned NOT NULL;
ALTER TABLE `character_summons` MODIFY `summonSkillId` int(10) unsigned NOT NULL;
ALTER TABLE `character_summons` MODIFY `curHp` int(9) unsigned DEFAULT '0';
ALTER TABLE `character_summons` MODIFY `curMp` int(9) unsigned DEFAULT '0';
ALTER TABLE `character_summons` MODIFY `time` int(10) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `pets` MODIFY `item_obj_id` int(10) unsigned NOT NULL;
ALTER TABLE `pets` MODIFY `name` varchar(16);
ALTER TABLE `pets` MODIFY `level` smallint(2) unsigned NOT NULL;
ALTER TABLE `pets` MODIFY `curHp` int(9) unsigned DEFAULT '0';
ALTER TABLE `pets` MODIFY `curMp` int(9) unsigned DEFAULT '0';
ALTER TABLE `pets` MODIFY `exp` bigint(20) unsigned DEFAULT '0';
ALTER TABLE `pets` MODIFY `sp` int(10) unsigned DEFAULT '0';
ALTER TABLE `pets` MODIFY `fed` int(10) unsigned DEFAULT '0';
ALTER TABLE `pets` MODIFY `ownerId` int(10) NOT NULL DEFAULT '0';
ALTER TABLE `pets` MODIFY `restore` enum('true','false') NOT NULL DEFAULT 'false';