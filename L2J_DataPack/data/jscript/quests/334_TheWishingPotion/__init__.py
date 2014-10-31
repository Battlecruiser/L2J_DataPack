#
# Created by DraX on 2005.09.08
#

import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "334_TheWishingPotion"

#### CONSTANTS
####

#rewards
ADENA_ID                  =   57
DEMONS_TUNIC_ID           =  441
DEMONS_STOCKINGS_ID       =  472
SCROLL_OF_ESCAPE_ID       =  736
NECKLACE_OF_GRACE_ID      =  931
SPELLBOOK_ICEBOLT_ID      = 1049
SPELLBOOK_BATTLEHEAL_ID   = 1050
DEMONS_BOOTS_ID           = 2435
DEMONS_GLOVES_ID          = 2459
WISH_POTION_ID            = 3467
ANCIENT_CROWN_ID          = 3468
CERTIFICATE_OF_ROYALTY_ID = 3469
GOLD_BAR_ID               = 3470
ALCHEMY_TEXT_ID           = 3678
SECRET_BOOK_ID            = 3679
POTION_RECIPE_1_ID        = 3680
POTION_RECIPE_2_ID        = 3681
MATILDS_ORB_ID            = 3682
FORBIDDEN_LOVE_SCROLL_ID  = 3683
HEART_OF_PAAGRIO_ID       = 3943
#quest items (ingredients)
AMBER_SCALE_ID            = 3684
WIND_SOULSTONE_ID         = 3685
GLASS_EYE_ID              = 3686
HORROR_ECTOPLASM_ID       = 3687
SILENOS_HORN_ID           = 3688
ANT_SOLDIER_APHID_ID      = 3689
TYRANTS_CHITIN_ID         = 3690
BUGBEAR_BLOOD_ID          = 3691
#npcs
GRIMA                     = 27135
SUCCUBUS_OF_SEDUCTION     = 27136
GREAT_DEMON_KING          = 27138
SECRET_KEEPER_TREE        = 27139
SANCHES                   = 27153
BONAPARTERIUS             = 27154
RAMSEBALIUS               = 27155
TORAI                     = 30557
ALCHEMIST_MATILD          = 30738
RUPINA                    = 30742
WISDOM_CHEST              = 30743
#mobs
WHISPERING_WIND           = 20078
ANT_RECRUIT               = 20082
ANT_WARRIOR_CAPTAIN       = 20088
SILENOS                   = 20168
TYRANT                    = 20192
TYRANT_KINGPIN            = 20193
AMBER_BASILISK            = 20199
HORROR_MIST_RIPPER        = 20227
TURAK_BUGBEAR             = 20248
TURAK_BUGBEAR_WARRIOR     = 20249
GLASS_JAGUAR              = 20250

### DROP CHANCE CONFIGURATION 
###
### 100 = 100% ( every kill/drop )
###

MAX_VALUE                            = 100
DROP_CHANCE_AMBER_SCALE_ID           = 15
DROP_CHANCE_WIND_SOULSTONE_ID        = 20
DROP_CHANCE_GLASS_EYE_ID             = 35
DROP_CHANCE_HORROR_ECTOPLASM_ID      = 15
DROP_CHANCE_SILENOS_HORN_ID          = 30
DROP_CHANCE_ANT_SOLDIER_APHID_ID     = 40
DROP_CHANCE_TYRANTS_CHITIN_ID        = 50
DROP_CHANCE_BUGBEAR_BLOOD_ID         = 25
DROP_CHANCE_FORBIDDEN_LOVE_SCROLL_ID = 3
DROP_CHANCE_NECKLACE_OF_GRACE_ID     = 4
DROP_CHANCE_GOLD_BAR_ID              = 10

