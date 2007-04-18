# Supplier of Reagents version 0.1 
# by DrLecter
print "importing quests:",
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
#Quest info
QUEST_NUMBER,QUEST_NAME,QUEST_DESCRIPTION = 373,"SupplierOfReagents","Supplier of Reagents"
qn = "373_SupplierOfReagents"

#Variables
#Rewards
SHOP_LIST={#itemid:[qty,required_id,required_qty]
4042:[1,  6025,8],4043:[1,  6021,6],4044:[1,  6024,4],2508:[100,6016,4],735: [10, 6025,7],
737: [10, 6023,8],4953:[1,  6027,1],4960:[1,  6027,1],4959:[1,  6030,1],4958:[1,  6030,1],
4998:[1,  6030,1],4992:[1,  6030,1],4993:[1,  6030,1],4999:[1,  6030,1],5524:[1,  6029,1],
5478:[1,  6029,1],5520:[1,  6031,1],5479:[1,  6031,1],5521:[1,  6032,1],5480:[1,  6032,1],
5481:[1,  6032,1],5522:[1,  6028,1],5523:[1,  6028,1],103: [1,  6031,8],2437:[1,  6032,8],
630: [1,  6029,5],612: [1,  6033,1],2464:[1,  6033,1],554: [1,  6033,1],600: [1,  6033,1],
601: [1,  6034,4],2439:[1,  6034,4],2475:[1,  6034,4],2487:[1,  6034,4]
}
#itemId:[icon,name,description]
ITEMS={
4042:["etc_gem_red_i00","Enria",""],
4043:["etc_gem_blue_i00","Asofe",""],
4044:["etc_gem_clear_i00","Thons",""],
2508:["etc_piece_bone_red_i00","Cursed Bone",""],
735:["etc_reagent_green_i00","Potion of Alacrity",""],
737:["etc_scroll_of_resurrection_i00","Scroll of Resurrection",""],
4953:["etc_recipe_red_i00","Recipe: Avadon Gloves (60%)",""],
4960:["etc_recipe_red_i00","Recipe: Zubei's Gauntlets (60%)",""],
4959:["etc_recipe_red_i00","Recipe: Avadon Boots (60%)",""],
4958:["etc_recipe_red_i00","Recipe: Zubei's Boots (60%)",""],
4998:["etc_recipe_red_i00","Recipe: Blue Wolf Gloves (60%)",""],
4992:["etc_recipe_red_i00","Recipe: Blue Wolf Boots (60%)",""],
4993:["etc_recipe_red_i00","Recipe: Doom Gloves (60%)",""],
4999:["etc_recipe_red_i00","Recipe: Doom Boots (60%)",""],
5524:["etc_letter_red_i00","Sealed Dark Crystal Gaiters Pattern",""],
5478:["etc_letter_red_i00","Sealed Dark Crystal Leather Armor Pattern",""],
5520:["etc_letter_red_i00","Sealed Dark Crystal Breastplate Pattern",""],
5479:["etc_letter_red_i00","Sealled Tallum Leather Armor Pattern",""],
5521:["etc_letter_red_i00","Sealed Tallum Plate Armor Pattern",""],
5480:["etc_leather_gray_i00","Sealed Leather Armor of Nightmare Fabric",""],
5481:["etc_leather_gray_i00","Sealed Majestic Leather Armor Fabric",""],
5522:["etc_letter_red_i00","Sealed Armor of Nightmare Pattern",""],
5523:["etc_letter_red_i00","Sealed Majestic Plate Armor Pattern",""],
103:["shield_tower_shield_i00","Tower Shield","Shield"],
2437:["armor_t21_b_i00","Drake Leather Boots","Boots"],
630:["shield_square_shield_i00","Square Shield","Shield"],
612:["armor_t64_g_i00","Zubei's Gauntlets","Gloves"],
2464:["armor_t66_g_i00","Avadon Gloves","Gloves"],
554:["armor_t64_b_i00","Zubei's Boots","Boots"],
600:["armor_t66_b_i00","Avadon Boots","Boots"],
601:["armor_t68_b_i00","Blue Wolf Boots","Boots"],
2439:["armor_t71_b_i00","Boots of Doom","Boots"],
2475:["armor_t68_g_i00","Blue Wolf Gloves","Gloves"],
2487:["armor_t71_g_i00","Doom Gloves","Gloves"],
6011:["etc_reagent_red_i00","Wyrm's Blood",""],
6012:["etc_inf_ore_high_i00","Lava Stone",""],
6013:["etc_broken_crystal_silver_i00","Moonstone Shard",""],
6014:["etc_piece_bone_black_i00","Rotten Bone Piece",""],
6015:["etc_reagent_green_i00","Demon's Blood",""],
6016:["etc_inf_ore_least_i00","Infernium Ore","Low Level Reagent"],
6017:["etc_ginseng_red_i00","Blood Root",""],
6018:["etc_powder_gray_i00","Volcanic Ash",""],
6019:["etc_reagent_silver_i00","Quicksilver",""],
6020:["etc_powder_orange_i00","Sulfur",""],
6021:["etc_dragons_blood_i05","Dracoplasm","Low Level Reagent"],
6022:["etc_powder_red_i00","Magma Dust",""],
6023:["etc_powder_white_i00","Moon Dust","Low Level Reagent"],
6024:["etc_potion_purpel_i00","Necroplasm","Low Level Reagent"],
6025:["etc_potion_green_i00","Demonplasm","Low Level Reagent"],
6026:["etc_powder_black_i00","Inferno Dust",""], 
6027:["etc_dragon_blood_i00","Draconic Essence","High Level Reagent"],
6028:["etc_dragons_blood_i00","Fire Essence","High Level Reagent"],
6029:["etc_mithril_ore_i00","Lunargent","High Level Reagent"],
6030:["etc_dragons_blood_i02","Midnight Oil","High Level Reagent"],
6031:["etc_dragons_blood_i05","Demonic Essence","High Level Reagent"],
6032:["etc_dragons_blood_i04","Abyss Oil","High Level Reagent"],
6033:["etc_luxury_wine_b_i00","Hellfire Oil","Highest Level Reagent"],
6034:["etc_luxury_wine_c_i00","Nightmare Oil","Highest Level Reagent"],
6320:["etc_broken_crystal_silver_i00","Pure Silver",""],
6321:["etc_broken_crystal_gold_i00","True Gold",""],
}
#Quest items
REAGENT_POUCH1,   REAGENT_POUCH2,REAGENT_POUCH3, REAGENT_BOX, \
WYRMS_BLOOD,      LAVA_STONE,    MOONSTONE_SHARD,ROTTEN_BONE, \
DEMONS_BLOOD,     INFERNIUM_ORE, BLOOD_ROOT,     VOLCANIC_ASH,\
QUICKSILVER,      SULFUR,        DRACOPLASM,     MAGMA_DUST,  \
MOON_DUST,        NECROPLASM,    DEMONPLASM,     INFERNO_DUST,\
DRACONIC_ESSENCE, FIRE_ESSENCE,  LUNARGENT,      MIDNIGHT_OIL,\
DEMONIC_ESSENCE,  ABYSS_OIL,     HELLFIRE_OIL,   NIGHTMARE_OIL=range(6007,6035)
MIXING_STONE1 = 5904
#Mimir's Elixir items
BLOOD_FIRE, MIMIRS_ELIXIR, PURE_SILVER, TRUE_GOLD = range(6318,6322)

