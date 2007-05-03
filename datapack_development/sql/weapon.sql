-- 
-- Table structure for table `weapon`
-- 

DROP TABLE IF EXISTS `weapon`;
CREATE TABLE `weapon` (
  `item_id` decimal(11,0) NOT NULL default '0',
  `name` varchar(70) default NULL,
  `bodypart` varchar(15) default NULL,
  `crystallizable` varchar(5) default NULL,
  `weight` decimal(4,0) default NULL,
  `soulshots` decimal(2,0) default NULL,
  `spiritshots` decimal(1,0) default NULL,
  `material` varchar(11) default NULL,
  `crystal_type` varchar(4) default NULL,
  `p_dam` decimal(5,0) default NULL,
  `rnd_dam` decimal(2,0) default NULL,
  `weaponType` varchar(8) default NULL,
  `critical` decimal(2,0) default NULL,
  `hit_modify` decimal(6,5) default NULL,
  `avoid_modify` decimal(2,0) default NULL,
  `shield_def` decimal(3,0) default NULL,
  `shield_def_rate` decimal(2,0) default NULL,
  `atk_speed` decimal(3,0) default NULL,
  `mp_consume` decimal(2,0) default NULL,
  `m_dam` decimal(3,0) default NULL,
  `durability` decimal(3,0) default NULL,
  `price` decimal(11,0) default NULL,
  `crystal_count` int(4) default NULL,
  `sellable` varchar(5) default NULL,
  `dropable` varchar(5) default NULL,
  `destroyable` varchar(5) default NULL,
  `tradeable` varchar(5) default NULL,
  PRIMARY KEY  (`item_id`)
) TYPE=MyISAM;

-- 
-- Dumping data for table `weapon`
-- 