def check_ingredients(st,amber,wind,glass,ecto,horn,aphid,chit,blood) :
    if st.getQuestItemsCount(AMBER_SCALE_ID) != amber : return 0
    if st.getQuestItemsCount(WIND_SOULSTONE_ID) != wind : return 0
    if st.getQuestItemsCount(GLASS_EYE_ID) != glass : return 0
    if st.getQuestItemsCount(HORROR_ECTOPLASM_ID) != ecto : return 0
    if st.getQuestItemsCount(SILENOS_HORN_ID) != horn : return 0
    if st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) != aphid : return 0
    if st.getQuestItemsCount(TYRANTS_CHITIN_ID) != chit : return 0
    if st.getQuestItemsCount(BUGBEAR_BLOOD_ID) != blood : return 0
    return 1

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = event

   if st.get("cond") == None: st.set("cond","0")

   if event == "30738-03.htm":
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
     if st.getQuestItemsCount(ALCHEMY_TEXT_ID) >= 2: st.takeItems(ALCHEMY_TEXT_ID,-1)
     if st.getQuestItemsCount(ALCHEMY_TEXT_ID) == 0: st.giveItems(ALCHEMY_TEXT_ID,1)
     htmltext = "30738-03.htm"
   if event == "30738-06.htm":
     # first time 
     if st.getQuestItemsCount(MATILDS_ORB_ID) == 0 and st.getQuestItemsCount(WISH_POTION_ID) == 0:
       st.playSound("ItemSound.quest_accept")
       st.set("cond","2")
       st.setState(MIDDLE)
       if st.getQuestItemsCount(ALCHEMY_TEXT_ID) >= 1: st.takeItems(ALCHEMY_TEXT_ID,-1)
       if st.getQuestItemsCount(SECRET_BOOK_ID) >= 1: st.takeItems(SECRET_BOOK_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_1_ID) >= 2: st.takeItems(POTION_RECIPE_1_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_1_ID) == 0: st.giveItems(POTION_RECIPE_1_ID,1)
       if st.getQuestItemsCount(POTION_RECIPE_2_ID) >= 2: st.takeItems(POTION_RECIPE_2_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_2_ID) == 0: st.giveItems(POTION_RECIPE_2_ID,1)
       htmltext = "30738-06.htm"
     # you did already this quest
     if st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and st.getQuestItemsCount(WISH_POTION_ID) == 0:
       st.playSound("ItemSound.quest_accept")
       st.set("cond","2")
       st.setState(MIDDLE)
       if st.getQuestItemsCount(ALCHEMY_TEXT_ID) >= 1: st.takeItems(ALCHEMY_TEXT_ID,-1)
       if st.getQuestItemsCount(SECRET_BOOK_ID) >= 1: st.takeItems(SECRET_BOOK_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_1_ID) >= 2: st.takeItems(POTION_RECIPE_1_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_1_ID) == 0: st.giveItems(POTION_RECIPE_1_ID,1)
       if st.getQuestItemsCount(POTION_RECIPE_2_ID) >= 2: st.takeItems(POTION_RECIPE_2_ID,-1)
       if st.getQuestItemsCount(POTION_RECIPE_2_ID) == 0: st.giveItems(POTION_RECIPE_2_ID,1)
       htmltext = "30738-12.htm"
     # you did already this quest, but you dont have taken your wish potion yet
     if st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       htmltext = "30738-13.htm"
   if event == "30738-10.htm":
     if check_ingredients(st,1,1,1,1,1,1,1,1) :
       st.playSound("ItemSound.quest_finish")
       st.takeItems(ALCHEMY_TEXT_ID,-1)
       st.takeItems(SECRET_BOOK_ID,-1)
       st.takeItems(POTION_RECIPE_1_ID,-1)
       st.takeItems(POTION_RECIPE_2_ID,-1)
       st.takeItems(AMBER_SCALE_ID,-1)
       st.takeItems(WIND_SOULSTONE_ID,-1)
       st.takeItems(GLASS_EYE_ID,-1)
       st.takeItems(HORROR_ECTOPLASM_ID,-1)
       st.takeItems(SILENOS_HORN_ID,-1)
       st.takeItems(ANT_SOLDIER_APHID_ID,-1)
       st.takeItems(TYRANTS_CHITIN_ID,-1)
       st.takeItems(BUGBEAR_BLOOD_ID,-1)
       if st.getQuestItemsCount(MATILDS_ORB_ID) == 0 : st.giveItems(MATILDS_ORB_ID,1)
       st.giveItems(WISH_POTION_ID,1)
       st.set("cond","0")
     else :
       htmltext="You don't have required items"
   if event == "30738-14.htm":
     if st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       htmltext = "30738-15.htm"
     # if you dropped or destroyed your wish potion, you are not able to see the list
     else:
       htmltext = "30738-14.htm"
