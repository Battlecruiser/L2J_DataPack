constants/main
jbf/Main
l2j/main
admin/main
games/Casino
service/main
events/main
proof/main
\ users-commands/main

disablers

: test   "npc" player@ target@ -1 (polymorph) ;
: unp   null null "npc" player@ target@ 0 (polymorph) ;

events.f

"all files loaded ok" .
