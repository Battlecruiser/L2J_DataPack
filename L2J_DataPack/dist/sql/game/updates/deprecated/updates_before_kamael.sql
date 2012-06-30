-- What this file should be useful for?
-- This file is a collection of old cummulative updates,
-- with old meaning: "between C3 and Interlude"
-- Most fresh setups won't ever need to run these queries,
-- and while most of them have been writen to be safe
-- they have been moved during the Kamael release 
-- to a new home directory: 'deprecated'
-- 
-- Why do we still keep these queries here?
-- Mostly for reference purposes, and perhaps to be used
-- by people that, years after hosting an L2J server
-- decided to use an ancient database, would like to
-- update it and keep their old users/items.
-- 
-- If you are such a person, please note that running
-- these queries will require some certain SQL skills
-- from you, and we can't provide you any support on
-- executing them or for any similar update process, 
-- whatsoever.

-- 050912-[1033].sql
ALTER TABLE `items` ADD KEY `key_item_id` (`item_id`);
ALTER TABLE `items` ADD `time_of_use` INT;
ALTER TABLE `items` ADD KEY `key_time_of_use` (`time_of_use`);

-- 051016-[1334].sql
ALTER TABLE `items` ADD COLUMN `custom_type1` INT DEFAULT 0;
ALTER TABLE `items` ADD COLUMN `custom_type2` INT DEFAULT 0;

-- 051103-[1438].sql
-- needed only if your charater tables doesn't contains clan_privs already
ALTER TABLE `characters` ADD `clan_privs` INT DEFAULT '0' NOT NULL ;

-- 051104-[1447].sql
-- needed only if your charater tables doesn't contains 'wantspeace' already
alter table `characters` add column `wantspeace` decimal(1,0) DEFAULT 0;

-- 051112-[1505].sql
-- needed only if your charater tables doesn't contains 'deletetime' already
alter table `characters` modify `deletetime` decimal(20,0) NOT NULL DEFAULT 0;

-- 051112-[1506].sql
-- needed only if your charater tables doesn't contains 'deleteclan' already
alter table `characters` add column `deleteclan` decimal(20,0) NOT NULL DEFAULT 0;

-- 051129-[1670].sql
-- needed only if your clan_data tables doesn't contains 'crest_id' and 'ally_crest_id' already
alter table `clan_data` add column `crest_id` INT DEFAULT 0;
alter table `clan_data` add column `ally_crest_id` INT DEFAULT 0;

-- 051205-[1768].sql
-- Needed only if your character tables are needed to be preserved.
ALTER TABLE `character_hennas` ADD `class_index` int(1) NOT NULL default '0', DROP PRIMARY KEY, ADD PRIMARY KEY (`char_obj_id`,`slot`,`class_index`);
ALTER TABLE `character_quests` ADD `class_index` int(1) NOT NULL default '0', DROP PRIMARY KEY, ADD PRIMARY KEY (`char_id`,`name`,`var`,`class_index`);
ALTER TABLE `character_shortcuts` CHANGE `unknown` `class_index` int(1) NOT NULL default '0', DROP PRIMARY KEY, ADD PRIMARY KEY (`char_obj_id`,`slot`,`page`,`class_index`);
ALTER TABLE `character_skills` ADD `class_index` int(1) NOT NULL default '0', DROP PRIMARY KEY, ADD PRIMARY KEY (`char_obj_id`,`skill_id`,`class_index`);
ALTER TABLE `character_skills_save` ADD `class_index` int(1) NOT NULL default '0', DROP PRIMARY KEY, ADD PRIMARY KEY (`char_obj_id`,`skill_id`,`class_index`);
ALTER TABLE `characters` ADD `base_class` int(2) NOT NULL default '0';

-- 051205-[1769].sql
-- UPDATE `characters` set `base_class` = `classid`;
-- see http://forum.l2jserver.com/thread.php?threadid=21983 for reason why commented out-- 051208-[1876].sql

-- 060215-[c4_1489].sql
ALTER TABLE `clan_data` ADD `crest_large_id` INT( 11 ) AFTER `crest_id` ;

-- 060215-[c4req_update].sql
ALTER TABLE `character_recipebook` ADD type INT NOT NULL DEFAULT 0;
UPDATE `character_recipebook` set type = 1;

-- 11012007_3477.sql
ALTER TABLE `clanhall` ADD paid INT( 1 ) NOT NULL DEFAULT '0';
UPDATE `clanhall` SET paid = 1 WHERE paidUntil >0; -- 20060305-[1575].sql
-- add column onlinetime
ALTER TABLE `characters` ADD `onlinetime` DECIMAL( 20, 0 ) DEFAULT '0' NOT NULL AFTER `online`;

