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
