#!/bin/bash
##########################################################################
## Copyright (C) 2004-2013 L2J DataPack                                 ##
##                                                                      ##
## This file is part of L2J DataPack.                                   ##
##                                                                      ##
## L2J DataPack is free software: you can redistribute it and/or modify ##
## it under the terms of the GNU General Public License as published by ##
## the Free Software Foundation, either version 3 of the License, or    ##
## (at your option) any later version.                                  ##
##                                                                      ##
## L2J DataPack is distributed in the hope that it will be useful,      ##
## but WITHOUT ANY WARRANTY; without even the implied warranty of       ##
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU     ##
## General Public License for more details.                             ##
##                                                                      ##
## You should have received a copy of the GNU General Public License    ##
## along with this program. If not, see <http://www.gnu.org/licenses/>. ##
##########################################################################
## WARNING!  WARNING!  WARNING!  WARNING! ##
##                                        ##
## DON'T USE NOTEPAD TO CHANGE THIS FILE  ##
## INSTEAD USE SOME DECENT TEXT EDITOR.   ##
## NEWLINE CHARACTERS DIFFER BETWEEN DOS/ ##
## WINDOWS AND UNIX.                      ##
##                                        ##
## USING NOTEPAD TO SAVE THIS FILE WILL   ##
## LEAVE IT IN A BROKEN STATE!!!          ##
############################################
## Writen by DrLecter                     ##
## License: GNU GPL                       ##
## Based on Tiago Tagliaferri's script    ##
## E-mail: tiago_tagliaferri@msn.com      ##
## From "L2J-DataPack"                    ##
## Bug reports: http://trac.l2jdp.com/    ##
############################################
trap finish 2

