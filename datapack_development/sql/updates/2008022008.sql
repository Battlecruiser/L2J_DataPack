create table tmp_friends(char_id int, friend_id int);

insert into tmp_friends
(char_id, friend_id)
select CF1.char_id, CF1.friend_id from character_friends CF1 where CF1.char_id not in (select CF2.friend_id from character_friends CF2 where CF2.char_id = CF1.friend_id);

delete from character_friends using character_friends 
inner join tmp_friends TF
On character_friends.char_id = TF.char_id
and character_friends.friend_id = TF.friend_id;

drop table tmp_friends;