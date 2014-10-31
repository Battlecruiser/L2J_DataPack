DELETE FROM `character_quests` WHERE `name`='376_GiantsExploration1' AND `var`='progress';
DELETE FROM `character_quests` WHERE `name`='376_GiantsExploration1' AND `var`='awaitBook';
UPDATE `character_quests` SET `value`='1' WHERE `name`='376_GiantsExploration1' AND `var`='cond' AND `value`>'1';