-- ---------------------------
-- Table structure for fort_door
-- ---------------------------
DROP TABLE IF EXISTS `fort_door`;
CREATE TABLE `fort_door` (
  `fortId` INT NOT NULL default 0,
  `id` INT NOT NULL default 0,
  `name` varchar(30) NOT NULL,
  `x` INT NOT NULL default 0,
  `y` INT NOT NULL default 0,
  `z` INT NOT NULL default 0,
  `range_xmin` INT NOT NULL default 0,
  `range_ymin` INT NOT NULL default 0,
  `range_zmin` INT NOT NULL default 0,
  `range_xmax` INT NOT NULL default 0,
  `range_ymax` INT NOT NULL default 0,
  `range_zmax` INT NOT NULL default 0,
  `hp` INT NOT NULL default 0,
  `pDef` INT NOT NULL default 0,
  `mDef` INT NOT NULL default 0,
  `openType` varchar(5) NOT NULL default 'false', -- used for tracking doors that can be opened by double clicking on them
  PRIMARY KEY (`id`),
  KEY `id` (`fortId`)
);

-- Dragonspine doors
INSERT INTO `fort_door` VALUES 
(115,20200001,'Gate_of_fort',12503,93513,-3475,12383,93463,-3475,12611,93568,-3475,158250,644,518,'false'),
(115,20200002,'Gate_of_fort',11458,95587,-3454,11459,95578,-3454,11544,95595,-3454,158250,644,518,'false'),
(115,20200003,'Gate_of_fort',11616,95587,-3454,11530,95578,-3454,11627,95597,-3454,158250,644,518,'false'),
(115,20200004,'Gate_of_fort',10128,94936,-3426,10120,94925,-3426,10133,95024,-3426,158250,644,518,'false'),
(115,20200005,'Gate_of_fort',10128,95094,-3426,10120,95006,-3426,10137,95105,-3426,158250,644,518,'false'),
(115,20200006,'Gate_of_fort',11458,94386,-3454,11459,94379,-3454,11543,94393,-3454,158250,644,518,'false'),
(115,20200007,'Gate_of_fort',11616,94386,-3454,11530,94377,-3454,11627,94396,-3454,158250,644,518,'false'),
(115,20200008,'Gate_of_fort',10493,96565,-3475,10373,96515,-3475,10601,96619,-3475,158250,644,518,'false');