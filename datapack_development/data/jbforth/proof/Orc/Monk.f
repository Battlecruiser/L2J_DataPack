1615 CONSTANT Khavatari_totem
47 CONSTANT oMonk_ClassId

: to_oMonk
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Khavatari_totem player@ inventory? 0 > if
		Khavatari_totem 1 player@ inventory-! drop
		oMonk_ClassId player@ class!
		2025 st
		exit
	then
;
