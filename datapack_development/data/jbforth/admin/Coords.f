: loc@  ( -- x y z ) \ прочитать текущие координаты
	player@ "X" p@
	player@ "Y" p@
	player@ "Z" p@
;

: jump ( x y z -- ) \ телепортация в заданные координаты
	player@ teleport-player-to
;