configure() {
echo "#############################################"
echo "# You entered script configuration area     #"
echo "# No change will be performed in your DB    #"
echo "# I will just ask you some questions about  #"
echo "# your hosts and DB.                        #"
echo "#############################################"
MYSQLDUMPPATH=`which -a mysqldump 2>/dev/null`
MYSQLPATH=`which -a mysql 2>/dev/null`
if [ $? -ne 0 ]; then
echo "We were unable to find MySQL binaries on your path"
while :
 do
  echo -ne "\nPlease enter MySQL binaries directory (no trailing slash): "
  read MYSQLBINPATH
    if [ -e "$MYSQLBINPATH" ] && [ -d "$MYSQLBINPATH" ] && \
       [ -e "$MYSQLBINPATH/mysqldump" ] && [ -e "$MYSQLBINPATH/mysql" ]; then
       MYSQLDUMPPATH="$MYSQLBINPATH/mysqldump"
       MYSQLPATH="$MYSQLBINPATH/mysql"
       break
    else
       echo "The data you entered is invalid. Please verify and try again."
       exit 1
    fi
 done
fi
#LS
echo -ne "\nPlease enter MySQL Login Server hostname (default localhost): "
read LSDBHOST
if [ -z "$LSDBHOST" ]; then
  LSDBHOST="localhost"
fi
echo -ne "\nPlease enter MySQL Login Server database name (default l2jls): "
read LSDB
if [ -z "$LSDB" ]; then
  LSDB="l2jls"
fi
echo -ne "\nPlease enter MySQL Login Server user (default root): "
read LSUSER
if [ -z "$LSUSER" ]; then
  LSUSER="root"
fi
echo -ne "\nPlease enter MySQL Login Server $LSUSER's password (won't be displayed) :"
stty -echo
read LSPASS
stty echo
echo ""
if [ -z "$LSPASS" ]; then
  echo "Hum.. I'll let it be but don't be stupid and avoid empty passwords"
elif [ "$LSUSER" == "$LSPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
#CB
echo -ne "\nPlease enter MySQL Community Server hostname (default localhost): "
read CBDBHOST
if [ -z "$CBDBHOST" ]; then
  CBDBHOST="localhost"
fi
echo -ne "\nPlease enter MySQL Community Server database name (default l2jcs): "
read CBDB
if [ -z "$CBDB" ]; then
  CBDB="l2jcs"
fi
echo -ne "\nPlease enter MySQL Community Server user (default root): "
read CBUSER
if [ -z "$CBUSER" ]; then
  CBUSER="root"
fi
echo -ne "\nPlease enter MySQL Community Server $CBUSER's password (won't be displayed) :"
stty -echo
read CBPASS
stty echo
echo ""
if [ -z "$CBPASS" ]; then
  echo "Hum.. I'll let it be but don't be stupid and avoid empty passwords"
elif [ "$CBUSER" == "$CBPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
#GS
echo -ne "\nPlease enter MySQL Game Server hostname (default $LSDBHOST): "
read GSDBHOST
if [ -z "$GSDBHOST" ]; then
  GSDBHOST="localhost"
fi
echo -ne "\nPlease enter MySQL Game Server database name (default l2jgs): "
read GSDB
if [ -z "$GSDB" ]; then
  GSDB="l2jgs"
fi
echo -ne "\nPlease enter MySQL Game Server user (default $LSUSER): "
read GSUSER
if [ -z "$GSUSER" ]; then
  GSUSER="root"
fi
echo -ne "\nPlease enter MySQL Game Server $GSUSER's password (won't be displayed): "
stty -echo
read GSPASS
stty echo
echo ""
if [ -z "$GSPASS" ]; then
  echo "Hum.. I'll let it be but don't be stupid and avoid empty passwords"
elif [ "$GSUSER" == "$GSPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
save_config $1
}

save_config() {
if [ -n "$1" ]; then
CONF="$1"
else 
CONF="database_installer.rc"
fi
echo ""
echo "With these data I can generate a configuration file which can be read"
echo "on future updates. WARNING: this file will contain clear text passwords!"
echo -ne "Shall I generate config file $CONF? (Y/n):"
read SAVE
if [ "$SAVE" == "y" -o "$SAVE" == "Y" -o "$SAVE" == "" ];then 
cat <<EOF>$CONF
#Configuration settings for L2J-Datapack database installer script
MYSQLDUMPPATH=$MYSQLDUMPPATH
MYSQLPATH=$MYSQLPATH
LSDBHOST=$LSDBHOST
LSDB=$LSDB
LSUSER=$LSUSER
LSPASS=$LSPASS
CBDBHOST=$CBDBHOST
CBDB=$CBDB
CBUSER=$CBUSER
CBPASS=$CBPASS
GSDBHOST=$GSDBHOST
GSDB=$GSDB
GSUSER=$GSUSER
GSPASS=$GSPASS
EOF
chmod 600 $CONF
echo "Configuration saved as $CONF"
echo "Permissions changed to 600 (rw- --- ---)"
elif [ "$SAVE" != "n" -a "$SAVE" != "N" ]; then
  save_config
fi
}

load_config() {
if [ -n "$1" ]; then
CONF="$1"
else 
CONF="database_installer.rc"
fi
if [ -e "$CONF" ] && [ -f "$CONF" ]; then
. $CONF
else
echo "Settings file not found: $CONF"
echo "You can specify an alternate settings filename:"
echo $0 config_filename
echo ""
echo "If file doesn't exist it can be created"
echo "If nothing is specified script will try to work with ./database_installer.rc"
echo ""
configure $CONF
fi
}

ls_backup(){
while :
  do
   clear
   echo ""
   echo -ne "Do you want to make a backup copy of your LSDB? (y/n): "
   read LSB
   if [ "$LSB" == "Y" -o "$LSB" == "y" ]; then
     echo "Trying to make a backup of your Login Server DataBase."
     $MYSQLDUMPPATH --add-drop-table -h $LSDBHOST -u $LSUSER --password=$LSPASS $LSDB > ls_backup.sql
     if [ $? -ne 0 ];then
        clear
		echo ""
        echo "There was a problem accesing your LS database, either it wasnt created or authentication data is incorrect."
        exit 1
     fi
     break
   elif [ "$LSB" == "n" -o "$LSB" == "N" ]; then 
     break
   fi
  done 
ls_ask
}

ls_ask(){
clear
echo ""
echo "LOGINSERVER DATABASE install type:"
echo ""
echo "(f) Full: WARNING! I'll destroy ALL of your existing login"
echo "    data."
echo ""
echo "(u) Upgrade: I'll do my best to preserve all login data."
echo ""
echo "(s) Skip: I'll take you to the communityserver database"
echo "    installation and upgrade options."
echo ""
echo "(q) Quit"
echo ""
echo -ne "LOGINSERVER DB install type: "
read LSASK
case "$LSASK" in
	"f"|"F") ls_cleanup I;;
	"u"|"U") ls_upgrade U;;
	"s"|"S") cs_backup;;
	"q"|"Q") finish;;
	*) ls_ask;;
