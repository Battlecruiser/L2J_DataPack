\ Show GM commands help

\ called by "//help2"

: gm_help2
	"admin-menu" check-access
	tail drop
	"jbforth/admin/help.htm" show
;
