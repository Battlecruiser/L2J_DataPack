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
%mysqldumpPath% --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% > l2jdb_backup.sql
echo.
echo Deleting tables for new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < full_install.sql
echo.
echo Installling new content.
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/armor.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/boxaccess.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/boxes.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/castle.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/castle_door.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/castle_doorupgrade.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/castle_siege_guards.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/char_templates.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_friends.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_hennas.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_macroses.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_quests.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_recipebook.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_shortcuts.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_skills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_skills_save.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/character_subclasses.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/characters.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/clan_data.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/clan_wars.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/class_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/droplist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/etcitem.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/global_tasks.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/henna.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/henna_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/items.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/locations.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/lvlupgain.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/mapregion.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_areas_list.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_buylists.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_lease.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchant_shopids.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/merchants.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/minions.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/npc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/npcskills.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/pets.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/random_spawn.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/random_spawn_loc.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/seven_signs.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/seven_signs_festival.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/siege_clans.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_learn.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_spellbooks.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/skill_trees.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/spawnlist.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/teleport.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/weapon.sql
%mysqlPath% -h %DBHost% -u %user% --password=%pass% -D %DBname% < ../sql/zone.sql
pause
