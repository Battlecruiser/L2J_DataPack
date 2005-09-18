\ Work with map

: loc@  ( -- x y z ) \ read current player coords
	player@ "X" p@
	player@ "Y" p@
	player@ "Z" p@
;

: map>loc  ( map_x map_y -- loc_x loc_y )
	\ map-coordinates to server-coordinates.
	dup f> 39 < if
		12198.6   f* 539062.8  f- \ Elmor
	else
		12178.364 f* 505265.57 f- \ Aden
	then
	f>
	swap
	12191.92  f* 559420.12 f- f>
	swap
;

: loc>map  ( loc_x loc_y -- map_x map_y )
	\ server-coordinates to map-coordinates
    dup f> -30500 < if
    	539062.8  f+ 12198.6   f/
   	else
		505265.57 f+ 12178.364 f/
	then
	swap
	559420.12 f+ 12191.92  f/
	swap
;
