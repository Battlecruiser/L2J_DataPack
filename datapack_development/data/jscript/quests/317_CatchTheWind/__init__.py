# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "317_CatchTheWind"

WIND_SHARD = 1078
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30361-04.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30361-08.htm" :
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 18 :
       htmltext = "30361-03.htm"
     else:
       htmltext = "30361-02.htm"
       st.exitQuest(1)
   else :
     count = st.getQuestItemsCount(WIND_SHARD)
     if count :
       if count > 9 :
          st.giveItems(ADENA,2988+40*count)
       else :
          st.giveItems(ADENA,40*count)
       st.takeItems(WIND_SHARD,-1)
       htmltext = "30361-07.htm"
     else :
       htmltext = "30361-05.htm"
   return htmltext

 def onKill (self,npc,st):
   if st.getRandom(100) < 50:
      st.giveItems(WIND_SHARD,1)
      st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(317,qn,"Catch The Wind")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30361)

CREATED.addTalkId(30361)
STARTING.addTalkId(30361)
STARTED.addTalkId(30361)
COMPLETED.addTalkId(30361)

STARTED.addKillId(20036)
STARTED.addKillId(20044)

STARTED.addQuestDrop(20036,WIND_SHARD,1)

print "importing quests: 317: Catch The Wind"
