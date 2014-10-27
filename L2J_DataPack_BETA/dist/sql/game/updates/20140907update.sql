ALTER TABLE `castle_manor_procure`
	CHANGE COLUMN `castle_id` `castle_id` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' FIRST,
	CHANGE COLUMN `crop_id` `crop_id` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `castle_id`,
	CHANGE COLUMN `can_buy` `amount` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `crop_id`,
	CHANGE COLUMN `start_buy` `start_amount` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `amount`,
	CHANGE COLUMN `price` `price` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `start_amount`,
	CHANGE COLUMN `reward_type` `reward_type` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' AFTER `price`,
	CHANGE COLUMN `period` `next_period` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' AFTER `reward_type`;

ALTER TABLE `castle_manor_production`
	CHANGE COLUMN `castle_id` `castle_id` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' FIRST,
	CHANGE COLUMN `seed_id` `seed_id` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `castle_id`,
	CHANGE COLUMN `can_produce` `amount` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `seed_id`,
	CHANGE COLUMN `start_produce` `start_amount` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `amount`,
	CHANGE COLUMN `seed_price` `price` INT(11) UNSIGNED NOT NULL DEFAULT '0' AFTER `start_amount`,
	CHANGE COLUMN `period` `next_period` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' AFTER `price`;