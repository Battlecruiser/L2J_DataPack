-- Delete Male Soldier skills:
-- Ancient Sword Mastery, Strike Back, Dark Strike, Sword Shield, Rush, Disarm, Courage, Increase Power
-- from Female SoulBreakers/SoulHounds and Inspectors/Judicators (subclass only)
DELETE FROM character_skills WHERE skill_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 0 AND charId IN ( SELECT charId FROM characters WHERE base_class IN (129, 133));
DELETE FROM character_skills WHERE skill_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 1 AND charId IN ( SELECT charId FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 1);
DELETE FROM character_skills WHERE skill_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 2 AND charId IN ( SELECT charId FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 2);
DELETE FROM character_skills WHERE skill_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 3 AND charId IN ( SELECT charId FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 3);
-- Delete Male Soldier shortcuts:
-- Ancient Sword Mastery, Strike Back, Dark Strike, Sword Shield, Rush, Disarm, Courage, Increase Power
-- from Female SoulBreakers/SoulHounds and Inspectors/Judicators (subclass only)
DELETE FROM character_shortcuts WHERE shortcut_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 0 AND charid IN ( SELECT charid FROM characters WHERE base_class IN (129, 133));
DELETE FROM character_shortcuts WHERE shortcut_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 1 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 1);
DELETE FROM character_shortcuts WHERE shortcut_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 2 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 2);
DELETE FROM character_shortcuts WHERE shortcut_id IN (472, 475, 476, 483, 484, 485, 499, 1432) AND class_index = 3 AND charid IN ( SELECT charid FROM character_subclasses WHERE class_id IN (129, 133, 135, 136) AND class_index = 3);