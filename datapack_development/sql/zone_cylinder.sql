-- 
-- Table structure for table `zone_cylinder`
-- 
DROP TABLE IF EXISTS `zone_cylinder`;
CREATE TABLE `zone_cylinder` (
  `id` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `z1` int(11) NOT NULL,
  `z2` int(11) NOT NULL,
  `rad` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
);
