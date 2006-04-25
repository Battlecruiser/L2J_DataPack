CREATE TABLE IF NOT EXISTS `seven_signs` (
  `char_obj_id` INT NOT NULL default '0',
  `cabal` VARCHAR(4) NOT NULL default '',
  `seal` INT(1) NOT NULL default '0',
  `dawn_red_stones` INT NOT NULL default '0',
  `dawn_green_stones` INT NOT NULL default '0',
  `dawn_blue_stones` INT NOT NULL default '0',
  `dawn_ancient_adena_amount` INT NOT NULL default '0',
  `dawn_contribution_score` INT NOT NULL default '0',
  `dusk_red_stones` INT NOT NULL default '0',
  `dusk_green_stones` INT NOT NULL default '0',
  `dusk_blue_stones` INT NOT NULL default '0',
  `dusk_ancient_adena_amount` INT NOT NULL default '0',
  `dusk_contribution_score` INT NOT NULL default '0',
  PRIMARY KEY  (`char_obj_id`)
);
