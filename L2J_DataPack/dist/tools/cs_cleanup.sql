-- These queries are meant to delete any communityserver table you may have from
-- previous L2J installations.
--
-- Queries that create these tables and/or populate them apropiately are
-- available in the ../cb_sql/ folder. See the documentation.txt file
-- for more details.

DROP TABLE IF EXISTS
clan_introductions,
comments,
forums,
registered_gameservers,
posts,
topics;