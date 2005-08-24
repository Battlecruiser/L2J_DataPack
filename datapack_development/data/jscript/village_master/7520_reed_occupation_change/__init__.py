#
# Created by DraX on 2005.08.08
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WAREHOUSE_CHIEF_REED = 7520

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7520-01.htm":
     st.exitQuest(True)
     return "7520-01.htm"

   if event == "7520-02.htm":
     st.exitQuest(True)
     return "7520-02.htm"

   if event == "7520-03.htm":
     st.exitQuest(True)
     return "7520-03.htm"

   if event == "7520-04.htm":
     st.exitQuest(True)
     return "7520-04.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarf´s got accepted
   if npcId == WAREHOUSE_CHIEF_REED and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       st.exitQuest(True)
       return "7520-01.htm"
     if ClassId in [ClassId.scavenger, ClassId.artisan]:
       st.exitQuest(True)
       return "7520-05.htm"
     if ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       st.exitQuest(True)
       return "7520-06.htm"

   # All other Races must be out
   if npcId == WAREHOUSE_CHIEF_REED and Race in [Race.orc, Race.darkelf, Race.elf, Race.human]:
     st.exitQuest(True)
     return "7520-07.htm"

QUEST   = Quest(7520,"7520_reed_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7520)

STARTED.addTalkId(7520)