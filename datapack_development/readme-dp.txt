	Copyright 2005 L2J-DataPack team

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


L2J-Datapack version 3 dev/unstable:

Sourceforge project page: http://sourceforge.net/projects/l2j-c2datapack
Forum: http://l2j-c2datapack.sourceforge.net/bb/index.php
Wiki: https://opensvn.csie.org/traccgi/L2J_Datapack/trac.cgi
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
	Copy all content to your server dir, you know if you are doing it right if you are being asked 
	if you want to overwrite the data folder, sellect yes to all at that stage.

For new L2J databases or existing databases where you want to delete character and account information:
	Create a database to match the one in your L2J server.cfg (the default is l2jdb)
	Select your database
	Now run all the batch scripts in the sql folder**
	Or just run the full_install.bat for windows users, or the full_install.sh for linux/unix users.

For existing L2J databases where you want to keep character and account information:
	Select your database
	Run upgrade.sql in the tools folder
	Now run all the batch scripts in the sql folder** that correspond to tables that no longer exist in your database.
	Or just run the upgrade.bat for windows users, or the upgrade.sh for linux/unix users.
		IMPORTANT: 	There may also be changes to character data tables, to update these tables run the relevant batch scripts in /sql/updates/
					Files in /sql/updates/ have the following naming convention (L2J revision date and L2J revision number): YYMMDD-[REVISION].sql


**NOTE:	There may be multiple sql files for the same table, they are often mutually exclusive, that is you must choose one or the other, not both.
		Usually the default file will have the same name as the table and be listed in the tools files.

-the l2j-datapack team