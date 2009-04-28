# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project
# Visit http://www.l2jdp.com/forum/ for more details.
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Complete - 100%.
qn = "172_NewHorizons"

#NPC'S
ZENYA = 32140 
RAGARA = 32163

#ITEM'S
SCROLL_OF_ESCAPE_GIRAN = 7559 
MARK_OF_TRAVELER       = 7570 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr) 

 def onEvent (self,event,st) :
     htmltext = event
     if event == "32140-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     elif event == "32163-02.htm" :
       st.unset("cond")
       st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1)
       st.giveItems(MARK_OF_TRAVELER,1)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(False)
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     if id == State.COMPLETED :
       htmltext = "<html><body>This quest has already been completed.</body></html>"
     elif id == State.CREATED and npcId == ZENYA :
       if player.getLevel() >= 3 and player.getRace().ordinal() == 5 :
         htmltext = "32140-01.htm"
       else :
         htmltext = "32140-02.htm"
         st.exitQuest(1)       
     elif id == State.STARTED :
       if npcId == ZENYA : 
         if cond == 1 :
           htmltext = "32140-04.htm"
       elif npcId == RAGARA :
         if cond == 1 :
           htmltext = "32163-01.htm"
     return htmltext
 
QUEST = Quest(172,qn,"New Horizons") 
 
QUEST.addStartNpc(ZENYA) 

QUEST.addTalkId(ZENYA) 
QUEST.addTalkId(RAGARA)