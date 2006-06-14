# Made by Mr. Have fun!
# Version 0.3 by H1GHL4ND3R
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_TRUST_ID = 2734
LETTER_TO_ELF_ID = 1558
LETTER_TO_DARKELF_ID = 1556

LETTER_TO_DWARF_ID,           LETTER_TO_ORC_ID,        LETTER_TO_SERESIN_ID,  SCROLL_OF_DARKELF_TRUST_ID, \
SCROLL_OF_ELF_TRUST_ID,       SCROLL_OF_DWARF_TRUST_ID,SCROLL_OF_ORC_TRUST_ID,RECOMMENDATION_OF_HOLLIN_ID,\
ORDER_OF_OZZY_ID,             BREATH_OF_WINDS_ID,      SEED_OF_VERDURE_ID,    LETTER_OF_THIFIELL_ID,      \
BLOOD_OF_GUARDIAN_BASILISK_ID,GIANT_APHID_ID,          STAKATOS_FLUIDS_ID,    BASILISK_PLASMA_ID,         \
HONEY_DEW_ID,                 STAKATO_ICHOR_ID,        ORDER_OF_CLAYTON_ID,   PARASITE_OF_LOTA_ID,        \
LETTER_TO_MANAKIA_ID,         LETTER_OF_MANAKIA_ID,    LETTER_TO_NICHOLA_ID,  ORDER_OF_NICHOLA_ID,        \
HEART_OF_PORTA_ID = range(2737,2762)

