#
# Created by DraX on 2005.08.13
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RING_OF_RAVEN_ID        = 1642
WAREHOUSE_CHIEF_RIKADIO = 7503

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7503-01.htm":
     st.exitQuest(1)
     return "7503-01.htm"

   if event == "7503-02.htm":
     st.exitQuest(1)
     return "7503-02.htm"

   if event == "7503-03.htm":
     st.exitQuest(1)
     return "7503-03.htm"

   if event == "7503-04.htm":
     st.exitQuest(1)
     return "7503-04.htm"

   if event == "class_change_54":
     if ClassId in [ClassId.dwarvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(RING_OF_RAVEN_ID) == 0:
          st.exitQuest(1)
          return "7503-05.htm"
        if Level <= 19 and st.getQuestItemsCount(RING_OF_RAVEN_ID) >= 1:
          st.exitQuest(1)
          return "7503-06.htm"
        if Level >= 20 and st.getQuestItemsCount(RING_OF_RAVEN_ID) == 0:
          st.exitQuest(1)
          return "7503-07.htm"
        if Level >= 20 and st.getQuestItemsCount(RING_OF_RAVEN_ID) >= 1:
          st.takeItems(RING_OF_RAVEN_ID,1)
          st.player.setClassId(54)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7503-08.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarf´s got accepted
   if npcId == WAREHOUSE_CHIEF_RIKADIO and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       st.exitQuest(1)
       return "7503-01.htm"
     if ClassId in [ClassId.scavenger, ClassId.artisan]:
       st.exitQuest(1)
       return "7503-09.htm"
     if ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       st.exitQuest(1)
       return "7503-10.htm"

   # All other Races must be out
   if npcId == WAREHOUSE_CHIEF_RIKADIO and Race in [Race.elf, Race.darkelf, Race.orc, Race.human]:
     st.exitQuest(1)
     return "7503-11.htm"

QUEST   = Quest(7503,"7503_rikadio_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7503)

STARTED.addTalkId(7503)