\ Ride on wyvern or strider

true value players-can-ride-hair

: ride-over ( -- )
	\ Unride with notification
	player@ unride
	"Ride time is over.<br><br>Welcome back again!" show
;

: do-limited-ride  ( pet time -- flag )
	swap player@ ride 0= if rdrop exit then
	1000 *  dup
	3  player@ gauge
	"ride-over"  swap  timer-start
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

	rot rot do-limited-ride

	( adena )

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