DROPLIST={
# For condition 2
5120:[ORDER_OF_OZZY_ID,BREATH_OF_WINDS_ID,               1],
5121:[ORDER_OF_OZZY_ID,SEED_OF_VERDURE_ID,               1],
# For condition 6
550 :[ORDER_OF_CLAYTON_ID,BLOOD_OF_GUARDIAN_BASILISK_ID,10],
82  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
84  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
86  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
87  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
88  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
157 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
230 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
232 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
234 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
# For condition 19
213 :[ORDER_OF_NICHOLA_ID,HEART_OF_PORTA_ID,            10]
}


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7191-04.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(LETTER_TO_ELF_ID,1)
      st.giveItems(LETTER_TO_DARKELF_ID,1)
    elif event == "7154-03.htm" :
      st.takeItems(LETTER_TO_ELF_ID,1)
      st.giveItems(ORDER_OF_OZZY_ID,1)
      st.set("cond","2")
    elif event == "7358-02.htm" :
      st.takeItems(LETTER_TO_DARKELF_ID,1)
      st.giveItems(LETTER_OF_THIFIELL_ID,1)
      st.set("cond","5")
    elif event == "7657-03.htm" :
      if st.getPlayer().getLevel() >= 38 :                 # Condition 12 meet the Lord Kakai (Orc Master)
        st.takeItems(LETTER_TO_SERESIN_ID,1)
        st.giveItems(LETTER_TO_ORC_ID,1)
        st.giveItems(LETTER_TO_DWARF_ID,1)
        st.set("cond","12")
      else:                                                # Condition 11 A lack of Experience
        htmltext = "7657-02.htm"
        st.set("cond","11")
    elif event == "7565-02.htm" :
      st.takeItems(LETTER_TO_ORC_ID,1)
      st.giveItems(LETTER_TO_MANAKIA_ID,1)
      st.set("cond","13")
    elif event == "7515-02.htm" :
      st.takeItems(LETTER_TO_MANAKIA_ID,1)
      st.set("cond","14")
    elif event == "7531-02.htm" :
      st.takeItems(LETTER_TO_DWARF_ID,1)
      st.giveItems(LETTER_TO_NICHOLA_ID,1)
      st.set("cond","18")
    elif event == "7621-02.htm" :
      st.takeItems(LETTER_TO_NICHOLA_ID,1)
      st.giveItems(ORDER_OF_NICHOLA_ID,1)
      st.set("cond","19")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :                                      # Check if is starting the quest
     st.set("cond","0")
     st.set("id","0")
     if npcId == 7191 :
       if st.getPlayer().getRace().ordinal() == 0 :
         if st.getPlayer().getLevel() >= 37 :
           htmltext = "7191-03.htm"
         else:
           htmltext = "7191-01.htm"
           st.exitQuest(1)
       else:
         htmltext = "7191-02.htm"
         st.exitQuest(1)
   elif id == COMPLETED :                                  # Check if the quest is already made
      if npcId == 7191 :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   else :                                                  # The quest it self
     try :
       cond = int(st.get("cond"))
     except :
       cond = None
     if cond == 1 :                                        # Condition 1 take the letter to Hierarch Asterios (Elven Master)
         if npcId == 7191 :
           htmltext = "7191-08.htm"
         elif npcId == 7154 and st.getQuestItemsCount(LETTER_TO_ELF_ID) :
           htmltext = "7154-01.htm"
     elif cond == 2 :                                      # Condition 2 kill the Luel of Zephy and Aktea of the Woods
         if npcId == 7154 and st.getQuestItemsCount(ORDER_OF_OZZY_ID) :
           htmltext = "7154-04.htm"
     elif cond == 3 :                                      # Condition 3 bring back the Breath of winds and Seed of Verdure to Asterios
         if npcId == 7154 and st.getQuestItemsCount(BREATH_OF_WINDS_ID) and st.getQuestItemsCount(SEED_OF_VERDURE_ID) :
           htmltext = "7154-05.htm"
           st.takeItems(BREATH_OF_WINDS_ID,1)
           st.takeItems(SEED_OF_VERDURE_ID,1)
           st.takeItems(ORDER_OF_OZZY_ID,1)
           st.giveItems(SCROLL_OF_ELF_TRUST_ID,1)
           st.set("cond","4")
     elif cond == 4 :                                      # Condition 4 take the letter to Tetrarch Thifiell (Dark Elven Master)
         if npcId == 7154 :
           htmltext = "7154-06.htm"
         elif npcId == 7358 and st.getQuestItemsCount(LETTER_TO_DARKELF_ID) :
           htmltext = "7358-01.htm"
     elif cond == 5 :                                      # Condition 5 meet the Magister Clayton
         if npcId == 7358 :
           htmltext = "7358-05.htm"
         elif npcId == 7464 and st.getQuestItemsCount(LETTER_OF_THIFIELL_ID) :
           htmltext = "7464-01.htm"
           st.takeItems(LETTER_OF_THIFIELL_ID,1)
           st.giveItems(ORDER_OF_CLAYTON_ID,1)
           st.set("cond","6")
     elif cond == 6 :                                      # Condition 6 get 10 of each, Stakato ichor, honey dew and basilisk plasma
         if npcId == 7464 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) :
           htmltext = "7464-02.htm"
     elif cond == 7 :                                      # Condition 7 bring back the Stakato ichor, honey dew and basilisk plasma to Magister Clayton
         if npcId == 7464 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) :
           htmltext = "7464-03.htm"
           st.set("cond","8")
     elif cond == 8 :                                      # Condition 8 take the Stakato ichor, honey dew and basilisk plasma to Thifiell
         if npcId == 7358 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) :
           htmltext = "7358-03.htm"
           st.takeItems(ORDER_OF_CLAYTON_ID,1)
           st.takeItems(BASILISK_PLASMA_ID,1)
           st.takeItems(STAKATO_ICHOR_ID,1)
           st.takeItems(HONEY_DEW_ID,1)
           st.giveItems(SCROLL_OF_DARKELF_TRUST_ID,1)
           st.set("cond","9")
     elif cond == 9 :                                      # Condition 9 take the Elven and Dark Elven scroll to Hollint
         if npcId == 7191 and st.getQuestItemsCount(SCROLL_OF_ELF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_DARKELF_TRUST_ID) :
           htmltext = "7191-05.htm"
           st.takeItems(SCROLL_OF_DARKELF_TRUST_ID,1)
           st.takeItems(SCROLL_OF_ELF_TRUST_ID,1)
           st.giveItems(LETTER_TO_SERESIN_ID,1)
           st.set("cond","10")
         elif npcId == 7358 :
           htmltext = "7358-04.htm"
     elif cond in [ 10, 11 ] :                             # Condition 10 meet the Seresin or Condition 11 A lack of Experience 
         if npcId == 7191 :
           htmltext = "7191-09.htm"
         elif npcId == 7657 and st.getQuestItemsCount(LETTER_TO_SERESIN_ID) :
           htmltext = "7657-01.htm"
     elif cond == 12 :                                     # Condition 12 meet the Lord Kakai (Orc Master)
         if npcId == 7657 :
           htmltext = "7657-04.htm"
         elif npcId == 7565 and st.getQuestItemsCount(LETTER_TO_ORC_ID) :
           htmltext = "7565-01.htm"
     elif cond == 13 :                                     # Condition 13 meet the Seer Manakia
         if npcId == 7565 :
           htmltext = "7565-03.htm"
         elif npcId == 7515 and st.getQuestItemsCount(LETTER_TO_MANAKIA_ID) :
           htmltext = "7515-01.htm"
     elif cond == 14 :                                     # Condition 14 get 10 Parasite of lota
         if npcId == 7515 :
           htmltext = "7515-03.htm"
     elif cond == 15 :                                     # Condition 15 bring back the Parasite of lota to Seer Manakia
         if npcId == 7515 and st.getQuestItemsCount(PARASITE_OF_LOTA_ID)==10 :
           htmltext = "7515-04.htm"
           st.takeItems(PARASITE_OF_LOTA_ID,10)
           st.giveItems(LETTER_OF_MANAKIA_ID,1)
           st.set("cond","16")
     elif cond == 16 :                                     # Condition 16 bring the letter of Manakia to the Lord Kakai
         if npcId == 7565 and st.getQuestItemsCount(LETTER_OF_MANAKIA_ID) :
           htmltext = "7565-04.htm"
           st.takeItems(LETTER_OF_MANAKIA_ID,1)
           st.giveItems(SCROLL_OF_ORC_TRUST_ID,1)
           st.set("cond","17")
         elif npcId == 7515 :
           htmltext = "7515-05.htm"
     elif cond == 17 :                                     # Condition 17 meet the Lockirin (Dwarven Master)
         if npcId == 7565 :
           htmltext = "7565-05.htm"
         elif npcId == 7531 and st.getQuestItemsCount(LETTER_TO_DWARF_ID) :
           htmltext = "7531-01.htm"
     elif cond == 18 :                                     # Condition 18 take the letter to Nichola
         if npcId == 7531 :
           htmltext = "7531-03.htm"
         elif npcId == 7621 and st.getQuestItemsCount(LETTER_TO_NICHOLA_ID) :
           htmltext = "7621-01.htm"
     elif cond == 19 :                                     # Condition 19 get 10 Heart of Porta
         if npcId == 7621 :
           htmltext = "7621-03.htm"
     elif cond == 20 :                                     # Condition 20 bring the 10 Heart of Porta to Nichola
         if npcId == 7621 and st.getQuestItemsCount(ORDER_OF_NICHOLA_ID) and st.getQuestItemsCount(HEART_OF_PORTA_ID)==10 :
           htmltext = "7621-04.htm"
           st.takeItems(HEART_OF_PORTA_ID,10)
           st.takeItems(ORDER_OF_NICHOLA_ID,1)
           st.set("cond","21")
     elif cond == 21 :                                     # Condition 21 take the letter to Lockirin
         if npcId == 7621 :
           htmltext = "7621-05.htm"
         elif npcId == 7531 :
           htmltext = "7531-04.htm"
           st.giveItems(SCROLL_OF_DWARF_TRUST_ID,1)
           st.set("cond","22")
     elif cond == 22 :                                     # Condition 22 take the Orc and Dwarven scroll to High Priest Hollint
         if npcId == 7191 and st.getQuestItemsCount(SCROLL_OF_DWARF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_ORC_TRUST_ID) :
           htmltext = "7191-06.htm"
           st.takeItems(SCROLL_OF_DWARF_TRUST_ID,1)
           st.takeItems(SCROLL_OF_ORC_TRUST_ID,1)
           st.giveItems(RECOMMENDATION_OF_HOLLIN_ID,1)
           st.set("cond","23")
         elif npcId == 7657 :
           htmltext = "7657-05.htm"
         elif npcId == 7531 :
           htmltext = "7531-05.htm"
     elif cond == 23 :                                     # Condition 23 take the Recommendation of Hollin to the High Priest Biotin
         if npcId == 7191 :
           htmltext = "7191-07.htm"
         elif npcId == 7031 and st.getQuestItemsCount(RECOMMENDATION_OF_HOLLIN_ID) :
           st.addExpAndSp(39571,2500)
           st.giveItems(7562,16)
           htmltext = "7031-01.htm"
           st.takeItems(RECOMMENDATION_OF_HOLLIN_ID,1)
           st.giveItems(MARK_OF_TRUST_ID,1)
           st.unset("cond")
           st.unset("id")
           st.setState(COMPLETED)
           st.playSound("ItemSound.quest_finish")
   return htmltext


 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   cond = int(st.get("cond"))
   if cond == 2 and npcId in [ 13, 19, 36, 44 ] :          # Condition 2 kill the Luel of Zephy and Aktea of the Woods
     if npcId in [ 36,44 ] and st.getQuestItemsCount(BREATH_OF_WINDS_ID) == 0 :
       st.set("id",str(int(st.get("id"))+1))
       if st.getRandom(100)<(int(st.get("id"))*33) :
         st.playSound("Itemsound.quest_before_battle")
         st.getPcSpawn().addSpawn(5120,9410,50301,-3713)   ### FIXME ### Temp fix for spawn
         return "Luell Of Zephyr Winds has spawned at X=9410 Y=50301 Z=-3713"