MATS=range(6011,6032)+range(6320,6322)
#Messages
default   = "<html><head><body>I have nothing to say to you.</body></html>"
#NPCs
WESLEY,URN=30166,31149
#Mobs & Drop
DROPLIST = {
20813: [(QUICKSILVER,60),(ROTTEN_BONE,100)],
20822: [(VOLCANIC_ASH,40),(REAGENT_POUCH1,100)],
21061: [(DEMONS_BLOOD,70),(MOONSTONE_SHARD,90)],
20828: [(REAGENT_POUCH2,70),(QUICKSILVER,30)],
21066: [(REAGENT_BOX,40)],
21111: [(WYRMS_BLOOD,50)],
21115: [(REAGENT_POUCH3,50)]
}
#temperature:[success_%,reagent_qty_obtained]
TEMPERATURE={1:[100,1],2:[45,2],3:[15,3]}
#reagent:[ingredient,ingredient_qty,catalyst,catalyst_qty]
FORMULAS = {
DRACOPLASM:      [WYRMS_BLOOD,10,BLOOD_ROOT,1],     MAGMA_DUST:     [LAVA_STONE,10,VOLCANIC_ASH,1],MOON_DUST:[MOONSTONE_SHARD,10,VOLCANIC_ASH,1],
NECROPLASM:      [ROTTEN_BONE,10,BLOOD_ROOT,1],     DEMONPLASM:     [DEMONS_BLOOD,10,BLOOD_ROOT,1],INFERNO_DUST:[INFERNIUM_ORE,10,VOLCANIC_ASH,1],
DRACONIC_ESSENCE:[DRACOPLASM,10,QUICKSILVER,1],     FIRE_ESSENCE:   [MAGMA_DUST,10,SULFUR,1],      LUNARGENT:[MOON_DUST,10,QUICKSILVER,1],
MIDNIGHT_OIL:    [NECROPLASM,10,QUICKSILVER,1],     DEMONIC_ESSENCE:[DEMONPLASM,10,SULFUR,1],      ABYSS_OIL:[INFERNO_DUST,10,SULFUR,1],
HELLFIRE_OIL:    [FIRE_ESSENCE,1,DEMONIC_ESSENCE,1],NIGHTMARE_OIL:  [LUNARGENT,1,MIDNIGHT_OIL,1],  PURE_SILVER:[LUNARGENT,1,QUICKSILVER,1],
MIMIRS_ELIXIR:   [PURE_SILVER,1,TRUE_GOLD,1],
}

