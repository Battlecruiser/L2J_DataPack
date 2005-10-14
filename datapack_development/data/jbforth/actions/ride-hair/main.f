\ Ride on wyvern or strider

\ bypass handlers

: bypass_ride  ( "pet time" -- )
	2 args

    players-can-ride-hair not if
    	"Can't ride now" .
    	2drop
    	exit
	then

	over 12621 <> if
		over 12526 <> if
			drop
			"Unknown pet " . .
			exit
		then
	then

	dup 86400 > if
		"Too long time to ride" show
		exit
	then

	over >r
	dup
	dup * ( price is time^2 )
	r> 12621 = if 2 * then ( double price for wyvern )
	temporary-ride
;

: npc-7827-dialog-append 
    players-can-ride-hair not if exit then
	'<br><a action="bypass -h jbf_action_ride-hair_7827">Ride hire strider/wyvern</a><br>' .
;

: bypass_action_ride-hair_7827
	"jbforth/actions/ride-hair/prices.htm" show
;
