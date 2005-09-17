: adena?  ( n -- n f )
	\ Have current player n adena or not?
	player@ adena@ over >=
;

: item_add  ( count item_id -- ) \ add count items to current player
	swap player@ inventory+!
;

: adena_pay  ( adena -- ) \ remove adena from player if exists. Exit from called word if not have.
	adena? not if
		drop
    	"You have not enough money to pay" show 
    	rdrop \ drop one call level, return to up level function
    	exit
	then

	%adena swap player@ inventory-!
;


l2j/var-load
l2j/doors


variable _world
: world		_world @ ;
: world!	_world ! ;

: find_player  ( "name" -- player )  world "Player" get(s) ; \ Find player by his name
