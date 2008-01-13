# Originally Created by Ham Wong on 2007.03.07
# updated by Kerberos on 2008.01.13
import sys

from net.sf.l2j.gameserver.model.actor.instance import L2PcInstance
from net.sf.l2j.gameserver.model.quest          import State
from net.sf.l2j.gameserver.model.quest          import QuestState
from net.sf.l2j.gameserver.model.quest.jython   import QuestJython as JQuest
qn = "1103_OracleTeleport"
TOWN_DAWN = [31078,31079,31080,31081,31083,31084,31082,31692,31694,31997,31168]
TOWN_DUSK = [31085,31086,31087,31088,31090,31091,31089,31693,31695,31998,31169]
TEMPLE_PRIEST = [31127,31128,31129,31130,31131,31137,31138,31139,31140,31141] + range(31488,31494)

TELEPORTERS = {
# Dawn
31078:1,
31079:2,
31080:3,
31081:4,
31083:5,
31084:6,
31082:7,
31692:8,
31694:9,
31997:10,
31168:11,
# Dusk
31085:12,
31086:13,
31087:14,
31088:15,
31090:16,
31091:17,
31089:18,
31693:19,
31695:20,
31998:21,
31169:22,
# Catacombs and Necropolis
31494:23,
31495:24,
31496:25,
31497:26,
31498:27,
31499:28,
31500:29,
31501:30,
31502:31,
31503:32,
31504:33,
31505:34,
31506:35,
31507:36
# Ziggurats
#
# will be done later
}

RETURN_LOCS = [[-80555,150337,-3040],[-13953,121404,-2984],[16354,142820,-2696],[83369,149253,-3400], \
              [83106,53965,-1488],[146983,26595,-2200],[111386,220858,-3544],[148256,-55454,-2779], \
              [45664,-50318,-800],[86795,-143078,-1341],[115136,74717,-2608],[-82368,151568,-3120], \
              [-14748,123995,-3112],[18482,144576,-3056],[81623,148556,-3464],[82819,54607,-1520], \
              [147570,28877,-2264],[112486,220123,-3592],[149888,-56574,-2979],[44528,-48370,-800], \
              [85129,-142103,-1542],[116642,77510,-2688],[-41572,209731,-5087],[-52872,-250283,-7908], \
              [ 45256,123906,-5411],[ 46192,170290,-4981],[111273,174015,-5437],[-20604,-250789,-8165], \
              [-21726, 77385,-5171],[140405, 79679,-5427],[-52366, 79097,-4741],[118311,132797,-4829], \
              [172185,-17602,-4901],[ 83000,209213,-5439],[-19500, 13508,-4901],[113865, 84543,-6541]]
class Quest (JQuest) :

 def __init__(self, id, name, descr): JQuest.__init__(self, id, name, descr)

 def onEvent (self,event,st) : 
    htmltext = event
    id=st.getInt("id")
    count=st.getInt("count")
    npcId = npc.getNpcId()
    if event == "Return":
       htmltext = None
       if npcId in TEMPLE_PRIEST and st.getState() == State.STARTED :
          return_id = st.getInt("id") - 1
          st.getPlayer().teleToLocation(RETURN_LOCS[return_id][0],RETURN_LOCS[return_id][1],RETURN_LOCS[return_id][2])
          st.exitQuest(1)
    elif event == "Dimensional":
       htmltext = "oracle.htm"
       st.getPlayer().teleToLocation(-114755,-179466,-6752)
    elif event == "5.htm" :
       if id :
          if count:
             htmltext="5a.htm"
          st.set("count",str(count+1))
          st.set("id",str(TELEPORTERS[npcId]))
          st.setState(State.STARTED)
          st.getPlayer().teleToLocation(-114790,-180576,-6781)
       else :
          htmltext="What are you trying to do?"
          st.exitQuest(1)
    elif event == "6.htm" :
       st.exitQuest(1)
    return htmltext

 def onTalk (Self, npc, player):
    st = player.getQuestState(qn)
    if not st: return
    npcId = npc.getNpcId()
    ##################
    # Dawn Locations #
    ##################
    if npcId in TOWN_DAWN: 
       st.setState(State.STARTED)
       st.set("id",str(TELEPORTERS[npcId]))
       st.playSound("ItemSound.quest_accept")
       st.getPlayer().teleToLocation(-80157,111344,-4901)
    ##################
    # Dusk Locations #
    ##################
    elif npcId in TOWN_DUSK: 
       st.setState(State.STARTED)
       st.set("id",str(TELEPORTERS[npcId]))
       st.playSound("ItemSound.quest_accept")
       st.getPlayer().teleToLocation(-81261,86531,-5157)
    elif npcId in range(31494,31508) +range(31095,31112)+range(31114,31125):
       if player.getLevel() < 20 :
          st.exitQuest(1)
          htmltext="1.htm"
       elif len(player.getAllActiveQuests()) > 23 :
          st.exitQuest(1)
          htmltext="1a.htm"
       elif not st.getQuestItemsCount(7079) :
          htmltext="3.htm"
       else :
          st.setState(State.CREATED)
          htmltext="4.htm"
    elif npcId in range(31095,31112)+range(31114,31125):
       if player.getLevel() < 20 :
          st.exitQuest(1)
          htmltext="ziggurats not supported yet"
       elif len(player.getAllActiveQuests()) > 23 :
          st.exitQuest(1)
          htmltext="ziggurats not supported yet"
       elif not st.getQuestItemsCount(7079) :
          htmltext="ziggurats not supported yet"
       else :
          #st.setState(State.CREATED)
          htmltext="ziggurats not supported yet"
    return

QUEST      = Quest(1103, qn, "Teleports")

for i in TELEPORTERS :
    QUEST.addStartNpc(i)
    QUEST.addTalkId(i)
for k in range(31494,31508)+range(31095,31112)+range(31114,31125):
    QUEST.addStartNpc(k)
    QUEST.addTalkId(k)
for j in TEMPLE_PRIEST :
    QUEST.addTalkId(j)