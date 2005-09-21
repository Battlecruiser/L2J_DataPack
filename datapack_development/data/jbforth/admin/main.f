\ GM commands handlers

admin/help
admin/teleports
admin/polymorph

\ unride selected player
: gm_ur   "ride" check-access   player@ target@ unride ;

\ ride strider selected player
: gm_sr   "ride" check-access   gm_ur strider player@ target@ ride ;

\ ride wyvern selected player
: gm_wr   "ride" check-access   gm_ur wyvern player@ target@ ride ;

: bypass_admin_menu
	tail drop
	"admin-menu" check-access
	'<button value="Play Sounds" action="bypass -h admin_play_sounds" width=90 height=15 back="sek.cbui94" fore="sek.cbui92">'
	'<button value="Paralyze" action="bypass -h forth player@ target@ paralyze" width=90 height=15 back="sek.cbui94" fore="sek.cbui92">' S+
	'<button value="Unparalyze" action="bypass -h forth player@ target@ unparalyze" width=90 height=15 back="sek.cbui94" fore="sek.cbui92">' S+
	show
;
