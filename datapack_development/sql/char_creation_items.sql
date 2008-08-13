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
  PRIMARY KEY  (`classId`,`itemId`)
) ;

--
-- Dumping data for table `char_creation_items`
--

INSERT INTO `char_creation_items` VALUES
(-1,5588,1), -- All classes - Tutorial Guide
(-1,9716,10), -- All classes - Scroll of Escape: Kamael Village
(-1,10650,5), -- All classes - Adventurer's Scroll of Escape
(0,10,1), -- Human Fighter - Dagger
(0,1146,1), -- Human Fighter - Squire's Shirt
(0,1147,1), -- Human Fighter - Squire's Pants
(0,2369,1), -- Human Fighter - Squire's Sword
(1,10,1), -- Warrior - Dagger
(1,1146,1), -- Warrior - Squire's Shirt
(1,1147,1), -- Warrior - Squire's Pants
(1,2369,1), -- Warrior - Squire's Sword
(2,10,1), -- Gladiator - Dagger
(2,1146,1), -- Gladiator - Squire's Shirt
(2,1147,1), -- Gladiator - Squire's Pants
(2,2369,1), -- Gladiator - Squire's Sword
(3,10,1), -- Warlord - Dagger
(3,1146,1), -- Warlord - Squire's Shirt
(3,1147,1), -- Warlord - Squire's Pants
(3,2369,1), -- Warlord - Squire's Sword
(4,10,1), -- Human Knight - Dagger
(4,1146,1), -- Human Knight - Squire's Shirt
(4,1147,1), -- Human Knight - Squire's Pants
(4,2369,1), -- Human Knight - Squire's Sword
(5,10,1), -- Paladin - Dagger
(5,1146,1), -- Paladin - Squire's Shirt
(5,1147,1), -- Paladin - Squire's Pants
(5,2369,1), -- Paladin - Squire's Sword
(6,10,1), -- Dark Avenger - Dagger
(6,1146,1), -- Dark Avenger - Squire's Shirt
(6,1147,1), -- Dark Avenger - Squire's Pants
(6,2369,1), -- Dark Avenger - Squire's Sword
(7,10,1), -- Rogue - Dagger
(7,1146,1), -- Rogue - Squire's Shirt
(7,1147,1), -- Rogue - Squire's Pants
(7,2369,1), -- Rogue - Squire's Sword
(8,10,1), -- Treasure Hunter - Dagger
(8,1146,1), -- Treasure Hunter - Squire's Shirt
(8,1147,1), -- Treasure Hunter - Squire's Pants
(8,2369,1), -- Treasure Hunter - Squire's Sword
(9,10,1), -- Hawkeye - Dagger
(9,1146,1), -- Hawkeye - Squire's Shirt
(9,1147,1), -- Hawkeye - Squire's Pants
(9,2369,1), -- Hawkeye - Squire's Sword
(10,6,1), -- Human Mage - Apprentice's Wand
(10,425,1), -- Human Mage - Apprentice's Tunic
(10,461,1), -- Human Mage - Apprentice's Stockings
(11,6,1), -- Human Wizard - Apprentice's Wand
(11,425,1), -- Human Wizard - Apprentice's Tunic
(11,461,1), -- Human Wizard - Apprentice's Stockings
(12,6,1), -- Sorcerer - Apprentice's Wand
(12,425,1), -- Sorcerer - Apprentice's Tunic
(12,461,1), -- Sorcerer - Apprentice's Stockings
(13,6,1), -- Necromancer - Apprentice's Wand
(13,425,1), -- Necromancer - Apprentice's Tunic
(13,461,1), -- Necromancer - Apprentice's Stockings
(14,6,1), -- Warlock - Apprentice's Wand
(14,425,1), -- Warlock - Apprentice's Tunic
(14,461,1), -- Warlock - Apprentice's Stockings
(15,6,1), -- Cleric - Apprentice's Wand
(15,425,1), -- Cleric - Apprentice's Tunic
(15,461,1), -- Cleric - Apprentice's Stockings
(16,6,1), -- Bishop - Apprentice's Wand
(16,425,1), -- Bishop - Apprentice's Tunic
(16,461,1), -- Bishop - Apprentice's Stockings
(17,6,1), -- Human Prophet - Apprentice's Wand
(17,425,1), -- Human Prophet - Apprentice's Tunic
(17,461,1), -- Human Prophet - Apprentice's Stockings
(18,10,1), -- Elf Fighter - Dagger
(18,1146,1), -- Elf Fighter - Squire's Shirt
(18,1147,1), -- Elf Fighter - Squire's Pants
(18,2369,1), -- Elf Fighter - Squire's Sword
(19,10,1), -- Elf Knight - Dagger
(19,1146,1), -- Elf Knight - Squire's Shirt
(19,1147,1), -- Elf Knight - Squire's Pants
(19,2369,1), -- Elf Knight - Squire's Sword
(20,10,1), -- Temple Knight - Dagger
(20,1146,1), -- Temple Knight - Squire's Shirt
(20,1147,1), -- Temple Knight - Squire's Pants
(20,2369,1), -- Temple Knight - Squire's Sword
(21,10,1), -- Swordsinger - Dagger
(21,1146,1), -- Swordsinger - Squire's Shirt
(21,1147,1), -- Swordsinger - Squire's Pants
(21,2369,1), -- Swordsinger - Squire's Sword
(22,10,1), -- Scout - Dagger
(22,1146,1), -- Scout - Squire's Shirt
(22,1147,1), -- Scout - Squire's Pants
(22,2369,1), -- Scout - Squire's Sword
(23,10,1), -- Plains Walker - Dagger
(23,1146,1), -- Plains Walker - Squire's Shirt
(23,1147,1), -- Plains Walker - Squire's Pants
(23,2369,1), -- Plains Walker - Squire's Sword
(24,10,1), -- Silver Ranger - Dagger
(24,1146,1), -- Silver Ranger - Squire's Shirt
(24,1147,1), -- Silver Ranger - Squire's Pants
(24,2369,1), -- Silver Ranger - Squire's Sword
(25,6,1), -- Elf Mage - Apprentice's Wand
(25,425,1), -- Elf Mage - Apprentice's Tunic
(25,461,1), -- Elf Mage - Apprentice's Stockings
(26,6,1), -- Elf Wizard - Apprentice's Wand
(26,425,1), -- Elf Wizard - Apprentice's Tunic
(26,461,1), -- Elf Wizard - Apprentice's Stockings
(27,6,1), -- Spellsinger - Apprentice's Wand
(27,425,1), -- Spellsinger - Apprentice's Tunic
(27,461,1), -- Spellsinger - Apprentice's Stockings
(28,6,1), -- Elemental Summoner - Apprentice's Wand
(28,425,1), -- Elemental Summoner - Apprentice's Tunic
(28,461,1), -- Elemental Summoner - Apprentice's Stockings
(29,6,1), -- Oracle - Apprentice's Wand
(29,425,1), -- Oracle - Apprentice's Tunic
(29,461,1), -- Oracle - Apprentice's Stockings
(30,6,1), -- Elder - Apprentice's Wand
(30,425,1), -- Elder - Apprentice's Tunic
(30,461,1), -- Elder - Apprentice's Stockings
(31,10,1), -- DE Fighter - Dagger
(31,1146,1), -- DE Fighter - Squire's Shirt
(31,1147,1), -- DE Fighter - Squire's Pants
(31,2369,1), -- DE Fighter - Squire's Sword
(32,10,1), -- Palus Knight - Dagger
(32,1146,1), -- Palus Knight - Squire's Shirt
(32,1147,1), -- Palus Knight - Squire's Pants
(32,2369,1), -- Palus Knight - Squire's Sword
(33,10,1), -- Shillien Knight - Dagger
(33,1146,1), -- Shillien Knight - Squire's Shirt
(33,1147,1), -- Shillien Knight - Squire's Pants
(33,2369,1), -- Shillien Knight - Squire's Sword
(34,10,1), -- Bladedancer - Dagger
(34,1146,1), -- Bladedancer - Squire's Shirt
(34,1147,1), -- Bladedancer - Squire's Pants
(34,2369,1), -- Bladedancer - Squire's Sword
(35,10,1), -- Assassin - Dagger
(35,1146,1), -- Assassin - Squire's Shirt
(35,1147,1), -- Assassin - Squire's Pants
(35,2369,1), -- Assassin - Squire's Sword
(36,10,1), -- Abyss Walker - Dagger
(36,1146,1), -- Abyss Walker - Squire's Shirt
(36,1147,1), -- Abyss Walker - Squire's Pants
(36,2369,1), -- Abyss Walker - Squire's Sword
(37,10,1), -- Phantom Ranger - Dagger
(37,1146,1), -- Phantom Ranger - Squire's Shirt
(37,1147,1), -- Phantom Ranger - Squire's Pants
(37,2369,1), -- Phantom Ranger - Squire's Sword
(38,6,1), -- DE Mage - Apprentice's Wand
(38,425,1), -- DE Mage - Apprentice's Tunic
(38,461,1), -- DE Mage - Apprentice's Stockings
(39,6,1), -- DE Wizard - Apprentice's Wand
(39,425,1), -- DE Wizard - Apprentice's Tunic
(39,461,1), -- DE Wizard - Apprentice's Stockings
(40,6,1), -- Spell Howler - Apprentice's Wand
(40,425,1), -- Spell Howler - Apprentice's Tunic
(40,461,1), -- Spell Howler - Apprentice's Stockings
(41,6,1), -- Phantom Summoner - Apprentice's Wand
(41,425,1), -- Phantom Summoner - Apprentice's Tunic
(41,461,1), -- Phantom Summoner - Apprentice's Stockings
(42,6,1), -- Shillien Oracle - Apprentice's Wand
(42,425,1), -- Shillien Oracle - Apprentice's Tunic
(42,461,1), -- Shillien Oracle - Apprentice's Stockings
(43,6,1), -- Shillien Elder - Apprentice's Wand
(43,425,1), -- Shillien Elder - Apprentice's Tunic
(43,461,1), -- Shillien Elder - Apprentice's Stockings
(44,1146,1), -- Orc Fighter - Squire's Shirt
(44,1147,1), -- Orc Fighter - Squire's Pants
(44,2368,1), -- Orc Fighter - Training Gloves
(44,2369,1), -- Orc Fighter - Squire's Sword
(45,1146,1), -- Raider - Squire's Shirt
(45,1147,1), -- Raider - Squire's Pants
(45,2368,1), -- Raider - Training Gloves
(45,2369,1), -- Raider - Squire's Sword
(46,1146,1), -- Destroyer - Squire's Shirt
(46,1147,1), -- Destroyer - Squire's Pants
(46,2368,1), -- Destroyer - Training Gloves
(46,2369,1), -- Destroyer - Squire's Sword
(47,1146,1), -- Monk - Squire's Shirt
(47,1147,1), -- Monk - Squire's Pants
(47,2368,1), -- Monk - Training Gloves
(47,2369,1), -- Monk - Squire's Sword
(48,1146,1), -- Tyrant - Squire's Shirt
(48,1147,1), -- Tyrant - Squire's Pants
(48,2368,1), -- Tyrant - Training Gloves
(48,2369,1), -- Tyrant - Squire's Sword
(49,425,1), -- Orc Mage - Apprentice's Tunic
(49,461,1), -- Orc Mage - Apprentice's Stockings
(49,2368,1), -- Orc Mage - Training Gloves
(50,425,1), -- Shaman - Apprentice's Tunic
(50,461,1), -- Shaman - Apprentice's Stockings
(50,2368,1), -- Shaman - Training Gloves
(51,425,1), -- Overlord - Apprentice's Tunic
(51,461,1), -- Overlord - Apprentice's Stockings
(51,2368,1), -- Overlord - Training Gloves
(52,425,1), -- Warcryer - Apprentice's Tunic
(52,461,1), -- Warcryer - Apprentice's Stockings
(52,2368,1), -- Warcryer - Training Gloves
(53,10,1), -- Dwarf Fighter - Dagger
(53,1146,1), -- Dwarf Fighter - Squire's Shirt
(53,1147,1), -- Dwarf Fighter - Squire's Pants
(53,2370,1), -- Dwarf Fighter - Guild Member's Club
(54,10,1), -- Scavenger - Dagger
(54,1146,1), -- Scavenger - Squire's Shirt
(54,1147,1), -- Scavenger - Squire's Pants
(54,2370,1), -- Scavenger - Guild Member's Club
(55,10,1), -- Bounty Hunter - Dagger
(55,1146,1), -- Bounty Hunter - Squire's Shirt
(55,1147,1), -- Bounty Hunter - Squire's Pants
(55,2370,1), -- Bounty Hunter - Guild Member's Club
(56,10,1), -- Artisan - Dagger
(56,1146,1), -- Artisan - Squire's Shirt
(56,1147,1), -- Artisan - Squire's Pants
(56,2370,1), -- Artisan - Guild Member's Club
(57,10,1), -- Warsmith - Dagger
(57,1146,1), -- Warsmith - Squire's Shirt
(57,1147,1), -- Warsmith - Squire's Pants
(57,2370,1), -- Warsmith - Guild Member's Club
(88,10,1), -- Duelist - Dagger
(88,1146,1), -- Duelist - Squire's Shirt
(88,1147,1), -- Duelist - Squire's Pants
(88,2369,1), -- Duelist - Squire's Sword
(89,10,1), -- DreadNought - Dagger
(89,1146,1), -- DreadNought - Squire's Shirt
(89,1147,1), -- DreadNought - Squire's Pants
(89,2369,1), -- DreadNought - Squire's Sword
(90,10,1), -- Phoenix Knight - Dagger
(90,1146,1), -- Phoenix Knight - Squire's Shirt
(90,1147,1), -- Phoenix Knight - Squire's Pants
(90,2369,1), -- Phoenix Knight - Squire's Sword
(91,10,1), -- Hell Knight - Dagger
(91,1146,1), -- Hell Knight - Squire's Shirt
(91,1147,1), -- Hell Knight - Squire's Pants
(91,2369,1), -- Hell Knight - Squire's Sword
(92,10,1), -- Sagittarius - Dagger
(92,1146,1), -- Sagittarius - Squire's Shirt
(92,1147,1), -- Sagittarius - Squire's Pants
(92,2369,1), -- Sagittarius - Squire's Sword
(93,10,1), -- Adventurer - Dagger
(93,1146,1), -- Adventurer - Squire's Shirt
(93,1147,1), -- Adventurer - Squire's Pants
(93,2369,1), -- Adventurer - Squire's Sword
(94,6,1), -- Archmage - Apprentice's Wand
(94,425,1), -- Archmage - Apprentice's Tunic
(94,461,1), -- Archmage - Apprentice's Stockings
(95,6,1), -- Soultaker - Apprentice's Wand
(95,425,1), -- Soultaker - Apprentice's Tunic
(95,461,1), -- Soultaker - Apprentice's Stockings
(96,6,1), -- Arcana Lord - Apprentice's Wand
(96,425,1), -- Arcana Lord - Apprentice's Tunic
(96,461,1), -- Arcana Lord - Apprentice's Stockings
(97,6,1), -- Cardinal - Apprentice's Wand
(97,425,1), -- Cardinal - Apprentice's Tunic
(97,461,1), -- Cardinal - Apprentice's Stockings
(98,6,1), -- Hierophant - Apprentice's Wand
(98,425,1), -- Hierophant - Apprentice's Tunic
(98,461,1), -- Hierophant - Apprentice's Stockings
(99,10,1), -- Eva Templar - Dagger
(99,1146,1), -- Eva Templar - Squire's Shirt
(99,1147,1), -- Eva Templar - Squire's Pants
(99,2369,1), -- Eva Templar - Squire's Sword
(100,10,1), -- Sword Muse - Dagger
(100,1146,1), -- Sword Muse - Squire's Shirt
(100,1147,1), -- Sword Muse - Squire's Pants
(100,2369,1), -- Sword Muse - Squire's Sword
(101,10,1), -- Wind Rider - Dagger
(101,1146,1), -- Wind Rider - Squire's Shirt
(101,1147,1), -- Wind Rider - Squire's Pants
(101,2369,1), -- Wind Rider - Squire's Sword
(102,10,1), -- Moonlight Sentinel - Dagger
(102,1146,1), -- Moonlight Sentinel - Squire's Shirt
(102,1147,1), -- Moonlight Sentinel - Squire's Pants
(102,2369,1), -- Moonlight Sentinel - Squire's Sword
(103,6,1), -- Mystic Muse - Apprentice's Wand
(103,425,1), -- Mystic Muse - Apprentice's Tunic
(103,461,1), -- Mystic Muse - Apprentice's Stockings
(104,6,1), -- Elemental Master - Apprentice's Wand
(104,425,1), -- Elemental Master - Apprentice's Tunic
(104,461,1), -- Elemental Master - Apprentice's Stockings
(105,6,1), -- Eva Saint - Apprentice's Wand
(105,425,1), -- Eva Saint - Apprentice's Tunic
(105,461,1), -- Eva Saint - Apprentice's Stockings
(106,10,1), -- Shillien Templar - Dagger
(106,1146,1), -- Shillien Templar - Squire's Shirt
(106,1147,1), -- Shillien Templar - Squire's Pants
(106,2369,1), -- Shillien Templar - Squire's Sword
(107,10,1), -- Spectral Dancer - Dagger
(107,1146,1), -- Spectral Dancer - Squire's Shirt
(107,1147,1), -- Spectral Dancer - Squire's Pants
(107,2369,1), -- Spectral Dancer - Squire's Sword
(108,10,1), -- Ghost Hunter - Dagger
(108,1146,1), -- Ghost Hunter - Squire's Shirt
(108,1147,1), -- Ghost Hunter - Squire's Pants
(108,2369,1), -- Ghost Hunter - Squire's Sword
(109,10,1), -- Ghost Sentinel - Dagger
(109,1146,1), -- Ghost Sentinel - Squire's Shirt
(109,1147,1), -- Ghost Sentinel - Squire's Pants
(109,2369,1), -- Ghost Sentinel - Squire's Sword
(110,6,1), -- Storm Screamer - Apprentice's Wand
(110,425,1), -- Storm Screamer - Apprentice's Tunic
(110,461,1), -- Storm Screamer - Apprentice's Stockings
(111,6,1), -- Spectral Master - Apprentice's Wand
(111,425,1), -- Spectral Master - Apprentice's Tunic
(111,461,1), -- Spectral Master - Apprentice's Stockings
(112,6,1), -- Shillen Saint - Apprentice's Wand
(112,425,1), -- Shillen Saint - Apprentice's Tunic
(112,461,1), -- Shillen Saint - Apprentice's Stockings
(113,1146,1), -- Titan - Squire's Shirt
(113,1147,1), -- Titan - Squire's Pants
(113,2368,1), -- Titan - Training Gloves
(113,2369,1), -- Titan - Squire's Sword
(114,1146,1), -- Grand Khauatari - Squire's Shirt
(114,1147,1), -- Grand Khauatari - Squire's Pants
(114,2368,1), -- Grand Khauatari - Training Gloves
(114,2369,1), -- Grand Khauatari - Squire's Sword
(115,425,1), -- Dominator - Apprentice's Tunic
(115,461,1), -- Dominator - Apprentice's Stockings
(115,2368,1), -- Dominator - Training Gloves
(116,425,1), -- Doomcryer - Apprentice's Tunic
(116,461,1), -- Doomcryer - Apprentice's Stockings
(116,2368,1), -- Doomcryer - Training Gloves
(117,10,1), -- Fortune Seeker - Dagger
(117,1146,1), -- Fortune Seeker - Squire's Shirt
(117,1147,1), -- Fortune Seeker - Squire's Pants
(117,2370,1), -- Fortune Seeker - Guild Member's Club
(118,10,1), -- Maestro - Dagger
(118,1146,1), -- Maestro - Squire's Shirt
(118,1147,1), -- Maestro - Squire's Pants
(118,2370,1), -- Maestro - Guild Member's Club
(123,10,1), -- Male Soldier - Dagger
(123,1146,1), -- Male Soldier - Squire's Shirt
(123,1147,1), -- Male Soldier - Squire's Pants
(123,2369,1), -- Male Soldier - Squire's Sword
(124,10,1), -- Female Soldier - Dagger
(124,1146,1), -- Female Soldier - Squire's Shirt
(124,1147,1), -- Female Soldier - Squire's Pants
(124,2369,1), -- Female Soldier - Squire's Sword
(125,10,1), -- Trooper - Dagger
(125,1146,1), -- Trooper - Squire's Shirt
(125,1147,1), -- Trooper - Squire's Pants
(125,2369,1), -- Trooper - Squire's Sword
(126,10,1), -- Warder - Dagger
(126,1146,1), -- Warder - Squire's Shirt
(126,1147,1), -- Warder - Squire's Pants
(126,2369,1), -- Warder - Squire's Sword
(127,10,1), -- Berserker - Dagger
(127,1146,1), -- Berserker - Squire's Shirt
(127,1147,1), -- Berserker - Squire's Pants
(127,2369,1), -- Berserker - Squire's Sword
(128,10,1), -- Male Soulbreaker - Dagger
(128,1146,1), -- Male Soulbreaker - Squire's Shirt
(128,1147,1), -- Male Soulbreaker - Squire's Pants
(128,2369,1), -- Male Soulbreaker - Squire's Sword
(129,10,1), -- Female Soulbreaker - Dagger
(129,1146,1), -- Female Soulbreaker - Squire's Shirt
(129,1147,1), -- Female Soulbreaker - Squire's Pants
(129,2369,1), -- Female Soulbreaker - Squire's Sword
(130,10,1), -- Arbalester - Dagger
(130,1146,1), -- Arbalester - Squire's Shirt
(130,1147,1), -- Arbalester - Squire's Pants
(130,2369,1), -- Arbalester - Squire's Sword
(131,10,1), -- Doombringer - Dagger
(131,1146,1), -- Doombringer - Squire's Shirt
(131,1147,1), -- Doombringer - Squire's Pants
(131,2369,1), -- Doombringer - Squire's Sword
(132,10,1), -- Male Soulhound - Dagger
(132,1146,1), -- Male Soulhound - Squire's Shirt
(132,1147,1), -- Male Soulhound - Squire's Pants
(132,2369,1), -- Male Soulhound - Squire's Sword
(133,10,1), -- Female Soulhound - Dagger
(133,1146,1), -- Female Soulhound - Squire's Shirt
(133,1147,1), -- Female Soulhound - Squire's Pants
(133,2369,1), -- Female Soulhound - Squire's Sword
(134,10,1), -- Trickster - Dagger
(134,1146,1), -- Trickster - Squire's Shirt
(134,1147,1), -- Trickster - Squire's Pants
(134,2369,1), -- Trickster - Squire's Sword
(135,10,1), -- Inspector - Dagger
(135,1146,1), -- Inspector - Squire's Shirt
(135,1147,1), -- Inspector - Squire's Pants
(135,2369,1), -- Inspector - Squire's Sword
(136,10,1), -- Judicator - Dagger
(136,1146,1), -- Judicator - Squire's Shirt
(136,1147,1), -- Judicator - Squire's Pants
(136,2369,1); -- Judicator - Squire's Sword
