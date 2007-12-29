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
-- DROP TABLE IF EXISTS `random_spawn`;
-- CREATE TABLE `random_spawn` (
--   groupId INT NOT NULL default 0,
--   npcId INT NOT NULL default 0,
--   count INT NOT NULL default 0,
--   initialDelay BIGINT NOT NULL default -1,
--   respawnDelay BIGINT NOT NULL default -1,
--   despawnDelay BIGINT NOT NULL default -1,
--   broadcastSpawn VARCHAR(5) NOT NULL default 'false',
--   randomSpawn VARCHAR(5) NOT NULL default 'true',
--   PRIMARY KEY  (groupId)
-- );

-- INSERT INTO `random_spawn` VALUES (1, 7556, 1, -1, 1800000, 1800000, 'false', 'true');
-- 060215-[c4_1489].sql
ALTER TABLE `clan_data` ADD `crest_large_id` INT( 11 ) AFTER `crest_id` ;
-- 060215-[c4req_update].sql
ALTER TABLE character_recipebook ADD type INT NOT NULL DEFAULT 0;
UPDATE character_recipebook set type = 1;
-- 11012007_3477.sql
ALTER TABLE clanhall ADD paid INT( 1 ) NOT NULL DEFAULT '0';
UPDATE clanhall SET paid = 1 WHERE paidUntil >0; -- 20060305-[1575].sql
-- add column onlinetime
ALTER TABLE `characters` ADD `onlinetime` DECIMAL( 20, 0 ) DEFAULT '0' NOT NULL AFTER `online`; -- 20060314-[1581].sql
-- increase lhand column field size from decimal 3 to decimal 4
ALTER TABLE `npc` MODIFY `lhand` DECIMAL(4); -- 20060412.sql
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

ALTER TABLE `random_spawn_loc` ADD COLUMN `heading` INTEGER NOT NULL DEFAULT -1,
DROP PRIMARY KEY,
ADD PRIMARY KEY(`groupId`, `x`, `y`, `z`, `heading`);
-- 20060424b.sql
UPDATE seven_signs SET dawn_red_stones = dawn_red_stones + dusk_red_stones, 
dawn_green_stones = dawn_green_stones + dusk_green_stones, 
dawn_blue_stones = dawn_blue_stones + dusk_blue_stones, 
dawn_ancient_adena_amount = dawn_ancient_adena_amount + dusk_ancient_adena_amount, 
dawn_contribution_score = dawn_contribution_score + dusk_contribution_score;
 
ALTER TABLE seven_signs
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
ALTER TABLE character_friends ADD COLUMN friend_id INT(11) DEFAULT 0 NOT NULL AFTER char_id;

-- get the friend_id
UPDATE character_friends SET friend_id=(SELECT obj_Id FROM characters WHERE char_name=friend_name); -- 20060527-[2012].sql
-- 
-- Alter `characters` table
-- 
ALTER TABLE `characters` ADD COLUMN in_jail decimal(1,0) DEFAULT 0;
ALTER TABLE `characters` ADD COLUMN jail_timer decimal(20,0) DEFAULT 0;


-- 
-- Insert data in table `zone`
-- 

INSERT INTO `zone` VALUES (1, 'Jail', 'GM Jail', -115600, -250700, -113500, -248200, 0, 0);
-- 20060712-[2209].sql
ALTER TABLE `spawnlist` ADD `periodOfDay` decimal(2,0) default 0;
update spawnlist set periodOfDay = 0;

