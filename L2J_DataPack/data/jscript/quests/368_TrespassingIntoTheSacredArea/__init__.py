# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ADENA = 57
BLADE_STAKATO_FANG = 5881
CHANCE = 9

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     if event == "7926-02.htm" :
         st.set("cond","1")
         st.setState(STARTED)
         st.playSound("ItemSound.quest_accept")
     elif event == "7926-05.htm" :
         st.playSound("ItemSound.quest_finish")
         st.exitQuest(1)
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     amount = st.getQuestItemsCount(BLADE_STAKATO_FANG)
     if id == CREATED :
        if level>=36 :
            htmltext = "7926-01.htm"
        else :
            htmltext = "<html><head><body>(This is a quest that can only be performed by players of level 36 and above.)</body></html>"
     elif cond and not amount :
         htmltext = "7926-03.htm"
     elif amount :
         htmltext = "7926-04.htm"
         st.giveItems(ADENA,amount*2250)
         st.takeItems(BLADE_STAKATO_FANG,-1)
         st.playSound("ItemSound.quest_middle")
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     random = st.getRandom(100)
     chance = CHANCE + npcId - 794
     if random<=chance :
         st.giveItems(BLADE_STAKATO_FANG,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(368,"368_TrespassingIntoTheSacredArea","Trespassing Into The Sacred Area")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7926)

CREATED.addTalkId(7926)
STARTED.addTalkId(7926)

STARTED.addQuestDrop(7926,BLADE_STAKATO_FANG,1)

for i in range(794,798) :
    STARTED.addKillId(i)

print "importing quests: 368: Trespassing Into The Sacred Area"


