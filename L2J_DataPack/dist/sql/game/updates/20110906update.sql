ALTER TABLE `character_offline_trade_items` ADD KEY `charId` (`charId`);
ALTER TABLE `character_offline_trade_items` ADD KEY `item` (`item`);

ALTER TABLE `character_premium_items` ADD KEY `itemId` (`itemId`);

ALTER TABLE `characters` ADD KEY `account_name` (`account_name`);
ALTER TABLE `characters` ADD KEY `char_name` (`char_name`);
ALTER TABLE `characters` ADD KEY `online` (`online`);

ALTER TABLE `clan_data` ADD KEY `auction_bid_at` (`auction_bid_at`);

ALTER TABLE `clan_subpledges` ADD KEY `leader_id` (`leader_id`);

ALTER TABLE `clanhall` ADD KEY `ownerId` (`ownerId`);

ALTER TABLE `clan_wars` ADD KEY `clan1` (`clan1`);
ALTER TABLE `clan_wars` ADD KEY `clan2` (`clan2`);

ALTER TABLE `cursed_weapons` ADD KEY `charId` (`charId`);

ALTER TABLE `fort` ADD KEY `owner` (`owner`);

ALTER TABLE `forums` DROP KEY `forum_id`;
ALTER TABLE `forums` ADD PRIMARY KEY (`forum_id`);
ALTER TABLE `forums` ADD KEY `forum_owner_id` (`forum_owner_id`);

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

ALTER TABLE `pets` ADD KEY `ownerId` (`ownerId`);

ALTER TABLE `posts` ADD KEY `post_forum_id` (`post_forum_id`);

ALTER TABLE `rainbowsprings_attacker_list` ADD KEY `clanid` (`clanid`);

ALTER TABLE `siegable_clanhall` ADD KEY `ownerId` (`ownerId`);

ALTER TABLE `siegable_hall_flagwar_attackers` ADD KEY `hall_id` (`hall_id`);
ALTER TABLE `siegable_hall_flagwar_attackers` ADD KEY `clan_id` (`clan_id`);

ALTER TABLE `siegable_hall_flagwar_attackers_members` ADD KEY `hall_id` (`hall_id`);
ALTER TABLE `siegable_hall_flagwar_attackers_members` ADD KEY `clan_id` (`clan_id`);
ALTER TABLE `siegable_hall_flagwar_attackers_members` ADD KEY `object_id` (`object_id`);