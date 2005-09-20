--
-- Table structure for table `lvlupgain`
--

CREATE TABLE lvlupgain (
  classid int(3) NOT NULL default '0',
  defaulthpbase decimal(4,1) NOT NULL default '0.0',
  defaulthpadd decimal(3,2) NOT NULL default '0.00',
  defaulthpmod decimal(3,2) NOT NULL default '0.00',
  defaultcpbase decimal(4,1) NOT NULL default '0.0',
  defaultcpadd decimal(3,2) NOT NULL default '0.00',
  defaultcpmod decimal(3,2) NOT NULL default '0.00',
  defaultmpbase decimal(4,1) NOT NULL default '0.0',
  defaultmpadd decimal(3,2) NOT NULL default '0.00',
  defaultmpmod decimal(3,2) NOT NULL default '0.00',
  class_lvl int(3) NOT NULL default '0',
  PRIMARY KEY  (classid)
) TYPE=MyISAM;

--
-- Dumping data for table `lvlupgain`
--

INSERT INTO lvlupgain VALUES (0, 80, 11.7, 0.13, 40, 5.85, 0.07, 30, 5.4, 0.06, 1);
INSERT INTO lvlupgain VALUES (1, 327, 32.7, 0.3, 163.5, 16.35, 0.15, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (2, 1044, 49.02, 0.38, 522, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (3, 1044, 54.18, 0.42, 522, 0.21, 0.21, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (4, 327, 29.43, 0.27, 163.5, 14.71, 0.14, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (5, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (6, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (7, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (8, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (9, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (10, 80, 11.7, 0.13, 40, 5.85, 0.07, 40, 7.16, 0.12, 1);
INSERT INTO lvlupgain VALUES (11, 327, 21.8, 0.2, 163.5, 10.9, 0.1, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (12, 805, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (13, 805, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (14, 805, 38.7, 0.3, 402.5, 19.35, 0.15, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (15, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (16, 924.5, 38.7, 0.3, 462.3, 19.35, 0.15, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (17, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (18, 80, 11.7, 0.13, 40, 5.85, 0.07, 30, 5.4, 0.06, 1);
INSERT INTO lvlupgain VALUES (19, 327, 29.43, 0.27, 163.5, 14.71, 0.14, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (20, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (21, 972.3, 49.02, 0.38, 486.2, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (22, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (23, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (24, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (25, 80, 11.7, 0.13, 40, 5.85, 0.07, 40, 7.16, 0.12, 1);
INSERT INTO lvlupgain VALUES (26, 327, 21.8, 0.2, 163.5, 10.9, 0.1, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (27, 805, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (28, 805, 38.7, 0.3, 402.5, 19.35, 0.15, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (29, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (30, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (31, 80, 11.7, 0.13, 40, 5.85, 0.07, 30, 5.4, 0.06, 1);
INSERT INTO lvlupgain VALUES (32, 327, 29.43, 0.27, 163.5, 14.71, 0.14, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (33, 972.3, 46.44, 0.36, 486.2, 23.22, 0.18, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (34, 972.3, 49.02, 0.38, 486.2, 24.51, 0.19, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (35, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (36, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (37, 924.5, 43.86, 0.34, 462.3, 21.93, 0.17, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (38, 80, 11.7, 0.13, 40, 5.85, 0.07, 40, 7.16, 0.12, 1);
INSERT INTO lvlupgain VALUES (39, 327, 21.8, 0.2, 163.5, 10.9, 0.1, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (40, 805, 36.12, 0.28, 402.5, 18.06, 0.14, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (41, 805, 38.7, 0.3, 402.5, 19.35, 0.15, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (42, 327, 27.25, 0.25, 163.5, 13.6, 0.13, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (43, 924.5, 41.28, 0.32, 462.3, 20.64, 0.16, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (44, 80, 12.62, 0.12, 40, 6.31, 0.06, 30, 5.4, 0.06, 1);
INSERT INTO lvlupgain VALUES (45, 346, 34.88, 0.32, 173, 17.44, 0.16, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (46, 1110.8, 56.76, 0.44, 555.4, 28.38, 0.22, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (47, 346, 32.7, 0.3, 173, 16.35, 0.15, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (48, 1063, 54.18, 0.42, 531.5, 27.09, 0.21, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (49, 80, 12.62, 0.12, 40, 6.31, 0.06, 40, 7.16, 0.12, 1);
INSERT INTO lvlupgain VALUES (50, 346, 29.43, 0.27, 173, 14.71, 0.14, 192, 13.08, 0.12, 20);
INSERT INTO lvlupgain VALUES (51, 991.3, 43.86, 0.34, 495.7, 21.93, 0.17, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (52, 991.3, 43.86, 0.34, 495.7, 21.93, 0.17, 478.8, 25.8, 0.2, 40);
INSERT INTO lvlupgain VALUES (53, 80, 12.62, 0.12, 40, 6.31, 0.06, 30, 5.4, 0.06, 1);
INSERT INTO lvlupgain VALUES (54, 346, 34.88, 0.32, 173, 17.44, 0.16, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (55, 1110.8, 56.76, 0.44, 555.4, 28.38, 0.22, 359.1, 19.35, 0.15, 40);
INSERT INTO lvlupgain VALUES (56, 346, 32.7, 0.3, 173, 16.35, 0.15, 144, 9.81, 0.09, 20);
INSERT INTO lvlupgain VALUES (57, 1063, 54.18, 0.42, 531.5, 27.09, 0.21, 359.1, 19.35, 0.15, 40);