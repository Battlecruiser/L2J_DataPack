-- ---------------------------
-- Table structure for character_skills
-- ---------------------------
CREATE TABLE character_skills (
  char_obj_id decimal(11) ,
  skill_id decimal(3) ,
  skill_level varchar(5) ,
  skill_name varchar(24),
  PRIMARY KEY  (char_obj_id,skill_id)
) ;