#         st.getPcSpawn().addSpawn(5120)                   # The original spawn code
     elif npcId in [ 13,19 ] and st.getQuestItemsCount(SEED_OF_VERDURE_ID) == 0 :
       st.set("id",str(int(st.get("id"))+1))
       if st.getRandom(100)<(int(st.get("id"))*33) :
         st.playSound("Itemsound.quest_before_battle")
         st.getPcSpawn().addSpawn(5121,16895,47210,-3673)  ### FIXME ### Temp fix for spawn
         return "Actea Of Verdant Wilds has spawned at X=16895 Y=47210 Z=-3673"
#         st.getPcSpawn().addSpawn(5121)                   # The original spawn code
   elif cond == 14 :                                       # Condition 14 get 10 Parasite of lota
     parasite = st.getQuestItemsCount(PARASITE_OF_LOTA_ID)
     if npcId == 553 and parasite < 10 :
       if st.getRandom(2) == 1 :
         st.giveItems(PARASITE_OF_LOTA_ID,1)
         if parasite+1 == 10 :
           st.set("cond","15")
           st.playSound("Itemsound.quest_middle")
         else:
           st.playSound("Itemsound.quest_itemget")
   elif cond in [ 2,6,19 ] and npcId in DROPLIST.keys() :
     required,item,maxqty=DROPLIST[npcId]
     count = st.getQuestItemsCount(item)
     if st.getQuestItemsCount(required) and count < maxqty :
        st.giveItems(item,1)
        if count+1 == maxqty :                             # Check if got enough number of items
          # Special Sound event
          if npcId in [ 550, 82, 84, 86, 87, 88, 157, 230, 232, 234 ] : 
             # Condition 6 get 10 of each, Stakato ichor, honey dew and basilisk plasma, and transform it
             if item == BLOOD_OF_GUARDIAN_BASILISK_ID :
               st.takeItems(BLOOD_OF_GUARDIAN_BASILISK_ID, maxqty)
               st.giveItems(BASILISK_PLASMA_ID, 1)
             elif item == GIANT_APHID_ID :
               st.takeItems(GIANT_APHID_ID, maxqty)
               st.giveItems(HONEY_DEW_ID, 1)
             elif item == STAKATOS_FLUIDS_ID :
               st.takeItems(STAKATOS_FLUIDS_ID, maxqty)
               st.giveItems(STAKATO_ICHOR_ID, 1)
             # Check if player got all the items of condition 6 and set the condition to 7
             if st.getQuestItemsCount(BASILISK_PLASMA_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) :
               st.set("cond","7")
               st.playSound("Itemsound.quest_middle")
             else:
               st.playSound("Itemsound.quest_itemget")
          elif npcId in [ 5120,5121 ] :             # Condition 2 kill the Luel of Zephy and Aktea of the Woods
            # Check if player got all the items of condition 2 and set the condition to 3
            if st.getQuestItemsCount(SEED_OF_VERDURE_ID) and st.getQuestItemsCount(BREATH_OF_WINDS_ID) :
              st.set("cond","3")
              st.playSound("Itemsound.quest_middle")
            else :
              st.playSound("Itemsound.quest_itemget")
          elif npcId == 213 :                              # Condition 19 Porta
            st.set("cond","20")
            st.playSound("ItemSound.quest_middle")
        else:
          st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(217,"217_TestimonyOfTrust","Testimony Of Trust")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7191)

