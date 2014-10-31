# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FUNGUS_SAC = 707
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7137-03.htm" :
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
     if st.getPlayer().getLevel() >= 8 :
       htmltext = "7137-02.htm"
     else:
       htmltext = "7137-01.htm"
       st.exitQuest(1)
   else :
     if st.getQuestItemsCount(FUNGUS_SAC)<10 :
       htmltext = "7137-04.htm"
     else :
       st.giveItems(ADENA,3000)
       st.takeItems(FUNGUS_SAC,-1)
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
       htmltext = "7137-05.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(FUNGUS_SAC)
   chance = 3
   if npc.getNpcId() == 400 : chance += 1
   if count < 10 and st.getRandom(10) < chance :
     st.giveItems(FUNGUS_SAC,1)
     if count == 9 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else :
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(262,"262_BringMeMushrooms1","Bring Me Mushrooms1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7137)

CREATED.addTalkId(7137)
STARTING.addTalkId(7137)
STARTED.addTalkId(7137)
COMPLETED.addTalkId(7137)

STARTED.addKillId(400)
STARTED.addKillId(7)

STARTED.addQuestDrop(400,FUNGUS_SAC,1)

print "importing quests: 262: Bring Me Mushrooms1"
