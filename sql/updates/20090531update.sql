alter table `custom_etcitem` 
drop `html`,
add  `handler` varchar(70) NOT NULL DEFAULT 'none' after `tradeable`;