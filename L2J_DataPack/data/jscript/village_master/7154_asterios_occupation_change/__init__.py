#
# Created by DraX on 2005.08.08
#

print "importing village master data: Elven Village          ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HIERARCH_ASTERIOS = 7154

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"

   if event == "7154-01.htm":
     htmltext = event

   if event == "7154-02.htm":
     htmltext = event

   if event == "7154-03.htm":
     htmltext = event

   if event == "7154-04.htm":
     htmltext = event

   if event == "7154-05.htm":
     htmltext = event
   
   if event == "7154-06.htm":
     htmltext = event

   if event == "7154-07.htm":
     htmltext = event

   if event == "7154-08.htm":
     htmltext = event

   if event == "7154-09.htm":
     htmltext = event

   if event == "7154-10.htm":
     htmltext = event
   return htmltext
 
 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs got accepted
   if npcId == HIERARCH_ASTERIOS and Race in [Race.elf]:
     if ClassId in [ClassId.elvenFighter]: 
       st.setState(STARTED)
       return "7154-01.htm"
     if ClassId in [ClassId.elvenMage]:
       st.setState(STARTED)
       return "7154-02.htm"
     if ClassId in [ClassId.elvenWizard, ClassId.oracle, ClassId.elvenKnight, ClassId.elvenScout]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7154-12.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7154-13.htm"

   # All other Races must be out
   if npcId == HIERARCH_ASTERIOS and Race in [Race.dwarf, Race.human, Race.darkelf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7154-11.htm"

QUEST     = Quest(7154,"7154_asterios_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7154)

STARTED.addTalkId(7154)
