\ Some game effects

\ Remove polymorph from character
: unpoly  ( char -- )  0 null rot 0 (polymorph) ;

\ Polymorph player char to npc with npc_id code
: poly  ( npc_id char -- )  "npc" swap -1 (polymorph) ;

effects/rename
effects/ride
effects/dice
