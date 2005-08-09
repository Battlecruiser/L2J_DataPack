print "importing teleport data: 1102_toivortex_blue"

import sys

from net.sf.l2j.gameserver.model              import L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLUE_DIMENSION_STONE    = 4402
DIMENSION_VORTEX_1      = 7952
DIMENSION_VORTEX_3      = 7954

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):
   # Dimension Vortex 1
   if npcId == DIMENSION_VORTEX_1: 
     if st.getQuestItemsCount(BLUE_DIMENSION_STONE) >= 1:
       st.takeItems(BLUE_DIMENSION_STONE,1)
       st.player.teleToLocation(114097,19935,935)
       st.exitQuest(True)
       return
     else:
       st.exitQuest(True)
       return "1.htm"

   # Dimension Vortex 3
   if npcId == DIMENSION_VORTEX_3: 
     if st.getQuestItemsCount(BLUE_DIMENSION_STONE) >= 1:
       st.takeItems(BLUE_DIMENSION_STONE,1)
       st.player.teleToLocation(114097,19935,935)
       st.exitQuest(True)
       return
     else:
       st.exitQuest(True)
       return "1.htm"

QUEST       = Quest(1102,"1102_toivortex_blue","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7952)
QUEST.addStartNpc(7954)

STARTED.addTalkId(7952)
STARTED.addTalkId(7954)