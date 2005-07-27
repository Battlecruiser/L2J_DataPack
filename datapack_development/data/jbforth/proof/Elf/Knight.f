1204 CONSTANT Elven_knight_brooch
19 CONSTANT eKnight_ClassId

: to_eKnight
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Elven_knight_brooch player@ inventory? 0 > if
		Elven_knight_brooch 1 player@ inventory-! drop
		eKnight_ClassId player@ class!
		2025 st
		exit
	then
;
