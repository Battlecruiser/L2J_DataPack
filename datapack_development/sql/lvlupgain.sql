-- 
-- Table structure for table `lvlupgain`
-- 

DROP TABLE IF EXISTS `lvlupgain`;
CREATE TABLE `lvlupgain` (
  `classid` int(3) NOT NULL default '0',
  `defaulthpbase` decimal(5,1) NOT NULL default '0.0',
  `defaulthpadd` decimal(4,2) NOT NULL default '0.00',
  `defaulthpmod` decimal(4,2) NOT NULL default '0.00',
  `defaultcpbase` decimal(5,1) NOT NULL default '0.0',
  `defaultcpadd` decimal(4,2) NOT NULL default '0.00',
  `defaultcpmod` decimal(4,2) NOT NULL default '0.00',
  `defaultmpbase` decimal(5,1) NOT NULL default '0.0',
  `defaultmpadd` decimal(4,2) NOT NULL default '0.00',
  `defaultmpmod` decimal(4,2) NOT NULL default '0.00',
  `class_lvl` int(3) NOT NULL default '0',
  PRIMARY KEY  (`classid`)
) TYPE=MyISAM;

-- 
-- Dumping data for table `lvlupgain`
-- 

INSERT INTO `lvlupgain` VALUES (0, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 30.0, 5.40, 0.06, 1);
INSERT INTO `lvlupgain` VALUES (1, 327.0, 32.70, 0.30, 163.5, 16.35, 0.15, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (2, 1044.0, 49.02, 0.38, 522.0, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (3, 1044.0, 54.18, 0.42, 522.0, 24.21, 0.21, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (4, 327.0, 29.43, 0.27, 163.5, 14.71, 0.14, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (5, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (6, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (7, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (8, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (9, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (10, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 40.0, 7.16, 0.12, 1);
INSERT INTO `lvlupgain` VALUES (11, 327.0, 21.80, 0.20, 163.5, 10.90, 0.10, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (12, 805.0, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (13, 805.0, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (14, 805.0, 38.70, 0.30, 402.5, 19.35, 0.15, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (15, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (16, 924.5, 38.70, 0.30, 462.3, 19.35, 0.15, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (17, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (18, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 30.0, 5.40, 0.06, 1);
INSERT INTO `lvlupgain` VALUES (19, 327.0, 29.43, 0.27, 163.5, 14.71, 0.14, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (20, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (21, 972.3, 49.02, 0.38, 486.2, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (22, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (23, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (24, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (25, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 40.0, 7.16, 0.12, 1);
INSERT INTO `lvlupgain` VALUES (26, 327.0, 21.80, 0.20, 163.5, 10.90, 0.10, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (27, 805.0, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (28, 805.0, 38.70, 0.30, 402.5, 19.35, 0.15, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (29, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (30, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (31, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 30.0, 5.40, 0.06, 1);
INSERT INTO `lvlupgain` VALUES (32, 327.0, 29.43, 0.27, 163.5, 14.71, 0.14, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (33, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (34, 972.3, 49.02, 0.38, 486.2, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (35, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (36, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (37, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (38, 80.0, 11.70, 0.13, 40.0, 5.85, 0.07, 40.0, 7.16, 0.12, 1);
INSERT INTO `lvlupgain` VALUES (39, 327.0, 21.80, 0.20, 163.5, 10.90, 0.10, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (40, 805.0, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (41, 805.0, 38.70, 0.30, 402.5, 19.35, 0.15, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (42, 327.0, 27.25, 0.25, 163.5, 13.60, 0.13, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (43, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (44, 80.0, 12.62, 0.12, 40.0, 6.31, 0.06, 30.0, 5.40, 0.06, 1);
INSERT INTO `lvlupgain` VALUES (45, 346.0, 34.88, 0.32, 173.0, 17.44, 0.16, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (46, 1110.8, 56.76, 0.44, 555.4, 28.38, 0.22, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (47, 346.0, 32.70, 0.30, 173.0, 16.35, 0.15, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (48, 1063.0, 54.18, 0.42, 531.5, 27.09, 0.21, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (49, 80.0, 12.62, 0.12, 40.0, 6.31, 0.06, 40.0, 7.16, 0.12, 1);
INSERT INTO `lvlupgain` VALUES (50, 346.0, 29.43, 0.27, 173.0, 14.71, 0.14, 192.0, 13.08, 0.12, 20);
INSERT INTO `lvlupgain` VALUES (51, 991.3, 43.86, 0.34, 495.7, 21.93, 0.17, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (52, 991.3, 43.86, 0.34, 495.7, 21.93, 0.17, 478.8, 25.80, 0.20, 40);
INSERT INTO `lvlupgain` VALUES (53, 80.0, 12.62, 0.12, 40.0, 6.31, 0.06, 30.0, 5.40, 0.06, 1);
INSERT INTO `lvlupgain` VALUES (54, 346.0, 34.88, 0.32, 173.0, 17.44, 0.16, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (55, 1110.8, 56.76, 0.44, 555.4, 28.38, 0.22, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (56, 346.0, 32.70, 0.30, 173.0, 16.35, 0.15, 144.0, 9.81, 0.09, 20);
INSERT INTO `lvlupgain` VALUES (57, 1063.0, 54.18, 0.42, 531.5, 27.09, 0.21, 359.1, 19.35, 0.15, 40);
INSERT INTO `lvlupgain` VALUES (88, 2846.7, 49.02, 0.38, 1423.4, 24.51, 0.19, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (89, 3036.5, 54.18, 0.42, 1414.6, 24.21, 0.21, 1423.4, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (90, 2680.0, 46.44, 0.36, 1340.0, 23.22, 0.18, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (91, 2680.0, 46.44, 0.36, 1340.0, 23.22, 0.18, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (92, 2537.5, 43.86, 0.34, 1268.8, 21.93, 0.17, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (93, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (94, 2133.0, 36.12, 0.28, 1066.7, 18.06, 0.14, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (95, 2133.0, 36.12, 0.28, 1066.7, 18.06, 0.14, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (96, 2228.0, 38.70, 0.30, 1114.0, 19.35, 0.15, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (97, 2347.7, 38.70, 0.30, 1173.9, 19.35, 0.15, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (98, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (99, 2680.1, 46.44, 0.36, 1340.0, 23.22, 0.18, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (100, 2775.0, 49.02, 0.38, 1387.6, 24.51, 0.19, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (101, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (102, 2537.5, 43.86, 0.34, 1268.8, 21.93, 0.17, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (103, 2133.0, 36.12, 0.28, 1066.7, 18.06, 0.14, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (104, 2228.0, 38.70, 0.30, 1114.0, 19.35, 0.15, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (105, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (106, 2680.0, 46.44, 0.36, 1340.0, 23.22, 0.18, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (107, 2775.0, 49.02, 0.38, 1387.6, 24.51, 0.19, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (108, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (109, 2537.5, 43.86, 0.34, 1268.8, 21.93, 0.17, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (110, 2133.0, 36.12, 0.28, 1066.7, 18.06, 0.14, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (111, 2228.0, 38.70, 0.30, 1114.0, 19.35, 0.15, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (112, 2442.6, 41.28, 0.32, 1221.0, 20.64, 0.16, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (113, 3608.0, 56.76, 0.44, 1599.1, 28.38, 0.22, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (114, 3055.5, 54.18, 0.42, 1527.7, 27.09, 0.21, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (115, 2604.3, 43.86, 0.34, 1302.2, 21.93, 0.17, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (116, 2604.3, 43.86, 0.34, 1302.2, 21.93, 0.17, 1427.6, 25.80, 0.20, 76);
INSERT INTO `lvlupgain` VALUES (117, 3608.0, 56.76, 0.44, 1599.1, 28.38, 0.22, 1070.7, 19.35, 0.15, 76);
INSERT INTO `lvlupgain` VALUES (118, 3055.5, 54.18, 0.42, 1527.7, 27.09, 0.21, 1070.7, 19.35, 0.15, 76);
