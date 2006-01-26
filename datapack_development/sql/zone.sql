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
insert into zone values (2, 'Arena', 'Gudin Arena', -88410, 142728, -87421, 142715, -3648, 0);
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

insert into zone values (1, 'Castle Defender Spawn', 'Gludio', -18105, 110303, 0, 0, -2146, 0);
insert into zone values (2, 'Castle Defender Spawn', 'Dion', 22080, 159450, 0, 0, -2441, 0);
insert into zone values (3, 'Castle Defender Spawn', 'Giran', 115621, 145097, 0, 0, -2214, 0);
insert into zone values (4, 'Castle Defender Spawn', 'Oren', 81707, 37208, 0, 0, -1941, 0);
insert into zone values (5, 'Castle Defender Spawn', 'Aden', 147456, 6048, 0, 0, 253, 0);
insert into zone values (6, 'Castle Defender Spawn', 'Innadril', 116025, 248229, 0, 0, -536, 0);

insert into zone values (1, 'Clan Hall', 'Gludio 1', -12954, 123785, -12291, 124264, -3117, 1);
insert into zone values (2, 'Clan Hall', 'Gludio 2', -16405, 123275, -15551, 123886, -3117, 1);
insert into zone values (3, 'Clan Hall', 'Gludio 3', -14172, 125045, -13661, 125830, -3143, 1);
insert into zone values (4, 'Clan Hall', 'Gludio 4', -15137, 125284, -14705, 125845, -3143, 1);

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

insert into zone values (1, 'Water', 'ascetics necropolis', -56190, 78595, -55175, 79600, -3061, 0);