DROP TABLE IF EXISTS `npc_buffer`;
CREATE TABLE `npc_buffer` (
  `npc_id` int(6) NOT NULL,
  `skill_id` int(6) NOT NULL,
  `skill_level` int(6) NOT NULL DEFAULT '1',
  `skill_fee_id` int(6) NOT NULL DEFAULT '0',
  `skill_fee_amount` int(6) NOT NULL DEFAULT '0',
  `buff_group` int(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`npc_id`,`skill_id`,`buff_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `npc_buffer` VALUES
(36402,4356,3,0,0,4356),
(36402,4352,2,0,0,4352),
(36402,4345,3,0,0,4345),
(36402,4359,3,0,0,4359),
(36402,4351,6,0,0,4351),
(36402,4355,3,0,0,4355),
(36402,4357,2,0,0,4357),
(36402,4342,2,0,0,4342),
(36402,4358,3,0,0,4358),
(36402,4360,3,0,0,4360);