service/teleport
service/jail

: .t if -1 else 0 then . ;

: online-notify
	player-online-notify not if exit then
	'caller "Name" p@    " is online " s+ .' do-players
;

: offline-notify
	player-offline-notify not if exit then
	'caller "Name" p@    " is offline " s+ .' do-players
;
