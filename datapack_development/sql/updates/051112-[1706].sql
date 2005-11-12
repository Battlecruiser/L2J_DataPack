-- needed only if your charater tables doesn't contains 'deletetime' already
alter table `characters` add column `deletetime` DECIMAL(20,0) NOT NULL default 0;