-- 20060314-[1581].sql
ALTER TABLE `seven_signs`
CHANGE COLUMN `red_stones` `dawn_red_stones` INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN `green_stones` `dawn_green_stones` INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN `blue_stones` `dawn_blue_stones` INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN `ancient_adena_amount` `dawn_ancient_adena_amount` INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN `contribution_score` `dawn_contribution_score` INT(10) NOT NULL DEFAULT 0,
ADD COLUMN `dusk_red_stones` INT(10) NOT NULL DEFAULT 0,
ADD COLUMN `dusk_green_stones` INT(10) NOT NULL DEFAULT 0,
ADD COLUMN `dusk_blue_stones` INT(10) NOT NULL DEFAULT 0,
ADD COLUMN `dusk_ancient_adena_amount` INT(10) NOT NULL DEFAULT 0,
ADD COLUMN `dusk_contribution_score` INT(10) NOT NULL DEFAULT 0;
UPDATE `seven_signs` SET
`dusk_red_stones` = `dawn_red_stones`, `dawn_red_stones` = 0,
`dusk_green_stones` = `dawn_green_stones`, `dawn_green_stones` = 0,
`dusk_blue_stones` = `dawn_blue_stones`, `dawn_blue_stones` = 0,
`dusk_ancient_adena_amount` = `dawn_ancient_adena_amount`, `dawn_ancient_adena_amount` = 0,
`dusk_contribution_score` = `dawn_contribution_score`, `dawn_contribution_score` = 0
WHERE `cabal` = 'dusk';

-- 20060424b.sql
UPDATE `seven_signs` SET dawn_red_stones = dawn_red_stones + dusk_red_stones,
dawn_green_stones = dawn_green_stones + dusk_green_stones,
dawn_blue_stones = dawn_blue_stones + dusk_blue_stones,
dawn_ancient_adena_amount = dawn_ancient_adena_amount + dusk_ancient_adena_amount,
dawn_contribution_score = dawn_contribution_score + dusk_contribution_score;
ALTER TABLE `seven_signs`
CHANGE COLUMN dawn_red_stones red_stones INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN dawn_green_stones green_stones INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN dawn_blue_stones blue_stones INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN dawn_ancient_adena_amount ancient_adena_amount INT(10) NOT NULL DEFAULT 0,
CHANGE COLUMN dawn_contribution_score contribution_score INT(10) NOT NULL DEFAULT 0,
DROP COLUMN dusk_red_stones,
DROP COLUMN dusk_green_stones,
DROP COLUMN dusk_blue_stones,
DROP COLUMN dusk_ancient_adena_amount,
DROP COLUMN dusk_contribution_score;

-- 20060424.sql
-- add colum friend_id
ALTER TABLE `character_friends` ADD COLUMN friend_id INT(11) DEFAULT 0 NOT NULL AFTER char_id;
-- get the friend_id
UPDATE `character_friends` SET friend_id=(SELECT obj_Id FROM characters WHERE char_name=friend_name);

-- 20060527-[2012].sql
-- Alter `characters` table
ALTER TABLE `characters` ADD COLUMN in_jail decimal(1,0) DEFAULT 0;
ALTER TABLE `characters` ADD COLUMN jail_timer decimal(20,0) DEFAULT 0;
-- Insert data in table `zone`
INSERT INTO `zone` VALUES (1, 'Jail', 'GM Jail', -115600, -250700, -113500, -248200, 0, 0);

-- 20060712-[dp1896].sql
ALTER TABLE `global_tasks` CHANGE `last_activation` `last_activation` DECIMAL(20,0) NOT NULL DEFAULT 0;

-- 20060920-[dp2090].sql
ALTER TABLE `characters` ADD power_grade DECIMAL( 11, 0 );

-- 20060925-[dp2103].sql
ALTER TABLE `characters` CHANGE power_grade power_grade DECIMAL( 11, 0 ) NULL DEFAULT NULL;

-- 20061010-[dp2162].sql
ALTER TABLE `character_subclasses` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT '0' NOT NULL;
ALTER TABLE `characters` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT NULL;
ALTER TABLE `pets` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT NULL;

-- 20061015-[dp2197].sql
ALTER TABLE `characters` ADD nobless DECIMAL( 1, 0 ) DEFAULT '0' NOT NULL;

