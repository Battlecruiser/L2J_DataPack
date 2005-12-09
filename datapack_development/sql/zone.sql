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
  taxById INT NOT NULL default 0,
  PRIMARY KEY  (id, type)
);

insert into zone values (1, 'Arena', 'Giran Arena', 73482, 142269, 72507, 143247, 0, 0);
insert into zone values (2, 'Arena', 'Gudin Arena', -88412, 142709, -87392, 141696, 0, 0);
insert into zone values (3, 'Arena', 'Collusieum', 148089, 48067, 150938, 45359, 0, 0);
insert into zone values (4, 'Arena', 'Monster Track', 12942, 183021, 11953, 184009, 0, 0);

insert into zone values (1, 'Arena Spawn', 'Giran Arena Spawn', 73890, 142656, 0, 0, -3778, 0);
insert into zone values (2, 'Arena Spawn', 'Gludin Arena Spawn', -86979, 142402, 0, 0, -3643, 0);
insert into zone values (3, 'Arena Spawn', 'Collusieum Spawn', 147451, 46728, 0, 0, -3410, 0);
insert into zone values (4, 'Arena Spawn', 'Monster Track', 12312, 182752, 0, 0, -3558, 0);

insert into zone values (1, 'Castle Area', 'Gludio', -22900, 104000, -14567, 116513, 0, 5);
insert into zone values (2, 'Castle Area', 'Dion', 18438, 152343, 25757, 164097, 0, 5);
insert into zone values (3, 'Castle Area', 'Giran', 105737, 140128, 121331, 149842, 0, 5);
insert into zone values (4, 'Castle Area', 'Oren', 72876, 32336, 87556, 40457, 0, 5);
insert into zone values (5, 'Castle Area', 'Aden', 134790, -2552, 154760, 20850, 0, 0);
insert into zone values (6, 'Castle Area', 'Innadril', 0, 0, 0, 0, 0, 5);

-- insert into zone values (1, 'Castle Area', 'Gludio Castle', -20394, 106804, -15708, 113862, 0, 5);
-- insert into zone values (2, 'Castle Area', 'Dion Castle', 19679, 155959, 24365, 163101, 0, 5);
-- insert into zone values (3, 'Castle Area', 'Giran Castle', 112064, 142760, 119112, 147446, 0, 5);
-- insert into zone values (4, 'Castle Area', 'Oren Castle', 78142, 34908, 85284, 39594, 0, 5);
-- insert into zone values (5, 'Castle Area', 'Aden Castle', 144599, 577, 150295, 8521, 0, 0);

insert into zone values (1, 'Castle Defender Spawn', 'Gludio', -18105, 110303, 0, 0, -2146, 0);
insert into zone values (2, 'Castle Defender Spawn', 'Dion', 22080, 159450, 0, 0, -2441, 0);
insert into zone values (3, 'Castle Defender Spawn', 'Giran', 115621, 145097, 0, 0, -2214, 0);
insert into zone values (4, 'Castle Defender Spawn', 'Oren', 81707, 37208, 0, 0, -1941, 0);
insert into zone values (5, 'Castle Defender Spawn', 'Aden', 147456, 6048, 0, 0, 253, 0);
insert into zone values (6, 'Castle Defender Spawn', 'Innadril', 0, 0, 0, 0, 0, 5);

insert into zone values (1, 'Siege Battlefield', 'Gludio', -22900, 104000, -14567, 116513, 0, 0);
insert into zone values (2, 'Siege Battlefield', 'Dion', 18438, 152343, 25757, 164097, 0, 0);
insert into zone values (3, 'Siege Battlefield', 'Giran', 105737, 140128, 121331, 149842, 0, 0);
insert into zone values (4, 'Siege Battlefield', 'Oren', 72876, 32336, 87556, 40457, 0, 0);
insert into zone values (5, 'Siege Battlefield', 'Aden', 134790, -2552, 154760, 20850, 0, 0);
insert into zone values (6, 'Siege Battlefield', 'Innadril', 0, 0, 0, 0, 0, 0);

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
insert into zone values (13, 'Town', 'Collusieum 1', 147121, 46232, 148016, 47213, 0, 0);
insert into zone values (14, 'Town', 'Collusieum 2', 150945, 47214, 151895, 46225, 0, 0);
insert into zone values (15, 'Town', 'Heine/K Spawn', 103598, 216010, 118991, 225905, 0, 6);
insert into zone values (16, 'Town', 'Floran Village', 0, 0, 0, 0, 0, 2);
insert into zone values (17, 'Town', 'Ivory Tower', 0, 0, 0, 0, 0, 4);
insert into zone values (18, 'Town', 'Monster Track', 14500, 181250, 11700, 182700, 0, 2);

insert into zone values (1, 'Underground', 'ascetics necropolis', 0, 0, 0, 0, -4844, 0);
insert into zone values (1, 'Water', 'ascetics necropolis', -56190, 78595, -55175, 79600, -3061, 0);
insert into zone values (2, 'Underground', 'elven ruins', 43100, 246500, 49400, 249200, -6614, 0);
insert into zone values (3, 'Underground', 'school of dark arts1', -49800, 56879, -35311, 43790, -6000, 0);
insert into zone values (4, 'Underground', 'school of dark arts2', -47150, 41782, -54659, 53065, -6000, 0);
insert into zone values (5, 'Underground', 'school of dark arts3', -38741, 55152, -55186, 62474, -6000, 0);
insert into zone values (6, 'Underground', 'ants nest', 917, 165166, -45452, 201937, -6000, 0);
insert into zone values (7, 'Underground', 'elven fortress', 6068, 88790, 36734, 69188, -6000, 0);
insert into zone values (8, 'Underground', 'ivory tower', 76563, 27040, 98577, 7238, -6000, 0);
insert into zone values (9, 'Underground', 'hunter village', 1234573, 68112, 98542, 92245, -6000, 0);
insert into zone values (10, 'Underground', 'de village', 34061, 8905, -7877, 26384, -6000, 0);

