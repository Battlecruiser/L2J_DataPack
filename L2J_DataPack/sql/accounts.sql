-- ---------------------------
-- Table structure for accounts
-- ---------------------------
CREATE TABLE IF NOT EXISTS `accounts` (
  `login` VARCHAR(45) NOT NULL default '',
  `password` VARCHAR(45) ,
  `lastactive` DECIMAL(20),
  `accessLevel` TINYINT NOT NULL DEFAULT 0,
  `lastIP` CHAR(15) NULL DEFAULT NULL,
  `lastServer` TINYINT DEFAULT 1,
  PRIMARY KEY (`login`)
);