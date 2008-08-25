ALTER TABLE `class_list` CHANGE `class_name` `class_name` varchar(20) NOT NULL default '';
ALTER TABLE `enchant_skill_trees` CHANGE `enchant_type` `enchant_type` varchar(26) default NULL;
ALTER TABLE `pets_stats` CHANGE `expMax` `expMax` BIGINT UNSIGNED NOT NULL DEFAULT 0;