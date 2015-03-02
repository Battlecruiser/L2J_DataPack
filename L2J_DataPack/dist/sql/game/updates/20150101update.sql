ALTER TABLE `messages` ADD `itemId` INT(11) NOT NULL DEFAULT '0' , ADD `enchantLvl` INT(3) NOT NULL DEFAULT '0' , ADD `elementals` VARCHAR(25);
UPDATE character_quests SET name='Q00340_SubjugationOfLizardmen' WHERE name='340_SubjugationOfLizardmen'; 
