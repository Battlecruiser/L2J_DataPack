\ Jail system by Balancer, (c) 2005

\ You may change it properties
{ -78930 109530 -4895 } value jail-coords
6353 constant	~BlueGemstone

~BlueGemstone	constant jail-item

false 	suvalue jailed?
0 		suvalue jail-to-collect
0 		suvalue jail-total-collected
"0 0 0"	suvalue	jail-coords-back

: items:remove-all ( item-id -- )
	dup items# ?dup if
		swap items_remove
	else
		drop
	then
;

: jail-me ( items_count -- )
	int to jail-to-collect

	jail-to-collect jail-total-collected + to jail-total-collected
	
	true to jailed?

	{ 736 1538 1829 1830 3958 4677 5858 5859 } "items:remove-all" do-list

	loc@ coords>s to jail-coords-back

	"You are jailed! To free you must collect " jail-to-collect s+ " item(s) from killed mobs" s+ "Jail system" player@ .tell
	jail-coords list-rev> drop jump
;

: jail-stop ( -- )
	0 		to jail-to-collect
	false 	to jailed?
	
	jail-item dup items# ?dup if swap items_remove else drop then
	"You are freed!" "Jail system" player@ .tell
	jail-coords-back s>coords drop jump
;

: jail-check ( -- )
	jailed? int 0= if rdrop rdrop exit then
	
	killer "Level" p@
	self "Level" p@ /
	choose 0 > if exit then
	jail-to-collect 1- to jail-to-collect
	1 jail-item item_add
	jail-to-collect 0 > if exit then

	"Player " player@ target@ "Name" p@ s+
	" collect all jail items and freed
" s+
	"log/game/jail.log" file-append

	jail-stop
;

: on-player-escape
	jailed? int if 
		"You are jailed yet." "Jail system" player@ .tell
		jail-coords list-rev> drop
	else
		0 0 0
	then
;

: on-npc-653-die	jail-check ;
: on-npc-172-die	jail-check ;

: gm_jail
	"jail" check-access
	dup 0= if
		drop 100
	then

	"Player " player@ target@ "Name" p@ s+
	" jailed by " s+ player@ "Name" p@ s+
	" for " s+ over s+ " items
" s+
	"log/game/jail.log" file-append


	int "jail-me" player@ target@ p-do-player 
;

: gm_unjail
	"jail" check-access
	drop 

	"Player " player@ target@ "Name" p@ s+
	" unjailed by " s+ player@ "Name" p@ s+ "
" s+
	"log/game/jail.log" file-append

	"jail-stop" player@ target@ do-player 
;
