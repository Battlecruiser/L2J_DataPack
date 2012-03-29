ALTER TABLE `pets` DROP `armor`;
ALTER TABLE `pets` DROP `weapon`;
ALTER TABLE `pets` DROP `jewel`;

DROP TABLE IF EXISTS
armor,
etcitem,
weapon,
custom_armor,
custom_etcitem,
custom_weapon;