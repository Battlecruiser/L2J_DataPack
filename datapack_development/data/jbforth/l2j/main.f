true value player-online-notify
true value player-offline-notify

: adena#  ( -- count )
	\ Get adena for curent player
	player@ adena@
;

: adena?  ( n -- n f )
	\ Have current player n adena or not?
	adena# over >=
;

: items#  ( item_id -- count )
	\ Return count of items for curent player
	player@ inventory?
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

: items_remove  ( count_to_remove item_id -- ) 
	\ remove count items from inventory. Exit from calling word if not have.
	2dup items# (  count_to_remove  item_id  count_to_remove have_count  )
	> if \ not enough items
		"Not enough items. Need " .
		over .						\ print count
		.							\ print item name
		1 > if "s" . then			\ print "s" if multiple items
		rdrop exit					\ exit from calling word
	then
	swap player@ inventory-! drop
;

l2j/var-load
l2j/map
l2j/teleports
l2j/doors

null value world
null value server_config
null value event-coordinates
false value event?

false suvalue back-coordinates

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
