1592 CONSTANT Mark_of_raider
45 CONSTANT oRaider_ClassId

: to_oRaider
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Mark_of_raider player@ inventory? 0 > if
		Mark_of_raider 1 player@ inventory-! drop
		oRaider_ClassId player@ class!
		exit
	then
;
