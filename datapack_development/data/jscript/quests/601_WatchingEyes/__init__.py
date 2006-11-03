# by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
EYE_OF_ARGOS = 31683
#ITEMS
PROOF_OF_AVENGER = 7188
#CHANCE
DROP_CHANCE = 25
ADENA_CHANCE = 40
#MOBS
MOBS = [ 21306,21308,21309,21310,21311 ]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   if event == "31683-1.htm" :
      if st.getPlayer().getLevel() < 71 : 
         htmltext = "31683-0a.htm"
         st.exitQuest(1)
      else :
         st.set("cond","1")
         st.setState(STARTED)
         st.playSound("ItemSound.quest_accept")
   elif event == "31683-4.htm" :
     if st.getQuestItemsCount(PROOF_OF_AVENGER) == 100 :
        if st.getRandom(100) < ADENA_CHANCE :
           st.giveItems(57,230000)
        else :
           st.giveItems(6698+st.getRandom(3),5)
        st.takeItems(PROOF_OF_AVENGER,-1)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
     else :
        htmltext="31683-4a.htm"
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if cond == 0 :
       htmltext = "31683-0.htm"
   elif cond == 1 :
       htmltext = "31683-2.htm"
   elif cond == 2 :
       htmltext = "31683-3.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(PROOF_OF_AVENGER)
   if st.getInt("cond") == 1 and count < 100 :
     if st.getRandom(100) < DROP_CHANCE : 
       st.giveItems(PROOF_OF_AVENGER,1)
       if count == 99 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","2")
       else:
         st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(601,"601_WatchingEyes","Watching Eyes")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(EYE_OF_ARGOS)
CREATED.addTalkId(EYE_OF_ARGOS)
STARTED.addTalkId(EYE_OF_ARGOS)

for i in MOBS :
  STARTED.addKillId(i)

STARTED.addQuestDrop(EYE_OF_ARGOS,PROOF_OF_AVENGER,1)

print "importing quests: 601: Watching Eyes"
