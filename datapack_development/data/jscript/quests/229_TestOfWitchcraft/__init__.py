# Maked by Mr. Have fun! Version 0.2
print "importing quests: 229: Test Of Witchcraft"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_WITCHCRAFT_ID = 3307
ORIMS_DIAGRAM_ID = 3308
ALEXANDRIAS_BOOK_ID = 3309
IKERS_LIST_ID = 3310
DIRE_WYRM_FANG_ID = 3311
LETO_LIZARDMAN_CHARM_ID = 3312
EN_GOLEM_HEARTSTONE_ID = 3313
LARS_MEMO1_ID = 3314
NESTLE_MEMO1_ID = 3315
LEOPOLDS_JOURNAL1_ID = 3316
AKLANTOS_GEM1_ID = 3317
AKLANTOS_GEM2_ID = 3318
AKLANTOS_GEM3_ID = 3319
AKLANTOS_GEM4_ID = 3320
AKLANTOS_GEM5_ID = 3321
AKLANTOS_GEM6_ID = 3322
BRIMSTONE1_ID = 3323
ORIMS_INSTRUCTIONS_ID = 3324
ORIMS_LETTER1_ID = 3325
ORIMS_LETTER2_ID = 3326
SIR_VASPERS_LETTER_ID = 3327
VADINS_CRUCIFIX_ID = 3328
TAMLIN_ORC_AMULET_ID = 3329
VADINS_SANCTIONS_ID = 3330
IKERS_AMULET_ID = 3331
SOULTRAP_CRYSTAL_ID = 3332
PURGATORY_KEY_ID = 3333
ZERUEL_BIND_CRYSTAL_ID = 3334
BRIMSTONE2_ID = 3335
SWORD_OF_BINDING_ID = 3029

#This handels all Mob Drop relatet Datas npcId:[condition,maxcount,chance,giveList,takeList]
DROPLIST={
5101:[24,1,100,[ZERUEL_BIND_CRYSTAL_ID,PURGATORY_KEY_ID],[BRIMSTONE2_ID,SOULTRAP_CRYSTAL_ID]],
5100:[10,1,100,[AKLANTOS_GEM4_ID,AKLANTOS_GEM5_ID,AKLANTOS_GEM6_ID],[LEOPOLDS_JOURNAL1_ID]],
601:[19,20,50,[TAMLIN_ORC_AMULET_ID],0],
602:[19,20,55,[TAMLIN_ORC_AMULET_ID],0],
5099:[7,1,100,[AKLANTOS_GEM3_ID],0],
557:[3,20,100,[DIRE_WYRM_FANG_ID],0],
565:[3,20,80,[EN_GOLEM_HEARTSTONE_ID],0],
577:[3,20,50,[LETO_LIZARDMAN_CHARM_ID],0],
578:[3,20,50,[LETO_LIZARDMAN_CHARM_ID],0],
579:[3,20,60,[LETO_LIZARDMAN_CHARM_ID],0],
580:[3,20,60,[LETO_LIZARDMAN_CHARM_ID],0],
581:[3,20,70,[LETO_LIZARDMAN_CHARM_ID],0],
582:[3,20,70,[LETO_LIZARDMAN_CHARM_ID],0]
}

