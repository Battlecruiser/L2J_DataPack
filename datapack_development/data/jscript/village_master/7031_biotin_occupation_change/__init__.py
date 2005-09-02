#
# Created by DraX on 2005.08.08
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HIGH_PRIEST_BIOTIN = 7031

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "7031-01.htm":
     st.exitQuest(1)
     return "7031-01.htm"

   if event == "7031-02.htm":
     st.exitQuest(1)
     return "7031-02.htm"

   if event == "7031-03.htm":
     st.exitQuest(1)
     return "7031-03.htm"

   if event == "7031-04.htm":
     st.exitQuest(1)
     return "7031-04.htm"

   if event == "7031-05.htm":
     st.exitQuest(1)
     return "7031-05.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Human´s got accepted
   if npcId == HIGH_PRIEST_BIOTIN and Race in [Race.human]:
     if ClassId in [ClassId.fighter, ClassId.warrior, ClassId.knight, ClassId.rogue]:
       st.exitQuest(1)
       return "7031-08.htm"
     if ClassId in [ClassId.warlord, ClassId.paladin, ClassId.treasureHunter]:
       st.exitQuest(1)
       return "7031-08.htm"
     if ClassId in [ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.exitQuest(1)
       return "7031-08.htm"
     if ClassId in [ClassId.wizard, ClassId.cleric]:
       st.exitQuest(1)
       return "7031-06.htm"
     if ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.warlock, ClassId.bishop, ClassId.prophet]:
       st.exitQuest(1)
       return "7031-07.htm"
     else:
       st.exitQuest(1)
       return "7031-01.htm"

   # All other Races must be out
   if npcId == HIGH_PRIEST_BIOTIN and Race in [Race.dwarf, Race.darkelf, Race.elf, Race.orc]:
     st.exitQuest(1)
     return "7031-08.htm"

QUEST   = Quest(7031,"7031_biotin_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7031)

STARTED.addTalkId(7031)