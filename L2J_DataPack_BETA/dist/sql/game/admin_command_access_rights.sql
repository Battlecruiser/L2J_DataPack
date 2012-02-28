-- For further information on the usage of this table, please refer to the
-- documentation comments in the access_levels.sql file
CREATE TABLE IF NOT EXISTS `admin_command_access_rights` (
  `adminCommand` varchar(255) NOT NULL DEFAULT 'admin_',
  `accessLevels` tinyint(3) NOT NULL DEFAULT '1',
  `confirmDlg` enum('true','false') NOT NULL DEFAULT 'false',
  PRIMARY KEY (`adminCommand`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `admin_command_access_rights` VALUES
-- ADMIN ADMIN
('admin_admin',1,'false'),
('admin_admin1',1,'false'),
('admin_admin2',1,'false'),
('admin_admin3',1,'false'),
('admin_admin4',1,'false'),
('admin_admin5',1,'false'),
('admin_admin6',1,'false'),
('admin_admin7',1,'false'),
('admin_admin8',1,'false'),
('admin_gmliston',1,'false'),
('admin_gmlistoff',1,'false'),
('admin_silence',1,'false'),
('admin_diet',1,'false'),
('admin_tradeoff',1,'false'),
('admin_reload',1,'true'),
('admin_set',1,'false'),
('admin_set_mod',1,'false'),
('admin_saveolymp',1,'false'),
('admin_manualhero',1,'true'),
('admin_sethero',1,'false'),
('admin_endolympiad',1,'true'),
('admin_setconfig',1,'false'),
('admin_config_server',1,'false'),
('admin_gmon',1,'false'),

-- ADMIN ANNOUNCEMENTS
('admin_list_announcements',1,'false'),
('admin_list_critannouncements',1,'false'),
('admin_reload_announcements',1,'false'),
('admin_announce_announcements',1,'false'),
('admin_add_announcement',1,'false'),
('admin_del_announcement',1,'false'),
('admin_add_critannouncement',1,'false'),
('admin_del_critannouncement',1,'false'),
('admin_announce',1,'false'),
('admin_critannounce',1,'false'),
('admin_announce_menu',1,'false'),
('admin_critannounce_menu',1,'false'),
('admin_list_autoann',1,'false'),
('admin_reload_autoann',1,'false'),
('admin_add_autoann',1,'false'),
('admin_del_autoann',1,'false'),

-- ADMIN BAN
('admin_ban',1,'false'),
('admin_ban_acc',1,'true'),
('admin_ban_char',1,'false'),
('admin_ban_chat',1,'false'),
('admin_unban',1,'false'),
('admin_unban_acc',1,'false'),
('admin_unban_char',1,'false'),
('admin_unban_chat',1,'false'),
('admin_jail',1,'true'),
('admin_unjail',1,'false'),

-- ADMIN BBS
('admin_bbs',1,'false'),

-- ADMIN BUFFS
('admin_getbuffs',1,'false'),
('admin_stopbuff',1,'false'),
('admin_stopallbuffs',1,'true'),
('admin_areacancel',1,'false'),
('admin_removereuse',1,'false'),
('admin_switch_gm_buffs',1,'false'),

-- ADMIN CACHE
('admin_cache_htm_rebuild',1,'false'),
('admin_cache_htm_reload',1,'false'),
('admin_cache_reload_path',1,'false'),
('admin_cache_reload_file',1,'false'),
('admin_cache_crest_rebuild',1,'false'),
('admin_cache_crest_reload',1,'false'),
('admin_cache_crest_fix',1,'false'),

-- ADMIN CAMERA
('admin_camera',1,'false'),

-- ADMIN CHANGE ACCESS LEVEL
('admin_changelvl',1,'false'),

-- ADMIN CLAN
('admin_clan_info',1,'false'),
('admin_clan_changeleader',1,'true'),

-- ADMIN CREATE ITEM
('admin_itemcreate',1,'false'),
('admin_create_item',1,'false'),
('admin_create_coin',1,'false'),
('admin_give_item_target',1,'true'),
('admin_give_item_to_all',1,'true'),

-- ADMIN CURSED WEAPONS
('admin_cw_info',1,'false'),
('admin_cw_remove',1,'false'),
('admin_cw_goto',1,'false'),
('admin_cw_reload',1,'false'),
('admin_cw_add',1,'true'),
('admin_cw_info_menu',1,'false'),

-- ADMIN DEBUG
('admin_debug',1,'false'),

-- ADMIN DELETE
('admin_delete',1,'false'),

-- ADMIN DISCONNECT
('admin_character_disconnect',1,'false'),

-- ADMIN DOOR CONTROL
('admin_open',1,'false'),
('admin_close',1,'false'),
('admin_openall',1,'false'),
('admin_closeall',1,'false'),

-- ADMIN EDIT CHAR
('admin_edit_character',1,'false'),
('admin_current_player',1,'false'),
('admin_nokarma',1,'false'),
('admin_setkarma',1,'false'),
('admin_setfame',1,'false'),
('admin_character_list',1,'false'),
('admin_character_info',1,'false'),
('admin_show_characters',1,'false'),
('admin_find_character',1,'false'),
('admin_find_ip',1,'false'),
('admin_find_account',1,'false'),
('admin_find_dualbox',1,'false'),
('admin_strict_find_dualbox',1,'false'),
('admin_tracert',1,'false'),
('admin_save_modifications',1,'false'),
('admin_rec',1,'false'),
('admin_settitle',1,'false'),
('admin_changename',1,'false'),
('admin_setsex',1,'false'),
('admin_setcolor',1,'false'),
('admin_settcolor',1,'false'),
('admin_setclass',1,'false'),
('admin_setpk',1,'false'),
('admin_setpvp',1,'false'),
('admin_fullfood',1,'false'),
('admin_remove_clan_penalty',1,'false'),
('admin_summon_info',1,'false'),
('admin_unsummon',1,'false'),
('admin_summon_setlvl',1,'false'),
('admin_show_pet_inv',1,'false'),
('admin_partyinfo',1,'false'),
('admin_setnoble',1,'true'),

-- ADMIN EDIT NPC
('admin_edit_npc',1,'false'),
('admin_save_npc',1,'false'),
('admin_show_droplist',1,'false'),
('admin_edit_drop',1,'false'),
('admin_add_drop',1,'false'),
('admin_del_drop',1,'false'),
('admin_showShop',1,'false'),
('admin_showShopList',1,'false'),
('admin_addShopItem',1,'false'),
('admin_delShopItem',1,'false'),
('admin_editShopItem',1,'false'),
('admin_close_window',1,'false'),
('admin_show_skilllist_npc',1,'false'),
('admin_add_skill_npc',1,'false'),
('admin_edit_skill_npc',1,'false'),
('admin_del_skill_npc',1,'false'),
('admin_log_npc_spawn',1,'false'),

-- ADMIN EFFECTS
('admin_invis',1,'false'),
('admin_invisible',1,'false'),
('admin_vis',1,'false'),
('admin_visible',1,'false'),
('admin_invis_menu',1,'false'),
('admin_earthquake',1,'false'),
('admin_earthquake_menu',1,'false'),
('admin_bighead',1,'false'),
('admin_shrinkhead',1,'false'),
('admin_gmspeed',1,'false'),
('admin_gmspeed_menu',1,'false'),
('admin_unpara_all',1,'false'),
('admin_para_all',1,'false'),
('admin_unpara',1,'false'),
('admin_para',1,'false'),
('admin_unpara_all_menu',1,'false'),
('admin_para_all_menu',1,'false'),
('admin_unpara_menu',1,'false'),
('admin_para_menu',1,'false'),
('admin_polyself',1,'false'),
('admin_unpolyself',1,'false'),
('admin_polyself_menu',1,'false'),
('admin_unpolyself_menu',1,'false'),
('admin_clearteams',1,'false'),
('admin_setteam_close',1,'false'),
('admin_setteam',1,'false'),
('admin_social',1,'false'),
('admin_effect',1,'false'),
('admin_social_menu',1,'false'),
('admin_special',1,'false'),
('admin_special_menu',1,'false'),
('admin_effect_menu',1,'false'),
('admin_abnormal',1,'false'),
('admin_abnormal_menu',1,'false'),
('admin_play_sounds',1,'false'),
('admin_play_sound',1,'false'),
('admin_atmosphere',1,'false'),
('admin_atmosphere_menu',1,'false'),
('admin_set_displayeffect',1,'false'),
('admin_set_displayeffect_menu',1,'false'),

-- ADMIN ELEMENT
('admin_setlh',1,'false'),
('admin_setlc',1,'false'),
('admin_setll',1,'false'),
('admin_setlg',1,'false'),
('admin_setlb',1,'false'),
('admin_setlw',1,'false'),
('admin_setls',1,'false'),

-- ADMIN ENCHANT
('admin_seteh',1,'false'),
('admin_setec',1,'false'),
('admin_seteg',1,'false'),
('admin_setel',1,'false'),
('admin_seteb',1,'false'),
('admin_setew',1,'false'),
('admin_setes',1,'false'),
('admin_setle',1,'false'),
('admin_setre',1,'false'),
('admin_setlf',1,'false'),
('admin_setrf',1,'false'),
('admin_seten',1,'false'),
('admin_setun',1,'false'),
('admin_setba',1,'false'),
('admin_setbe',1,'false'),
('admin_enchant',1,'false'),

-- ADMIN EVENT ENGINE
('admin_event',1,'false'),
('admin_event_new',1,'false'),
('admin_event_choose',1,'false'),
('admin_event_store',1,'false'),
('admin_event_set',1,'false'),
('admin_event_change_teams_number',1,'false'),
('admin_event_announce',1,'false'),
('admin_event_panel',1,'false'),
('admin_event_control_begin',1,'false'),
('admin_event_control_teleport',1,'false'),
('admin_add',1,'false'),
('admin_event_see',1,'false'),
('admin_event_del',1,'false'),
('admin_delete_buffer',1,'false'),
('admin_event_control_sit',1,'false'),
('admin_event_name',1,'false'),
('admin_event_control_kill',1,'false'),
('admin_event_control_res',1,'false'),
('admin_event_control_poly',1,'false'),
('admin_event_control_unpoly',1,'false'),
('admin_event_control_prize',1,'false'),
('admin_event_control_chatban',1,'false'),
('admin_event_control_finish',1,'false'),

-- ADMIN EVENTS
('admin_event_menu',1,'false'),
('admin_event_start',1,'false'),
('admin_event_stop',1,'false'),
('admin_event_start_menu',1,'false'),
('admin_event_stop_menu',1,'false'),
('admin_event_bypass',1,'false'),

-- ADMIN EXP SP
('admin_add_exp_sp_to_character',1,'false'),
('admin_add_exp_sp',1,'false'),
('admin_remove_exp_sp',1,'false'),

-- ADMIN FIGHT CALCULATOR
('admin_fight_calculator',1,'false'),
('admin_fight_calculator_show',1,'false'),
('admin_fcs',1,'false'),

-- ADMIN FORT SIEGE
('admin_fortsiege',1,'false'),
('admin_add_fortattacker',1,'false'),
('admin_list_fortsiege_clans',1,'false'),
('admin_clear_fortsiege_list',1,'false'),
('admin_spawn_fortdoors',1,'false'),
('admin_endfortsiege',1,'false'),
('admin_startfortsiege',1,'false'),
('admin_setfort',1,'false'),
('admin_removefort',1,'false'),

-- ADMIN GEODATA
('admin_geo_z',1,'false'),
('admin_geo_type',1,'false'),
('admin_geo_nswe',1,'false'),
('admin_geo_los',1,'false'),
('admin_geo_position',1,'false'),
('admin_geo_bug',1,'false'),
('admin_geo_load',1,'false'),
('admin_geo_unload',1,'false'),

-- ADMIN GEO EDITOR
('admin_ge_status',1,'false'),
('admin_ge_mode',1,'false'),
('admin_ge_join',1,'false'),
('admin_ge_leave',1,'false'),

-- ADMIN GM
('admin_gm',1,'false'),

-- ADMIN GM CHAT
('admin_gmchat',1,'false'),
('admin_snoop',1,'false'),
('admin_gmchat_menu',1,'false'),

-- ADMIN GRACIA SEEDS
('admin_gracia_seeds',1,'false'),
('admin_kill_tiat',1,'false'),
('admin_set_sodstate',1,'false'),

-- ADMIN HEAL
('admin_heal',1,'false'),

-- HELLBOUND
('admin_hellbound_setlevel',1,'false'),
('admin_hellbound',1,'false'),

-- ADMIN HELP PAGE
('admin_help',1,'false'),

-- ADMIN INSTANCE
('admin_setinstance',1,'false'),
('admin_ghoston',1,'false'),
('admin_ghostoff',1,'false'),
('admin_createinstance',1,'false'),
('admin_destroyinstance',1,'false'),
('admin_listinstances',1,'false'),

-- ADMIN INSTANCE ZONE
('admin_instancezone',1,'false'),
('admin_instancezone_clear',1,'false'),

-- ADMIN INVUL
('admin_invul',1,'false'),
('admin_setinvul',1,'false'),

-- ADMIN KICK
('admin_kick',1,'true'),
('admin_kick_non_gm',1,'false'),

-- ADMIN KILL
('admin_kill',1,'false'),
('admin_kill_monster',1,'false'),

-- ADMIN LEVEL
('admin_add_level',1,'false'),
('admin_set_level',1,'false'),

-- ADMIN LOGIN
('admin_server_gm_only',1,'false'),
('admin_server_all',1,'false'),
('admin_server_max_player',1,'false'),
('admin_server_list_type',1,'false'),
('admin_server_list_age',1,'false'),
('admin_server_login',1,'false'),

-- ADMIN MAMMON
('admin_mammon_find',1,'false'),
('admin_mammon_respawn',1,'false'),

-- ADMIN MANOR
('admin_manor',1,'false'),
('admin_manor_approve',1,'false'),
('admin_manor_setnext',1,'false'),
('admin_manor_reset',1,'false'),
('admin_manor_setmaintenance',1,'false'),
('admin_manor_save',1,'false'),
('admin_manor_disable',1,'false'),

-- ADMIN MENU
('admin_char_manage',1,'false'),
('admin_teleport_character_to_menu',1,'false'),
('admin_recall_char_menu',1,'true'),
('admin_recall_party_menu',1,'false'),
('admin_recall_clan_menu',1,'false'),
('admin_goto_char_menu',1,'false'),
('admin_kick_menu',1,'false'),
('admin_kill_menu',1,'false'),
('admin_ban_menu',1,'false'),
('admin_unban_menu',1,'false'),

-- ADMIN MESSAGES
('admin_msg',1,'false'),

-- ADMIN MOB GROUP
('admin_mobmenu',1,'false'),
('admin_mobgroup_list',1,'false'),
('admin_mobgroup_create',1,'false'),
('admin_mobgroup_remove',1,'false'),
('admin_mobgroup_delete',1,'false'),
('admin_mobgroup_spawn',1,'false'),
('admin_mobgroup_unspawn',1,'false'),
('admin_mobgroup_kill',1,'false'),
('admin_mobgroup_idle',1,'false'),
('admin_mobgroup_attack',1,'false'),
('admin_mobgroup_rnd',1,'false'),
('admin_mobgroup_return',1,'false'),
('admin_mobgroup_follow',1,'false'),
('admin_mobgroup_casting',1,'false'),
('admin_mobgroup_nomove',1,'false'),
('admin_mobgroup_attackgrp',1,'false'),
('admin_mobgroup_invul',1,'false'),

-- ADMIN MONSTER RACE
('admin_mons',1,'false'),

-- ADMIN PATH NODE
('admin_pn_info',1,'false'),
('admin_show_path',1,'false'),
('admin_path_debug',1,'false'),
('admin_show_pn',1,'false'),
('admin_find_path',1,'false'),

-- ADMIN PETITION
('admin_view_petitions',1,'false'),
('admin_view_petition',1,'false'),
('admin_accept_petition',1,'false'),
('admin_reject_petition',1,'false'),
('admin_reset_petitions',1,'false'),
('admin_force_peti',1,'false'),

-- ADMIN P FORGE
('admin_forge',1,'false'),
('admin_forge2',1,'false'),
('admin_forge3',1,'false'),

-- ADMIN PLEDGE
('admin_pledge',1,'false'),

-- ADMIN POLYMORPH
('admin_polymorph',1,'false'),
('admin_unpolymorph',1,'false'),
('admin_polymorph_menu',1,'false'),
('admin_unpolymorph_menu',1,'false'),
('admin_transform',1,'false'),
('admin_untransform',1,'false'),
('admin_transform_menu',1,'false'),
('admin_untransform_menu',1,'false'),

-- ADMIN QUEST
('admin_quest_reload',1,'false'),
('admin_script_load',1,'false'),

-- ADMIN REPAIR CHAR
('admin_restore',1,'false'),
('admin_repair',1,'false'),

-- ADMIN RES
('admin_res',1,'false'),
('admin_res_monster',1,'false'),

-- ADMIN RIDE
('admin_ride_horse',1,'false'),
('admin_ride_bike',1,'false'),
('admin_ride_wyvern',1,'false'),
('admin_ride_strider',1,'false'),
('admin_unride_wyvern',1,'false'),
('admin_unride_strider',1,'false'),
('admin_unride',1,'false'),
('admin_ride_wolf',1,'false'),
('admin_unride_wolf',1,'false'),

-- ADMIN SHOP
('admin_buy',1,'false'),
('admin_gmshop',1,'false'),

-- ADMIN SHOW QUEST
('admin_charquestmenu',1,'false'),
('admin_setcharquest',1,'false'),

-- ADMIN SHUTDOWN
('admin_server_shutdown',1,'true'),
('admin_server_restart',1,'true'),
('admin_server_abort',1,'false'),

-- ADMIN SIEGE
('admin_siege',1,'false'),
('admin_add_attacker',1,'false'),
('admin_add_defender',1,'false'),
('admin_add_guard',1,'false'),
('admin_list_siege_clans',1,'false'),
('admin_clear_siege_list',1,'false'),
('admin_move_defenders',1,'false'),
('admin_spawn_doors',1,'false'),
('admin_endsiege',1,'false'),
('admin_startsiege',1,'false'),
('admin_setsiegetime',1,'false'),
('admin_setcastle',1,'false'),
('admin_removecastle',1,'false'),
('admin_clanhall',1,'false'),
('admin_clanhallset',1,'false'),
('admin_clanhalldel',1,'false'),
('admin_clanhallopendoors',1,'false'),
('admin_clanhallclosedoors',1,'false'),
('admin_clanhallteleportself',1,'false'),

-- ADMIN SKILL
('admin_show_skills',1,'false'),
('admin_remove_skills',1,'false'),
('admin_skill_list',1,'false'),
('admin_skill_index',1,'false'),
('admin_add_skill',1,'false'),
('admin_remove_skill',1,'false'),
('admin_get_skills',1,'false'),
('admin_reset_skills',1,'false'),
('admin_give_all_skills',1,'false'),
('admin_give_all_skills_fs',1,'false'),
('admin_give_all_clan_skills,1,'false'),
('admin_remove_all_skills',1,'false'),
('admin_add_clan_skill',1,'false'),
('admin_setskill',1,'false'),

-- ADMIN SPAWN
('admin_show_spawns',1,'false'),
('admin_spawn',1,'false'),
('admin_spawn_monster',1,'false'),
('admin_spawn_index',1,'false'),
('admin_unspawnall',1,'false'),
('admin_respawnall',1,'false'),
('admin_spawn_reload',1,'false'),
('admin_npc_index',1,'false'),
('admin_spawn_once',1,'false'),
('admin_show_npcs',1,'false'),
('admin_teleport_reload',1,'false'),
('admin_spawnnight',1,'false'),
('admin_spawnday',1,'false'),
('admin_instance_spawns',1,'false'),
('admin_list_spawns',1,'false'),
('admin_list_positions',1,'false'),
('admin_spawn_debug_menu',1,'false'),
('admin_spawn_debug_print',1,'false'),
('admin_spawn_debug_print_menu',1,'false'),

-- ADMIN SUMMON
('admin_summon',1,'false'),

-- ADMIN TARGET
('admin_target',1,'false'),

-- ADMIN TARGETSAY
('admin_targetsay',1,'false'),

-- ADMIN TELEPORT
('admin_show_moves',1,'false'),
('admin_show_moves_other',1,'false'),
('admin_show_teleport',1,'false'),
('admin_teleport_to_character',1,'false'),
('admin_teleportto',1,'false'),
('admin_move_to',1,'false'),
('admin_teleport_character',1,'false'),
('admin_recall',1,'false'),
('admin_walk',1,'false'),
('admin_explore',1,'false'),
('teleportto',1,'false'),
('recall',1,'false'),
('admin_recall_npc',1,'false'),
('admin_gonorth',1,'false'),
('admin_gosouth',1,'false'),
('admin_goeast',1,'false'),
('admin_gowest',1,'false'),
('admin_goup',1,'false'),
('admin_godown',1,'false'),
('admin_tele',1,'false'),
('admin_teleto',1,'false'),
('admin_instant_move',1,'false'),
('admin_sendhome',1,'true'),

-- ADMIN TERRITORY WAR
('admin_territory_war',1,'false'),
('admin_territory_war_time',1,'false'),
('admin_territory_war_start',1,'false'),
('admin_territory_war_end',1,'false'),
('admin_territory_wards_list',1,'false'),

-- ADMIN TEST
('admin_test',1,'false'),
('admin_stats',1,'false'),
('admin_skill_test',1,'false'),
('admin_st',1,'false'),
('admin_mp',1,'false'),
('admin_known',1,'false'),

-- ADMIN TVT EVENT
('admin_tvt_add',1,'false'),
('admin_tvt_remove',1,'false'),
('admin_tvt_advance',1,'false'),

-- ADMIN UNBLOCK IP
('admin_unblockip',1,'false'),

-- ADMIN VITALITY
('admin_set_vitality',1,'false'),
('admin_set_vitality_level',1,'false'),
('admin_full_vitality',1,'false'),
('admin_empty_vitality',1,'false'),
('admin_get_vitality',1,'false'),

-- ADMIN ZONE
('admin_zone_check',1,'false'),
('admin_zone_reload',1,'false'),
('admin_zone_visual',1,'false'),
('admin_zone_visual_clear',1,'false'),

-- VOICE COMMANDS
('banchat', 7,'false'),
('debug',1,'false'),
('unbanchat', 7,'false'),

-- SIEGABLE HALLS
('admin_chsiege_siegablehall', 1, 'false'),
('admin_chsiege_startSiege', 1, 'false'),
('admin_chsiege_endsSiege', 1, 'false'),
('admin_chsiege_setSiegeDate', 1, 'false'),
('admin_chsiege_addAttacker', 1, 'false'),
('admin_chsiege_removeAttacker', 1, 'false'),
('admin_chsiege_listAttackers', 1, 'false'),
('admin_chsiege_clearAttackers', 1, 'false'),
('admin_chsiege_forwardSiege', 1, 'false');