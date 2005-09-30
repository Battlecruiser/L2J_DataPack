{ -78930 109530 -4895 } value jail-coords

false 	suvalue jailed?
0 		suvalue jail-to-collect
0 		suvalue jail-total-collected
"0 0 0"	suvalue	jail-coords-back

: jail-me ( items_count -- )
	int to jail-to-collect

	jail-to-collect jail-total-collected +  to jail-total-collected
	
	true to jailed?

	loc@ coords>s to jail-coords-back

	"You are jailed! To free you must collect " jail-to-collect s+ " item(s) from killed mobs" s+ "Jail system" player@ .tell
	jail-coords list-rev> drop jump
;

: jail-stop ( -- )
	0 		to jail-to-collect
	false 	to jailed
	
	"You are freed!" "Jail system" player@ .tell
	jail-coords-back s>coords drop jump
;

: jail-check ( -- )
	self "Level" p@ 10 *
	killer "Level" p@ /
	choose 0 > if exit then
	jail-to-collect 1- to jail-to-collect
	1 57 item_add
	jail-to-collect 0 > if exit then
	57 dup items# swap items_remove
	jail-stop
;

: on-player-escape
	jailed? -1 = if 
		"You are jailed yet." "Jail system" player@ .tell
		jail-coords list-rev> drop
	else
		0 0 0
	then
;

: on-npc-653-die
	jail-check
;

: to-jail	1 "jail-me" player@ target@ do-player ;
: from-jail	1 "jail-stop" player@ target@ do-player ;
