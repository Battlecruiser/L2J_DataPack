\ This file loaded on every player enter in game

\ Announce about enter every player in game.
player@ "Name" p@    " is online " S+    announce











\ ================================
\ Load users vars. Not working now
\ ================================

\ statement = con.prepareStatement("SELECT name,value FROM character_quests WHERE char_id=? AND var=?");

\ "SELECT `name`, `value` FROM `character_quests` WHERE `char_id`=" 
\ 	player@ "ObjectId" p@ +
\	" AND `var`='jbforth_user_var';" +

\ query

