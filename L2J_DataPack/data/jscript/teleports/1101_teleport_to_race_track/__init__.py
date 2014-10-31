#
# Created by DraX on 2005.07.27. updated by mr.
#

print "importing teleport data: 1101_teleport_to_race_track"

import sys

from net.sf.l2j.gameserver.model.actor.instance import L2PcInstance
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

TRISHA	     = 7059
CLARISSA     = 7080
VALENTIA     = 7177
ESMERALDA    = 7233
BELLA        = 7256
RICHLIN	     = 7320
ELISA        = 7848
FLAUEN       = 7899
TATIANA	     = 8275
ILYANA	     = 8320
RACE_MANAGER = 7995

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()

   ###################
   # Start Locations #
   ###################

   # Gludin Village
   if npcId == RICHLIN: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","1")     
     return

   # Gludio Castle Town
   if npcId == BELLA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","2")     
     return

   # Dion Castle Town
   if npcId == TRISHA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","3")     
     return

   # Giran Castle Town
   if npcId == CLARISSA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","4")     
     return

   # Heine
   if npcId == FLAUEN: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","5")     
     return

   # Town of Oren
   if npcId == VALENTIA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","6")     
     return

   # Town of Aden
   if npcId == ELISA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","7")     
     return

   # Hunters Village
   if npcId == ESMERALDA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","8")     
     return

   # Rune Town
   if npcId == ILYANA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","9")     
     return

   # Goddard Castle Town
   if npcId == TATIANA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.setState(STARTED)
     st.set("id","10")     
     return

   ############################
   # Monster Derby Race Track #
   ############################
   
   # back to Gludin Village
   if npcId == RACE_MANAGER and int(st.get("id")) == 1:
     st.player.teleToLocation(-80826,149775,-3043)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return
   
   # back to Gludio Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 2:
     st.player.teleToLocation(-12672,122776,-3116)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return
  
   # back to Dion Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 3:
     st.player.teleToLocation(15670,142983,-2705)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Giran Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 4:
     st.player.teleToLocation(83400,147943,-3404)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Heine
   if npcId == RACE_MANAGER and int(st.get("id")) == 5:
     st.player.teleToLocation(111409,219364,-3545)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Town of Oren
   if npcId == RACE_MANAGER and int(st.get("id")) == 6:
     st.player.teleToLocation(82956,53162,-1495)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Town of Aden
   if npcId == RACE_MANAGER and int(st.get("id")) == 7:
     st.player.teleToLocation(146331,25762,-2018)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Hunters Village
   if npcId == RACE_MANAGER and int(st.get("id")) == 8:
     st.player.teleToLocation(116819,76994,-2714)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Rune Village
   if npcId == RACE_MANAGER and int(st.get("id")) == 9:
     st.player.teleToLocation(43835,-47749,-792)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

   # back to Goddard Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 10:
     st.player.teleToLocation(147930,-55281,-2728)
     st.setState(COMPLETED)
     st.exitQuest(1)
     return

QUEST       = Quest(1101,"1101_teleport_to_race_track","Teleports")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(TRISHA)
QUEST.addStartNpc(CLARISSA)
QUEST.addStartNpc(VALENTIA)
QUEST.addStartNpc(ESMERALDA)
QUEST.addStartNpc(BELLA)
QUEST.addStartNpc(RICHLIN)
QUEST.addStartNpc(ELISA)
QUEST.addStartNpc(FLAUEN)
QUEST.addStartNpc(ILYANA)
QUEST.addStartNpc(TATIANA)

CREATED.addTalkId(TRISHA)
CREATED.addTalkId(CLARISSA)
CREATED.addTalkId(VALENTIA)
CREATED.addTalkId(ESMERALDA)
CREATED.addTalkId(BELLA)
CREATED.addTalkId(RICHLIN)
CREATED.addTalkId(ELISA)
CREATED.addTalkId(FLAUEN)
CREATED.addTalkId(ILYANA)
CREATED.addTalkId(TATIANA)

STARTED.addTalkId(RACE_MANAGER)
