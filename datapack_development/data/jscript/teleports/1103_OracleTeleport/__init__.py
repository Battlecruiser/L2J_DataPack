# Originally created by DraX on 2005.07.27, modified by Tempy #
import sys

from net.sf.l2j.gameserver.model.actor.instance import L2PcInstance
from net.sf.l2j.gameserver.model.quest          import State
from net.sf.l2j.gameserver.model.quest          import QuestState
from net.sf.l2j.gameserver.model.quest.jython   import QuestJython as JQuest

GLUDIN_DAWN = 31078
GLUDIN_DUSK = 31085
GLUDIO_DAWN = 31079
GLUDIO_DUSK = 31086
DION_DAWN = 31080
DION_DUSK = 31087
GIRAN_DAWN = 31081
GIRAN_DUSK = 31088
OREN_DAWN = 31083
OREN_DUSK = 31090
ADEN_DAWN = 31084
ADEN_DUSK = 31091
HEINE_DAWN = 31082
HEINE_DUSK = 31089
GODDARD_DAWN = 31962
GODDARD_DUSK = 31963
RUNE_DAWN = 31964
RUNE_DUSK = 31965
SCHUTTGART_DAWN = 31997
SCHUTTGART_DUSK = 31998
HV_DAWN = 31168
HV_DUSK = 31169

class Quest (JQuest) :

 def __init__(self, id, name, descr): JQuest.__init__(self, id, name, descr)

 def onTalk (Self, npc, st):
    npcId = npc.getNpcId()
   #############
   # Dawn Locations #
   #############
    if npcId == GLUDIN_DAWN :
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
      
    if npcId == GODDARD_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "8")   
     st.set("cabal", "2")
     return

    if npcId == RUNE_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "9")   
     st.set("cabal", "2")
     return

    if npcId == SCHUTTGART_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "10")   
     st.set("cabal", "2")
     return
     
    if npcId == HV_DAWN: 
     st.player.teleToLocation(-80157, 111344, -4901)
     st.setState(STARTED)
     st.set("id", "11")   
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

    if npcId == GODDARD_DUSK: 
     	st.player.teleToLocation(-81261, 86531, -5157)
     	st.setState(STARTED)
     	st.set("id", "8")   
     	st.set("cabal", "1")
     	return

    if npcId == RUNE_DUSK: 
    	st.player.teleToLocation(-81261, 86531, -5157)
     	st.setState(STARTED)
     	st.set("id", "9")   
     	st.set("cabal", "1")
     	return

    if npcId == SCHUTTGART_DUSK: 
    	st.player.teleToLocation(-81261, 86531, -5157)
     	st.setState(STARTED)
     	st.set("id", "10")   
     	st.set("cabal", "1")
     	return
     	
    if npcId == HV_DUSK: 
    	st.player.teleToLocation(-81261, 86531, -5157)
     	st.setState(STARTED)
     	st.set("id", "11")   
     	st.set("cabal", "1")
     	return
     	
   ################
   # Oracle of Dusk/Dawn #
   ################
   
   # back to Gludin Village
    if st.getInt("id") == 1:
        st.player.teleToLocation(-80826, 149775, -3043)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
   
   # back to Gludio Castle Town
    if st.getInt("id") == 2:
        st.player.teleToLocation(-12672, 122776, -3116)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
  
   # back to Dion Castle Town
    if st.getInt("id") == 3:
        st.player.teleToLocation(15670, 142983, -2705)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Giran Castle Town
    if st.getInt("id") == 4:
        st.player.teleToLocation(83400, 147943, -3404)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Town of Oren
    if st.getInt("id") == 5:
        st.player.teleToLocation(82956, 53162, -1495)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

   # back to Town of Aden
    if st.getInt("id") == 6:
        st.player.teleToLocation(146331, 25762, -2018)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

    # back to Heine
    if st.getInt("id") == 7:
        st.player.teleToLocation(111409, 219364, -3545)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

    # back to Goddard
    if st.getInt("id") == 8:
        st.player.teleToLocation(147928, -55273, -2734)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

    # back to Rune
    if st.getInt("id") == 9:
        st.player.teleToLocation(43799, -47727, -798)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return

    # back to Schuttgart
    if st.getInt("id") == 10:
        st.player.teleToLocation(87386, -143246, -1293)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
        
    # back to Hunters Village
    if st.getInt("id") == 11:
        st.player.teleToLocation(116819, 76994, -2714)
        st.setState(COMPLETED)
        st.exitQuest(1)
        return
        
QUEST    = Quest(1103, "1103_OracleTeleport", "Teleports")
CREATED    = State('Start', QUEST)
STARTED    = State('Started', QUEST)
COMPLETED    = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

for i in range(31078,31092)+range(31168,31170)+range(31692,31696)+range(31997,31999) :
    QUEST.addStartNpc(i)
    CREATED.addTalkId(i)
    STARTED.addTalkId(i)

for j in range(31127,31142) :
    STARTED.addTalkId(j)

print "importing teleport data: 1103_OracleTeleport"
