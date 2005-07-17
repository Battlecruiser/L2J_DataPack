1635 CONSTANT Final_Pass_Sertificate
56 CONSTANT dArtisan_ClassId

: to_dArtisan
    player@ level@ 20 < if
        "You have not enough level" .
        exit
    then
  Final_Pass_Sertificate player@ inventory? 0 > if
		Final_Pass_Sertificate 1 player@ inventory-! drop
		dArtisan_ClassId player@ class!
		exit
	then
;
	