#
# Created by DraX on 2005.07.20
#
import sys

from net.sf.l2j.gameserver.model.actor.instance import      L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORC_GATEKEEPER_CHARM   	= 1658
DWARF_GATEKEEPER_TOKEN 	= 1659
WHIRPY			= 7540
TAMIL			= 7576

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   # ORC_VILLAGE
   if npcId == TAMIL: 
     if st.getQuestItemsCount(ORC_GATEKEEPER_CHARM) >= 1:
       st.takeItems(ORC_GATEKEEPER_CHARM,1)
       st.player.teleToLocation(-80826,149775,-3043)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "7576-01.htm"
   # DWARVEN_VILLAGE
   elif npcId == WHIRPY: 
     if st.getQuestItemsCount(DWARF_GATEKEEPER_TOKEN) >= 1:
       st.takeItems(DWARF_GATEKEEPER_TOKEN,1)
       st.player.teleToLocation(-80826,149775,-3043)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "7540-01.htm"

QUEST       = Quest(1100,"1100_teleport_with_charm","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

for i in [ WHIRPY, TAMIL ] :
    QUEST.addStartNpc(i)
    CREATED.addTalkId(i)

print "importing teleport data: 1100_teleport_with_charm"
