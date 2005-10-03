\ Player jump to event and back

\ called as ".to_event"

: user_to_event
	event? not if
		"Not any events now" show
		exit
	then

	can-teleport? not if "You cannot be teleported" show exit then
	
	back-coordinates if
	else
		player@ coords@ coords>s to back-coordinates
	then
	
	event-coordinates s>coords if jump then
;


: user_back
	can-teleport? not if "You cannot be teleported" show exit then

	back-coordinates if
		back-coordinates s>coords if jump then
	else
		"You already teleported back" show
	then
	
	0 to back-coordinates
;

: gm_event-is-here
	player@ coords@ coords>s to event-coordinates
;
