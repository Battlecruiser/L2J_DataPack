# Engine written by Advi (and what a lovely engine it is ^^)
# Mammon added by mr
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver import ItemTable


############################## Feel Free to add more Weapons ##########################################################################################################3

# Weapon exchangeable definition  WeaponID:[Name, Icon]]
ExchangeList={
# Bows D grade
274:["Strengthened Bow", "weapon_strengthening_bow_i0"],
275:["Long Bow", "weapon_strengthening_bow_i0"],
276:["Elven Bow", "weapon_elven_bow_i0"],
277:["Dark Elven Bow", "weapon_dark_elven_bow_i0"],
278:["Gastraphetes", "weapon_gastraphetes_i0"],
279:["Strengthened Long Bow", "weapon_strengthening_long_bow_i0"],
280:["Light Crossbow", "weapon_cyclone_bow_i0"],
# Bows C grade
281:["Crystallized Ice Bow", "weapon_crystallized_ice_bow_i0"],
282:["Elemental Bow", "weapon_elemental_bow_i0"],
283:["Akat Long Bow", "weapon_akat_long_bow_i0"],
285:["Noble Elven Bow", "weapon_noble_elven_bow_i0"],
286:["Eminence Bow", "weapon_eminence_bow_i0"],
# Swords D grade
69:["Bastard Sword", "weapon_bastard_sword_i0"],
70:["Claymore", "weapon_claymore_i0"],
83:["Sword of Magic", "weapon_sword_of_magic_i0"],
123:["Saber", "weapon_saber_i0"],
124:["Two-Handed Sword", "weapon_two_handed_sword_i0"],
125:["Spinebone Sword", "weapon_spinebone_sword_i0"],
126:["Artisan's Sword", "weapon_artisans_sword_i0"],
127:["Crimson Sword", "weapon_crimson_sword_i0"],
128:["Knight's Sword", "weapon_knights_sword_i0"],
129:["Sword of Revolution", "weapon_sword_of_revolution_i0"],
130:["Elven Sword", "weapon_elven_sword_i0"],
143:["Sword of Mystic", "weapon_sword_of_mystic_i0"],
144:["Sword of Occult", "weapon_sword_of_occult_i0"],
2499:["Elven Long Sword", "weapon_elven_long_sword_i0"],
5285:["Heavy Sword", "weapon_heavy_sword_i0"],
# Swords C grade
71:["Flamberge", "weapon_flamberge_i0"],
72:["Stormbringer", "weapon_stormbringer_i0"],
73:["Shamshir", "weapon_shamshir_i0"],
74:["Katana", "weapon_katana_i0"],
75:["Caliburs", "weapon_caliburs_i0"],
76:["Sword of Delusion", "weapon_sword_of_delusion_i0"],
77: ["Tsurugi", "weapon_tsurugi_i0"],
84:["Homunkulus's Sword", "weapon_homunkuluss_sword_i0"],
131:["Spirit Sword", "weapon_spirits_sword_i0"],
132:["Sword of Limit", "weapon_sword_of_limit_i0"],
133:["Raid Sword", "weapon_raid_sword_i0"],
134:["Sword of Nightmare", "weapon_sword_of_nightmare_i0"],
135:["Samurai Longsword", "weapon_samurai_longsword_i0"],
145:["Sword of Whispering Death", "weapon_deathbreath_sword_i0"],
5286:["Berserker Blade", "weapon_berserker_blade_i0"],
# Blunts D grade
86:["Tomahawk", "weapon_tomahawk_i0"],
88:["Morning Star", "weapon_morning_star_i0"],
90:["Goat Head Staff", "weapon_goathead_staff_i0"],
156:["Hand Axe", "weapon_hand_axe_i0"],
157:["Spiked Club", "weapon_spike_club_i0"],
158:["Tarbar", "weapon_tarbar_i0"],
159:["Bonebreaker", "weapon_bonebreaker_i0"],
166:["Heavy Mace", "weapon_heavy_mace_i0"],
167:["Scalpel", "weapon_scalpel_i0"],
168:["Work Hammer", "weapon_work_hammer_i0"],
169:["Skull Breaker", "weapon_skull_breaker_i0"],
172:["Heavy Bone Club", "weapon_heavy_bone_club_i0"],
178:["Bone Staff", "weapon_apprentices_staff_i0"],
179:["Mace of Prayer", "weapon_mace_of_prayer_i0"],
180:["Mace of Judgment", "weapon_mace_of_judgment_i0"],
181:["Mace of Miracle", "weapon_mace_of_miracle_i0"],
182:["Doom Hammer", "weapon_doom_hammer_i0"],
183:["Mystic Staff", "weapon_apprentices_staff_i0"],
184:["Conjuror's Staff", "weapon_conjure_staff_i0"],
185:["Staff of Mana", "weapon_staff_of_mana_i0"],
186:["Staff of Magic", "weapon_staff_of_magicpower_i0"],
187:["Atuba Hammer", "weapon_atuba_hammer_i0"],
188:["Ghost Staff", "weapon_goathead_staff_i0"],
189:["Staff of Life", "weapon_life_stick_i0"],
190:["Atuba Mace", "weapon_atuba_mace_i0"],
# Blunts C grade
89:["Big Hammer", "weapon_big_hammer_i0"],
160:["Battle Axe", "weapon_battle_axe_i0"],
161:["Silver Axe", "weapon_war_pick_i0"],
162:["War Axe", "weapon_war_axe_i0"],
173:["Skull Graver", "weapon_skull_graver_i0"],
174:["Nirvana Axe", "weapon_nirvana_axe_i0"],
191:["Heavy Doom Hammer", "weapon_heavy_doom_hammer_i0"],
192:["Crystal Staff", "weapon_crystal_staff_i0"],
193:["Stick of Faith", "weapon_stick_of_faith_i0"],
194:["Heavy Doom Axe", "weapon_heavy_doom_axe_i0"],
195:["Cursed Staff", "weapon_cursed_staff_i0"],
196:["Stick of Eternity", "weapon_stick_of_eternity_i0"],
197:["Paradia Staff", "weapon_paradia_staff_i0"],
198:["Inferno Staff", "weapon_inferno_staff_i0"],
199:["Paagrio Hammer", "weapon_paagrio_hammer_i0"],
200:["Sage's Staff", "weapon_sages_staff_i0"],
201:["Club of Nature", "weapon_club_of_nature_i0"],
202:["Mace of The Underworld", "weapon_mace_of_underworld_i0"],
203:["Paagrio Axe", "weapon_paagrio_axe_i0"],
204:["Deadman's Staff", "weapon_deadmans_staff_i0"],
205:["Ghoul's Staff", "weapon_ghouls_staff_i0"],
206:["Demon's Staff", "weapon_demons_staff_i0"],
2502:["Dwarven War Hammer", "weapon_dwarven_warhammer_i0"],
2503:["Yaksa Mace", "weapon_yaksa_mace_i0"],
# Pole D grade
93:["Winged Spear", "weapon_winged_spear_i0"],
291:["Trident", "weapon_trident_i0"],
292:["Pike", "weapon_long_spear_i0"],
293:["War Hammer", "weapon_war_hammer_i0"],
294:["War Pick", "weapon_hammer_in_flames_i0"],
295:["Dwarven Trident", "weapon_trident_i0"],
296:["Dwarven Pike", "weapon_winged_spear_i0"],
297:["Glaive", "weapon_glaive_i0"],
# Pole C grade
94:["Bec de Corbin", "weapon_bech_de_corbin_i0"],
95:["Poleaxe", "weapon_poleaxe_i0"],
96:["Scythe", "weapon_scythe_i0"],
298:["Orcish Glaive", "weapon_orcish_glaive_i0"],
299:["Orcish Poleaxe", "weapon_orcish_poleaxe_i0"],
301:["Scorpion", "weapon_scorpion_i0"],
302:["Body Slasher", "weapon_body_slasher_i0"],
303:["Widow Maker", "weapon_widow_maker_i0"],
# Dagger D grade
220:["Crafted Dagger", "weapon_handiwork_dagger_i0"],
221:["Assassin Knife", "weapon_assassin_knife_i0"],
222:["Poniard Dagger", "weapon_dirk_i0"],
223:["Kukuri", "weapon_kukuri_i0"],
224:["Maingauche", "weapon_maingauche_i0"],
225:["Mithril Dagger", "weapon_mithril_dagger_i0"],
238:["Dagger of Mana", "weapon_dagger_of_mana_i0"],
239:["Mystic Knife", "weapon_mystic_knife_i0"],
240:["Conjurer's Knife", "weapon_conjure_knife_i0"],
241:["Shillien Knife", "weapon_conjure_knife_i0"],
1660:["Cursed Maingauche", "weapon_maingauche_i0"],
# Dagger C grade
226:["Cursed Dagger", "weapon_cursed_dagger_i0"],
227:["Stiletto", "weapon_stiletto_i0"],
228:["Crystal Dagger", "weapon_crystal_dagger_i0"],
230:["Wolverine Needle", "weapon_cursed_dagger_i0"],
231:["Grace Dagger", "weapon_grace_dagger_i0"],
232:["Dark Elven Dagger", "weapon_darkelven_dagger_i0"],
233:["Dark Screamer", "weapon_dark_screamer_i0"],
242:["Soulfire Dirk", "weapon_dagger_of_magicflame_i0"],
# Fist D grade
258:["Bagh-Nakh", "weapon_baghnakh_i0"],
259:["Single-Edged Jamadhr", "weapon_baghnakh_i0"],
260:["Triple-Edged Jamadhr", "weapon_baghnakh_i0"],
261:["Bich'Hwa", "weapon_bichhwa_i0"],
262:["Scallop Jamadhr", "weapon_scallop_jamadhr_i0"],
# Fist C grade
263:["Chakram", "weapon_chakram_i0"],
265:["Fist Blade", "weapon_fist_blade_i0"],
266:["Great Pata", "weapon_great_pata_i0"],
4233:["Knuckle Duster", "weapon_knuckle_dust_i0"],
# Etc D grade
101:["Scroll of Wisdom", "weapon_scroll_of_wisdom_i0"],
312:["Branch of Life", "weapon_apprentices_spellbook_i0"],
313:["Temptation of Abyss", "weapon_apprentices_spellbook_i0"],
314:["Proof of Revenge", "weapon_apprentices_spellbook_i0"],
315:["Divine Tome", "weapon_divine_tome_i0"],
316:["Blood of Saints", "weapon_blood_of_saints_i0"],
317:["Tome of Blood", "weapon_blood_of_saints_i0"],
318:["Crucifix of Blood", "weapon_crucifix_of_blood_i0"],
319:["Eye of Infinity", "weapon_apprentices_spellbook_i0"],
320:["Blue Crystal Skull", "weapon_apprentices_spellbook_i0"],
321:["Demon Fangs", "weapon_demon_fangs_i0"],
322:["Vajra Wands", "weapon_apprentices_spellbook_i0"],
323:["Ancient Reagent", "weapon_apprentices_spellbook_i0"],
# Etc C grade
324:["Tears of Fairy", "weapon_apprentices_spellbook_i0"],
325:["Horn of Glory", "weapon_horn_of_glory_i0"],
326:["Heathen's Book", "weapon_heathens_book_i0"],
327:["Hex Doll", "weapon_apprentices_spellbook_i0"],
328:["Candle of Wisdom", "weapon_apprentices_spellbook_i0"],
329:["Blessed Branch", "weapon_apprentices_spellbook_i0"],
330:["Phoenix Feather", "weapon_apprentices_spellbook_i0"],
331:["Cerberus Eye", "weapon_apprentices_spellbook_i0"],
332:["Scroll of Destruction", "weapon_apprentices_spellbook_i0"],
333:["Claws of Black Dragon", "weapon_apprentices_spellbook_i0"],
334:["Three Eyed Crow's Feather", "weapon_apprentices_spellbook_i0"],
#Dual D grade
2516:["Saber*Saber", "weapon_dual_sword_i0"],
2517:["Saber*Bastard Sword", "weapon_dual_sword_i0"],
2518:["Saber*Spinebone Sword", "weapon_dual_sword_i0"],
2519:["Saber*Artisan's Sword", "weapon_dual_sword_i0"],
2520:["Saber*Knight's Sword", "weapon_dual_sword_i0"],
2521:["Saber*Crimson Sword", "weapon_dual_sword_i0"],
2522:["Saber*Elven Sword", "weapon_dual_sword_i0"],
2525:["Bastard Sword*Bastard Sword", "weapon_dual_sword_i0"],
2526:["Bastard Sword*Spinebone Sword", "weapon_dual_sword_i0"],
2527:["Bastard Sword*Artisan's Sword", "weapon_dual_sword_i0"],
2528:["Bastard Sword*Knight's Sword", "weapon_dual_sword_i0"],
2529:["Bastard Sword*Crimson Sword", "weapon_dual_sword_i0"],
2530:["Bastard Sword*Elven Sword", "weapon_dual_sword_i0"],
2533:["Spinebone Sword*Spinebone Sword", "weapon_dual_sword_i0"],
2534:["Spinebone Sword*Artisan's Sword", "weapon_dual_sword_i0"],
2535:["Spinebone Sword*Knight's Sword", "weapon_dual_sword_i0"],
2536:["Spinebone Sword*Crimson Sword", "weapon_dual_sword_i0"],
2537:["Spinebone Sword*Elven Sword", "weapon_dual_sword_i0"],
2540:["Artisan's Sword*Artisan's Sword", "weapon_dual_sword_i0"],
2541:["Artisan's Sword*Knight's Sword", "weapon_dual_sword_i0"],
2542:["Artisan's Sword*Crimson Sword", "weapon_dual_sword_i0"],
2543:["Artisan's Sword*Elven Sword", "weapon_dual_sword_i0"],
2546:["Knight's Sword*Knight's Sword", "weapon_dual_sword_i0"],
2547:["Knight's Sword*Crimson Sword", "weapon_dual_sword_i0"],
2548:["Knight's Sword*Elven Sword", "weapon_dual_sword_i0"],
#Dual C grade
2523:["Saber*Sword of Revolution", "weapon_dual_sword_i0"],
2524:["Saber*Elven Long Sword", "weapon_dual_sword_i0"],
2531:["Bastard Sword*Sword of Revolution", "weapon_dual_sword_i0"],
2532:["Bastard Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2538:["Spinebone Sword*Sword of Revolution", "weapon_dual_sword_i0"],
2539:["Spinebone Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2544:["Artisan's Sword*Sword of Revolution", "weapon_dual_sword_i0"],
2545:["Artisan's Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2549:["Knight's Sword*Sword of Revolution", "weapon_dual_sword_i0"],
2550:["Knight's Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2551:["Crimson Sword*Crimson Sword", "weapon_dual_sword_i0"],
2552:["Crimson Sword*Elven Sword", "weapon_dual_sword_i0"],
2553:["Crimson Sword*Sword of Revolution", "weapon_dual_sword_i0"],
2554:["Crimson Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2555:["Elven Sword*Elven Sword", "weapon_dual_sword_i0"],
2556:["Elven Sword*Sword Of Revolution", "weapon_dual_sword_i0"],
2557:["Elven Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2558:["Sword of Revolution*Sword of Revolution", "weapon_dual_sword_i0"],
2559:["Sword of Revolution*Elven Long Sword", "weapon_dual_sword_i0"],
2560:["Elven Long Sword*Elven Long Sword", "weapon_dual_sword_i0"],
2561:["Stormbringer*Stormbringer", "weapon_dual_sword_i0"],
2562:["Stormbringer*Shamshir", "weapon_dual_sword_i0"],
2563:["Stormbringer*Katana", "weapon_dual_sword_i0"],
2564:["Stormbringer*Spirit Sword", "weapon_dual_sword_i0"],
2565:["Stormbringer*Raid Sword", "weapon_dual_sword_i0"],
2572:["Shamshir*Shamshir", "weapon_dual_sword_i0"],
2573:["Shamshir*Katana", "weapon_dual_sword_i0"],
2574:["Shamshir*Spirit Sword", "weapon_dual_sword_i0"],
2575:["Shamshir*Raid Sword", "weapon_dual_sword_i0"],
2582:["Katana*Katana", "weapon_dual_sword_i0"],
2583:["Katana*Spirit Sword", "weapon_dual_sword_i0"],
2584:["Katana*Raid Sword", "weapon_dual_sword_i0"],
2591:["Spirit Sword*Spirit Sword", "weapon_dual_sword_i0"],
2592:["Spirit Sword*Raid Sword", "weapon_dual_sword_i0"],
2599:["Raid Sword*Raid Sword", "weapon_dual_sword_i0"]
}

############################################################## DO NOT MODIFY BELOW THIS LINE #####################################################################################


def getItemName(Item):
    ItemName = Item.getItem().getName()
    if Item.getEnchantLevel() > 0:
        ItemName = "+" + str(Item.getEnchantLevel()) + " " + ItemName
    return ItemName

def getItemIcon(ItemName):
    Icon = ""
    Name = ""
    for TestID in ExchangeList:
        Name, IconTemp = ExchangeList[TestID]
        if ItemName.startswith(Name):
            Icon = IconTemp
            break
    if Icon != "":
        if ItemName == Name:
            Icon += "0"
        else:
            Icon += "1"
    return Icon
   
# Main Code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
   # shows the weapons available for exchange that you can trade from
    if event.startswith("1_"):
        for ItemInst in st.getPlayer().getInventory().getItems():
            Item = ItemInst.getItem()
            if Item.getType1() != 0 or Item.getType2() != 0 or ItemInst.isEquipped():
                continue
            if Item.getItemGrade() == 1 or Item.getItemGrade() == 2:
                htmltext += "<tr><td width=35><img src=\"icon." + getItemIcon(Item.getName()) + "\" width=32 height=32 align=\"left\"></td>" \
                    "<td width=835><table border=0 width=835><tr><td><a action=\"bypass -h Quest 1010_exchange 2_" + str(ItemInst.getObjectId()) + "\">" + getItemName(ItemInst) + "</a></td></tr>" \
                    "<tr><td><font color=\"B09878\">Exchange</font></td></tr></table></td></tr>"
        if htmltext == "":
            htmltext = "<html><body><tr><td>You have no weapon in your inventory that can be exchanged.</td></tr></body></html>"
        htmltext = "<html><body>Choose the weapon you want exchange:<br>List:<left><table width=870 border=0>" + htmltext + "</table><br></left></body></html>"
        return htmltext
   
   # shows you the available weapons for exchange that you can trade for
    elif event.startswith("2_"):
        reqExch = event.replace("2_", "")
        ObjectID = int(reqExch)
        ItemInst = st.getPlayer().getInventory().getItemByObjectId(ObjectID)
        htmltext = ""
        Item = ItemInst.getItem()
        if Item.getType1() != 0 or Item.getType2() != 0 or ItemInst.isEquipped():
            return htmltext
        if Item.getItemGrade() != 1 and Item.getItemGrade() != 2:
            return htmltext
        WeaponCrystals = Item.getCrystalCount()
        WeaponGrade = Item.getItemGrade()
        WeaponName = Item.getName()
        for NewItemID in ExchangeList:
            NewItem = ItemTable.getInstance().getTemplate(NewItemID)
            NewItemName = NewItem.getName()
            if NewItem.getItemGrade() == WeaponGrade and NewItem.getCrystalCount() == WeaponCrystals and not WeaponName.startswith(NewItemName):
                if (NewItem.getItemType().toString() == "Dual Sword") ^ (Item.getItemType().toString() == "Dual Sword"):
                    continue
                if ItemInst.getEnchantLevel() > 0:
                    NewItemName = "+" + str(ItemInst.getEnchantLevel()) + " " + NewItemName
                htmltext += "<tr><td width=35><img src=\"icon." + getItemIcon(NewItem.getName()) + "\" width=32 height=32 align=\"left\"></td>" \
                    "<td width=835><table border=0 width=835><tr><td><a action=\"bypass -h Quest 1010_exchange 3_" + str(ObjectID) + "." + str(NewItemID) + "\">" + NewItemName + "</a></td></tr>" \
                    "<tr><td><font color=\"B09878\">Exchange</font></td></tr></table></td></tr>"
        if htmltext == "":
            htmltext = "<html><body><tr><td>This weapon is not suitable for exchange.</td></tr></body></html>"
        else:
            htmltext = "<html><body>Choose the weapon you want to trade for: <br>List:<left><table width=870 border=0>" + htmltext + "</table><br></left></body></html>"
        return htmltext
   
    # displays the recipe
    elif event.startswith("3_"):
        TradeItems = event.replace("3_", "").split(".")
        OldItemID = int(TradeItems[0])
        NewItemID = int(TradeItems[1])
        OldItemInst = st.getPlayer().getInventory().getItemByObjectId(OldItemID)
        OldWeaponName = getItemName(OldItemInst)
        NewItem = ItemTable.getInstance().getTemplate(NewItemID)
        NewItemName = NewItem.getName()
        if OldItemInst.getEnchantLevel() > 0:
            NewItemName = "+" + str(OldItemInst.getEnchantLevel()) + " " + NewItemName
        htmltext = st.showHtmlFile("2.htm")
        return htmltext.replace("<NewWeaponName>", NewItemName)\
            .replace("<OldWeaponName>", OldWeaponName)\
            .replace("<NewWeaponIcon>", getItemIcon(NewItem.getName()))\
            .replace("<OldWeaponIcon>", getItemIcon(OldItemInst.getItem().getName()))\
            .replace("<EventOK>", "4_" + str(OldItemID) + "." + str(NewItemID))
   
    # this handles the whole exchange stuff with objectIds... no html shows up.. just work and socket return
    elif event.startswith("4_"):
        TradeItems = event.replace("4_", "").split(".")
        OldItemID = int(TradeItems[0])
        NewItemID = int(TradeItems[1])
        OldItemInst = st.getPlayer().getInventory().getItemByObjectId(OldItemID)
        #only need to check if the item hasn't been equiped or dropped
        if not OldItemInst or OldItemInst.isEquipped():
            return "Player is trying to dupe!"
        NewItem = ItemTable.getInstance().getTemplate(NewItemID)
        EnchantLevel = OldItemInst.getEnchantLevel()
        st.getPlayer().destroyItem("exchange",OldItemID, 1, st.getPlayer(), 0)
        NewItem = ItemTable.getInstance().createItem("exchange", NewItemID, 1, st.getPlayer())
        NewItem.setEnchantLevel(EnchantLevel)
        st.getPlayer().addItem("exchange", NewItem, st.getPlayer(), 0)
        htmltext = "Exchange has been successfully done!"

    # if event is 0, or has a bug... trade is canceled
    else :
        htmltext = "Trade has been cancelled."
    st.setState(COMPLETED)
    st.exitQuest(1)
    return htmltext
   
   
# this just return new html, if the player can talk with this npc about that enhance stuff
 def onTalk (self,npc,st) :
   npcId = npc.getNpcId()
   st.set("cond","0")
   st.setState(STARTED)
   return "1.htm"


QUEST       = Quest(1010,"1010_exchange","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)


# init all npc to the correct stats
for npcId in [8126]:
   QUEST.addStartNpc(npcId)
   STARTED.addTalkId(npcId)
   
# always at the end, then it shows only up if anything is correct in the code.. no jython error.. because we cant check jython errors with idle
print "importing blacksmith data: 1010_exchange"
