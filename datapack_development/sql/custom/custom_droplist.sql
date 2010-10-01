-- Information about category:
-- Only one drop will be given per category, except in the cases when category is -1.
-- Category -1 is used for SPOIL/SWEEP drops only!  Do NOT change this.
-- In general, category 0 is for adena and 1 seal stone color (each seal stone color should be in a different category).
-- In general, category 1 is for full drops and parts of equipable items.  However, you can change this.
-- In general, category 2 is for all other items.  However, you can change this.
-- In general, more categories are only used for RBs who have 1 item per category (i.e. do not really drop categorized),
-- with the sole exception of category 200 that is used for Life Stones. However, you can change this too.
-- You can create more categories as you see fit.  Just make sure the "category" number is non-negative!!
-- Also, it is NOT a problem if category numbers are skipped (so you can have -1, 1, 5, 10 as your categories).
--
-- If you wish to allow more than one item to be given from the same category, you can
-- split them up over several categories.
-- In addition, RBs and Grandbosses (mainly) may have the exact same item repeated in multiple categories.
-- This allows mobs to give 1 copy of the drop to each of several people (if they are lucky enough to get the drops).
-- Calculation for each drop, when in categories, is equivallent in chance as when outside of categories.
-- First, the sum of chances for each category is calculated as category chance.  If the category is selected
-- for drops (i.e. its chance is successful), then exactly 1 item from that category will be selected, with 
-- such a chance that the overall probability is maintained unchanged. 
--
-- Category Explanation (non-RaidBoss & non-GrandBoss)
--   -1 is sweep
--    0 is adena and one of the seal stones (your choice)
--    1 is any item that is in weapon.sql, any item in armor.sql, and some of the items from etcitem. Before dropping the column, I had those marked in etcitem.sql. However, you can recognize them easily. If they are parts unique for crafting equipable items, it's category 1.
--    2 is almost all of the other items except:
--    3 and 4 are used for the other two seal stones (1 category each).
--
-- Category Explanation (RaidBoss & their minions)
--    0 is full armor and armor mats
--    1 is full weapon and weapon mats
--    2 is anything else (scrolls,arrows,etc)
--
-- Category Explanation (GrandBoss)
--    0+ is defined catagories
--    Any other mobs within this range (minions, etc) use the default drop categories (most often 2)

CREATE TABLE IF NOT EXISTS `custom_droplist` (
  `mobId` smallint(5) unsigned NOT NULL DEFAULT '0',
  `itemId` smallint(5) unsigned NOT NULL DEFAULT '0',
  `min` int(8) unsigned NOT NULL DEFAULT '0',
  `max` int(8) unsigned NOT NULL DEFAULT '0',
  `category` smallint(3) NOT NULL DEFAULT '0',
  `chance` mediumint(7) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`mobId`,`itemId`,`category`),
  KEY `key_mobId` (`mobId`)
);