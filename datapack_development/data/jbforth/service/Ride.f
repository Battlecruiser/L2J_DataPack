\ Ride on wyvern or strider

: ride-over ( -- )
	\ Ссаживаем с вайверна с предупреждением
	player@ unride
	"Ride time is over.<br><br>Welcome back again!" show
;

: temporary-ride ( pet time price -- )
	\ Катаемся на вайверне или другом пете ('pet') 'time' секунд за цену 'price'
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

\ bypass handlers

: bypass_ride  ( pet time -- )
	over wyvern <> if
		over strider <> if
			drop
			"Unknown pet " . .
			exit
		then
	then

	over >r
	dup
	dup * ( price is time^2 )
	r> wyvern = if 2 * then ( double price for wyvern )
	temporary-ride
;
