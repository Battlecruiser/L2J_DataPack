-- -----------------------------------------------
-- Table structure for admin_command_access_rights
-- -----------------------------------------------
CREATE TABLE IF NOT EXISTS `admin_command_access_rights` (
  `admin_command` varchar(255) NOT NULL default 'admin_',
  `access_levels` varchar(255) NOT NULL,
  PRIMARY KEY  (`admin_command`)
) ;

INSERT INTO `admin_command_access_rights` VALUES 
('admin_admin','6');