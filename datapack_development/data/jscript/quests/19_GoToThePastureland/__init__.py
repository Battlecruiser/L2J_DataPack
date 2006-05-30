# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
VLADIMIR = 8302
TUNATUN = 8537

#ITEMS
BEAST_MEAT = 7547

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "8302-1.htm" :
     st.giveItems(BEAST_MEAT,1)
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "8537-1.htm" :
     st.takeItems(BEAST_MEAT,1)
     st.giveItems(57,30000)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if npcId == VLADIMIR :
     if cond == 0 :
       if id == COMPLETED :
         htmltext = "<html><head><body>This quest has already been completed.</body></html>"
       elif st.getPlayer().getLevel() >= 63 :
         htmltext = "8302-0.htm"
       else:
         htmltext = "<html><head><body>Quest for characters level 63 or above.</body></html>"
         st.exitQuest(1)
     else :
       htmltext = "8302-2.htm"
   else :
       htmltext = "8537-0.htm"
   return htmltext

QUEST       = Quest(19,"19_GoToThePastureland","Go To The Pastureland")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(VLADIMIR)

CREATED.addTalkId(VLADIMIR)
STARTED.addTalkId(VLADIMIR)
STARTED.addTalkId(TUNATUN)

STARTED.addQuestDrop(VLADIMIR,BEAST_MEAT,1)
print "importing quests: 19: Go To The Pastureland"
