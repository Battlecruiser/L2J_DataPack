		SQL File History, or, "What happened to the files?"

	
Old name as in:                         New name as in:
Revision 659                            Revision 687

/sql/locations-c3.sql               ->  /sql/experimental/locations.sql
/sql/locations.sql                  ->  /sql/locations.sql
/sql/npc-c3.sql                     ->  /sql/experimental/npc.sql
/sql/npc.sql                        ->  /sql/npc.sql
/sql/npcskills-c3.sql               ->  /sql/experimental/npcskills.sql
/sql/npcskills.sql                  ->  /sql/npcskills.sql

/sql/spawnlist-experimental-c3.sql  ->  /sql/experimental/spawnlist-experimental.sql
/sql/spawnlist-sexy.sql             ->  /sql/experimental/spawnlist-loc_id.sql
/sql/spawnlist.sql                  ->  /sql/spawnlist.sql


Link to Revision 659 /sql/ folder for reference:
https://opensvn.csie.org/traccgi/L2J_Datapack/trac.cgi/browser/trunk/datapack_development/sql/?rev=659

Link to Revision 687 /sql/ folder for reference:
https://opensvn.csie.org/traccgi/L2J_Datapack/trac.cgi/browser/trunk/datapack_development/sql/?rev=687



	Notes:
	(by no means extensive final or accurate but better than nothing, other devs feel free to add comments - warrax)

		LOCATIONS
/sql/locations.sql    -> fewer entries
/sql/experimental/locations.sql    -> more entries, should work better with /sql/experimental/spawnlist-experimental.sql

		NPC
/sql/npc.sql    -> better stats
/sql/experimental/npc.sql    -> better `type` field entries

		NPCSKILLS
/sql/npcskills.sql    -> fewer entries
/sql/experimental/npcskills.sql    -> more entries

		SPAWNLIST
/sql/spawnlist.sql    -> old spawnlist (no usage of loc_id?)
/sql/experimental/spawnlist-experimental.sql    -> newer spawnlist, more raidbosses, more usage of loc_id (locations.sql)
/sql/experimental/spawnlist-loc_id.sql   -> initial implementation of loc_id, old, not maintained


	*i would reccomend /sql/experimental/locations.sql, /sql/experimental/npcskills.sql, /sql/experimental/spawnlist-experimental.sql
		and /sql/npc.sql with the `type` field entries from /sql/experimental/npc.sql (warrax)