\ Word to loads user-defined vars from character_quests table

: uv@ ( var-name -- value )
\	"Load " over s+ .
	
	player@ null? if
		drop
		.rs
		null exit
	then

	"select value from character_quests where char_id = " player@ "ObjectId" p@ s+
	" and name='user-var' and var='" s+
	swap >slashes s+
	"';" s+
	
	query ?dup if . exit then
	?dup 0= if null exit then
	1- ?dup if ndrop then
	"value" m@
\	" = " over s+ .
;

: uv-del ( var-name -- )
	"delete from character_quests where char_id = "	player@ "ObjectId" p@ s+
	" and name='user-var' and var='" s+
	swap >slashes s+
	"';" s+
	update ?dup if . else drop then
;

: uv! ( value var-name -- )
\	2dup "Save " swap s+ "=" s+ swap s+ .
	over null? if  nip uv-del exit	then
		
	"replace character_quests set name='user-var', char_id = "	player@ "ObjectId" p@ s+
	", var='" s+
	swap >slashes s+
	"', value='" s+
	swap s+
	"';" s+
	update ?dup if . else drop then
;

new-list value suv-list

: suvalue  ( val -- \ name )
	value
	last-word suv-list list+
	player@ null? if exit then
	last-word uv@  dup null? if
		drop
	else
		"to " last-word s+ eval
	then
;

: suv-load  ( name -- )
	dup uv@  "to " rot s+ eval
;

: suv-load-all  ( -- )
	suv-list "suv-load" do-list
;

: suv-save ( name -- )
	dup eval swap uv!
;

: suv-save-all  ( -- )
	suv-list "suv-save" do-list
;

: suv-jbf-restart-check
	jbf_restarted? if
		"suv-load-all" do-players
	then
; suv-jbf-restart-check

7 uvalue xt
