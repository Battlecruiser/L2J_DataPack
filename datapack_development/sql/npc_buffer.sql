-- ----------------------------
-- Table structure for npc_buffer
-- ----------------------------
CREATE TABLE `npc_buffer` (
  `npc_id` int(6) NOT NULL,
  `skill_id` int(6) NOT NULL,
  `skill_level` int(6) NOT NULL default '1',
  `skill_fee_id` int(6) NOT NULL default '0',
  `skill_fee_amount` int(6) NOT NULL default '0',
  `buff_group` int(6) NOT NULL default '0',
  PRIMARY KEY  (`npc_id`,`skill_id`,`buff_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `npc_buffer` VALUES ('31688', '1059', '3', '0', '0', '1059');
INSERT INTO `npc_buffer` VALUES ('31688', '1062', '2', '0', '0', '1062');
INSERT INTO `npc_buffer` VALUES ('31688', '1068', '3', '0', '0', '1068');
INSERT INTO `npc_buffer` VALUES ('31688', '1077', '3', '0', '0', '1077');
INSERT INTO `npc_buffer` VALUES ('31688', '1078', '6', '0', '0', '1078');
INSERT INTO `npc_buffer` VALUES ('31688', '1085', '3', '0', '0', '1085');
INSERT INTO `npc_buffer` VALUES ('31688', '1086', '2', '0', '0', '1086');
INSERT INTO `npc_buffer` VALUES ('31688', '1204', '2', '0', '0', '1204');
INSERT INTO `npc_buffer` VALUES ('31688', '1240', '3', '0', '0', '1240');
INSERT INTO `npc_buffer` VALUES ('31688', '1242', '3', '0', '0', '1242');
INSERT INTO `npc_buffer` VALUES ('1000003', '264', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '264', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '264', '1', '57', '100', '264');
INSERT INTO `npc_buffer` VALUES ('1000003', '265', '1', '57', '100', '265');
INSERT INTO `npc_buffer` VALUES ('1000003', '266', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '266', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '266', '1', '57', '100', '266');
INSERT INTO `npc_buffer` VALUES ('1000003', '267', '1', '57', '100', '267');
INSERT INTO `npc_buffer` VALUES ('1000003', '268', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '268', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '268', '1', '57', '100', '268');
INSERT INTO `npc_buffer` VALUES ('1000003', '269', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '269', '1', '57', '100', '269');
INSERT INTO `npc_buffer` VALUES ('1000003', '270', '1', '57', '100', '270');
INSERT INTO `npc_buffer` VALUES ('1000003', '271', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '271', '1', '57', '100', '271');
INSERT INTO `npc_buffer` VALUES ('1000003', '272', '1', '57', '100', '272');
INSERT INTO `npc_buffer` VALUES ('1000003', '273', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '273', '1', '57', '100', '273');
INSERT INTO `npc_buffer` VALUES ('1000003', '274', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '274', '1', '57', '100', '274');
INSERT INTO `npc_buffer` VALUES ('1000003', '275', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '275', '1', '57', '100', '275');
INSERT INTO `npc_buffer` VALUES ('1000003', '276', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '276', '1', '57', '100', '276');
INSERT INTO `npc_buffer` VALUES ('1000003', '277', '1', '57', '100', '277');
INSERT INTO `npc_buffer` VALUES ('1000003', '304', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '304', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '304', '1', '57', '100', '304');
INSERT INTO `npc_buffer` VALUES ('1000003', '305', '1', '57', '100', '305');
INSERT INTO `npc_buffer` VALUES ('1000003', '306', '1', '57', '100', '306');
INSERT INTO `npc_buffer` VALUES ('1000003', '307', '1', '57', '100', '307');
INSERT INTO `npc_buffer` VALUES ('1000003', '308', '1', '57', '100', '308');
INSERT INTO `npc_buffer` VALUES ('1000003', '309', '1', '57', '100', '309');
INSERT INTO `npc_buffer` VALUES ('1000003', '310', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '310', '1', '57', '100', '310');
INSERT INTO `npc_buffer` VALUES ('1000003', '311', '1', '57', '100', '311');
INSERT INTO `npc_buffer` VALUES ('1000003', '349', '1', '57', '100', '349');
INSERT INTO `npc_buffer` VALUES ('1000003', '363', '1', '57', '100', '363');
INSERT INTO `npc_buffer` VALUES ('1000003', '364', '1', '57', '100', '364');
INSERT INTO `npc_buffer` VALUES ('1000003', '366', '1', '57', '100', '366');
INSERT INTO `npc_buffer` VALUES ('1000003', '367', '1', '57', '100', '367');
INSERT INTO `npc_buffer` VALUES ('1000003', '529', '1', '57', '100', '529');
INSERT INTO `npc_buffer` VALUES ('1000003', '530', '1', '57', '100', '530');
INSERT INTO `npc_buffer` VALUES ('1000003', '1032', '1', '57', '100', '1032');
INSERT INTO `npc_buffer` VALUES ('1000003', '1033', '1', '57', '100', '1033');
INSERT INTO `npc_buffer` VALUES ('1000003', '1035', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1035', '1', '57', '100', '1035');
INSERT INTO `npc_buffer` VALUES ('1000003', '1036', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1036', '1', '57', '100', '1036');
INSERT INTO `npc_buffer` VALUES ('1000003', '1040', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1040', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1040', '1', '57', '100', '1040');
INSERT INTO `npc_buffer` VALUES ('1000003', '1043', '1', '57', '100', '1043');
INSERT INTO `npc_buffer` VALUES ('1000003', '1044', '1', '57', '100', '1044');
INSERT INTO `npc_buffer` VALUES ('1000003', '1045', '6', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1045', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1045', '1', '57', '100', '1045');
INSERT INTO `npc_buffer` VALUES ('1000003', '1048', '6', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1048', '1', '57', '100', '1048');
INSERT INTO `npc_buffer` VALUES ('1000003', '1059', '1', '57', '100', '1059');
INSERT INTO `npc_buffer` VALUES ('1000003', '1062', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1062', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1062', '1', '57', '100', '1062');
INSERT INTO `npc_buffer` VALUES ('1000003', '1068', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1068', '1', '57', '100', '1068');
INSERT INTO `npc_buffer` VALUES ('1000003', '1077', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1077', '1', '57', '100', '1077');
INSERT INTO `npc_buffer` VALUES ('1000003', '1078', '1', '57', '100', '1078');
INSERT INTO `npc_buffer` VALUES ('1000003', '1085', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1085', '1', '57', '100', '1085');
INSERT INTO `npc_buffer` VALUES ('1000003', '1086', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1086', '1', '57', '100', '1086');
INSERT INTO `npc_buffer` VALUES ('1000003', '1182', '1', '57', '100', '1182');
INSERT INTO `npc_buffer` VALUES ('1000003', '1189', '1', '57', '100', '1189');
INSERT INTO `npc_buffer` VALUES ('1000003', '1191', '1', '57', '100', '1191');
INSERT INTO `npc_buffer` VALUES ('1000003', '1204', '1', '57', '100', '1204');
INSERT INTO `npc_buffer` VALUES ('1000003', '1240', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1240', '1', '57', '100', '1');
INSERT INTO `npc_buffer` VALUES ('1000003', '1240', '1', '57', '100', '1240');
INSERT INTO `npc_buffer` VALUES ('1000003', '1242', '1', '57', '100', '0');
INSERT INTO `npc_buffer` VALUES ('1000003', '1242', '1', '57', '100', '1242');
INSERT INTO `npc_buffer` VALUES ('1000003', '1243', '1', '57', '100', '1243');
INSERT INTO `npc_buffer` VALUES ('1000003', '1303', '1', '57', '100', '1303');
INSERT INTO `npc_buffer` VALUES ('1000003', '1397', '1', '57', '100', '1397');
