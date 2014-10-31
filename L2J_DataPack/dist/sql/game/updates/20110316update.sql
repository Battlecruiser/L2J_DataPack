ALTER TABLE `auction` MODIFY `endDate` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `auction_bid` MODIFY `time_bid` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `castle` MODIFY `siegeDate` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `castle` MODIFY `regTimeEnd` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `castle_functions` MODIFY `endTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `character_instance_time` MODIFY `time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `character_offline_trade` MODIFY `time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `character_skills_save` MODIFY `systime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `character_reco_bonus` MODIFY `time_left` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `characters` MODIFY `deletetime` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `characters` MODIFY `lastAccess` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `characters` MODIFY `clan_join_expiry_time` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `characters` MODIFY `clan_create_expiry_time` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `characters` MODIFY `createTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `clan_data` MODIFY `ally_penalty_expiry_time` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `clan_data` MODIFY `char_penalty_expiry_time` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `clan_data` MODIFY `dissolving_expiry_time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `clanhall` MODIFY `paidUntil` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `clanhall_functions` MODIFY `endTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `cursed_weapons` MODIFY `endTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `fort` MODIFY `siegeDate` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `fort` MODIFY `lastOwnedTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `fort_functions` MODIFY `endTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `grandboss_data` MODIFY `respawn_time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `games` MODIFY `enddate` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `global_tasks` MODIFY `last_activation` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `heroes_diary` MODIFY `time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `item_auction` MODIFY `startingTime` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `item_auction` MODIFY `endingTime` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `itemsonground` MODIFY `drop_time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `messages` MODIFY `expiration` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `merchant_buylists` MODIFY `savetimer` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `olympiad_data` MODIFY `olympiad_end` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `olympiad_data` MODIFY `validation_end` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `olympiad_data` MODIFY `next_weekly_change` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `olympiad_fights` MODIFY `start` bigint(13) unsigned NOT NULL DEFAULT '0';
ALTER TABLE `olympiad_fights` MODIFY `time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `posts` MODIFY `post_date` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `raidboss_spawnlist` MODIFY `respawn_time` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `seven_signs_festival` MODIFY `date` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `seven_signs_status` MODIFY `date` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `topic` MODIFY `topic_date` bigint(13) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `custom_merchant_buylists` MODIFY `savetimer` bigint(13) unsigned NOT NULL DEFAULT '0';