-- 0 = Default
-- 1 = Day mob
-- 2 = Night mob-- 20060712-[dp1888].sql
-- Forest of dead/Cursed Village Day/Night spawn implementation
-- npc
/*
UPDATE npc SET type = 'L2Monster' WHERE id IN (1596,1599);
UPDATE npc SET type = 'L2Minion' WHERE id IN (1597,1598,1600,1601);
UPDATE npc SET hp=0,mp=0,exp=0,sp=0 WHERE id = 12789;
UPDATE npc SET collision_height = 40.5 WHERE id IN (1578,1597,1598,1600,1601);
UPDATE npc SET collision_height = 28 WHERE id IN (1582,1586,1590,1591);
UPDATE npc SET collision_height = 32 WHERE id IN (1593,1594,1595,1587);
UPDATE npc SET collision_height = 26.00 WHERE id = 10328;
UPDATE npc SET collision_height =30,collision_radius = 15 WHERE id IN (1557,1558,1559,1560,1563,1564,1565,1566,1567,1572,1574,1575,1580,1581,1583,1584,1596,1599);
UPDATE npc SET collision_height = 26.00 WHERE id = 10328;
UPDATE npc SET aggro = 300 WHERE id IN (1548,1549,1550,1552,1559,1569,1570,1596,1599,1565,1563,1562,1571,1574,1573,1591,1586,1585,1589,1581);
UPDATE npc SET rhand=211,lhand=0 WHERE id IN (1557,1558,1559,1560,1563,1564,1565,1566,1567,1572,1574,1575,1580,1581,1583,1584,1596,1599);
UPDATE npc SET rhand = 6723,lhand = 0 WHERE id IN (1593,1594,1595);
UPDATE npc SET rhand = 234,lhand = 0 WHERE id IN (1582,1587);
UPDATE npc SET rhand = 946,lhand = 945 WHERE id IN (1549,1550,1551,1552,1579);
UPDATE npc SET rhand = 150,lhand = 103 WHERE id IN (1547,1548,1571); 

-- minions
DELETE FROM minions WHERE boss_id BETWEEN 1547 AND 1601;
INSERT INTO minions VALUES (1596,1597,2,2),(1599,1600,2,2);

-- spawnlist
DELETE FROM spawnlist WHERE npc_templateid BETWEEN 1547 AND 1601;
DELETE FROM spawnlist WHERE npc_templateid BETWEEN 8386 AND 8389;
DELETE FROM spawnlist WHERE npc_templateid BETWEEN 8646 AND 8660;
DELETE FROM spawnlist WHERE npc_templateid = 8522;
DELETE FROM spawnlist WHERE npc_templateid IN (8523,8531);
DELETE FROM spawnlist WHERE npc_templateid IN (8526,8533,8534,8535);
DELETE FROM spawnlist WHERE npc_templateid = 8530;
DELETE FROM spawnlist WHERE npc_templateid = 8532;
INSERT INTO spawnlist VALUES
(NULL,'forest_of_the_dead',1,8386,59618,-42774,-3000,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,8387,58790,-42646,-3000,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,8388,59626,-41684,-3000,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,8389,60161,-42086,-3000,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,8646,47356,-56905,-2296,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8647,46028,-36343,-1656,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8522,51899,-54771,-3160,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8523,51444,-54595,-3136,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8531,60140,-35916,-672 ,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8526,52008,-51307,-3096,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8533,47120,-35967,-1632,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8534,49977,-46955,-3392,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8530,56380,-47203,-2984,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,8532,47151,-36080,-1608,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1548,49688,-55415,-2671,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,49338,-56672,-2606,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,48569,-56478,-2682,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,48584,-57028,-2664,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,49129,-56102,-2663,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,50671,-56097,-2635,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,49831,-56091,-2631,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,47578,-56799,-2406,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,47503,-57703,-2398,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1548,48529,-57876,-2669,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,47606,-58036,-2411,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,47835,-57077,-2503,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,49167,-57514,-2650,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,49828,-57248,-2644,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,50149,-56582,-2595,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,50725,-56350,-2641,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,49518,-55697,-2658,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,46625,-57423,-2207,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,46967,-57949,-2300,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,48783,-57640,-2543,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,49483,-56769,-2594,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,49526,-56046,-2632,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,48861,-55321,-2633,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,48863,-56828,-2632,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,50024,-59443,-2697,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,50710,-59332,-2763,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,50706,-60594,-2746,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,50463,-61405,-2580,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,50194,-60964,-2624,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,53877,-59043,-3307,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,53378,-59545,-3184,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1549,52559,-59990,-2980,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54628,-54580,-3153,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54282,-54372,-3084,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54164,-54938,-3065,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54688,-55261,-3185,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54930,-55645,-3271,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54981,-54831,-3203,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54960,-54173,-3179,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,54588,-53995,-3117,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,52636,-51351,-3079,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,52380,-51661,-3066,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,51353,-51782,-3046,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,51076,-51509,-2971,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,50667,-51150,-2855,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,50791,-51965,-2925,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,51052,-52300,-2922,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,50973,-50770,-2808,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,50806,-50365,-2820,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,51297,-50445,-2888,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57795,-56499,-3354,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57951,-56935,-3326,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,58547,-56897,-3327,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57928,-57450,-3324,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,58210,-58051,-3338,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57435,-57513,-3323,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57050,-56817,-3373,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57345,-56206,-3314,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57852,-55924,-3344,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,58225,-56306,-3329,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57575,-52434,-3209,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57720,-52135,-3168,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57956,-51857,-3179,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57777,-51542,-3195,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,58123,-51235,-3158,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,58277,-51635,-3181,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57648,-51248,-3184,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57318,-51082,-3135,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,50203,-45475,-2942,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,49756,-45188,-2955,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,49350,-45241,-2925,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,49131,-44975,-2799,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,48900,-45391,-2769,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1582,47869,-44809,-2286,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1582,50888,-38089,-1749,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1582,50302,-38632,-1928,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1586,53788,-40379,-2147,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1586,55624,-40631,-2791,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1593,53087,-39549,-2373,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1590,53708,-39219,-2055,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1588,54056,-39844,-1719,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1593,57912,-45479,-2620,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1593,57634,-44762,-2586,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1593,56355,-45441,-3026,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1593,55852,-44786,-2944,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1590,58331,-45085,-2542,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1590,57485,-44686,-2548,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1590,56576,-45889,-2854,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1590,55156,-43707,-2902,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1587,55479,-44060,-2975,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1587,55289,-44657,-2956,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,56886,-45690,-2850,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57223,-45885,-2789,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57405,-45563,-2743,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57624,-45653,-2694,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57619,-45353,-2674,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57416,-46077,-2755,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,12789,57416,-46378,-2756,0,0,0,240,0,0),
(NULL,'forest_of_the_dead',1,1547,48536,-55831,-2607,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1547,48027,-56062,-2532,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49084,-56652,-2625,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,48665,-56346,-2672,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,48325,-57700,-2636,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,48127,-58500,-2669,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49286,-57626,-2692,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49833,-56757,-2586,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49876,-55365,-2683,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49209,-55832,-2669,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,50902,-61550,-2621,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,51537,-61249,-2796,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,51317,-60678,-2815,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50675,-59203,-2765,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,49698,-60886,-2677,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50177,-61547,-2553,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,51021,-61847,-2584,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,51414,-61701,-2663,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,51137,-58952,-2860,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,50183,-58715,-2766,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,49793,-59267,-2688,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,50362,-60677,-2680,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,51005,-61221,-2690,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,52440,-60323,-2936,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,53477,-60199,-3052,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,52946,-59606,-3097,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,52404,-59630,-2947,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,51849,-60206,-2883,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1553,53574,-59686,-3168,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,53130,-60575,-3069,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,53920,-59258,-3307,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,53109,-59165,-3210,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,54564,-58884,-3428,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,54695,-59700,-3262,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,54746,-56945,-3315,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,53826,-56627,-3356,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,53379,-55737,-3061,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,53616,-55692,-3075,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,53915,-56593,-3362,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,54662,-57008,-3306,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,54788,-56144,-3360,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,54860,-55067,-3197,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,54442,-54337,-3116,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,54048,-54670,-3029,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,54851,-56283,-3374,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,54175,-56646,-3387,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,54678,-56682,-3359,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,54530,-55364,-3173,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,53578,-54253,-3023,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,50477,-53112,-2832,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,51135,-52651,-2878,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,52439,-51751,-3044,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,51223,-50366,-2889,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,50661,-50988,-2818,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,50888,-51439,-2927,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,50591,-52055,-2906,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,51408,-51807,-3062,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,52802,-51307,-3091,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1563,52133,-50948,-3104,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,51388,-52773,-2854,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,51616,-53310,-2813,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,53966,-51786,-3040,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,53162,-51515,-3079,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,50735,-49675,-2891,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50651,-50371,-2821,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,51158,-50845,-2781,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50239,-52165,-2888,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50709,-52928,-2849,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,50942,-49222,-2947,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,57651,-57366,-3321,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,57479,-56340,-3352,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,58591,-56157,-3327,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,57921,-55653,-3332,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1565,58586,-55192,-3238,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,58541,-54553,-3199,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,57455,-55249,-3342,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,57795,-56317,-3356,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,58137,-57629,-3334,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,58732,-56918,-3315,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,58138,-56992,-3324,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,58320,-55917,-3328,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,58197,-55189,-3326,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,59375,-53820,-3234,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,59583,-52914,-3089,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,59599,-52087,-3114,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,60271,-52020,-2989,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,60098,-51343,-2908,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,58877,-54473,-3201,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,59859,-54113,-3224,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,59932,-53555,-3130,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,59558,-53238,-3129,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1557,59382,-53801,-3230,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,59230,-52339,-3216,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,60343,-52399,-3030,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,60335,-51304,-2905,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,59889,-50734,-2842,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1559,60008,-53840,-3180,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,57275,-51383,-3163,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,57360,-52552,-3246,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1596,56573,-52246,-3190,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,56705,-52962,-3249,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,58278,-53041,-3321,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,57959,-52212,-3188,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,58133,-51647,-3177,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,57724,-51131,-3162,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1561,56908,-51662,-3114,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,56911,-51997,-3108,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,56347,-52636,-3253,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,56326,-53644,-3371,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,57634,-52728,-3251,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,50543,-47113,-3400,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,50594,-45885,-3173,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,49583,-45329,-2974,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,48871,-41838,-2168,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,49500,-41375,-2258,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,50042,-40808,-2241,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,49128,-40288,-2233,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,48418,-45335,-2632,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,48096,-43849,-2518,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,47788,-44267,-2391,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,48165,-44671,-2370,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,48260,-43792,-2556,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,48852,-42343,-2244,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,49239,-41885,-2157,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,48679,-41409,-2210,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,51288,-39548,-2040,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,51139,-38515,-1869,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,49853,-39734,-2218,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,50690,-40715,-2247,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1567,49579,-40567,-2250,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,47922,-42001,-1975,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,47676,-42661,-1852,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,46839,-42134,-1830,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,47068,-41622,-1771,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,46687,-40857,-1973,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,46832,-39995,-2015,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1593,46819,-39146,-1833,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1593,48104,-39472,-1735,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1593,47554,-38683,-1746,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1593,47097,-37901,-1749,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,47194,-37474,-1738,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,47988,-38781,-1731,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,46341,-39761,-1990,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,46281,-37205,-1678,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,46591,-36695,-1645,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,48353,-36680,-1338,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,49108,-37431,-1696,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,49953,-36330,-1641,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,49937,-36622,-1662,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,48064,-35987,-1521,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,46220,-36387,-1657,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,45907,-37365,-1657,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,46953,-38272,-1652,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,47638,-39222,-1752,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1587,46406,-40009,-2014,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1587,47566,-36619,-1568,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1587,48921,-36541,-1428,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1590,49681,-35721,-1503,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,50513,-36225,-1684,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,48763,-35805,-1514,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,49860,-35989,-1573,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,49282,-36549,-1493,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,47292,-36405,-1583,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,47306,-37312,-1742,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,47668,-38458,-1710,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,47900,-39171,-1747,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,46559,-39483,-1938,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,46665,-40387,-2061,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,47337,-37731,-1783,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,46794,-36869,-1639,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,48262,-36194,-1467,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,48786,-37466,-1681,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,50311,-36408,-1703,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,59644,-47486,-2714,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,58796,-49470,-2857,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,58534,-48549,-2682,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,60537,-48137,-2758,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,60343,-48346,-2743,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,60498,-47581,-2732,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,60383,-46579,-2426,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,60543,-45606,-2523,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,61564,-45859,-2537,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,59828,-46270,-2557,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,60371,-47156,-2695,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,59604,-46657,-2583,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,60388,-46022,-2545,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1578,58721,-48937,-2808,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,59338,-47119,-2553,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,60084,-46497,-2521,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,60362,-45743,-2532,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,61124,-45712,-2563,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,61586,-46424,-2561,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,61044,-46919,-2646,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,60271,-47841,-2738,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,59560,-48114,-2721,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,59270,-47594,-2621,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,60078,-46796,-2500,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,55748,-49097,-3082,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,55648,-49845,-3110,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1572,55092,-49068,-3105,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,54226,-48559,-3323,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,53752,-49038,-3265,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,55168,-49364,-3085,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,55557,-48001,-3171,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,56254,-48938,-3036,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1570,57232,-47528,-2827,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,57732,-48609,-2848,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,57451,-49456,-3020,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,55995,-49507,-3121,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,54899,-49706,-2955,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,12789,54614,-48491,-3280,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,54520,-48223,-3335,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,53906,-48190,-3346,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,54608,-49142,-3206,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,57471,-45126,-2615,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,57483,-45984,-2739,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,56851,-45610,-2866,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1574,56018,-45147,-2961,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,56138,-45668,-3030,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,56691,-45861,-2848,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1583,57385,-45629,-2754,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1580,55384,-45226,-2874,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,55112,-44053,-2931,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,55544,-44500,-2922,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1599,57349,-46255,-2773,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,55602,-45389,-2894,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1581,56641,-45690,-2905,0,0,0,240,0,1),
(NULL,'forest_of_the_dead',1,1555,48536,-55831,-2607,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,48027,-56062,-2532,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49084,-56652,-2625,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,48665,-56346,-2672,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,48325,-57700,-2636,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,48127,-58500,-2669,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49286,-57626,-2692,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49833,-56757,-2586,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49876,-55365,-2683,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49209,-55832,-2669,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,50902,-61550,-2621,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,51537,-61249,-2796,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,51317,-60678,-2815,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,50675,-59203,-2765,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49698,-60886,-2677,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,50177,-61547,-2553,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,51021,-61847,-2584,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,51414,-61701,-2663,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,51137,-58952,-2860,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,50183,-58715,-2766,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,49793,-59267,-2688,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,50362,-60677,-2680,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,51005,-61221,-2690,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,52440,-60323,-2936,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,53477,-60199,-3052,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,52946,-59606,-3097,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,52404,-59630,-2947,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,51849,-60206,-2883,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,53574,-59686,-3168,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,53130,-60575,-3069,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,53920,-59258,-3307,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,53109,-59165,-3210,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,54564,-58884,-3428,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1555,54695,-59700,-3262,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,54746,-56945,-3315,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,53826,-56627,-3356,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,53379,-55737,-3061,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,53616,-55692,-3075,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,53915,-56593,-3362,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,54662,-57008,-3306,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,54788,-56144,-3360,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,54860,-55067,-3197,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,54442,-54337,-3116,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,54048,-54670,-3029,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,54851,-56283,-3374,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,54175,-56646,-3387,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,54678,-56682,-3359,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,54530,-55364,-3173,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,53578,-54253,-3023,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,50477,-53112,-2832,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,51135,-52651,-2878,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,52439,-51751,-3044,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,51223,-50366,-2889,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,50661,-50988,-2818,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,50888,-51439,-2927,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,50591,-52055,-2906,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,51408,-51807,-3062,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,52802,-51307,-3091,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,52133,-50948,-3104,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,51388,-52773,-2854,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,51616,-53310,-2813,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,53966,-51786,-3040,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,53162,-51515,-3079,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,50735,-49675,-2891,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,50651,-50371,-2821,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,51158,-50845,-2781,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,50239,-52165,-2888,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,50709,-52928,-2849,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,50942,-49222,-2947,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,57651,-57366,-3321,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,57479,-56340,-3352,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,58591,-56157,-3327,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,57921,-55653,-3332,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1560,58586,-55192,-3238,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,58541,-54553,-3199,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,57455,-55249,-3342,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,57795,-56317,-3356,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,58137,-57629,-3334,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,58732,-56918,-3315,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,58138,-56992,-3324,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,58320,-55917,-3328,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,58197,-55189,-3326,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,59375,-53820,-3234,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,59583,-52914,-3089,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,59599,-52087,-3114,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,60271,-52020,-2989,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1562,60098,-51343,-2908,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,60316,-51703,-2980,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,59779,-52288,-3096,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,60261,-52695,-3034,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,59702,-52908,-3065,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,58970,-53897,-3272,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,58939,-54541,-3194,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,59104,-55594,-3220,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,58341,-56172,-3328,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,57439,-56653,-3383,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1552,57543,-55825,-3309,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,58877,-54473,-3201,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,59859,-54113,-3224,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,59932,-53555,-3130,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,59558,-53238,-3129,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,59382,-53801,-3230,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,59230,-52339,-3216,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,60343,-52399,-3030,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,60335,-51304,-2905,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,59889,-50734,-2842,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,60008,-53840,-3180,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,57275,-51383,-3163,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,57360,-52552,-3246,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,56573,-52246,-3190,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,56705,-52962,-3249,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,58278,-53041,-3321,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1566,57959,-52212,-3188,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,58133,-51647,-3177,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,57724,-51131,-3162,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1564,56908,-51662,-3114,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,56911,-51997,-3108,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,56347,-52636,-3253,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,56326,-53644,-3371,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1569,57634,-52728,-3251,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,50543,-47113,-3400,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,50594,-45885,-3173,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,49583,-45329,-2974,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,48871,-41838,-2168,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,49500,-41375,-2258,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,50042,-40808,-2241,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,49128,-40288,-2233,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,48418,-45335,-2632,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,48096,-43849,-2518,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,47788,-44267,-2391,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,48165,-44671,-2370,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,48260,-43792,-2556,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,48852,-42343,-2244,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,49239,-41885,-2157,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,48679,-41409,-2210,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,51288,-39548,-2040,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,51139,-38515,-1869,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,49853,-39734,-2218,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,50690,-40715,-2247,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,49579,-40567,-2250,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,47922,-42001,-1975,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,47676,-42661,-1852,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,46839,-42134,-1830,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,47068,-41622,-1771,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,46687,-40857,-1973,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,46832,-39995,-2015,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,46819,-39146,-1833,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,48104,-39472,-1735,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,47554,-38683,-1746,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,47097,-37901,-1749,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,47194,-37474,-1738,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,47988,-38781,-1731,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,46341,-39761,-1990,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,46281,-37205,-1678,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,46591,-36695,-1645,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,48353,-36680,-1338,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,49108,-37431,-1696,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,49953,-36330,-1641,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,49937,-36622,-1662,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,48064,-35987,-1521,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,46220,-36387,-1657,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,45907,-37365,-1657,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,46953,-38272,-1652,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,47638,-39222,-1752,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,46406,-40009,-2014,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,47566,-36619,-1568,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,48921,-36541,-1428,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,49681,-35721,-1503,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,50513,-36225,-1684,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,48763,-35805,-1514,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,49860,-35989,-1573,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,49282,-36549,-1493,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,47292,-36405,-1583,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,47306,-37312,-1742,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,47668,-38458,-1710,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,47900,-39171,-1747,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,46559,-39483,-1938,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,46665,-40387,-2061,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,47337,-37731,-1783,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,46794,-36869,-1639,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,48262,-36194,-1467,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,48786,-37466,-1681,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,50311,-36408,-1703,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,61107,-42717,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1594,57710,-41463,-3156,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,59234,-41792,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,59437,-42557,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,58886,-42430,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1591,60096,-42638,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,60686,-42166,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,57751,-41812,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,60042,-42141,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,59221,-42362,-3003,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,59644,-47486,-2714,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,58796,-49470,-2857,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,58534,-48549,-2682,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,60537,-48137,-2758,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,60343,-48346,-2743,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,60498,-47581,-2732,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,60383,-46579,-2426,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,60543,-45606,-2523,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,61564,-45859,-2537,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,59828,-46270,-2557,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,60371,-47156,-2695,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,59604,-46657,-2583,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,60388,-46022,-2545,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,58721,-48937,-2808,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,59338,-47119,-2553,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,60084,-46497,-2521,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,60362,-45743,-2532,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,61124,-45712,-2563,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,61586,-46424,-2561,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,61044,-46919,-2646,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,60271,-47841,-2738,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,59560,-48114,-2721,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,59270,-47594,-2621,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,60078,-46796,-2500,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,55748,-49097,-3082,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,55648,-49845,-3110,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1577,55092,-49068,-3105,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,54226,-48559,-3323,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,53752,-49038,-3265,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1573,55168,-49364,-3085,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,55557,-48001,-3171,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,56254,-48938,-3036,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1599,57232,-47528,-2827,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,57732,-48609,-2848,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1579,57451,-49456,-3020,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,55995,-49507,-3121,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,54899,-49706,-2955,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1582,54614,-48491,-3280,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,54520,-48223,-3335,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,53906,-48190,-3346,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1571,54608,-49142,-3206,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,57471,-45126,-2615,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,57483,-45984,-2739,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,56851,-45610,-2866,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1586,56018,-45147,-2961,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,56138,-45668,-3030,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,56691,-45861,-2848,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1589,57385,-45629,-2754,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,55384,-45226,-2874,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,55112,-44053,-2931,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,55544,-44500,-2922,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1585,57349,-46255,-2773,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1587,55602,-45389,-2894,0,0,0,240,0,2),
(NULL,'forest_of_the_dead',1,1587,56641,-45690,-2905,0,0,0,240,0,2); */-- 20060712-[dp1896].sql
ALTER TABLE `global_tasks` CHANGE `last_activation` `last_activation` DECIMAL(20,0) NOT NULL DEFAULT 0;
-- 20060720-[2229].sql
/*
update npc set absorb_level='13' where (id='10319');

update npc set absorb_level='13', `type`='L2Boss' where (id='10338');

update npc set absorb_level='12', `type`='L2Boss' where (id='10337');

UPDATE npc SET absorb_level='12' WHERE id IN
('12372','10286','10374','10283');

UPDATE npc SET absorb_level='8' WHERE (id='650');

UPDATE npc SET absorb_level='6' WHERE id IN 
('647','646','987','986','988','847','846');

UPDATE npc SET absorb_level='4' WHERE id IN 
('800','638');

UPDATE npc SET absorb_level='3' WHERE id IN 
('797','636','637');

UPDATE npc SET absorb_level='2' WHERE id IN 
('795','796');
*/-- 20060920-[dp2090].sql
ALTER TABLE characters ADD power_grade DECIMAL( 11, 0 );
-- 20060925-[dp2103].sql
ALTER TABLE characters CHANGE power_grade power_grade DECIMAL( 11, 0 ) NULL DEFAULT NULL;
-- 20061010-[dp2162].sql
ALTER TABLE `character_subclasses` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT '0' NOT NULL;
ALTER TABLE `characters` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT NULL;
ALTER TABLE `pets_stats` CHANGE `expMax` `expMax` INT( 20 ) DEFAULT '0' NOT NULL;
ALTER TABLE `pets` CHANGE `exp` `exp` DECIMAL( 20, 0 ) DEFAULT NULL;
-- 20061015-[dp2197].sql
ALTER TABLE characters ADD nobless DECIMAL( 1, 0 ) DEFAULT '0' NOT NULL;
-- 20070501.sql
DELETE FROM character_quests WHERE var = 'awaitSealedMStone';

