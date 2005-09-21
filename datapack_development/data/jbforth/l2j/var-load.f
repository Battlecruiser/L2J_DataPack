\ Word to loads user-defined vars from character_quests table

: uv-load  ( name -- value )
\	"Try load '" over s+ "'" s+ .
	"select value from character_quests where char_id = "
	player@ "ObjectId" p@ s+
	" and name='user-var' and var='" s+
	swap >slashes s+
	"';" s+
	query ?dup if . exit else drop then
	"value" m@
;

: uv-save ( value name -- )
\	over over "Try save to '" swap s+ "' value '" s+ swap s+ "'" s+ .
	"replace character_quests set name='user-var', char_id = "
	player@ "ObjectId" p@ s+
	", var='" s+
	swap >slashes s+
	"', value='" s+
	swap s+
	"';" s+
	update ?dup if . else drop then
;
