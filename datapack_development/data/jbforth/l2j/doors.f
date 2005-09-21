variable _doors_table

0 value doors-table

: id>door  ( door_id -- door_object )
	doors-table "Door" get(i)
;

: door-open  ( door_id -- )
	id>door "openMe" jexec
;

: door-close  ( door_id -- )
	id>door "closeMe" jexec
;
