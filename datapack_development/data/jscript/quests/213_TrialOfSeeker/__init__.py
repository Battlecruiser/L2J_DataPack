# Made by Mr. Have fun!
# Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DUFNERS_LETTER_ID,  TERYS_ORDER1_ID,    TERYS_ORDER2_ID,           TERYS_LETTER_ID,      \
VIKTORS_LETTER_ID,  HAWKEYES_LETTER_ID, MYSTERIOUS_RUNESTONE_ID,   OL_MAHUM_RUNESTONE_ID,\
TUREK_RUNESTONE_ID, ANT_RUNESTONE_ID,   TURAK_BUGBEAR_RUNESTONE_ID,TERYS_BOX_ID,         \
VIKTORS_REQUEST_ID, MEDUSAS_SCALES_ID,  SILENS_RUNESTONE_ID,       ANALYSIS_REQUEST_ID,  \
MARINAS_LETTER_ID,  EXPERIMENT_TOOLS_ID,ANALYSIS_RESULT_ID,        TERYS_ORDER3_ID,      \
LIST_OF_HOST_ID,    ABYSS_RUNESTONE1_ID,ABYSS_RUNESTONE2_ID,       ABYSS_RUNESTONE3_ID,  \
ABYSS_RUNESTONE4_ID,TERYS_REPORT_ID,    MARK_OF_SEEKER_ID = range(2647,2674)

DROPLIST={
198:[TERYS_ORDER1_ID,MYSTERIOUS_RUNESTONE_ID,   10,1],
211:[TERYS_ORDER2_ID,OL_MAHUM_RUNESTONE_ID,     25,1],
495:[TERYS_ORDER2_ID,TUREK_RUNESTONE_ID,        25,1],
80 :[TERYS_ORDER2_ID,ANT_RUNESTONE_ID,          25,1],
249:[TERYS_ORDER2_ID,TURAK_BUGBEAR_RUNESTONE_ID,25,1],
234:[LIST_OF_HOST_ID,ABYSS_RUNESTONE1_ID,       25,1],
270:[LIST_OF_HOST_ID,ABYSS_RUNESTONE2_ID,       25,1],
88 :[LIST_OF_HOST_ID,ABYSS_RUNESTONE3_ID,       25,1],
580:[LIST_OF_HOST_ID,ABYSS_RUNESTONE4_ID,       25,1],
158:[VIKTORS_REQUEST_ID,MEDUSAS_SCALES_ID,      30,10]
}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7106-05.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(DUFNERS_LETTER_ID,1)
    elif event == "7064-03.htm" :
      st.giveItems(TERYS_ORDER1_ID,1)
      st.takeItems(DUFNERS_LETTER_ID,1)
    elif event == "7064-06.htm" :
      st.takeItems(MYSTERIOUS_RUNESTONE_ID,1)
      st.giveItems(TERYS_ORDER2_ID,1)
      st.takeItems(TERYS_ORDER1_ID,1)
    elif event == "7064-10.htm" :
      st.takeItems(OL_MAHUM_RUNESTONE_ID,1)
      st.takeItems(TUREK_RUNESTONE_ID,1)
      st.takeItems(ANT_RUNESTONE_ID,1)
      st.takeItems(TURAK_BUGBEAR_RUNESTONE_ID,1)
      st.takeItems(TERYS_ORDER2_ID,1)
      st.giveItems(TERYS_LETTER_ID,1)
      st.giveItems(TERYS_BOX_ID,1)
    elif event == "7064-18.htm" :
      if st.getPlayer().getLevel()<36 :
        htmltext = "7064-17.htm"
        st.giveItems(TERYS_ORDER3_ID,1)
        st.takeItems(ANALYSIS_RESULT_ID,1)
      else:
        st.giveItems(LIST_OF_HOST_ID,1)
        st.takeItems(ANALYSIS_RESULT_ID,1)
    elif event == "7684-05.htm" :
      st.giveItems(VIKTORS_LETTER_ID,1)
      st.takeItems(TERYS_LETTER_ID,1)
    elif event == "7684-11.htm" :
      st.giveItems(VIKTORS_REQUEST_ID,1)
      st.takeItems(TERYS_LETTER_ID,1)
      st.takeItems(TERYS_BOX_ID,1)
      st.takeItems(HAWKEYES_LETTER_ID,1)
      st.takeItems(VIKTORS_LETTER_ID,st.getQuestItemsCount(VIKTORS_LETTER_ID))
    elif event == "7684-15.htm" :
      st.takeItems(VIKTORS_REQUEST_ID,1)
      st.takeItems(MEDUSAS_SCALES_ID,st.getQuestItemsCount(MEDUSAS_SCALES_ID))
      st.giveItems(SILENS_RUNESTONE_ID,1)
      st.giveItems(ANALYSIS_REQUEST_ID,1)
    elif event == "7715-02.htm" :
      st.takeItems(SILENS_RUNESTONE_ID,1)
      st.takeItems(ANALYSIS_REQUEST_ID,1)
      st.giveItems(MARINAS_LETTER_ID,1)
    elif event == "7715-05.htm" :
      st.takeItems(EXPERIMENT_TOOLS_ID,1)
      st.giveItems(ANALYSIS_RESULT_ID,1)
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7106 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
     if st.getPlayer().getClassId().getId() in [ 0x07, 0x16, 0x23 ] :
       if st.getPlayer().getLevel() >= 35 :
         htmltext = "7106-03.htm"
       else:
         htmltext = "7106-02.htm"
         st.exitQuest(1)
     else:
       htmltext = "7106-00.htm"
       st.exitQuest(1)
   elif npcId == 7106 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7106 and int(st.get("cond")) == 1 and int(st.get("onlyone")) == 0:
          if st.getQuestItemsCount(DUFNERS_LETTER_ID) == 1 and st.getQuestItemsCount(TERYS_REPORT_ID) == 0 :
            htmltext = "7106-06.htm"
          elif st.getQuestItemsCount(DUFNERS_LETTER_ID) == 0 and st.getQuestItemsCount(TERYS_REPORT_ID) == 0 :
            htmltext = "7106-07.htm"
          elif st.getQuestItemsCount(DUFNERS_LETTER_ID) == 0 and st.getQuestItemsCount(TERYS_REPORT_ID) == 1 :
              st.addExpAndSp(72126,11000)
              st.giveItems(7562,8)
              htmltext = "7106-08.htm"
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

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   required,item,chance,maxqty=DROPLIST[npcId]
   count = st.getQuestItemsCount(item)
   if st.getQuestItemsCount(required) and count < maxqty :
      if st.getRandom(100) < chance :
        st.giveItems(item,1)
        if count+1 == maxqty :
           st.playSound("Itemsound.quest_middle")
        else :
           st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(213,"213_TrialOfSeeker","Trial Of Seeker")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7106)

CREATED.addTalkId(7106)
STARTING.addTalkId(7106)
COMPLETED.addTalkId(7106)

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

print "importing quests: 213: Trial Of Seeker"
