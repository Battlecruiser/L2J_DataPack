-- ---------------------------
-- Table structure for accounts
-- ---------------------------
CREATE TABLE accounts (
  login varchar(45) ,
  password varchar(45) ,
  lastactive decimal(20) ,
  access_level decimal(11) ,
  lastIP varchar (20),
  PRIMARY KEY  (login)
) ;
