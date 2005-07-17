1230 CONSTANT Eternity_diamond
15 CONSTANT eWizard_ClassId

: to_eWizard
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Eternity_diamond player@ inventory? 0 > if
		Eternity_diamond 1 player@ inventory-! drop
		eWizard_ClassId player@ class!
		exit
	then
;
