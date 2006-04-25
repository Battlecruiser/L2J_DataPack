-- ---------------------------
-- Table structure for auction
-- ---------------------------
CREATE TABLE IF NOT EXISTS auction (
  id INT NOT NULL default 0,
  sellerId INT NOT NULL default 0,
  sellerName varchar(50) NOT NULL default 'NPC',
  itemType varchar(25) NOT NULL,
  itemId INT NOT NULL default 0,
  itemObjectId INT NOT NULL default 0,
  itemName varchar(40) NOT NULL,
  itemQuantity INT NOT NULL default 0,
  startingBid int(11) NOT NULL default 0,
  currentBid int(11) NOT NULL default 0,
  endDate DECIMAL(20,0) NOT NULL default 0,
  PRIMARY KEY  (itemType, itemId, itemObjectId),
  KEY id (id)
);