INSERT INTO `weapon` VALUES 
 ('1','Short Sword','rhand','false','1600','1','1','steel','none','8','10','sword','8','0.00000','0','0','0','379','0','6','-1','768','0','true','true','true','true'),
 ('2','Long Sword','rhand','false','1560','2','2','fine_steel','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','136000','0','true','true','true','true'),
 ('3','Broadsword','rhand','false','1590','1','1','steel','none','11','10','sword','8','0.00000','0','0','0','379','0','9','-1','12500','0','true','true','true','true'),
 ('4','Club','rhand','false','1870','1','1','wood','none','8','20','blunt','4','4.00000','0','0','0','379','0','6','-1','768','0','true','true','true','true'),
 ('5','Mace','rhand','false','1880','1','1','steel','none','11','20','blunt','4','4.00000','0','0','0','379','0','9','-1','12500','0','true','true','true','true'),
 ('6','Apprentice\'s Wand','rhand','false','1350','1','1','steel','none','5','20','blunt','4','4.00000','0','0','0','379','0','7','-1','138','0','false','false','true','false'),
 ('7','Apprentice\'s Rod','rhand','false','1330','1','1','wood','none','6','20','blunt','4','4.00000','0','0','0','379','0','8','-1','768','0','true','true','true','true'),
 ('8','Willow Staff','lrhand','false','1080','1','1','wood','none','11','20','bigblunt','4','4.00000','0','0','0','325','0','12','-1','12500','0','true','true','true','true'),
 ('9','Cedar Staff','lrhand','false','1090','2','2','wood','none','16','20','bigblunt','4','4.00000','0','0','0','325','0','16','-1','54100','0','true','true','true','true'),
 ('10','Dagger','rhand','false','1160','1','1','steel','none','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','138','0','false','false','true','false'),
 ('11','Bone Dagger','rhand','false','1150','1','1','bone','none','7','5','dagger','12','-3.00000','0','0','0','433','0','6','-1','768','0','true','true','true','true'),
 ('12','Knife','rhand','false','1140','1','1','bronze','none','10','5','dagger','12','-3.00000','0','0','0','433','0','9','-1','12500','0','true','true','true','true'),
 ('13','Short Bow','lrhand','false','1950','1','1','wood','none','16','5','bow','12','-3.00000','0','0','0','293','1','6','-1','768','0','true','true','true','true'),
 ('14','Bow','lrhand','false','1930','1','1','wood','none','23','5','bow','12','-3.00000','0','0','0','293','1','9','-1','12500','0','true','true','true','true'),
 ('15','Short Spear','lrhand','false','2140','2','2','steel','none','24','10','pole','8','-3.00000','0','0','0','325','0','17','-1','136000','0','true','true','true','true'),
 ('16','Long Spear','lrhand','false','2120','2','2','bronze','none','31','10','pole','8','-3.00000','0','0','0','325','0','21','-1','244000','0','true','true','true','true'),
 ('18','Leather Shield','lhand','false','1430','0','0','leather','none','0','0','none','0','0.00000','-8','47','20','0','0','0','-1','39','0','true','true','true','true'),
 ('19','Small Shield','lhand','false','1420','0','0','steel','none','0','0','none','0','0.00000','-8','56','20','0','0','0','-1','638','0','true','true','true','true'),
 ('20','Buckler','lhand','false','1410','0','0','wood','none','0','0','none','0','0.00000','-8','67','20','0','0','0','-1','2780','0','true','true','true','true'),
 ('66','Gladius','rhand','false','1570','2','2','steel','none','17','10','sword','8','0.00000','0','0','0','379','0','12','-1','54100','0','true','true','true','true'),
 ('67','Orcish Sword','rhand','false','1570','2','2','steel','none','17','10','sword','8','0.00000','0','0','0','379','0','12','-1','54100','0','true','true','true','true'),
 ('68','Falchion','rhand','false','1530','2','2','bronze','none','31','10','sword','8','0.00000','0','0','0','379','0','21','-1','244000','0','true','true','true','true'),
 ('69','Bastard Sword','rhand','true','1510','2','2','fine_steel','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','644000','1170','true','true','true','true'),
 ('70','Claymore','lrhand','true','2090','3','3','fine_steel','d','112','10','bigsword','8','0.00000','0','0','0','325','0','54','-1','1800000','3272','true','true','true','true'),
 ('71','Flamberge','lrhand','true','2010','2','2','bronze','c','130','10','bigsword','8','0.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('72','Stormbringer','rhand','true','1430','2','2','fine_steel','c','107','10','sword','8','0.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('73','Shamshir','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('74','Katana','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('75','Caliburs','rhand','true','1400','3','3','oriharukon','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('76','Sword of Delusion','rhand','true','1400','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('77','Tsurugi','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('78','Great Sword','lrhand','true','1930','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('79','Sword of Damascus','rhand','true','1350','1','1','damascus','b','194','10','sword','8','0.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('80','Tallum Blade','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('81','Dragon Slayer','lrhand','true','1840','1','1','bone','a','282','10','bigsword','8','0.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('82','God\'s Blade','rhand','true','1300','1','1','adamantaite','s','257','10','sword','8','0.00000','0','0','0','379','0','124','-1','38000000','1900','false','false','true','false'),
 ('83','Sword of Magic','rhand','true','970','2','2','bronze','d','43','10','sword','8','0.00000','0','0','0','379','0','45','-1','716000','1301','true','true','true','true'),
 ('84','Homunkulus\'s Sword','rhand','true','950','3','3','blood_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('85','Phantom Sword','rhand','true','860','1','1','oriharukon','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('86','Tomahawk','rhand','true','1780','2','2','bronze','d','51','20','blunt','4','4.00000','0','0','0','379','0','32','-1','644000','1170','true','true','true','true'),
 ('87','Iron Hammer','rhand','false','1850','2','2','steel','none','31','20','blunt','4','4.00000','0','0','0','379','0','21','-1','244000','0','true','true','true','true'),
 ('88','Morning Star','rhand','true','1720','3','3','steel','d','79','20','blunt','4','4.00000','0','0','0','379','0','47','-1','1400000','2545','true','true','true','true'),
 ('89','Big Hammer','rhand','true','1710','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('90','Goat Head Staff','lrhand','true','1000','3','3','wood','d','77','20','bigblunt','4','4.00000','0','0','0','325','0','63','-1','1400000','2545','true','true','true','true'),
 ('91','Heavy War Axe','rhand','true','1620','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('92','Sprite\'s Staff','lrhand','true','960','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('93','Winged Spear','lrhand','true','2060','3','3','fine_steel','d','79','10','pole','8','-3.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('94','Bec de Corbin','lrhand','true','2020','2','2','fine_steel','c','122','10','pole','8','-3.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('95','Poleaxe','lrhand','true','2010','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('96','Scythe','lrhand','true','2040','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('97','Lance','lrhand','true','1920','1','1','blood_steel','b','194','10','pole','8','-3.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('98','Halberd','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('99','Apprentice\'s Spellbook','rhand','false','650','1','1','paper','none','9','10','etc','8','0.00000','0','0','0','379','0','12','-1','12500','0','true','true','true','true'),
 ('100','Voodoo Doll','rhand','false','630','2','2','wood','none','25','10','etc','8','0.00000','0','0','0','379','0','28','-1','244000','0','true','true','true','true'),
 ('101','Scroll of Wisdom','rhand','true','610','2','2','paper','d','32','10','etc','8','0.00000','0','0','0','379','0','35','-1','409000','743','true','true','true','true'),
 ('102','Round Shield','lhand','false','1390','0','0','steel','none','0','0','none','0','0.00000','-8','79','20','0','0','0','-1','7110','0','true','true','true','true'),
 ('103','Tower Shield','lhand','true','1240','0','0','fine_steel','c','0','0','none','0','0.00000','-8','190','20','0','0','0','-1','258000','103','true','true','true','true'),
 ('104','Shield of Victory','lhand','true','6400','0','0','oriharukon','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('105','Implosion Shield','lhand','true','4800','0','0','damascus','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('106','Dark Dragon Shield','lhand','true','4800','0','0','damascus','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('107','Composite Shield','lhand','true','1230','0','0','fine_steel','c','0','0','none','0','0.00000','-8','190','20','0','0','0','-1','258000','103','true','true','true','true'),
 ('108','Masterpiece Shield','lhand','true','5600','0','0','oriharukon','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('109','Shield of Solar Eclipse','lhand','true','4800','0','0','blood_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('110','Doom Shield','lhand','true','1200','0','0','fine_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('111','Shield of Pledge','lhand','true','4800','0','0','fine_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('120','Sword of Reflection','rhand','false','1550','2','2','bronze','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','136000','0','true','true','true','true'),
 ('121','Sword of Watershadow','rhand','false','1540','2','2','bronze','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','136000','0','true','true','true','true'),
 ('122','Handmade Sword','rhand','false','1570','2','2','bronze','none','17','10','sword','8','0.00000','0','0','0','379','0','12','-1','54100','0','true','true','true','true'),
 ('123','Saber','rhand','true','1520','2','2','steel','d','40','10','sword','8','0.00000','0','0','0','379','0','26','-1','409000','743','true','true','true','true'),
 ('124','Two-Handed Sword','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','967000','1758','true','true','true','true'),
 ('125','Spinebone Sword','rhand','true','1510','2','2','bone','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','644000','1170','true','true','true','true'),
 ('126','Artisan\'s Sword','rhand','true','1500','2','2','steel','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','644000','1170','true','true','true','true'),
 ('127','Crimson Sword','rhand','true','1490','2','2','fine_steel','d','64','10','sword','8','0.00000','0','0','0','379','0','39','-1','967000','1758','true','true','true','true'),
 ('128','Knight\'s Sword','rhand','true','1500','2','2','steel','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','644000','1170','true','true','true','true'),
 ('129','Sword of Revolution','rhand','true','1450','3','3','fine_steel','d','79','10','sword','8','0.00000','0','0','0','379','0','47','-1','1400000','2545','true','true','true','true'),
 ('130','Elven Sword','rhand','true','1470','2','2','mithril','d','64','10','sword','8','0.00000','0','0','0','379','0','39','-1','967000','1758','true','true','true','true'),
 ('131','Spirit Sword','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('132','Sword of Limit','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('133','Raid Sword','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('134','Sword of Nightmare','rhand','true','1400','3','3','blood_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('135','Samurai Longsword','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('136','Blade of Serenity','rhand','true','1370','1','1','mithril','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('137','Mist Sword','rhand','true','1370','1','1','mithril','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('138','Sword of Desert','rhand','true','1370','1','1','blood_steel','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('139','Dusk Sword','rhand','true','1370','1','1','blood_steel','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('140','Eminence Sword','rhand','true','1370','1','1','chrysolite','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('141','Hazard Sword','rhand','true','1370','1','1','blood_steel','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('142','Keshanberk','rhand','true','1370','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('143','Sword of Mystic','rhand','true','980','2','2','fine_steel','d','43','10','sword','8','0.00000','0','0','0','379','0','45','-1','716000','1301','true','true','true','true'),
 ('144','Sword of Occult','rhand','true','970','2','2','fine_steel','d','43','10','sword','8','0.00000','0','0','0','379','0','45','-1','716000','1301','true','true','true','true'),
 ('145','Sword of Whispering Death','rhand','true','920','3','3','fine_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('146','Ghoulbane','rhand','true','910','1','1','damascus','b','140','10','sword','8','0.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('147','Tear of Darkness','rhand','true','830','1','1','blood_steel','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('148','Sword of Valhalla','rhand','true','900','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('149','Sword of Life','rhand','true','840','1','1','mithril','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('150','Elemental Sword','rhand','true','830','1','1','mithril','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('151','Sword of Miracles','rhand','true','840','1','1','adamantaite','a','186','10','sword','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('152','Heavy Chisel','rhand','false','1890','1','1','steel','none','10','20','blunt','4','4.00000','0','0','0','379','0','8','-1','8070','0','true','true','true','true'),
 ('153','Sickle','rhand','false','1850','1','1','steel','none','12','20','blunt','4','4.00000','0','0','0','379','0','9','-1','18500','0','true','true','true','true'),
 ('154','Dwarven Mace','rhand','false','1860','2','2','bronze','none','17','20','blunt','4','4.00000','0','0','0','379','0','12','-1','54100','0','true','true','true','true'),
 ('155','Buzdygan','rhand','false','1800','2','2','steel','none','31','20','blunt','4','4.00000','0','0','0','379','0','21','-1','244000','0','true','true','true','true'),
 ('156','Hand Axe','rhand','true','1820','2','2','steel','d','40','20','blunt','4','4.00000','0','0','0','379','0','26','-1','409000','743','true','true','true','true'),
 ('157','Spiked Club','rhand','true','1750','2','2','steel','d','64','20','blunt','4','4.00000','0','0','0','379','0','39','-1','967000','1758','true','true','true','true'),
 ('158','Tarbar','rhand','true','1730','3','3','fine_steel','d','79','20','blunt','4','4.00000','0','0','0','379','0','47','-1','1400000','2545','true','true','true','true'),
 ('159','Bonebreaker','rhand','true','1720','3','3','fine_steel','d','92','20','blunt','4','4.00000','0','0','0','379','0','54','-1','1800000','3272','true','true','true','true'),
 ('160','Battle Axe','rhand','true','1720','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('161','Silver Axe','rhand','true','1690','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('162','War Axe','rhand','true','1660','3','3','fine_steel','c','139','20','blunt','4','4.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('163','_','rhand','true','4000','1','1','mithril','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('164','Elysian','rhand','true','1580','1','1','oriharukon','a','232','20','blunt','4','4.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('165','Yablonski\'s Hammer','rhand','true','1570','1','1','adamantaite','s','251','20','blunt','4','4.00000','0','0','0','379','0','121','-1','35300000','1765','true','true','true','true'),
 ('166','Heavy Mace','rhand','true','1800','2','2','steel','d','40','20','blunt','4','4.00000','0','0','0','379','0','26','-1','409000','743','true','true','true','true'),
 ('167','Scalpel','rhand','true','1810','2','2','steel','d','40','20','blunt','4','4.00000','0','0','0','379','0','26','-1','409000','743','true','true','true','true'),
 ('168','Work Hammer','rhand','true','1790','2','2','steel','d','40','20','blunt','4','4.00000','0','0','0','379','0','26','-1','409000','743','true','true','true','true'),
 ('169','Skull Breaker','rhand','true','1740','3','3','fine_steel','d','79','20','blunt','4','4.00000','0','0','0','379','0','47','-1','1400000','2545','true','true','true','true'),
 ('170','_','rhand','true','6400','1','1','fine_steel','a','258','20','blunt','4','4.00000','0','0','0','379','0','86','-1','16600000','7','true','true','true','true'),
 ('171','Deadman\'s Glory','rhand','true','1600','1','1','bone','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('172','Heavy Bone Club','rhand','true','1730','3','3','bone','d','79','20','blunt','4','4.00000','0','0','0','379','0','47','-1','1400000','2545','true','true','true','true'),
 ('173','Skull Graver','rhand','true','1640','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('174','Nirvana Axe','rhand','true','1150','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('175','Art of Battle Axe','rhand','true','1570','1','1','chrysolite','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('176','Journeyman\'s Staff','lrhand','false','1070','2','2','wood','none','23','20','bigblunt','4','4.00000','0','0','0','325','0','22','-1','136000','0','true','true','true','true'),
 ('177','Mage Staff','lrhand','false','1050','2','2','wood','none','30','20','bigblunt','4','4.00000','0','0','0','325','0','28','-1','244000','0','true','true','true','true'),
 ('178','Bone Staff','lrhand','true','1060','2','2','bone','d','39','20','bigblunt','4','4.00000','0','0','0','325','0','35','-1','409000','743','true','true','true','true'),
 ('179','Mace of Prayer','rhand','true','1300','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','644000','1170','true','true','true','true'),
 ('180','Mace of Judgment','rhand','true','1280','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','644000','1170','true','true','true','true'),
 ('181','Mace of Miracle','rhand','true','1250','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','644000','1170','true','true','true','true'),
 ('182','Doom Hammer','rhand','true','1200','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','644000','1170','true','true','true','true'),
 ('183','Mystic Staff','lrhand','true','1040','2','2','wood','d','50','20','bigblunt','4','4.00000','0','0','0','325','0','43','-1','644000','1170','true','true','true','true'),
 ('184','Conjuror\'s Staff','lrhand','true','1030','2','2','oriharukon','d','50','20','bigblunt','4','4.00000','0','0','0','325','0','43','-1','644000','1170','true','true','true','true'),
 ('185','Staff of Mana','lrhand','true','1040','2','2','wood','d','50','20','bigblunt','4','4.00000','0','0','0','325','0','43','-1','644000','1170','true','true','true','true'),
 ('186','Staff of Magic','lrhand','true','1020','2','2','wood','d','62','20','bigblunt','4','4.00000','0','0','0','325','0','52','-1','967000','1758','true','true','true','true'),
 ('187','Atuba Hammer','lrhand','true','1010','3','3','fine_steel','d','90','20','bigblunt','4','4.00000','0','0','0','325','0','72','-1','1800000','3272','true','true','true','true'),
 ('188','Ghost Staff','lrhand','true','1000','3','3','wood','d','90','20','bigblunt','4','4.00000','0','0','0','325','0','72','-1','1800000','3272','true','true','true','true'),
 ('189','Staff of Life','rhand','true','1180','3','3','wood','d','74','20','blunt','4','4.00000','0','0','0','379','0','72','-1','1800000','3272','true','true','true','true'),
 ('190','Atuba Mace','lrhand','true','1010','3','3','fine_steel','d','90','20','bigblunt','4','4.00000','0','0','0','325','0','72','-1','1800000','3272','true','true','true','true'),
 ('191','Heavy Doom Hammer','lrhand','true','1020','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('192','Crystal Staff','lrhand','true','1020','2','2','crystal','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('193','Stick of Faith','rhand','true','1160','2','2','wood','c','85','20','blunt','4','4.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('194','Heavy Doom Axe','lrhand','true','1010','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('195','Cursed Staff','lrhand','true','1000','2','2','wood','c','119','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','2870000','1148','true','true','true','true'),
 ('196','Stick of Eternity','rhand','true','1130','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('197','Paradia Staff','lrhand','true','1010','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('198','Inferno Staff','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('199','Pa\'agrian Hammer','lrhand','true','1010','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('200','Sage\'s Staff','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('201','Club of Nature','rhand','true','1100','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('202','Mace of The Underworld','rhand','true','1090','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('203','Pa\'agrian Axe','lrhand','true','990','3','3','blood_steel','c','141','20','bigblunt','4','4.00000','0','0','0','325','0','104','-1','4780000','1912','true','true','true','true'),
 ('204','Deadman\'s Staff','lrhand','true','1010','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('205','Ghoul\'s Staff','lrhand','true','1000','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('206','Demon\'s Staff','lrhand','true','990','3','3','wood','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('207','Staff of Phantom','lrhand','true','980','1','1','oriharukon','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('208','Staff of Seal','lrhand','true','970','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('209','Divine Staff','lrhand','true','960','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('210','Staff of Evil Spirits','lrhand','true','930','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('211','Staff of Nobility','lrhand','true','910','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('212','Dasparion\'s Staff','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('213','Branch of The Mother Tree','lrhand','true','900','1','1','wood','a','226','20','bigblunt','4','4.00000','0','0','0','325','0','152','-1','27000000','2160','true','true','true','true'),
 ('214','The Staff','lrhand','true','910','1','1','wood','s','245','20','bigblunt','4','4.00000','0','0','0','325','0','162','-1','35300000','1765','true','true','true','true'),
 ('215','Doom Dagger','rhand','false','1130','1','1','steel','none','10','5','dagger','12','-3.00000','0','0','0','433','0','9','-1','12500','0','true','true','true','true'),
 ('216','Dirk','rhand','false','1130','2','2','steel','none','15','5','dagger','12','-3.00000','0','0','0','433','0','12','-1','54100','0','true','true','true','true'),
 ('217','Shining Knife','rhand','false','1120','2','2','mithril','none','21','5','dagger','12','-3.00000','0','0','0','433','0','17','-1','136000','0','true','true','true','true'),
 ('218','Throwing Knife','rhand','false','1120','2','2','fine_steel','none','21','5','dagger','12','-3.00000','0','0','0','433','0','17','-1','136000','0','true','true','true','true'),
 ('219','Sword Breaker','rhand','false','1110','2','2','steel','none','27','5','dagger','12','-3.00000','0','0','0','433','0','21','-1','244000','0','true','true','true','true'),
 ('220','Crafted Dagger','rhand','true','1100','2','2','chrysolite','d','35','5','dagger','12','-3.00000','0','0','0','433','0','26','-1','409000','743','true','true','true','true'),
 ('221','Assassin Knife','rhand','true','1100','2','2','steel','d','35','5','dagger','12','-3.00000','0','0','0','433','0','26','-1','409000','743','true','true','true','true'),
 ('222','Poniard Dagger','rhand','true','1090','2','2','steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','32','-1','644000','1170','true','true','true','true'),
 ('223','Kukuri','rhand','true','1080','2','2','fine_steel','d','56','5','dagger','12','-3.00000','0','0','0','433','0','39','-1','967000','1758','true','true','true','true'),
 ('224','Maingauche','rhand','true','1070','3','3','fine_steel','d','69','5','dagger','12','-3.00000','0','0','0','433','0','47','-1','1400000','2545','true','true','true','true'),
 ('225','Mithril Dagger','rhand','true','1060','3','3','mithril','d','80','5','dagger','12','-3.00000','0','0','0','433','0','54','-1','1800000','3272','true','true','true','true'),
 ('226','Cursed Dagger','rhand','true','1040','2','2','mithril','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('227','Stiletto','rhand','true','1030','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','-1','2870000','1148','true','true','true','true'),
 ('228','Crystal Dagger','rhand','true','1000','3','3','crystal','c','136','5','dagger','12','-3.00000','0','0','0','433','0','83','-1','6130000','2452','true','true','true','true'),
 ('229','Kris','rhand','true','980','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','8680000','1157','true','true','true','true'),
 ('230','Wolverine Needle','rhand','true','1040','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('231','Grace Dagger','rhand','true','1020','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('232','Dark Elven Dagger','rhand','true','1050','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('233','Dark Screamer','rhand','true','1010','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('234','Demon Dagger','rhand','true','970','1','1','blood_steel','b','170','5','dagger','12','-3.00000','0','0','0','433','0','99','-1','13100000','1746','true','true','true','true'),
 ('235','Bloody Orchid','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('236','Soul Separator','rhand','true','950','1','1','oriharukon','a','203','5','dagger','12','-3.00000','0','0','0','433','0','114','-1','27000000','2160','true','true','true','true'),
 ('237','Dragon\'s Tooth','rhand','true','950','1','1','bone','s','220','5','dagger','12','-3.00000','0','0','0','433','0','121','-1','35300000','1765','true','true','true','true'),
 ('238','Dagger of Mana','rhand','true','750','2','2','fine_steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','-1','967000','1758','true','true','true','true'),
 ('239','Mystic Knife','rhand','true','760','2','2','fine_steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','-1','967000','1758','true','true','true','true'),
 ('240','Conjurer\'s Knife','rhand','true','750','2','2','fine_steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','-1','967000','1758','true','true','true','true'),
 ('241','Shilen Knife','rhand','true','750','2','2','bronze','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','-1','967000','1758','true','true','true','true'),
 ('242','Soulfire Dirk','rhand','true','750','2','2','fine_steel','c','86','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','2870000','1148','true','true','true','true'),
 ('243','Hell Knife','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('244','Elven Fighter Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','22','0','true','true','true','true'),
 ('245','Dark Fighter Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','33','0','true','true','true','true'),
 ('246','Human Fighter Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','44','0','true','true','true','true'),
 ('247','Dwarven Fighter Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','56','0','true','true','true','true'),
 ('248','Orc Fighter Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','67','0','true','true','true','true'),
 ('249','Elven Mystic Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','11','0','true','true','true','true'),
 ('250','Dark Mystic Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','22','0','true','true','true','true'),
 ('251','Human Mystic Fist','rhand','false','0','0','0','leather','none','0','5','fist','4','4.00000','0','0','0','325','0','0','-1','33','0','true','true','true','true'),
 ('252','Orc Shaman Fist','rhand','false','0','0','0','leather','none','0','10','fist','8','0.00000','0','0','0','379','0','0','-1','44','0','true','true','true','true'),
 ('253','Spiked Gloves','lrhand','false','1590','1','1','leather','none','10','5','dualfist','4','4.00000','0','0','0','325','0','6','-1','768','0','true','true','true','true'),
 ('254','Iron Gloves','lrhand','false','1580','1','1','steel','none','13','5','dualfist','4','4.00000','0','0','0','325','0','9','-1','12500','0','true','true','true','true'),
 ('255','Fox Claw Gloves','lrhand','false','1560','2','2','steel','none','21','5','dualfist','4','4.00000','0','0','0','325','0','12','-1','54100','0','true','true','true','true'),
 ('256','Cestus','lrhand','false','1570','2','2','steel','none','29','5','dualfist','4','4.00000','0','0','0','325','0','17','-1','136000','0','true','true','true','true'),
 ('257','Viper\'s Fang','lrhand','false','1560','2','2','steel','none','38','5','dualfist','4','4.00000','0','0','0','325','0','21','-1','244000','0','true','true','true','true'),
 ('258','Bagh-Nakh','lrhand','true','1540','2','2','steel','d','49','5','dualfist','4','4.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('259','Single-Edged Jamadhr','lrhand','true','1550','2','2','steel','d','62','5','dualfist','4','4.00000','0','0','0','325','0','32','-1','644000','1170','true','true','true','true'),
 ('260','Triple-Edged Jamadhr','lrhand','true','1540','2','2','fine_steel','d','78','5','dualfist','4','4.00000','0','0','0','325','0','39','-1','967000','1758','true','true','true','true'),
 ('261','Bich\'Hwa','lrhand','true','1510','3','3','fine_steel','d','96','5','dualfist','4','4.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('262','Scallop Jamadhr','lrhand','true','1520','3','3','fine_steel','d','112','5','dualfist','4','4.00000','0','0','0','325','0','54','-1','1800000','3272','true','true','true','true'),
 ('263','Chakram','lrhand','true','1490','2','2','fine_steel','c','130','5','dualfist','4','4.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('264','Pata','lrhand','true','1440','1','1','fine_steel','b','204','5','dualfist','4','4.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('265','Fisted Blade','lrhand','true','1480','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('266','Great Pata','lrhand','true','1460','3','3','chrysolite','c','190','5','dualfist','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('267','Arthro Nail','lrhand','true','1420','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('268','Bellion Cestus','lrhand','true','1390','1','1','mithril','b','236','5','dualfist','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('269','Blood Tornado','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('270','Dragon Grinder','lrhand','true','1350','1','1','adamantaite','a','282','5','dualfist','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('271','Hunting Bow','lrhand','false','1910','6','2','wood','none','34','5','bow','12','-3.00000','0','0','0','293','2','12','-1','54100','0','true','true','true','true'),
 ('272','Forest Bow','lrhand','false','1900','7','2','wood','none','49','5','bow','12','-3.00000','0','0','0','293','3','17','-1','136000','0','true','true','true','true'),
 ('273','Composition Bow','lrhand','false','1880','7','2','steel','none','64','5','bow','12','-3.00000','0','0','0','293','3','21','-1','244000','0','true','true','true','true'),
 ('274','Strengthened Bow','lrhand','true','1870','6','2','steel','d','82','5','bow','12','-3.00000','0','0','0','293','3','26','-1','409000','743','true','true','true','true'),
 ('275','Long Bow','lrhand','true','1830','6','2','steel','d','114','5','bow','12','-3.00000','0','0','0','227','4','35','-1','644000','1170','true','true','true','true'),
 ('276','Elven Bow','lrhand','true','1850','6','2','mithril','d','105','5','bow','12','-3.00000','0','0','0','293','4','32','-1','644000','1170','true','true','true','true'),
 ('277','Dark Elven Bow','lrhand','true','1830','6','2','steel','d','105','5','bow','12','-3.00000','0','0','0','293','4','32','-1','644000','1170','true','true','true','true'),
 ('278','Gastraphetes','lrhand','true','1840','8','2','fine_steel','d','132','5','bow','12','-3.00000','0','0','0','293','4','39','-1','967000','1758','true','true','true','true'),
 ('279','Strengthened Long Bow','lrhand','true','1820','10','3','fine_steel','d','179','5','bow','12','-3.00000','0','0','0','227','5','51','-1','1400000','2545','true','true','true','true'),
 ('280','Light Crossbow','lrhand','true','1810','10','3','fine_steel','d','191','5','bow','12','-3.00000','0','0','0','293','6','54','-1','1800000','3272','true','true','true','true'),
 ('281','Crystallized Ice Bow','lrhand','true','1790','8','2','crystal','c','220','5','bow','12','-3.00000','0','0','0','293','6','61','-1','2290000','916','true','true','true','true'),
 ('282','Elemental Bow','lrhand','true','1770','8','2','fine_steel','c','277','5','bow','12','-3.00000','0','0','0','227','7','75','-1','2870000','1148','true','true','true','true'),
 ('283','Akat Long Bow','lrhand','true','1740','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','-1','4300000','1720','true','true','true','true'),
 ('284','Dark Elven Long Bow','lrhand','true','1720','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','-1','8680000','1157','true','true','true','true'),
 ('285','Noble Elven Bow','lrhand','true','1760','8','2','mithril','c','252','5','bow','12','-3.00000','0','0','0','293','7','68','-1','2870000','1148','true','true','true','true'),
 ('286','Eminence Bow','lrhand','true','1720','10','3','chrysolite','c','323','5','bow','12','-3.00000','0','0','0','293','8','83','-1','6130000','2452','true','true','true','true'),
 ('287','Bow of Peril','lrhand','true','1700','3','1','blood_steel','b','400','5','bow','12','-3.00000','0','0','0','293','9','99','-1','13100000','1746','true','true','true','true'),
 ('288','Carnage Bow','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('289','Soul Bow','lrhand','true','1660','2','1','oriharukon','a','528','5','bow','12','-3.00000','0','0','0','227','10','125','-1','27000000','2160','true','true','true','true'),
 ('290','The Bow','lrhand','true','1650','1','1','adamantaite','s','519','5','bow','12','-3.00000','0','0','0','293','10','121','-1','35300000','1765','true','true','true','true'),
 ('291','Trident','lrhand','true','2100','2','2','bronze','d','40','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('292','Pike','lrhand','true','2090','2','2','steel','d','51','10','pole','8','-3.00000','0','0','0','325','0','32','-1','644000','1170','true','true','true','true'),
 ('293','War Hammer','lrhand','true','2080','2','2','fine_steel','d','64','10','pole','8','-3.00000','0','0','0','325','0','39','-1','967000','1758','true','true','true','true'),
 ('294','War Pick','lrhand','true','2050','3','3','fine_steel','d','79','10','pole','8','-3.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('295','Dwarven Trident','lrhand','true','2090','2','2','steel','d','51','10','pole','8','-3.00000','0','0','0','325','0','32','-1','644000','1170','true','true','true','true'),
 ('296','Dwarven Pike','lrhand','true','2070','2','2','fine_steel','d','64','10','pole','8','-3.00000','0','0','0','325','0','39','-1','967000','1758','true','true','true','true'),
 ('297','Glaive','lrhand','true','2050','3','3','fine_steel','d','92','10','pole','8','-3.00000','0','0','0','325','0','54','-1','1800000','3272','true','true','true','true'),
 ('298','Orcish Glaive','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('299','Orcish Poleaxe','lrhand','true','1950','3','3','blood_steel','c','156','10','pole','8','-3.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('300','Great Axe','lrhand','true','1940','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('301','Scorpion','lrhand','true','1990','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('302','Body Slasher','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('303','Widow Maker','lrhand','true','1980','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('304','Orcish Halberd','lrhand','true','1880','1','1','blood_steel','a','219','10','pole','8','-3.00000','0','0','0','325','0','109','-1','21300000','1704','true','true','true','true'),
 ('305','Tallum Glaive','lrhand','true','1840','1','1','chrysolite','a','232','10','pole','8','-3.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('306','Dragon Claw Axe','lrhand','true','1820','1','1','bone','s','251','10','pole','8','-3.00000','0','0','0','325','0','121','-1','35300000','1765','true','true','true','true'),
 ('307','Aurakyria Lance','lrhand','true','1800','1','1','adamantaite','s','269','10','pole','8','-3.00000','0','0','0','325','0','128','-1','0','0','false','false','true','false'),
 ('308','Buffalo\'s Horn','rhand','false','660','1','1','steel','none','6','10','etc','8','0.00000','0','0','0','379','0','8','-1','768','0','true','true','true','true'),
 ('309','Tears of Eva','rhand','false','630','2','2','steel','none','19','10','etc','8','0.00000','0','0','0','379','0','22','-1','136000','0','true','true','true','true'),
 ('310','Relic of The Saints','rhand','false','610','2','2','steel','none','19','10','etc','8','0.00000','0','0','0','379','0','22','-1','136000','0','true','true','true','true'),
 ('311','Crucifix of Blessing','rhand','false','620','2','2','steel','none','25','10','etc','8','0.00000','0','0','0','379','0','28','-1','244000','0','true','true','true','true'),
 ('312','Branch of Life','rhand','true','620','2','2','steel','d','32','10','etc','8','0.00000','0','0','0','379','0','35','-1','409000','743','true','true','true','true'),
 ('313','Temptation of Abyss','rhand','true','610','2','2','steel','d','32','10','etc','8','0.00000','0','0','0','379','0','35','-1','409000','743','true','true','true','true'),
 ('314','Proof of Revenge','rhand','true','600','2','2','steel','d','32','10','etc','8','0.00000','0','0','0','379','0','35','-1','409000','743','true','true','true','true'),
 ('315','Divine Tome','rhand','true','570','2','2','steel','d','41','10','etc','8','0.00000','0','0','0','379','0','43','-1','644000','1170','true','true','true','true'),
 ('316','Blood of Saints','rhand','true','580','2','2','steel','d','51','10','etc','8','0.00000','0','0','0','379','0','52','-1','967000','1758','true','true','true','true'),
 ('317','Tome of Blood','rhand','true','570','2','2','steel','d','51','10','etc','8','0.00000','0','0','0','379','0','52','-1','967000','1758','true','true','true','true'),
 ('318','Crucifix of Blood','rhand','true','540','3','3','steel','d','63','10','etc','8','0.00000','0','0','0','379','0','63','-1','1400000','2545','true','true','true','true'),
 ('319','Eye of Infinity','rhand','true','1600','3','3','steel','d','63','10','etc','8','0.00000','0','0','0','379','0','63','-1','1400000','2545','true','true','true','true'),
 ('320','Blue Crystal Skull','rhand','true','1600','3','3','steel','d','67','10','etc','8','0.00000','0','0','0','379','0','66','-1','1520000','2763','true','true','true','true'),
 ('321','Demon Fangs','rhand','true','1600','3','3','steel','d','67','10','etc','8','0.00000','0','0','0','379','0','66','-1','1520000','2763','true','true','true','true'),
 ('322','Vajra Wands','rhand','true','1600','3','3','steel','d','74','10','etc','8','0.00000','0','0','0','379','0','72','-1','1800000','3272','true','true','true','true'),
 ('323','Ancient Reagent','rhand','true','1600','3','3','steel','d','74','10','etc','8','0.00000','0','0','0','379','0','72','-1','1800000','3272','true','true','true','true'),
 ('324','Tears of Fairy','rhand','true','1600','2','2','steel','c','98','10','etc','8','0.00000','0','0','0','379','0','91','-1','2870000','1148','true','true','true','true'),
 ('325','Horn of Glory','rhand','true','540','2','2','steel','c','98','10','etc','8','0.00000','0','0','0','379','0','91','-1','2870000','1148','true','true','true','true'),
 ('326','Heathen\'s Book','rhand','true','560','3','3','steel','c','111','10','etc','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('327','Hex Doll','rhand','true','1600','3','3','steel','c','111','10','etc','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('328','Candle of Wisdom','rhand','true','1600','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('329','Blessed Branch','rhand','true','800','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('330','Phoenix Feather','rhand','true','800','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('331','Cerberus Eye','rhand','true','1600','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('332','Scroll of Destruction','rhand','true','1600','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('333','Claws of Black Dragon','rhand','true','1600','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('334','Three Eyed Crow\'s Feather','rhand','true','1600','3','3','steel','c','125','10','etc','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('335','Soul Crystal','rhand','true','1600','1','1','steel','b','155','10','etc','8','0.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('336','Scroll of Mana','rhand','true','1600','1','1','steel','b','170','10','etc','8','0.00000','0','0','0','379','0','143','-1','18300000','2440','true','true','true','true'),
 ('337','Scroll of Massacre','rhand','true','1600','1','1','steel','b','170','10','etc','8','0.00000','0','0','0','379','0','143','-1','18300000','2440','true','true','true','true'),
 ('338','Wyvern\'s Skull','rhand','true','4800','1','1','steel','b','170','10','etc','8','0.00000','0','0','0','379','0','143','-1','18300000','2440','true','true','true','true'),
 ('339','Blood Crystal','rhand','true','1600','1','1','steel','b','170','10','etc','8','0.00000','0','0','0','379','0','143','-1','18300000','2440','true','true','true','true'),
 ('340','Unicorn\'s Horn','rhand','true','1600','1','1','steel','b','170','10','etc','8','0.00000','0','0','0','379','0','143','-1','18300000','2440','true','true','true','true'),
 ('341','Forgotten Tome','rhand','true','1600','1','1','steel','a','186','10','etc','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('342','Enchanted Flute','rhand','true','1600','1','1','steel','a','186','10','etc','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('343','Headless Arrow','rhand','true','1600','1','1','steel','a','186','10','etc','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('344','Proof of Overlord','rhand','true','1600','1','1','steel','a','186','10','etc','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('345','Deathbringer Sword','rhand','true','4800','1','1','steel','a','186','10','etc','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('346','Tears of Fallen Angel','rhand','true','1600','1','1','steel','s','201','10','etc','8','0.00000','0','0','0','379','0','162','-1','35300000','1765','true','true','true','true'),
 ('625','Bone Shield','lhand','false','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','true','true','true','true'),
 ('626','Bronze Shield','lhand','true','1370','0','0','bronze','d','0','0','none','0','0.00000','-8','101','20','0','0','0','-1','21900','39','true','true','true','true'),
 ('627','Aspis','lhand','true','1350','0','0','steel','d','0','0','none','0','0.00000','-8','114','20','0','0','0','-1','35000','63','true','true','true','true'),
 ('628','Hoplon','lhand','true','1340','0','0','fine_steel','d','0','0','none','0','0.00000','-8','128','20','0','0','0','-1','53300','96','true','true','true','true'),
 ('629','Kite Shield','lhand','true','1320','0','0','fine_steel','d','0','0','none','0','0.00000','-8','142','20','0','0','0','-1','78400','142','true','true','true','true'),
 ('630','Square Shield','lhand','true','1310','0','0','fine_steel','d','0','0','none','0','0.00000','-8','154','20','0','0','0','-1','103000','187','true','true','true','true'),
 ('631','Eldarake','lhand','true','1290','0','0','fine_steel','c','0','0','none','0','0.00000','-8','166','20','0','0','0','-1','133000','53','true','true','true','true'),
 ('632','Knight\'s Shield','lhand','true','1260','0','0','fine_steel','c','0','0','none','0','0.00000','-8','178','20','0','0','0','-1','169000','67','true','true','true','true'),
 ('633','Zubei\'s Shield','lhand','true','1210','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('634','Dragon Shield','lhand','true','1170','0','0','bone','s','0','0','none','0','0.00000','-8','269','20','0','0','0','-1','2510000','125','true','false','true','true'),
 ('635','Wolf Shield','lhand','true','640','0','0','oriharukon','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('636','Shining Dragon Shield','lhand','true','720','0','0','mithril','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('637','Shield of Valor','lhand','true','720','0','0','blood_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('638','Glorious Shield','lhand','true','800','0','0','fine_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('639','Red Flame Shield','lhand','true','960','0','0','blood_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('640','Elven Crystal Shield','lhand','true','720','0','0','crystal','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('641','Dark Crystal Shield','lhand','true','1190','0','0','crystal','a','0','0','none','0','0.00000','-8','243','20','0','0','0','-1','1220000','97','true','true','true','true'),
 ('642','Elven Vagian Shield','lhand','true','560','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('643','Dark Vagian Shield','lhand','true','560','0','0','mithril','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('644','Hell Shield','lhand','true','640','0','0','blood_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('645','Art of Shield','lhand','true','560','0','0','chrysolite','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('646','Shield of Silence','lhand','true','640','0','0','damascus','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('647','Gust Shield','lhand','true','720','0','0','blood_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('648','Prairie Shield','lhand','true','800','0','0','chrysolite','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('649','Shield of The Underworld','lhand','true','880','0','0','damascus','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('650','Shield of Concentration','lhand','true','960','0','0','oriharukon','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('651','Ace\'s Shield','lhand','true','1040','0','0','oriharukon','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('652','Guardian\'s Shield','lhand','true','1120','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('653','Marksman Shield','lhand','true','1200','0','0','leather','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('654','Shield of Mana','lhand','true','480','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('655','Sage\'s Shield','lhand','true','560','0','0','steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('656','Paradia Shield','lhand','true','480','0','0','chrysolite','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('657','Inferno Shield','lhand','true','560','0','0','blood_steel','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('658','Shield of Black Ore','lhand','true','560','0','0','damascus','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('659','Shield of Summoning','lhand','true','480','0','0','leather','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('660','Otherworldly Shield','lhand','true','560','0','0','adamantaite','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('661','Elemental Shield','lhand','true','480','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('662','Shield of Phantom','lhand','true','560','0','0','oriharukon','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('663','Shield of Grace','lhand','true','480','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('664','Shield of Holy Spirit','lhand','true','560','0','0','chrysolite','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('665','Phoenix Shield','lhand','true','480','0','0','blood_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('666','Cerberus Shield','lhand','true','560','0','0','damascus','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('667','Shield of Aid','lhand','true','480','0','0','fine_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('668','Shield of Blessing','lhand','true','560','0','0','adamantaite','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('669','Flame Shield','lhand','true','480','0','0','blood_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('670','Shield of Bravery','lhand','true','560','0','0','chrysolite','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('671','Blood Shield','lhand','true','480','0','0','blood_steel','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('672','Absolute Shield','lhand','true','560','0','0','mithril','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('673','Avadon Shield','lhand','true','1210','0','0','damascus','b','0','0','none','0','0.00000','-8','216','20','0','0','0','-1','545000','72','true','true','true','true'),
 ('674','Divine Shield','lhand','true','560','0','0','adamantaite','b','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','112','true','true','true','true'),
 ('738','Sword of Solidarity','rhand','false','1300','1','1','bronze','none','12','10','sword','8','0.00000','0','0','0','379','0','9','-1','18500','0','true','true','true','true'),
 ('743','Sword of Sentinel','rhand','false','1300','1','1','steel','none','14','10','sword','8','0.00000','0','0','0','379','0','11','-1','33600','0','true','true','true','true'),
 ('744','Staff of Sentinel','lrhand','false','1800','1','1','wood','none','13','20','bigblunt','4','4.00000','0','0','0','325','0','14','-1','33600','0','true','true','true','true'),
 ('747','Wand of Adept','rhand','false','1300','1','1','wood','none','11','20','blunt','4','4.00000','0','0','0','379','0','13','-1','25500','0','true','true','true','true'),
 ('748','Gallint\'s Oak Wand','rhand','false','1350','1','1','wood','none','10','20','blunt','4','4.00000','0','0','0','325','0','10','-1','0','0','false','false','true','false'),
 ('749','0','lrhand','true','100','0','0','wood','d','21','20','bigblunt','4','4.00000','0','0','0','325','0','32','-1','35800','5','true','false','true','true'),
 ('754','Red Sunset Staff','lrhand','false','1700','1','1','wood','none','13','20','bigblunt','4','4.00000','0','0','0','325','0','13','-1','25500','0','true','true','true','true'),
 ('945','Skeleton Buckler','lhand','false','1400','0','0','wood','none','0','0','none','0','0.00000','-8','69','20','0','0','0','-1','3440','0','true','true','true','true'),
 ('946','Skeleton Dagger','rhand','false','1100','2','2','steel','none','16','5','dagger','12','-3.00000','0','0','0','433','0','13','-1','66600','0','true','true','true','true'),
 ('975','Blood Saber','rhand','false','1450','1','1','oriharukon','none','14','10','sword','8','0.00000','0','0','0','379','0','11','-1','33600','0','true','true','true','true'),
 ('981','Red Sunset Sword','lrhand','false','1300','1','1','steel','none','16','10','bigsword','8','0.00000','0','0','0','325','0','10','-1','25500','0','true','true','true','true'),
 ('989','Eldritch Dagger','rhand','false','1130','1','1','steel','none','11','5','dagger','12','-3.00000','0','0','0','433','0','10','-1','25500','0','true','true','true','true'),
 ('1142','Rusted Bronze Sword','rhand','false','1400','2','2','steel','none','18','10','sword','8','0.00000','0','0','0','379','0','21','-1','0','0','false','false','true','false'),
 ('1181','Neti\'s Bow','lrhand','false','1850','7','2','wood','none','45','5','bow','12','-3.00000','0','0','0','293','2','16','-1','0','0','false','false','true','false'),
 ('1182','Neti\'s Dagger','rhand','false','1000','2','2','steel','none','19','5','dagger','12','-3.00000','0','0','0','433','0','16','-1','0','0','false','false','true','false'),
 ('1213','Guard\'s Bow','lrhand','false','1800','7','2','steel','none','49','5','bow','12','-3.00000','0','0','0','293','3','17','-1','136000','0','false','false','true','false'),
 ('1295','Long Sword','rhand','false','200','2','2','fine_steel','none','22','10','sword','8','0.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1296','Gladius','rhand','false','300','2','2','steel','none','22','10','sword','8','0.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1297','Bastard Sword','rhand','false','400','2','2','fine_steel','none','22','10','sword','8','0.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1298','Caliburs','rhand','false','500','2','2','oriharukon','none','22','10','sword','8','0.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1299','Great Sword','lrhand','false','500','3','3','steel','none','200','10','dual','8','0.00000','0','0','0','325','0','6','-1','3340000','0','false','true','true','false'),
 ('1300','Apprentice\'s Rod','rhand','false','80','2','2','wood','none','22','20','blunt','4','4.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1301','Big Hammer','rhand','false','300','2','2','fine_steel','none','22','20','blunt','4','4.00000','0','0','0','379','0','6','-1','66600','0','false','true','true','false'),
 ('1302','Bec de Corbin','lrhand','false','750','2','2','fine_steel','none','22','10','pole','8','-3.00000','0','0','0','325','0','6','-1','66600','0','false','true','true','false'),
 ('1303','Lance','lrhand','false','750','2','2','fine_steel','none','30000','10','pole','8','-3.00000','0','0','0','325','0','6','-1','66600','0','false','true','true','false'),
 ('1304','Conjuror\'s Staff','lrhand','false','150','2','2','oriharukon','none','15','20','bigblunt','4','4.00000','0','0','0','325','0','17','-1','66600','0','false','true','true','false'),
 ('1305','Knife','rhand','false','120','2','2','bronze','none','30000','5','dagger','12','-3.00000','0','0','0','433','0','6','-1','66600','0','false','true','true','false'),
 ('1306','Crafted Dagger','rhand','false','100','2','2','chrysolite','none','22','5','dagger','12','-3.00000','0','0','0','433','0','6','-1','66600','0','false','true','true','false'),
 ('1307','Bow','lrhand','false','150','9','3','wood','none','120','5','bow','12','-3.00000','0','0','0','293','7','6','-1','3340000','0','false','true','true','false'),
 ('1328','Shield of Grace','lhand','false','300','0','0','mithril','none','0','0','none','0','0.00000','-8','71','20','0','0','0','-1','1090000','0','false','true','true','false'),
 ('1329','Shield of Victory','lhand','false','350','0','0','adamantaite','none','0','0','none','0','0.00000','-8','71','20','0','0','0','-1','1430000','0','false','true','true','false'),
 ('1330','Zubei\'s Shield','lhand','false','500','0','0','mithril','none','0','0','none','0','0.00000','-8','71','20','0','0','0','-1','1850000','0','false','true','true','false'),
 ('1331','Otherworldly Shield','lhand','false','350','0','0','adamantaite','none','0','0','none','0','0.00000','-8','71','20','0','0','0','-1','1430000','0','false','true','true','false'),
 ('1332','Knight\'s Shield','lhand','false','500','0','0','fine_steel','none','0','0','none','0','0.00000','-8','71','20','0','0','0','-1','321000','0','false','true','true','false'),
 ('1333','Brandish','lrhand','false','2250','2','2','steel','none','21','10','bigsword','8','0.00000','0','0','0','325','0','12','-1','54100','0','true','true','true','true'),
 ('1376','Guard Spear','lrhand','true','300','2','2','bronze','d','50','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('1471','Silenos Blowgun','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('1472','Dreadbane','lrhand','true','300','2','2','bronze','d','50','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('1510','Butcher\'s Sword','rhand','false','1450','1','1','steel','none','13','10','sword','8','0.00000','0','0','0','379','0','10','-1','25500','0','true','true','true','true'),
 ('1511','Silversmith Hammer','rhand','false','1860','1','1','steel','none','13','20','blunt','4','4.00000','0','0','0','379','0','10','-1','25500','0','true','true','true','true'),
 ('1660','Cursed Maingauche','rhand','true','1070','3','3','fine_steel','d','62','5','dagger','12','-3.00000','0','0','0','433','0','42','-1','1400000','2545','true','true','true','true'),
 ('2368','Training Gloves','lrhand','false','1580','1','1','cloth','none','7','5','dualfist','4','4.00000','0','0','0','325','0','5','-1','138','0','false','false','true','false'),
 ('2369','Squire\'s Sword','rhand','false','1600','1','1','steel','none','6','10','sword','8','0.00000','0','0','0','379','0','5','-1','138','0','false','false','true','false'),
 ('2370','Guild Member\'s Club','rhand','false','1910','1','1','wood','none','6','20','blunt','4','4.00000','0','0','0','379','0','5','-1','138','0','false','false','true','false'),
 ('2371','Fist of Butcher','lrhand','false','1400','1','1','steel','none','16','5','dualfist','4','4.00000','0','0','0','325','0','10','-1','25500','0','true','true','true','true'),
 ('2372','Dagger of Adept','rhand','false','1050','1','1','steel','none','11','5','dagger','12','-3.00000','0','0','0','433','0','10','-1','25500','0','true','true','true','true'),
 ('2373','Eldritch Staff','lrhand','false','1050','1','1','wood','none','13','20','bigblunt','4','4.00000','0','0','0','325','0','13','-1','25500','0','true','true','true','true'),
 ('2374','Red Sunset Dagger','rhand','false','1100','1','1','steel','none','11','5','dagger','12','-3.00000','0','0','0','433','0','10','-1','25500','0','true','true','true','true'),
 ('2493','Brigandine Shield','lhand','true','1320','0','0','leather','d','0','0','none','0','0.00000','-8','142','20','0','0','0','-1','78400','142','true','true','true','true'),
 ('2494','Plate Shield','lhand','true','1310','0','0','leather','d','0','0','none','0','0.00000','-8','154','20','0','0','0','-1','103000','187','true','true','true','true'),
 ('2495','Chain Shield','lhand','true','1280','0','0','leather','c','0','0','none','0','0.00000','-8','166','20','0','0','0','-1','133000','53','true','true','true','true'),
 ('2496','Dwarven Chain Shield','lhand','true','1280','0','0','leather','c','0','0','none','0','0.00000','-8','174','20','0','0','0','-1','156000','62','true','true','true','true'),
 ('2497','Full Plate Shield','lhand','true','1220','0','0','leather','c','0','0','none','0','0.00000','-8','203','20','0','0','0','-1','375000','150','true','true','true','true'),
 ('2498','Shield of Nightmare','lhand','true','1180','0','0','leather','a','0','0','none','0','0.00000','-8','256','20','0','0','0','-1','1850000','148','true','true','true','true'),
 ('2499','Elven Long Sword','rhand','true','1440','3','3','fine_steel','d','92','10','sword','8','0.00000','0','0','0','379','0','54','-1','1800000','3272','true','true','true','true'),
 ('2500','Dark Legion\'s Edge','rhand','true','1320','1','1','fine_steel','a','232','10','sword','8','0.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('2501','Bone Club','rhand','false','1850','2','2','fine_steel','none','24','20','blunt','4','4.00000','0','0','0','379','0','17','-1','136000','0','true','true','true','true'),
 ('2502','Dwarven War Hammer','rhand','true','1670','2','2','fine_steel','c','122','20','blunt','4','4.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('2503','Yaksa Mace','rhand','true','1640','3','3','fine_steel','c','156','20','blunt','4','4.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('2504','Meteor Shower','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('2505','Iron Canine','wolf','false','100','1','1','steel','none','4','10','pet','8','0.00000','0','0','0','277','0','5','-1','14000','0','true','true','true','true'),
 ('2507','Lizardspear','rhand','false','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('2516','Saber*Saber','lrhand','true','2530','2','2','fine_steel','d','73','10','dual','8','0.00000','0','0','0','325','0','37','-1','877000','1594','true','true','true','true'),
 ('2517','Saber*Bastard Sword','lrhand','true','2520','3','3','fine_steel','d','83','10','dual','8','0.00000','0','0','0','325','0','41','-1','1060000','1927','true','true','true','true'),
 ('2518','Saber*Spinebone Sword','lrhand','true','2530','3','3','fine_steel','d','83','10','dual','8','0.00000','0','0','0','325','0','41','-1','1060000','1927','true','true','true','true'),
 ('2519','Saber*Artisan\'s Sword','lrhand','true','2520','3','3','fine_steel','d','83','10','dual','8','0.00000','0','0','0','325','0','41','-1','1060000','1927','true','true','true','true'),
 ('2520','Saber*Knight\'s Sword','lrhand','true','2510','3','3','fine_steel','d','83','10','dual','8','0.00000','0','0','0','325','0','41','-1','1060000','1927','true','true','true','true'),
 ('2521','Saber*Crimson Sword','lrhand','true','2530','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2522','Saber*Elven Sword','lrhand','true','2510','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2523','Saber*Sword of Revolution','lrhand','true','2420','2','2','fine_steel','c','118','10','dual','8','0.00000','0','0','0','325','0','56','-1','1960000','784','true','true','true','true'),
 ('2524','Saber*Elven Long Sword','lrhand','true','2420','2','2','fine_steel','c','130','10','dual','8','0.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('2525','Bastard Sword*Bastard Sword','lrhand','true','2470','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2526','Bastard Sword*Spinebone Sword','lrhand','true','2470','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2527','Bastard Sword*Artisan\'s Sword','lrhand','true','2480','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2528','Bastard Sword*Knight\'s Sword','lrhand','true','2500','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2529','Bastard Sword*Crimson Sword','lrhand','true','2470','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2530','Bastard Sword*Elven Sword','lrhand','true','2460','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2531','Bastard Sword*Sword of Revolution','lrhand','true','2450','2','2','fine_steel','c','124','10','dual','8','0.00000','0','0','0','325','0','58','-1','2120000','848','true','true','true','true'),
 ('2532','Bastard Sword*Elven Long Sword','lrhand','true','2430','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2533','Spinebone Sword*Spinebone Sword','lrhand','true','2520','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2534','Spinebone Sword*Artisan\'s Sword','lrhand','true','2460','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2535','Spinebone Sword*Knight\'s Sword','lrhand','true','2470','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2536','Spinebone Sword*Crimson Sword','lrhand','true','2520','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2537','Spinebone Sword*Elven Sword','lrhand','true','2460','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2538','Spinebone Sword*Sword of Revolution','lrhand','true','2460','2','2','fine_steel','c','124','10','dual','8','0.00000','0','0','0','325','0','58','-1','2120000','848','true','true','true','true'),
 ('2539','Spinebone Sword*Elven Long Sword','lrhand','true','2390','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2540','Artisan\'s Sword*Artisan\'s Sword','lrhand','true','2480','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2541','Artisan\'s Sword*Knight\'s Sword','lrhand','true','2470','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2542','Artisan\'s Sword*Crimson Sword','lrhand','true','2450','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2543','Artisan\'s Sword*Elven Sword','lrhand','true','2470','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2544','Artisan\'s Sword*Sword of Revolution','lrhand','true','2420','2','2','fine_steel','c','124','10','dual','8','0.00000','0','0','0','325','0','58','-1','2120000','848','true','true','true','true'),
 ('2545','Artisan\'s Sword*Elven Long Sword','lrhand','true','2410','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2546','Knight\'s Sword*Knight\'s Sword','lrhand','true','2460','3','3','fine_steel','d','96','10','dual','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('2547','Knight\'s Sword*Crimson Sword','lrhand','true','2460','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2548','Knight\'s Sword*Elven Sword','lrhand','true','2450','3','3','fine_steel','d','107','10','dual','8','0.00000','0','0','0','325','0','51','-1','1660000','3018','true','true','true','true'),
 ('2549','Knight\'s Sword*Sword of Revolution','lrhand','true','2430','2','2','fine_steel','c','124','10','dual','8','0.00000','0','0','0','325','0','58','-1','2120000','848','true','true','true','true'),
 ('2550','Knight\'s Sword*Elven Long Sword','lrhand','true','2370','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2551','Crimson Sword*Crimson Sword','lrhand','true','2440','2','2','fine_steel','c','118','10','dual','8','0.00000','0','0','0','325','0','56','-1','1960000','784','true','true','true','true'),
 ('2552','Crimson Sword*Elven Sword','lrhand','true','2450','2','2','fine_steel','c','118','10','dual','8','0.00000','0','0','0','325','0','56','-1','1960000','784','true','true','true','true'),
 ('2553','Crimson Sword*Sword of Revolution','lrhand','true','2390','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2554','Crimson Sword*Elven Long Sword','lrhand','true','2360','2','2','fine_steel','c','148','10','dual','8','0.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('2555','Elven Sword*Elven Sword','lrhand','true','2440','2','2','fine_steel','c','118','10','dual','8','0.00000','0','0','0','325','0','56','-1','1960000','784','true','true','true','true'),
 ('2556','Elven Sword*Sword of Revolution','lrhand','true','2410','2','2','fine_steel','c','136','10','dual','8','0.00000','0','0','0','325','0','63','-1','2470000','988','true','true','true','true'),
 ('2557','Elven Sword*Elven Long Sword','lrhand','true','2410','2','2','fine_steel','c','148','10','dual','8','0.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('2558','Sword of Revolution*Sword of Revolution','lrhand','true','2360','2','2','fine_steel','c','148','10','dual','8','0.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('2559','Sword of Revolution*Elven Long Sword','lrhand','true','2340','3','3','fine_steel','c','155','10','dual','8','0.00000','0','0','0','325','0','70','-1','3340000','1336','true','true','true','true'),
 ('2560','Elven Long Sword*Elven Long Sword','lrhand','true','2340','3','3','fine_steel','c','162','10','dual','8','0.00000','0','0','0','325','0','73','-1','3820000','1528','true','true','true','true'),
 ('2561','Stormbringer*Stormbringer','lrhand','true','2330','3','3','fine_steel','c','175','10','dual','8','0.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('2562','Stormbringer*Shamshir','lrhand','true','2300','3','3','fine_steel','c','183','10','dual','8','0.00000','0','0','0','325','0','81','-1','5280000','2112','true','true','true','true'),
 ('2563','Stormbringer*Katana','lrhand','true','2320','3','3','fine_steel','c','183','10','dual','8','0.00000','0','0','0','325','0','81','-1','5280000','2112','true','true','true','true'),
 ('2564','Stormbringer*Spirit Sword','lrhand','true','2310','3','3','fine_steel','c','183','10','dual','8','0.00000','0','0','0','325','0','81','-1','5280000','2112','true','true','true','true'),
 ('2565','Stormbringer*Raid Sword','lrhand','true','2340','3','3','fine_steel','c','183','10','dual','8','0.00000','0','0','0','325','0','81','-1','5280000','2112','true','true','true','true'),
 ('2566','Stormbringer*Caliburs','lrhand','true','2260','1','1','fine_steel','b','197','10','dual','8','0.00000','0','0','0','325','0','86','-1','6980000','930','true','true','true','true'),
 ('2567','Stormbringer*Sword of Limit','lrhand','true','2240','1','1','fine_steel','b','197','10','dual','8','0.00000','0','0','0','325','0','86','-1','6980000','930','true','true','true','true'),
 ('2568','Stormbringer*Sword of Delusion','lrhand','true','2230','1','1','fine_steel','b','197','10','dual','8','0.00000','0','0','0','325','0','86','-1','6980000','930','true','true','true','true'),
 ('2569','Stormbringer*Sword of Nightmare','lrhand','true','2250','1','1','fine_steel','b','197','10','dual','8','0.00000','0','0','0','325','0','86','-1','6980000','930','true','true','true','true'),
 ('2570','Stormbringer*Tsurugi','lrhand','true','2220','1','1','fine_steel','b','197','10','dual','8','0.00000','0','0','0','325','0','86','-1','6980000','930','true','true','true','true'),
 ('2571','Stormbringer*Samurai Long sword','lrhand','true','2150','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2572','Shamshir*Shamshir','lrhand','true','2330','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2573','Shamshir*Katana','lrhand','true','2310','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2574','Shamshir*Spirit Sword','lrhand','true','2280','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2575','Shamshir*Raid Sword','lrhand','true','2280','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2576','Shamshir*Caliburs','lrhand','true','2230','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2577','Shamshir*Sword of Limit','lrhand','true','2240','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2578','Shamshir*Sword of Delusion','lrhand','true','2200','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2579','Shamshir*Sword of Nightmare','lrhand','true','2210','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2580','Shamshir*Tsurugi','lrhand','true','2220','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2581','Shamshir*Samurai Long Sword','lrhand','true','2110','1','1','fine_steel','b','220','10','dual','8','0.00000','0','0','0','325','0','94','-1','9550000','1273','true','true','true','true'),
 ('2582','Katana*Katana','lrhand','true','2270','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2583','Katana*Spirit Sword','lrhand','true','2250','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2584','Katana*Raid Sword','lrhand','true','2270','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2585','Katana*Caliburs','lrhand','true','2220','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2586','Katana*Sword of Limit','lrhand','true','2180','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2587','Katana*Sword of Delusion','lrhand','true','2190','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2588','Katana*Sword of Nightmare','lrhand','true','2170','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2589','Katana*Tsurugi','lrhand','true','2210','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2590','Katana*Samurai Long Sword','lrhand','true','2130','1','1','fine_steel','b','220','10','dual','8','0.00000','0','0','0','325','0','94','-1','9550000','1273','true','true','true','true'),
 ('2591','Spirit Sword*Spirit Sword','lrhand','true','2240','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2592','Spirit Sword*Raid Sword','lrhand','true','2260','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2593','Spirit Sword*Caliburs','lrhand','true','2240','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2594','Spirit Sword*Sword of Limit','lrhand','true','2240','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2595','Spirit Sword*Sword of Delusion','lrhand','true','2250','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2596','Spirit Sword*Sword of Nightmare','lrhand','true','2250','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2597','Spirit Sword*Tsurugi','lrhand','true','2220','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2598','Spirit Sword*Samurai Long Sword','lrhand','true','2110','1','1','fine_steel','b','220','10','dual','8','0.00000','0','0','0','325','0','94','-1','9550000','1273','true','true','true','true'),
 ('2599','Raid Sword*Raid Sword','lrhand','true','2250','3','3','fine_steel','c','190','10','dual','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('2600','Raid Sword*Caliburs','lrhand','true','2190','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2601','Raid Sword*Sword of Limit','lrhand','true','2170','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2602','Raid Sword*Sword of Delusion','lrhand','true','2180','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2603','Raid Sword*Sword of Nightmare','lrhand','true','2170','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2604','Raid Sword*Tsurugi','lrhand','true','2170','1','1','fine_steel','b','204','10','dual','8','0.00000','0','0','0','325','0','89','-1','7830000','1044','true','true','true','true'),
 ('2605','Raid Sword*Samurai Long Sword','lrhand','true','2130','1','1','fine_steel','b','220','10','dual','8','0.00000','0','0','0','325','0','94','-1','9550000','1273','true','true','true','true'),
 ('2606','Caliburs*Caliburs','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2607','Caliburs*Sword of Limit','lrhand','true','2150','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2608','Caliburs*Sword of Delusion','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2609','Caliburs*Sword of Nightmare','lrhand','true','2170','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2610','Caliburs*Tsurugi','lrhand','true','2150','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2611','Caliburs*Samurai Long Sword','lrhand','true','2120','1','1','fine_steel','b','228','10','dual','8','0.00000','0','0','0','325','0','97','-1','11300000','1506','true','true','true','true'),
 ('2612','Sword of Limit*Sword of Limit','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2613','Sword of Limit*Sword of Delusion','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2614','Sword of Limit*Sword of Nightmare','lrhand','true','2130','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2615','Sword of Limit*Tsurugi','lrhand','true','2120','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2616','Sword of Limit*Samurai Long Sword','lrhand','true','2100','1','1','fine_steel','b','228','10','dual','8','0.00000','0','0','0','325','0','97','-1','11300000','1506','true','true','true','true'),
 ('2617','Sword of Delusion*Sword of Delusion','lrhand','true','2150','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2618','Sword of Delusion*Sword of Nightmare','lrhand','true','2130','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2619','Sword of Delusion*Tsurugi','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2620','Sword of Delusion*Samurai Long Sword','lrhand','true','2110','1','1','fine_steel','b','228','10','dual','8','0.00000','0','0','0','325','0','97','-1','11300000','1506','true','true','true','true'),
 ('2621','Sword of Nightmare*Sword of Nightmare','lrhand','true','2130','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2622','Sword of Nightmare*Tsurugi','lrhand','true','2140','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2623','Sword of Nightmare*Samurai Long Sword','lrhand','true','2080','1','1','fine_steel','b','228','10','dual','8','0.00000','0','0','0','325','0','97','-1','11300000','1506','true','true','true','true'),
 ('2624','Tsurugi*Tsurugi','lrhand','true','2120','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('2625','Tsurugi*Samurai Long Sword','lrhand','true','2090','1','1','fine_steel','b','228','10','dual','8','0.00000','0','0','0','325','0','97','-1','11300000','1506','true','true','true','true'),
 ('2626','Samurai Long Sword*Samurai Long Sword','lrhand','true','2080','1','1','fine_steel','b','236','10','dual','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('2915','Old Knight Sword','rhand','false','3200','2','2','fine_steel','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','136000','0','false','false','false','false'),
 ('3026','Talins Spear','lrhand','false','800','2','2','steel','none','24','10','pole','8','-3.00000','0','0','0','325','0','17','-1','0','0','false','false','true','false'),
 ('3027','Old Knight\'s Sword','lrhand','false','2100','2','2','steel','none','29','10','bigsword','8','0.00000','0','0','0','325','0','17','-1','0','0','false','false','true','false'),
 ('3028','Crescent Moon Bow','lrhand','false','600','6','2','wood','none','34','5','bow','12','-3.00000','0','0','0','293','2','12','-1','0','0','false','false','true','false'),
 ('3029','Sword of Binding','rhand','false','1200','2','2','steel','none','17','10','sword','8','0.00000','0','0','0','379','0','12','-1','0','0','false','false','true','false'),
 ('3439','Shining Canine','wolf','false','100','1','1','steel','none','7','10','pet','8','0.00000','0','0','0','277','0','8','-1','34000','0','true','true','true','true'),
 ('3471','Cybellin\'s Dagger','rhand','false','400','1','1','steel','none','10','5','dagger','12','-3.00000','0','0','0','433','0','9','-1','0','0','false','false','false','false'),
 ('3902','Ghost Canine','wolf','false','100','1','1','steel','none','11','10','pet','8','0.00000','0','0','0','277','0','10','-1','61000','0','true','true','true','true'),
 ('3903','Mithril Canine','wolf','false','100','1','1','steel','none','16','10','pet','8','0.00000','0','0','0','277','0','14','-1','100000','0','true','true','true','true'),
 ('3904','Sylvan Canine','wolf','false','100','1','1','steel','none','23','10','pet','8','0.00000','0','0','0','277','0','18','-1','160000','0','true','true','true','true'),
 ('3905','Orikarukon Canine','wolf','false','100','1','1','steel','none','31','10','pet','8','0.00000','0','0','0','277','0','24','-1','240000','0','true','true','true','true'),
 ('3906','Fang of Saltydog','wolf','false','100','1','1','steel','none','40','10','pet','8','0.00000','0','0','0','277','0','30','-1','350000','0','true','true','true','true'),
 ('3907','Fang of Cerberus','wolf','false','100','1','1','steel','none','49','10','pet','8','0.00000','0','0','0','277','0','36','-1','450000','0','true','true','true','true'),
 ('3908','Fang of Coyote','wolf','false','100','1','1','steel','none','58','10','pet','8','0.00000','0','0','0','277','0','42','-1','570000','0','true','true','true','true'),
 ('3909','Crystalized Ice Canine','wolf','false','100','1','1','steel','none','69','10','pet','8','0.00000','0','0','0','277','0','50','-1','720000','0','true','true','true','true'),
 ('3910','Fang of The Blue Wolf','wolf','false','100','1','1','steel','none','80','10','pet','8','0.00000','0','0','0','277','0','58','-1','1100000','0','true','true','true','true'),
 ('3911','Fang of Fenril','wolf','false','100','1','1','steel','none','93','10','pet','8','0.00000','0','0','0','277','0','66','-1','1500000','0','true','true','true','true'),
 ('3919','Serpent Fang','hatchling','false','100','1','1','steel','none','8','10','pet','8','0.00000','0','0','0','277','0','7','-1','200000','0','true','true','true','true'),
 ('3920','Viperbite','hatchling','false','100','1','1','steel','none','9','10','pet','8','0.00000','0','0','0','277','0','10','-1','310000','0','true','true','true','true'),
 ('3921','Shadow Fang','hatchling','false','100','1','1','steel','none','10','10','pet','8','0.00000','0','0','0','277','0','11','-1','420000','0','true','true','true','true'),
 ('3922','Alya Fang','hatchling','false','100','1','1','steel','none','12','10','pet','8','0.00000','0','0','0','277','0','13','-1','570000','0','true','true','true','true'),
 ('3923','Torturer','hatchling','false','100','1','1','steel','none','13','10','pet','8','0.00000','0','0','0','277','0','16','-1','760000','0','true','true','true','true'),
 ('3924','Unuk Alhay Fang','hatchling','false','100','1','1','steel','none','15','10','pet','8','0.00000','0','0','0','277','0','18','-1','1100000','0','true','true','true','true'),
 ('3925','Antiplague','hatchling','false','100','1','1','steel','none','17','10','pet','8','0.00000','0','0','0','277','0','21','-1','1600000','0','true','true','true','true'),
 ('3937','Giant Bar','lrhand','true','300','2','2','bronze','d','50','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('3938','Giant Rod','lrhand','true','300','2','2','bronze','d','50','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('3939','Lady\'s Fan','lrhand','true','300','2','2','bronze','d','50','10','pole','8','-3.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('4027','Bouquet','rhand','false','0','0','0','paper','none','8','10','sword','8','0.00000','0','0','0','379','0','6','-1','0','0','true','true','true','true'),
 ('4028','Giant Cannon','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('4202','Chrono Cithara','rhand','false','0','0','0','steel','none','1','10','sword','8','0.00000','0','0','0','379','0','1','-1','0','0','true','false','true','true'),
 ('4219','Dream Sword','rhand','true','1530','2','2','bronze','none','31','10','sword','8','0.00000','0','0','0','379','0','21','-1','244000','0','false','false','false','false'),
 ('4220','Dream Knife','rhand','true','1110','2','2','steel','none','27','5','dagger','12','-3.00000','0','0','0','433','0','21','-1','244000','0','false','false','false','false'),
 ('4221','Ubiquitous Axe','rhand','true','1850','2','2','steel','none','31','20','blunt','4','4.00000','0','0','0','379','0','21','-1','244000','0','false','false','false','false'),
 ('4222','Dream Shield','lhand','true','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','false','false','false','false'),
 ('4223','Ubiquitous Shield','lhand','true','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','false','false','false','false'),
 ('4233','Knuckle Duster','lrhand','true','1490','2','2','fine_steel','c','148','5','dualfist','4','4.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4237','Hatchling\'s Level 65 Weapon','hatchling','false','100','1','1','steel','none','24','10','pet','8','0.00000','0','0','0','277','0','32','-1','5628000','0','true','true','true','true'),
 ('4238','Hatchling\'s Level 75 Weapon','hatchling','false','100','1','1','steel','none','31','10','pet','8','0.00000','0','0','0','277','0','43','-1','19073000','0','true','true','true','true'),
 ('4665','Pipette Knife','rhand','false','1140','1','1','bronze','none','10','5','dagger','12','-3.00000','0','0','0','433','0','9','-1','0','0','false','false','false','false'),
 ('4681','Stormbringer - Critical Anger','rhand','true','1430','2','2','fine_steel','c','107','10','sword','8','0.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4682','Stormbringer - Focus','rhand','true','1430','2','2','fine_steel','c','107','10','sword','8','0.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4683','Stormbringer - Light','rhand','true','430','2','2','fine_steel','c','107','10','sword','8','0.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4684','Shamshir - Guidance','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4685','Shamshir - Back Blow','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4686','Shamshir - Rsk. Evasion','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4687','Katana - Focus','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4688','Katana - Critical Damage','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4689','Katana - Haste','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4690','Spirit Sword - Critical Damage','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4691','Spirit Sword - Critical Poison','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4692','Spirit Sword - Haste','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4693','Raid Sword - Focus','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4694','Raid Sword - Critical Drain','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4695','Raid Sword - Critical Poison','rhand','true','1420','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4696','Caliburs - Guidance','rhand','true','1400','3','3','oriharukon','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4697','Caliburs - Focus','rhand','true','1400','3','3','oriharukon','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4698','Caliburs - Critical Damage','rhand','true','1400','3','3','oriharukon','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4699','Sword of Delusion - Focus','rhand','true','1400','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4700','Sword of Delusion - Health','rhand','true','1400','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4701','Sword of Delusion - Rsk. Haste','rhand','true','1400','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4702','Tsurugi - Focus','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4703','Tsurugi - Critical Damage','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4704','Tsurugi - Haste','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4705','Sword of Nightmare - Health','rhand','true','1400','3','3','blood_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4706','Sword of Nightmare - Focus','rhand','true','1400','3','3','blood_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4707','Sword of Nightmare - Light','rhand','true','420','3','3','blood_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4708','Samurai Longsword - Focus','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4709','Samurai Longsword - Critical Damage','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4710','Samurai Longsword - Haste','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4711','Flamberge - Critical Damage','lrhand','true','2010','2','2','bronze','c','130','10','bigsword','8','0.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4712','Flamberge - Focus','lrhand','true','2010','2','2','bronze','c','130','10','bigsword','8','0.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4713','Flamberge - Light','lrhand','true','600','2','2','bronze','c','130','10','bigsword','8','0.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4714','Keshanberk - Guidance','rhand','true','1370','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4715','Keshanberk - Focus','rhand','true','1370','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4716','Keshanberk - Back Blow','rhand','true','1370','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4717','Sword of Damascus - Focus','rhand','true','1350','1','1','damascus','b','194','10','sword','8','0.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4718','Sword of Damascus - Critical Damage','rhand','true','1350','1','1','damascus','b','194','10','sword','8','0.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4719','Sword of Damascus - Haste','rhand','true','1350','1','1','damascus','b','194','10','sword','8','0.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4720','Tallum Blade - Health','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4721','Tallum Blade - Rsk. Evasion','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4722','Tallum Blade - Rsk. Haste','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4723','Great Sword - Health','lrhand','true','1930','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4724','Great Sword - Critical Damage','lrhand','true','1930','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4725','Great Sword - Focus','lrhand','true','1930','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4726','Big Hammer - Health','rhand','true','1710','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4727','Big Hammer - Rsk.Focus','rhand','true','1710','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4728','Big Hammer - Haste','rhand','true','1710','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4729','Battle Axe - Anger','rhand','true','1720','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4730','Battle Axe - Rsk.Focus','rhand','true','1720','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4731','Battle Axe - Haste','rhand','true','1720','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4732','Silver Axe - Anger','rhand','true','1690','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4733','Silver Axe - Rsk.Focus','rhand','true','1690','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4734','Silver Axe - Haste','rhand','true','1690','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4735','Skull Graver - Anger','rhand','true','1640','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4736','Skull Graver - Health','rhand','true','1640','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4737','Skull Graver - Rsk.Focus','rhand','true','1640','2','2','fine_steel','c','107','20','blunt','4','4.00000','0','0','0','379','0','61','-1','2290000','916','true','true','true','true'),
 ('4738','Dwarven War Hammer - Anger','rhand','true','1670','2','2','fine_steel','c','122','20','blunt','4','4.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4739','Dwarven War Hammer - Health','rhand','true','1670','2','2','fine_steel','c','122','20','blunt','4','4.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4740','Dwarven War Hammer - Haste','rhand','true','1670','2','2','fine_steel','c','122','20','blunt','4','4.00000','0','0','0','379','0','68','-1','2870000','1148','true','true','true','true'),
 ('4741','War Axe - Anger','rhand','true','1660','3','3','fine_steel','c','139','20','blunt','4','4.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4742','War Axe - Health','rhand','true','1660','3','3','fine_steel','c','139','20','blunt','4','4.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4743','War Axe - Haste','rhand','true','1660','3','3','fine_steel','c','139','20','blunt','4','4.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('4744','Yaksa Mace - Anger','rhand','true','1640','3','3','fine_steel','c','156','20','blunt','4','4.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4745','Yaksa Mace - Health','rhand','true','1640','3','3','fine_steel','c','156','20','blunt','4','4.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4746','Yaksa Mace - Rsk. Focus','rhand','true','1640','3','3','fine_steel','c','156','20','blunt','4','4.00000','0','0','0','379','0','83','-1','6130000','2452','true','true','true','true'),
 ('4747','Heavy War Axe - Anger','rhand','true','1620','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4748','Heavy War Axe - Health','rhand','true','1620','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4749','Heavy War Axe - Rsk. Focus','rhand','true','1620','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','-1','8680000','1157','true','true','true','true'),
 ('4750','Deadman\'s Glory - Anger','rhand','true','1600','1','1','bone','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4751','Deadman\'s Glory - Health','rhand','true','1600','1','1','bone','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4752','Deadman\'s Glory - Haste','rhand','true','1600','1','1','bone','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4753','Art of Battle Axe - Health','rhand','true','1570','1','1','chrysolite','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4754','Art of Battle Axe - Rsk. Focus','rhand','true','1570','1','1','chrysolite','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4755','Art of Battle Axe - Haste','rhand','true','1570','1','1','chrysolite','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('4756','Meteor Shower - Health','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4757','Meteor Shower - Focus','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4758','Meteor Shower - P.Focus','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('4759','Cursed Dagger - Critical Bleed','rhand','true','1040','2','2','mithril','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4760','Cursed Dagger - Critical Poison','rhand','true','1040','2','2','mithril','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4761','Cursed Dagger - Rsk. Haste','rhand','true','1040','2','2','mithril','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4762','Dark Elven Dagger - Focus','rhand','true','1050','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4763','Dark Elven Dagger - Back Blow','rhand','true','1050','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4764','Dark Elven Dagger - Mortal Strike','rhand','true','1050','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('4765','Stiletto - Critical Bleed','rhand','true','1030','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','-1','2870000','1148','true','true','true','true'),
 ('4766','Stiletto - Critical Poison','rhand','true','1030','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','-1','2870000','1148','true','true','true','true'),
 ('4767','Stiletto - Mortal Strike','rhand','true','1030','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','-1','2870000','1148','true','true','true','true'),
 ('4768','Grace Dagger - Evasion','rhand','true','1020','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4769','Grace Dagger - Focus','rhand','true','1020','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4770','Grace Dagger - Back Blow','rhand','true','1020','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4771','Dark Screamer - Evasion','rhand','true','1010','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4772','Dark Screamer - Focus','rhand','true','1010','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4773','Dark Screamer - Critical Bleed','rhand','true','1010','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','-1','4300000','1720','true','true','true','true'),
 ('4774','Crystal Dagger - Critical Bleed','rhand','true','1000','3','3','crystal','c','136','5','dagger','12','-3.00000','0','0','0','433','0','83','-1','6130000','2452','true','true','true','true'),
 ('4775','Crystal Dagger - Critical Poison','rhand','true','1000','3','3','crystal','c','136','5','dagger','12','-3.00000','0','0','0','433','0','83','-1','6130000','2452','true','true','true','true'),
 ('4776','Crystal Dagger - Mortal Strike','rhand','true','1000','3','3','crystal','c','136','5','dagger','12','-3.00000','0','0','0','433','0','83','-1','6130000','2452','true','true','true','true'),
 ('4777','Kris - Evasion','rhand','true','980','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','8680000','1157','true','true','true','true'),
 ('4778','Kris - Focus','rhand','true','980','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','8680000','1157','true','true','true','true'),
 ('4779','Kris - Back Blow','rhand','true','980','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','8680000','1157','true','true','true','true'),
 ('4780','Demon Dagger - Critical Bleed','rhand','true','970','1','1','blood_steel','b','170','5','dagger','12','-3.00000','0','0','0','433','0','99','-1','13100000','1746','true','true','true','true'),
 ('4781','Demon Dagger - Critical Poison','rhand','true','970','1','1','blood_steel','b','170','5','dagger','12','-3.00000','0','0','0','433','0','99','-1','13100000','1746','true','true','true','true'),
 ('4782','Demon Dagger - Mortal Strike','rhand','true','970','1','1','blood_steel','b','170','5','dagger','12','-3.00000','0','0','0','433','0','99','-1','13100000','1746','true','true','true','true'),
 ('4783','Bloody Orchid - Evasion','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('4784','Bloody Orchid - Focus','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('4785','Bloody Orchid - Back Blow','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('4786','Hell Knife - Focus','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('4787','Hell Knife - Back Blow','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('4788','Hell Knife - Mortal Strike','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('4789','Chakram - Critical Drain','lrhand','true','1490','2','2','fine_steel','c','130','5','dualfist','4','4.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4790','Chakram - Critical Poison','lrhand','true','1490','2','2','fine_steel','c','130','5','dualfist','4','4.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4791','Chakram - Rsk. Haste','lrhand','true','1490','2','2','fine_steel','c','130','5','dualfist','4','4.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4792','Fisted Blade - Rsk. Evasion','lrhand','true','1480','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('4793','Fisted Blade - Rsk. Haste','lrhand','true','1480','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('4794','Fisted Blade - Haste','lrhand','true','1480','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('4795','Great Pata - Critical Drain','lrhand','true','1460','3','3','chrysolite','c','190','5','dualfist','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4796','Great Pata - Critical Poison','lrhand','true','1460','3','3','chrysolite','c','190','5','dualfist','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4797','Great Pata - Rsk. Haste','lrhand','true','1460','3','3','chrysolite','c','190','5','dualfist','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4798','Knuckle Duster - Rsk. Evasion','lrhand','true','1490','2','2','fine_steel','c','148','5','dualfist','4','4.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4799','Knuckle Duster - Rsk. Haste','lrhand','true','1490','2','2','fine_steel','c','148','5','dualfist','4','4.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4800','Knuckle Duster - Haste','lrhand','true','1490','2','2','fine_steel','c','148','5','dualfist','4','4.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4801','Arthro Nail - Critical Poison','lrhand','true','1420','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4802','Arthro Nail - Rsk. Evasion','lrhand','true','1420','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4803','Arthro Nail - Rsk. Haste','lrhand','true','1420','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4804','Bellion Cestus - Critical Drain','lrhand','true','1390','1','1','mithril','b','236','5','dualfist','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4805','Bellion Cestus - Critical Poison','lrhand','true','1390','1','1','mithril','b','236','5','dualfist','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4806','Bellion Cestus - Rsk. Haste','lrhand','true','1390','1','1','mithril','b','236','5','dualfist','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4807','Blood Tornado - Critical Drain','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4808','Blood Tornado - Rsk. Evasion','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4809','Blood Tornado - Haste','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4810','Crystallized Ice Bow - Guidance','lrhand','true','1790','8','2','crystal','c','220','5','bow','12','-3.00000','0','0','0','293','6','61','-1','2290000','916','true','true','true','true'),
 ('4811','Crystallized Ice Bow - Evasion','lrhand','true','1790','8','2','crystal','c','220','5','bow','12','-3.00000','0','0','0','293','6','61','-1','2290000','916','true','true','true','true'),
 ('4812','Crystallized Ice Bow - Quick Recovery','lrhand','true','1790','8','2','crystal','c','220','5','bow','12','-3.00000','0','0','0','293','6','61','-1','2290000','916','true','true','true','true'),
 ('4813','Elemental Bow - Guidance','lrhand','true','1770','8','2','fine_steel','c','277','5','bow','12','-3.00000','0','0','0','227','7','75','-1','2870000','1148','true','true','true','true'),
 ('4814','Elemental Bow - Miser','lrhand','true','1770','8','2','fine_steel','c','277','5','bow','12','-3.00000','0','0','0','227','7','75','-1','2870000','1148','true','true','true','true'),
 ('4815','Elemental Bow - Quick Recovery','lrhand','true','1770','8','2','fine_steel','c','277','5','bow','12','-3.00000','0','0','0','227','7','75','-1','2870000','1148','true','true','true','true'),
 ('4816','Elven Bow of Nobility - Evasion','lrhand','true','1760','8','2','mithril','c','252','5','bow','12','-3.00000','0','0','0','293','7','68','-1','2870000','1148','true','true','true','true'),
 ('4817','Elven Bow of Nobility - Miser','lrhand','true','1760','8','2','mithril','c','252','5','bow','12','-3.00000','0','0','0','293','7','68','-1','2870000','1148','true','true','true','true'),
 ('4818','Elven Bow of Nobility - Cheap Shot','lrhand','true','1760','8','2','mithril','c','252','5','bow','12','-3.00000','0','0','0','293','7','68','-1','2870000','1148','true','true','true','true'),
 ('4819','Akat Long Bow - Guidance','lrhand','true','1740','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','-1','4300000','1720','true','true','true','true'),
 ('4820','Akat Long Bow - Evasion','lrhand','true','1740','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','-1','4300000','1720','true','true','true','true'),
 ('4821','Akat Long Bow - Miser','lrhand','true','1740','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','-1','4300000','1720','true','true','true','true'),
 ('4822','Eminence Bow - Guidance','lrhand','true','1720','10','3','chrysolite','c','323','5','bow','12','-3.00000','0','0','0','293','8','83','-1','6130000','2452','true','true','true','true'),
 ('4823','Eminence Bow - Miser','lrhand','true','1720','10','3','chrysolite','c','323','5','bow','12','-3.00000','0','0','0','293','8','83','-1','6130000','2452','true','true','true','true'),
 ('4824','Eminence Bow - Cheap Shot','lrhand','true','1720','10','3','chrysolite','c','323','5','bow','12','-3.00000','0','0','0','293','8','83','-1','6130000','2452','true','true','true','true'),
 ('4825','Dark Elven Long Bow - Evasion','lrhand','true','1720','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','-1','8680000','1157','true','true','true','true'),
 ('4826','Dark Elven Long Bow - Critical Bleed','lrhand','true','1720','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','-1','8680000','1157','true','true','true','true'),
 ('4827','Dark Elven Long Bow - Miser','lrhand','true','1720','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','-1','8680000','1157','true','true','true','true'),
 ('4828','Bow of Peril - Guidance','lrhand','true','1700','3','1','blood_steel','b','400','5','bow','12','-3.00000','0','0','0','293','9','99','-1','13100000','1746','true','true','true','true'),
 ('4829','Bow of Peril - Quick Recovery','lrhand','true','1700','3','1','blood_steel','b','400','5','bow','12','-3.00000','0','0','0','293','9','99','-1','13100000','1746','true','true','true','true'),
 ('4830','Bow of Peril - Cheap Shot','lrhand','true','1700','3','1','blood_steel','b','400','5','bow','12','-3.00000','0','0','0','293','9','99','-1','13100000','1746','true','true','true','true'),
 ('4831','Carnage Bow - Critical Bleed','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('4832','Carnage Bow - Mana Up','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('4833','Carnage Bow - Quick Recovery','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('4834','Scythe - Anger','lrhand','true','2040','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4835','Scythe - Critical Stun','lrhand','true','2040','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4836','Scythe - Light','lrhand','true','610','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4837','Orcish Glaive - Anger','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4838','Orcish Glaive - Critical Stun','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4839','Orcish Glaive - Towering Blow','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4840','Body Slasher - Critical Stun','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4841','Body Slasher - Towering Blow','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4842','Body Slasher - Wide Blow','lrhand','true','2030','2','2','fine_steel','c','107','10','pole','8','-3.00000','0','0','0','325','0','61','-1','2290000','916','true','true','true','true'),
 ('4843','Bec de Corbin - Critical Stun','lrhand','true','2020','2','2','fine_steel','c','122','10','pole','8','-3.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4844','Bec de Corbin - Towering Blow','lrhand','true','2020','2','2','fine_steel','c','122','10','pole','8','-3.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4845','Bec de Corbin - Light','lrhand','true','600','2','2','fine_steel','c','122','10','pole','8','-3.00000','0','0','0','325','0','68','-1','2870000','1148','true','true','true','true'),
 ('4846','Scorpion - Anger','lrhand','true','1990','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4847','Scorpion - Critical Stun','lrhand','true','1990','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4848','Scorpion - Towering Blow','lrhand','true','1990','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4849','Widow Maker - Critical Stun','lrhand','true','1980','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4850','Widow Maker - Towering Blow','lrhand','true','1980','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4851','Widow Maker - Wide Blow','lrhand','true','1980','3','3','fine_steel','c','144','10','pole','8','-3.00000','0','0','0','325','0','78','-1','4780000','1912','true','true','true','true'),
 ('4852','Orcish Poleaxe - Critical Stun','lrhand','true','1950','3','3','blood_steel','c','156','10','pole','8','-3.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4853','Orcish Poleaxe - Towering Blow','lrhand','true','1950','3','3','blood_steel','c','156','10','pole','8','-3.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4854','Orcish Poleaxe - Wide Blow','lrhand','true','1950','3','3','blood_steel','c','156','10','pole','8','-3.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('4855','Great Axe - Anger','lrhand','true','1940','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4856','Great Axe - Critical Stun','lrhand','true','1940','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4857','Great Axe - Light','lrhand','true','580','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('4858','Lance - Anger','lrhand','true','1920','1','1','blood_steel','b','194','10','pole','8','-3.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4859','Lance - Critical Stun','lrhand','true','1920','1','1','blood_steel','b','194','10','pole','8','-3.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4860','Lance - Towering Blow','lrhand','true','1920','1','1','blood_steel','b','194','10','pole','8','-3.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('4861','Halberd - Critical Stun','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4862','Halberd - Towering Blow','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4863','Halberd - Wide Blow','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('4864','Heavy Doom Hammer - Magic Regeneration','lrhand','true','1020','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4865','Heavy Doom Hammer - Mental Shield','lrhand','true','1020','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4866','Heavy Doom Hammer - Magic Hold','lrhand','true','1020','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4867','Crystal Staff - Rsk. Evasion','lrhand','true','1020','2','2','crystal','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4868','Crystal Staff - Mana Up','lrhand','true','1020','2','2','crystal','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4869','Crystal Staff - Bodily Blessing','lrhand','true','1020','2','2','crystal','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4870','Heavy Doom Axe - Magic Poison','lrhand','true','1010','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4871','Heavy Doom Axe - Magic Weakness','lrhand','true','1010','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4872','Heavy Doom Axe - Magic Chaos','lrhand','true','1010','2','2','fine_steel','c','103','20','bigblunt','4','4.00000','0','0','0','325','0','81','-1','2290000','916','true','true','true','true'),
 ('4873','Cursed Staff - Magic Hold','lrhand','true','1000','2','2','wood','c','119','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','2870000','1148','true','true','true','true'),
 ('4874','Cursed Staff - Magic Poison','lrhand','true','1000','2','2','wood','c','119','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','2870000','1148','true','true','true','true'),
 ('4875','Cursed Staff - Magic Weakness','lrhand','true','1000','2','2','wood','c','119','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','2870000','1148','true','true','true','true'),
 ('4876','Paradia Staff - Magic Regeneration','lrhand','true','1010','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4877','Paradia Staff - Mental Shield','lrhand','true','1010','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4878','Paradia Staff - Magic Hold','lrhand','true','1010','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4879','Pa\'agrian Hammer - Rsk. Evasion','lrhand','true','1010','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4880','Pa\'agrian Hammer - Magic Poison','lrhand','true','1010','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4881','Pa\'agrian Hammer - Magic Weakness','lrhand','true','1010','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4882','Sage\'s Staff - Magic Hold','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4883','Sage\'s Staff - Magic Poison','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4884','Sage\'s Staff - Magic Weakness','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('4885','Pa\'agrian Axe - Mana Up','lrhand','true','990','3','3','blood_steel','c','141','20','bigblunt','4','4.00000','0','0','0','325','0','104','-1','4780000','1912','true','true','true','true'),
 ('4886','Pa\'agrian Axe - Magic Weakness','lrhand','true','990','3','3','blood_steel','c','141','20','bigblunt','4','4.00000','0','0','0','325','0','104','-1','4780000','1912','true','true','true','true'),
 ('4887','Pa\'agrian Axe - Magic Chaos','lrhand','true','990','3','3','blood_steel','c','141','20','bigblunt','4','4.00000','0','0','0','325','0','104','-1','4780000','1912','true','true','true','true'),
 ('4888','Deadman\'s Staff - Magic Regeneration','lrhand','true','1010','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4889','Deadman\'s Staff - Mental Shield','lrhand','true','1010','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4890','Deadman\'s Staff - Magic Hold','lrhand','true','1010','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4891','Ghoul\'s Staff - Rsk. Evasion','lrhand','true','1000','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4892','Ghoul\'s Staff - Mana Up','lrhand','true','1000','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4893','Ghoul\'s Staff - Bodily Blessing','lrhand','true','1000','3','3','bone','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4894','Demon\'s Staff - Magic Poison','lrhand','true','990','3','3','wood','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4895','Demon\'s Staff - Magic Weakness','lrhand','true','990','3','3','wood','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4896','Demon\'s Staff - Magic Chaos','lrhand','true','990','3','3','wood','c','152','20','bigblunt','4','4.00000','0','0','0','325','0','111','-1','6130000','2452','true','true','true','true'),
 ('4897','Sprite\'s Staff - Magic Regeneration','lrhand','true','960','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('4898','Sprite\'s Staff - Mental Shield','lrhand','true','960','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('4899','Sprite\'s Staff - Magic Hold','lrhand','true','960','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','-1','8680000','1157','true','true','true','true'),
 ('4900','Staff of Evil Spirits - Magic Focus','lrhand','true','930','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('4901','Staff of Evil Spirits - Bodily Blessing','lrhand','true','930','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('4902','Staff of Evil Spirits - Magic Poison','lrhand','true','930','1','1','wood','b','189','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','13100000','1746','true','true','true','true'),
 ('4903','Dasparion\'s Staff','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('4904','Dasparion\'s Staff','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('4905','Dasparion\'s Staff','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('5127','Dailaon Knife','rhand','false','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('5128','Crokian Blade','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('5129','Doll Knife','lrhand','false','300','2','2','steel','d','200','10','dual','8','0.00000','0','0','0','325','0','6','-1','409000','743','true','true','true','true'),
 ('5130','Nos Sword','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('5131','Parhit Staff','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('5132','Giant Trident','rhand','true','300','2','2','steel','d','5','5','dagger','12','-3.00000','0','0','0','433','0','5','-1','409000','743','true','true','true','true'),
 ('5133','Chrono Unitus','lrhand','false','0','0','0','steel','none','1','5','dualfist','4','4.00000','0','0','0','325','0','1','-1','0','0','true','false','true','true'),
 ('5176','Serpentine Spike','strider','false','100','1','1','steel','none','17','10','pet','8','0.00000','0','0','0','277','0','21','-1','1600000','0','true','true','true','true'),
 ('5177','Drake Horn','strider','false','100','1','1','steel','none','19','10','pet','8','0.00000','0','0','0','277','0','24','-1','2300000','0','true','true','true','true'),
 ('5178','Assult Alicorn','strider','false','100','1','1','steel','none','21','10','pet','8','0.00000','0','0','0','277','0','27','-1','3400000','0','true','true','true','true'),
 ('5179','Draconic Slicer','strider','false','100','1','1','steel','none','23','10','pet','8','0.00000','0','0','0','277','0','30','-1','4900000','0','true','true','true','true'),
 ('5180','Ohpdian Lance','strider','false','100','1','1','steel','none','25','10','pet','8','0.00000','0','0','0','277','0','34','-1','7300000','0','true','true','true','true'),
 ('5181','Diamond Drill','strider','false','100','1','1','steel','none','27','10','pet','8','0.00000','0','0','0','277','0','37','-1','10000000','0','true','true','true','true'),
 ('5187','Serpentine Grinder','hatchling','false','100','1','1','steel','none','19','10','pet','8','0.00000','0','0','0','277','0','24','-1','2300000','0','true','true','true','true'),
 ('5188','Fang of Dahak','hatchling','false','100','1','1','steel','none','21','10','pet','8','0.00000','0','0','0','277','0','27','-1','3400000','0','true','true','true','true'),
 ('5189','Crimson Blood Fang','hatchling','false','100','1','1','steel','none','23','10','pet','8','0.00000','0','0','0','277','0','30','-1','4900000','0','true','true','true','true'),
 ('5190','Draconic Chopper','hatchling','false','100','1','1','steel','none','25','10','pet','8','0.00000','0','0','0','277','0','34','-1','7300000','0','true','true','true','true'),
 ('5191','Diabolic Grinder','hatchling','false','100','1','1','steel','none','27','10','pet','8','0.00000','0','0','0','277','0','37','-1','10000000','0','true','true','true','true'),
 ('5217','Wolf Level 75 Weapon','wolf','false','100','1','1','steel','none','194','10','pet','8','0.00000','0','0','0','277','0','136','-1','61084','0','true','true','true','true'),
 ('5233','Keshanberk*Keshanberk','lrhand','true','2080','1','1','fine_steel','a','259','10','dual','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5284','Zweihander','lrhand','false','1530','2','2','bronze','none','38','10','bigsword','8','0.00000','0','0','0','325','0','21','-1','244000','0','true','true','true','true'),
 ('5285','Heavy Sword','lrhand','true','1520','2','2','steel','d','49','10','bigsword','8','0.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('5286','Berserker Blade','lrhand','true','1380','3','3','oriharukon','c','190','10','bigsword','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('5292','Sealed Dark Crystal Shield','lhand','true','1190','0','0','crystal','a','0','0','none','0','0.00000','-8','243','20','0','0','0','-1','1220000','97','true','true','true','true'),
 ('5315','Sealed Shield of Nightmare','lhand','true','1180','0','0','leather','a','0','0','none','0','0.00000','-8','256','20','0','0','0','-1','1850000','148','true','true','true','true'),
 ('5596','Dasparion\'s Staff - Mana Up','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('5597','Dasparion\'s Staff - Conversion','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('5598','Dasparion\'s Staff - Acumen','lrhand','true','920','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','-1','18300000','1464','true','true','true','true'),
 ('5599','Meteor Shower - Focus','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5600','Meteor Shower - Critical Bleed','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5601','Meteor Shower - Rsk. Haste','rhand','true','1600','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5602','Elysian - Health','rhand','true','1580','1','1','oriharukon','a','232','20','blunt','4','4.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5603','Elysian - Anger','rhand','true','1580','1','1','oriharukon','a','232','20','blunt','4','4.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5604','Elysian - Critical Drain','rhand','true','1580','1','1','oriharukon','a','232','20','blunt','4','4.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5605','Branch of The Mother Tree - Conversion','lrhand','true','900','1','1','wood','a','226','20','bigblunt','4','4.00000','0','0','0','325','0','152','-1','27000000','2160','true','true','true','true'),
 ('5606','Branch of The Mother Tree - Magic Damage','lrhand','true','900','1','1','wood','a','226','20','bigblunt','4','4.00000','0','0','0','325','0','152','-1','27000000','2160','true','true','true','true'),
 ('5607','Branch of The Mother Tree - Acumen','lrhand','true','900','1','1','wood','a','226','20','bigblunt','4','4.00000','0','0','0','325','0','152','-1','27000000','2160','true','true','true','true'),
 ('5608','Carnage Bow - Light','lrhand','true','500','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('5609','Carnage Bow - Critical Bleed','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('5610','Carnage Bow - Mana Up','lrhand','true','1670','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','-1','18300000','1464','true','true','true','true'),
 ('5611','Soul Bow - Cheap Shot','lrhand','true','1660','2','1','oriharukon','a','528','5','bow','12','-3.00000','0','0','0','227','10','125','-1','27000000','2160','true','true','true','true'),
 ('5612','Soul Bow - Quick Recovery','lrhand','true','1660','2','1','oriharukon','a','528','5','bow','12','-3.00000','0','0','0','227','10','125','-1','27000000','2160','true','true','true','true'),
 ('5613','Soul Bow - Critical Poison','lrhand','true','1660','2','1','oriharukon','a','528','5','bow','12','-3.00000','0','0','0','227','10','125','-1','27000000','2160','true','true','true','true'),
 ('5614','Bloody Orchid - Focus','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('5615','Bloody Orchid - Back Blow','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('5616','Bloody Orchid - Critical Bleed','rhand','true','960','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','-1','18300000','1464','true','true','true','true'),
 ('5617','Soul Separator - Guidance','rhand','true','950','1','1','oriharukon','a','203','5','dagger','12','-3.00000','0','0','0','433','0','114','-1','27000000','2160','true','true','true','true'),
 ('5618','Soul Separator - Critical Damage','rhand','true','950','1','1','oriharukon','a','203','5','dagger','12','-3.00000','0','0','0','433','0','114','-1','27000000','2160','true','true','true','true'),
 ('5619','Soul Separator - Rsk. Haste','rhand','true','950','1','1','oriharukon','a','203','5','dagger','12','-3.00000','0','0','0','433','0','114','-1','27000000','2160','true','true','true','true'),
 ('5620','Blood Tornado - Haste','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5621','Blood Tornado - Focus','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5622','Blood Tornado - Anger','lrhand','true','1370','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5623','Dragon Grinder - Rsk. Evasion','lrhand','true','1350','1','1','adamantaite','a','282','5','dualfist','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5624','Dragon Grinder - Guidance','lrhand','true','1350','1','1','adamantaite','a','282','5','dualfist','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5625','Dragon Grinder - Health','lrhand','true','1350','1','1','adamantaite','a','282','5','dualfist','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5626','Halberd - Haste','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5627','Halberd - Critical Stun','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5628','Halberd - Wide Blow','lrhand','true','1900','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5629','Orcish Halberd','lrhand','true','1880','1','1','blood_steel','a','219','10','pole','8','-3.00000','0','0','0','325','0','109','-1','21300000','1704','true','true','true','true'),
 ('5630','Orcish Halberd','lrhand','true','1880','1','1','blood_steel','a','219','10','pole','8','-3.00000','0','0','0','325','0','109','-1','21300000','1704','true','true','true','true'),
 ('5631','Orcish Halberd','lrhand','true','1880','1','1','blood_steel','a','219','10','pole','8','-3.00000','0','0','0','325','0','109','-1','21300000','1704','true','true','true','true'),
 ('5632','Tallum Glaive - Guidance','lrhand','true','1840','1','1','chrysolite','a','232','10','pole','8','-3.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5633','Tallum Glaive - Health','lrhand','true','1840','1','1','chrysolite','a','232','10','pole','8','-3.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5634','Tallum Glaive - Wide Blow','lrhand','true','1840','1','1','chrysolite','a','232','10','pole','8','-3.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5635','Tallum Blade - Critical Poison','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5636','Tallum Blade - Haste','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5637','Tallum Blade - Anger','rhand','true','1330','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','-1','18300000','1464','true','true','true','true'),
 ('5638','Elemental Sword - Magic Power','rhand','true','830','1','1','mithril','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('5639','Elemental Sword - Magic Paralyze','rhand','true','830','1','1','mithril','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('5640','Elemental Sword - Empower','rhand','true','830','1','1','mithril','a','170','10','sword','8','0.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('5641','Sword of Miracles - Magic Power','rhand','true','840','1','1','adamantaite','a','186','10','sword','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('5642','Sword of Miracles - Magic Silence','rhand','true','840','1','1','adamantaite','a','186','10','sword','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('5643','Sword of Miracles - Acumen','rhand','true','840','1','1','adamantaite','a','186','10','sword','8','0.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('5644','Dragon Slayer - Health','lrhand','true','1840','1','1','bone','a','282','10','bigsword','8','0.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5645','Dragon Slayer - Critical Bleed','lrhand','true','1840','1','1','bone','a','282','10','bigsword','8','0.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5646','Dragon Slayer - Critical Drain','lrhand','true','1840','1','1','bone','a','282','10','bigsword','8','0.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5647','Dark Legion\'s Edge - Critical Damage','rhand','true','1320','1','1','fine_steel','a','232','10','sword','8','0.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5648','Dark Legion\'s Edge - Health','rhand','true','1320','1','1','fine_steel','a','232','10','sword','8','0.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5649','Dark Legion\'s Edge - Rsk. Focus','rhand','true','1320','1','1','fine_steel','a','232','10','sword','8','0.00000','0','0','0','379','0','114','-1','27000000','2160','true','true','true','true'),
 ('5704','Keshanberk*Keshanberk','lrhand','true','2080','1','1','fine_steel','a','259','10','dual','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('5705','Keshanberk*Damascus','lrhand','true','2080','1','1','fine_steel','a','275','10','dual','8','0.00000','0','0','0','325','0','112','-1','24100000','1928','true','true','true','true'),
 ('5706','Damascus*Damascus','lrhand','true','2080','1','1','fine_steel','a','282','10','dual','8','0.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('5791','Tomb Guard A','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','967000','0','true','true','true','true'),
 ('5792','Tomb Guard B','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','967000','0','true','true','true','true'),
 ('5793','Tomb Savant A','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5794','Tomb Savant B','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5795','Tomb Guard A','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','967000','0','true','true','true','true'),
 ('5796','Tomb Guard B','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','967000','0','true','true','true','true'),
 ('5797','Tomb Savant A','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5798','Tomb Savant B','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5799','Nephilim Lord','lhand','true','1320','0','0','leather','d','0','0','none','0','0.00000','-8','142','20','0','0','0','-1','78400','0','true','true','true','true'),
 ('5800','Nephilim Lord','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5801','Dusk','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5802','Dawn','lhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','6130000','0','true','true','true','true'),
 ('5817','Chrono Campana','lrhand','false','0','0','0','steel','none','1','10','pole','8','-3.00000','0','0','0','325','0','1','-1','0','0','true','false','true','true'),
 ('6307','Sword of Limit - Guidance','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('6308','Sword of Limit - Critical Drain','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('6309','Sword of Limit - Health','rhand','true','1400','3','3','fine_steel','c','139','10','sword','8','0.00000','0','0','0','379','0','76','-1','4300000','1720','true','true','true','true'),
 ('6310','Sword of Whispering Death - Empower','rhand','true','920','3','3','fine_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6311','Sword of Whispering Death - Magic Power','rhand','true','920','3','3','fine_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6312','Sword of Whispering Death - Magic Silence','rhand','true','920','3','3','fine_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6313','Homunkulus\'s Sword - Acumen','rhand','true','950','3','3','blood_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6314','Homunkulus\'s Sword - Conversion','rhand','true','950','3','3','blood_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6315','Homunkulus\'s Sword - Magic Paralyze','rhand','true','950','3','3','blood_steel','c','111','10','sword','8','0.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('6347','Berserker Blade - Focus','lrhand','true','1380','3','3','oriharukon','c','190','10','bigsword','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('6348','Berserker Blade - Critical Damage','lrhand','true','1380','3','3','oriharukon','c','190','10','bigsword','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('6349','Berserker Blade - Haste','lrhand','true','1380','3','3','oriharukon','c','190','10','bigsword','8','0.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('6354','Falchion - for Beginners','rhand','false','1530','2','2','bronze','none','31','10','sword','8','0.00000','0','0','0','379','0','21','-1','0','0','false','false','false','false'),
 ('6355','Mage Staff - for Beginners','lrhand','false','1050','2','2','wood','none','30','20','bigblunt','4','4.00000','0','0','0','325','0','28','-1','0','0','false','false','false','false'),
 ('6356','Dark Elven Dagger - Rsk. Haste','rhand','true','1050','2','2','fine_steel','c','94','5','dagger','12','-3.00000','0','0','0','433','0','61','-1','2290000','916','true','true','true','true'),
 ('6357','Stiletto - Rsk. Haste','rhand','true','1030','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','-1','2870000','1148','true','true','true','true'),
 ('6358','Crystal Dagger - Critical Damage','rhand','true','1000','3','3','crystal','c','136','5','dagger','12','-3.00000','0','0','0','433','0','83','-1','6130000','2452','true','true','true','true'),
 ('6359','Demon Dagger - Critical Damage','rhand','true','970','1','1','blood_steel','b','170','5','dagger','12','-3.00000','0','0','0','433','0','99','-1','13100000','1746','true','true','true','true'),
 ('6364','Forgotten Blade','rhand','true','1300','1','1','adamantaite','s','281','10','sword','8','0.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6365','Basalt Battlehammer','rhand','true','1570','1','1','adamantaite','s','281','20','blunt','4','4.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6366','Imperial Staff','lrhand','true','910','1','1','adamantaite','s','274','20','bigblunt','4','4.00000','0','0','0','325','0','175','-1','48800000','2440','true','true','true','true'),
 ('6367','Angel Slayer','rhand','true','950','1','1','adamantaite','s','246','5','dagger','12','-3.00000','0','0','0','433','0','132','-1','48800000','2440','true','true','true','true'),
 ('6368','Shining Bow','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('6369','Dragon Hunter Axe','lrhand','true','1820','1','1','adamantaite','s','342','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6370','Saint Spear','lrhand','true','1800','1','1','adamantaite','s','281','10','pole','8','-3.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6371','Demon Splinter','lrhand','true','1350','1','1','adamantaite','s','342','5','dualfist','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6372','Heaven\'s Divider','lrhand','true','1380','1','1','adamantaite','s','342','10','bigsword','8','0.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6377','Imperial Crusader Shield','lhand','true','1170','0','0','bone','s','0','0','none','0','0.00000','-8','290','20','0','0','0','-1','3760000','188','true','true','true','true'),
 ('6529','Baby Duck Rod','lrhand','false','1000','0','0','bone','none','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','30000','0','true','true','true','true'),
 ('6530','Albatross Rod','lrhand','false','1000','0','0','bone','d','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','100000','0','true','true','true','true'),
 ('6531','Pelican Rod','lrhand','false','1000','0','0','bone','c','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','400000','0','true','true','true','true'),
 ('6532','KingFisher Rod','lrhand','false','1000','0','0','bone','b','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','1200000','0','true','true','true','true'),
 ('6533','Cygnus Pole','lrhand','false','1000','0','0','bone','a','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','4000000','0','true','true','true','true'),
 ('6534','Triton Pole','lrhand','false','1000','0','0','bone','s','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','10000000','0','true','true','true','true'),
 ('6579','Arcana Mace','rhand','true','1300','1','1','adamantaite','s','225','20','blunt','4','4.00000','0','0','0','379','0','175','-1','48800000','2440','true','true','true','true'),
 ('6580','Tallum Blade*Dark Legion\'s Edge','lrhand','true','2080','1','1','adamantaite','s','342','10','dual','8','0.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6581','Forgotten Blade - Haste','rhand','true','1300','1','1','adamantaite','s','281','10','sword','8','0.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6582','Forgotten Blade - Health','rhand','true','1300','1','1','adamantaite','s','281','10','sword','8','0.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6583','Forgotten Blade - Focus','rhand','true','1300','1','1','adamantaite','s','281','10','sword','8','0.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6584','Basalt Battlehammer - HP Drain','rhand','true','1570','1','1','adamantaite','s','281','20','blunt','4','4.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6585','Basalt Battlehammer - Health','rhand','true','1570','1','1','adamantaite','s','281','20','blunt','4','4.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6586','Basalt Battlehammer - HP Regeneration','rhand','true','1570','1','1','adamantaite','s','281','20','blunt','4','4.00000','0','0','0','379','0','132','-1','48800000','2440','true','true','true','true'),
 ('6587','Imperial Staff - Empower','lrhand','true','910','1','1','adamantaite','s','274','20','bigblunt','4','4.00000','0','0','0','325','0','175','-1','48800000','2440','true','true','true','true'),
 ('6588','Imperial Staff - MP Regeneration','lrhand','true','910','1','1','adamantaite','s','274','20','bigblunt','4','4.00000','0','0','0','325','0','175','-1','48800000','2440','true','true','true','true'),
 ('6589','Imperial Staff - Magic Hold','lrhand','true','910','1','1','adamantaite','s','274','20','bigblunt','4','4.00000','0','0','0','325','0','175','-1','48800000','2440','true','true','true','true'),
 ('6590','Angel Slayer - Crt. Damage','rhand','true','950','1','1','adamantaite','s','246','5','dagger','12','-3.00000','0','0','0','433','0','132','-1','48800000','2440','true','true','true','true'),
 ('6591','Angel Slayer - HP Drain','rhand','true','950','1','1','adamantaite','s','246','5','dagger','12','-3.00000','0','0','0','433','0','132','-1','48800000','2440','true','true','true','true'),
 ('6592','Angel Slayer - Haste','rhand','true','950','1','1','adamantaite','s','246','5','dagger','12','-3.00000','0','0','0','433','0','132','-1','48800000','2440','true','true','true','true'),
 ('6593','Shining Bow - Cheap Shot','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('6594','Shining Bow - Focus','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('6595','Shining Bow - Crt. Slow','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('6596','Dragon Hunter Axe - HP Regeneration','lrhand','true','1820','1','1','adamantaite','s','342','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6597','Dragon Hunter Axe - Health','lrhand','true','1820','1','1','adamantaite','s','342','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6598','Dragon Hunter Axe - HP Drain','lrhand','true','1820','1','1','adamantaite','s','342','20','bigblunt','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6599','Saint Spear - Health','lrhand','true','1800','1','1','adamantaite','s','281','10','pole','8','-3.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6600','Saint Spear - Guidance','lrhand','true','1800','1','1','adamantaite','s','281','10','pole','8','-3.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6601','Saint Spear - Haste','lrhand','true','1800','1','1','adamantaite','s','281','10','pole','8','-3.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6602','Demon Splinter - Focus','lrhand','true','1350','1','1','adamantaite','s','342','5','dualfist','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6603','Demon Splinter - Health','lrhand','true','1350','1','1','adamantaite','s','342','5','dualfist','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6604','Demon Splinter - Crt. Stun','lrhand','true','1350','1','1','adamantaite','s','342','5','dualfist','4','4.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6605','Heavens Divider - Haste','lrhand','true','1380','1','1','adamantaite','s','342','10','bigsword','8','0.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6606','Heavens Divider - Health','lrhand','true','1380','1','1','adamantaite','s','342','10','bigsword','8','0.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6607','Heavens Divider - Focus','lrhand','true','1380','1','1','adamantaite','s','342','10','bigsword','8','0.00000','0','0','0','325','0','132','-1','48800000','2440','true','true','true','true'),
 ('6608','Arcana Mace - Acumen','rhand','true','1300','1','1','adamantaite','s','225','20','blunt','4','4.00000','0','0','0','379','0','175','-1','48800000','2440','true','true','true','true'),
 ('6609','Arcana Mace - MP Regeneration','rhand','true','1300','1','1','adamantaite','s','225','20','blunt','4','4.00000','0','0','0','379','0','175','-1','48800000','2440','true','true','true','true'),
 ('6610','Arcana Mace - Mana Up','rhand','true','1300','1','1','adamantaite','s','225','20','blunt','4','4.00000','0','0','0','379','0','175','-1','48800000','2440','true','true','true','true'),
 ('6611','Infinity Blade','rhand','false','1300','1','1','adamantaite','s','297','10','sword','8','0.00000','0','0','0','379','0','137','-1','57000000','2850','false','false','false','false'),
 ('6612','Infinity Cleaver','lrhand','false','1300','1','1','adamantaite','s','361','10','bigsword','8','0.00000','0','0','0','325','0','137','-1','57000000','2850','false','false','false','false'),
 ('6613','Infinity Axe','rhand','false','1300','1','1','adamantaite','s','297','20','blunt','4','4.00000','0','0','0','379','0','137','-1','57000000','2850','false','false','false','false'),
 ('6614','Infinity Rod','rhand','false','1300','1','1','adamantaite','s','238','20','blunt','4','4.00000','0','0','0','379','0','182','-1','57000000','2850','false','false','false','false'),
 ('6615','Infinity Crusher','lrhand','false','1300','1','1','adamantaite','s','361','20','bigblunt','4','4.00000','0','0','0','325','0','137','-1','57000000','2850','false','false','false','false'),
 ('6616','Infinity Scepter','lrhand','false','1300','1','1','adamantaite','s','290','20','bigblunt','4','4.00000','0','0','0','325','0','182','-1','57000000','2850','false','false','false','false'),
 ('6617','Infinity Stinger','rhand','false','1300','1','1','adamantaite','s','260','5','dagger','12','-3.00000','0','0','0','433','0','137','-1','57000000','2850','false','false','false','false'),
 ('6618','Infinity Fang','lrhand','false','1300','1','1','adamantaite','s','361','5','dualfist','4','4.00000','0','0','0','325','0','137','-1','57000000','2850','false','false','false','false'),
 ('6619','Infinity Bow','lrhand','false','1300','1','1','adamantaite','s','614','5','bow','12','-3.00000','0','0','0','293','11','137','-1','57000000','2850','false','false','false','false'),
 ('6620','Infinity Wing','lrhand','false','1300','1','1','adamantaite','s','361','10','dual','8','0.00000','0','0','0','325','0','137','-1','57000000','2850','false','false','false','false'),
 ('6621','Infinity Spear','lrhand','false','1300','1','1','adamantaite','s','297','10','pole','8','-3.00000','0','0','0','325','0','137','-1','57000000','2850','false','false','false','false'),
 ('6678','Sealed Imperial Crusader Shield','lhand','true','1170','0','0','bone','s','0','0','none','0','0.00000','-8','276','20','0','0','0','-1','3760000','188','true','true','true','true'),
 ('6715','Monster Only(Silenos Archer)','lrhand','false','1950','1','1','wood','none','16','5','bow','12','-3.00000','0','0','0','293','1','6','-1','768','0','true','true','true','true'),
 ('6716','Monster Only(Silenos Shaman)','lrhand','false','1080','1','1','wood','none','11','20','bigblunt','4','4.00000','0','0','0','325','0','12','-1','12500','0','true','true','true','true'),
 ('6717','Monster Only(Einhasad Warrior)','rhand','false','1560','2','2','fine_steel','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','136000','0','true','true','true','true'),
 ('6718','Monster Only(Einhasad Shaman)','lrhand','false','1560','2','2','adamantaite','none','24','20','bigblunt','4','4.00000','0','0','0','325','0','17','-1','136000','0','true','true','true','true'),
 ('6719','Monster Only(Ketra Orc Chieftain)','lrhand','false','990','3','3','blood_steel','c','141','20','bigblunt','4','4.00000','0','0','0','325','0','104','-1','4780000','1912','true','true','true','true'),
 ('6720','Monster Only(Shadow of Halisha)','lrhand','false','1950','1','1','wood','none','16','5','bow','12','-3.00000','0','0','0','293','1','6','-1','768','0','true','true','true','true'),
 ('6721','Monster Only(Shield of Imperial Warlord Zombie)','lhand','false','1430','0','0','leather','none','0','0','none','0','0.00000','-8','47','20','0','0','0','-1','39','0','true','true','true','true'),
 ('6722','Monster Only(Ahrimanes)','lrhand','false','2080','1','1','fine_steel','b','236','10','dual','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('6723','Monster Only(Vampire Warrior)','lrhand','false','2090','3','3','fine_steel','d','112','10','bigsword','8','0.00000','0','0','0','325','0','54','-1','1800000','3272','true','true','true','true'),
 ('6902','Pledge Shield','lhand','true','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','true','true','true','true'),
 ('6917','Monster Only (Poison Sting)','rhand','false','6','0','0','wood','none','0','0','fist','0','0.00000','0','0','0','0','0','0','-1','2','0','true','true','true','true'),
 ('6918','Monster Only (Shield of Silenos)','lhand','false','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','true','true','true','true'),
 ('6919','Monster Only (Shield of Ketra Orc)','lhand','false','1320','0','0','fine_steel','none','0','0','none','0','0.00000','-8','142','20','0','0','0','-1','78400','142','true','true','true','true'),
 ('7014','Monster Only (Shield of Dark Dragon)','lhand','false','4800','0','0','damascus','none','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','845000','0','true','true','true','true'),
 ('7015','Shield of Castle Pledge','lhand','true','1380','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','-1','12900','0','true','true','true','true'),
 ('7058','Chrono Darbuka','lrhand','false','0','0','0','steel','none','1','20','bigblunt','4','4.00000','0','0','0','325','0','1','-1','0','0','true','false','true','true'),
 ('7560','Monster Only (Fishing Rod)','lrhand','false','1000','0','0','bone','none','1','1','rod','0','-3.00000','0','0','0','325','0','1','-1','30000','0','false','false','true','false'),
 ('7575','Draconic Bow','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('7576','Draconic Bow - Cheap Shot','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('7577','Draconic Bow - Focus','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('7578','Draconic Bow - Critical Slow','lrhand','true','1650','1','1','adamantaite','s','581','5','bow','12','-3.00000','0','0','0','293','11','132','-1','48800000','2440','true','true','true','true'),
 ('7701','Stick of Faith - Mana Up','rhand','true','1160','2','2','wood','c','85','20','blunt','4','4.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('7702','Stick of Faith - Magic Hold','rhand','true','1160','2','2','wood','c','85','20','blunt','4','4.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('7703','Stick of Faith - Magic Shield','rhand','true','1160','2','2','wood','c','85','20','blunt','4','4.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('7704','Stick of Eternity - Empower','rhand','true','1130','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7705','Stick of Eternity - Rsk., Evasion','rhand','true','1130','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7706','Stick of Eternity - Blessed Body','rhand','true','1130','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7707','Nirvana Axe - Magic Power','rhand','true','1150','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7708','Nirvana Axe - Magic Poison','rhand','true','1150','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7709','Nirvana Axe - Magic Weakness','rhand','true','1150','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7710','Club of Nature - Acumen','rhand','true','1100','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7711','Club of Nature - Magic Mental Shield','rhand','true','1100','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7712','Club of Nature - Magic Hold','rhand','true','1100','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7713','Mace of The Underworld - Mana Up','rhand','true','1090','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7714','Mace of The Underworld - Magic Silence','rhand','true','1090','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7715','Mace of The Underworld - Conversion','rhand','true','1090','3','3','fine_steel','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','-1','4300000','1720','true','true','true','true'),
 ('7716','Inferno Staff - Acumen','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('7717','Inferno Staff - Magic Silence','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('7718','Inferno Staff - Magic Paralyze','lrhand','true','1000','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','-1','4300000','1720','true','true','true','true'),
 ('7719','Poleaxe - Critical Stun','lrhand','true','2010','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('7720','Poleaxe - Towering Blow','lrhand','true','2010','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('7721','Poleaxe - Wide Blow','lrhand','true','2010','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('7722','Sword of Valhalla - Acumen','rhand','true','900','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('7723','Sword of Valhalla - Magic Weakness','rhand','true','900','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('7724','Sword of Valhalla - Magic Regeneration','rhand','true','900','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('7810','Soulfire Dirk - Mana Up','rhand','true','750','2','2','fine_steel','c','86','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','2870000','1148','true','true','true','true'),
 ('7811','Soulfire Dirk - Magic Hold','rhand','true','750','2','2','fine_steel','c','86','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','2870000','1148','true','true','true','true'),
 ('7812','Soulfire Dirk - Magic Silence','rhand','true','750','2','2','fine_steel','c','86','5','dagger','12','-3.00000','0','0','0','433','0','91','-1','2870000','1148','true','true','true','true'),
 ('7813','Hell Knife - Magic Regeneration','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('7814','Hell Knife - Mental Shield','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('7815','Hell Knife - Magic Weakness','rhand','true','740','1','1','blood_steel','b','122','5','dagger','12','-3.00000','0','0','0','433','0','122','-1','8680000','1157','true','true','true','true'),
 ('7816','Apprentice Adventurer\'s Staff','lrhand','false','1070','2','2','wood','none','23','20','bigblunt','4','4.00000','0','0','0','325','0','22','-1','0','0','false','false','true','false'),
 ('7817','Apprentice Adventurer\'s Bone Club','rhand','false','1850','2','2','fine_steel','none','24','20','blunt','4','4.00000','0','0','0','379','0','17','-1','0','0','false','false','true','false'),
 ('7818','Apprentice Adventurer\'s Knife','rhand','false','1120','2','2','mithril','none','21','5','dagger','12','-3.00000','0','0','0','433','0','17','-1','0','0','false','false','true','false'),
 ('7819','Apprentice Adventurer\'s Cestus','lrhand','false','1570','2','2','steel','none','29','5','dualfist','4','4.00000','0','0','0','325','0','17','-1','0','0','false','false','true','false'),
 ('7820','Apprentice Adventurer\'s Bow','lrhand','false','1900','7','2','wood','none','49','5','bow','12','-3.00000','0','0','0','293','3','17','-1','0','0','false','false','true','false'),
 ('7821','Apprentice Adventurer\'s Long Sword','rhand','false','1560','2','2','fine_steel','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','0','0','false','false','true','false'),
 ('7822','Traveler\'s Mace','rhand','false','1300','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','0','0','false','false','true','false'),
 ('7823','Traveler\'s Dark Elven Bow','lrhand','false','1830','6','2','steel','d','105','5','bow','12','-3.00000','0','0','0','293','4','32','-1','0','0','false','false','true','false'),
 ('7824','Traveler\'s Long Bow','lrhand','false','1830','6','2','steel','d','105','5','bow','12','-3.00000','0','0','0','293','4','32','-1','0','0','false','false','true','false'),
 ('7825','Traveler\'s Staff','lrhand','false','1040','2','2','wood','d','50','20','bigblunt','4','4.00000','0','0','0','325','0','43','-1','0','0','false','false','true','false'),
 ('7826','Traveler\'s Bastard Sword','rhand','false','1510','2','2','fine_steel','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','0','0','false','false','true','false'),
 ('7827','Traveler\'s Spellbook','rhand','false','570','2','2','steel','d','41','10','etc','8','0.00000','0','0','0','379','0','43','-1','0','0','false','false','true','false'),
 ('7828','Traveler\'s Jamadhr','lrhand','false','1550','2','2','steel','d','62','5','dualfist','4','4.00000','0','0','0','325','0','32','-1','0','0','false','false','true','false'),
 ('7829','Traveler\'s Tomahawk','rhand','false','1780','2','2','bronze','d','51','20','blunt','4','4.00000','0','0','0','379','0','32','-1','0','0','false','false','true','false'),
 ('7830','Traveler\'s Poniard Dagger','rhand','false','1090','2','2','steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','32','-1','0','0','false','false','true','false'),
 ('7831','Traveler\'s Pike','lrhand','false','2090','2','2','steel','d','51','10','pole','8','-3.00000','0','0','0','325','0','32','-1','0','0','false','false','true','false'),
 ('7834','Art of Battle Axe','rhand','true','1570','1','1','chrysolite','b','194','20','blunt','4','4.00000','0','0','0','379','0','99','-1','13100000','1746','true','true','true','true'),
 ('7880','Steel Sword','lrhand','true','2100','2','2','fine_steel','d','49','10','bigsword','8','0.00000','0','0','0','325','0','26','-1','409000','743','true','true','true','true'),
 ('7881','Titan Sword','lrhand','true','2020','3','3','fine_steel','d','96','10','bigsword','8','0.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('7882','Pa\'agrian Sword','lrhand','true','1980','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('7883','Guardian Sword','lrhand','true','1930','1','1','steel','b','236','10','bigsword','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('7884','Infernal Master','lrhand','true','1900','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('7885','Priest Sword','rhand','true','1520','2','2','fine_steel','d','32','10','sword','8','0.00000','0','0','0','379','0','35','-1','409000','743','true','true','true','true'),
 ('7886','Sword of Magic Fog','rhand','true','1450','3','3','fine_steel','d','63','10','sword','8','0.00000','0','0','0','379','0','63','-1','1400000','2545','true','true','true','true'),
 ('7887','Mysterious Sword','rhand','true','1430','2','2','fine_steel','c','85','10','sword','8','0.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('7888','Ecliptic Sword','rhand','true','1380','3','3','oriharukon','c','125','10','sword','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('7889','Wizard\'s Tear','rhand','true','1350','1','1','oriharukon','b','155','10','sword','8','0.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('7890','Priest Mace','rhand','true','1720','3','3','steel','d','63','20','blunt','4','4.00000','0','0','0','379','0','63','-1','1400000','2545','true','true','true','true'),
 ('7891','Ecliptic Axe','rhand','true','1640','3','3','fine_steel','c','125','20','blunt','4','4.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('7892','Spell Breaker','rhand','true','1620','1','1','fine_steel','b','140','20','blunt','4','4.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('7893','Kaim Vanul\'s Bones','rhand','true','1570','1','1','bone','b','155','20','blunt','4','4.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('7894','Spiritual Eye','rhand','true','1550','1','1','bone','a','170','20','blunt','4','4.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('7895','Flaming Dragon Skull','rhand','true','1530','1','1','fine_steel','a','186','20','blunt','4','4.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('7896','Titan Hammer','lrhand','true','2100','3','3','steel','d','96','20','bigblunt','4','4.00000','0','0','0','325','0','47','-1','1400000','2545','true','true','true','true'),
 ('7897','Dwarven Hammer','lrhand','true','2010','3','3','steel','c','190','20','bigblunt','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('7898','Karik Horn','lrhand','true','2020','3','3','oriharukon','c','169','20','bigblunt','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('7899','Destroyer Hammer','lrhand','true','1910','1','1','fine_steel','a','259','20','bigblunt','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('7900','Ice Storm Hammer','lrhand','true','1950','1','1','fine_steel','b','213','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('7901','Star Buster','lrhand','true','1930','1','1','fine_steel','b','236','20','bigblunt','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('7902','Doom Crusher','lrhand','true','1900','1','1','fine_steel','a','282','20','bigblunt','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('7903','Frintezza\'s Sword','lrhand','true','2180','2','2','fine_steel','none','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','0','0','true','true','true','true'),
 ('8102','Pa\'agrian Sword - Focus','lrhand','true','1980','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8103','Pa\'agrian Sword - Health','lrhand','true','1980','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8104','Pa\'agrian Sword - Critical Drain','lrhand','true','1980','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8105','Guardian Sword - Critical Drain','lrhand','true','1930','1','1','steel','b','236','10','bigsword','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8106','Guardian Sword - Health','lrhand','true','1930','1','1','steel','b','236','10','bigsword','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8107','Guardian Sword - Critical Bleed','lrhand','true','1930','1','1','steel','b','236','10','bigsword','8','0.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8108','Infernal Master - Haste','lrhand','true','1900','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8109','Infernal Master - Critical Damage','lrhand','true','1900','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8110','Infernal Master - Focus','lrhand','true','1900','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8111','Mysterious Sword - Acumen','rhand','true','1430','2','2','fine_steel','c','85','10','sword','8','0.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('8112','Mysterious Sword - Magic Power','rhand','true','1430','2','2','fine_steel','c','85','10','sword','8','0.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('8113','Mysterious Sword - Magic Weakness','rhand','true','1430','2','2','fine_steel','c','85','10','sword','8','0.00000','0','0','0','379','0','81','-1','2290000','916','true','true','true','true'),
 ('8114','Ecliptic Sword - Empower','rhand','true','1380','3','3','oriharukon','c','125','10','sword','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8115','Ecliptic Sword - Magic Power','rhand','true','1380','3','3','oriharukon','c','125','10','sword','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8116','Ecliptic Sword - Magic Silence','rhand','true','1380','3','3','oriharukon','c','125','10','sword','8','0.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8117','Wizard\'s Tear - Acumen','rhand','true','1350','1','1','oriharukon','b','155','10','sword','8','0.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8118','Wizard\'s Tear - Magic Power','rhand','true','1350','1','1','oriharukon','b','155','10','sword','8','0.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8119','Wizard\'s Tear - Conversion','rhand','true','1350','1','1','oriharukon','b','155','10','sword','8','0.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8120','Dwarven Hammer - Health','lrhand','true','2010','3','3','steel','c','190','20','bigblunt','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('8121','Dwarven Hammer - Anger','lrhand','true','2010','3','3','steel','c','190','20','bigblunt','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('8122','Dwarven Hammer - Critical Bleed','lrhand','true','2010','3','3','steel','c','190','20','bigblunt','4','4.00000','0','0','0','325','0','83','-1','6130000','2452','true','true','true','true'),
 ('8123','Karik Horn - Focus','lrhand','true','2020','3','3','oriharukon','c','169','20','bigblunt','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8124','Karik Horn - Haste','lrhand','true','2020','3','3','oriharukon','c','169','20','bigblunt','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8125','Karik Horn - Critical Drain','lrhand','true','2020','3','3','oriharukon','c','169','20','bigblunt','4','4.00000','0','0','0','325','0','76','-1','4300000','1720','true','true','true','true'),
 ('8126','Destroyer Hammer - Health','lrhand','true','1910','1','1','fine_steel','a','259','20','bigblunt','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8127','Destroyer Hammer - Haste','lrhand','true','1910','1','1','fine_steel','a','259','20','bigblunt','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8128','Destroyer Hammer - Critical Drain','lrhand','true','1910','1','1','fine_steel','a','259','20','bigblunt','4','4.00000','0','0','0','325','0','107','-1','18300000','1464','true','true','true','true'),
 ('8129','Ice Storm Hammer - Focus','lrhand','true','1950','1','1','fine_steel','b','213','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('8130','Ice Storm Hammer - Anger','lrhand','true','1950','1','1','fine_steel','b','213','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('8131','Ice Storm Hammer - Critical Bleed','lrhand','true','1950','1','1','fine_steel','b','213','20','bigblunt','4','4.00000','0','0','0','325','0','91','-1','8680000','1157','true','true','true','true'),
 ('8132','Star Buster - Health','lrhand','true','1930','1','1','fine_steel','b','236','20','bigblunt','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8133','Star Buster - Haste','lrhand','true','1930','1','1','fine_steel','b','236','20','bigblunt','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8134','Star Buster - Rsk. Focus','lrhand','true','1930','1','1','fine_steel','b','236','20','bigblunt','4','4.00000','0','0','0','325','0','99','-1','13100000','1746','true','true','true','true'),
 ('8135','Doom Crusher - Health','lrhand','true','1900','1','1','fine_steel','a','282','20','bigblunt','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('8136','Doom Crusher - Anger','lrhand','true','1900','1','1','fine_steel','a','282','20','bigblunt','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('8137','Doom Crusher - Rsk. Haste','lrhand','true','1900','1','1','fine_steel','a','282','20','bigblunt','4','4.00000','0','0','0','325','0','114','-1','27000000','2160','true','true','true','true'),
 ('8138','Ecliptic Axe - Conversion','rhand','true','1640','3','3','fine_steel','c','125','20','blunt','4','4.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8139','Ecliptic Axe - Magic Power','rhand','true','1640','3','3','fine_steel','c','125','20','blunt','4','4.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8140','Ecliptic Axe - Magic Hold','rhand','true','1640','3','3','fine_steel','c','125','20','blunt','4','4.00000','0','0','0','379','0','111','-1','6130000','2452','true','true','true','true'),
 ('8141','Spell Breaker - Acumen','rhand','true','1620','1','1','fine_steel','b','140','20','blunt','4','4.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('8142','Spell Breaker - Magic Mental Shield','rhand','true','1620','1','1','fine_steel','b','140','20','blunt','4','4.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('8143','Spell Breaker - Magic Hold','rhand','true','1620','1','1','fine_steel','b','140','20','blunt','4','4.00000','0','0','0','379','0','122','-1','8680000','1157','true','true','true','true'),
 ('8144','Kaim Vanul\'s Bones - Mana Up','rhand','true','1570','1','1','bone','b','155','20','blunt','4','4.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8145','Kaim Vanul\'s Bones - Magic Silence','rhand','true','1570','1','1','bone','b','155','20','blunt','4','4.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8146','Kaim Vanul\'s Bones - Conversion','rhand','true','1570','1','1','bone','b','155','20','blunt','4','4.00000','0','0','0','379','0','132','-1','13100000','1746','true','true','true','true'),
 ('8147','Spiritual Eye - Mana Up','rhand','true','1550','1','1','bone','a','170','20','blunt','4','4.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('8148','Spiritual Eye - Magic Poison','rhand','true','1550','1','1','bone','a','170','20','blunt','4','4.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('8149','Spiritual Eye - Acumen','rhand','true','1550','1','1','bone','a','170','20','blunt','4','4.00000','0','0','0','379','0','143','-1','18300000','1464','true','true','true','true'),
 ('8150','Flaming Dragon Skull - Acumen','rhand','true','1530','1','1','fine_steel','a','186','20','blunt','4','4.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('8151','Flaming Dragon Skull - Magic Power','rhand','true','1530','1','1','fine_steel','a','186','20','blunt','4','4.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('8152','Flaming Dragon Skull - Magic Silence','rhand','true','1530','1','1','fine_steel','a','186','20','blunt','4','4.00000','0','0','0','379','0','152','-1','27000000','2160','true','true','true','true'),
 ('8190','Demonic Sword Zariche','lrhand','true','1840','0','0','bone','none','361','10','bigsword','8','0.00000','0','0','0','325','0','137','-1','0','0','false','false','false','false'),
 ('8203','Monster Only (Venom Sword)','lrhand','true','1560','1','1','fine_steel','none','0','10','dual','8','0.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8204','Monster Only (Follower of Frintezza Calibur)','rhand','false','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8205','Monster Only (Heretic Priest Sickle)','lrhand','true','1920','1','1','blood_steel','none','0','10','pole','8','-3.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8206','Monster Only (Heretic Priest Sword)','lrhand','true','2080','1','1','fine_steel','none','0','10','dual','8','0.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8207','Monster Only (Heretic Private Axe)','lrhand','true','1560','1','1','fine_steel','none','0','10','dual','8','0.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8208','Monster Only (Heretic Private Staff)','lrhand','true','1560','1','1','fine_steel','none','0','10','pole','8','-3.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8209','Monster Only (Lidia Von Helmann Sword)','rhand','false','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8210','Monster Only (Monk Warrior Shield)','lhand','false','4800','0','0','damascus','none','0','0','none','0','0.00000','-8','230','20','0','0','0','-1','0','0','true','true','true','true'),
 ('8211','Monster Only (Monk Warrior Sword)','rhand','false','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8212','Monster Only (Solina Brother Mace)','rhand','true','1570','1','1','chrysolite','none','0','20','blunt','4','4.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8213','Monster Only (Solina Father Mace)','rhand','true','1570','1','1','chrysolite','none','0','20','blunt','4','4.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8214','Monster Only (Solina Priest Pole)','lrhand','false','1920','1','1','blood_steel','none','0','10','pole','8','-3.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8215','Monster Only (Zombie Enlisted Man Sword)','lrhand','true','2090','3','3','fine_steel','none','0','10','bigsword','8','0.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8216','Monster Only (Zombie Enlisted Man Claw)','lrhand','false','1350','1','1','adamantaite','none','0','5','dualfist','4','4.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8217','Monster Only (Zombie Gateguard Spear)','rhand','true','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8218','Monster Only (Zombie Laborer Axe)','rhand','true','1570','1','1','chrysolite','none','0','20','blunt','4','4.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8219','Monster Only (Zombie Laborer Sword)','rhand','true','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8220','Monster Only (Apostle Grail Bow)','lrhand','true','1950','1','1','wood','none','0','5','bow','12','-3.00000','0','0','0','293','1','0','-1','0','0','true','true','true','true'),
 ('8221','Monster Only (Apostle Grail Spear)','lrhand','false','1920','1','1','blood_steel','none','0','10','pole','8','-3.00000','0','0','0','325','0','0','-1','0','0','true','true','true','true'),
 ('8222','Monster Only (Follower of Frintezza Tran Calibur)','rhand','true','1560','2','2','fine_steel','none','0','10','sword','8','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8350','Chrono Maracas','lrhand','true','0','0','0','fine_steel','none','1','5','dual','8','0.00000','0','0','0','325','0','1','-1','0','0','true','false','true','true'),
 ('8527','For Monsters Only (Strengthened Bow)','lrhand','true','1870','6','2','steel','d','82','5','bow','12','-3.00000','0','0','0','293','3','26','-1','0','0','true','true','true','true'),
 ('8528','For Monsters Only (Doom Hammer)','rhand','true','1200','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','0','0','true','true','true','true'),
 ('8529','For Monsters Only (Knife)','rhand','false','1140','1','1','bronze','none','10','5','dagger','12','-3.00000','0','0','0','433','0','9','-1','0','0','true','true','true','true'),
 ('8530','For Monsters Only (Squire\'s Sword)','rhand','false','1600','1','1','steel','none','6','10','sword','8','0.00000','0','0','0','379','0','5','-1','0','0','true','true','true','true'),
 ('8531','For Monsters Only (Bone Staff)','lrhand','true','1060','2','2','bone','d','39','20','bigblunt','4','4.00000','0','0','0','325','0','35','-1','0','0','true','true','true','true'),
 ('8532','For Monsters Only (Spinebone Sword)','rhand','true','1510','2','2','bone','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','0','0','true','true','true','true'),
 ('8533','For Monsters Only (Crimson Sword)','rhand','true','1490','2','2','fine_steel','d','64','10','sword','8','0.00000','0','0','0','379','0','39','-1','0','0','true','true','true','true'),
 ('8576','Apprentice\'s Staff - For Coupon Event','lrhand','false','1070','2','2','wood','none','23','20','bigblunt','4','4.00000','0','0','0','325','0','22','-1','0','0','false','false','false','false'),
 ('8577','Bone Club - For Coupon Event','rhand','false','1850','2','2','fine_steel','none','24','20','blunt','4','4.00000','0','0','0','379','0','17','-1','0','0','false','false','false','false'),
 ('8578','Shining Knife - For Coupon Event','rhand','false','1120','2','2','mithril','none','21','5','dagger','12','-3.00000','0','0','0','433','0','17','-1','0','0','false','false','false','false'),
 ('8579','Cestus - For Coupon Event','lrhand','false','1570','2','2','steel','none','29','5','dualfist','4','4.00000','0','0','0','325','0','17','-1','0','0','false','false','false','false'),
 ('8580','Forest Bow - For Coupon Event','lrhand','false','1900','7','2','wood','none','49','5','bow','12','-3.00000','0','0','0','293','3','17','-1','0','0','false','false','false','false'),
 ('8581','Long Sword - For Coupon Event','rhand','false','1560','2','2','fine_steel','none','24','10','sword','8','0.00000','0','0','0','379','0','17','-1','0','0','false','false','false','false'),
 ('8582','Mace of Prayer - For Coupon Event','rhand','false','1300','2','2','steel','d','41','20','blunt','4','4.00000','0','0','0','379','0','43','-1','0','0','false','false','false','false'),
 ('8583','Dark Elven Bow - For Coupon Event','lrhand','false','1830','6','2','steel','d','105','5','bow','12','-3.00000','0','0','0','293','4','32','-1','0','0','false','false','false','false'),
 ('8584','Long Bow - For Coupon Event','lrhand','false','1830','6','2','steel','d','114','5','bow','12','-3.00000','0','0','0','227','4','35','-1','0','0','false','false','false','false'),
 ('8585','Staff of Mana - For Coupon Event','lrhand','false','1040','2','2','wood','d','50','20','bigblunt','4','4.00000','0','0','0','325','0','43','-1','0','0','false','false','false','false'),
 ('8586','Bastard Sword - For Coupon Event','rhand','false','1510','2','2','fine_steel','d','51','10','sword','8','0.00000','0','0','0','379','0','32','-1','0','0','false','false','false','false'),
 ('8587','Divine Tomb - For Coupon Event','rhand','false','570','2','2','steel','d','41','10','etc','8','0.00000','0','0','0','379','0','43','-1','0','0','false','false','false','false'),
 ('8588','Single-Edged Jamadhr - For Coupon Event','lrhand','false','1550','2','2','steel','d','62','5','dualfist','4','4.00000','0','0','0','325','0','32','-1','0','0','false','false','false','false'),
 ('8589','Tomahawk - For Coupon Event','rhand','false','1780','2','2','bronze','d','51','20','blunt','4','4.00000','0','0','0','379','0','32','-1','0','0','false','false','false','false'),
 ('8590','Poniard Daggar - For Coupon Event','rhand','false','1090','2','2','steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','32','-1','0','0','false','false','false','false'),
 ('8591','Pike - For Coupon Event','lrhand','false','2090','2','2','steel','d','51','10','pole','8','-3.00000','0','0','0','325','0','32','-1','0','0','false','false','false','false'),
 ('8678','Sirra\'s Blade','rhand','true','1300','1','1','fine_steel','a','251','10','sword','8','0.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8679','Sword of Ipos','lrhand','true','1820','1','1','bone','a','305','10','bigsword','8','0.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8680','Barakiel\'s Axe','rhand','true','1550','1','1','oriharukon','a','251','20','blunt','4','4.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8681','Behemoth\'s Tuning Fork','lrhand','true','1890','1','1','fine_steel','a','305','20','bigblunt','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8682','Naga Storm','rhand','true','930','1','1','oriharukon','a','220','5','dagger','12','-3.00000','0','0','0','433','0','121','-1','0','2824','true','true','true','true'),
 ('8683','Tiphon\'s Spear','lrhand','true','1820','1','1','chrysolite','a','251','10','pole','8','-3.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8684','Shyeed\'s Bow','lrhand','true','1640','2','1','oriharukon','a','570','5','bow','12','-3.00000','0','0','0','227','10','133','-1','0','2824','true','true','true','true'),
 ('8685','Sobekk\'s Hurricane','lrhand','true','1330','1','1','adamantaite','a','305','5','dualfist','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8686','Themis\' Tongue','rhand','true','820','1','1','adamantaite','a','202','10','sword','8','0.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8687','Cabrio\'s Hand','rhand','true','1510','1','1','fine_steel','a','202','20','blunt','4','4.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8688','Daimon Crystal','lrhand','true','880','1','1','wood','a','245','20','bigblunt','4','4.00000','0','0','0','325','0','161','-1','0','2824','true','true','true','true'),
 ('8689','Blood Sword Akamanah','lrhand','true','1840','0','0','bone','none','361','10','dual','8','0.00000','0','0','0','325','0','137','-1','0','0','false','false','false','false'),
 ('8763','Elrokian Trap','rhand','false','500','0','0','oriharukon','a','0','0','blunt','0','0.00000','0','0','0','379','0','0','-1','0','0','true','true','true','true'),
 ('8788','Sirra\'s Blade - Haste','rhand','true','1300','1','1','fine_steel','a','251','10','sword','8','0.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8789','Sirra\'s Blade - Health','rhand','true','1300','1','1','fine_steel','a','251','10','sword','8','0.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8790','Sirra\'s Blade - Critical Poison','rhand','true','1300','1','1','fine_steel','a','251','10','sword','8','0.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8791','Sword of Ipos - Focus','lrhand','true','1820','1','1','bone','a','305','10','bigsword','8','0.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8792','Sword of Ipos - Haste','lrhand','true','1820','1','1','bone','a','305','10','bigsword','8','0.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8793','Sword of Ipos - Health','lrhand','true','1820','1','1','bone','a','305','10','bigsword','8','0.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8794','Barakiel\'s Axe - Health','rhand','true','1550','1','1','oriharukon','a','251','20','blunt','4','4.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8795','Barakiel\'s Axe - Haste','rhand','true','1550','1','1','oriharukon','a','251','20','blunt','4','4.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8796','Barakiel\'s Axe - Focus','rhand','true','1550','1','1','oriharukon','a','251','20','blunt','4','4.00000','0','0','0','379','0','121','-1','0','2824','true','true','true','true'),
 ('8797','Behemoth\'s Tuning Fork - Focus','lrhand','true','1890','1','1','fine_steel','a','305','20','bigblunt','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8798','Behemoth\'s Tuning Fork - Health','lrhand','true','1890','1','1','fine_steel','a','305','20','bigblunt','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8799','Behemoth\'s Tuning Fork - Anger','lrhand','true','1890','1','1','fine_steel','a','305','20','bigblunt','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8800','Naga Storm - Focus','rhand','true','930','1','1','oriharukon','a','220','5','dagger','12','-3.00000','0','0','0','433','0','121','-1','0','2824','true','true','true','true'),
 ('8801','Naga Storm - Critical Damage','rhand','true','930','1','1','oriharukon','a','220','5','dagger','12','-3.00000','0','0','0','433','0','121','-1','0','2824','true','true','true','true'),
 ('8802','Naga Storm - Back Blow','rhand','true','930','1','1','oriharukon','a','220','5','dagger','12','-3.00000','0','0','0','433','0','121','-1','0','2824','true','true','true','true'),
 ('8803','Tiphon\'s Spear - Critical Stun','lrhand','true','1820','1','1','chrysolite','a','251','10','pole','8','-3.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8804','Tiphon\'s Spear - Towering Blow','lrhand','true','1820','1','1','chrysolite','a','251','10','pole','8','-3.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8805','Tiphon\'s Spear - Wild Blow','lrhand','true','1820','1','1','chrysolite','a','251','10','pole','8','-3.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8806','Shyeed\'s Bow - Cheap Shot','lrhand','true','1640','2','1','oriharukon','a','570','5','bow','12','-3.00000','0','0','0','227','10','133','-1','0','2824','true','true','true','true'),
 ('8807','Shyeed\'s Bow - Focus','lrhand','true','1640','2','1','oriharukon','a','570','5','bow','12','-3.00000','0','0','0','227','10','133','-1','0','2824','true','true','true','true'),
 ('8808','Shyeed\'s Bow - Quick Recovery','lrhand','true','1640','2','1','oriharukon','a','570','5','bow','12','-3.00000','0','0','0','227','10','133','-1','0','2824','true','true','true','true'),
 ('8809','Sobekk\'s Hurricane - Rsk. Haste','lrhand','true','1330','1','1','adamantaite','a','305','5','dualfist','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8810','Sobekk\'s Hurricane - Haste','lrhand','true','1330','1','1','adamantaite','a','305','5','dualfist','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8811','Sobekk\'s Hurricane - Critical Drain','lrhand','true','1330','1','1','adamantaite','a','305','5','dualfist','4','4.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8812','Themis\' Tongue - Mana Up','rhand','true','820','1','1','adamantaite','a','202','10','sword','8','0.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8813','Themis\' Tongue - Magic Mental Shield','rhand','true','820','1','1','adamantaite','a','202','10','sword','8','0.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8814','Themis\' Tongue - Magic Focus','rhand','true','820','1','1','adamantaite','a','202','10','sword','8','0.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8815','Cabrio\'s Hand - Conversion','rhand','true','1510','1','1','fine_steel','a','202','20','blunt','4','4.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8816','Cabrio\'s Hand - Mana Up','rhand','true','1510','1','1','fine_steel','a','202','20','blunt','4','4.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8817','Cabrio\'s Hand - Magic Silence','rhand','true','1510','1','1','fine_steel','a','202','20','blunt','4','4.00000','0','0','0','379','0','161','-1','0','2824','true','true','true','true'),
 ('8818','Daimon Crystal - Mana Up','lrhand','true','880','1','1','wood','a','245','20','bigblunt','4','4.00000','0','0','0','325','0','161','-1','0','2824','true','true','true','true'),
 ('8819','Daimon Crystal - Acumen','lrhand','true','880','1','1','wood','a','245','20','bigblunt','4','4.00000','0','0','0','325','0','161','-1','0','2824','true','true','true','true'),
 ('8820','Daimon Crystal - Magic Mental Shield','lrhand','true','880','1','1','wood','a','245','20','bigblunt','4','4.00000','0','0','0','325','0','161','-1','0','2824','true','true','true','true'),
 ('8821','Shadow Weapon: Two Handed Sword','lrhand','true','730','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','90','0','0','false','false','true','false'),
 ('8822','Shadow Weapon: Crimson Sword','rhand','true','500','2','2','fine_steel','d','64','10','sword','8','0.00000','0','0','0','379','0','39','90','0','0','false','false','true','false'),
 ('8823','Shadow Weapon: Spiked Club','rhand','true','590','2','2','steel','d','64','20','blunt','4','4.00000','0','0','0','379','0','39','90','0','0','false','false','true','false'),
 ('8824','Shadow Weapon: Staff of Magic','lrhand','true','340','2','2','wood','d','62','20','bigblunt','4','4.00000','0','0','0','325','0','52','90','0','0','false','false','true','false'),
 ('8825','Shadow Weapon: Kukuri','rhand','true','360','2','2','fine_steel','d','56','5','dagger','12','-3.00000','0','0','0','433','0','39','90','0','0','false','false','true','false'),
 ('8826','Shadow Weapon: Dagger of Mana','rhand','true','250','2','2','fine_steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','90','0','0','false','false','true','false'),
 ('8827','Shadow Weapon: Triple-Edged Jamadhr','lrhand','true','520','2','2','fine_steel','d','78','5','dualfist','4','4.00000','0','0','0','325','0','39','90','0','0','false','false','true','false'),
 ('8828','Shadow Weapon: Gastraphetes','lrhand','true','620','8','2','fine_steel','d','132','5','bow','12','-3.00000','0','0','0','293','4','39','90','0','0','false','false','true','false'),
 ('8829','Shadow Weapon: War Hammer','lrhand','true','700','2','2','fine_steel','d','64','10','pole','8','-3.00000','0','0','0','325','0','39','90','0','0','false','false','true','false'),
 ('8830','Shadow Weapon: Katana','rhand','true','480','2','2','fine_steel','c','122','10','sword','8','0.00000','0','0','0','379','0','68','300','0','0','false','false','true','false'),
 ('8831','Shadow Weapon: Bec de Corbin','lrhand','true','680','2','2','fine_steel','c','122','10','pole','8','-3.00000','0','0','0','325','0','68','300','0','0','false','false','true','false'),
 ('8832','Shadow Weapon: Cursed Staff','lrhand','true','340','2','2','wood','c','119','20','bigblunt','4','4.00000','0','0','0','325','0','91','300','0','0','false','false','true','false'),
 ('8833','Shadow Weapon: Stiletto','rhand','true','350','2','2','fine_steel','c','107','5','dagger','12','-3.00000','0','0','0','433','0','68','300','0','0','false','false','true','false'),
 ('8834','Shadow Weapon: Soulfire Dirk','rhand','true','250','2','2','fine_steel','c','86','5','dagger','12','-3.00000','0','0','0','433','0','91','300','0','0','false','false','true','false'),
 ('8835','Shadow Weapon: Elemental Bow','lrhand','true','590','8','2','fine_steel','c','277','5','bow','12','-3.00000','0','0','0','227','7','75','300','0','0','false','false','true','false'),
 ('8836','Shadow Weapon: Dwarven War Hammer','rhand','true','560','2','2','fine_steel','c','122','20','blunt','4','4.00000','0','0','0','379','0','68','300','0','0','false','false','true','false'),
 ('8837','Shadow Weapon: Sword of Revolution','lrhand','true','790','2','2','fine_steel','c','148','10','dual','8','0.00000','0','0','0','325','0','68','300','0','0','false','false','true','false'),
 ('8838','Shadow Weapon: Knuckle Duster','lrhand','true','500','2','2','fine_steel','c','148','5','dualfist','4','4.00000','0','0','0','325','0','68','300','0','0','false','false','true','false'),
 ('8839','Shadow Weapon: Sword of Delusion','rhand','true','470','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','300','0','0','false','false','true','false'),
 ('8840','Shadow Weapon: Poleaxe','lrhand','true','670','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','300','0','0','false','false','true','false'),
 ('8841','Shadow Weapon: Stick of Eternity','rhand','true','380','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','300','0','0','false','false','true','false'),
 ('8842','Shadow Weapon: Inferno Staff','lrhand','true','340','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','300','0','0','false','false','true','false'),
 ('8843','Shadow Weapon: Pa\'agrian Hammer','lrhand','true','340','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','300','0','0','false','false','true','false'),
 ('8844','Shadow Weapon: Dark Screamer','rhand','true','340','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','300','0','0','false','false','true','false'),
 ('8845','Shadow Weapon: Akat Long Bow','lrhand','true','580','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','300','0','0','false','false','true','false'),
 ('8846','Shadow Weapon: Pa\'agrian Sword','lrhand','true','660','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','300','0','0','false','false','true','false'),
 ('8847','Shadow Weapon: Fisted Blade','lrhand','true','500','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','300','0','0','false','false','true','false'),
 ('8848','Shadow Weapon: Stormbringer','lrhand','true','780','3','3','fine_steel','c','175','10','dual','8','0.00000','0','0','0','325','0','78','300','0','0','false','false','true','false'),
 ('8849','Shadow Weapon: Great Sword','lrhand','true','650','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','300','0','0','false','false','true','false'),
 ('8850','Shadow Weapon: Heavy War Axe','rhand','true','540','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','300','0','0','false','false','true','false'),
 ('8851','Shadow Weapon: Sprite\'s Staff','lrhand','true','320','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','300','0','0','false','false','true','false'),
 ('8852','Shadow Weapon: Keshanberk','rhand','true','460','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','300','0','0','false','false','true','false'),
 ('8853','Shadow Weapon: Sword of Valhalla','rhand','true','300','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','300','0','0','false','false','true','false'),
 ('8854','Shadow Weapon: Kris','rhand','true','330','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','300','0','0','false','false','true','false'),
 ('8855','Shadow Weapon: Arthro Nail','lrhand','true','480','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','300','0','0','false','false','true','false'),
 ('8856','Shadow Weapon: Dark Elven Long Bow','lrhand','true','580','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','300','0','0','false','false','true','false'),
 ('8857','Shadow Weapon: Swords of Delusion','lrhand','true','720','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','300','0','0','false','false','true','false'),
 ('8858','Shadow Weapon: Great Axe','lrhand','true','650','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','300','0','0','false','false','true','false'),
 ('8859','Shadow Weapon: Tallum Blade','rhand','true','450','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','300','0','0','false','false','true','false'),
 ('8860','Shadow Weapon: Halberd','lrhand','true','640','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','300','0','0','false','false','true','false'),
 ('8861','Shadow Weapon: Dasparion\'s Staff','lrhand','true','310','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','300','0','0','false','false','true','false'),
 ('8862','Shadow Weapon: Bloody Orchid','rhand','true','320','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','300','0','0','false','false','true','false'),
 ('8863','Shadow Weapon: Blood Tornado','lrhand','true','460','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','300','0','0','false','false','true','false'),
 ('8864','Shadow Weapon: Carnage Bow','lrhand','true','560','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','300','0','0','false','false','true','false'),
 ('8865','Shadow Weapon: Dual Keshanberk','lrhand','true','700','1','1','fine_steel','a','259','10','dual','8','0.00000','0','0','0','325','0','107','300','0','0','false','false','true','false'),
 ('8866','Shadow Weapon: Meteor Shower','rhand','true','540','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','300','0','0','false','false','true','false'),
 ('8867','Shadow Weapon: Inferno Master','lrhand','true','640','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','300','0','0','false','false','true','false'),
 ('8926','Shadow Weapon: Sword of Delusion','rhand','true','470','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','120','0','0','false','false','true','false'),
 ('8927','Shadow Weapon: Poleaxe','lrhand','true','670','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','120','0','0','false','false','true','false'),
 ('8928','Shadow Weapon: Stick of Eternity','rhand','true','380','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','120','0','0','false','false','true','false'),
 ('8929','Shadow Weapon: Inferno Staff','lrhand','true','340','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','120','0','0','false','false','true','false'),
 ('8930','Shadow Weapon: Pa\'agrian Hammer','lrhand','true','340','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','120','0','0','false','false','true','false'),
 ('8931','Shadow Weapon: Dark Screamer','rhand','true','340','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','120','0','0','false','false','true','false'),
 ('8932','Shadow Weapon: Akat Long Bow','lrhand','true','580','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','120','0','0','false','false','true','false'),
 ('8933','Shadow Weapon: Pa\'agrian Sword','lrhand','true','660','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','120','0','0','false','false','true','false'),
 ('8934','Shadow Weapon: Fisted Blade','lrhand','true','500','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','120','0','0','false','false','true','false'),
 ('8935','Test Rapier','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','120','0','0','true','true','true','true'),
 ('8937','Shadow Weapon: Stormbringer','lrhand','true','780','3','3','fine_steel','c','175','10','dual','8','0.00000','0','0','0','325','0','78','120','0','0','false','false','true','false'),
 ('8938','Damascus * Tallum Blade','lrhand','true','1890','1','1','fine_steel','a','305','10','dual','8','0.00000','0','0','0','325','0','121','-1','0','2824','true','true','true','true'),
 ('8972','Shadow Weapon: Long Spear','lrhand','false','707','2','2','bronze','none','31','10','pole','8','-3.00000','0','0','0','325','0','21','600','0','0','false','false','true','false'),
 ('8973','Shadow Weapon: Falchion','rhand','false','510','2','2','bronze','none','31','10','sword','8','0.00000','0','0','0','379','0','21','600','0','0','false','false','true','false'),
 ('8974','Shadow Weapon: Iron Hammer','rhand','false','617','2','2','steel','none','31','20','blunt','4','4.00000','0','0','0','379','0','21','600','0','0','false','false','true','false'),
 ('8975','Shadow Weapon: Voodoo Doll','rhand','false','210','2','2','wood','none','25','10','etc','8','0.00000','0','0','0','379','0','28','600','0','0','false','false','true','false'),
 ('8976','Shadow Weapon: Buzdygan','rhand','false','600','2','2','steel','none','31','20','blunt','4','4.00000','0','0','0','379','0','21','600','0','0','false','false','true','false'),
 ('8977','Shadow Weapon: Mage Staff','lrhand','false','350','2','2','wood','none','30','20','bigblunt','4','4.00000','0','0','0','325','0','28','600','0','0','false','false','true','false'),
 ('8978','Shadow Weapon: Sword Breaker','rhand','false','370','2','2','steel','none','27','5','dagger','12','-3.00000','0','0','0','433','0','21','600','0','0','false','false','true','false'),
 ('8979','Shadow Weapon: Viper\'s Fang','lrhand','false','520','2','2','steel','none','38','5','dualfist','4','4.00000','0','0','0','325','0','21','600','0','0','false','false','true','false'),
 ('8980','Shadow Weapon: Composition Bow','lrhand','false','627','7','2','steel','none','64','5','bow','12','-3.00000','0','0','0','293','3','21','600','0','0','false','false','true','false'),
 ('8981','Shadow Weapon: Crucifix of Blessing','rhand','false','207','2','2','steel','none','25','10','etc','8','0.00000','0','0','0','379','0','28','600','0','0','false','false','true','false'),
 ('8982','Shadow Weapon: Two Handed Sword','lrhand','true','730','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','600','0','0','false','false','true','false'),
 ('8983','Shadow Weapon: Crimson Sword','rhand','true','500','2','2','fine_steel','d','64','10','sword','8','0.00000','0','0','0','379','0','39','600','0','0','false','false','true','false'),
 ('8984','Shadow Weapon: Spiked Club','rhand','true','590','2','2','steel','d','64','20','blunt','4','4.00000','0','0','0','379','0','39','600','0','0','false','false','true','false'),
 ('8985','Shadow Weapon: Staff of Magic','lrhand','true','340','2','2','wood','d','62','20','bigblunt','4','4.00000','0','0','0','325','0','52','600','0','0','false','false','true','false'),
 ('8986','Shadow Weapon: Kukuri','rhand','true','360','2','2','fine_steel','d','56','5','dagger','12','-3.00000','0','0','0','433','0','39','600','0','0','false','false','true','false'),
 ('8987','Shadow Weapon: Dagger of Mana','rhand','true','250','2','2','fine_steel','d','45','5','dagger','12','-3.00000','0','0','0','433','0','52','600','0','0','false','false','true','false'),
 ('8988','Shadow Weapon: Triple-Edged Jamadhr','lrhand','true','520','2','2','fine_steel','d','78','5','dualfist','4','4.00000','0','0','0','325','0','39','600','0','0','false','false','true','false'),
 ('8989','Shadow Weapon: Gastraphetes','lrhand','true','620','8','2','fine_steel','d','132','5','bow','12','-3.00000','0','0','0','293','4','39','600','0','0','false','false','true','false'),
 ('8990','Shadow Weapon: War Hammer','lrhand','true','700','2','2','fine_steel','d','64','10','pole','8','-3.00000','0','0','0','325','0','39','600','0','0','false','false','true','false'),
 ('8991','Shadow Weapon: Sword of Delusion','rhand','true','470','3','3','mithril','c','139','10','sword','8','0.00000','0','0','0','379','0','76','600','0','0','false','false','true','false'),
 ('8992','Shadow Weapon: Poleaxe','lrhand','true','670','3','3','bronze','c','139','10','pole','8','-3.00000','0','0','0','325','0','76','600','0','0','false','false','true','false'),
 ('8993','Shadow Weapon: Stick of Eternity','rhand','true','380','3','3','wood','c','111','20','blunt','4','4.00000','0','0','0','379','0','101','600','0','0','false','false','true','false'),
 ('8994','Shadow Weapon: Inferno Staff','lrhand','true','340','3','3','wood','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','600','0','0','false','false','true','false'),
 ('8995','Shadow Weapon: Pa\'agrian Hammer','lrhand','true','340','3','3','blood_steel','c','135','20','bigblunt','4','4.00000','0','0','0','325','0','101','600','0','0','false','false','true','false'),
 ('8996','Shadow Weapon: Dark Screamer','rhand','true','340','3','3','fine_steel','c','122','5','dagger','12','-3.00000','0','0','0','433','0','76','600','0','0','false','false','true','false'),
 ('8997','Shadow Weapon: Akat Long Bow','lrhand','true','580','9','3','horn','c','316','5','bow','12','-3.00000','0','0','0','227','7','84','600','0','0','false','false','true','false'),
 ('8998','Shadow Weapon: Pa\'agrian Sword','lrhand','true','660','3','3','steel','c','169','10','bigsword','8','0.00000','0','0','0','325','0','76','600','0','0','false','false','true','false'),
 ('8999','Shadow Weapon: Fisted Blade','lrhand','true','500','3','3','fine_steel','c','169','5','dualfist','4','4.00000','0','0','0','325','0','76','600','0','0','false','false','true','false'),
 ('9000','Shadow Weapon: Stormbringer*Stormbringer','lrhand','true','780','3','3','fine_steel','c','175','10','dual','8','0.00000','0','0','0','325','0','78','600','0','0','false','false','true','false'),
 ('9001','Shadow Weapon: Great Sword','lrhand','true','650','1','1','steel','b','213','10','bigsword','8','0.00000','0','0','0','325','0','91','600','0','0','false','false','true','false'),
 ('9002','Shadow Weapon: Heavy War Axe','rhand','true','540','1','1','fine_steel','b','175','20','blunt','4','4.00000','0','0','0','379','0','91','600','0','0','false','false','true','false'),
 ('9003','Shadow Weapon: Sprite\'s Staff','lrhand','true','320','1','1','wood','b','170','20','bigblunt','4','4.00000','0','0','0','325','0','122','600','0','0','false','false','true','false'),
 ('9004','Shadow Weapon: Keshanberk','rhand','true','460','1','1','oriharukon','b','175','10','sword','8','0.00000','0','0','0','379','0','91','600','0','0','false','false','true','false'),
 ('9005','Shadow Weapon: Sword of Valhalla','rhand','true','300','1','1','oriharukon','b','140','10','sword','8','0.00000','0','0','0','379','0','122','600','0','0','false','false','true','false'),
 ('9006','Shadow Weapon: Kris','rhand','true','330','1','1','mithril','b','153','5','dagger','12','-3.00000','0','0','0','433','0','91','600','0','0','false','false','true','false'),
 ('9007','Shadow Weapon: Arthro Nail','lrhand','true','480','1','1','blood_steel','b','213','5','dualfist','4','4.00000','0','0','0','325','0','91','600','0','0','false','false','true','false'),
 ('9008','Shadow Weapon: Dark Elven Long Bow','lrhand','true','580','3','1','oriharukon','b','397','5','bow','12','-3.00000','0','0','0','227','8','100','600','0','0','false','false','true','false'),
 ('9009','Shadow Weapon: Sword of Delusion*Sword of Delusion','lrhand','true','720','1','1','fine_steel','b','213','10','dual','8','0.00000','0','0','0','325','0','91','600','0','0','false','false','true','false'),
 ('9010','Shadow Weapon: Great Axe','lrhand','true','650','1','1','damascus','b','175','10','pole','8','-3.00000','0','0','0','325','0','91','600','0','0','false','false','true','false'),
 ('9011','Shadow Weapon: Tallum Blade','rhand','true','450','1','1','adamantaite','a','213','10','sword','8','0.00000','0','0','0','379','0','107','600','0','0','false','false','true','false'),
 ('9012','Shadow Weapon: Halberd','lrhand','true','640','1','1','oriharukon','a','213','10','pole','8','-3.00000','0','0','0','325','0','107','600','0','0','false','false','true','false'),
 ('9013','Shadow Weapon: Dasparion\'s Staff','lrhand','true','310','1','1','adamantaite','a','207','20','bigblunt','4','4.00000','0','0','0','325','0','143','600','0','0','false','false','true','false'),
 ('9014','Shadow Weapon: Bloody Orchid','rhand','true','320','1','1','blood_steel','a','186','5','dagger','12','-3.00000','0','0','0','433','0','107','600','0','0','false','false','true','false'),
 ('9015','Shadow Weapon: Blood Tornado','lrhand','true','460','1','1','blood_steel','a','259','5','dualfist','4','4.00000','0','0','0','325','0','107','600','0','0','false','false','true','false'),
 ('9016','Shadow Weapon: Carnage Bow','lrhand','true','560','2','1','damascus','a','440','5','bow','12','-3.00000','0','0','0','293','9','107','600','0','0','false','false','true','false'),
 ('9017','Shadow Weapon: Keshanberk*Keshanberk','lrhand','true','700','1','1','fine_steel','a','259','10','dual','8','0.00000','0','0','0','325','0','107','600','0','0','false','false','true','false'),
 ('9018','Shadow Weapon: Meteor Shower','rhand','true','540','1','1','fine_steel','a','213','20','blunt','4','4.00000','0','0','0','379','0','107','600','0','0','false','false','true','false'),
 ('9019','Shadow Weapon: Inferno Master','lrhand','true','640','1','1','fine_steel','a','259','10','bigsword','8','0.00000','0','0','0','325','0','107','600','0','0','false','false','true','false'),
 ('9020','Shadow Weapon: Keshanberk*Damascus','lrhand','true','693','1','1','fine_steel','a','275','10','dual','8','0.00000','0','0','0','325','0','112','600','0','0','false','false','true','false'),
 ('9021','Shadow Weapon: Dragon Slayer','lrhand','true','613','1','1','bone','a','282','10','bigsword','8','0.00000','0','0','0','325','0','114','600','0','0','false','false','true','false'),
 ('9022','Shadow Weapon: Sword of Miracles','rhand','true','280','1','1','adamantaite','a','186','10','sword','8','0.00000','0','0','0','379','0','152','600','0','0','false','false','true','false'),
 ('9023','Shadow Weapon: Elysian','rhand','true','527','1','1','oriharukon','a','232','20','blunt','4','4.00000','0','0','0','379','0','114','600','0','0','false','false','true','false'),
 ('9024','Shadow Weapon: Branch of the Mother Tree','lrhand','true','300','1','1','wood','a','226','20','bigblunt','4','4.00000','0','0','0','325','0','152','600','0','0','false','false','true','false'),
 ('9025','Shadow Weapon: Soul Separator','rhand','true','317','1','1','oriharukon','a','203','5','dagger','12','-3.00000','0','0','0','433','0','114','600','0','0','false','false','true','false'),
 ('9026','Shadow Weapon: Dragon Grinder','lrhand','true','450','1','1','adamantaite','a','282','5','dualfist','4','4.00000','0','0','0','325','0','114','600','0','0','false','false','true','false'),
 ('9027','Shadow Weapon: Soul Bow','lrhand','true','553','2','1','oriharukon','a','528','5','bow','12','-3.00000','0','0','0','227','10','125','600','0','0','false','false','true','false'),
 ('9028','Shadow Weapon: Tallum Glaive','lrhand','true','613','1','1','chrysolite','a','232','10','pole','8','-3.00000','0','0','0','325','0','114','600','0','0','false','false','true','false'),
 ('9029','Shadow Weapon: Dark Legion','rhand','true','440','1','1','fine_steel','a','232','10','sword','8','0.00000','0','0','0','379','0','114','600','0','0','false','false','true','false'),
 ('9036','Shadow Weapon: Bone Shield','lhand','false','460','0','0','bone','none','0','0','none','0','0.00000','-8','90','20','0','0','0','600','0','0','false','false','true','false'),
 ('9044','Shadow Weapon: Hoplon','lhand','true','447','0','0','fine_steel','d','0','0','none','0','0.00000','-8','128','20','0','0','0','600','0','0','false','false','true','false'),
 ('9056','Shadow Weapon: Composite Shield','lhand','true','410','0','0','fine_steel','c','0','0','none','0','0.00000','-8','190','20','0','0','0','600','0','0','false','false','true','false'),
 ('9070','Shadow Weapon: Zubei\'s Shield','lhand','true','403','0','0','mithril','b','0','0','none','0','0.00000','-8','216','20','0','0','0','600','0','0','false','false','true','false'),
 ('9084','Shadow Weapon: Dark Crystal Shield','lhand','true','397','0','0','crystal','a','0','0','none','0','0.00000','-8','243','20','0','0','0','600','0','0','false','false','true','false'),
 ('9129','Shadow Weapon: Shield of Nightmare','lhand','true','400','0','0','leather','a','0','0','none','0','0.00000','-8','256','20','0','0','0','600','0','0','false','false','true','false'),
 ('9136','Sword of Valakas (1-Handed)','rhand','true','1380','3','3','oriharukon','c','156','10','sword','8','0.00000','0','0','0','379','0','83','-1','0','0','true','true','true','true'),
 ('9137','Sword of Valakas (2-Handed)','lrhand','true','2180','2','2','fine_steel','d','78','10','bigsword','8','0.00000','0','0','0','325','0','39','-1','0','0','true','true','true','true'),
 ('9140','Cupid\'s Bow','lrhand','false','900','1','1','wood','none','1','1','bow','12','-3.00000','0','0','0','293','1','1','-1','0','0','true','true','true','true'),
 ('9141','Cupid\'s Bow - Event Use','lrhand','false','300','1','1','wood','none','1','1','bow','12','-3.00000','0','0','0','293','1','1','60','0','0','true','true','true','true');


-- apologies for the dump of this below... I'll create the proper tables when I get some time

ALTER TABLE weapon ADD item_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD item_skill_lvl decimal(11,0) NOT NULL default '0';

ALTER TABLE weapon ADD enchant4_skill_id decimal(11,0) NOT NULL default '0'; -- for duals +4
ALTER TABLE weapon ADD enchant4_skill_lvl decimal(11,0) NOT NULL default '0';

ALTER TABLE weapon ADD onCast_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCast_skill_lvl decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCast_skill_chance decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_id decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_lvl decimal(11,0) NOT NULL default '0';
ALTER TABLE weapon ADD onCrit_skill_chance decimal(11,0) NOT NULL default '0';

-- +4  passiveskills ------
 
-- UPDATE weapon SET enchant4_skill_id = , enchant4_skill_lvl = WHERE item_id = ; --

--     passive weapon SAs     --
                                         --    swords   --
UPDATE weapon  SET item_skill_id = 3026, item_skill_lvl = 1 WHERE item_id = 4681; -- stormbringer 'critical anger' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4682; -- stormbringer 'focus' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4683; -- stormbringer 'light' 

UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 2 WHERE item_id = 4684; -- shamshir 'guidance' 
UPDATE weapon  SET item_skill_id = 3018, item_skill_lvl = 2 WHERE item_id = 4685; -- shamshir 'back blow' 
UPDATE weapon  SET item_skill_id = 3028, item_skill_lvl = 2 WHERE item_id = 4686; -- shamshir 'rsk. evasion'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 2 WHERE item_id = 4687; -- katana 'focus' 
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 2 WHERE item_id = 4688; -- katana 'critical damage' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4689; -- katana 'haste'

UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 2 WHERE item_id = 4690; -- spirit sword 'critical damage' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4692; -- spirit sword 'haste'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 2 WHERE item_id = 4693; -- raid sword 'focus'

UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 3 WHERE item_id = 4696; -- caliburs 'guidance' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4697; -- caliburs 'focus'
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4698; -- caliburs 'critical damage'

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4699; -- sword of delusion 'focus'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4700; -- sword of delusion 'health'
UPDATE weapon  SET item_skill_id = 3032, item_skill_lvl = 3 WHERE item_id = 4701; -- sword of delustion 'rsk. haste'
	  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4702; -- tsurugi 'focus'
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4703; -- tsurugi 'critical damage'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 3 WHERE item_id = 4704; -- tsurugi 'haste'
  
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4705; -- sword of nightmare 'health'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 3 WHERE item_id = 4706; -- sword of nightmare 'focus'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4707; -- sword of nightmare 'light'
  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 4 WHERE item_id = 4708; -- samurai long sword 'focus'  
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 4 WHERE item_id = 4709; -- samurai long sword 'critical damage'  
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 4 WHERE item_id = 4710; -- samurai long sword 'haste'

UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 1 WHERE item_id = 4711; -- flamberge 'critical damage'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4712; -- flamberge 'focus'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4713; -- flamberge 'light'
  
UPDATE weapon  SET item_skill_id = 3007, item_skill_lvl = 5 WHERE item_id = 4714; -- keshanberk 'guidance'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 5 WHERE item_id = 4715; -- keshanberk 'focus'  
UPDATE weapon  SET item_skill_id = 3018, item_skill_lvl = 5 WHERE item_id = 4716; -- keshanberk 'back blow'  

UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 6 WHERE item_id = 4717; -- sword of damascus 'focus'     
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 3 WHERE item_id = 4718; -- sword of damascus 'critical damage'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4719; -- sword of damascus 'haste'

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4720; -- tallum blade 'health'
UPDATE weapon  SET item_skill_id = 3028, item_skill_lvl = 1 WHERE item_id = 4721; -- tallum blade 'rsk.evasion' 
UPDATE weapon  SET item_skill_id = 3032, item_skill_lvl = 2 WHERE item_id = 4722; -- tallum blade 'rsk.haste'
  
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4723; -- great sword 'health'  
UPDATE weapon  SET item_skill_id = 3023, item_skill_lvl = 5 WHERE item_id = 4724; -- great sword 'critical damage'  
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 5 WHERE item_id = 4725; -- great sword 'focus'

UPDATE weapon  SET item_skill_id = 3073, item_skill_lvl = 1 WHERE item_id = 5638; -- elemental sword 'magic power'
UPDATE weapon  SET item_skill_id = 3072, item_skill_lvl = 1 WHERE item_id = 5604; -- elemental sword 'empower'

UPDATE weapon  SET item_skill_id = 3073, item_skill_lvl = 2 WHERE item_id = 5641; -- sword of miracles 'magic power'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5643; -- sword of miracles 'acumen'

UPDATE weapon  SET item_skill_id = 3067, item_skill_lvl = 2 WHERE item_id = 5647; -- dark legions edge 'critical damage'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5648; -- dark legions edge 'health'
UPDATE weapon  SET item_skill_id = 3071, item_skill_lvl = 2 WHERE item_id = 5649; -- dark legions edge 'rsk. focus' 

  --     passive weapon SAs     --
                                         --    blunts   --
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4726; -- big hammer 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4727; -- big hammer 'rsk.focus'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4728; -- big hammer 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4729; -- battle axe 'anger' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4730; -- battle axe 'rsk.focus'
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4731; -- battle axe 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4732; -- silver axe 'anger' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4733; -- silver axe 'rsk.focus' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 1 WHERE item_id = 4734; -- silver axe 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 1 WHERE item_id = 4735; -- skull graver 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4736; -- skull graver 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 1 WHERE item_id = 4737; -- skull graver 'rsk.focus'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 2 WHERE item_id = 4738; -- dwarven war hammer 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4739; -- dwarven war hammer 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 2 WHERE item_id = 4740; -- dwarven war hammer 'haste' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 3 WHERE item_id = 4741; -- war axe 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4742; -- war axe 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 3 WHERE item_id = 4743; -- war axe 'haste'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 4 WHERE item_id = 4744; -- yaksa mace 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4745; -- yaksa mace 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 4 WHERE item_id = 4746; -- yaksa mace 'rsk.focus' 

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 5 WHERE item_id = 4747; -- heav war axe 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4748; -- heavy war axe 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 5 WHERE item_id = 4749; -- heavy war axe 'rsk.focus'

UPDATE weapon  SET item_skill_id = 3012, item_skill_lvl = 6 WHERE item_id = 4750; -- deadmans glory 'anger' 
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4751; -- deadmans glory 'health' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4752; -- deadmans glory 'haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4753; -- art of battle axe 'health' 
UPDATE weapon  SET item_skill_id = 3027, item_skill_lvl = 6 WHERE item_id = 4754; -- art of battle axe 'rsk.focus' 
UPDATE weapon  SET item_skill_id = 3036, item_skill_lvl = 6 WHERE item_id = 4755; -- art of battle axe 'haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 1 WHERE item_id = 4756; -- meteor shower 'health' 
UPDATE weapon  SET item_skill_id = 3010, item_skill_lvl = 1 WHERE item_id = 4757; -- meteor shower 'focus' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4758; -- meteor shower 'p.focus'  ?????
UPDATE weapon  SET item_skill_id = 3050, item_skill_lvl = 1 WHERE item_id = 5599; -- meteor shower 'focus'
UPDATE weapon  SET item_skill_id = 3056, item_skill_lvl = 2 WHERE item_id = 5601; -- meteor shower 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5602; -- elysian 'health'
UPDATE weapon  SET item_skill_id = 3057, item_skill_lvl = 2 WHERE item_id = 5603; -- elysian 'anger'


-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = ;
  --     passive weapon SAs     --
                                         --    daggers   --

UPDATE weapon  SET item_skill_id = 3033, item_skill_lvl = 1 WHERE item_id = 4761; -- cursed dagger 'rsk.haste'

UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 1 WHERE item_id = 4762; -- dark elven dagger 'focus'
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 1 WHERE item_id = 4763; -- dark elven dagger 'back blow' 
UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 1 WHERE item_id = 4764; -- dark elven dagger 'might mortal'

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 2 WHERE item_id = 4767; -- stiletoo 'might mortal'
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4768; -- grace dagger 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 3 WHERE item_id = 4769; -- grace dagger 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 3 WHERE item_id = 4770; -- grace dagger 'back blow' 

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4771; -- dark screamer 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 3 WHERE item_id = 4772; -- dark screamer 'focus' 

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 4 WHERE item_id = 4776; -- crystal dagger 'might mortal'
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 5 WHERE item_id = 4777; -- kris 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 5 WHERE item_id = 4778; -- kris 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 5 WHERE item_id = 4779; -- kris 'back blow' 

UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 6 WHERE item_id = 4782; -- deamons sword 'might mortal'

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 6 WHERE item_id = 4783; -- bloody orchid 'evasion' 
UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 2 WHERE item_id = 4784; -- bloody orchid 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 6 WHERE item_id = 4785; -- bloody orchid 'back blow' 
UPDATE weapon  SET item_skill_id = 3051, item_skill_lvl = 1 WHERE item_id = 5614; -- bloody orchid 'focus';

UPDATE weapon  SET item_skill_id = 3011, item_skill_lvl = 5 WHERE item_id = 4786; -- hell knife 'focus' 
UPDATE weapon  SET item_skill_id = 3019, item_skill_lvl = 5 WHERE item_id = 4787; -- hell knife 'back blow' 
UPDATE weapon  SET item_skill_id = 3035, item_skill_lvl = 5 WHERE item_id = 4788; -- hell knife 'might mortal' 

UPDATE weapon  SET item_skill_id = 3064, item_skill_lvl = 1 WHERE item_id = 5617; -- soul separator 'guidance'
UPDATE weapon  SET item_skill_id = 3066, item_skill_lvl = 2 WHERE item_id = 5618; -- soul separator 'critical damage'
UPDATE weapon  SET item_skill_id = 3056, item_skill_lvl = 2 WHERE item_id = 5619; -- sould separator 'rsk. haste';


  --     passive weapon SAs     --
                                         --    fists    --

UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 1 WHERE item_id = 4791; -- chakram 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 3 WHERE item_id = 4792; -- fist blade 'rsk.evasion'
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 3 WHERE item_id = 4793; -- fist blade 'rsk. haste' 
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 3 WHERE item_id = 4794; -- fist blade 'haste'  

UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 4 WHERE item_id = 4797; -- great pata 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 2 WHERE item_id = 4798; -- knuckle duster 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 2 WHERE item_id = 4799; -- knuckle duster 'rsk. haste' 
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 2 WHERE item_id = 4800; -- knuckle duster 'haste'  

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 5 WHERE item_id = 4802; -- arthro nail 'rsk. evasion'  
UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 5 WHERE item_id = 4803; -- arthro nail 'rsk. haste' 

UPDATE weapon  SET item_skill_id = 3034, item_skill_lvl = 6 WHERE item_id = 4806; -- bellion cestus 'rsk. haste'  

UPDATE weapon  SET item_skill_id = 3030, item_skill_lvl = 6 WHERE item_id = 4808; -- blood tornado 'rsk. evasion'
UPDATE weapon  SET item_skill_id = 3037, item_skill_lvl = 6 WHERE item_id = 4809; -- bloody tornado 'haste' 
UPDATE weapon  SET item_skill_id = 3068, item_skill_lvl = 2 WHERE item_id = 5620; -- blood tornado 'haste'
UPDATE weapon  SET item_skill_id = 3565, item_skill_lvl = 1 WHERE item_id = 5621; -- blood tornado 'focus'
UPDATE weapon  SET item_skill_id = 3058, item_skill_lvl = 1 WHERE item_id = 5622; -- blood tornado 'anger';

UPDATE weapon  SET item_skill_id = 3069, item_skill_lvl = 1 WHERE item_id = 5623; -- dragon grinder 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3065, item_skill_lvl = 1 WHERE item_id = 5624; -- dragon grinder 'guidance'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5625; -- dragon grinder 'health'

   --     passive weapon SAs     --
                                            --    bows    --
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 1 WHERE item_id = 4810; -- crystalized ice bow 'guidance'  
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 1 WHERE item_id = 4811; -- crystalized ice bow 'evasion' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4812; -- crystalized ice bow 'quite recovery'
  
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 2 WHERE item_id = 4813; -- elemental bow 'guidance'   
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4814; -- elemental bow 'miser'   
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4815; -- elemental bow 'quick recovery' 
 
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 2 WHERE item_id = 4816; -- elven bow of nobility 'evasion'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4817; -- elven bow of nobility 'miser'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4817; -- elven bow of nobility 'cheap shot'
  
UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 3 WHERE item_id = 4819; -- akat long bow 'guidance'  
UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 3 WHERE item_id = 4820; -- akat long bow 'evasion' 
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4821; --  akat long bow 'miser'

UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 4 WHERE item_id = 4822; -- eminence bow 'guidance'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4823; --  eminence bow 'miser'
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4824; --  eminence bow 'cheap shot'

UPDATE weapon  SET item_skill_id = 3009, item_skill_lvl = 5 WHERE item_id = 4825; -- dark elven long bow 'evasion'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4827; -- dark elven long bow 'miser'  

UPDATE weapon  SET item_skill_id = 3008, item_skill_lvl = 6 WHERE item_id = 4828; -- bow of peril 'guidance'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4829; -- bow of peril 'quick recovery'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4830; -- bow of peril 'cheap shot'  

UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4832; -- carnage bow 'mana up'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4833; --  carnage 'quick recovery'
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 2 WHERE item_id = 5610; -- carnage bow 'mana up'


  --     passive weapon SAs     --
                                           --    pole    --
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 1 WHERE item_id = 4834; -- scythe 'anger'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4836; -- scythe 'light'  

UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 1 WHERE item_id = 4837; -- orcish glaive 'anger'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4839; -- orcish glaive 'long blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4841; -- body slasher 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4842; -- body slasher 'wide blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4844; -- bec de corbin 'long blow'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4845; -- bec de corbin 'light'
  
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 3 WHERE item_id = 4846; --  scorpion 'anger'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4848; -- scorpion 'long blow'  

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4850; -- widow maker 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4851; -- widow maker 'wide blow'

UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4853; -- orcish poleaxe 'long blow'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4854; -- orcish poleaxe 'wide blow'
 
UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 5 WHERE item_id = 4855; -- great axe 'anger'  
-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = 4857; --  great axe 'light'

UPDATE weapon  SET item_skill_id = 3600, item_skill_lvl = 6 WHERE item_id = 4858; -- lance 'anger'
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4860; -- lance 'long blow' 
 
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4862; -- halberd 'long blow'  
UPDATE weapon  SET item_skill_id = 3599, item_skill_lvl = 1 WHERE item_id = 4863; -- halberd 'wide blow'
UPDATE weapon  SET item_skill_id = 3601, item_skill_lvl = 7 WHERE item_id = 5626; -- halberd 'haste'

UPDATE weapon  SET item_skill_id = 3602, item_skill_lvl = 8 WHERE item_id = 5632; -- tallum glaive 'guidance'
UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 4 WHERE item_id = 5633; -- tallum glaive 'health'
UPDATE weapon  SET item_skill_id = 3068, item_skill_lvl = 2 WHERE item_id = 5636; -- tallum glaive 'haste'
UPDATE weapon  SET item_skill_id = 3057, item_skill_lvl = 1 WHERE item_id = 5637; -- tallum blade 'anger'


  --     passive weapon SAs     --
                                           --    staff    --

UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 1 WHERE item_id = 4867; -- crystal staff 'rsk. evasion' 
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4868; -- crystal staff 'mana up'  

UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 3 WHERE item_id = 4879; -- paagrio hammer 'rsk. evasion'  
 
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4885; -- paagrio axe 'mana up' 
 
UPDATE weapon  SET item_skill_id = 3031, item_skill_lvl = 4 WHERE item_id = 4891; -- ghouls staff 'rsk. evasion'   
UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 4892; -- ghouls staff 'mana up' 

UPDATE weapon  SET item_skill_id = 3014, item_skill_lvl = 1 WHERE item_id = 5596; -- dasparions staff 'mana up'
UPDATE weapon  SET item_skill_id = 3048, item_skill_lvl = 2 WHERE item_id = 5597; -- dasparions staff 'conversion'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5598; -- dasparions staff 'acumen'

UPDATE weapon  SET item_skill_id = 3048, item_skill_lvl = 2 WHERE item_id = 5605; -- branch of the mother tree 'conversion'
UPDATE weapon  SET item_skill_id = 3552, item_skill_lvl = 1 WHERE item_id = 5606; -- branch of the mother tree 'magic damage'
UPDATE weapon  SET item_skill_id = 3047, item_skill_lvl = 2 WHERE item_id = 5607; -- branch of the mother tree 'acumen'

-- UPDATE weapon  SET item_skill_id = , item_skill_lvl = WHERE item_id = ; --  
  --     passive weapon SAs     --
                                           --    big swords    --

UPDATE weapon  SET item_skill_id = 3013, item_skill_lvl = 3 WHERE item_id = 5644; -- dragon slayer 'health'

--   active onCrit weapons SA ----
-- UPDATE weapon SET onCrit_skill_id = , onCrit_skill_lvl = , onCrit_skill_chance = WHERE item_id = ; --


--   active onCast weapons SA  ---

-- UPDATE weapon SET onCast_skill_id = , onCast_skill_lvl = , onCast_skill_chance = WHERE item_id = ; --