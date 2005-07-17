-- ----------------------------
-- Table structure for leasing merchants
-- ----------------------------
CREATE TABLE merchant_lease (
  merchant_id int(11),
  player_id int(11),
  bid int(11),
  `type` int(11),
  player_name varchar(35),
  PRIMARY KEY  (merchant_id,player_id,`type`)
);