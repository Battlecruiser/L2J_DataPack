-- Fix for pet summon items with name
UPDATE `items` SET `custom_type2` = 1 WHERE `object_id` IN (SELECT `item_obj_id` FROM `pets` WHERE `name` IS NOT NULL);
