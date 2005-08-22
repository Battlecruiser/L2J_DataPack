-- ----------------------------
-- Table structure for items
-- ----------------------------
CREATE TABLE items (
  owner_id decimal(10) , -- object id of the player or clan,owner of this item
  object_id decimal(11) ,-- object id of the item
  item_id decimal(6) ,   -- item id
  count decimal(10) ,
  enchant_level decimal(2) ,
  loc varchar(10) ,      -- inventory,paperdoll,npc,clan warehouse,pet,and so on
  loc_data decimal(10) , -- depending on location: equiped slot,npc id,pet id,etc
  price_sell decimal(10) ,
  price_buy decimal(10) ,
  PRIMARY KEY  (object_id),
  KEY `owner_id` (`owner_id`)
) ;