#### WISH I : Please make me into a loving person.
   if event == "30738-16.htm":
     # if you dropped or destroyed your wish potion, you are not able to make a wish
     if st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       ### autochat should begin here !!!!
       st.takeItems(WISH_POTION_ID,1)
       WISH_CHANCE = st.getRandom(100)
       if WISH_CHANCE <= 50:
         st.getPcSpawn().addSpawn(SUCCUBUS_OF_SEDUCTION)
         st.getPcSpawn().addSpawn(SUCCUBUS_OF_SEDUCTION)
         st.getPcSpawn().addSpawn(SUCCUBUS_OF_SEDUCTION)
       else:
         st.getPcSpawn().addSpawn(RUPINA)
       htmltext = "30738-16.htm"
     else:
       htmltext = "30738-14.htm"
#### WISH II : I want to become an extremely rich person. How about 100 million adena?! 
   if event == "30738-17.htm":
     # if you dropped or destroyed your wish potion, you are not able to make a wish
     if st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       ### autochat should begin here !!!!
       st.takeItems(WISH_POTION_ID,1)
       WISH_CHANCE = st.getRandom(100)
       if WISH_CHANCE <= 33:
         st.getPcSpawn().addSpawn(GRIMA)
         st.getPcSpawn().addSpawn(GRIMA)
         st.getPcSpawn().addSpawn(GRIMA)
       elif WISH_CHANCE >= 66:
         st.giveItems(ADENA_ID,10000)
       else:
         if st.getRandom(100) <= 1:
           st.giveItems(ADENA_ID,((st.getRandom(9)+1)*1000000))
         else:
           st.getPcSpawn().addSpawn(GRIMA)
           st.getPcSpawn().addSpawn(GRIMA)
           st.getPcSpawn().addSpawn(GRIMA)
       htmltext = "30738-17.htm"
     else:
       htmltext = "30738-14.htm"
#### WISH III : I want to be a king in this world.
   if event == "30738-18.htm":
     # if you dropped or destroyed your wish potion, you are not able to make a wish
     if st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       ### autochat should begin here !!!!
       st.takeItems(WISH_POTION_ID,1)
       WISH_CHANCE = st.getRandom(100)
       if WISH_CHANCE <= 33:
         st.giveItems(CERTIFICATE_OF_ROYALTY_ID,1)
       elif WISH_CHANCE >= 66:
         st.giveItems(ANCIENT_CROWN_ID,1)
       else:
         st.getPcSpawn().addSpawn(SANCHES)
       htmltext = "30738-18.htm"
     else:
       htmltext = "30738-14.htm"
