# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORC_AMULET = 1116
ORC_NECKLACE = 1117
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7346-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7346-06.htm" :
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
       htmltext = "7346-00.htm"
       st.exitQuest(1)
     elif st.getPlayer().getLevel()<8 :
       htmltext = "7346-01.htm"
       st.exitQuest(1)
     else :
       htmltext = "7346-02.htm"
   else :
     amulet = st.getQuestItemsCount(ORC_AMULET)
     necklace = st.getQuestItemsCount(ORC_NECKLACE)
     if amulet == necklace == 0 :
       htmltext = "7346-04.htm"
     else :
       htmltext = "7346-05.htm"
       st.giveItems(ADENA_ID,amulet*20+necklace*30)
       st.takeItems(ORC_AMULET,-1)
       st.takeItems(ORC_NECKLACE,-1)
   return htmltext

 def onKill (self,npc,st):
   item=ORC_NECKLACE
   if npc.getNpcId() == 385 :
     item = ORC_AMULET
   if st.getRandom(10)>4 :
     st.giveItems(item,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(263,"263_KillAllSylphs1","Kill All Sylphs1")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7346)

CREATED.addTalkId(7346)
STARTING.addTalkId(7346)
STARTED.addTalkId(7346)
COMPLETED.addTalkId(7346)

STARTED.addKillId(385)
STARTED.addKillId(386)
STARTED.addKillId(387)
STARTED.addKillId(388)

STARTED.addQuestDrop(385,ORC_AMULET,1)
STARTED.addQuestDrop(386,ORC_NECKLACE,1)

print "importing quests: 263: Kill All Sylphs1"
