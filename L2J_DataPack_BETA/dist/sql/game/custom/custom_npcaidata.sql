CREATE TABLE IF NOT EXISTS `custom_npcaidata` (
  `npcId` mediumint(7) unsigned NOT NULL,
  `minSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '7',
  `maxSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '15',
  `primarySkillId` smallint(5) unsigned DEFAULT '0',
  `agroRange` smallint(4) unsigned NOT NULL DEFAULT '0',
  `canMove` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `targetable` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `showName` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `minRangeSkill` smallint(5) unsigned DEFAULT '0',
  `minRangeChance` tinyint(3) unsigned DEFAULT '0',
  `maxRangeSkill` smallint(5) unsigned DEFAULT '0',
  `maxRangeChance` tinyint(3) unsigned DEFAULT '0',
  `soulShot` smallint(4) unsigned DEFAULT '0',
  `spiritShot` smallint(4) unsigned DEFAULT '0',
  `spsChance` tinyint(3) unsigned DEFAULT '0',
  `ssChance` tinyint(3) unsigned DEFAULT '0',
  `aggro` smallint(4) unsigned NOT NULL DEFAULT '0',
  `isChaos` smallint(4) unsigned DEFAULT '0',
  `clan` varchar(40) DEFAULT NULL,
  `clanRange` smallint(4) unsigned DEFAULT '0',
  `enemyClan` varchar(40) DEFAULT NULL,
  `enemyRange` smallint(4) unsigned DEFAULT '0',
  `dodge` tinyint(3) unsigned DEFAULT '0',
  `aiType` varchar(8) NOT NULL DEFAULT 'fighter',
  PRIMARY KEY (`npcId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `custom_npcaidata` VALUES
(50007,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
(70010,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
(1000003,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
-- eventmod Elpies
(900100,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
-- eventmod Rabbits
(900101,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
(900102,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
-- eventmod Race
(900103,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter'),
(900104,7,15,0,1000,0,1,1,0,0,0,0,0,0,0,0,0,0,NULL,300,NULL,0,0,'fighter');