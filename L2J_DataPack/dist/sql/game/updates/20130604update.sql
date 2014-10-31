ALTER TABLE `character_recipeshoplist`
CHANGE COLUMN `Recipeid` `recipeId` int(11) UNSIGNED NOT NULL DEFAULT 0 AFTER `charId`,
CHANGE COLUMN `Price` `price` bigint(20) UNSIGNED NOT NULL DEFAULT 0 AFTER `recipeId`,
CHANGE COLUMN `Pos` `index` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 AFTER `price`;