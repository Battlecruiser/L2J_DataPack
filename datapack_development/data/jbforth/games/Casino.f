: DICE1 ( -- )
	\ Бросок случайного типа кости (item с кодом от 4625 до 4628)
    PLAYER@   4625   4 CHOOSE  +   DICE
;

: CASINO  ( n -- )
	\ Играем
    DUP player@ ADENA@ > 
    IF
        "Not enough adena" SHOW
        DROP
        EXIT
    THEN
     
    DICE1
    DICE1
    OVER = 
     
    ( stake result flag )
    IF
        6 = IF
            5 *
            "You win " . DUP . "adena!!!" .
        ELSE
            2 *
            "You win " . DUP . "adena!" .
        THEN
     ELSE
        DROP
        "You loose " . DUP . "adena." .
        NEGATE
     THEN

     player@ ADENA+!
;
