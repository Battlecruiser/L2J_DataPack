1161 CONSTANT Sword_of_Ritual
4 CONSTANT hKnight_ClassId

: to_hKnight
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Sword_of_Ritual player@ inventory? 0 > if
		Sword_of_Ritual 1 player@ inventory-! drop
		hKnight_ClassId player@ class!
		2025 st
		exit
	then
;
