# Made by Mr. Have fun! - Version 0.3 by DrLecter

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORCISH_ARROWHEAD_ID = 963
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7029-04.htm"
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7029 and int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 10 :
        htmltext = "7029-03.htm"
        return htmltext
     else:
        htmltext = "7029-02.htm"
        st.exitQuest(1)
   elif npcId == 7029 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORCISH_ARROWHEAD_ID)<10 :
        htmltext = "7029-05.htm"
   elif npcId == 7029 and int(st.get("cond"))==2 and st.getQuestItemsCount(ORCISH_ARROWHEAD_ID)>=10 :
        st.takeItems(ORCISH_ARROWHEAD_ID,-1)
        st.playSound("ItemSound.quest_finish")
        st.giveItems(ADENA_ID,1000)
        st.addExpAndSp(1200,0)
        htmltext = "7029-06.htm"
        st.exitQuest(1)
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 361 :
      if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORCISH_ARROWHEAD_ID)<10 and st.getRandom(100)<40 :
         st.giveItems(ORCISH_ARROWHEAD_ID,1)
         if st.getQuestItemsCount(ORCISH_ARROWHEAD_ID) == 10 :
            st.set("cond","2") 
            st.playSound("ItemSound.quest_middle")
         else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(303,"303_CollectArrowheads","Collect Arrowheads")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7029)

CREATED.addTalkId(7029)
STARTED.addTalkId(7029)

STARTED.addKillId(361)
STARTED.addQuestDrop(361,ORCISH_ARROWHEAD_ID,1)

print "importing quests: 303: Collect Arrowheads"
