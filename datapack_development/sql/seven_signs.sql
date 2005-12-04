CREATE TABLE IF NOT EXISTS `seven_signs` (
  `char_obj_id` int(11) NOT NULL default '0',
  `cabal` varchar(4) NOT NULL default '',
  `seal` int(1) NOT NULL default '-1',
  `red_stones` int(10) NOT NULL default '0',
  `green_stones` int(10) NOT NULL default '0',
  `blue_stones` int(10) NOT NULL default '0',
  `ancient_adena_amount` int(10) NOT NULL default '0',
  `contribution_score` int(10) NOT NULL default '0',
  PRIMARY KEY  (`char_obj_id`)
);