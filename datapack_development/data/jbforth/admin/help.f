\ Show GM commands help

\ called by "//help2"

: gm_help2
	"admin-menu" check-access
	"jbforth/admin/help.htm" show
;
