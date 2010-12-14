DROP TABLE IF EXISTS `armorsets`;
CREATE TABLE `armorsets` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `chest` smallint(5) unsigned NOT NULL DEFAULT '0',
  `legs` smallint(5) unsigned NOT NULL DEFAULT '0',
  `head` smallint(5) unsigned NOT NULL DEFAULT '0',
  `gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
  `feet` smallint(5) unsigned NOT NULL DEFAULT '0',
  `skill` varchar(70) NOT NULL DEFAULT '0-0;',
  `shield` smallint(5) unsigned NOT NULL DEFAULT '0',
  `shield_skill_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `enchant6skill` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_legs` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_head` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_gloves` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_feet` smallint(5) unsigned NOT NULL DEFAULT '0',
  `mw_shield` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`chest`)
);

INSERT INTO `armorsets` VALUES
-- NO GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (1,   23,    2386, 43,   0,    0,    '3006-1;3500-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Wooden Breastplate set (heavy)
  (2,   1101,  1104, 44,   0,    0,    '3006-1;3501-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Devotion robe set (robe)

-- D GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (3,   58,    59,   47,   0,    0,    '3006-1;3502-1;',               628,  3543, 3611,     0,       0,       0,         0,       0),     -- Mithril Breastplate set(heavy)
  (4,   394,   416,  0,    0,    2422, '3006-1;3503-1;',               0,    0,    3612,     0,       0,       0,         0,       0),     -- Reinforced leather set
  (5,   436,   469,  0,    2447, 0,    '3006-1;3504-1;',               0,    0,    3613,     0,       0,       0,         0,       0),     -- Tunic of knowledge set
  (6,   395,   417,  0,    0,    2424, '3006-1;3505-1;',               0,    0,    3612,     0,       0,       0,         0,       0),     -- Manticore skin set
  (7,   352,   2378, 2411, 0,    0,    '3006-1;3506-1;',               2493, 3544, 3611,     0,       0,       0,         0,       0),     -- Brigandine Armor set
  (8,   437,   470,  0,    2450, 0,    '3006-1;3507-1;',               0,    0,    3613,     0,       0,       0,         0,       0),     -- Mithril Tunic

-- C GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (12,  397,   2387, 0,    0,    62,   '3006-1;3508-1;',               0,    0,    3615,     0,       0,       0,         0,       0),     -- Mithrill shirt set
  (13,  354,   381,  2413, 0,    0,    '3006-1;3509-1;',               2495, 3545, 3614,     0,       0,       0,         0,       0),     -- Chain Mail Shirt set
  (14,  439,   471,  0,    2454, 0,    '3006-1;3510-1;',               0,    0,    3616,     0,       0,       0,         0,       0),     -- Karmian robe set
  (15,  398,   418,  0,    0,    2431, '3006-1;3511-1;',               0,    0,    3615,     0,       0,       0,         0,       0),     -- Plated leather set
  (19,  60,    0,    517,  0,    0,    '3006-1;3512-1;',               107,  3546, 3614,     0,       0,       0,         0,       0),     -- Composite Armor set
  (20,  441,   472,  0,    2459, 0,    '3006-1;3513-1;',               0,    0,    3616,     0,       0,       0,         0,       0),     -- Demon robe set
  (21,  400,   420,  0,    0,    2436, '3006-1;3514-1;',               0,    0,    3615,     0,       0,       0,         0,       0),     -- Theca leather set
  (22,  401,   0,    0,    0,    2437, '3006-1;3515-1;',               0,    0,    3615,     0,       0,       0,         0,       0),     -- Drake leather set
  (23,  356,   0,    2414, 0,    0,    '3006-1;3516-1;',               2497, 3547, 3614,     0,       0,       0,         0,       0),     -- Full Plate Armor set
  (24,  442,   473,  0,    2463, 0,    '3006-1;3517-1;',               0,    0,    3616,     0,       0,       0,         0,       0),     -- Divine robe set

-- B GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (25,  357,   383,  503,  5710, 5726, '3006-1;3518-1;',               0,    0,    3617,     11355,   11363,   11356,     11359,   0),     -- Zubei's Breastplate set
  (26,  2376,  2379, 2415, 5714, 5730, '3006-1;3519-1;',               673,  3548, 3617,     11375,   11373,   11365,     11370,   11374), -- Avadon heavy set
  (27,  2384,  2388, 503,  5711, 5727, '3006-1;3520-1;',               0,    0,    3618,     11353,   12978,   11357,     11360,   0),     -- Zubei's leather set
  (28,  2390,  0,    2415, 5715, 5731, '3006-1;3521-1;',               0,    0,    3618,     0,       12980,   11366,     11371,   0),     -- Avadon leather set
  (29,  2397,  2402, 503,  5712, 5728, '3006-1;3522-1;',               0,    0,    3619,     11378,   12979,   11358,     11361,   0),     -- Zubei robe set
  (30,  2406,  0,    2415, 5716, 5732, '3006-1;3523-1;',               0,    0,    3619,     0,       12981,   11367,     11372,   0),     -- Avadon robe set
  (32,  358,   2380, 2416, 5718, 5734, '3006-1;3524-1;',               0,    0,    3617,     11394,   11403,   11399,     11396,   0),     -- Blue Wolf's Breastplate set
  (33,  2381,  0,    2417, 5722, 5738, '3006-1;3525-1;',               110,  3549, 3617,     0,       11387,   11379,     11382,   11385), -- Doom plate heavy set
  (34,  2391,  0,    2416, 5719, 5735, '3006-1;3526-1;',               0,    0,    3618,     0,       12984,   11400,     11397,   0),     -- Blue wolf leather set
  (35,  2392,  0,    2417, 5723, 5739, '3006-1;3527-1;',               0,    0,    3618,     0,       12982,   11380,     11383,   0),     -- Doom leather set
  (36,  2398,  2403, 2416, 5720, 5736, '3006-1;3528-1;',               0,    0,    3619,     11404,   12985,   11401,     11398,   0),     -- Blue Wolf robe set
  (37,  2399,  2404, 2417, 5724, 5740, '3006-1;3529-1;',               0,    0,    3619,     11406,   12983,   11381,     11384,   0),     -- Doom robe set

-- A GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (39,  365,   388,  512,  5765, 5777, '3006-1;3530-1;',               641,  3550, 3620,     11407,   11417,   11408,     11413,   11416), -- Dark Crystal Breastplate set
  (40,  2382,  0,    547,  5768, 5780, '3006-1;3531-1;',               0,    0,    3620,     0,       11446,   11437,     11441,   0),     -- Tallum plate heavy set
  (41,  2385,  2389, 512,  5766, 5778, '3006-1;3532-1;',               0,    0,    3621,     11419,   12986,   11409,     11414,   0),     -- Dark Crystal leather set
  (42,  2393,  0,    547,  5769, 5781, '3006-1;3533-1;',               0,    0,    3621,     0,       12988,   11438,     11442,   0),     -- Tallum leather set
  (43,  2400,  2405, 547,  5770, 5782, '3006-1;3534-1;',               0,    0,    3622,     11447,   12989,   11439,     11443,   0),     -- Tallum robe set
  (44,  2407,  0,    512,  5767, 5779, '3006-1;3535-1;',               0,    0,    3622,     0,       12987,   11410,     11415,   0),     -- Dark Crystal robe set
  (46,  374,   0,    2418, 5771, 5783, '3006-1;3536-1;',               2498, 3551, 3620,     0,       11481,   11472,     11477,   11480), -- Nightmare heavy set
  (47,  2383,  0,    2419, 5774, 5786, '3006-1;3537-1;',               0,    0,    3620,     0,       11456,   11448,     11453,   0),     -- Majestic plate heavy set
  (48,  2394,  0,    2418, 5772, 5784, '3006-1;3538-1;',               0,    0,    3621,     0,       12992,   11473,     11478,   0),     -- Nightmare leather set
  (49,  2395,  0,    2419, 5775, 5787, '3006-1;3539-1;',               0,    0,    3621,     0,       12990,   11449,     11454,   0),     -- Majestic leather set
  (50,  2408,  0,    2418, 5773, 5785, '3006-1;3540-1;',               0,    0,    3622,     0,       12993,   11474,     11479,   0),     -- Robe of nightmare set
  (51,  2409,  0,    2419, 5776, 5788, '3006-1;3541-1;',               0,    0,    3622,     0,       12991,   11450,     11455,   0),     -- Majestic robe set

-- S GRADE Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (56,  6373,  6374, 6378, 6375, 6376, '3006-1;3553-1;',               6377, 3554, 3623,     11505,   11509,   11506,     11507,   11508), -- Imperial crusader set
  (57,  6379,  0,    6382, 6380, 6381, '3006-1;3555-1;',               0,    0,    3624,     0,       11486,   11483,     11484,   0),     -- Draconic leather set
  (58,  6383,  0,    6386, 6384, 6385, '3006-1;3556-1;',               0,    0,    3625,     0,       11490,   11487,     11489,   0),     -- Major arcana robe set

-- Clan Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (59,  7851,  0,    7850, 7852, 7853, '3006-1;3605-1;',               0,    0,    3611,     0,       0,       0,         0,       0),     -- Clan oath Armor set (heavy)
  (60,  7854,  0,    7850, 7855, 7856, '3006-1;3606-1;',               0,    0,    3612,     0,       0,       0,         0,       0),     -- Clan Oath Brigandine set (light)
  (61,  7857,  0,    7850, 7858, 7859, '3006-1;3607-1;',               0,    0,    3613,     0,       0,       0,         0,       0),     -- Clan Oath Aketon set (robe)
  (62,  7861,  0,    7860, 7862, 7863, '3006-1;3608-1;',               0,    0,    3620,     0,       0,       0,         0,       0),     -- Apella plate armor set (heavy)
  (63,  7864,  0,    7860, 7865, 7866, '3006-1;3609-1;',               0,    0,    3621,     0,       0,       0,         0,       0),     -- Apella Brigandine set (light)
  (64,  7867,  0,    7860, 7868, 7869, '3006-1;3610-1;',               0,    0,    3622,     0,       0,       0,         0,       0),     -- Apella Doublet set (robe)

-- S80 Dynasty Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (65,  9417,  9421, 9422, 9423, 9424, '3006-1;3348-1;',               9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Breast Plate - Shield Master
  (66,  9418,  9421, 9422, 9423, 9424, '3006-1;3349-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breast Plate - Weapon Master
  (67,  9419,  9421, 9422, 9423, 9424, '3006-1;3350-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breast Plate - Force Master
  (68,  9420,  9421, 9422, 9423, 9424, '3006-1;3351-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breast Plate - Bard
  (69,  9426,  9428, 9429, 9430, 9431, '3006-1;3352-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Dagger Master
  (70,  9427,  9428, 9429, 9430, 9431, '3006-1;3353-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Bow Master
  (71,  9433,  9437, 9438, 9439, 9440, '3006-1;3354-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - Healer
  (72,  9434,  9437, 9438, 9439, 9440, '3006-1;3355-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - Enchanter
  (73,  9435,  9437, 9438, 9439, 9440, '3006-1;3356-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - Summoner
  (74,  9436,  9437, 9438, 9439, 9440, '3006-1;3357-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - Human Wizard

-- Clan Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (75,  9821,  0,    9820, 9822, 9823, '3006-1;3605-1;',               0,    0,    3611,     0,       0,       0,         0,       0),     -- Shadow Item: Clan oath Armor set (heavy)
  (76,  9824,  0,    9820, 9825, 9826, '3006-1;3606-1;',               0,    0,    3612,     0,       0,       0,         0,       0),     -- Shadow Item: Clan Oath Brigandine set (light)
  (77,  9827,  0,    9820, 9828, 9829, '3006-1;3607-1;',               0,    0,    3613,     0,       0,       0,         0,       0),     -- Shadow Item: Clan Oath Aketon set (robe)
  (78,  9831,  0,    9830, 9832, 9833, '3006-1;3608-2;',               0,    0,    3620,     0,       0,       0,         0,       0),     -- Improved Apella plate armor set (heavy)
  (79,  9834,  0,    9830, 9835, 9836, '3006-1;3609-2;',               0,    0,    3621,     0,       0,       0,         0,       0),     -- Improved Apella Brigandine set (light)
  (80,  9837,  0,    9830, 9838, 9839, '3006-1;3610-2;',               0,    0,    3622,     0,       0,       0,         0,       0),     -- Improved Apella Doublet set (robe)

-- Special Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (81,  9670,  9671, 9669,0,    0,    '3006-1;8277-1;',                0,    0,    0,        0,       0,       0,         0,       0),     -- Natives Set (bestows Native transform skill when all are worn together)

-- S80 Dynasty Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (82,  9416,  9421, 9422, 9423, 9424, '3006-1;3412-1;',               9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Breast Plate
  (83,  9425,  9428, 9429, 9430, 9431, '3006-1;3413-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor
  (84,  9432,  9437, 9438, 9439, 9440, '3006-1;3416-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic
  (85,  10126, 9428, 9429, 9430, 9431, '3006-1;3414-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Force Master
  (86,  10127, 9428, 9429, 9430, 9431, '3006-1;3415-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Weapon Master
  (87,  10168, 9428, 9429, 9430, 9431, '3006-1;3355-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Enchanter
  (88,  10214, 9428, 9429, 9430, 9431, '3006-1;3420-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - Summoner
  (89,  10228, 9421, 9422, 9423, 9424, '3006-1;3636-1;',               9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Platinum Plate - Shield Master
  (90,  10229, 9421, 9422, 9423, 9424, '3006-1;3637-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Plate - Weapon Master
  (91,  10230, 9421, 9422, 9423, 9424, '3006-1;3639-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Plate - Force Master
  (92,  10231, 9421, 9422, 9423, 9424, '3006-1;3638-1;',               0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Plate - Bard
  (93,  10233, 9428, 9429, 9430, 9431, '3006-1;3640-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Mail Dagger Master
  (94,  10234, 9428, 9429, 9430, 9431, '3006-1;3641-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Mail Bow Master
  (95,  10487, 9428, 9429, 9430, 9431, '3006-1;3642-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jeweled Leather Armor Force Master
  (96,  10488, 9428, 9429, 9430, 9431, '3006-1;3643-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jeweled Leather Armor Weapon Master
  (97,  10489, 9428, 9429, 9430, 9431, '3006-1;3646-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jeweled Leather Armor Enchanter
  (98,  10490, 9428, 9429, 9430, 9431, '3006-1;3644-1;',               0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jeweled Leather Armor Summoner
  (99,  10236, 9437, 9438, 9439, 9440, '3006-1;3645-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic Healer
  (100, 10237, 9437, 9438, 9439, 9440, '3006-1;3646-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic Enchanter
  (101, 10238, 9437, 9438, 9439, 9440, '3006-1;3647-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic Summoner
  (102, 10239, 9437, 9438, 9439, 9440, '3006-1;3648-1;',               0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic Human Wizard

-- A GRADE PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill		               shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (103, 10793, 0,    2418, 5771, 5783, '3006-1;8193-1;3659-1;3662-1;', 2498, 3551, 3620,     0,       11481,   11472,     11477,   11480), -- Armor of Nightmare - PvP
  (104, 10794, 0,    2419, 5774, 5786, '3006-1;8194-1;3659-1;3662-1;', 0,    0,    3620,     0,       11456,   11448,     11453,   0),     -- Majestic Plate Armor - PvP
  (105, 10795, 0,    2418, 5772, 5784, '3006-1;8195-1;3663-1;',        0,    0,    3621,     0,       12992,   11473,     11478,   0),     -- Leather Armor of Nightmare - PvP
  (106, 10796, 0,    2419, 5775, 5787, '3006-1;8196-1;3663-1;',        0,    0,    3621,     0,       12990,   11449,     11454,   0),     -- Majestic Leather Mail - PvP
  (107, 10797, 0,    2418, 5773, 5785, '3006-1;8197-1;3660-1;',        0,    0,    3622,     0,       12993,   11474,     11479,   0),     -- Robe of Nightmare - PvP
  (108, 10798, 0,    2419, 5776, 5788, '3006-1;8198-1;3660-1;',        0,    0,    3622,     0,       12991,   11450,     11455,   0),     -- Majestic Robe - PvP

-- S GRADE PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (109, 10799, 6374, 6378, 6375, 6376, '3006-1;8199-1;3659-1;3662-1;', 6377, 3554, 3623,     11505,   11509,   11506,     11507,   11508), -- Imperial Crusader Breastplate - PvP
  (110, 10800, 0,    6382, 6380, 6381, '3006-1;8200-1;3663-1;',        0,    0,    3624,     0,       11486,   11483,     11484,   0),     -- Draconic Leather Armor - PvP
  (111, 10801, 0,    6386, 6384, 6385, '3006-1;8201-1;3660-1;',        0,    0,    3625,     0,       11490,   11487,     11489,   0),     -- Major Arcana Robe - PvP

-- S80 Dynasty PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (112, 10802, 9421, 9422, 9423, 9424, '3006-1;8202-1;3659-1;3662-1;', 9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Breastplate - PvP
  (113, 10803, 9421, 9422, 9423, 9424, '3006-1;8203-1;3659-1;3662-1;', 9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Breastplate - PvP Shield Master
  (114, 10804, 9421, 9422, 9423, 9424, '3006-1;8204-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breastplate - PvP Weapon Master
  (115, 10805, 9421, 9422, 9423, 9424, '3006-1;8205-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breastplate - PvP Force Master
  (116, 10806, 9421, 9422, 9423, 9424, '3006-1;8206-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Breastplate - PvP Bard
  (117, 10807, 9428, 9429, 9430, 9431, '3006-1;8207-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP
  (118, 10808, 9428, 9429, 9430, 9431, '3006-1;8208-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Dagger Master
  (119, 10809, 9428, 9429, 9430, 9431, '3006-1;8209-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Bow Master
  (120, 10810, 9437, 9438, 9439, 9440, '3006-1;8210-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - PvP
  (121, 10811, 9437, 9438, 9439, 9440, '3006-1;8211-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - PvP Healer
  (122, 10812, 9437, 9438, 9439, 9440, '3006-1;8212-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - PvP Enchanter
  (123, 10813, 9437, 9438, 9439, 9440, '3006-1;8213-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - PvP Summoner
  (124, 10814, 9437, 9438, 9439, 9440, '3006-1;8214-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Tunic - PvP Wizard
  (125, 10815, 9428, 9429, 9430, 9431, '3006-1;8215-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Force Master
  (126, 10816, 9428, 9429, 9430, 9431, '3006-1;8216-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Weapon Master
  (127, 10817, 9428, 9429, 9430, 9431, '3006-1;8217-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Enchanter
  (128, 10818, 9428, 9429, 9430, 9431, '3006-1;8218-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Leather Armor - PvP Summoner
  (129, 10820, 9421, 9422, 9423, 9424, '3006-1;8219-1;3659-1;3662-1;', 9441, 3417, 3623,     11512,   11557,   11513,     11526,   11532), -- Dynasty Platinum Breastplate - PvP Shield Master
  (130, 10821, 9421, 9422, 9423, 9424, '3006-1;8220-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Breastplate - PvP Weapon Master
  (131, 10822, 9421, 9422, 9423, 9424, '3006-1;8222-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Breastplate - PvP Force Master
  (132, 10823, 9421, 9422, 9423, 9424, '3006-1;8221-1;3659-1;3662-1;', 0,    0,    3623,     11512,   11557,   11513,     11526,   0),     -- Dynasty Platinum Breastplate - PvP Bard
  (133, 10825, 9428, 9429, 9430, 9431, '3006-1;8223-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP Dagger Master
  (134, 10826, 9428, 9429, 9430, 9431, '3006-1;8224-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP Bow Master
  (135, 10832, 9428, 9429, 9430, 9431, '3006-1;8225-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP	Force Master
  (136, 10833, 9428, 9429, 9430, 9431, '3006-1;8226-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP	Weapon Master
  (137, 10834, 9428, 9429, 9430, 9431, '3006-1;8228-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP	Enchanter
  (138, 10835, 9428, 9429, 9430, 9431, '3006-1;8227-1;3663-1;',        0,    0,    3624,     11516,   11525,   11515,     11524,   0),     -- Dynasty Jewel Leather Armor - PvP	Summoner
  (139, 10828, 9437, 9438, 9439, 9440, '3006-1;8229-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic - PvP Healer
  (140, 10829, 9437, 9438, 9439, 9440, '3006-1;8230-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic - PvP Enchanter
  (141, 10830, 9437, 9438, 9439, 9440, '3006-1;8231-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic - PvP Summoner
  (142, 10831, 9437, 9438, 9439, 9440, '3006-1;8232-1;3660-1;',        0,    0,    3625,     11558,   11539,   11514,     11533,   0),     -- Dynasty Silver Satin Tunic - PvP Wizard

-- S84 Vesper Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (143, 13432, 13438,13137,13439,13440,'3006-1;8283-1;',               13471,8494, 3623,     16312,   16306,   16315,     16318,   16321), -- Vesper Breastplate
  (144, 13433, 13441,13138,13442,13443,'3006-1;8285-1;',               0,    0,    3624,     16313,   16307,   16316,     16319,   0),     -- Vesper Leather Breastplate
  (145, 13434, 13444,13139,13445,13446,'3006-1;8287-1;',               0,    0,    3625,     16314,   16308,   16317,     16320,   0),     -- Vesper Tunic
  (146, 13435, 13448,13140,13449,13450,'3006-1;8284-1;',               13471,8494, 3623,     16843,   16837,   16846,     16849,   16321), -- Vesper Noble Breastplate
  (147, 13436, 13451,13141,13452,13453,'3006-1;8286-1;',               0,    0,    3624,     16844,   16838,   16847,     16850,   0),     -- Vesper Noble Leather Breastplate
  (148, 13437, 13454,13142,13455,13456,'3006-1;8288-1;',               0,    0,    3625,     16845,   16839,   16848,     16851,   0),     -- Vesper Noble Tunic

-- S84 Vesper PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (149, 14520, 13438,13137,13439,13440,'3006-1;8301-1;3659-1;3662-1;', 13471,8494, 3623,     16312,   16306,   16315,     16318,   16321), -- Vesper Breastplate {PvP}
  (150, 14521, 13441,13138,13442,13443,'3006-1;8303-1;3663-1;',        0,    0,    3624,     16313,   16307,   16316,     16319,   0),     -- Vesper Leather Breastplate {PvP}
  (151, 14522, 13444,13139,13445,13446,'3006-1;8305-1;3660-1;',        0,    0,    3625,     16314,   16308,   16317,     16320,   0),     -- Vesper Tunic {PvP}
  (152, 14523, 13448,13140,13449,13450,'3006-1;8302-1;3659-1;3662-1;', 13471,8494, 3623,     16843,   16837,   16846,     16849,   16321), -- Vesper Noble Breastplate {PvP}
  (153, 14524, 13451,13141,13452,13453,'3006-1;8304-1;3663-1;',        0,    0,    3624,     16844,   16838,   16847,     16850,   0),     -- Vesper Noble Leather Breastplate {PvP}
  (154, 14525, 13454,13142,13455,13456,'3006-1;8306-1;3660-1;',        0,    0,    3625,     16845,   16839,   16848,     16851,   0),     -- Vesper Noble Tunic {PvP}

-- Clan Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (155, 14583, 0,    14582,14584,14585,'3006-1;3608-2;',               0,    0,    3620,     0,       0,       0,         0,       0),     -- Apella Combat Armor set (heavy)
  (156, 14586, 0,    14582,14587,14588,'3006-1;3609-2;',               0,    0,    3621,     0,       0,       0,         0,       0),     -- Apella Combat Clothes set (light)
  (157, 14589, 0,    14582,14590,14591,'3006-1;3610-2;',               0,    0,    3622,     0,       0,       0,         0,       0),     -- Apella Combat Overcoat set (robe)

-- Friendship Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (158, 15092, 0,    15093,15094,15095,'3006-1;3535-1;',               0,    0,    0,        0,       14979,   14980,     14981,   0),     -- Dark Crystal robe set
  (159, 14978, 0,    15093,15094,15095,'3006-1;3535-1;',               0,    0,    0,        0,       14979,   14980,     14981,   0),     -- Dark Crystal robe set
  (160, 15097, 0,    14984,14985,14986,'3006-1;3523-1;',               0,    0,    0,        0,       15098,   15099,     15100,   0),     -- Avadon robe set
  (161, 14983, 0,    14984,14985,14986,'3006-1;3523-1;',               0,    0,    0,        0,       15098,   15099,     15100,   0),     -- Avadon robe set
  (162, 15102, 15103,0,    15104,0,    '3006-1;3510-1;',               0,    0,    0,        14989,   0,       14990,     0,       0),     -- Karmian robe set
  (163, 14988, 15103,0,    15104,0,    '3006-1;3510-1;',               0,    0,    0,        14989,   0,       14990,     0,       0),     -- Karmian robe set
  (164, 15106, 15107,0,    15108,0,    '3006-1;3507-1;',               0,    0,    0,        14993,   0,       14994,     0,       0),     -- Mithril Tunic
  (165, 14992, 15107,0,    15108,0,    '3006-1;3507-1;',               0,    0,    0,        14993,   0,       14994,     0,       0),     -- Mithril Tunic
  (166, 15110, 15111,15093,15112,15113,'3006-1;3532-1;',               0,    0,    0,        14997,   14979,   14998,     14999,   0),     -- Dark Crystal leather set
  (167, 14996, 15111,15093,15112,15113,'3006-1;3532-1;',               0,    0,    0,        14997,   14979,   14998,     14999,   0),     -- Dark Crystal leather set
  (168, 15114, 0,    15115,15117,15119,'3006-1;3527-1;',               0,    0,    0,        0,       15001,   15003,     15005,   0),     -- Doom leather set
  (169, 15000, 0,    15115,15117,15119,'3006-1;3527-1;',               0,    0,    0,        0,       15001,   15003,     15005,   0),     -- Doom leather set
  (170, 15122, 15123,0,    0,    15124,'3006-1;3511-1;',               0,    0,    0,        15009,   0,       0,         15010,   0),     -- Plated leather set
  (171, 15008, 15123,0,    0,    15124,'3006-1;3511-1;',               0,    0,    0,        15009,   0,       0,         15010,   0),     -- Plated leather set
  (172, 15131, 15132,0,    0,    15133,'3006-1;3505-1;',               0,    0,    0,        15018,   0,       0,         15019,   0),     -- Manticore skin set
  (173, 15017, 15132,0,    0,    15133,'3006-1;3505-1;',               0,    0,    0,        15018,   0,       0,         15019,   0),     -- Manticore skin set
  (174, 15141, 0,    15142,15143,15144,'3006-1;3536-1;',               15145,3551, 0,        0,       15028,   15029,     15030,   15031), -- Nightmare heavy set
  (175, 15027, 0,    15142,15143,15144,'3006-1;3536-1;',               15145,3551, 0,        0,       15028,   15029,     15030,   15031), -- Nightmare heavy set
  (176, 15120, 0,    15001,15002,15004,'3006-1;3525-1;',               15007,3549, 0,        0,       15115,   15116,     15118,   15121), -- Doom plate heavy set
  (177, 15006, 0,    15001,15002,15004,'3006-1;3525-1;',               15007,3549, 0,        0,       15115,   15116,     15118,   15121), -- Doom plate heavy set
  (178, 15127, 0,    15012,0,    0,    '3006-1;3516-1;',               15016,3547, 0,        0,       15126,   0,         0,       15130), -- Full Plate Armor set
  (179, 15013, 0,    15012,0,    0,    '3006-1;3516-1;',               15016,3547, 0,        0,       15126,   0,         0,       15130), -- Full Plate Armor set
  (180, 15135, 15022,15023,0,    0,    '3006-1;3506-1;',               15026,3544, 0,        15136,   15137,   0,         0,       15140), -- Brigandine Armor set
  (181, 15021, 15022,15023,0,    0,    '3006-1;3506-1;',               15026,3544, 0,        15136,   15137,   0,         0,       15140), -- Brigandine Armor set

-- S80 Moirai Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (182, 15609, 15612,15606,15615,15618,'3006-1;8397-1;',               15621,8493, 3623,     16295,   16289,   16298,     16301,   16304), -- Moirai Cuirass
  (183, 15610, 15613,15607,15616,15619,'3006-1;8398-1;',               0,    0,    3624,     16296,   16290,   16299,     16302,   0),     -- Moirai Houberk
  (184, 15611, 15614,15608,15617,15620,'3006-1;8399-1;',               0,    0,    3625,     16297,   16291,   16300,     16303,   0),     -- Moirai Tunic

-- S84 Vorpal Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (185, 15592, 15595,15589,15598,15601,'3006-1;8400-1;',               15604,8495, 8461,     0,       0,       0,         0,       0),     -- Vorpal Cuirass
  (186, 15593, 15596,15590,15599,15602,'3006-1;8401-1;',               0,    0,    8462,     0,       0,       0,         0,       0),     -- Vorpal Houberk
  (187, 15594, 15597,15591,15600,15603,'3006-1;8402-1;',               0,    0,    8463,     0,       0,       0,         0,       0),     -- Vorpal Tunic

-- S84 Elegia Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (188, 15575, 15578,15572,15581,15584,'3006-1;8403-1;',               15587,8496, 8461,     0,       0,       0,         0,       0),     -- Elegia Cuirass
  (189, 15576, 15579,15573,15582,15585,'3006-1;8404-1;',               0,    0,    8462,     0,       0,       0,         0,       0),     -- Elegia Houberk
  (190, 15577, 15580,15574,15583,15586,'3006-1;8405-1;',               0,    0,    8463,     0,       0,       0,         0,       0),     -- Elegia Tunic

-- S84 Elegia PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (191, 16168, 15578,15572,15581,15584,'3006-1;8412-1;3659-1;3662-1;', 15587,8496, 8461,     0,       0,       0,         0,       0),     -- Elegia Cuirass {PvP}
  (192, 16169, 15579,15573,15582,15585,'3006-1;8413-1;3663-1;',        0,    0,    8462,     0,       0,       0,         0,       0),     -- Elegia Hourberk {PvP}
  (193, 16170, 15580,15574,15583,15586,'3006-1;8414-1;3660-1;',        0,    0,    8463,     0,       0,       0,         0,       0),     -- Elegia Tunic {PvP}

-- S84 Vorpal PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (194, 16171, 15595,15589,15598,15601,'3006-1;8409-1;3659-1;3662-1;', 15604,8495, 8461,     0,       0,       0,         0,       0),     -- Vorpal Cuirass {PvP}
  (195, 16172, 15596,15590,15599,15602,'3006-1;8410-1;3663-1;',        0,    0,    8462,     0,       0,       0,         0,       0),     -- Vorpal Houberk {PvP}
  (196, 16173, 15597,15591,15600,15603,'3006-1;8411-1;3660-1;',        0,    0,    8463,     0,       0,       0,         0,       0),     -- Vorpal Tunic {PvP}

-- S80 Moirai PVP Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (197, 16174, 15612,15606,15615,15618,'3006-1;8406-1;3659-1;3662-1;', 15621,8493, 3623,     16295,   16289,   16298,     16301,   16304), -- Moirai Cuirass {PvP}
  (198, 16175, 15613,15607,15616,15619,'3006-1;8407-1;3663-1;',        0,    0,    3624,     16296,   16290,   16299,     16302,   0),     -- Moirai Houberk {PvP}
  (199, 16176, 15614,15608,15617,15620,'3006-1;8408-1;3660-1;',        0,    0,    3625,     16297,   16291,   16300,     16303,   0),     -- Moirai Tunic {PvP}

-- Friendship Armor Sets
-- id   chest  legs  head  glov  feet  skill                           shld  shsk  enchant6  mw_legs  mw_head  mw_gloves  mw_feet  mw_shield
  (200, 16866, 0,    16867,16868,16869,'3006-1;3535-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Dark Crystal robe set
  (201, 16871, 0,    16872,16873,16874,'3006-1;3523-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Avadon robe set
  (202, 16876, 16877,0,    16878,0,    '3006-1;3510-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Karmian robe set
  (203, 16880, 16881,0,    16882,0,    '3006-1;3507-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Mithril Tunic
  (204, 16884, 16885,16867,16886,16887,'3006-1;3532-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Dark Crystal leather set
  (205, 16888, 0,    16889,16891,16893,'3006-1;3527-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Doom leather set
  (206, 16896, 16897,0,    0,    16898,'3006-1;3511-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Plated leather set
  (207, 16905, 16906,0,    0,    16907,'3006-1;3505-1;',               0,    0,    0,        0,       0,       0,         0,       0),     -- Manticore skin set
  (208, 16915, 0,    16916,16917,16918,'3006-1;3536-1;',               16919,3551, 0,        0,       0,       0,         0,       0),     -- Nightmare heavy set
  (209, 16894, 0,    16889,16890,16892,'3006-1;3525-1;',               16895,3549, 0,        0,       0,       0,         0,       0),     -- Doom plate heavy set
  (210, 16901, 0,    16900,0,    0,    '3006-1;3516-1;',               16904,3547, 0,        0,       0,       0,         0,       0),     -- Full Plate Armor set
  (211, 16909, 16910,16911,0,    0,    '3006-1;3506-1;',               16914,3544, 0,        0,       0,       0,         0,       0);     -- Brigandine Armor set