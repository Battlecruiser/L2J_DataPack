1217 CONSTANT Reoria_recommendation
22 CONSTANT eScout_ClassId

: bypass_to_eScout
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Reoria_recommendation player@ inventory? 0 > if
		Reoria_recommendation 1 player@ inventory-! drop
		eScout_ClassId player@ class!
		2025 st
		exit
	then
;
