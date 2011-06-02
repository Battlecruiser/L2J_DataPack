@echo off
REM ##############################################
REM ## L2JDP Database Installer - (by DrLecter) ##
REM ##############################################
REM ## Interactive script setup -  (by TanelTM) ##
REM ##############################################
REM Copyright (C) 2010 L2J DataPack
REM This program is free software; you can redistribute it and/or modify 
REM it under the terms of the GNU General Public License as published by 
REM the Free Software Foundation; either version 3 of the License, or (at
REM your option) any later version.
REM
REM This program is distributed in the hope that it will be useful, but 
REM WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
REM or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
REM for more details.
REM
REM You should have received a copy of the GNU General Public License along 
REM with this program; if not, write to the Free Software Foundation, Inc., 
REM 675 Mass Ave, Cambridge, MA 02139, USA. Or contact the Official L2J
REM DataPack Project at http://www.l2jdp.com, http://www.l2jdp.com/forum or
REM #l2j @ irc://irc.freenode.net

set config_file=vars.txt
set config_version=0

set workdir="%cd%"
set full=0
set stage=0
set logging=0

set upgrade_mode=0
set backup=.
set logdir=.
set safe_mode=1
set cmode=c
set fresh_setup=0

:loadconfig
title L2JDP Installer - Reading Configuration from File...
cls
if not exist %config_file% goto configure
ren %config_file% vars.bat
call vars.bat
ren vars.bat %config_file%
call :colors 17
if /i %config_version% == 2 goto ls_section
set upgrade_mode=2
echo It seems to be the first time you run this version of
echo database_installer but I found a settings file already.
echo I'll hopefully ask this questions just once.
echo.
echo Configuration upgrade options:
echo.
echo (1) Import and continue: I'll read your old settings and
echo     continue execution, but since no new settings will be
echo     saved, you'll see this menu again next time.
echo.
echo (2) Import and configure: This tool has some new available
echo     options, you choose the values that fit your needs
echo     using former settings as a base.
echo.
echo (3) Ignose stored settings: I'll let you configure me 
echo     with a fresh set of default values as a base.
echo.
echo (4) View saved settings: See the contents of the config 
echo     file.
echo.
echo (5) Quit: Did you came here by mistake?
echo.
set /P upgrade_mode="Type a number, press Enter (default is '%upgrade_mode%'): "
if %upgrade_mode%==1 goto ls_section
if %upgrade_mode%==2 goto configure
if %upgrade_mode%==3 goto configure
if %upgrade_mode%==4 (cls&type %config_file%&pause&goto loadconfig)
if %upgrade_mode%==5 goto :eof
goto loadconfig

:colors
if /i "%cmode%"=="n" (
if not "%1"=="17" (	color F	) else ( color )
) else ( color %1 )
goto :eof