def render_shop(mode,item) :
    html = "<html><body><font color=\"LEVEL\">List:</font><table border=0 width=300>"
    if mode == "list" :
       for i in SHOP_LIST.keys() :
          html += "<tr><td width=35 height=45><img src=icon."+ITEMS[i][0]+" width=32 height=32 align=left></td><td valign=top><a action=\"bypass -h Quest 373_SupplierOfReagents _"+str(i)+"\"><font color=\"FFFFFF\">"+ITEMS[i][1]+" x"+str(SHOP_LIST[i][0])+"</font></a></td></tr>"
    else :
       html += "<tr><td align=left><font color=\"LEVEL\">Item Information</font></td><td align=right><button value=Back action=\"bypass -h Quest 373_SupplierOfReagents buy\" width=40 height=15 back=sek.cbui94 fore=sek.cbui92></td><td width=5><br></td></tr></table><table border=0 bgcolor=\"000000\" width=500 height=160><tr><td valign=top><table border=0><tr><td valign=top width=35><img src=icon."+ITEMS[item][0]+" width=32 height=32 align=left></td><td valign=top width=400><table border=0 width=100%><tr><td><font color=\"FFFFFF\">"+ITEMS[item][1]+" x"+str(SHOP_LIST[item][0])+"</font></td></tr><tr><td><font color=\"B09878\">"+ITEMS[item][2]+"</font></td></tr></table></td></tr></table><br><font color=\"LEVEL\">Item Required:</font><table border=0 bgcolor=\"000000\" width=500 height=120><tr><td valign=top><table border=0><tr><td valign=top width=35><img src=icon."+ITEMS[SHOP_LIST[item][1]][0]+" width=32 height=32 align=left></td><td valign=top width=400><table border=0 width=100%><tr><td><font color=\"FFFFFF\">"+ITEMS[SHOP_LIST[item][1]][1]+" x"+str(SHOP_LIST[item][2])+"</font></td></tr><tr><td><font color=\"B09878\">"+ITEMS[SHOP_LIST[item][1]][2]+"</font></td></tr></table></td></tr></table><br><table border=0 width=300><tr><td align=center><button value=Exchange action=\"bypass -h Quest 373_SupplierOfReagents "+str(item)+"\" width=60 height=15 back=sek.cbui94 fore=sek.cbui92></td></tr></table></td></tr></table></td></tr>"
    html += "</table></body></html>"
    return html

