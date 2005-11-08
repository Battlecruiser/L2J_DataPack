#
# Created by DraX on 2005.08.08
#

print "importing village master data: Dark Elven Temple      ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

TETRARCH_THIFIELL = 7358

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7358-01.htm":
     return "7358-01.htm"

   if event == "7358-02.htm":
     return "7358-02.htm"

   if event == "7358-03.htm":
     return "7358-03.htm"

   if event == "7358-04.htm":
     return "7358-04.htm"

   if event == "7358-05.htm":
     return "7358-05.htm"
   
   if event == "7358-06.htm":
     return "7358-06.htm"

   if event == "7358-07.htm":
     return "7358-07.htm"

   if event == "7358-08.htm":
     return "7358-08.htm"

   if event == "7358-09.htm":
     return "7358-09.htm"

   if event == "7358-10.htm":
     return "7358-10.htm"

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # DarkElfs got accepted
   if npcId == TETRARCH_THIFIELL and Race in [Race.darkelf]:
     if ClassId in [ClassId.darkFighter]: 
       st.setState(STARTED)
       return "7358-01.htm"
     if ClassId in [ClassId.darkMage]:
       st.setState(STARTED)
       return "7358-02.htm"
     if ClassId in [ClassId.darkWizard, ClassId.shillienOracle, ClassId.palusKnight, ClassId.assassin]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7358-12.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7358-13.htm"

   # All other Races must be out
   if npcId == TETRARCH_THIFIELL and Race in [Race.dwarf, Race.human, Race.elf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7358-11.htm"

QUEST     = Quest(7358,"7358_thifiell_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7358)

STARTED.addTalkId(7358)
