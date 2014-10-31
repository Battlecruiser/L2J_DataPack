# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

VENOM_SAC = 1077
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7351-04.htm" :
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
     if st.getPlayer().getLevel() >= 18 :
       htmltext = "7351-03.htm"
     else:
       htmltext = "7351-02.htm"
       st.exitQuest(1)
   else :
     if st.getQuestItemsCount(VENOM_SAC)<10 :
       htmltext = "7351-05.htm"
     else :
       st.takeItems(VENOM_SAC,-1)
       st.giveItems(ADENA,5810)
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
       htmltext = "7351-06.htm"
   return htmltext

 def onKill (self,npc,st):
   chance=12*(1+((npc.getNpcId()^34)/4))
   count=st.getQuestItemsCount(VENOM_SAC)
   if count < 10 and st.getRandom(100) < chance :
     st.giveItems(VENOM_SAC,1)
     if count == 9 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else :
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(324,"324_SweetestVenom","Sweetest Venom")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7351)

CREATED.addTalkId(7351)
STARTING.addTalkId(7351)
STARTED.addTalkId(7351)
COMPLETED.addTalkId(7351)

STARTED.addKillId(34)
STARTED.addKillId(38)
STARTED.addKillId(43)

STARTED.addQuestDrop(34,VENOM_SAC,1)

print "importing quests: 324: Sweetest Venom"