:configure
call :colors 17
title L2JDP Installer - Setup
cls
set config_version=2
if NOT %upgrade_mode% == 2 (
set fresh_setup=1
set mysqlBinPath=%ProgramFiles%\MySQL\MySQL Server 5.5\bin
set lsuser=root
set lspass=
set lsdb=l2jls
set lshost=localhost
set cbuser=root
set cbpass=
set cbdb=l2jcs
set cbhost=localhost
set gsuser=root
set gspass=
set gsdb=l2jgs
set gshost=localhost
set cmode=c
set backup=.
set logdir=.
)
set mysqlPath=%mysqlBinPath%\mysql.exe
echo New settings will be created for this tool to run in
echo your computer, so I need to ask you some questions.
echo.
echo 1-MySql Binaries
echo --------------------
echo In order to perform my tasks, I need the path for commands
echo such as 'mysql' and 'mysqldump'. Both executables are
echo usually stored in the same place.
echo.
if "%mysqlBinPath%" == "" (
set mysqlBinPath=use path
echo I can't determine if the binaries are available with your
echo default settings.
) else (
echo I can try to find out if the current setting actually works...
echo.
echo %mysqlPath%
)
if not "%mysqlBinPath%" == "use path" call :binaryfind
echo.
path|find "MySQL">NUL
if %errorlevel% == 0 (
echo I found MySQL is in your PATH, this will be used by default.
echo If you want to use something different, change 'use path' for 
echo something else.
set mysqlBinPath=use path
) else (
echo Look, I can't find "MYSQL" in your PATH environment variable.
echo It would be good if you go and find out where "mysql.exe" and 
echo "mysqldump.exe" are.
echo.
echo If you have no idea about the meaning of words such as MYSQL
echo or PATH, you'd better close this window, and consider googling
echo and reading about it. Setup and host an L2J server requires a
echo minimum of technical skills.
)
echo.
echo Write the path to your MySQL binaries (no trailing slash needed):
set /P mysqlBinPath="(default %mysqlBinPath%): "
cls
echo.
echo 2-LoginServer settings
echo --------------------
echo I will connect to the MySQL server you specify, and setup a
echo Loginserver database there, most people use a single MySQL
echo server and database for both Login and Gameserver tables.
echo.
set /P lsuser="MySQL Username (default is '%lsuser%'): "
set /P lspass="Password (default is '%lspass%'): "
set /P lsdb="Database (default is '%lsdb%'): "
set /P lshost="Host (default is '%lshost%'): "
echo.
cls
echo.
echo 3-Community Board Server settings
echo --------------------
echo I will connect to the MySQL server you specify, and setup a
echo Community Board server database there, most people use a single MySQL
echo server for both Login and Gameserver which CBserver can use too,
echo but CBserver requires a different database!
echo.
set /P cbuser="MySQL Username (default is '%cbuser%'): "
set /P cbpass="Password (default is '%cbpass%'): "
set /P cbdb="Database (default is '%cbdb%'): "
set /P cbhost="Host (default is '%cbhost%'): "
echo.
cls
echo.
echo 4-GameServer settings
echo --------------------
set /P gsuser="User (default is '%gsuser%'): "
set /P gspass="Pass (default is '%gspass%'): "
set /P gsdb="Database (default is '%gsdb%'): "
set /P gshost="Host (default is '%gshost%'): "
echo.
cls
echo.
echo 5-Misc. settings
echo --------------------
set /P cmode="Color mode (c)olor or (n)on-color, default %cmode% : "
set /P backup="Path for your backups (default '%backup%'): "
set /P logdir="Path for your logs (default '%logdir%'): "
:safe1
set safemode=y
set /P safemode="Debugging messages and increase verbosity a lil bit (y/n, default '%safemode%'): "
if /i %safemode%==y (set safe_mode=1&goto safe2)
if /i %safemode%==n (set safe_mode=0&goto safe2)
goto safe1
:safe2
echo.
if "%mysqlBinPath%" == "use path" (
set mysqlBinPath=
set mysqldumpPath=mysqldump
set mysqlPath=mysql
) else (
set mysqldumpPath=%mysqlBinPath%\mysqldump.exe
set mysqlPath=%mysqlBinPath%\mysql.exe
)
echo @echo off > %config_file%
echo set config_version=%config_version% >> %config_file%
echo set cmode=%cmode%>> %config_file%
echo set safe_mode=%safe_mode% >> %config_file%
echo set mysqlPath=%mysqlPath%>> %config_file%
echo set mysqlBinPath=%mysqlBinPath%>> %config_file%
echo set mysqldumpPath=%mysqldumpPath%>> %config_file%
echo set lsuser=%lsuser%>> %config_file%
echo set lspass=%lspass%>> %config_file%
echo set lsdb=%lsdb%>> %config_file%
echo set lshost=%lshost% >> %config_file%
echo set cbuser=%cbuser%>> %config_file%
echo set cbpass=%cbpass%>> %config_file%
echo set cbdb=%cbdb%>> %config_file%
echo set cbhost=%cbhost% >> %config_file%
echo set gsuser=%gsuser%>> %config_file%
echo set gspass=%gspass%>> %config_file%
echo set gsdb=%gsdb%>> %config_file%
echo set gshost=%gshost%>> %config_file%
echo set logdir=%logdir%>> %config_file%
echo set backup=%backup%>> %config_file%
echo.
echo Script setup complete, your settings were saved in the
echo '%config_file%' file. Remember: your passwords are stored
echo as clear text.
echo.
pause
goto loadconfig

