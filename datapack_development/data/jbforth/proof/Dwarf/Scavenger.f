1642 CONSTANT Ring_Of_Riven
54 CONSTANT dScavenger_ClassId

: to_dScavenger
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
    Ring_Of_Riven player@ inventory? 0 > if
	Ring_Of_Riven 1 player@ inventory-! drop
	dScavenger_ClassId player@ class!
	2025 st
	exit
    then
;
	