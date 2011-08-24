ALTER TABLE `characters` CHANGE `obj_Id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_quests` CHANGE `char_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_recipebook` CHANGE `char_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_hennas` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_macroses` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_shortcuts` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_subclasses` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_skills` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `character_skills_save` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;

ALTER TABLE `character_friends`
CHANGE `char_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0,
CHANGE `friend_id` `friendId` INT UNSIGNED NOT NULL DEFAULT 0;

ALTER TABLE `cursed_weapons` CHANGE `playerId` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `heroes` CHANGE `char_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `char_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE `seven_signs` CHANGE `char_obj_id` `charId` INT UNSIGNED NOT NULL DEFAULT 0;