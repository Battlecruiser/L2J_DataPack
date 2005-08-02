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
Download: The forum contains a post linking to the latest build from the SVN repository.
IRC: irc.freenode.net #l2j-datapack

L2J-Datapack is *NOT* L2J. L2J is NOT L2J-Datapack.
Comments, questions, suggestions etc. should be directed to the appropriate forums.

This datapack is optimised for the current L2J SVN.

This readme assumes a basic understanding of MySQL and MySQL commands or familiarity with a MySQL frontend.
This readme will not teach you how to install MySQL nor will it teach you to use MySQL or any MySQL frontends.
This readme is for the sole purpose of providing a brief overview of how to either install or upgrade the data in your database.


Installation:

All users:
Copy all content to your server dir, you know if you are doing it right if you are being asked 
if you want to overwrite the data folder, sellect yes to all at that stage.

For new L2J databases:
Create a database to match the one in your L2J server.cfg (the default is l2jdb)
Select your database
Now run all the batch scripts in the sql folder*
Or just run the full_Install.bat for windows users, or the full_install.sh for linux/unix users.

For existing L2J databases where you want to keep character and account information:
Select your database
Run upgrade.sql in the tools folder
Now run all the batch scripts in the sql folder* that correspond to tables that no longer exist in your database.
Or just run the upgrade.bat for windows users, or the upgrade.sh for linux/unix users.

For existing databases where you want to delete character and account information:
Select your database
Run newinstall.sql in the tools folder
Now run all the batch scripts in the sql folder*
Or just run the full_Install.bat for windows users, or the full_install.sh for linux/unix users.

Spawnlist Viewer:
Just run the spawnviewer.bat, the file is located in the same folder as the startserver.bat.

*NOTES:	spawnlist-c2.sql is optional (it contains developmental C2/C3 spawns - some aren't checked, these are noted read the comments *in* the file spawnlist-c2)
		if you want these spawns, spawnlist-c2.sql must be run *after* spawnlist.sql
		spawnlist-sexy.sql is optional it uses the new spawnlist functions, you must run either this file *or* spawnlist.sql (optionally with spawnlist-c2), not both
		skill_trees-c2.sql contains c2 skills, you must run either this file *or* skill_trees.sql, not both
		merchant_buylists_with_tax.sql is as the name suggests you must run either this file *or* merchant_buylists.sql, not both
		to buff/unbuff mobs execute this query where stat is the stat you want to modify (hp/mp/pdef/mdef/patk etc) and mod is the mod value (.5,1.5,2 etc):
		update npc set stat=stat*mod;
		for example to set all mob hp to 60% of current values do the following: update npc set hp=hp*.6;

		DELETE YOUR BUYLIST.CSV IF YOU ARE NOT USING IT.

-the l2j-datapack team