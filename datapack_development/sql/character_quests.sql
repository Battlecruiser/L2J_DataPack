-- ----------------------------
-- Table structure for character_quests
-- ----------------------------
CREATE TABLE character_quests (
  char_id int(11),
  name varchar(40) ,
  var  varchar(20) ,
  value varchar(255) ,
  PRIMARY KEY  (char_id,name,var)
);
