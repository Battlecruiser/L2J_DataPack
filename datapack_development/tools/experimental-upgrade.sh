USER=root
PASS=
DBNAME=l2jdb
DBHOST=localhost
mysqldump --add-drop-table -h $DBHOST -u $USER --password=$PASS $DBNAME > l2jdb_experimental-backup.sql

mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/experimental/locations.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/experimental/npc.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/experimental/npcskills.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/experimental/spawnlist-experimental.sql
