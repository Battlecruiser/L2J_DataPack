\ GM teleport commands

: gm_all-to-me \ move all player in world to calling GM

	"teleport" check-access			\ check access level
	
	player@ coords@ coords>s

	" player@ jump" s+ do-players	\ jump (teleport) to saved coordinates all players
;

: gm_bm+ \ bookmark current location by name: //bm+ cool place
	"teleport" check-access
	player@ coords@	coords>s
	"bookmark-" rot s+ uv!
;

: gm_bm \ jump to stored place: //bm cool place
	"teleport" check-access
	"bookmark-" swap s+ dup >r uv@
	dup null? if
		drop "Not found bookmark '" r> s+ "'." s+
	else
		rdrop
		s>coords if	jump else drop then
	then
;

: gm_rcl-tp	\ Recall target player to GM position: //rcl-tp
	"spawn" check-access
	player@ coords@
	player@ target@ teleport-player-to
;

: gm_to-npc  ( npc-id -- )
	"teleport" check-access
	find-by-npc_id coords@ jump
;
