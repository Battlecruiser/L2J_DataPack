--
-- Table structure for table `global_tasks`
--

CREATE TABLE global_tasks (
  task_name int(11) NOT NULL default 0,
  last_activation int(11) NOT NULL default 0,
  PRIMARY KEY  (task_name)
);
