#
# Created by DraX on 2005.08.08 modified by Ariakas on 2005.09.19
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

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7525-01.htm":
     return "7525-01.htm"

   if event == "7525-02.htm":
     return "7525-02.htm"

   if event == "7525-03.htm":
     return "7525-03.htm"

   if event == "7525-04.htm":
     return "7525-04.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarfs got accepted
   if npcId == HEAD_BLACKSMITH_BRONK and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       htmltext = "7525-01.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.artisan]:
       htmltext = "7525-05.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     if ClassId in [ClassId.warsmith]:
       htmltext = "7525-06.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     if ClassId in [ClassId.scavenger, ClassId.bountyHunter]:
       htmltext = "7525-07.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   if npcId == HEAD_BLACKSMITH_BRONK and Race in [Race.orc, Race.darkelf, Race.elf, Race.human]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7525-07.htm"

QUEST   = Quest(7525,"7525_bronk_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7525)

STARTED.addTalkId(7525)
