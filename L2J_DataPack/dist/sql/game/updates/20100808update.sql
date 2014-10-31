INSERT INTO item_elementals (itemId, elemType, elemValue) SELECT itemId, elemType, elemValue FROM item_attributes;
ALTER TABLE item_attributes DROP COLUMN elemType;
ALTER TABLE item_attributes DROP COLUMN elemValue;