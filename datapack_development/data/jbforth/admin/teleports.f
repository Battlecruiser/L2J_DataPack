\ GM teleport commands

0 value x   0 value y   0 value z

: gm_all_to_me \ move all player in world to calling GM

	"teleport" check-access			\ check access level
	
	player@ coords@ to z to y to z	\ save coordinates of caller

	"x y z player@ jump" do-players	\ jump (teleport) to saved coordinates all players
;

: gm_bm+ \ bookmark current location by name: //bm+ cool place
	"teleport" check-access
	player@ coords@	coords>s
	"bookmark-" rot s+ uv-save
;

: gm_bm \ jump to stored place: //bm cool place
	"teleport" check-access
	"bookmark-" swap s+ dup >r uv-load
	dup null? if
		drop "Not found bookmark '" r> s+ "'." s+
	else
		rdrop
		s>coords if	jump else drop then
	then
;
