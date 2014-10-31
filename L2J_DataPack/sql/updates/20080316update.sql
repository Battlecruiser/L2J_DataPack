UPDATE grandboss_list SET zone = 12000 WHERE zone = 'LairofZaken';
UPDATE grandboss_list SET zone = 12001 WHERE zone = 'LairofAntharas';
UPDATE grandboss_list SET zone = 12002 WHERE zone = 'LairofBaium';
UPDATE grandboss_list SET zone = 12003 WHERE zone = 'LairofAnakim';
UPDATE grandboss_list SET zone = 12003 WHERE zone = 'LairofLilith';
UPDATE grandboss_list SET zone = 12010 WHERE zone = 'LairofValakas';
UPDATE grandboss_list SET zone = 12014 WHERE zone = 'AltarofSacrifice';
ALTER TABLE grandboss_list MODIFY zone decimal(11,0) NOT NULL;