INSERT INTO character_quests
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitSealedMStone',1,0
FROM character_quests
WHERE name LIKE '374%' AND var='cond' AND value='1';

DELETE FROM character_quests WHERE var = 'awaitLight';

INSERT INTO character_quests
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitLight',1,0
FROM character_quests
WHERE name LIKE '374%' ;

DELETE FROM character_quests WHERE var = 'awaitTooth';

INSERT INTO character_quests
SELECT DISTINCT char_id, '374_WhisperOfDreams1','awaitTooth',1,0
FROM character_quests
WHERE name LIKE '374%' ;
-- update05052006.sql
ALTER TABLE `etcitem` ADD `drop_category` enum('0','1','2') NOT NULL default '2';
UPDATE etcitem SET drop_category = '1' WHERE item_id IN (
709,
710,
711,
712,
713,
714,
715,
716,
717,
718,
719,
720,
721,
722,
723,
724,
757,
805,
1007,
1548,
1712,
1713,
1714,
1715,
1716,
1717,
1719,
1722,
1723,
1725,
1726,
1727,
1731,
1736,
1737,
1896,
1897,
1898,
1899,
1900,
1901,
1902,
1903,
1904,
1905,
1906,
1907,
1908,
1909,
1910,
1911,
1912,
1913,
1914,
1915,
1916,
1917,
1918,
1920,
1921,
1922,
1924,
1925,
1926,
1927,
1928,
1929,
1932,
1934,
1935,
1936,
1937,
1938,
1939,
1940,
1942,
1943,
1944,
1946,
1948,
1949,
1951,
1952,
1953,
1955,
1957,
1958,
1959,
1960,
1961,
1962,
1963,
1965,
1966,
1967,
1968,
1969,
1970,
1971,
1973,
1976,
1977,
1979,
1981,
1982,
1983,
1985,
1986,
1988,
1990,
1991,
1992,
1993,
1994,
1995,
1996,
1997,
1998,
1999,
2000,
2001,
2002,
2003,
2004,
2917,
2918,
2919,
2920,
2921,
2922,
2924,
2925,
2926,
2927,
2928,
2930,
2932,
2933,
2935,
2936,
2937,
2938,
2940,
2941,
2942,
2943,
2944,
2946,
2949,
2950,
2951,
2952,
2953,
2954,
2955,
2956,
2957,
2958,
2959,
2960,
2961,
2962,
2963,
2964,
2965,
4049,
4050,
4051,
4052,
4053,
4054,
4056,
4058,
4059,
4060,
4061,
4062,
4063,
4064,
4065,
4066,
4069,
4070,
4071,
4072,
4073,
4074,
4075,
4076,
4078,
4079,
4080,
4081,
4082,
4084,
4085,
4086,
4087,
4088,
4089,
4090,
4091,
4092,
4093,
4095,
4096,
4097,
4098,
4099,
4102,
5478,
5479,
5480,
5481,
5482,
5483,
5485,
5486,
5487,
5488,
5489,
5490,
5492,
5496,
5497,
5498,
5499,
5500,
5501,
5502,
5503,
5504,
5505,
5506,
5507,
5508,
5509,
5510,
5511,
5512,
5513,
5514,
5515,
5516,
5517,
5518,
5519,
5520,
5521,
5522,
5523,
5524,
5525,
5526,
5527,
5528,
5758,
5759,
5760,
5761,
5762,
5763,
5764,
6341,
6342,
6343,
6344,
6345,
6346,
6698,
6699,
6700,
6701,
6702,
6703,
6704,
6706,
6707,
6708,
6709,
6710,
6711,
6712,
6713,
6714,
6904,
6907,
7684,
7685,
7686,
7687,
7688,
7697,
7698);
UPDATE etcitem SET drop_category = '0' WHERE consume_type = 'asset' OR item_type = 'quest';
-- update05092006.sql
UPDATE npc SET type = 'L2PenaltyMonster' WHERE id BETWEEN 13245 AND 13252;
UPDATE etcitem SET item_type = 'lure' WHERE name LIKE '%lure%' AND name NOT LIKE '%chest%';
-- update05152006.sql
ALTER TABLE character_subclasses ADD COLUMN class_index INT(1) NOT NULL DEFAULT 0 AFTER level;

