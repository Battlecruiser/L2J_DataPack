#!/bin/bash
############################################
## Writen by DrLecter                     ##
## License: GNU GPL                       ##
## Based on Tiago Tagliaferri's script    ##
## E-mail: tiago_tagliaferri@msn.com	##
## From "L2J-DataPack"                    ##
############################################
trap finish 2

configure() {
MYSQLDUMPPATH=`which mysqldump 2>/dev/null`
MYSQLPATH=`which mysql 2>/dev/null`
if [ $? -ne 0 ]; then
echo "We were unable to find MySQL binaries on your path"
while :
 do
  echo -ne "\nPlease enter MySQL binaries directory (no trailing slash): "
  read MYSQLBINPATH
    if [ -e "$MYSQLBINPATH" ] && [ -d "$MYSQLBINPATH" ] && [ -e "$MYSQLBINPATH/mysqldump" ] && [ -e "$MYSQLBINPATH/mysql" ]; then
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
echo -ne "\nPlease enter MySQL Login Server database name (default l2jdb): "
read LSDB
if [ -z "$LSDB" ]; then
  LSDB="l2jdb"
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
  echo "Hum.. i'll let it be but don't be stupid and avoid empty passwords"
elif [ "$LSUSER" == "$LSPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
#GS
echo -ne "\nPlease enter MySQL Game Server hostname (default localhost): "
read GSDBHOST
if [ -z "$GSDBHOST" ]; then
  GSDBHOST="localhost"
fi
echo -ne "\nPlease enter MySQL Game Server database name (default l2jdb): "
read GSDB
if [ -z "$GSDB" ]; then
  GSDB="l2jdb"
fi
echo -ne "\nPlease enter MySQL Game Server user (default root): "
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
  echo "Hum.. i'll let it be but don't be stupid and avoid empty passwords"
elif [ "$GSUSER" == "$GSPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
save_config
}

save_config() {
echo ""
echo "With these data i can generate a configuration file which can be read"
echo "on future updates. WARNING: this file will contain clear text passwords!"
echo -ne "Shall i generate config file? (Y/n):"
read SAVE
if [ "$SAVE" == "y" -o "$SAVE"=="Y" -o "$SAVE"=="" ];then 
cat <<EOF>database_installer.rc
#Configuration settings for L2J-Datapack database installer script
MYSQLDUMPPATH=$MYSQLDUMPPATH
MYSQLPATH=$MYSQLPATH
LSDBHOST=$LSDBHOST
LSDB=$LSDB
LSUSER=$LSUSER
LSPASS=$LSPASS
GSDBHOST=$GSDBHOST
GSDB=$GSDB
GSUSER=$GSUSER
GSPASS=$GSPASS
EOF
chmod 600 database_installer.rc
echo "Configuration saved as ./database_installer.rc"
echo "Permissions changed to 600 (rw- --- ---)"
elif [ "$SAVE" != "n" -o "$SAVE"!="N" ]; then
  save_config
fi
}

load_config() {
if [ -e database_installer.rc ] && [ -f database_installer.rc ]; then
. database_installer.rc
else
echo "Saved settings file not found"
configure
fi
}

asklogin(){
echo ""
echo "WARNING: A full install (f) will destroy data in your"
echo "'accounts' and 'gameserver' tables."
echo "Choose upgrade (u) if you already have an 'accounts' table but no"
echo "'gameserver' table (ie. your server is a pre LS/GS split version.)"
echo "Choose skip (s) to skip loginserver DB installation and go to"
echo "gameserver DB installation/upgrade."
echo -ne "LOGINSERVER DB install type: (f) full, (u) upgrade or (s) skip or (q) quit? "
read LOGINPROMPT
case "$LOGINPROMPT" in
	"f"|"F") logininstall; loginupgrade; gsbackup; asktype;;
	"u"|"U") loginupgrade; gsbackup; asktype;;
	"s"|"S") gsbackup; asktype;;
	"q"|"Q") finish;;
	*) asklogin;;
esac
}

logininstall(){
echo "Deleting loginserver tables for new content."
$MYL < login_install.sql &> /dev/null
}

loginupgrade(){
echo "Installling new loginserver content."
$MYL < ../sql/accounts.sql &> /dev/null
$MYL < ../sql/gameservers.sql &> /dev/null
}

gsbackup(){
echo ""
echo "Making a backup of the original gameserver database."
$MYSQLDUMPPATH --add-drop-table -h $GSDBHOST -u $GSUSER --password=$GSPASS $GSDB > gameserver_backup.sql
if [ $? -ne 0 ];then
 echo ""
 echo "There was a problem accesing your GS database, either it wasnt created or authentication data is incorrect."
 exit 1
fi
}

lsbackup(){
echo ""
echo "Making a backup of the original loginserver database."
$MYSQLDUMPPATH --add-drop-table -h $LSDBHOST -u $LSUSER --password=$LSPASS $LSDB > loginserver_backup.sql
if [ $? -ne 0 ];then
 echo ""
 echo "There was a problem accesing your LS database, either it wasnt created or authentication data is incorrect."
 exit 1
fi
}