#### WISH IV : I'd like to become the wisest person in the world.
   if event == "30738-19.htm":
     # if you dropped or destroyed your wish potion, you are not able to make a wish
     if st.getQuestItemsCount(WISH_POTION_ID) >= 1:
       ### autochat should begin here !!!!
       st.takeItems(WISH_POTION_ID,1)
       WISH_CHANCE = st.getRandom(100)
       if WISH_CHANCE <= 33:
         st.giveItems(SPELLBOOK_ICEBOLT_ID,1)
       elif WISH_CHANCE >= 66:
         st.giveItems(SPELLBOOK_BATTLEHEAL_ID,1)
       else:
         st.getPcSpawn().addSpawn(WISDOM_CHEST,69988,18197,-3647)
       htmltext = "30738-19.htm"
     else:
       htmltext = "30738-14.htm"
   return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId in [RUPINA,WISDOM_CHEST] and id in [CREATED,STARTED] : return htmltext
   if npcId == TORAI and id in [MIDDLE,END] : return htmltext
   if npcId == TORAI:
     if st.get("cond") == None or int(st.get("cond")) == 0:
       st.exitQuest(1)
     if st.getQuestItemsCount(FORBIDDEN_LOVE_SCROLL_ID) >= 1:
       st.takeItems(FORBIDDEN_LOVE_SCROLL_ID,1)     
       st.giveItems(ADENA_ID,500000)
       return "30557-01.htm"
     else:
       return "no_quest.htm"

   if npcId == WISDOM_CHEST:
     DROP_CHANCE = st.getRandom(100)
     if DROP_CHANCE <= 20:
       st.giveItems(SPELLBOOK_ICEBOLT_ID,1)
       st.giveItems(SPELLBOOK_BATTLEHEAL_ID,1)
       st.getPlayer().getTarget().decayMe()
       return "30743-06.htm"
     elif DROP_CHANCE <= 30:
       st.giveItems(HEART_OF_PAAGRIO_ID,1)
       st.getPlayer().getTarget().decayMe()
       return "30743-06.htm"
     else:
       st.getPlayer().getTarget().decayMe()
       return "30743-0" + str(st.getRandom(4)+1) + ".htm"

   if npcId == RUPINA:
     DROP_CHANCE = st.getRandom(MAX_VALUE)
     if DROP_CHANCE <= DROP_CHANCE_NECKLACE_OF_GRACE_ID:
       st.giveItems(NECKLACE_OF_GRACE_ID,1)
     else:
       st.giveItems(SCROLL_OF_ESCAPE_ID,1)
     st.getPlayer().getTarget().decayMe()
     return "30742-01.htm"

   if npcId == ALCHEMIST_MATILD:
     if st.getPlayer().getLevel() <= 29:
       st.exitQuest(1)
       return "30738-21.htm"
     else:
       if st.getState() == CREATED:
         st.set("cond","0")
       if st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and check_ingredients(st,0,0,0,0,0,0,0,0) and st.getState() != MIDDLE:
         if st.get("cond") == None:
           st.set("cond","0")
         return "30738-11.htm"
       elif st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and check_ingredients(st,1,1,1,1,1,1,1,1):
         return "30738-08.htm"
       elif st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and st.getQuestItemsCount(WISH_POTION_ID) == 0 and st.getState() != MIDDLE:
         st.set("cond","2")
         st.setState(MIDDLE)
         if st.getQuestItemsCount(POTION_RECIPE_1_ID) == 0: st.giveItems(POTION_RECIPE_1_ID,1)
         if st.getQuestItemsCount(POTION_RECIPE_2_ID) == 0: st.giveItems(POTION_RECIPE_2_ID,1)
         return "30738-12.htm"
       elif st.getState() == END and not check_ingredients(st,1,1,1,1,1,1,1,1):
         return "30738-07.htm"       
       elif check_ingredients(st,1,1,1,1,1,1,1,1):
         return "30738-08.htm"
       elif st.getQuestItemsCount(ALCHEMY_TEXT_ID) >= 1 and st.getQuestItemsCount(SECRET_BOOK_ID) >= 1:
         return "30738-05.htm"
       elif st.getState() == MIDDLE and st.getQuestItemsCount(MATILDS_ORB_ID) >= 1 and st.getQuestItemsCount(POTION_RECIPE_1_ID) == 0 and st.getQuestItemsCount(POTION_RECIPE_2_ID) == 0:
         return "30738-11.htm"
       elif st.getState() == MIDDLE and st.getQuestItemsCount(POTION_RECIPE_1_ID) >= 1 and st.getQuestItemsCount(POTION_RECIPE_2_ID) >= 1:
         return "30738-07.htm"
       elif st.getState() == STARTED and st.getQuestItemsCount(ALCHEMY_TEXT_ID) >= 1 and st.getQuestItemsCount(SECRET_BOOK_ID) == 0:
         return "30738-04.htm"
       else:
         return "30738-01.htm"

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   

   npcId = npc.getNpcId()
   id = st.getState()
   # hm, you already collected all items. but now you have lost one (destroyed maybe). will give you a second try 
   if id == END:
     if npcId in [AMBER_BASILISK, WHISPERING_WIND, GLASS_JAGUAR, HORROR_MIST_RIPPER, SILENOS, ANT_RECRUIT, ANT_WARRIOR_CAPTAIN, TYRANT, TYRANT_KINGPIN, TURAK_BUGBEAR, TURAK_BUGBEAR_WARRIOR]:
       if not check_ingredients(st,1,1,1,1,1,1,1,1): st.setState(MIDDLE)
    # todo: Shouldn't SUCCUBUS_OF_SEDUCTION be handled here, too?
   elif id == STARTED:
     if npcId == SECRET_KEEPER_TREE:
       if st.getQuestItemsCount(SECRET_BOOK_ID) == 0:
         st.giveItems(SECRET_BOOK_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif id == MIDDLE and st.getQuestItemsCount(POTION_RECIPE_1_ID) >= 1 and st.getQuestItemsCount(POTION_RECIPE_2_ID) >= 1: 
     DROP_CHANCE = st.getRandom(MAX_VALUE)
     if npcId == AMBER_BASILISK:
       if (st.getQuestItemsCount(AMBER_SCALE_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_AMBER_SCALE_ID): 
         st.giveItems(AMBER_SCALE_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")          
     elif npcId == WHISPERING_WIND:
       if (st.getQuestItemsCount(WIND_SOULSTONE_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_WIND_SOULSTONE_ID):
         st.giveItems(WIND_SOULSTONE_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == GLASS_JAGUAR:
       if (st.getQuestItemsCount(GLASS_EYE_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_GLASS_EYE_ID):
         st.giveItems(GLASS_EYE_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == HORROR_MIST_RIPPER:
       if (st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_HORROR_ECTOPLASM_ID):
         st.giveItems(HORROR_ECTOPLASM_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == SILENOS:
       if (st.getQuestItemsCount(SILENOS_HORN_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_SILENOS_HORN_ID):
         st.giveItems(SILENOS_HORN_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == ANT_RECRUIT or npcId == ANT_WARRIOR_CAPTAIN:
       if (st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_ANT_SOLDIER_APHID_ID):
         st.giveItems(ANT_SOLDIER_APHID_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == TYRANT or npcId == TYRANT_KINGPIN:
       if (st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_TYRANTS_CHITIN_ID):
         st.giveItems(TYRANTS_CHITIN_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
     elif npcId == TURAK_BUGBEAR or npcId == TURAK_BUGBEAR_WARRIOR:
       if (st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 0) and (DROP_CHANCE <= DROP_CHANCE_BUGBEAR_BLOOD_ID):
         st.giveItems(BUGBEAR_BLOOD_ID,1)
         if check_ingredients(st,1,1,1,1,1,1,1,1):
           st.playSound("ItemSound.quest_middle")
           st.setState(END)
         else: st.playSound("ItemSound.quest_itemget")
   elif id == MIDDLE :
     DROP_CHANCE = st.getRandom(MAX_VALUE)
     if npcId == SUCCUBUS_OF_SEDUCTION:
       if DROP_CHANCE <= DROP_CHANCE_FORBIDDEN_LOVE_SCROLL_ID:
         st.playSound("ItemSound.quest_itemget")
         st.giveItems(FORBIDDEN_LOVE_SCROLL_ID,1)
     elif npcId == GRIMA:
       if DROP_CHANCE <= DROP_CHANCE_GOLD_BAR_ID:
         st.playSound("ItemSound.quest_itemget") 
         st.giveItems(GOLD_BAR_ID,st.getRandom(4)+1)
     elif npcId == SANCHES:
       if st.getRandom(100) <= 50:
         st.getPcSpawn().addSpawn(BONAPARTERIUS)
     elif npcId == BONAPARTERIUS:
       if st.getRandom(100) <= 50:
         st.getPcSpawn().addSpawn(RAMSEBALIUS)
     elif npcId == RAMSEBALIUS:
       if st.getRandom(100) <= 50:
         st.getPcSpawn().addSpawn(GREAT_DEMON_KING)
     elif npcId == GREAT_DEMON_KING:
       if st.getRandom(100) <= 50:
         DEMON_DROP_CHANCE = st.getRandom(100)
         if DEMON_DROP_CHANCE <= 20:
           st.playSound("ItemSound.quest_itemget") 
           st.giveItems(DEMONS_BOOTS_ID,1)
         elif DEMON_DROP_CHANCE <= 40:
           st.playSound("ItemSound.quest_itemget") 
           st.giveItems(DEMONS_GLOVES_ID,1)
         elif DEMON_DROP_CHANCE <= 60:
           st.playSound("ItemSound.quest_itemget") 
           st.giveItems(DEMONS_STOCKINGS_ID,1)
         elif DEMON_DROP_CHANCE <= 80:
           st.playSound("ItemSound.quest_itemget") 
           st.giveItems(DEMONS_TUNIC_ID,1)
   return


QUEST     = Quest(334,qn,"The Wishing Potion")
CREATED   = State('Start',     QUEST)
STARTED   = State('started',   QUEST)
MIDDLE    = State('middle',    QUEST)
END       = State('end',       QUEST)
COMPLETED = State('completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(ALCHEMIST_MATILD)
QUEST.addStartNpc(TORAI)

QUEST.addTalkId(ALCHEMIST_MATILD)
QUEST.addTalkId(TORAI)

QUEST.addTalkId(RUPINA)
QUEST.addTalkId(WISDOM_CHEST)

QUEST.addKillId(SECRET_KEEPER_TREE)

QUEST.addKillId(AMBER_BASILISK)
QUEST.addKillId(WHISPERING_WIND)
QUEST.addKillId(GLASS_JAGUAR)
QUEST.addKillId(HORROR_MIST_RIPPER)
QUEST.addKillId(SILENOS)
QUEST.addKillId(ANT_RECRUIT)
QUEST.addKillId(ANT_WARRIOR_CAPTAIN)
QUEST.addKillId(TYRANT)
QUEST.addKillId(TYRANT_KINGPIN)
QUEST.addKillId(TURAK_BUGBEAR)
QUEST.addKillId(TURAK_BUGBEAR_WARRIOR)

QUEST.addKillId(SUCCUBUS_OF_SEDUCTION)
# END.addKillId(SUCCUBUS_OF_SEDUCTION) todo: This was in the old code but was not handled...

QUEST.addKillId(GRIMA)
QUEST.addKillId(SANCHES)
QUEST.addKillId(RAMSEBALIUS)
QUEST.addKillId(BONAPARTERIUS)
QUEST.addKillId(GREAT_DEMON_KING)

QUEST.addKillId(GRIMA)
QUEST.addKillId(SANCHES)
QUEST.addKillId(RAMSEBALIUS)
QUEST.addKillId(BONAPARTERIUS)
QUEST.addKillId(GREAT_DEMON_KING)

QUEST.addKillId(AMBER_BASILISK)
QUEST.addKillId(WHISPERING_WIND)
QUEST.addKillId(GLASS_JAGUAR)
QUEST.addKillId(HORROR_MIST_RIPPER)
QUEST.addKillId(SILENOS)
QUEST.addKillId(ANT_RECRUIT)
QUEST.addKillId(ANT_WARRIOR_CAPTAIN)
QUEST.addKillId(TYRANT)
QUEST.addKillId(TYRANT_KINGPIN)
QUEST.addKillId(TURAK_BUGBEAR)
QUEST.addKillId(TURAK_BUGBEAR_WARRIOR)

STARTED.addQuestDrop(ALCHEMIST_MATILD,ALCHEMY_TEXT_ID,1)
STARTED.addQuestDrop(SECRET_KEEPER_TREE,SECRET_BOOK_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,POTION_RECIPE_1_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,POTION_RECIPE_2_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,MATILDS_ORB_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,AMBER_SCALE_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,WIND_SOULSTONE_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,GLASS_EYE_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,HORROR_ECTOPLASM_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,SILENOS_HORN_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,ANT_SOLDIER_APHID_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,TYRANTS_CHITIN_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,BUGBEAR_BLOOD_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,AMBER_SCALE_ID,1)
MIDDLE.addQuestDrop(ALCHEMIST_MATILD,AMBER_SCALE_ID,1)

print "importing quests: 334: The Wishing Potion"
