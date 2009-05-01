-- ---------------------------
-- Table structure for item_attributes
-- ---------------------------

CREATE TABLE IF NOT EXISTS item_attributes (
  itemId int(11) NOT NULL default 0,
  augAttributes int(11) NOT NULL default -1,
  augSkillId int(11) NOT NULL default -1,
  augSkillLevel int(11) NOT NULL default -1,
  elemType tinyint(1) NOT NULL default -1,
  elemValue int(11) NOT NULL default -1,
  PRIMARY KEY  (itemId)
);
