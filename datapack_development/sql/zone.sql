-- ---------------------------
-- Table structure for zone
-- ---------------------------
DROP TABLE IF EXISTS zone;
CREATE TABLE zone (
  id INT NOT NULL default 0,
  type varchar(25) NOT NULL,
  name varchar(40) NOT NULL,
  x1 INT NOT NULL default 0,
  y1 INT NOT NULL default 0,
  x2 INT NOT NULL default 0,
  y2 INT NOT NULL default 0,
  z INT NOT NULL default 0,
  taxById INT NOT NULL default 0
);

insert into zone values (1, 'Arena', 'Giran Arena', 72498, 142271, 73495, 143255, -3774, 0);
insert into zone values (2, 'Arena', 'Gludin Arena', -88410, 142728, -87421, 141730, -3633, 0);
insert into zone values (3, 'Arena', 'Collusieum', 148014, 45304, 150976, 48020, -3410, 0);
insert into zone values (4, 'Arena', 'Monster Track', 11955, 183017, 12937, 184008, -3565, 0);

insert into zone values (1, 'Arena Spawn', 'Giran Arena', 73890, 142656, 0, 0, -3778, 0);
insert into zone values (2, 'Arena Spawn', 'Gludin Arena', -86979, 142402, 0, 0, -3643, 0);
insert into zone values (3, 'Arena Spawn', 'Collusieum', 147451, 46728, 0, 0, -3410, 0);
insert into zone values (4, 'Arena Spawn', 'Monster Track', 12312, 182752, 0, 0, -3558, 0);

insert into zone values (1, 'Castle Area', 'Gludio', -22900, 104000, -14567, 116513, 0, 5);
insert into zone values (2, 'Castle Area', 'Dion', 18438, 152343, 25757, 164097, 0, 5);
insert into zone values (3, 'Castle Area', 'Giran', 105737, 140128, 121331, 149842, 0, 5);
insert into zone values (4, 'Castle Area', 'Oren', 72876, 32336, 87556, 40457, 0, 5);
insert into zone values (5, 'Castle Area', 'Aden', 134790, -2552, 154760, 20850, 0, 0);
insert into zone values (6, 'Castle Area', 'Innadril', 111975, 241396, 120720, 253425, 0, 5);
insert into zone values (7, 'Castle Area', 'Goddard', 0, 0, 0, 0, 0, 5);

insert into zone values (1, 'Castle HQ', 'Gludio', -20400, 106800, -15700, 113750, 0, 5);
insert into zone values (2, 'Castle HQ', 'Dion', 19650, 163000, 24350, 155950, 0, 5);
insert into zone values (3, 'Castle HQ', 'Giran', 119200, 142750, 112200, 147450, 0, 5);
insert into zone values (4, 'Castle HQ', 'Oren', 85300, 34900, 78100, 39600, 0, 5);
insert into zone values (5, 'Castle HQ', 'Aden', 144600, 550, 150300, 8550, 0, 0);
insert into zone values (6, 'Castle HQ', 'Innadril', 111975, 241396, 120720, 253425, 0, 5);
insert into zone values (7, 'Castle HQ', 'Goddard', 0, 0, 0, 0, 0, 5);

insert into zone values (1, 'Castle Defender Spawn', 'Gludio', -18105, 110303, 0, 0, -2146, 0);
insert into zone values (2, 'Castle Defender Spawn', 'Dion', 22080, 159450, 0, 0, -2441, 0);
insert into zone values (3, 'Castle Defender Spawn', 'Giran', 115621, 145097, 0, 0, -2214, 0);
insert into zone values (4, 'Castle Defender Spawn', 'Oren', 81707, 37208, 0, 0, -1941, 0);
insert into zone values (5, 'Castle Defender Spawn', 'Aden', 147456, 6048, 0, 0, 253, 0);
insert into zone values (6, 'Castle Defender Spawn', 'Innadril', 116025, 248229, 0, 0, -536, 0);

