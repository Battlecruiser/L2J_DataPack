\ Этот файл вызывает�?�? каждый раз, когда игрок входит в игру

\ Загружаем переменные пользовател�?

\ statement = con.prepareStatement("SELECT name,value FROM character_quests WHERE char_id=? AND var=?");

\ "SELECT `name`, `value` FROM `character_quests` WHERE `char_id`=" 
\ 	player@ "ObjectId" p@ +
\	" AND `var`='jbforth_user_var';" +

\ query

"JBForth is enabled." .

"Itemsound.quest_before_battle"  player@  PLAY-SOUND
