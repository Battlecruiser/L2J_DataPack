ALTER TABLE `custom_armorsets`
MODIFY `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
MODIFY `chest` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `legs` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `head` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `feet` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `skill` varchar(70) NOT NULL DEFAULT '0-0;',
MODIFY `shield` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `shield_skill_id` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `enchant6skill` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `mw_legs` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `mw_head` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `mw_gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `mw_feet` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `mw_shield` smallint(5) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `custom_droplist`
MODIFY `mobId` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `itemId` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `min` int(8) unsigned NOT NULL DEFAULT '0',
MODIFY `max` int(8) unsigned NOT NULL DEFAULT '0',
MODIFY `category` smallint(3) NOT NULL DEFAULT '0',
MODIFY `chance` mediumint(7) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `custom_merchant_buylists`
MODIFY `item_id` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `price` int(10) NOT NULL DEFAULT '0',
MODIFY `shop_id` mediumint(7) unsigned NOT NULL DEFAULT '0',
MODIFY `order` tinyint(3) unsigned NOT NULL DEFAULT '0',
MODIFY `count` tinyint(2) NOT NULL DEFAULT '-1',
MODIFY `currentCount` tinyint(2) NOT NULL DEFAULT '-1',
MODIFY `time` int(11) NOT NULL DEFAULT '0',
MODIFY `savetimer` decimal(20,0) NOT NULL DEFAULT '0';

ALTER TABLE `custom_merchant_shopids`
MODIFY `shop_id` mediumint(7) unsigned NOT NULL DEFAULT '0',
MODIFY `npc_id` varchar(5) DEFAULT NULL;

ALTER TABLE `custom_npc`
MODIFY `id` mediumint(7) unsigned NOT NULL DEFAULT '0',
MODIFY `idTemplate` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `name` varchar(200) NOT NULL DEFAULT '',
MODIFY `serverSideName` tinyint(1) NOT NULL DEFAULT '1',
MODIFY `title` varchar(45) NOT NULL DEFAULT '',
MODIFY `serverSideTitle` tinyint(1) NOT NULL DEFAULT '1',
MODIFY `class` varchar(200) DEFAULT NULL,
MODIFY `collision_radius` decimal(6,2) DEFAULT NULL,
MODIFY `collision_height` decimal(6,2) DEFAULT NULL,
MODIFY `level` tinyint(2) DEFAULT NULL,
MODIFY `sex` varchar(6) DEFAULT NULL,
MODIFY `type` varchar(22) DEFAULT NULL,
MODIFY `attackrange` smallint(4) DEFAULT NULL,
MODIFY `hp` decimal(8,0) DEFAULT NULL,
MODIFY `mp` decimal(8,0) DEFAULT NULL,
MODIFY `hpreg` decimal(6,2) DEFAULT NULL,
MODIFY `mpreg` decimal(6,2) DEFAULT NULL,
MODIFY `str` tinyint(2) NOT NULL DEFAULT '40',
MODIFY `con` tinyint(2) NOT NULL DEFAULT '43',
MODIFY `dex` tinyint(2) NOT NULL DEFAULT '30',
MODIFY `int` tinyint(2) NOT NULL DEFAULT '21',
MODIFY `wit` tinyint(2) NOT NULL DEFAULT '20',
MODIFY `men` tinyint(2) NOT NULL DEFAULT '20',
MODIFY `exp` int(9) NOT NULL DEFAULT '0',
MODIFY `sp` int(9) NOT NULL DEFAULT '0',
MODIFY `patk` mediumint(6) DEFAULT NULL,
MODIFY `pdef` mediumint(6) DEFAULT NULL,
MODIFY `matk` mediumint(6) DEFAULT NULL,
MODIFY `mdef` mediumint(6) DEFAULT NULL,
MODIFY `atkspd` smallint(4) NOT NULL DEFAULT '230',
MODIFY `aggro` smallint(4) NOT NULL DEFAULT '0',
MODIFY `matkspd` smallint(4) NOT NULL DEFAULT '333',
MODIFY `rhand` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `lhand` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `armor` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `enchant` tinyint(1) NOT NULL DEFAULT '0',
MODIFY `walkspd` smallint(3) NOT NULL DEFAULT '60',
MODIFY `runspd` smallint(3) NOT NULL DEFAULT '120',
MODIFY `isUndead` tinyint(1) NOT NULL DEFAULT '0',
MODIFY `dropHerbGroup` tinyint(1) NOT NULL DEFAULT '0',
MODIFY `basestats` tinyint(1) NOT NULL DEFAULT '0';

ALTER TABLE `custom_npcskills`
MODIFY `npcid` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `skillid` smallint(5) unsigned NOT NULL DEFAULT '0',
MODIFY `level` tinyint(2) unsigned NOT NULL DEFAULT '0';

ALTER TABLE `custom_teleport`
MODIFY `Description` varchar(75) DEFAULT NULL,
MODIFY `id` mediumint(7) unsigned NOT NULL DEFAULT '0',
MODIFY `loc_x` mediumint(6) DEFAULT NULL,
MODIFY `loc_y` mediumint(6) DEFAULT NULL,
MODIFY `loc_z` mediumint(6) DEFAULT NULL,
MODIFY `price` int(10) unsigned DEFAULT NULL,
MODIFY `fornoble` tinyint(1) NOT NULL DEFAULT '0',
MODIFY `itemId` smallint(5) unsigned NOT NULL DEFAULT '57';