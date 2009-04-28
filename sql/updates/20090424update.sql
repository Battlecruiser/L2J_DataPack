ALTER TABLE `items`
CHANGE count count BIGINT UNSIGNED NOT NULL default 0;
ALTER TABLE `itemsonground`
CHANGE count count BIGINT UNSIGNED NOT NULL default 0;