CREATED.addTalkId(7191)
STARTING.addTalkId(7191)
COMPLETED.addTalkId(7191)

STARTED.addTalkId(7031)
STARTED.addTalkId(7154)
STARTED.addTalkId(7191)
STARTED.addTalkId(7358)
STARTED.addTalkId(7464)
STARTED.addTalkId(7515)
STARTED.addTalkId(7531)
STARTED.addTalkId(7565)
STARTED.addTalkId(7621)
STARTED.addTalkId(7657)

for i in DROPLIST.keys()+[13,19,36,44,553] :
    STARTED.addKillId(i)

STARTED.addQuestDrop(7358,SCROLL_OF_DARKELF_TRUST_ID,1)
STARTED.addQuestDrop(7154,SCROLL_OF_ELF_TRUST_ID,1)
STARTED.addQuestDrop(7531,SCROLL_OF_DWARF_TRUST_ID,1)
STARTED.addQuestDrop(7565,SCROLL_OF_ORC_TRUST_ID,1)
STARTED.addQuestDrop(5120,BREATH_OF_WINDS_ID,1)
STARTED.addQuestDrop(5121,SEED_OF_VERDURE_ID,1)
STARTED.addQuestDrop(7154,ORDER_OF_OZZY_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_ELF_ID,1)
STARTED.addQuestDrop(7464,ORDER_OF_CLAYTON_ID,1)
STARTED.addQuestDrop(550,BASILISK_PLASMA_ID,1)
STARTED.addQuestDrop(157,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(82,HONEY_DEW_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_DARKELF_ID,1)
STARTED.addQuestDrop(7358,LETTER_OF_THIFIELL_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_SERESIN_ID,1)
STARTED.addQuestDrop(7657,LETTER_TO_ORC_ID,1)
STARTED.addQuestDrop(7515,LETTER_OF_MANAKIA_ID,1)
STARTED.addQuestDrop(7565,LETTER_TO_MANAKIA_ID,1)
STARTED.addQuestDrop(553,PARASITE_OF_LOTA_ID,1)
STARTED.addQuestDrop(7657,LETTER_TO_DWARF_ID,1)
STARTED.addQuestDrop(7531,LETTER_TO_NICHOLA_ID,1)
STARTED.addQuestDrop(213,HEART_OF_PORTA_ID,1)
STARTED.addQuestDrop(7621,ORDER_OF_NICHOLA_ID,1)
STARTED.addQuestDrop(7191,RECOMMENDATION_OF_HOLLIN_ID,1)
STARTED.addQuestDrop(550,BLOOD_OF_GUARDIAN_BASILISK_ID,1)
STARTED.addQuestDrop(157,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(82,GIANT_APHID_ID,1)

print "importing quests: 217: Testimony Of Trust"
