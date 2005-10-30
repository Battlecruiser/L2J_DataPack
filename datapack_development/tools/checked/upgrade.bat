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
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/armor.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/castle_door.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/char_templates.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/class_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/droplist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/etcitem.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/henna_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/henna.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/lvlupgain.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/mapregion.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/merchant_areas_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/merchant_buylists.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/merchant_shopids.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/merchants.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/minions.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/npc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/npcskills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/skill_learn.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/skill_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/skill_spellbooks.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/spawnlist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/teleport.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/weapon.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-checked/zone.sql
pause
