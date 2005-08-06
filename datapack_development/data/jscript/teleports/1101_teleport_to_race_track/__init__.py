#
# Created by DraX on 2005.07.27
#

print "importing teleport data: 1101_teleport_to_race_track"

import sys

from net.sf.l2j.gameserver.model              import L2PcInstance
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
RACE_MANAGER = 7995

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):

   ###################
   # Start Locations #
   ###################

   # Gludin Village
   if npcId == RICHLIN: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","1")     
     return

   # Gludio Castle Town
   if npcId == BELLA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","2")     
     return

   # Dion Castle Town
   if npcId == TRISHA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","3")     
     return

   # Giran Castle Town
   if npcId == CLARISSA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","4")     
     return

   # Heine
   if npcId == FLAUEN: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","5")     
     return

   # Town of Oren
   if npcId == VALENTIA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","6")     
     return

   # Town of Aden
   if npcId == ELISA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","7")     
     return

   # Hunter´s Village
   if npcId == ESMERALDA: 
     st.player.teleToLocation(12661,181687,-3560)
     st.set("id","8")     
     return

   ############################
   # Monster Derby Race Track #
   ############################
   
   # back to Gludin Village
   if npcId == RACE_MANAGER and int(st.get("id")) == 1:
     st.player.teleToLocation(-80826,149775,-3043)
     st.exitQuest(True)
     return
   
   # back to Gludio Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 2:
     st.player.teleToLocation(-12672,122776,-3116)
     st.exitQuest(True)
     return
  
   # back to Dion Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 3:
     st.player.teleToLocation(15670,142983,-2705)
     st.exitQuest(True)
     return

   # back to Giran Castle Town
   if npcId == RACE_MANAGER and int(st.get("id")) == 4:
     st.player.teleToLocation(83400,147943,-3404)
     st.exitQuest(True)
     return

   # back to Heine
   if npcId == RACE_MANAGER and int(st.get("id")) == 5:
     st.player.teleToLocation(111409,219364,-3545)
     st.exitQuest(True)
     return

   # back to Town of Oren
   if npcId == RACE_MANAGER and int(st.get("id")) == 6:
     st.player.teleToLocation(82956,53162,-1495)
     st.exitQuest(True)
     return

   # back to Town of Aden
   if npcId == RACE_MANAGER and int(st.get("id")) == 7:
     st.player.teleToLocation(146331,25762,-2018)
     st.exitQuest(True)
     return

   # back to Hunter´s Village
   if npcId == RACE_MANAGER and int(st.get("id")) == 8:
     st.player.teleToLocation(116819,76994,-2714)
     st.exitQuest(True)
     return

QUEST       = Quest(1101,"1101_teleport_to_race_track","Teleports")
CREATED     = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7059)
QUEST.addStartNpc(7080)
QUEST.addStartNpc(7177)
QUEST.addStartNpc(7233)
QUEST.addStartNpc(7256)
QUEST.addStartNpc(7320)
QUEST.addStartNpc(7848)
QUEST.addStartNpc(7899)

STARTED.addTalkId(7059)
STARTED.addTalkId(7080)
STARTED.addTalkId(7177)
STARTED.addTalkId(7233)
STARTED.addTalkId(7256)
STARTED.addTalkId(7320)
STARTED.addTalkId(7848)
STARTED.addTalkId(7899)