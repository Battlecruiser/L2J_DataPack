-- ---------------------------
-- Table structure for fort_door
-- ---------------------------
DROP TABLE IF EXISTS fort_door;
CREATE TABLE fort_door (
  fortId INT NOT NULL default 0,
  id INT NOT NULL default 0,
  name varchar(30) NOT NULL,
  x INT NOT NULL default 0,
  y INT NOT NULL default 0,
  z INT NOT NULL default 0,
  range_xmin INT NOT NULL default 0,
  range_ymin INT NOT NULL default 0,
  range_zmin INT NOT NULL default 0,
  range_xmax INT NOT NULL default 0,
  range_ymax INT NOT NULL default 0,
  range_zmax INT NOT NULL default 0,
  hp INT NOT NULL default 0,
  pDef INT NOT NULL default 0,
  mDef INT NOT NULL default 0,
  PRIMARY KEY(id),
  KEY id (fortId)
);

-- Dragonspine doors
INSERT INTO `fort_door` VALUES 
(115,20200001,'Gate_of_fort',12496,93487,-3429,0,0,0,0,0,0,158250,644,518),
(115,20200002,'Gate_of_fort',11466,95581,-3426,0,0,0,0,0,0,158250,644,518),
(115,20200003,'Gate_of_fort',11610,95585,-3426,0,0,0,0,0,0,158250,644,518),
(115,20200004,'Gate_of_fort',10126,94940,-3399,0,0,0,0,0,0,158250,644,518),
(115,20200005,'Gate_of_fort',10124,95088,-3399,0,0,0,0,0,0,158250,644,518),
(115,20200006,'Gate_of_fort',11467,94385,-3426,0,0,0,0,0,0,158250,644,518),
(115,20200007,'Gate_of_fort',11605,94374,-3426,0,0,0,0,0,0,158250,644,518),
(115,20200008,'Gate_of_fort',10476,96573,-3429,0,0,0,0,0,0,158250,644,518);