# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "246_PossessorOfAPreciousSoul_3"

#NPC
LADD = 30721
CARADINE = 31740
OSSIAN = 31741

#QUEST ITEM
CARADINE_LETTER = 7678
CARADINE_LETTER_LAST = 7679
WATERBINDER = 7591
EVERGREEN = 7592
RAIN_SONG = 7593
RELIC_BOX = 7594

#MOBS
PILGRIM_OF_SPLENDOR = 21541
JUDGE_OF_SPLENDOR = 21544
BARAKIEL = 25325

#CHANCE FOR DROP
CHANCE_FOR_DROP = 5

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "31740-4.htm" :
     if cond == 0 :
       st.setState(STARTED)
       st.takeItems(CARADINE_LETTER,1)
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
   if event == "31741-2.htm" :
     if cond == 1 :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
   if event == "31741-5.htm" :
     if cond == 3 :
       st.set("cond","4")
       st.takeItems(WATERBINDER,1)
       st.takeItems(EVERGREEN,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31741-9.htm" :
     if cond == 5 :
       st.set("cond","6")
       st.takeItems(RAIN_SONG,1)
       st.giveItems(RELIC_BOX,1)
       st.playSound("ItemSound.quest_middle")
   if event == "30721-2.htm" :
     if cond == 6 :
       st.set("cond","0")
       st.takeItems(RELIC_BOX,1)
       st.giveItems(CARADINE_LETTER_LAST,1)
       st.playSound("ItemSound.quest_finish")
       st.setState(COMPLETED)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond = st.getInt("cond")
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if st.getPlayer().isSubClassActive() :
     if npcId == CARADINE and cond == 0 and st.getQuestItemsCount(CARADINE_LETTER) == 1 :
       if id == COMPLETED :
         htmltext = "<html><head><body>This quest have already been completed.</body></html>"
       elif st.getPlayer().getLevel() < 65 : 
         htmltext = "31740-2.htm"
         st.exitQuest(1)
       elif st.getPlayer().getLevel() >= 65 :
         htmltext = "31740-1.htm"
     if npcId == CARADINE and cond == 1 :
       htmltext = "31740-5.htm"
     if npcId == OSSIAN and cond == 1 :
       htmltext = "31741-1.htm"
     if npcId == OSSIAN and cond == 2 :
       htmltext = "31741-4.htm"
     if npcId == OSSIAN and cond == 3 and st.getQuestItemsCount(WATERBINDER) == 1 and st.getQuestItemsCount(EVERGREEN) == 1 :
       htmltext = "31741-3.htm"
     if npcId == OSSIAN and cond == 4 :
       htmltext = "31741-8.htm"
     if npcId == OSSIAN and cond == 5 and st.getQuestItemsCount(RAIN_SONG) == 1 :
       htmltext = "31741-7.htm"
     if npcId == OSSIAN and cond == 6 and st.getQuestItemsCount(RELIC_BOX) == 1 :
       htmltext = "31741-11.htm"
     if npcId == LADD and cond == 6 :
       htmltext = "30721-1.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   chance = st.getRandom(100)
   cond = st.getInt("cond")
   if npcId == PILGRIM_OF_SPLENDOR :
     if cond == 2 and st.getQuestItemsCount(WATERBINDER) < 1 :
       if chance < CHANCE_FOR_DROP :
         st.giveItems(WATERBINDER,1)
         if st.getQuestItemsCount(EVERGREEN) < 1 :
           st.playSound("ItemSound.quest_itemget")
         else:
           st.playSound("ItemSound.quest_middle")
           st.set("cond","3")
   if npcId == JUDGE_OF_SPLENDOR :
     if cond == 2 and st.getQuestItemsCount(EVERGREEN) < 1 :
       if chance < CHANCE_FOR_DROP :
         st.giveItems(EVERGREEN,1)
         if st.getQuestItemsCount(WATERBINDER) < 1 :
           st.playSound("ItemSound.quest_itemget")
         else:
           st.playSound("ItemSound.quest_middle")
           st.set("cond","3")
   if npcId == BARAKIEL :
     if cond == 4 and st.getQuestItemsCount(RAIN_SONG) < 1 :
       st.giveItems(RAIN_SONG,1)
       st.playSound("ItemSound.quest_middle")
       st.set("cond","5")
   return 

QUEST       = Quest(246,qn,"Possessor Of A Precious Soul - 3")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST,True)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(CARADINE)
CREATED.addTalkId(CARADINE)
STARTED.addTalkId(CARADINE)
STARTED.addTalkId(OSSIAN)
STARTED.addTalkId(LADD)

STARTED.addKillId(PILGRIM_OF_SPLENDOR)
STARTED.addKillId(JUDGE_OF_SPLENDOR)
STARTED.addKillId(BARAKIEL)

STARTED.addQuestDrop(CARADINE,CARADINE_LETTER_LAST,1)
STARTED.addQuestDrop(CARADINE,WATERBINDER,1)
STARTED.addQuestDrop(CARADINE,EVERGREEN,1)
STARTED.addQuestDrop(CARADINE,RAIN_SONG,1)
STARTED.addQuestDrop(CARADINE,RELIC_BOX,1)

print "importing quests: 246: Possessor Of A Precious Soul - 3"
