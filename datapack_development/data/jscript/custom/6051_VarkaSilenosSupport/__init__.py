# Created by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "6051_VarkaSilenosSupport"

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
 
 def onEvent (self,event,st):
    return
 
 def onTalk (self,npc,player):
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext

     npcId = npc.getNpcId()
     id = st.getState()
     Alevel = st.getPlayer().getAllianceWithVarkaKetra()
     if Alevel == -2 :
        htmltext = "1.htm"
     elif Alevel == -3 or Alevel == -4 :
         htmltext = "2.htm"
     elif Alevel == -5 :
         htmltext = "3.htm"
     else :
         htmltext = "no.htm"
     return htmltext

QUEST       = Quest(6051, qn, "custom")
CREATED     = State('Start', QUEST)
COMPLETED   = State('Completed',   QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(31382)
QUEST.addTalkId(31382)

print "importing quests: 6051: Varka Silenos Support"