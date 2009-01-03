-- DUPLICATED DEATH SPIKE FIX
update character_skills set skill_id = 1530 where skill_id = 1148 and class_index = 0 and charid in ( select charid from characters where base_class = 41 );
update character_skills set skill_id = 1530 where skill_id = 1148 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 1 ); 
update character_skills set skill_id = 1530 where skill_id = 1148 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 2 ); 
update character_skills set skill_id = 1530 where skill_id = 1148 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 3 ); 

-- UPDATE DEATH SPIKE SHORTCUTS
update character_shortcuts set shortcut_id = 1530 where shortcut_id = 1148 and class_index = 0 and charid in ( select charid from characters where base_class = 41 );
update character_shortcuts set shortcut_id = 1530 where shortcut_id = 1148 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 1 ); 
update character_shortcuts set shortcut_id = 1530 where shortcut_id = 1148 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 2 ); 
update character_shortcuts set shortcut_id = 1530 where shortcut_id = 1148 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 41 and class_index = 3 );