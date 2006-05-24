# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BEAR_SKIN = 4259
ADENA = 57
CHANCE = 400000

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     if event == "7078-02.htm" :
        st.setState(STARTED)
        st.set("cond","1")
        st.playSound("ItemSound.quest_accept")
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     if id == CREATED :
         if level>=20 :
             htmltext = "7078-01.htm"
         else:
             htmltext = "<html><head><body>This quest can only be taken by characters level 20 and higher!</body></html>"
             st.exitQuest(1)
     elif cond==1 :
         if st.getQuestItemsCount(BEAR_SKIN)>=20 :
            htmltext = "7078-04.htm"
            st.giveItems(ADENA,3710)
            st.takeItems(BEAR_SKIN,-1)
            st.playSound("ItemSound.quest_finish")
            st.exitQuest(1)
         else :
            htmltext = "7078-03.htm"
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     cond = st.getInt("cond")
     if cond==1 :
         st.dropQuestItems(BEAR_SKIN,1,20,CHANCE,1)
     return

QUEST       = Quest(341,"341_HuntingForWildBeasts","Hunting For Wild Beasts")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7078)

CREATED.addTalkId(7078)
STARTED.addTalkId(7078)

STARTED.addQuestDrop(7078,BEAR_SKIN,1)

STARTED.addKillId(21)
STARTED.addKillId(203)
STARTED.addKillId(310)
STARTED.addKillId(335)

print "importing quests: 341: Hunting For Wild Beasts"
