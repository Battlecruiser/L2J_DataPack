\ 2005-09-15, (c) Balancer
\ Event of steal lady Kamilla

"hallelujah" constant password

variable stealed_player		"Kamilla" stealed_player !

: on-npc-XXX-die
	"Test" .
	"s4" do-players
;

\ Remove polymorph from character
: unpoly  ( char -- )  0 null rot 0 (polymorph) ;
: gm_poly  ( npc_id -- ) "npc" player@ target@ -1 (polymorph) ;

variable kamilla
variable balancer

: event_do_unpoly  ( -- )
	kamilla @ unpoly
	
;

: bypass_event_theft-of-kamilla_pw_check:
	password = if
		"obj=" . .
		"Password matches!" .
		"Kamilla" find_player kamilla !

		302 5000 5000 player@ kamilla @  MAGIC-SKILL-TARGET

		"event_do_unpoly" 5000 timer-start
		"s4" do-players
   	else
   		"Password not matches. Try again." .
   	then
;

: bypass_event_theft-of-kamilla_7120
	"events/theft-of-kamilla/maximilian-password-form.htm" show
;

: npc-7120-dialog-append 
	'<br>' .
	'<a action="bypass -h jbf_event_theft-of-kamilla_7120">Event "Theft of Kamilla"</a><br>' .
;

: start
	10110  "npc"  "Kamilla" find_player 0 (polymorph)
;