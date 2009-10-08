ALTER TABLE `custom_armor` ADD `depositable` varchar(5) NOT NULL default 'false' AFTER `tradeable`;
ALTER TABLE `custom_etcitem` ADD `depositable` varchar(5) NOT NULL default 'false' AFTER `tradeable`;
ALTER TABLE `custom_weapon` ADD `depositable` varchar(5) NOT NULL default 'false' AFTER `tradeable`;