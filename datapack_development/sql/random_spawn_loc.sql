-- ---------------------------
-- Table structure for random_spawn_loc
-- ---------------------------
DROP TABLE IF EXISTS random_spawn_loc;
CREATE TABLE random_spawn_loc (
  groupId INT NOT NULL default 0,
  x INT NOT NULL default 0,
  y INT NOT NULL default 0,
  z INT NOT NULL default 0,
  PRIMARY KEY (groupId, x, y, z)
);

Insert Into random_spawn_loc Values (1, 178834, -184336, -355);
Insert Into random_spawn_loc Values (1, 151680, -174891, -1782);
Insert Into random_spawn_loc Values (1, 154153, -220105, -3402);
