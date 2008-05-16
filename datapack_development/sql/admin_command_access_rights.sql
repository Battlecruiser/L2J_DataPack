-- 
-- For further information on the usage of this table, please refer to the 
-- documentation comments in the access_levels.sql file
-- 
-- -----------------------------------------------
-- Table structure for admin_command_access_rights
-- -----------------------------------------------
CREATE TABLE IF NOT EXISTS `admin_command_access_rights` (
  `adminCommand` varchar(255) NOT NULL default 'admin_',
  `accessLevels` varchar(255) NOT NULL,
  PRIMARY KEY  (`adminCommand`)
) ;

INSERT IGNORE INTO `admin_command_access_rights` VALUES 
('admin_admin','6');