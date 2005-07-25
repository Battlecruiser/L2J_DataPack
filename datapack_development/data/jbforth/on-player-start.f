\ Этот файл вызывается каждый раз, когда игрок входит в игру

\ Загружаем переменные пользователя

\ statement = con.prepareStatement("SELECT name,value FROM character_quests WHERE char_id=? AND var=?");

\ "SELECT `name`, `value` FROM `character_quests` WHERE `char_id`=" 
\ 	player@ "ObjectId" p@ +
\	" AND `var`='jbforth_user_var';" +

\ query

"Hello from JBForth!" .

"Itemsound.quest_before_battle"  player@  PLAY-SOUND
