# Power of Darkness - Version 0.1 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
GALMAN=31044
#Items
STONE=5862
ADENA=57
#BASE CHANCE FOR DROP
CHANCE = 50

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "31044-04.htm" and cond == 0 :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "31044-08.htm" :
     st.exitQuest(1)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 55 :
       htmltext = "31044-02.htm"
     else:
       htmltext = "31044-01.htm"
       st.exitQuest(1)
   else :
     stone=st.getQuestItemsCount(STONE)
     if not stone :
       htmltext = "31044-05.htm"
     else :
       st.giveItems(ADENA,2500+230*stone)
       st.takeItems(STONE,-1)
       htmltext = "31044-06.htm"
   return htmltext

 def onKill (self,npc,st):
   if st.getRandom(100) < CHANCE :
     st.giveItems(STONE,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(353,"353_PowerOfDarkness","Power of Darkness")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(GALMAN)

CREATED.addTalkId(GALMAN)
STARTED.addTalkId(GALMAN)

for mob in [20284,20245,20244,20283] :
    STARTED.addKillId(mob)

STARTED.addQuestDrop(GALMAN,STONE,1)

print "importing quests: 353: Power of Darkness"
