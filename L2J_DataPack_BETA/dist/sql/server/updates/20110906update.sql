ALTER TABLE `characters`
ADD KEY `account_name` (`account_name`),
ADD KEY `char_name` (`char_name`),
ADD KEY `clanid` (`clanid`),
ADD KEY `online` (`online`);

ALTER TABLE `clan_subpledges`
ADD KEY `leader_id` (`leader_id`);

ALTER TABLE `clanhall`
ADD KEY `ownerId` (`ownerId`);

ALTER TABLE `clan_wars`
ADD KEY `clan1` (`clan1`),
ADD KEY `clan2` (`clan2`);

ALTER TABLE `items`
DROP KEY `key_owner_id`,
DROP KEY `key_loc`,
DROP KEY `key_item_id`,
DROP KEY `key_time_of_use`,
ADD KEY `owner_id` (`owner_id`),
ADD KEY `item_id` (`item_id`),
ADD KEY `loc` (`loc`),
ADD KEY `time_of_use` (`time_of_use`);