ALTER TABLE `olympiad_nobles` ADD `competitions_done_week` decimal(3,0) NOT NULL DEFAULT 0 AFTER `competitions_drawn`;
ALTER TABLE `olympiad_nobles` ADD `competitions_done_week_classed` decimal(3,0) NOT NULL DEFAULT 0 AFTER `competitions_done_week`;
ALTER TABLE `olympiad_nobles` ADD `competitions_done_week_non_classed` decimal(3,0) NOT NULL DEFAULT 0 AFTER `competitions_done_week_classed`;
ALTER TABLE `olympiad_nobles` ADD `competitions_done_week_team` decimal(3,0) NOT NULL DEFAULT 0 AFTER `competitions_done_week_non_classed`;