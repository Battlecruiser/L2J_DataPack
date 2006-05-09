# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ADENA = 57
ALLIGATOR = 135
ALLIGATOR_PELTS = 4337
CHANCE = 90

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     if event == "7892-00a.htm" :
         htmltext = "7892-00a.htm"
         st.exitQuest(1)
     elif event == "7892-02.htm" :
         st.setState(STARTED)
         st.set("cond","1")
         st.playSound("ItemSound.quest_accept")
     elif event == "2" :
         st.exitQuest(1)
         st.playSound("ItemSound.quest_finish")
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     amount = st.getQuestItemsCount(ALLIGATOR_PELTS)*40
     if id == CREATED :
        if level>=40 :
           htmltext = "7892-01.htm"
        else :
           htmltext = "7892-00.htm"
     elif cond==1 :
        if amount :
           htmltext = "7892-03.htm"
           st.giveItems(ADENA,amount)
           st.takeItems(ALLIGATOR_PELTS,-1)
        else :
           htmltext = "7892-04.htm"
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     if st.getRandom(100)<CHANCE :
         st.giveItems(ALLIGATOR_PELTS,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(338,"338_AlligatorHunter","Alligator Hunter")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7892)

CREATED.addTalkId(7892)
STARTED.addTalkId(7892)

STARTED.addKillId(ALLIGATOR)
STARTED.addQuestDrop(ALLIGATOR,ALLIGATOR_PELTS,1)

print "importing quests: 338: Alligator Hunter"
