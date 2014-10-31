import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver import ItemTable
 

# Weapon enhancement definition  WeaponID:[BasicIconName, [[BasicItemName, newWeaponID, MaterialID, MaterialQuant]]]
 
EnhanceList={
# Bows'
5608:["weapon_carnium_bow_i", [["Light", 288, 5575, 109000]]], 
5609:["weapon_carnium_bow_i", [["Critical Bleed", 288, 5575, 109000]]], 
5610:["weapon_carnium_bow_i", [["Mana Up", 288, 5575, 109000]]], 
5611:["weapon_soul_bow_i", [["Cheap Shot", 289, 5575, 109000]]], 
5612:["weapon_soul_bow_i", [["Quick Recovery", 289, 5575, 109000]]], 
5613:["weapon_soul_bow_i", [["Critical Poison", 289, 5575, 109000]]], 
# Swords'
5635:["weapon_tallum_blade_i", [["Critical Poison", 80, 5575, 109000]]], 
5636:["weapon_tallum_blade_i", [["Haste", 80, 5575, 109000]]], 
5637:["weapon_tallum_blade_i", [["Anger", 80, 5575, 109000]]], 
5638:["weapon_elemental_sword_i", [["Magic Power", 150, 5575, 109000]]], 
5639:["weapon_elemental_sword_i", [["Magic Paralyze", 150, 5575, 109000]]], 
5640:["weapon_elemental_sword_i", [["Magic Empower", 150, 5575, 109000]]], 
5641:["weapon_sword_of_miracle_i", [["Magic Power", 151, 5575, 109000]]], 
5642:["weapon_sword_of_miracle_i", [["Magic Silence", 151, 5575, 109000]]], 
5643:["weapon_sword_of_miracle_i", [["Acumen", 151, 5575, 109000]]], 
5644:["weapon_dragon_slayer_i", [["Health", 81, 5575, 109000]]], 
5645:["weapon_dragon_slayer_i", [["Critical Bleed", 81, 5575, 109000]]], 
5646:["weapon_dragon_slayer_i", [["Critical Drain", 81, 5575, 109000]]], 
5647:["weapon_dark_legions_edge_i", [["Critical Damage", 2500, 5575, 109000]]], 
5648:["weapon_dark_legions_edge_i", [["Health", 2500, 5575, 109000]]], 
5649:["weapon_dark_legions_edge_i", [["Rsk. Focus", 2500, 5575, 109000]]], 
# Blunts'
5599:["weapon_meteor_shower_i", [["Focus", 2504, 5575, 109000]]], 
5600:["weapon_meteor_shower_i", [["Critical Bleed", 2504, 5575, 109000]]], 
5601:["weapon_meteor_shower_i", [["Rsk. Haste", 2504, 5575, 109000]]], 
5596:["weapon_dasparions_staff_i", [["Mana Up", 212, 5575, 109000]]], 
5597:["weapon_dasparions_staff_i", [["Conversion", 212, 5575, 109000]]], 
5598:["weapon_dasparions_staff_i", [["Acumen", 212, 5575, 109000]]], 
5605:["weapon_worldtrees_branch_i", [["Conversion", 213, 5575, 109000]]], 
5606:["weapon_worldtrees_branch_i", [["Magic Damage", 213, 5575, 109000]]], 
5607:["weapon_worldtrees_branch_i", [["Acumen", 213, 5575, 109000]]], 
5602:["weapon_elysian_i", [["Health", 164, 5575, 109000]]], 
5603:["weapon_elysian_i", [["Anger", 164, 5575, 109000]]], 
5604:["weapon_elysian_i", [["Critical Drain", 164, 5575, 109000]]], 
# Dagger'
5614:["weapon_bloody_orchid_i", [["Focus", 235, 5575, 109000]]], 
5615:["weapon_bloody_orchid_i", [["Back Blow", 235, 5575, 109000]]], 
5616:["weapon_bloody_orchid_i", [["Critical Bleed", 235, 5575, 109000]]], 
5617:["weapon_soul_separator_i", [["Guidance", 236, 5575, 109000]]], 
5618:["weapon_soul_separator_i", [["Critical Damage", 236, 5575, 109000]]], 
5619:["weapon_soul_separator_i", [["Rsk. Haste", 236, 5575, 109000]]], 
# Poleaxe'
5626:["weapon_halbard_i", [["Haste", 98, 5575, 109000]]], 
5627:["weapon_halbard_i", [["Critical Stun", 98, 5575, 109000]]], 
5628:["weapon_halbard_i", [["Wide Blow", 98, 5575, 109000]]], 
5632:["weapon_tallum_glaive_i", [["Guidance", 305, 5575, 109000]]], 
5633:["weapon_tallum_glaive_i", [["Health", 305, 5575, 109000]]], 
5634:["weapon_tallum_glaive_i", [["Wide Blow", 305, 5575, 109000]]], 
# Fist'
5620:["weapon_blood_tornado_i", [["Haste", 269, 5575, 109000]]], 
5621:["weapon_blood_tornado_i", [["Focus", 269, 5575, 109000]]], 
5622:["weapon_blood_tornado_i", [["Anger", 269, 5575, 109000]]], 
5623:["weapon_dragon_grinder_i", [["Rsk. Evasion", 270, 5575, 109000]]], 
5624:["weapon_dragon_grinder_i", [["Guidance", 270, 5575, 109000]]], 
5625:["weapon_dragon_grinder_i", [["Health", 270, 5575, 109000]]], 
}
 
 
############################################################## DO NOT MODIFY BELOW THIS LINE #####################################################################################
 
 
def getItemName(Item):
    ItemName = Item.getItem().getName()
    if Item.getEnchantLevel() > 0: 
        ItemName = "+" + str(Item.getEnchantLevel()) + " " + ItemName
    return ItemName
 
 
