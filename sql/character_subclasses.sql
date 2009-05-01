-- ---------------------------
-- Table structure for character_subclasses
-- ---------------------------
CREATE TABLE IF NOT EXISTS `character_subclasses` (
	`charId` INT UNSIGNED NOT NULL default 0,
	`class_id` int(2) NOT NULL default 0,
	`exp` decimal(20,0) NOT NULL default 0,
	`sp` decimal(11,0) NOT NULL default 0,
	`level` int(2) NOT NULL default 40,
	`class_index` int(1) NOT NULL default 0,
	PRIMARY KEY (`charId`,`class_id`)
);