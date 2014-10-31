# Originally created by Ham Wong on 2007.03.07 #
import sys

from net.sf.l2j.gameserver.model.actor.instance import L2PcInstance
from net.sf.l2j.gameserver.model.quest          import State
from net.sf.l2j.gameserver.model.quest          import QuestState
from net.sf.l2j.gameserver.model.quest.jython   import QuestJython as JQuest
qn = "1103_OracleTeleport"
TOWN_DAWN = [31078,31079,31080,31081,31083,31084,31082,31692,31694,31997,31168]
TOWN_DUSK = [31085,31086,31087,31088,31090,31091,31089,31693,31695,31998,31169]
TEMPLE_PRIEST = [31127,31128,31129,31130,31131,31137,31138,31139,31140,31141]


class Quest (JQuest) :

 def __init__(self, id, name, descr): JQuest.__init__(self, id, name, descr)

 def onTalk (Self, npc, player):
    st = player.getQuestState(qn)
    if not st: return
    npcId = npc.getNpcId()
    xx = st.getInt("X")
    yy = st.getInt("Y")
    zz = st.getInt("Z")
    ##################
    # Dawn Locations #
    ##################
    if npcId in TOWN_DAWN: 
       st.setState(STARTED)
       st.set("X",str(int(st.getPlayer().getX())))
       st.set("Y",str(int(st.getPlayer().getY())))
       st.set("Z",str(int(st.getPlayer().getZ())))
       st.getPlayer().teleToLocation(-80157,111344,-4901)
    ##################
    # Dusk Locations #
    ##################
    elif npcId in TOWN_DUSK: 
       st.setState(STARTED)
       st.set("X",str(int(st.getPlayer().getX())))
       st.set("Y",str(int(st.getPlayer().getY())))
       st.set("Z",str(int(st.getPlayer().getZ())))
       st.getPlayer().teleToLocation(-81261,86531,-5157)
    #######################
    # Oracle of Dusk/Dawn #
    #######################
    elif npcId in TEMPLE_PRIEST and xx+yy+zz and st.getState() == STARTED :
       st.getPlayer().teleToLocation(xx,yy,zz) 
       st.exitQuest(1)
    return
   
        
QUEST      = Quest(1103, qn, "Teleports")
CREATED    = State('Start', QUEST)
STARTED    = State('Started', QUEST)

QUEST.setInitialState(CREATED)

for i in TOWN_DAWN+TOWN_DUSK :
    QUEST.addStartNpc(i)
    QUEST.addTalkId(i)

for j in TEMPLE_PRIEST :
    QUEST.addTalkId(j)

print "importing teleport data: 1103_OracleTeleport"
