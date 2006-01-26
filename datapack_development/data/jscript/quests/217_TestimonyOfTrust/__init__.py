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
5120:[ORDER_OF_OZZY_ID,BREATH_OF_WINDS_ID,               1],
5121:[ORDER_OF_OZZY_ID,SEED_OF_VERDURE_ID,               1],
550 :[ORDER_OF_CLAYTON_ID,BLOOD_OF_GUARDIAN_BASILISK_ID,10],
82  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
86  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
87  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
84  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
88  :[ORDER_OF_CLAYTON_ID,GIANT_APHID_ID,               10],
157 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
230 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
232 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
234 :[ORDER_OF_CLAYTON_ID,STAKATOS_FLUIDS_ID,           10],
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
      if st.getPlayer().getLevel() >= 38 :
        st.takeItems(LETTER_TO_SERESIN_ID,1)
        st.giveItems(LETTER_TO_ORC_ID,1)
        st.giveItems(LETTER_TO_DWARF_ID,1)
        st.set("cond","12")
      else:
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
   cond = int(st.get("cond"))
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7191 and cond==0 and int(st.get("onlyone"))==0 :
     if st.getPlayer().getRace().ordinal() == 0 :
       if st.getPlayer().getLevel() >= 37 :
         htmltext = "7191-03.htm"
       else:
         htmltext = "7191-01.htm"
         st.exitQuest(1)
     else:
       htmltext = "7191-02.htm"
       st.exitQuest(1)
   elif npcId == 7191 and cond==0 and int(st.get("onlyone")) == 1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7191 and cond==9 and st.getQuestItemsCount(SCROLL_OF_ELF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_DARKELF_TRUST_ID) :
      htmltext = "7191-05.htm"
      st.takeItems(SCROLL_OF_DARKELF_TRUST_ID,1)
      st.takeItems(SCROLL_OF_ELF_TRUST_ID,1)
      st.giveItems(LETTER_TO_SERESIN_ID,1)
      st.set("cond","10")
   elif npcId == 7191 and cond==22 and st.getQuestItemsCount(SCROLL_OF_DWARF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_ORC_TRUST_ID) :
      htmltext = "7191-06.htm"
      st.takeItems(SCROLL_OF_DWARF_TRUST_ID,1)
      st.takeItems(SCROLL_OF_ORC_TRUST_ID,1)
      st.giveItems(RECOMMENDATION_OF_HOLLIN_ID,1)
      st.set("cond","23")
   elif npcId == 7191 and cond==23 :
      htmltext = "7191-07.htm"
   elif npcId == 7191 and cond==1 :
      htmltext = "7191-08.htm"
   elif npcId == 7191 and cond==10 :
      htmltext = "7191-09.htm"
   elif npcId == 7154 and cond==1 and st.getQuestItemsCount(LETTER_TO_ELF_ID) :
      htmltext = "7154-01.htm"
   elif npcId == 7154 and cond==2 and st.getQuestItemsCount(ORDER_OF_OZZY_ID) :
      htmltext = "7154-04.htm"
   elif npcId == 7154 and cond==3 and st.getQuestItemsCount(BREATH_OF_WINDS_ID) and st.getQuestItemsCount(SEED_OF_VERDURE_ID) :
      htmltext = "7154-05.htm"
      st.takeItems(BREATH_OF_WINDS_ID,1)
      st.takeItems(SEED_OF_VERDURE_ID,1)
      st.takeItems(ORDER_OF_OZZY_ID,1)
      st.giveItems(SCROLL_OF_ELF_TRUST_ID,1)
      st.set("cond","4")
   elif npcId == 7154 and cond==4 :
      htmltext = "7154-06.htm"
   elif npcId == 7358 and cond==4 and st.getQuestItemsCount(LETTER_TO_DARKELF_ID) :
      htmltext = "7358-01.htm"
   elif npcId == 7358 and cond==8 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) :
      htmltext = "7358-03.htm"
      st.takeItems(ORDER_OF_CLAYTON_ID,1)
      st.takeItems(BASILISK_PLASMA_ID,1)
      st.takeItems(STAKATO_ICHOR_ID,1)
      st.takeItems(HONEY_DEW_ID,1)
      st.giveItems(SCROLL_OF_DARKELF_TRUST_ID,1)
      st.set("cond","9")
   elif npcId == 7358 and cond==9 :
      htmltext = "7358-04.htm"
   elif npcId == 7358 and cond==5 :
      htmltext = "7358-05.htm"
   elif npcId == 7464 and cond==5 and st.getQuestItemsCount(LETTER_OF_THIFIELL_ID) :
      htmltext = "7464-01.htm"
      st.takeItems(LETTER_OF_THIFIELL_ID,1)
      st.giveItems(ORDER_OF_CLAYTON_ID,1)
      st.set("cond","6")
   elif npcId == 7464 and cond==6 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) :
      htmltext = "7464-02.htm"
   elif npcId == 7464 and cond==7 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) :
      htmltext = "7464-03.htm"
      st.set("cond","8")
   elif npcId == 7657 and cond==10 and st.getQuestItemsCount(LETTER_TO_SERESIN_ID) :
      htmltext = "7657-01.htm"
   elif npcId == 7657 and cond==12 :
      htmltext = "7657-04.htm"
   elif npcId == 7657 and cond==22 :
      htmltext = "7657-05.htm"
   elif npcId == 7565 and cond==12 and st.getQuestItemsCount(LETTER_TO_ORC_ID) :
      htmltext = "7565-01.htm"
   elif npcId == 7565 and cond==13 :
      htmltext = "7565-03.htm"
   elif npcId == 7565 and cond==16 and st.getQuestItemsCount(LETTER_OF_MANAKIA_ID) :
      htmltext = "7565-04.htm"
      st.takeItems(LETTER_OF_MANAKIA_ID,1)
      st.giveItems(SCROLL_OF_ORC_TRUST_ID,1)
      st.set("cond","17")
   elif npcId == 7565 and cond==17 :
      htmltext = "7565-05.htm"
   elif npcId == 7515 and cond==13 and st.getQuestItemsCount(LETTER_TO_MANAKIA_ID) :
      htmltext = "7515-01.htm"
   elif npcId == 7515 and cond==14 :
      htmltext = "7515-03.htm"
   elif npcId == 7515 and cond==15 and st.getQuestItemsCount(PARASITE_OF_LOTA_ID)==10 :
      htmltext = "7515-04.htm"
      st.takeItems(PARASITE_OF_LOTA_ID,10)
      st.giveItems(LETTER_OF_MANAKIA_ID,1)
      st.set("cond","16")
   elif npcId == 7515 and cond==16 :
      htmltext = "7515-05.htm"
   elif npcId == 7531 and cond==17 and st.getQuestItemsCount(LETTER_TO_DWARF_ID) :
      htmltext = "7531-01.htm"
   elif npcId == 7531 and cond==18 :
      htmltext = "7531-03.htm"
   elif npcId == 7531 and cond==21 :
      htmltext = "7531-04.htm"
      st.giveItems(SCROLL_OF_DWARF_TRUST_ID,1)
      st.set("cond","22")
   elif npcId == 7531 and cond==22 :
      htmltext = "7531-05.htm"
   elif npcId == 7621 and cond==18 and st.getQuestItemsCount(LETTER_TO_NICHOLA_ID) :
      htmltext = "7621-01.htm"
   elif npcId == 7621 and cond==19 :
      htmltext = "7621-03.htm"
   elif npcId == 7621 and cond==20 and st.getQuestItemsCount(ORDER_OF_NICHOLA_ID) and st.getQuestItemsCount(HEART_OF_PORTA_ID)==10 :
      htmltext = "7621-04.htm"
      st.takeItems(HEART_OF_PORTA_ID,10)
      st.takeItems(ORDER_OF_NICHOLA_ID,1)
      st.set("cond","21")
   elif npcId == 7621 and cond==21 :
      htmltext = "7621-05.htm"
   elif npcId == 7031 and cond==23 and st.getQuestItemsCount(RECOMMENDATION_OF_HOLLIN_ID) :
      st.addExpAndSp(32600,4000)
      htmltext = "7031-01.htm"
      st.takeItems(RECOMMENDATION_OF_HOLLIN_ID,1)
      st.giveItems(MARK_OF_TRUST_ID,1)
      st.set("cond","0")
      st.set("onlyone","1")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   cond = int(st.get("cond"))
   if cond == 2 and npcId in [ 13, 19, 36, 44 ] :
     if npcId == 36 or npcId == 44 and st.getQuestItemsCount(BREATH_OF_WINDS_ID) == 0 :
       st.set("id",str(int(st.get("id"))+1))
       if st.getRandom(100)<(int(st.get("id"))*33) :
         st.playSound("Itemsound.quest_before_battle")
         st.getPcSpawn().addSpawn(5120)
     elif npcId == 13 or npcId == 19 and st.getQuestItemsCount(SEED_OF_VERDURE_ID) == 0 :
       st.set("id",str(int(st.get("id"))+1))
       if st.getRandom(100)<(int(st.get("id"))*33) :
         st.playSound("Itemsound.quest_before_battle")
         st.getPcSpawn().addSpawn(5121)
   elif cond == 14 :
     parasite = st.getQuestItemsCount(PARASITE_OF_LOTA_ID)
     if npcId == 553 and parasite < 10 :
       if st.getRandom(2) == 1 :
         st.giveItems(PARASITE_OF_LOTA_ID,1)
         if parasite+1 == 10 :
           st.set("cond","15")
           st.playSound("Itemsound.quest_middle")
         else:
           st.playSound("Itemsound.quest_itemget")
   elif cond == 2 or cond == 6 or cond == 19 :
     required,item,maxqty=DROPLIST[npcId]
     count = st.getQuestItemsCount(item)
     if st.getQuestItemsCount(required) and count < maxqty :
        st.giveItems(item,1)
        if count+1 == maxqty :
          if npcId in [ 550, 82, 84, 86, 88, 157, 230, 232, 234 ] :
             if item == BLOOD_OF_GUARDIAN_BASILISK_ID :
               st.takeItems(BLOOD_OF_GUARDIAN_BASILISK_ID, maxqty)
               st.giveItems(BASILISK_PLASMA_ID, 1)
             elif item == GIANT_APHID_ID :
               st.takeItems(GIANT_APHID_ID, maxqty)
               st.giveItems(HONEY_DEW_ID, 1)
             elif item == STAKATOS_FLUIDS_ID :
               st.takeItems(STAKATOS_FLUIDS_ID, maxqty)
               st.giveItems(STAKATO_ICHOR_ID, 1)
             if st.getQuestItemsCount(BASILISK_PLASMA_ID) and st.getQuestItemsCount(HONEY_DEW_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) :
               st.set("cond","7")
               st.playSound("Itemsound.quest_middle")
             else:
               st.playSound("Itemsound.quest_itemget")
          elif npcId == 5120 or npcId == 5121:
            if st.getQuestItemsCount(SEED_OF_VERDURE_ID) and st.getQuestItemsCount(BREATH_OF_WINDS_ID) :
              st.set("cond","3")
              st.playSound("Itemsound.quest_middle")
            else:
              st.playSound("Itemsound.quest_itemget")
          elif npcId == 213 :
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

