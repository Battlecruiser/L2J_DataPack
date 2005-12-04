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
echo Making a backup of the original database.
%mysqldumpPath% --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% > login_backup.sql
echo.
echo Deleting tables for new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < login_install.sql
echo.
echo Installling new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/accounts.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/gameservers.sql