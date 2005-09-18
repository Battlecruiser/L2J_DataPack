\ Change player password

\ called as ".password new-password new-password"

: (password-change)
	over <> if
		drop
		"Passwords not match" .
		exit
   	then

	?dup "" = if
		"Password can not be empty!"
		exit
	then	
  	
	>password_hash >slashes
	"update `accounts` set `password`='" swap s+
	"' where `login` LIKE '" s+
	player@ "AccountName" p@ >slashes s+ 
	"';" s+
	update ?dup if
		"Error while update: " . .
		exit
	then

	drop
	"Password changed successfully" .
;

: bypass_change_user_password
	next-word
	next-word
	(password-change)
	tail drop \ drop tail of command. Antihack.
;

: user_password
	"<font color="LEVEL">Enter you new password and confirm it:</font>"
	"<table width=250><tr><td><edit var="password" width=80 height=15></td>" s+
	"<td><edit var="password2" width=80 height=15></td>" s+
	"<td><button value="Check" action="bypass -h jbf_change_user_password $password $password2" width=40 height=15 back="sek.cbui94" fore="sek.cbui92"></td></tr></table>" s+
	show
;
