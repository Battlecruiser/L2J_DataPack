1252 CONSTANT Iron_heart
35 CONSTANT deAssassin_ClassId

: to_deAssassin
  player@ level@ 20 < if
       "You have not enough level" .
       exit
  then
  Iron_heart player@ inventory? 0 > if
		Iron_heart 1 player@ inventory-! drop
		deAssassin_ClassId player@ class!
		2025 st
		exit
	then
;
