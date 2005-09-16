: adena?  ( n -- n f )
	\ Have current player n adena or not?
	player@ adena@ over >=
;

l2j/var-load
l2j/doors


variable _world
: world		_world @ ;
: world!	_world ! ;

: find_player  ( "name" -- player )  world "Player" get(s) ; \ Find player by his name
