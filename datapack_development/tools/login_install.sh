USER=root
PASS=
DBNAME=l2jdb
DBHOST=localhost
mysqldump --add-drop-table -h $DBHOST -u $USER --password=$PASS $DBNAME > login_backup.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < login_install.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/gameservers.sql