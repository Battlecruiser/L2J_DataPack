DROP TABLE IF EXISTS `armorsets`;
CREATE TABLE `armorsets` (
 `id` SMALLINT UNSIGNED NOT NULL auto_increment,
 `chest` SMALLINT UNSIGNED NOT NULL default 0,
 `legs` SMALLINT UNSIGNED NOT NULL default 0,
 `head` SMALLINT UNSIGNED NOT NULL default 0,
 `gloves` SMALLINT UNSIGNED NOT NULL default 0,
 `feet` SMALLINT UNSIGNED NOT NULL default 0,
 `skill_id` SMALLINT UNSIGNED NOT NULL default 0,
 `skill_lvl` TINYINT UNSIGNED NOT NULL default 0,
 `shield` SMALLINT UNSIGNED NOT NULL default 0,
 `shield_skill_id` SMALLINT UNSIGNED NOT NULL default 0,
 `enchant6skill` SMALLINT UNSIGNED NOT NULL default 0,
 `mw_legs` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_head` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_gloves` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_feet` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 `mw_shield` SMALLINT UNSIGNED NOT NULL DEFAULT '0',
 PRIMARY KEY (`id`,`chest`)
);

-- NO GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (1,   23,    2386, 43,   0,    0,    3500, 1,    0,    0,    0,        0,       0,       0,         0,       0);   -- Wooden Breastplate set (heavy)
INSERT INTO `armorsets` VALUES (2,   1101,  1104, 44,   0,    0,    3501, 1,    0,    0,    0,        0,       0,       0,         0,       0);   -- Devotion robe set (robe)

-- D GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (3,   58,    59,   47,   0,    0,    3502, 1,    628,  3543, 3611,     0,       0,       0,         0,       0);   -- Mithril Breastplate set(heavy)
INSERT INTO `armorsets` VALUES (4,   352,   2378, 2411, 0,    0,    3506, 1,    2493, 3544, 3611,     0,       0,       0,         0,       0);   -- Brigandine Armor set

INSERT INTO `armorsets` VALUES (5,   394,   416,  0,    0,    2422, 3503, 1,    0,    0,    3612,     0,       0,       0,         0,       0);   -- Reinforced leather set
INSERT INTO `armorsets` VALUES (6,   395,   417,  0,    0,    2424, 3505, 1,    0,    0,    3612,     0,       0,       0,         0,       0);   -- Manticore skin set

INSERT INTO `armorsets` VALUES (7,   436,   469,  0,    2447, 0,    3504, 1,    0,    0,    3613,     0,       0,       0,         0,       0);   -- Tunic of knowledge set
INSERT INTO `armorsets` VALUES (8,   437,   470,  0,    2450, 0,    3507, 1,    0,    0,    3613,     0,       0,       0,         0,       0);   -- Mithril Tunic

-- C GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (9,   354,   381,  2413, 0,    0,    3509, 1,    2495, 3545, 3614,     0,       0,       0,         0,       0);   -- Chain Mail Shirt set
INSERT INTO `armorsets` VALUES (10,  60,    0,    517,  0,    0,    3512, 1,    107,  3546, 3614,     0,       0,       0,         0,       0);   -- Composite Armor set
INSERT INTO `armorsets` VALUES (11,  356,   0,    2414, 0,    0,    3516, 1,    2497, 3547, 3614,     0,       0,       0,         0,       0);   -- Full Plate Armor set

INSERT INTO `armorsets` VALUES (12,  397,   2387, 0,    0,    62,   3508, 1,    0,    0,    3615,     0,       0,       0,         0,       0);   -- Mithrill shirt set
INSERT INTO `armorsets` VALUES (13,  398,   418,  0,    0,    2431, 3511, 1,    0,    0,    3615,     0,       0,       0,         0,       0);   -- Plated leather set
INSERT INTO `armorsets` VALUES (14,  400,   420,  0,    0,    2436, 3514, 1,    0,    0,    3615,     0,       0,       0,         0,       0);   -- Theca leather set
INSERT INTO `armorsets` VALUES (15,  401,   0,    0,    0,    2437, 3515, 1,    0,    0,    3615,     0,       0,       0,         0,       0);   -- Drake leather set

INSERT INTO `armorsets` VALUES (16,  439,   471,  0,    2454, 0,    3510, 1,    0,    0,    3616,     0,       0,       0,         0,       0);   -- Karmian robe set
INSERT INTO `armorsets` VALUES (17,  441,   472,  0,    2459, 0,    3513, 1,    0,    0,    3616,     0,       0,       0,         0,       0);   -- Demon robe set
INSERT INTO `armorsets` VALUES (18,  442,   473,  0,    2463, 0,    3517, 1,    0,    0,    3616,     0,       0,       0,         0,       0);   -- Divine robe set

-- B GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (19,  357,   383,  503,  5710, 5726, 3518, 1,    0,    0,    3617,     11355,   11363,   11356,     11359,   0);   -- Zubei's Breastplate set
INSERT INTO `armorsets` VALUES (20,  2384,  2388, 503,  5711, 5727, 3520, 1,    0,    0,    3618,     11353,   12978,   11357,     11360,   0);   -- Zubei's leather set
INSERT INTO `armorsets` VALUES (21,  2397,  2402, 503,  5712, 5728, 3522, 1,    0,    0,    3619,     11378,   12979,   11358,     11361,   0);   -- Zubei robe set

INSERT INTO `armorsets` VALUES (22,  2376,  2379, 2415, 5714, 5730, 3519, 1,    673,  3548, 3617,     11375,   11373,   11365,     11370,   11374);   -- Avadon heavy set
INSERT INTO `armorsets` VALUES (23,  2390,  0,    2415, 5715, 5731, 3521, 1,    0,    0,    3618,     0,       12980,   11366,     11371,   0);   -- Avadon leather set
INSERT INTO `armorsets` VALUES (24,  2406,  0,    2415, 5716, 5732, 3523, 1,    0,    0,    3619,     0,       12981,   11367,     11372,   0);   -- Avadon robe set

INSERT INTO `armorsets` VALUES (25,  358,   2380, 2416, 5718, 5734, 3524, 1,    0,    0,    3617,     11394,   11403,   11399,     11396,   0);   -- Blue Wolf's Breastplate set
INSERT INTO `armorsets` VALUES (26,  2391,  0,    2416, 5719, 5735, 3526, 1,    0,    0,    3618,     0,       12984,   11400,     11397,   0);   -- Blue wolf leather set
INSERT INTO `armorsets` VALUES (27,  2398,  2403, 2416, 5720, 5736, 3528, 1,    0,    0,    3619,     11404,   12985,   11401,     11398,   0);   -- Blue Wolf robe set

INSERT INTO `armorsets` VALUES (28,  2381,  0,    2417, 5722, 5738, 3525, 1,    110,  3549, 3617,     0,       11387,   11379,     11382,   11385);   -- Doom plate heavy set
INSERT INTO `armorsets` VALUES (29,  2392,  0,    2417, 5723, 5739, 3527, 1,    0,    0,    3618,     0,       12982,   11380,     11383,   0);   -- Doom leather set
INSERT INTO `armorsets` VALUES (30,  2399,  2404, 2417, 5724, 5740, 3529, 1,    0,    0,    3619,     11406,   12983,   11381,     11384,   0);   -- Doom robe set

-- A GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (31,  365,   388,  512,  5765, 5777, 3530, 1,    641,  3550, 3620,     11407,   11417,   11408,     11413,   11416);   -- Dark Crystal Breastplate set
INSERT INTO `armorsets` VALUES (32,  2385,  2389, 512,  5766, 5778, 3532, 1,    0,    0,    3621,     11419,   12986,   11409,     11414,   0);   -- Dark Crystal leather set
INSERT INTO `armorsets` VALUES (33,  2407,  0,    512,  5767, 5779, 3535, 1,    0,    0,    3622,     0,       12987,   11410,     11415,   0);   -- Dark Crystal robe set

INSERT INTO `armorsets` VALUES (34,  2382,  0,    547,  5768, 5780, 3531, 1,    0,    0,    3620,     0,       11446,   11437,     11441,   0);   -- Tallum plate heavy set
INSERT INTO `armorsets` VALUES (35,  2393,  0,    547,  5769, 5781, 3533, 1,    0,    0,    3621,     0,       12988,   11438,     11442,   0);   -- Tallum leather set
INSERT INTO `armorsets` VALUES (36,  2400,  2405, 547,  5770, 5782, 3534, 1,    0,    0,    3622,     11447,   12989,   11439,     11443,   0);   -- Tallum robe set

INSERT INTO `armorsets` VALUES (37,  374,   0,    2418, 5771, 5783, 3536, 1,    2498, 3551, 3620,     0,       11481,   11472,     11477,   11480);   -- Nightmare heavy set
INSERT INTO `armorsets` VALUES (38,  2394,  0,    2418, 5772, 5784, 3538, 1,    0,    0,    3621,     0,       12992,   11473,     11478,   0);   -- Nightmare leather set
INSERT INTO `armorsets` VALUES (39,  2408,  0,    2418, 5773, 5785, 3540, 1,    0,    0,    3622,     0,       12993,   11474,     11479,   0);   -- Robe of nightmare set

INSERT INTO `armorsets` VALUES (40,  2383,  0,    2419, 5774, 5786, 3537, 1,    0,    0,    3620,     0,       11456,   11448,     11453,   0);   -- Majestic plate heavy set
INSERT INTO `armorsets` VALUES (41,  2395,  0,    2419, 5775, 5787, 3539, 1,    0,    0,    3621,     0,       12990,   11449,     11454,   0);   -- Majestic leather set
INSERT INTO `armorsets` VALUES (42,  2409,  0,    2419, 5776, 5788, 3541, 1,    0,    0,    3622,     0,       12991,   11450,     11455,   0);   -- Majestic robe set

-- S GRADE Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (43,  6373,  6374, 6378, 6375, 6376, 3553, 1,    6377, 3554, 3623,     11505,   11509,   11506,     11507,   11508);   -- Imperial crusader set
INSERT INTO `armorsets` VALUES (44,  6379,  0,    6382, 6380, 6381, 3555, 1,    0,    0,    3624,     0,       11486,   11483,     11484,   0);   -- Draconic leather set
INSERT INTO `armorsets` VALUES (45,  6383,  0,    6386, 6384, 6385, 3556, 1,    0,    0,    3625,     0,       11490,   11487,     11489,   0);   -- Major arcana robe set

-- Clan Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (46,  7851,  0,    7850, 7852, 7853, 3605, 1,    0,    0,    3611,     0,       0,       0,         0,       0);   -- Clan oath Armor set (heavy)
INSERT INTO `armorsets` VALUES (47,  7854,  0,    7850, 7855, 7856, 3606, 1,    0,    0,    3612,     0,       0,       0,         0,       0);   -- Clan Oath Brigandine set (light)
INSERT INTO `armorsets` VALUES (48,  7857,  0,    7850, 7858, 7859, 3607, 1,    0,    0,    3613,     0,       0,       0,         0,       0);   -- Clan Oath Aketon set (robe)

INSERT INTO `armorsets` VALUES (49,  9821,  0,    9820, 9822, 9823, 3605, 1,    0,    0,    3611,     0,       0,       0,         0,       0);   -- Shadow Item: Clan oath Armor set (heavy)
INSERT INTO `armorsets` VALUES (50,  9824,  0,    9820, 9825, 9826, 3606, 1,    0,    0,    3612,     0,       0,       0,         0,       0);   -- Shadow Item: Clan Oath Brigandine set (light)
INSERT INTO `armorsets` VALUES (51,  9827,  0,    9820, 9828, 9829, 3607, 1,    0,    0,    3613,     0,       0,       0,         0,       0);   -- Shadow Item: Clan Oath Aketon set (robe)

INSERT INTO `armorsets` VALUES (52,  7861,  0,    7860, 7862, 7863, 3608, 1,    0,    0,    3620,     0,       0,       0,         0,       0);   -- Apella plate armor set (heavy)
INSERT INTO `armorsets` VALUES (53,  7864,  0,    7860, 7865, 7866, 3609, 1,    0,    0,    3621,     0,       0,       0,         0,       0);   -- Apella Brigandine set (light)
INSERT INTO `armorsets` VALUES (54,  7867,  0,    7860, 7868, 7869, 3610, 1,    0,    0,    3622,     0,       0,       0,         0,       0);   -- Apella Doublet set (robe)

INSERT INTO `armorsets` VALUES (55,  9831,  0,    9830, 9832, 9833, 3608, 2,    0,    0,    3620,     0,       0,       0,         0,       0);   -- Improved Apella plate armor set (heavy)
INSERT INTO `armorsets` VALUES (56,  9834,  0,    9830, 9835, 9836, 3609, 2,    0,    0,    3621,     0,       0,       0,         0,       0);   -- Improved Apella Brigandine set (light)
INSERT INTO `armorsets` VALUES (57,  9837,  0,    9830, 9838, 9839, 3610, 2,    0,    0,    3622,     0,       0,       0,         0,       0);   -- Improved Apella Doublet set (robe)

-- S80 Dynasty Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (58,  9432,  9437, 9438, 9439, 9440, 3416, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic
INSERT INTO `armorsets` VALUES (59,  9433,  9437, 9438, 9439, 9440, 3354, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - Healer
INSERT INTO `armorsets` VALUES (60,  9434,  9437, 9438, 9439, 9440, 3355, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - Enchanter
INSERT INTO `armorsets` VALUES (61,  9435,  9437, 9438, 9439, 9440, 3356, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - Summoner
INSERT INTO `armorsets` VALUES (62,  9436,  9437, 9438, 9439, 9440, 3357, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - Human Wizard

INSERT INTO `armorsets` VALUES (63,  9416,  9421, 9422, 9423, 9424, 3412, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breast Plate
INSERT INTO `armorsets` VALUES (64,  9417,  9421, 9422, 9423, 9424, 3348, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breast Plate - Shield Master
INSERT INTO `armorsets` VALUES (65,  9418,  9421, 9422, 9423, 9424, 3349, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breast Plate - Weapon Master
INSERT INTO `armorsets` VALUES (66,  9419,  9421, 9422, 9423, 9424, 3350, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breast Plate - Force Master
INSERT INTO `armorsets` VALUES (67,  9420,  9421, 9422, 9423, 9424, 3351, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breast Plate - Bard

INSERT INTO `armorsets` VALUES (68,  9425,  9428, 9429, 9430, 9431, 3413, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor
INSERT INTO `armorsets` VALUES (69,  9426,  9428, 9429, 9430, 9431, 3352, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Dagger Master
INSERT INTO `armorsets` VALUES (70,  9427,  9428, 9429, 9430, 9431, 3353, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Bow Master
INSERT INTO `armorsets` VALUES (71,  10126, 9428, 9429, 9430, 9431, 3414, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Force Master
INSERT INTO `armorsets` VALUES (72,  10127, 9428, 9429, 9430, 9431, 3415, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Weapon Master
INSERT INTO `armorsets` VALUES (73,  10168, 9428, 9429, 9430, 9431, 3355, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Enchanter
INSERT INTO `armorsets` VALUES (74,  10214, 9428, 9429, 9430, 9431, 3420, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - Summoner

INSERT INTO `armorsets` VALUES (75,  10228, 9421, 9422, 9423, 9424, 3636, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Plate - Shield Master
INSERT INTO `armorsets` VALUES (76,  10229, 9421, 9422, 9423, 9424, 3637, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Plate - Weapon Master
INSERT INTO `armorsets` VALUES (77,  10230, 9421, 9422, 9423, 9424, 3639, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Plate - Force Master
INSERT INTO `armorsets` VALUES (78,  10231, 9421, 9422, 9423, 9424, 3638, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Plate - Bard

INSERT INTO `armorsets` VALUES (79,  10233, 9428, 9429, 9430, 9431, 3640, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Mail Dagger Master
INSERT INTO `armorsets` VALUES (80,  10234, 9428, 9429, 9430, 9431, 3641, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Mail Bow Master

INSERT INTO `armorsets` VALUES (81,  10236, 9437, 9438, 9439, 9440, 3645, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic Healer
INSERT INTO `armorsets` VALUES (82,  10237, 9437, 9438, 9439, 9440, 3646, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic Enchanter
INSERT INTO `armorsets` VALUES (83,  10238, 9437, 9438, 9439, 9440, 3647, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic Summoner
INSERT INTO `armorsets` VALUES (84,  10239, 9437, 9438, 9439, 9440, 3648, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic Human Wizard

INSERT INTO `armorsets` VALUES (85,  10487, 9428, 9429, 9430, 9431, 3642, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jeweled Leather Armor Force Master
INSERT INTO `armorsets` VALUES (86,  10488, 9428, 9429, 9430, 9431, 3643, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jeweled Leather Armor Weapon Master
INSERT INTO `armorsets` VALUES (87,  10489, 9428, 9429, 9430, 9431, 3646, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jeweled Leather Armor Enchanter
INSERT INTO `armorsets` VALUES (88,  10490, 9428, 9429, 9430, 9431, 3644, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jeweled Leather Armor Summoner

-- S84 Vesper Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (89,  13432, 13438,13137,13439,13440,8283, 1,    13471,3417, 3623,     0,       0,       0,         0,       0);   -- Vesper Breastplate
INSERT INTO `armorsets` VALUES (90,  13433, 13441,13138,13442,13443,8285, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Vesper Leather Breastplate
INSERT INTO `armorsets` VALUES (91,  13434, 13444,13139,13445,13446,8287, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Vesper Tunic

INSERT INTO `armorsets` VALUES (92,  13435, 13448,13140,13449,13450,8284, 1,    13471,3417, 3623,     0,       0,       0,         0,       0);   -- Vesper Noble Breastplate
INSERT INTO `armorsets` VALUES (93,  13436, 13451,13141,13452,13453,8286, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Vesper Noble Leather Breastplate
INSERT INTO `armorsets` VALUES (94,  13437, 13454,13142,13455,13456,8288, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Vesper Noble Tunic

-- A GRADE PVP Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (95,  10793, 0,    2418, 5771, 5783, 8193, 1,    2498, 3551, 3620,     0,       0,       0,         0,       0);   -- Armor of Nightmare - PvP
INSERT INTO `armorsets` VALUES (96,  10794, 0,    2419, 5774, 5786, 8194, 1,    0,    0,    3620,     0,       0,       0,         0,       0);   -- Majestic Plate Armor - PvP
INSERT INTO `armorsets` VALUES (97,  10795, 0,    2418, 5772, 5784, 8195, 1,    0,    0,    3621,     0,       0,       0,         0,       0);   -- Leather Armor of Nightmare - PvP
INSERT INTO `armorsets` VALUES (98,  10796, 0,    2419, 5775, 5787, 8196, 1,    0,    0,    3621,     0,       0,       0,         0,       0);   -- Majestic Leather Mail - PvP
INSERT INTO `armorsets` VALUES (99,  10797, 0,    2418, 5773, 5785, 8197, 1,    0,    0,    3622,     0,       0,       0,         0,       0);   -- Robe of Nightmare - PvP
INSERT INTO `armorsets` VALUES (100, 10798, 0,    2419, 5776, 5788, 8198, 1,    0,    0,    3622,     0,       0,       0,         0,       0);   -- Majestic Robe - PvP

-- S GRADE PVP Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (101, 10799, 6374, 6378, 6375, 6376, 8199, 1,    6377, 3554, 3623,     0,       0,       0,         0,       0);   -- Imperial Crusader Breastplate - PvP
INSERT INTO `armorsets` VALUES (102, 10800, 0,    6382, 6380, 6381, 8200, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Draconic Leather Armor - PvP
INSERT INTO `armorsets` VALUES (103, 10801, 0,    6386, 6384, 6385, 8201, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Major Arcana Robe - PvP

-- S80 Dynasty PVP Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (104, 10802, 9421, 9422, 9423, 9424, 8202, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breastplate - PvP
INSERT INTO `armorsets` VALUES (105, 10803, 9421, 9422, 9423, 9424, 8203, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breastplate - PvP Shield Master
INSERT INTO `armorsets` VALUES (106, 10804, 9421, 9422, 9423, 9424, 8204, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breastplate - PvP Weapon Master
INSERT INTO `armorsets` VALUES (107, 10805, 9421, 9422, 9423, 9424, 8205, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breastplate - PvP Force Master
INSERT INTO `armorsets` VALUES (108, 10806, 9421, 9422, 9423, 9424, 8206, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Breastplate - PvP Bard

INSERT INTO `armorsets` VALUES (109, 10807, 9428, 9429, 9430, 9431, 8207, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP
INSERT INTO `armorsets` VALUES (110, 10808, 9428, 9429, 9430, 9431, 8208, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Dagger Master
INSERT INTO `armorsets` VALUES (111, 10809, 9428, 9429, 9430, 9431, 8209, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Bow Master

INSERT INTO `armorsets` VALUES (112, 10810, 9437, 9438, 9439, 9440, 8210, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - PvP
INSERT INTO `armorsets` VALUES (113, 10811, 9437, 9438, 9439, 9440, 8211, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - PvP Healer
INSERT INTO `armorsets` VALUES (114, 10812, 9437, 9438, 9439, 9440, 8212, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - PvP Enchanter
INSERT INTO `armorsets` VALUES (115, 10813, 9437, 9438, 9439, 9440, 8213, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - PvP Summoner
INSERT INTO `armorsets` VALUES (116, 10814, 9437, 9438, 9439, 9440, 8214, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Tunic - PvP Wizard

INSERT INTO `armorsets` VALUES (117, 10815, 9428, 9429, 9430, 9431, 8215, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Force Master
INSERT INTO `armorsets` VALUES (118, 10816, 9428, 9429, 9430, 9431, 8216, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Weapon Master
INSERT INTO `armorsets` VALUES (119, 10817, 9428, 9429, 9430, 9431, 8217, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Enchanter
INSERT INTO `armorsets` VALUES (120, 10818, 9428, 9429, 9430, 9431, 8218, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Leather Armor - PvP Summoner

INSERT INTO `armorsets` VALUES (121, 10820, 9421, 9422, 9423, 9424, 8219, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Breastplate - PvP Shield Master
INSERT INTO `armorsets` VALUES (122, 10821, 9421, 9422, 9423, 9424, 8220, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Breastplate - PvP Weapon Master
INSERT INTO `armorsets` VALUES (123, 10822, 9421, 9422, 9423, 9424, 8222, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Breastplate - PvP Force Master
INSERT INTO `armorsets` VALUES (124, 10823, 9421, 9422, 9423, 9424, 8221, 1,    9441, 3417, 3623,     0,       0,       0,         0,       0);   -- Dynasty Platinum Breastplate - PvP Bard

INSERT INTO `armorsets` VALUES (125, 10825, 9428, 9429, 9430, 9431, 8223, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP Dagger Master
INSERT INTO `armorsets` VALUES (126, 10826, 9428, 9429, 9430, 9431, 8224, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP Bow Master

INSERT INTO `armorsets` VALUES (127, 10828, 9437, 9438, 9439, 9440, 8229, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic - PvP Healer
INSERT INTO `armorsets` VALUES (128, 10829, 9437, 9438, 9439, 9440, 8230, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic - PvP Enchanter
INSERT INTO `armorsets` VALUES (129, 10830, 9437, 9438, 9439, 9440, 8231, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic - PvP Summoner
INSERT INTO `armorsets` VALUES (130, 10831, 9437, 9438, 9439, 9440, 8232, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Dynasty Silver Satin Tunic - PvP Wizard

INSERT INTO `armorsets` VALUES (131, 10832, 9428, 9429, 9430, 9431, 8225, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP	Force Master
INSERT INTO `armorsets` VALUES (132, 10833, 9428, 9429, 9430, 9431, 8226, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP	Weapon Master
INSERT INTO `armorsets` VALUES (133, 10834, 9428, 9429, 9430, 9431, 8228, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP	Enchanter
INSERT INTO `armorsets` VALUES (134, 10835, 9428, 9429, 9430, 9431, 8227, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Dynasty Jewel Leather Armor - PvP	Summoner

-- S84 Vesper PVP Armor Sets
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (135, 14520, 13438,13137,13439,13440,8301, 1,    13471,3417, 3623,     0,       0,       0,         0,       0);   -- Vesper Breastplate {PvP}
INSERT INTO `armorsets` VALUES (136, 14521, 13441,13138,13442,13443,8303, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Vesper Leather Breastplate {PvP}
INSERT INTO `armorsets` VALUES (137, 14522, 13444,13139,13445,13446,8305, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Vesper Tunic {PvP}

INSERT INTO `armorsets` VALUES (138, 14523, 13448,13140,13449,13450,8302, 1,    13471,3417, 3623,     0,       0,       0,         0,       0);   -- Vesper Noble Breastplate {PvP}
INSERT INTO `armorsets` VALUES (139, 14524, 13451,13141,13452,13453,8304, 1,    0,    0,    3624,     0,       0,       0,         0,       0);   -- Vesper Noble Leather Breastplate {PvP}
INSERT INTO `armorsets` VALUES (140, 14525, 13454,13142,13455,13456,8306, 1,    0,    0,    3625,     0,       0,       0,         0,       0);   -- Vesper Noble Tunic {PvP}

-- Special Sets (as more armor sets are added, these should be the last in the list, adjust their IDs accordingly)
-- #########################    id   chest  legs  head  glov  feet  skill sklvl shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
INSERT INTO `armorsets` VALUES (141, 13803, 13804,13802,0,    0,    8277, 1,    0,    0,    0,        0,       0,       0,         0,       0);   -- Natives Set (bestows Native transform skill when all are worn together)
INSERT INTO `armorsets` VALUES (142, 13806, 13807,13805,0,    0,    8278, 1,    0,    0,    0,        0,       0,       0,         0,       0);   -- Guard of Dawns Set (bestows Guard transform skill when all are worn together)
