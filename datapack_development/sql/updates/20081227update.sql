-- DUPLICATED SMART CUBIC FIX
update character_skills set skill_id = 781 where skill_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 96 );
update character_skills set skill_id = 781 where skill_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 1 ); 
update character_skills set skill_id = 781 where skill_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 2 ); 
update character_skills set skill_id = 781 where skill_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 3 ); 
 
update character_skills set skill_id = 782 where skill_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 104 );
update character_skills set skill_id = 782 where skill_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 1); 
update character_skills set skill_id = 782 where skill_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 2); 
update character_skills set skill_id = 782 where skill_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 3); 
 
update character_skills set skill_id = 780 where skill_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 106 );
update character_skills set skill_id = 780 where skill_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 1 );
update character_skills set skill_id = 780 where skill_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 2 );
update character_skills set skill_id = 780 where skill_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 3 );
 
update character_skills set skill_id = 783 where skill_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 111 );
update character_skills set skill_id = 783 where skill_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 1 );
update character_skills set skill_id = 783 where skill_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 2 );
update character_skills set skill_id = 783 where skill_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 3 );

-- UPDATE SMART CUBIC SHORTCUTS
update character_shortcuts set shortcut_id = 781 where shortcut_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 96 );
update character_shortcuts set shortcut_id = 781 where shortcut_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 1 ); 
update character_shortcuts set shortcut_id = 781 where shortcut_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 2 ); 
update character_shortcuts set shortcut_id = 781 where shortcut_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 96 and class_index = 3 ); 
 
update character_shortcuts set shortcut_id = 782 where shortcut_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 104 );
update character_shortcuts set shortcut_id = 782 where shortcut_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 1); 
update character_shortcuts set shortcut_id = 782 where shortcut_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 2); 
update character_shortcuts set shortcut_id = 782 where shortcut_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 104 and class_index = 3); 
 
update character_shortcuts set shortcut_id = 780 where shortcut_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 106 );
update character_shortcuts set shortcut_id = 780 where shortcut_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 1 );
update character_shortcuts set shortcut_id = 780 where shortcut_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 2 );
update character_shortcuts set shortcut_id = 780 where shortcut_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 106 and class_index = 3 );
 
update character_shortcuts set shortcut_id = 783 where shortcut_id = 779 and class_index = 0 and charid in ( select charid from characters where base_class = 111 );
update character_shortcuts set shortcut_id = 783 where shortcut_id = 779 and class_index = 1 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 1 );
update character_shortcuts set shortcut_id = 783 where shortcut_id = 779 and class_index = 2 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 2 );
update character_shortcuts set shortcut_id = 783 where shortcut_id = 779 and class_index = 3 and charid in ( select charid from character_subclasses where class_id = 111 and class_index = 3 );