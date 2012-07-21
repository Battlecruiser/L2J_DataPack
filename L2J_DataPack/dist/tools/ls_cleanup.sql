-- These queries are meant to delete any loginserver table you may have from
-- previous L2J installations.
--
-- Queries that create these tables and/or populate them apropiately are
-- available in the ../sql/ folder. See the documentation.txt file
-- for more details.

DROP TABLE IF EXISTS
account_data,
accounts,
accounts_ipauth,
gameservers;