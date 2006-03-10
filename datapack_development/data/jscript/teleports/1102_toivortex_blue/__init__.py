import sys

from net.sf.l2j.gameserver.model.actor.instance import      L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLUE_DIMENSION_STONE    = 4402
DIMENSION_VORTEX_1      = 7952
DIMENSION_VORTEX_3      = 7954

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   if npcId in [ DIMENSION_VORTEX_1,DIMENSION_VORTEX_3 ] :
     if st.getQuestItemsCount(BLUE_DIMENSION_STONE) >= 1 :
       st.takeItems(BLUE_DIMENSION_STONE,1)
       st.player.teleToLocation(114097,19935,935)
       st.exitQuest(1)
       return
     else :
       st.exitQuest(1)
       return "1.htm"

QUEST       = Quest(1102,"1102_toivortex_blue","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

for i in [DIMENSION_VORTEX_1,DIMENSION_VORTEX_3] :
   QUEST.addStartNpc(i)
   CREATED.addTalkId(i)

print "importing teleport data: 1102_toivortex_blue"
