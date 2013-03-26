ALTER TABLE `grandboss_data` MODIFY `boss_id` smallint(5) unsigned NOT NULL;
ALTER TABLE `grandboss_data` MODIFY `loc_x` mediumint(6) NOT NULL;
ALTER TABLE `grandboss_data` MODIFY `loc_y` mediumint(6) NOT NULL;
ALTER TABLE `grandboss_data` MODIFY `loc_z` mediumint(6) NOT NULL;

ALTER TABLE `raidboss_spawnlist` MODIFY `boss_id` smallint(5) unsigned NOT NULL;
ALTER TABLE `raidboss_spawnlist` MODIFY `amount` tinyint(1) unsigned NOT NULL DEFAULT '1';
ALTER TABLE `raidboss_spawnlist` MODIFY `loc_x` mediumint(6) NOT NULL;
ALTER TABLE `raidboss_spawnlist` MODIFY `loc_y` mediumint(6) NOT NULL;
ALTER TABLE `raidboss_spawnlist` MODIFY `loc_z` mediumint(6) NOT NULL;
ALTER TABLE `raidboss_spawnlist` MODIFY `heading` mediumint(6) NOT NULL DEFAULT '0';
ALTER TABLE `raidboss_spawnlist` MODIFY `respawn_min_delay` mediumint(6) unsigned NOT NULL DEFAULT '43200';
ALTER TABLE `raidboss_spawnlist` MODIFY `respawn_max_delay` mediumint(6) unsigned NOT NULL DEFAULT '129600';