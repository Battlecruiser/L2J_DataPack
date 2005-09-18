\ Change player password

\ called as ".password new-password new-password"

: user_password
	next-word
	next-word
	over <> if
		"Passwords not match" .
	else
		>password_hash >slashes
		"update `accounts` set `password`='" swap s+
		"' where `login` LIKE '" s+
		player@ "AccountName" p@ >slashes s+ 
		"';" s+
		update ?dup if
			"Error while update: " . .
		else
			drop
			"Password changed successfully" .
		then
	then

	tail drop \ drop tail of command. Antihack.
;
