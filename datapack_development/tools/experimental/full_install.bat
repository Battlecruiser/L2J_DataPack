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
echo Deleting tables for new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < full_install.sql
echo.
echo Installling new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/accounts.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/armor-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/boxaccess.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/boxes.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/castle.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/castle_door-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/castle_doorupgrade.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/castle_guards.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/castle_guards_skills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/char_templates-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_friends.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_hennas.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_macroses.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_quests.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_recipebook.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_shortcuts.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/character_skills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/characters.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/clan_data.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/clan_wars.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/class_list-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/droplist-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/etcitem-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/henna-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/henna_trees-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/items.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/locations-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/lvlupgain-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/mapregion-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/merchant_areas_list-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/merchant_buylists-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/merchant_lease.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/merchant_shopids-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/merchants-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/minions-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/npc-c3-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/npcskills-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/pets.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/seven_signs.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../../sql/seven_signs_festival.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/siege_clans.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/skill_learn-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/skill_spellbooks-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/skill_trees-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/spawnlist-c3-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/teleport-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/weapon-c3.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../../sql/sql-experimental/zone-c3.sql
pause