def countGem(st):
 count=0
 for gem in [AKLANTOS_GEM1_ID,AKLANTOS_GEM2_ID,AKLANTOS_GEM3_ID,AKLANTOS_GEM4_ID,AKLANTOS_GEM5_ID,AKLANTOS_GEM6_ID]:
  count+=st.getQuestItemsCount(gem)
 if count>5:
  st.set("cond","12")
 return 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          htmltext = "7630-08.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
          st.giveItems(ORIMS_DIAGRAM_ID,1)
    elif event == "7630_1" :
          htmltext = "7630-04.htm"
    elif event == "7630_2" :
          htmltext = "7630-06.htm"
    elif event == "7630_3" :
          htmltext = "7630-07.htm"
    elif event == "7630_4" :
          htmltext = "7630-12.htm"
    elif event == "7630_5" :
          htmltext = "7630-13.htm"
    elif event == "7630_6" :
          htmltext = "7630-14.htm"
          st.giveItems(BRIMSTONE1_ID,1)
          st.takeItems(ALEXANDRIAS_BOOK_ID,1)
          st.takeItems(AKLANTOS_GEM1_ID,1)
          st.takeItems(AKLANTOS_GEM2_ID,1)
          st.takeItems(AKLANTOS_GEM3_ID,1)
          st.takeItems(AKLANTOS_GEM4_ID,1)
          st.takeItems(AKLANTOS_GEM5_ID,1)
          st.takeItems(AKLANTOS_GEM6_ID,1)
          st.set("cond","14")
          st.spawnNpc(5101)
    elif event == "7630_7" :
          htmltext = "7630-16.htm"
          st.giveItems(ORIMS_INSTRUCTIONS_ID,1)
          st.takeItems(BRIMSTONE1_ID,1)
          st.giveItems(ORIMS_LETTER1_ID,1)
          st.giveItems(ORIMS_LETTER2_ID,1)
          st.set("cond","15")
    elif event == "7630_8" :
          htmltext = "7630-20.htm"
    elif event == "7630_9" :
          htmltext = "7630-21.htm"
    elif event == "7630_10" :
          st.takeItems(LARS_MEMO1_ID,1)
          st.takeItems(PURGATORY_KEY_ID,1)
          st.takeItems(SWORD_OF_BINDING_ID,1)
          st.takeItems(IKERS_AMULET_ID,1)
          st.takeItems(ORIMS_INSTRUCTIONS_ID,1)
          st.addExpAndSp(50000,6400)
          st.giveItems(MARK_OF_WITCHCRAFT_ID,1)
          st.takeItems(ZERUEL_BIND_CRYSTAL_ID,1)
          htmltext = "7630-22.htm"
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7098_1" :
          htmltext = "7098-02.htm"
    elif event == "7098_2" :
          htmltext = "7098-03.htm"
          st.giveItems(ALEXANDRIAS_BOOK_ID,1)
          st.takeItems(ORIMS_DIAGRAM_ID,1)
          st.set("cond","2")
    elif event == "7110_1" :
          htmltext = "7110-02.htm"
    elif event == "7110_2" :
          htmltext = "7110-03.htm"
          st.giveItems(IKERS_LIST_ID,1)
          st.set("cond","3")
    elif event == "7110_3" :
          htmltext = "7110-08.htm"
          st.giveItems(SOULTRAP_CRYSTAL_ID,1)
          st.giveItems(IKERS_AMULET_ID,1)
          st.takeItems(ORIMS_LETTER2_ID,1)
          st.set("cond",str(int(st.get("cond"))+1))
    elif event == "7476_1" :
          htmltext = "7476-02.htm"
          st.giveItems(AKLANTOS_GEM2_ID,1)
          st.set("cond","6")
          countGem(st)
    elif event == "7063_1" :
          htmltext = "7063-02.htm"
          st.giveItems(LARS_MEMO1_ID,1)
          st.set("cond","7")
    elif event == "7314_1" :
          htmltext = "7314-02.htm"
          st.giveItems(NESTLE_MEMO1_ID,1)
          st.set("cond","9")
    elif event == "7435_1" :
          htmltext = "7435-02.htm"
          st.giveItems(LEOPOLDS_JOURNAL1_ID,1)
          st.takeItems(NESTLE_MEMO1_ID,1)
          st.set("cond","10")
    elif event == "7417_1" :
          htmltext = "7417-02.htm"
    elif event == "7417_2" :
          htmltext = "7417-03.htm"
          st.giveItems(SIR_VASPERS_LETTER_ID,1)
          st.takeItems(ORIMS_LETTER1_ID,1)
          st.set("cond",str(int(st.get("cond"))+2))
    elif event == "7633_1" :
          htmltext = "7633-02.htm"
          st.giveItems(BRIMSTONE2_ID,1)
          st.spawnNpc(5101,13395,169807,-3708)
    return htmltext


 def onTalk (self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7630:
     if int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if (st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x04 or st.getPlayer().getClassId().getId() == 0x20) :
            if st.getPlayer().getLevel() < 39 :
              htmltext = "7630-02.htm"
            else:
              if st.getPlayer().getClassId().getId() == 0x0b :
                htmltext = "7630-03.htm"
              else:
                htmltext = "7630-05.htm"
          else:
            htmltext = "7630-01.htm"
            st.exitQuest(1)
        else:
          htmltext = "7630-01.htm"
          st.exitQuest(1)
     elif int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
        htmltext = "<html><head><body>This quest has already been completed.</body></html>"
     elif int(st.get("cond"))==1:
        htmltext = "7630-09.htm"
     elif int(st.get("cond"))==2:
        htmltext = "7630-10.htm"
     elif int(st.get("cond"))==12 :
        htmltext = "7630-11.htm"
     elif int(st.get("cond"))==14:
        htmltext = "7630-15.htm"
     elif int(st.get("cond"))==15 :
        htmltext = "7630-17.htm"
     elif int(st.get("cond"))==23 :
        htmltext = "7630-18.htm"
     elif int(st.get("cond"))==25 :
        htmltext = "7630-19.htm"
   elif npcId == 7098:
     if int(st.get("cond"))==1 :
        htmltext = "7098-01.htm"
     elif int(st.get("cond"))==2 :
        htmltext = "7098-04.htm"
     elif int(st.get("cond"))==14 :
        htmltext = "7098-05.htm"
   elif npcId == 7110:
     if int(st.get("cond"))==2 or int(st.get("cond"))==6 or int(st.get("cond"))==8 or int(st.get("cond"))==11:
        htmltext = "7110-01.htm"
     elif int(st.get("cond"))==3 :
        htmltext = "7110-04.htm"
     elif int(st.get("cond"))==4 :
          htmltext = "7110-05.htm"
          st.giveItems(AKLANTOS_GEM1_ID,1)
          st.takeItems(IKERS_LIST_ID,1)
          st.takeItems(DIRE_WYRM_FANG_ID,st.getQuestItemsCount(DIRE_WYRM_FANG_ID))
          st.takeItems(LETO_LIZARDMAN_CHARM_ID,st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID))
          st.takeItems(EN_GOLEM_HEARTSTONE_ID,st.getQuestItemsCount(EN_GOLEM_HEARTSTONE_ID))
          st.set("cond","5")
          countGem(st)
     elif int(st.get("cond"))>4 and int(st.get("cond"))<15 :
        htmltext = "7110-06.htm"
     elif int(st.get("cond"))==15 or int(st.get("cond"))==22 :
        htmltext = "7110-07.htm"
     elif int(st.get("cond"))==23 :
        htmltext = "7110-09.htm"
     elif int(st.get("cond"))==24 :
        htmltext = "7110-10.htm"
   elif npcId == 7476:
     if int(st.get("cond"))==2 or int(st.get("cond"))==5 or int(st.get("cond"))==8 or int(st.get("cond"))==11:
        htmltext = "7476-01.htm"
     elif int(st.get("cond"))==6 :
        htmltext = "7476-03.htm"
     elif int(st.get("cond"))==14 or int(st.get("cond"))==15 :
        htmltext = "7476-04.htm"
   elif npcId == 7063:
     if int(st.get("cond"))==6 or int(st.get("cond"))==2 or int(st.get("cond"))==5 or int(st.get("cond"))==11:
        htmltext = "7063-01.htm"
     elif int(st.get("cond"))==7 :
        htmltext = "7063-03.htm"
     elif int(st.get("cond"))==8 :
        htmltext = "7063-04.htm"
     elif int(st.get("cond"))==14 or int(st.get("cond"))==15:
        htmltext = "7063-05.htm"
   elif npcId == 7314:
     if int(st.get("cond"))==2 or int(st.get("cond"))==5 or int(st.get("cond"))==6 or int(st.get("cond"))==8:
        htmltext = "7314-01.htm"
     elif int(st.get("cond"))==9 :
        htmltext = "7314-03.htm"
     elif int(st.get("cond"))==10 or int(st.get("cond"))==13 :
        htmltext = "7314-04.htm"
   elif npcId == 7435 :
     if int(st.get("cond"))==9 :
        htmltext = "7435-01.htm"
     elif int(st.get("cond"))==10 :
        htmltext = "7435-03.htm"
     elif int(st.get("cond"))==13 :
        htmltext = "7435-04.htm"
     elif int(st.get("cond"))==14 or int(st.get("cond"))==15 :
        htmltext = "7435-05.htm"
   elif npcId == 7631 and int(st.get("cond"))==7 :
        htmltext = "7631-01.htm"
   elif npcId == 7632 and int(st.get("cond"))==7 :
        htmltext = "7632-01.htm"
   elif npcId == 7417  :
     if int(st.get("cond"))==15 or int(st.get("cond"))==16 :
        htmltext = "7417-01.htm"
     elif int(st.get("cond"))==17 or int(st.get("cond"))==18 :
        htmltext = "7417-04.htm"
     elif int(st.get("cond"))==21 :
        htmltext = "7417-05.htm"
        st.giveItems(SWORD_OF_BINDING_ID,1)
        st.takeItems(VADINS_SANCTIONS_ID,1)
        if st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID) :
         st.set("cond","23")
        else:
         st.set("cond","22")
     elif int(st.get("cond"))==22 or int(st.get("cond"))==23 :
        htmltext = "7417-06.htm"
   elif npcId == 7188:
     if int(st.get("cond"))==17 or int(st.get("cond"))==18 :
        htmltext = "7188-01.htm"
        st.giveItems(VADINS_CRUCIFIX_ID,1)
        st.takeItems(SIR_VASPERS_LETTER_ID,1)
        st.set("cond","19")
     elif int(st.get("cond"))==19:
        htmltext = "7188-02.htm"
     elif int(st.get("cond"))==20:
          htmltext = "7188-03.htm"
          st.takeItems(TAMLIN_ORC_AMULET_ID,st.getQuestItemsCount(TAMLIN_ORC_AMULET_ID))
          st.giveItems(VADINS_SANCTIONS_ID,1)
          st.takeItems(VADINS_CRUCIFIX_ID,1)
          st.set("cond","21")
     elif int(st.get("cond"))==21 :
        htmltext = "7188-04.htm"
     elif int(st.get("cond"))==22 :
        htmltext = "7188-05.htm"
   elif npcId == 7633 :
     if int(st.get("cond"))==23 and st.getQuestItemsCount(BRIMSTONE2_ID)==0 :
        htmltext = "7633-01.htm"
     elif int(st.get("cond"))==23 or int(st.get("cond"))==24:
        htmltext = "7633-02.htm"
        st.spawnNpc(5101,13395,169807,-3708)
        st.set("cond","24")
     elif int(st.get("cond"))==25 :
        htmltext = "7633-03.htm"
   return htmltext

 def onKill (self,npcId,st):
   condition,maxcount,chance,giveList,takeList=DROPLIST[npcId]
   random=st.getRandom(100)
   if int(st.get("cond"))==condition and random<chance:
     if takeList==0:
      for give in giveList:
       if st.getQuestItemsCount(give)<maxcount:
        if st.getQuestItemsCount(give)==maxcount-1:
         st.giveItems(give,1)
         st.playSound("ItemSound.quest_middle")
         count=0
         if condition==3:
          for item in [DIRE_WYRM_FANG_ID,EN_GOLEM_HEARTSTONE_ID,LETO_LIZARDMAN_CHARM_ID]:
           count+=st.getQuestItemsCount(item)
          if count>59:
           st.set("cond","4")
         else:
          st.set("cond",str(condition+1)) 
          countGem(st)
        else:
         st.giveItems(give,1)
         st.playSound("ItemSound.quest_itemget")
     elif takeList>0:
      if npcId==5101 :
       if st.getItemEquipped(7)==SWORD_OF_BINDING_ID:
        for give in giveList:
         st.giveItems(give,1)
         for take in takeList:
          st.takeItems(take,1)
        st.set("cond",str(int(st.get("cond"))+1))
        return "You trapped the Seal of Drevanul Prince Zeruel"
      else:
       for give in giveList:
        if st.getQuestItemsCount(give)!=maxcount:
         st.giveItems(give,1)
         for take in takeList:
          st.takeItems(take,1)
         if st.getQuestItemsCount(AKLANTOS_GEM4_ID) and st.getQuestItemsCount(AKLANTOS_GEM5_ID) and st.getQuestItemsCount(AKLANTOS_GEM6_ID):
          st.set("cond","11")
          countGem(st)
         return 
   return

QUEST       = Quest(229,"229_TestOfWitchcraft","Test Of Witchcraft")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7630)

STARTING.addTalkId(7630)

for npcId in [7063,7098,7110,7188,7314,7417,7435,7476,7630,7631,7632,7633]:
 STARTED.addTalkId(npcId)

for mobId in [5099,5100,5101,557,565,577,578,579,580,581,582,601,602]:
 STARTED.addKillId(mobId)
