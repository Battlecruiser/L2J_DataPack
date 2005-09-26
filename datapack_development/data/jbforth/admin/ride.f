\ Ride/unride

: gm_ride  ( -- )  
\ Show ride menu
	"ride" check-access
	"jbforth/admin/ride.htm" show
;

: gm_ride_wyvern    ( -- ) ( Ride gm on wyvern )	"ride" check-access	wyvern player@ ride ;
: gm_ride_strider   ( -- ) ( Ride gm on strider)	"ride" check-access	strider player@ ride ;
: gm_unride         ( -- ) ( unride GM ) "ride" check-access   player@ unride ;
: gm_unride_wyvern  ( -- ) ( unride GM ) gm_unride ;
: gm_unride_strider ( -- ) ( unride GM ) gm_unride ;

\ unride selected player
: gm_ur   "ride" check-access   player@ target@ unride ;

\ ride strider selected player
: gm_sr   "ride" check-access   gm_ur strider player@ target@ ride ;

\ ride wyvern selected player
: gm_wr   "ride" check-access   gm_ur wyvern player@ target@ ride ;
