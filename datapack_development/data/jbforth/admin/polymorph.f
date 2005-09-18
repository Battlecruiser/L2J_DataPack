\ Polymorph selected char to NPC with the code, undertaken from the input flow: //poly 10110

: gm_poly  ( -- / type )  
	"polymorph" check-access  
	tail player@ target@ poly 
;

\ Unpolymorph char: //unpoly
: gm_unpoly  ( -- )
	"polymorph" check-access  
	player@ target@ unpoly 
;
