#
# Created by DraX on 2005.08.13
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PASS_FINAL_ID           = 1635
HEAD_BLACKSMITH_OPIX    = 7595

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7595-01.htm":
     st.exitQuest(1)
     return "7595-01.htm"

   if event == "7595-02.htm":
     st.exitQuest(1)
     return "7595-02.htm"

   if event == "7595-03.htm":
     st.exitQuest(1)
     return "7595-03.htm"

   if event == "7595-04.htm":
     st.exitQuest(1)
     return "7595-04.htm"

   if event == "class_change_56":
     if ClassId in [ClassId.dwarvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          st.exitQuest(1)
          return "7595-05.htm"
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          st.exitQuest(1)
          return "7595-06.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          st.exitQuest(1)
          return "7595-07.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          st.takeItems(PASS_FINAL_ID,1)
          st.player.setClassId(56)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7595-08.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarf´s got accepted
   if npcId == HEAD_BLACKSMITH_OPIX and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       st.exitQuest(1)
       return "7595-01.htm"
     if ClassId in [ClassId.scavenger, ClassId.artisan]:
       st.exitQuest(1)
       return "7595-09.htm"
     if ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       st.exitQuest(1)
       return "7595-10.htm"

   # All other Races must be out
   if npcId == HEAD_BLACKSMITH_OPIX and Race in [Race.elf, Race.darkelf, Race.orc, Race.human]:
     st.exitQuest(1)
     return "7595-11.htm"

QUEST   = Quest(7595,"7595_opix_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7595)

STARTED.addTalkId(7595)