insert into zone values (1, 'Clan Hall', 'Gludio 1', -16400, 123275, -15551, 123850, -3117, 1);
insert into zone values (2, 'Clan Hall', 'Gludio 2', -15100, 125350, -14800, 125800, -3143, 1);
insert into zone values (3, 'Clan Hall', 'Gludio 3', -14050, 125050, -13700, 125700, -3143, 1);
insert into zone values (4, 'Clan Hall', 'Gludio 4', -12950, 123900, -12300, 124250, -3117, 1);
insert into zone values (5, 'Clan Hall', 'Gludin 1', -84700, 151550, -84250, 152350, -3130, 1);
insert into zone values (5, 'Clan Hall', 'Gludin 1', -84350, 151950, -83800, 152350, -3130, 1);
insert into zone values (6, 'Clan Hall', 'Gludin 2', -84400, 153050, -83950, 154050, -3166, 1);
insert into zone values (6, 'Clan Hall', 'Gludin 2', -84200, 153050, -83550, 153600, -3166, 1);
insert into zone values (7, 'Clan Hall', 'Gludin 3', -84500, 154900, -83950, 155700, -3158, 1);
insert into zone values (7, 'Clan Hall', 'Gludin 3', -84100, 155300, -83500, 155700, -3158, 1);
insert into zone values (8, 'Clan Hall', 'Gludin 4', -79700, 149400, -79250, 150300, -3061, 1);
insert into zone values (8, 'Clan Hall', 'Gludin 4', -80100, 149400, -79500, 149850, -3061, 1);
insert into zone values (9, 'Clan Hall', 'Gludin 5', -79700, 151350, -79300, 152250, -3036, 1);
insert into zone values (9, 'Clan Hall', 'Gludin 5', -80100, 151800, -79500, 152250, -3036, 1);
insert into zone values (10, 'Clan Hall', 'Dion 1', 17400, 144800, 18000, 145350, -3043, 1);
insert into zone values (11, 'Clan Hall', 'Dion 2', 18850, 143600, 18600, 143100, -3017, 1);
insert into zone values (12, 'Clan Hall', 'Dion 3', 19950, 146000, 20400, 146300, -3118, 1);
insert into zone values (13, 'Clan Hall', 'Giran 1', 80780, 151063, 81156, 152111, -3518, 1);
insert into zone values (14, 'Clan Hall', 'Giran 2', 82288, 152437, 81912, 151393, -3543, 1);
insert into zone values (15, 'Clan Hall', 'Giran 3', 78077, 148285, 79119, 147911, -3608, 1);
insert into zone values (16, 'Clan Hall', 'Giran 4', 83205, 144788, 83577, 145837, -3396, 1);
insert into zone values (17, 'Clan Hall', 'Giran 5', 82244, 145860, 81870, 144814, -3517, 1);
insert into zone values (18, 'Clan Hall', 'Aden 1', 143712, 27490, 144222, 26713, -2255, 1);
insert into zone values (19, 'Clan Hall', 'Aden 2', 143720, 28607, 144262, 27789, -2247, 1);
insert into zone values (20, 'Clan Hall', 'Aden 3', 151025, 26140, 150512, 26916, -2249, 1);
insert into zone values (21, 'Clan Hall', 'Aden 4', 150396, 24062, 150940, 23243, -2120, 1);
insert into zone values (22, 'Clan Hall', 'Aden 5', 149362, 22756, 148855, 23536, -2132, 1);
insert into zone values (23, 'Clan Hall', 'Aden 6', 145999, 24932, 145455, 25753, -2121, 1);
insert into zone values (24, 'Clan Hall', 'Goddard 1', 149717, -55824, 149063, -55350, -2783, 1);
insert into zone values (25, 'Clan Hall', 'Goddard 2', 148479, -56473, 148479, -57275, -2773, 1);
insert into zone values (26, 'Clan Hall', 'Goddard 3', 147238, -56636, 146564, -57078, -2783, 1);
insert into zone values (27, 'Clan Hall', 'Goddard 4', 146399, -55682, 145652, -55386, -2773, 1);
insert into zone values (28, 'Clan Hall', 'Bandits Stronghold', 80738, -15914, 79627, -15054, -1810, 1);
insert into zone values (29, 'Clan Hall', 'Partisan Hideaway', 43151, 108377, 43648, 109399, -1981, 1);
insert into zone values (30, 'Clan Hall', 'Hot Springs Guild House', 141414, -124508, 140590, -124706, -1896, 1);

