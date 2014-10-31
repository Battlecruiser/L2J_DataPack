# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WIND_SHARD = 1078
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7361-04.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7361-08.htm" :
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 18 :
       htmltext = "7361-03.htm"
     else:
       htmltext = "7361-02.htm"
       st.exitQuest(1)
   else :
     count = st.getQuestItemsCount(WIND_SHARD)
     if count :
       st.giveItems(ADENA,30*count)
       st.takeItems(WIND_SHARD,-1)
       htmltext = "7361-07.htm"
     else :
       htmltext = "7361-05.htm"
   return htmltext

 def onKill (self,npc,st):
   st.giveItems(WIND_SHARD,1)
   st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(317,"317_CatchTheWind","Catch The Wind")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7361)

CREATED.addTalkId(7361)
STARTING.addTalkId(7361)
STARTED.addTalkId(7361)
COMPLETED.addTalkId(7361)

STARTED.addKillId(36)
STARTED.addKillId(44)

STARTED.addQuestDrop(36,WIND_SHARD,1)

print "importing quests: 317: Catch The Wind"
