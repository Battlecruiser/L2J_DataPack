ALTER TABLE `characters` ALTER `title_color` SET DEFAULT 0xECF9A2;
UPDATE `characters` SET `title_color` = 0xECF9A2 WHERE `title_color` = 0xFFFFFF;