@echo off

REM ############################################
REM ## You can change here your own DB params ##
REM ############################################


REM MYSQL 4.0
set mysqlBinPath=C:\mysql\bin
REM MYSQL 4.1
REM set mysqlBinPath=C:\Program Files\MySQL\MySQL Server 4.1\bin
set user=root
set pass=
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
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/armor-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/castle_door-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/char_templates-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/class_list-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/droplist-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/etcitem-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/henna_trees-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/henna-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/locations-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/lvlupgain-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/mapregion-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/merchant_areas_list-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/merchant_buylists-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/merchant_shopids-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/merchants-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/minions-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/npc-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/npcskills-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/skill_learn-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/skill_trees-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/skill_spellbooks-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/spawnlist-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/teleport-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/weapon-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql_experimental/zone-c3.sql
pause
