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

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7630-08.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(ORIMS_DIAGRAM_ID)
    elif event == "7630_1" :
          htmltext = "7630-04.htm"
    elif event == "7630_2" :
          htmltext = "7630-06.htm"
    elif event == "7630_3" :
          htmltext = "7630-07.htm"
          st.set("cond","1")
          return htmltext
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
          st.spawnNpc(5101)
    elif event == "7630_7" :
          htmltext = "7630-16.htm"
          st.giveItems(ORIMS_INSTRUCTIONS_ID,1)
          st.takeItems(BRIMSTONE1_ID,1)
          st.giveItems(ORIMS_LETTER1_ID,1)
          st.giveItems(ORIMS_LETTER2_ID,1)
    elif event == "7630_8" :
          htmltext = "7630-20.htm"
    elif event == "7630_9" :
          htmltext = "7630-21.htm"
    elif event == "7630_10" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            st.takeItems(PURGATORY_KEY_ID,1)
            st.takeItems(SWORD_OF_BINDING_ID,1)
            st.takeItems(IKERS_AMULET_ID,1)
            st.takeItems(ORIMS_INSTRUCTIONS_ID,1)
            st.addExpAndSp(50000,6400)
            st.giveItems(MARK_OF_WITCHCRAFT_ID,1)
            st.takeItems(ZERUEL_BIND_CRYSTAL_ID,1)
            htmlfile = "7630-22.htm"
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
    elif event == "7110_1" :
          htmltext = "7110-02.htm"
    elif event == "7110_2" :
          htmltext = "7110-03.htm"
          st.giveItems(IKERS_LIST_ID,1)
    elif event == "7110_3" :
          htmltext = "7110-08.htm"
          st.giveItems(SOULTRAP_CRYSTAL_ID,1)
          st.giveItems(IKERS_AMULET_ID,1)
          st.takeItems(ORIMS_LETTER2_ID,1)
    elif event == "7476_1" :
          htmltext = "7476-02.htm"
          st.giveItems(AKLANTOS_GEM2_ID,1)
    elif event == "7063_1" :
          htmltext = "7063-02.htm"
          st.giveItems(LARS_MEMO1_ID,1)
    elif event == "7314_1" :
          htmltext = "7314-02.htm"
          st.giveItems(NESTLE_MEMO1_ID,1)
    elif event == "7435_1" :
          htmltext = "7435-02.htm"
          st.giveItems(LEOPOLDS_JOURNAL1_ID,1)
          st.takeItems(NESTLE_MEMO1_ID,1)
    elif event == "7417_1" :
          htmltext = "7417-02.htm"
    elif event == "7417_2" :
          htmltext = "7417-03.htm"
          st.giveItems(SIR_VASPERS_LETTER_ID,1)
          st.takeItems(ORIMS_LETTER1_ID,1)
    elif event == "7633_1" :
          htmltext = "7633-02.htm"
          st.giveItems(BRIMSTONE2_ID,1)
