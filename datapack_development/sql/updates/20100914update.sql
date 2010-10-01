-- Delete incorrect Dwarven Craft skill
DELETE FROM character_skills WHERE skill_id = 1321 AND class_index = 0 AND charId NOT IN ( SELECT charId FROM characters WHERE base_class IN (53, 54, 55, 56, 57, 117, 118));
DELETE FROM character_skills WHERE skill_id = 1321 AND class_index = 1 AND charId NOT IN ( SELECT charId FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 1);
DELETE FROM character_skills WHERE skill_id = 1321 AND class_index = 2 AND charId NOT IN ( SELECT charId FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 2);
DELETE FROM character_skills WHERE skill_id = 1321 AND class_index = 3 AND charId NOT IN ( SELECT charId FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 3);
-- Delete incorrect Dwarven Craft shortcuts
DELETE FROM character_shortcuts WHERE shortcut_id = 1321 AND class_index = 0 AND charid NOT IN ( SELECT charid FROM characters WHERE base_class IN (53, 54, 55, 56, 57, 117, 118));
DELETE FROM character_shortcuts WHERE shortcut_id = 1321 AND class_index = 1 AND charid NOT IN ( SELECT charid FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 1);
DELETE FROM character_shortcuts WHERE shortcut_id = 1321 AND class_index = 2 AND charid NOT IN ( SELECT charid FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 2);
DELETE FROM character_shortcuts WHERE shortcut_id = 1321 AND class_index = 3 AND charid NOT IN ( SELECT charid FROM character_subclasses WHERE class_id IN (55, 117) AND class_index = 3);