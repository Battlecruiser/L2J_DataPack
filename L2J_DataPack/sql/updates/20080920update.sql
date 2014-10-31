-- DELETE INCORRECT DEATH SPIKE SKILLS
DELETE FROM character_skills WHERE skill_id = 1148 AND class_index = 0 AND charid IN ( SELECT charid FROM characters WHERE base_class IN (41, 111));
DELETE FROM character_skills WHERE skill_id = 1148 AND class_index = 1 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 1);
DELETE FROM character_skills WHERE skill_id = 1148 AND class_index = 2 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 2);
DELETE FROM character_skills WHERE skill_id = 1148 AND class_index = 3 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 3);

-- DELETE INCORRECT DEATH SPIKE SHORTCUTS
DELETE FROM character_shortcuts WHERE shortcut_id = 1148 AND class_index = 0 AND charid IN ( SELECT charid FROM characters WHERE base_class IN (41, 111));
DELETE FROM character_shortcuts WHERE shortcut_id = 1148 AND class_index = 1 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 1);
DELETE FROM character_shortcuts WHERE shortcut_id = 1148 AND class_index = 2 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 2);
DELETE FROM character_shortcuts WHERE shortcut_id = 1148 AND class_index = 3 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (41, 111) AND class_index = 3);