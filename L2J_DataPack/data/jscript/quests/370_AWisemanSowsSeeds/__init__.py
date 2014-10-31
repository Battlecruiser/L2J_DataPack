# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
CASIAN = 7612

#MOBS
MOBS = [82,84,86,89,90]

#ITEMS
CHAPTER_OF_FIRE,CHAPTER_OF_WATER,CHAPTER_OF_WIND,CHAPTER_OF_EARTH = range(5917,5921)

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7612-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7612-6.htm" :
     if st.getQuestItemsCount(CHAPTER_OF_FIRE) and \
        st.getQuestItemsCount(CHAPTER_OF_WATER) and \
        st.getQuestItemsCount(CHAPTER_OF_WIND) and \
        st.getQuestItemsCount(CHAPTER_OF_EARTH) :
       st.takeItems(CHAPTER_OF_FIRE,1)
       st.takeItems(CHAPTER_OF_WATER,1)
       st.takeItems(CHAPTER_OF_WIND,1)
       st.takeItems(CHAPTER_OF_EARTH,1)
       st.giveItems(57,3600)
       htmltext = "7612-8.htm"
   elif event == "7612-9.htm" :
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 28 :
       htmltext = "7612-0.htm"
     else:
       htmltext = "7612-0a.htm"
       st.exitQuest(1)
   elif cond :
     htmltext = "7612-4.htm"
   return htmltext

 def onKill (self,npc,st):
   chance = st.getRandom(100)
   if chance in range(1,15) and st.getQuestItemsCount(CHAPTER_OF_FIRE) == 0 :
     st.giveItems(CHAPTER_OF_FIRE,1)
     st.playSound("ItemSound.quest_itemget")
   elif chance in range(25,40) and st.getQuestItemsCount(CHAPTER_OF_WATER) == 0 :
     st.giveItems(CHAPTER_OF_WATER,1)
     st.playSound("ItemSound.quest_itemget")
   elif chance in range(50,65) and st.getQuestItemsCount(CHAPTER_OF_WIND) == 0 :
     st.giveItems(CHAPTER_OF_WIND,1)
     st.playSound("ItemSound.quest_itemget")
   elif chance in range(75,90) and st.getQuestItemsCount(CHAPTER_OF_EARTH) == 0 :
     st.giveItems(CHAPTER_OF_EARTH,1)
     st.playSound("ItemSound.quest_itemget")
   if st.getQuestItemsCount(CHAPTER_OF_FIRE) and st.getQuestItemsCount(CHAPTER_OF_WATER) and st.getQuestItemsCount(CHAPTER_OF_WIND) and st.getQuestItemsCount(CHAPTER_OF_EARTH) :
       st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(370,"370_AWisemanSowsSeeds","A Wiseman Sows Seeds")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(CASIAN)
CREATED.addTalkId(CASIAN)
STARTED.addTalkId(CASIAN)
for i in MOBS :
  STARTED.addKillId(i)

for i in range(5917,5921):
    STARTED.addQuestDrop(CASIAN,i,1)
    
print "importing quests: 370: A Wiseman Sows Seeds"
