: gm_point>file
	
	"admin" check-access

	"{ "
	player@ coords@ coords>s s+
	" }" s+
	"
" s+
	"data/jbforth/points.f" file-append
	"Point added" .
;

: gm_npc>file

	"admin" check-access

	player@ target@ >r

	"{ "
	r@ "NpcId" p@ s+ " " s+
	r@ coords@ coords>s s+
	" 0 false } \ " s+
	r@ "Name" p@ s+
	"
" s+
	"data/jbforth/npc-spawn.f" file-append
	rdrop
	"NPC added" .
;

: lspawn  ( {npc_id x y z h respawn save-db} -- obj )
	list-rev> 7 <> if
		"Error: to low elements in spawn point" .
		exit
	then
	>r >r 
	5 peek
	r> r> spawn
	nip
;

: npc-list-spawn  ( {list-of-spawn-data-lists} -- {spawned-npc-objects-list})
	new-list swap "lspawn over list+" do-list
;

: npc-list-unspawn
	list> 0 ?do
		true unspawn
	loop
;

{ } value spawned-list

: t1  
	npc-for-spawn-test npc-list-spawn to spawned-list
;

: t2
	spawned-list npc-list-unspawn
;
