# Engine written by Advi (and what a lovely engine it is ^^)
# Mammon thingy added by NewAge
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver import ItemTable


############################## Feel Free to add more Weapons ##########################################################################################################3

# Weapon enhancement definition  WeaponID:[Icon, [[Enhancement, newWeaponID, CrystalID, MaterialID, MaterialQuant], ...]]

EnhanceList={
# Bows'
7575:["weapon_draconic_bow_i01", [["Cheap Shot", 7576, 5908, 2134, 82], ["Focus", 7577, 5911, 2134, 82], ["Critical Slow", 7578, 5914, 2134, 82]]], 
# Swords'
6364:["weapon_forgotten_blade_i01", [["Haste", 6581, 5908, 2134, 82], ["Health", 6582, 5911, 2134, 82], ["Focus", 6583, 5914, 2134, 82]]], 
6372:["weapon_heavens_divider_i01", [["Haste", 6605, 5908, 2134, 82], ["Health", 6606, 5911, 2134, 82], ["Focus", 6607, 5914, 2134, 82]]], 
# Blunts'
6369:["weapon_dragon_hunter_axe_i01", [["HP Regeneration", 6596, 5908, 2134, 82], ["Health", 6597, 5911, 2134, 82], ["HP Drain", 6598, 5914, 2134, 82]]], 
6579:["weapon_arcana_mace_i01", [["Acumen", 6608, 5908, 2134, 82], ["MP Regeneration", 6609, 5911, 2134, 82], ["Mana Up", 6610, 5914, 2134, 82]]], 
6366:["weapon_imperial_staff_i01", [["Empower", 6587, 5908, 2134, 82], ["MP Regeneration", 6588, 5911, 2134, 82], ["Magic Hold", 6589, 5914, 2134, 82]]], 
6563:["weapon_basalt_battlehammer_i01", [["HP Drain", 6584, 5908, 2134, 82], ["Health", 6585, 5911, 2134, 82], ["HP Regeneration", 6586, 5914, 2134, 82]]], 
# Dagger'
6367:["weapon_angel_slayer_i01", [["Crt. Damage", 6590, 5908, 2134, 82], ["HP Drain", 6591, 5911, 2134, 82], ["Haste", 6592, 5914, 2134, 82]]], 
# Poleaxe'
6370:["weapon_saint_spear_i01", [["Health", 6599, 5908, 2134, 82], ["Guidance", 6600, 5911, 2134, 82], ["Haste", 6601, 5914, 2134, 82]]], 
# Fist'
6371:["weapon_demon_splinter_i01", [["Focus", 6602, 5908, 2134, 82], ["Health", 6603, 5911, 2134, 82], ["Crt. Stun", 6604, 5914, 2134, 82]]] 
}


############################################################## DO NOT MODIFY BELOW THIS LINE #####################################################################################


def getItemName(Item):
    ItemName = Item.getItem().getName()
    if Item.getEnchantLevel() > 0: 
        ItemName = "+" + str(Item.getEnchantLevel()) + " " + ItemName
    return ItemName


def getMaterialName(MaterialID):
    if MaterialID in range(2131, 2134):
        return "Gemstone " + chr(ord('A') + 2133 - MaterialID)
    Stage = ""
    if MaterialID in range(5577, 5580):
        Stage = "11"
    if MaterialID in range(5580, 5583):
        Stage = "12"
    if MaterialID in [5577, 5580]:
        return "Red Soul Crystal - Stage " + Stage
    if MaterialID in [5578, 5581]:
        return "Green Soul Crystal - Stage " + Stage
    if MaterialID in [5579, 5582]:
        return "Blue Soul Crystal - Stage " + Stage
    return "Unknown material"

    
def getMaterialIcon(MaterialID):
    if MaterialID in [2133]:
        return "etc_crystal_ball_green_i00"
    if MaterialID in [5577]:
        return "etc_crystal_red_i00"
    if MaterialID in [5580]:
        return "etc_crystal_red_i00"
    if MaterialID in [5578]:
        return "etc_crystal_green_i00"
    if MaterialID in [5581]:
        return "etc_crystal_green_i00"
    if MaterialID in [5579]:
        return "etc_crystal_blue_i00"
    if MaterialID in [5582]:
        return "etc_crystal_blue_i00"
    return "etc_unknown_material_i00"


