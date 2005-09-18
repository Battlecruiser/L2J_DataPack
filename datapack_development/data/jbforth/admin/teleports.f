\ GM teleport commands

0 value x   0 value y   0 value z

: gm_all_to_me \ move all player in world to calling GM

	"teleport" check-access			\ check access level
	
	player@ coords@ to z to y to z	\ save coordinates of caller

	"x y z player@ jump" do-players	\ jump (teleport) to saved coordinates all players
;
