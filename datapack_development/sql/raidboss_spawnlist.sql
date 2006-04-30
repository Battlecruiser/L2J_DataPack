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
  respawn_time INT NOT NULL default 0,
  PRIMARY KEY  (boss_id, loc_x, loc_x, loc_x)
);
