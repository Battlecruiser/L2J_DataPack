@echo off

REM ############################################
REM ## You can change here your own DB params ##
REM ############################################


REM MYSQL 4.1
set mysqlBinPath=F:\Program Files\MySQL\MySQL Server 4.1\bin
set user=root
set pass=root
set DBname=l2jdb
set DBHost=localhost

REM ############################################

set mysqldumpPath="%mysqlBinPath%\mysqldump"
set mysqlPath="%mysqlBinPath%\mysql"

echo.
echo Making a backup of the original database.
%mysqldumpPath% --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% > l2jdb_backup.sql
echo.
echo Deleting table for new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < upgrade.sql
echo.
echo Installing new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/armor.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/castle_door.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/char_templates.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/class_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/droplist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/etcitem.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/global_tasks.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/henna_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/henna.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/locations.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/lvlupgain.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/mapregion.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_areas_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_buylists.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_shopids.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchants.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/minions.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/npc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/npcskills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/random_spawn.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/random_spawn_loc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_learn.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_spellbooks.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/spawnlist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/teleport.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/weapon.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/zone.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/gameservers.sql
echo.
echo If you got an error 1050 about 'gameservers' table, anything is ok. 
echo This is only if you don't have a gameservers table already, it will create one
echo and leave it alone, if you had one already, we don't delete your hex ids.
echo.
echo If you can read this, anything is upgraded and ready to work. 
echo Enjoy your new database.
echo.
pause
