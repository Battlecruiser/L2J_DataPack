: sk 3000 3000 player@ MAGIC-SKILL-USE ;
: st 3000 3000 player@ dup target@ MAGIC-SKILL-TARGET ;
: so player@ PLAY-SOUND ;
: ga 3000 swap player@ gauge ;

: s0 "ItemSound2.race_start" so ; \ выстрел + колокол
: s1 "ItemSound.quest_accept" so ; \ дзинь
: s2 "Itemsound.quest_before_battle" so ; \ короткий, фанфарный
: s3 "ItemSound.quest_fanfare_1" so ; \ долгий торжественный, с хором
: s4 "ItemSound.quest_fanfare_2" so ; \ покороче, торжественный
: s5 "ItemSound.quest_finish" so ; \ финиш
: s6 "ItemSound.quest_getitem" so ; \ 
: s7 "ItemSound.quest_giveup" so ;
: s8 "ItemSound.quest_itemget" so ;
: s9 "ItemSound.quest_jackpot" so ;
: sa "ItemSound.quest_midddle" so ;
: sb "ItemSound.quest_tutorial" so ;

: adena?  ( n -- n f )
	\ Возвращаем истину, если есть n адены и ложь - иначе
	player@ adena@ over >=
;

variable sword
variable book
variable armor
variable rec

: award?  ( to from -- )
    dup player@ inventory? 0 > if
        1 player@ inventory-! drop
	1 player@ inventory+!
	"ItemSound.quest_finish" player@ play-sound
	-1
    else
	2drop	
	0
    then
;

991 constant royen's_key

variable to_king_transferred
variable king_ring_given

: check-event  ( -- )
    royen's_key player@ inventory? 1 < if
	"You have not key!" .
	exit
    then
    
    to_king_transferred @ not if   
		-1 to_king_transferred !
		player@ 83914 36230 -1833 teleport_player_to
	then

	royen's_key 1 player@ inventory-! drop
;


: event-check:king  ( -- )
     king_ring_given @ 0= if
 		1509 1 player@ inventory+!
 		"However, run faster after the reward!" .
     then
     -1 king_ring_given !
;

: reset
	0 to_king_transferred !
	0 king_ring_given !
;
