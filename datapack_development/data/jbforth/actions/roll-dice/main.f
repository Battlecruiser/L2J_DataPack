\ Roll dice

\ bypass handlers
: bypass_casino_100   	100 roll-dice ;
: bypass_casino_1k  	1000 roll-dice ;
: bypass_casino_10k  	10000 roll-dice ;
: bypass_casino_100k  	100000 roll-dice ;
: bypass_casino_1km 	1000000 roll-dice ;


: npc-7785-dialog-append 
	'<br>' .
	'Roll dice!<br><br>' .
	'<a action="bypass -h jbf_casino_100">Play for 100a</a><br>' .
	'<a action="bypass -h jbf_casino_1k">Play for 1000a</a><br>' .
	'<a action="bypass -h jbf_casino_10k">Play for 10000a</a><br>' .
	'<a action="bypass -h jbf_casino_100k">Play for 100000a</a><br>' .
;
