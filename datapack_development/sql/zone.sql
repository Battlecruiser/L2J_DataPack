-- ---------------------------
-- Table structure for zone
-- ---------------------------
CREATE TABLE zone (
  castleId INT NOT NULL default 0,
  id INT NOT NULL default 0,
  name varchar(25) NOT NULL,
  x1 INT NOT NULL default 0,
  y1 INT NOT NULL default 0,
  x2 INT NOT NULL default 0,
  y2 INT NOT NULL default 0,
  PRIMARY KEY  (name),
  KEY id (castleId, id)
);

Insert Into zone Values (0, id, 'Collusieum 1', 147121, 46232, 148016, 47213);
Insert Into zone Values (0, id, 'Collusieum 2', 150945, 47214, 151895, 46225);
Insert Into zone Values (4, id, 'DE Village', 6063, 19664, 17248, 14019);
Insert Into zone Values (2, id, 'Dion Castle Town', 15300, 141609, 21570, 147635);
Insert Into zone Values (0, id, 'Dwarven Village', 117395, -176766, 114650, -184347);
Insert Into zone Values (4, id, 'Elven Village', 48294, 52995, 43193, 45911);
Insert Into zone Values (2, id, 'Floran Village', 0, 0, 0, 0);
Insert Into zone Values (3, id, 'Giran Castle Town', 76995, 141424, 90565, 153614);
Insert Into zone Values (1, id, 'Gludin Village', -84892, 149075, -76820, 156125);
Insert Into zone Values (1, id, 'Gludio Castle Town', -11853, 126610, -16652, 121003);
Insert Into zone Values (6, id, 'Heine/K Spawn', 103598, 216010, 118991, 225905);
Insert Into zone Values (5, id, 'Hunter Village', 121308, 73941, 114667, 80383);
Insert Into zone Values (4, id, 'Ivory Tower', 0, 0, 0, 0);
Insert Into zone Values (0, id, 'Orc Village', -42078, -109785, -47648, -117366);
Insert Into zone Values (1, id, 'Talking Island', -87312, 240096, -81129, 246345);
Insert Into zone Values (5, id, 'Town of Aden', 142312, 32317, 152163, 19708);
Insert Into zone Values (4, id, 'Town of Oren', 76696, 57199, 84511, 50120);
