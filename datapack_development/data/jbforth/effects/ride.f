\ Ride on wyvern or strider

true value players-can-ride-hair

: ride-over ( -- )
	\ Unride with notification
	player@ unride
	"Ride time is over.<br><br>Welcome back again!" show
;

: temporary-ride ( pet time price -- )
	players-can-ride-hair not if 3 ndrop "Can't ride now" . exit then

	\ Ride at wyvern or other pet 'time' seconds for 'price'
	adena? not if
		>r 2drop r>
		"Not enough adena! Need" . .
		exit
	then
	
	( pet time adena )

	rot player@ ride 0= if 2drop exit then
	over 1000 *  3  player@ gauge
	
	( time adena )

	swap  1000 *  "ride-over"  swap  timer-start

	dup player@ adena-!
	"You loose" . . "adena." .
	"Good ride!" .
;

: ride-hair-disable
	false to players-can-ride-hair
	"unride" do-players
;

: ride-hair-enable
	true to players-can-ride-hair
;