insert into zone values (1, 'Peace', 'Giran Arena', 72249, 142018, 72498, 143510, -3774, 0);
insert into zone values (1, 'Peace', 'Giran Arena', 73495, 142018, 73738, 143510, -3774, 0);
insert into zone values (1, 'Peace', 'Giran Arena', 72498, 142018, 73495, 142271, -3774, 0);
insert into zone values (1, 'Peace', 'Giran Arena', 72498, 143255, 73495, 143510, -3774, 0);
insert into zone values (2, 'Peace', 'Gudin Arena', -88654, 141479, -88410, 142960, -3648, 0);
insert into zone values (2, 'Peace', 'Gudin Arena', -87421, 141479, -87172, 142960, -3648, 0);
insert into zone values (2, 'Peace', 'Gudin Arena', -88410, 141479, -87421, 141728, -3648, 0);
insert into zone values (2, 'Peace', 'Gudin Arena', -88410, 142715, -87421, 142960, -3648, 0);
insert into zone values (3, 'Peace', 'Collusieum', 147117, 46230, 148014, 47217, -3410, 0);
insert into zone values (3, 'Peace', 'Collusieum', 147771, 45304, 148014, 46230, -3410, 0);
insert into zone values (3, 'Peace', 'Collusieum', 147771, 47217, 148014, 48020, -3410, 0);
insert into zone values (3, 'Peace', 'Collusieum', 150976, 46228, 151885, 47217, -3410, 0);
insert into zone values (3, 'Peace', 'Collusieum', 150976, 45304, 151218, 46228, -3410, 0);
insert into zone values (3, 'Peace', 'Collusieum', 150976, 47217, 151218, 48020, -3410, 0);
insert into zone values (4, 'Peace', 'Monster Track', 11703, 181289, 14574, 183017, -3564, 0);
insert into zone values (4, 'Peace', 'Monster Track', 11703, 183017, 11955, 184260, -3564, 0);
insert into zone values (4, 'Peace', 'Monster Track', 11955, 184008, 12937, 184260, -3564, 0);
insert into zone values (4, 'Peace', 'Monster Track', 12937, 183017, 13192, 184260, -3564, 0);

insert into zone values (1, 'Siege Battlefield', 'Gludio', -22900, 104000, -14567, 116513, 0, 0);
insert into zone values (2, 'Siege Battlefield', 'Dion', 18438, 152343, 25757, 164097, 0, 0);
insert into zone values (3, 'Siege Battlefield', 'Giran', 105737, 140128, 121331, 149842, 0, 0);
insert into zone values (4, 'Siege Battlefield', 'Oren', 72876, 32336, 87556, 40457, 0, 0);
insert into zone values (5, 'Siege Battlefield', 'Aden', 134790, -2552, 154760, 20850, 0, 0);
insert into zone values (6, 'Siege Battlefield', 'Innadril', 111975, 241396, 120720, 253425, 0, 0);

