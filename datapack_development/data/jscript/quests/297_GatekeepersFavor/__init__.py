# Made by Mr. Have fun! Version 0.2
# Fixed by Pela Version 0.3 - Enough credits, but DrLecter was here :D
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

STARSTONE2_ID = 1573
GATEKEEPER_TOKEN_ID = 1659

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
       if st.getPlayer().getLevel() >= 15 :
          htmltext = "7540-03.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
       else:
          htmltext = "7540-01.htm"
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
      st.set("cond","0")
   if npcId == 7540 :
      if int(st.get("cond"))==0 :
         htmltext = "7540-02.htm"
      elif int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE2_ID)<20 :
         htmltext = "7540-04.htm"
      elif int(st.get("cond"))==2 and st.getQuestItemsCount(STARSTONE2_ID)==20 :
         htmltext = "7540-05.htm"
         st.takeItems(STARSTONE2_ID,-1)
         st.giveItems(GATEKEEPER_TOKEN_ID,2)
         st.exitQuest(1)
         st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 521 :
      if int(st.get("cond")) == 1 and st.getQuestItemsCount(STARSTONE2_ID) < 20 :
         if st.getRandom(2) == 0 :
            st.giveItems(STARSTONE2_ID,1) 
            if st.getQuestItemsCount(STARSTONE2_ID) == 20 :
               st.playSound("ItemSound.quest_middle")
               st.set("cond","2")
            else:
               st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(297,"297_GatekeepersFavor","Gatekeepers Favor")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7540)
CREATED.addTalkId(7540)

STARTED.addTalkId(7540)
STARTED.addKillId(521)
STARTED.addQuestDrop(521,STARSTONE2_ID,1)

print "importing quests: 297: Gatekeepers Favor"
