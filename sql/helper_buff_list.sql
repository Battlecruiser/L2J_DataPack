DROP TABLE IF EXISTS `helper_buff_list`;
CREATE TABLE `helper_buff_list` (
  `id` int(11) NOT NULL default '0',
  `skill_id` int(10) unsigned NOT NULL default '0',
  `name` varchar(25) NOT NULL default '',
  `skill_level` int(10) unsigned NOT NULL default '0',
  `lower_level` int(10) unsigned NOT NULL default '0',
  `upper_level` int(10) unsigned NOT NULL default '0',
  `is_magic_class` varchar(5) default NULL,
  `forSummon` varchar(5) default NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `helper_buff_list` VALUES
(0,5627,'WindWalk',1,6,62,'false','true'),
(1,5628,'Shield',1,6,62,'false','true'),
(2,4338,'Life Cubic',1,16,34,'false','false'),
(3,5629,'Bless the Body',1,6,62,'false','true'),
(4,5630,'Vampiric Rage',1,6,62,'false','true'),
(5,5631,'Regeneration',1,6,62,'false','true'),
(6,5632,'Haste',1,6,39,'false','true'),
(7,5627,'WindWalk',1,6,62,'true','false'),
(8,5628,'Shield',1,6,62,'true','false'),
(9,4338,'Life Cubic',1,16,34,'true','false'),
(10,5633,'Bless the Soul',1,6,62,'true','true'),
(11,5634,'Acumen',1,6,62,'true','true'),
(12,5635,'Concentration',1,6,62,'true','true'),
(13,5636,'Empower',1,6,62,'true','true'),
(14,5632,'Haste',1,40,62,'false','true'),
(15,5637,'Magic Barrier',1,6,62,'false','true'),
(16,5637,'Magic Barrier',1,6,62,'true','false');
-- (14,5182,'Blessing of Protection',1,1,39,'false'); -- Keeps you safe from an attack by a chaotic character who is more than 10 levels apart from you.