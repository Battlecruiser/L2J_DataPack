\ Телепортируем текущего игрока в координаты x y z за adena

: gatekeeper-jump ( x y z adena -- )
	10 /    ( temporary price is 1/10 of original )
	adena? not if
		"You have not adena!" .
		drop 2drop
		exit
	then
	
	player@ adena-!
	
	jump
;

