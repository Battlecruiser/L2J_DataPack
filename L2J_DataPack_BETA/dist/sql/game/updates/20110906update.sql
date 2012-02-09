ALTER TABLE `characters` ADD KEY `account_name` (`account_name`);
ALTER TABLE `characters` ADD KEY `char_name` (`char_name`);
ALTER TABLE `characters` ADD KEY `online` (`online`);

ALTER TABLE `clan_subpledges` ADD KEY `leader_id` (`leader_id`);

ALTER TABLE `clanhall` ADD KEY `ownerId` (`ownerId`);

ALTER TABLE `clan_wars` ADD KEY `clan1` (`clan1`);
ALTER TABLE `clan_wars` ADD KEY `clan2` (`clan2`);

ALTER TABLE `items` DROP KEY `key_owner_id`;
ALTER TABLE `items` DROP KEY `key_loc`;
ALTER TABLE `items` DROP KEY `key_item_id`;
ALTER TABLE `items` DROP KEY `key_time_of_use`;
ALTER TABLE `items` ADD KEY `owner_id` (`owner_id`);
ALTER TABLE `items` ADD KEY `item_id` (`item_id`);
ALTER TABLE `items` ADD KEY `loc` (`loc`);
ALTER TABLE `items` ADD KEY `time_of_use` (`time_of_use`);

ALTER TABLE `mods_wedding` ADD KEY `player1Id` (`player1Id`);
ALTER TABLE `mods_wedding` ADD KEY `player2Id` (`player2Id`);