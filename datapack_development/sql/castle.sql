-- ---------------------------
-- Table structure for castle
-- ---------------------------
CREATE TABLE castle (
  id INT NOT NULL default 0,
  name varchar(25) NOT NULL,
  taxPercent INT NOT NULL default 15,
  treasury INT NOT NULL default 0,
  siegeDate DECIMAL(20,0) NOT NULL default 0,
  PRIMARY KEY  (name),
  KEY id (id)
);

Insert Into castle Values (1, 'Gludio', 15, 0, 0);
Insert Into castle Values (2, 'Dion', 15, 0, 0);
Insert Into castle Values (3, 'Giran', 15, 0, 0);
Insert Into castle Values (4, 'Oren', 15, 0, 0);
Insert Into castle Values (5, 'Aden', 15, 0, 0);
Insert Into castle Values (6, 'Innadril', 15, 0, 0);
