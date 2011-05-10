CREATE TABLE IF NOT EXISTS `pets` (
  `item_obj_id` decimal(11) NOT NULL default 0,
  `name` varchar(16) ,
  `level` decimal(11) ,
  `curHp` decimal(18,0) ,
  `curMp` decimal(18,0) ,
  `exp` decimal(20, 0) ,
  `sp` decimal(11) ,
  `fed` decimal(11) ,
  `ownerId` decimal(11) NOT NULL default 0 ,
  `restore` enum('true','false') NOT NULL DEFAULT 'false' ,
  PRIMARY KEY (`item_obj_id`)
);