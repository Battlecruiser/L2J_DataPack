DROP TABLE IF EXISTS `bandit_stronghold_attackers`;

CREATE TABLE `bandit_stronghold_attackers` (
`flag` INT( 10 ) UNSIGNED NOT NULL DEFAULT '0',
`npc` INT( 10 ) UNSIGNED NOT NULL DEFAULT '0',
`clan_id` INT( 10 ) UNSIGNED NOT NULL DEFAULT '0',
PRIMARY KEY ( `flag` )
) ENGINE = MYISAM ;