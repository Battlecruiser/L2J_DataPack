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
	{ 115272 -182684 -1467 }
	{ 115506 -182668 -1470 }
	{ 116499 -182312 -1533 }
	{ 117752 -182463 -1557 }
	{ 118090 -182463 -1585 }
	{ 118867 -182182 -1922 }
	{ 119189 -182070 -1971 }
	{ 119662 -182015 -1892 }
	{ 120093 -181960 -1918 }
	{ 120382 -181919 -1992 }
	{ 120641 -181770 -2003 }
	{ 121043 -181349 -1985 }
	{ 121680 -181128 -1923 }
	{ 122701 -181395 -1909 }
	{ 123969 -181755 -1909 }
	{ 124504 -181705 -1909 }
	{ 124968 -181440 -1856 }
	{ 125175 -181248 -1808 }
	{ 125655 -180505 -1800 }
	{ 126326 -179979 -1836 }
	{ 127045 -179778 -1868 }
	{ 127605 -179153 -1949 }
	{ 127911 -178736 -1999 }
	{ 128849 -177905 -2147 }
	{ 129366 -177456 -2198 }
	{ 129662 -177191 -2267 }
	{ 130249 -176836 -2480 }
	{ 131279 -177565 -2753 }
	{ 133041 -178865 -2641 }
	{ 134644 -179039 -2415 }
	{ 136757 -179328 -1878 }
	{ 137304 -179346 -1717 }
	{ 137654 -179309 -1650 }
	{ 137928 -179141 -1604 }
	{ 138259 -178898 -1579 }
	{ 139075 -178060 -1567 }
	{ 139430 -177817 -1554 }
	{ 139649 -177684 -1567 }
	{ 139869 -177620 -1568 }
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

: walk:now?  ( npc_obj -- flag ) walk:full-path@ null? not ;

: walk:next  ( npc-obj -- npc-obj x y z h )
    >r
	r@ walk:curr-path@ list-shift r@ walk:curr-path!
	rdrop
	list-rev> drop
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
	self walk:now? if
		self walk:do
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
