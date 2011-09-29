ALTER TABLE `character_summons`
MODIFY `ownerId` int(10) unsigned NOT NULL,
MODIFY `summonSkillId` int(10) unsigned NOT NULL,
MODIFY `curHp` int(9) unsigned DEFAULT '0',
MODIFY `curMp` int(9) unsigned DEFAULT '0',
MODIFY `time` int(10) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `pets`
MODIFY `item_obj_id` int(10) unsigned NOT NULL,
MODIFY `name` varchar(16),
MODIFY `level` smallint(2) unsigned NOT NULL,
MODIFY `curHp` int(9) unsigned DEFAULT '0',
MODIFY `curMp` int(9) unsigned DEFAULT '0',
MODIFY `exp` bigint(20) unsigned DEFAULT '0',
MODIFY `sp` int(10) unsigned DEFAULT '0',
MODIFY `fed` int(10) unsigned DEFAULT '0',
MODIFY `ownerId` int(10) NOT NULL DEFAULT '0',
MODIFY `restore` enum('true','false') NOT NULL DEFAULT 'false';