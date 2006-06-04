# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPCs 
ARUJIEN = 7223 
MIRABEL = 7146 
HERBIEL = 7150 
GREENIS = 7157 

#ITEMS 
ARUJIENS_LETTER1 = 1092 
ARUJIENS_LETTER2 = 1093 
ARUJIENS_LETTER3 = 1094 
POETRY_BOOK      = 689 
GREENIS_LETTER   = 693 
 
#REWARDS 
ADENA            = 57 
BEGINNERS_POTION = 1073 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event 
   if event == "7223-04.htm" : 
     if st.getQuestItemsCount(ARUJIENS_LETTER1) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER2) == 0 and st.getQuestItemsCount(ARUJIENS_LETTER3) == 0 : 
       st.giveItems(ARUJIENS_LETTER1,1) 
     st.set("cond","1") 
     st.set("id","1") 
     st.setState(STARTED) 
     st.playSound("ItemSound.quest_accept") 
   elif event == "7223-08.htm" : 
     st.takeItems(ARUJIENS_LETTER3,-1) 
     st.giveItems(POETRY_BOOK,1) 
     st.set("cond","4") 
     st.set("id","4") 
     st.playSound("ItemSound.quest_middle") 
   elif event == "7223-10.htm" : 
     st.takeItems(ARUJIENS_LETTER3,-1) 
     st.giveItems(ADENA,450) 
     st.set("cond","0") 
     st.setState(COMPLETED) 
     st.playSound("ItemSound.quest_finish") 
   return htmltext 

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>" 
   npcId = npc.getNpcId()
   id = st.getState()
 
   if id == CREATED :
     st.set("cond","0")
     st.set("id","0") 
 
   cond = st.getInt("cond") 
 
   if npcId == ARUJIEN and id == CREATED : 
     if st.getPlayer().getRace().ordinal() != 1 and st.getPlayer().getRace().ordinal() != 0 : 
       htmltext = "7223-00.htm" 
     elif st.getPlayer().getLevel() >= 2 : 
       htmltext = "7223-02.htm" 
     else: 
       htmltext = "7223-01.htm" 
       st.exitQuest(1) 
   elif npcId == ARUJIEN and id == COMPLETED : 
     htmltext = "<html><head><body>This quest have already been completed.</body></html>" 
   elif npcId == ARUJIEN and cond >= 1 : 
     if st.getQuestItemsCount(ARUJIENS_LETTER1) : 
       htmltext = "7223-05.htm" 
     elif st.getQuestItemsCount(ARUJIENS_LETTER3) : 
       htmltext = "7223-07.htm" 
     elif st.getQuestItemsCount(ARUJIENS_LETTER2) : 
       htmltext = "7223-06.htm" 
     elif st.getQuestItemsCount(POETRY_BOOK) : 
       htmltext = "7223-11.htm" 
     elif st.getQuestItemsCount(GREENIS_LETTER) : 
       htmltext = "7223-10.htm" 
       st.takeItems(GREENIS_LETTER,-1) 
       st.giveItems(BEGINNERS_POTION,5) 
       st.set("cond","0") 
       st.setState(COMPLETED) 
       st.playSound("ItemSound.quest_finish") 
   elif npcId == MIRABEL and cond == 1 : 
     if st.getQuestItemsCount(ARUJIENS_LETTER1) : 
       htmltext = "7146-01.htm" 
       st.takeItems(ARUJIENS_LETTER1,-1) 
       st.giveItems(ARUJIENS_LETTER2,1) 
       st.set("cond","2") 
       st.set("id","2") 
       st.playSound("ItemSound.quest_middle") 
     elif st.getQuestItemsCount(ARUJIENS_LETTER2) or st.getQuestItemsCount(ARUJIENS_LETTER3) or st.getQuestItemsCount(POETRY_BOOK) or st.getQuestItemsCount(GREENIS_LETTER) : 
       htmltext = "7146-02.htm" 
   elif npcId == HERBIEL and cond == 2 and st.getQuestItemsCount(ARUJIENS_LETTER1) == 0 : 
     if st.getQuestItemsCount(ARUJIENS_LETTER2) : 
       htmltext = "7150-01.htm" 
       st.takeItems(ARUJIENS_LETTER2,-1) 
       st.giveItems(ARUJIENS_LETTER3,1) 
       st.set("cond","3") 
       st.set("id","3") 
       st.playSound("ItemSound.quest_middle") 
     elif st.getQuestItemsCount(ARUJIENS_LETTER3) or st.getQuestItemsCount(POETRY_BOOK) or st.getQuestItemsCount(GREENIS_LETTER) : 
       htmltext = "7150-02.htm" 
   elif npcId == GREENIS and cond == 4 : 
     if st.getQuestItemsCount(POETRY_BOOK) : 
       htmltext = "7157-02.htm" 
       st.takeItems(POETRY_BOOK,-1) 
       st.giveItems(GREENIS_LETTER,1) 
       st.set("cond","5") 
       st.set("id","5") 
       st.playSound("ItemSound.quest_middle") 
   elif npcId == GREENIS and st.getQuestItemsCount(GREENIS_LETTER) : 
     htmltext = "7157-03.htm" 
   elif npcId == GREENIS and (st.getQuestItemsCount(ARUJIENS_LETTER1) or st.getQuestItemsCount(ARUJIENS_LETTER2) or st.getQuestItemsCount(ARUJIENS_LETTER3)) : 
     htmltext = "7157-01.htm" 
   return htmltext

QUEST     = Quest(2,"2_WhatWomenWant1","What Women Want") 
CREATED   = State('Start',     QUEST) 
STARTED   = State('Started',   QUEST) 
COMPLETED = State('Completed', QUEST) 

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ARUJIEN) 

CREATED.addTalkId(ARUJIEN) 
COMPLETED.addTalkId(ARUJIEN) 

STARTED.addTalkId(MIRABEL) 
STARTED.addTalkId(HERBIEL) 
STARTED.addTalkId(GREENIS) 
STARTED.addTalkId(ARUJIEN) 

STARTED.addQuestDrop(ARUJIEN,GREENIS_LETTER,1) 
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER3,1) 
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER1,1) 
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER2,1) 
STARTED.addQuestDrop(ARUJIEN,POETRY_BOOK,1) 

print "importing quests: 2: What Women Want" 
