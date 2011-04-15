ALTER TABLE `character_friends` 
DROP PRIMARY KEY, ADD PRIMARY KEY (`charId`, `friendId`),
DROP column `friend_name`;