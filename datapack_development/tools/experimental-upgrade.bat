@echo off

REM ############################################
REM ## You can change here your own DB params ##
REM ############################################


REM MYSQL 4.1
set mysqlBinPath=C:\Program Files\MySQL\MySQL Server 4.1\bin
set user=root
set pass=
set DBname=l2jdb
set DBHost=localhost

REM ############################################

set mysqldumpPath="%mysqlBinPath%\mysqldump"
set mysqlPath="%mysqlBinPath%\mysql"

echo.
echo Making a backup of the original tables.
%mysqldumpPath% --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% > l2jdb_experimental-backup.sql
echo.
echo Deleting table for new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < experimental-upgrade.sql
echo.
echo Installing new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/experimental/locations.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/experimental/npc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/experimental/npcskills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/experimental/spawnlist-experimental.sql


pause
