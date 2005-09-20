# Maked by Mr. Have fun! Version 0.2
print "importing quests: 213: Trial Of Seeker"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DUFNERS_LETTER_ID = 2647
TERYS_ORDER1_ID = 2648
TERYS_ORDER2_ID = 2649
TERYS_LETTER_ID = 2650
VIKTORS_LETTER_ID = 2651
HAWKEYES_LETTER_ID = 2652
MYSTERIOUS_RUNESTONE_ID = 2653
TERYS_REPORT_ID = 2672
MARK_OF_SEEKER_ID = 2673
OL_MAHUM_RUNESTONE_ID = 2654
TUREK_RUNESTONE_ID = 2655
ANT_RUNESTONE_ID = 2656
TURAK_BUGBEAR_RUNESTONE_ID = 2657
TERYS_BOX_ID = 2658
TERYS_ORDER3_ID = 2666
ANALYSIS_RESULT_ID = 2665
LIST_OF_HOST_ID = 2667
ABYSS_RUNESTONE1_ID = 2668
ABYSS_RUNESTONE2_ID = 2669
ABYSS_RUNESTONE3_ID = 2670
ABYSS_RUNESTONE4_ID = 2671
VIKTORS_REQUEST_ID = 2659
MEDUSAS_SCALES_ID = 2660
SILENS_RUNESTONE_ID = 2661
ANALYSIS_REQUEST_ID = 2662
MARINAS_LETTER_ID = 2663
EXPERIMENT_TOOLS_ID = 2664

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7106-05.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(DUFNERS_LETTER_ID,1)
    elif event == "7106_1" :
          htmltext = "7106-04.htm"
          st.set("cond","1")
          return htmltext
    elif event == "7064_1" :
          htmltext = "7064-02.htm"
    elif event == "7064_2" :
          htmltext = "7064-03.htm"
          st.giveItems(TERYS_ORDER1_ID,1)
          st.takeItems(DUFNERS_LETTER_ID,1)
    elif event == "7064_3" :
          htmltext = "7064-06.htm"
          st.takeItems(MYSTERIOUS_RUNESTONE_ID,1)
          st.giveItems(TERYS_ORDER2_ID,1)
          st.takeItems(TERYS_ORDER1_ID,1)
    elif event == "7064_4" :
          htmltext = "7064-10.htm"
          st.takeItems(OL_MAHUM_RUNESTONE_ID,1)
          st.takeItems(TUREK_RUNESTONE_ID,1)
          st.takeItems(ANT_RUNESTONE_ID,1)
          st.takeItems(TURAK_BUGBEAR_RUNESTONE_ID,1)
          st.takeItems(TERYS_ORDER2_ID,1)
          st.giveItems(TERYS_LETTER_ID,1)
          st.giveItems(TERYS_BOX_ID,1)
    elif event == "7064_5" :
          htmltext = "7064-16.htm"
    elif event == "7064_6" :
          if st.getPlayer().getLevel()<36 :
            htmltext = "7064-17.htm"
            st.giveItems(TERYS_ORDER3_ID,1)
            st.takeItems(ANALYSIS_RESULT_ID,1)
          else:
            htmltext = "7064-18.htm"
            st.giveItems(LIST_OF_HOST_ID,1)
            st.takeItems(ANALYSIS_RESULT_ID,1)
    elif event == "7684_1" :
          htmltext = "7684-02.htm"
    elif event == "7684_2" :
          htmltext = "7684-03.htm"
    elif event == "7684_3" :
          htmltext = "7684-04.htm"
    elif event == "7684_4" :
          htmltext = "7684-05.htm"
          st.giveItems(VIKTORS_LETTER_ID,1)
          st.takeItems(TERYS_LETTER_ID,1)
    elif event == "7684_5" :
          htmltext = "7684-06.htm"
    elif event == "7684_6" :
          htmltext = "7684-07.htm"
    elif event == "7684_7" :
          htmltext = "7684-08.htm"
    elif event == "7684_8" :
          htmltext = "7684-09.htm"
    elif event == "7684_9" :
          htmltext = "7684-10.htm"
    elif event == "7684_10" :
          htmltext = "7684-11.htm"
          st.giveItems(VIKTORS_REQUEST_ID,1)
          st.takeItems(TERYS_LETTER_ID,1)
          st.takeItems(TERYS_BOX_ID,1)
          st.takeItems(HAWKEYES_LETTER_ID,1)
          st.takeItems(VIKTORS_LETTER_ID,st.getQuestItemsCount(VIKTORS_LETTER_ID))
    elif event == "7684_11" :
          htmltext = "7684-15.htm"
          st.takeItems(VIKTORS_REQUEST_ID,1)
          st.takeItems(MEDUSAS_SCALES_ID,st.getQuestItemsCount(MEDUSAS_SCALES_ID))
          st.giveItems(SILENS_RUNESTONE_ID,1)
          st.giveItems(ANALYSIS_REQUEST_ID,1)
    elif event == "7715_1" :
          htmltext = "7715-02.htm"
          st.takeItems(SILENS_RUNESTONE_ID,1)
          st.takeItems(ANALYSIS_REQUEST_ID,1)
          st.giveItems(MARINAS_LETTER_ID,1)
    elif event == "7715_2" :
          htmltext = "7715-05.htm"
          st.takeItems(EXPERIMENT_TOOLS_ID,1)
          st.giveItems(ANALYSIS_RESULT_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7106 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if (st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23) == 0 :
            htmltext = "7106-00.htm"
          elif (st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23) and st.getPlayer().getLevel()<35 :
            htmltext = "7106-02.htm"
          else:
            htmltext = "7106-03.htm"
        else:
          htmltext = "7106-03.htm"
   elif npcId == 7106 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7106 and int(st.get("cond"))==1 :
          if st.getQuestItemsCount(DUFNERS_LETTER_ID) == 1 and st.getQuestItemsCount(TERYS_REPORT_ID) == 0 :
            htmltext = "7106-06.htm"
          elif st.getQuestItemsCount(DUFNERS_LETTER_ID) == 0 and st.getQuestItemsCount(TERYS_REPORT_ID) == 0 :
            htmltext = "7106-07.htm"
          elif st.getQuestItemsCount(DUFNERS_LETTER_ID) == 0 and st.getQuestItemsCount(TERYS_REPORT_ID) == 1 :
            if st.getGameTicks() != int(st.get("id")) :
              st.set("id",str(st.getGameTicks()))
              st.addExpAndSp(27000,3100)
              htmlfile = "7106-08.htm"
              st.set("cond","0")
              st.set("onlyone","1")
              st.setState(COMPLETED)
              st.playSound("ItemSound.quest_finish")
              st.takeItems(TERYS_REPORT_ID,1)
              st.giveItems(MARK_OF_SEEKER_ID,1)
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(DUFNERS_LETTER_ID)==1 :
        htmltext = "7064-01.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_ORDER1_ID)==1 :
      if st.getQuestItemsCount(MYSTERIOUS_RUNESTONE_ID) == 0 :
        htmltext = "7064-04.htm"
      else:
        htmltext = "7064-05.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_ORDER2_ID)==1 :
      if st.getQuestItemsCount(TERYS_ORDER2_ID) == 1 :
        if st.getQuestItemsCount(OL_MAHUM_RUNESTONE_ID)+st.getQuestItemsCount(TUREK_RUNESTONE_ID)+st.getQuestItemsCount(ANT_RUNESTONE_ID)+st.getQuestItemsCount(TURAK_BUGBEAR_RUNESTONE_ID)<4 :
          htmltext = "7064-08.htm"
        else:
          htmltext = "7064-09.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_LETTER_ID)==1 :
      htmltext = "7064-11.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(VIKTORS_LETTER_ID)==1 :
      htmltext = "7064-12.htm"
      st.takeItems(VIKTORS_LETTER_ID,1)
      st.giveItems(HAWKEYES_LETTER_ID,1)
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(HAWKEYES_LETTER_ID)==1 :
      htmltext = "7064-13.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and (st.getQuestItemsCount(VIKTORS_REQUEST_ID)==1 or st.getQuestItemsCount(ANALYSIS_REQUEST_ID)==1 or st.getQuestItemsCount(MARINAS_LETTER_ID)==1 or st.getQuestItemsCount(EXPERIMENT_TOOLS_ID)==1) :
      htmltext = "7064-14.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(ANALYSIS_RESULT_ID)==1 :
      htmltext = "7064-15.htm"
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_ORDER3_ID)==1 :
      if st.getPlayer().getLevel()<36 :
        htmltext = "7064-20.htm"
      else:
        htmltext = "7064-21.htm"
        st.giveItems(LIST_OF_HOST_ID,1)
        st.takeItems(TERYS_ORDER3_ID,1)
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(LIST_OF_HOST_ID)==1 :
      if st.getQuestItemsCount(ABYSS_RUNESTONE1_ID)+st.getQuestItemsCount(ABYSS_RUNESTONE2_ID)+st.getQuestItemsCount(ABYSS_RUNESTONE3_ID)+st.getQuestItemsCount(ABYSS_RUNESTONE4_ID)<4 :
        htmltext = "7064-22.htm"
      else:
        htmltext = "7064-23.htm"
        st.giveItems(TERYS_REPORT_ID,1)
        st.takeItems(LIST_OF_HOST_ID,1)
        st.takeItems(ABYSS_RUNESTONE1_ID,1)
        st.takeItems(ABYSS_RUNESTONE2_ID,1)
        st.takeItems(ABYSS_RUNESTONE3_ID,1)
        st.takeItems(ABYSS_RUNESTONE4_ID,1)
   elif npcId == 7064 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_REPORT_ID)==1 :
      htmltext = "7064-24.htm"
   elif npcId == 7684 and int(st.get("cond"))==1 and st.getQuestItemsCount(TERYS_LETTER_ID)==1 :
      htmltext = "7684-01.htm"
   elif npcId == 7684 and int(st.get("cond"))==1 and st.getQuestItemsCount(HAWKEYES_LETTER_ID)==1 :
      htmltext = "7684-12.htm"
   elif npcId == 7684 and int(st.get("cond"))==1 and st.getQuestItemsCount(VIKTORS_REQUEST_ID)==1 :
      if st.getQuestItemsCount(MEDUSAS_SCALES_ID)<10 :
        htmltext = "7684-13.htm"
      else:
        htmltext = "7684-14.htm"
   elif npcId == 7684 and int(st.get("cond"))==1 and st.getQuestItemsCount(SILENS_RUNESTONE_ID)==1 and st.getQuestItemsCount(ANALYSIS_REQUEST_ID)==1 :
      htmltext = "7684-16.htm"
   elif npcId == 7684 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MARINAS_LETTER_ID)==1 and st.getQuestItemsCount(EXPERIMENT_TOOLS_ID)==1 and st.getQuestItemsCount(ANALYSIS_RESULT_ID)==1 and st.getQuestItemsCount(TERYS_REPORT_ID)==1) :
      htmltext = "7684-17.htm"
   elif npcId == 7715 and int(st.get("cond"))==1 and st.getQuestItemsCount(SILENS_RUNESTONE_ID)==1 and st.getQuestItemsCount(ANALYSIS_REQUEST_ID)==1 :
      htmltext = "7715-01.htm"
   elif npcId == 7715 and int(st.get("cond"))==1 and st.getQuestItemsCount(MARINAS_LETTER_ID)==1 :
      htmltext = "7715-03.htm"
   elif npcId == 7715 and int(st.get("cond"))==1 and st.getQuestItemsCount(EXPERIMENT_TOOLS_ID)==1 :
      htmltext = "7715-04.htm"
   elif npcId == 7715 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ANALYSIS_RESULT_ID)==1 or st.getQuestItemsCount(TERYS_REPORT_ID)==1) :
      htmltext = "7715-06.htm"
   elif npcId == 7526 and int(st.get("cond"))==1 and st.getQuestItemsCount(MARINAS_LETTER_ID)==1 :
      htmltext = "7526-01.htm"
      st.takeItems(MARINAS_LETTER_ID,1)
      st.giveItems(EXPERIMENT_TOOLS_ID,1)
   elif npcId == 7526 and int(st.get("cond"))==1 and st.getQuestItemsCount(EXPERIMENT_TOOLS_ID)==1 :
      htmltext = "7526-02.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 198 :
    if int(st.get("cond")) and st.getQuestItemsCount(TERYS_ORDER1_ID) == 1 and st.getQuestItemsCount(MYSTERIOUS_RUNESTONE_ID) == 0 :
      if st.getRandom(100) < 10 :
        st.giveItems(MYSTERIOUS_RUNESTONE_ID,1)
   elif npcId == 211 :
    if int(st.get("cond")) and st.getQuestItemsCount(TERYS_ORDER2_ID) == 1 and st.getQuestItemsCount(OL_MAHUM_RUNESTONE_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(OL_MAHUM_RUNESTONE_ID,1)
   elif npcId == 495 :
    if int(st.get("cond")) and st.getQuestItemsCount(TERYS_ORDER2_ID) == 1 and st.getQuestItemsCount(TUREK_RUNESTONE_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(TUREK_RUNESTONE_ID,1)
   elif npcId == 80 :
    if int(st.get("cond")) and st.getQuestItemsCount(TERYS_ORDER2_ID) == 1 and st.getQuestItemsCount(ANT_RUNESTONE_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(ANT_RUNESTONE_ID,1)
   elif npcId == 249 :
    if int(st.get("cond")) and st.getQuestItemsCount(TERYS_ORDER2_ID) == 1 and st.getQuestItemsCount(TURAK_BUGBEAR_RUNESTONE_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(TURAK_BUGBEAR_RUNESTONE_ID,1)
   elif npcId == 234 :
    if int(st.get("cond")) and st.getQuestItemsCount(LIST_OF_HOST_ID) == 1 and st.getQuestItemsCount(ABYSS_RUNESTONE1_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(ABYSS_RUNESTONE1_ID,1)
   elif npcId == 270 :
    if int(st.get("cond")) and st.getQuestItemsCount(LIST_OF_HOST_ID) == 1 and st.getQuestItemsCount(ABYSS_RUNESTONE2_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(ABYSS_RUNESTONE2_ID,1)
   elif npcId == 88 :
    if int(st.get("cond")) and st.getQuestItemsCount(LIST_OF_HOST_ID) == 1 and st.getQuestItemsCount(ABYSS_RUNESTONE3_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(ABYSS_RUNESTONE3_ID,1)
   elif npcId == 580 :
    if int(st.get("cond")) and st.getQuestItemsCount(LIST_OF_HOST_ID) == 1 and st.getQuestItemsCount(ABYSS_RUNESTONE4_ID) == 0 :
      if st.getRandom(100) < 25 :
        st.giveItems(ABYSS_RUNESTONE4_ID,1)
   elif npcId == 158 :
    if int(st.get("cond")) and st.getQuestItemsCount(VIKTORS_REQUEST_ID) == 1 and st.getQuestItemsCount(MEDUSAS_SCALES_ID)<10 :
      if st.getRandom(100) < 30 :
        st.giveItems(MEDUSAS_SCALES_ID,1)
   return

QUEST       = Quest(213,"213_TrialOfSeeker","Trial Of Seeker")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7106)

STARTING.addTalkId(7106)

STARTED.addTalkId(7064)
STARTED.addTalkId(7106)
STARTED.addTalkId(7526)
STARTED.addTalkId(7684)
STARTED.addTalkId(7715)

STARTED.addKillId(158)
STARTED.addKillId(198)
STARTED.addKillId(211)
STARTED.addKillId(234)
STARTED.addKillId(249)
STARTED.addKillId(270)
STARTED.addKillId(495)
STARTED.addKillId(580)
STARTED.addKillId(80)
STARTED.addKillId(88)

STARTED.addQuestDrop(7064,TERYS_REPORT_ID,1)
STARTED.addQuestDrop(7106,DUFNERS_LETTER_ID,1)
STARTED.addQuestDrop(198,MYSTERIOUS_RUNESTONE_ID,1)
STARTED.addQuestDrop(7064,TERYS_ORDER1_ID,1)
STARTED.addQuestDrop(211,OL_MAHUM_RUNESTONE_ID,1)
STARTED.addQuestDrop(495,TUREK_RUNESTONE_ID,1)
STARTED.addQuestDrop(80,ANT_RUNESTONE_ID,1)
STARTED.addQuestDrop(249,TURAK_BUGBEAR_RUNESTONE_ID,1)
STARTED.addQuestDrop(7064,TERYS_ORDER2_ID,1)
STARTED.addQuestDrop(7715,ANALYSIS_RESULT_ID,1)
STARTED.addQuestDrop(7684,VIKTORS_LETTER_ID,1)
STARTED.addQuestDrop(7064,TERYS_ORDER3_ID,1)
STARTED.addQuestDrop(7064,LIST_OF_HOST_ID,1)
STARTED.addQuestDrop(234,ABYSS_RUNESTONE1_ID,1)
STARTED.addQuestDrop(270,ABYSS_RUNESTONE2_ID,1)
STARTED.addQuestDrop(88,ABYSS_RUNESTONE3_ID,1)
STARTED.addQuestDrop(580,ABYSS_RUNESTONE4_ID,1)
STARTED.addQuestDrop(7064,TERYS_LETTER_ID,1)
STARTED.addQuestDrop(7064,TERYS_BOX_ID,1)
STARTED.addQuestDrop(7064,HAWKEYES_LETTER_ID,1)
STARTED.addQuestDrop(7684,VIKTORS_REQUEST_ID,1)
STARTED.addQuestDrop(158,MEDUSAS_SCALES_ID,1)
STARTED.addQuestDrop(7684,SILENS_RUNESTONE_ID,1)
STARTED.addQuestDrop(7684,ANALYSIS_REQUEST_ID,1)
STARTED.addQuestDrop(7526,EXPERIMENT_TOOLS_ID,1)
STARTED.addQuestDrop(7715,MARINAS_LETTER_ID,1)
