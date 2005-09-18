\ Show player coordinates in map format

\ called as ".loc"

: user_loc
	loc@
	drop 		\ drop z-coordinate
	loc>map		\ server coordinates to map coordinates
	swap

	"Map coordinates is " .
    "%.1f/" .f \ print x-coord with 1/10 precision
    "%.1f" .f \ Y-coord.

	tail drop \ drop tail of command. Antihack.
;
