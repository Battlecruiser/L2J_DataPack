: gm_ptf
	"admin" check-access
	"{ "
	player@ coords@ coords>s s+
	" }" s+
	"
" s+
	"data/jbforth/points.f" file-append
;

{
{ 115285 -182680 -1467 }
{ 115575 -182670 -1474 }
{ 116325 -182367 -1527 }
{ 117752 -182441 -1557 }
{ 118042 -182420 -1574 }
{ 118945 -182143 -1947 }
{ 119496 -182033 -1903 }
{ 119982 -181973 -1901 }
{ 120453 -181903 -1996 }
{ 121514 -181130 -1926 }
{ 123790 -181784 -1909 }
{ 124641 -181653 -1907 }
{ 125168 -181224 -1807 }
{ 125963 -180182 -1803 }
{ 127185 -179693 -1870 }
{ 127859 -178802 -1988 }
{ 129152 -177623 -2175 }
{ 129601 -177259 -2254 }
{ 129943 -176993 -2371 }
{ 130447 -177017 -2568 }
{ 130984 -177434 -2723 }
{ 132783 -178715 -2670 }
{ 134187 -179026 -2498 }
{ 136718 -179297 -1888 }
{ 137526 -179311 -1668 }
{ 138136 -178963 -1583 }
{ 139071 -178065 -1567 }
{ 139305 -177833 -1558 }
{ 139888 -177486 -1569 }
} value path_to_abandoned_mine

: move-to  ( npc x y z h -- )
\	"Move to: " . .s
	coords>pos swap  ( pos npc )
\	"Get ai for " . dup . ":" .
	"AI" p@ ( pos ai )
	"move-to" ( pos ai "move-to" ) rot null
\	"Intention: " . .s exit
	intention!
;

new-hashmap value walk:full-path
new-hashmap value walk:curr-path

: walk:curr-path!  ( path npc-obj -- )
	walk:curr-path swap m!
;

: walk:full-path!  ( path npc_obj -- )
	"ObjectId" p@ "" s+ 2dup 
	walk:full-path swap m!
	swap list> >list swap walk:curr-path!
;

: walk:full-path@  ( npc-obj -- path )
	"ObjectId" p@
	walk:full-path swap "" s+ m@
;

: walk:curr-path@  ( npc-obj -- path )
	"ObjectId" p@
	walk:curr-path swap "" s+ m@
;

: walk?  ( npc_obj -- flag ) 
	dup  walk:full-path@ null? if drop false exit then
	walk:curr-path@ dup null? if drop false exit then
	list# 0= not 
;

: walk:next  ( npc-obj -- npc-obj x y z h )
    >r
	r@ walk:curr-path@ list-shift r@ walk:curr-path!
	rdrop
	list-rev> drop
\	"Next point: " . >r 2dup swap . . r>
	0
;

\		r@ dup full-path walk-by-path
: walk:do  ( npc_obj -- )
	>r
	r@ walk:curr-path@ list# 0 <= if
		null r@ walk:full-path!
		rdrop exit
   	then

	r@ walk:curr-path@ list# 0 > if
		r@ dup walk:next move-to
			rdrop
			exit
	then
	rdrop
;

: on-char-EVT_ARRIVED
	self walk? if
		self walk:do
		500 sleep
		true
	else
		null
   	then
;

: walk  ( npc-obj path -- )
	over walk:full-path!
	>r
	r@ walk:next drop r@ teleport-char-to
	r> walk:do
;	

0 value c
: gm_zz
	7527 find-by-npc_id dup to c
	path_to_abandoned_mine
	walk
;

{
	{ 122068 -221032 -3674 }
	{ 194628 -183403 0571 }
	{ -115197 222523 -2948 }
	{ 177643 -177243 -544 }
	{ 63853 29202 -3841 }
} value random-points

: random-jump  ( points player -- )
	>r
	dup list# choose list@
	list-rev> drop r> teleport-char-to
;
