USER=root
PASS=
DBNAME=l2jdb
DBHOST=localhost
mysqldump --add-drop-table -h $DBHOST -u $USER --password=$PASS $DBNAME > l2jdb_backup.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < upgrade.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/armor.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/castle_door.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/char_templates.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/class_list.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/droplist.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/etcitem.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/henna_trees.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/henna.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/locations.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/lvlupgain.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/mapregion.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/merchant_areas_list.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/merchant_buylists.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/merchant_shopids.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/merchants.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/minions.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/npc.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/npcskills.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/random_spawn.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/random_spawn_loc.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/skill_learn.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/skill_trees.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/skill_spellbooks.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/spawnlist.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/teleport.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/weapon.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/zone.sql
mysql -h $DBHOST -u $USER --password=$PASS -D $DBNAME < ../sql/gameservers.sql
echo.
echo if you got an error 1050 about 'gameservers' table, anything is ok. 
echo This is only if you dont have a gameservers table already, it will create one
echo and it will leave it allone, if you have one, we dont want delete your hex ids.
echo.
echo If you read that, anything is upgraded and ready to work. 
echo Enojoy ure new database.
echo.