# Main Code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    
    # shows you how much materials you need to enhance, ok button to go forward, too
    if event.startswith("2_"):
        reqEnh = event.replace("2_", "").split(".")
        ObjectID = int(reqEnh[0])
        EnhancID = int(reqEnh[1])
        Item = st.getPlayer().getInventory().getItemByObjectId(ObjectID)
        if Item.getItemId() in EnhanceList:
            Icon, Enhancements = EnhanceList[Item.getItemId()]
            Name, WeaponID, CrystalID, MaterialID, MaterialQuant = Enhancements[EnhancID]
            htmltext = st.showHtmlFile("2.htm")
            return htmltext.replace("<WeaponName>", getItemName(Item) + ": " + Name)\
                .replace("<WeaponIcon>", Icon)\
                .replace("<CrystalName>", getMaterialName(CrystalID))\
                .replace("<CrystalIcon>", getMaterialIcon(CrystalID))\
                .replace("<MaterialName>", getMaterialName(MaterialID))\
                .replace("<MaterialIcon>", getMaterialIcon(MaterialID))\
                .replace("<MaterialQuantity>", str(MaterialQuant))\
                .replace("<EventOK>", "3_" + str(Item.getObjectId()) + "." + str(EnhancID))
    
    # this handles the whole enhance stuff with objectIds, not ItemIds... no html shows up.. just work and socket return
    elif event.startswith("3_"):
        reqEnh = event.replace("3_", "").split(".")
        ObjectID = int(reqEnh[0])
        EnhancID = int(reqEnh[1])
        Item = st.getPlayer().getInventory().getItemByObjectId(ObjectID)
        if Item.getItemId() in EnhanceList:
            Icon, Enhancements = EnhanceList[Item.getItemId()]
            Name, WeaponID, CrystalID, MaterialID, MaterialQuant = Enhancements[EnhancID]

            if st.getQuestItemsCount(CrystalID) >= 1 and st.getQuestItemsCount(MaterialID) >= MaterialQuant :
                EnchantLevel = Item.getEnchantLevel()
                st.getPlayer().destroyItem("enhance_1008",ObjectID, 1, st.getPlayer(), 0)
                NewItem = ItemTable.getInstance().createItem("enhance",WeaponID, 1, st.getPlayer())
                NewItem.setEnchantLevel(EnchantLevel)
                st.getPlayer().addItem("enhance",NewItem, st.getPlayer(), 0)
                st.takeItems(CrystalID, 1)
                st.takeItems(MaterialID, MaterialQuant)
                htmltext = "Item has been succesfully enhanced!"
            else :
                htmltext = "You do not have enough materials."
    
    # if event is 0, or has a bug... trade is canceled
    else :
        htmltext = "Trade has been cancelled."

    
    st.setState(COMPLETED)
    st.exitQuest(1)
    return htmltext
    
    
# this just return new html, if the player can talk with this npc about that enhance stuff
 def onTalk (self,npc,st):
   npcId = npc.getNpcId()
   st.set("cond","0")
   st.setState(STARTED)
   htmltext = ""
   for Item in st.getPlayer().getInventory().getItems():
            if Item.getItemId() in EnhanceList and not Item.isEquipped():
                Icon, Enhancements = EnhanceList[Item.getItemId()]
                EnhancID = 0
                for Name, WeaponID, CrystalID, MaterialID, MaterialQuant in Enhancements:
                    htmltext += "<tr>\n<td width=35><img src=\"icon." + Icon + "\" width=32 height=32 align=\"left\"></td>\n" \
                        "<td width=835><table border=0 width=\"835\">\n<tr><td><a action=\"bypass -h Quest 1008_enhance_mammon 2_" + str(Item.getObjectId()) + "." + str(EnhancID) + "\">" + getItemName(Item) + ": " + Name + "</a></td></tr>\n" \
                        "<tr><td><font color=\"B09878\">Enhance</font></td></tr></table></td>\n</tr>"
                    EnhancID += 1
   if htmltext == "": 
          htmltext = "<tr><td>You have no enhancable weapon in your inventory</td></tr>"
   htmltext = "<html><body>\nList:\n<left>\n<table width=870 border=0>\n" + htmltext + "</table>\n<br></left></body></html>"
   return htmltext


QUEST       = Quest(1011,"1011_enhance_mammon_s","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)


# init all npc to the correct stats
for npcId in [8126]:
	QUEST.addStartNpc(npcId)
	STARTED.addTalkId(npcId)
	
# always at the end, then it shows only up if anything is correct in the code.. no jython error.. because we cant check jython errors with idle
print "importing blacksmith data: 1011_enhance_mammon_s"