UPDATE weapon SET soulshots=1,spiritshots=1 WHERE weaponType='pet';
-- update06072007.sql
ALTER TABLE pets_stats ADD owner_exp_taken DECIMAL(2,2) DEFAULT '0' NOT NULL;
UPDATE pets_stats SET owner_exp_taken = 0.1 WHERE type LIKE 'baby%' AND level >= 20;
UPDATE pets_stats SET owner_exp_taken = 0.3 WHERE type LIKE 'baby%' AND level < 20;
UPDATE npc SET type = 'L2BabyPet' WHERE name IN ('Baby Buffalo','Baby Kookaburra','Baby Cougar');
-- update06082007.sql
ALTER TABLE `pets_stats` 
CHANGE `owner_exp_taken` `owner_exp_taken` DECIMAL( 3, 2 ) NOT NULL DEFAULT '0.00';
-- update06122007.sql
ALTER TABLE items ADD mana_left DECIMAL( 3, 0 ) NOT NULL DEFAULT -1;
ALTER TABLE etcitem CHANGE COLUMN durability duration DECIMAL(3,0) DEFAULT NULL;
ALTER TABLE armor CHANGE COLUMN durability duration DECIMAL(3,0) DEFAULT NULL;
ALTER TABLE weapon CHANGE COLUMN durability duration DECIMAL(3,0) DEFAULT NULL;
-- update09122007.sql
ALTER TABLE characters ADD COLUMN death_penalty_level int(2) NOT NULL DEFAULT 0 AFTER clan_create_expiry_time;
-- update10152006.sql
ALTER TABLE characters ADD nobless DECIMAL( 1, 0 ) DEFAULT '0' NOT NULL AFTER power_grade;
-- update12092007.sql
DROP TABLE IF EXISTS zone_cuboid;
DROP TABLE IF EXISTS zone_cylinder;
DROP TABLE IF EXISTS zone_npoly;
-- update17092007.sql
DELETE FROM `spawnlist` WHERE `npc_templateid` IN(
18287,18288,18289,18290,18291,18292,18293,18294,
18295,18296,18297,18298,21659,21660,21661,21662,
21663,21664,21665,21666,21667,21668,21669,21670,
21671,21672,21673,21674,21675,21676,21677,21678,
21679,21680,21681,21682,21683,21684,21685,21686,
21687,21688,21689,21690,21691,21692,21693,21694,
21695,21696,21697,21698,21699,21700,21701,21702,
21703,21704,21705,21706,21707,21708,21709,21710,
21711,21712,21713,21714,21715,21716,21717,21718,
21719,21720,21721,21722,21723,21724,21725,21726,
21727,21728,21729,21730,21731,21732,21733,21734,
21735,21736,21737,21738,21739,21740,21741,21742,
21743,21744,21745,21746,21747,21748,21749,21750,
21751,21752,21753,21754,21755,21756,21757,21758,
21759,21760,21761,21762,21763,21764,21765,21766,
21767,21768,21769,21770,21771,21772,21773,21774,
21775,21776,21777,21778,21779,21780,21781,21782,
21783,21784,21785,21786,21787,21788,21789,21790,
21791,21792,21793,21794,21795,21796,25333,25334,
25335,25336,25337,25338
);