-- 20070501.sql
DELETE FROM `character_quests` WHERE var = 'awaitSealedMStone';
INSERT INTO `character_quests`
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitSealedMStone',1,0
FROM `character_quests`
WHERE name LIKE '374%' AND var='cond' AND value='1';
DELETE FROM `character_quests` WHERE var = 'awaitLight';
INSERT INTO `character_quests`
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitLight',1,0
FROM `character_quests`
WHERE name LIKE '374%' ;
DELETE FROM `character_quests` WHERE var = 'awaitTooth';
INSERT INTO `character_questsv
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitTooth',1,0
FROM `character_quests`
WHERE name LIKE '374%' ;

-- update05152006.sql
ALTER TABLE `character_subclasses` ADD COLUMN class_index INT(1) NOT NULL DEFAULT 0 AFTER level;

-- update06122007.sql
ALTER TABLE `items` ADD mana_left DECIMAL( 3, 0 ) NOT NULL DEFAULT -1;

-- update09122007.sql
ALTER TABLE `characters` ADD COLUMN death_penalty_level int(2) NOT NULL DEFAULT 0 AFTER clan_create_expiry_time;

-- update10152006.sql
ALTER TABLE `characters` ADD nobless DECIMAL( 1, 0 ) DEFAULT '0' NOT NULL AFTER power_grade;

-- update12092007.sql
DROP TABLE IF EXISTS
`zone_cuboid`,
`zone_cylinder`,
`zone_npoly`;

-- update17112007.sql
ALTER TABLE `character_skills_save` ADD buff_index int(2) NOT NULL default 0;

-- update20060522.sql
ALTER TABLE `seven_signs_status` MODIFY COLUMN dawn_stone_score DECIMAL(20,0) NOT NULL DEFAULT 0,
 MODIFY COLUMN dusk_stone_score DECIMAL(20,0) NOT NULL DEFAULT 0;
ALTER TABLE `seven_signs` MODIFY COLUMN ancient_adena_amount DECIMAL(20,0) NOT NULL DEFAULT 0,
 MODIFY COLUMN contribution_score DECIMAL(20,0) NOT NULL DEFAULT 0;

-- update20060607.sql
ALTER TABLE `characters` ADD COLUMN `isin7sdungeon` DECIMAL(1,0) NOT NULL DEFAULT 0 AFTER `deleteclan`;

-- update20061118.sql
ALTER TABLE `clan_data` ADD COLUMN reputation_score INT NOT NULL DEFAULT 0;

ALTER TABLE `characters` ADD COLUMN subpledge INT NOT NULL DEFAULT 0;

-- update20061124.sql
ALTER TABLE `characters` ADD COLUMN last_recom_date decimal(20,0) NOT NULL DEFAULT 0 AFTER subpledge;

-- update20061126.sql
ALTER TABLE `character_skills_save` ADD COLUMN reuse_delay INT(8) NOT NULL DEFAULT 0 AFTER effect_cur_time;
ALTER TABLE `character_skills_save` ADD COLUMN restore_type INT(1) NOT NULL DEFAULT 0 AFTER reuse_delay;

-- update20061206.sql
ALTER TABLE `characters` ADD COLUMN lvl_joined_academy int(1) NOT NULL DEFAULT 0 AFTER last_recom_date;
ALTER TABLE `characters` ADD COLUMN apprentice int(1) NOT NULL DEFAULT 0 AFTER lvl_joined_academy;
ALTER TABLE `characters` ADD COLUMN sponsor int(1) NOT NULL DEFAULT 0 AFTER apprentice;

-- update20061207.sql
ALTER TABLE `character_skills` CHANGE `skill_name` `skill_name` varchar(30);

-- update20061208.sql
ALTER TABLE `character_skills` CHANGE skill_name skill_name varchar(35);

-- update20061230.sql
-- *** DANGER *** - This update must DROP & CREATE the `clanhall` & `auction` tables due to structure changes
ALTER TABLE `clan_data` ADD `auction_bid_at` INT NOT NULL default '0';
ALTER TABLE `auction_bid` ADD `time_bid` decimal(20,0) NOT NULL default '0';
ALTER TABLE `auction_bid` ADD `clan_name` varchar(50) NOT NULL after `bidderName`;

-- setting zones
DELETE FROM `zone` WHERE type = 'Clan Hall';
ALTER TABLE `zone` ADD `z2` int(11) NOT NULL default '0' AFTER `z`;
INSERT INTO `zone` VALUES
 (22, 'Clan Hall', 'Gludio 1', -16400, 123275, -15551, 123850, -3117,0, 1),
 (23, 'Clan Hall', 'Gludio 2', -15100, 125350, -14800, 125800, -3143,0, 1),
 (24, 'Clan Hall', 'Gludio 3', -14050, 125050, -13700, 125700, -3143,0, 1),
 (25, 'Clan Hall', 'Gludio 4', -12950, 123900, -12300, 124250, -3117,0, 1),
 (26, 'Clan Hall', 'Gludin 1', -84700, 151550, -84250, 152350, -3130,0, 1),
 (26, 'Clan Hall', 'Gludin 1', -84350, 151950, -83800, 152350, -3130,0, 1),
 (27, 'Clan Hall', 'Gludin 2', -84400, 153050, -83950, 154050, -3166,0, 1),
 (27, 'Clan Hall', 'Gludin 2', -84200, 153050, -83550, 153600, -3166,0, 1),
 (28, 'Clan Hall', 'Gludin 3', -84500, 154900, -83950, 155700, -3158,0, 1),
 (28, 'Clan Hall', 'Gludin 3', -84100, 155300, -83500, 155700, -3158,0, 1),
 (29, 'Clan Hall', 'Gludin 4', -79700, 149400, -79250, 150300, -3061,0, 1),
 (29, 'Clan Hall', 'Gludin 4', -80100, 149400, -79500, 149850, -3061,0, 1),
 (30, 'Clan Hall', 'Gludin 5', -79700, 151350, -79300, 152250, -3036,0, 1),
 (30, 'Clan Hall', 'Gludin 5', -80100, 151800, -79500, 152250, -3036,0, 1),
 (31, 'Clan Hall', 'Dion 1', 17400, 144800, 18000, 145350, -3043,0, 1),
 (32, 'Clan Hall', 'Dion 2', 18850, 143600, 18600, 143100, -3017,0, 1),
 (33, 'Clan Hall', 'Dion 3', 19950, 146000, 20400, 146300, -3118,0, 1),
 (42, 'Clan Hall', 'Giran 1', 80780, 151063, 81156, 152111, -3518,0, 1),
 (43, 'Clan Hall', 'Giran 2', 82288, 152437, 81912, 151393, -3543,0, 1),
 (44, 'Clan Hall', 'Giran 3', 78077, 148285, 79119, 147911, -3608,0, 1),
 (45, 'Clan Hall', 'Giran 4', 83205, 144788, 83577, 145837, -3396,0, 1),
 (46, 'Clan Hall', 'Giran 5', 82244, 145860, 81870, 144814, -3517,0, 1),
 (36, 'Clan Hall', 'Aden 1', 143712, 27490, 144222, 26713, -2255,0, 1),
 (37, 'Clan Hall', 'Aden 2', 143720, 28607, 144262, 27789, -2247,0, 1),
 (38, 'Clan Hall', 'Aden 3', 151025, 26140, 150512, 26916, -2249,0, 1),
 (39, 'Clan Hall', 'Aden 4', 150396, 24062, 150940, 23243, -2120,0, 1),
 (40, 'Clan Hall', 'Aden 5', 149362, 22756, 148855, 23536, -2132,0, 1),
 (41, 'Clan Hall', 'Aden 6', 145999, 24932, 145455, 25753, -2121,0, 1),
 (47, 'Clan Hall', 'Goddard 1', 149717, -55824, 149063, -55350, -2783,0, 1),
 (48, 'Clan Hall', 'Goddard 2', 148479, -56473, 148479, -57275, -2773,0, 1),
 (49, 'Clan Hall', 'Goddard 3', 147238, -56636, 146564, -57078, -2783,0, 1),
 (50, 'Clan Hall', 'Goddard 4', 146399, -55682, 145652, -55386, -2773,0, 1),
 (35, 'Clan Hall', 'Bandits Stronghold', 80738, -15914, 79627, -15054, -1810,0, 1),
 (21, 'Clan Hall', 'Partisan Hideaway', 43151, 108377, 43648, 109399, -1981,0, 1),
 (62, 'Clan Hall', 'Hot Springs Guild House', 141414, -124508, 140590, -124706, -1896,0, 1);

-- C5 Clan Halls (these are not correct, but just to avoid NPEs)
INSERT INTO `zone` (id, type, name, x1, y1, x2, y2, z, taxById) VALUES
  (62, "Clan Hall", "Hot Springs Guild House", 141414, -124508, 140590, -124706, -1896, 1),
  (34, "Clan Hall", "Devastated Castle", 0, 0, 0, 0, 0, 0),
  (51, "Clan Hall", "Mont Chamber", 37437, -45872, 38024, -45460, 900, 8),
  (52, "Clan Hall", "Astaire Chamber", 38433, -46322, 39062, -45731, 900, 8),
  (53, "Clan Hall", "Aria Chamber", 39437, -47141, 39760, -46668, 900, 8),
  (54, "Clan Hall", "Yiana Chamber", 39426, -48619, 39820, -47871, 899, 8),
  (55, "Clan Hall", "Roien Chamber", 39173, -50020, 39774, -49340, 900, 8),
  (56, "Clan Hall", "Luna Chamber", 38401, -50516, 39054, -50404, 900, 8),
  (57, "Clan Hall", "Traban Chamber", 37461, -50973, 38006, -50589, 900, 8),
  (58, "Clan Hall", "Eisen Hall", 85426, -143448, 86069, -142769, -1342, 8),
  (59, "Clan Hall", "Heavy Metal Hall", 86162, -142094, 87003, -141727, -1340, 8),
  (60, "Clan Hall", "Molten Ore Hall", 88600, -142111, 87724, -141750, -1341, 8),
  (61, "Clan Hall", "Titan Hall", 88500, -143500, 89500, -142880, -1340, 8),
  (63, "Clan Hall", "Beast Farm", 0, 0, 0, 0, 0, 0),
  (64, "Clan Hall", "Fortress of the Dead", 0, 0, 0, 0, 0, 0);

-- C5 town and castle spawns
INSERT INTO `zone` (id, type, name, x1, y1, x2, y2, z, taxById) VALUES
  (17, "Town", "Schuttgart", 83881, -146500, 90908, -139486, 0, 9),
  (17, "Town Spawn", "Schuttgart", 87331, -142842, 0, 0, -1317, 0),
  (9, "Castle Area", "Schuttgart", 73000, -156600, 80740, -147592, 0, 8),
  (9, "Castle HQ", "Schuttgart", 77200, -153000, 77900, -478700, -545, 8),
  (9, "Castle Defender Spawn", "Schuttgart", 77524, -152709, 0, 0, -545, 0),
  (8, "Castle Defender Spawn", "Rune", 11388, -49160, 0, 0, -537, 0),
  (8, "Castle HQ", "Rune", 7000, -52500, 18493, -45900, -547, 0),
  (8, "Castle Area", "Rune", 7000, -55500, 27000, -41716, 0, 0),
  (8, "Siege Battlefield", "Rune", 7000, -55500, 27000, -41716, 0, 0),
  (9, "Siege Battlefield", "Schuttgart", 73000, -156600, 80740, -147592, 0, 0);

-- creating new tables and replacing old ones
DROP TABLE IF EXISTS `clanhall_functions`;
CREATE TABLE `clanhall_functions` (
  `hall_id` int(2) NOT NULL default '0',
  `type` int(1) NOT NULL default '0',
  `lvl` int(3) NOT NULL default '0',
  `lease` int(10) NOT NULL default '0',
  `rate` decimal(20,0) NOT NULL default '0',
  `endTime` decimal(20,0) NOT NULL default '0',
  `inDebt` int(1) NOT NULL default '0',
  PRIMARY KEY  (`hall_id`,`type`)
);

DROP TABLE IF EXISTS `clanhall`;
CREATE TABLE `clanhall` (
  `id` int(11) NOT NULL default '0',
  `name` varchar(40) NOT NULL default '',
  `ownerId` int(11) NOT NULL default '0',
  `lease` int(10) NOT NULL default '0',
  `desc` text NOT NULL,
  `location` varchar(15) NOT NULL default '',
  `paidUntil` decimal(20,0) NOT NULL default '0',
  `Grade` decimal(1,0) NOT NULL default '0',
  PRIMARY KEY  (`id`,`name`),
  KEY `id` (`id`)
);

INSERT INTO `clanhall` VALUES ('21', 'Fortress of Resistance', '0', '500000', 'Ol Mahum Fortress of Resistance', 'Dion', '0', '0');
INSERT INTO `clanhall` VALUES ('22', 'Moonstone Hall', '0', '500000', 'Clan hall located in the Town of Gludio', 'Gludio', '0', '2');
INSERT INTO `clanhall` VALUES ('23', 'Onyx Hall', '0', '500000', 'Clan hall located in the Town of Gludio', 'Gludio', '0', '2');
INSERT INTO `clanhall` VALUES ('24', 'Topaz Hall', '0', '500000', 'Clan hall located in the Town of Gludio', 'Gludio', '0', '2');
INSERT INTO `clanhall` VALUES ('25', 'Ruby Hall', '0', '500000', 'Clan hall located in the Town of Gludio', 'Gludio', '0', '2');
INSERT INTO `clanhall` VALUES ('26', 'Crystal Hall', '0', '500000', 'Clan hall located in Gludin Village', 'Gludin', '0', '2');
INSERT INTO `clanhall` VALUES ('27', 'Onyx Hall', '0', '500000', 'Clan hall located in Gludin Village', 'Gludin', '0', '2');
INSERT INTO `clanhall` VALUES ('28', 'Sapphire Hall', '0', '500000', 'Clan hall located in Gludin Village', 'Gludin', '0', '2');
INSERT INTO `clanhall` VALUES ('29', 'Moonstone Hall', '0', '500000', 'Clan hall located in Gludin Village', 'Gludin', '0', '2');
INSERT INTO `clanhall` VALUES ('30', 'Emerald Hall', '0', '500000', 'Clan hall located in Gludin Village', 'Gludin', '0', '2');
INSERT INTO `clanhall` VALUES ('31', 'The Atramental Barracks', '0', '500000', 'Clan hall located in the Town of Dion', 'Dion', '0', '1');
INSERT INTO `clanhall` VALUES ('32', 'The Scarlet Barracks', '0', '500000', 'Clan hall located in the Town of Dion', 'Dion', '0', '1');
INSERT INTO `clanhall` VALUES ('33', 'The Viridian Barracks', '0', '500000', 'Clan hall located in the Town of Dion', 'Dion', '0', '1');
INSERT INTO `clanhall` VALUES ('34', 'Devastated Castle', '0', '500000', 'Contestable Clan Hall', 'Aden', '0', '0');
INSERT INTO `clanhall` VALUES ('35', 'Bandit Stronghold', '0', '500000', 'Contestable Clan Hall', 'Oren', '0', '0');
INSERT INTO `clanhall` VALUES ('36', 'The Golden Chamber', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('37', 'The Silver Chamber', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('38', 'The Mithril Chamber', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('39', 'Silver Manor', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('40', 'Gold Manor', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('41', 'The Bronze Chamber', '0', '500000', 'Clan hall located in the Town of Aden', 'Aden', '0', '3');
INSERT INTO `clanhall` VALUES ('42', 'The Golden Chamber', '0', '500000', 'Clan hall located in the Town of Giran', 'Giran', '0', '3');
INSERT INTO `clanhall` VALUES ('43', 'The Silver Chamber', '0', '500000', 'Clan hall located in the Town of Giran', 'Giran', '0', '3');
INSERT INTO `clanhall` VALUES ('44', 'The Mithril Chamber', '0', '500000', 'Clan hall located in the Town of Giran', 'Giran', '0', '3');
INSERT INTO `clanhall` VALUES ('45', 'The Bronze Chamber', '0', '500000', 'Clan hall located in the Town of Giran', 'Giran', '0', '3');
INSERT INTO `clanhall` VALUES ('46', 'Silver Manor', '0', '500000', 'Clan hall located in the Town of Giran', 'Giran', '0', '3');
INSERT INTO `clanhall` VALUES ('47', 'Moonstone Hall', '0', '500000', 'Clan hall located in the Town of Goddard', 'Goddard', '0', '3');
INSERT INTO `clanhall` VALUES ('48', 'Onyx Hall', '0', '500000', 'Clan hall located in the Town of Goddard', 'Goddard', '0', '3');
INSERT INTO `clanhall` VALUES ('49', 'Emerald Hall', '0', '500000', 'Clan hall located in the Town of Goddard', 'Goddard', '0', '3');
INSERT INTO `clanhall` VALUES ('50', 'Sapphire Hall', '0', '500000', 'Clan hall located in the Town of Goddard', 'Goddard', '0', '3');
INSERT INTO `clanhall` VALUES ('51', 'Mont Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('52', 'Astaire Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('53', 'Aria Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('54', 'Yiana Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('55', 'Roien Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('56', 'Luna Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('57', 'Traban Chamber', '0', '500000', 'An upscale Clan hall located in the Rune Township', 'Rune', '0', '3');
INSERT INTO `clanhall` VALUES ('58', 'Eisen Hall', '0', '500000', 'Clan hall located in the Town of Schuttgart', 'Schuttgart', '0', '2');
INSERT INTO `clanhall` VALUES ('59', 'Heavy Metal Hall', '0', '500000', 'Clan hall located in the Town of Schuttgart', 'Schuttgart', '0', '2');
INSERT INTO `clanhall` VALUES ('60', 'Molten Ore Hall', '0', '500000', 'Clan hall located in the Town of Schuttgart', 'Schuttgart', '0', '2');
INSERT INTO `clanhall` VALUES ('61', 'Titan Hall', '0', '500000', 'Clan hall located in the Town of Schuttgart', 'Schuttgart', '0', '2');
INSERT INTO `clanhall` VALUES ('62', 'Rainbow Springs', '0', '500000', '', 'Goddard', '0', '0');
INSERT INTO `clanhall` VALUES ('63', 'Beast Farm', '0', '500000', '', 'Rune', '0', '0');
INSERT INTO `clanhall` VALUES ('64', 'Fortress of the Dead', '0', '500000', '', 'Rune', '0', '0');


DROP TABLE IF EXISTS `auction`;
CREATE TABLE `auction` (
  id int(11) NOT NULL default '0',
  sellerId int(11) NOT NULL default '0',
  sellerName varchar(50) NOT NULL default 'NPC',
  sellerClanName varchar(50) NOT NULL default '',
  itemType varchar(25) NOT NULL default '',
  itemId int(11) NOT NULL default '0',
  itemObjectId int(11) NOT NULL default '0',
  itemName varchar(40) NOT NULL default '',
  itemQuantity int(11) NOT NULL default '0',
  startingBid int(11) NOT NULL default '0',
  currentBid int(11) NOT NULL default '0',
  endDate decimal(20,0) NOT NULL default '0',
  PRIMARY KEY  (`itemType`,`itemId`,`itemObjectId`),
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `auction` VALUES
(22, 0, 'NPC', 'NPC Clan', 'ClanHall', 22, 0, 'Moonstone Hall', 1, 20000000, 0, 1164841200000),
(23, 0, 'NPC', 'NPC Clan', 'ClanHall', 23, 0, 'Onyx Hall', 1, 20000000, 0, 1164841200000),
(24, 0, 'NPC', 'NPC Clan', 'ClanHall', 24, 0, 'Topaz Hall', 1, 20000000, 0, 1164841200000),
(25, 0, 'NPC', 'NPC Clan', 'ClanHall', 25, 0, 'Ruby Hall', 1, 20000000, 0, 1164841200000),
(26, 0, 'NPC', 'NPC Clan', 'ClanHall', 26, 0, 'Crystal Hall', 1, 20000000, 0, 1164841200000),
(27, 0, 'NPC', 'NPC Clan', 'ClanHall', 27, 0, 'Onyx Hall', 1, 20000000, 0, 1164841200000),
(28, 0, 'NPC', 'NPC Clan', 'ClanHall', 28, 0, 'Sapphire Hall', 1, 20000000, 0, 1164841200000),
(29, 0, 'NPC', 'NPC Clan', 'ClanHall', 29, 0, 'Moonstone Hall', 1, 20000000, 0, 1164841200000),
(30, 0, 'NPC', 'NPC Clan', 'ClanHall', 30, 0, 'Emerald Hall', 1, 20000000, 0, 1164841200000),
(31, 0, 'NPC', 'NPC Clan', 'ClanHall', 31, 0, 'The Atramental Barracks', 1, 8000000, 0, 1164841200000),
(32, 0, 'NPC', 'NPC Clan', 'ClanHall', 32, 0, 'The Scarlet Barracks', 1, 8000000, 0, 1164841200000),
(33, 0, 'NPC', 'NPC Clan', 'ClanHall', 33, 0, 'The Viridian Barracks', 1, 8000000, 0, 1164841200000),
(36, 0, 'NPC', 'NPC Clan', 'ClanHall', 36, 0, 'The Golden Chamber', 1, 50000000, 0, 1164841200000),
(37, 0, 'NPC', 'NPC Clan', 'ClanHall', 37, 0, 'The Silver Chamber', 1, 50000000, 0, 1164841200000),
(38, 0, 'NPC', 'NPC Clan', 'ClanHall', 38, 0, 'The Mithril Chamber', 1, 50000000, 0, 1164841200000),
(39, 0, 'NPC', 'NPC Clan', 'ClanHall', 39, 0, 'Silver Manor', 1, 50000000, 0, 1164841200000),
(40, 0, 'NPC', 'NPC Clan', 'ClanHall', 40, 0, 'Gold Manor', 1, 50000000, 0, 1164841200000),
(41, 0, 'NPC', 'NPC Clan', 'ClanHall', 41, 0, 'The Bronze Chamber', 1, 50000000, 0, 1164841200000),
(42, 0, 'NPC', 'NPC Clan', 'ClanHall', 42, 0, 'The Golden Chamber', 1, 50000000, 0, 1164841200000),
(43, 0, 'NPC', 'NPC Clan', 'ClanHall', 43, 0, 'The Silver Chamber', 1, 50000000, 0, 1164841200000),
(44, 0, 'NPC', 'NPC Clan', 'ClanHall', 44, 0, 'The Mithril Chamber', 1, 50000000, 0, 1164841200000),
(45, 0, 'NPC', 'NPC Clan', 'ClanHall', 45, 0, 'The Bronze Chamber', 1, 50000000, 0, 1164841200000),
(46, 0, 'NPC', 'NPC Clan', 'ClanHall', 46, 0, 'Silver Manor', 1, 50000000, 0, 1164841200000),
(47, 0, 'NPC', 'NPC Clan', 'ClanHall', 47, 0, 'Moonstone Hall', 1, 50000000, 0, 1164841200000),
(48, 0, 'NPC', 'NPC Clan', 'ClanHall', 48, 0, 'Onyx Hall', 1, 50000000, 0, 1164841200000),
(49, 0, 'NPC', 'NPC Clan', 'ClanHall', 49, 0, 'Emerald Hall', 1, 50000000, 0, 1164841200000),
(50, 0, 'NPC', 'NPC Clan', 'ClanHall', 50, 0, 'Sapphire Hall', 1, 50000000, 0, 1164841200000),
(51, 0, 'NPC', 'NPC Clan', 'ClanHall', 51, 0, 'Mont Chamber', 1, 50000000, 0, 1164841200000),
(52, 0, 'NPC', 'NPC Clan', 'ClanHall', 52, 0, 'Astaire Chamber', 1, 50000000, 0, 1164841200000),
(53, 0, 'NPC', 'NPC Clan', 'ClanHall', 53, 0, 'Aria Chamber', 1, 50000000, 0, 1164841200000),
(54, 0, 'NPC', 'NPC Clan', 'ClanHall', 54, 0, 'Yiana Chamber', 1, 50000000, 0, 1164841200000),
(55, 0, 'NPC', 'NPC Clan', 'ClanHall', 55, 0, 'Roien Chamber', 1, 50000000, 0, 1164841200000),
(56, 0, 'NPC', 'NPC Clan', 'ClanHall', 56, 0, 'Luna Chamber', 1, 50000000, 0, 1164841200000),
(57, 0, 'NPC', 'NPC Clan', 'ClanHall', 57, 0, 'Traban Chamber', 1, 50000000, 0, 1164841200000),
(58, 0, 'NPC', 'NPC Clan', 'ClanHall', 58, 0, 'Eisen Hall', 1, 50000000, 0, 1164841200000),
(59, 0, 'NPC', 'NPC Clan', 'ClanHall', 59, 0, 'Heavy Metal Hall', 1, 50000000, 0, 1164841200000),
(60, 0, 'NPC', 'NPC Clan', 'ClanHall', 60, 0, 'Molten Ore Hall', 1, 50000000, 0, 1164841200000),
(61, 0, 'NPC', 'NPC Clan', 'ClanHall', 61, 0, 'Titan Hall', 1, 50000000, 0, 1164841200000);

-- update20070101.sql
ALTER TABLE `characters` ADD COLUMN varka_ketra_ally int(1) NOT NULL DEFAULT 0 AFTER sponsor;

-- update20070216.sql
ALTER TABLE `pets` DROP objId;
ALTER TABLE `pets` DROP maxHp;
ALTER TABLE `pets` DROP maxMp;
ALTER TABLE `pets` DROP acc;
ALTER TABLE `pets` DROP crit;
ALTER TABLE `pets` DROP evasion;
ALTER TABLE `pets` DROP mAtk;
ALTER TABLE `pets` DROP mDef;
ALTER TABLE `pets` DROP mSpd;
ALTER TABLE `pets` DROP pAtk;
ALTER TABLE `pets` DROP pDef;
ALTER TABLE `pets` DROP pSpd;
ALTER TABLE `pets` DROP str;
ALTER TABLE `pets` DROP con;
ALTER TABLE `pets` DROP dex;
ALTER TABLE `pets` DROP _int;
ALTER TABLE `pets` DROP men;
ALTER TABLE `pets` DROP wit;
ALTER TABLE `pets` DROP maxload;
ALTER TABLE `pets` DROP max_fed;

-- update20070221.sql
CREATE TABLE `repair_character_quests` (
	  `char_id` INT NOT NULL DEFAULT 0,
 	  `cond` VARCHAR(40) NOT NULL DEFAULT '',
 	  PRIMARY KEY  (`char_id`,`cond`)
 	  );

INSERT INTO `repair_character_quests` SELECT `char_id`,`value` FROM `character_quests` WHERE `name` = '336_CoinOfMagic' and `var`= 'cond';
UPDATE `character_quests`,repair_character_quests  SET
character_quests.`value` = 'Solo'
WHERE character_quests.`name` = '336_CoinOfMagic' and
character_quests.`var` = '<state>' and character_quests.`value` = 'Started' and
character_quests.`char_id` =  repair_character_quests.`char_id` AND  repair_character_quests.`cond` < 4;
UPDATE `character_quests`,repair_character_quests  SET
character_quests.`value` = 'Party' WHERE character_quests.`name` = '336_CoinOfMagic' and
character_quests.`var` = '<state>' and character_quests.`value` = 'Started' and
character_quests.`char_id` =  repair_character_quests.`char_id` AND  repair_character_quests.`cond` >= 4;
DROP TABLE `repair_character_quests`;

-- update20070223.sql
ALTER TABLE `raidboss_spawnlist` DROP respawn_delay;
ALTER TABLE `raidboss_spawnlist` ADD respawn_min_delay INT( 11 ) NOT NULL default '43200' AFTER heading; -- 12 (36-24) hours
ALTER TABLE `raidboss_spawnlist` ADD respawn_max_delay INT( 11 ) NOT NULL default '129600' AFTER respawn_min_delay; -- 36 hours
DELETE FROM `raidboss_spawnlist` WHERE boss_id IN (25328, 25339, 25342, 25346, 25349); -- remove Shadow of Halisha and Hellman spawns (possible exploits)-- update20070303.sql
ALTER TABLE `clan_data`
ADD `ally_penalty_expiry_time` DECIMAL( 20,0 ) NOT NULL DEFAULT '0',
ADD `ally_penalty_type` DECIMAL( 1 ) NOT NULL DEFAULT '0',
ADD `char_penalty_expiry_time` DECIMAL( 20,0 ) NOT NULL DEFAULT '0',
ADD `dissolving_expiry_time` DECIMAL( 20,0 ) NOT NULL DEFAULT '0';
ALTER TABLE `characters`
ADD `clan_join_expiry_time` DECIMAL( 20,0 ) NOT NULL DEFAULT '0',
ADD `clan_create_expiry_time` DECIMAL( 20,0 ) NOT NULL DEFAULT '0';
ALTER TABLE `characters`
DROP `allyId`;
ALTER TABLE `characters`
DROP `deleteclan`;

-- update20070511.sql
ALTER TABLE `characters`
ADD `expBeforeDeath` decimal(20,0) default 0 
AFTER `exp`;

-- update20070601.sql
ALTER TABLE `accounts`
ADD lastServer int(4) default '1'
AFTER lastIP;

-- update20070929-[dp3399].sql
ALTER TABLE `character_skills` CHANGE skill_name skill_name varchar(40);

-- update20071203.sql
UPDATE `clanhall` SET `lease`=100000;

-- update25052007.sql
ALTER TABLE `clanhall_functions`
DROP `inDebt`;
ALTER TABLE `clan_data`
DROP `hasHideout`;