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
288:["weapon_carnium_bow_i01", [["Light", 5608, 5577, 2133, 147], ["Critical Bleed", 5609, 5578, 2133, 147], ["Mana Up", 5610, 5579, 2133, 147]]], 
289:["weapon_soul_bow_i01", [["Cheap Shot", 5611, 5580, 2133, 147], ["Quick Recovery", 5612, 5581, 2133, 147], ["Critical Poison", 5613, 5582, 2133, 147]]], 
# Swords'
150:["weapon_elemental_sword_i01", [["Magic Power", 5638, 5577, 2133, 147], ["Magic Paralyze", 5639, 5578, 2133, 147], ["Empower", 5640, 5579, 2133, 147]]], 
80:["weapon_tallum_blade_i01", [["Critical Poison", 5635, 5577, 2133, 147], ["Haste", 5636, 5578, 2133, 147], ["Anger", 5637, 5579, 2133, 147]]], 
151:["weapon_sword_of_miracle_i01", [["Magic Power", 5641, 5580, 2133, 147], ["Magic Silence", 5642, 5581, 2133, 147], ["Acumen", 5643, 5582, 2133, 147]]], 
2500:["weapon_dark_legions_edge_i01", [["Critical Damage", 5647, 5580, 2133, 147], ["Health", 5648, 5581, 2133, 147], ["Rsk. Focus", 5649, 5582, 2133, 147]]], 
81:["weapon_dragon_slayer_i01", [["Health", 5644, 5580, 2133, 147], ["Critical Bleed", 5645, 5581, 2133, 147], ["Critical Drain", 5646, 5582, 2133, 147]]], 
# Blunts'
2504:["weapon_meteor_shower_i01", [["Focus", 5599, 5577, 2133, 147], ["Critical Bleed", 5600, 5578, 2133, 147], ["Rsk. Haste", 5601, 5579, 2133, 147]]], 
212:["weapon_dasparions_staff_i01", [["Mana Up", 5596, 5577, 2133, 147], ["Conversion", 5597, 5578, 2133, 147], ["Acumen", 5598, 5579, 2133, 147]]], 
213:["weapon_worldtrees_branch_i01", [["Conversion", 5605, 5580, 2133, 147], ["Magic Damage", 5606, 5581, 2133, 147], ["Acumen", 5607, 5582, 2133, 147]]], 
164:["weapon_elysian_i01", [["Health", 5602, 5580, 2133, 147], ["Anger", 5603, 5581, 2133, 147], ["Critical Drain", 5604, 5582, 2133, 147]]], 
# Dagger'
235:["weapon_bloody_orchid_i01", [["Focus", 5614, 5577, 2133, 147], ["Back Blow", 5615, 5578, 2133, 147], ["Critical Bleed", 5616, 5579, 2133, 147]]], 
236:["weapon_soul_separator_i01", [["Guidance", 5617, 5580, 2133, 147], ["Critical Damage", 5618, 5581, 2133, 147], ["Rsk. Haste", 5619, 5582, 2133, 147]]], 
# Poleaxe'
98:["weapon_halbard_i01", [["Haste", 5626, 5577, 2133, 147], ["Critical Stun", 5627, 5578, 2133, 147], ["Wide Blow", 5628, 5579, 2133, 147]]], 
305:["weapon_tallum_glaive_i01", [["Guidance", 5632, 5580, 2133, 147], ["Health", 5633, 5581, 2133, 147], ["Wide Blow", 5634, 5582, 2133, 147]]], 
# Fist'
269:["weapon_blood_tornado_i01", [["Haste", 5620, 5577, 2133, 147], ["Focus", 5621, 5578, 2133, 147], ["Anger", 5622, 5579, 2133, 147]]], 
270:["weapon_dragon_grinder_i01", [["Rsk. Evasion", 5623, 5580, 2133, 147], ["Guidance", 5624, 5581, 2133, 147], ["Health", 5625, 5582, 2133, 147]]] 
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


QUEST       = Quest(1008,"1008_enhance_mammon","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)


# init all npc to the correct stats
for npcId in [8126]:
	QUEST.addStartNpc(npcId)
	STARTED.addTalkId(npcId)
	
# always at the end, then it shows only up if anything is correct in the code.. no jython error.. because we cant check jython errors with idle
print "importing blacksmith data: 1008_enhance_mammon"
