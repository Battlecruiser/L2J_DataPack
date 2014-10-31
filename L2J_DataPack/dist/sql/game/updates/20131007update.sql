ALTER TABLE `character_skills_save` CHANGE COLUMN `effect_cur_time` `remaining_time` INT(11) NOT NULL DEFAULT '0' AFTER `skill_level`;
ALTER TABLE `character_skills_save` DROP COLUMN `effect_count`;
ALTER TABLE `character_summon_skills_save` DROP COLUMN `effect_count`;
ALTER TABLE `character_summon_skills_save` CHANGE COLUMN `effect_cur_time` `remaining_time` INT(11) NOT NULL DEFAULT '0' AFTER `skill_level`;
ALTER TABLE `character_pet_skills_save` DROP COLUMN `effect_count`;
ALTER TABLE `character_pet_skills_save` CHANGE COLUMN `effect_cur_time` `remaining_time` INT(11) NOT NULL DEFAULT '0' AFTER `skill_level`;