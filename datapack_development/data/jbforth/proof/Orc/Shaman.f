1631 CONSTANT Mask_of_medium
50 CONSTANT oShaman_ClassId

: to_oShaman
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Mask_of_medium player@ inventory? 0 > if
		Mask_of_medium 1 player@ inventory-! drop
		oShaman_ClassId player@ class!
		2025 st
		exit
	then
;