def render_urn(st, page) :
    stone,ingredient,catalyst = int(st.get("mixing")),int(st.get("ingredient")),int(st.get("catalyst"))
    if page == "Start" :
       html = "<html><body>Alchemists Mixing Urn:<br><table border=0 width=300><tr><tr><td width=50%><a action=\"bypass -h Quest 373_SupplierOfReagents U_M_MACT\">MACT Mixing Stone</a></td><td></td></tr><tr><td><a action=\"bypass -h Quest 373_SupplierOfReagents U_I_IACT\">IACT Ingredients</a></td><td>(current: INGR)</td></tr><tr><td><a action=\"bypass -h Quest 373_SupplierOfReagents U_C_CACT\">CACT Catalyst</a></td><td>(current: CATA)</td></tr><tr><td><a action=\"bypass -h Quest 373_SupplierOfReagents 31149-5.htm\">Select Temperature</a></td><td>(current: TEMP)</td></tr><tr><td><a action=\"bypass -h Quest 373_SupplierOfReagents 31149-6.htm\">Mix Ingredients</a></td><td></td></tr></table></body></html>"
       ingr,cata,temp=int(st.get("ingredient")),int(st.get("catalyst")),st.get("temp")
       if ingr : ingr = ITEMS[ingr][1]+"x"+st.get("i_qty")
       else : ingr = "None"
       if cata : cata = ITEMS[cata][1]+"x"+st.get("c_qty")
       else : cata = "None"
       html = html.replace("INGR",ingr).replace("CATA",cata).replace("TEMP",temp)
       if stone : html = html.replace("MACT","Retrieve")
       else : html = html.replace("MACT","Insert")
       if ingredient : html = html.replace("IACT","Retrieve")
       else : html = html.replace("IACT","Insert")
       if catalyst : html = html.replace("CACT","Retrieve")
       else : html = html.replace("CACT","Insert")
    elif isinstance(page,list) :
       html = "<html><body>Insert:<table border=0>"
       for i in MATS :
          html += "<tr><td height=45><img src=icon."+ITEMS[i][0]+" height=32 width=32></td><td>"+ITEMS[i][1]+"</td><td><button value=X1 action=\"bypass -h Quest 373_SupplierOfReagents x_1_"+page[1]+"_"+str(i)+"\" width=40 height=15 fore=sek.cbui92><button value=X10 action=\"bypass -h Quest 373_SupplierOfReagents x_2_"+page[1]+"_"+str(i)+"\" width=40 height=15 fore=sek.cbui92></td></tr>"
       html += "</table><center><a action=\"bypass -h Quest 373_SupplierOfReagents urn\">Back</a></center></body></html>"
    return html

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    id = st.getState() 
    htmltext = event
    if event == "30166-4.htm" :
       st.setState(STARTED)
       st.set("cond","1")
       st.set("ingredient","0")
       st.set("catalyst","0")
       st.set("i_qty","0")
       st.set("c_qty","0")
       st.set("temp","0")
       st.set("mixing","0")
       st.giveItems(6317,1)
       st.giveItems(5904,1)
       st.playSound("ItemSound.quest_accept")
    elif event == "30166-5.htm" :
       for i in range(6007,6035)+[6317,5904] : 
          st.takeItems(i,-1)
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
    elif event == "urn" :
        htmltext = render_urn(st,"Start")
    elif event == "buy" :
       htmltext = render_shop("list",None)
    elif event.startswith("_") :
       htmltext = render_shop("item",int(event.lstrip("_")))
    elif event.isdigit() and int(event) in SHOP_LIST.keys() :
       item = int(event)
       if st.getQuestItemsCount(SHOP_LIST[item][1]) >= SHOP_LIST[item][2] :
          st.takeItems(SHOP_LIST[item][1],SHOP_LIST[item][2])
          st.giveItems(item,SHOP_LIST[item][0])
          htmltext = render_shop("item",item)
       else :
          htmltext = "You don't have enough materials"
    elif event.startswith("U_") :
       event = event.split("_")
       if event[1]=="M" :
          if event[2] == "Insert" :
              if st.getQuestItemsCount(MIXING_STONE1) :
                 st.takeItems(MIXING_STONE1,-1)
                 st.set("mixing","1")
                 htmltext = "31149-2.htm"
              else :
                 htmltext = "You don't have a mixing stone."
          elif event[2] == "Retrieve" :
              if int(st.get("mixing")) :
                 st.set("mixing","0")
                 st.set("temp","0")
                 st.giveItems(MIXING_STONE1,1)
                 if int(st.get("ingredient")) or int(st.get("catalyst")) :
                     htmltext = "31149-2c.htm"
                 else :
                     htmltext = "31149-2a.htm"
              else :
                 htmltext = "31149-2b.htm"
       elif event[2] == "Insert" :
          htmltext = render_urn(st,event)
       elif event[2] == "Retrieve" :
          if event[1] == "I" :
             item=int(st.get("ingredient"))
             qty =int(st.get("i_qty"))
             st.set("ingredient","0")
             st.set("i_qty","0")
          elif event[1] == "C" :
             item=int(st.get("catalyst"))
             qty =int(st.get("c_qty"))
             st.set("catalyst","0")
             st.set("c_qty","0")
          if item and qty :
             st.giveItems(item,qty)
             htmltext="31149-3a.htm"
          else :
             htmltext = "31149-3b.htm" 
    elif event.startswith("x_") :
       x,qty,dst,item=event.split("_")
       if qty=="2": qty="10"
       if st.getQuestItemsCount(int(item)) >= int(qty) :
          if dst == "I" :
             dest = "ingredient"
             count= "i_qty"
          else :
             dest = "catalyst"
             count= "c_qty"
          st.takeItems(int(item),int(qty))
          st.set(dest,item)
          st.set(count,qty)
          htmltext = "31149-4a.htm"
       else :
          htmltext = "31149-4b.htm"
    elif event.startswith("tmp_") :
       st.set("temp",event.split("_")[1])
       htmltext = "31149-5a.htm"
    elif event == "31149-6.htm" :
       if int(st.get("mixing")) :
          temp=int(st.get("temp"))
          if temp :
             ingredient,catalyst,iq,cq = int(st.get("ingredient")),int(st.get("catalyst")),int(st.get("i_qty")),int(st.get("c_qty"))
             st.set("ingredient","0")
             st.set("i_qty","0")
             st.set("catalyst","0")
             st.set("c_qty","0")
             st.set("temp","0")
             item=0
             for i in FORMULAS :
                 if [ingredient,iq,catalyst,cq] == FORMULAS[i] :
                    item=i
                    break
             if item == PURE_SILVER and temp != 1: return "31149-7c.htm"
             if item == MIMIRS_ELIXIR :
                if temp == 3 :
                  if st.getQuestItemsCount(BLOOD_FIRE) :
                     st.takeItems(BLOOD_FIRE,1)
                  else :
                     return "31149-7a.htm"
                else :
                  return "31149-7b.htm"
             if item :
                chance,qty=TEMPERATURE[temp]
                if item == MIMIRS_ELIXIR :
                   mimirs=st.getPlayer().getQuestState("235_MimirsElixir")
                   if mimirs :
                      chance = 100
                      qty = 1
                      mimirs.set("cond","8")
                   else :
                      return "31149-7d.htm"
                if st.getRandom(100) < chance :
                   st.giveItems(item,qty)
                else :
                   htmltext = "31149-6c.htm"
             else :
                htmltext = "31149-6d.htm"
          else :
             htmltext = "31149-6b.htm"
       else :
          htmltext="31149-6a.htm"
    return htmltext

 def onTalk (self,npc,player):
   htmltext = default
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId == WESLEY :
      if id == CREATED :
         st.set("cond","0")
         htmltext = "30166-1.htm"
         if st.getPlayer().getLevel() < 57 :
            st.exitQuest(1)
            htmltext = "30166-2.htm"
      else :
         htmltext = "30166-3.htm"
   elif id == STARTED :
      htmltext = render_urn(st,"Start")
   return htmltext

 def onKill (self,npc,player) :
     partyMember = self.getRandomPartyMemberState(player, STARTED)
     if not partyMember : return
     st = partyMember.getQuestState(qn)
     npcId = npc.getNpcId()
     drop = st.getRandom(100)
     for entry in DROPLIST[npcId] :
        item,chance=entry
        numItems,chance = divmod(chance*Config.RATE_DROP_QUEST,100)
        if drop < chance :
           numItems = numItems +1
        numItems = int(numItems)
        st.giveItems(item,numItems)
        if numItems != 0 :
           st.playSound("ItemSound.quest_itemget")
        break
     return

# Quest class and state definition
QUEST       = Quest(QUEST_NUMBER, str(QUEST_NUMBER)+"_"+QUEST_NAME, QUEST_DESCRIPTION)

CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
# Quest NPC starter initialization
QUEST.addStartNpc(WESLEY)
# Quest initialization
QUEST.addTalkId(WESLEY)

QUEST.addTalkId(URN)

for i in DROPLIST.keys():
  QUEST.addKillId(i)

for i in range(6007,6035)+[6317,5904] :
  STARTED.addQuestDrop(31149,i,1)

print str(QUEST_NUMBER)+": "+QUEST_DESCRIPTION
