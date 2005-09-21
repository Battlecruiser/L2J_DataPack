\ This first tests of skills engine

( 1170	"Anchor"	skill

{ 39 44 48 52 54 55 58 60 62 64 65 67 69 } mp-consume

"Anchor" 	skill-name

target-one	skill-target
true		is-magic

active		operate-type

0.0			power
6000 		hit-time
180000  	reuse-delay
6000		skill-time

120000 		buff-duration
400 		cast-range

make-skill
)

: mp-reduce ( mp_reduce -- )
	"Reduce MP for " self S+ announce
	self "Mp" p@ swap -
	self "Mp" p!
;

: do-skill-unparalize  ( target -- )
	"Unparalized " over S+ announce
	unparalyze
;

: do-skill-paralize  ( time -- )  
	"Paralize " target S+ announce
	target "do-skill-unparalize" rot p-do-timer
	target paralyze
;

: on-skill-1170-xtarget ( -- )
	"Skill: " skill s+ announce
	"Reduces " skill "MpConsume" p@ S+ "mp" S+ announce
	"Player" player@ s+ announce
	"Test" .
	skill "MpConsume" p@	mp-reduce
	120000 do-skill-paralize
	drop
;









\        float CONModifier = activeChar.getCON()/target.getCON();
\        float DEXModifier = activeChar.getDEX()/target.getDEX();
\        float WITModifier = activeChar.getWIT()/target.getWIT();

\        float rate = 
\        rate *= *;
\        rate = 

: level@  ( char -- level )  "Level" p@ ;
: 2dup   over over ;

: modifyer  ( a b -- a-b/a+b )	2dup   f+   >r   f-   r>   f/  1. f+ "Modif: " over s+ announce ;
: norm  ( x -- 0.5 + atan x / pi )  atan pi f/ 0.5 f+  "Norm: " over s+ announce ;

: calc-modif-success ( modifier -- rate )
	self level@    target level@    modifyer	\ rate = (activeChar.getLevel()-target.getLevel())/(activeChar.getLevel()+target.getLevel());
	skill "Power" p@   sqrt   1 f+   f* 		\ rate *= 1. + Math.sqrt(skill.getPower())
	skill level@ f*					\ rate *= skill.getLevel()

	norm	\ 0.5 + Math.atan(rate)/Math.Pi
	
	f+ 2. f/ \ (x1+x2)/2 average of rates

	sqrt
	"Rate = " over s+ announce
;

0 uvalue calc-stat-modif

: calc-success  ( -- flag )
	self target skill matk
	self target skill mdef
	f/									\ matk/mdef
	self level@  f* target level@ f/	\ my_lev/targ_lev
	dup f* 								\ sqr
	
	skill "Power" p@ dup 0. f> if
		f*
		3. f*
		calc-stat-modif f/
	else
		drop 
		skill level@ 3. f+ f*
		calc-stat-modif	f/					\ *= (skill_level+2)*10/targetWit
	then

	atan pi f/ 2. f*
	
	"Chance =" . dup 100. f* "%2.1f" .f "%" .
	rnd f>
;

: calc-modif  ( modify "type" -- flag )
	target swap p@	swap f/ to calc-stat-modif
	calc-success
;	

: calc-PARALYZE-success	( -- flag )	   2. "WIT" calc-modif ;
: calc-SLEEP-success	( -- flag )  100. "WIT" calc-modif ;

: calc-PDAM-success
	skill "Id" p@ 100 = if
		1 "CON" calc-modif
		exit
	then

	null
;
