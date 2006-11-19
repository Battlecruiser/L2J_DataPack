# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GIANT_SPIDER_SKIN = 1495
ADENA = 57
HEALING_POTION = 1061
WOODEN_ARROW = 17

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    count=st.getQuestItemsCount(GIANT_SPIDER_SKIN)
    if event == "30497-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30497-06.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    elif event == "30405-04.htm" :
      if count >= 10 :
         st.giveItems(HEALING_POTION,1)
         st.takeItems(GIANT_SPIDER_SKIN,10)
      else:
         htmltext="Incorrect item count"
    elif event == "30405-05.htm" :
      if count >= 10 :
         st.giveItems(WOODEN_ARROW,50)
         st.takeItems(GIANT_SPIDER_SKIN,10)
      else:
         htmltext="Incorrect item count"
    elif event == "30405-07.htm" :
      if st.getQuestItemsCount(GIANT_SPIDER_SKIN) >= 10 :
        htmltext = "30405-06.htm"
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id != STARTED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 15 :
       htmltext = "30497-02.htm"
     else:
       htmltext = "30497-01.htm"
       st.exitQuest(1)
   else :
     count=st.getQuestItemsCount(GIANT_SPIDER_SKIN)
     if npcId == 30497 :
       if count == 0 :
         htmltext = "30497-04.htm"
       else :
         htmltext = "30497-05.htm"
         st.giveItems(ADENA,count*25)
         st.takeItems(GIANT_SPIDER_SKIN,-1)
     else :
       if count < 10 :
         htmltext = "30405-01.htm"
       else :
         htmltext = "30405-02.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   st.giveItems(GIANT_SPIDER_SKIN,1)
   st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(259,"259_RanchersPlea","Ranchers Plea")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30497)

CREATED.addTalkId(30497)
STARTING.addTalkId(30497)
COMPLETED.addTalkId(30497)

STARTED.addTalkId(30405)
STARTED.addTalkId(30497)

STARTED.addKillId(20103)
STARTED.addKillId(20106)
STARTED.addKillId(20108)

STARTED.addQuestDrop(20103,GIANT_SPIDER_SKIN,1)

print "importing quests: 259: Ranchers Plea"
