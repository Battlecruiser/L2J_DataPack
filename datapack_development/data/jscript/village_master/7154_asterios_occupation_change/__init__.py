#
# Created by DraX on 2005.08.08
#

print "importing village master data: 7154_asterios_occupation_change"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HIERARCH_ASTERIOS = 7154

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7154-01.htm":
     st.exitQuest(True)
     return "7154-01.htm"

   if event == "7154-02.htm":
     st.exitQuest(True)
     return "7154-02.htm"

   if event == "7154-03.htm":
     st.exitQuest(True)
     return "7154-03.htm"

   if event == "7154-04.htm":
     st.exitQuest(True)
     return "7154-04.htm"

   if event == "7154-05.htm":
     st.exitQuest(True)
     return "7154-05.htm"
   
   if event == "7154-06.htm":
     st.exitQuest(True)
     return "7154-06.htm"

   if event == "7154-07.htm":
     st.exitQuest(True)
     return "7154-07.htm"

   if event == "7154-08.htm":
     st.exitQuest(True)
     return "7154-08.htm"

   if event == "7154-09.htm":
     st.exitQuest(True)
     return "7154-09.htm"

   if event == "7154-10.htm":
     st.exitQuest(True)
     return "7154-10.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elf´s got accepted
   if npcId == HIERARCH_ASTERIOS and Race in [Race.elf]:
     if ClassId in [ClassId.elvenFighter]: 
       st.exitQuest(True)
       return "7154-01.htm"
     if ClassId in [ClassId.elvenMage]:
       st.exitQuest(True)
       return "7154-02.htm"
     if ClassId in [ClassId.elvenWizard, ClassId.oracle, ClassId.elvenKnight, ClassId.elvenScout]:
       st.exitQuest(True)
       return "7154-12.htm"
     else:
       st.exitQuest(True)
       return "7154-13.htm"

   # All other Races must be out
   if npcId == HIERARCH_ASTERIOS and Race in [Race.dwarf, Race.human, Race.darkelf, Race.orc]:
     st.exitQuest(True)
     return "7154-11.htm"

QUEST   = Quest(7154,"7154_asterios_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7154)

STARTED.addTalkId(7154)