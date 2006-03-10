import sys

from net.sf.l2j.gameserver.model.actor.instance import      L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RED_DIMENSION_STONE	= 4403
DIMENSION_VORTEX_1      = 7952
DIMENSION_VORTEX_2      = 7953

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   if npcId in [ DIMENSION_VORTEX_1, DIMENSION_VORTEX_2 ] : 
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

for i in [DIMENSION_VORTEX_1,DIMENSION_VORTEX_2] :
   QUEST.addStartNpc(i)
   CREATED.addTalkId(i)

print "importing teleport data: 1102_toivortex_red"
