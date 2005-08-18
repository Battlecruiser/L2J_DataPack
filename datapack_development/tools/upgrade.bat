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

REM ############################################

set mysqldumpPath="%mysqlBinPath%\mysqldump"
set mysqlPath="%mysqlBinPath%\mysql"

echo.
echo Making a backup of the original database.
%mysqldumpPath% --add-drop-table -u %user% --password=%pass% %DBname% > l2jdb_backup.sql
echo.
echo Deleting table for new content.
%mysqlPath% -u %user% --password=%pass% -D %DBname% < upgrade.sql
echo.
echo Installing new content.
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/armor.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/char_templates.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/class_list.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/droplist.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/etcitem.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/henna_trees.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/henna.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/locations.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/lvlupgain.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/mapregion.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_areas_list.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_buylists.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_shopids.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/merchants.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/minions.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/npc.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/npcskills.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/skill_learn.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/skill_trees.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/skill_spellbooks.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/spawnlist.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/teleport.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/weapon.sql
%mysqlPath% -u %user% --password=%pass% -D %DBname% < ../sql/seven_signs.sql
pause