#          if Maker_GetNpcCount() == 1 :
          st.spawnNpc(5101,13395,169807,-3708)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7630 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
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
        else:
          htmltext = "7630-01.htm"
   elif npcId == 7630 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_DIAGRAM_ID)==1 :
        htmltext = "7630-09.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 :
        if st.getQuestItemsCount((AKLANTOS_GEM1_ID) and st.getQuestItemsCount(AKLANTOS_GEM2_ID) and st.getQuestItemsCount(AKLANTOS_GEM3_ID)) and st.getQuestItemsCount((AKLANTOS_GEM4_ID) and st.getQuestItemsCount(AKLANTOS_GEM5_ID) and st.getQuestItemsCount(AKLANTOS_GEM6_ID)) :
          htmltext = "7630-11.htm"
        else:
          htmltext = "7630-10.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRIMSTONE1_ID)==1 :
        htmltext = "7630-15.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount((SWORD_OF_BINDING_ID) == 0 and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID)) == 0 :
        htmltext = "7630-17.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount((SWORD_OF_BINDING_ID) and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID)) :
        htmltext = "7630-18.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount((SWORD_OF_BINDING_ID) and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)) :
        htmltext = "7630-19.htm"
   elif npcId == 7098 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_DIAGRAM_ID)==1 :
        htmltext = "7098-01.htm"
   elif npcId == 7098 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 :
        htmltext = "7098-04.htm"
   elif npcId == 7098 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(BRIMSTONE1_ID)==1 :
        htmltext = "7098-05.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount((IKERS_LIST_ID) == 0 and st.getQuestItemsCount(AKLANTOS_GEM1_ID)) == 0 :
        htmltext = "7110-01.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(IKERS_LIST_ID)) :
        if st.getQuestItemsCount(DIRE_WYRM_FANG_ID) >= 20 and st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID) >= 20 and st.getQuestItemsCount(EN_GOLEM_HEARTSTONE_ID) >= 20 :
          htmltext = "7110-05.htm"
          st.giveItems(AKLANTOS_GEM1_ID,1)
          st.takeItems(IKERS_LIST_ID,1)
          st.takeItems(DIRE_WYRM_FANG_ID,st.getQuestItemsCount(DIRE_WYRM_FANG_ID))
          st.takeItems(LETO_LIZARDMAN_CHARM_ID,st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID))
          st.takeItems(EN_GOLEM_HEARTSTONE_ID,st.getQuestItemsCount(EN_GOLEM_HEARTSTONE_ID))
        else:
          htmltext = "7110-04.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount(IKERS_LIST_ID)==0 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(AKLANTOS_GEM1_ID)) :
        htmltext = "7110-06.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount((SOULTRAP_CRYSTAL_ID) == 0 and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)) == 0 :
        htmltext = "7110-07.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)==0 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID)) :
        htmltext = "7110-09.htm"
   elif npcId == 7110 and int(st.get("cond"))==1 and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID)==0 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)) :
        htmltext = "7110-10.htm"
   elif npcId == 7476 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount(AKLANTOS_GEM2_ID)==0 :
        htmltext = "7476-01.htm"
   elif npcId == 7476 and int(st.get("cond"))==1 and st.getQuestItemsCount(AKLANTOS_GEM2_ID)==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 :
        htmltext = "7476-03.htm"
   elif npcId == 7476 and int(st.get("cond"))==1 and st.getQuestItemsCount((BRIMSTONE1_ID) or st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)) :
        htmltext = "7476-04.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount((LARS_MEMO1_ID) == 0 and st.getQuestItemsCount(AKLANTOS_GEM3_ID)) == 0 :
        htmltext = "7063-01.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(AKLANTOS_GEM3_ID)==0 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(LARS_MEMO1_ID)) :
        htmltext = "7063-03.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(LARS_MEMO1_ID)==0 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(AKLANTOS_GEM3_ID)) :
        htmltext = "7063-04.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount((BRIMSTONE1_ID) or st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)) :
        htmltext = "7063-05.htm"
   elif npcId == 7314 and int(st.get("cond"))==1 and st.getQuestItemsCount((LEOPOLDS_JOURNAL1_ID) == 0 and st.getQuestItemsCount(NESTLE_MEMO1_ID)) == 0 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount((AKLANTOS_GEM4_ID) == 0 and st.getQuestItemsCount(AKLANTOS_GEM5_ID) == 0 and st.getQuestItemsCount(AKLANTOS_GEM6_ID)) == 0 :
        htmltext = "7314-01.htm"
   elif npcId == 7314 and int(st.get("cond"))==1 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(NESTLE_MEMO1_ID)) and st.getQuestItemsCount(LEOPOLDS_JOURNAL1_ID)==0 :
        htmltext = "7314-03.htm"
   elif npcId == 7314 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount(NESTLE_MEMO1_ID)==0 and (st.getQuestItemsCount(LEOPOLDS_JOURNAL1_ID)==0 or st.getQuestItemsCount((AKLANTOS_GEM4_ID) or st.getQuestItemsCount(AKLANTOS_GEM5_ID) or st.getQuestItemsCount(AKLANTOS_GEM6_ID))) :
        htmltext = "7314-04.htm"
   elif npcId == 7435 and int(st.get("cond"))==1 and st.getQuestItemsCount(LEOPOLDS_JOURNAL1_ID)==0 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(NESTLE_MEMO1_ID)) :
        htmltext = "7435-01.htm"
   elif npcId == 7435 and int(st.get("cond"))==1 and st.getQuestItemsCount(NESTLE_MEMO1_ID)==0 and st.getQuestItemsCount((ALEXANDRIAS_BOOK_ID) and st.getQuestItemsCount(LEOPOLDS_JOURNAL1_ID)) :
        htmltext = "7435-03.htm"
   elif npcId == 7435 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 and st.getQuestItemsCount((AKLANTOS_GEM4_ID) and st.getQuestItemsCount(AKLANTOS_GEM5_ID) and st.getQuestItemsCount(AKLANTOS_GEM6_ID)) :
        htmltext = "7435-04.htm"
   elif npcId == 7435 and int(st.get("cond"))==1 and st.getQuestItemsCount((BRIMSTONE1_ID) or st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)) :
        htmltext = "7435-05.htm"
   elif npcId == 7631 and int(st.get("cond"))==1 and st.getQuestItemsCount((LARS_MEMO1_ID) or st.getQuestItemsCount(AKLANTOS_GEM3_ID)) and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 :
        htmltext = "7631-01.htm"
   elif npcId == 7632 and int(st.get("cond"))==1 and st.getQuestItemsCount((LARS_MEMO1_ID) or st.getQuestItemsCount(AKLANTOS_GEM3_ID)) and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID)==1 :
        htmltext = "7632-01.htm"
   elif npcId == 7417 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(ORIMS_LETTER1_ID)) :
        htmltext = "7417-01.htm"
   elif npcId == 7417 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(SIR_VASPERS_LETTER_ID)) :
        htmltext = "7417-04.htm"
   elif npcId == 7417 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(VADINS_SANCTIONS_ID)) :
        htmltext = "7417-05.htm"
        st.giveItems(SWORD_OF_BINDING_ID,1)
        st.takeItems(VADINS_SANCTIONS_ID,1)
   elif npcId == 7417 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(SWORD_OF_BINDING_ID)) :
        htmltext = "7417-06.htm"
   elif npcId == 7188 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(SIR_VASPERS_LETTER_ID)==1 :
        htmltext = "7188-01.htm"
        st.giveItems(VADINS_CRUCIFIX_ID,1)
        st.takeItems(SIR_VASPERS_LETTER_ID,1)
   elif npcId == 7188 and int(st.get("cond"))==1 and st.getQuestItemsCount(VADINS_CRUCIFIX_ID)==1 :
        if st.getQuestItemsCount(TAMLIN_ORC_AMULET_ID) < 20 :
          htmltext = "7188-02.htm"
        else:
          htmltext = "7188-03.htm"
          st.takeItems(TAMLIN_ORC_AMULET_ID,st.getQuestItemsCount(TAMLIN_ORC_AMULET_ID))
          st.giveItems(VADINS_SANCTIONS_ID,1)
          st.takeItems(VADINS_CRUCIFIX_ID,1)
   elif npcId == 7188 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(VADINS_SANCTIONS_ID)==1 :
        htmltext = "7188-04.htm"
   elif npcId == 7188 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(SWORD_OF_BINDING_ID)==1 :
        htmltext = "7188-05.htm"
   elif npcId == 7633 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID) and st.getQuestItemsCount(SWORD_OF_BINDING_ID)) and st.getQuestItemsCount(BRIMSTONE2_ID)==0 :
        htmltext = "7633-01.htm"
   elif npcId == 7633 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID) and st.getQuestItemsCount(BRIMSTONE2_ID)) and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)==0 :
        htmltext = "7633-02.htm"
