-- ---------------------------
-- Table structure for clanhall
-- ---------------------------
CREATE TABLE IF NOT EXISTS clanhall (
  id INT NOT NULL default 0,
  name varchar(40) NOT NULL,
  ownerId INT NOT NULL default 0,
  PRIMARY KEY  (name),
  KEY id (id)
);

INSERT IGNORE INTO `clanhall` VALUES (1, 'Gludio 1', 0),
(2, 'Gludio 2', 0),
(3, 'Gludio 3', 0),
(4, 'Gludio 4', 0),
(5, 'Gludin 1', 0),
(6, 'Gludin 2', 0),
(7, 'Gludin 3', 0),
(8, 'Gludin 4', 0),
(9, 'Gludin 5', 0),
(10, 'Dion 1', 0),
(11, 'Dion 2', 0),
(12, 'Dion 3', 0),
(13, 'Giran 1', 0),
(14, 'Giran 2', 0),
(15, 'Giran 3', 0),
(16, 'Giran 4', 0),
(17, 'Giran 5', 0),
(18, 'Aden 1', 0),
(19, 'Aden 2', 0),
(20, 'Aden 3', 0),
(21, 'Aden 4', 0),
(22, 'Aden 5', 0),
(23, 'Aden 6', 0),
(24, 'Goddard 1', 0),
(25, 'Goddard 2', 0),
(26, 'Goddard 3', 0),
(27, 'Goddard 4', 0),
(28, 'Bandits Stronghold', 0),
(29, 'Partisan Hideaway', 0),
(30, 'Hot Springs Guild House', 0);