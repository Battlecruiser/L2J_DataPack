1270 CONSTANT Orb_of_abyss
42 CONSTANT deShillienOracle_ClassId

: bypass_to_deShillienOracle
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Orb_of_abyss player@ inventory? 0 > if
		Orb_of_abyss 1 player@ inventory-! drop
		deShillienOracle_ClassId player@ class!
		2025 st
		exit
	then
;
