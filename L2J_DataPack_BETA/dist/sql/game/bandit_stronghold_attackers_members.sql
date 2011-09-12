DROP TABLE IF EXISTS `bandit_stronghold_attackers_members`;

CREATE TABLE `bandit_stronghold_attackers_members` (
`clan_id` INT( 10 ) UNSIGNED NOT NULL DEFAULT '0',
`object_id` INT( 10 ) UNSIGNED NOT NULL DEFAULT '0'
) ENGINE = MYISAM ;