asktype(){
echo ""
echo ""
echo "WARNING: A full install (f) will destroy all existing character data."
echo -ne "GAMESERVER DB install type: (f) full install or (u) upgrade or (s) skip or (q) quit?"
read INSTALLTYPE
case "$INSTALLTYPE" in
	"f"|"F") fullinstall; upgradeinstall; experimental; expinstall;;
	"u"|"U") upgradeinstall; experimental; expinstall;;
	"s"|"S") experimental; expinstall;;
	"q"|"Q") finish;;
	*) asktype;;
esac
}

fullinstall(){
echo "Deleting all gameserver tables for new content."
$MYG < full_install.sql &> /dev/null
}

upgradeinstall(){
echo "Installling new gameserver content."
$MYG < ../sql/armor.sql &> /dev/null
$MYG < ../sql/boxaccess.sql &> /dev/null
$MYG < ../sql/boxes.sql &> /dev/null
$MYG < ../sql/castle.sql &> /dev/null
$MYG < ../sql/castle_door.sql &> /dev/null
$MYG < ../sql/castle_doorupgrade.sql &> /dev/null
$MYG < ../sql/castle_siege_guards.sql &> /dev/null
$MYG < ../sql/char_templates.sql &> /dev/null
$MYG < ../sql/character_friends.sql &> /dev/null
$MYG < ../sql/character_hennas.sql &> /dev/null
$MYG < ../sql/character_macroses.sql &> /dev/null
$MYG < ../sql/character_quests.sql &> /dev/null
$MYG < ../sql/character_recipebook.sql &> /dev/null
$MYG < ../sql/character_shortcuts.sql &> /dev/null
$MYG < ../sql/character_skills.sql &> /dev/null
$MYG < ../sql/character_skills_save.sql &> /dev/null
$MYG < ../sql/character_subclasses.sql &> /dev/null
$MYG < ../sql/characters.sql &> /dev/null
$MYG < ../sql/clan_data.sql &> /dev/null
$MYG < ../sql/clan_wars.sql &> /dev/null
$MYG < ../sql/class_list.sql &> /dev/null
$MYG < ../sql/droplist.sql &> /dev/null
$MYG < ../sql/etcitem.sql &> /dev/null
$MYG < ../sql/global_tasks.sql &> /dev/null
$MYG < ../sql/henna.sql &> /dev/null
$MYG < ../sql/henna_trees.sql &> /dev/null
$MYG < ../sql/items.sql &> /dev/null
$MYG < ../sql/locations.sql &> /dev/null
$MYG < ../sql/lvlupgain.sql &> /dev/null
$MYG < ../sql/mapregion.sql &> /dev/null
$MYG < ../sql/merchant_areas_list.sql &> /dev/null
$MYG < ../sql/merchant_buylists.sql &> /dev/null
$MYG < ../sql/merchant_lease.sql &> /dev/null
$MYG < ../sql/merchant_shopids.sql &> /dev/null
$MYG < ../sql/merchants.sql &> /dev/null
$MYG < ../sql/minions.sql &> /dev/null
$MYG < ../sql/npc.sql &> /dev/null
$MYG < ../sql/npcskills.sql &> /dev/null
$MYG < ../sql/pets.sql &> /dev/null
$MYG < ../sql/random_spawn.sql &> /dev/null
$MYG < ../sql/random_spawn_loc.sql &> /dev/null
$MYG < ../sql/seven_signs.sql &> /dev/null
$MYG < ../sql/seven_signs_festival.sql &> /dev/null
$MYG < ../sql/siege_clans.sql &> /dev/null
$MYG < ../sql/skill_learn.sql &> /dev/null
$MYG < ../sql/skill_spellbooks.sql &> /dev/null
$MYG < ../sql/skill_trees.sql &> /dev/null
$MYG < ../sql/spawnlist.sql &> /dev/null
$MYG < ../sql/teleport.sql &> /dev/null
$MYG < ../sql/weapon.sql &> /dev/null
$MYG < ../sql/zone.sql &> /dev/null
}

experimental(){
echo ""
echo ""
echo "WARNING: Installing experimental gameserver data (y) may change game balance."
echo -ne "Install experimental gameserver DB tables: (y) yes or (n) no or (q) quit?"
read ASKXP
case "$ASKXP" in
	"y"|"Y") expinstall;;
	"n"|"N") finish;;
	"q"|"Q") finish;;
	*) experimental;;
esac
}

expinstall(){
echo "Making a backup of the default gameserver tables."
$MYSQLDUMPPATH --add-drop-table -h $GSDBHOST -u $GSUSER --password=$GSPASS $LSDB > experimental_backup.sql &> /dev/null
echo "Installing new content."
$MYG < ../sql/experimental/locations.sql &> /dev/null
$MYG < ../sql/experimental/npc.sql &> /dev/null
$MYG < ../sql/experimental/npcskills.sql &> /dev/null
$MYG < ../sql/experimental/spawnlist-experimental.sql &> /dev/null
finish
}

finish(){
echo ""
echo "Script execution finished."
exit 0
}

clear
load_config
MYL="$MYSQLPATH -h $LSDBHOST -u $LSUSER --password=$LSPASS -D $LSDB"
MYG="$MYSQLPATH -h $GSDBHOST -u $GSUSER --password=$GSPASS -D $GSDB"
lsbackup
asklogin
