: level@  ( player -- level )
   "Level" p@
;

proof/Human/main
proof/Elf/main
proof/DarkElf/main
proof/Dwarf/main
proof/Orc/main

: mage?  ( player -- flag )
\ Return true if mage
	"ClassId" p@ "Id" p@
	{ 10 11 12 13 14 15 16 17 25 26 27 28 29 30 38 39 40 41 42 43 49 50 51 52 } in-list?
;