insert into zone values (1, 'Town', 'DE Village', 6063, 19664, 17248, 14019, 0, 4);
insert into zone values (2, 'Town', 'Talking Island', -87312, 240096, -81129, 246345, 0, 1);
insert into zone values (3, 'Town', 'Elven Village', 48294, 52995, 43193, 45911, 0, 4);
insert into zone values (4, 'Town', 'Orc Village', -42078, -109785, -47648, -117366, 0, 0);
insert into zone values (5, 'Town', 'Gludin Village', -84892, 149075, -76820, 156125, 0, 1);
insert into zone values (6, 'Town', 'Dwarven Village', 117395, -176766, 114650, -184347, 0, 0);
insert into zone values (7, 'Town', 'Gludio Castle Town', -11853, 126610, -16652, 121003, 0, 1);
insert into zone values (8, 'Town', 'Dion Castle Town', 15300, 141609, 21570, 147635, 0, 2);
insert into zone values (9, 'Town', 'Giran Castle Town', 76995, 141424, 90565, 153614, 0, 3);
insert into zone values (10, 'Town', 'Town of Oren', 76696, 57199, 84511, 50120, 0, 4);
insert into zone values (11, 'Town', 'Hunter Village', 121308, 73941, 114667, 80383, 0, 5);
insert into zone values (12, 'Town', 'Town of Aden', 142312, 32317, 152163, 19708, 0, 5);
insert into zone values (13, 'Town', 'Goddard', 152846, -54289, 143721, -59446, 0, 5);
insert into zone values (14, 'Town', 'Rune Castle Town', 47150, -44815, 32531, -52045, 0, 6);
insert into zone values (15, 'Town', 'Heine', 103598, 216010, 118991, 225905, 0, 6);
insert into zone values (16, 'Town', 'Floran Village', 0, 0, 0, 0, 0, 2);
insert into zone values (17, 'Town', 'Ivory Tower', 0, 0, 0, 0, 0, 4);

insert into zone values (1, 'Town Spawn', 'DE Village', 12181, 16675, 0, 0, -4580, 0);
insert into zone values (2, 'Town Spawn', 'Talking Island', -84176, 243382, 0, 0, -3126, 0);
insert into zone values (3, 'Town Spawn', 'Elven Village', 45525, 48376, 0, 0, -3059, 0);
insert into zone values (4, 'Town Spawn', 'Orc Village', -45232, -113603, 0, 0, -224, 0);
insert into zone values (5, 'Town Spawn', 'Gludin Village', -82856, 150901, 0, 0, -3128, 0);
insert into zone values (6, 'Town Spawn', 'Dwarven Village', 115074, -178115, 0, 0, -880, 0);
insert into zone values (7, 'Town Spawn', 'Gludio Castle Town', -14138, 122042, 0, 0, -2988, 0);
insert into zone values (8, 'Town Spawn', 'Dion Castle Town', 18823, 145048, 0, 0, -3126, 0);
insert into zone values (9, 'Town Spawn', 'Giran Castle Town', 83235, 148497, 0, 0, -3404, 0);
insert into zone values (10, 'Town Spawn', 'Town of Oren', 80853, 54653, 0, 0, -1524, 0);
insert into zone values (11, 'Town Spawn', 'Hunter Village', 117163, 76511, 0, 0, -2712, 0);
insert into zone values (12, 'Town Spawn', 'Town of Aden', 147391, 25967, 0, 0, -2012, 0);
insert into zone values (13, 'Town Spawn', 'Goddard', 147955, -55339, 0, 0, -2734, 0);
insert into zone values (14, 'Town Spawn', 'Rune Castle Town', 43813, -47790, 0, 0, -797, 0);
insert into zone values (15, 'Town Spawn', 'Heine', 111381, 219064, 0, 0, -3543, 0);
insert into zone values (16, 'Town Spawn', 'Floran Village', 17817, 170079, 0, 0, -3530, 0);
insert into zone values (17, 'Town Spawn', 'Ivory Tower', 0, 0, 0, 0, 0, 0);

insert into zone values (1, 'Underground', 'Ascetics Necropolis', 0, 0, 0, 0, -4844, 0);
insert into zone values (2, 'Underground', 'Elven Ruins', 43100, 246500, 49400, 249200, -6614, 0);
insert into zone values (3, 'Underground', 'School of Dark Arts', -49800, 56879, -35311, 43790, -6000, 0);
insert into zone values (4, 'Underground', 'School of Dark Arts', -47150, 41782, -54659, 53065, -6000, 0);
insert into zone values (5, 'Underground', 'School of Dark Arts', -38741, 55152, -55186, 62474, -6000, 0);
insert into zone values (6, 'Underground', 'Ants Nest', 917, 165166, -45452, 201937, -6000, 0);
insert into zone values (7, 'Underground', 'Elven Fortress', 6068, 88790, 36734, 69188, -6000, 0);
insert into zone values (8, 'Underground', 'Ivory Tower', 76563, 27040, 98577, 7238, -6000, 0);
insert into zone values (9, 'Underground', 'Hunter Village', 1234573, 68112, 98542, 92245, -6000, 0);
insert into zone values (10, 'Underground', 'DE Village', 34061, 8905, -7877, 26384, -6000, 0);
insert into zone values (11, 'Underground', 'Ruins of Despair', -1231, 131977, -31364, 160147, -5000, 0);
insert into zone values (12, 'Underground', 'Gludin North Road', -77276, 134858, -69590, 120547, -5000, 0);
insert into zone values (13, 'Underground', '', 38116, 147264, -69590, 120547, -5000, 0);

