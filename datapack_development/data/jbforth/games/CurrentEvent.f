: sk 3000 3000 player@ MAGIC-SKILL-USE ;
: st 3000 3000 player@ dup target@ MAGIC-SKILL-TARGET ;
: so player@ PLAY-SOUND ;
: ga 3000 swap player@ gauge ;

: s0 "'ItemSound2.race_start' so" do-players ; \ выстрел + колокол
: s1 "'ItemSound.quest_accept' so" do-players ; \ дзинь
: s2 "'Itemsound.quest_before_battle' so" do-players ; \ короткий, фанфарный
: s3 "'ItemSound.quest_fanfare_1' so" do-players ; \ долгий торжественный, с хором
: s4 "'ItemSound.quest_fanfare_2' so" do-players ; \ покороче, торжественный
: s5 "'ItemSound.quest_finish' so" do-players ; \ финиш
: s6 "'ItemSound.quest_getitem' so" do-players ; \ 
: s7 "'ItemSound.quest_giveup' so" do-players ;
: s8 "'ItemSound.quest_itemget' so" do-players ;
: s9 "'ItemSound.quest_jackpot' so" do-players ;
: sa "'ItemSound.quest_midddle' so" do-players ;
: sb "'ItemSound.quest_tutorial' so" do-players ;

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
