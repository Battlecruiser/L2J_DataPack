# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

IMP_SHACKLES = 1368
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7357-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7357-06.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getRace().ordinal() != 2 :
       htmltext = "7357-00.htm"
       st.exitQuest(1)
     else :
       if st.getPlayer().getLevel()<6 :
          htmltext = "7357-01.htm"
          st.exitQuest(1)
       else:
          htmltext = "7357-02.htm"
   else :
     count=st.getQuestItemsCount(IMP_SHACKLES)
     if count :
       st.giveItems(ADENA,13*count)
       st.takeItems(IMP_SHACKLES,-1)
       htmltext = "7357-05.htm"
     else:
       htmltext = "7357-04.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   chance=5+(npcId^4)
   if st.getRandom(10)<chance :
     st.giveItems(IMP_SHACKLES,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(265,"265_ChainsOfSlavery","Chains Of Slavery")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7357)

CREATED.addTalkId(7357)
STARTING.addTalkId(7357)
STARTED.addTalkId(7357)
COMPLETED.addTalkId(7357)

STARTED.addKillId(4)
STARTED.addKillId(5)

STARTED.addQuestDrop(4,IMP_SHACKLES,1)

print "importing quests: 265: Chains Of Slavery"
