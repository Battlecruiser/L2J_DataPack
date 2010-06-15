INSERT INTO item_attributes (itemId, augAttributes, augSkillId, augSkillLevel) (SELECT * from augmentations);
DROP TABLE augmentations;