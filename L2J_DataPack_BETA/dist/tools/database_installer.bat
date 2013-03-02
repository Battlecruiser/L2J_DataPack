@echo off
REM ##############################################
REM ## L2JDP Database Installer - (by DrLecter) ##
REM ##############################################
REM ## Interactive script setup -  (by TanelTM) ##
REM ##############################################
REM Copyright (C) 2004-2013 L2J DataPack
REM
REM This file is part of L2J DataPack.
REM
REM L2J DataPack is free software: you can redistribute it and/or modify
REM it under the terms of the GNU General Public License as published by
REM the Free Software Foundation, either version 3 of the License, or
REM (at your option) any later version.
REM
REM L2J DataPack is distributed in the hope that it will be useful,
REM but WITHOUT ANY WARRANTY; without even the implied warranty of
REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
REM General Public License for more details.
REM
REM You should have received a copy of the GNU General Public License
REM along with this program. If not, see <http://www.gnu.org/licenses/>.

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
cls
title L2JDP Installer - Reading Configuration from File...
if not exist %config_file% goto configure
ren %config_file% vars.bat
call vars.bat
ren vars.bat %config_file%
call :colors 17
if /i %config_version% == 2 goto ls_backup
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
if %upgrade_mode%==1 goto ls_backup
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
cls
call :colors 17
title L2JDP Installer - Setup
set config_version=2
if NOT %upgrade_mode% == 2 (
set fresh_setup=1
set mysqlBinPath=%ProgramFiles%\MySQL\MySQL Server 5.6\bin
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
echo 2-Login Server settings
echo -----------------------
echo I order to connect to the MySQL Server, you should
echo specify the Login Server DataBase parameters here.
echo.
set /P lsuser="MySQL Username (default is '%lsuser%'): "
set /P lspass="Password (default is '%lspass%'): "
set /P lsdb="Database (default is '%lsdb%'): "
set /P lshost="Host (default is '%lshost%'): "
echo.
cls
echo.
echo 3-Community Server settings
echo ---------------------------
echo I order to connect to the MySQL Server, you should
echo specify the Community Server DataBase parameters here.
echo.
set /P cbuser="MySQL Username (default is '%cbuser%'): "
set /P cbpass="Password (default is '%cbpass%'): "
set /P cbdb="Database (default is '%cbdb%'): "
set /P cbhost="Host (default is '%cbhost%'): "
echo.
cls
echo.
echo 4-Game Server settings
echo ----------------------
echo I order to connect to the MySQL Server, you should
echo specify the Game Server DataBase parameters here.
echo.
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
cls
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

:ls_backup
cls
call :colors 17
set cmdline=
set stage=1
title L2JDP Installer - Login Server DataBase Backup
echo.
echo Trying to make a backup of your Login Server DataBase.
set cmdline="%mysqldumpPath%" --add-drop-table -h %lshost% -u %lsuser% --password=%lspass% %lsdb% ^> "%backup%\ls_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto ls_db_ok

:ls_err1
cls
set lsdbprompt=y
call :colors 47
title L2JDP Installer - Login Server DataBase Backup ERROR!
echo.
echo Backup attempt failed! A possible reason for this to
echo happen, is that your DB doesn't exist yet. I could
echo try to create %lsdb% for you, or maybe you prefer to
echo contunue with the Community Server part of this tool.
echo.
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
set /p lsdbprompt=Choose (default yes):
if /i %lsdbprompt%==y goto ls_db_create
if /i %lsdbprompt%==n goto cs_backup
if /i %lsdbprompt%==r goto configure
if /i %lsdbprompt%==q goto end
goto ls_err1

:ls_db_create
cls
call :colors 17
set cmdline=
set stage=2
title L2JDP Installer - Login Server DataBase Creation
echo.
echo Trying to create a Login Server DataBase.
set cmdline="%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -e "CREATE DATABASE %lsdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto ls_db_ok
if %safe_mode% == 1 goto omfg

:ls_err2
cls
set omfgprompt=q
call :colors 47
title L2JDP Installer - Login Server DataBase Creation ERROR!
echo.
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
echo (c) Continue running
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt=Choose (default quit):
if /i %omfgprompt%==c goto cs_backup
if /i %omfgprompt%==r goto configure
if /i %omfgprompt%==q goto end
goto ls_err2

:ls_db_ok
cls
set loginprompt=u
call :colors 17
title L2JDP Installer - Login Server DataBase WARNING!
echo.
echo LOGINSERVER DATABASE install type:
echo.
echo (f) Full: WARNING! I'll destroy ALL of your existing login
echo     data.
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
set /p loginprompt=Choose (default upgrade):
if /i %loginprompt%==f goto ls_cleanup
if /i %loginprompt%==u goto ls_upgrade
if /i %loginprompt%==s goto cs_backup
if /i %loginprompt%==r goto configure
if /i %loginprompt%==q goto end
goto ls_db_ok

:ls_cleanup
call :colors 17
set cmdline=
title L2JDP Installer - Login Server DataBase Full Install
echo.
echo Deleting Login Server tables for new content.
set cmdline="%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -D %lsdb% ^< ls_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Login Server tables has been deleted.
goto ls_install

:ls_upgrade
cls
echo.
echo Upgrading structure of Login Server tables.
echo.
echo @echo off> temp.bat
if exist ls_errors.log del ls_errors.log
for %%i in (..\sql\login\updates\*.sql) do echo "%mysqlPath%" -h %lshost% -u %lsuser% --password=%lspass% -D %lsdb% --force ^< %%i 2^>^> ls_errors.log >> temp.bat
call temp.bat> nul
del temp.bat
move ls_errors.log %workdir%
goto ls_install

:ls_install
cls
set cmdline=
if %full% == 1 (
title L2JDP Installer - Login Server DataBase Installing...
echo.
echo Installing new Login Server content.
echo.
) else (
title L2JDP Installer - Login Server DataBase Upgrading...
echo.
echo Upgrading Login Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=ls
for %%i in (..\sql\login\*.sql) do call :dump %%i

echo done...
echo.
goto cs_backup

:cs_backup
cls
call :colors 17
set cmdline=
set stage=3
title L2JDP Installer - Community Server DataBase Backup
echo.
echo Trying to make a backup of your Comunity Server DataBase.
set cmdline="%mysqldumpPath%" --add-drop-table -h %cbhost% -u %cbuser% --password=%cbpass% %cbdb% ^> "%backup%\cs_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto cs_db_ok

:cs_err1
cls
set cbdbprompt=y
call :colors 47
title L2JDP Installer - Community Server DataBase Backup ERROR!
echo.
echo Backup attempt failed! A possible reason for this to
echo happen, is that your DB doesn't exist yet. I could
echo try to create %cbdb% for you, or maybe you prefer to
echo continue with the GameServer part of this tool.
echo.
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
set /p cbdbprompt=Choose (default yes):
if /i %cbdbprompt%==y goto cs_db_create
if /i %cbdbprompt%==n goto gs_backup
if /i %cbdbprompt%==r goto configure
if /i %cbdbprompt%==q goto end
goto cs_err1

:cs_db_create
cls
call :colors 17
set cmdline=
set stage=4
title L2JDP Installer - Community Server DataBase Creation
echo.
echo Trying to create a Community Server DataBase...
set cmdline="%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -e "CREATE DATABASE %cbdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto cs_db_ok
if %safe_mode% == 1 goto omfg

:cs_err2
cls
set omfgprompt=q
call :colors 47
title L2JDP Installer - Community Server DataBase Creation ERROR!
echo.
echo An error occured while trying to create a database for
echo your Community Server.
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
echo (c) Continue running
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt=Choose (default quit):
if /i %omfgprompt%==c goto gs_backup
if /i %omfgprompt%==r goto configure
if /i %omfgprompt%==q goto end
goto cs_err2

:cs_db_ok
cls
set communityprompt=u
call :colors 17
title L2JDP Installer - Community Server DataBase WARNING!
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
set /p communityprompt=Choose (default upgrade):
if /i %communityprompt%==f goto cs_cleanup
if /i %communityprompt%==u goto cs_upgrade
if /i %communityprompt%==s goto gs_backup
if /i %communityprompt%==r goto configure
if /i %communityprompt%==q goto end
goto cs_db_ok

:cs_cleanup
call :colors 17
set cmdline=
title L2JDP Installer - Community Server DataBase Full Install
echo.
echo Deleting Community Server tables for new content.
set cmdline="%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -D %cbdb% ^< cs_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Community Server tables has been deleted.
goto cs_install

:cs_upgrade
cls
echo.
echo Upgrading structure of Community Server tables.
echo.
echo @echo off> temp.bat
if exist cs_errors.log del cs_errors.log
for %%i in (..\sql\community\updates\*.sql) do echo "%mysqlPath%" -h %cbhost% -u %cbuser% --password=%cbpass% -D %cbdb% --force ^< %%i 2^>^> cs_errors.log >> temp.bat
call temp.bat> nul
del temp.bat
move cs_errors.log %workdir%
goto cs_install

:cs_install
cls
set cmdline=
if %full% == 1 (
title L2JDP Installer - Community Server DataBase Installing...
echo.
echo Installing new Community Server content.
echo.
) else (
title L2JDP Installer - Community Server DataBase Upgrading...
echo.
echo Upgrading Community Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=cb
for %%i in (..\sql\community\*.sql) do call :dump %%i

echo done...
echo.
goto gs_backup

:gs_backup
cls
call :colors 17
set cmdline=
set stage=5
title L2JDP Installer - Game Server DataBase Backup
echo.
echo Trying to make a backup of your Game Server DataBase.
set cmdline="%mysqldumpPath%" --add-drop-table -h %gshost% -u %gsuser% --password=%gspass% %gsdb% ^> "%backup%\gs_backup.sql" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto gs_db_ok

:gs_err1
cls
set gsdbprompt=y
call :colors 47
title L2JDP Installer - Game Server DataBase Backup ERROR!
echo.
echo Backup attempt failed! A possible reason for this to
echo happen, is that your DB doesn't exist yet. I could
echo try to create %gsdb% for you, but maybe you prefer to
echo continue with last part of the script.
echo.
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
set /p gsdbprompt=Choose (default yes):
if /i %gsdbprompt%==y goto gs_db_create
if /i %gsdbprompt%==n goto eof
if /i %gsdbprompt%==r goto configure
if /i %gsdbprompt%==q goto end
goto gs_err1

:gs_db_create
cls
call :colors 17
set stage=6
set cmdline=
title L2JDP Installer - Game Server DataBase Creation
echo.
echo Trying to create a Game Server DataBase...
set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -e "CREATE DATABASE %gsdb%" 2^> NUL
%cmdline%
if %ERRORLEVEL% == 0 goto gs_db_ok
if %safe_mode% == 1 goto omfg

:gs_err2
cls
set omfgprompt=q
call :colors 47
title L2JDP Installer - Game Server DataBase Creation ERROR!
echo.
echo An error occured while trying to create a database for
echo your Game Server.
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
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt=Choose (default quit):
if /i %omfgprompt%==r goto configure
if /i %omfgprompt%==q goto end
goto gs_err2

:gs_db_ok
cls
set installtype=u
call :colors 17
title L2JDP Installer - Game Server DataBase WARNING!
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
set /p installtype=Choose (default upgrade):
if /i %installtype%==f goto gs_cleanup
if /i %installtype%==u goto gs_upgrade
if /i %installtype%==s goto custom_ask
if /i %installtype%==q goto end
goto gs_db_ok

:gs_cleanup
call :colors 17
set cmdline=
title L2JDP Installer - Game Server DataBase Full Install
echo.
echo Deleting all Game Server tables for new content.
set cmdline="%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< gs_cleanup.sql 2^> NUL
%cmdline%
if not %ERRORLEVEL% == 0 goto omfg
set full=1
echo.
echo Game Server tables has been deleted.
goto gs_install

:gs_upgrade
cls
echo.
echo Upgrading structure of Game Server tables (this could take awhile, be patient).
echo.
echo @echo off> temp.bat
if exist gs_errors.log del gs_errors.log
for %%i in (..\sql\game\updates\*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% --force ^< %%i 2^>^> gs_errors.log >> temp.bat
call temp.bat> nul
del temp.bat
move gs_errors.log %workdir%
goto gs_install

:gs_install
cls
set cmdline=
if %full% == 1 (
title L2JDP Installer - Game Server DataBase Installing...
echo.
echo Installing new Game Server content.
echo.
) else (
title L2JDP Installer - Game Server DataBase Upgrading...
echo.
echo Upgrading Game Server content.
echo.
)
if %logging% == 0 set output=NUL
set dest=gs
for %%i in (..\sql\game\*.sql) do call :dump %%i

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
set ntpebcak=c
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
set /p ntpebcak=Choose (default continue):
if /i %ntpebcak%==c (call :colors 17 & goto :eof)
if /i %ntpebcak%==l (call :logginon %1 & goto :eof)
if /i %ntpebcak%==r (call :configure & exit)
if /i %ntpebcak%==q (call :end)
goto omfg2

:logginon
cls
call :colors 17
title L2JDP Installer - Game Server Logging Options turned on
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
echo Remember that in order to get these additions actually working
echo you need to edit your configuration files.
echo.
set /p cstprompt=Install Custom Server Tables: (y) yes or (n) no (default no):
if /i %cstprompt%==y goto custom_install
if /i %cstprompt%==n goto mod_ask

:custom_install
cls
echo.
echo Installing Custom content.
echo @echo off> temp.bat
if exist custom_errors.log del custom_errors.log
for %%i in (..\sql\game\custom\*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %%i 2^>^> custom_errors.log >> temp.bat
call temp.bat> nul
del temp.bat
move custom_errors.log %workdir%
goto mod_ask

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
echo Remember that in order to get these additions actually working
echo you need to edit your configuration files.
echo.
set /p cstprompt=Install Mod Server Tables: (y) yes or (n) no (default no):
if /i %cstprompt%==y goto mod_install
if /i %cstprompt%==n goto end

:mod_install
cls
echo.
echo Installing Mods content.
echo @echo off> temp.bat
if exist mods_errors.log del mods_errors.log
for %%i in (..\sql\game\mods\*.sql) do echo "%mysqlPath%" -h %gshost% -u %gsuser% --password=%gspass% -D %gsdb% ^< %%i 2^>^> mods_errors.log >> temp.bat
call temp.bat> nul
del temp.bat
move mods_errors.log %workdir%
goto end

:omfg
set omfgprompt=q
call :colors 57
cls
title L2JDP Installer - Potential PICNIC detected at stage %stage%
echo.
echo There was some problem while executing:
echo.
echo "%cmdline%"
echo.
echo I'd suggest you to look for correct values and try this
echo script again later. But maybe you'd prefer to go on now.
if %stage% == 1 set label=ls_err1
if %stage% == 2 set label=ls_err2
if %stage% == 3 set label=cs_err1
if %stage% == 4 set label=cs_err2
if %stage% == 5 set label=gs_err1
if %stage% == 6 set label=gs_err2
echo.
echo (c) Continue running the script
echo.
echo (r) Reconfigure
echo.
echo (q) Quit now
echo.
set /p omfgprompt=Choose (default quit):
if /i %omfgprompt%==c goto %label%
if /i %omfgprompt%==r goto configure
if /i %omfgprompt%==q goto end
goto omfg

:binaryfind
if EXIST "%mysqlBinPath%" (echo Found) else (echo Not Found)
goto :eof

:end
call :colors 17
title L2JDP Installer - Script Execution Finished
cls
echo.
echo L2JDP Database Installer
echo.
echo Thanks for using our software.
echo visit http://www.l2jdp.com for more info about
echo the L2J DataPack Project.
echo.
pause