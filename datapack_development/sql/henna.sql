--
-- Table structure for table `henna`
--

CREATE TABLE henna (
  symbol_id int(11) NOT NULL default '0',
  symbol_name varchar(45) default NULL,
  dye_id int(11) default NULL,
  dye_amount int(11) default NULL,
  price int(11) default NULL,
  stat_INT decimal(11,0) default NULL,
  stat_STR decimal(11,0) default NULL,
  stat_CON decimal(11,0) default NULL,
  stat_MEM decimal(11,0) default NULL,
  stat_DEX decimal(11,0) default NULL,
  stat_WIT decimal(11,0) default NULL,
  PRIMARY KEY  (symbol_id)
) TYPE=MyISAM;

--
-- Dumping data for table `henna`
--

INSERT INTO henna VALUES (1,'symbol_s1c3_d',4445,1,1,0,1,-3,0,0,0);
INSERT INTO henna VALUES (2,'symbol_s1d3_d',4446,1,1,0,1,0,0,-3,0);
INSERT INTO henna VALUES (3,'symbol_c1s3_d',4447,1,1,0,-3,1,0,0,0);
INSERT INTO henna VALUES (4,'symbol_c1d3_d',4448,1,1,0,0,1,0,-3,0);
INSERT INTO henna VALUES (5,'symbol_d1s3_d',4449,1,1,0,-3,0,0,1,0);
INSERT INTO henna VALUES (6,'symbol_d1c3_d',4450,1,1,0,0,-3,0,1,0);
INSERT INTO henna VALUES (7,'symbol_i1m3_d',4451,1,1,1,0,0,-3,0,0);
INSERT INTO henna VALUES (8,'symbol_i1w3_d',4452,1,1,1,0,0,0,0,-3);
INSERT INTO henna VALUES (9,'symbol_m1i3_d',4453,1,1,-3,0,0,1,0,0);
INSERT INTO henna VALUES (10,'symbol_m1w3_d',4454,1,1,0,0,0,1,0,-3);
INSERT INTO henna VALUES (11,'symbol_w1i3_d',4455,1,1,-3,0,0,0,0,1);
INSERT INTO henna VALUES (12,'symbol_w1m3_d',4456,1,1,0,0,0,-3,0,1);
INSERT INTO henna VALUES (13,'symbol_s1c2_d',4457,1,1,0,1,-2,0,0,0);
INSERT INTO henna VALUES (14,'symbol_s1d2_d',4458,1,1,0,1,0,0,-2,0);
INSERT INTO henna VALUES (15,'symbol_c1s2_d',4459,1,1,0,-2,1,0,0,0);
INSERT INTO henna VALUES (16,'symbol_c1d2_d',4460,1,1,0,0,1,0,-2,0);
INSERT INTO henna VALUES (17,'symbol_d1s2_d',4461,1,1,0,-2,0,0,1,0);
INSERT INTO henna VALUES (18,'symbol_d1c2_d',4462,1,1,0,0,-2,0,1,0);
INSERT INTO henna VALUES (19,'symbol_i1m2_d',4463,1,1,1,0,0,-2,0,0);
INSERT INTO henna VALUES (20,'symbol_i1w2_d',4464,1,1,1,0,0,0,0,-2);
INSERT INTO henna VALUES (21,'symbol_m1i2_d',4465,1,1,-2,0,0,1,0,0);
INSERT INTO henna VALUES (22,'symbol_m1w2_d',4466,1,1,0,0,0,1,0,-2);
INSERT INTO henna VALUES (23,'symbol_w1i2_d',4467,1,1,-2,0,0,0,0,1);
INSERT INTO henna VALUES (24,'symbol_w1m2_d',4468,1,1,0,0,0,-2,0,1);
INSERT INTO henna VALUES (25,'symbol_s1c1_d',4469,1,1,0,1,-1,0,0,0);
INSERT INTO henna VALUES (26,'symbol_s1d1_d',4470,1,1,0,1,0,0,-1,0);
INSERT INTO henna VALUES (27,'symbol_c1s1_d',4471,1,1,0,-1,1,0,0,0);
INSERT INTO henna VALUES (28,'symbol_c1d1_d',4472,1,1,0,0,1,0,-1,0);
INSERT INTO henna VALUES (29,'symbol_d1s1_d',4473,1,1,0,-1,0,0,1,0);
INSERT INTO henna VALUES (30,'symbol_d1c1_d',4474,1,1,0,0,-1,0,1,0);
INSERT INTO henna VALUES (31,'symbol_i1m1_d',4475,1,1,1,0,0,-1,0,0);
INSERT INTO henna VALUES (32,'symbol_i1w1_d',4476,1,1,1,0,0,0,0,-1);
INSERT INTO henna VALUES (33,'symbol_m1i1_d',4477,1,1,-1,0,0,1,0,0);
INSERT INTO henna VALUES (34,'symbol_m1w1_d',4478,1,1,0,0,0,1,0,-1);
INSERT INTO henna VALUES (35,'symbol_w1i1_d',4479,1,1,-1,0,0,0,0,1);
INSERT INTO henna VALUES (36,'symbol_w1m1_d',4480,1,1,0,0,0,-1,0,1);
INSERT INTO henna VALUES (37,'symbol_s1c3_c',4481,1,1,0,1,-3,0,0,0);
INSERT INTO henna VALUES (38,'symbol_s1d3_c',4482,1,1,0,1,0,0,-3,0);
INSERT INTO henna VALUES (39,'symbol_c1s3_c',4483,1,1,0,-3,1,0,0,0);
INSERT INTO henna VALUES (40,'symbol_c1c3_c',4484,1,1,0,0,1,0,-3,0);
INSERT INTO henna VALUES (41,'symbol_d1s3_c',4485,1,1,0,-3,0,0,1,0);
INSERT INTO henna VALUES (42,'symbol_d1c3_c',4486,1,1,0,0,-3,0,1,0);
INSERT INTO henna VALUES (43,'symbol_i1m3_c',4487,1,1,1,0,0,-3,0,0);
INSERT INTO henna VALUES (44,'symbol_i1w3_c',4488,1,1,1,0,0,0,0,-3);
INSERT INTO henna VALUES (45,'symbol_m1i3_c',4489,1,1,-3,0,0,1,0,0);
INSERT INTO henna VALUES (46,'symbol_m1w3_c',4490,1,1,0,0,0,1,0,-3);
INSERT INTO henna VALUES (47,'symbol_w1i3_c',4491,1,1,-3,0,0,0,0,1);
INSERT INTO henna VALUES (48,'symbol_w1m3_c',4492,1,1,0,0,0,-3,0,1);
INSERT INTO henna VALUES (49,'symbol_s1c2_c',4493,1,1,0,1,-2,0,0,0);
INSERT INTO henna VALUES (50,'symbol_s1d2_c',4494,1,1,0,1,0,0,-2,0);
INSERT INTO henna VALUES (51,'symbol_c1s2_c',4495,1,1,0,-2,1,0,0,0);
INSERT INTO henna VALUES (52,'symbol_c1c2_c',4496,1,1,0,0,1,0,-2,0);
INSERT INTO henna VALUES (53,'symbol_d1s2_c',4497,1,1,0,-2,0,0,1,0);
INSERT INTO henna VALUES (54,'symbol_d1c2_c',4498,1,1,0,0,-2,0,1,0);
INSERT INTO henna VALUES (55,'symbol_i1m2_c',4499,1,1,1,0,0,-2,0,0);
INSERT INTO henna VALUES (56,'symbol_i1w2_c',4500,1,1,1,0,0,0,0,-2);
INSERT INTO henna VALUES (57,'symbol_m1i2_c',4501,1,1,-2,0,0,1,0,0);
INSERT INTO henna VALUES (58,'symbol_m1w2_c',4502,1,1,0,0,0,1,0,-2);
INSERT INTO henna VALUES (59,'symbol_w1i2_c',4503,1,1,-2,0,0,0,0,1);
INSERT INTO henna VALUES (60,'symbol_w1m2_c',4504,1,1,0,0,0,-2,0,1);
INSERT INTO henna VALUES (61,'symbol_s2c4_c',4505,1,1,0,2,-4,0,0,0);
INSERT INTO henna VALUES (62,'symbol_s2d4_c',4506,1,1,0,2,0,0,-4,0);
INSERT INTO henna VALUES (63,'symbol_c2s4_c',4507,1,1,0,-4,2,0,0,0);
INSERT INTO henna VALUES (64,'symbol_c2c4_c',4508,1,1,0,0,2,0,-4,0);
INSERT INTO henna VALUES (65,'symbol_d2s4_c',4509,1,1,0,-4,0,0,2,0);
INSERT INTO henna VALUES (66,'symbol_d2c4_c',4510,1,1,0,0,-4,0,2,0);
INSERT INTO henna VALUES (67,'symbol_i2m4_c',4511,1,1,2,0,0,-4,0,0);
INSERT INTO henna VALUES (68,'symbol_i2w4_c',4512,1,1,2,0,0,0,0,-4);
INSERT INTO henna VALUES (69,'symbol_m2i4_c',4513,1,1,-4,0,0,2,0,0);
INSERT INTO henna VALUES (70,'symbol_m2w4_c',4514,1,1,0,0,0,2,0,-4);
INSERT INTO henna VALUES (71,'symbol_w2i4_c',4515,1,1,-4,0,0,0,0,2);
INSERT INTO henna VALUES (72,'symbol_w2m4_c',4516,1,1,0,0,0,-4,0,2);
INSERT INTO henna VALUES (73,'symbol_s2c3_c',4517,1,1,0,2,-3,0,0,0);
INSERT INTO henna VALUES (74,'symbol_s2d3_c',4518,1,1,0,2,0,0,-3,0);
INSERT INTO henna VALUES (75,'symbol_c2s3_c',4519,1,1,0,-3,2,0,0,0);
INSERT INTO henna VALUES (76,'symbol_c2c3_c',4520,1,1,0,0,2,0,-3,0);
INSERT INTO henna VALUES (77,'symbol_d2s3_c',4521,1,1,0,-3,0,0,2,0);
INSERT INTO henna VALUES (78,'symbol_d2c3_c',4522,1,1,0,0,-3,0,2,0);
INSERT INTO henna VALUES (79,'symbol_i2m3_c',4523,1,1,2,0,0,-3,0,0);
INSERT INTO henna VALUES (80,'symbol_i2w3_c',4524,1,1,2,0,0,0,0,-3);
INSERT INTO henna VALUES (81,'symbol_m2i3_c',4525,1,1,-3,0,0,2,0,0);
INSERT INTO henna VALUES (82,'symbol_m2w3_c',4526,1,1,0,0,0,2,0,-3);
INSERT INTO henna VALUES (83,'symbol_w2i3_c',4527,1,1,-3,0,0,0,0,2);
INSERT INTO henna VALUES (84,'symbol_w2m3_c',4528,1,1,0,0,0,-3,0,2);
INSERT INTO henna VALUES (85,'symbol_s3c5_c',4529,1,1,0,3,-5,0,0,0);
INSERT INTO henna VALUES (86,'symbol_s3d5_c',4530,1,1,0,3,0,0,-5,0);
INSERT INTO henna VALUES (87,'symbol_c3s5_c',4531,1,1,0,-5,3,0,0,0);
INSERT INTO henna VALUES (88,'symbol_c3c5_c',4532,1,1,0,0,3,0,-5,0);
INSERT INTO henna VALUES (89,'symbol_d3s5_c',4533,1,1,0,-5,0,0,3,0);
INSERT INTO henna VALUES (90,'symbol_d3c5_c',4534,1,1,0,0,-5,0,3,0);
INSERT INTO henna VALUES (91,'symbol_i3m5_c',4535,1,1,3,0,0,-5,0,0);
INSERT INTO henna VALUES (92,'symbol_i3w5_c',4536,1,1,3,0,0,0,0,-5);
INSERT INTO henna VALUES (93,'symbol_m3i5_c',4537,1,1,-5,0,0,3,0,0);
INSERT INTO henna VALUES (94,'symbol_m3w5_c',4538,1,1,0,0,0,3,0,-5);
INSERT INTO henna VALUES (95,'symbol_w3i5_c',4539,1,1,-5,0,0,0,0,3);
INSERT INTO henna VALUES (96,'symbol_w3m5_c',4540,1,1,0,0,0,-5,0,3);
INSERT INTO henna VALUES (97,'symbol_s3c4_c',4541,1,1,0,3,-4,0,0,0);
INSERT INTO henna VALUES (98,'symbol_s3d4_c',4542,1,1,0,3,0,0,-4,0);
INSERT INTO henna VALUES (99,'symbol_c3s4_c',4543,1,1,0,-4,3,0,0,0);
INSERT INTO henna VALUES (100,'symbol_c3c4_c',4544,1,1,0,0,3,0,-4,0);
INSERT INTO henna VALUES (101,'symbol_d3s4_c',4545,1,1,0,-4,0,0,3,0);
INSERT INTO henna VALUES (102,'symbol_d3c4_c',4546,1,1,0,0,-4,0,3,0);
INSERT INTO henna VALUES (103,'symbol_i3m4_c',4547,1,1,3,0,0,-4,0,0);
INSERT INTO henna VALUES (104,'symbol_i3w4_c',4548,1,1,3,0,0,0,0,-4);
INSERT INTO henna VALUES (105,'symbol_m3i4_c',4549,1,1,-4,0,0,3,0,0);
INSERT INTO henna VALUES (106,'symbol_m3w4_c',4550,1,1,0,0,0,3,0,-4);
INSERT INTO henna VALUES (107,'symbol_w3i4_c',4551,1,1,-4,0,0,0,0,3);
INSERT INTO henna VALUES (108,'symbol_w3m4_c',4552,1,1,0,0,0,-4,0,3);
INSERT INTO henna VALUES (109,'symbol_s1c1_c',4553,1,1,0,1,-1,0,0,0);
INSERT INTO henna VALUES (110,'symbol_s1d1_c',4554,1,1,0,1,0,0,-1,0);
INSERT INTO henna VALUES (111,'symbol_c1s1_c',4555,1,1,0,-1,1,0,0,0);
INSERT INTO henna VALUES (112,'symbol_c1c1_c',4556,1,1,0,0,1,0,-1,0);
INSERT INTO henna VALUES (113,'symbol_d1s1_c',4557,1,1,0,-1,0,0,1,0);
INSERT INTO henna VALUES (114,'symbol_d1c1_c',4558,1,1,0,0,-1,0,1,0);
INSERT INTO henna VALUES (115,'symbol_i1m1_c',4559,1,1,1,0,0,-1,0,0);
INSERT INTO henna VALUES (116,'symbol_i1w1_c',4560,1,1,1,0,0,0,0,-1);
INSERT INTO henna VALUES (117,'symbol_m1i1_c',4561,1,1,-1,0,0,1,0,0);
INSERT INTO henna VALUES (118,'symbol_m1w1_c',4562,1,1,0,0,0,1,0,-1);
INSERT INTO henna VALUES (119,'symbol_w1i1_c',4563,1,1,-1,0,0,0,0,1);
INSERT INTO henna VALUES (120,'symbol_w1m1_c',4564,1,1,0,0,0,-1,0,1);
INSERT INTO henna VALUES (121,'symbol_s4c6_c',4565,1,1,0,4,-6,0,0,0);
INSERT INTO henna VALUES (122,'symbol_s4d6_c',4566,1,1,0,4,0,0,-6,0);
INSERT INTO henna VALUES (123,'symbol_c4s6_c',4567,1,1,0,-6,4,0,0,0);
INSERT INTO henna VALUES (124,'symbol_c4c6_c',4568,1,1,0,0,4,0,-6,0);
INSERT INTO henna VALUES (125,'symbol_d4s6_c',4569,1,1,0,-6,0,0,4,0);
INSERT INTO henna VALUES (126,'symbol_d4c6_c',4570,1,1,0,0,-6,0,4,0);
INSERT INTO henna VALUES (127,'symbol_i4m6_c',4571,1,1,4,0,0,-6,0,0);
INSERT INTO henna VALUES (128,'symbol_i4w6_c',4572,1,1,4,0,0,0,0,-6);
INSERT INTO henna VALUES (129,'symbol_m4i6_c',4573,1,1,-6,0,0,4,0,0);
INSERT INTO henna VALUES (130,'symbol_m4w6_c',4574,1,1,0,0,0,4,0,-6);
INSERT INTO henna VALUES (131,'symbol_w4i6_c',4575,1,1,-6,0,0,0,0,4);
INSERT INTO henna VALUES (132,'symbol_w4m6_c',4576,1,1,0,0,0,-6,0,4);
INSERT INTO henna VALUES (133,'symbol_s4c5_c',4577,1,1,0,4,-5,0,0,0);
INSERT INTO henna VALUES (134,'symbol_s4d5_c',4578,1,1,0,4,0,0,-5,0);
INSERT INTO henna VALUES (135,'symbol_c4s5_c',4579,1,1,0,-5,4,0,0,0);
INSERT INTO henna VALUES (136,'symbol_c4c5_c',4580,1,1,0,0,4,0,-5,0);
INSERT INTO henna VALUES (137,'symbol_d4s5_c',4581,1,1,0,-5,0,0,4,0);
INSERT INTO henna VALUES (138,'symbol_d4c5_c',4582,1,1,0,0,-5,0,4,0);
INSERT INTO henna VALUES (139,'symbol_i4m5_c',4583,1,1,4,0,0,-5,0,0);
INSERT INTO henna VALUES (140,'symbol_i4w5_c',4584,1,1,4,0,0,0,0,-5);
INSERT INTO henna VALUES (141,'symbol_m4i5_c',4585,1,1,-5,0,0,4,0,0);
INSERT INTO henna VALUES (142,'symbol_m4w5_c',4586,1,1,0,0,0,4,0,-5);
INSERT INTO henna VALUES (143,'symbol_w4i5_c',4587,1,1,-5,0,0,0,0,4);
INSERT INTO henna VALUES (144,'symbol_w4m5_c',4588,1,1,0,0,0,-5,0,4);
INSERT INTO henna VALUES (145,'symbol_s2c2_c',4589,1,1,0,2,-2,0,0,0);
INSERT INTO henna VALUES (146,'symbol_s2d2_c',4590,1,1,0,2,0,0,-2,0);
INSERT INTO henna VALUES (147,'symbol_c2s2_c',4591,1,1,0,-2,2,0,0,0);
INSERT INTO henna VALUES (148,'symbol_c2c2_c',4592,1,1,0,0,2,0,-2,0);
INSERT INTO henna VALUES (149,'symbol_d2s2_c',4593,1,1,0,-2,0,0,2,0);
INSERT INTO henna VALUES (150,'symbol_d2c2_c',4594,1,1,0,0,-2,0,2,0);
INSERT INTO henna VALUES (151,'symbol_i2m2_c',4595,1,1,2,0,0,-2,0,0);
INSERT INTO henna VALUES (152,'symbol_i2w2_c',4596,1,1,2,0,0,0,0,-2);
INSERT INTO henna VALUES (153,'symbol_m2i2_c',4597,1,1,-2,0,0,2,0,0);
INSERT INTO henna VALUES (154,'symbol_m2w2_c',4598,1,1,0,0,0,2,0,-2);
INSERT INTO henna VALUES (155,'symbol_w2i2_c',4599,1,1,-2,0,0,0,0,2);
INSERT INTO henna VALUES (156,'symbol_w2m2_c',4600,1,1,0,0,0,-2,0,2);
INSERT INTO henna VALUES (157,'symbol_s3c3_c',4601,1,1,0,3,-3,0,0,0);
INSERT INTO henna VALUES (158,'symbol_s3d3_c',4602,1,1,0,3,0,0,-3,0);
INSERT INTO henna VALUES (159,'symbol_c3s3_c',4603,1,1,0,-3,3,0,0,0);
INSERT INTO henna VALUES (160,'symbol_c3c3_c',4604,1,1,0,0,3,0,-3,0);
INSERT INTO henna VALUES (161,'symbol_d3s3_c',4605,1,1,0,-3,0,0,3,0);
INSERT INTO henna VALUES (162,'symbol_d3c3_c',4606,1,1,0,0,-3,0,3,0);
INSERT INTO henna VALUES (163,'symbol_i3m3_c',4607,1,1,3,0,0,-3,0,0);
INSERT INTO henna VALUES (164,'symbol_i3w3_c',4608,1,1,3,0,0,0,0,-3);
INSERT INTO henna VALUES (165,'symbol_m3i3_c',4609,1,1,-3,0,0,3,0,0);
INSERT INTO henna VALUES (166,'symbol_m3w3_c',4610,1,1,0,0,0,3,0,-3);
INSERT INTO henna VALUES (167,'symbol_w3i3_c',4611,1,1,-3,0,0,0,0,3);
INSERT INTO henna VALUES (168,'symbol_w3m3_c',4612,1,1,0,0,0,-3,0,3);
INSERT INTO henna VALUES (169,'symbol_s4c4_c',4613,1,1,0,4,-4,0,0,0);
INSERT INTO henna VALUES (170,'symbol_s4d4_c',4614,1,1,0,4,0,0,-4,0);
INSERT INTO henna VALUES (171,'symbol_c4s4_c',4615,1,1,0,-4,4,0,0,0);
INSERT INTO henna VALUES (172,'symbol_c4c4_c',4616,1,1,0,0,4,0,-4,0);
INSERT INTO henna VALUES (173,'symbol_d4s4_c',4617,1,1,0,-4,0,0,4,0);
INSERT INTO henna VALUES (174,'symbol_d4c4_c',4618,1,1,0,0,-4,0,4,0);
INSERT INTO henna VALUES (175,'symbol_i4m4_c',4619,1,1,4,0,0,-4,0,0);
INSERT INTO henna VALUES (176,'symbol_i4w4_c',4620,1,1,4,0,0,0,0,-4);
INSERT INTO henna VALUES (177,'symbol_m4i4_c',4621,1,1,-4,0,0,4,0,0);
INSERT INTO henna VALUES (178,'symbol_m4w4_c',4622,1,1,0,0,0,4,0,-4);
INSERT INTO henna VALUES (179,'symbol_w4i4_c',4623,1,1,-4,0,0,0,0,4);
INSERT INTO henna VALUES (180,'symbol_w4m4_c',4624,1,1,0,0,0,-4,0,4);
