# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GIANT_SPIDER_LEG = 1087
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7222-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 15 :
       htmltext = "7222-02.htm"
     else:
       htmltext = "7222-01.htm"
       st.exitQuest(1)
   else :
     if st.getQuestItemsCount(GIANT_SPIDER_LEG) >= 8 :
       st.giveItems(ADENA,1000)
       st.addExpAndSp(2000,0)
       st.takeItems(GIANT_SPIDER_LEG,-1)
       htmltext = "7222-05.htm"
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
     else:
       htmltext = "7222-04.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(GIANT_SPIDER_LEG)
   if count < 8 :
     st.giveItems(GIANT_SPIDER_LEG,1)
     if count == 7 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(261,"261_DreamOfMoneylender1","Dream Of Moneylender1")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7222)

CREATED.addTalkId(7222)
STARTING.addTalkId(7222)
STARTED.addTalkId(7222)
COMPLETED.addTalkId(7222)

STARTED.addKillId(308)
STARTED.addKillId(460)
STARTED.addKillId(466)

STARTED.addQuestDrop(460,GIANT_SPIDER_LEG,1)

print "importing quests: 261: Dream Of Moneylender1"
