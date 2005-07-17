# Maked by Mr. Have fun! Version 0.2
print "importing quests: 297: Gatekeepers Favor"
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
          st.set("id","0")
          if st.getPlayer().getLevel() >= 15 :
            htmltext = "7540-03.htm"
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
          else:
            htmltext = "7540-01.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7540 and int(st.get("cond"))==0 :
          if int(st.get("cond")) < 15 :
            htmltext = "7540-02.htm"
            st.set("cond","1")
            return htmltext
          else:
            htmltext = "7540-02.htm"
   elif npcId == 7540 and int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE2_ID)<20 :
          htmltext = "7540-04.htm"
   elif npcId == 7540 and int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE2_ID)>=20 :
          if int(st.get("id")) != 297 :
            st.set("id","297")
            htmltext = "7540-05.htm"
            st.takeItems(STARSTONE2_ID,st.getQuestItemsCount(STARSTONE2_ID))
            st.giveItems(GATEKEEPER_TOKEN_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 521 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(STARSTONE2_ID) < 20 :
          if st.getRandom(2) == 0 :
            if st.getQuestItemsCount(STARSTONE2_ID) == 19 :
              st.giveItems(STARSTONE2_ID,1)
              st.playSound("ItemSound.quest_middle")
            else:
              st.giveItems(STARSTONE2_ID,1)
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(297,"297_GatekeepersFavor","Gatekeepers Favor")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7540)

STARTED.addTalkId(7540)

STARTED.addKillId(521)

STARTED.addQuestDrop(521,STARSTONE2_ID,1)