#        if Maker_GetNpcCount() == 1 :
        st.spawnNpc(5101,13395,169807,-3708)
   elif npcId == 7633 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_INSTRUCTIONS_ID) and st.getQuestItemsCount(ZERUEL_BIND_CRYSTAL_ID)) and st.getQuestItemsCount((SOULTRAP_CRYSTAL_ID) == 0 and st.getQuestItemsCount(BRIMSTONE2_ID)) == 0 :
        htmltext = "7633-03.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5101 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORIMS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(BRIMSTONE2_ID) == 1 and st.getQuestItemsCount(SWORD_OF_BINDING_ID) == 1 and st.getQuestItemsCount(SOULTRAP_CRYSTAL_ID) == 1 :
      if talker.equiped_weapon_class_id == SWORD_OF_BINDING_ID :
        st.giveItems(ZERUEL_BIND_CRYSTAL_ID,1)
        st.giveItems(PURGATORY_KEY_ID,1)
        st.takeItems(BRIMSTONE2_ID,1)
        st.takeItems(SOULTRAP_CRYSTAL_ID,1)
   elif npcId == 5099 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(LARS_MEMO1_ID) == 1 and st.getQuestItemsCount(AKLANTOS_GEM3_ID) == 0 :
      st.giveItems(AKLANTOS_GEM3_ID,1)
      st.takeItems(LARS_MEMO1_ID,1)
   elif npcId == 5100 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(AKLANTOS_GEM4_ID) == 0 or st.getQuestItemsCount(AKLANTOS_GEM5_ID) == 0 or st.getQuestItemsCount(AKLANTOS_GEM6_ID) == 0 :
      if st.getQuestItemsCount(AKLANTOS_GEM4_ID) == 0 :
        st.giveItems(AKLANTOS_GEM4_ID,1)
      elif st.getQuestItemsCount(AKLANTOS_GEM5_ID) == 0 :
        st.giveItems(AKLANTOS_GEM5_ID,1)
      elif st.getQuestItemsCount(AKLANTOS_GEM6_ID) == 0 :
        st.giveItems(AKLANTOS_GEM6_ID,1)
        st.takeItems(LEOPOLDS_JOURNAL1_ID,1)
   elif npcId == 601 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(VADINS_CRUCIFIX_ID) == 1 :
     if st.getRandom(100) < 50 and st.getQuestItemsCount(TAMLIN_ORC_AMULET_ID) < 20 :
      st.giveItems(TAMLIN_ORC_AMULET_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 602 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(VADINS_CRUCIFIX_ID) == 1 :
     if st.getRandom(100) < 50 and st.getQuestItemsCount(TAMLIN_ORC_AMULET_ID) < 20 :
      st.giveItems(TAMLIN_ORC_AMULET_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 557 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(IKERS_LIST_ID) == 1 :
     if st.getQuestItemsCount(DIRE_WYRM_FANG_ID) < 20 :
      st.giveItems(DIRE_WYRM_FANG_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 565 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(IKERS_LIST_ID) == 1 :
     if st.getQuestItemsCount(EN_GOLEM_HEARTSTONE_ID) < 20 :
      st.giveItems(EN_GOLEM_HEARTSTONE_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 577 or npcId == 578 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(IKERS_LIST_ID) == 1 :
     if st.getRandom(100) <= 50 and st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID) < 20 :
      st.giveItems(LETO_LIZARDMAN_CHARM_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 579 or npcId == 580 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(IKERS_LIST_ID) == 1 :
     if st.getRandom(100) <= 60 and st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID) < 20 :
      st.giveItems(LETO_LIZARDMAN_CHARM_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 581 or npcId == 582 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ALEXANDRIAS_BOOK_ID) == 1 and st.getQuestItemsCount(IKERS_LIST_ID) == 1 :
     if st.getRandom(100) <= 70 and st.getQuestItemsCount(LETO_LIZARDMAN_CHARM_ID) < 20 :
      st.giveItems(LETO_LIZARDMAN_CHARM_ID,1)
      st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(229,"229_TestOfWitchcraft","Test Of Witchcraft")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7630)

STARTING.addTalkId(7630)

STARTED.addTalkId(7063)
STARTED.addTalkId(7098)
STARTED.addTalkId(7110)
STARTED.addTalkId(7188)
STARTED.addTalkId(7314)
STARTED.addTalkId(7417)
STARTED.addTalkId(7435)
STARTED.addTalkId(7476)
STARTED.addTalkId(7630)
STARTED.addTalkId(7631)
STARTED.addTalkId(7632)
STARTED.addTalkId(7633)

STARTED.addKillId(5099)
STARTED.addKillId(5100)
STARTED.addKillId(5101)
STARTED.addKillId(557)
STARTED.addKillId(565)
STARTED.addKillId(577)
STARTED.addKillId(578)
STARTED.addKillId(579)
STARTED.addKillId(580)
STARTED.addKillId(581)
STARTED.addKillId(582)
STARTED.addKillId(601)
STARTED.addKillId(602)

STARTED.addQuestDrop(7098,ALEXANDRIAS_BOOK_ID,1)
STARTED.addQuestDrop(7110,AKLANTOS_GEM1_ID,1)
STARTED.addQuestDrop(7476,AKLANTOS_GEM2_ID,1)
STARTED.addQuestDrop(5099,AKLANTOS_GEM3_ID,1)
STARTED.addQuestDrop(5100,AKLANTOS_GEM4_ID,1)
STARTED.addQuestDrop(5100,AKLANTOS_GEM5_ID,1)
STARTED.addQuestDrop(5100,AKLANTOS_GEM6_ID,1)
STARTED.addQuestDrop(7630,BRIMSTONE1_ID,1)
STARTED.addQuestDrop(5101,PURGATORY_KEY_ID,1)
STARTED.addQuestDrop(7417,SWORD_OF_BINDING_ID,1)
STARTED.addQuestDrop(7110,IKERS_AMULET_ID,1)
STARTED.addQuestDrop(7630,ORIMS_INSTRUCTIONS_ID,1)
STARTED.addQuestDrop(5101,ZERUEL_BIND_CRYSTAL_ID,1)
STARTED.addQuestDrop(7630,ORIMS_DIAGRAM_ID,1)
STARTED.addQuestDrop(7630,ORIMS_LETTER2_ID,1)
STARTED.addQuestDrop(7110,IKERS_LIST_ID,1)
STARTED.addQuestDrop(7314,NESTLE_MEMO1_ID,1)
STARTED.addQuestDrop(7630,ORIMS_LETTER1_ID,1)
STARTED.addQuestDrop(7188,VADINS_SANCTIONS_ID,1)
STARTED.addQuestDrop(7417,SIR_VASPERS_LETTER_ID,1)
STARTED.addQuestDrop(601,TAMLIN_ORC_AMULET_ID,1)
STARTED.addQuestDrop(602,TAMLIN_ORC_AMULET_ID,1)
STARTED.addQuestDrop(7188,VADINS_CRUCIFIX_ID,1)
STARTED.addQuestDrop(7633,BRIMSTONE2_ID,1)
STARTED.addQuestDrop(7110,SOULTRAP_CRYSTAL_ID,1)
STARTED.addQuestDrop(7063,LARS_MEMO1_ID,1)
STARTED.addQuestDrop(7435,LEOPOLDS_JOURNAL1_ID,1)
