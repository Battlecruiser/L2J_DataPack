\ Work with map

: loc@  ( -- x y z ) \ read current player coords
	player@ "X" p@
	player@ "Y" p@
	player@ "Z" p@
;
