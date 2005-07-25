# Maked by Mr. Have fun! Version 0.2
print "importing quests: 329: Curiosity Of Dwarf"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GOLEM_HEARTSTONE_ID = 1346
BROKEN_HEARTSTONE_ID = 1365
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7437-03.htm"
    elif event == "7437_1" :
            htmltext = "7437-06.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "7437_2" :
            htmltext = "7437-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7437 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 33 :
            htmltext = "7437-02.htm"
            st.set("cond","1")
            return htmltext
          else:
            htmltext = "7437-01.htm"
        else:
          htmltext = "7437-01.htm"
   elif npcId == 7437 and int(st.get("cond"))==1 :
        if st.getQuestItemsCount(BROKEN_HEARTSTONE_ID)+st.getQuestItemsCount(GOLEM_HEARTSTONE_ID)>0 :
          if int(st.get("id")) != 329 :
            st.set("id","329")
            st.giveItems(ADENA_ID,40*st.getQuestItemsCount(BROKEN_HEARTSTONE_ID)+800*st.getQuestItemsCount(GOLEM_HEARTSTONE_ID))
            st.takeItems(BROKEN_HEARTSTONE_ID,st.getQuestItemsCount(BROKEN_HEARTSTONE_ID))
            st.takeItems(GOLEM_HEARTSTONE_ID,st.getQuestItemsCount(GOLEM_HEARTSTONE_ID))
            htmltext = "7437-05.htm"
        else:
          htmltext = "7437-04.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 85 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n<5 :
          st.giveItems(GOLEM_HEARTSTONE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n<58 :
            st.giveItems(BROKEN_HEARTSTONE_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 83 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n<6 :
          st.giveItems(GOLEM_HEARTSTONE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n<56 :
            st.giveItems(BROKEN_HEARTSTONE_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(329,"329_CuriosityOfDwarf","Curiosity Of Dwarf")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7437)

STARTED.addTalkId(7437)

STARTED.addKillId(83)
STARTED.addKillId(85)

STARTED.addQuestDrop(85,BROKEN_HEARTSTONE_ID,1)
STARTED.addQuestDrop(83,BROKEN_HEARTSTONE_ID,1)
STARTED.addQuestDrop(85,GOLEM_HEARTSTONE_ID,1)
STARTED.addQuestDrop(83,GOLEM_HEARTSTONE_ID,1)
