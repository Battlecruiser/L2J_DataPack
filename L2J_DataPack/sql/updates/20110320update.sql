ALTER TABLE `custom_npc`
MODIFY `hp` decimal(30,15) DEFAULT NULL,
MODIFY `mp` decimal(30,15) DEFAULT NULL,
MODIFY `hpreg` decimal(30,15) DEFAULT NULL,
MODIFY `mpreg` decimal(30,15) DEFAULT NULL,
MODIFY `patk` decimal(12,5) DEFAULT NULL,
MODIFY `pdef` decimal(12,5) DEFAULT NULL,
MODIFY `matk` decimal(12,5) DEFAULT NULL,
MODIFY `mdef` decimal(12,5) DEFAULT NULL,
MODIFY `walkspd` decimal(10,5) NOT NULL DEFAULT '60',
MODIFY `runspd` decimal(10,5) NOT NULL DEFAULT '120';

ALTER TABLE `grandboss_data`
MODIFY `boss_id` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `loc_x` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `loc_y` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `loc_z` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `heading` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `currentHP` decimal(30,15) NOT NULL,
MODIFY `currentMP` decimal(30,15) NOT NULL,
MODIFY `status` tinyint(1) unsigned NOT NULL DEFAULT '0';