:ls_section
cls
call :colors 17
set cmdline=
set stage=1
title L2JDP Installer - Login Server DataBase Setup
echo.
echo Trying to make a backup of your Login Server database.
set cmdline="%mysqldumpPath%" --add-drop-table -h %lshost% -u %lsuser% --password=%lspass% %lsdb% ^> "%backup%\ls_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto ls_db_ok
:ls_err1
call :colors 47
title L2JDP Installer - Login Server DataBase Setup ERROR!
cls
echo.
echo Backup attempt failed! A possible reason for this to 
echo happen, is that your DB doesn't exist yet. I could 
echo try to create %lsdb% for you, or maybe you prefer to
echo proceed with the CommunityServer part of this tool.
echo.
:ls_ask1
set lsdbprompt=y
echo ATTEMPT TO CREATE LOGINSERVER DATABASE:
echo.
echo (y) Yes
echo.
echo (n) No
echo.
echo (r) Reconfigure
echo.
echo (q) Quit
echo.
set /p lsdbprompt= Choose (default yes):
if /i %lsdbprompt%==y goto lsdbcreate
if /i %lsdbprompt%==n goto cb_backup
if /i %lsdbprompt%==r goto configure
if /i %lsdbprompt%==q goto end
goto ls_ask1

:omfg
cls
call :colors 57
title L2JDP Installer - Potential PICNIC detected at stage %stage%
echo.
echo There was some problem while executing:
echo.
echo "%cmdline%"
echo.
echo I'd suggest you to look for correct values and try this
echo script again later. But maybe you'd prefer to go on now.
echo.
if %stage% == 1 set label=ls_err1
if %stage% == 2 set label=ls_err2
if %stage% == 3 set label=cb_backup
if %stage% == 4 set label=cb_err1
if %stage% == 5 set label=cb_err2
if %stage% == 6 set label=gs_backup
if %stage% == 7 set label=gs_err1
if %stage% == 8 set label=gs_err2
if %stage% == 9 set label=horrible_end
if %stage% == 10 set label=horrible_end
:omfgask1
set omfgprompt=q
echo (c) Continue running the script
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt= Choose (default quit):
if  /i %omfgprompt%==c goto %label%
if  /i %omfgprompt%==r goto configure
if  /i %omfgprompt%==q goto horrible_end
goto omfgask1

:lsdbcreate
cls
call :colors 17
set cmdline=
set stage=2
title L2JDP Installer - Login Server DataBase Setup - DB Creation
echo.
echo Trying to create a Login Server database...
set cmdline="%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -e "CREATE DATABASE %lsdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto ls_install
if %safe_mode% == 1 goto omfg
:ls_err2
call :colors 47
title L2JDP Installer - Login Server DataBase Setup - DB Creation ERROR!
cls
echo An error occured while trying to create a database for 
echo your login server.
echo.
echo Possible reasons:
echo 1-You provided innacurate info , check user, password, etc.
echo 2-User %lsuser% don't have enough privileges for 
echo database creation. Check your MySQL privileges.
echo 3-Database exists already...?
echo.
echo Unless you're sure that the pending actions of this tool 
echo could work, i'd suggest you to look for correct values
echo and try this script again later.
echo.
:ls_ask2
set omfgprompt=q
echo (c) Continue running
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt= Choose (default quit):
if /i %omfgprompt%==c goto cb_backup
if /i %omfgprompt%==q goto horrible_end
if /i %omfgprompt%==r goto configure
goto ls_ask2

