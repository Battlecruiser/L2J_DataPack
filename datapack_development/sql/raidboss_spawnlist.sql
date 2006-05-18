-- ---------------------------
-- Table structure for raidboss_spawnlist
-- ---------------------------
CREATE TABLE IF NOT EXISTS raidboss_spawnlist (
  boss_id INT NOT NULL default 0,
  amount INT NOT NULL default 0,
  loc_x INT NOT NULL default 0,
  loc_y INT NOT NULL default 0,
  loc_z INT NOT NULL default 0,
  heading INT NOT NULL default 0,
  respawn_delay INT NOT NULL default 0,
  respawn_time BIGINT NOT NULL default 0,
  currentHp decimal(8,0) default NULL,
  currentMp decimal(8,0) default NULL,
  PRIMARY KEY  (boss_id, loc_x, loc_y, loc_z)
);
