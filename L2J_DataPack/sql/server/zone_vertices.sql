DROP TABLE IF EXISTS `zone_vertices`;
CREATE TABLE `zone_vertices` (
  `id` mediumint(5) unsigned NOT NULL,
  `order` tinyint(2) unsigned NOT NULL,
  `x` mediumint(6) NOT NULL,
  `y` mediumint(6) NOT NULL,
  PRIMARY KEY (`id`,`order`)
);