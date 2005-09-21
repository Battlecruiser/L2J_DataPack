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
l2j/map
l2j/teleports
l2j/doors

0 value world
0 value server_config
0 value event-coordinates
false value event?

0 uvalue back-coordinates

0 uvalue killer
0 uvalue self
0 uvalue player
0 uvalue char
0 uvalue object
0 uvalue target
0 uvalue skill
0 uvalue level
0 uvalue caller


: find_player  ( "name" -- player )  world "Player" get(s) ; \ Find player by his name

: paralyze ( target -- )
	-1 over "Paralyzed" set(b)
	"startRooted" jexec
;

: unparalyze ( target -- )
	0 swap "Paralyzed" set(b)
;
