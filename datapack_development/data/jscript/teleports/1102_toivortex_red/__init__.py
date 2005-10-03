print "importing teleport data: 1102_toivortex_red"

import sys

from net.sf.l2j.gameserver.model              import L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RED_DIMENSION_STONE	= 4403
DIMENSION_VORTEX_1      = 7952
DIMENSION_VORTEX_2      = 7953

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):
   # Dimension Vortex 1
   if npcId == DIMENSION_VORTEX_1: 
     if st.getQuestItemsCount(RED_DIMENSION_STONE) >= 1:
       st.takeItems(RED_DIMENSION_STONE,1)
       st.player.teleToLocation(118558,16659,5987)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "1.htm"
  
   # Dimension Vortex 2
   if npcId == DIMENSION_VORTEX_2: 
     if st.getQuestItemsCount(RED_DIMENSION_STONE) >= 1:
       st.takeItems(RED_DIMENSION_STONE,1)
       st.player.teleToLocation(118558,16659,5987)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "1.htm"

QUEST       = Quest(1102,"1102_toivortex_red","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7952)
QUEST.addStartNpc(7953)

STARTING.addTalkId(7952)
STARTING.addTalkId(7953)

STARTED.addTalkId(7952)
STARTED.addTalkId(7953)