# Maked by Mr. Have fun! Version 0.2
print "importing quests: 2: What Women Want1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ARUJIENS_LETTER1_ID = 1092
ARUJIENS_LETTER2_ID = 1093
ARUJIENS_LETTER3_ID = 1094
POETRY_BOOK_ID = 689
GREENIS_LETTER_ID = 693
BEGINNERS_POTION_ID = 1073
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER2_ID) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER3_ID) == 0 :
          st.giveItems(ARUJIENS_LETTER1_ID,1)
        htmltext = "7223-04.htm"
    elif event == "2_1" :
          st.takeItems(ARUJIENS_LETTER3_ID,1)
          st.giveItems(POETRY_BOOK_ID,1)
          htmltext = "7223-08.htm"
    elif event == "2_2" and int(st.get("onlyone")) == 0 :
            st.takeItems(ARUJIENS_LETTER3_ID,1)
            st.giveItems(ADENA_ID,450)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7223 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getRace().ordinal() != 1 and st.getPlayer().getRace().ordinal() != 0 :
            htmltext = "7223-00.htm"
          elif st.getPlayer().getLevel()>1 :
            htmltext = "7223-02.htm"
            return htmltext
          else:
            htmltext = "7223-01.htm"
        else:
          htmltext = "7223-01.htm"
   elif npcId == 7223 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7223 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) :
          htmltext = "7223-05.htm"
        elif st.getQuestItemsCount(ARUJIENS_LETTER3_ID) :
          htmltext = "7223-07.htm"
        elif st.getQuestItemsCount(ARUJIENS_LETTER2_ID) :
          htmltext = "7223-06.htm"
        elif st.getQuestItemsCount(POETRY_BOOK_ID) :
          htmltext = "7223-11.htm"
        elif st.getQuestItemsCount(GREENIS_LETTER_ID) :
          if int(st.get("id")) != 2 :
            st.set("id","2")
            st.giveItems(BEGINNERS_POTION_ID,5)
            st.takeItems(GREENIS_LETTER_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
            htmltext = "7223-10.htm"
   elif npcId == 7146 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) :
          htmltext = "7146-01.htm"
          st.takeItems(ARUJIENS_LETTER1_ID,1)
          st.giveItems(ARUJIENS_LETTER2_ID,1)
        elif st.getQuestItemsCount(ARUJIENS_LETTER2_ID) or st.getQuestItemsCount(ARUJIENS_LETTER3_ID) or st.getQuestItemsCount(POETRY_BOOK_ID) or st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7146-02.htm"
   elif npcId == 7150 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 and st.getQuestItemsCount(ARUJIENS_LETTER1_ID)==0 :
        if st.getQuestItemsCount(ARUJIENS_LETTER2_ID) :
          htmltext = "7150-01.htm"
          st.takeItems(ARUJIENS_LETTER2_ID,1)
          st.giveItems(ARUJIENS_LETTER3_ID,1)
        elif st.getQuestItemsCount(ARUJIENS_LETTER3_ID) or st.getQuestItemsCount(POETRY_BOOK_ID) or st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7150-02.htm"
   elif npcId == 7157 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 :
        if st.getQuestItemsCount(POETRY_BOOK_ID) :
          htmltext = "7157-02.htm"
          st.takeItems(POETRY_BOOK_ID,1)
          st.giveItems(GREENIS_LETTER_ID,1)
        elif st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7157-03.htm"
        elif st.getQuestItemsCount(ARUJIENS_LETTER1_ID) or st.getQuestItemsCount(ARUJIENS_LETTER2_ID) or st.getQuestItemsCount(ARUJIENS_LETTER3_ID) :
          htmltext = "7157-01.htm"
   return htmltext

QUEST       = Quest(2,"2_WhatWomenWant1","What Women Want1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7223)

STARTED.addTalkId(7146)
STARTED.addTalkId(7150)
STARTED.addTalkId(7157)
STARTED.addTalkId(7223)


STARTED.addQuestDrop(7157,GREENIS_LETTER_ID,1)
STARTED.addQuestDrop(7150,ARUJIENS_LETTER3_ID,1)
STARTED.addQuestDrop(7223,ARUJIENS_LETTER1_ID,1)
STARTED.addQuestDrop(7146,ARUJIENS_LETTER2_ID,1)
STARTED.addQuestDrop(7223,POETRY_BOOK_ID,1)
