# Maked by Mr. Have fun! Version 0.2
print "importing quests: 1: Letters Of Love1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DARINGS_LETTER_ID = 687
RAPUNZELS_KERCHIEF_ID = 688
DARINGS_RECEIPT_ID = 1079
BAULS_POTION_ID = 1080
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(DARINGS_LETTER_ID) == 0 :
          st.giveItems(DARINGS_LETTER_ID,1)
        htmltext = "7048-06.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7048 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel()>1 :
            htmltext = "7048-02.htm"
            return htmltext
          else:
            htmltext = "7048-01.htm"
        else:
          htmltext = "7048-01.htm"
   elif npcId == 7048 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7006 and int(st.get("cond")) and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(RAPUNZELS_KERCHIEF_ID) == 0 and st.getQuestItemsCount(DARINGS_LETTER_ID) :
          st.giveItems(RAPUNZELS_KERCHIEF_ID,1)
          st.takeItems(DARINGS_LETTER_ID,1)
          htmltext = "7006-01.htm"
          st.set("cond","2")
        elif st.getQuestItemsCount(BAULS_POTION_ID) or st.getQuestItemsCount(DARINGS_RECEIPT_ID) :
            htmltext = "7006-03.htm"
        elif st.getQuestItemsCount(RAPUNZELS_KERCHIEF_ID) :
              htmltext = "7006-02.htm"
   elif npcId == 7048 and int(st.get("cond")) and st.getQuestItemsCount(RAPUNZELS_KERCHIEF_ID) and int(st.get("onlyone"))==0 :
        if int(st.get("id")) != 1 :
          st.set("id","1")
          st.giveItems(DARINGS_RECEIPT_ID,1)
          st.takeItems(RAPUNZELS_KERCHIEF_ID,1)
          htmltext = "7048-08.htm"
          st.set("cond","3")
   elif npcId == 7033 and int(st.get("cond")) and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(DARINGS_RECEIPT_ID)>0 :
          htmltext = "7033-01.htm"
          st.takeItems(DARINGS_RECEIPT_ID,1)
          st.giveItems(BAULS_POTION_ID,1)
          st.set("cond","4")
        elif st.getQuestItemsCount(BAULS_POTION_ID)>0 :
          htmltext = "7033-02.htm"
   elif npcId == 7048 and int(st.get("cond")) and st.getQuestItemsCount(RAPUNZELS_KERCHIEF_ID)==0 and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(DARINGS_RECEIPT_ID)>0 :
          htmltext = "7048-09.htm"
        elif st.getQuestItemsCount(BAULS_POTION_ID)>0 :
            htmltext = "7048-10.htm"
            st.takeItems(BAULS_POTION_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.giveItems(ADENA_ID,450)
            st.set("onlyone","1")
        else:
          htmltext = "7048-07.htm"
   return htmltext

QUEST       = Quest(1,"1_LettersOfLove1","Letters Of Love1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7048)

STARTED.addTalkId(7006)
STARTED.addTalkId(7033)
STARTED.addTalkId(7048)


STARTED.addQuestDrop(7048,DARINGS_LETTER_ID,1)
STARTED.addQuestDrop(7006,RAPUNZELS_KERCHIEF_ID,1)
STARTED.addQuestDrop(7048,DARINGS_RECEIPT_ID,1)
STARTED.addQuestDrop(7033,BAULS_POTION_ID,1)
