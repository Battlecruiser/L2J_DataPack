\ Change player password

\ called as ".password new-password new-password"

: bypass_change_user_password
	over <> if
		drop
		"Passwords not match" show
		exit
   	then

	dup is-null if
		drop
		"Password can not be empty!" show
		exit
	then	
  	
	>password_hash >slashes

	"update `accounts` set `password`='" swap s+
	"' where `login` LIKE '" s+
	player@ "AccountName" p@ >slashes s+ 
	"';" s+

	update ?dup if
		"Error while update: " swap s+ show
		exit
	then

	drop
	"Password changed successfully" show
;

: user_password
	'<font color="LEVEL">Enter you new password and confirm it:</font>'
	'<table width=250><tr><td><edit var="password1" width=80 height=15></td>' s+
	'<td><edit var="password2" width=80 height=15></td>' s+
	'<td><button value="Change" action="bypass -h jbf_change_user_password $password1 $password2" width=50 height=15 back="sek.cbui94" fore="sek.cbui92"></td></tr></table>' s+
	show
	tail drop \ drop tail of command. Antihack.
;
