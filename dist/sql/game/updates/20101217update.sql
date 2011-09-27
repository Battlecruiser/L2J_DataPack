ALTER TABLE `admin_command_access_rights` MODIFY `accessLevels` tinyint(3) NOT NULL DEFAULT '1';
ALTER TABLE `admin_command_access_rights` ADD `confirmDlg` enum('true','false') NOT NULL DEFAULT 'false' AFTER `accessLevels`;
UPDATE `admin_command_access_rights` SET `confirmDlg`='true' WHERE `adminCommand` IN (
'admin_reload',
'admin_manualhero',
'admin_endolympiad',
'admin_ban_acc',
'admin_jail',
'admin_stopallbuffs',
'admin_clan_changeleader',
'admin_give_item_target',
'admin_give_item_to_all',
'admin_cw_add',
'admin_kick',
'admin_recall_char_menu',
'admin_server_shutdown',
'admin_server_restart'
);