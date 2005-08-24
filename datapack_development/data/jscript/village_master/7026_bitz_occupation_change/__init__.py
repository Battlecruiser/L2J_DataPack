#
# Created by DraX on 2005.08.08
#

print "importing village master data: Talking Island Village ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GRAND_MASTER_BITZ = 7026

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7026-01.htm":
     st.exitQuest(True)
     return "7026-01.htm"

   if event == "7026-02.htm":
     st.exitQuest(True)
     return "7026-02.htm"

   if event == "7026-03.htm":
     st.exitQuest(True)
     return "7026-03.htm"

   if event == "7026-04.htm":
     st.exitQuest(True)
     return "7026-04.htm"

   if event == "7026-05.htm":
     st.exitQuest(True)
     return "7026-05.htm"

   if event == "7026-06.htm":
     st.exitQuest(True)
     return "7026-06.htm"

   if event == "7026-07.htm":
     st.exitQuest(True)
     return "7026-07.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Human´s got accepted
   if npcId == GRAND_MASTER_BITZ and Race in [Race.human]:
     if ClassId in [ClassId.fighter]:
       st.exitQuest(True)
       return "7026-01.htm"
     if ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue]:
       st.exitQuest(True)
       return "7026-08.htm"
     if ClassId in [ClassId.warlord, ClassId.paladin, ClassId.treasureHunter]:
       st.exitQuest(True)
       return "7026-09.htm"
     if ClassId in [ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.exitQuest(True)
       return "7026-09.htm"
     if ClassId in [ClassId.wizard, ClassId.cleric]:
       st.exitQuest(True)
       return "7026-10.htm"
     if ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.warlock, ClassId.bishop, ClassId.prophet]:
       st.exitQuest(True)
       return "7026-10.htm"

   # All other Races must be out
   if npcId == GRAND_MASTER_BITZ and Race in [Race.dwarf, Race.darkelf, Race.elf, Race.orc]:
     st.exitQuest(True)
     return "7026-10.htm"

QUEST   = Quest(7026,"7026_bitz_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7026)

STARTED.addTalkId(7026)