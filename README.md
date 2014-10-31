	Copyright 2006 L2J-DataPack team

	This file is part of the L2J-DataPack.

    L2J-DataPack is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    L2J-DataPack is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with L2J-DataPack; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA


L2J-Datapack SVN Build:  

Project page: http://www.l2jdp.com
Forum: http://forum.l2jdp.com
Wiki: http://www.l2jdp.com/trac/wiki
Download: The wiki contains guides to get the latest datapack revision from the SVN repository.
IRC: irc.freenode.net #l2j-datapack

L2J-Datapack is *NOT* L2J. L2J is *NOT* L2J-Datapack.
Comments, questions, suggestions etc. should be directed to the appropriate forums.

This datapack is optimised for the current L2J SVN.

This readme assumes a basic understanding of MySQL and MySQL commands or familiarity with a MySQL frontend.
This readme will not teach you how to install MySQL nor will it teach you to use MySQL or any MySQL frontends.
This readme is for the sole purpose of providing a brief overview of how to either install or upgrade the data in your database.


Installation:

All users:
	Copy all content to your server dir, you know if you are doing it right if you are being asked if you want to overwrite the data folder, sellect yes to all at that stage.

For new L2J databases or existing databases where you want to delete character and account information:
	Create your loginserver and gameserver databases to match the ones in loginserver.properties and server.properties respectively (the default for both is l2jdb.)
	Method 1: Select your database and run all the batch scripts in the sql folder**
	Method 2: run database_installer.bat for windows users, or database_installer.sh for linux/unix users.

For existing L2J databases where you want to keep character and account information:
	Method 1: Select your database and run all the batch scripts in the sql folder** that correspond to tables in your database that are missing or you want to upgrade.
	Method 2: Run database_installer.bat for windows users, or database_installer.sh for linux/unix users.

IMPORTANT: 	There may also be changes to character data tables, to update these tables run the relevant batch scripts in /sql/updates/
			Files in /sql/updates/ have the following naming convention (L2J revision date and L2J revision number): YYMMDD-[REVISION].sql


**NOTE:	Read readme in the /sql/experimental/ folder.

-the l2j-datapack team

    L2JDP, Copyright (C) 2006 
    L2JDP comes with ABSOLUTELY NO WARRANTY.
    This is free software, and you are welcome to redistribute it
    under certain conditions.