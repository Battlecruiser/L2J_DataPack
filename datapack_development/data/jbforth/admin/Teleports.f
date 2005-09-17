: TELEPORT_TO_NPC ( npc_id -- )
\ Teleport player to npc
	
	DUP NUMBER? NOT IF NPC_NAME>ID THEN

	NPC_ID>SPAWN

( 	DUP null = IF
		"Not found this NPC" .
		EXIT
   	THEN
)

	PLAYER@
	SWAP COORDS@
	SWAP 100 + SWAP
	TELEPORT_PLAYER_TO
;

0 value x   0 value y   0 value z
: gm_all_to_me \ move all player in world to calling GM
	player@ coords@ to z to y to z
	"x y z player@ jump" do-players
;
