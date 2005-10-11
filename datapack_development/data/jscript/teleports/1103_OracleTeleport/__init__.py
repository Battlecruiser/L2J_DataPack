# Originally created by DraX on 2005.07.27, modified by Tempy #
print "importing teleport data: 1103_OracleTeleport"

import sys

from net.sf.l2j.gameserver.model            import L2PcInstance
from net.sf.l2j.gameserver.model.quest      import State
from net.sf.l2j.gameserver.model.quest      import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GLUDIN_DAWN = 8078
GLUDIN_DUSK = 8085
GLUDIO_DAWN = 8079
GLUDIO_DUSK = 8086
DION_DAWN = 8080
DION_DUSK = 8087
GIRAN_DAWN = 8081
GIRAN_DUSK = 8088
OREN_DAWN = 8083
OREN_DUSK = 8090
ADEN_DAWN = 8084
ADEN_DUSK = 8091
HEINE_DAWN = 8082
HEINE_DUSK = 8089

class Quest (JQuest) :

 def __init__(self, id, name, descr): JQuest.__init__(self, id, name, descr)

 def onTalk (Self, npcId, st):

   #############
   # Dawn Locations #
   #############
    if npcId == GLUDIN_DAWN:
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "1")
     st.set("cabal", "2")
     return
 
    if npcId == GLUDIO_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "2")   
     st.set("cabal", "2")
     return
 
    if npcId == DION_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "3")   
     st.set("cabal", "2")
     return
 
    if npcId == GIRAN_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "4")   
     st.set("cabal", "2")
     return
 
    if npcId == OREN_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "5")   
     st.set("cabal", "2")
     return
 
    if npcId == ADEN_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "6")   
     st.set("cabal", "2")
     return
 
    if npcId == HEINE_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "7")   
     st.set("cabal", "2")
     return
     
   ############
   # Dusk Locations #
   ############
 
    if npcId == GLUDIN_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "1")    
        st.set("cabal", "1")
        return

    if npcId == GLUDIO_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "2")    
        st.set("cabal", "1")
        return

    if npcId == DION_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "3")    
        st.set("cabal", "1")
        return

    if npcId == GIRAN_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "4")    
        st.set("cabal", "1")
        return

    if npcId == OREN_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "5")    
        st.set("cabal", "1")
        return

    if npcId == ADEN_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "6")    
        st.set("cabal", "1")
        return

    if npcId == HEINE_DUSK: 
        st.player.teleToLocation(-81261, 86531, -5157)
        st.setState(STARTED)
        st.set("id", "7")    
        st.set("cabal", "1")
        return

   ################
   # Oracle of Dusk/Dawn #
   ################
   
   # back to Gludin Village
    if int(st.get("id")) == 1:
        st.player.teleToLocation(-80826, 149775, -3043)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
   
   # back to Gludio Castle Town
    if int(st.get("id")) == 2:
        st.player.teleToLocation(-12672, 122776, -3116)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
  
   # back to Dion Castle Town
    if int(st.get("id")) == 3:
        st.player.teleToLocation(15670, 142983, -2705)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Giran Castle Town
    if int(st.get("id")) == 4:
        st.player.teleToLocation(83400, 147943, -3404)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Town of Oren
    if int(st.get("id")) == 5:
        st.player.teleToLocation(82956, 53162, -1495)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Town of Aden
    if int(st.get("id")) == 6:
        st.player.teleToLocation(146331, 25762, -2018)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

    # back to Heine
    if int(st.get("id")) == 7:
        st.player.teleToLocation(111409, 219364, -3545)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

QUEST    = Quest(1103, "1103_OracleTeleport", "Teleports")
CREATED    = State('Start', QUEST)
STARTED    = State('Started', QUEST)
COMPLETED    = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(8078)
QUEST.addStartNpc(8079)
QUEST.addStartNpc(8080)
QUEST.addStartNpc(8081)
QUEST.addStartNpc(8082)
QUEST.addStartNpc(8083)
QUEST.addStartNpc(8084)
QUEST.addStartNpc(8085)
QUEST.addStartNpc(8086)
QUEST.addStartNpc(8087)
QUEST.addStartNpc(8088)
QUEST.addStartNpc(8089)
QUEST.addStartNpc(8090)
QUEST.addStartNpc(8091)
    
CREATED.addTalkId(8078)
CREATED.addTalkId(8079)
CREATED.addTalkId(8080)
CREATED.addTalkId(8081)
CREATED.addTalkId(8082)
CREATED.addTalkId(8083)
CREATED.addTalkId(8084)
CREATED.addTalkId(8085)
CREATED.addTalkId(8086)
CREATED.addTalkId(8087)
CREATED.addTalkId(8088)
CREATED.addTalkId(8089)
CREATED.addTalkId(8090)
CREATED.addTalkId(8091)

STARTED.addTalkId(8127)
STARTED.addTalkId(8128)
STARTED.addTalkId(8129)
STARTED.addTalkId(8130)
STARTED.addTalkId(8131)
STARTED.addTalkId(8137)
STARTED.addTalkId(8138)
STARTED.addTalkId(8139)
STARTED.addTalkId(8140)
STARTED.addTalkId(8141)
