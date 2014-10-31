ALTER TABLE `bandit_stronghold_attackers` ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `bandit_stronghold_attackers_members` ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `clanhall_siege_attackers` ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `rainbowsprings_attacker_list` ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `siegable_clanhall` ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `character_quests`
WHERE `name` IN (
'30026_bitz_occupation_change',
'30031_biotin_occupation_change',
'30154_asterios_occupation_change',
'30358_thifiell_occupation_change',
'30520_reed_occupation_change',
'30525_bronk_occupation_change',
'30565_kakai_occupation_change');