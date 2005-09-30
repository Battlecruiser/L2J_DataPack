\ Some game effects

\ Remove polymorph from character
: unpoly  ( char -- )  0 null rot 0 (polymorph) ;

\ Polymorph player char to npc with npc_id code
: poly  ( npc_id char -- )  "npc" swap -1 (polymorph) ;

effects/rename
effects/ride
effects/dice

: (shout)  ( args -- )
	list> drop
	
	"shout" swap
	0
	self
	( "text" "type" "sender" sender reciever -- ) 
	say2
;

: shout  ( "text" "from" -- )
	2 >list "(shout)" p-do-players
;

: tell ( "text" "sender" reciever -- )
	>r
	
	"tell" swap 0 r> say2
;
