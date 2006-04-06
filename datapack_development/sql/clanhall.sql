-- ---------------------------
-- Table structure for clanhall
-- ---------------------------
DROP TABLE IF EXISTS clanhall;
CREATE TABLE clanhall (
  id INT NOT NULL default 0,
  name varchar(40) NOT NULL,
  ownerId INT NOT NULL default 0,
  PRIMARY KEY  (name),
  KEY id (id)
);

insert into clanhall values (1, 'Gludio 1', 0);
insert into clanhall values (2, 'Gludio 2', 0);
insert into clanhall values (3, 'Gludio 3', 0);
insert into clanhall values (4, 'Gludio 4', 0);
insert into clanhall values (5, 'Gludin 1', 0);
insert into clanhall values (6, 'Gludin 2', 0);
insert into clanhall values (7, 'Gludin 3', 0);
insert into clanhall values (8, 'Gludin 4', 0);
insert into clanhall values (9, 'Gludin 5', 0);
insert into clanhall values (10, 'Dion 1', 0);
insert into clanhall values (11, 'Dion 2', 0);
insert into clanhall values (12, 'Dion 3', 0);
insert into clanhall values (13, 'Giran 1', 0);
insert into clanhall values (14, 'Giran 2', 0);
insert into clanhall values (15, 'Giran 3', 0);
insert into clanhall values (16, 'Giran 4', 0);
insert into clanhall values (17, 'Giran 5', 0);
insert into clanhall values (18, 'Aden 1', 0);
insert into clanhall values (19, 'Aden 2', 0);
insert into clanhall values (20, 'Aden 3', 0);
insert into clanhall values (21, 'Aden 4', 0);
insert into clanhall values (22, 'Aden 5', 0);
insert into clanhall values (23, 'Aden 6', 0);
insert into clanhall values (24, 'Goddard 1', 0);
insert into clanhall values (25, 'Goddard 2', 0);
insert into clanhall values (26, 'Goddard 3', 0);
insert into clanhall values (27, 'Goddard 4', 0);
insert into clanhall values (28, 'Bandits Stronghold', 0);
insert into clanhall values (29, 'Partisan Hideaway', 0);
insert into clanhall values (30, 'Hot Springs Guild House', 0);