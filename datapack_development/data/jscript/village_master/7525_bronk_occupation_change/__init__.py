#
# Created by DraX on 2005.08.08
#

print "importing village master data: Dwarven Village        ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HEAD_BLACKSMITH_BRONK = 7525

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7525-01.htm":
     st.exitQuest(True)
     return "7525-01.htm"

   if event == "7525-02.htm":
     st.exitQuest(True)
     return "7525-02.htm"

   if event == "7525-03.htm":
     st.exitQuest(True)
     return "7525-03.htm"

   if event == "7525-04.htm":
     st.exitQuest(True)
     return "7525-04.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarf´s got accepted
   if npcId == HEAD_BLACKSMITH_BRONK and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       st.exitQuest(True)
       return "7525-01.htm"
     if ClassId in [ClassId.artisan]:
       st.exitQuest(True)
       return "7525-05.htm"
     if ClassId in [ClassId.warsmith]:
       st.exitQuest(True)
       return "7525-06.htm"
     if ClassId in [ClassId.scavenger, ClassId.bountyHunter]:
       st.exitQuest(True)
       return "7525-07.htm"

   # All other Races must be out
   if npcId == HEAD_BLACKSMITH_BRONK and Race in [Race.orc, Race.darkelf, Race.elf, Race.human]:
     st.exitQuest(True)
     return "7525-07.htm"

QUEST   = Quest(7525,"7525_bronk_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7525)

STARTED.addTalkId(7525)