UPDATE `npc` SET `type` = 'L2RiftInvader', `aggro` = 300, `faction_range` = 300, `isUndead` = 1 WHERE `id` IN(
21659,21660,21661,21662,21663,21664,21665,21666,
21667,21668,21669,21670,21671,21672,21673,21674,
21675,21676,21677,21678,21679,21680,21681,21682,
21683,21684,21685,21686,21687,21688,21689,21690,
21691,21692,21693,21694,21695,21696,21697,21698,
21699,21700,21701,21702,21703,21704,21705,21706,
21707,21708,21709,21710,21711,21712,21713,21714,
21715,21716,21717,21718,21719,21720,21721,21722,
21723,21724,21725,21726,21727,21728,21729,21730,
21731,21732,21733,21734,21735,21736,21737,21738,
21739,21740,21741,21742,21743,21744,21745,21746,
21747,21748,21749,21750,21751,21752,21753,21754,
21755,21756,21757,21758,21759,21760,21761,21762,
21763,21764,21765,21766,21767,21768,21769,21770,
21771,21772,21773,21774,21775,21776,21777,21778,
21779,21780,21781,21782,21783,21784,21785,21786,
21787,21788,21789,21790,21791,21792,21793,21794,
21795,21796
);

UPDATE `npc` SET `type` = 'L2Chest', `isUndead` = 0 WHERE `id` IN(
21671,21694,21717,21740,21763,21786
);

UPDATE `npc` SET `type` = 'L2Chest', `aggro` = 0 WHERE `id` IN(
18287,18288,18289,18290,18291,18292,18293,18294,
18295,18296,18297,18298
);

UPDATE `npc` SET `type` = 'L2Boss', `aggro` = 0 WHERE `id` IN(
25333,25334,25335,25336,25337,25338
);
-- update17112007.sql
ALTER TABLE character_skills_save ADD buff_index int(2) NOT NULL default 0;
-- update20060522.sql
ALTER TABLE seven_signs_status MODIFY COLUMN dawn_stone_score DECIMAL(20,0) NOT NULL DEFAULT 0,
 MODIFY COLUMN dusk_stone_score DECIMAL(20,0) NOT NULL DEFAULT 0;

ALTER TABLE seven_signs MODIFY COLUMN ancient_adena_amount DECIMAL(20,0) NOT NULL DEFAULT 0,
 MODIFY COLUMN contribution_score DECIMAL(20,0) NOT NULL DEFAULT 0;
-- update20060607.sql
ALTER TABLE `characters` ADD COLUMN `isin7sdungeon` DECIMAL(1,0) NOT NULL DEFAULT 0 AFTER `deleteclan`;
-- update20060614.sql
ALTER TABLE `npc` ADD `absorb_level` decimal(2,0) default 0;

UPDATE `npc` SET `absorb_level` = 2 WHERE `id` IN (583, 584, 585, 586, 769, 770, 793, 794, 849);
UPDATE `npc` SET `absorb_level` = 3 WHERE `id` IN (587, 588, 638, 767, 768, 798, 799, 800, 838, 839, 848);
UPDATE `npc` SET `absorb_level` = 4 WHERE `id` IN (636, 637, 639, 801, 802, 840, 841, 842, 995);
UPDATE `npc` SET `absorb_level` = 5 WHERE `id` IN (640, 641, 642, 803, 843, 844, 846, 847, 986, 987, 988, 994, 12544);
UPDATE `npc` SET `absorb_level` = 7 WHERE `id` IN (646, 647, 648, 649, 650);
UPDATE `npc` SET `absorb_level` = 9 WHERE `id` IN (1006);
UPDATE `npc` SET `absorb_level` = 10 WHERE `id` IN (625, 626, 627, 628, 629, 674, 761, 762, 821, 823, 826, 827, 828, 829, 830, 831, 1007, 1008, 1063, 1067, 1070);
UPDATE `npc` SET `absorb_level` = 13 WHERE `id` IN (10283, 10286, 12211, 12372, 12374, 12899);
-- update20061118.sql
ALTER TABLE clan_data ADD COLUMN reputation_score INT NOT NULL DEFAULT 0;

ALTER TABLE characters ADD COLUMN subpledge INT NOT NULL DEFAULT 0;
-- update20061124.sql
ALTER TABLE characters ADD COLUMN last_recom_date decimal(20,0) NOT NULL DEFAULT 0 AFTER subpledge;
-- update20061126.sql
ALTER TABLE character_skills_save ADD COLUMN reuse_delay INT(8) NOT NULL DEFAULT 0 AFTER effect_cur_time;
ALTER TABLE character_skills_save ADD COLUMN restore_type INT(1) NOT NULL DEFAULT 0 AFTER reuse_delay;
-- update20061204.sql
ALTER TABLE teleport ADD COLUMN fornoble INT(1) NOT NULL DEFAULT 0 AFTER price;
-- update20061206.sql
ALTER TABLE characters ADD COLUMN lvl_joined_academy int(1) NOT NULL DEFAULT 0 AFTER last_recom_date;
ALTER TABLE characters ADD COLUMN apprentice int(1) NOT NULL DEFAULT 0 AFTER lvl_joined_academy;
ALTER TABLE characters ADD COLUMN sponsor int(1) NOT NULL DEFAULT 0 AFTER apprentice;
-- update20061207.sql
ALTER TABLE `character_skills` CHANGE `skill_name` `skill_name` varchar(30);
-- update20061208.sql
ALTER TABLE skill_trees CHANGE name name varchar(35) NOT NULL default '';
ALTER TABLE character_skills CHANGE skill_name skill_name varchar(35);
-- update20061223.sql
ALTER TABLE weapon CHANGE name name varchar(50) default NULL;
-- update20061230.sql
-- *** DANGER *** - This update must DROP & CREATE the `clanhall` & `auction` tables due to structure changes

ALTER TABLE clan_data ADD `auction_bid_at` INT NOT NULL default '0';
ALTER TABLE auction_bid ADD `time_bid` decimal(20,0) NOT NULL default '0';
ALTER TABLE auction_bid ADD `clan_name` varchar(50) NOT NULL after `bidderName`;

-- removing duplicates spawns with bad IDs
DELETE FROM spawnlist where npc_templateid in (30800,30798,30802,31158,31160,31156,31152,31150,31154,30784,30788,30790,30786,30778,30780,30782,30774,30776,30798);

-- updating auctionners
UPDATE npc SET type = 'L2Auctioneer' where id in (30767,30768,30769,30770,30771);

-- updating doormens
update npc set type = 'L2Doormen' where id in
(30492,30493,30772,30773,30775,30777,30778,30779,30781,30783,30785,30787,30789,30791,30799,30801,30803,31151,31153,31155,31157,31159,31161,31352,31353,31354,31355,31447,31448,31449,31450,31451,35138,35139,35180,35181,35222,35223,35267,35268,35269,35270,35337,35433,35434,35312,35313,35096,35097,35581,35583,35585,35587);

-- updating clan hall managers
update npc set type='L2ClanHallManager', title='Clan Hall Manager' where id in
(35385,35438,35453,35455,35451,35457,35459,35398,35400,35392,35394,35396,35384,35390,35386,35388,35855,35856,35857,35858,35859,35860,35407,35403,35405,35864,35865,35866,35867,35868,35869,35870,35871,35872,35873,35874,35875,35876,35877,35878,35879,35880,35881,35882,35883,35884,35885,35886,35439,35441,35443,35445,35447,35449,35467,35465,35463,35461);

-- setting zones
DELETE FROM zone WHERE type = 'Clan Hall';
ALTER TABLE zone ADD `z2` int(11) NOT NULL default '0' AFTER `z`;
INSERT INTO  zone VALUES
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

-- -------------------------------
-- C5 Clan Halls (these are not correct, but just to avoid NPEs)
-- -------------------------------

INSERT INTO zone (id, type, name, x1, y1, x2, y2, z, taxById) VALUES
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

-- -----------------------------------------
-- C5 town and castle spawns
-- -----------------------------------------
INSERT INTO zone (id, type, name, x1, y1, x2, y2, z, taxById) VALUES
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

-- adding teleporting locations
INSERT INTO teleport VALUES
('Clan Hall -> Execution Grounds',502,51055,141959,-2869,500,0),
-- ('Clan Hall -> Fortress of Resistance',503,51055,141959,-2869,500,0), -- dunno coords !
('Clan Hall -> Cruma Marshlands',504,5106,126916,-3664,500,0),
('Clan Hall -> Cruma Tower Entrance',505,17192,114178,-3439,500,0),
 -- ('Clan Hall -> Mandragora Farm',506,17192,114178,-3439,500,0), -- dunno coords !
('Clan Hall -> Town of Dion',507,15670,142983,-2705,500,0),
('Clan Hall -> Floran Village',508,17838,170274,-3508,500,0),
 -- 509
