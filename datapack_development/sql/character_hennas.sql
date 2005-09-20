CREATE TABLE `character_hennas` (
  `char_obj_id` INT NOT NULL DEFAULT 0,
  `symbol_id` INT,
  `slot` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`char_obj_id`,`slot`)
);

