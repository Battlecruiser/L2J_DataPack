\ Bypass packets
: bypass_rename_5kk ( new_name -- )
	5000000 swap rename_for_price
;


: npc-7031-dialog-append 
	'<br>' .
	'<a action="bypass -h jbf_action_rename_7031">Rename player for adena</a><br>' .
;

: bypass_action_rename_7031
	"jbforth/actions/rename/rename.htm" show
;
