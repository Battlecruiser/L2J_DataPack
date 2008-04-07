-- ---------------------------
-- Table structure for fort_siege_guards
-- ---------------------------
DROP TABLE IF EXISTS `fort_siege_guards`;
CREATE TABLE IF NOT EXISTS fort_siege_guards (
  fortId INT NOT NULL default 0,
  id int(11) NOT NULL auto_increment,
  npcId INT NOT NULL default 0,
  x INT NOT NULL default 0,
  y INT NOT NULL default 0,
  z INT NOT NULL default 0,
  heading INT NOT NULL default 0,
  respawnDelay INT NOT NULL default 0,
  isHired INT NOT NULL default 1,
  PRIMARY KEY  (id),
  KEY id (fortId)
);

-- Dragonspine Guards
-- RETAIL !!
INSERT INTO `fort_siege_guards` VALUES ('115', '1', '36145', '12829', '96214', '-3392', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '2', '36154', '11488', '95088', '-2496', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '3', '36154', '11489', '94886', '-2496', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '4', '36154', '11584', '95088', '-2480', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '5', '36154', '11521', '94886', '-2496', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '6', '36154', '11553', '94886', '-2496', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '7', '36154', '11585', '94886', '-2496', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '8', '36154', '11552', '95088', '-2496', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '9', '36154', '11520', '95088', '-2496', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '10', '36155', '11567', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '11', '36155', '11759', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '12', '36155', '11903', '94560', '-2712', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '13', '36155', '10960', '95376', '-3056', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '14', '36155', '11903', '94624', '-2712', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '15', '36155', '11014', '94644', '-2904', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '16', '36155', '11695', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '17', '36155', '11791', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '18', '36155', '10960', '95344', '-3056', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '19', '36155', '11903', '94592', '-2712', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '20', '36155', '11078', '94644', '-2904', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '21', '36155', '11046', '94644', '-2904', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '22', '36155', '10960', '95408', '-3056', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '23', '36155', '11727', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '24', '36155', '11663', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '25', '36155', '11631', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '26', '36155', '11599', '95280', '-2528', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '27', '36156', '12512', '93488', '-3424', '39347', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '28', '36156', '12544', '93488', '-3424', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '29', '36156', '11487', '94361', '-3424', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '30', '36156', '11519', '95609', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '31', '36156', '11583', '94361', '-3424', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '32', '36156', '12448', '93488', '-3424', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '33', '36156', '12480', '93488', '-3424', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '34', '36156', '11519', '94361', '-3424', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '35', '36156', '11551', '94361', '-3424', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '36', '36156', '10436', '96588', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '37', '36156', '10530', '96589', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '38', '36156', '11583', '95609', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '39', '36156', '11487', '95609', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '40', '36156', '10468', '96588', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '41', '36156', '11551', '95609', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '42', '36156', '10499', '96589', '-3424', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '43', '36157', '12206', '93591', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '44', '36157', '13170', '93936', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '45', '36157', '12841', '93590', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '46', '36157', '12174', '93591', '-3248', '57674', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '47', '36157', '8896', '94448', '-3048', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '48', '36157', '13296', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '49', '36157', '13264', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '50', '36157', '11568', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '51', '36157', '10720', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '52', '36157', '9024', '93984', '-3048', '40960', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '53', '36157', '8896', '95424', '-3048', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '54', '36157', '8896', '95456', '-3048', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '55', '36157', '9056', '93968', '-3048', '40960', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '56', '36157', '14080', '95600', '-3048', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '57', '36157', '14080', '94624', '-3048', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '58', '36157', '14080', '94656', '-3048', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '59', '36157', '13952', '94000', '-3048', '57344', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '60', '36157', '13920', '93968', '-3048', '57344', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '61', '36157', '12304', '94096', '-3424', '54044', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '62', '36157', '12267', '94077', '-3424', '54044', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '63', '36157', '10622', '95920', '-3424', '20480', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '64', '36157', '10730', '95971', '-3424', '20480', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '65', '36157', '12112', '93744', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '66', '36157', '12112', '93680', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '67', '36157', '12898', '93676', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '68', '36157', '12898', '93742', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '69', '36157', '12930', '93708', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '70', '36157', '11843', '93930', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '71', '36157', '12809', '93590', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '72', '36157', '12343', '94061', '-3424', '54044', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '73', '36157', '11536', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '74', '36157', '9872', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '75', '36157', '12373', '94132', '-3424', '54044', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '76', '36157', '12112', '93712', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '77', '36157', '10752', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '78', '36157', '9920', '93472', '-3048', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '79', '36157', '12898', '93708', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '80', '36157', '12080', '93712', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '81', '36157', '13202', '93936', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '82', '36157', '12340', '94114', '-3424', '54044', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '83', '36157', '11811', '93930', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '84', '36157', '9680', '96608', '-3048', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '85', '36157', '11584', '96608', '-3048', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '86', '36157', '10912', '96368', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '87', '36157', '10912', '96336', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '88', '36157', '10912', '96400', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '89', '36157', '10944', '96368', '-2976', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '90', '36157', '10112', '96400', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '91', '36157', '10112', '96334', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '92', '36157', '10805', '96480', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '93', '36157', '11616', '96608', '-3048', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '94', '36157', '10200', '96480', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '95', '36157', '10168', '96480', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '96', '36157', '9712', '96608', '-3048', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '97', '36157', '12448', '96608', '-3024', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '98', '36157', '10837', '96480', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '99', '36157', '10112', '96368', '-2960', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '100', '36157', '10656', '95936', '-3424', '20480', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '101', '36157', '10693', '95951', '-3424', '20480', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '102', '36157', '10080', '96368', '-2976', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '103', '36157', '11165', '96144', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '104', '36157', '9806', '96139', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '105', '36157', '9838', '96139', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '106', '36157', '11197', '96144', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '107', '36157', '10640', '95987', '-3424', '20480', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '108', '36157', '14080', '95632', '-3048', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '109', '36157', '12400', '96608', '-3048', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '110', '36161', '9529', '94927', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '111', '36161', '9529', '95023', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '112', '36161', '9529', '94991', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '113', '36161', '9529', '95055', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '114', '36161', '9529', '94959', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '115', '36162', '9561', '95055', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '116', '36162', '9561', '95023', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '117', '36162', '9561', '94991', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '118', '36162', '9561', '94959', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '119', '36162', '9561', '94927', '-3392', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '120', '36163', '12829', '96214', '-3392', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '121', '36164', '12258', '93598', '-3424', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '122', '36164', '12414', '93598', '-3424', '59404', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '123', '36164', '12750', '93598', '-3424', '32767', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '124', '36164', '12594', '93598', '-3424', '38902', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '125', '36164', '9838', '96107', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '126', '36164', '11811', '93962', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '127', '36164', '12809', '93558', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '128', '36164', '13120', '96085', '-3392', '27864', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '129', '36164', '12174', '93559', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '130', '36164', '12827', '95980', '-3392', '16232', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '131', '36164', '13202', '93968', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '132', '36164', '11197', '96112', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '133', '36164', '12704', '95984', '-3392', '10308', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '134', '36164', '12992', '96260', '-3392', '35780', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '135', '36164', '10200', '96512', '-3248', '59067', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '136', '36164', '10242', '96482', '-3424', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '137', '36164', '10398', '96482', '-3424', '6132', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '138', '36164', '10446', '96514', '-3424', '17151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '139', '36164', '10837', '96512', '-3248', '35725', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '140', '36164', '10492', '96609', '-3424', '10835', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '141', '36164', '10578', '96482', '-3424', '27931', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '142', '36164', '10514', '96514', '-3424', '19632', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '143', '36165', '12592', '96096', '-3392', '6296', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '144', '36165', '12206', '93559', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '145', '36165', '11843', '93962', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '146', '36165', '12841', '93558', '-3248', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '147', '36165', '11165', '96112', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '148', '36165', '13170', '93968', '-3072', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '149', '36165', '10805', '96512', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '150', '36165', '12928', '95968', '-3392', '19600', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '151', '36165', '10168', '96512', '-3248', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '152', '36165', '9806', '96107', '-3072', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '153', '36167', '13119', '94688', '-3240', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '154', '36167', '13447', '94764', '-3344', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '155', '36167', '13447', '95116', '-3344', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '156', '36167', '13216', '95200', '-3240', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '157', '36167', '12927', '94758', '-3344', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '158', '36167', '13248', '95200', '-3240', '49152', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '159', '36167', '13151', '94688', '-3240', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '160', '36167', '12928', '95112', '-3344', '32768', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '161', '36167', '12986', '95168', '-3344', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '162', '36167', '13380', '95167', '-3344', '16384', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '163', '36167', '12988', '94714', '-3344', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '164', '36167', '13184', '94944', '-3344', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '165', '36167', '13388', '94712', '-3344', '49151', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '166', '36168', '13894', '95136', '-3424', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '167', '36168', '9086', '94588', '-3424', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '168', '36168', '12330', '94991', '-3424', '0', '10800', '0');
INSERT INTO `fort_siege_guards` VALUES ('115', '169', '36168', '10675', '95036', '-3424', '0', '10800', '0');