esac
}

ls_cleanup(){
clear
echo "Deleting Login Server tables for new content."
$MYL < ls_cleanup.sql
ls_install
}

ls_upgrade(){
clear
echo ""
echo "Upgrading structure of Login Server tables."
echo ""
for file in $(ls ../sql/login/updates/*.sql);do
	$MYL --force < $file 2>> ls_error.log
done
ls_install
}

ls_install(){
clear
if [ "$1" == "I" ]; then 
echo ""
echo "Installing new Login Server content."
echo ""
else
echo ""
echo "Upgrading Login Server content."
echo ""
fi
for login in $(ls ../sql/login/*.sql);do
	echo "Installing loginserver table : $login"
	$MYL < $login
done
cs_ask
}

cs_backup(){
while :
  do
   clear
   echo ""
   echo -ne "Do you want to make a backup copy of your CBDB? (y/n): "
   read CSB
   if [ "$CSB" == "Y" -o "$CSB" == "y" ]; then
     echo "Trying to make a backup of your Community Server DataBase."
     $MYSQLDUMPPATH --add-drop-table -h $CBDBHOST -u $CBUSER --password=$CBPASS $CBDB > cs_backup.sql
     if [ $? -ne 0 ];then
     clear
	 echo ""
     echo "There was a problem accesing your CB database, either it wasnt created or authentication data is incorrect."
     exit 1
     fi
     break
   elif [ "$CSB" == "n" -o "$CSB" == "N" ]; then 
     break
   fi
  done 
cs_ask
}

cs_ask(){
clear
echo ""
echo "COMMUNITY SERVER DATABASE install type:"
echo ""
echo "(f) Full: WARNING! I'll destroy ALL of your existing community"
echo "    data (i really mean it: mail, forum, memo.. ALL)"
echo ""
echo "(u) Upgrade: I'll do my best to preserve all of your community"
echo "    data."
echo ""
echo "(s) Skip: I'll take you to the gameserver database"
echo "    installation and upgrade options."
echo ""
echo "(q) Quit"
echo ""
echo -ne "COMMUNITYSERVER DB install type: "
read CSASK
case "$CSASK" in
	"f"|"F") cs_cleanup I;;
	"u"|"U") cs_upgrade U;;
	"s"|"S") gs_backup;;
	"q"|"Q") finish;;
	*) cs_ask;;
esac
}

cs_cleanup(){
clear
echo "Deleting Community Server tables for new content."
$MYC < cs_cleanup.sql
cs_install
}

cs_upgrade(){
clear
echo ""
echo "Upgrading structure of Community Server tables."
echo ""
for file in $(ls ../sql/community/updates/*sql);do
	$MYC --force < $file 2>> cs_error.log
done
cs_install
}

cs_install(){
clear
if [ "$1" == "I" ]; then 
echo ""
echo "Installing new Community Server content."
echo ""
else
echo ""
echo "Upgrading Community Server content."
echo ""
fi
for cb in $(ls ../sql/community/*.sql);do
	echo "Installing Communityserver table : $cb"
	$MYC < $cb
done
gs_ask
}

gs_backup(){
while :
  do
   clear
   echo ""
   echo -ne "Do you want to make a backup copy of your GSDB? (y/n): "
   read GSB
   if [ "$GSB" == "Y" -o "$GSB" == "y" ]; then
     echo "Trying to create a Game Server DataBase."
     $MYSQLDUMPPATH --add-drop-table -h $GSDBHOST -u $GSUSER --password=$GSPASS $GSDB > gs_backup.sql
     if [ $? -ne 0 ];then
	 clear
     echo ""
     echo "There was a problem accesing your GS database, either it wasnt created or authentication data is incorrect."
     exit 1
     fi
     break
   elif [ "$GSB" == "n" -o "$GSB" == "N" ]; then 
     break
   fi
  done 
gs_ask
}

gs_ask(){
clear
echo ""
echo "GAME SERVER DATABASE install:"
echo ""
echo "(f) Full: WARNING! I'll destroy ALL of your existing character"
echo "    data (i really mean it: items, pets.. ALL)"
echo ""
echo "(u) Upgrade: I'll do my best to preserve all of your character"
echo "    data."
echo ""
echo "(s) Skip: We'll get into the last set of questions (cummulative"
echo "    updates, custom stuff...)"
echo ""
echo "(q) Quit"
echo ""
echo -ne "GAMESERVER DB install type: "
read GSASK
case "$GSASK" in
	"f"|"F") gs_cleanup I;;
	"u"|"U") gs_upgrade U;;
	"s"|"S") custom_ask;;
	"q"|"Q") finish;;
	*) gs_ask;;
esac
}

gs_cleanup(){
clear
echo "Deleting all Game Server tables for new content."
$MYG < gs_cleanup.sql
gs_install
}

gs_upgrade(){
clear
echo ""
echo "Upgrading structure of Game Server tables (this could take awhile, be patient)"
echo ""
for file in $(ls ../sql/game/updates/*.sql);do
	$MYG --force < $file 2>> gs_error.log
done
gs_install
}

gs_install(){
clear
if [ "$1" == "I" ]; then 
echo ""
echo "Installing new Game Server content."
echo ""
else
echo ""
echo "Upgrading Game Server content."
echo ""
fi
for gs in $(ls ../sql/game/*.sql);do
	echo "Installing GameServer table : $gs"
	$MYG < $gs
done
custom_ask
}

custom_ask(){
clear
echo ""
echo "L2J provides some Custom Server Tables for non-retail modifications"
echo "in order to avoid override the original Server Tables."
echo ""
echo "Remember that in order to get these additions actually working"
echo "you need to edit your configuration files."
echo ""
echo -ne "Install Custom Server Tables: (y) yes or (n) no ?"
read CSASK
case "$CSASK" in
	"y"|"Y") custom_install;;
	"n"|"N") mod_ask;;
	*) custom_ask;;
esac
}

custom_install(){
clear
echo ""
echo "Installing Custom content."
for custom in $(ls ../sql/game/custom/*.sql);do 
	echo "Installing custom table: $custom"
	$MYG < $custom
done
clear
mod_ask
}

mod_ask(){
clear
echo ""
echo "L2J provides a basic infraestructure for some non-retail features"
echo "(aka L2J mods) to get enabled with a minimum of changes."
echo ""
echo "Some of these mods would require extra tables in order to work"
echo "and those tables could be created now if you wanted to."
echo ""
echo "Remember that in order to get these additions actually working"
echo "you need to edit your configuration files."
echo ""
echo -ne "Install Mod Server Tables: (y) yes or (n) no ?"
read MDASK
case "$MDASK" in
	"y"|"Y") mod_install;;
	"n"|"N") finish;;
	*) mod_ask;;
esac
}

mod_install(){
clear
echo ""
echo "Installing Mods content."
for mod in $(ls ../sql/game/mods/*.sql);do
	echo "Installing custom mod table : $mod"
	$MYG < $mod
done
clear
finish
}

finish(){
clear
echo "L2JDP Database Installer"
echo ""
echo "Thanks for using our software."
echo "visit http://www.l2jdp.com for more info about"
echo "the L2J DataPack Project."
exit 0
}

clear
load_config $1
MYL="$MYSQLPATH -h $LSDBHOST -u $LSUSER --password=$LSPASS -D $LSDB"
MYG="$MYSQLPATH -h $GSDBHOST -u $GSUSER --password=$GSPASS -D $GSDB"
MYC="$MYSQLPATH -h $CBDBHOST -u $CBUSER --password=$CBPASS -D $CBDB"
ls_backup