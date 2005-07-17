1235 CONSTANT Leaf_of_oracle
15 CONSTANT eOracle_ClassId

: to_eOracle
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Leaf_of_oracle player@ inventory? 0 > if
		Leaf_of_oracle 1 player@ inventory-! drop
		eOracle_ClassId player@ class!
		exit
	then
;
