-- These queries will cleanup your tables from an already
-- fixed bug where a player that deleted a friend
-- stood in the ex-friend contacts list.
-- 
-- If your L2J server setup was born after Core release 1711
-- you won't need to run this script. Else you might want to
-- run it just once. (Running it again shouldn't have any
-- effect.)

CREATE TABLE tmp_friends(char_id INT, friend_id INT);

INSERT INTO tmp_friends
(char_id, friend_id)
SELECT CF1.char_id, CF1.friend_id FROM character_friends AS CF1 WHERE CF1.char_id NOT IN 
(SELECT CF2.friend_id FROM character_friends AS CF2 WHERE CF2.char_id = CF1.friend_id);

DELETE FROM character_friends using character_friends 
INNER JOIN tmp_friends AS TF
ON character_friends.char_id = TF.char_id
AND character_friends.friend_id = TF.friend_id;

DROP TABLE tmp_friends;