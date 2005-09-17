: DICE ( -- )
	\ Roll of random dice-type (item with code 4625 to 4628)
    PLAYER@   4625   4 CHOOSE  +   (DICE)
;

: dice-win-play-music  ( -- ) \ timer-task
	"ItemSound.quest_itemget" player@ PLAY-SOUND
;

: ROLL-DICE  ( n -- )
    DUP player@ ADENA@ > 
    IF
        "Not enough adena" SHOW
        DROP
        EXIT
    THEN
     
    DICE
    DICE
    OVER = 
     
    ( stake result flag )
    IF
        \ Play music after 2 seconds
        "dice-win-play-music" 2000 timer-start

        6 = IF
	        "dice-win-play-music" 2500 timer-start
    	    "dice-win-play-music" 3000 timer-start
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
