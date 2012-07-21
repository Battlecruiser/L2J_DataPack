CREATE TABLE `accounts_ipauth` (
  `login`  varchar(45) NOT NULL ,
  `ip`  char(15) NOT NULL ,
  `type`  enum('deny','allow') NULL DEFAULT 'allow' 
);
