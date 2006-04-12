-- ---------------------------
-- Table structure for auction_watch
-- ---------------------------
DROP TABLE IF EXISTS auction_watch;
CREATE TABLE auction_watch (
  charObjId INT NOT NULL default 0,
  auctionId INT NOT NULL default 0,
  PRIMARY KEY  (charObjId, auctionId)
);
