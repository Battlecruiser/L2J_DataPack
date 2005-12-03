--
-- Table structure for table `global_tasks`
--

CREATE TABLE global_tasks (
  task_name char(50) NOT NULL default '',
  last_activation decimal(20,0) NOT NULL default 0,
  PRIMARY KEY  (task_name)
);
