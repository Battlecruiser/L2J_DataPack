# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

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
     st.takeItems(GREENIS_LETTER,-1)
     st.giveItems(BEGINNERS_POTION,5)
     st.giveItems(ADENA,450)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")
   count_AL1 = st.getQuestItemsCount(ARUJIENS_LETTER1)
   count_AL2 = st.getQuestItemsCount(ARUJIENS_LETTER2)
   count_AL3 = st.getQuestItemsCount(ARUJIENS_LETTER3)
   count_PB  = st.getQuestItemsCount(POETRY_BOOK)
   count_GL  = st.getQuestItemsCount(GREENIS_LETTER)

   if id == CREATED :
     if st.getPlayer().getRace().ordinal() != 1 and st.getPlayer().getRace().ordinal() != 0 :
       htmltext = "7223-00.htm"
     elif st.getPlayer().getLevel() >= 2 :
       htmltext = "7223-02.htm"
     else:
       htmltext = "7223-01.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == ARUJIEN and cond >= 1 :
     if count_AL1 :
       htmltext = "7223-05.htm"
     elif count_AL3 :
       htmltext = "7223-07.htm"
     elif count_AL2 :
       htmltext = "7223-06.htm"
     elif count_PB :
       htmltext = "7223-11.htm"
     elif count_GL :
       htmltext = "7223-10.htm"
   elif npcId == MIRABEL and cond == 1 :
     if count_AL1 :
       htmltext = "7146-01.htm"
       st.takeItems(ARUJIENS_LETTER1,-1)
       st.giveItems(ARUJIENS_LETTER2,1)
       st.set("cond","2")
       st.set("id","2")
       st.playSound("ItemSound.quest_middle")
     elif count_AL2 or count_AL3 or count_PB or count_GL :
       htmltext = "7146-02.htm"
   elif npcId == HERBIEL and cond == 2 and count_AL1 == 0 :
     if count_AL2 :
       htmltext = "7150-01.htm"
       st.takeItems(ARUJIENS_LETTER2,-1)
       st.giveItems(ARUJIENS_LETTER3,1)
       st.set("cond","3")
       st.set("id","3")
       st.playSound("ItemSound.quest_middle")
     elif count_AL3 or count_PB or count_GL :
       htmltext = "7150-02.htm"
   elif npcId == GREENIS and cond == 4 :
     if count_PB :
       htmltext = "7157-02.htm"
       st.takeItems(POETRY_BOOK,-1)
       st.giveItems(GREENIS_LETTER,1)
       st.set("cond","5")
       st.set("id","5")
       st.playSound("ItemSound.quest_middle")
   elif npcId == GREENIS and count_GL :
     htmltext = "7157-03.htm"
   elif npcId == GREENIS and (count_AL1 or count_AL2 or count_AL3) :
     htmltext = "7157-01.htm"
   return htmltext

qnum  = 2
qdef  = str(qnum) + "2_WhatWomenWant1"
qname = "What Women Want"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ARUJIEN)

CREATED.addTalkId(ARUJIEN)

STARTED.addTalkId(MIRABEL)
STARTED.addTalkId(HERBIEL)
STARTED.addTalkId(GREENIS)
STARTED.addTalkId(ARUJIEN)

COMPLETED.addTalkId(ARUJIEN)

STARTED.addQuestDrop(ARUJIEN,GREENIS_LETTER,1)
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER1,1)
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER2,1)
STARTED.addQuestDrop(ARUJIEN,ARUJIENS_LETTER3,1)
STARTED.addQuestDrop(ARUJIEN,POETRY_BOOK,1)

print "importing quests: " + str(qnum) + ": " + qname