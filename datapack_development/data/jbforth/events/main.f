4442 CONSTANT Lottery_Ticket

: sell-ticket  ( price -- )
    Lottery_Ticket player@ inventory? 0 > if
		"You already have ticket" .
		drop
		exit
	then

    adena? not if
		drop
		"Not enough adena" .
		exit
    then
    
    player@ adena-!
    Lottery_Ticket 1 player@ inventory+!
;

: jump-for-item ( x y z item_id -- )
    dup player@ inventory? 1 < if
		"You have not needed item!" .
		2drop 2drop
		exit
	then
	
	1 player@ inventory-! drop
	jump
;

games/CurrentEvent

\ events/theft-of-kamilla
\ events/tests
