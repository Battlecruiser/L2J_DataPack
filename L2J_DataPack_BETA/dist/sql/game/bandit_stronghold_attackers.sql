CREATE TABLE `bandit_stronghold_attackers` (
`flag` int(10) UNSIGNED NOT NULL DEFAULT '0',
`npc` int(10) UNSIGNED NOT NULL DEFAULT '0',
`clan_id` int(10) UNSIGNED NOT NULL DEFAULT '0',
PRIMARY KEY (`flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;