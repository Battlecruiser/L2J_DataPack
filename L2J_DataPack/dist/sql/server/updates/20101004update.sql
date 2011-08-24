ALTER TABLE `custom_spawnlist`
MODIFY `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
MODIFY `location` varchar(40) NOT NULL DEFAULT '',
MODIFY `count` tinyint(1) unsigned NOT NULL DEFAULT '0',
MODIFY `npc_templateid` mediumint(7) unsigned NOT NULL DEFAULT '0',
MODIFY `locx` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `locy` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `locz` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `randomx` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `randomy` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `heading` mediumint(6) NOT NULL DEFAULT '0',
MODIFY `respawn_delay` mediumint(5) NOT NULL DEFAULT '0',
MODIFY `loc_id` int(9) NOT NULL DEFAULT '0',
MODIFY `periodOfDay` tinyint(1) unsigned NOT NULL DEFAULT '0';