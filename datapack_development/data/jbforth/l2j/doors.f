variable _doors_table

: doors-table   _doors_table @ ;
: doors-table!	_doors_table ! ;

: id>door  ( door_id -- door_object )
	doors-table "Door" get(i)
;

: door-open  ( door_id -- )
	id>door "openMe" jexec
;

: door-close  ( door_id -- )
	id>door "closeMe" jexec
;
