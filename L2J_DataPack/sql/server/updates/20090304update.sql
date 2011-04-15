ALTER TABLE `pets`
DROP `karma`,
DROP `pkkills`,
ADD `weapon` int(5) NOT NULL default 0 AFTER `fed`,
ADD `armor` int(5) NOT NULL default 0 AFTER `weapon`,
ADD `jewel` int(5) NOT NULL default 0 AFTER `armor`;