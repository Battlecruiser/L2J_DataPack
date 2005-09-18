\ 2005-09-15..17, (c)Balancer, (c)Tais
\ Event "Theft of lady Kamilla"

\ ========= Configurable vars =========

"halleluiah" value password
"inferno" value orator_password
"Kamilla" value stolen_player


\ ========= Internal vars =========

false value theft-of-kamilla:running
false value MarlukKnight-killed
false value PytanKnight-killed
false value UtenusGuard-killed
false value BloodyKnight-killed
false value DeathLord-killed
false value spell-given

\ ========= Begin of main code ============

: run? theft-of-kamilla:running ;
: run! true to theft-of-kamilla:running ;

\ : run? not if exit then  ( -- ) \ if not running show message and exit from called word
\ 	run? not if
\		'Event "Theft of lady Kamilla" not running yet' .
\		rdrop 
\		exit
\	then
\ ;

\ null value kamilla

: stop
	ride-hair-enable
	false to theft-of-kamilla:running
;

\ used by timer
: event_ends  ( -- )  
    run? not if exit then
	"Kamilla" find_player unpoly
	"jbforth/events/theft-of-kamilla/maximilian-2.htm" show
	"Event ends! Kamilla come back!" announce
	stop
;

: bypass_event_theft-of-kamilla_pw_check:
    run? not if exit then

	~MercenaryPostingTicket player@ inventory? 1 < if
		"You have not main quest item, Mercenary Posting Ticket" show
		exit
  	then
	
	password = if
		~MercenaryPostingTicket 1 player@ inventory-!
		"Password matches!" .
		s4
		
		302 ( spoil festival ) 5000 5000 player@  "Kamilla" find_player  MAGIC-SKILL-TARGET

		1 ~MistSword item_add

		"event_ends" 5000 timer-start
   	else
   		"Password not matches. Try again." show
   	then
;

: bypass_event_theft-of-kamilla_orator_pw_check:
    run? not if exit then
	orator_password = if
		"Password matches! You can enter now." show
		22170001 door-open
		22170002 door-open
\		"Event: Gate opened!" announce
\ 		s4
   	else
   		"Password not matches. Try again." show
   	then
;


\ Priest Dustin, start of even:
: bypass_event_theft-of-kamilla_7116
    run? not if exit then
	"jbforth/events/theft-of-kamilla/dustin.htm" show
;

\ Priest Dustin, after offer money
: bypass_event_theft-of-kamilla_7116-2
	run? not if exit then

    1000 adena_pay \ remove money from inventory

	1 ~MercenaryPostingTicket item_add

	"jbforth/events/theft-of-kamilla/dustin-2.htm" show
;

: bypass_event_theft-of-kamilla_7120
	run? not if exit then
	spell-given not if exit then
	~MercenaryPostingTicket player@ inventory? 1 < if
		"You have not main quest item, Mercenary Posting Ticket" show
		exit
  	then

	"jbforth/events/theft-of-kamilla/maximilian.htm" show
;

: bypass_event_theft-of-kamilla_7025
	run? not if exit then
	"jbforth/events/theft-of-kamilla/trash.htm" show
;

: bypass_event_theft-of-kamilla_7025-2
    run? not if exit then

    300000 adena_pay

	"jbforth/events/theft-of-kamilla/trash-2.htm" show
;

: bypass_event_theft-of-kamilla_8201
    run? not if exit then

	"jbforth/events/theft-of-kamilla/orator.htm" show
;

: npc-7116-dialog-append 
    run? not if exit then
	'<br><a action="bypass -h jbf_event_theft-of-kamilla_7116">Event "Theft of lady Kamilla"</a><br>' .
;

: npc-7120-dialog-append 
    run? not if exit then
    spell-given not if exit then
	'<br><a action="bypass -h jbf_event_theft-of-kamilla_7120">Event "Theft of lady Kamilla"</a><br>' .
;

: npc-7025-dialog-append 
    run? not if exit then
	'<br><a action="bypass -h jbf_event_theft-of-kamilla_7025">Event "Theft of lady Kamilla"</a><br>' .
;

: npc-8201-dialog-append 
    run? not if exit then
	'<br><a action="bypass -h jbf_event_theft-of-kamilla_8201">Event "Theft of lady Kamilla"</a><br>' .
;

: npc-123-dialog-append 
    run? not if exit then
	'<br><a action="bypass -h jbf_event_theft-of-kamilla_123">Event "Theft of lady Kamilla"</a><br>' .
;
: bypass_event_theft-of-kamilla_123
    run? not if exit then
    
    DeathLord-killed not if "You must kill Death Lord" show exit then

	22170003 door-open
	22170004 door-open
	"jbforth/events/theft-of-kamilla/dixy.htm" show
	true to spell-given
;

: on-npc-862-die   run? not if exit then   DeathLord-killed not if  true to DeathLord-killed then ;

: start
	ride-hair-disable
	10110 "Kamilla" find_player  poly
	"Event begins!" announce
	"Event begins!" announce
	"Event begins!" announce
	s4
	true to theft-of-kamilla:running
	false to MarlukKnight-killed
	false to PytanKnight-killed
	false to UtenusGuard-killed
	false to BloodyKnight-killed
	false to DeathLord-killed
	false to spell-given
;




\ === teleporters ===
: npc-7588-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_7588">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_7588 79766 -15347 -276 jump ;

: npc-12046-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_12046">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_12046 83616 -14684 -1272 jump ;

: npc-12048-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_12048">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_12048  MarlukKnight-killed if 83407 -18122 -1272 jump else "You must kill Marluk Knight" show then ;
: on-npc-625-die  ( Marluk Knight )  run? not if exit then   MarlukKnight-killed not if true to MarlukKnight-killed  1 ~ElvenSword*ElvenSword item_add then ;

: npc-12047-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_12047">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_12047  PytanKnight-killed if 79261 -16613 -840 jump else "You must kill Pytan Knight" show then ;
: on-npc-762-die  ( Pytan Knight )  run? not if exit then   PytanKnight-killed not if true to PytanKnight-killed  4 ~EnchantScrollWeaponB item_add then ;

: npc-12050-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_12050">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_12050  UtenusGuard-killed if 80148 -16485 -505 jump else "You must kill Utenus Guard" show then ;
: on-npc-897-die   run? not if exit then   UtenusGuard-killed not if  true to UtenusGuard-killed  1 ~Flamberge item_add  then ;

: npc-12049-dialog-append  run? not if exit then		'<br><a action="bypass -h jbf_event_theft-of-kamilla_12049">Event "Theft of lady Kamilla". Next point...</a><br>' . ;
: bypass_event_theft-of-kamilla_12049  BloodyKnight-killed if 80545 -15189 -1831 jump else "You must kill Bloody Knight" show then ;
: on-npc-1087-die   run? not if exit then   BloodyKnight-killed not if  true to BloodyKnight-killed  1 ~HeavyDoomAxe item_add  then ;


: bypass_event_theft-of-kamilla_8201-giran   run? not if exit then   Town_of_Giran jump ;

: t1 bypass_event_theft-of-kamilla_123 ;
: t2 bypass_event_theft-of-kamilla_7588 ;
: t3 bypass_event_theft-of-kamilla_7116-2 ;