('Clan Hall -> Tanor Canyon',510,51147,165543,-2829,500,0),
('Clan Hall -> Bee Hive',511,20505,189036,-3344,500,0),
 -- ('Clan Hall -> Dion Hills',512,20505,189036,-3344,500,0), -- dunno coords !
 -- ('Clan Hall -> Floran Agricultural Area',513,20505,189036,-3344,500,0), -- dunno coords !
 -- ('Clan Hall -> Plains of Dion',514,20505,189036,-3344,500,0), -- dunno coords !
 -- 515
 -- 516
('Clan Hall -> Hardin\'s Academy',517,105918,109759,-3207,500,0),
('Clan Hall -> Dragon Valley',518,122824,110836,-3720,500,0),
 -- 519
 -- 520
('Clan Hall -> Death Pass',521,70000,126636,-3804,500,0),
('Clan Hall -> Pirate Tunnel',522,41298,200350,-4583,500,0),
 -- 523
('Clan Hall -> Giran Harbor',524,47942,186764,-3485,500,0),
('Clan Hall -> Giran Castle Town',525,83400,147943,-3404,500,0),
('Clan Hall -> Giran Arena',526,73890,142656,-3778,500,0),
 -- 527
('Clan Hall -> Breka\'s Stronghold',528,79798,130624,-3677,500,0),
 -- ('Clan Hall -> Gorgon Flower Garden',529,79798,130624,-3677,500,0),  -- dunno coords !
 -- -----------------
('Clan Hall -> Ivory Tower',581,85348,16142,-3699,500,0),
('Clan Hall -> Town of Oren',582,85348,16142,-3699,500,0),
 -- 583
('Clan Hall -> Plains of Lizardmen',584,87252,85514,-3056,500,0),
('Clan Hall -> Skyshadow Meadow',585,82764,61145,-3502,500,0),
 -- ('Clan Hall -> Shilen\'s Garden',586,82764,61145,-3502,500,0),  -- dunno coords !
 -- ('Clan Hall -> Black Rock Hill',587,82764,61145,-3502,500,0),  -- dunno coords !
('Clan Hall -> Spider Nest',588,-56532,78321,-2960,500,0),
 -- ('Clan Hall -> Timak Outpost',589,-56532,78321,-2960,500,0), -- dunno coords !
 -- ('Clan Hall -> Ivory Tower Crater',590,-56532,78321,-2960,500,0), -- dunno coords !
 -- ('Clan Hall -> Forest of Evil',591,-56532,78321,-2960,500,0), -- dunno coords !
('Clan Hall -> Outlaw Forest',592,85995,-2433,-3528,500,0),
 -- ('Clan Hall -> Misty Mountains',593,85995,-2433,-3528,500,0), -- dunno coords !
 -- ('Clan Hall -> Starlight Waterfall',594,85995,-2433,-3528,500,0), -- dunno coords !
 -- ('Clan Hall -> Undine Waterfall',595,85995,-2433,-3528,500,0), -- dunno coords !
 -- ('Clan Hall -> The Gods\' Falls',596,85995,-2433,-3528,500,0),  -- dunno coords !
 -- 597
('Clan Hall -> Tower of Insolence',598,121685,15749,-3852,500,0),
('Clan Hall -> The Blazing Swamp',599,146828,-12859,-4455,500,0),
 -- 600
('Clan Hall -> The Forbidden Gateway',601,185395,20359,-3270,500,0),
('Clan Hall -> The Giants Cave',602,174528,52683,-4369,500,0),
('Clan Hall -> Northern Pathway of Enchanted Valley',603,104426,33746,-3800,500,0), -- need also southern?
('Clan Hall -> The Cemetery',604,172136,20325,-3326,500,0),
('Clan Hall -> The Forest of Mirrors',605,150477,85907,-2753,500,0),
('Clan Hall -> Anghel Waterfall',606,165584,85997,-2338,500,0),
('Clan Hall -> Aden Castle Town',607,146331,25762,-2018,500,0),
('Clan Hall -> Hunters Village',608,117110,76883,-2695,500,0),
('Clan Hall -> Border Outpost(Aden Side)',609,109699,-7908,-2902,500,0),
('Clan Hall -> Coliseum',610,150086,46733,-3412,500,0),
-- ('Clan Hall -> Narsell Lake',611,150086,46733,-3412,500,0), -- dunno coords !
 -- 612 
('Clan Hall -> Ancient Battleground',613,127739,-6998,-3869,500,0),
('Clan Hall -> Forsaken Plains',614,167285,37109,-4008,500,0),
('Clan Hall -> Silent Valley',615,177318,48447,-3835,500,0),
-- ('Clan Hall -> Hunters Valley',616,177318,48447,-3835,500,0), -- dunno coords !
-- ('Clan Hall -> Plains of Glory',617,177318,48447,-3835,500,0), -- dunno coords !
('Clan Hall -> Fields of Massacre',618,179718,48447,-7843,500,0),
-- ('Clan Hall -> War-Torn Plains',619,179718,48447,-7843,500,0), --- dunno coords !
('Clan Hall -> Border Outpost(Unknown Side)',620,114172,-18034,-1875,500,0);

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

-- ----------------------------
-- Records 
-- ----------------------------
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

-- 
-- Dumping data for table `auction`
-- 

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
ALTER TABLE characters ADD COLUMN varka_ketra_ally int(1) NOT NULL DEFAULT 0 AFTER sponsor;
-- update20070210.sql
ALTER TABLE `armor` ADD COLUMN `dropable` VARCHAR(5) DEFAULT 'true' AFTER `sellable`;
ALTER TABLE `etcitem` ADD COLUMN `dropable` VARCHAR(5) DEFAULT 'true' AFTER `sellable`;
ALTER TABLE `weapon` ADD COLUMN `dropable` VARCHAR(5) DEFAULT 'true' AFTER `sellable`;
ALTER TABLE `armor` ADD COLUMN `destroyable` VARCHAR(5) DEFAULT 'true' AFTER `dropable`;
ALTER TABLE `etcitem` ADD COLUMN `destroyable` VARCHAR(5) DEFAULT 'true' AFTER `dropable`;
ALTER TABLE `weapon` ADD COLUMN `destroyable` VARCHAR(5) DEFAULT 'true' AFTER `dropable`;
ALTER TABLE `armor` ADD COLUMN `tradeable` VARCHAR(5) DEFAULT 'true' AFTER `destroyable`;
ALTER TABLE `etcitem` ADD COLUMN `tradeable` VARCHAR(5) DEFAULT 'true' AFTER `destroyable`;
ALTER TABLE `weapon` ADD COLUMN `tradeable` VARCHAR(5) DEFAULT 'true' AFTER `destroyable`;

UPDATE `etcitem` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '4425');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6611');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6612');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6613');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6614');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6615');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6616');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6617');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6618');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6619');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6620');
UPDATE `weapon` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6621');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6408');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6656');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6657');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6658');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6659');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6660');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6661');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '6662');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6834');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6835');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6836');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6837');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6838');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6839');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6840');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '6841');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '7694');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '8182');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'false' WHERE (`item_id` = '8183');
UPDATE `armor` SET dropable = 'false', destroyable = 'false', tradeable = 'true' WHERE (`item_id` = '8191');
-- update20070216.sql
ALTER TABLE pets DROP objId;
ALTER TABLE pets DROP maxHp;
ALTER TABLE pets DROP maxMp;
ALTER TABLE pets DROP acc;
ALTER TABLE pets DROP crit;
ALTER TABLE pets DROP evasion;
ALTER TABLE pets DROP mAtk;
ALTER TABLE pets DROP mDef;
ALTER TABLE pets DROP mSpd;
ALTER TABLE pets DROP pAtk;
ALTER TABLE pets DROP pDef;
ALTER TABLE pets DROP pSpd;
ALTER TABLE pets DROP str;
ALTER TABLE pets DROP con;
ALTER TABLE pets DROP dex;
ALTER TABLE pets DROP _int;
ALTER TABLE pets DROP men;
ALTER TABLE pets DROP wit;
ALTER TABLE pets DROP maxload;
ALTER TABLE pets DROP max_fed;

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
ALTER TABLE raidboss_spawnlist DROP respawn_delay;
ALTER TABLE raidboss_spawnlist ADD respawn_min_delay INT( 11 ) NOT NULL default '43200' AFTER heading; -- 12 (36-24) hours
ALTER TABLE raidboss_spawnlist ADD respawn_max_delay INT( 11 ) NOT NULL default '129600' AFTER respawn_min_delay; -- 36 hours
DELETE FROM raidboss_spawnlist WHERE boss_id IN (25328, 25339, 25342, 25346, 25349); -- remove Shadow of Halisha and Hellman spawns (possible exploits)-- update20070303.sql
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
-- update20070503-[dp2978].sql
ALTER TABLE `merchant_buylists` ADD COLUMN `count` INT( 11 ) NOT NULL default '-1' AFTER `order`;
ALTER TABLE `merchant_buylists` ADD COLUMN `currentCount` INT( 11 ) NOT NULL default '-1' AFTER `count`;
ALTER TABLE `merchant_buylists` ADD COLUMN `time` INT NOT NULL DEFAULT '0' AFTER `currentCount`;
ALTER TABLE `merchant_buylists` ADD COLUMN `savetimer` DECIMAL(20,0) NOT NULL DEFAULT '0' AFTER `time`;
-- update20070503-[dp2980].sql
ALTER TABLE armor ADD item_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE armor ADD item_skill_lvl decimal(11,0) NOT NULL default '0';

