1145 CONSTANT Medallion_of_Warrior
1 CONSTANT hWarrior_ClassId

: to_hWarrior
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Medallion_of_Warrior player@ inventory? 0 > if
		Medallion_of_Warrior 1 player@ inventory-! drop
		hWarrior_ClassId player@ class!
		2025 st
		exit
	then
;
