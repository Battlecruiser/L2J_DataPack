\ Some game effects

\ Remove polymorph from character
: unpoly  ( char -- )  0 null rot 0 (polymorph) ;

\ Polymorph player char to npc with npc_id code
: poly  ( npc_id char -- )  "npc" swap -1 (polymorph) ;

\ Polymorph selected char to NPC with the code, undertaken from the input flow: //poly 10110
: gm_poly  ( -- / type ) tail player@ target@ poly ;

\ Unpolymorph char: //unpoly
: gm_unpoly  ( -- ) player@ target@ unpoly ;

effects/rename
effects/ride
effects/dice
