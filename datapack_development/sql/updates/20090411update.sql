alter table items add  `time_left` decimal(4,00) NOT NULL default -1 after `mana_left`;
alter table custom_etcitem add  `time` decimal(4,00) NOT NULL default '-1'  after `duration`;
alter table custom_armor add  `time` decimal(4,00) NOT NULL default '-1'  after `duration`;
alter table custom_weapon add  `time` decimal(4,00) NOT NULL default '-1'  after `duration`;