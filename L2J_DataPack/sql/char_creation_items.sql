-- This table allows yoo to define what items
-- should players receive upon character creation,
-- depending on the class they choose.
--
-- A value of -1 in the classId field means "Any class"

--
-- Table structure for table `char_creation_items`
--

DROP TABLE IF EXISTS `char_creation_items`;

CREATE TABLE IF NOT EXISTS `char_creation_items` (
  `classId` smallint(6) NOT NULL,
  `itemId` smallint(6) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL default '1',
  `equipped` enum('true','false') NOT NULL default 'false',
  PRIMARY KEY (`classId`,`itemId`)
);

--
-- Dumping data for table `char_creation_items`
--

INSERT INTO `char_creation_items` VALUES
(-1,5588,1,'false'), -- All classes - Tutorial Guide
(-1,9716,10,'false'), -- All classes - Scroll of Escape: Kamael Village
(-1,10650,5,'false'), -- All classes - Adventurer's Scroll of Escape
(0,10,1,'false'), -- Human Fighter - Dagger
(0,1146,1,'true'), -- Human Fighter - Squire's Shirt
(0,1147,1,'true'), -- Human Fighter - Squire's Pants
(0,2369,1,'true'), -- Human Fighter - Squire's Sword
(10,6,1,'true'), -- Human Mage - Apprentice's Wand
(10,425,1,'true'), -- Human Mage - Apprentice's Tunic
(10,461,1,'true'), -- Human Mage - Apprentice's Stockings
(18,10,1,'false'), -- Elf Fighter - Dagger
(18,1146,1,'true'), -- Elf Fighter - Squire's Shirt
(18,1147,1,'true'), -- Elf Fighter - Squire's Pants
(18,2369,1,'true'), -- Elf Fighter - Squire's Sword
(25,6,1,'true'), -- Elf Mage - Apprentice's Wand
(25,425,1,'true'), -- Elf Mage - Apprentice's Tunic
(25,461,1,'true'), -- Elf Mage - Apprentice's Stockings
(31,10,1,'false'), -- DE Fighter - Dagger
(31,1146,1,'true'), -- DE Fighter - Squire's Shirt
(31,1147,1,'true'), -- DE Fighter - Squire's Pants
(31,2369,1,'true'), -- DE Fighter - Squire's Sword
(38,6,1,'true'), -- DE Mage - Apprentice's Wand
(38,425,1,'true'), -- DE Mage - Apprentice's Tunic
(38,461,1,'true'), -- DE Mage - Apprentice's Stockings
(44,1146,1,'true'), -- Orc Fighter - Squire's Shirt
(44,1147,1,'true'), -- Orc Fighter - Squire's Pants
(44,2368,1,'true'), -- Orc Fighter - Training Gloves
(44,2369,1,'false'), -- Orc Fighter - Squire's Sword
(49,425,1,'true'), -- Orc Mage - Apprentice's Tunic
(49,461,1,'true'), -- Orc Mage - Apprentice's Stockings
(49,2368,1,'true'), -- Orc Mage - Training Gloves
(53,10,1,'false'), -- Dwarf Fighter - Dagger
(53,1146,1,'true'), -- Dwarf Fighter - Squire's Shirt
(53,1147,1,'true'), -- Dwarf Fighter - Squire's Pants
(53,2370,1,'true'), -- Dwarf Fighter - Guild Member's Club
(123,10,1,'false'), -- Male Soldier - Dagger
(123,1146,1,'true'), -- Male Soldier - Squire's Shirt
(123,1147,1,'true'), -- Male Soldier - Squire's Pants
(123,2369,1,'true'), -- Male Soldier - Squire's Sword
(124,10,1,'false'), -- Female Soldier - Dagger
(124,1146,1,'true'), -- Female Soldier - Squire's Shirt
(124,1147,1,'true'), -- Female Soldier - Squire's Pants
(124,2369,1,'true'); -- Female Soldier - Squire's Sword