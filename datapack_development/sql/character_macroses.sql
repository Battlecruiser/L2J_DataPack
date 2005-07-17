-- ---------------------------
-- Table structure for character_macroses
-- ---------------------------
CREATE TABLE character_macroses (
  char_obj_id decimal(11) ,
  id decimal(11) ,
  icon decimal(3) ,
  name varchar(20) ,
  descr varchar(80) ,
  acronym varchar(4) ,
  commands varchar(255) ,
  PRIMARY KEY  (char_obj_id,id)
) ;