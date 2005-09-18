\ GM commands handlers

admin/teleports
admin/polymorph

\ unride selected player
: gm_ur   "ride" check-access   player@ target@ unride ;

\ ride strider selected player
: gm_sr   "ride" check-access   gm_ur strider player@ target@ ride ;

\ ride wyvern selected player
: gm_wr   "ride" check-access   gm_ur wyvern player@ target@ ride ;
