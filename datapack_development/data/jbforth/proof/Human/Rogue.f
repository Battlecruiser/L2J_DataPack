1190 CONSTANT Beziques_recommendation
7 CONSTANT hRogue_ClassId

: to_hRogue
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Beziques_recommendation player@ inventory? 0 > if
		Beziques_recommendation 1 player@ inventory-! drop
		hRogue_ClassId player@ class!
		2025 st
		exit
	then
;