STARTED.addKillId(13)
STARTED.addKillId(157)
STARTED.addKillId(19)
STARTED.addKillId(213)
STARTED.addKillId(230)
STARTED.addKillId(232)
STARTED.addKillId(234)
STARTED.addKillId(36)
STARTED.addKillId(44)
STARTED.addKillId(5120)
STARTED.addKillId(5121)
STARTED.addKillId(550)
STARTED.addKillId(553)
STARTED.addKillId(82)
STARTED.addKillId(84)
STARTED.addKillId(86)
STARTED.addKillId(87)
STARTED.addKillId(88)

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
STARTED.addQuestDrop(230,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(232,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(234,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(82,HONEY_DEW_ID,1)
STARTED.addQuestDrop(86,HONEY_DEW_ID,1)
STARTED.addQuestDrop(87,HONEY_DEW_ID,1)
STARTED.addQuestDrop(84,HONEY_DEW_ID,1)
STARTED.addQuestDrop(88,HONEY_DEW_ID,1)
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
STARTED.addQuestDrop(230,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(232,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(234,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(82,GIANT_APHID_ID,1)
STARTED.addQuestDrop(86,GIANT_APHID_ID,1)
STARTED.addQuestDrop(87,GIANT_APHID_ID,1)
STARTED.addQuestDrop(84,GIANT_APHID_ID,1)
STARTED.addQuestDrop(88,GIANT_APHID_ID,1)

print "importing quests: 217: Testimony Of Trust"
