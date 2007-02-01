# by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
EYE_OF_ARGOS = 31683
#ITEMS
EYE_OF_DARKNESS = 7189
#CHANCE
CHANCE = 30
#MOBS
MOBS = [ 21299,21304 ]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   if event == "31683-1.htm" :
     if st.getPlayer().getLevel() >= 68 : 
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
     else :
        htmltext = "31683-0a.htm"
        st.exitQuest(1)
   if event == "31683-4.htm" :
     if st.getQuestItemsCount(EYE_OF_DARKNESS) == 100 :
        st.giveItems(57,100000)
        st.takeItems(EYE_OF_DARKNESS,-1)
        st.addExpAndSp(140000,11250)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
     else :
        htmltext = "31683-4a.htm"
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond = st.getInt("cond")
   if cond == 0 :
      htmltext = "31683-0.htm"
   elif cond == 1 :
      htmltext = "31683-2.htm"
   elif cond == 2 :
      htmltext = "31683-3.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(EYE_OF_DARKNESS)
   if st.getInt("cond") == 1 and st.getRandom(100) < CHANCE :
     qty=1+st.getRandom(2)
     if count+qty>100 :
        qty=100-count
     if count+qty == 100 :
        st.playSound("ItemSound.quest_middle")
        st.set("cond","2")
     else :
        st.playSound("ItemSound.quest_itemget")
     st.giveItems(EYE_OF_DARKNESS,qty)
   return

QUEST       = Quest(602,"602_ShadowOfLight","Shadow Of Light")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST,True)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(EYE_OF_ARGOS)
CREATED.addTalkId(EYE_OF_ARGOS)
STARTED.addTalkId(EYE_OF_ARGOS)

for i in MOBS :
  STARTED.addKillId(i)

STARTED.addQuestDrop(EYE_OF_ARGOS,EYE_OF_DARKNESS,1)

print "importing quests: 602: Shadow Of Light"
