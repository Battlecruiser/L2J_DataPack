\ 2005-09-25, (c)Balancer, (c)Tais
\ Event "Gillian’s Way"

true value event:run

\ ========= Begin of main code ============

: stop
	ride-hair-enable
	false to event:run
;

: ustep!   0 swap + "event:Gillian’s Way:steps" uv-save ;

: ustep@   "event:Gillian’s Way:steps" uv-load
	dup null? if 
		drop 
		0
		"7525-1st" uv-load 1 = if   drop 11   then
		dup ustep!
	then
	event:run not if drop 0 then
	0 swap +
;

: ustep?   ustep@ > if rdrop exit then ;

: 4296?   4296 items? 1 < if rdrop exit then ;

: npc-7518-dialog-append
	4296?
	'<br><a action="bypass -h jbf_event_050925_GW_7518">Event "Gillian’s Way"</a><br>' .
;

: bypass_event_050925_GW_7518
	4296?

	ustep@ 10 >= if
		"jbforth/events/050925-Gillians_Way/7518-2.htm" show exit
	then

	5862 player@ inventory? 50 >= if
	 	5862 50 player@ inventory-!
		10 ustep!
		"jbforth/events/050925-Gillians_Way/7518-2.htm" show
		exit
	then
	
	"jbforth/events/050925-Gillians_Way/7518.htm" show
;


: npc-7525-dialog-append 
	'<br><a action="bypass -h jbf_event_050925_GW_7525">Event "Gillian’s Way"</a><br>' .
;

: bypass_event_050925_GW_7525
	4296 player@ inventory? 1 < if
		"Sorry, i don't know you" show
		exit
	then

	ustep@ 20 >= if
			"jbforth/events/050925-Gillians_Way/7525-4.htm" show exit
	then
	
	ustep@ 12 = if
		6363 player@ inventory? 50 >= if
		 	6363 50 player@ inventory-!
			20 ustep!
			"jbforth/events/050925-Gillians_Way/7525-4.htm" show
			exit
		then
	
		"jbforth/events/050925-Gillians_Way/7525-3.htm" show
		exit
	then

	ustep@ 11 = if
		812 player@ inventory? 50 >= if
		 	812 50 player@ inventory-!
			12 ustep!
			"jbforth/events/050925-Gillians_Way/7525-3.htm" show
			exit
			then
	
			"jbforth/events/050925-Gillians_Way/7525-2.htm" show
			exit
		then

	ustep@ 10 = if
		1111 player@ inventory? 50 >= if
		 	1111 50 player@ inventory-!
			11 ustep!
			"jbforth/events/050925-Gillians_Way/7525-2.htm" show
			exit
		then
	
		"jbforth/events/050925-Gillians_Way/7525.htm" show
		exit
	then

	"jbforth/events/050925-Gillians_Way/7525.htm" show
	10 ustep!
;

: on-npc-1032-die   4296?  1 1111 item_add ;
: on-npc-1033-die   4296?  1 1111 item_add ;
: on-npc-886-die   4296?  1 812 item_add ;
: on-npc-881-die   4296?  1 6363 item_add ;

: npc-7527-dialog-append 
    20 ustep? 4296?
	'<br><a action="bypass -h jbf_event_050925_GW_7527">Event "Gillian’s Way"</a><br>' .
;

7527 find-by-npc_id value s

: 7527-back
	7527 find-by-npc_id dup walk? if drop "7527-back" 120000 timer-start exit then
	>r	
	115285 -182680 -1467   r> teleport-char-to
;

: 7527-follow
	5559 items? if
		'Follow me to <font color="LEVEL">Nuakuri</font>, please!' show
		7527 find-by-npc_id 
		dup walk? if
			drop 'Follow me to <font color="LEVEL">Nuakuri</font>, please!' show
		else
			path_to_abandoned_mine  walk
			"7527-back" 480000 timer-start
		then
		rdrop exit
	then
;

: bypass_event_050925_GW_7527
    20 ustep? 4296?

	7527-follow
	
	"jbforth/events/050925-Gillians_Way/7527.htm" show
;

