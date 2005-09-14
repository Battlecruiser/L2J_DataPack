\ This file loaded on every player enter in game

\ Load users vars. Not working now

\ statement = con.prepareStatement("SELECT name,value FROM character_quests WHERE char_id=? AND var=?");

\ "SELECT `name`, `value` FROM `character_quests` WHERE `char_id`=" 
\ 	player@ "ObjectId" p@ +
\	" AND `var`='jbforth_user_var';" +

\ query

"JBForth is enabled." .

"Itemsound.quest_before_battle"  player@  PLAY-SOUND
