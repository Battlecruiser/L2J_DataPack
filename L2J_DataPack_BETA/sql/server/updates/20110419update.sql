-- Remove augments from Freya Necklace
DELETE FROM `item_attributes` WHERE `itemId` IN (SELECT `object_id` FROM `items` WHERE `item_id` IN (16025,16026));