ALTER TABLE weapon ADD item_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD item_skill_lvl decimal(11,0) NOT NULL default '0';

ALTER TABLE weapon ADD enchant4_skill_id decimal(11,0) NOT NULL default '0'; -- for duals +4
ALTER TABLE weapon ADD enchant4_skill_lvl decimal(11,0) NOT NULL default '0';

ALTER TABLE weapon ADD onCast_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCast_skill_lvl decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCast_skill_chance decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_lvl decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_chance decimal(11,0) NOT NULL default '0';


--         Boss jewelry        ---
UPDATE armor  SET item_skill_id = 3558, item_skill_lvl = 1 WHERE item_id = 6656; -- antharas earring 
UPDATE armor  SET item_skill_id = 3557, item_skill_lvl = 1 WHERE item_id = 6657; -- necklace of valakas 
UPDATE armor  SET item_skill_id = 3561, item_skill_lvl = 1 WHERE item_id = 6658; -- Ring of baium 
UPDATE armor  SET item_skill_id = 3559, item_skill_lvl = 1 WHERE item_id = 6659; -- Zaken earring 
UPDATE armor  SET item_skill_id = 3562, item_skill_lvl = 1 WHERE item_id = 6660; -- Ring of ant queen
UPDATE armor  SET item_skill_id = 3560, item_skill_lvl = 1 WHERE item_id = 6661; -- Earring of Orfen
UPDATE armor  SET item_skill_id = 3563, item_skill_lvl = 1 WHERE item_id = 6662; -- Ring of core
UPDATE armor  SET item_skill_id = 3604, item_skill_lvl = 1 WHERE item_id = 8191; -- Frintezza's Necklace

-- +4  passive skills ------
-- UPDATE weapon SET enchant4_skill_id = , enchant4_skill_lvl = WHERE item_id = ; --


--     passive weapon SAs     --
                                         --    swords   --
UPDATE weapon  SET item_skill_id = 3026, item_skill_lvl = 1 WHERE item_id = 4681; -- stormbringer 'critical anger' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4682; -- stormbringer 'focus' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4683; -- stormbringer 'light' 

UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 2 WHERE item_id = 4684; -- shamshir 'guidance' 
UPDATE weapon  SET item_skill_id = 3018, item_skill_lvl = 2 WHERE item_id = 4685; -- shamshir 'back blow' 
UPDATE weapon  SET item_skill_id = 3028, item_skill_lvl = 2 WHERE item_id = 4686; -- shamshir 'rsk. evasion'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 2 WHERE item_id = 4687; -- katana 'focus' 
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 2 WHERE item_id = 4688; -- katana 'critical damage' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4689; -- katana 'haste'

UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 2 WHERE item_id = 4690; -- spirit sword 'critical damage' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4692; -- spirit sword 'haste'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 2 WHERE item_id = 4693; -- raid sword 'focus'

UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 3 WHERE item_id = 4696; -- caliburs 'guidance' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4697; -- caliburs 'focus'
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4698; -- caliburs 'critical damage'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4699; -- sword of delusion 'focus'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4700; -- sword of delusion 'health'
UPDATE weapon  SET item_skill_id = 3032, item_skill_lvl = 3 WHERE item_id = 4701; -- sword of delustion 'rsk. haste'
	  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4702; -- tsurugi 'focus'
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4703; -- tsurugi 'critical damage'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 3 WHERE item_id = 4704; -- tsurugi 'haste'
  
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4705; -- sword of nightmare 'health'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4706; -- sword of nightmare 'focus'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4707; -- sword of nightmare 'light'
  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 4 WHERE item_id = 4708; -- samurai long sword 'focus'  
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 4 WHERE item_id = 4709; -- samurai long sword 'critical damage'  
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 4 WHERE item_id = 4710; -- samurai long sword 'haste'

UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 1 WHERE item_id = 4711; -- flamberge 'critical damage'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4712; -- flamberge 'focus'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4713; -- flamberge 'light'
  
UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 5 WHERE item_id = 4714; -- keshanberk 'guidance'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 5 WHERE item_id = 4715; -- keshanberk 'focus'  
UPDATE weapon  SET item_skill_id = 3018, item_skill_lvl = 5 WHERE item_id = 4716; -- keshanberk 'back blow'  

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 6 WHERE item_id = 4717; -- sword of damascus 'focus'     
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4718; -- sword of damascus 'critical damage'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4719; -- sword of damascus 'haste'

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4720; -- tallum blade 'health'
UPDATE weapon  SET item_skill_id = 3028, item_skill_lvl = 1 WHERE item_id = 4721; -- tallum blade 'rsk.evasion' 
UPDATE weapon  SET item_skill_id = 3032, item_skill_lvl = 2 WHERE item_id = 4722; -- tallum blade 'rsk.haste'
  
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4723; -- great sword 'health'  
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 5 WHERE item_id = 4724; -- great sword 'critical damage'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 5 WHERE item_id = 4725; -- great sword 'focus'

UPDATE weapon  SET item_skill_id = 3073, item_skill_lvl = 1 WHERE item_id = 5638; -- elemental sword 'magic power'
UPDATE weapon  SET item_skill_id = 3072, item_skill_lvl = 1 WHERE item_id = 5604; -- elemental sword 'empower'

UPDATE weapon  SET item_skill_id = 3073, item_skill_lvl = 2 WHERE item_id = 5641; -- sword of miracles 'magic power'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5643; -- sword of miracles 'acumen'

UPDATE weapon  SET item_skill_id = 3067, item_skill_lvl = 2 WHERE item_id = 5647; -- dark legions edge 'critical damage'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5648; -- dark legions edge 'health'
UPDATE weapon  SET item_skill_id = 3071, item_skill_lvl = 2 WHERE item_id = 5649; -- dark legions edge 'rsk. focus' 


  --     passive weapon SAs     --
                                         --    blunts   --
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4726; -- big hammer 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4727; -- big hammer 'rsk.focus'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4728; -- big hammer 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4729; -- battle axe 'anger' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4730; -- battle axe 'rsk.focus'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4731; -- battle axe 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4732; -- silver axe 'anger' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4733; -- silver axe 'rsk.focus' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4734; -- silver axe 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4735; -- skull graver 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4736; -- skull graver 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4737; -- skull graver 'rsk.focus'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 2 WHERE item_id = 4738; -- dwarven war hammer 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4739; -- dwarven war hammer 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4740; -- dwarven war hammer 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 3 WHERE item_id = 4741; -- war axe 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4742; -- war axe 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 3 WHERE item_id = 4743; -- war axe 'haste'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 4 WHERE item_id = 4744; -- yaksa mace 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4745; -- yaksa mace 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 4 WHERE item_id = 4746; -- yaksa mace 'rsk.focus' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 5 WHERE item_id = 4747; -- heav war axe 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4748; -- heavy war axe 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 5 WHERE item_id = 4749; -- heavy war axe 'rsk.focus'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 6 WHERE item_id = 4750; -- deadmans glory 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4751; -- deadmans glory 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4752; -- deadmans glory 'haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4753; -- art of battle axe 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 6 WHERE item_id = 4754; -- art of battle axe 'rsk.focus' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4755; -- art of battle axe 'haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4756; -- meteor shower 'health' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4757; -- meteor shower 'focus' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4758; -- meteor shower 'p.focus'  ?????
UPDATE weapon  SET item_skill_id = 3050, item_skill_lvl = 1 WHERE item_id = 5599; -- meteor shower 'focus'
UPDATE weapon  SET item_skill_id = 3056, item_skill_lvl = 2 WHERE item_id = 5601; -- meteor shower 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5602; -- elysian 'health'
UPDATE weapon  SET item_skill_id = 3057, item_skill_lvl = 2 WHERE item_id = 5603; -- elysian 'anger'

-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = ;


--     passive weapon SAs     --
                                         --    daggers   --
UPDATE weapon  SET item_skill_id = 3033, item_skill_lvl = 1 WHERE item_id = 4761; -- cursed dagger 'rsk.haste'

UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 1 WHERE item_id = 4762; -- dark elven dagger 'focus'
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 1 WHERE item_id = 4763; -- dark elven dagger 'back blow' 
UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 1 WHERE item_id = 4764; -- dark elven dagger 'might mortal'

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 2 WHERE item_id = 4767; -- stiletoo 'might mortal'
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4768; -- grace dagger 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 3 WHERE item_id = 4769; -- grace dagger 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 3 WHERE item_id = 4770; -- grace dagger 'back blow' 

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4771; -- dark screamer 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 3 WHERE item_id = 4772; -- dark screamer 'focus' 

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 4 WHERE item_id = 4776; -- crystal dagger 'might mortal'
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 5 WHERE item_id = 4777; -- kris 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 5 WHERE item_id = 4778; -- kris 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 5 WHERE item_id = 4779; -- kris 'back blow' 

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 6 WHERE item_id = 4782; -- deamons sword 'might mortal'

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 6 WHERE item_id = 4783; -- bloody orchid 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 2 WHERE item_id = 4784; -- bloody orchid 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 6 WHERE item_id = 4785; -- bloody orchid 'back blow' 
UPDATE weapon  SET item_skill_id = 3051, item_skill_lvl = 1 WHERE item_id = 5614; -- bloody orchid 'focus';

UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 5 WHERE item_id = 4786; -- hell knife 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 5 WHERE item_id = 4787; -- hell knife 'back blow' 
UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 5 WHERE item_id = 4788; -- hell knife 'might mortal' 

UPDATE weapon  SET item_skill_id = 3064, item_skill_lvl = 1 WHERE item_id = 5617; -- soul separator 'guidance'
UPDATE weapon  SET item_skill_id = 3066, item_skill_lvl = 2 WHERE item_id = 5618; -- soul separator 'critical damage'
UPDATE weapon  SET item_skill_id = 3056, item_skill_lvl = 2 WHERE item_id = 5619; -- sould separator 'rsk. haste';


--     passive weapon SAs     --
                                         --    fists    --
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 1 WHERE item_id = 4791; -- chakram 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 3 WHERE item_id = 4792; -- fist blade 'rsk.evasion'
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 3 WHERE item_id = 4793; -- fist blade 'rsk. haste' 
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 3 WHERE item_id = 4794; -- fist blade 'haste'  

UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 4 WHERE item_id = 4797; -- great pata 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 2 WHERE item_id = 4798; -- knuckle duster 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 2 WHERE item_id = 4799; -- knuckle duster 'rsk. haste' 
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 2 WHERE item_id = 4800; -- knuckle duster 'haste'  

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 5 WHERE item_id = 4802; -- arthro nail 'rsk. evasion'  
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 5 WHERE item_id = 4803; -- arthro nail 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 6 WHERE item_id = 4806; -- bellion cestus 'rsk. haste'  

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 6 WHERE item_id = 4808; -- blood tornado 'rsk. evasion'
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 6 WHERE item_id = 4809; -- bloody tornado 'haste' 
UPDATE weapon  SET item_skill_id = 3068, item_skill_lvl = 2 WHERE item_id = 5620; -- blood tornado 'haste'
UPDATE weapon  SET item_skill_id = 3565, item_skill_lvl = 1 WHERE item_id = 5621; -- blood tornado 'focus'
UPDATE weapon  SET item_skill_id = 3058, item_skill_lvl = 1 WHERE item_id = 5622; -- blood tornado 'anger';

UPDATE weapon  SET item_skill_id = 3069, item_skill_lvl = 1 WHERE item_id = 5623; -- dragon grinder 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3065, item_skill_lvl = 1 WHERE item_id = 5624; -- dragon grinder 'guidance'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5625; -- dragon grinder 'health'


--     passive weapon SAs     --
                                            --    bows    --
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 1 WHERE item_id = 4810; -- crystalized ice bow 'guidance'  
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 1 WHERE item_id = 4811; -- crystalized ice bow 'evasion' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4812; -- crystalized ice bow 'quite recovery'
  
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 2 WHERE item_id = 4813; -- elemental bow 'guidance'   
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4814; -- elemental bow 'miser'   
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4815; -- elemental bow 'quick recovery' 
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 2 WHERE item_id = 4816; -- elven bow of nobility 'evasion'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4817; -- elven bow of nobility 'miser'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4817; -- elven bow of nobility 'cheap shot'
  
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 3 WHERE item_id = 4819; -- akat long bow 'guidance'  
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4820; -- akat long bow 'evasion' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4821; --  akat long bow 'miser'

UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 4 WHERE item_id = 4822; -- eminence bow 'guidance'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4823; --  eminence bow 'miser'
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4824; --  eminence bow 'cheap shot'

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 5 WHERE item_id = 4825; -- dark elven long bow 'evasion'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4827; -- dark elven long bow 'miser'  

UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 6 WHERE item_id = 4828; -- bow of peril 'guidance'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4829; -- bow of peril 'quick recovery'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4830; -- bow of peril 'cheap shot'  

UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4832; -- carnage bow 'mana up'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4833; --  carnage 'quick recovery'
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 2 WHERE item_id = 5610; -- carnage bow 'mana up'


--     passive weapon SAs     --
                                           --    pole    --
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 1 WHERE item_id = 4834; -- scythe 'anger'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4836; -- scythe 'light'  

UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 1 WHERE item_id = 4837; -- orcish glaive 'anger'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4839; -- orcish glaive 'long blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4841; -- body slasher 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4842; -- body slasher 'wide blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4844; -- bec de corbin 'long blow'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4845; -- bec de corbin 'light'
  
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 3 WHERE item_id = 4846; --  scorpion 'anger'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4848; -- scorpion 'long blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4850; -- widow maker 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4851; -- widow maker 'wide blow'

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4853; -- orcish poleaxe 'long blow'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4854; -- orcish poleaxe 'wide blow'
 
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 5 WHERE item_id = 4855; -- great axe 'anger'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4857; --  great axe 'light'

UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 6 WHERE item_id = 4858; -- lance 'anger'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4860; -- lance 'long blow' 
 
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4862; -- halberd 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4863; -- halberd 'wide blow'
UPDATE weapon  SET item_skill_id = 3601, item_skill_lvl = 7 WHERE item_id = 5626; -- halberd 'haste'

UPDATE weapon  SET item_skill_id = 3602, item_skill_lvl = 8 WHERE item_id = 5632; -- tallum glaive 'guidance'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 4 WHERE item_id = 5633; -- tallum glaive 'health'
UPDATE weapon  SET item_skill_id = 3068, item_skill_lvl = 2 WHERE item_id = 5636; -- tallum glaive 'haste'
UPDATE weapon  SET item_skill_id = 3057, item_skill_lvl = 1 WHERE item_id = 5637; -- tallum blade 'anger'


--     passive weapon SAs     --
                                           --    staff    --
UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 1 WHERE item_id = 4867; -- crystal staff 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4868; -- crystal staff 'mana up'  

UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 3 WHERE item_id = 4879; -- paagrio hammer 'rsk. evasion'  
 
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4885; -- paagrio axe 'mana up' 
 
UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 4 WHERE item_id = 4891; -- ghouls staff 'rsk. evasion'   
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4892; -- ghouls staff 'mana up' 

UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 5596; -- dasparions staff 'mana up'
UPDATE weapon  SET item_skill_id = 3048, item_skill_lvl = 2 WHERE item_id = 5597; -- dasparions staff 'conversion'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5598; -- dasparions staff 'acumen'

UPDATE weapon  SET item_skill_id = 3048, item_skill_lvl = 2 WHERE item_id = 5605; -- branch of the mother tree 'conversion'
UPDATE weapon  SET item_skill_id = 3552, item_skill_lvl = 1 WHERE item_id = 5606; -- branch of the mother tree 'magic damage'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5607; -- branch of the mother tree 'acumen'

-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = ; --  


--     passive weapon SAs     --
                                           --    big swords    --
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5644; -- dragon slayer 'health'


--   active onCrit weapons SA ----
-- UPDATE weapon SET onCrit_skill_id = , onCrit_skill_lvl = , onCrit_skill_chance = WHERE item_id = ; --


--   active onCast weapons SA  ---

-- UPDATE weapon SET onCast_skill_id = , onCast_skill_lvl = , onCast_skill_chance = WHERE item_id = ; ---- update20070511.sql
ALTER TABLE `characters`
ADD `expBeforeDeath` decimal(20,0) default 0 
AFTER `exp`;

-- update20070601.sql
ALTER TABLE accounts
ADD lastServer int(4) default '1'
AFTER lastIP; -- update20070929-[dp3399].sql
ALTER TABLE skill_trees CHANGE name name varchar(40) NOT NULL default '';
ALTER TABLE character_skills CHANGE skill_name skill_name varchar(40);
-- update20071203.sql
UPDATE `clanhall` SET `lease`=100000;
-- update25052007.sql
ALTER TABLE `clanhall_functions`
DROP `inDebt`;
ALTER TABLE `clan_data`
DROP `hasHideout`;