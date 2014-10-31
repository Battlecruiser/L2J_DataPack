ALTER TABLE `account_gsdata`
MODIFY COLUMN `var` varchar(255) NOT NULL DEFAULT '' AFTER `account_name`,
MODIFY COLUMN `value` text NOT NULL AFTER `var`;