: bypass_event_050925_GW_7527-2
    20 ustep? 4296?
	20000 adena_pay
	1 5559 item_add
	7527-follow
;

: 5559?   5559 items? 1 < if rdrop exit then ;

: npc-7670-dialog-append 
    20 ustep? 4296? 5559?
	'<br><a action="bypass -h jbf_event_050925_GW_7670">Event "Gillian’s Way"</a><br>' .
;

: gm?
	player@ "Name" p@ "Balancer" != if
		"Sorry, talk with me later..." show rdrop exit
	then
;

: bypass_event_050925_GW_7670
    20 ustep? 4296? 5559?

	"jbforth/events/050925-Gillians_Way/7670.htm" show
;

: bypass_event_050925_GW_7670-2
    20 ustep? 4296? 5559?

	30000 adena_pay
	wyvern 1800 do-limited-ride
;

: npc-8147-dialog-append 
    20 ustep? 4296? 5559?
	'<br><a action="bypass -h jbf_event_050925_GW_8147">Event "Gillian’s Way"</a><br>' .
;

: bypass_event_050925_GW_8147
    20 ustep? 4296? 5559?

	"jbforth/events/050925-Gillians_Way/8147.htm" show
;

: bypass_event_050925_GW_8147-2
    20 ustep? 4296? 5559?
	"edmond" = if
		"All right! Go to Edmond!" show
		5559 1 player@ inventory-!
		21 ustep!
		175829 -179015 -545 jump
		exit
	then

	"You are miss. Bye-bye!" show
	
	random-points player@ random-jump
;

: npc-7497-dialog-append 
    21 ustep? 4296?
	'<br><a action="bypass -h jbf_event_050925_GW_7497-2">Event "Gillian’s Way"</a><br>' .
;

: bypass_event_050925_GW_7497-2
    21 ustep? 4296?
	"jbforth/events/050925-Gillians_Way/7497-2.htm" show
;

: bypass_event_050925_GW_7497-3
    21 ustep? 4296?
    30 ustep!
	"jbforth/events/050925-Gillians_Way/7497-3.htm" show
;

: npc-7154-dialog-append 
    30 ustep? 4296?
	'<br><a action="bypass -h jbf_event_050925_GW_7154-2">Event "Gillian’s Way"</a><br>' .
;

: bypass_event_050925_GW_7154-2
    30 ustep? 4296?
	"jbforth/events/050925-Gillians_Way/7154-2.htm" show
	1 5970 item_add
;

: 5970?   5970 items? 1 < if rdrop exit then ;

: npc-7371-dialog-append 
    30 ustep? 4296?
	'<br><a action="bypass -h jbf_event_050925_GW_7371-2">Event "Gillian’s Way"</a><br>' .
;

false value 5138-npc

: bypass_event_050925_GW_7371-2
    30 ustep? 4296? 
	5970 items? if
		"jbforth/events/050925-Gillians_Way/7371-2.htm" show exit
	then

	ustep@ 35 < if
		"I dont know you" show exit
	then

	5138-npc false <> if "Kill obserber" show exit then
	"jbforth/events/050925-Gillians_Way/7371-3.htm" show
;


: bypass_event_050925_GW_7371-3
    30 ustep? 4296? 5970?
	5970 1 player@ inventory-!
	35 ustep!
	"jbforth/events/050925-Gillians_Way/7371-3.htm" show
;

: my-stop
	"Event endeded!" .
	"Winner is " player@ "Name" p@ s+ .
;

: event-stop
	stop
	"Event endeded!" announce
	"Winner is " player@ "Name" p@ s+  announce
	s4
;

: on-npc-5138-die 
	5138-npc false unspawn
	false to 5138-npc
	35 ustep?  4296?  
	1 4311 item_add 
	event-stop
;

: bypass_event_050925_GW_7371-4
    35 ustep? 4296?
	"observer" = if
		51830 82737 -3345 0 5138 0 false spawn to 5138-npc
		"Good fight to you!" show
		exit
	then

	"You are miss. Bye-bye!" show
	
	random-points player@ random-jump
;

: start
	ride-hair-disable
	true to event:run
;

\ : on-npc-479-die   4296?  50 5862 item_add ;