insert into zone values (1, 'Water', 'Ascetics Necropolis', -56190, 78595, -55175, 79600, -3061, 0);
insert into zone values (1, 'Fishing', 'Giran North Entrance', 82480, 143048, 83321, 141782, 0, 0);
insert into zone values (1, 'Fishing', 'Giran North Entrance', 82109, 142550, 82211, 142149, 0, 0);
insert into zone values (1, 'Fishing', 'Giran North Entrance', 82235, 141780, 82700, 142718, 0, 0);
insert into zone values (1, 'No Landing', 'ToI', 109448, 10233, 118547, 21446, 0, 0);

insert into zone values (1, 'Jail', 'GM Jail', -115600, -250700, -113500, -248200, 0, 0);
insert into zone values (1, 'Monster Derby Track', 'Monster Derby Track', 11600, 181200, 14600, 184500, -3565, 0);

insert into zone values
(1, "Olympiad Stadia", "Stadia 1",-19627, -19712, -22024, -22322, -3026, 0),
(2, "Olympiad Stadia", "Stadia 2",-119100, -223705, -121484, -226316, -3331, 0),
(3, "Olympiad Stadia", "Stadia 3",-103690, -210300, -101325, -207724, -3331, 0),
(4, "Olympiad Stadia", "Stadia 4",-119079, -206078, -121438, -208668, -3331, 0),
(5, "Olympiad Stadia", "Stadia 5",-88700, -226280, -86351, -223722, -3331, 0),
(6, "Olympiad Stadia", "Stadia 6",-80586, -211911, -82939, -214487, -3331, 0),
(7, "Olympiad Stadia", "Stadia 7",-88659, -208652, -86297, -206075, -3331, 0),
(8, "Olympiad Stadia", "Stadia 8",-95000, -219531, -92632, -216950, -3331, 0),
(9, "Olympiad Stadia", "Stadia 9",-75936, -217408, -78306, -220017, -3331, 0),
(10, "Olympiad Stadia", "Stadia 10",-68560, -207718, -70933, -210312, -3331, 0),
(11, "Olympiad Stadia", "Stadia 11",-78008, -202528, -75663, -199943, -3331, 0),
(12, "Olympiad Stadia", "Stadia 12",-108690, -217403, -111072, -220023, -3331, 0),
(13, "Olympiad Stadia", "Stadia 13",-127766, -219555, -125394, -216946, -3331, 0),
(14, "Olympiad Stadia", "Stadia 14",-108428, -199935, -110796, -202541, -3331, 0),
(15, "Olympiad Stadia", "Stadia 15",-88677, -241444, -86294, -238836, -3331, 0),
(16, "Olympiad Stadia", "Stadia 16",-82938, -247261, -80580, -244668, -3331, 0),
(17, "Olympiad Stadia", "Stadia 17",-75930, -250175, -78298, -252779, -3331, 0),
(18, "Olympiad Stadia", "Stadia 18",-70920, -243079, -68547, -240473, -3331, 0),
(19, "Olympiad Stadia", "Stadia 19",-75670, -232712, -78027, -235326, -3331, 0),
(20, "Olympiad Stadia", "Stadia 20",-92632, -249706, -94999, -252316, -3331, 0),
(21, "Olympiad Stadia", "Stadia 21",-87816, -254280, -86332, -256466, -3331, 0),
(22, "Olympiad Stadia", "Stadia 22",-113332, -211881, -115713, -214513, -3331, 0);
