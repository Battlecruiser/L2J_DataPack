# Made by Mr. Have fun! - Version 0.3 by DrLecter
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
    if event == "7223-04.htm" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER2_ID) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER3_ID) == 0 :
          st.giveItems(ARUJIENS_LETTER1_ID,1)
    elif event == "7223-08.htm" :
        st.takeItems(ARUJIENS_LETTER3_ID,1)
        st.giveItems(POETRY_BOOK_ID,1)
        st.set("cond","4")
    elif event == "7223-10.htm" :
        st.takeItems(ARUJIENS_LETTER3_ID,1)
        st.giveItems(ADENA_ID,450)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7223 and id == CREATED :
      if st.getPlayer().getRace().ordinal() != 1 and st.getPlayer().getRace().ordinal() != 0 :
         htmltext = "7223-00.htm"
      elif st.getPlayer().getLevel()>1 :
         htmltext = "7223-02.htm"
         return htmltext
      else:
         htmltext = "7223-01.htm"
         st.exitQuest(1)
   elif npcId == 7223 and id== COMPLETED :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7223 and int(st.get("cond"))>=1 :
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) :
          htmltext = "7223-05.htm"
        elif st.getQuestItemsCount(ARUJIENS_LETTER3_ID) :
          htmltext = "7223-07.htm"
        elif st.getQuestItemsCount(ARUJIENS_LETTER2_ID) :
          htmltext = "7223-06.htm"
        elif st.getQuestItemsCount(POETRY_BOOK_ID) :
          htmltext = "7223-11.htm"
        elif st.getQuestItemsCount(GREENIS_LETTER_ID) :
          st.giveItems(BEGINNERS_POTION_ID,5)
          st.takeItems(GREENIS_LETTER_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          htmltext = "7223-10.htm"
   elif npcId == 7146 and int(st.get("cond"))==1 :
        if st.getQuestItemsCount(ARUJIENS_LETTER1_ID) :
          htmltext = "7146-01.htm"
          st.takeItems(ARUJIENS_LETTER1_ID,1)
          st.giveItems(ARUJIENS_LETTER2_ID,1)
          st.set("cond","2")
        elif st.getQuestItemsCount(ARUJIENS_LETTER2_ID) or st.getQuestItemsCount(ARUJIENS_LETTER3_ID) or st.getQuestItemsCount(POETRY_BOOK_ID) or st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7146-02.htm"
   elif npcId == 7150 and int(st.get("cond"))==2 and st.getQuestItemsCount(ARUJIENS_LETTER1_ID)==0 :
        if st.getQuestItemsCount(ARUJIENS_LETTER2_ID) :
          htmltext = "7150-01.htm"
          st.takeItems(ARUJIENS_LETTER2_ID,1)
          st.giveItems(ARUJIENS_LETTER3_ID,1)
          st.set("cond","3")
        elif st.getQuestItemsCount(ARUJIENS_LETTER3_ID) or st.getQuestItemsCount(POETRY_BOOK_ID) or st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7150-02.htm"
   elif npcId == 7157 and int(st.get("cond"))==4 :
        if st.getQuestItemsCount(POETRY_BOOK_ID) :
          htmltext = "7157-02.htm"
          st.takeItems(POETRY_BOOK_ID,1)
          st.giveItems(GREENIS_LETTER_ID,1)
          st.set("cond","5")
   elif npcId == 7157 and st.getQuestItemsCount(GREENIS_LETTER_ID) :
          htmltext = "7157-03.htm"
   elif npcId == 7157 and (st.getQuestItemsCount(ARUJIENS_LETTER1_ID) or st.getQuestItemsCount(ARUJIENS_LETTER2_ID) or st.getQuestItemsCount(ARUJIENS_LETTER3_ID)) :
          htmltext = "7157-01.htm"
   return htmltext

QUEST       = Quest(2,"2_WhatWomenWant1","What Women Want1")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7223)

CREATED.addTalkId(7223)
COMPLETED.addTalkId(7223)

STARTED.addTalkId(7146)
STARTED.addTalkId(7150)
STARTED.addTalkId(7157)
STARTED.addTalkId(7223)

STARTED.addQuestDrop(7157,GREENIS_LETTER_ID,1)
STARTED.addQuestDrop(7150,ARUJIENS_LETTER3_ID,1)
STARTED.addQuestDrop(7223,ARUJIENS_LETTER1_ID,1)
STARTED.addQuestDrop(7146,ARUJIENS_LETTER2_ID,1)
STARTED.addQuestDrop(7223,POETRY_BOOK_ID,1)

print "importing quests: 2: What Women Want"
