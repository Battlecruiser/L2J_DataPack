1201 CONSTANT Mark_of_faith
15 CONSTANT hCleric_ClassId

: to_hCleric
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Mark_of_faith player@ inventory? 0 > if
		Mark_of_faith 1 player@ inventory-! drop
		hCleric_ClassId player@ class!
		2025 st
		exit
	then
;
