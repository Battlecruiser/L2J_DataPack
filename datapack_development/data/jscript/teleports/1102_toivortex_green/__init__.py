print "importing teleport data: 1102_toivortex_green"

import sys

from net.sf.l2j.gameserver.model              import L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GREEN_DIMENSION_STONE  	= 4401
DIMENSION_VORTEX_2      = 7953
DIMENSION_VORTEX_3      = 7954

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):
 # Dimension Vortex 2
   if npcId == DIMENSION_VORTEX_2:
     if st.getQuestItemsCount(GREEN_DIMENSION_STONE) >= 1:
       st.takeItems(GREEN_DIMENSION_STONE,1)
       st.player.teleToLocation(110930,15963,-4378)
       st.exitQuest(True)
       return
     else:
       st.exitQuest(True)
       return "1.htm"

 # Dimension Vortex 3
   if npcId == DIMENSION_VORTEX_3: 
     if st.getQuestItemsCount(GREEN_DIMENSION_STONE) >= 1:
       st.takeItems(GREEN_DIMENSION_STONE,1)
       st.player.teleToLocation(110930,15963,-4378)
       st.exitQuest(True)
       return
     else:
       st.exitQuest(True)
       return "1.htm"

QUEST       = Quest(1102,"1102_toivortex_green","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7953)
QUEST.addStartNpc(7954)

STARTED.addTalkId(7953)
STARTED.addTalkId(7954)