:ls_db_ok
call :colors 17
title L2JDP Installer - Login Server DataBase Setup - WARNING!
:ls_ask
cls
set loginprompt=u
echo.
echo LOGINSERVER DATABASE install type:
echo.
echo (f) Full: I will destroy data in your `accounts` tables.
echo.
echo (u) Upgrade: I'll do my best to preserve all login data.
echo.
echo (s) Skip: I'll take you to the communityserver database
echo     installation and upgrade options.
echo.
echo (r) Reconfigure: You'll be able to redefine MySQL path,
echo     user and database information and start over with
echo     those fresh values.
echo.
echo (q) Quit
echo.
set /p loginprompt= Choose (default upgrade):
if /i %loginprompt%==f goto ls_install
if /i %loginprompt%==u goto ls_upgrade
if /i %loginprompt%==s goto cb_backup
if /i %loginprompt%==r goto configure
if /i %loginprompt%==q goto end
goto ls_ask

:ls_install
set stage=3
call :colors 17
set cmdline=
title L2JDP Installer - Login Server DataBase Setup - Full Install
echo.
echo Deleting Login Server tables for new content.
set cmdline="%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -D %lsdb% ^< ls_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Login Server tables has been deleted.
goto ls_upgrade

:ls_upgrade
set stage=3
set cmdline=
if %full% == 1 (
title L2JDP Installer - Login Server DataBase Setup - Installing...
echo.
echo Installing new Login Server content.
echo.
) else (
title L2JDP Installer - Login Server DataBase Setup - Upgrading...
echo.
echo Upgrading Login Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=ls
for %%i in (..\sql\login\*.sql) do call :dump %%i

echo done...
echo.
goto cb_backup

:cb_backup
cls
call :colors 17
set cmdline=
set stage=4
title L2JDP Installer - Community Server DataBase Setup
echo.
echo Trying to make a backup of your Comunity Server database.
set cmdline="%mysqldumpPath%" --add-drop-table -h %cbhost% -u %cbuser% --password=%cbpass% %cbdb% ^> "%backup%\cs_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto cs_db_ok
:cb_err1
call :colors 47
title L2JDP Installer - Community Server DataBase Setup ERROR!
cls
echo.
echo Backup attempt failed! A possible reason for this to 
echo happen, is that your DB doesn't exist yet. I could 
echo try to create %cbdb% for you, or maybe you prefer to
echo proceed with the GameServer part of this tool.
echo.
:cb_ask1
set cbdbprompt=y
echo ATTEMPT TO CREATE COMMUNITY SERVER DATABASE:
echo.
echo (y) Yes
echo.
echo (n) No
echo.
echo (r) Reconfigure
echo.
echo (q) Quit
echo.
set /p cbdbprompt= Choose (default yes):
if /i %cbdbprompt%==y goto cbdbcreate
if /i %cbdbprompt%==n goto gs_backup
if /i %cbdbprompt%==r goto configure
if /i %cbdbprompt%==q goto end
goto cb_ask1

:cbdbcreate
call :colors 17
set cmdline=
set stage=5
title L2JDP Installer - Communty Server DataBase Setup - DB Creation
echo.
echo Trying to create a Community Server database...
set cmdline="%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -e "CREATE DATABASE %cbdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto cs_install
if %safe_mode% == 1 goto omfg
:cb_err2
call :colors 47
title L2JDP Installer - Community Board Server DataBase Setup - DB Creation ERROR!
cls
echo An error occured while trying to create a database for 
echo your Community Board server.
echo.
echo Possible reasons:
echo 1-You provided innacurate info , check user, password, etc.
echo 2-User %cbuser% don't have enough privileges for 
echo database creation. Check your MySQL privileges.
echo 3-Database exists already...?
echo.
echo Unless you're sure that the pending actions of this tool 
echo could work, i'd suggest you to look for correct values
echo and try this script again later.
echo.
:cb_ask2
set omfgprompt=q
echo (c) Continue running
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt= Choose (default quit):
if /i %omfgprompt%==c goto gs_backup
if /i %omfgprompt%==q goto horrible_end
if /i %omfgprompt%==r goto configure
goto cb_ask2

:cs_db_ok
call :colors 17
title L2JDP Installer - Community Server DataBase Setup - WARNING!
:cs_ask
cls
set communityprompt=u
echo.
echo COMMUNITY SERVER DATABASE install type:
echo.
echo (f) Full: WARNING! I'll destroy ALL of your existing community
echo     data (i really mean it: mail, forum, memo.. ALL)
echo.
echo (u) Upgrade: I'll do my best to preserve all of your community
echo     data.
echo.
echo (s) Skip: I'll take you to the gameserver database
echo     installation and upgrade options.
echo.
echo (r) Reconfigure: You'll be able to redefine MySQL path,
echo     user and database information and start over with
echo     those fresh values.
echo.
echo (q) Quit
echo.
set /p communityprompt= Choose (default upgrade):
if /i %communityprompt%==f goto cs_install
if /i %communityprompt%==u goto cs_upgrade
if /i %communityprompt%==s goto gs_backup
if /i %communityprompt%==r goto configure
if /i %communityprompt%==q goto end
goto cs_ask

:cs_install
set stage=6
call :colors 17
set cmdline=
title L2JDP Installer - Community Server DataBase Setup - Full Install
echo.
echo Deleting Community Server tables for new content.
set cmdline="%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -D %cbdb% ^< cs_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Community Server tables has been deleted.
goto cs_upgrade

:cs_upgrade
set stage=6
set cmdline=
if %full% == 1 (
title L2JDP Installer - Community Server DataBase Setup - Installing...
echo.
echo Installing new Community Server content.
echo.
) else (
title L2JDP Installer - Community Server DataBase Setup - Upgrading...
echo.
echo Upgrading Community Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=cb
for %%i in (..\cb_sql\*.sql) do call :dump %%i

echo done...
echo.
goto gs_backup

:gs_backup
cls
call :colors 17
set cmdline=
set stage=7
title L2JDP Installer - Game Server DataBase Setup
cls
echo.
echo Trying to make a backup of your Game Server database.
set cmdline="%mysqldumpPath%" --add-drop-table -h %gshost% -u %gsuser% --password=%gspass% %gsdb% ^> "%backup%\gs_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto gs_db_ok
:gs_err1
call :colors 47
title L2JDP Installer - Game Server DataBase Setup - Backup ERROR!
cls
echo.
echo Backup attempt failed! A possible reason for this to happen,
echo is that your DB doesn't exist yet. I could try to create 
echo %gsdb% for you, but maybe you prefer me to continue with 
echo last part of the script.
echo.
:askgsdb
set gsdbprompt=y
echo ATTEMPT TO CREATE GAME SERVER DATABASE?
echo.
echo (y) Yes
echo.
echo (n) No
echo.
echo (r) Reconfigure
echo.
echo (q) Quit
echo.
set /p gsdbprompt= Choose (default yes):
if /i %gsdbprompt%==y goto gsdbcreate
if /i %gsdbprompt%==n goto horrible_end
if /i %gsdbprompt%==r goto configure
if /i %gsdbprompt%==q goto end
goto askgsdb

:gsdbcreate
call :colors 17
set stage=8
set cmdline=
title L2JDP Installer - Game Server DataBase Setup - DB Creation
cls
echo Trying to create a Game Server database...
set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -e "CREATE DATABASE %gsdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto gs_install
if %safe_mode% == 1 goto omfg
:gs_err2
call :colors 47
title L2JDP Installer - Game Server DataBase Setup - DB Creation ERROR!
cls
echo.
echo An error occured while trying to create a database for 
echo your game server.
echo.
echo Possible reasons:
echo 1-You provided innacurate info, check username, pass, etc.
echo 2-User %gsuser% don't have enough privileges for 
echo database creation.
echo 3-Database exists already...?
echo.
echo I'd suggest you to look for correct values and try this
echo script again later. But you can try to reconfigure it now.
echo.
:askgsdbcreate
set omfgprompt=q
echo (r) Restart script with fresh configuration values
echo.
echo (q) Quit now
echo.
set /p omfgprompt=  Choose (default quit):
if /i %omfgprompt%==r goto configure
if /i %omfgprompt%==q goto horrible_end
goto askgsdbcreate

:gs_db_ok
call :colors 17
title L2JDP Installer - Game Server DataBase Setup - WARNING!
cls
:gs_ask
cls
set installtype=u
echo.
echo GAME SERVER DATABASE install:
echo.
echo (f) Full: WARNING! I'll destroy ALL of your existing character
echo     data (i really mean it: items, pets.. ALL)
echo.
echo (u) Upgrade: I'll do my best to preserve all of your character
echo     data.
echo.
echo (s) Skip: We'll get into the last set of questions (cummulative
echo     updates, custom stuff...)
echo.
echo (q) Quit
echo.
set /p installtype= Choose (default upgrade):
if /i %installtype%==f goto gs_install
if /i %installtype%==u goto gs_upgrade
if /i %installtype%==s goto custom_ask
if /i %installtype%==q goto end
goto gs_ask

:gs_install
call :colors 17
set stage=9
set cmdline=
title L2JDP Installer - Game Server DataBase Setup - Full Install
echo.
echo Deleting all Game Server tables for new content...
set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< gs_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Game Server tables has been deleted.
goto gs_upgrade

:gs_upgrade
set stage=9
set cmdline=
if %full% == 1 (
title L2JDP Installer - Game Server DataBase Setup - Installing...
echo.
echo Installing new Game Server content.
echo.
) else (
title L2JDP Installer - Game Server DataBase Setup - Upgrading...
echo.
echo Upgrading Game Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=gs
for %%i in (..\sql\server\*.sql) do call :dump %%i

echo done...
echo.
goto custom_ask

:dump
set cmdline=
if /i %full% == 1 (set action=Installing) else (set action=Upgrading)
echo %action% %1>>"%output%"
echo %action% %~nx1
if "%dest%"=="ls" set cmdline="%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -D %lsdb% ^< %1 2^>^>"%output%"
if "%dest%"=="cb" set cmdline="%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -D %cbdb% ^< %1 2^>^>"%output%"
if "%dest%"=="gs" set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %1 2^>^>"%output%"
%cmdline%
if %logging%==0 if NOT %ERRORLEVEL%==0 call :omfg2 %1
goto :eof

:omfg2
cls
call :colors 47
title L2JDP Installer - Potential DataBase Issue at stage %stage%
echo.
echo Something caused an error while executing instruction :
echo %mysqlPath% -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb%
echo.
echo with file %~nx1
echo.
echo What we should do now?
echo.
:askomfg2
set ntpebcak=c
echo (l) Log it: I will create a log for this file, then continue
echo     with the rest of the list in non-logging mode.
echo.
echo (c) Continue: Let's pretend that nothing happened and continue with
echo     the rest of the list.
echo.
echo (r) Reconfigure: Perhaps these errors were caused by a typo.
echo     you can restart from scratch and redefine paths, databases
echo     and user info again.
echo.
echo (q) Quit now
echo.
set /p ntpebcak= Choose (default continue):
if  /i %ntpebcak%==c (call :colors 17 & goto :eof)
if  /i %ntpebcak%==l (call :logginon %1 & goto :eof)
if  /i %ntpebcak%==r (call :configure & exit)
if  /i %ntpebcak%==q (call :horrible_end & exit)
goto askomfg2

:logginon
cls
call :colors 17
title L2JDP Installer - Game Server DataBase Setup - Logging Options turned on
set logging=1
if %full% == 1 (
  set output=%logdir%\install-%~nx1.log
) else (
  set output=%logdir%\upgrade-%~nx1.log
)
echo.
echo Per your request, i'll create a log file for your reading pleasure.
echo.
echo I'll call it %output%
echo.
echo If you already have such a file and would like to keep a copy.
echo go now and read it or back it up, because it's not going to be rotated
echo or anything, instead i'll just overwrite it.
echo.
pause
set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^<..\sql\%1 2^>^>"%output%"
date /t >"%output%"
time /t >>"%output%"
%cmdline%
echo Log file created, resuming normal operations...
call :colors 17
set logging=0
set output=NUL
goto :eof

:custom_ask
title L2JDP Installer - Custom Server Tables
cls
set cstprompt=n
echo.
echo L2J provides some "Custom Server Tables" for non-retail modifications
echo in order to avoid override the original Server Tables.
echo.
set /p cstprompt=Install Custom Server Tables: (y) yes or (n) no (default no):
if /i %cstprompt%==y goto custom_install
if /i %cstprompt%==n goto mod_ask
goto mod_ask
:custom_install
cls
echo.
echo Installing Custom content.
cd ..\sql\server\custom\
echo @echo off> temp.bat
if exist custom_errors.txt del custom_errors.txt
for %%i in (*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %%i 2^>^> custom_errors.txt >> temp.bat
call temp.bat> nul
del temp.bat
move custom_errors.txt %workdir%
title L2JDP Installer - Custom Server Tables Process Complete
cls
echo Database structure for L2J Custom finished.
echo.
echo Remember that in order to get these additions actually working 
echo you need to edit your configuration files. 
echo.
pause
cd %workdir%

:mod_ask
title L2JDP Installer - Mod Server Tables
cls
set cstprompt=n
echo.
echo L2J provides a basic infraestructure for some non-retail features
echo (aka L2J mods) to get enabled with a minimum of changes.
echo.
echo Some of these mods would require extra tables in order to work
echo and those tables could be created now if you wanted to.
echo.
set /p cstprompt=Install Mod Server Tables: (y) yes or (n) no (default no):
if /i %cstprompt%==y goto mod_install
if /i %cstprompt%==n goto tables_updates
goto tables_updates
:mod_install
cls
echo.
echo Installing Mods content.
cd ..\sql\server\mods\
echo @echo off> temp.bat
if exist mods_errors.txt del mods_errors.txt
for %%i in (*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %%i 2^>^> mods_errors.txt >> temp.bat
call temp.bat> nul
del temp.bat
move mods_errors.txt %workdir%
title L2JDP Installer - Mod Server Tables Process Complete
cls
echo Database structure for L2J Mods finished.
echo.
echo Remember that in order to get these additions actually working 
echo you need to edit your configuration files. 
echo.
pause
cd %workdir%
goto tables_updates

:tables_updates
call :colors 17
set stage=10
title L2JDP Installer - Automatic Tables Updates
if %full% == 1 goto end
:tables_updates_ask
cls
set nbprompt=a
echo.
echo Automatic Tables Updates:
echo.
echo This will update tables automatically depending of your chooise, due some
echo Changesets require updates in specific tables to work properly, due new
echo columns are introduced or table structure is modified.
echo.
echo (a) All LS/CS/GS Tables Updates.
echo.
echo (l) LS Tables Updates only.
echo.
echo (c) CS Tables Updates only.
echo.
echo (g) GS Tables Updates only.
echo.
echo (q) Quit.
echo.
set /p nbprompt= Choose (default auto-all):
if /i %nbprompt%==a goto ls_tables_updates
if /i %nbprompt%==l goto ls_tables_updates
if /i %nbprompt%==c goto cs_tables_updates
if /i %nbprompt%==g goto gs_tables_updates
if /i %nbprompt%==q goto end
goto tables_updates_ask
:ls_tables_updates
cd ..\sql\login\updates\
echo @echo off> temp.bat
if exist ls_errors.txt del ls_errors.txt
for %%i in (*.sql) do echo "%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -D %lsdb% ^< %%i 2^>^> ls_errors.txt >> temp.bat
call temp.bat> nul
del temp.bat
move ls_errors.txt %workdir%
cd %workdir%
if /i %nbprompt%==l goto tables_updates_done
:cs_tables_updates
cd ..\cb_sql\updates\
echo @echo off> temp.bat
if exist cs_errors.txt del cs_errors.txt
for %%i in (*.sql) do echo "%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -D %cbdb% ^< %%i 2^>^> cs_errors.txt >> temp.bat
call temp.bat> nul
del temp.bat
move cs_errors.txt %workdir%
cd %workdir%
if /i %nbprompt%==c goto tables_updates_done
:gs_tables_updates
cd ..\sql\server\updates\
echo @echo off> temp.bat
if exist gs_errors.txt del gs_errors.txt
for %%i in (*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %%i 2^>^> gs_errors.txt >> temp.bat
call temp.bat> nul
del temp.bat
move gs_errors.txt %workdir%
cd %workdir%
:tables_updates_done
title L2JDP Installer - Automatic Tables Updates Process Complete
cls
echo.
echo Automagic processing finished, i'm leaving 'ls/cs/gs_errors.txt' files
echo for your consideration.
echo.
pause
goto end

:binaryfind
if EXIST "%mysqlBinPath%" (echo Found) else (echo Not Found)
goto :eof

:horrible_end
call :colors 47
title L2JDP Installer - Oops!
cls
echo This wasn't a clean run, but don't worry.
echo You can get help and support:
echo.
echo 1-Read the L2J Datapack project wiki :
echo       (http://www.l2jdp.com/trac/wiki)
echo 2-Search for a similar problem in our forums
echo       (http://www.l2jdp.com/forum)
echo.
echo You can ask for support in our forums or irc channel:
echo irc://irc.freenode.net channel: #l2j
echo.
echo I'll try to gather some versioning information that you
echo may find useful when asking for support :
echo.
echo Datapack revision reported by 'SVN version':
svnversion -n 2>NUL
echo.
if %ERRORLEVEL% == 9009 (
echo   SVN commandline tools not found!
echo   Please download and install "Windows installer with 
echo   the basic win32 binaries" (or something that fits our
echo   binaries needs) from :
echo   http://goo.gl/c0uyh
echo.
)
set dpvf="..\config\l2jdp-version.properties" 
echo Datapack revision reported by properties file :
if NOT EXIST %dpvf% (
echo   Your %dpvf% file is missing!
echo   Use eclipse/ant to build one from your DP SVN copy.
echo   With it we'll be able to help you better.
) else (
type %dpvf% | find "version" 2> NUL
if not %ERRORLEVEL% == 0 (
echo   An error occured while trying to read
echo   your %dpvf% file!
echo   Make sure you keep it up to date
echo   and in the correct place.
echo %ERRORLEVEL%
))
echo.
rem del %config_file%
pause
goto end

:end
call :colors 17
title L2JDP Installer - Script Execution Finished
cls
echo.
echo L2JDP database_installer version 0.2.4
echo (C) 2007-2011 L2J Datapack Team
echo database_installer comes with ABSOLUTELY NO WARRANTY;
echo This is free software, and you are welcome to redistribute it
echo under certain conditions; See the file gpl.txt for further
echo details.
echo.
echo Thanks for using our software.
echo visit http://www.l2jdp.com for more info about
echo the L2J Datapack project.
echo.
pause
color