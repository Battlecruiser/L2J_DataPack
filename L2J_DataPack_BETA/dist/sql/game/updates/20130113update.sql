ALTER TABLE `raidboss_spawnlist` CHANGE `respawn_min_delay` `respawn_delay` mediumint(6) unsigned NOT NULL DEFAULT '129600';
ALTER TABLE `raidboss_spawnlist` CHANGE `respawn_max_delay` `respawn_random` mediumint(6) unsigned NOT NULL DEFAULT '86400';

UPDATE `raidboss_spawnlist` SET `respawn_delay` = 129600 WHERE `respawn_delay` = 43200;
UPDATE `raidboss_spawnlist` SET `respawn_random` = 86400 WHERE `respawn_random` = 129600;

UPDATE `raidboss_spawnlist` SET `respawn_delay` = 907200, `respawn_random` = 604800 WHERE `boss_id` = 25149; -- Zombie Lord Crowl (25)

UPDATE `raidboss_spawnlist` SET `respawn_delay` = 10800, `respawn_random` = 3600 WHERE `boss_id` = 25328; -- Eilhalder Von Hellmann (71) (Only Spawn at Night)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 21600, `respawn_random` = 21600 WHERE `boss_id` = 25680; -- Giant Marpanak (82)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 21600, `respawn_random` = 21600 WHERE `boss_id` = 25681; -- Gorgolos (82)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 21600, `respawn_random` = 21600 WHERE `boss_id` = 25684; -- Last Titan Utenus (83)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 86400, `respawn_random` = 21600 WHERE `boss_id` = 25696; -- Taklacan (85)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 86400, `respawn_random` = 21600 WHERE `boss_id` = 25697; -- Torumba (85)
UPDATE `raidboss_spawnlist` SET `respawn_delay` = 86400, `respawn_random` = 21600 WHERE `boss_id` = 25698; -- Dopagen (85)
UPDATE `raidboss_spawnlist` SET `respawn_random` = 0 WHERE `boss_id` = 29040; -- Wings of Flame Ixion (84)