def getMaterialName(MaterialID):
    return "Ancient Adena"
 
    
def getMaterialIcon(MaterialID):
    return "etc_ancient_adena_i00"
 
 
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
            Name, WeaponID, MaterialID, MaterialQuant = Enhancements[EnhancID]
            htmltext = st.showHtmlFile("2.htm")
            return htmltext.replace("<WeaponName>", Name)\
                .replace("<WeaponIcon>", Icon+"00")\
                .replace("<SaWeaponIcon>", Icon+"01")\
                .replace("<SaWeaponName>", getItemName(Item))\
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
            Name, WeaponID, MaterialID, MaterialQuant = Enhancements[EnhancID]
            if st.getQuestItemsCount(MaterialID) >= MaterialQuant :
                EnchantLevel = Item.getEnchantLevel()
                st.getPlayer().destroyItem("remove_1009",ObjectID, 1,st.getPlayer(), 0)
                NewItem = ItemTable.getInstance().createItem("enhance",WeaponID, 1,st.getPlayer())
                NewItem.setEnchantLevel(EnchantLevel)
                st.getPlayer().addItem("enhance", NewItem, st.getPlayer(), 0)
                st.takeItems(MaterialID, MaterialQuant)
                htmltext = "Enhancement has been successfully removed!"
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
   st.set("cond","0")
   st.setState(STARTED)
   htmltext = ""
   for Item in st.getPlayer().getInventory().getItems():
       if Item.getItemId() in EnhanceList and not Item.isEquipped():
           Icon, Enhancements = EnhanceList[Item.getItemId()]
           EnhancID = 0
           for Name, WeaponID, MaterialID, MaterialQuant in Enhancements:
                    htmltext += "<tr>\n<td width=35><img src=\"icon." + Icon + "01\" width=32 height=32 align=\"left\"></td>\n" \
                        "<td width=835><table border=0 width=\"835\">\n<tr><td><a action=\"bypass -h Quest 1009_remove_mammon 2_" + str(Item.getObjectId()) + "." + str(EnhancID) + "\">" + getItemName(Item) + ": " + Name + "</a></td></tr>\n" \
                        "<tr><td><font color=\"B09878\">Remove Enhancement</font></td></tr></table></td>\n</tr>"
                    EnhancID += 1
   if htmltext == "": 
       htmltext = "<tr><td>You have no weapons with special abilities in your inventory</td></tr>"
   htmltext = "<html><body>\nList:\n<left>\n<table width=870 border=0>\n" + htmltext + "</table>\n<br></left></body></html>"
   return htmltext
 
 
QUEST       = Quest(1009,"1009_remove_mammon","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)
 
 
QUEST.setInitialState(CREATED)
 
 
# init all npc to the correct stats
for npcId in [8092]:
        QUEST.addStartNpc(npcId)
        STARTED.addTalkId(npcId)
        
# always at the end, then it shows only up if anything is correct in the code.. no jython error.. because we cant check jython errors with idle
print "importing blacksmith data: 1009_remove_mammon"
