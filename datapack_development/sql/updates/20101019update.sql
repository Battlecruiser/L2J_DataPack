ALTER TABLE `custom_npc` MODIFY `sex` ENUM('etc','female','male') NOT NULL DEFAULT  'etc';
ALTER TABLE `custom_npc` MODIFY `hpreg` DECIMAL(10,4) NULL DEFAULT NULL;
ALTER TABLE `custom_npc` MODIFY `mpreg` DECIMAL(10,4) NULL DEFAULT NULL;
ALTER TABLE `custom_npc` ADD `targetable` TINYINT(1) NOT NULL DEFAULT '1' AFTER `runspd`;
ALTER TABLE `custom_npc` ADD `show_name`  TINYINT(1) NOT NULL DEFAULT '1' AFTER `targetable`;
ALTER TABLE `custom_npcaidata` CHANGE `canMove` `can_move` TINYINT(1) NOT NULL DEFAULT '1';

-- moar
ALTER TABLE `admin_command_access_rights` ADD `confirmDlg` enum('true','false') NOT NULL DEFAULT 'false' AFTER `accessLevels`;
UPDATE `admin_command_access_rights` SET `confirmDlg` = 'true' WHERE `adminCommand` IN 
('admin_reload','admin_manualhero','admin_ban_acc','admin_jail','admin_stopallbuffs','admin_give_item_target','admin_give_item_to_all',
'admin_cw_add','admin_kick','admin_server_shutdown','admin_server_restart','admin_recall_char_menu');