ALTER TABLE `character_friends` DROP PRIMARY KEY;
ALTER TABLE `character_friends` ADD PRIMARY KEY (`charId`, `friendId`);
ALTER TABLE `character_friends` DROP column `friend_name`;