DROP TABLE `merchant_buylists`;
DROP TABLE `merchant_shopids`;
ALTER TABLE `npcskills` DROP PRIMARY KEY, ADD PRIMARY KEY (`npcid`, `skillid`);
ALTER TABLE `custom_droplist` MODIFY `mobId` MEDIUMINT(7) UNSIGNED;
DROP TABLE `custom_merchant_buylists`;
DROP TABLE `custom_merchant_shopids`;
ALTER TABLE `custom_npc_buffer` MODIFY `npc_id` MEDIUMINT(7) UNSIGNED;
ALTER TABLE `custom_npcskills` DROP PRIMARY KEY, ADD PRIMARY KEY (`npcid`, `skillid`), MODIFY `npcid` MEDIUMINT(7) UNSIGNED;