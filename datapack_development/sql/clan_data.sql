-- ---------------------------
-- Table structure for clan_data
-- ---------------------------
CREATE TABLE clan_data (
  clan_id decimal(11) ,
  clan_name varchar(45) ,
  clan_level decimal(1) ,
  hasCastle decimal(1) ,
  hasHideout decimal(1) ,
  ally_id decimal(9) ,
  ally_name varchar(45) ,
  leader_id decimal(11) ,
  PRIMARY KEY  (clan_id)
);
